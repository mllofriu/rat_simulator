package nsl.modules;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import support.Configuration;
import nslj.src.lang.NslDinBoolean1;
import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslModule;

import com.sun.tools.javac.util.Pair;

import experiment.ExperimentUniverse;

public class QLearning extends NslModule {

	public NslDinBoolean1 states;
	public NslDinInt0 actionTaken;

	// The value function that maps between a pair of <state,action> to a float
	// specifying its value
	private AbstractMap<Pair<Integer, Integer>, Float> value;
	// A linked list used to store the states visited between reward episodes
	private List<Integer> visitedStates;
	// A list of the actions taken by the subject
	private List<Integer> actionsTaken;

	private ExperimentUniverse universe;

	public QLearning(String nslMain, NslModule nslParent, int stateSize,
			ExperimentUniverse universe) {
		super(nslMain, nslParent);

		this.universe = universe;

		states = new NslDinBoolean1(this, "states", stateSize);
		actionTaken = new NslDinInt0(this, "actionTaken");

		value = new HashMap<Pair<Integer, Integer>, Float>();

		visitedStates = new LinkedList<Integer>();

		actionsTaken = new LinkedList<Integer>();
	}

	public void simRun() {
		// If the robot has not found food, just save the state info
		if (!universe.hasRobotFoundFood()) {
			// Look for the active state
			int i = 0;
			while (i < states.getSize() && states.get(i))
				i++;
			if (i < states.getSize()) {
				// Add at the head (for reverse q-learning)
				visitedStates.add(0, i);
				// Save last action
				actionsTaken.add(0, actionTaken.get());
			}
		} else {
			float reward = Configuration.getFloat("QLearning.foodReward");
			float discountFactor = Configuration
					.getFloat("QLearning.discountFactor");
			for (int i = 0; i < visitedStates.size(); i++) {
				Integer state = visitedStates.get(i);
				Integer action = actionsTaken.get(i);
				Pair<Integer, Integer> stateAction = new Pair<Integer, Integer>(
						state, action);

				float currentValue;
				if (value.containsKey(stateAction))
					currentValue = value.get(stateAction);
				else
					currentValue = 0;

				float newValue = currentValue + reward;

				value.put(stateAction, newValue);

				// Discount reward
				reward *= discountFactor;
			}

			// Empty record of visited states and actions taken
			visitedStates.clear();
			actionsTaken.clear();
		}

	}
}
