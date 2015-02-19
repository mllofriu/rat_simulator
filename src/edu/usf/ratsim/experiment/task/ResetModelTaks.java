package edu.usf.ratsim.experiment.task;


public class ResetModelTaks implements ExperimentTask {

	public ResetModelTaks() {
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		((MultiScaleMultiIntentionCooperativeModel)subject.getModel()).newTrial();
	}

}
