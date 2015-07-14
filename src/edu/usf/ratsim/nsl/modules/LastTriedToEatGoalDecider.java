package edu.usf.ratsim.nsl.modules;

import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.utils.Debug;
import edu.usf.ratsim.micronsl.IntArrayPort;
import edu.usf.ratsim.micronsl.Module;

public class LastTriedToEatGoalDecider extends Module {

	public int[] goalFeeder;
	private Subject sub;

	public LastTriedToEatGoalDecider(Subject sub) {
		this.sub = sub;

		goalFeeder = new int[2];
		addPort(new IntArrayPort("goalFeeder", goalFeeder));

		goalFeeder[0] = -1;
		goalFeeder[1] = -1;
	}

	public void simRun() {
		if (sub.hasTriedToEat()) {
			goalFeeder[1] = goalFeeder[0];
			goalFeeder[0] = sub.getRobot().getClosestFeeder().getId();
		}

		if (Debug.printActiveGoal)
			System.out.println("LastTriedToEat GD: " + goalFeeder[0] + " "
					+ goalFeeder[1]);
	}

	public void newTrial() {
		goalFeeder[0] = -1;
		goalFeeder[1] = -1;
	}
}
