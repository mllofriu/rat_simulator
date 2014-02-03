package edu.usf.ratsim.experiment.multiscalemorris;

import java.util.List;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;

public class PolicyDumper implements ExperimentTask {
	private String logDir;
	private List<ArtificialPlaceCellLayer> pclLayers;
	private List<QLSupport> qlDatas;
	
	public PolicyDumper(MSMSubject subject, String logDir) {
		pclLayers = subject.getPCLLayers();
		qlDatas = subject.getQLDatas();
		this.logDir = logDir;
	}

	
	public void perform(ExperimentUniverse univ) {
		for(int i = 0; i < pclLayers.size(); i++)
			qlDatas.get(i).dumpPolicy(logDir, pclLayers.get(i));
	}

}
