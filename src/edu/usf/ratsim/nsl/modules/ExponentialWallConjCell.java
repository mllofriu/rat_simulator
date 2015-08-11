package edu.usf.ratsim.nsl.modules;

import java.util.Random;

import javax.vecmath.Point3f;

public class ExponentialWallConjCell extends ExponentialConjCell {

	private boolean wallCell;

	public ExponentialWallConjCell(Point3f preferredLocation,
			float preferredDirection, float placeRadius, float angleRadius,
			int preferredIntention, Random r) {
		super(preferredLocation, preferredDirection, placeRadius, angleRadius,
				preferredIntention);
		wallCell = r.nextBoolean();
	}

	@Override
	public float getActivation(Point3f currLocation, float currAngle,
			int currIntention, float distanceToWall) {
		float activation =  super.getActivation(currLocation, currAngle, currIntention,
				distanceToWall);
		if (activation != 0) {
			float d = distanceToWall / (getPlaceRadius());
			float dAcross = Math.max(0, (d - getPreferredLocation().distance(currLocation)
					/ getPlaceRadius()));
			if (wallCell) {
				// If it is a wall cell, it activates more near walls but no
				// across also
				return (float) (activation
						* (1 - 1 / (Math.exp(-10 * (d - .7)) + 1)) * (1 / (Math
						.exp(-10 * (dAcross - .2)) + 1)));
			} else {
				// If it is not a wall cell, it should activate less near walls
				return (float) (activation * (1 / (Math.exp(-10
						* (dAcross - .2)) + 1)));
			}

		} else
			return 0;
		
		
	}
	
	

}
