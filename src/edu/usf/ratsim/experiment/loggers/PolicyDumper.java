package edu.usf.ratsim.experiment.loggers;

import java.util.List;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.MultiScaleModel;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;

public class PolicyDumper implements ExperimentLogger {
	private List<ArtificialPlaceCellLayer> pclLayers;
	private List<QLSupport> qlDatas;
	private String rep;
	private String subName;
	private String trial;
	private String groupName;

	public PolicyDumper(MultiScaleModel model, String trial,
			String groupName, String subName, String rep) {
		pclLayers = model.getPCLLayers();
		qlDatas = model.getQLDatas();
		this.trial = trial;
		this.subName = subName;
		this.groupName = groupName;
		this.rep = rep;
	}

	public void log(ExperimentUniverse universe) {
		for (int i = 0; i < pclLayers.size(); i++)
			qlDatas.get(i).dumpPolicy(trial, groupName, subName, rep,
					pclLayers.get(i), i);
	}

	public void finalizeLog() {

	}

}
