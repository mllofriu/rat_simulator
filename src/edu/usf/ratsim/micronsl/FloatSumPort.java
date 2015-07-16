package edu.usf.ratsim.micronsl;

import java.util.List;

public class FloatSumPort extends FloatPort {

	private List<FloatPort> states;

	public FloatSumPort(Module owner, List<FloatPort> states) {
		super(owner);

		if (states.isEmpty())
			throw new IllegalArgumentException(
					"Cannot use an empty list of states");

		boolean allSameSize = true;
		int sizeFirst = states.get(0).getSize();
		for (FloatPort state : states)
			allSameSize = sizeFirst == state.getSize();
		if (!allSameSize)
			throw new IllegalArgumentException(
					"All states should be the same size");

		this.states = states;
	}

	@Override
	public int getSize() {
		return states.get(0).getSize();
	}

	@Override
	public float get(int index) {
		float sum = 0;
		for (FloatPort state : states)
			sum += state.get(index);
		return sum;
	}

}
