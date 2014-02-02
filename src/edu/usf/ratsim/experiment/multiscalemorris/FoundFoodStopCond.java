package edu.usf.ratsim.experiment.multiscalemorris;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.StopCondition;

public class FoundFoodStopCond implements StopCondition {

	private ExperimentUniverse uni;

	public FoundFoodStopCond(ExperimentUniverse uni) {
		this.uni = uni;
	}

	public boolean experimentFinished() {
		return uni.hasRobotFoundFood();
	}

}
