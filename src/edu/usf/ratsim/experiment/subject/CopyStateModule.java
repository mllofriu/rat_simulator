package edu.usf.ratsim.experiment.subject;

import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.Module;

public class CopyStateModule extends Module {
	
	private CopyFloatPort copyPort;

	public CopyStateModule(FloatPort toCopy) {
		copyPort = new CopyFloatPort("copy", toCopy);
		addPort(copyPort);
	}

	@Override
	public void simRun() {
		copyPort.copy();
	}

}
