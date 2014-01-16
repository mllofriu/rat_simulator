package robot.virtual;


//import Rat;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Vector3f;

import robot.IRobot;
import support.Configuration;
import support.Utiles;
import experiment.ExpUniverseFactory;
import experiment.ExperimentUniverse;



public class VirtualRobot extends java.awt.Frame implements IRobot {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5581489496269696656L;

	private final int MAX_PIXEL_LATERAL = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_LATERAL");
	private final int MAX_PIXEL_DIAGONAL = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_DIAGONAL");
	private final int MAX_PIXEL_FRENTE = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_FRENTE");
	private static final float STEP = 0.01f;
	
	public VirtualExpUniverse world;
	
	public VirtualRobot(){
		ExperimentUniverse univ = ExpUniverseFactory.getUniverse();
		if (! (univ instanceof VirtualExpUniverse) )
			throw new RuntimeException("Virtual robot can only be run with a virtual universe");
		
		this.world = (VirtualExpUniverse) univ;
		UniverseFrame worldFrame = new UniverseFrame(world);
		worldFrame.setVisible(true);
	}

	public VirtualRobot(VirtualExpUniverse world) {
		this.world = world;
		
		UniverseFrame worldFrame = new UniverseFrame(world);
		worldFrame.setVisible(true);
	}

	@Override
	public boolean[] affordances() {
		BufferedImage[] pan = getPanoramica();
		
		boolean[] affordances = new boolean[IRobot.NUM_POSSIBLE_ACTIONS];;

		affordances[Utiles.gradosRelative2Acccion(-90)] =
				Utiles.contador(pan[0], Color.red) < MAX_PIXEL_LATERAL;
		affordances[Utiles.gradosRelative2Acccion(90)] =
				Utiles.contador(pan[4], Color.red) < MAX_PIXEL_LATERAL;
		affordances[Utiles.gradosRelative2Acccion(0)] = 
				Utiles.contador(pan[2], Color.red) < MAX_PIXEL_FRENTE;
		affordances[Utiles.gradosRelative2Acccion(-45)] =
				Utiles.contador(pan[1], Color.red) < MAX_PIXEL_DIAGONAL;
		affordances[Utiles.gradosRelative2Acccion(45)] = 
				Utiles.contador(pan[3], Color.red) < MAX_PIXEL_DIAGONAL;
		
		affordances[Utiles.gradosRelative2Acccion(-180)] = true;
		affordances[Utiles.gradosRelative2Acccion(180)] = true;
		affordances[Utiles.gradosRelative2Acccion(-135)] = true;
		affordances[Utiles.gradosRelative2Acccion(135)] = true;

		return affordances;
	}

	@Override
	synchronized public BufferedImage[] getPanoramica() {
		BufferedImage[] panoramica = new BufferedImage[RobotNode.NUM_ROBOT_VIEWS];
		
		Canvas3D[] offScreenCanvas = world.getRobotOffscreenCanvas();
		ImageComponent2D[] offScreenImages = world.getRobotOffscreenImages();
		
		for (int i = 0; i < RobotNode.NUM_ROBOT_VIEWS; i++){
			offScreenCanvas[i].renderOffScreenBuffer();
			offScreenCanvas[i].waitForOffScreenRendering();
			panoramica[i] = offScreenImages[i].getImage();
		}
		
		return panoramica;
	}
	
	@Override
	public void doAction(int grados) {
		// If no rotation, do translation
		if (grados == 0)
			world.moveRobot(new Vector3f(STEP,0f,0f));
		else
			rotateRobot(grados);
	}

	@Override
	public void startRobot() {
	}

	public void rotateRobot(int actionDegrees) {
		world.rotateRobot(Math.toRadians(actionDegrees));
	}

	@Override
	public boolean hasFoundFood() {
		return world.hasRobotFoundFood();
	}

}
