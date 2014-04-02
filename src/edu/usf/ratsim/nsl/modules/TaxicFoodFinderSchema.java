package edu.usf.ratsim.nsl.modules;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import nslj.src.lang.NslNumeric0;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

public class TaxicFoodFinderSchema extends NslModule {

	private IRobot robot;
	private ExperimentUniverse univ;
	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 votes;
	private float maxReward;

	public TaxicFoodFinderSchema(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse univ, int numActions,
			float maxReward) {
		super(nslName, nslParent);
		this.robot = robot;
		this.univ = univ;
		this.maxReward = maxReward;

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		votes = new NslDoutFloat1(this, "votes", numActions);

	}

	public void simRun() {
		// If the current goal is flashing override other modules actions
		// (this module should come after others)
		votes.set(0);
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
//			boolean[] affordances;

			// Get best action to food
//			int action = Utiles.bestActionToRot(rotToFood, rRot);

//			System.out.println(goalFeeder.get() + " " + Utiles.discretizeAngle(new Vector3f(1, 0, 0).angle(vToFood)));
			
			votes.set(Utiles.discretizeAngle(rotToFood), maxReward);
			
//			if (action == -1)
//				System.out.println("No affordances available");
//			else {
//				robot.rotate(Utiles.actions[action]);
//				affordances = robot.getAffordances();
//				if (affordances[Utiles.discretizeAction(0)])
//					robot.forward();
//			}

		}
	}
}
