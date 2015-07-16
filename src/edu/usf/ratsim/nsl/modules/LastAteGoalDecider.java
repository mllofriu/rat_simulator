package edu.usf.ratsim.nsl.modules;

import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.utils.Debug;
import edu.usf.ratsim.micronsl.IntArrayPort;
import edu.usf.ratsim.micronsl.Module;

public class LastAteGoalDecider extends Module {

	public int[] goalFeeder;
	public static int currentGoal;
	private Subject sub;

	public LastAteGoalDecider(String name, Subject sub) {
		super(name);
		this.sub = sub;

		goalFeeder = new int[1];
		addOutPort("goalFeeder", new IntArrayPort(this, goalFeeder));

		currentGoal = -1;
	}

	public void simRun() {
		if (sub.hasEaten()) {
			currentGoal = sub.getRobot().getClosestFeeder().getId();
		}

		goalFeeder[0] = currentGoal;
		if (Debug.printActiveGoal)
			System.out.println("Last Ate GD: " + currentGoal);
	}

	public void newTrial() {
		currentGoal = -1;
		goalFeeder[0] = currentGoal;
	}
}
