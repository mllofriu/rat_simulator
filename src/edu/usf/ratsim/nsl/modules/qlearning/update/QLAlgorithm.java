package edu.usf.ratsim.nsl.modules.qlearning.update;


public interface QLAlgorithm {

	void setUpdatesEnabled(boolean b);

	void savePolicy();

	void dumpPolicy(String trial, String groupName, String subName, String rep,
			int numIntentions, ExperimentUniverse universe, ExpSubject subject);

}
