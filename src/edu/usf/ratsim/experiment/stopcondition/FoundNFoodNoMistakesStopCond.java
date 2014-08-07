package edu.usf.ratsim.experiment.stopcondition;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.support.Debug;
import edu.usf.ratsim.support.ElementWrapper;

public class FoundNFoodNoMistakesStopCond implements StopCondition {

	private ExperimentUniverse uni;
	private int stepsSinceLastAte;
	private int timeout;
	private int n;
	private int toGo;

	public FoundNFoodNoMistakesStopCond(ExperimentUniverse uni,
			ElementWrapper condParams) {
		this.uni = uni;
		n = condParams.getChildInt("n");
		toGo = n;
		timeout = condParams.getChildInt("timeout");
		stepsSinceLastAte = 0;
	}

	public boolean experimentFinished() {
		if (uni.hasRobotAte()) {
			stepsSinceLastAte = 0;
			toGo--;
		} else
			stepsSinceLastAte++;
				
		if (stepsSinceLastAte >= timeout){
			if (Debug.printFoundNNoMistakes)
				System.out.println("Reseting count of feeders");
			toGo = n;
			stepsSinceLastAte = 0;
		}
		
		if (Debug.printFoundNNoMistakes)
			System.out.println("Feeders to go " + toGo);
		
		return toGo <= 0;
	}

}
