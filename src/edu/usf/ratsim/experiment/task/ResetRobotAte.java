package edu.usf.ratsim.experiment.task;


public class ResetRobotAte implements ExperimentTask {


	public ResetRobotAte() {
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		univ.clearRobotAte();
	}

}
