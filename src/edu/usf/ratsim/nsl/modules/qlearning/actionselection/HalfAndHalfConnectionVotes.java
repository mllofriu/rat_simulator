package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinFloat2;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.nsl.modules.Voter;

public class HalfAndHalfConnectionVotes extends NslModule implements Voter {

	public NslDoutFloat1 actionVote;
	public NslDinFloat1 states;
	public NslDinFloat2 value;
	private int numActions;

	public HalfAndHalfConnectionVotes(String nslName, NslModule nslParent,
			int numStates, int numActions) {
		super(nslName, nslParent);

		actionVote = new NslDoutFloat1(this, "votes", numActions + 1);
		states = new NslDinFloat1(this, "states", numStates);
		value = new NslDinFloat2(this, "value", numStates, numActions + 1);

		this.numActions = numActions;
	}

	public void simRun() {
		float[] values = new float[numActions + 1];
		for (int action = 0; action < numActions + 1; action++)
			values[action] = 0f;

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
						values[action] = values[action] + stateVal * actionVal;
				}
			}
		}

		// Value place
		float sumValue = 0;
		for (int state = cantStates / 2; state < cantStates ; state++) {
			float valueVal = value.get(state, numActions);
			float stateVal = states.get(state);
			sumValue += stateVal;
			if (valueVal != 0)
				values[numActions] = values[numActions] + stateVal * valueVal;
		}

		// Normalize
		if (sumActionSel != 0)
			for (int action = 0; action < numActions; action++)
				// Normalize with real value and revert previous normalization
				values[action] = values[action] / sumActionSel;
		if (sumValue != 0)
			values[numActions] = values[numActions] / sumValue;

		// for (int action = 0; action < numActions; action++)
		// if (values[action] != 0)
		// System.out.println("value action " + values[action]);

		actionVote.set(values);
	}

	@Override
	public NslDoutFloat1 getVotes() {
		return actionVote;
	}

}
