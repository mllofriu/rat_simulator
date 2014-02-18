package edu.usf.ratsim.experiment.task;

import java.util.Collection;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class ActivateFeeders implements ExperimentTask {

	public static final String STR_ACTIVE_FEEDERS = "activeFeeders";

	@Override
	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		Collection<Integer> feeders = (Collection<Integer>) subject
				.getProperty(STR_ACTIVE_FEEDERS);
		
		for (Integer i : feeders)
			univ.setActiveFeeder(i, true);
	}

}
