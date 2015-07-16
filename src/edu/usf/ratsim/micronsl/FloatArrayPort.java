package edu.usf.ratsim.micronsl;

public class FloatArrayPort extends FloatPort {

	float[] data;

	public FloatArrayPort(Module owner, float[] data) {
		super(owner);
		this.data = data;
	}

	@Override
	public int getSize() {
		return data.length;
	}

	@Override
	public float get(int index) {
		return data[index];
	}

}
