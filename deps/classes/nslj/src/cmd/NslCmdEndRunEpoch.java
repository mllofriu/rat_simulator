/* SCCS  @(#)NslCmdEndRunEpoch.java	1.2---09/01/99--00:14:44 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdEndRunEpoch.java
//
//////////////////////////////////////////////////////////////////////

/**
 * Execute endRun of all modules
 */

package nslj.src.cmd;

import nslj.src.lang.NslModule;
import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdEndRunEpoch extends NslCmd {

    /**
     * Setup class name and help engine
     */

    public NslCmdEndRunEpoch() {
	_name = "endRunEpoch";
        _simple_help_string = "endRunEpoch";
    }

    public void printHelp() {
	System.out.println("Nsl command : endRunEpoch");
	System.out.println("usage: endRunEpoch");
	System.out.println("The endRunEpoch method of all modules will be called");
    }

    /**
     * Executes endRun in the current active model
     * including its child modules
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.endRunEpoch();  
    }

    public void execute(StringTokenizer st) {
	execute();
    }

}
