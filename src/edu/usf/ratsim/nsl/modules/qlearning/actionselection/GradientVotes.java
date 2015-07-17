package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.FloatMatrixPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.nsl.modules.Voter;

public class GradientVotes extends Module implements Voter {

	public float[] actionVote;
	private int numActions;
	private FloatMatrixPort value;
	private Float1dPortArray states;

	public GradientVotes(String name, Float1dPortArray states,
			FloatMatrixPort value, int numActions) {
		super(name);

		actionVote = new float[numActions + 1];
		addOutPort("votes", new Float1dPortArray(this, actionVote));

		this.states = states;
		this.value = value;
		this.numActions = numActions;
	}

	public void run() {
		for (int action = 0; action < numActions + 1; action++)
			actionVote[action] = 0f;

		float sumActionSel = 0;
		float sumValue = 0;
		float cantStates = states.getSize();
		// System.out.println(cantStates);
		float gradient = .5f;
		for (int state = 0; state < cantStates; state++) {
			float stateVal = states.get(state);
			// Update gradient every some steps
			if (state % 10000 == 0)
				gradient = (float) (1f / (Math.exp(-10
						* (state / cantStates - .5) + 1)));
			if (stateVal != 0) {
				sumActionSel += stateVal * (1 - gradient);
				sumValue += stateVal * (gradient);
				for (int action = 0; action < numActions; action++) {
					float actionVal = value.get(state, action);
					if (actionVal != 0)
						// action selection contributes only in smaller states
						// (smaller scales
						actionVote[action] = actionVote[action] + stateVal
								* actionVal * (1 - gradient);
				}

				// Value place
				float actionVal = value.get(state, numActions);
				if (actionVal != 0)
					actionVote[numActions] = actionVote[numActions] + stateVal
							* actionVal * (gradient);
			}
		}

		// Normalize
		if (sumActionSel != 0)
			for (int action = 0; action < numActions; action++)
				// Normalize with real value and revert previous normalization
				actionVote[action] = actionVote[action] / sumActionSel;
		if (sumValue != 0)
			actionVote[numActions] = actionVote[numActions] / sumValue;

		// for (int action = 0; action < numActions; action++)
		// if (values[action] != 0)
		// System.out.println("value action " + values[action]);

	}

	public float[] getVotes() {
		return actionVote;
	}

}
