package nsl.modules;

import javax.vecmath.Point3f;

public class ArtificialPlaceCell {

	private Point3f center;
	private float radius;

	public ArtificialPlaceCell(Point3f center, float radius){
		this.center = center;
		this.radius = radius;
	}
	
	public boolean isActive(Point3f currLocation){
		return center.distance(currLocation) < radius;
	}
}

