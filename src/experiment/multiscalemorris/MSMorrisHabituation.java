package experiment.multiscalemorris;

import java.util.Map;

import robot.virtual.ExperimentUniverse;
import robot.virtual.VirtualRobot;
import nslj.src.lang.NslModel;
import experiment.TimeStop;
import experiment.Trial;

public class MSMorrisHabituation extends Trial {

	private ExperimentUniverse world;
	private VirtualRobot robot;
	private HabituationModel model;
	private int time;

	public MSMorrisHabituation(Map<String, String> params) {
		super(params);
		
		time = Integer.parseInt(params.get(STR_TIME));
	}

	@Override
	public NslModel initModel() {
		System.out.println("Init model");
		world = new ExperimentUniverse(getParams().get(Trial.STR_MAZE));
		robot = new VirtualRobot(world);
		model = new HabituationModel("MSMHabituationModel", (NslModel) null, robot);
		return model;
	}

	@Override
	public void loadConditions() {
		addStopCond(new FoundFoodStopCond(robot));
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
