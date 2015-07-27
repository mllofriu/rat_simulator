package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import edu.usf.experiment.utils.Debug;
import edu.usf.ratsim.micronsl.Float1dPort;
import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.FloatMatrixPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.nsl.modules.Voter;

public class HalfAndHalfConnectionValue extends Module implements Voter {

	public float[] value;
	private int numActions;

	public HalfAndHalfConnectionValue(String name, int numActions) {
		super(name);
		value = new float[1];
		addOutPort("valueEst", new Float1dPortArray(this, value));
		this.numActions = numActions;
	}

	public void run() {
		Float1dPort states = (Float1dPort) getInPort("states");
		float[] data = states.getData();
		FloatMatrixPort rlValue = (FloatMatrixPort) getInPort("value");

		value[0] = 0f;
		int cantStates = states.getSize();
		float sumValue = 0;
		for (int state = cantStates / 2; state < cantStates; state++) {
			float valueVal = rlValue.get(state, numActions);
			float stateVal = data[state];
			sumValue += stateVal;
			if (valueVal != 0)
				value[0] = value[0] + stateVal
						* valueVal;
		}

		if (sumValue != 0)
			value[0] = value[0] / sumValue;

		if (Debug.printHalfAndHalf) {
			System.out.println("RL Half and Half value: " + value[0]);
		}

	}

	public float[] getVotes() {
		return value;
	}

	@Override
	public boolean usesRandom() {
		return false;
	}

}
