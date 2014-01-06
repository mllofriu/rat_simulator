/* SCCS  @(#)NslCmdInitModule.java	1.7 --- 09/01/99 -- 00:14:41 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdInitModule.java
//
//////////////////////////////////////////////////////////////////////

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdInitModule extends NslCmd {

    /**
     *Setup class name and help engine
     */

    public NslCmdInitModule() {
	_name = "initModule";
	_simple_help_string = "initModule";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : initModule");
	System.out.println("usage: initModule");
	System.out.println("The initModule() methods of  module and the descendants' will be called");
    }

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.initModule();
    }

    /**
     * Initialize a series of modules in the array <tt>module_list</tt> and
     * their corresponding child modules
     * @param module_list
     */

    public void execute(StringTokenizer st) {
	execute();
    }
}

