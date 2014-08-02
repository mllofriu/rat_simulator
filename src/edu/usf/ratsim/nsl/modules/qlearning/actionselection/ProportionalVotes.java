package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinFloat2;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.support.Utiles;

/**
 * Class to set the votes for actions depending both in the state activation and a value function.
 * @author ludo
 *
 */
public class ProportionalVotes extends NslModule {

	public NslDoutFloat1 actionVote;
	public NslDinFloat1 states;
	public NslDinFloat2 value;
	private int numActions;

	public ProportionalVotes(String nslName, NslModule nslParent, int numStates) {
		super(nslName, nslParent);

		numActions = Utiles.numActions;
		
		actionVote = new NslDoutFloat1(this, "votes", numActions);
		states = new NslDinFloat1(this, "states", numStates);
		value = new NslDinFloat2(this, "value", numStates, numActions);
	}

	public void simRun() {
		float[] values = new float[numActions];
		for (int action = 0; action < numActions; action++)
			values[action] = 0f;
		
		float sum = 0;
		for (int j = 0; j < states.getSize(); j++){
			sum += states.get(j);
		}
		
		for (int state = 0; state < states.getSize(); state++){
			if (states.get(state) != 0){
				for (int action = 0; action < numActions; action++){
					if (value.get(state, action) != 0) 
						// Normalize activity when weighting votes
						values[action] = values[action] + states.get(state) / sum * value.get(state, action);
//						if (values[action] != 0)
//							System.out.println("value action " + values[action]);
//						if (states.get(state) / sum * value.get(state, action) < 0)
//							System.out.println("Termino negativo");
				}
			}
	//		System.out.println();
		}
		
		for (int action = 0; action < numActions; action++)
			if (values[action] != 0)
				System.out.println("value action " + values[action]);
		
		actionVote.set(values);
	}

}
