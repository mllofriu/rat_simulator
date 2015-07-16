package edu.usf.ratsim.nsl.modules;

import edu.usf.ratsim.micronsl.FloatArrayPort;
import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.IntPort;
import edu.usf.ratsim.micronsl.Module;

public class PlaceIntention extends Module {

	private float[] states;
	private FloatPort places;
	private IntPort goalFeeder;

	public PlaceIntention(String name, FloatPort places, IntPort goalFeeder) {
		super(name);
		this.goalFeeder = goalFeeder;
		this.places = places;
		states = new float[goalFeeder.getSize() * places.getSize()];
		addOutPort("states", new FloatArrayPort(this, states));
	}

	public void simRun() {
		for (int i = 0; i < states.length; i++)
			states[i] = 0;

		for (int i = 0; i < places.getSize(); i++) {
			goalFeeder.get();
			places.get(i);
			states[goalFeeder.get() * places.getSize() + i] = places.get(i);
		}
	}
}
