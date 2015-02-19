package edu.usf.ratsim.experiment.task;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import edu.usf.ratsim.support.ElementWrapper;

public class DeactivatePCLTaks implements ExperimentTask {

	List<Integer> feedersToDeactivate;
	String group;

	public DeactivatePCLTaks(ElementWrapper taskParams) {
		feedersToDeactivate = new LinkedList<Integer>();
		StringTokenizer tokenizer = new StringTokenizer(
				taskParams.getChildText("layers"), ",");
		while (tokenizer.hasMoreElements()) {
			feedersToDeactivate.add(Integer.parseInt(tokenizer.nextToken()));
		}
		
		group = taskParams.getChildText("group");
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		if (subject.getGroup().equals(group))
			((MultiScaleMultiIntentionCooperativeModel) subject.getModel())
					.deactivatePCL(feedersToDeactivate);
	}

}
