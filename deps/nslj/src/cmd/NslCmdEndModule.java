/* SCCS  @(#)NslCmdEndModule.java	1.4---09/01/99--00:14:42 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdEndModule.java
//
//////////////////////////////////////////////////////////////////////

/**
* initialize current / a specific model/module
*/

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdEndModule extends NslCmd {

    /**
     *Setup class name and help engine
     */

    public NslCmdEndModule() {
	_name = "endModule";
	_simple_help_string = "endModule";
    }

    public void printHelp() {
	System.out.println("Nsl command : endModule");
	System.out.println("usage: endModule");
	System.out.println("The endModule() methods of module and the descendants' will be called");
    }

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.endModule();
    }

    public void execute(StringTokenizer st) {
	execute();
    }  
}
