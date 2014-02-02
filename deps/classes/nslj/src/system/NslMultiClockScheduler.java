/* SCCS %W% -- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslMultiClockScheduler.java
//
//////////////////////////////////////////////////////////////////////

/**
 * NslMultiClockScheduler - a multi-clocked scheduler.
   In NslMultiClockScheduler, each module can have its
   <tt>run_step_size</tt>, or each has different run time
   interval. In the currnt implementation, the <tt>run_step_size</tt>
   must be set by <tt>nslSetRunDelta</tt> in <tt>makeinst</tt>
   in <tt>NslModule</tt>. Otherwise, the module will have system
   default time step and in order to activate this parameter,
   the whole model has to be removed from the scheduler and
   re-initialize.

   The run step size of each module should be the maximum possible
   step size without making chaotic behaviour in the model.

   Run step size of modules without execution part, like top-level
   modules, should be 0. This could improve the performance of
   simulation

 * @see NslModule#nslSetRunDelta, NslModule#nslGetRunDelta

 */
package nslj.src.system;

import nslj.src.lang.NslModule;
import nslj.src.lang.NslClass;
import nslj.src.display.*;
import java.lang.*;
import java.util.Vector;
import java.util.Enumeration;


public class NslMultiClockScheduler extends NslScheduler {

    NslSystem system;
    Vector clockRunList;   // a list of vectors, in which contains
                           // modules with a same run time step size
    Vector clockTrainList; // a list of vectors, in which contains
                           // modules with a same train time step size
                       
    // it keep checking the minimum run step size in the module.
    // it is used to warn the system the possibility that the
    // system run step size is larger than the required run step
    // size.
    double _min_step_size=Double.MAX_VALUE;
    double epsilon;
    int endModules;

    boolean firstEpoch;
    boolean stepEpoch;
    //boolean startingScheduler;

    public NslMultiClockScheduler(NslSystem sys) {
        system = sys;
	notFinished = true;
	stepEpoch = firstEpoch = false;
	cycleSuspended = epochSuspended = moduleSuspended = true;
    }

    public void createRunListRecursive(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	addModuleToClockRunList(module);

	Enumeration e = moduleChildren.elements();
	while(e.hasMoreElements()) {
	    createRunListRecursive((NslModule)e.nextElement());
	}
    }

    public void createTrainListRecursive(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	addModuleToClockTrainList(module);

	Enumeration e = moduleChildren.elements();
	while(e.hasMoreElements()) {
	    createTrainListRecursive((NslModule)e.nextElement());
	}
    }

    public void getNumModulesRecursive(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	numModules++;

	//System.err.println(""+numModules+".-"+module.nslGetName());

	Enumeration e = moduleChildren.elements();
	while(e.hasMoreElements()) {
	    getNumModulesRecursive((NslModule)e.nextElement());
	}
    }

    public int getNumModules() {
	numModules = 0;
	getNumModulesRecursive(system.nslGetModelRef());
	return numModules;
    }

    public void createSchedulerRunList() {
        clockRunList = new Vector(10,10);
	createRunListRecursive(system.nslGetModelRef());
    }

    public void createSchedulerTrainList() {
        clockTrainList = new Vector(10,10);	
	createTrainListRecursive(system.nslGetModelRef());
    }

    /**
    * Add module into the scheduler. 
    * If there exists a list of
    * modules with the same run step size as the object module,
    * the module will be added to the corresponding list. 
    * Otherwise, a list of module with that run step size will be created.

    * There is no maximum number of those lists, but keeping it
    * minimal can significantly speed up the simulation.

    * @param module - module to be added.
    * 
    */
    
    public void addModuleToClockRunList(NslModule module) {

        if(module==null)
            throw new NullPointerException();

	//numModules++;

        Enumeration E = clockRunList.elements();
        NslClockSchedulerModuleVector V;
        double stepsize = module.nslGetRunDelta();

        while (E.hasMoreElements()) {
            V = (NslClockSchedulerModuleVector)E.nextElement();
            if (V.nslGetRunDelta() == stepsize) {
                V.addElement(module);  
                return;
            }
        }
        
        // no module vector or step size is new
        if (stepsize > 0 && stepsize < _min_step_size) {
            _min_step_size = stepsize;
	    system.nslSetRunDelta(stepsize);
	}
        
        V = new NslClockSchedulerModuleVector(stepsize);
	V.addElement(module);
        clockRunList.addElement(V);
        
        return;
    }

    public void addModuleToClockTrainList(NslModule module) {

        if(module==null)
            throw new NullPointerException();

	//numModules++;

        Enumeration E = clockTrainList.elements();
        NslTrainClockSchedulerModuleVector V;
        double stepsize = module.nslGetTrainDelta();

        while (E.hasMoreElements()) {
            V = (NslTrainClockSchedulerModuleVector)E.nextElement();
            if (V.nslGetTrainDelta() == stepsize) {
                V.addElement(module);  
                return;
            }
        }
        
        // no module vector or step size is new
        if (stepsize > 0 && stepsize < _min_step_size) {
            _min_step_size = stepsize;
	    system.nslSetTrainDelta(stepsize);
	}
        
        V = new NslTrainClockSchedulerModuleVector(stepsize);
	V.addElement(module);
        clockTrainList.addElement(V);
        
        return;
    }    

    public void checkSystemStatus() {
	if (schedulerMode=='R' && system._runDeltaChanged) {
	    createSchedulerRunList(); 
	    epsilon = system.nslGetDelta()/2;
	    system._runDeltaChanged=false;
	} else if (schedulerMode=='T' && system._trainDeltaChanged) {
	    createSchedulerTrainList(); 
	    epsilon = system.nslGetDelta()/2;
	    system._trainDeltaChanged=false;
	}
    }

    /**
    * Run the active modules until time <tt>time</tt>
    * in the basic step size specified in <tt>NslSystem</tt>
    * The modules registered in the scheduler will run
    * with an interval specified by the run step size.
    */
    public void run() {
    
	double endTime;
        int  endEpochs;
        	
	NslDisplaySystemVector dsv = system.display_system_list;

	// scheduler

	//System.err.println("Starting Scheduler");
	
     //System.err.println("initModule");

	initModule();

        contEndTime    = actualEndTime    = endTime   = system.getEndTime();
	contNumEpochs  = actualNumEpochs  = endEpochs = system.getEpochs();
	contNumModules = actualNumModules = endModules = getNumModules();

	while (notFinished) {
	    try {
		//System.err.println("Cleaning variables");
		//system.initAll();
		//System.err.println("Wating for commands");
		schedulerState = 'W';
		synchronized (this) {
		    while (epochSuspended || cycleSuspended || moduleSuspended
			     || system.getEndTime() == 0.0) {
			wait(); 
			if (system.getEndTime() == 0.0) {
	    			system.continueCmd();
		      }
		    }

		    //if (startingScheduler) {
			//contEndTime    = actualEndTime    = endTime   = system.getEndTime();
			//contNumEpochs  = actualNumEpochs  = endEpochs = system.getEpochs();
			//contNumModules = actualNumModules = endModules = getNumModules();
		    //} else {
		
		    //if (actualNumModules==endModules) {
		    //	actualNumModules = endModules = getNumModules();
		    //} else 
		    if (actualNumModules<endModules) {
			endModules = actualNumModules;
		    }

		    /*if (system.endEpochChanged) {
			contNumEpochs  = actualNumEpochs  = endEpochs = system.getEpochs();
			system.endEpochChanged = false;
		    } else {*/
		    if (actualNumEpochs==endEpochs) {
		    	actualNumEpochs = endEpochs = system.getEpochs();
		    } else if (actualNumEpochs<endEpochs) {
			endEpochs = actualNumEpochs;
		    }
		    //}
		    epsilon = system.nslGetDelta()/2;
		    if (actualEndTime==endTime) {
			actualEndTime = endTime = system.getEndTime();
		    } else if (actualEndTime<endTime-epsilon) {
			endTime = actualEndTime;
		    }

		    //}
		} 
		schedulerState = 'R';
	    } catch (InterruptedException e) {  }
	  
            //System.err.println("Simulation Type: "+simulationType);
            //System.err.println("Number of modules: "+endModules);

	    system.startEpochTimer();

	    if (firstEpoch && simulationType=='E') {
		//System.err.println("initEpoch: "+endEpochs);
		initEpoch();
		firstEpoch=false;
	    } else if (simulationType=='C' || simulationType=='N') {
		if (schedulerMode=='R') {
			system.setRunEpoch(1);
		} else { 
			system.setTrainEpoch(1);
		}
		if (simulationType=='C') {
		    endEpochs = 1;
		}
	    }

            //System.err.println("Epochs: "+endEpochs);

	    while (system.getCurrentEpoch()<=endEpochs) {

	    	if (simulationType=='E'||simulationType=='C') {
            	    //System.err.println("initCycle: "+endTime);
		    initCycle();
		}

            	//System.err.println("End time: "+endTime);

		while (system.getCurTime() < endTime-epsilon) {

		    try {

			checkSystemStatus();

			system.startCycleTimer();

			oneCycle();
			system.incTime(); 

			//System.err.println("Letting all threads to reset themselves");
			//yield();
			//sleep(0);
			//System.out.println("Finished all threads to reset themselves");

			system.incCycle();
			
			//dsv.awake();							
			
			//yield();
			//sleep(0);
			
			dsv.collect();	      

			system.stopCycleTimer();

			synchronized (this) {

		    	    //System.err.println("Looking if somebody suspended inner cycle");
			    while (cycleSuspended || (contEndTime != system.getEndTime() && 
				! (system.getCurTime() < contEndTime-epsilon)) ) {
		  		wait();
	      		    }
			    if (actualEndTime<endTime-epsilon) {
				endTime = actualEndTime;
			    }
			    //System.err.println("Continuing cycle: "+endTime);
		    	}

		    } catch (InterruptedException e) { }
		}

		if (schedulerMode=='R') {
			system.incRunEpoch();
		} else { 
			system.incTrainEpoch();
		}

	    	if (simulationType=='E'||simulationType=='C') {
		    
            	    //System.err.println("endCycle");
            	    endCycle();
		    if (simulationType=='C') {
			schedulerState = 'F';
		    }
		}

		if (system.endEpochChanged) {
		    actualNumEpochs  = endEpochs = system.getEpochs();
		    if (!stepEpoch) {
			contNumEpochs  = endEpochs;
		    }
		    stepEpoch = system.endEpochChanged = false;
		}

		try {
		    synchronized (this) {
			while (epochSuspended || (contNumEpochs != system.getEpochs() && 
			    ! (system.getCurrentEpoch() <= contNumEpochs)) ) {
				System.err.println("Waiting: "+system.getEpochs()+","+contNumEpochs);
			    wait();
			}
			if (actualNumEpochs<endEpochs) {
			    endEpochs = actualNumEpochs;
			}
			contEndTime = actualEndTime = endTime = system.getEndTime();
		    }
		} catch (InterruptedException e) { }
	    }

	    if (simulationType=='E') {
		if (system.getFinishedEpochs()==system.getEpochs()) {
		    endEpoch();
            	    //System.err.println("endEpoch");
		    schedulerState = 'F';
		}
	    }

	    system.stopEpochTimer();	  	  	  	  	  	  

	    synchronized (this) {
		//simulationType ='N';
		moduleSuspended = cycleSuspended = epochSuspended = true;
		contNumEpochs  = actualNumEpochs = endEpochs = system.getEpochs();
	    }

	    if (schedulerState=='F') {
	        system.nslPrintStatistics();
		// system.nslPrintStatistics();  //99/9/2 aa
		simulationType ='N';
	    	system.continueCmd();
	    }

	}

	endModule();

    }

    /**
     * make one system run step.
     */

    public void stepCycle() { 
	int endTime = system.getFinishedCycles() + 1; // Time() + system.nslGetDelta();
	continueCycle(endTime);
    }

    public void stepCycle(int cycles) {
	int endTime = system.getFinishedCycles() + cycles; //system.nslGetDelta()*cycles;
	continueCycle(endTime);
    }

    public void stepEpoch() {      
	stepEpoch = true;     
	int end = system.getFinishedEpochs() + 1;
	continueEpoch(end);
    }

    public void stepEpoch(int epochs) {
	stepEpoch = true;     
	int end = system.getFinishedEpochs() + epochs;
	continueEpoch(end);
    }

    public void stepModule() {      
	int end = curNumModules + 1;
	continueModule(end);
    }

    public void stepModule(int modules) {
	int end = curNumModules + modules;
	continueModule(end);
    }

    public void initSys() {
	initSysRecursively(system.nslGetModelRef());
	system.initSys();
    }

    public void initModule() {
	initModuleRecursively(system.nslGetModelRef());
	system.initModule();
    }

    public void initEpoch() {
	if (schedulerMode=='R') {
	    initRunEpoch();
	} else {
	    initTrainEpoch();
	}
    }

    public void initCycle() {
	if (schedulerMode=='R') {
	    initRun();
	} else {
	    initTrain();
	}
    }

    /**
     * make one cycle through simulation.
     * For a module, if the last run time plus its step size is equal
     * to or smaller than the current system time, that module is
     * executed. Otherwise, the run command is ignored.
     */

    public void oneCycle() {
        
	if (schedulerMode=='R') {
	    runOneCycle();
	} else {
	    trainOneCycle();
	}
    }

    public void endCycle() {
	if (schedulerMode=='R') {
	    endRun();
	} else {
	    endTrain();
	}
    }

    public void endEpoch() {
	if (schedulerMode=='R') {
	    endRunEpoch();
	} else {
	    endTrainEpoch();
	}
    }

    public void endModule() {
	endModuleRecursively(system.nslGetModelRef());
	system.endModule();
    }

    public void endSys() {
	endSysRecursively(system.nslGetModelRef());
	system.endSys();
    }

    public void initTrainList() {
        if (clockTrainList == null) {
	    return;
	}

	Enumeration e = clockTrainList.elements();
	NslTrainClockSchedulerModuleVector V;
	while (e.hasMoreElements()) {
	    V = (NslTrainClockSchedulerModuleVector)e.nextElement();
	    V.initTrain();
	}
    }   

    public void initRunList() {
        if (clockRunList == null) {
	    return;
	}
	Enumeration e = clockRunList.elements();
	NslClockSchedulerModuleVector V;
	while (e.hasMoreElements()) {
	    V = (NslClockSchedulerModuleVector)e.nextElement();
	    V.initRun();
	}
    }

    public void initSysRecursively(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();

        module.initSys();

	while(e.hasMoreElements()) {
            initSysRecursively((NslModule)e.nextElement());
        }
    } 

    public void initModuleRecursively(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();

	Vector classChildren = module.nslGetClassInstancesVector();
	
	Enumeration c = classChildren.elements();
	while(c.hasMoreElements()) {
	    initClassRecursively((NslClass)c.nextElement());
	}	
	
        module.nslInitTempModule();
	module.nslInitTempTrain();
	module.nslInitTempTrainEpoch();
	module.nslInitTempRun();
	module.nslInitTempRunEpoch();
	
        module.initModule();
	
	while(e.hasMoreElements()) {
            initModuleRecursively((NslModule)e.nextElement());
        }
    }
        
    public void initClassRecursively(NslClass cl) {
	Vector classChildren = cl.nslGetClassInstancesVector();

	cl.nslInitTempClass();
	
	Enumeration c = classChildren.elements();
	while(c.hasMoreElements()) {
	    initClassRecursively((NslClass)c.nextElement());
	}
    }
    
    // ----- Train methods

    public void initTrainEpoch() {	
	simulationType = 'E';
	schedulerMode='T';
	firstEpoch=false;
	initTrainEpochRecursively(system.nslGetModelRef());
   	system.initTrainEpoch(); 
    }

    public void initTrainEpochRecursively(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();

        module.initTrainEpoch();

	while(e.hasMoreElements()) {
            initTrainEpochRecursively((NslModule)e.nextElement());
        }
    }

    /**
     * Initialize the system and current active module
     * including its child modules
     */

    public void initTrain() {

	/*if (simulationType != 'E') {
	   simulationType = 'N';
	}*/
	schedulerMode='T';

   	system.initTrain(); 

        NslModule model=system.nslGetModelRef();    

    	initTrainRecursively(model);    
	updateBuffers(model);

	system.init_displays();	
        system.init_run_char='A';  //after
	
	initTrainList();
    }

    /**
     * Initialize module <tt>module</tt> and its child modules
     * recursively.
     * @param module
     */

    public void initTrainRecursively(NslModule module) {

	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();    
	if ((system.schedulerMethod.equals("pre"))||
	    (system.schedulerMethod.equals("mixed"))) {	    

	    //module.nslInitTempTrain();
	    module.initTrain();

	    while (e.hasMoreElements()) {
	        initTrainRecursively((NslModule)e.nextElement());
	    }

	} else if (system.schedulerMethod.equals("post")) {

	    while (e.hasMoreElements()) {
	        initTrainRecursively((NslModule)e.nextElement());
	    }

	    module.nslInitTempTrain();
	    module.initTrain();
	}
    }

    public void trainOneCycle() {
        double curtime = system.getCurTime();

	Enumeration f, e = clockTrainList.elements();
	NslModule module;
	NslTrainClockSchedulerModuleVector v;
	curNumModules = 0;
	while (e.hasMoreElements() && curNumModules < endModules) {
	    v = (NslTrainClockSchedulerModuleVector)e.nextElement();
	    if (v.isRunnable(curtime)) {
		f = v.elements();
		while(f.hasMoreElements() && curNumModules < endModules) {
		    module = (NslModule)f.nextElement();
		    // Train only if the run enable flag is set
		    //System.err.println("Steping module: "+module.nslGetName());
		    if(module.nslGetTrainEnableFlag()) {
			module.simTrain();
		    }
		    curNumModules++;
		    try {
			synchronized (this) {
			    while (moduleSuspended || (contNumModules != numModules &&
				!(curNumModules < contNumModules)) ) {
				wait();
			    }
			    if (actualNumModules<endModules) {
				endModules = actualNumModules;
			    }
			}
		    } catch (InterruptedException ex) { }
		}
	    }
	    //v.simTrain(curtime);
	} 
	curNumModules = 0;
	e = clockTrainList.elements();
	
	while (e.hasMoreElements()) {
	    v = (NslTrainClockSchedulerModuleVector)e.nextElement();
	    v.updateBuffers(curtime);
	}
    }

    public void trainAll() {

	synchronized (this) {  
	    firstEpoch=true;

	    simulationType = 'E';
	    schedulerMode  = 'T';

	    reset();

	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

	system.waitScheduler();
    }

    public void trainAll(int endTime) {

	synchronized (this) {  
	    firstEpoch=true;
	    simulationType = 'E';
	    schedulerMode  = 'T';

	    reset();

	    actualNumEpochs = endTime;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

	system.waitScheduler();

    }

    public void train(char type) {
	
	synchronized (this) {  
	    simulationType = type;
	    schedulerMode  = 'T';

	    reset();

	    actualNumEpochs = 1;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

    }

    public void train(double endTime) {
	
	synchronized (this) {  
	    simulationType='N';
	    schedulerMode='T';

	    reset();

	    actualNumEpochs = 1;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    actualEndTime   = endTime;
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

    }

    public void simTrain() {

	synchronized (this) {  
	    simulationType = 'C';
	    schedulerMode  = 'T';

	    reset();

	    actualNumEpochs = 1;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

	system.waitScheduler();

    }

    public void simTrain(double endTime) {

	synchronized (this) {  
	    simulationType = 'C';
	    schedulerMode  = 'T';
	
	    reset();

	    actualNumEpochs = 1;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    actualEndTime   = endTime;
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

	system.waitScheduler();

    }

    public void endTrain() {
  
        NslModule model=system.nslGetModelRef();    

	endTrainRecursively(model);
	updateBuffers(model);
  
	system.endTrain();
  
    }

    public void endTrainRecursively(NslModule module) {

	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();    

	if ((system.schedulerMethod.equals("pre"))||
	    (system.schedulerMethod.equals("mixed"))) {

	    module.endTrain();

	    while(e.hasMoreElements()) {
	        endTrainRecursively((NslModule)e.nextElement());
	    }

	} else if (system.schedulerMethod.equals("post")) {

	    while(e.hasMoreElements()) {
	        endTrainRecursively((NslModule)e.nextElement());
	    }
	    
	    module.endTrain();
	}    
    }

    public void endTrainEpoch() {
	endTrainEpochRecursively(system.nslGetModelRef());
   	system.endTrainEpoch(); 
    }

    public void endTrainEpochRecursively(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();

        module.endTrainEpoch();

	while(e.hasMoreElements()) {
           endTrainEpochRecursively((NslModule)e.nextElement());
        }
    }

    //------ Run methods

    public void initRunEpoch() {	
	schedulerMode='R';
	simulationType = 'E';
	firstEpoch=false;
	initRunEpochRecursively(system.nslGetModelRef());
   	system.initRunEpoch(); 
    }

    public void initRunEpochRecursively(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();

        module.initRunEpoch();

	while(e.hasMoreElements()) {
            initRunEpochRecursively((NslModule)e.nextElement());
        }
    }

    /**
     * Initialize the system and current active module
     * including its child modules
     */

    public void initRun() {

	/*if (simulationType != 'E') {
	   simulationType = 'N';
	}*/
	schedulerMode='R';

   	system.initRun(); 

        NslModule model=system.nslGetModelRef();    

    	initRunRecursively(model);    
	updateBuffers(model);

	system.init_displays();	
        system.init_run_char='A';  //after
	
	initRunList();
    }

    /**
     * Initialize module <tt>module</tt> and its child modules
     * recursively.
     * @param module
     */

    public void initRunRecursively(NslModule module) {

	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();    
	if ((system.schedulerMethod.equals("pre"))||
	    (system.schedulerMethod.equals("mixed"))) {	    

	    module.nslInitTempRun();
	    module.initRun();

	    while (e.hasMoreElements()) {
	        initRunRecursively((NslModule)e.nextElement());
	    }

	} else if (system.schedulerMethod.equals("post")) {

	    while (e.hasMoreElements()) {
	        initRunRecursively((NslModule)e.nextElement());
	    }

	    // module.nslInitTempRun();
	    module.initRun();
	}
    }

    public void runOneCycle() {
        double curtime = system.getCurTime();

	Enumeration f, e = clockRunList.elements();
	NslModule module;
	NslClockSchedulerModuleVector v;
	curNumModules = 0;
	while (e.hasMoreElements() && curNumModules < endModules) {
	    v = (NslClockSchedulerModuleVector)e.nextElement();
	    if (v.isRunnable(curtime)) {
		f = v.elements();
		while(f.hasMoreElements() && curNumModules < endModules) {
		    module = (NslModule)f.nextElement();
		    // Run only if the run enable flag is set
		    //System.err.println("Steping module: "+module.nslGetName());
		    if(module.nslGetRunEnableFlag()) {
			module.simRun();
		    }
		    curNumModules++;
		    try {
			synchronized (this) {
			    while (moduleSuspended || (contNumModules != numModules &&
				!(curNumModules < contNumModules)) ) {
				wait();
			    }
			    if (actualNumModules<endModules) {
				endModules = actualNumModules;
			    }
			}
		    } catch (InterruptedException ex) { }
		}
	    }
	    //v.simRun(curtime);
	} 
	curNumModules = 0;
	e = clockRunList.elements();
	
	while (e.hasMoreElements()) {
	    v = (NslClockSchedulerModuleVector)e.nextElement();
	    v.updateBuffers(curtime);
	}
    }

    public void runAll() {

	synchronized (this) {  
	    firstEpoch=true;

	    simulationType = 'E';
	    schedulerMode  = 'R';

	    reset();

	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

	system.waitScheduler();
    }

    public void runAll(int endTime) {

	synchronized (this) {  
	    firstEpoch=true;
	    simulationType = 'E';
	    schedulerMode  = 'R';

	    reset();

	    actualNumEpochs = endTime;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

	system.waitScheduler();

    }

    public void run(char type) {
	
	synchronized (this) {  
	    simulationType = type;
	    schedulerMode  = 'R';

	    reset();

	    actualNumEpochs = 1;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

    }

    public void run(double endTime) {
	
	synchronized (this) {  
	    simulationType='N';
	    schedulerMode='R';

	    reset();

	    actualNumEpochs = 1;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    actualEndTime   = endTime;
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

    }

    public void simRun() {

	synchronized (this) {  
	    simulationType = 'C';
	    schedulerMode  = 'R';

	    reset();

	    actualNumEpochs = 1;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

	system.waitScheduler();

    }

    public void simRun(double endTime) {

	synchronized (this) {  
	    simulationType = 'C';
	    schedulerMode  = 'R';
	
	    reset();

	    actualNumEpochs = 1;
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    actualEndTime   = endTime;
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}

	system.waitScheduler();

    }

    public void endRun() {
  
        NslModule model=system.nslGetModelRef();    

	endRunRecursively(model);
	updateBuffers(model);
  
	system.endRun();
  
    }

    public void endRunRecursively(NslModule module) {

	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();    

	if ((system.schedulerMethod.equals("pre"))||
	    (system.schedulerMethod.equals("mixed"))) {

	    module.endRun();

	    while(e.hasMoreElements()) {
	        endRunRecursively((NslModule)e.nextElement());
	    }

	} else if (system.schedulerMethod.equals("post")) {

	    while(e.hasMoreElements()) {
	        endRunRecursively((NslModule)e.nextElement());
	    }
	    
	    module.endRun();
	}    
    }

    public void endRunEpoch() {
	endRunEpochRecursively(system.nslGetModelRef());
   	system.endRunEpoch(); 
    }

    public void endRunEpochRecursively(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();

        module.endRunEpoch();

	while(e.hasMoreElements()) {
           endRunEpochRecursively((NslModule)e.nextElement());
        }
    }

    public void endModuleRecursively(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

        module.endModule();

	Enumeration e = moduleChildren.elements();
	while(e.hasMoreElements()) {
            endModuleRecursively((NslModule)e.nextElement());
        }
    } 

    public void endSysRecursively(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();

	Enumeration e = moduleChildren.elements();

        module.endSys();

	while(e.hasMoreElements()) {
            endSysRecursively((NslModule)e.nextElement());
        }
    } 


    public void trainAndRunAll() {

	trainAll();
	try {
	    yield();
	    sleep(0);
	} catch(InterruptedException e) {
	}
	runAll();

    }
	


    /**
     * Update module <tt>module</tt> and its child modules
     * recursively.
     * @param module
     */

    public void updateBuffers(NslModule module) {
	Vector moduleChildren = module.nslGetModuleChildrenVector();
	NslModule child;

	Enumeration e = moduleChildren.elements();

	module.nslUpdateBuffers();
	while(e.hasMoreElements()) {
	    child = (NslModule)e.nextElement();
	    updateBuffers(child);
	}
    } 

    public void breakAll() {
	synchronized (this) {  
	    moduleSuspended = true;
	    cycleSuspended = true;
	    epochSuspended = true;
	}
    }

    public void breakModules() {
	synchronized (this) {  
	    moduleSuspended = true;
	}
    }

    public void breakCycles() {
	synchronized (this) {  
	    cycleSuspended = true;
	}
    }

    public void breakEpochs() {
	synchronized (this) {  
	    epochSuspended = true;
	}
    }

    public void continueAll() {
	synchronized (this) {  
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}
    }

    public void continueCycle() {
	synchronized (this) {  
	    contNumEpochs   = system.getFinishedEpochs() + 1;
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}
    }

    public void continueEpoch() {
	synchronized (this) {  
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}
    }

    public void continueModule() {
	synchronized (this) {  
	    contNumEpochs   = system.getFinishedEpochs() + 1;
	    epochSuspended  = false;

	    contEndTime     = system.getCurTime() + system.nslGetDelta();
	    cycleSuspended  = false;

	    contNumModules  = curNumModules+1;
	    moduleSuspended = false;

	    notify();
	}
    }

    public void continueAll(double endTime) {

	synchronized (this) {  
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = endTime;
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}
    }

    public void continueCycle(int endCycle) {

	synchronized (this) {  
	    contNumEpochs   = system.getEpochs();
	    epochSuspended  = false;

	    contEndTime     = endCycle*system.nslGetDelta();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}
    }

    public void continueEpoch(int endEpochs) {
	synchronized (this) {  
	    contNumEpochs   = endEpochs;
	    epochSuspended  = false;

	    contEndTime     = system.getEndTime();
	    cycleSuspended  = false;

	    contNumModules  = numModules;
	    moduleSuspended = false;

	    notify();
	}
    }

    public void continueModule(int end) {
	synchronized (this) {  
	    contNumEpochs   = system.getFinishedEpochs() + 1;
	    epochSuspended  = false;

	    contEndTime     = system.getCurTime() + system.nslGetDelta();
	    cycleSuspended  = false;

	    contNumModules  = end;
	    moduleSuspended = false;

	    notify();
	}
    }

    /**
     * reset the scheduler, clear all nslConnection and modules in the scheduler
     * ready for next model to run.
     */

    public void reset() {

	if (schedulerMode=='R') {
	    if (schedulerState == 'W') {
   	    	system.initRun(); 
	    	system.init_displays();	
            	system.init_run_char='A';  //after
	    	initRunList();
	    } else {
		if (simulationType=='E') {
		    initRunEpoch();
		    initRun();
		} else if (simulationType=='C') {
		    initRun();
		}		
	    }
	} else {
	    if (schedulerState == 'W') {
   	    	system.initTrain(); 
	    	system.init_displays();	
            	system.init_run_char='A';  //after
	    	initTrainList();
	    } else {
		if (simulationType=='E') {
		    initTrainEpoch();
		    initTrain();
		} else if (simulationType=='C') {
		    initTrain();
		}		
	    }
	}

	/*synchronized (this) {  
	    moduleSuspended = false;
	    cycleSuspended  = false;
	    epochSuspended  = false;
	    resetScheduler  = true;
            notify();
	}*/

        //system.continueCmd();

/*        Enumeration E = clock_list.elements();
        NslClockSchedulerModuleVector V;
            while (E.hasMoreElements()) {
                V = (NslClockSchedulerModuleVector)E.nextElement();
                V.reset();
                }
        clock_list.removeAllElements();
        system.cur_time = 0.0;*/
    }
  
}
