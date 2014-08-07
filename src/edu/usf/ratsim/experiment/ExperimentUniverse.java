package edu.usf.ratsim.experiment;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

import com.vividsolutions.jts.geom.LineSegment;

import edu.usf.ratsim.robot.virtual.WallNode;

public interface ExperimentUniverse {

	public Point3f getFoodPosition(int i);

	public Point3f getRobotPosition();

	public void setRobotPosition(Point2D.Float pos, float angle);

	public boolean hasRobotFoundFood();
	
	public List<Integer> getFlashingFeeders();
	
	public List<Integer> getActiveFeeders();

	/**
	 * Returns the robot orientation as a 3d heading vector
	 * 
	 * @return
	 */
	public Quat4f getRobotOrientation();

	/**
	 * Returns the robot orientation angle to the x axis over the y axis
	 * 
	 * @return
	 */
	public float getRobotOrientationAngle();

	public Rectangle2D.Float getBoundingRectangle();

	public int getNumFeeders();

	public void setActiveFeeder(int i, boolean active);

	public void setFlashingFeeder(Integer integer, boolean b);

	public boolean isRobotCloseToFeeder(int currentGoal);

	public int getFeedingFeeder();
	
	public void addWall(float x1, float y1, float x2, float y2);
	
	public void clearWalls();

	public List<Integer> getFeeders();

	public boolean hasRobotFoundFeeder(int i);

	public boolean isRobotParallelToWall();
	
	public void setWantedFeeder(int feeder, boolean wanted);
	
	public void clearWantedFeeders();

	public int getWantedFeeder();

	public List<WallNode> getWalls();

	public boolean wallIntersectsOtherWalls(LineSegment wall);

	public float shortestDistanceToWalls(LineSegment wall);

	public boolean wallInsidePool(LineSegment wall2);

	public void dispose();

	public void robotEat();
	
	public void clearRobotAte();

	public boolean hasRobotAte();

	public boolean hasRobotTriedToEat();

	int getFeederInFrontOfRobot(int excludeFeeder);

	public boolean isRobotCloseToAFeeder();

	public float getDistanceToFeeder(int i);

	public int getFoundFeeder();

	public float angleToFeeder(Integer fn);

	public float wallDistanceToFeeders(LineSegment wall);
}
