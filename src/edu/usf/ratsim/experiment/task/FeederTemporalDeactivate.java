package edu.usf.ratsim.experiment.task;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class FeederTemporalDeactivate implements ExperimentTask {

	private int sleepingFeeder;
	
	public FeederTemporalDeactivate(){
		sleepingFeeder = -1;
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		if(univ.hasRobotFoundFood()){
			// If one is sleeping, reactivate
			if (sleepingFeeder != -1)
				univ.setActiveFeeder(sleepingFeeder, true);
			// Deactivate the feeder
			sleepingFeeder = univ.getFeedingFeeder();
			univ.setActiveFeeder(sleepingFeeder, false);
		} 
	}

}
