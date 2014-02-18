package edu.usf.ratsim.experiment;

import edu.usf.ratsim.experiment.subject.ExpSubject;

public interface ExperimentTask {

	void perform(ExperimentUniverse univ, ExpSubject subject);

}
