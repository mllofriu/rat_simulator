package robot.virtual;


//import Rat;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.vecmath.Vector3f;

import robot.IRobot;
import support.Configuration;
import support.Utiles;



public class VirtualRobot extends java.awt.Frame implements IRobot {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5581489496269696656L;

	private final String DEFAULT_MAZE_DIR = Configuration
			.getString("WorldFrame.MAZE_DIRECTORY");
	private final String DEFAULT_MAZE_FILE = Configuration
			.getString("WorldFrame.MAZE_FILE");
	private final String CURRENT_MAZE_DIR = System.getProperty("user.dir")
			+ File.separatorChar + DEFAULT_MAZE_DIR + File.separatorChar;
	private final int MAX_PIXEL_LATERAL = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_LATERAL");
	private final int MAX_PIXEL_DIAGONAL = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_DIAGONAL");
	private final int MAX_PIXEL_FRENTE = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_FRENTE");
	public static final double SPEED_ERROR = Configuration.getDouble("Robot.SPEED_ERROR");
	private static final double CLOSE_TO_FOOD_THRS = 0.015; 
	private static final float STEP = 0.1f;
	
	public ExperimentUniverse world;
	
	public VirtualRobot(){
		this.world = new ExperimentUniverse(CURRENT_MAZE_DIR+DEFAULT_MAZE_FILE);
		WorldFrame worldFrame = new WorldFrame(world);
		worldFrame.setVisible(true);
	}

	public VirtualRobot(ExperimentUniverse world) {
		this.world = world;
		
		WorldFrame worldFrame = new WorldFrame(world);
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
			world.moveRobot(new Vector3f(0f,0f,-STEP));
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
		Vector3f food = world.getFood();
		food.sub(world.getRobotPosition());
		double distanceToFood = food.length();
		return distanceToFood < CLOSE_TO_FOOD_THRS;
	}

	@Override
	public Point2D.Double getGlobalCoodinate() {
		Vector3f rPos = world.getRobotPosition();
		return new Point2D.Double(rPos.x, rPos.z);
	}

}
