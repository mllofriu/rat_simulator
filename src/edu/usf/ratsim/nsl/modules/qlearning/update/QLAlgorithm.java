package edu.usf.ratsim.nsl.modules.qlearning.update;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public interface QLAlgorithm {

	void setUpdatesEnabled(boolean b);

	void savePolicy();

	void dumpPolicy(String trial, String groupName, String subName, String rep,
			int numIntentions, ExperimentUniverse universe, ExpSubject subject);

}
