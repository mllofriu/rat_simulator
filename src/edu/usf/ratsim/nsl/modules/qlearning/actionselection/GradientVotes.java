package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinFloat2;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.nsl.modules.Voter;

public class GradientVotes extends NslModule implements Voter {

	public NslDoutFloat1 actionVote;
	public NslDinFloat1 states;
	public NslDinFloat2 value;
	private int numActions;
	
	public GradientVotes(String nslName, NslModule nslParent, int numStates, int numActions){
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
		float sumValue = 0;
		float cantStates = states.getSize();
//		System.out.println(cantStates);
		float gradient = .5f;
		for (int state = 0; state < cantStates; state++){
			float stateVal = states.get(state);
			// Update gradient every some steps
			if (state % 10000 == 0)
				gradient = (float) (1f / (Math.exp(-10 * (state/cantStates - .5) + 1)));
			if (stateVal != 0){
				sumActionSel += stateVal * (1-gradient);
				sumValue += stateVal * (gradient);
				for (int action = 0; action < numActions; action++){
					float actionVal = value.get(state, action);
					if (actionVal != 0) 
						// action selection contributes only in smaller states (smaller scales
						values[action] = values[action] + stateVal * actionVal * (1-gradient);
				}
				
				// Value place
				float actionVal = value.get(state, numActions);
				if (actionVal != 0)
					values[numActions] = values[numActions] + stateVal * actionVal * (gradient);
			}
		}
		
		// Normalize
		if (sumActionSel != 0)
			for (int action = 0; action < numActions; action++)
				// Normalize with real value and revert previous normalization
				values[action] = values[action] / sumActionSel;
		if (sumValue != 0)
			values[numActions] = values[numActions] / sumValue;
			
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
