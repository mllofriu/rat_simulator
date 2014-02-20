package edu.usf.ratsim.experiment.task;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class FeederTemporalDeactivate implements ExperimentTask {

	private static final int SLEEP_TIME = 50;
	private int sleepingTime;
	private int sleepingFeeder;
	
	public FeederTemporalDeactivate(){
		sleepingFeeder = -1;
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		if(univ.hasRobotFoundFood()){
			sleepingFeeder = univ.getFeedingFeeder();
			univ.setActiveFeeder(sleepingFeeder, false);
			sleepingTime = SLEEP_TIME;
		} else if(sleepingFeeder != -1){
			sleepingTime--;
			if (sleepingTime <= 0){
				univ.setActiveFeeder(sleepingFeeder, true);
				sleepingFeeder = -1;
			}
		}
	}

}
