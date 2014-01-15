package experiment.multiscalemorris;

import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;
import experiment.Trial;

public class MSMTrial extends Trial {
	
	
	private MSMModel model;
	private Point4f initPos;

	public MSMTrial(Map<String, String> params, Hashtable<String, Point4f> points) {
		super(params);
		
		// Get the initial position
		initPos = points.get(params.get(Trial.STR_STARTS));
	}

	@Override
	public NslModel initModel() {
		System.out.println("Init model");
		model = new MSMModel("MSMHabituationModel", (NslModel) null, getRobot());
		return model;
	}

	@Override
	public void loadConditions() {
		addStopCond(new FoundFoodStopCond(getRobot()));
	}

	@Override
	public void loadTasks() {
		addTask(new PlaceRobotInitallyTask(initPos));
	}
	
	@Override
	public void finalizeModel(NslModel model) {
	}

}
