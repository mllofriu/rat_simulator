package edu.usf.ratsim.nsl.modules;

import edu.usf.experiment.utils.Debug;
import edu.usf.ratsim.micronsl.FloatArrayPort;
import edu.usf.ratsim.micronsl.IntPort;
import edu.usf.ratsim.micronsl.Module;

public class LastAteIntention extends Module implements Intention {

	public IntPort goalFeeder;
	public float[] intention;

	public LastAteIntention(IntPort goalFeeder, int numIntentions) {
		this.goalFeeder = goalFeeder;
		intention = new float[numIntentions];
		addPort(new FloatArrayPort("intention", intention));
	}

	public void simRun() {
		for (int i = 0; i < intention.length; i++)
			intention[i] = 0;

		// System.out.println(goalFeeder.get());
		if (goalFeeder.get() != -1)
			intention[goalFeeder.get()] = 1;
		if (Debug.printIntention) {
			for (int i = 0; i < intention.length; i++)
				System.out.print(intention[i] + " ");
			System.out.println();
		}

	}

	public void simRun(int inte) {
		for (int i = 0; i < intention.length; i++)
			intention[i] = 0;
		intention[inte] = 1;
	}
}
