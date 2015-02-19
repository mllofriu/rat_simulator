package edu.usf.ratsim.robot.virtual;

//import Rat;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import edu.usf.ratsim.experiment.universe.virtual.RobotNode;
import edu.usf.ratsim.experiment.universe.virtual.UniverseFrame;
import edu.usf.ratsim.experiment.universe.virtual.VirtUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.robot.Landmark;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.GeomUtils;

public class VirtualRobot implements IRobot {

	// private final int MAX_PIXEL_LATERAL = Configuration
	// .getInt("RobotVirtual.MAX_PIXEL_LATERAL");
	// private final int MAX_PIXEL_DIAGONAL = Configuration
	// .getInt("RobotVirtual.MAX_PIXEL_DIAGONAL");
	// private final int MAX_PIXEL_FRENTE = Configuration
	// .getInt("RobotVirtual.MAX_PIXEL_FRENTE");
	public static final float STEP = Configuration
			.getFloat("RobotVirtual.Step");

	public VirtUniverse universe;

	// private boolean[] affordances;

	private boolean validCachedAffordances;

	private Random r;

	public VirtualRobot(VirtUniverse world) {
		this.universe = world;

		if (Configuration.getBoolean("UniverseFrame.display")) {
			UniverseFrame worldFrame = new UniverseFrame(world);
			worldFrame.setVisible(true);
		}

		validCachedAffordances = false;
		
		r = new Random();
	}

	public boolean[] getAffordances() {
		// Use cache if robot has not moved
		// if (!validCachedAffordances){
		// BufferedImage[] pan = getPanoramica();
		//
		// affordances = new boolean[IRobot.NUM_POSSIBLE_ACTIONS];
		//
		// affordances[Utiles.discretizeAction(-90)] = Utiles.contador(
		// pan[0], Color.red) < MAX_PIXEL_LATERAL;
		// affordances[Utiles.discretizeAction(90)] = Utiles.contador(
		// pan[4], Color.red) < MAX_PIXEL_LATERAL;
		// affordances[Utiles.discretizeAction(0)] = Utiles.contador(
		// pan[2], Color.red) < MAX_PIXEL_FRENTE;
		// affordances[Utiles.discretizeAction(-45)] = Utiles.contador(
		// pan[1], Color.red) < MAX_PIXEL_DIAGONAL;
		// affordances[Utiles.discretizeAction(45)] = Utiles.contador(
		// pan[3], Color.red) < MAX_PIXEL_DIAGONAL;
		//
		// affordances[Utiles.discretizeAction(-180)] = true;
		// affordances[Utiles.discretizeAction(180)] = true;
		// affordances[Utiles.discretizeAction(-135)] = true;
		// affordances[Utiles.discretizeAction(135)] = true;
		//
		// validCachedAffordances = true;
		// }
		//
		// return affordances;

		// Lighter version of the affordance checking
		// long time = System.currentTimeMillis();
		// boolean[] ret = universe.getRobotAffordances();
		// System.out.println("Affordances took " + (System.currentTimeMillis()
		// - time));
		// return ret;
		return universe.getRobotAffordances();
	}

	synchronized public BufferedImage[] getPanoramica() {
		BufferedImage[] panoramica = new BufferedImage[RobotNode.NUM_ROBOT_VIEWS];

		Canvas3D[] offScreenCanvas = universe.getRobotOffscreenCanvas();
		ImageComponent2D[] offScreenImages = universe.getRobotOffscreenImages();

		if (Configuration.getBoolean("UniverseFrame.display")){
			long time = System.currentTimeMillis();
			// First schedulle all renderings
			for (int i = 0; i < RobotNode.NUM_ROBOT_VIEWS; i++) {
				offScreenCanvas[i].renderOffScreenBuffer();
			}
	
			for (int i = 0; i < RobotNode.NUM_ROBOT_VIEWS; i++) {
				offScreenCanvas[i].waitForOffScreenRendering();
				panoramica[i] = offScreenImages[i].getImage();
			}
			System.out.println((System.currentTimeMillis() - time));
		}

		
		return panoramica;
	}

	public void rotate(float grados) {
		universe.rotateRobot(grados + .2f * r.nextFloat() * grados);
		validCachedAffordances = false;
	}

	public void startRobot() {
	}

	public boolean hasFoundFood() {
		return universe.hasRobotFoundFood();
	}

	public void forward() {
		universe.moveRobot(new Vector3f(STEP + STEP * r.nextFloat() * .2f, 0f, 0f));
		validCachedAffordances = false;
	}

	@Override
	public boolean[] getAffordances(int lookahead) {
		return universe.getRobotAffordances(lookahead);
	}

	@Override
	public void eat() {
		universe.robotEat();
	}

	@Override
	public boolean hasTriedToEat() {
		return universe.hasRobotTriedToEat();
	}

	@Override
	public List<Landmark> getLandmarks() {
		List<Landmark> res = new LinkedList<Landmark>();
		for(Integer i : universe.getFeeders())
			if(universe.canRobotSeeFeeder(i)){
				// Get relative position
				Point3f fPos = universe.getFoodPosition(i);
				Point3f rPos = universe.getRobotPosition();
				Point3f relFPos = new Point3f(GeomUtils.pointsToVector(rPos, fPos));
				// Rotate to robots framework
				Quat4f rRot = universe.getRobotOrientation();
				rRot.inverse();
				Transform3D t = new Transform3D();
				t.setRotation(rRot);
				t.transform(relFPos);
				// Return the landmark
				res.add(new Landmark(i, relFPos));
			}
				
		
		return res;
	}

}
