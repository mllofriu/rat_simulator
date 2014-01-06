/* SCCS  @(#)NslCmdStepEpoch.java	1.2---09/01/99--00:14:45 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdStepEpoch.java
//
//////////////////////////////////////////////////////////////////////

/**
 * Run current / a specific model/module for a single or definite
 * number of steps
 */

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdStepEpoch extends NslCmd {

    public NslCmdStepEpoch() {
	_name = "stepEpoch";
	_simple_help_string = "stepEpoch [numberOfSteps]";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : stepEpoch");
	System.out.println("usage: stepEpoch [numberOfSteps]");
	System.out.println("The model would be run for numberOfSteps cycles");
    }

    /**
     * Run the model in the current context for a single step.
     * It will recursively call the child modules to run.
     */
    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.stepEpoch();
    }

    public void execute(int steps) {
	NslScheduler scheduler = system.getScheduler();
	scheduler.stepEpoch(steps);
    }

    public void execute(StringTokenizer st) {
	if(st.hasMoreTokens()) {
	    String str = st.nextToken();
	    try {
		execute((Integer.valueOf(str)).intValue());
		return;
	    } catch(NumberFormatException e) {
		System.out.println("Invalid argument: "+str);
		return;
	    }
	}
    }

}
//////////////////////////////////////////////////////////////////////
