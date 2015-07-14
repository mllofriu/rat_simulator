package edu.usf.ratsim.nsl.modules;

import java.util.List;

import edu.usf.ratsim.micronsl.FloatMultiplyPort;
import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.Module;

public class JointStatesManyMultiply extends Module {

	private static final float EPS = 0.2f;

	public JointStatesManyMultiply(List<FloatPort> states) {
		addPort(new FloatMultiplyPort("jointState", states, EPS));
	}

	public void simRun() {
		// All is done in the multiply port
	}

}
