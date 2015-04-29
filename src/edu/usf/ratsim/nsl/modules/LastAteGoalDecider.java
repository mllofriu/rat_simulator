package edu.usf.ratsim.nsl.modules;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.utils.Debug;

public class LastAteGoalDecider extends NslModule {

	public NslDoutInt0 goalFeeder;
	public static int currentGoal;
	private Subject sub;

	public LastAteGoalDecider(String nslName, NslModule nslParent, Subject sub) {
		super(nslName, nslParent);

		this.sub = sub;
		
		goalFeeder = new NslDoutInt0(this, "goalFeeder");

		currentGoal = -1;
	}

	public void simRun() {
		if (sub.hasEaten()) {
			currentGoal = sub.getRobot().getClosestFeeder().getId();
		}

		goalFeeder.set(currentGoal);
		if (Debug.printActiveGoal)
			System.out.println("Last Ate GD: " + currentGoal);
	}

	public void newTrial() {
		currentGoal = -1;
		goalFeeder.set(currentGoal);
	}
}
