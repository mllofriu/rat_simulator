package edu.usf.ratsim.experiment.task;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class ActivateRandomFeeder implements ExperimentTask {

	public static final String STR_ACTIVE_FEEDERS = "activeFeeders";

	@Override
	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		Collection<Integer> feeders = (Collection<Integer>) subject
				.getProperty(STR_ACTIVE_FEEDERS);
		
		Random r = new Random();
		int feeder = (new LinkedList<Integer>(feeders)).get(r.nextInt(feeders.size()));
//		feeder = 0;
		univ.setActiveFeeder(feeder, true);
	}

}
