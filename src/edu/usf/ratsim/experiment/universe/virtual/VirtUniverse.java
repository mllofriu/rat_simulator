package edu.usf.ratsim.experiment.universe.virtual;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.Polygon;

import edu.usf.experiment.PropertyHolder;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.EatAffordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;
import edu.usf.experiment.subject.affordance.TurnAffordance;
import edu.usf.experiment.universe.Universe;
import edu.usf.experiment.utils.ElementWrapper;
import edu.usf.experiment.utils.IOUtils;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.GeomUtils;
import edu.usf.ratsim.support.XMLDocReader;

/**
 * This universe class creates a universe from an XML file and exposes
 * functionalities needed for performing experiments.
 * 
 * @author ludo
 * 
 */
public class VirtUniverse extends Universe {

	private static VirtUniverse instance = null;
	private View topView;
	private RobotNode robot;
	private List<FeederNode> feederNodes;

	private BranchGroup bg;

	private PoolNode pool;

	private BoundingRectNode boundingRect;

	private List<WallNode> wallNodes;
	private boolean display;

	public VirtUniverse(ElementWrapper params) {
		super(params);

		String mazeFile = params.getChildText("maze");
		display = params.getChildBoolean("display");

		wallNodes = new LinkedList<WallNode>();

		// Just initialize the nodes we need
		PropertyHolder props = PropertyHolder.getInstance();
		String dstMazeFile = props.getProperty("log.directory") + "maze.xml";
		IOUtils.copyFile(mazeFile, dstMazeFile);
		Document doc = XMLDocReader.readDocument(dstMazeFile);
		ElementWrapper maze = new ElementWrapper(doc.getDocumentElement());
		List<ElementWrapper> list;

		robot = new RobotNode(maze.getChild("robotview"), display);

		if (display) {
			VirtualUniverse vu = new VirtualUniverse();
			Locale l = new Locale(vu);

			bg = new BranchGroup();
			bg.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
			bg.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
			l.addBranchGraph(bg);

			// Add previously created elements, but not added to the 3d universe
			bg.addChild(robot);

			// Walls
			list = maze.getChildren("wall");
			for (ElementWrapper wn : list){
				WallNode w = new WallNode(wn);
				wallNodes.add(w);
			}

			
			list = maze.getChildren("feeder");
			feederNodes = new LinkedList<FeederNode>();
			for (ElementWrapper fn : list){
				FeederNode feeder = new FeederNode(fn);
				feederNodes.add(feeder);
			}

			ElementWrapper floor = maze.getChild("floor");
			if (floor != null)
				bg.addChild(new CylinderNode(floor));


			// Top view
			ElementWrapper tv = maze.getChild("topview");
			ViewNode vn = new ViewNode(tv);
			topView = vn.getView();
			bg.addChild(vn);

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

			UniverseFrame frame = new UniverseFrame(this);
			frame.setVisible(true);
		}

		instance = this;
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
		translate.setTranslation(new Vector3f(pos.x, pos.y, 0));
		Transform3D rot = new Transform3D();
		rot.rotZ(angle);
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
		trans.rotZ(degrees);
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
		return (float) (2 * Math.acos(rot.w) * Math.signum(rot.z));
	}

	// public static void main(String[] args) {
	// new VirtualExpUniverse();
	// }

	public List<Affordance> getRobotAffordances(List<Affordance> affs,
			int lookaheadSteps, float step) {
		for (Affordance af : affs) {
			boolean realizable;
			if (af instanceof TurnAffordance){
				TurnAffordance ta = (TurnAffordance) af;
				realizable = canMove(ta.getAngle(), ta.getDistance());
			} else if (af instanceof ForwardAffordance)
				realizable = canMove(0, ((ForwardAffordance)af).getDistance());
			else if (af instanceof EatAffordance)
				realizable = hasRobotFoundFood();
			else
				throw new RuntimeException("Affordance "
						+ af.getClass().getName() + " not supported by robot");

			af.setRealizable(realizable);
		}

		return affs;
	}

	private boolean canMove(float angle, float step) {
		// The current position with rotation
		Transform3D rPos = new Transform3D();
		robot.getTransformGroup().getTransform(rPos);
		Vector3f p = new Vector3f();
		rPos.get(p);
		Coordinate initCoordinate = new Coordinate(p.x, p.y);
		// A translation vector to calc affordances
		Transform3D trans = new Transform3D();
		trans.setTranslation(new Vector3f(step, 0f, 0f));
		// The rotatio of the action
		Transform3D rot = new Transform3D();
		rot.rotZ(angle);
		// Apply hipotetical transformations
		rPos.mul(rot);
		rPos.mul(trans);
		// Get the new position
		Vector3f finalPos = new Vector3f();
		rPos.get(finalPos);
		Coordinate finalCoordinate = new Coordinate(finalPos.x, finalPos.y);
		// Check it's in the maze
		boolean insideMaze = (pool == null)
				|| pool.isInside(new Point3f(finalPos));
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

		return insideMaze && !intesectsWall;
	}

	public Rectangle2D.Float getBoundingRectangle() {
		return boundingRect.getRect();
	}

	// public boolean isRobotParallelToWall() {
	// boolean aff[] = getRobotAffordances();
	//
	// // If I cannot move to any of the perpendicular directions I am near a
	// // wall
	// return !aff[GeomUtils.discretizeAction(-90)]
	// || !aff[GeomUtils.discretizeAction(90)];
	// }

	public void setWantedFeeder(int feeder, boolean wanted) {
		if (display)
			feederNodes.get(feeder).setWanted(wanted);
	}

	public void clearWantedFeeders() {
		for (FeederNode f : feederNodes)
			f.setWanted(false);
	}

	// @Override
	// public int getWantedFeeder() {
	// int wantedFeeder = -1;
	// for (int i = 0; i < feeders.size(); i++)
	// if (feeders.get(i).isWanted()) {
	// wantedFeeder = i;
	// break;
	// }
	//
	// return wantedFeeder;
	// }

	public List<WallNode> getWalls() {
		return wallNodes;
	}

	public boolean wallIntersectsOtherWalls(LineSegment wall) {
		boolean intersects = false;
		for (WallNode w : wallNodes)
			intersects = intersects || w.intersects(wall);

		return intersects;
	}

	public float shortestDistanceToWalls(LineSegment wall) {
		float shortestDistance = Float.MAX_VALUE;
		for (WallNode w : wallNodes)
			if (w.distanceTo(wall) < shortestDistance)
				shortestDistance = w.distanceTo(wall);

		float distanceToPool = pool.distanceToWall(wall);

		return Math.min(shortestDistance, distanceToPool);
	}

	public boolean wallInsidePool(LineSegment wall2) {
		return pool.isInside(new Point3f((float) wall2.p0.x,
				(float) wall2.p0.y, 0))
				&& pool.isInside(new Point3f((float) wall2.p1.x,
						(float) wall2.p1.y, 0));
	}

	public void dispose() {
		for (FeederNode f : feederNodes)
			f.terminate();
	}

	public float wallDistanceToFeeders(LineSegment wall) {
		float minDist = Float.MAX_VALUE;
		for (Integer fn : getFeeders()) {
			Point3f pos = getFoodPosition(fn);
			Coordinate c = new Coordinate(pos.x, pos.y);
			if (wall.distance(c) < minDist)
				minDist = (float) wall.distance(c);
		}
		return minDist;
	}

	public boolean canRobotSeeFeeder(Integer fn, float halfFieldOfView,
			float visionDist) {
		float angleToFeeder = angleToFeeder(fn);
		boolean inField = angleToFeeder <= halfFieldOfView;

		boolean intersects = false;
		Coordinate rPos = new Coordinate(getRobotPosition().x,
				getRobotPosition().y);
		Point3f fPosV = getFoodPosition(fn);
		Coordinate fPos = new Coordinate(fPosV.x, fPosV.y);
		LineSegment lineOfSight = new LineSegment(rPos, fPos);
		for (WallNode w : wallNodes)
			intersects = intersects || w.intersects(lineOfSight);

		boolean closeEnough = getRobotPosition().distance(
				new Point3f(getFoodPosition(fn))) < visionDist;

		return inField && !intersects && closeEnough;
	}

	private float angleToFeeder(Integer fn) {
		return Math.abs(GeomUtils.angleToPointWithOrientation(
				getRobotOrientation(), getRobotPosition(), getFoodPosition(fn)));

	}

	public boolean placeIntersectsWalls(Polygon c) {
		boolean intersects = false;
		for (WallNode w : wallNodes)
			intersects = intersects || w.intersects(c);

		return intersects;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();

		// System.out.println("Finalizing Virtual Universe");
	}

	public static VirtUniverse getInstance() {
		return instance;
	}
}
