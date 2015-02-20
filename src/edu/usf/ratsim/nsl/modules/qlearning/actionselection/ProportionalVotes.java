package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinFloat2;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.nsl.modules.Voter;

/**
 * Class to set the votes for actions depending both in the state activation and a value function.
 * @author ludo
 *
 */
public class ProportionalVotes extends NslModule implements Voter {

	public NslDoutFloat1 actionVote;
	public NslDinFloat1 states;
	public NslDinFloat2 value;
	private int numActions;

	public ProportionalVotes(String nslName, NslModule nslParent, int numStates, int numActions) {
		super(nslName, nslParent);

		actionVote = new NslDoutFloat1(this, "votes", numActions);
		states = new NslDinFloat1(this, "states", numStates);
		value = new NslDinFloat2(this, "value", numStates, numActions);
		
		this.numActions = numActions;
	}

	public void simRun() {
		float[] values = new float[numActions];
		for (int action = 0; action < numActions; action++)
			values[action] = 0f;
		
		float sum = 0;
		float cantStates = states.getSize();
		for (int state = 0; state < cantStates; state++){
			float stateVal = states.get(state);
			if (stateVal != 0){
				sum += stateVal;
				for (int action = 0; action < numActions; action++){
					float actionVal = value.get(state, action);
					if (actionVal != 0) 
						values[action] = values[action] + stateVal * actionVal;
				}
			}
		}
		
		// Normalize
		if (sum != 0)
			for (int action = 0; action < numActions; action++)
				// Normalize with real value and revert previous normalization
				values[action] = values[action] / sum;
			
//		for (int action = 0; action < numActions; action++)
//			if (values[action] != 0)
//				System.out.println("value action " + values[action]);
		
		actionVote.set(values);
	}

	@Override
	public NslDoutFloat1 getVotes() {
		return actionVote;
	}

}
