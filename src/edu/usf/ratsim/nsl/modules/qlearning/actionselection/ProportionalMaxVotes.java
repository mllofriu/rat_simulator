package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinFloat2;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.support.GeomUtils;

public class ProportionalMaxVotes extends NslModule {

	public NslDoutFloat1 actionVote;
	public NslDinFloat1 states;
	public NslDinFloat2 value;
	private int numActions;

	public ProportionalMaxVotes(String nslName, NslModule nslParent,
			int numStates, int numActions) {
		super(nslName, nslParent);

		actionVote = new NslDoutFloat1(this, "votes", numActions);
		states = new NslDinFloat1(this, "states", numStates);
		value = new NslDinFloat2(this, "value", numStates, numActions);
	}

	public void simRun() {
		// Find the best value for each action, taking into acount state activation
		for (int action = 0; action < numActions; action++) {
			float bestActionValue = Float.NEGATIVE_INFINITY;
			for (int state = 0; state < states.getSize(); state++)
				if (states.get(state) * value.get(state, action) > bestActionValue) {
					bestActionValue = states.get(state)
							* value.get(state, action);
				}
			actionVote.set(action, bestActionValue);
		}
	}

}
