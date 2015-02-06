package edu.usf.ratsim.nsl.modules;

import javax.vecmath.Point3f;

public interface ArtificialPlaceCell {

	public float getActivation(Point3f currLocation);

	public Point3f getCenter();
	
}
