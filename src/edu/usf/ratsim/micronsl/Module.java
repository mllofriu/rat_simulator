package edu.usf.ratsim.micronsl;

import java.util.LinkedList;
import java.util.List;

public abstract class Module {

	private List<Module> preReqs;
	private List<Port> ports;

	public Module() {
		preReqs = new LinkedList<Module>();
		ports = new LinkedList<Port>();
	}

	public abstract void simRun();

	public List<Module> getPreReqs() {
		return preReqs;
	}

	public void addPreReq(Module m) {
		preReqs.add(m);
	}

	public void addPort(Port port) {
		ports.add(port);
	}

	public Port getPort(String name) {
		for (Port p : ports)
			if (p.getName().equals(name))
				;

		return null;
	}

}
