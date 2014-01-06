/* SCCS  @(#)NslCmdExit.java	1.6---09/01/99--00:14:41 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslCmdExit.java,v $
 * Revision 1.1.1.1  1997/03/12 22:52:16  nsl
 * new dir structure
 *
 * Revision 1.3  1997/02/19 22:53:07  nsl
 * *** empty log message ***
 *
 * Revision 1.2  1997/02/19 22:48:33  chetan
 * *** empty log message ***
 *
 * Revision 1.1.1.1  1997/02/08 00:40:38  nsl
 *  Imported the Source directory
 *
*/
//
// NslCmdExit.java
//
//////////////////////////////////////////////////////////////////////
package nslj.src.cmd;
/**
 exit NSL
*/
import nslj.src.lang.NslModule;
import nslj.src.system.NslSystem;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdExit extends NslCmd {
  /**
   *Setup class name and help engine
   */
  public NslCmdExit() {
    _name = "exit";
    _simple_help_string = "exit: exit Nsl Simulator";

  }

  /**
   * Print out help string in system console
   */
  public void printHelp() {
    System.out.println("Nsl command : exit");
    System.out.println("usage: exit");
    System.out.println("Quit the Nsl Simulation environment");
  }

  /**
   * Ready for exit. Clean up the system/windows
   */
  public void execute() {
  }

  public void execute(String module_name) {
    execute();
  }

  public void execute(StringTokenizer st) {
    execute();
  }

  void execute(NslModule module) {
    execute();
  }

}


