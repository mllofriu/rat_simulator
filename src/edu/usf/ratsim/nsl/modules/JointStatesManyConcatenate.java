package edu.usf.ratsim.nsl.modules;

import java.util.List;

import edu.usf.ratsim.micronsl.FloatConcatenatePort;
import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.Module;

public class JointStatesManyConcatenate extends Module {

	public JointStatesManyConcatenate(List<FloatPort> states) {
		addPort(new FloatConcatenatePort("jointState", states));
	}

	public void simRun() {
		// Do nothing, the port does all the work
	}
}
