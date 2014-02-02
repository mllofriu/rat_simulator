package edu.usf.ratsim.experiment.multiscalemorris;

import java.util.List;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.qlearning.QLUpdateValue;

public class PolicyValueUpdater implements ExperimentTask {

	
	private List<QLUpdateValue> qlValUpdaters;

	public PolicyValueUpdater(List<QLUpdateValue> qlValUpdaters) {
		this.qlValUpdaters = qlValUpdaters;
	}

	public void perform(ExperimentUniverse univ) {
		for(QLUpdateValue qLUpdVal : qlValUpdaters)
			qLUpdVal.updateQValue();
	}

}
