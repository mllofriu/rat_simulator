package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import edu.usf.ratsim.micronsl.FloatArrayPort;
import edu.usf.ratsim.micronsl.FloatMatrixPort;
import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.nsl.modules.Voter;

public class HalfAndHalfConnectionVotes extends Module implements Voter {

	public float[] actionVote;
	private int numActions;

	public HalfAndHalfConnectionVotes(String name, int numActions) {
		super(name);
		actionVote = new float[numActions + 1];
		addOutPort("votes", new FloatArrayPort(this, actionVote));
	}

	public void simRun() {
		FloatPort states = (FloatPort) getInPort("states");
		FloatMatrixPort value = (FloatMatrixPort) getInPort("value");

		for (int action = 0; action < numActions + 1; action++)
			actionVote[action] = 0f;

		float sumActionSel = 0;
		int cantStates = states.getSize();
		// System.out.println(cantStates);
		for (int state = 0; state < cantStates / 5; state++) {
			float stateVal = states.get(state);
			// Update gradient every some steps
			if (stateVal != 0) {
				sumActionSel += stateVal;
				for (int action = 0; action < numActions; action++) {
					float actionVal = value.get(state, action);
					if (actionVal != 0)
						// action selection contributes only in smaller states
						// (smaller scales
						actionVote[action] = actionVote[action] + stateVal
								* actionVal;
				}
			}
		}

		// Value place
		float sumValue = 0;
		for (int state = cantStates / 2; state < cantStates; state++) {
			float valueVal = value.get(state, numActions);
			float stateVal = states.get(state);
			sumValue += stateVal;
			if (valueVal != 0)
				actionVote[numActions] = actionVote[numActions] + stateVal
						* valueVal;
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
