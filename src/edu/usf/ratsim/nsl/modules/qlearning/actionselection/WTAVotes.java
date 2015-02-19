package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinFloat2;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.nsl.modules.Voter;
import edu.usf.ratsim.support.GeomUtils;

public class WTAVotes extends NslModule implements Voter {

	public NslDoutFloat1 actionVote;
	public NslDinFloat1 states;
	public NslDinFloat2 value;
	private int numActions;

	public WTAVotes(String nslName, NslModule nslParent, int numStates, int numActions) {
		super(nslName, nslParent);

		this.numActions = numActions;
		
		actionVote = new NslDoutFloat1(this, "votes", numActions);
		states = new NslDinFloat1(this, "states", numStates);
		value = new NslDinFloat2(this, "value", numStates, numActions);
	}

	public void simRun() {
		int s = getActiveState();

		setVotes(s);
	}

	private void setVotes(int state) {
		float[] values = new float[numActions];
		for (int action = 0; action < numActions; action++){
			values[action] = value.get(state, action);
//			System.out.print(value.get(state, action));
		}
//		System.out.println();
		
		actionVote.set(values);
	}

	private int getActiveState() {
		// Winner take all within the layer
		float maxVal = Float.NEGATIVE_INFINITY;
		int activeState = -1;
		for (int i = 0; i < states.getSize(); i++)
			if (states.get(i) > maxVal) {
				activeState = i;
				maxVal = states.get(i);
			}

		return activeState;
	}

	@Override
	public NslDoutFloat1 getVotes() {
		return actionVote;
	}

}
