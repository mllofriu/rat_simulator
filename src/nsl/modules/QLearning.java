package nsl.modules;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Quat4f;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import robot.IRobot;
import support.Configuration;
import support.Utiles;
import experiment.ExperimentUniverse;

public class QLearning extends NslModule {

	private static final Float INITIAL_VALUE = 0f;
	private static final float EXPLORE_THRESHOLD = 10;
	private static final float FOOD_REWARD = Configuration.getFloat("QLearning.foodReward");
	public NslDinFloat1 states;
	public NslDoutInt0 actionVote;

	// The value function that maps between a pair of <state,action> to a float
	// specifying its value
	private AbstractMap<StateAction, Float> value;
	// A linked list used to store the states visited between reward episodes
	private List<Integer> visitedStates;
	// A list of the actions taken by the subject
	private List<Integer> actionsTaken;

	private ExperimentUniverse universe;
	private IRobot robot;
	private Random random;

	public QLearning(String nslMain, NslModule nslParent, int stateSize,
			IRobot robot, ExperimentUniverse universe) {
		super(nslMain, nslParent);

		this.universe = universe;
		this.robot = robot;

		states = new NslDinFloat1(this, "states", stateSize);
		actionVote = new NslDoutInt0(this, "vote");

		visitedStates = new LinkedList<Integer>();
		actionsTaken = new LinkedList<Integer>();

		value = new HashMap<StateAction, Float>(states.getSize()
				* Utiles.discreteAngles.length);
		for (int s = 0; s < stateSize; s++)
			for (int a = 0; a < Utiles.discreteAngles.length; a++)
				value.put(new StateAction(s, a), INITIAL_VALUE);

		random = new Random();
//		System.out.println(value.get(new StateAction(0, 0)));
//		StateAction s = new StateAction(0, 0);
//		value.put(s, 0f);
//		System.out.println(value.get(new StateAction(0, 0)));
	}

	public void simRun() {
		int s = getActiveState();

		// There may not be any state
		saveStateAction(s);
		updateQValue();

		int nextAction = getNextStep(s);
		actionVote.set(nextAction);
	}

	private void saveStateAction(int s) {
		// Add at the head (for reverse q-learning)
		visitedStates.add(0, s);
		// The last action is the current allothetic heading
		actionsTaken.add(0, Utiles.discretizeAngle(universe.getRobotOrientationAngle()));
	}

	private int getNextStep(int s) {
		// Store values in array
		float[] vals = new float[Utiles.discreteAngles.length];
		float totalVal = 0;
		float maxVal = 0;
		for (int a = 0; a < Utiles.discreteAngles.length; a++) {
			vals[a] = value.get(new StateAction(s, a));
			totalVal += vals[a];
			if (vals[a] > maxVal)
				maxVal = vals[a];
		}
		
		int action;		
		// Explore with probability 1 - maxExpectedVal / maxPossibleVal
		if (random.nextFloat() > maxVal / FOOD_REWARD ){
			action = -1;
		} else {
			// Randomly assign an angle proportional to its expected reward
			float r = random.nextFloat() * totalVal;
			float acc = 0;
			int nextAngle = -1;
			do {
				nextAngle++;
				acc += vals[nextAngle];
			} while (acc < r);
			
		
			// Get angle to that maximal direction
			Quat4f nextRot = Utiles.angleToRot(Utiles.discreteAngles[nextAngle]);
	
			// Get the action that better approximates that angle
			boolean[] affordances = robot.affordances();
			action = Utiles.bestActionToRot(nextRot,
					universe.getRobotOrientation(), affordances);
		}

		return action;
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

	private void updateQValue() {
		if (universe.hasRobotFoundFood()) {
			float reward = FOOD_REWARD;
			float discountFactor = Configuration
					.getFloat("QLearning.discountFactor");
			float alpha = Configuration.getFloat("QLearning.learningRate");

			int nextState = visitedStates.get(0);
			int actionTaken = actionsTaken.get(0);
			for (int i = 1; i < visitedStates.size(); i++) {
				Integer state = visitedStates.get(i);
				// This state took the action sensed in the following state (use
				// actionTaken)
				StateAction stateAction = new StateAction(state, actionTaken);

				float currentValue = value.get(stateAction);

				float maxNextState = 0;
				// Maximize posible outcome of next state
				for (int a = 0; a < Utiles.discreteAngles.length; a++) {
					float aVal = value.get(new StateAction(
							nextState, a));
					if (aVal > maxNextState) {
						maxNextState = aVal;
					}
				}
				// Compute new value
				float newValue = currentValue
						+ alpha
						* (reward + discountFactor * maxNextState - currentValue);

//				System.out.println(newValue);
				value.put(stateAction, newValue);

				// This state is the state the next one arrived to (reverse
				// order)
				nextState = state;
				// The action taken to arrive to this state is saved in this
				// pair
				actionTaken = actionsTaken.get(i);
				
				// Following state-action have no reward
				reward = 0;
			}

			// Empty record of visited states and actions taken
			visitedStates.clear();
			actionsTaken.clear();
		}
	}
}

final class StateAction {
	private int state;
	private int action;

	public int getState() {
		return state;
	}

	public int getAction() {
		return action;
	}

	public StateAction(int state, int action) {
		this.state = state;
		this.action = action;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof StateAction))
			return false;
		
		StateAction stateAction = (StateAction)o;
		
		return stateAction.state == state && stateAction.action == action;
	}

	@Override
	public int hashCode() {
		return state * Utiles.discreteAngles.length + action;
	}
	
}