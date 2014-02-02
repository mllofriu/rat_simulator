/* SCCS  @(#)NslCmdEndTrain.java	1.8---09/01/99--00:14:42 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdEndTrain.java
//
//////////////////////////////////////////////////////////////////////

/**
 * Execute endTrain of all modules
 */

package nslj.src.cmd;

import nslj.src.lang.NslModule;
import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdEndTrain extends NslCmd {

    /**
     * Setup class name and help engine
     */

    public NslCmdEndTrain() {
	_name = "endTrain";
        _simple_help_string = "endTrain";
    }

    public void printHelp() {
	System.out.println("Nsl command : endTrain");
	System.out.println("usage: endTrain");
	System.out.println("The endRun method of all modules will be called");
    }

    /**
     * Executes endRun in the current active model
     * including its child modules
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.endTrain();  
    }

    public void execute(StringTokenizer st) {
	execute();
    }

}
