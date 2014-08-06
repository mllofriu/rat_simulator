package edu.usf.ratsim.experiment.task;

import java.util.Collection;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.support.ElementWrapper;

public class DeactivateFeeder implements ExperimentTask {

	private int feeder;

	public DeactivateFeeder(ElementWrapper taskParams) {
		feeder = taskParams.getChildInt("feeder");
	}

	@Override
	public void perform(ExperimentUniverse univ, ExpSubject subject) {	
		univ.setActiveFeeder(feeder, false);
	}

}
