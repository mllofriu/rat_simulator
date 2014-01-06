/* SCCS  @(#)NslCmdContEpoch.java	1.2---09/01/99--00:14:44 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdContEpoch.java
//
//////////////////////////////////////////////////////////////////////

/**
 * Continue to run current model
 */

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdContEpoch extends NslCmd {

    /**
     * Setup class name and help engine
     */

    public NslCmdContEpoch() {
	_name = "contEpoch";
	_simple_help_string = "contEpoch";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : contEpoch");
	System.out.println("usage: contEpoch [endEpoch]");
	System.out.println("The model would be continued until endEpoch");
    }

    /**
     * Start to run the model in the current context until
     * simulation end time is reached. It will recursively
     * call the child modules to run.
     */

    public void execute() {     
	NslScheduler scheduler = system.getScheduler();
	scheduler.continueEpoch();
    }

    void execute(int endEpoch) {              
      NslScheduler scheduler = system.getScheduler();
      scheduler.continueEpoch(endEpoch);
    }
  
    public void execute(StringTokenizer st) {
	if(st.hasMoreTokens()) {
	    String str = st.nextToken();
	    //System.out.println("Continue with parameter: "+str);
	    try {
		execute((Integer.valueOf(str)).intValue());
		return;
	    } catch(NumberFormatException e) {
                System.out.println("Invalid argument: "+str);
                return;
	    }
	}
	execute();
    }

} //end class


//////////////////////////////////////////////////////////////////////
