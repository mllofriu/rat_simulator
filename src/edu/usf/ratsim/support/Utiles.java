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

	 public static final float[] actions = { -(float) Math.PI, -(float) (3 *
	 Math.PI / 4),
	 -(float) (Math.PI / 2), -(float) (Math.PI / 4), 0,
	 (float) (Math.PI / 4), (float) (Math.PI / 2),
	 (float) (3 * Math.PI / 4) };
//	public static final float[] actions = { -(float) (Math.PI / 4), 0,
//			(float) (Math.PI / 4) };
	public static final float[] discreteAngles = { 0, (float) (Math.PI / 4),
			(float) (Math.PI / 2), (float) (3 * Math.PI / 4), (float) Math.PI,
			(float) (5 * Math.PI / 4), (float) (6 * Math.PI / 4),
			(float) (7 * Math.PI / 4) };

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
		
		int action = -1;
		float angleDifference = (float) (Math.PI * 2);
		for (int i = 0; i < actions.length; i++) {
			// Make rotation for this action
			Quat4f rotAction = angleToRot(actions[i]);
			// Invert
			rotAction.inverse();
			// Compose rotToMake and inverse of action.
			Quat4f tmpRot = new Quat4f(rotToMake);
			tmpRot.mul(rotAction);
			// Compare axis angle. The closer to 0, the more suitable
			// Take the min of normal and inverse
			float resultingAnglePos = (float) Math.abs(rotToAngle(tmpRot));
			float resultingAngleInv = (float) Math.abs(Math.PI * 2 - resultingAnglePos);
			if (Math.min(resultingAnglePos, resultingAngleInv) < angleDifference) {
				angleDifference = Math.min(resultingAnglePos, resultingAngleInv);
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
		Quat4f allotRot = angleToRot(allotAngle);
		int angle = -1;
		float angleDifference = (float) (Math.PI * 2);
		for (int i = 0; i < discreteAngles.length; i++) {
			// Make rotation for this action
			Quat4f rotAction = angleToRot(discreteAngles[i]);
			// Invert
			rotAction.inverse();
			// Compose rotToMake and inverse of action.
			rotAction.mul(allotRot);
			// Compare axis angle. The closer to 0, the more suitable
			float resultingAngle = (float) Math.abs(rotToAngle(rotAction));
			if (resultingAngle < angleDifference) {
				angleDifference = resultingAngle;
				angle = i;
			}
		}

		return angle;
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
		for (int i = 0; i < actions.length; i++) {
			// Make rotation for this action
			Quat4f rotAction = angleToRot(actions[i]);
			// Invert
			rotAction.inverse();
			// Compose rotToMake and inverse of action.
			rotAction.mul(allotRot);
			// Compare axis angle. The closer to 0, the more suitable
			float resultingAbsAngle = (float) Math.abs(rotToAngle(rotAction));
			if (resultingAbsAngle < angleDifference) {
				angleDifference = resultingAbsAngle;
				action = i;
			}
		}

		return action;
	}

	 public static void main(String[] args){
//		 System.out.println(discretizeAngle((float) (-135 * Math.PI / 180)));
//		 System.out.println(discretizeAngle((float) (-90 * Math.PI / 180)));
		 System.out.println(
				 Math.toDegrees(
						 actions[
						         bestActionToRot(
						        		 angleToRot((float) Math.toRadians(135)),
						        		 angleToRot((float) Math.toRadians(90)))]
						        )
						    );
	 }

}
