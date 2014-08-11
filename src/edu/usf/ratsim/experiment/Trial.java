package edu.usf.ratsim.experiment;

import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;
import edu.usf.ratsim.experiment.loggers.LoggerFactory;
import edu.usf.ratsim.experiment.plot.ExperimentPlotter;
import edu.usf.ratsim.experiment.plot.PlottingFactory;
import edu.usf.ratsim.experiment.stopcondition.ConditionFactory;
import edu.usf.ratsim.experiment.stopcondition.StopCondition;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.experiment.task.TaskFactory;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Debug;
import edu.usf.ratsim.support.ElementWrapper;

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
public class Trial implements Runnable {
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

	private static final long SLEEP_BETWEEN_CYCLES = 30;
	private static final String STR_INITIAL_TASKS = "initialTasks";
	private static final String STR_AFTER_CYCLE_TASKS = "afterCycleTasks";
	private static final String STR_AFTER_TRIAL_TASKS = "afterTrialTasks";
	private static final String STR_STOP_CONDITIONS = "stopConditions";
	private static final String STR_CYCLE_LOGGERS = "afterCycleLoggers";
	private static final String STR_TRIAL_LOGGERS = "afterTrialLoggers";
	private static final String STR_PLOTTING_STR = "plotters";

	private String name;
	private Collection<StopCondition> stopConds;
	private Collection<ExperimentTask> initialTasks;
	private Collection<ExperimentTask> afterCycleTasks;
	private Collection<ExperimentTask> afterTrialTasks;
	private Collection<ExperimentLogger> afterCycleloggers;
	private Collection<ExperimentLogger> afterTrialloggers;
	private ExpSubject subject;
	private String rep;
	private String group;
	private ElementWrapper trialNode;
	private Hashtable<String, Point4f> points;
	private Collection<ExperimentPlotter> plotters;

	public Trial(ElementWrapper trialNode, Hashtable<String, Point4f> points,
			String group, ExpSubject subject, int rep) {
		super();
		// Trial is identified by its logpath
		this.name = trialNode.getChildText(STR_NAME);
		this.rep = new Integer(rep).toString();
		this.subject = subject;
		this.group = group;
		this.trialNode = trialNode;
		this.points = points;

	}

	public void run() {
		if (Debug.pressEnterBeforeTrial) {
			// Lock before starting
			try {
				System.out.println("Press enter to continue");
				System.in.read();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		

		// Load the trial tasks
		loadInitialTasks(trialNode.getChild(STR_INITIAL_TASKS), points,
				subject.getModel());
		loadAfterCycleTasks(trialNode.getChild(STR_AFTER_CYCLE_TASKS), points,
				subject.getModel());
		loadAfterTrialTasks(trialNode.getChild(STR_AFTER_TRIAL_TASKS), points,
				subject.getModel());
		// Load the stop conditions
		loadConditions(trialNode.getChild(STR_STOP_CONDITIONS), points,
				subject.getModel(), subject.getUniverse());
		// Load loggers
		loadAfterCycleLoggers(trialNode.getChild(STR_CYCLE_LOGGERS));
		loadAfterTrialLoggers(trialNode.getChild(STR_TRIAL_LOGGERS));

		// Lock on the subject to ensure mutual exclusion for the same rat
		// Assumes is fifo
		synchronized (getSubject()) {

			// Do all after-cycle tasks
			for (ExperimentTask task : initialTasks)
				task.perform(getUniverse(), getSubject());

			if (Debug.sleepBeforeStart)
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			boolean stop;
			boolean sleep = Configuration.getBoolean("UniverseFrame.display");
			do {
				// One cycle to the trial
				subject.stepCycle();
				if (Debug.printEndCycle)
					System.out.println("End of cycle");

				if (sleep && !name.equals("training")) {
					try {
						Thread.sleep(SLEEP_BETWEEN_CYCLES);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// Run the loggers
				for (ExperimentLogger logger : afterCycleloggers)
					logger.log(getUniverse());
				// // Check all stop conds
				stop = false;
				for (StopCondition sc : stopConds)
					stop = stop || sc.experimentFinished();
				// Do all after-cycle tasks
				for (ExperimentTask task : afterCycleTasks)
					task.perform(getUniverse(), getSubject());
			} while (!stop);

			// After trial tasks
			for (ExperimentTask task : afterTrialTasks)
				task.perform(subject.getUniverse(), getSubject());

			// Close file handlers
			for (ExperimentLogger logger : afterCycleloggers)
				logger.finalizeLog();

			// After trial loggers
			for (ExperimentLogger logger : afterTrialloggers) {
				logger.log(subject.getUniverse());
				logger.finalizeLog();
			}

			System.out.println("Trial " + getName() + " " + group + " "
					+ getSubjectName() + " " + getRep() + " finished.");

			// Load plotters
			if (trialNode.getChild(STR_PLOTTING_STR) != null)
				loadPlottingTasks(trialNode.getChild(STR_PLOTTING_STR), points,
						subject.getModel());
			for (ExperimentPlotter p : plotters)
				p.plot();
		}

	}

	private void loadPlottingTasks(ElementWrapper elementWrapper,
			Hashtable<String, Point4f> points2, NslModel model) {
		plotters = PlottingFactory.createPlottingTasks(elementWrapper);

	}

	public void loadConditions(ElementWrapper elementWrapper,
			Hashtable<String, Point4f> points, NslModel nslModel,
			ExperimentUniverse universe) {
		stopConds = ConditionFactory.createConditions(elementWrapper, points,
				nslModel, universe);
	}

	public void loadAfterCycleTasks(ElementWrapper elementWrapper,
			Hashtable<String, Point4f> points, NslModel nslModel) {
		afterCycleTasks = TaskFactory.createTasks(elementWrapper, points, this);
	}

	public void loadAfterTrialTasks(ElementWrapper elementWrapper,
			Hashtable<String, Point4f> points, NslModel nslModel) {
		afterTrialTasks = TaskFactory.createTasks(elementWrapper, points, this);
	}

	public void loadInitialTasks(ElementWrapper elementWrapper,
			Hashtable<String, Point4f> points, NslModel nslModel) {
		initialTasks = TaskFactory.createTasks(elementWrapper, points, this);
	}

	public void loadAfterCycleLoggers(ElementWrapper elementWrapper) {
		afterCycleloggers = LoggerFactory.createLoggers(elementWrapper, this);
	}

	private void loadAfterTrialLoggers(ElementWrapper elementWrapper) {
		afterTrialloggers = LoggerFactory.createLoggers(elementWrapper, this);
	}

	public ExperimentUniverse getUniverse() {
		return subject.getUniverse();
	}

	public String getRep() {
		return rep;
	}

	public String getName() {
		return name;
	}

	public String getSubjectName() {
		return getSubject().getName();
	}

	public String getGroup() {
		return group;
	}

	public String toString() {
		return name;
	}
}
