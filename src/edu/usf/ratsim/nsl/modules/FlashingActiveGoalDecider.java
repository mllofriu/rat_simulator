package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;

public class FlashingActiveGoalDecider extends NslModule {

	private ExperimentUniverse universe;
	public NslDoutInt0 goalFeeder;
	private int currentGoal;
	private Random r;

	public FlashingActiveGoalDecider(String nslName, NslModule nslParent,
			ExperimentUniverse univ) {
		super(nslName, nslParent);

		this.universe = univ;
		goalFeeder = new NslDoutInt0(this, "goalFeeder");

		// Initialize a goal 
		currentGoal = -1;
		List<Integer> active = universe.getActiveFeeders();
		// Dont pick the same goal twice
		active.remove(new Integer(currentGoal));
		if (!active.isEmpty()) {
			currentGoal = active.get(r.nextInt(active.size()));
		}

		r = new Random();
	}

	public void simRun() {
		if (currentGoal == -1)
			currentGoal = universe.getActiveFeeders().get(
					r.nextInt(universe.getActiveFeeders().size()));

		if (!universe.getFlashingFeeders().isEmpty()) {
			currentGoal = universe.getFlashingFeeders().get(0);
		} else {
			if (universe.hasRobotFoundFood()) {
				List<Integer> active = universe.getActiveFeeders();
				// Dont pick the same goal twice
				active.remove(new Integer(currentGoal));
				if (!active.isEmpty()) {
					currentGoal = active.get(r.nextInt(active.size()));
				}
			}
		}

		goalFeeder.set(currentGoal);

		System.out.println("Active GD: " +currentGoal);
	}

	public void newTrial() {
		currentGoal = -1;
	}
}
