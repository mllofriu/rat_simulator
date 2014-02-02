/* SCCS  @(#)NslCmdInitSys.java	1.7 --- 09/01/99 -- 00:14:41 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdInitSys.java
//
//////////////////////////////////////////////////////////////////////

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdInitSys extends NslCmd {

    /**
     *Setup class name and help engine
     */

    public NslCmdInitSys() {
	_name = "initSys";
	_simple_help_string = "initSys";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : initSys");
	System.out.println("usage: initSys");
	System.out.println("The initSys() methods of  module and the descendants' will be called");
    }

    public void execute() {
	NslScheduler scheduler = system.getScheduler();
	scheduler.initSys();
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

