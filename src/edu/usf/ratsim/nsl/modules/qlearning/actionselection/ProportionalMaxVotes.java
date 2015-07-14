package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import edu.usf.ratsim.micronsl.FloatArrayPort;
import edu.usf.ratsim.micronsl.Module;

public class ProportionalMaxVotes extends Module {

	public float[] actionVote;
	private int numActions;
	private FloatArrayPort states;
	private FloatMatrixPort value;

	public ProportionalMaxVotes(FloatArrayPort states, FloatMatrixPort value) {
		actionVote = new float[numActions];
		addPort(new FloatArrayPort("votes", actionVote));

		this.states = states;
		this.value = value;
	}

	public void simRun() {
		for (int i = 0; i < actionVote.length; i++)
			actionVote[i] = 0;

		// Find the best value for each action, taking into acount state
		// activation
		for (int action = 0; action < numActions; action++) {
			float bestActionValue = Float.NEGATIVE_INFINITY;
			for (int state = 0; state < states.getSize(); state++)
				if (states.get(state) * value.get(state, action) > bestActionValue) {
					bestActionValue = states.get(state)
							* value.get(state, action);
				}
			actionVote[action] = bestActionValue;
		}
	}

}
