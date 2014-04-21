package edu.usf.ratsim.nsl.modules;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;

public class PlaceIntention extends NslModule {

	private NslDinFloat1 places;
	private NslDinInt0 goalFeeder;
	private NslDoutFloat1 states;
	
	public PlaceIntention(NslModule parent, String nslName, int numPlaces, int numGoals){
		super(nslName, parent);
		
		places = new NslDinFloat1(this, "places", numPlaces);
		goalFeeder = new NslDinInt0(this, "goalFeeder");
		states = new NslDoutFloat1(this, "states", numPlaces * numGoals);
	}
	
	public void simRun(){
		states.set(0);
		
		for(int i = 0; i < places.getSize(); i ++){
			goalFeeder.get();
			places.get(i);
			states.set((int) (goalFeeder.get() * places.getSize() + i), places.get(i));
		}
	}

	public int getSize() {
		return states.getSize();
	}

}
