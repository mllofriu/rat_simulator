package experiment.multiscalemorris;

import java.util.List;

import nsl.modules.ArtificialPlaceCellLayer;
import nsl.modules.QLearning;

import com.sun.tools.javac.util.Pair;

import experiment.ExperimentTask;
import experiment.ExperimentUniverse;

public class PolicyDumper implements ExperimentTask {
	List<Pair<QLearning, ArtificialPlaceCellLayer>> pclQlearnings;
	private String logDir;
	
	public PolicyDumper(MSMSubject subject, String logDir) {
		pclQlearnings = subject.getPCLQlearningPairs();
		this.logDir = logDir;
	}

	@Override
	public void perform(ExperimentUniverse univ) {
		for(Pair<QLearning, ArtificialPlaceCellLayer> pair : pclQlearnings)
			pair.fst.dumpPolicy(logDir, pair.snd);
	}

}
