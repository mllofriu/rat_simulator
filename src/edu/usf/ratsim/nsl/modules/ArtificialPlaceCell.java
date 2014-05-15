package edu.usf.ratsim.nsl.modules;

import javax.vecmath.Point3f;

import com.sun.org.apache.xml.internal.serializer.utils.Utils;

import edu.usf.ratsim.support.Utiles;

public class ArtificialPlaceCell {

	private Point3f center;
	private float width;

	public ArtificialPlaceCell(Point3f center, float radius) {
		this.center = center;
		this.width = radius*2;
	}

//	public boolean isActive(Point3f currLocation) {
//		return center.distance(currLocation) < radius;
//	}

	public float getActivation(Point3f currLocation) {
//		return 1 / center.distance(currLocation);
		return (float) Math.exp(-center.distance(currLocation) / width);
//		return Utiles.gaussian(center.distance(currLocation), width);
//		return 0;
	}

	public Point3f getCenter() {
		return center;
	}
}
