package edu.usf.ratsim.nsl.modules;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

public class TaxicFoodFinderSchema extends NslModule {

	private IRobot robot;
	private ExperimentUniverse univ;
	public NslDinInt0 goalFeeder;

	public TaxicFoodFinderSchema(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse univ) {
		super(nslName, nslParent);
		this.robot = robot;
		this.univ = univ;

		goalFeeder = new NslDinInt0(this);
	}

	public void simRun() {
		// If the current goal is flashing override other modules actions
		// (this module should come after others)
		if (goalFeeder.get() != -1
				&& univ.getFlashingFeeders().contains(goalFeeder.get())) {
			// Get angle to food
			Point3f rPos = univ.getRobotPosition();
			Point3f fPos = univ.getFoodPosition(goalFeeder.get());
			Quat4f rRot = univ.getRobotOrientation();

			// Get the vector food - robot
			Vector3f vToFood = Utiles.vectorToPoint(rPos, fPos);

			// Build quat4d for angle to food
			// Use (1,0,0) to get absolute orientation
			Quat4f rotToFood = Utiles
					.rotToPoint(new Vector3f(1, 0, 0), vToFood);

			// Get affordances
			boolean[] affordances;

			// Get best action to food
			int action = Utiles.bestActionToRot(rotToFood, rRot);

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
}
