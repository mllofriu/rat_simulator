package edu.usf.ratsim.experiment.task;

import java.util.List;

import nslj.src.lang.NslModel;
import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.MultiScaleMorrisModel;
import edu.usf.ratsim.nsl.modules.qlearning.QLUpdateValue;

public class PolicyValueUpdater implements ExperimentTask {

	
	private List<QLUpdateValue> qlValUpdaters;

	public PolicyValueUpdater(List<QLUpdateValue> qlValUpdaters) {
		this.qlValUpdaters = qlValUpdaters;
	}

	public PolicyValueUpdater(NslModel model) {
		if (! (model instanceof MultiScaleMorrisModel))
			throw new RuntimeException("Policy value updater can only be used with MSM model");
		
		qlValUpdaters = ((MultiScaleMorrisModel)model).getQLValUpdaters();
	}

	public void perform(ExperimentUniverse univ) {
		for(QLUpdateValue qLUpdVal : qlValUpdaters)
			qLUpdVal.updateQValueFoo();
	}

}
