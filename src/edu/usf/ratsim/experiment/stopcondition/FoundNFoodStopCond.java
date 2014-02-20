package edu.usf.ratsim.experiment.stopcondition;

import org.w3c.dom.Element;

import edu.usf.ratsim.experiment.ExperimentUniverse;

public class FoundNFoodStopCond implements StopCondition {

	private static final String STR_N = "n";
	private ExperimentUniverse uni;
	private int n;

	public FoundNFoodStopCond(ExperimentUniverse uni, Element paramNode) {
		this.n = Integer.parseInt(paramNode.getElementsByTagName(STR_N).item(0).getTextContent());
		this.uni = uni;
	}

	public boolean experimentFinished() {
		if (uni.hasRobotFoundFood())
			n--;
		return n <= 0;
	}

}
