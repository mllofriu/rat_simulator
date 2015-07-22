package edu.usf.ratsim.micronsl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Module extends DependencyRunnable {

	private Map<String, Port> outPorts;
	private String name;
	private Map<String, Port> inPorts;

	public Module(String name) {
		super();
		
		outPorts = new HashMap<String, Port>();
		inPorts = new HashMap<String, Port>();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addInPort(String name, Port port) {
		addInPort(name, port, false);
	}

	public void addInPort(String name, Port port, boolean reverseDependency) {
		if (!reverseDependency) {
			// If it is an in port, the module producing it is a prereq
			if (port.getOwner() != null)
				addPreReq(port.getOwner());
		} else {
			if (port.getOwner() != null)
				port.getOwner().addPreReq(this);
		}

		inPorts.put(name, port);
	}

	public void addInPorts(List<Port> ports) {
		for (Port port : ports)
			addInPort("Nameless Port #" + inPorts.size(), port);
	}

	public void addOutPort(String name, Port port) {
		outPorts.put(name, port);
	}

	public Port getOutPort(String name) {
		if (!outPorts.containsKey(name))
			throw new RuntimeException("There is no out-port named " + name);
		return outPorts.get(name);
	}

	public Port getInPort(String name) {
		if (!inPorts.containsKey(name))
			throw new RuntimeException("There is no in-port named " + name);
		return inPorts.get(name);
	}

}