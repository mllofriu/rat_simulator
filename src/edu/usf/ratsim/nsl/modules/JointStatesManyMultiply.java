package edu.usf.ratsim.nsl.modules;

import java.util.List;

import edu.usf.ratsim.micronsl.Float1dPort;
import edu.usf.ratsim.micronsl.Float1dPortMultiply;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.micronsl.Port;

public class JointStatesManyMultiply extends Module {

	private static final float EPS = 0.2f;

	public JointStatesManyMultiply(String name) {
		super(name);

	}

	public void run() {
		// All is done in the multiply port
		
		// Clear optimization cache
		((Float1dPortMultiply)getOutPort("jointState")).clearOptimizationCache();
	}

	@Override
	public void addInPorts(List<Port> ports) {
		super.addInPorts(ports);

		addOutPort("jointState", new Float1dPortMultiply(this,
				(List<Float1dPort>) (List<?>) ports, EPS));
	}

}
