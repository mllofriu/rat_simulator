package edu.usf.ratsim.micronsl;

import java.util.List;

public class FloatMultiplyPort extends FloatPort {

	private List<FloatPort> states;
	private int size;
	private float eps;

	public FloatMultiplyPort(String name, List<FloatPort> states, float eps) {
		super(name);

		this.states = states;
		this.eps = eps;

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
				break;
			}
		}

		return jointActivation;
	}

}
