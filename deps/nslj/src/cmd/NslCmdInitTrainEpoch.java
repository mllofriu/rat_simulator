/* SCCS  @(#)NslCmdInitTrainEpoch.java	1.2---09/01/99--00:14:45 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdInitTrainEpoch.java
//
//////////////////////////////////////////////////////////////////////

/**
 * initialize all modules
 */

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdInitTrainEpoch extends NslCmd {

    /**
     * Setup class name and help engine
     */

    public NslCmdInitTrainEpoch() {
        _name = "initTrainEpoch";
        _simple_help_string = "initTrainEpoch";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : initTrainEpoch");
	System.out.println("usage: initTrainEpoch");
	System.out.println("All modules in scheduler would be initialized");
    }

    /**
     * Initialize the current active model
     * including its child modules
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.initTrainEpoch();
    }

    public void execute(StringTokenizer st) {
	execute();
    }

} //end class
