package edu.usf.ratsim.micronsl;

public class CopyStateModule extends Module {

	private CopyFloatPort copyPort;

	public CopyStateModule(String name) {
		super(name);
	}

	@Override
	public void simRun() {
		copyPort.copy();
	}

	@Override
	public void addInPort(String name, Port port) {
		super.addInPort(name, port);
		copyPort = new CopyFloatPort(this, (FloatPort) port);
		addOutPort("copy", copyPort);
	}

	@Override
	public void addInPort(String name, Port port, boolean reverseDependency) {
		super.addInPort(name, port, reverseDependency);
		copyPort = new CopyFloatPort(this, (FloatPort) port);
		addOutPort("copy", copyPort);
	}

}
