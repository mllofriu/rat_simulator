package edu.usf.ratsim.experiment.task;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.MultiScaleMultiIntentionCooperativeModel;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class ResetModelTaks implements ExperimentTask {

	public ResetModelTaks() {
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		((MultiScaleMultiIntentionCooperativeModel)subject.getModel()).newTrial();
	}

}
