package edu.usf.ratsim.experiment.subject;

import edu.usf.ratsim.micronsl.FloatPort;

public class CopyFloatPort extends FloatPort {

	private FloatPort toCopy;
	private float[] data;

	public CopyFloatPort(String name, FloatPort toCopy) {
		super(name);
		
		this.toCopy = toCopy;
		data = new float[toCopy.getSize()];
	}

	@Override
	public int getSize() {
		return toCopy.getSize();
	}

	@Override
	public float get(int index) {
		return data[index];
	}
	
	public void copy(){
		for (int i = 0; i < toCopy.getSize(); i++)
			data[i] = toCopy.get(i);
	}

}
