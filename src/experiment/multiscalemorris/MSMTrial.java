package experiment.multiscalemorris;

import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;
import experiment.ExpSubject;
import experiment.Trial;

public class MSMTrial extends Trial {

	private Point4f initPos;

	public MSMTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, ExpSubject subject, String trialLogPath) {
		super(params, subject, trialLogPath);

		// Get the initial position
		initPos = points.get(params.get(Trial.STR_STARTS));
	}

	@Override
	public void loadConditions() {
		addStopCond(new FoundFoodStopCond(getUniverse()));
	}

	@Override
	public void loadAfterCycleTasks() {
		MSMSubject subject = (MSMSubject) getSubject();
		addAfterCycleTask(new PolicyDumper(subject,getLogPath()));
	}

	@Override
	public void loadInitialTasks() {
		addInitialTask(new PlaceRobotInitallyTask(initPos));
	}

	@Override
	public void loadLoggers() {
		addLogger(new PositionLogger(getLogPath()));
	}

}
