/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdReset.java
//
//////////////////////////////////////////////////////////////////////

/**
 * Stop the active simultion at the next time step
 */

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdReset extends NslCmd {
    /**
     * Setup class name and help engine
     */

    public NslCmdReset() {
	_name = "reset";
	_simple_help_string = "reset";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : reset");
	System.out.println("usage: reset");
	System.out.println("Reset the scheduler");
    }

    public void execute() {    
	NslScheduler scheduler = system.getScheduler();
        scheduler.reset();
    }

    public void execute(StringTokenizer st) {
	execute();
    }

}
//////////////////////////////////////////////////////////////////////
