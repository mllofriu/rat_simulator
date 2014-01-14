package experiment;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModel;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslScheduler;
import nslj.src.system.NslSystem;
import robot.IRobot;
import robot.RobotFactory;
import support.Configuration;

/*
 * SimulationItem.java
 * Este modulo representa un item simulable 
 * Autor: Gonzalo Tejera
 * Fecha: 11 de agosto de 2010
 */
/**
 * Successor of SimulationItem class. It models a runnable trial of a
 * experiment.
 * 
 * @author ludo
 * 
 */
public abstract class Trial implements Runnable {
	public static final int HABITUATION = 0;
	public static final int TRAINING = 1;
	public static final int TESTING = 2;

	public static enum Type {
		HABITUATION, TRAINING, TESTING
	};

	public static final String STR_REPETITIONS = "reps";
	public static final String STR_TIME = "time";
	public static final String STR_NAME = "name";
	public static final Object STR_MAZE = "maze";

	private int repetitions;
	private String name;
	private Collection<StopCondition> stopConds;
	private NslSystem system;
	private NslInterpreter interpreter;
	private NslScheduler scheduler;
	private Collection<ExperimentTask> tasks;
	private Map<String, String> params;
	private ExperimentUniverse world;
	private IRobot robot;

	public Trial(Map<String, String> params) {
		super();
		this.setParams(params);
		this.name = params.get(STR_NAME);
		this.repetitions = Integer.parseInt(params.get(STR_REPETITIONS));

		stopConds = new LinkedList<StopCondition>();

		tasks = new LinkedList<ExperimentTask>();

		// Set the maze to execute
		Configuration.setProperty("WorldFrame.MAZE_FILE",
				getParams().get(Trial.STR_MAZE));
		setWorld(ExpUniverseFactory.getUniverse());
		// Get the robot
		setRobot(RobotFactory.getRobot());

		system = new NslSystem(); // Create System
		interpreter = new NslInterpreter(system); // Create Interpreter
//		scheduler = new NslMultiClockScheduler(system); // Create Scheduler
		scheduler = new NslSequentialScheduler(system); // Create Scheduler
		
		system.setNoDisplay(false);
		system.setDebug(0);
		system.setStdOut(true);
		system.setStdErr(true);
		system.setInterpreter(interpreter);
		system.nslSetScheduler(scheduler);
		system.nslSetSchedulerMethod("pre");

		system.setRunEndTime(1);
		system.nslSetRunDelta(.1);
		system.setNumRunEpochs(1);

		NslHierarchy.nslSetSystem(system);
	}

	@Override
	public void run() {
		for (int r = 0; r < repetitions; r++) {
			System.out.println("Starting repetition");
			// Create the model
			NslModel model = initModel();
			// Load the trial tasks
			loadTasks();
			// Load the stop conditions
			loadConditions();
			// Load it into nsl
			system.addModel(model);

			// init Run epochs
			scheduler.initRun();

			boolean stop;
			do {
				System.out.println("Beginning cycle");
				// One cycle to the trial
				scheduler.stepCycle();
				
				// Do all after-cycle tasks
				for (ExperimentTask task : tasks)
					task.perform();
				System.out.println("End cycle");
				// // Check all stop conds
				stop = false;
				for (StopCondition sc : stopConds)
					stop = stop || sc.experimentFinished();
			} while (!stop);
			System.out.println("Repetition finished");
			// // Sleep to appreciate changes
			// try {
			// Thread.sleep(5000);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			finalizeModel(model);

			stopConds.clear();
			tasks.clear();
		}
	}

	public abstract void loadConditions();

	public abstract void loadTasks();

	public abstract NslModel initModel();

	public abstract void finalizeModel(NslModel model);

	@Override
	public String toString() {
		return name;
	}

	public void addTask(ExperimentTask t) {
		tasks.add(t);
	}

	public void addStopCond(StopCondition sc) {
		stopConds.add(sc);
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public ExperimentUniverse getWorld() {
		return world;
	}

	public void setWorld(ExperimentUniverse world) {
		this.world = world;
	}

	public IRobot getRobot() {
		return robot;
	}

	public void setRobot(IRobot robot) {
		this.robot = robot;
	}
}
