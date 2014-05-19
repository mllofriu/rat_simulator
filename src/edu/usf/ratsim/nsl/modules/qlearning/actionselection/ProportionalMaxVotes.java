package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinFloat2;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.support.Utiles;

public class ProportionalMaxVotes extends NslModule {

	public NslDoutFloat1 actionVote;
	public NslDinFloat1 states;
	public NslDinFloat2 value;
	private int numActions;

	public ProportionalMaxVotes(String nslName, NslModule nslParent,
			int numStates) {
		super(nslName, nslParent);

		numActions = Utiles.numActions;

		actionVote = new NslDoutFloat1(this, "votes", numActions);
		states = new NslDinFloat1(this, "states", numStates);
		value = new NslDinFloat2(this, "value", numStates, numActions);
	}

	public void simRun() {
		// Find the max action
		int bestActionState = 0;
		float bestActionValue = Float.NEGATIVE_INFINITY;
		for (int state = 0; state < states.getSize(); state++)
			for (int action = 0; action < numActions; action++) {
				if (states.get(state) * value.get(state, action) > bestActionValue) {
					bestActionState = state;
					bestActionValue = states.get(state)
							* value.get(state, action);
				}
			}

		// Set the votes of the state that gave the best action
		for (int action = 0; action < numActions; action++) {
			actionVote.set(
					action,
					states.get(bestActionState)
							* value.get(bestActionState, action));
		}
	}

	private void setVotes(int state) {

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

}
