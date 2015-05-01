package edu.usf.ratsim.support;

import java.util.Arrays;
import java.util.Collections;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;

public class GeomUtils {

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
	public static Quat4f rotBetweenVectors(Vector3f from, Vector3f to) {
		// Taken from
		// http://lolengine.net/blog/2013/09/18/beautiful-maths-quaternion-from-vectors
		// from.normalize();
		// to.normalize();
		// Quat4f res = new Quat4f();
		// Vector3f cross = new Vector3f();
		// cross.cross(from, to);
		// cross.normalize();
		// float dot = from.dot(to);
		//
		// res.x = cross.x;
		// res.y = cross.y;
		// res.z = cross.z;
		// res.w = 1.f + dot;
		//
		// res.normalize();
		//
		// Taken
		// fromhttp://lolengine.net/blog/2014/02/24/quaternion-from-two-vectors-final
		from.normalize();
		to.normalize();
		float norm_u_norm_v = 1f;
		float real_part = norm_u_norm_v + from.dot(to);
		Vector3f w;
		if (real_part < 1.e-6f * norm_u_norm_v) {
			/*
			 * If u and v are exactly opposite, rotate 180 degrees around an
			 * arbitrary orthogonal axis. Axis normalisation can happen later,
			 * when we normalise the quaternion.
			 */
			real_part = (float) 0;
//			if (Math.abs(from.x) > Math.abs(from.z))
//				w = new Vector3f(-from.y, from.x, 0.f);
//			else
//				w = new Vector3f(0.f, -from.z, from.y);
			// We know rotations are always using Z axis
			w = new Vector3f(0,0,1);
		} else {
			/* Otherwise, build quaternion the standard way. */
			w = new Vector3f();
			w.cross(from, to);
		}

		Quat4f res = new Quat4f(w.x, w.y, w.z, real_part);
		res.normalize();
		return res;
	}

	public static float angleToPointWithOrientation(Quat4f orientation,
			Point3f from, Point3f to) {
		Vector3f toPoint = pointsToVector(from, to);
		Quat4f rotTo = rotBetweenVectors(new Vector3f(1, 0, 0), toPoint);
		rotTo.inverse();
		rotTo.mul(orientation);
		return rotToAngle(rotTo);
	}

	public static Vector3f pointsToVector(Point3f from, Point3f to) {
		Vector3f fVect = new Vector3f(to);
		fVect.sub(from);

		return fVect;
	}

	public static Quat4f angleToRot(float angle) {
		Quat4f res = new Quat4f();
		Transform3D t = new Transform3D();
		t.rotZ(angle);
		t.get(res);
		return res;
	}

	private static float rotToAngle(Quat4f rot) {
		rot.normalize();
		float angle = (float) (2 * Math.acos(rot.w)) * Math.signum(rot.z);
		// Get the shortest
		if (angle > Math.PI)
			angle -= Math.PI * 2;
		else if (angle < -Math.PI)
			angle -= -Math.PI * 2;

		return (float) (angle);
	}

	public static Quat4f angleToPoint(Point3f location) {
		Vector3f toPoint = new Vector3f(location);
		Quat4f rotTo = rotBetweenVectors(new Vector3f(1, 0, 0), toPoint);
		return rotTo;
	}
	
	/**
	 * Angle from rot1 to rot2
	 * @param rot1
	 * @param rot2
	 * @return
	 */
	public static float angleDiff(Quat4f rot1, Quat4f rot2){
		rot1.inverse();
		rot2.mul(rot1);
		return rotToAngle(rot2);
	}

	public static float angleDiff(float a1, float a2) {
		Quat4f rot1 = angleToRot(a1);
		Quat4f rot2 = angleToRot(a2);
		return angleDiff(rot1, rot2);
	}

	public static float distanceToPoint(Point3f p) {
		return (float) Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2) + Math.pow(p.z, 2));
	}

	public static float getFeederReward(Point3f position, float rotationAngle, float maxReward,
			Subject subject, LocalizableRobot robot) {
		Quat4f rotToFood = GeomUtils.angleToPoint(position);

		Quat4f actionAngle = GeomUtils.angleToRot(rotationAngle);

		float angleDiff = Math.abs(GeomUtils.angleDiff(actionAngle,
				rotToFood));

		float rotationSteps = angleDiff / subject.getMinAngle();

		float dist = GeomUtils.distanceToPoint(position);

		float forwardSteps = dist / subject.getStepLenght();

		// TODO: improve this function
		return (float) (maxReward * Math.exp(-(forwardSteps + rotationSteps) / 10));
	}
}
