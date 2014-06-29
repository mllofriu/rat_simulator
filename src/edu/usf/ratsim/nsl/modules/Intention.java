package edu.usf.ratsim.nsl.modules;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;

public class Intention extends NslModule {

	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 intention;

	public Intention(NslModule parent, String nslName, int numIntentions) {
		super(nslName, parent);

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		intention = new NslDoutFloat1(this, "intention", numIntentions);
	}

	public void simRun() {
		intention.set(0);

		if (goalFeeder.get() != -1)
			intention.set(goalFeeder.get(), 1);
		// for(int i = 0; i < intention.getSize(); i++)
		// System.out.print(intention.get(i) + " ");
		// System.out.println();

	}
}
