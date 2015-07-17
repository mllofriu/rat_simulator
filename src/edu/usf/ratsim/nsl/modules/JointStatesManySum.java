package edu.usf.ratsim.nsl.modules;

import java.util.List;

import edu.usf.ratsim.micronsl.Float1dPort;
import edu.usf.ratsim.micronsl.Float1dPortSum;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.micronsl.Port;

public class JointStatesManySum extends Module {

	public JointStatesManySum(String name) {
		super(name);
	}

	@Override
	public void addInPorts(List<Port> states) {
		super.addInPorts(states);
		addOutPort("jointState", new Float1dPortSum(this, (List<Float1dPort>)(List<?>)states));
	}

	public void run() {
		// Do nothing, the port does it all
	}

}
