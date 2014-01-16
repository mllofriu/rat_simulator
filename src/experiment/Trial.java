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
import tcl.lang.Namespace.DeleteProc;

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

	public static final String STR_TIME = "time";
	public static final String STR_NAME = "name";
	public static final String STR_MAZE = "maze";
	public static final String STR_STARTS = "start";
	private static final long SLEEP_BETWEEN_CYCLES = 000;

	private String name;
	private Collection<StopCondition> stopConds;
	private NslSystem system;
	private NslInterpreter interpreter;
	private NslScheduler scheduler;
	private Collection<ExperimentTask> initialTasks;
	private Collection<ExperimentTask> afterCycleTasks;
	private Map<String, String> params;
	private ExperimentUniverse universe;
	private IRobot robot;

	public Trial(Map<String, String> params) {
		super();
		this.setParams(params);
		this.name = params.get(STR_NAME);

		stopConds = new LinkedList<StopCondition>();
		// Add default stop condition - time constraints
		int times = Integer.parseInt(params.get(STR_TIME));
		addStopCond(new TimeStop(times));

		initialTasks = new LinkedList<ExperimentTask>();
		afterCycleTasks = new LinkedList<ExperimentTask>();

		// Set the maze to execute
		Configuration.setProperty("Experiment.MAZE_FILE",
				getParams().get(Trial.STR_MAZE));
		setUniverse(ExpUniverseFactory.getUniverse());
		// Get the robot
		setRobot(RobotFactory.getRobot());

		system = new NslSystem(); // Create System
		interpreter = new NslInterpreter(system); // Create Interpreter
		// scheduler = new NslMultiClockScheduler(system); // Create Scheduler
		scheduler = new NslSequentialScheduler(system); // Create Scheduler

		system.setNoDisplay(false);
		system.setDebug(0);
		system.setStdOut(true);
		system.setStdErr(true);
		system.setInterpreter(interpreter);
		system.nslSetScheduler(scheduler);
		system.nslSetSchedulerMethod("pre");

		system.setRunEndTime(10000000);
		system.nslSetRunDelta(.1);
		system.setNumRunEpochs(1);

		NslHierarchy.nslSetSystem(system);
	}

	@Override
	public void run() {
		// Create the model
		NslModel model = initModel();
		// Load the trial tasks
		loadInitialTasks();
		loadAfterCycleTasks();
		// Load the stop conditions
		loadConditions();
		// Load it into nsl
		system.addModel(model);

		// init Run epochs
		scheduler.initRun();

		// Do all after-cycle tasks
		for (ExperimentTask task : initialTasks)
			task.perform(getUniverse());
		
		boolean stop;
		do {
			// One cycle to the trial
			scheduler.stepCycle();

			try {
				Thread.sleep(SLEEP_BETWEEN_CYCLES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Do all after-cycle tasks
			for (ExperimentTask task : afterCycleTasks)
				task.perform(getUniverse());
			// // Check all stop conds
			stop = false;
			for (StopCondition sc : stopConds)
				stop = stop || sc.experimentFinished();
		} while (!stop);

		finalizeModel(model);
	}

	public abstract void loadConditions();

	public abstract void loadAfterCycleTasks();
	
	public abstract void loadInitialTasks();

	public abstract NslModel initModel();

	public abstract void finalizeModel(NslModel model);

	@Override
	public String toString() {
		return name;
	}

	public void addInitialTask(ExperimentTask t) {
		initialTasks.add(t);
	}
	
	public void addAfterCycleTask(ExperimentTask t) {
		afterCycleTasks.add(t);
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

	public ExperimentUniverse getUniverse() {
		return universe;
	}

	public void setUniverse(ExperimentUniverse world) {
		this.universe = world;
	}

	public IRobot getRobot() {
		return robot;
	}

	public void setRobot(IRobot robot) {
		this.robot = robot;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
	
}
