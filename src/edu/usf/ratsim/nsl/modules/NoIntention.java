package edu.usf.ratsim.nsl.modules;

import edu.usf.ratsim.micronsl.FloatArrayPort;
import edu.usf.ratsim.micronsl.Module;

/**
 * Dummy intention module that always sets the same intention
 * 
 * @author biorob
 *
 */
public class NoIntention extends Module implements Intention {

	public float[] intention;

	public NoIntention(String name, int numIntentions) {
		super(name);
		intention = new float[numIntentions];
		addOutPort("intention", new FloatArrayPort(this, intention));
	}

	public void simRun() {
		for (int i = 0; i < intention.length; i++)
			intention[i] = 0;

		intention[0] = 1;
	}

	public void simRun(int inte) {
		simRun();
	}
}
