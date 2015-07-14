package edu.usf.ratsim.nsl.modules;

import edu.usf.ratsim.micronsl.Port;

public interface Intention {

	public void simRun(int inte);

	public Port getPort(String name);
}
