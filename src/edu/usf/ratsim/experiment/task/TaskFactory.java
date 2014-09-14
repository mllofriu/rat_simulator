package edu.usf.ratsim.experiment.task;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point4f;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.Trial;
import edu.usf.ratsim.support.ElementWrapper;

public class TaskFactory {

	private static final String STR_TASK = "task";
	private static final String STR_TASK_NAME = "name";
	private static final String STR_TASK_PARAMS = "params";

	public static Collection<ExperimentTask> createTasks(ElementWrapper elementWrapper,
			Map<String, Point4f> points, Trial t) {
		Collection<ExperimentTask> res = new LinkedList<ExperimentTask>();

		List<ElementWrapper> taskList = elementWrapper.getChildren(STR_TASK);
		for (ElementWrapper taskNode : taskList) {
			String taskName = taskNode.getChildText(STR_TASK_NAME);

			ElementWrapper taskParams = taskNode.getChild(STR_TASK_PARAMS);
			if (taskName.equals("PlaceRobotInitiallyTask")) {
				res.add(new PlaceRobotInitallyTask(taskParams, points));
			}else if (taskName.equals("ResetModelTask")) {
				res.add(new ResetModelTaks());
			}else if (taskName.equals("DeactivatePCL")) {
				res.add(new DeactivatePCLTaks(taskParams));
//			} else if (taskName.equals("PolicyValueUpdater")) {
//				res.add(new PolicyValueUpdater(model));
			} else if (taskName.equals("ActivateFeeders")) {
				res.add(new ActivateAllFeeders());
			} else if (taskName.equals("ActivateRandomFeeder")) {
				res.add(new ActivateRandomFeeder());
			} else if (taskName.equals("GiveFood")) {
				res.add(new GiveFood());
			} else if (taskName.equals("DeactivateFeeder")) {
				res.add(new DeactivateFeeder(taskParams));
			} else if (taskName.equals("DeactivateAllFeeders")) {
				res.add(new DeactivateAllFeeders());
			}else if (taskName.equals("FlashFeeder")) {
				res.add(new FlashFeeder());
			}else if (taskName.equals("FlashFeederWhenWrong")) {
				res.add(new FlashFeederWhenWrong(taskParams));
			} else if (taskName.equals("FeederTemporalDeactivate")) {
				res.add(new FeederTemporalDeactivate());
			} else if (taskName.equals("UnflashFeeder")) {
				res.add(new UnflashFeeder());
			} else if (taskName.equals("PlaceWall")) {
				res.add(new PlaceWallTask(taskParams, points));
			} else if (taskName.equals("PlaceFeederWalls")) {
				res.add(new PlaceFeederWallsTask(taskParams));
			} else if (taskName.equals("PlaceRandomWalls")) {
				res.add(new PlaceRandomWallsTask(taskParams));
			} else if (taskName.equals("ClearWalls")) {
				res.add(new ClearWallsTask());
			} else if (taskName.equals("ResetRobotAte")) {
				res.add(new ResetRobotAte());
			} else {
				throw new RuntimeException("Task " + taskName
						+ " not implemented.");
			}
		}

		return res;
	}
}
