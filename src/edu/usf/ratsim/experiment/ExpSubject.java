package edu.usf.ratsim.experiment;

import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModel;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslSystem;

import org.w3c.dom.Element;

import edu.usf.ratsim.experiment.model.ModelFactory;
import edu.usf.ratsim.experiment.model.MultiScaleMorrisModel;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.robot.virtual.ExpUniverseNode;
import edu.usf.ratsim.robot.virtual.VirtualExpUniverse;
import edu.usf.ratsim.robot.virtual.VirtualRobot;

public class ExpSubject {

	private NslModel model;
	private NslSequentialScheduler scheduler;
	private String name;
	private NslSystem system;
	private ExperimentUniverse universe;

	public ExpSubject(String name, IRobot robot, ExperimentUniverse universe, Element modelNode) {
		this.name = name;
		this.universe = universe;

		initNSL();

		model = (MultiScaleMorrisModel) ModelFactory.createModel(modelNode,
				robot, universe);

		// Load it into nsl
		system.addModel(model);

		// init Run epochs
		scheduler.initRun();
	}

	private void initNSL() {
		system = new NslSystem(); // Create System
		NslInterpreter interpreter = new NslInterpreter(system); // Create
																	// Interpreter
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

	/**
	 * Returns the name of the subject
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Advances one cycle in the internal model of the brain usually resulting
	 * in a decision being taken
	 */
	public void stepCycle() {
		scheduler.stepCycle();
	}

	public ExperimentUniverse getUniverse() {
		return universe;
	}

	public NslModel getModel() {
		return model;
	}

	public void setUniverse(ExperimentUniverse universe) {
		this.universe = universe;
	}
}
