package edu.usf.ratsim.experiment.subject.initializer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import org.w3c.dom.Element;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.experiment.task.ActivateFeeders;
import edu.usf.ratsim.support.ElementWrapper;

public class FeederSelector implements SubjectInitializer {

	private static final String STR_NUM_FEEDERS = "numFeeders";
	private Random r;
	private int numFeeders;

	public FeederSelector(ElementWrapper initParams) {
		numFeeders = Integer
				.parseInt(initParams.getChildText(STR_NUM_FEEDERS));
		r = new Random();
	}

	public void initializeSubject(ExpSubject subject) {
		ExperimentUniverse univ = subject.getUniverse();

		Collection<Integer> feeders = new LinkedList<Integer>();
		while (feeders.size() < numFeeders) {
			int i = r.nextInt(univ.getNumFeeders());
			if (!feeders.contains(i))
				feeders.add(i);
		}

		subject.setProperty(ActivateFeeders.STR_ACTIVE_FEEDERS, feeders);
	}

}
