package experiment;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.io.FileUtils;

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
 * Autor: Gonzalo Tejera, Martin Llofriu
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
	public static final String STR_STARTS = "start";
	private static final String STR_MAZE = "maze";
	
	private static final long SLEEP_BETWEEN_CYCLES = 000;

	private String name;
	private Collection<StopCondition> stopConds;
	private Collection<ExperimentTask> initialTasks;
	private Collection<ExperimentTask> afterCycleTasks;
	private Map<String, String> params;
	private ExperimentUniverse universe;
	private LinkedList<ExperimentLogger> loggers;
	private String logPath;
	private ExpSubject subject;

	public Trial(Map<String, String> params, ExpSubject subject, String trialLogPath) {
		super();
		this.setParams(params);
		// Trial is identified by its logpath
		this.name = trialLogPath;
		this.subject = subject;
		
		// Set the log path adding the individual name
		setLogPath(trialLogPath);

		setupLogDir(trialLogPath);
		
		setUniverse(ExpUniverseFactory.getUniverse());
		
		stopConds = new LinkedList<StopCondition>();
		// Add default stop condition - time constraints
		int times = Integer.parseInt(params.get(STR_TIME));
		addStopCond(new TimeStop(times));

		initialTasks = new LinkedList<ExperimentTask>();
		afterCycleTasks = new LinkedList<ExperimentTask>();
		
		loggers = new LinkedList<ExperimentLogger>();

		// Set the maze to execute
		Configuration.setProperty("Experiment.MAZE_FILE",
				getParams().get(Trial.STR_MAZE));		
	}

	private void setUniverse(ExperimentUniverse universe) {
		this.universe = universe;
	}

	private void setupLogDir(String trialLogPath) {
		// Create directories
		File dir = new File(trialLogPath + File.separator);
		dir.mkdirs();
		
		// Copy maze file to log directory for plot purposes
		File maze = new File(params.get(STR_MAZE));
		File mazeCopy = new File(trialLogPath + File.separator + "maze.xml");
		try {
			FileUtils.copyFile(maze, mazeCopy);
		} catch (IOException e) {
			System.out.println("Could not copy maze file");
			e.printStackTrace();
		}
		
		// Copy config file for reproducibilty		
		File config = new File(Configuration.PROP_FILE);
		File configCopy = new File(trialLogPath + File.separator + "config.properties");
		try {
			FileUtils.copyFile(config, configCopy);
		} catch (IOException e) {
			System.out.println("Could not copy prop file");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// Load the trial tasks
		loadInitialTasks();
		loadAfterCycleTasks();
		// Load the stop conditions
		loadConditions();
		// Load loggers
		loadLoggers();
		
		// Do all after-cycle tasks
		for (ExperimentTask task : initialTasks)
			task.perform(getUniverse());

		boolean stop;
		do {
			// One cycle to the trial
			subject.stepCycle();

//			try {
//				Thread.sleep(SLEEP_BETWEEN_CYCLES);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			// Run the loggers
			for (ExperimentLogger logger : loggers)
				logger.log(getUniverse());
			// Do all after-cycle tasks
			for (ExperimentTask task : afterCycleTasks)
				task.perform(getUniverse());
			// // Check all stop conds
			stop = false;
			for (StopCondition sc : stopConds)
				stop = stop || sc.experimentFinished();
		} while (!stop);

	}

	public ExperimentUniverse getUniverse() {
		return universe;
	}

	public abstract void loadConditions();

	public abstract void loadAfterCycleTasks();

	public abstract void loadInitialTasks();
	
	public abstract void loadLoggers();

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
	
	public void addLogger(ExperimentLogger logger) {
		loggers.add(logger);
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

}
