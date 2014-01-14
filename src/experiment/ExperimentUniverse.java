package experiment;

import java.awt.geom.Point2D;

public interface ExperimentUniverse {

	public Point2D.Float getFoodPosition();
	
	public Point2D.Float getRobotPosition();
	
	public void setRobotPosition(Point2D.Float pos);
	
	public void setFoodPosition(Point2D.Float pos);
	
	public boolean hasRobotFoundFood();
}
