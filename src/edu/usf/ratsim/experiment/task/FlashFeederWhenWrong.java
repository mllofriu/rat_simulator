package edu.usf.ratsim.experiment.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.usf.ratsim.support.ElementWrapper;

public class FlashFeederWhenWrong implements ExperimentTask {

	private int timeout;
	private int stepsSinceLastAte;
	private Random r;

	public FlashFeederWhenWrong(ElementWrapper taskParams) {
		timeout = taskParams.getChildInt("timeout");
		stepsSinceLastAte = 0;
		r = new Random();
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		if (univ.hasRobotAte()) {
			stepsSinceLastAte = 0;
			if (!univ.getFlashingFeeders().isEmpty())
				univ.setFlashingFeeder(univ.getFlashingFeeders().get(0), false);
		} else
			stepsSinceLastAte++;

		if (stepsSinceLastAte > timeout
				&& univ.getFlashingFeeders().isEmpty()) {
			List<Integer> active = univ.getActiveFeeders();
			active = new LinkedList<Integer>(active);
			int feeder = active.get(r.nextInt(active.size()));
			univ.setFlashingFeeder(feeder, true);
		}

	}

}
