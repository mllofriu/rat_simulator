package experiment.multiscalemorris;

import java.util.Map;

import robot.IRobot;
import robot.RobotFactory;
import robot.virtual.VirtualExpUniverse;
import robot.virtual.VirtualRobot;
import support.Configuration;
import nslj.src.lang.NslModel;
import experiment.ExpUniverseFactory;
import experiment.ExperimentUniverse;
import experiment.TimeStop;
import experiment.Trial;

public class MSMTrial extends Trial {
	
	
	private MSMModel model;
	private int time;

	public MSMTrial(Map<String, String> params) {
		super(params);
		
		time = Integer.parseInt(params.get(STR_TIME));
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
		// Add default stop condition - time constraints
		addStopCond(new TimeStop(time));
	}

	@Override
	public void loadTasks() {
	}
	
	@Override
	public void finalizeModel(NslModel model) {
	}

}
