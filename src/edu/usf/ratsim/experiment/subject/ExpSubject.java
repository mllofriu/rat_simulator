package edu.usf.ratsim.experiment.subject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModel;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslSystem;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.NslSequentialScheduler;
import edu.usf.ratsim.experiment.model.ModelFactory;
import edu.usf.ratsim.experiment.subject.initializer.SubInitializerFactory;
import edu.usf.ratsim.experiment.subject.initializer.SubjectInitializer;
<<<<<<< HEAD
import edu.usf.ratsim.experiment.universe.UniverseFactory;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.robot.RobotFactory;
import edu.usf.ratsim.robot.naorobot.GlobalCameraUniv;
=======
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.robot.RobotFactory;
>>>>>>> 9a9cd5d65e743e12727fe9c647f1a62190880224
import edu.usf.ratsim.robot.virtual.VirtualExpUniverse;
import edu.usf.ratsim.support.Configuration;
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
	private String group;
	private ElementWrapper params;
	private IRobot robot;
	private boolean initialezed;
	private String mazeFile;
	private NslInterpreter interpreter;

	public ExpSubject(String name, String group, ElementWrapper params, String mazeFile) {
		this.name = name;
		this.group = group;
		
		this.params = params;
		
		initialezed = false;
		this.mazeFile = mazeFile;
	}
	
	public void initModel() {
		properties = new HashMap<String, Object>();

		initNSL();

//		System.out.println("Initializing model for subject " + getName());
<<<<<<< HEAD
//		universe = new VirtualExpUniverse(mazeFile);
		universe = UniverseFactory.getUniverse(mazeFile);
=======
		universe = new VirtualExpUniverse(mazeFile);
>>>>>>> 9a9cd5d65e743e12727fe9c647f1a62190880224
		IRobot robot = RobotFactory.getRobot(
		Configuration.getString("Reflexion.Robot"), universe);
		model = ModelFactory.createModel(params.getChild(STR_MODEL), robot, universe);

		// Load it into nsl
		system.addModel(model);

		// Create and run subject initializers
		List<ElementWrapper> initializersList = params.getChildren(STR_INITIALIZERS);
		Collection<SubjectInitializer> initializers = SubInitializerFactory
				.createInitializer(initializersList);
		for (SubjectInitializer si : initializers)
			si.initializeSubject(this);

		// init Run epochs
		scheduler.initRun();
		
		initialezed = true;
	}

	public void initNSL() {
		system = new NslSystem(); // Create System
		
		interpreter = new NslInterpreter(system); // Create
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public boolean isInitialized() {
		return initialezed;
	}
	
	public void disposeInterp(){
		try {
			interpreter.executive.disposeInterpreter();
		} catch (Exception e){
			System.err.println("Exception when trying to dispose TCL interpreter");
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		
//		scheduler.join();
//
//		scheduler.setSystem(null);
//		scheduler = null;
//		system.scheduler = null;
//		system.setInterpreter(null);
//		system = null;
//		NslHierarchy.system = null;
		model = null;
		universe = null;
		robot = null;
		
//		initNSL();
		
		
		
		
//		System.out.println("Finalized subject");
	}

	public void destroyUniv() {
		universe.dispose();
		universe = null;
	}
	
}
