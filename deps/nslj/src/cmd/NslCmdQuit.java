/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslCmdQuit.java,v $
 * Revision 1.1.1.1  1997/03/12 22:52:16  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:38  nsl
 *  Imported the Source directory
 *
*/
//
// NslCmdQuit.java
//
//////////////////////////////////////////////////////////////////////
package nslj.src.cmd;
/**
 quit NSL
*/
import nslj.src.lang.NslModule;
import nslj.src.system.NslSystem;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdQuit extends NslCmd {
  /**
   *Setup class name and help engine
   */
  public NslCmdQuit() {
    _name = "quit";
    _simple_help_string = "quit: exit Nsl Simulator";

  }

  /**
   * Print out help string in system console
   */
  public void printHelp() {
    System.out.println("Nsl command : quit");
    System.out.println("usage: quit");
    System.out.println("Quit the Nsl Simulation environment");
  }

  /**
   * Ready for exit. Clean up the system/windows
   */
  public void execute() {
    system.getScheduler().stop();    
  }

  public void execute(String module_name) {
    execute();
  }
  public void execute(StringTokenizer st) {
String str;

while(st.hasMoreTokens()) {
    str = st.nextToken();
    System.out.println(str);
}


    execute();
  }


  void execute(NslModule module) {
    execute();
  }

}


