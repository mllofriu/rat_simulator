package edu.usf.ratsim.nsl.modules.qlearning;

import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;

public class QLUpdateValue extends NslModule {

	private static final float FOOD_REWARD = Configuration
			.getFloat("QLearning.foodReward");

	private static final float NON_FOOD_REWARD = Configuration
			.getFloat("QLearning.nonFoodReward");

	private ExperimentUniverse universe;

	private QLSupport qlData;

	public QLUpdateValue(String nslMain, NslModule nslParent, int stateSize,
			QLSupport qlData, IRobot robot, ExperimentUniverse universe) {
		super(nslMain, nslParent);

		this.universe = universe;
		this.qlData = qlData;
	}

	public void simRun() {
		// Do not do anything in simrun, as it should be updated only at the end
		// of the trial
		// updateQValue();
	}

	public void updateQValue() {
		float reward;
		// If has found food, start with food reward
		if (universe.hasRobotFoundFood())
			reward = FOOD_REWARD;
		else
			reward = NON_FOOD_REWARD;
		float discountFactor = Configuration
				.getFloat("QLearning.discountFactor");
		float alpha = Configuration.getFloat("QLearning.learningRate");

		// The current heading referst to the last taken action
		int currHeading = Utiles.discretizeAngle(universe
				.getRobotOrientationAngle());
		// The state is never going to be used
		StateAction nextSA = new StateAction(0, currHeading);
		// Keep the max of the next state to pass on the next iter
		float maxNextState = 0;
		for (int i = 0; i < qlData.numVisitedSA(); i++) {
			StateAction previusSA = qlData.getVisitedSA(i);

			StateAction prevStateNextAction = new StateAction(
					previusSA.getState(), nextSA.getAction());
			// The value to update corresponds to the state recorded in
			// previous iteration and the action recorded in the following
			float value = qlData.getValue(prevStateNextAction);

			// Compute new value
			float newValue = value + alpha
					* (reward + discountFactor * maxNextState - value);

			// System.out.println(newValue);
			qlData.setValue(prevStateNextAction, newValue);

			maxNextState = 0;
			// Maximize posible outcome of this state and save it for next
			// iter
			for (int a = 0; a < Utiles.discreteAngles.length; a++) {
				float aVal = qlData.getValue(new StateAction(
						prevStateNextAction.getState(), a));
				if (aVal > maxNextState) {
					maxNextState = aVal;
				}
			}

			// This state is the state the next one arrived to (reverse
			// order)
			nextSA = previusSA;

			// Following state-actions have non-food rewards (only one has found food state)
			reward = NON_FOOD_REWARD;
		}

		qlData.clearRecord();
	}

}