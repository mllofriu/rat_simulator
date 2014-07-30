package edu.usf.ratsim.experiment.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class FlashFeederWhenWrong implements ExperimentTask {

	private static final int STEPS_THRS = 100;
	private int stepsSinceLastAte;
	private Random r;

	public FlashFeederWhenWrong() {
		stepsSinceLastAte = 0;
		r = new Random();
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		// // If the robot tried to eat from an incorrect feeder
		// if (univ.hasRobotTriedToEat()
		// && (univ.getFeedingFeeder() == -1)
		// && univ.getFlashingFeeders().isEmpty()) {
		// // List<Integer> active = (List<Integer>) subject
		// // .getProperty(ActivateFeeders.STR_ACTIVE_FEEDERS);
		// List<Integer> active = univ.getActiveFeeders();
		// active = new LinkedList<Integer>(active);
		// int feeder = active.get(r.nextInt(active.size()));
		// univ.setFlashingFeeder(feeder, true);
		// univ.setActiveFeeder(feeder, true);
		// } else if (!univ.getFlashingFeeders().isEmpty()
		// && univ.hasRobotFoundFeeder(univ.getFlashingFeeders().get(0))
		// && univ.hasRobotAte()) {
		// int flashingFeeder = univ.getFlashingFeeders().get(0);
		// univ.setFlashingFeeder(flashingFeeder, false);
		// // univ.setActiveFeeder(flashingFeeder, false);
		// // List<Integer> selectedFeeders = (List<Integer>) subject
		// // .getProperty(ActivateFeeders.STR_ACTIVE_FEEDERS);
		// // // Copy to avoid modifying the property holder
		// // selectedFeeders = new LinkedList<Integer>(selectedFeeders);
		// // if (selectedFeeders.contains(flashingFeeder))
		// // selectedFeeders.remove(new Integer(flashingFeeder));
		// // int feeder =
		// // selectedFeeders.get(r.nextInt(selectedFeeders.size()));
		// // univ.setFlashingFeeder(feeder, true);
		// // univ.setActiveFeeder(feeder, true);
		// }
		if (univ.hasRobotAte()) {
			stepsSinceLastAte = 0;
			if (!univ.getFlashingFeeders().isEmpty())
				univ.setFlashingFeeder(univ.getFlashingFeeders().get(0), false);
		} else
			stepsSinceLastAte++;

		if (stepsSinceLastAte > STEPS_THRS
				&& univ.getFlashingFeeders().isEmpty()) {
			List<Integer> active = univ.getActiveFeeders();
			active = new LinkedList<Integer>(active);
			int feeder = active.get(r.nextInt(active.size()));
			univ.setFlashingFeeder(feeder, true);
		}

	}

}
