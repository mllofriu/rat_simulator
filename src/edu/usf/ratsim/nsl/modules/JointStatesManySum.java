package edu.usf.ratsim.nsl.modules;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

	@Override
	public boolean usesRandom() {
		return false;
	}

	@Override
	public Set<Module> getValueInfluencingModules() {
		Set<Module> res = new LinkedHashSet<Module>();
		for (Port p : getInPorts())
			res.addAll(p.getOwner().getValueInfluencingModules());
		return res;
	}
}
