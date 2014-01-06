/* SCCS  @(#)NslCmdBreakCycles.java	1.2---09/01/99--00:14:43 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdBreakCycles.java
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

public class NslCmdBreakCycles extends NslCmd {
    /**
     * Setup class name and help engine
     */

    public NslCmdBreakCycles() {
	_name = "breakCycles";
	_simple_help_string = "breakCycles";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : breakCycles");
	System.out.println("usage: breakCycles");
	System.out.println("Stop the simulation at the beginning of next time step");
    }

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
        scheduler.breakCycles();
    }

    public void execute(StringTokenizer st) {
	execute();
    }

}
//////////////////////////////////////////////////////////////////////
