package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;

/**
 * Sets the goal to be the flashing feeder (if any) or any feeder if there is none flashing.
 * Does not use the knowledge of which feeders are active.
 * @author ludo
 *
 */
public class FlashingOrAnyGoalDecider extends NslModule {

	private ExperimentUniverse universe;
	public NslDoutInt0 goalFeeder;
	// Keep the goal as a static variable to be able to pass among iterations
	public static int currentGoal;
	private Random r;
	private static int lastFeeder;
	

	public FlashingOrAnyGoalDecider(String nslName, NslModule nslParent,
			ExperimentUniverse univ) {
		super(nslName, nslParent);
		
		this.universe = univ;
		goalFeeder = new NslDoutInt0(this, "goalFeeder");

		r = new Random();
		
		// Initialize a goal 
		currentGoal = -1;
//		List<Integer> feeders = universe.getFeeders();
//		currentGoal = feeders.get(r.nextInt(feeders.size()));

		lastFeeder = -1;
	}

	public void simRun() {
		if (currentGoal == -1){
//			currentGoal = 0;
			currentGoal = universe.getFeeders().get(
					r.nextInt(universe.getFeeders().size()));
//			System.out.println("Goal in -1 for " + nslGetName());
		}

		if (!universe.getFlashingFeeders().isEmpty()) {
			currentGoal = universe.getFlashingFeeders().get(0);
		} else {
			if (universe.hasRobotAte() || universe.hasRobotTriedToEat()) {
				lastFeeder = currentGoal;
				
////			System.out.println("Changin goal in "  + nslGetName());
//			List<Integer> feeders = universe.getFeeders();
//			// Dont pick the same goal twice
//			feeders.remove(new Integer(currentGoal));
//			if (!feeders.isEmpty()) {
//				currentGoal = feeders.get(r.nextInt(feeders.size()));
//			}
			}
			
			currentGoal = universe.getFeedingInFrontOfRobot(lastFeeder);
			
		}

		goalFeeder.set(currentGoal);
		System.out.println("Any GD: " + currentGoal + " " + goalFeeder.get());
	}

	public void newTrial() {
		currentGoal = -1;
	}
}
