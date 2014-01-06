/* SCCS  %W--- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslMain.java
//
//////////////////////////////////////////////////////////////////////

import nslj.src.system.*;
import nslj.src.cmd.*;
import nslj.src.lang.*;
import nslj.src.math.*;
import nslj.src.display.*;
import nslj.src.exceptions.*;

/**
 * The main routine.
 * Parses the command line options, instantiates all essential 
 * components in the simulator, launches the interpreter and
 * loads the model.
 *
 * The command line options are:
 *
 * -schedulerMethod  [Select the scheduling method
 * -noDisplay        [Determines if the graphic interface should be used]
 * -stdout & -stderr [Select the display where the messages should be displayed]
 * -debug	     [Determines if the debug messages should be printed or not]
 */

public class NslMain {

    // Public variables

    /**
     * Indicates if the model has been loaded or not.
     */
          
    static public boolean TopLoaded = false;
        
    // Private members
    
    static NslInterpreter interpreter;
    static NslExecutive   executive;
    static NslScheduler   scheduler;
    static NslSystem      system; 

    static String  modelname;
    static String  schedulerMethod = "pre";
    static boolean noDisplay       = false;
    static boolean stdout          = true;
    static boolean stderr          = false;
    static int     debug           = 0;
    
    // Private methods
    
    /**
     * Parses command line arguments such as:
     *
     *     schedulerMethod, noDisplay, stdout, stderr, debug
     * 
     * @param argv The vector containing the command line arguments.
     * @throws NslParseArgumentException This exception is thrown if the user
     *         does not provide enough or correct information in the command
     *         line.
     */

    private static void parseArguments(String argv[]) 
        throws NslParseArgumentException {
    
        if (argv.length < 1) {
	    throw new NslParseArgumentException();
	}
 
	modelname=argv[0];

	for(int i=1;i<argv.length;i++) {
	
	    if (argv[i].compareTo("-stdout")==0) {
	    	    
                if(i == argv.length) {
                    System.err.println("-stdout requires an argument");
	    	    throw new NslParseArgumentException();
       		}
		i++;
		stdout = argv[i].compareTo("executive")==0;
		
            } else if(argv[i].compareTo("-stderr")==0) {

		if(i == argv.length) {
                    System.err.println("-stderr requires an argument");
	    	    throw new NslParseArgumentException();
		}
		i++;
		stderr = argv[i].compareTo("executive")==0;
		
	    } else if(argv[i].compareTo("-noDisplay")==0) {
	    
		noDisplay=true;
		
	    } else if(argv[i].compareTo("-debug")==0) {
	    
		if(i == argv.length) {
		    System.err.println("-debug requires a positive numeric level");
	    	    throw new NslParseArgumentException();
		}
		i++;
		try {
		    debug = (Integer.valueOf(argv[i])).intValue();
		} catch (Exception e) {
		    System.err.println("-debug requires an integer argument");
	    	    throw new NslParseArgumentException();
		}
		
	    } else if(argv[i].compareTo("-schedulerMethod")==0) {
	    
		if(i == argv.length) {
		    System.err.println("-schedulerMethod requires a string");
	    	    throw new NslParseArgumentException();
		}
		i++;
		try {
		    schedulerMethod=argv[i];
		} catch (Exception e) {
		    System.err.println("-schedulerMethod requires a string");
	    	    throw new NslParseArgumentException();
		}

	    } else {	    
	    	throw new NslParseArgumentException();
	    }	    
	    
	} 
    }

    
    /**
     * Sets global system parameters such as: 
     *
     *     schedulerMethod, noDisplay, debug
     *
     * @todo change the method names that begin with nsl
     */

    private static void setSystemParameters() {
		
	system.setNoDisplay(noDisplay);
	system.setDebug(debug);	
	system.setStdOut(stdout);	
	system.setStdErr(stderr);	
	system.setInterpreter(interpreter);
	system.nslSetScheduler(scheduler);
	system.nslSetSchedulerMethod(schedulerMethod);

    }


    /**
     * Add all differential / numerical methods to the system.
     * Setup the default numerical methods to run.
     *
     * @todo add Runge-kutta method.
     */
          
    private static void addDefaultDiffMethods() {

	NslDiff diff = (NslDiff) (new NslDiffEuler(system));
	
        system.addApproximationMethod(diff);
	system.nslSetApproximationMethod(diff);
    }
  

    /**
     * Add user commands to the system and interpreter.
     */
     
    private static void addDefaultCmd() {
    
	NslCmd.nslSetSystem(system);

	system.addCommand(new NslCmdInitSys());

	system.addCommand(new NslCmdInitModule());

	system.addCommand(new NslCmdInitTrainEpoch());
	system.addCommand(new NslCmdInitTrain());
	system.addCommand(new NslCmdDoTrainEpochTimes());
	system.addCommand(new NslCmdSimTrain());
	system.addCommand(new NslCmdTrain());
	system.addCommand(new NslCmdEndTrain());
	system.addCommand(new NslCmdEndTrainEpoch());

	system.addCommand(new NslCmdInitRunEpoch());
	system.addCommand(new NslCmdInitRun());
	system.addCommand(new NslCmdDoRunEpochTimes());
	system.addCommand(new NslCmdSimRun());
	system.addCommand(new NslCmdRun());
	system.addCommand(new NslCmdEndRun());
	system.addCommand(new NslCmdEndRunEpoch());

	system.addCommand(new NslCmdTrainAndRunAll());

	system.addCommand(new NslCmdStepCycle());
	system.addCommand(new NslCmdStepEpoch());
 	system.addCommand(new NslCmdStepModule());

	system.addCommand(new NslCmdCont());
	system.addCommand(new NslCmdContCycle());
	system.addCommand(new NslCmdContEpoch());
	system.addCommand(new NslCmdContModule());

	system.addCommand(new NslCmdBreak());
	system.addCommand(new NslCmdBreakCycles());
	system.addCommand(new NslCmdBreakEpochs());
	system.addCommand(new NslCmdBreakModules());

	system.addCommand(new NslCmdEndModule());

	system.addCommand(new NslCmdEndSys());

	system.addCommand(new NslCmdExit());
    }

    /**
     * Add the model to the system. All the corresponding 
     * children modules are instantiated and registered
     * in the schduler.
     *
     * @todo Make the system flexible, so it can load
     *       dynamically any compiled model.
     */
  
    private static void loadModel(String s)  {
		
	try {
 	    NslHierarchy.nslSetSystem(system);
	
	    Class classRef = Class.forName(s);
	
	    // the following line not only instantiates the
	    // model, it also instantiates the children and grandchildren
	    
	    NslModule model = (NslModule)classRef.newInstance();

	    if (noDisplay) {
	    
		system.nslDebug("NslMain","NSL 3.0 terminal version");
		
		system.addModel(model);		
		interpreter.execute();
		
                System.exit(0); // This line is never executed
		
	    } else {	

		system.nslDebug("NslMain","NSL 3.0 windows version");

		NslFrame.nslSetSystem(system);
		
		executive = new NslExecutive(system);
    		executive.show();
    		
                system.redirectMessages();                   
		system.addModel(model); 
			        
	    }
	    
	} catch (ClassNotFoundException classNotFoundError) {

	    System.err.println("NslMain [Error]: The model \""+ modelname+"\" was not found in the classpath");
	    System.err.println("NslMain [Suggestion]: Check for possible misspelling in the name of the model");

	} catch (ExceptionInInitializerError initializerError) {

	    System.err.println("NslMain [Error]: Generated while initializing \""+ modelname+".class\"");

	} catch (LinkageError linkageError) {
	
	    System.err.println("NslMain [Error]: Class linkage failed while loading \""+ modelname+".class\"");
	    System.err.println("NslMain [Suggestion]: Recompile the model and/or the nsl simulator");

	} catch (InternalError internalError) {
	
	    System.err.println("NslMain [Error]: An unexpected internal error has occurred in the Java VM");
	    System.err.println("NslMain [Error]: "+internalError.toString().substring(internalError.toString().indexOf(":")+2));
	    
	    System.exit(0);
	    
	} catch (Exception e) {
	
	    System.err.println("NslMain [Error]: \""+e.toString()+"\"  when loading/constructing model "+ modelname+".class");
	    if (debug>0) {
	    	e.printStackTrace();
	    }
 	}
    }
	
	
    /**
     * Main routine for NslMain application.
     * It instantiates essential components in the
     * simulator and launch the interpreter for user input.
     * It also adds user commands, differential methods 
     * and modules into the simulator.
     *
     * @param argv The vector containing the command line arguments.
     */

    public static void main(String argv[]) {

	try {
	    
	    parseArguments(argv);                             // Parse command line arguments	    	    
	    
            system      = new NslSystem();                    // Create System
            interpreter = new NslInterpreter(system);         // Create Interpreter
            scheduler   = new NslMultiClockScheduler(system); // Create Scheduler
	    
	    setSystemParameters();
	    
            addDefaultDiffMethods();
            addDefaultCmd();
	
	    loadModel(modelname);
	    
	} catch (NslParseArgumentException argumentError) {
		    
	    System.err.println();
	    System.err.println("Usage:   java NslMain \"ModelName\" [-options]");
	    System.err.println();
	    System.err.println("Options: -noDisplay");
	    System.err.println("         -debug <level>");
	    System.err.println("         -stdout <console|executive>");	    
	    System.err.println("         -stderr <console|executive>");	    
	    System.err.println("         -schedulerMethod <pre|post|mixed>");
	    
	} catch (Exception unrecognizedError) {
	
	    System.err.println("NslMain [Error]: \""+unrecognizedError.toString()+"\" when loading/constructing model "+ modelname+".class");
	    
	    if (debug>0) {
	        unrecognizedError.printStackTrace();
	    }
	    
	} 	
	
    }

}











