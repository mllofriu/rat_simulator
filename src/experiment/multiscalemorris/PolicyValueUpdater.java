package experiment.multiscalemorris;

import java.util.List;

import nsl.modules.qlearning.QLUpdateValue;
import experiment.ExperimentTask;
import experiment.ExperimentUniverse;

public class PolicyValueUpdater implements ExperimentTask {

	
	private List<QLUpdateValue> qlValUpdaters;

	public PolicyValueUpdater(List<QLUpdateValue> qlValUpdaters) {
		this.qlValUpdaters = qlValUpdaters;
	}

	@Override
	public void perform(ExperimentUniverse univ) {
		for(QLUpdateValue qLUpdVal : qlValUpdaters)
			qLUpdVal.updateQValue();
	}

}
