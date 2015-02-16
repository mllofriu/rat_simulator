package edu.usf.ratsim.nsl.modules.qlearning.update;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;

public interface PolicyDumper {
	/**
	 * Dumps the qlearning policy with a certain intention to a file. The
	 * alignment between pcl cells and ql states is assumed for efficiency
	 * purposes.
	 * 
	 * @param rep
	 * @param subName
	 * @param trial
	 * @param rep
	 * @param rlRatModel 
	 * 
	 * @param writer
	 * @param pcl
	 */
	public void dumpPolicy(String trial, String groupName, String subName,
			String rep, int numIntentions, ExperimentUniverse univ, ExpSubject subject);

}
