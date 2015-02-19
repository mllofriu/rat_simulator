package edu.usf.ratsim.experiment.task;


public class PolicySaverTask implements ExperimentTask {

	public PolicySaverTask() {
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		((MultiScaleMultiIntentionCooperativeModel)subject.getModel()).savePolicy();
	}

}
