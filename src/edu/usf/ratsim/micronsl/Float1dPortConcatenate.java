package edu.usf.ratsim.micronsl;

import java.util.List;

public class Float1dPortConcatenate extends Float1dPort {

	private List<Float1dPort> states;
	private int size;

	public Float1dPortConcatenate(Module owner, List<Float1dPort> states) {
		super(owner);

		this.states = states;

		size = 0;
		for (Float1dPort state : states)
			size += state.getSize();
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public float get(int index) {
		for (Float1dPort state : states) {
			if (index - state.getSize() < 0)
				return state.get(index);
			else
				index -= state.getSize();
		}

		throw new RuntimeException("Index out of bounds");
	}

	@Override
	public float[] getData() {
		float [] data = new float[size];
		int i = 0;
		for (Float1dPort state : states){
			System.arraycopy(state.getData(), 0, data, i, state.getSize());
			i += state.getSize();
		}
			
		return data;
	}

	@Override
	public void getData(float[] data) {
		int i = 0;
		for (Float1dPort state : states){
			System.arraycopy(state.getData(), 0, data, i, state.getSize());
			i += state.getSize();
		}
	}

}
