package edu.usf.ratsim.experiment.task;


public class ExplorationIncRepTask implements ExperimentTask {

	public ExplorationIncRepTask() {
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		((MultiScaleMultiIntentionCooperativeModel)subject.getModel()).newRep();
	}

}
