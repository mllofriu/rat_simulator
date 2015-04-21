package edu.usf.ratsim.nsl.modules;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.utils.Debug;

public class LastAteIntention extends NslModule implements Intention{

	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 intention;

	public LastAteIntention(String nslName, NslModule parent, int numIntentions) {
		super(nslName, parent);

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		intention = new NslDoutFloat1(this, "intention", numIntentions);
	}

	public void simRun() {
		intention.set(0);

//		System.out.println(goalFeeder.get());
		if (goalFeeder.get() != -1)
			intention.set(goalFeeder.get(), 1);
		if (Debug.printIntention) {
			for (int i = 0; i < intention.getSize(); i++)
				System.out.print(intention.get(i) + " ");
			System.out.println();
		}

	}
	
	public void simRun(int inte){
		intention.set(0);
		intention.set(inte, 1);
	}
}
