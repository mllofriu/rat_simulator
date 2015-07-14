package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import edu.usf.ratsim.micronsl.FloatArrayPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.nsl.modules.Voter;

/**
 * Class to set the votes for actions depending both in the state activation and
 * a value function.
 * 
 * @author ludo
 *
 */
public class ProportionalVotes extends Module implements Voter {

	public float[] actionVote;
	private int numActions;
	private FloatArrayPort states;
	private FloatMatrixPort value;

	public ProportionalVotes(FloatArrayPort states, FloatMatrixPort value) {
		actionVote = new float[numActions];
		addPort(new FloatArrayPort("votes", actionVote));

		this.states = states;
		this.value = value;
	}

	public void simRun() {
		for (int action = 0; action < numActions; action++)
			actionVote[action] = 0f;

		double sum = 0;
		float cantStates = states.getSize();
		for (int state = 0; state < cantStates; state++) {
			float stateVal = states.get(state);
			if (stateVal != 0) {
				sum += stateVal;
				for (int action = 0; action < numActions; action++) {
					float actionVal = value.get(state, action);
					if (actionVal != 0)
						actionVote[action] = actionVote[action] + stateVal
								* actionVal;
				}
			}
		}

		// Normalize
		if (sum != 0)
			for (int action = 0; action < numActions; action++)
				// Normalize with real value and revert previous normalization
				actionVote[action] = (float) (actionVote[action] / sum);

		// for (int action = 0; action < numActions; action++)
		// if (values[action] != 0)
		// System.out.println("value action " + values[action]);

	}

	@Override
	public float[] getVotes() {
		return actionVote;
	}

}
