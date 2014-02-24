package edu.usf.ratsim.experiment.loggers;

import java.util.List;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.MultiScaleModel;
import edu.usf.ratsim.experiment.model.MultiScaleMultiIntentionModel;
import edu.usf.ratsim.experiment.model.RLRatModel;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;

public class PolicyDumperWithIntention implements ExperimentLogger {
	private List<ArtificialPlaceCellLayerWithIntention> pclLayers;
	private List<QLSupport> qlDatas;
	private String rep;
	private String subName;
	private String trial;
	private String groupName;
	private int numIntentions;

	public PolicyDumperWithIntention(MultiScaleMultiIntentionModel rlRatModel, String trial,
			String groupName, String subName, String rep, int numIntentions) {
		pclLayers = rlRatModel.getPCLLayers();
		qlDatas = rlRatModel.getQLDatas();
		this.trial = trial;
		this.subName = subName;
		this.groupName = groupName;
		this.rep = rep;
		this.numIntentions = numIntentions;
	}

	public void log(ExperimentUniverse universe) {
		for (int i = 0; i < pclLayers.size(); i++)
			qlDatas.get(i).dumpPolicy(trial, groupName, subName, rep,
					pclLayers.get(i), i, numIntentions);
	}

	public void finalizeLog() {

	}

}
