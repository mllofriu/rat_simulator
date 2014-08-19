package edu.usf.ratsim.experiment.task;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Debug;

public class GiveFood implements ExperimentTask {

	public static final String STR_ACTIVE_FEEDERS = "activeFeeders";
	private int feederDelay;
	private int stepsCloseToFeeder;

	public GiveFood(){
		this.feederDelay = Configuration.getInt("VirtualUniverse.feedersDelay");
		this.stepsCloseToFeeder = feederDelay;
	}
	@Override
	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		if (univ.getFeedingFeeder() != -1)
			stepsCloseToFeeder--;
		else
			stepsCloseToFeeder = feederDelay;
		
		if (stepsCloseToFeeder <= 0){
			stepsCloseToFeeder = feederDelay;
			if (Debug.printGiveFood) System.out.println("Close to a feeder for long");
			int feeder = univ.getFoundFeeder();
			if (univ.isFeederActive(feeder)){
				univ.releaseFood(feeder);
				if (Debug.printGiveFood) System.out.println("Feeder is active, releasing");
			}
		}
	}

}
