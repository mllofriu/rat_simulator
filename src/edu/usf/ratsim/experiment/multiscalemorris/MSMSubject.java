package edu.usf.ratsim.experiment.multiscalemorris;

import java.util.List;

import org.w3c.dom.NodeList;

import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModel;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslSystem;
import edu.usf.ratsim.experiment.ExpSubject;
import edu.usf.ratsim.experiment.NslSequentialScheduler;
import edu.usf.ratsim.nsl.modules.ActionPerformerVote;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.nsl.modules.qlearning.QLUpdateValue;
import edu.usf.ratsim.robot.virtual.VirtualExpUniverse;
import edu.usf.ratsim.robot.virtual.VirtualRobot;

public class MSMSubject implements ExpSubject {

	private static final Object STR_NUMLAYERS = "numLayers";
	private MSMModel model;
	private NslSequentialScheduler scheduler;
	private String name;
	private VirtualExpUniverse universe;
	private VirtualRobot robot;

	public MSMSubject(String name, NodeList params) {
		this.name = name;

		NslSystem system = new NslSystem(); // Create System
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

		// Try to workaround J3d 1.3.1 race condition
		synchronized (MSMSubject.class){
			System.out.println("Creating universe");
			universe = new VirtualExpUniverse();
			robot = new VirtualRobot(universe);
			System.out.println("Init model");
		}
		
		// Search for number of layers param
		int numLayers = 1;
		for (int i = 0; i < params.getLength(); i++)
			if(params.item(i).getNodeName().equals(STR_NUMLAYERS))
				numLayers = Integer.parseInt(params.item(i).getTextContent());
		System.out.println(numLayers);
		model = new MSMModel("MSMHabituationModel", (NslModel) null, numLayers, robot,
				universe);

		// Load it into nsl
		system.addModel(model);

		// init Run epochs
		scheduler.initRun();
	}

	public VirtualExpUniverse getUniverse() {
		return universe;
	}

	public VirtualRobot getRobot() {
		return robot;
	}

	public void stepCycle() {
		scheduler.stepCycle();
	}

	public String getName() {
		return name;
	}

	public ActionPerformerVote getActionPerformer() {
		return model.getActionPerformer();
	}

	public List<QLUpdateValue> getQLValUpdaters() {
		return model.getQLValUpdaters();
	}

	public List<ArtificialPlaceCellLayer> getPCLLayers() {
		return model.getPCLLayers();
	}

	public List<QLSupport> getQLDatas() {
		return model.getQLDatas();
	}

}
