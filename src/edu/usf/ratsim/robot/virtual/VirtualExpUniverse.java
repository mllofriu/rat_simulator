package edu.usf.ratsim.robot.virtual;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Locale;
import javax.media.j3d.Transform3D;
import javax.media.j3d.View;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineSegment;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;
import edu.usf.ratsim.support.XMLDocReader;

/**
 * This universe class creates a universe from an XML file and exposes
 * functionalities needed for performing experiments.
 * 
 * @author ludo
 * 
 */
public class VirtualExpUniverse extends VirtualUniverse implements
		ExperimentUniverse {

	private static final float CLOSE_TO_FOOD_THRS = Configuration
			.getFloat("VirtualUniverse.closeToFood");

	private static final int LOOKAHEADSTEPS = 5;

	private View topView;
	private RobotNode robot;
	private List<FeederNode> feeders;

	private BranchGroup bg;

	private PoolNode pool;

	private BoundingRectNode boundingRect;

	private List<WallNode> wallNodes;

	public VirtualExpUniverse(String mazeResource) {
		super();

		Locale l = new Locale(this);

		bg = new BranchGroup();
		bg.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		bg.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		l.addBranchGraph(bg);

		try {
			FileUtils.copyURLToFile(getClass().getResource(mazeResource),
					new File("/tmp/maze.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = XMLDocReader.readDocument("/tmp/maze.xml");

		// Build the group
		NodeList list;
		org.w3c.dom.Node params;

		// Bounding rectangle
		boundingRect = new BoundingRectNode(doc.getElementsByTagName(
				"boundingRect").item(0));

		// Spheres
		list = doc.getElementsByTagName("sphere");
		for (int i = 0; i < list.getLength(); i++) {
			bg.addChild(new SphereNode(list.item(i)));
		}

		// Morris tanks
		list = doc.getElementsByTagName("pool");
		pool = new PoolNode(list.item(0));
		bg.addChild(pool);

		// Cylinders
		list = doc.getElementsByTagName("cylinder");
		for (int i = 0; i < list.getLength(); i++) {
			bg.addChild(new CylinderNode(list.item(i)));
		}

		// Boxes
		list = doc.getElementsByTagName("box");
		for (int i = 0; i < list.getLength(); i++) {
			bg.addChild(new BoxNode(list.item(i)));
		}

		// Floor
		list = doc.getElementsByTagName("floor");
		params = list.item(0);
		bg.addChild(new CylinderNode(params));

		// Top view
		list = doc.getElementsByTagName("topview");
		params = list.item(0);
		ViewNode vn = new ViewNode(params);
		topView = vn.getView();
		bg.addChild(vn);

		// Robot
		list = doc.getElementsByTagName("robotview");
		params = list.item(0);
		robot = new RobotNode(params);
		bg.addChild(robot);

		// food
		list = doc.getElementsByTagName("feeder");
		feeders = new LinkedList<FeederNode>();
		for (int i = 0; i < list.getLength(); i++) {
			params = list.item(i);
			FeederNode feeder = new FeederNode(params);
			feeders.add(feeder);
			bg.addChild(feeder);
		}

		bg.addChild(new DirectionalLightNode(new Vector3f(0f, 0f, -5),
				new Color3f(1f, 1f, 1f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, 0f, 5),
				new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, -5f, -5),
				new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, 5f, -5),
				new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, -5f, 5),
				new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, 5f, 5),
				new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, -5, 0),
				new Color3f(1f, 1f, 1f)));

		// bg.addChild(new WallNode(-0.2f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f,
		// 0.025f));
		wallNodes = new LinkedList<WallNode>();
		// addWall(-0.2f, 0.0f, 0.2f, 0.0f);
		// bg.compile();
	}

	public void addWall(float x1, float y1, float x2, float y2) {
		WallNode w = new WallNode(x1, 0, y1, x2, 0, y2, 0.025f);
		bg.addChild(w);
		wallNodes.add(w);
	}

	public void clearWalls() {
		wallNodes.clear();
	}

	public View getTopView() {
		return topView;
	}

	public View[] getRobotViews() {
		return robot.getRobotViews();
	}

	public Canvas3D[] getRobotOffscreenCanvas() {
		return robot.getOffScreenCanvas();
	}

	public ImageComponent2D[] getRobotOffscreenImages() {
		return robot.getOffScreenImages();
	}

	/**
	 * Return the virtual robot's position
	 * 
	 * @return
	 */
	public Point3f getRobotPosition() {
		Transform3D t = new Transform3D();
		robot.getTransformGroup().getTransform(t);
		Vector3f pos = new Vector3f();
		t.get(pos);

		return new Point3f(pos);
	}

	/**
	 * Sets the virtual robot position
	 * 
	 * @param vector
	 *            Robots position
	 */

	public void setRobotPosition(Point2D.Float pos, float angle) {
		Transform3D translate = new Transform3D();
		translate.setTranslation(new Vector3f(pos.x, 0, pos.y));
		Transform3D rot = new Transform3D();
		rot.rotY(angle);
		translate.mul(rot);
		robot.getTransformGroup().setTransform(translate);
	}

	/**
	 * Rotate the virtual world robot.
	 * 
	 * @param degrees
	 *            Amount to rotate in degrees.
	 */
	public void rotateRobot(double degrees) {
		// Create a new transforme with the translation
		Transform3D trans = new Transform3D();
		trans.rotY(degrees);
		// Get the old pos transform
		Transform3D rPos = new Transform3D();
		robot.getTransformGroup().getTransform(rPos);
		// Apply translation to old transform
		rPos.mul(trans);
		// Set the new transform
		robot.getTransformGroup().setTransform(rPos);
	}

	/**
	 * Move the virtual robot a certain amount of distance
	 * 
	 * @param vector
	 *            Direction and magnitude of the movement
	 */
	public void moveRobot(Vector3f vector) {
		// Create a new transforme with the translation
		Transform3D trans = new Transform3D();
		trans.setTranslation(vector);
		// Get the old pos transform
		Transform3D rPos = new Transform3D();
		robot.getTransformGroup().getTransform(rPos);
		// Apply translation to old transform
		rPos.mul(trans);
		// Set the new transform
		robot.getTransformGroup().setTransform(rPos);
	}

	public Point3f getFoodPosition(int i) {
		Vector3f foodPos = feeders.get(i).getPosition();
		return new Point3f(foodPos);
	}

	public boolean hasRobotFoundFood() {
		Point3f robot = getRobotPosition();
		for (FeederNode fNode : feeders) {
			if (fNode.isActive()
					&& robot.distance(new Point3f(fNode.getPosition())) < CLOSE_TO_FOOD_THRS)
				return true;
		}

		return false;
	}

	public Quat4f getRobotOrientation() {
		Transform3D t = new Transform3D();
		robot.getTransformGroup().getTransform(t);
		// Get the rotation from the quaternion
		// http://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles
		Quat4f rot = new Quat4f();
		t.get(rot);

		return rot;
	}

	public float getRobotOrientationAngle() {
		Transform3D t = new Transform3D();
		robot.getTransformGroup().getTransform(t);
		// Get the rotation from the quaternion
		// http://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles
		Quat4f rot = new Quat4f();
		t.get(rot);
		return (float) (2 * Math.acos(rot.w) * Math.signum(rot.y));
	}

	// public static void main(String[] args) {
	// new VirtualExpUniverse();
	// }

	public boolean[] getRobotAffordances() {

		return getRobotAffordances(LOOKAHEADSTEPS);
	}

	public Rectangle2D.Float getBoundingRectangle() {
		return boundingRect.getRect();
	}

	public List<Integer> getFlashingFeeders() {
		List<Integer> res = new LinkedList<Integer>();
		for (int i = 0; i < feeders.size(); i++)
			if (feeders.get(i).isFlashing())
				res.add(i);

		return res;
	}

	public List<Integer> getActiveFeeders() {
		List<Integer> res = new LinkedList<Integer>();
		for (int i = 0; i < feeders.size(); i++)
			if (feeders.get(i).isActive())
				res.add(i);

		return res;
	}

	public int getNumFeeders() {
		return feeders.size();
	}

	public void setActiveFeeder(int i, boolean val) {
		feeders.get(i).setActive(val);
	}

	@Override
	public void setFlashingFeeder(Integer i, boolean flashing) {
		feeders.get(i).setFlashing(flashing);
	}

	@Override
	public boolean isRobotCloseToFeeder(int currentGoal) {
		Point3f robot = getRobotPosition();
		return robot.distance(new Point3f(feeders.get(currentGoal)
				.getPosition())) < CLOSE_TO_FOOD_THRS;
	}

	@Override
	public int getFeedingFeeder() {
		Point3f robotPos = getRobotPosition();
		for (int i = 0; i < feeders.size(); i++) {
			if (feeders.get(i).isActive()
					&& robotPos.distance(new Point3f(feeders.get(i)
							.getPosition())) < CLOSE_TO_FOOD_THRS)
				return i;
		}
		return -1;
	}

	@Override
	public List<Integer> getFeeders() {
		List<Integer> res = new LinkedList<Integer>();
		for (int i = 0; i < feeders.size(); i++)
			res.add(i);

		return res;
	}

	@Override
	public boolean hasRobotFoundFeeder(int i) {
		Point3f robot = getRobotPosition();
		FeederNode fNode = feeders.get(i);
		return robot.distance(new Point3f(fNode.getPosition())) < CLOSE_TO_FOOD_THRS;
	}

	@Override
	public boolean isRobotParallelToWall() {
		boolean aff[] = getRobotAffordances();
		
		// If I cannot move to any of the perpendicular directions I am near a wall
		return !aff[Utiles.discretizeAction(-90)] || !aff[Utiles.discretizeAction(90)];
	}

	public boolean[] getRobotAffordances(int lookahead) {
		boolean[] affordances = new boolean[Utiles.numActions];
		for (int action = 0; action < Utiles.numActions; action++) {
			// The current position with rotation
			Transform3D rPos = new Transform3D();
			robot.getTransformGroup().getTransform(rPos);
			Vector3f p = new Vector3f();
			rPos.get(p);
			Coordinate initCoordinate = new Coordinate(p.x, p.z);
			// A translation vector to calc affordances
			Transform3D trans = new Transform3D();
			trans.setTranslation(new Vector3f(VirtualRobot.STEP * lookahead, 0f, 0f));
			// The rotatio of the action
			Transform3D rot = new Transform3D();
			rot.rotY(Utiles.getAction(action));
			// Apply hipotetical transformations
			rPos.mul(rot);
			rPos.mul(trans);
			// Get the new position
			Vector3f finalPos = new Vector3f();
			rPos.get(finalPos);
			Coordinate finalCoordinate = new Coordinate(finalPos.x, finalPos.z);
			// Check it's in the maze
			boolean insideMaze = pool.isInside(new Point3f(finalPos));
			// Check if crosses any wall
			boolean intesectsWall = false;
			LineSegment path = new LineSegment(initCoordinate, finalCoordinate);
			for (WallNode wallNode : wallNodes) {
				// System.out.println(path);
				// System.out.println(wallNode.segment);
				// System.out.println(path.intersection(wallNode.segment));
				intesectsWall = intesectsWall
						|| (path.intersection(wallNode.segment) != null);
			}

			affordances[action] = insideMaze && !intesectsWall;
		}

		return affordances;
	}

	public void setWantedFeeder(int feeder, boolean wanted){
		feeders.get(feeder).setWanted(wanted);
	}
	
	public void clearWantedFeeders(){
		for(FeederNode f : feeders)
			f.setWanted(false);
	}
}
