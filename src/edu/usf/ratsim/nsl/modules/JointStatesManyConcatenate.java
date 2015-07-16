package edu.usf.ratsim.nsl.modules;

import java.util.List;

import edu.usf.ratsim.micronsl.FloatConcatenatePort;
import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.micronsl.Port;

public class JointStatesManyConcatenate extends Module {

	public JointStatesManyConcatenate(String name) {
		super(name);
	}

	public void simRun() {
	}

	@Override
	public void addInPorts(List<Port> ports) {
		super.addInPorts(ports);
		addOutPort("jointState", new FloatConcatenatePort(this,
				(List<FloatPort>) (List<?>) ports));
	}

}
