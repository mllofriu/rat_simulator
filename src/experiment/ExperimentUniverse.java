package experiment;

import java.awt.geom.Point2D;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public interface ExperimentUniverse {

	public Vector3f getFoodPosition();
	
	public Vector3f getRobotPosition();
	
	public void setRobotPosition(Point2D.Float pos);
	
	public void setFoodPosition(Point2D.Float pos);
	
	public boolean hasRobotFoundFood();

	public Quat4f getRobotOrientation();
}
