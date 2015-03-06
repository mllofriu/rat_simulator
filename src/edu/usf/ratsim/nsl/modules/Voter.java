package edu.usf.ratsim.nsl.modules;

import nslj.src.lang.NslDoutFloat1;

public interface Voter {

	public NslDoutFloat1 getVotes();
	
	public void simRun();
}
