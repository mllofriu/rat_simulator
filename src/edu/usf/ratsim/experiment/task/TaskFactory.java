package edu.usf.ratsim.experiment.task;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.usf.ratsim.experiment.ExperimentTask;

public class TaskFactory {

	private static final String STR_TASK = "task";
	private static final String STR_TASK_NAME = "name";
	private static final String STR_TASK_PARAMS = "params";

	public static Collection<ExperimentTask> createTasks(Element tasks,
			Map<String, Point4f> points, NslModel model) {
		Collection<ExperimentTask> res = new LinkedList<ExperimentTask>();

		NodeList taskList = tasks.getElementsByTagName(STR_TASK);
		for (int i = 0; i < taskList.getLength(); i++) {
			Element taskNode = (Element) taskList.item(i);
			String taskName = taskNode.getElementsByTagName(STR_TASK_NAME)
					.item(0).getTextContent();

			Element taskParams = (Element) taskNode.getElementsByTagName(
					STR_TASK_PARAMS).item(0);
			if (taskName.equals("PlaceRobotInitiallyTask")) {
				res.add(new PlaceRobotInitallyTask(taskParams, points));
			} else if (taskName.equals("PolicyValueUpdater")) {
				res.add(new PolicyValueUpdater(model));
			} else {
				throw new RuntimeException("Task " + taskName
						+ " not implemented.");
			}
		}

		return res;
	}
}
