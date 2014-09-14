package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.support.Debug;

/***
 * Sets the goal feeder to be one random from the active ones
 * Selection occurs only once and should be reseted after each episode
 * @author ludo
 *
 */
public class ActiveGoalDecider extends NslModule {

	private ExperimentUniverse universe;
	public NslDoutInt0 goalFeeder;
	public static int currentGoal;
	private Random r;

	public ActiveGoalDecider(String nslName, NslModule nslParent,
			ExperimentUniverse univ) {
		super(nslName, nslParent);

		this.universe = univ;
		goalFeeder = new NslDoutInt0(this, "goalFeeder");

		r = new Random();
		currentGoal = -1;
	}

	public void simRun() {
		 if (currentGoal == -1) {
			 List<Integer> active = universe.getActiveFeeders();
			 currentGoal = active.get(r.nextInt(active.size()));
		 } 
	

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
		universe.clearWantedFeeders();
	}
}
