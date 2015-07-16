package edu.usf.ratsim.micronsl;

import java.util.List;

public class FloatMultiplyPort extends FloatPort {

	private int size;
	private float eps;
	private List<FloatPort> states;

	public FloatMultiplyPort(Module owner, List<FloatPort> states, float eps) {
		super(owner);

		this.eps = eps;

		this.states = states;

		if (states.isEmpty())
			size = 0;
		else {
			size = 1;
			for (FloatPort state : states)
				size *= state.getSize();
		}
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public float get(int index) {
		int remainingSize = size;
		float jointActivation = 1;

		for (FloatPort state : states) {
			remainingSize /= state.getSize();
			int stateIndex = (index / remainingSize) % state.getSize();
			jointActivation *= state.get(stateIndex);
			if (jointActivation < eps) {
				jointActivation = 0;
				// TODO: Optimize to set zero from then on
				break;
			}
		}

		return jointActivation;
	}

}
