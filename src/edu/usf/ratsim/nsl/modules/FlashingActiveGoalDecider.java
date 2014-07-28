package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;

public class FlashingActiveGoalDecider extends NslModule {

	private ExperimentUniverse universe;
	public NslDoutInt0 goalFeeder;
	private Random r;

	public FlashingActiveGoalDecider(String nslName, NslModule nslParent,
			ExperimentUniverse univ) {
		super(nslName, nslParent);

		this.universe = univ;
		goalFeeder = new NslDoutInt0(this, "goalFeeder");

		r = new Random();
		// Initialize a goal
		List<Integer> active = universe.getActiveFeeders();
		// Dont pick the same goal twice
		if (!active.isEmpty()) {
			goalFeeder.set(active.get(r.nextInt(active.size())));
		}

	}

	public void simRun() {
//		System.out.println("Got goal: " + goalFeeder.get());
		if (goalFeeder.get() == -1)
			goalFeeder.set(universe.getActiveFeeders().get(
					r.nextInt(universe.getActiveFeeders().size())));

		if (!universe.getFlashingFeeders().isEmpty()) {
			goalFeeder.set(universe.getFlashingFeeders().get(0));
		} else {
			if (universe.hasRobotAte() || universe.hasRobotTriedToEat()) {
				List<Integer> active = universe.getActiveFeeders();
				// Dont pick the same goal twice
//				active.remove(new Integer(goalFeeder.get()));
				// Instead of removing the desired feeder, remove the one actually found
				active.remove(new Integer(universe.getFeedingFeeder()));
				if (!active.isEmpty()) {
					goalFeeder.set(active.get(r.nextInt(active.size())));
				}
			}
		}

		universe.clearWantedFeeders();
		universe.setWantedFeeder(goalFeeder.get(), true);
//		 System.out.println("Active GD: " + goalFeeder.get());
	}

	public void newTrial() {
		goalFeeder.set(-1);
	}
}
