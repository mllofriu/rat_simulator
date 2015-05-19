package edu.usf.ratsim.nsl.modules;

import nslj.src.lang.NslDoutInt1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.utils.Debug;

public class LastTriedToEatGoalDecider extends NslModule {

	public NslDoutInt1 goalFeeder;
	public static int currentGoals[];
	private Subject sub;

	public LastTriedToEatGoalDecider(String nslName, NslModule nslParent, Subject sub) {
		super(nslName, nslParent);

		this.sub = sub;
		
		goalFeeder = new NslDoutInt1(this, "goalFeeder", 2);

		currentGoals = new int[2];
		currentGoals[0] = -1;
		currentGoals[1] = -1;
	}

	public void simRun() {
		if (sub.hasTriedToEat()) {
			currentGoals[1] = currentGoals[0];
			currentGoals[0] = sub.getRobot().getClosestFeeder().getId();
		}

		goalFeeder.set(currentGoals);
		if (Debug.printActiveGoal)
			System.out.println("LastTriedToEat GD: " + currentGoals[0] + " " + currentGoals[1]);
	}

	public void newTrial() {
		currentGoals[0] = -1;
		currentGoals[1] = -1;
		goalFeeder.set(currentGoals);
	}
}
