package edu.usf.ratsim.experiment.loggers;

import java.util.List;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.RLRatModel;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;

public class PolicyDumper implements ExperimentLogger {
	private List<ArtificialPlaceCellLayer> pclLayers;
	private List<edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper> polDumpers;
	private String rep;
	private String subName;
	private String trial;
	private String groupName;

	public PolicyDumper(RLRatModel rlRatModel, String trial,
			String groupName, String subName, String rep) {
		pclLayers = rlRatModel.getPCLLayers();
		polDumpers = rlRatModel.getPolicyDumpers();
		this.trial = trial;
		this.subName = subName;
		this.groupName = groupName;
		this.rep = rep;
	}

	public void log(ExperimentUniverse universe) {
		for (int i = 0; i < polDumpers.size(); i++)
			polDumpers.get(i).dumpPolicy(trial, groupName, subName, rep,
					pclLayers.get(i), i);
	}

	public void finalizeLog() {

	}

}
