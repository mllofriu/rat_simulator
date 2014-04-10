package edu.usf.ratsim.experiment.task;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class ClearWallsTask implements ExperimentTask {

	public ClearWallsTask() {
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		univ.clearWalls();
	}

}
