package edu.usf.ratsim.micronsl;

public abstract class FloatPort extends Port {

	public FloatPort(Module owner) {
		super(owner);
	}

	public abstract int getSize();

	public abstract float get(int index);

	public float get() {
		return get(0);
	}

}
