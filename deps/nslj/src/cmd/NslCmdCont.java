/* SCCS  @(#)NslCmdCont.java	1.10---09/01/99--00:14:40 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslCmdCont.java,v $
 * Revision 1.2  1997/07/30 21:18:53  erhan
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
// NslCmdCont.java
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

public class NslCmdCont extends NslCmd {

    /**
     * Setup class name and help engine
     */

    public NslCmdCont() {
	_name = "cont";
	_simple_help_string = "cont";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : cont");
	System.out.println("usage: cont [endTime]");
	System.out.println("The model would be continued until endTime");
    }

    /**
     * Start to run the model in the current context until
     * simulation end time is reached. It will recursively
     * call the child modules to run.
     */

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.continueAll();
    }

    void execute(double endTime) {              
      NslScheduler scheduler = system.getScheduler();
      scheduler.continueAll(endTime);          
    }
  
    public void execute(StringTokenizer st) {
	if(st.hasMoreTokens()) {
	    String str = st.nextToken();
	    //System.out.println("Continue with parameter: "+str);
	    try {
		execute((Double.valueOf(str)).doubleValue());
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
