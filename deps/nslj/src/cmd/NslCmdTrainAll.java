/* SCCS  @(#)NslCmdTrainAll.java	1.2---09/01/99--00:14:46 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdTrainAll.java
//
//////////////////////////////////////////////////////////////////////

/**
 * Train all current model
 */

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.lang.Double;
import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdTrainAll extends NslCmd {

    /**
     * Setup class name and help engine
     */

    public NslCmdTrainAll() {
	_name = "trainAll";
	_simple_help_string = "trainAll [endEpoch]";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : trainAll");
	System.out.println("usage: trainAll [endEpoch]");
	System.out.println("All modules would be train all until endEpoch");
    }

    /**
     * Start to run the model in the current context until
     * simulation end time is reached. It will recursively
     * call the child modules to run.
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.trainAll();
    }

    public void execute(StringTokenizer st) {
	if (st.hasMoreTokens()) {
	    String str = st.nextToken();
	    try {
		execute((Integer.valueOf(str)).intValue());
		return;
	    } catch(NumberFormatException e) {
		System.out.println("Train All cmd: Invalid argument: "+str);
		return;
	    }
	}
	execute();
    }

    void execute(int endEpoch) {
	NslScheduler scheduler = system.getScheduler();
	scheduler.trainAll(endEpoch);
    }
}
//////////////////////////////////////////////////////////////////////




