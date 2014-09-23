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
import com.vividsolutions.jts.geom.Polygon;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Debug;
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

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();

		// System.out.println("Finalizing Virtual Universe");
	}

	private static final float CLOSE_TO_FOOD_THRS = Configuration
			.getFloat("VirtualUniverse.closeToFood");

	private static final float HALF_FIELD_OF_VIEW = (float) (105 * Math.PI / 180); // 105
	// degrees

	private static final int LOOKAHEADSTEPS = 2;

	private static final float VISION_DIST = 0.4f;

	private View topView;
	private RobotNode robot;
	private List<FeederNode> feeders;

	private BranchGroup bg;

	private PoolNode pool;

	private BoundingRectNode boundingRect;

	private List<WallNode> wallNodes;

	private boolean robotAte;

	private boolean robotTriedToEat;

	public VirtualExpUniverse(String mazeResource) {
		super();

		robotAte = false;
		robotTriedToEat = false;
		
		wallNodes = new LinkedList<WallNode>();

		if (Configuration.getBoolean("UniverseFrame.display")) {
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
//			list = doc.getElementsByTagName("pool");
//			if (list.getLength() != 0){
//				pool = new PoolNode(list.item(0));
//				bg.addChild(pool);
//			}

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
			
			// Walls
			list = doc.getElementsByTagName("wall");
			for (int i = 0; i < list.getLength(); i++) {
				WallNode w = new WallNode(list.item(i));
				bg.addChild(w);
				wallNodes.add(w);
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

			// addWall(-0.2f, 0.0f, 0.2f, 0.0f);
			// bg.compile();
		} else {
			// Just initialize the nodes we need
			NodeList list;
			org.w3c.dom.Node params;

			Document doc;
			synchronized (VirtualExpUniverse.class) {
				try {
					FileUtils.copyURLToFile(getClass()
							.getResource(mazeResource), new File(
							"/tmp/maze.xml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				doc = XMLDocReader.readDocument("/tmp/maze.xml");
			}

			boundingRect = new BoundingRectNode(doc.getElementsByTagName(
					"boundingRect").item(0));

			list = doc.getElementsByTagName("robotview");
			params = list.item(0);
			robot = new RobotNode(params);

			list = doc.getElementsByTagName("pool");
			if (list.getLength() != 0)
				pool = new PoolNode(list.item(0));
			
			// Walls
			list = doc.getElementsByTagName("wall");
			for (int i = 0; i < list.getLength(); i++) {
				WallNode w = new WallNode(list.item(i));
				wallNodes.add(w);
			}

			list = doc.getElementsByTagName("feeder");
			feeders = new LinkedList<FeederNode>();
			for (int i = 0; i < list.getLength(); i++) {
				params = list.item(i);
				FeederNode feeder = new FeederNode(params);
				feeders.add(feeder);
			}
		}

		
	}

	public void addWall(float x1, float y1, float x2, float y2) {
		WallNode w = new WallNode(x1, 0, y1, x2, 0, y2, 0.025f);
		if (Configuration.getBoolean("UniverseFrame.display"))
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
					&& fNode.hasFood()
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
			if (feeders.get(i).isActive())
				if (robotPos
						.distance(new Point3f(feeders.get(i).getPosition())) < CLOSE_TO_FOOD_THRS)
					return i;
			// else
			// System.out.println("Robot to far away: "
			// + robotPos.distance(new Point3f(feeders.get(i)
			// .getPosition())));
			// else
			// System.out.println("Feeder not active at distance "
			// + robotPos.distance(new Point3f(feeders.get(i)
			// .getPosition())));

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

		// If I cannot move to any of the perpendicular directions I am near a
		// wall
		return !aff[Utiles.discretizeAction(-90)]
				|| !aff[Utiles.discretizeAction(90)];
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
			trans.setTranslation(new Vector3f(VirtualRobot.STEP * lookahead,
					0f, 0f));
			// The rotatio of the action
			Transform3D rot = new Transform3D();
			rot.rotY(Utiles.getActionAngle(action));
			// Apply hipotetical transformations
			rPos.mul(rot);
			rPos.mul(trans);
			// Get the new position
			Vector3f finalPos = new Vector3f();
			rPos.get(finalPos);
			Coordinate finalCoordinate = new Coordinate(finalPos.x, finalPos.z);
			// Check it's in the maze
			boolean insideMaze = (pool == null) || pool.isInside(new Point3f(finalPos));
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

	public void setWantedFeeder(int feeder, boolean wanted) {
		feeders.get(feeder).setWanted(wanted);
	}

	public void clearWantedFeeders() {
		for (FeederNode f : feeders)
			f.setWanted(false);
	}

	@Override
	public int getWantedFeeder() {
		int wantedFeeder = -1;
		for (int i = 0; i < feeders.size(); i++)
			if (feeders.get(i).isWanted()) {
				wantedFeeder = i;
				break;
			}

		return wantedFeeder;
	}

	@Override
	public List<WallNode> getWalls() {
		return wallNodes;
	}

	@Override
	public boolean wallIntersectsOtherWalls(LineSegment wall) {
		boolean intersects = false;
		for (WallNode w : wallNodes)
			intersects = intersects || w.intersects(wall);

		return intersects;
	}
	


	@Override
	public float shortestDistanceToWalls(LineSegment wall) {
		float shortestDistance = Float.MAX_VALUE;
		for (WallNode w : wallNodes)
			if (w.distanceTo(wall) < shortestDistance)
				shortestDistance = w.distanceTo(wall);

		float distanceToPool = pool.distanceToWall(wall);

		return Math.min(shortestDistance, distanceToPool);
	}

	@Override
	public boolean wallInsidePool(LineSegment wall2) {
		return pool.isInside(new Point3f((float) wall2.p0.x, 0f,
				(float) wall2.p0.y))
				&& pool.isInside(new Point3f((float) wall2.p1.x, 0f,
						(float) wall2.p1.y));
	}

	@Override
	public void dispose() {
		for (FeederNode f : feeders)
			f.terminate();
	}

	public void robotEat() {
		int feedingFeeder = -1;

		Point3f robotPos = getRobotPosition();
		for (int i = 0; i < feeders.size(); i++) {
			if (robotPos.distance(new Point3f(feeders.get(i).getPosition())) < CLOSE_TO_FOOD_THRS)
				if (feeders.get(i).hasFood())
					feedingFeeder = i;
				else
					robotTriedToEat = true;
		}

		if (feedingFeeder != -1) {
			if (Debug.printRobotAte)
				System.out.println("Robot ate");
			robotAte = true;
			feeders.get(feedingFeeder).clearFood();
		} else {
			// System.out.println("Tried to eat but not close to active feeder");
		}
	}

	public void clearRobotAte() {
		robotAte = false;
		robotTriedToEat = false;
	}

	@Override
	public boolean hasRobotAte() {
		return robotAte;
	}

	@Override
	public boolean hasRobotTriedToEat() {
		return robotTriedToEat;
	}

	@Override
	public int getFeederInFrontOfRobot(int excludeFeeder) {
		float minAngle = Float.MAX_VALUE;
		FeederNode bestNode = feeders.get(0);
		for (FeederNode fn : feeders) {
			float fnAngle = angleToFeeder(feeders.indexOf(fn));
			if (feeders.indexOf(fn) != excludeFeeder && fnAngle < minAngle) {
				minAngle = fnAngle;
				bestNode = fn;
			}
		}
		return feeders.indexOf(bestNode);
	}

	@Override
	public boolean isRobotCloseToAFeeder() {
		Point3f robot = getRobotPosition();
		for (FeederNode fn : feeders)
			if (robot.distance(new Point3f(fn.getPosition())) < CLOSE_TO_FOOD_THRS)
				return true;
		return false;
	}

	@Override
	public float getDistanceToFeeder(int i) {
		// Get robot vector
		Transform3D t = new Transform3D();
		robot.getTransformGroup().getTransform(t);
		Vector3f pos = new Vector3f();
		t.get(pos);
		pos.sub(feeders.get(i).getPosition());
		return pos.length();
	}

	@Override
	public int getFoundFeeder() {
		Point3f robot = getRobotPosition();
		for (FeederNode fn : feeders)
			if (robot.distance(new Point3f(fn.getPosition())) < CLOSE_TO_FOOD_THRS)
				return feeders.indexOf(fn);

		return -1;
	}

	@Override
	public float angleToFeeder(Integer fn) {
		return Math.abs(Utiles.angleToPointWithOrientation(
				getRobotOrientation(), getRobotPosition(), new Point3f(feeders
						.get(fn).getPosition())));
	}

	@Override
	public float wallDistanceToFeeders(LineSegment wall) {
		float minDist = Float.MAX_VALUE;
		for (FeederNode fn : feeders) {
			Vector3f pos = fn.getPosition();
			Coordinate c = new Coordinate(pos.x, pos.z);
			if (wall.distance(c) < minDist)
				minDist = (float) wall.distance(c);
		}
		return minDist;
	}

	@Override
	public boolean isFeederActive(int feeder) {
		return feeders.get(feeder).isActive();
	}

	@Override
	public void releaseFood(int feeder) {
		feeders.get(feeder).releaseFood();
	}

	@Override
	public boolean canRobotSeeFeeder(Integer fn) {
		float angleToFeeder = angleToFeeder(fn);
		boolean inField = angleToFeeder <= HALF_FIELD_OF_VIEW;

		boolean intersects = false;
		Coordinate rPos = new Coordinate(getRobotPosition().x,
				getRobotPosition().z);
		Vector3f fPosV = feeders.get(fn).getPosition();
		Coordinate fPos = new Coordinate(fPosV.x, fPosV.z);
		LineSegment lineOfSight = new LineSegment(rPos, fPos);
		for (WallNode w : wallNodes)
			intersects = intersects || w.intersects(lineOfSight);

		boolean closeEnough = getRobotPosition().distance(new Point3f(feeders.get(fn).getPosition())) < VISION_DIST;
		
		return inField && !intersects && closeEnough;
	}

	@Override
	public boolean hasFoodFeeder(int feeder) {
		return feeders.get(feeder).hasFood();
	}

	@Override
	public boolean placeIntersectsWalls(Polygon c) {
		boolean intersects = false;
		for (WallNode w : wallNodes)
			intersects = intersects || w.intersects(c);

		return intersects;
	}
}
