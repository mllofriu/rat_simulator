/* SCCS  @(#)NslCmd.java	1.6---09/01/99--00:14:40 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslCmd.java,v $
 * Revision 1.2  1997/05/12 18:47:04  nsl
 * *** empty log message ***
 *
 * Revision 1.1.1.1  1997/03/12 22:52:16  nsl
 * new dir structure
 *
 * Revision 1.3  1997/02/19 21:46:54  nsl
 * *** empty log message ***
 *
 * Revision 1.2  1997/02/11 00:36:10  nsl
 * *** empty log message ***
 *
 * Revision 1.1.1.1  1997/02/08 00:40:38  nsl
 *  Imported the Source directory
 *
*/
//
// NslCmd.java
//
//////////////////////////////////////////////////////////////////////

package nslj.src.cmd;


import nslj.src.lang.NslModule;
import nslj.src.system.NslSystem;
import java.util.StringTokenizer;



/**
* The user command
  It set up the main system and calls interpreter to run.
*/

abstract public class NslCmd {
  String _name; // the "name" of this command
  String _simple_help_string; // one line help string

  static NslSystem system;

  /**
   * Point the command system to main system
   * @param s main NslSystem
   */
  public static void nslSetSystem(NslSystem s) {
    system = s;
  }

  /**
   * Get the name of the command
   * @return name
   */
  public String nslGetName() {
    return _name;
  }

  /**
   * @return the one line help string
   */
  public String getHelpString() {
    return _simple_help_string;
  }
  /**
   * Print the one line string to standard out
   */
  public void printHelpString() {
    System.out.println(_simple_help_string);
  }

  /**
   * Print complex help on standard out
   */
  abstract public void printHelp();

  /**
   * Execute this command without any parameter
   */
  abstract public void execute();
  /**
   * Execute this command with only one parameter
   */
//  abstract public void execute(String _parameter);

  /**
   * Execute command with a list of parameters
   */
  abstract public void execute(StringTokenizer parameter_list);

}
