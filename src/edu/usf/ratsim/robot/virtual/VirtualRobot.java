package edu.usf.ratsim.robot.virtual;

//import Rat;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.vecmath.Vector3f;

import edu.usf.ratsim.experiment.ExpUniverseFactory;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;

public class VirtualRobot implements IRobot {

	private final int MAX_PIXEL_LATERAL = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_LATERAL");
	private final int MAX_PIXEL_DIAGONAL = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_DIAGONAL");
	private final int MAX_PIXEL_FRENTE = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_FRENTE");
	private static final float STEP = Configuration
			.getFloat("RobotVirtual.Step");

	public VirtualExpUniverse universe;

	private boolean[] affordances;

	private boolean validCachedAffordances;

	public VirtualRobot() {
		ExperimentUniverse univ = ExpUniverseFactory.getUniverse();
		if (!(univ instanceof VirtualExpUniverse))
			throw new RuntimeException(
					"Virtual robot can only be run with a virtual universe");

		this.universe = (VirtualExpUniverse) univ;
		if (Configuration.getBoolean("UniverseFrame.display")){
			UniverseFrame worldFrame = new UniverseFrame(universe);
			worldFrame.setVisible(true);
		}
		
		validCachedAffordances = false;
	}

	public VirtualRobot(VirtualExpUniverse world) {
		this.universe = world;

		if (Configuration.getBoolean("UniverseFrame.display")){
			UniverseFrame worldFrame = new UniverseFrame(world);
			worldFrame.setVisible(true);
		}
		
		validCachedAffordances = false;
	}

	@Override
	public boolean[] affordances() {
		// Use cache if robot has not moved
		if (!validCachedAffordances){
			BufferedImage[] pan = getPanoramica();
	
			affordances = new boolean[IRobot.NUM_POSSIBLE_ACTIONS];
	
			affordances[Utiles.discretizeAction(-90)] = Utiles.contador(
					pan[0], Color.red) < MAX_PIXEL_LATERAL;
			affordances[Utiles.discretizeAction(90)] = Utiles.contador(
					pan[4], Color.red) < MAX_PIXEL_LATERAL;
			affordances[Utiles.discretizeAction(0)] = Utiles.contador(pan[2],
					Color.red) < MAX_PIXEL_FRENTE;
			affordances[Utiles.discretizeAction(-45)] = Utiles.contador(
					pan[1], Color.red) < MAX_PIXEL_DIAGONAL;
			affordances[Utiles.discretizeAction(45)] = Utiles.contador(
					pan[3], Color.red) < MAX_PIXEL_DIAGONAL;
	
			affordances[Utiles.discretizeAction(-180)] = true;
			affordances[Utiles.discretizeAction(180)] = true;
			affordances[Utiles.discretizeAction(-135)] = true;
			affordances[Utiles.discretizeAction(135)] = true;
			
			validCachedAffordances = true;
		}

		return affordances;
	}

	@Override
	synchronized public BufferedImage[] getPanoramica() {
		BufferedImage[] panoramica = new BufferedImage[RobotNode.NUM_ROBOT_VIEWS];

		Canvas3D[] offScreenCanvas = universe.getRobotOffscreenCanvas();
		ImageComponent2D[] offScreenImages = universe.getRobotOffscreenImages();

		for (int i = 0; i < RobotNode.NUM_ROBOT_VIEWS; i++) {
			offScreenCanvas[i].renderOffScreenBuffer();
			offScreenCanvas[i].waitForOffScreenRendering();
			panoramica[i] = offScreenImages[i].getImage();
		}

		return panoramica;
	}

	@Override
	public void rotate(float grados) {
		universe.rotateRobot(grados);
		validCachedAffordances = false;
	}

	@Override
	public void startRobot() {
	}

	@Override
	public boolean hasFoundFood() {
		return universe.hasRobotFoundFood();
	}

	@Override
	public void forward() {
		universe.moveRobot(new Vector3f(STEP, 0f, 0f));		
		validCachedAffordances = false;
	}

}
