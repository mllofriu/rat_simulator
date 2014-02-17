package edu.usf.ratsim.experiment;

import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModel;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslSystem;

import org.w3c.dom.Element;

import edu.usf.ratsim.experiment.model.ModelFactory;
import edu.usf.ratsim.experiment.model.MultiScaleMorrisModel;
import edu.usf.ratsim.robot.virtual.VirtualExpUniverse;
import edu.usf.ratsim.robot.virtual.VirtualRobot;

public class ExpSubject {

	private NslModel model;
	private NslSequentialScheduler scheduler;
	private String name;
	private VirtualExpUniverse universe;
	private VirtualRobot robot;
	private NslSystem system;

	public ExpSubject(String name, Element modelNode) {
		this.name = name;

		initNSL();

		// Try to workaround J3d 1.3.1 race condition
		synchronized (MultiScaleMorrisModel.class) {
			System.out.println("Creating universe");
			universe = new VirtualExpUniverse();
			robot = new VirtualRobot(universe);
			System.out.println("Init model");
		}

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

	public VirtualExpUniverse getUniverse() {
		return universe;
	}

	public VirtualRobot getRobot() {
		return robot;
	}

	public NslModel getModel() {
		return model;
	}
}
