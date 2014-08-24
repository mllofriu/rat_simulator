package edu.usf.ratsim.nsl.modules;

import javax.vecmath.Point3f;

public class ArtificialPlaceCell {

	private static final double RADIUS_THRS = 0.2;
	private Point3f center;
	private float width;

	public ArtificialPlaceCell(Point3f center, float radius) {
		this.center = center;
		// min_thrs = e^(-x_min_thrs^2/w) -> ...
		this.width = (float) (- Math.pow(radius,2) / Math.log(RADIUS_THRS));
	}

//	public boolean isActive(Point3f currLocation) {
//		return center.distance(currLocation) < radius;
//	}

	public float getActivation(Point3f currLocation) {
//		return 1 / center.distance(currLocation);
		return (float) Math.exp(-Math.pow(center.distance(currLocation),2) / width);
//		return Utiles.gaussian(center.distance(currLocation), width);
//		return 0;
	}

	public Point3f getCenter() {
		return center;
	}
}
