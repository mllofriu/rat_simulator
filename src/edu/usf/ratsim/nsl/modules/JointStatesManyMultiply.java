package edu.usf.ratsim.nsl.modules;

import java.util.List;

import edu.usf.ratsim.micronsl.FloatMultiplyPort;
import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.micronsl.Port;

public class JointStatesManyMultiply extends Module {

	private static final float EPS = 0.2f;

	public JointStatesManyMultiply(String name) {
		super(name);

	}

	public void simRun() {
		// All is done in the multiply port
	}

	@Override
	public void addInPorts(List<Port> ports) {
		super.addInPorts(ports);

		addOutPort("jointState", new FloatMultiplyPort(this,
				(List<FloatPort>) (List<?>) ports, EPS));
	}

}
