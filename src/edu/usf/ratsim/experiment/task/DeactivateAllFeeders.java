package edu.usf.ratsim.experiment.task;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class DeactivateAllFeeders implements ExperimentTask {


	public DeactivateAllFeeders() {
	}

	@Override
	public void perform(ExperimentUniverse univ, ExpSubject subject) {	
		for(Integer f : univ.getActiveFeeders())
			univ.setActiveFeeder(f, false);
	}

}
