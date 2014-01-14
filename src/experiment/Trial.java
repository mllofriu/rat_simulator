package experiment;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import nslj.src.lang.NslModel;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslMultiClockScheduler;
import nslj.src.system.NslSystem;

/*
 * SimulationItem.java
 * Este modulo representa un item simulable 
 * Autor: Gonzalo Tejera
 * Fecha: 11 de agosto de 2010
 */
/**
 * Successor of SimulationItem class. It models a runnable trial of a experiment.
 * @author ludo
 *
 */
public abstract class Trial implements Runnable {
	public static final int HABITUATION=0;
	public static final int TRAINING=1;
	public static final int TESTING=2;
	public static enum Type {HABITUATION, TRAINING, TESTING};
	
	public static final String STR_REPETITIONS = "reps";
	public static final String STR_TIME = "time";
	public static final String STR_NAME = "name";
		
	private int repetitions;
	private String name;
	private Collection<StopCondition> stopConds;
	private NslSystem system;
	private NslInterpreter interpreter;
	private NslMultiClockScheduler scheduler;
	private Collection<ExperimentTask> tasks;
	
	public Trial(Map<String, String> params) {
		super();
		this.name = params.get(STR_NAME);
		this.repetitions = Integer.parseInt(params.get(STR_REPETITIONS));
		
		stopConds = new LinkedList<StopCondition>();
		// Add default stop condition - time constraints
		stopConds.add(new TimeStop(Integer.parseInt(params.get(STR_TIME))));
		
		tasks = new LinkedList<ExperimentTask>();
		
		system      = new NslSystem();                    // Create System
        interpreter = new NslInterpreter(system);         // Create Interpreter
        scheduler   = new NslMultiClockScheduler(system); // Create Scheduler
        
        system.setNoDisplay(false);
    	system.setDebug(0);	
    	system.setStdOut(true);	
    	system.setStdErr(true);	
    	system.setInterpreter(interpreter);
    	system.nslSetScheduler(scheduler);
    	system.nslSetSchedulerMethod("pre");
	}

	@Override
	public void run() {
		for(int r = 0; r < repetitions; r++){
			// Create the model
			NslModel model = initModel();
			// Load it into nsl
			system.addModel(model);
			// init Run epochs
			scheduler.initRun();
			
			boolean stop; 
			do {
				// One cycle to the trial
				scheduler.stepCycle();
				// Do all after-cycle tasks
				for (ExperimentTask task : tasks)
					task.perform();
				// Check all stop conds
				stop = false;
				for (StopCondition sc : stopConds)
					stop = stop || sc.experimentFinished();
			} while (!stop);
			
			finalizeModel(model);
		}
	}

	public abstract NslModel initModel();
	
	public abstract void finalizeModel(NslModel model);

	@Override
	public String toString(){
		return name;
	}
	
	public void addTask(ExperimentTask t){
		tasks.add(t);
	}
	
	public void addStopCond(StopCondition sc){
		stopConds.add(sc);
	}
}
