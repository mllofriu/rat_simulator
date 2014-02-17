package edu.usf.ratsim.experiment.stopcondition;

import edu.usf.ratsim.experiment.ExperimentUniverse;

public class FoundFoodStopCond implements StopCondition {

	private ExperimentUniverse uni;

	public FoundFoodStopCond(ExperimentUniverse uni) {
		this.uni = uni;
	}

	public boolean experimentFinished() {
		return uni.hasRobotFoundFood();
	}

}
