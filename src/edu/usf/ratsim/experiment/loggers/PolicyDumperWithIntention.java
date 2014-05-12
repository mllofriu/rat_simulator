package edu.usf.ratsim.experiment.loggers;

import java.util.List;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.RLRatModel;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper;

public class PolicyDumperWithIntention implements ExperimentLogger {
	private List<ArtificialPlaceCellLayerWithIntention> pclLayers;
	private List<PolicyDumper> policyDumpers;
	private String rep;
	private String subName;
	private String trial;
	private String groupName;
	private int numIntentions;

	public PolicyDumperWithIntention(RLRatModel rlRatModel, String trial,
			String groupName, String subName, String rep, int numIntentions) {
		pclLayers = rlRatModel.getPCLLayersIntention();
		policyDumpers = rlRatModel.getPolicyDumpers();
		this.trial = trial;
		this.subName = subName;
		this.groupName = groupName;
		this.rep = rep;
		this.numIntentions = numIntentions;
	}

	public void log(ExperimentUniverse universe) {
		for (int i = 0; i < policyDumpers.size(); i++)
			policyDumpers.get(i).dumpPolicy(trial, groupName, subName, rep,
					pclLayers.get(i), i, numIntentions);
	}

	public void finalizeLog() {

	}

}