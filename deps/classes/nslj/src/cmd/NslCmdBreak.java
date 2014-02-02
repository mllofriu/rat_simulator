/* SCCS  @(#)NslCmdBreak.java	1.10---09/01/99--00:14:40 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslCmdBreak.java,v $
 * Revision 1.1.1.1  1997/03/12 22:52:16  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:38  nsl
 *  Imported the Source directory
 *
*/
//
// NslCmdBreak.java
//
//////////////////////////////////////////////////////////////////////

/**
 * Stop the active simultion at the next time step
 */

package nslj.src.cmd;

import nslj.src.system.NslSystem;
import nslj.src.system.NslScheduler;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdBreak extends NslCmd {
    /**
     * Setup class name and help engine
     */

    public NslCmdBreak() {
	_name = "break";
	_simple_help_string = "break";
    }

    /**
     * Print complex help on standard out
     */

    public void printHelp() {
	System.out.println("Nsl command : break");
	System.out.println("usage: break");
	System.out.println("Stop the simulation at the beginning of next time step");
    }

    public void execute() {
	// request scheduler to break the simulation    

	NslScheduler scheduler = system.getScheduler();
        scheduler.breakAll();
    }

    public void execute(StringTokenizer st) {
	execute();
    }

}
//////////////////////////////////////////////////////////////////////
