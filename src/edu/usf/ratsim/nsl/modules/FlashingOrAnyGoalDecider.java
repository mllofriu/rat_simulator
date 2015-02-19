package edu.usf.ratsim.nsl.modules;

import java.util.Random;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.experiment.subject.Subject;
import edu.usf.ratsim.support.Debug;

/**
 * Sets the goal to be the flashing feeder (if any) or any feeder if there is
 * none flashing. Does not use the knowledge of which feeders are active.
 * 
 * @author ludo
 * 
 */
public class FlashingOrAnyGoalDecider extends NslModule {

	public NslDoutInt0 goalFeeder;
	// Keep the goal as a static variable to be able to pass among iterations
	public static int currentGoal;
	private Random r;
	private Subject subject;
	private int numIntentions;
	private static int lastFeeder;

	public FlashingOrAnyGoalDecider(String nslName, NslModule nslParent,
			Subject subject, int numIntentions) {
		super(nslName, nslParent);

		this.subject = subject;
		this.numIntentions = numIntentions;

		goalFeeder = new NslDoutInt0(this, "goalFeeder");

		r = new Random();

		// Initialize a goal
		currentGoal = -1;
		// List<Integer> feeders = universe.getFeeders();
		// currentGoal = feeders.get(r.nextInt(feeders.size()));

		lastFeeder = -1;
	}

	public void simRun() {
		if (currentGoal == -1) {
			// currentGoal = 0;
			currentGoal = r.nextInt(numIntentions);
			// System.out.println("Goal in -1 for " + nslGetName());
		}

		// TODO: why do we need the second term?
		if (subject.hasEaten() || subject.hasTriedToEat()) {
			lastFeeder = currentGoal;
			currentGoal = subject.getRobot().getClosestFeeder(lastFeeder);
		}

		if (subject.getRobot().seesFlashingFeeder()) {
			currentGoal = subject.getRobot().getFlashingFeeder().id;
		}

		goalFeeder.set(currentGoal);

		if (Debug.printAnyGoal)
			System.out.println("Any GD: " + currentGoal + " "
					+ goalFeeder.get());
	}

	public void newTrial() {
		currentGoal = -1;
	}
}
