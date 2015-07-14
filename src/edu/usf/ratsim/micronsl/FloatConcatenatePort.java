package edu.usf.ratsim.micronsl;

import java.util.List;

public class FloatConcatenatePort extends FloatPort {

	private List<FloatPort> states;
	private int size;

	public FloatConcatenatePort(String name, List<FloatPort> states) {
		super(name);

		this.states = states;

		size = 0;
		for (FloatPort state : states)
			size += state.getSize();
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public float get(int index) {
		for (FloatPort state : states) {
			if (index - state.getSize() < 0)
				return state.get(index);
			else
				index -= state.getSize();
		}

		throw new RuntimeException("Index out of bounds");
	}

}
