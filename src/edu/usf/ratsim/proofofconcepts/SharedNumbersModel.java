package edu.usf.ratsim.proofofconcepts;

import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslSystem;
import edu.usf.ratsim.experiment.NslSequentialScheduler;

public class SharedNumbersModel extends NslModel {

	private SharedNumberModule num2;
	private SharedNumberModule num1;

	public SharedNumbersModel(String name, NslModule parent) {
		super(name, parent);
		
		num1 = new SharedNumberModule("num1", this);
		num2 = new SharedNumberModule("num2", this);
	}
	
	public void makeConn() {
		nslConnect(num1, "num0",
				num2, "num0");
		
	}

	private static NslSequentialScheduler scheduler;
	private static NslSystem system;

	public static void main(String[] args) {

		
		initNSL();

		// Load it into nsl
		SharedNumbersModel model = new SharedNumbersModel("sharedModel", (NslModule) null);
		system.addModel(model);

		// init Run epochs
		scheduler.initRun();
		
		
		while (true){
			scheduler.stepCycle();
		}
	}
	
	

	private static void initNSL() {
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

}
