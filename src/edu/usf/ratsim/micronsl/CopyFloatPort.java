package edu.usf.ratsim.micronsl;

public class CopyFloatPort extends FloatPort {

	private float[] data = null;
	private FloatPort toCopy;

	public CopyFloatPort(Module owner, FloatPort toCopy) {
		super(owner);

		data = new float[toCopy.getSize()];

		this.toCopy = toCopy;
	}

	@Override
	public int getSize() {
		return data.length;
	}

	@Override
	public float get(int index) {
		return data[index];
	}

	public void copy() {
		for (int i = 0; i < toCopy.getSize(); i++)
			data[i] = toCopy.get(i);
	}

}
