package edu.usf.ratsim.nsl.modules;

import javax.vecmath.Point3f;

public class ArtificialPlaceCell {

	private Point3f center;
	private float width;

	public ArtificialPlaceCell(Point3f center, float radius) {
		this.center = center;
		this.width = 1 / radius;
	}

//	public boolean isActive(Point3f currLocation) {
//		return center.distance(currLocation) < radius;
//	}

	public float getActivation(Point3f currLocation) {
//		return 1 / center.distance(currLocation);
		// TODO: add spread
		return (float) Math.exp(-center.distance(currLocation) / width);
	}

	public Point3f getCenter() {
		return center;
	}
}
