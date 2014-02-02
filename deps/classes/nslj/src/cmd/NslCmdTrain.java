/* SCCS  @(#)NslCmdTrain.java	1.7---09/01/99--00:14:41 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdTrain.java
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

public class NslCmdTrain extends NslCmd {

    /**
     * Setup class name and help engine
     */
    public NslCmdTrain() {
	_name = "train";
	_simple_help_string = "train [endTime]";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : train");
	System.out.println("usage: train [endTime]");
	System.out.println("All modules would be train until endTime");
    }

    /**
     * Start to run the model in the current context until
     * simulation end time is reached. It will recursively
     * call the child modules to run.
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.simTrain();
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
	scheduler.simTrain(endTime);                
    }
}
//////////////////////////////////////////////////////////////////////




