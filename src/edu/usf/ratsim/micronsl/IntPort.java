package edu.usf.ratsim.micronsl;

public abstract class IntPort extends Port {

	public IntPort(String name) {
		super(name);
	}

	public abstract int getSize();

	public abstract int get(int index);

	public int get() {
		return get(0);
	}

}
