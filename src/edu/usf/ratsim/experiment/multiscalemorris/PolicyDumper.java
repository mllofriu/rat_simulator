package edu.usf.ratsim.experiment.multiscalemorris;

import java.util.List;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;

public class PolicyDumper implements ExperimentTask {
	private List<ArtificialPlaceCellLayer> pclLayers;
	private List<QLSupport> qlDatas;
	private String rep;
	private String subName;
	private String trial;

	public PolicyDumper(MSMSubject subject, String trial, String subName, String rep) {
		pclLayers = subject.getPCLLayers();
		qlDatas = subject.getQLDatas();
		this.trial = trial;
		this.subName = subName;
		this.rep = rep;
	}

	public void perform(ExperimentUniverse univ) {
		for (int i = 0; i < pclLayers.size(); i++)
			qlDatas.get(i).dumpPolicy(trial, subName, rep, pclLayers.get(i), i);
	}

}
