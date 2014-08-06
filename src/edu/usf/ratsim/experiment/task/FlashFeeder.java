package edu.usf.ratsim.experiment.task;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class FlashFeeder implements ExperimentTask {

	private Random r;

	public FlashFeeder() {
		r = new Random();
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		if (univ.getFlashingFeeders().isEmpty()) {
			List<Integer> active = (List<Integer>) subject
					.getProperty(ActivateFeeders.STR_ACTIVE_FEEDERS);
			active = new LinkedList<Integer>(active);
//			int feeder = active.get(r.nextInt(active.size()));
			// Pick the greatest to avoid problems with ActiveGoal/Intention initialization
			Collections.sort(active);
			int feeder = active.get(active.size()-1);
			
			univ.setFlashingFeeder(feeder, true);
			univ.setActiveFeeder(feeder, true);
		} else if (!univ.getFlashingFeeders().isEmpty()
				&& univ.hasRobotFoundFeeder(univ.getFlashingFeeders().get(0))
				&& univ.hasRobotAte()) {
			int flashingFeeder = univ.getFlashingFeeders().get(0);
			univ.setFlashingFeeder(flashingFeeder, false);
			univ.setActiveFeeder(flashingFeeder, false);
			List<Integer> selectedFeeders = (List<Integer>) subject
					.getProperty(ActivateFeeders.STR_ACTIVE_FEEDERS);
			// Copy to avoid modifying the property holder
			selectedFeeders = new LinkedList<Integer>(selectedFeeders);
			if (selectedFeeders.contains(flashingFeeder))
				selectedFeeders.remove(new Integer(flashingFeeder));
			int feeder = selectedFeeders.get(r.nextInt(selectedFeeders.size()));
			univ.setFlashingFeeder(feeder, true);
			univ.setActiveFeeder(feeder, true);
		}
	}

}
