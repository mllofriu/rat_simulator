package edu.usf.ratsim.nsl.modules.qlearning;

import nslj.src.lang.NslDoutFloat0;
import nslj.src.lang.NslModule;
import edu.usf.experiment.subject.Subject;

public class Reward extends NslModule {

	private NslDoutFloat0 reward;
	private float nonFoodReward;
	private float foodReward;
	private Subject subject;

	public Reward(String nslName, NslModule nslParent, Subject subject,
			float foodReward, float nonFoodReward) {
		super(nslName, nslParent);

		reward = new NslDoutFloat0(this, "reward");

		this.foodReward = foodReward;
		this.nonFoodReward = nonFoodReward;
		this.subject = subject;
	}

	public void simRun() {
		if (subject.hasEaten()) {
			// System.out.println("Ate Food");
			reward.set(foodReward);
		} else
			reward.set(nonFoodReward);
	}
}
