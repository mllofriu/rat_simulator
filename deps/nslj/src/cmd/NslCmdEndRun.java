/* SCCS  @(#)NslCmdEndRun.java	1.8---09/01/99--00:14:40 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdEndRun.java
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

public class NslCmdEndRun extends NslCmd {

    /**
     * Setup class name and help engine
     */

    public NslCmdEndRun() {
	_name = "endRun";
        _simple_help_string = "endRun";
    }

    public void printHelp() {
	System.out.println("Nsl command : endRun");
	System.out.println("usage: endRun");
	System.out.println("The endRun method of all modules will be called");
    }

    /**
     * Executes endRun in the current active model
     * including its child modules
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.endRun();  
    }

    public void execute(StringTokenizer st) {
	execute();
    }

}
