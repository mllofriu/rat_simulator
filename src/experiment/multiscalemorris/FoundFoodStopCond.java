package experiment.multiscalemorris;

import experiment.ExperimentUniverse;
import experiment.StopCondition;

public class FoundFoodStopCond implements StopCondition {

	private ExperimentUniverse uni;

	public FoundFoodStopCond(ExperimentUniverse uni) {
		this.uni = uni;
	}

	@Override
	public boolean experimentFinished() {
		return uni.hasRobotFoundFood();
	}

}
