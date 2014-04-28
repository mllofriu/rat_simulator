package edu.usf.ratsim.nsl.modules;


public class ArtificialHDCell {

	private float preferredOrientation;

	public ArtificialHDCell(float preferredOrientation) {
		super();
		this.preferredOrientation = preferredOrientation;
	}

	public float getActivation(float currOrientation) {
		return (float) Math.exp(-angleDistance(currOrientation, preferredOrientation));
	}

	private float angleDistance(float from, float to) {
		// Create complex numbers for both orientations
		double r1 = Math.cos(from);
		double i1 = Math.sin(from);
		double r2 = Math.cos(to);
		double i2 = Math.sin(to);
		// Conjugate from
		i1 = -i1;
		// Multiply them
		double r = r1 * r2  - i1 * i2;
		double i = i1 * r2 + r1 * i2;
		// Get the argument and complementary
		double arg = Math.atan2(i, r);
		double argComp = Math.PI * 2 - arg;
		
		// Return the minimum of the absolute values
		return (float) Math.min(Math.abs(arg), Math.abs(argComp));		
	}

}
