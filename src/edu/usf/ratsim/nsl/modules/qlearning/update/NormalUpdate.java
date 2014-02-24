package edu.usf.ratsim.nsl.modules.qlearning.update;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.nsl.modules.qlearning.StateActionReward;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;

public class NormalUpdate extends NslModule {
	
	private static final float FOOD_REWARD = Configuration
			.getFloat("QLearning.foodReward");

	private static final float NON_FOOD_REWARD = Configuration
			.getFloat("QLearning.nonFoodReward");
	
	public NslDinFloat1 states;

	private ExperimentUniverse universe;

	private QLSupport qlData;

	private float alpha;

	private float discountFactor;

	public NormalUpdate(String nslMain, NslModule nslParent, int stateSize,
			QLSupport qlData, IRobot robot, ExperimentUniverse universe) {
		super(nslMain, nslParent);

		this.universe = universe;
		this.qlData = qlData;

		discountFactor = Configuration.getFloat("QLearning.discountFactor");
		alpha = Configuration.getFloat("QLearning.learningRate");
		
		states = new NslDinFloat1(this, "states", stateSize);
	}

	public void simRun() {
		// Gets the active state as computed at the beginning of the cycle
		int s = getActiveState();
		
		saveStateAction(s);
		
		updateLastAction();
	}

	private void updateLastAction() {
		if (qlData.numVisitedSA() >= 2) {
			// Pick the last state action, which is at position 0
			StateActionReward last = qlData.getVisitedSA(0);
			StateActionReward beforeLast = qlData.getVisitedSA(1);

			int action = qlData.getMaxAngle(last.getState());
			float maxNextState;
			if (action != -1)
				 maxNextState = qlData.getValue(new StateActionReward(last
						.getState(), action));
			else 
				maxNextState = 0;
			
			float reward = last.getReward();
			
			float value = qlData.getValue(beforeLast);
			float newValue = value + alpha
					* (reward + discountFactor * maxNextState - value);

			qlData.setValue(beforeLast, newValue);

			qlData.popLastRecord();
		}
	}

	
	private int getActiveState() {
		// Winner take all within the layer
		float maxVal = 0;
		int activeState = -1;
		for (int i = 0; i < states.getSize(); i++)
			if (states.get(i) > maxVal) {
				activeState = i;
				maxVal = states.get(i);
			}

		return activeState;
	}

	private void saveStateAction(int s) {
		float reward;
		// Save the current state and the past action
		if (universe.hasRobotFoundFood())
			reward = FOOD_REWARD;
		else 
			reward = NON_FOOD_REWARD;
		
		qlData.recordStateAction(new StateActionReward(s, Utiles
				.discretizeAngle(universe.getRobotOrientationAngle()), reward));
	}
}