package edu.usf.ratsim.experiment.task;

import java.util.Random;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class ResetRobotAte implements ExperimentTask {


	public ResetRobotAte() {
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		univ.clearRobotAte();
	}

}
