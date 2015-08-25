package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import java.util.List;
import java.util.Random;

import edu.usf.experiment.utils.Debug;
import edu.usf.experiment.utils.RandomSingleton;
import edu.usf.ratsim.micronsl.Float1dPort;
import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.FloatMatrixPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.nsl.modules.Voter;

public class GradientVotes extends Module implements Voter {

	public float[] actionVote;
	private int numActions;
	private boolean[] connected;

	public GradientVotes(String name, int numActions, List<Float> connProbs, List<Integer> statesPerLayer) {
		super(name);

		actionVote = new float[numActions];
		addOutPort("votes", new Float1dPortArray(this, actionVote));

		this.numActions = numActions;

		int numStates = 0;
		for (Integer stateLen : statesPerLayer)
			numStates += stateLen;
		connected = new boolean[numStates];
		Random r = RandomSingleton.getInstance();
		int layer = 0;
		int stateIndex = 0;
		for (Integer layerNumStates : statesPerLayer) {
			float prob = connProbs.get(layer);
			for (int i = 0; i < layerNumStates; i++) {
				connected[stateIndex] = r.nextFloat() < prob;
				stateIndex++;
			}
			layer++;
		}
	}

	public void run() {
		Float1dPort states = (Float1dPort) getInPort("states");
		FloatMatrixPort value = (FloatMatrixPort) getInPort("value");
		for (int action = 0; action < numActions; action++)
			actionVote[action] = 0f;

		double sum = 0;
		float cantStates = states.getSize();
		for (int state = 0; state < cantStates; state++) {
			if (connected[state]) {
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
		}

		// Normalize
		if (sum != 0)
			for (int action = 0; action < numActions; action++)
				// Normalize with real value and revert previous normalization
				actionVote[action] = (float) (actionVote[action] / sum);

		if (Debug.printValues) {
			System.out.println("RL votes");
			for (int action = 0; action < numActions; action++)
				System.out.print(actionVote[action] + " ");
			System.out.println();
		}
	}

	public float[] getVotes() {
		return actionVote;
	}

	@Override
	public boolean usesRandom() {
		return false;
	}

}
