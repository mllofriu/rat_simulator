/* SCCS  @(#)NslCmdTrainAndRunAll.java	1.2---09/01/99--00:14:46 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdTrainAndRunAll.java
//
//////////////////////////////////////////////////////////////////////

/**
 * Run all current model
 */

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.lang.Double;
import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdTrainAndRunAll extends NslCmd {

    /**
     * Setup class name and help engine
     */

    public NslCmdTrainAndRunAll() {
	_name = "trainAndRunAll";
	_simple_help_string = "trainAndRunAll";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : trainAndRunAll");
	System.out.println("usage: trainAndRunAll");
	System.out.println("All modules would be train and run all epochs");
    }

    /**
     * Start to run the model in the current context until
     * simulation end time is reached. It will recursively
     * call the child modules to run.
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.trainAndRunAll();
    }

    public void execute(StringTokenizer st) {
	execute();
    }


}
//////////////////////////////////////////////////////////////////////




