package edu.usf.ratsim.experiment.task;

import java.util.List;

import nslj.src.lang.NslModel;
import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.MultiScaleModel;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.nsl.modules.qlearning.update.ReverseUpdate;

public class PolicyValueUpdater implements ExperimentTask {

	private List<ReverseUpdate> qlValUpdaters;

	public PolicyValueUpdater(List<ReverseUpdate> qlValUpdaters) {
		this.qlValUpdaters = qlValUpdaters;
	}

	public PolicyValueUpdater(NslModel model) {
		if (!(model instanceof MultiScaleModel))
			throw new RuntimeException(
					"Policy value updater can only be used with MSM model");

		qlValUpdaters = ((MultiScaleModel) model).getQLValUpdaters();
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		for (ReverseUpdate qLUpdVal : qlValUpdaters)
			qLUpdVal.updateQValueFood();
	}

}
