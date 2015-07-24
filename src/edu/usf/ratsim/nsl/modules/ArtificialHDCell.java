package edu.usf.ratsim.nsl.modules;

public class ArtificialHDCell {

	private static final double RADIUS_THRS = 0.2;
	
	private float preferredOrientation;
	private float width;

	public ArtificialHDCell(float preferredOrientation, float radius) {
		super();
		this.preferredOrientation = preferredOrientation;
		this.width = (float) (-Math.pow(radius, 2) / Math.log(RADIUS_THRS));;
	}

	public float getActivation(float currOrientation) {
		return (float) Math.exp(-Math.pow(
				angleDistance(currOrientation, preferredOrientation), 2)
				/ width);
		// return Utiles.gaussian(angleDistance(currOrientation,
		// preferredOrientation), width);
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
		double r = r1 * r2 - i1 * i2;
		double i = i1 * r2 + r1 * i2;
		// Get the argument and complementary
		double arg = Math.atan2(i, r);
		double argComp;

		if (arg > 0)
			argComp = -(2 * Math.PI - arg);
		else
			argComp = 2 * Math.PI + arg;

		// Return the minimum of the absolute values
		return (float) Math.min(Math.abs(arg), Math.abs(argComp));
	}

	public static void main(String[] args) {
		ArtificialHDCell hdc = new ArtificialHDCell(1, 1);
		System.out.println(hdc.angleDistance((float) (Math.PI / 2), 0f));
		System.out.println(hdc.angleDistance(0, 0f));
		System.out.println(hdc.angleDistance((float) (Math.PI),
				(float) (Math.PI / 2)));
		System.out.println(hdc.angleDistance(0.1f, -0.1f));
		System.out.println(hdc.angleDistance(-0.1f, 0.1f));
		System.out.println(hdc.angleDistance(-(float) (Math.PI),
				(float) (Math.PI)));
	}

}
