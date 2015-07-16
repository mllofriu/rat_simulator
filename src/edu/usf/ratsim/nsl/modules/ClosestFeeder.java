package edu.usf.ratsim.nsl.modules;

import edu.usf.experiment.subject.Subject;
import edu.usf.ratsim.micronsl.Int0dPort;
import edu.usf.ratsim.micronsl.Module;

public class ClosestFeeder extends Module {

	private Subject sub;
	private Int0dPort outPort;

	public ClosestFeeder(String name, Subject sub) {
		super(name);
		
		this.sub = sub;
		
		outPort = new Int0dPort(this);
		addOutPort("closestFeeder", outPort);
	}

	@Override
	public void simRun() {
		outPort.set(sub.getRobot().getClosestFeeder().getId());
	}

	
}
