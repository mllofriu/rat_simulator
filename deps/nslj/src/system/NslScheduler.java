/* SCCS  @(#)NslScheduler.java	1.12---09/01/99--00:19:51 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslScheduler.java,v $
 * Revision 1.1.1.1  1997/03/12 22:52:21  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:40  nsl
 *  Imported the Source directory
 *
*/
//
// NslScheduler.java
//
//////////////////////////////////////////////////////////////////////

/**
 * NslScheduler - The scheduler interface.
 */

package nslj.src.system;

import nslj.src.lang.NslModule;

public abstract class NslScheduler extends Thread {
    /**
    * Add module into the scheduler.
    * @param module - module to be added.
    */
    public void addModule(NslModule module) { }
    public void addModel(NslModule module) { }
    /**
    * Add nslConnection into the scheduler.
    * @param link - link to be added.
    */
    // public abstract void addConnection(/*NslConnection link*/);
    /**
    * Run the active modules until NslSytem.cur_time = <tt>time</tt>
    * @param time - end time for running
    */

    //public abstract void run(double time);

    /**
    * make one step, call the graphics engine for display updating
    */
    public abstract void stepCycle();
    public abstract void stepCycle(int numSteps);
    public abstract void stepEpoch();
    public abstract void stepEpoch(int numSteps);
    public abstract void stepModule();
    public abstract void stepModule(int numSteps);

    public abstract void initSys();
    public abstract void initModule();
    public abstract void initRunEpoch();
    public abstract void initRun();
    public abstract void runAll();
    public abstract void runAll(int endEpoch);
    public abstract void simRun();
    public abstract void simRun(double endTime);
    public abstract void run(char simulationType);
    public abstract void run(double endTime);
    public abstract void endRun();
    public abstract void endRunEpoch();
    public abstract void initTrainEpoch();
    public abstract void initTrain();
    public abstract void trainAll();
    public abstract void trainAll(int endEpoch);
    public abstract void simTrain();
    public abstract void simTrain(double endTime);
    public abstract void train(char simulationType);
    public abstract void train(double endTime);
    public abstract void endTrain();
    public abstract void endTrainEpoch();
    public abstract void trainAndRunAll();
    public abstract void endModule();
    public abstract void endSys();

    /**
    * reset the scheduler, clear all nslConnection and modules in the scheduler
      ready for next model to run.
    */
    public abstract void reset();


    // public void createSchedulerList() {}
    /**
     *  Used for controlling state of scheduler thread
     */
    public abstract void breakAll();
    public abstract void breakModules();
    public abstract void breakEpochs();
    public abstract void breakCycles();

    public abstract void continueAll();
    public abstract void continueAll(double endTime);
    public abstract void continueModule();
    public abstract void continueEpoch();
    public abstract void continueCycle();
    public abstract void continueModule(int endModule);
    public abstract void continueEpoch(int endEpoch);
    public abstract void continueCycle(int endCycle);

    public boolean simulationSuspended;
    public boolean moduleSuspended;
    public boolean epochSuspended;
    public boolean cycleSuspended;

    public boolean resetScheduler;
    public boolean notFinished;
     
    public double actualEndTime;
    //public double originalEndTime;
    public double contEndTime;

    public int actualNumEpochs;
    //public int originalNumEpochs;
    public int contNumEpochs;

    public int numModules;
    public int curNumModules;
    public int actualNumModules;

    //public int originalNumEpochs;
    public int contNumModules;
    
    public char simulationType = 'N';
    public char schedulerMode   = 'R';
    public char schedulerState = 'W';
}
