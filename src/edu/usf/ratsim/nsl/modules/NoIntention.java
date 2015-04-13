package edu.usf.ratsim.nsl.modules;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;

/**
 * Dummy intention module that always sets the same intention
 * @author biorob
 *
 */
public class NoIntention extends NslModule implements Intention {

	public NslDoutFloat1 intention;

	public NoIntention(String nslName, NslModule parent, int numIntentions) {
		super(nslName, parent);

		intention = new NslDoutFloat1(this, "intention", numIntentions);
	}

	public void simRun() {
		intention.set(0);
		intention.set(0, 1);
	}
	
	public void simRun(int inte){
		simRun();
	}
}
