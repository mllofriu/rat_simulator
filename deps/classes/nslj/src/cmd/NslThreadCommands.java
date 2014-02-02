/* SCCS  @(#)NslThreadCommands.java	1.18---09/02/99--21:34:46 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.cmd;

import nslj.src.display.*;
import nslj.src.system.*; 
import nslj.src.nsls.struct.*; 

public class NslThreadCommands extends Thread {

    private NslInterpreter interpreter;
    private NslSystem system;
    private String command, parameter;
    static private NslExecutive executive=null;
      
    private int trainIndex;
    private int runIndex;

    public NslThreadCommands(String c, NslSystem s, 
        NslInterpreter i) {
         
        trainIndex = NslExecutive.trainIndex;
        runIndex = NslExecutive.runIndex;

	interpreter = i;
	system = s;
	command = c;
	parameter = "";
    }
    
    public NslThreadCommands(String c, String p, NslSystem s, 
        NslInterpreter i) {
        
        trainIndex = NslExecutive.trainIndex;
        runIndex = NslExecutive.runIndex;

	interpreter = i;
	system = s;
	command = c;
	parameter = " " + p;
    }
    
    public void run() {
	
	if (command.equals("Source")) {
            if (!parameter.equals("")) {
                parameter = parameter.substring(1);
                Executive.sourceFile(parameter);
	    }
	} else if (command.equals("InitSys")) {
	    //System.out.println("nsl initModule");
	    
	    interpreter.execute("initSys");

	} else if (command.equals("EndSys")) {
	    //System.out.println("nsl endSys");
	    
	    interpreter.execute("endSys");

	} else if (command.equals("InitModule")) {
	    //System.out.println("nsl initModule");
	    
	    interpreter.execute("initModule");

	} else if (command.equals("EndModule")) {
	    //System.out.println("nsl endModule");
	    
	    interpreter.execute("endModule");

	} else if (command.equals("InitRunEpoch")) {
	    //System.out.println("nsl initRunEpoch");
	    interpreter.execute("initRunEpoch");
	    Executive.initMade = true;

	    if (executive != null) {
	        executive.enable(runIndex,4); // EndRunEpoch
		executive.enable(runIndex,10); // breakEpochs
		executive.enable(runIndex,14); // continueEpoch
		executive.enable(runIndex+6,0); // stepEpoch
	    }

 	} else if (command.equals("InitRun")) {
	    //System.out.println("nsl initRun");

	    interpreter.execute("initRun");
	    Executive.initMade = true;
	    
	    if (executive != null) {
	        executive.enable(runIndex,2); // simRun
       		executive.enable(runIndex,3); // endRun 
		executive.enable(runIndex,7); // break
		executive.enable(runIndex,8); // breakModule
		executive.enable(runIndex,9); // breakCycle
		executive.enable(runIndex,11); // continue
		executive.enable(runIndex,12); // continueModule
		executive.enable(runIndex,13); // continueCycle
		executive.enable(runIndex+2,0); // stepModule
		executive.enable(runIndex+4,0); // stepCycle
	    }

	} else if (command.equals("SimRun")) {

	    if (executive != null) {
	    	executive.enable(runIndex,5); // break
	    }

	    interpreter.execute("simRun"+parameter);
	    
	} else if (command.equals("Run")) {

	    Executive.initMade = true;

	    if (executive != null) {
		executive.enable(runIndex,7); // break
		executive.enable(runIndex,8); // breakModules
		executive.enable(runIndex,9); // breakCycles
		executive.enable(runIndex,11); // continue
		executive.enable(runIndex,12); // continueModule
		executive.enable(runIndex,13); // continueCycle
		executive.enable(runIndex+2,0); // stepModule
		executive.enable(runIndex+4,0); // stepCycle
	    }

	    // System.out.println("nsl run"+parameter);
	    
	    interpreter.execute("run"+parameter);      
	    //system.waitScheduler();
      	    
    	    if (executive != null) {
		executive.disable(runIndex,2); // simRun     
       		executive.disable(runIndex,3); // endRun
		executive.disable(runIndex,7); // break
		executive.disable(runIndex,8); // breakModules
		executive.disable(runIndex,9); // breakCycles
		executive.disable(runIndex,11); // continue
		executive.disable(runIndex,12); // continueModule
		executive.disable(runIndex,13); // continueCycle
		executive.disable(runIndex+2,0); // stepModule
		executive.disable(runIndex+4,0); // stepCycle
    	    }


	    Executive.initMade = false;

	} else if (command.equals("DoRunEpochTimes")) {


	    Executive.initMade = true;

    	    if (executive != null) {
		executive.enable(runIndex,7); // break
		executive.enable(runIndex,8); // breakModules
		executive.enable(runIndex,9); // breakCycles
		executive.enable(runIndex,10); // breakEpochs
		executive.enable(runIndex,11); // continue
		executive.enable(runIndex,12); // continueModule
		executive.enable(runIndex,13); // continueCycle
		executive.enable(runIndex,14); // continueEpoch
		executive.enable(runIndex+2,0); // stepModule
		executive.enable(runIndex+4,0); // stepCycle
		executive.enable(runIndex+6,0); // stepEpoch
	    }

	    //System.out.println("nsl doRunEpochTimes"+parameter);

	    interpreter.execute("doRunEpochTimes"+parameter); 
	    //system.waitScheduler();
  	    
    	    if (executive != null) {
		executive.disable(runIndex,2); // simRun     
       		executive.disable(runIndex,3); // endRun
		executive.disable(runIndex,7); // break
		executive.disable(runIndex,8); // breakModules
		executive.disable(runIndex,9); // breakCycles
		executive.disable(runIndex,10); // breakEpochs
		executive.disable(runIndex,11); // continue
		executive.disable(runIndex,12); // continueModule
		executive.disable(runIndex,13); // continueCycle
		executive.disable(runIndex,14); // continueEpoch
		executive.disable(runIndex+2,0); // stepModule
		executive.disable(runIndex+4,0); // stepCycle
		executive.disable(runIndex+6,0); // stepEpoch
    	    }
  	    Executive.initMade = false;
	    
	} else if (command.equals("EndRun")) {

    	    if (executive != null) {
		executive.disable(runIndex,2); // simRun     
       		executive.disable(runIndex,3); // endRun
		executive.disable(runIndex,7); // break
		executive.disable(runIndex,8); // breakModules
		executive.disable(runIndex,9); // breakCycles
		executive.disable(runIndex,11); // continue
		executive.disable(runIndex,12); // continueModule
		executive.disable(runIndex,13); // continueCycle
		executive.disable(runIndex+2,0); // stepModule
		executive.disable(runIndex+4,0); // stepCycle
    	    }

	    // System.out.println("nsl endRun");
	    interpreter.execute("endRun");
	    Executive.initMade = false;

	} else if (command.equals("EndRunEpoch")) {
	    //System.out.println("nsl endRunEpoch");

	    interpreter.execute("endRunEpoch");
    	    if (executive != null) {
	        executive.disable(runIndex,4); // EndRunEpoch
		executive.disable(runIndex,10); // breakEpochs
		executive.disable(runIndex,14); // continueEpoch
		executive.disable(runIndex+6,0); // stepEpoch
	    }

	    Executive.initMade = false;
 	} else if (command.equals("Break")) {

	    // System.out.println("nsl break");
	    
	    interpreter.execute("break");

	} else if (command.equals("BreakCycles")) {

	    // System.out.println("nsl breakCycles");
	    
	    interpreter.execute("breakCycles");

	} else if (command.equals("BreakEpochs")) {

	    // System.out.println("nsl breakEpochs");
	    
	    interpreter.execute("breakEpochs");

	} else if (command.equals("BreakModules")) {

	    // System.out.println("nsl breakModules");
	    
	    interpreter.execute("breakModules");

	} else if (command.equals("Continue")) {

	    // System.out.println("nsl cont"+parameter);      
	    interpreter.execute("cont"+parameter);
      
	} else if (command.equals("ContinueEpoch")) {

	    // System.out.println("nsl contEpoch"+parameter);      
	    interpreter.execute("contEpoch"+parameter);
      
	} else if (command.equals("ContinueModule")) {

	    // System.out.println("nsl contModule"+parameter);      
	    interpreter.execute("contModule"+parameter);
      
	} else if (command.equals("ContinueCycle")) {

	    // System.out.println("nsl contCycle"+parameter);      
	    interpreter.execute("contCycle"+parameter);
      
	} else if (command.equals("InitTrainEpoch")) {

	    //System.out.println("nsl initTrainEpoch");
	    interpreter.execute("initTrainEpoch");

	    if (executive != null) {
	        executive.enable(trainIndex,4); // EndTrainEpoch
		executive.enable(trainIndex,10); // breakEpochs
		executive.enable(trainIndex,14); // continueEpoch
		executive.enable(trainIndex+6,0); // stepEpoch
	    }
	    Executive.initMade = true;

 	} else if (command.equals("InitTrain")) {
    
	    // System.out.println("nsl initTrain");
	    interpreter.execute("initTrain");  
	    Executive.initMade = true;
    
	    if (executive != null) {
	        executive.enable(trainIndex,2); // simTrain
       		executive.enable(trainIndex,3); // endTrain 
		executive.enable(trainIndex,7); // break
		executive.enable(trainIndex,8); // breakModule
		executive.enable(trainIndex,9); // breakCycle
		executive.enable(trainIndex,11); // continue
		executive.enable(trainIndex,12); // continueModule
		executive.enable(trainIndex,13); // continueCycle
		executive.enable(trainIndex+2,0); // stepModule
		executive.enable(trainIndex+4,0); // stepCycle
       	    }


      	} else if (command.equals("SimTrain")) {
	    
	    interpreter.execute("simTrain"+parameter);	    
	    
	} else if (command.equals("Train")) {

	    Executive.initMade = true;

	    if (executive != null) {
		executive.enable(trainIndex,7); // break
		executive.enable(trainIndex,8); // breakModule
		executive.enable(trainIndex,9); // breakCycle
		executive.enable(trainIndex,11); // continue
		executive.enable(trainIndex,12); // continueModule
		executive.enable(trainIndex,13); // continueCycle
		executive.enable(trainIndex+2,0); // stepModule
		executive.enable(trainIndex+4,0); // stepCycle
	    }
	    
	    // System.out.println("nsl train"+parameter);

	    interpreter.execute("train"+parameter); 	  
	    //system.waitScheduler();
    
 	    if (executive != null) {
	        executive.disable(trainIndex,2); // simTrain
       		executive.disable(trainIndex,3); // endTrain 
		executive.disable(trainIndex,7); // break
		executive.disable(trainIndex,8); // breakModule
		executive.disable(trainIndex,9); // breakCycle
		executive.disable(trainIndex,11); // continue
		executive.disable(trainIndex,12); // continueModule
		executive.disable(trainIndex,13); // continueCycle
		executive.disable(trainIndex+2,0); // stepModule
		executive.disable(trainIndex+4,0); // stepCycle
	    }
     	    Executive.initMade = false;

	} else if (command.equals("DoTrainEpochTimes")) {

	    Executive.initMade = true;

	    if (executive != null) {
		executive.enable(trainIndex,7); // break
		executive.enable(trainIndex,8); // breakModules
		executive.enable(trainIndex,9); // breakCycles
		executive.enable(trainIndex,10); // breakEpochs
		executive.enable(trainIndex,11); // continue
		executive.enable(trainIndex,12); // continueModule
		executive.enable(trainIndex,13); // continueCycle
		executive.enable(trainIndex,14); // continueEpoch
		executive.enable(trainIndex+2,0); // stepModule
		executive.enable(trainIndex+4,0); // stepCycle
		executive.enable(trainIndex+6,0); // stepEpoch
	    }

	    // System.out.println("nsl doTrainEpochTimes"+parameter);
	    
	    interpreter.execute("doTrainEpochTimes"+parameter); 
	    //system.waitScheduler();
	    
	    if (executive != null) {
		executive.disable(trainIndex,2); // simTrain
       		executive.disable(trainIndex,3); // endTrain
		executive.disable(trainIndex,7); // break
		executive.disable(trainIndex,8); // breakModules
		executive.disable(trainIndex,9); // breakCycles
		executive.disable(trainIndex,10); // breakEpochs
		executive.disable(trainIndex,11); // continue
		executive.disable(trainIndex,12); // continueModule
		executive.disable(trainIndex,13); // continueCycle
		executive.disable(trainIndex,14); // continueEpoch
		executive.disable(trainIndex+2,0); // stepModule
		executive.disable(trainIndex+4,0); // stepCycle
		executive.disable(trainIndex+6,0); // stepEpoch
	    }
  	    Executive.initMade = false;
    
	} else if (command.equals("EndTrain")) {

	    // System.out.println("nsl endTrain");
	    interpreter.execute("endTrain");  
	    Executive.initMade = false;
	    
	    if (executive != null) {
	        executive.disable(trainIndex,2); // simTrain
       		executive.disable(trainIndex,3); // endTrain 
		executive.disable(trainIndex,7); // break
		executive.disable(trainIndex,8); // breakModule
		executive.disable(trainIndex,9); // breakCycle
		executive.disable(trainIndex,11); // continue
		executive.disable(trainIndex,12); // continueModule
		executive.disable(trainIndex,13); // continueCycle
		executive.disable(trainIndex+2,0); // stepModule
		executive.disable(trainIndex+4,0); // stepCycle
	    }
	    
	} else if (command.equals("EndTrainEpoch")) {
	    //System.out.println("nsl endTrainEpoch");

	    Executive.initMade = false;

	    interpreter.execute("endTrainEpoch");
    	    if (executive != null) {
	        executive.disable(trainIndex,4); // EndTrainEpoch
		executive.disable(trainIndex,10); // breakEpochs
		executive.disable(trainIndex,14); // continueEpoch
		executive.disable(trainIndex+6,0); // stepEpoch
	    }

 	} else if (command.equals("StepCycle")) {

	    // System.out.println("nsl stepCycle "+parameter);
	    interpreter.execute("stepCycle"+parameter);

	} else if (command.equals("StepEpoch")) {

	    // System.out.println("nsl stepEpoch "+parameter);
	    interpreter.execute("stepEpoch"+parameter);

	} else if (command.equals("StepModule")) {

	    // System.out.println("nsl stepModule "+parameter);
	    interpreter.execute("stepModule"+parameter);

	} else if (command.equals("TrainAndRunAll")) {

	    Executive.initMade = false;

	    if (executive != null) {
		executive.enable(trainIndex,7); // break
		executive.enable(trainIndex,8); // breakModules
		executive.enable(trainIndex,9); // breakCycles
		executive.enable(trainIndex,10); // breakEpochs
		executive.enable(trainIndex,11); // continue
		executive.enable(trainIndex,12); // continueModule
		executive.enable(trainIndex,13); // continueCycle
		executive.enable(trainIndex,14); // continueEpoch
		executive.enable(trainIndex+2,0); // stepModule
		executive.enable(trainIndex+4,0); // stepCycle
		executive.enable(trainIndex+6,0); // stepEpoch
		executive.enable(runIndex,7); // break
		executive.enable(runIndex,8); // breakModules
		executive.enable(runIndex,9); // breakCycles
		executive.enable(runIndex,10); // breakEpochs
		executive.enable(runIndex,11); // continue
		executive.enable(runIndex,12); // continueModule
		executive.enable(runIndex,13); // continueCycle
		executive.enable(runIndex,14); // continueEpoch
		executive.enable(runIndex+2,0); // stepModule
		executive.enable(runIndex+4,0); // stepCycle
		executive.enable(runIndex+6,0); // stepEpoch
	    }
	    
	    interpreter.execute("trainAndRunAll");	    

	    if (executive != null) {
		executive.disable(trainIndex,2); // simTrain
       		executive.disable(trainIndex,3); // endTrain
		executive.disable(trainIndex,7); // break
		executive.disable(trainIndex,8); // breakModules
		executive.disable(trainIndex,9); // breakCycles
		executive.disable(trainIndex,10); // breakEpochs
		executive.disable(trainIndex,11); // continue
		executive.disable(trainIndex,12); // continueModule
		executive.disable(trainIndex,13); // continueCycle
		executive.disable(trainIndex,14); // continueEpoch
		executive.disable(trainIndex+2,0); // stepModule
		executive.disable(trainIndex+4,0); // stepCycle
		executive.disable(trainIndex+6,0); // stepEpoch
		executive.disable(runIndex,2); // simRun     
       		executive.disable(runIndex,3); // endRun
		executive.disable(runIndex,7); // break
		executive.disable(runIndex,8); // breakModules
		executive.disable(runIndex,9); // breakCycles
		executive.disable(runIndex,10); // breakEpochs
		executive.disable(runIndex,11); // continue
		executive.disable(runIndex,12); // continueModule
		executive.disable(runIndex,13); // continueCycle
		executive.disable(runIndex,14); // continueEpoch
		executive.disable(runIndex+2,0); // stepModule
		executive.disable(runIndex+4,0); // stepCycle
		executive.disable(runIndex+6,0); // stepEpoch
	    }	    
    	    Executive.initMade = false;

	} else {

	    System.out.println("Unkown nsl command: "+command);

	}
    }
    
    static public void setExecutive(NslExecutive e) {
    	executive = e;
    }
}

