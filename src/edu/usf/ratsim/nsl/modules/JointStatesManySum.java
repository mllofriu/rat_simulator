package edu.usf.ratsim.nsl.modules;

import java.util.List;

import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.FloatSumPort;
import edu.usf.ratsim.micronsl.Module;

public class JointStatesManySum extends Module {

	public JointStatesManySum(List<FloatPort> states) {
		addPort(new FloatSumPort("jointState", states));
	}

	public void simRun() {
		// Do nothing, the port does it all
	}

}
