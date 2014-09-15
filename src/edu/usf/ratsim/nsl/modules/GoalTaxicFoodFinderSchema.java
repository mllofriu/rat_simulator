package edu.usf.ratsim.nsl.modules;

import java.util.Random;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

public class GoalTaxicFoodFinderSchema extends NslModule {

	private IRobot robot;
	private ExperimentUniverse univ;
	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 votes;
	private float maxReward;
	private Random r;

	public GoalTaxicFoodFinderSchema(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse univ, int numActions,
			float maxReward) {
		super(nslName, nslParent);
		this.robot = robot;
		this.univ = univ;
		this.maxReward = maxReward;

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		votes = new NslDoutFloat1(this, "votes", numActions);
		
		r = new Random();

	}

	public void simRun() {
		// If the current goal is flashing override other modules actions
		// (this module should come after others)
		votes.set(0);
		if (goalFeeder.get() != -1 && univ.canRobotSeeFeeder(goalFeeder.get())) {

			if (univ.isRobotCloseToFeeder(goalFeeder.get())){
				votes.set(Utiles.eatAction, maxReward);
//				System.out.println("Setting votes to eat");
			} else {
				// Get angle to food
				Point3f rPos = univ.getRobotPosition();
				Point3f fPos = univ.getFoodPosition(goalFeeder.get());
				Quat4f rRot = univ.getRobotOrientation();

				// Get the vector food - robot
				Vector3f vToFood = Utiles.pointsToVector(rPos, fPos);

				// Build quat4d for angle to food
				// Use (1,0,0) to get absolute orientation
				Quat4f rotToFood = Utiles.rotBetweenVectors(new Vector3f(1, 0, 0),
						vToFood);

				// Get affordances
				// boolean[] affordances;

				// Get best action to food
				int action = Utiles.bestActionToRot(rotToFood, rRot);


				votes.set(action, maxReward);
			}
		} else {
			// Give a forward impulse
			if (r.nextFloat() > .8)
				votes.set(Utiles.discretizeAction(0),.01);
			else 
				if (r.nextFloat() > .5)
					votes.set(Utiles.discretizeAction(90), .01);
				else
					votes.set(Utiles.discretizeAction(-90), .01);
		}
	}
}
