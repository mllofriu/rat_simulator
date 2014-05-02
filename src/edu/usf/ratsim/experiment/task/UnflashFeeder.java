package edu.usf.ratsim.experiment.task;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class UnflashFeeder implements ExperimentTask {

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
//		if (univ.hasRobotFoundFood()) {
			for (Integer i : univ.getFlashingFeeders())
				univ.setFlashingFeeder(i, false);
			for (Integer i : univ.getActiveFeeders())
				univ.setActiveFeeder(i, false);
//		}

	}

}
