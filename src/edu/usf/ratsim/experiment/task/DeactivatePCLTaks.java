package edu.usf.ratsim.experiment.task;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.MultiScaleMultiIntentionCooperativeModel;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.support.ElementWrapper;

public class DeactivatePCLTaks implements ExperimentTask {

	List<Integer> feedersToDeactivate;

	public DeactivatePCLTaks(ElementWrapper taskParams) {
		feedersToDeactivate = new LinkedList<Integer>();
		StringTokenizer tokenizer = new StringTokenizer(
				taskParams.getChildText("layers"), ",");
		while (tokenizer.hasMoreElements()) {
			feedersToDeactivate.add(Integer.parseInt(tokenizer.nextToken()));
		}
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		((MultiScaleMultiIntentionCooperativeModel) subject.getModel())
				.deactivatePCL(feedersToDeactivate);
	}

}
