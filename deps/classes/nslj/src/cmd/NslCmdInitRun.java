/* SCCS  @(#)NslCmdInitRun.java	1.2---09/01/99--00:14:44 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdInitRun.java
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

public class NslCmdInitRun extends NslCmd {

    /**
     * Setup class name and help engine
     */

    public NslCmdInitRun() {
        _name = "initRun";
        _simple_help_string = "initRun";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : initRun");
	System.out.println("usage: initRun");
	System.out.println("All modules in scheduler would be initialized");
    }

    /**
     * Initialize the current active model
     * including its child modules
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.initRun();
    }

    public void execute(StringTokenizer st) {
	execute();
    }

} //end class
