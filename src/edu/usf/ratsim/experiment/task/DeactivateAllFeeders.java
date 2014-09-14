package edu.usf.ratsim.experiment.task;

import java.util.Collection;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.support.ElementWrapper;

public class DeactivateAllFeeders implements ExperimentTask {


	public DeactivateAllFeeders() {
	}

	@Override
	public void perform(ExperimentUniverse univ, ExpSubject subject) {	
		for(Integer f : univ.getActiveFeeders())
			univ.setActiveFeeder(f, false);
	}

}
