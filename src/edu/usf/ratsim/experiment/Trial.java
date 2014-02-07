package edu.usf.ratsim.experiment;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import edu.usf.ratsim.support.Configuration;

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
	public ExpSubject getSubject() {
		return subject;
	}

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
	
	private static final long SLEEP_BETWEEN_CYCLES = 100;

	private String name;
	private Collection<StopCondition> stopConds;
	private Collection<ExperimentTask> initialTasks;
	private Collection<ExperimentTask> afterCycleTasks;
	private Collection<ExperimentTask> afterTrialTasks;
	private Map<String, String> params;
	private ExperimentUniverse universe;
	private LinkedList<ExperimentLogger> loggers;
	private ExpSubject subject;
	private String rep;

	public Trial(Map<String, String> params, ExpSubject subject, int rep) {
		super();
		this.setParams(params);
		// Trial is identified by its logpath
		this.name = params.get(STR_NAME);
		this.rep = new Integer(rep).toString();
		this.subject = subject;
		
		setUniverse(subject.getUniverse());
		
		stopConds = new LinkedList<StopCondition>();
		// Add default stop condition - time constraints
		int times = Integer.parseInt(params.get(STR_TIME));
		addStopCond(new TimeStop(times));

		initialTasks = new LinkedList<ExperimentTask>();
		afterCycleTasks = new LinkedList<ExperimentTask>();
		afterTrialTasks = new LinkedList<ExperimentTask>();
		
		loggers = new LinkedList<ExperimentLogger>();

		// Set the maze to execute
		Configuration.setProperty("Experiment.MAZE_FILE",
				getParams().get(Trial.STR_MAZE));		
	}

	private void setUniverse(ExperimentUniverse universe) {
		this.universe = universe;
	}
	
	public void run() {
		// Lock on the subject to ensure mutual exclusion for the same rat
		// Assumes is fifo
		synchronized (getSubject()) {
			// Load the trial tasks
			loadInitialTasks();
			loadAfterCycleTasks();
			loadAfterTrialTasks();
			// Load the stop conditions
			loadConditions();
			// Load loggers
			loadLoggers();
			
			// Do all after-cycle tasks
			for (ExperimentTask task : initialTasks)
				task.perform(getUniverse());

			boolean stop;
			boolean sleep = Configuration.getBoolean("UniverseFrame.display");
			do {
				// One cycle to the trial
				subject.stepCycle();

				if (sleep){
					try {
						Thread.sleep(SLEEP_BETWEEN_CYCLES);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
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

			// After trial tasks
			for (ExperimentTask task : afterTrialTasks)
				task.perform(universe);
			
			// Close file handlers
			for (ExperimentLogger logger : loggers)
				logger.finalizeLog();
			
			System.out.println("Trial " + getName() + " " + getSubjectName() +
					" " + getRep() + " finished.");
		}
		
	}

	public ExperimentUniverse getUniverse() {
		return universe;
	}

	public abstract void loadConditions();

	public abstract void loadAfterCycleTasks();
	
	public abstract void loadAfterTrialTasks();

	public abstract void loadInitialTasks();
	
	public abstract void loadLoggers();

	
	public String toString() {
		return name;
	}

	public void addInitialTask(ExperimentTask t) {
		initialTasks.add(t);
	}

	public void addAfterCycleTask(ExperimentTask t) {
		afterCycleTasks.add(t);
	}

	public void addAfterTrialTask(ExperimentTask t){
		afterTrialTasks.add(t);
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

	public String getRep() {
		return rep;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubjectName() {
		return getSubject().getName();
	}

}
