package edu.usf.ratsim.nsl.modules;

import java.util.List;

import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.FloatSumPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.micronsl.Port;

public class JointStatesManySum extends Module {

	public JointStatesManySum(String name) {
		super(name);
	}

	@Override
	public void addInPorts(List<Port> states) {
		super.addInPorts(states);
		addOutPort("jointState", new FloatSumPort(this, (List<FloatPort>)(List<?>)states));
	}

	public void simRun() {
		// Do nothing, the port does it all
	}

}
