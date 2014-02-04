package edu.usf.ratsim.nsl.modules;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

public class TaxicFoodFinderSchema extends NslModule {

	private IRobot robot;
	private ExperimentUniverse univ;

	public TaxicFoodFinderSchema(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse univ) {
		super(nslName, nslParent);
		this.robot = robot;
		this.univ = univ;
	}

	public void simRun() {
		// Get angle to food
		Point3f rPos = univ.getRobotPosition();
		Point3f fPos = univ.getFoodPosition();
		Quat4f rRot = univ.getRobotOrientation();

		// Get the vector food - robot
		Vector3f vToFood = Utiles.vectorToPoint(rPos, fPos);

		// Build quat4d for angle to food
		// Use (1,0,0) to get absolute orientation
		Quat4f rotToFood = Utiles.rotToPoint(new Vector3f(1, 0, 0), vToFood);

		// Get affordances
		boolean[] affordances = robot.getAffordances();

		// Get best action to food
		int action = Utiles.bestActionToRot(rotToFood, rRot, affordances);

		if (action == -1)
			System.out.println("No affordances available");
		else {
			robot.rotate(Utiles.actions[action]);
			affordances = robot.getAffordances();
			if (affordances[Utiles.discretizeAction(0)])
				robot.forward();
		}
	}
}
