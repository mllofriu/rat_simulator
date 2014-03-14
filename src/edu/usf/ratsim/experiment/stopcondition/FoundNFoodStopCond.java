package edu.usf.ratsim.experiment.stopcondition;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.support.ElementWrapper;

public class FoundNFoodStopCond implements StopCondition {

	private static final String STR_N = "n";
	private ExperimentUniverse uni;
	private int n;

	public FoundNFoodStopCond(ExperimentUniverse uni, ElementWrapper condParams) {
		this.n = condParams.getChildInt(STR_N);
		this.uni = uni;
	}

	public boolean experimentFinished() {
		if (uni.hasRobotFoundFood())
			n--;
		return n <= 0;
	}

}
