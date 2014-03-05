package edu.usf.ratsim.experiment;

import java.util.Collection;
import java.util.Hashtable;

import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;

import org.w3c.dom.Element;

import edu.usf.ratsim.experiment.loggers.LoggerFactory;
import edu.usf.ratsim.experiment.stopcondition.ConditionFactory;
import edu.usf.ratsim.experiment.stopcondition.StopCondition;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.experiment.task.TaskFactory;
import edu.usf.ratsim.robot.virtual.VirtualExpUniverse;
import edu.usf.ratsim.support.Configuration;
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

	private String name;
	private Collection<StopCondition> stopConds;
	private Collection<ExperimentTask> initialTasks;
	private Collection<ExperimentTask> afterCycleTasks;
	private Collection<ExperimentTask> afterTrialTasks;
	private ExperimentUniverse universe;
	private Collection<ExperimentLogger> afterCycleloggers;
	private Collection<ExperimentLogger> afterTrialloggers;
	private ExpSubject subject;
	private String rep;
	private String group;

	public Trial(ElementWrapper trialNode, Hashtable<String, Point4f> points,
			String group, ExpSubject subject, int rep) {
		super();
		// Trial is identified by its logpath
		this.name = trialNode.getChildText(STR_NAME);
		this.rep = new Integer(rep).toString();
		this.subject = subject;
		this.group = group;
		this.universe = subject.getUniverse();

		// Load the trial tasks
		loadInitialTasks(trialNode.getChild(STR_INITIAL_TASKS)
				, points, subject.getModel());
		loadAfterCycleTasks(trialNode.getChild(STR_AFTER_CYCLE_TASKS)
						, points, subject.getModel());
		loadAfterTrialTasks(trialNode.getChild(STR_AFTER_TRIAL_TASKS)
				, points, subject.getModel());
		// Load the stop conditions
		loadConditions(trialNode.getChild(STR_STOP_CONDITIONS)
				, points, subject.getModel(), universe);
		// Load loggers
		loadAfterCycleLoggers(trialNode.getChild(
				STR_CYCLE_LOGGERS));
		loadAfterTrialLoggers(trialNode.getChild(
				STR_TRIAL_LOGGERS));
	}

	public void run() {
		// Lock on the subject to ensure mutual exclusion for the same rat
		// Assumes is fifo
		synchronized (getSubject()) {

			// Do all after-cycle tasks
			for (ExperimentTask task : initialTasks)
				task.perform(getUniverse(), getSubject());

			boolean stop;
			boolean sleep = Configuration.getBoolean("UniverseFrame.display");
			do {
				// One cycle to the trial
				subject.stepCycle();

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
					task.perform(getUniverse(),getSubject());
			} while (!stop);

			// After trial tasks
			for (ExperimentTask task : afterTrialTasks)
				task.perform(universe, getSubject());

			// Close file handlers
			for (ExperimentLogger logger : afterCycleloggers)
				logger.finalizeLog();

			// After trial loggers
			for (ExperimentLogger logger : afterTrialloggers) {
				logger.log(universe);
				logger.finalizeLog();
			}

			System.out.println("Trial " + getName() + " " + group + " "
					+ getSubjectName() + " " + getRep() + " finished.");
		}

	}

	public void loadConditions(ElementWrapper elementWrapper,
			Hashtable<String, Point4f> points, NslModel nslModel,
			ExperimentUniverse universe) {
		stopConds = ConditionFactory.createConditions(elementWrapper, points,
				nslModel, universe);
	}

	public void loadAfterCycleTasks(ElementWrapper elementWrapper,
			Hashtable<String, Point4f> points, NslModel nslModel) {
		afterCycleTasks = TaskFactory.createTasks(elementWrapper, points, nslModel);
	}

	public void loadAfterTrialTasks(ElementWrapper elementWrapper,
			Hashtable<String, Point4f> points, NslModel nslModel) {
		afterTrialTasks = TaskFactory.createTasks(elementWrapper, points, nslModel);
	}

	public void loadInitialTasks(ElementWrapper elementWrapper,
			Hashtable<String, Point4f> points, NslModel nslModel) {
		initialTasks = TaskFactory.createTasks(elementWrapper, points, nslModel);
	}

	public void loadAfterCycleLoggers(ElementWrapper elementWrapper) {
		afterCycleloggers = LoggerFactory.createLoggers(elementWrapper, this);
	}

	private void loadAfterTrialLoggers(ElementWrapper elementWrapper) {
		afterTrialloggers = LoggerFactory.createLoggers(elementWrapper, this);
	}

	public ExperimentUniverse getUniverse() {
		return universe;
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
