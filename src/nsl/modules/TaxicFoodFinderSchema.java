package nsl.modules;

import javax.vecmath.Point3f;
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
		Point3f rPos = univ.getRobotPosition();
		Point3f fPos = univ.getFoodPosition();
		Vector3f rRot = univ.getRobotOrientation();
		
		// Get the vector food - robot
		Vector3f vToFood = Utiles.vectorToPoint(rPos, fPos);

		// Build quat4d for angle to food
		Quat4f rotToFood = Utiles.rotToPoint(rRot, vToFood);

		// Get affordances
		boolean[] affordances = robot.affordances();

		// Get best action to food
		int action = Utiles.bestActionToRot(rotToFood, affordances);

		if (action == -1)
			System.out.println("No affordances available");

		actionTaken.set(action);
	}
}
