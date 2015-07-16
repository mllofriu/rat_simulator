package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import edu.usf.ratsim.micronsl.FloatArrayPort;
import edu.usf.ratsim.micronsl.FloatMatrixPort;
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

	public ProportionalVotes(String name) {
		super(name);
		actionVote = new float[numActions];
		addOutPort("votes", new FloatArrayPort(this, actionVote));
	}

	public void simRun() {
		FloatArrayPort states = (FloatArrayPort) getInPort("states");
		FloatMatrixPort value = (FloatMatrixPort) getOutPort("value");
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
