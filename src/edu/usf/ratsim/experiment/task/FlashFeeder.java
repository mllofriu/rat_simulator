package edu.usf.ratsim.experiment.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class FlashFeeder implements ExperimentTask {

	private Random r;
	private int lastFeeder;

	public FlashFeeder() {
		r = new Random();
		lastFeeder = -1;
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		if (univ.getFlashingFeeders().isEmpty()) {
			List<Integer> active = (List<Integer>) subject
					.getProperty(ActivateFeeders.STR_ACTIVE_FEEDERS);
			// Copy to avoid modifying the property holder
			active = new LinkedList<Integer>(active);
			if (active.contains(lastFeeder))
				active.remove(new Integer(lastFeeder));
			lastFeeder = active.get(r.nextInt(active.size()));
			univ.setFlashingFeeder(lastFeeder, true);
			univ.setActiveFeeder(lastFeeder, true);
		}
	}

}
