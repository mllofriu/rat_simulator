package edu.usf.ratsim.nsl.modules.qlearning;

import edu.usf.experiment.subject.Subject;
import edu.usf.ratsim.micronsl.FloatArrayPort;
import edu.usf.ratsim.micronsl.Module;

public class Reward extends Module {

	private float[] reward;
	private float nonFoodReward;
	private float foodReward;
	private Subject subject;

	public Reward(String name, Subject subject, float foodReward,
			float nonFoodReward) {
		super(name);
		reward = new float[1];
		addOutPort("reward", new FloatArrayPort(this, reward));

		this.foodReward = foodReward;
		this.nonFoodReward = nonFoodReward;
		this.subject = subject;
	}

	public void simRun() {
		if (subject.hasEaten()) {
			reward[0] = foodReward;
		} else {
			reward[0] = nonFoodReward;
		}

	}
}
