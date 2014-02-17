package edu.usf.ratsim.experiment;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;

import org.w3c.dom.Element;

import com.sun.org.apache.xpath.internal.operations.And;

import edu.usf.ratsim.experiment.loggers.LoggerFactory;
import edu.usf.ratsim.experiment.stopcondition.ConditionFactory;
import edu.usf.ratsim.experiment.stopcondition.StopCondition;
import edu.usf.ratsim.experiment.stopcondition.TimeStop;
import edu.usf.ratsim.experiment.task.TaskFactory;
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
	private static final String STR_MAZE = "maze";

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

	public Trial(Element trialNode, Hashtable<String, Point4f> points,
			String group, ExpSubject subject, int rep) {
		super();
		// Trial is identified by its logpath
		this.name = trialNode.getElementsByTagName(STR_NAME).item(0)
				.getTextContent();
		this.rep = new Integer(rep).toString();
		this.subject = subject;
		this.group = group;

		universe = subject.getUniverse();

		// Set the maze to execute
		String mazeFile = trialNode.getElementsByTagName(STR_MAZE).item(0)
				.getTextContent();
		Configuration.setProperty("Experiment.MAZE_FILE", mazeFile);

		// Load the trial tasks
		loadInitialTasks(
				(Element) trialNode.getElementsByTagName(STR_INITIAL_TASKS)
						.item(0), points, subject.getModel());
		loadAfterCycleTasks(
				(Element) trialNode.getElementsByTagName(STR_AFTER_CYCLE_TASKS)
						.item(0), points, subject.getModel());
		loadAfterTrialTasks(
				(Element) trialNode.getElementsByTagName(STR_AFTER_TRIAL_TASKS)
						.item(0), points, subject.getModel());
		// Load the stop conditions
		loadConditions(
				(Element) trialNode.getElementsByTagName(STR_STOP_CONDITIONS)
						.item(0), points, subject.getModel(), universe);
		// Load loggers
		loadAfterCycleLoggers((Element) trialNode.getElementsByTagName(
				STR_CYCLE_LOGGERS).item(0));
		loadAfterTrialLoggers((Element) trialNode.getElementsByTagName(
				STR_TRIAL_LOGGERS).item(0));
	}

	public void run() {
		// Lock on the subject to ensure mutual exclusion for the same rat
		// Assumes is fifo
		synchronized (getSubject()) {

			// Do all after-cycle tasks
			for (ExperimentTask task : initialTasks)
				task.perform(getUniverse());

			boolean stop;
			boolean sleep = Configuration.getBoolean("UniverseFrame.display");
			do {
				// One cycle to the trial
				subject.stepCycle();

				if (sleep && !rep.equals("0")) {
					try {
						Thread.sleep(SLEEP_BETWEEN_CYCLES);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// Run the loggers
				for (ExperimentLogger logger : afterCycleloggers)
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
			for (ExperimentLogger logger : afterCycleloggers)
				logger.finalizeLog();
			
			// After trial loggers
			for(ExperimentLogger logger : afterTrialloggers){
				logger.log(universe);
				logger.finalizeLog();
			}

			System.out.println("Trial " + getName() + " " + group + " "
					+ getSubjectName() + " " + getRep() + " finished.");
		}

	}

	public void loadConditions(Element codintions,
			Hashtable<String, Point4f> points, NslModel nslModel,
			ExperimentUniverse universe) {
		stopConds = ConditionFactory.createConditions(codintions, points,
				nslModel, universe);
	}

	public void loadAfterCycleTasks(Element tasks,
			Hashtable<String, Point4f> points, NslModel nslModel) {
		afterCycleTasks = TaskFactory.createTasks(tasks, points, nslModel);
	}

	public void loadAfterTrialTasks(Element tasks,
			Hashtable<String, Point4f> points, NslModel nslModel) {
		afterTrialTasks = TaskFactory.createTasks(tasks, points, nslModel);
	}

	public void loadInitialTasks(Element tasks,
			Hashtable<String, Point4f> points, NslModel nslModel) {
		initialTasks = TaskFactory.createTasks(tasks, points, nslModel);
	}

	public void loadAfterCycleLoggers(Element loggers) {
		afterCycleloggers = LoggerFactory.createLoggers(loggers, this);
	}

	private void loadAfterTrialLoggers(Element loggers) {
		afterTrialloggers = LoggerFactory.createLoggers(loggers, this);
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
