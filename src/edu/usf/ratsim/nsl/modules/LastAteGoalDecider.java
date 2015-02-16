package edu.usf.ratsim.nsl.modules;

import java.util.Random;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.support.Debug;

public class LastAteGoalDecider extends NslModule {

	private ExperimentUniverse universe;
	public NslDoutInt0 goalFeeder;
	public static int currentGoal;
	private Random r;

	public LastAteGoalDecider(String nslName, NslModule nslParent,
			ExperimentUniverse univ) {
		super(nslName, nslParent);

		this.universe = univ;
		goalFeeder = new NslDoutInt0(this, "goalFeeder");

		r = new Random();
		// Initialize a goal
		// List<Integer> active = universe.getActiveFeeders();
		// active.remove(new Integer(universe.getFlashingFeeders().get(0)));
		// if (!active.isEmpty()) {
		// currentGoal = active.get(r.nextInt(active.size()));
		// goalFeeder.set(currentGoal);
		// // currentGoal = 0;
		// // goalFeeder.set(currentGoal);
		// }
		currentGoal = -1;
	}

	public void simRun() {
		// System.out.println("Got goal: " + goalFeeder.get());
		// if (currentGoal == -1) {
		// List<Integer> active = universe.getActiveFeeders();
		// // currentGoal = active.get(r.nextInt(active.size()));
		// currentGoal = 0; // Hack to avoid too much comunication between
		// universe and rat
		// // First round of learning is "discarded"
		// // Problems may arise if first goal is 0
		// universe.clearWantedFeeders();
		// // universe.setWantedFeeder(currentGoal, true);
		// } else
		if (universe.hasRobotAte() /* || universe.hasRobotTriedToEat() */) {
			// List<Integer> active = universe.getActiveFeeders();
			// // Dont pick the same goal twice
			// // active.remove(new Integer(goalFeeder.get()));
			// // Instead of removing the desired feeder, remove the one
			// actually found
			// active.remove(new Integer(universe.getFeedingFeeder()));
			// if (!active.isEmpty()) {
			// goalFeeder.set(active.get(r.nextInt(active.size())));
			// }
			currentGoal = universe.getFeedingFeeder();

			universe.clearWantedFeeders();

		}

		// }

		if (currentGoal != -1)
			universe.setWantedFeeder(currentGoal, true);

		goalFeeder.set(currentGoal);
		if (Debug.printActiveGoal)
			System.out.println("Active GD: " + goalFeeder.get() + " "
					+ currentGoal);
	}

	public void newTrial() {
		currentGoal = -1;
		goalFeeder.set(currentGoal);
	}
}
