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
		
		for (int state = 0; state < states.getSize(); state++){
			if (states.get(state) != 0){
				for (int action = 0; action < numActions; action++){
					if (value.get(state, action) != 0) 
						values[action] += states.get(state) * value.get(state, action);
		//			System.out.print(value.get(state, action));
				}
			}
	//		System.out.println();
		}
		
		actionVote.set(values);
	}

}
