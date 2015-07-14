package edu.usf.ratsim.nsl.modules;

public class ArtificialFeederCell {

	private int preferredFeeder;

	public ArtificialFeederCell(int i) {
		this.preferredFeeder = i;
	}

	public float getActivation(int id) {
		if (id == preferredFeeder)
			return 0;
		else
			return 0;
	}

}
