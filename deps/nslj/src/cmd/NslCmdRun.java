/* SCCS  @(#)NslCmdRun.java	1.9---09/01/99--00:14:41 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslCmdRun.java,v $
 * Revision 1.2  1997/07/30 21:18:56  erhan
 * nsl3.0
 *
 * Revision 1.1.1.1  1997/03/12 22:52:16  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:38  nsl
 *  Imported the Source directory
 *
*/
//
// NslCmdRun.java
//
//////////////////////////////////////////////////////////////////////

/**
* Run current / a specific model/module
*/
package nslj.src.cmd;
import nslj.src.lang.NslModule;
import nslj.src.system.*;

import java.lang.Double;
import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdRun extends NslCmd {

    /**
     * Setup class name and help engine
     */
    public NslCmdRun() {
	_name = "run";
	_simple_help_string = "run [endTime]";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : run");
	System.out.println("usage: run [endTime]");
	System.out.println("All modules would be run until endTime");
    }

    /**
     * Start to run the model in the current context until
     * simulation end time is reached. It will recursively
     * call the child modules to run.
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.simRun();
    }

    public void execute(String time_str) {
	execute();
    }

    public void execute(StringTokenizer st) {
	if (st.hasMoreTokens()) {
	    String str = st.nextToken();
	    try {
		execute((Double.valueOf(str)).doubleValue());
		return;
	    } catch(NumberFormatException e) {
		System.out.println("Run cmd: Invalid argument: "+str);
		return;
	    }
	}
	execute();
    }

    void execute(double endTime) {
	NslScheduler scheduler = system.getScheduler();
	scheduler.simRun(endTime);                
    }
}
//////////////////////////////////////////////////////////////////////




