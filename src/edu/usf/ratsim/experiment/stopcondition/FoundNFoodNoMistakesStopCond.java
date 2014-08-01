package edu.usf.ratsim.experiment.stopcondition;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.support.ElementWrapper;

public class FoundNFoodNoMistakesStopCond implements StopCondition {

	private static final String STR_N = "n";
	private ExperimentUniverse uni;
	private int n;
	private int found;
	private boolean hasMadeMistake;

	public FoundNFoodNoMistakesStopCond(ExperimentUniverse uni,
			ElementWrapper condParams) {
		this.n = condParams.getChildInt(STR_N);
		this.found = this.n;
		this.uni = uni;
		this.hasMadeMistake = false;
	}

	public boolean experimentFinished() {
		
		if (uni.hasRobotFoundFood() && uni.hasRobotAte())
			found--;
		// If made a mistake but is the first one, dont reinitialize
		else if (uni.hasRobotTriedToEat() && uni.isRobotCloseToAFeeder()
				&& !uni.hasRobotAte() && !hasMadeMistake)
			hasMadeMistake = true;
		// If rat made a mistake already, reinitialize
		else if (uni.hasRobotTriedToEat() && uni.isRobotCloseToAFeeder()
				&& !uni.hasRobotAte() && hasMadeMistake){
			System.out.println("Reinitializing count");
			found = n;
			hasMadeMistake = false;
		}
		
		System.out.println("Feeders to go: " + found);
		return found <= 0;
	}

}
