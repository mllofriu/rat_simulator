package edu.usf.ratsim.experiment.multiscalemorris;

import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;
import edu.usf.ratsim.experiment.ExpSubject;
import edu.usf.ratsim.experiment.Experiment;
import edu.usf.ratsim.experiment.Trial;

public class MSMTrial extends Trial {

	private Point4f initPos;
	private Object type;

	public MSMTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, ExpSubject subject, String trialLogPath) {
		super(params, subject, trialLogPath);

		// Get the initial position
		initPos = points.get(params.get(Trial.STR_STARTS));
		
		type = params.get(Experiment.STR_TRIAL_TYPE);
	}

	
	public void loadConditions() {
		addStopCond(new FoundFoodStopCond(getUniverse()));
	}

	
	public void loadAfterCycleTasks() {
	}

	
	public void loadInitialTasks() {
		addInitialTask(new PlaceRobotInitallyTask(initPos));
	}

	
	public void loadLoggers() {
		MSMSubject subject = (MSMSubject) getSubject();
		addLogger(new PositionLogger(getLogPath(), subject.getActionPerformer()));
	}

	
	public void loadAfterTrialTasks() {
		MSMSubject subject = (MSMSubject) getSubject();
		addAfterTrialTask(new PolicyValueUpdater(subject.getQLValUpdaters()));
//		if (type.equals("testing"))
		addAfterTrialTask(new PolicyDumper(subject,getLogPath()));
	}

}
