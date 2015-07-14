package edu.usf.ratsim.micronsl;

public abstract class FloatPort extends Port {

	public FloatPort(String name) {
		super(name);
	}

	public abstract int getSize();

	public abstract float get(int index);

	public float get() {
		return get(0);
	}

}
