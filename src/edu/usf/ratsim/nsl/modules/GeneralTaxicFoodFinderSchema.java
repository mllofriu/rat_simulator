package edu.usf.ratsim.nsl.modules;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

/**
 * Sets the dopaminergic votes for both a flashing feeder and a non flashing
 * one.
 * 
 * @author ludo
 * 
 */
public class GeneralTaxicFoodFinderSchema extends NslModule {

	private static final float HALF_FIELD_OF_VIEW = (float) (105 * Math.PI / 180); // 105
																					// degrees
	private ExperimentUniverse univ;
	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 votes;
	private float rewardFlashing, rewardNonFlashing;
	private Integer lastEatenFeeder;

	public GeneralTaxicFoodFinderSchema(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse univ, int numActions,
			float rewardFlashing, float rewardNonFlashing) {
		super(nslName, nslParent);
		this.univ = univ;
		this.rewardFlashing = rewardFlashing;
		this.rewardNonFlashing = rewardNonFlashing;

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		votes = new NslDoutFloat1(this, "votes", numActions);

		lastEatenFeeder = -1;
	}

	public void simRun() {
		votes.set(0);

		boolean foundFeeder = false;

		// Go over all feeders
		for (Integer fn : univ.getFeeders()) {
			// Avoid last feeder
			if (fn != lastEatenFeeder){
				if (univ.angleToFeeder(fn) <= HALF_FIELD_OF_VIEW) {
					foundFeeder = true;
					// Get the best action for that feeder
					int action = getActionToFeeder(fn);
					// Set the reward to be the corresponding reward
					// float distanceMod = Math.max(0, (1 -
					// univ.getDistanceToFeeder(fn)));
					float distanceMod = 1;

					if (univ.getFlashingFeeders().contains(fn))
						votes.set(
								action,
								Math.max(rewardFlashing * distanceMod,
										votes.get(action)));
					else
						votes.set(
								action,
								Math.max(rewardNonFlashing * distanceMod,
										votes.get(action)));
					
					if (action == Utiles.eatAction)
						lastEatenFeeder = fn;
				}
			}

		}
		
		
		if (!foundFeeder){
			// Give a forward impulse
			votes.set(Utiles.discretizeAction(0), rewardNonFlashing / 2);
		}

	}

	private int getActionToFeeder(int feeder) {
		if (univ.isRobotCloseToFeeder(feeder))
			return Utiles.eatAction;
		else {
			Point3f rPos = univ.getRobotPosition();
			Point3f fPos = univ.getFoodPosition(feeder);

			// Get the vector food - robot
			Vector3f vToFood = Utiles.pointsToVector(rPos, fPos);

			// Build quat4d for angle to food
			// Use (1,0,0) to get absolute orientation
			Quat4f rotToFood = Utiles.rotBetweenVectors(new Vector3f(1, 0, 0),
					vToFood);

			Quat4f currRot = univ.getRobotOrientation();
			return Utiles.bestActionToRot(rotToFood, currRot);
		}
	}
}
