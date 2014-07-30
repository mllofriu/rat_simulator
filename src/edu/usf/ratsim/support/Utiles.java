package edu.usf.ratsim.support;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class Utiles {

	// public static final float[] actions = { -(float) Math.PI,
	// -(float) (3 * Math.PI / 4), -(float) (Math.PI / 2),
	// -(float) (Math.PI / 4), 0, (float) (Math.PI / 4),
	// (float) (Math.PI / 2), (float) (3 * Math.PI / 4) };
	// public static final float[] actions = {-(float) (Math.PI / 8), 0,
	// (float) (Math.PI / 8) };
	// public static final float[] discreteAngles = { 0, (float) (Math.PI / 4),
	// (float) (Math.PI / 2), (float) (3 * Math.PI / 4), (float) Math.PI,
	// (float) (5 * Math.PI / 4), (float) (6 * Math.PI / 4),
	// (float) (7 * Math.PI / 4) };

	public static final float actionInterval = (float) (Math.PI / 8);
	private static final float actionMin = (float) (-Math.PI / 8);
	// private static final float actionMin = (float) 0;
	public static final int numRotations = 3;
	public static final int numActions = numRotations + 1;
	public static int eatAction = numRotations;
	// private static final float actionMax = (float) (Math.PI/8);

	private static final float angleInterval = (float) (Math.PI / 8);
	private static final float angleMin = 0;
	public static final int numAngles = 16;
	// private static final float angleMax = (float) (2* Math.PI -
	// angleInterval);
	private static final float EPS_STRAIGHT = actionInterval;


	/**
	 * Gets the angle rotation for an action
	 * @param index
	 * @return
	 */
	public static float getActionAngle(int index) {
		float angle = actionMin;
		int i = 0;
		while (i < index) {
			angle += actionInterval;
			i++;
		}

		return angle;
	}

	public static float getAngle(int index) {
		float angle = angleMin;
		int i = 0;
		while (i < index) {
			angle += angleInterval;
			i++;
		}

		return angle;
	}

	public static int contador(BufferedImage image, Color color) {
		int iterH, iterW;
		int contador = 0;

		for (iterH = 0; iterH < image.getHeight(); iterH++)
			for (iterW = 0; iterW < image.getWidth(); iterW++) {
				if (rgb2Color(image.getRGB(iterH, iterW)).equals(color))
					contador++;
			}

		// System.out.println("Contador " +contador);
		return contador;
	}

	public static Color rgb2Color(int rgb) {
		int red = (rgb & 0x00ff0000) >> 16;
		int green = (rgb & 0x0000ff00) >> 8;
		int blue = rgb & 0x000000ff;
		return new Color(red, green, blue);
	}

	public static int color2RGB(Color color) {
		return (color.getRed() << 16) + (color.getGreen() << 8)
				+ color.getBlue();
	}

	public static String getCurrentDirectoryAbsolute() {
		return System.getProperty("user.dir");
	}

	public static void shuffleList(Object[] array) {
		Collections.shuffle(Arrays.asList(array));
	}

	/**
	 * Returns a Quaternion representing the 3d rotation that transforms vector
	 * from into vector to
	 * 
	 * @param from
	 *            vector representing heading direction
	 * @param to
	 *            vector of the position of the desired goal
	 * @return
	 */
	public static Quat4f rotToPoint(Vector3f from, Vector3f to) {
		Quat4f res = new Quat4f();
		Vector3f cross = new Vector3f();
		cross.cross(from, to);
		res.x = (float) (Math.sin(from.angle(to) / 2) * Math.cos(cross
				.angle(new Vector3f(1, 0, 0))));
		res.y = (float) (Math.sin(from.angle(to) / 2) * Math.cos(cross
				.angle(new Vector3f(0, 1, 0))));
		res.z = (float) (Math.sin(from.angle(to) / 2) * Math.cos(cross
				.angle(new Vector3f(0, 0, 1))));
		res.w = (float) Math.cos(from.angle(to) / 2);

		return res;
	}
	
	public static float angleToPointWithOrientation(Quat4f orientation, Point3f from, Point3f to)
	{
		Quat4f rotTo = rotToPoint(new Vector3f(1,0,0), vectorToPoint(from,to));
		rotTo.inverse();
		rotTo.mul(orientation);
		return rotToAngle(rotTo);
	}

	public static Vector3f vectorToPoint(Point3f from, Point3f to) {
		to.sub(from);
		Vector3f fVect = new Vector3f(to);
		return fVect;
	}

	public static int bestActionToRot(Quat4f rotToGoal, Quat4f currentRot) {
		// Find the desired rot
		Quat4f rotToMake = new Quat4f();
		rotToMake.inverse(currentRot);
		rotToMake.mul(rotToGoal);

		// See if going straight isnt good enough
		float resultingAnglePos = (float) Math.abs(rotToAngle(rotToMake));
		float resultingAngleInv = (float) Math.abs(Math.PI * 2
				- resultingAnglePos);
		if (Math.min(resultingAnglePos, resultingAngleInv) < EPS_STRAIGHT) {
			return discretizeAction(0);
		}

		int action = -1;
		float angleDifference = (float) (Math.PI * 2);
		for (int i = 0; i < numRotations; i++) {
			// Make rotation for this action
			Quat4f rotAction = angleToRot(getActionAngle(i));
			// Invert
			rotAction.inverse();
			// Compose rotToMake and inverse of action.
			Quat4f tmpRot = new Quat4f(rotToMake);
			tmpRot.mul(rotAction);
			// Compare axis angle. The closer to 0, the more suitable
			// Take the min of normal and inverse
			resultingAnglePos = (float) Math.abs(rotToAngle(tmpRot));
			resultingAngleInv = (float) Math.abs(Math.PI * 2
					- resultingAnglePos);
			if (Math.min(resultingAnglePos, resultingAngleInv) < angleDifference) {
				angleDifference = Math
						.min(resultingAnglePos, resultingAngleInv);
				action = i;
			}
		}

		return action;
	}

	public static Quat4f angleToRot(float angle) {
		Quat4f res = new Quat4f();
		Transform3D t = new Transform3D();
		t.rotY(angle);
		t.get(res);
		return res;
	}

	/**
	 * Discretizes the allothetic angle giving the index to the closer discrete
	 * angle
	 * 
	 * @param allotAngle
	 *            the allothetic angle in radians
	 * @return
	 */
	public static int discretizeAngle(float allotAngle) {
		// System.out.print(allotAngle);
		Quat4f allotRot = angleToRot(allotAngle);
		return discretizeAngle(allotRot);
	}

	private static float rotToAngle(Quat4f rot) {
		rot.normalize();
		return (float) (2 * Math.acos(rot.w));
	}

	/**
	 * Discretizes the rotation returning the index of the closest discrete
	 * action
	 * 
	 * @param degrees
	 *            the rotationg in degrees
	 * @return
	 */
	public static int discretizeAction(int degrees) {
		Quat4f allotRot = angleToRot((float) Math.toRadians(degrees));

		int action = -1;
		float angleDifference = (float) (Math.PI * 2);
		for (int i = 0; i < numRotations; i++) {
			// Make rotation for this action
			Quat4f rotAction = angleToRot(getActionAngle(i));
			// Invert
			rotAction.inverse();
			// Compose rotToMake and inverse of action.
			rotAction.mul(allotRot);
			// Compare axis angle. The closer to 0, the more suitable
			float resultingAbsAngle = (float) Math.abs(rotToAngle(rotAction));
			float resultingAngleInv = (float) Math.abs(Math.PI * 2
					- resultingAbsAngle);
			if (Math.min(resultingAbsAngle, resultingAngleInv) < angleDifference) {
				angleDifference = Math
						.min(resultingAbsAngle, resultingAngleInv);
				action = i;
			}
		}

		return action;
	}

	// public static void main(String[] args) {
	// // System.out.println(discretizeAngle((float) (-135 * Math.PI / 180)));
	// // System.out.println(discretizeAngle((float) (-90 * Math.PI / 180)));
	// System.out.println(Math.toDegrees(actions[bestActionToRot(
	// angleToRot((float) Math.toRadians(135)),
	// angleToRot((float) Math.toRadians(90)))]));
	// }

	public static double actionDistance(int a1, int a2) {
		return Math.min(Math.abs(a1 - a2), Math.abs(numRotations - a2 + a1));
	}

	public static int discretizeAngle(Quat4f allotRot) {
		// System.out.print(allotAngle);
		// Quat4f allotRot = angleToRot(allotAngle);
		int angle = -1;
		float angleDifference = (float) (Math.PI * 2);
		for (int i = 0; i < numAngles; i++) {
			// Make rotation for this action
			Quat4f rotAction = angleToRot(getAngle(i));
			// Invert
			rotAction.inverse();
			// Compose rotToMake and inverse of action.
			rotAction.mul(allotRot);
			// Compare axis angle. The closer to 0, the more suitable
			float resultingAngle = (float) Math.abs(rotToAngle(rotAction));
			float resultingAngleInv = (float) Math.abs(Math.PI * 2
					- resultingAngle);
			if (Math.min(resultingAngle, resultingAngleInv) < angleDifference) {
				angleDifference = Math.min(resultingAngle, resultingAngleInv);
				angle = i;
			}
		}

		// System.out.println( " " + discreteAngles[angle]);
		return angle;
	}

	public static float gaussian(float distance, float width) {
		return (float) ((1 / Math.sqrt(2 * Math.PI) / width) * Math.exp(-Math
				.pow(distance, 2) / (2 * Math.pow(width, 2))));
	}

}
