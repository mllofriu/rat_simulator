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
 * Sets the dopaminergic votes for both a flashing feeder and a non flashing one.
 * @author ludo
 *
 */
public class GeneralTaxicFoodFinderSchema extends NslModule {

	private ExperimentUniverse univ;
	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 votes;
	private float rewardFlashing, rewardNonFlashing;

	public GeneralTaxicFoodFinderSchema(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse univ, int numActions,
			float rewardFlashing, float rewardNonFlashing) {
		super(nslName, nslParent);
		this.univ = univ;
		this.rewardFlashing = rewardFlashing;
		this.rewardNonFlashing = rewardNonFlashing;

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		votes = new NslDoutFloat1(this, "votes", numActions);

	}

	public void simRun() {
		// If the current goal is flashing override other modules actions
		// (this module should come after others)
		votes.set(0);
//		System.out.println(goalFeeder.get());
		if (goalFeeder.get() != -1) {
			// Get angle to food
			Point3f rPos = univ.getRobotPosition();
			Point3f fPos = univ.getFoodPosition(goalFeeder.get());

			// Get the vector food - robot
			Vector3f vToFood = Utiles.vectorToPoint(rPos, fPos);

			// Build quat4d for angle to food
			// Use (1,0,0) to get absolute orientation
			Quat4f rotToFood = Utiles
					.rotToPoint(new Vector3f(1, 0, 0), vToFood);

			if (univ.getFlashingFeeders().contains(goalFeeder.get()))
				votes.set(Utiles.discretizeAngle(rotToFood), rewardFlashing);
			else 
				votes.set(Utiles.discretizeAngle(rotToFood), rewardNonFlashing);
			
		}
	}
}
