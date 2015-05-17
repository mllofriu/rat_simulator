package edu.usf.ratsim.nsl.modules;

import java.util.Random;

import javax.vecmath.Point3f;

public class WallExponentialArtificialPlaceCell extends
		ExponentialArtificialPlaceCell {

	private boolean wallCell;

	public WallExponentialArtificialPlaceCell(Point3f center, float radius) {
		super(center, radius);

		wallCell = new Random().nextBoolean();
	}

	public float getActivation(Point3f currLocation, float distanceToWall) {
		float activation = super.getActivation(currLocation, distanceToWall);
		if (activation != 0) {
			float d = distanceToWall / (getRadius());
			if (!wallCell)
				return (float) (activation / (Math.exp(-10 * (d - .5)) + 1));
			else
				return (float) (activation * (1 - 1 / (Math.exp(-10 * (d - .5)) + 1)));
		} else
			return 0;
	}
}
