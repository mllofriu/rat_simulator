package nsl.modules;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import robot.IRobot;
import support.Utiles;
import experiment.ExperimentUniverse;

public class TaxicFoodFinderSchema extends NslModule {

	public NslDoutInt0 actionTaken;
	private IRobot robot;
	private ExperimentUniverse univ;

	public TaxicFoodFinderSchema(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse univ) {
		super(nslName, nslParent);
		actionTaken = new NslDoutInt0("ActionTaken", this);
		this.robot = robot;
		this.univ = univ;
	}

	public void simRun() {
		// Get angle to food
		Point3d rPos = new Point3d(univ.getRobotPosition());
		Point3d fPos = new Point3d(univ.getFoodPosition());
		Quat4f rRot = univ.getRobotOrientation();

		// Transform x axis vector to robot heading vector
		Transform3D t = new Transform3D();
		t.set(rRot);
		Vector3f rVect = new Vector3f(1, 0, 0);
		t.transform(rVect);
		// Get the vector food - robot
		fPos.sub(rPos);
		Vector3f fVect = new Vector3f(fPos);

		// Build quat4d for angle to food
		Quat4f rotToFood = new Quat4f();
		Vector3f cross = new Vector3f();
		cross.cross(rVect, fVect);
		rotToFood.x = (float) (Math.sin(rVect.angle(fVect) / 2) * Math
				.cos(cross.angle(new Vector3f(1, 0, 0))));
		rotToFood.y = (float) (Math.sin(rVect.angle(fVect) / 2) * Math
				.cos(cross.angle(new Vector3f(0, 1, 0))));
		rotToFood.z = (float) (Math.sin(rVect.angle(fVect) / 2) * Math
				.cos(cross.angle(new Vector3f(0, 0, 1))));
		rotToFood.w = (float) Math.cos(rVect.angle(fVect) / 2);

		// Get affordances
		boolean[] affordances = robot.affordances();

		// Get best action to food
		int action = -1;
		double cosineDifference = 0;
		for (int i = 0; i < Utiles.actions.length; i++) {
			// Get angles rotated by the action
			double actionDegrees = Math.toRadians(Utiles
					.acccion2GradosRelative(i));
			// Build quaternion. Cross prod is assumed to be y axis
			Quat4f actionRot = new Quat4f(0,
					(float) Math.sin(actionDegrees / 2), 0,
					(float) Math.cos(actionDegrees / 2));
			// Get result for action rotation on robot
			actionRot.mulInverse(rotToFood);
			// Try to maximize w = cos(angle/2) => angle -> 0
			if (actionRot.w > cosineDifference && affordances[i]) {
				action = i;
				cosineDifference = actionRot.w;
				// System.out.println("Grados " +
				// Utiles.acccion2GradosRelative(i)
				// + " con error " + cosineDifference);
			}
		}

		if (action == -1)
			System.out.println("No affordances available");

		// System.out.println("Grados " +
		// Utiles.acccion2GradosRelative(action));

		actionTaken.set(action);
	}
}
