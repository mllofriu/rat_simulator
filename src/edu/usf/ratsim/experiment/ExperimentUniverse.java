package edu.usf.ratsim.experiment;

import java.awt.geom.Point2D;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

public interface ExperimentUniverse {

	public Point3f getFoodPosition();

	public Point3f getRobotPosition();

	public void setRobotPosition(Point2D.Float pos, float angle);

	public void setFoodPosition(Point2D.Float pos);

	public boolean hasRobotFoundFood();

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

}
