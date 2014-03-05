package edu.usf.ratsim.experiment.subject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModel;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslSystem;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.NslSequentialScheduler;
import edu.usf.ratsim.experiment.model.ModelFactory;
import edu.usf.ratsim.experiment.model.MultiScaleModel;
import edu.usf.ratsim.experiment.subject.initializer.SubInitializerFactory;
import edu.usf.ratsim.experiment.subject.initializer.SubjectInitializer;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;

public class ExpSubject {

	private static final String STR_INITIALIZERS = "initializers";
	private static final String STR_MODEL = "model";
	private NslModel model;
	private NslSequentialScheduler scheduler;
	private String name;
	private NslSystem system;
	private ExperimentUniverse universe;
	private Map<String, Object> properties;

	public ExpSubject(String name, IRobot robot, ExperimentUniverse universe,
			ElementWrapper params) {
		this.name = name;
		this.universe = universe;

		properties = new HashMap<String, Object>();

		initNSL();

		model = ModelFactory.createModel(params.getChild(STR_MODEL), robot, universe);

		// Load it into nsl
		system.addModel(model);

		// Create and run subject initializers
		List<ElementWrapper> initializersList = params.getDirectChildren(STR_INITIALIZERS);
		Collection<SubjectInitializer> initializers = SubInitializerFactory
				.createInitializer(initializersList);
		for (SubjectInitializer si : initializers)
			si.initializeSubject(this);

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

	public void setProperty(String key, Object object) {
		properties.put(key, object);
	}

	public Object getProperty(String key) {
		return properties.get(key);
	}
}
