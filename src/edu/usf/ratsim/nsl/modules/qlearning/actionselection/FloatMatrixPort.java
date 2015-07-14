package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import edu.usf.ratsim.micronsl.FloatPort;

public class FloatMatrixPort extends FloatPort {

	float[][] data;

	public FloatMatrixPort(String name, float[][] data) {
		super(name);

		if (data.length == 0)
			throw new IllegalArgumentException("Cannot use matrix with 0 rows");

		this.data = data;
	}

	@Override
	public int getSize() {
		return data.length * data[0].length;
	}

	@Override
	public float get(int index) {
		return data[index / data[0].length][index % data[0].length];
	}

	public float get(int i, int j) {
		return data[i][j];
	}

	public void set(int i, int j, float x) {
		data[i][j] = x;
	}

	public float[][] getData() {
		return data;
	}
}
