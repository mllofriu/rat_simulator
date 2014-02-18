package edu.usf.ratsim.experiment;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

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

}
