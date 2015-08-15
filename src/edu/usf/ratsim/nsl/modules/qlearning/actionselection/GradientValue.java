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

public class GradientValue extends Module implements Voter {

	public float[] valueEst;
	private int numActions;
	private boolean[] connected;

	public GradientValue(String name, int numActions, int numStates,
			int numLayers, List<Float> connProbs) {
		super(name);

		valueEst = new float[1];
		addOutPort("valueEst", new Float1dPortArray(this, valueEst));

		this.numActions = numActions;

		int statesPerLayer = (numStates / numLayers);
		connected = new boolean[numStates];
		Random r = RandomSingleton.getInstance();
		for (int layer = 0; layer < numLayers; layer++) {
			float prob = connProbs.get(layer);
			for (int state = statesPerLayer * layer; state < statesPerLayer
					* (layer + 1); state++) {
				connected[state] = r.nextFloat() < prob;
			}
		}
	}

	public void run() {
		Float1dPort states = (Float1dPort) getInPort("states");
		FloatMatrixPort value = (FloatMatrixPort) getInPort("value");
		valueEst[0] = 0f;

		double sum = 0;
		float cantStates = states.getSize();
		for (int state = 0; state < cantStates; state++) {
			if (connected[state]) {
				float stateVal = states.get(state);
				if (stateVal != 0) {
					sum += stateVal;
					float actionVal = value.get(state, numActions);
					if (actionVal != 0)
						valueEst[0] = valueEst[0] + stateVal
								* actionVal;
				}
			}
		}

		// Normalize
		if (sum != 0)
				// Normalize with real value and revert previous normalization
				valueEst[0] = (float) (valueEst[0] / sum);

		if (Debug.printValues) {
			System.out.println("RL value");
			System.out.print(valueEst[0] + " ");
			System.out.println();
		}
	}

	public float[] getVotes() {
		return valueEst;
	}

	@Override
	public boolean usesRandom() {
		return false;
	}

}
