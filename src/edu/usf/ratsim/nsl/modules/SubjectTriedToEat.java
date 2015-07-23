package edu.usf.ratsim.nsl.modules;

import edu.usf.experiment.subject.Subject;
import edu.usf.ratsim.micronsl.Bool1dPort;
import edu.usf.ratsim.micronsl.Module;


public class SubjectTriedToEat extends Module {

	private Subject sub;
	private Bool1dPort outPort;

	public SubjectTriedToEat(String name, Subject sub) {
		super(name);
		
		this.sub = sub;
		
		outPort = new Bool1dPort(this);
		addOutPort("subTriedToEat", outPort);
	}

	@Override
	public void run() {
		outPort.set(sub.hasTriedToEat());
	}

	@Override
	public boolean usesRandom() {
		return false;
	}

	
}
