/*  SCCS - @(#)NslInterpreter.java	1.7 - 09/01/99 - 00:19:50 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslInterpreter.java,v $
 * Revision 1.2  1997/11/06 03:15:27  erhan
 * nsl3.0.b
 *
 * Revision 1.1.1.1  1997/03/12 22:52:21  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:40  nsl
 *  Imported the Source directory
 *
*/
//
// NslInterpreter.java
//
//////////////////////////////////////////////////////////////////////

/**
* Basic User interpreter.
  This interpreter extracts the first word of the user input
  and select the corresponding <tt>NslCmd</tt> registered in
  simulation system for execution. If the command is not found
  in the command list, it prompts out "Bad Command" error message
  on the console output.
  @see nslj.src.cmd.NslCmd
*/
package nslj.src.system;

import nslj.src.cmd.*;
import nslj.src.nsls.struct.*;
import java.util.StringTokenizer;

public class NslInterpreter {

	static NslSystem system; // use only one system at a time

	public Executive executive;
  
	/**
	 * Constructor
	 * @param sys the main simulation system
	 */

	public NslInterpreter(NslSystem sys) {
		system = sys;
		executive = new Executive(system, this);
	}


	/**
	 * Receive user input. If the input is not null, it calls
	   <tt>execute(String)</tt> to do parsing.
	   This method also prints out <tt>nsl></tt> prompt to
	   console output.
	 */
	 
	public void execute() {

		String cin,cout;

		do {
 			// Here, we use Console from core java
			// it will return a String from console input

			cin = Console.readString("nsls%");
			if (cin == null || cin.equals(""))
				continue;
			cout = executive.execute_line(cin);
			if (cout == null || cout.equals(""))
				continue;
			System.out.println(cout);
			//execute(cin);
			
		} while (true); //!cin.equals("exit"));
	}

	/**
	 * Parse non-null command string. Get the user command from
	   the first word of the command string.
	   If the corresponding command is not found in the command
	   list, it prompts <i>"Bad command"</i>. Otherwise, it
	   tokenize the command string and pass it to the command
	   for execution
	   @param cin - command string
	 */
	 
	public void execute(String cin) {
		NslCmd command;

		StringTokenizer st;
		String command_str;

		if (cin == null || cin.equals(""))   
			return;

    		//command.execute(cin);
		st = new StringTokenizer(cin);
		if (!st.hasMoreTokens())
 			return;
		command_str = st.nextToken();
		//#command_str "+command_str);

		command = getCommand(command_str);
		// command = getCommand(cin);
		// command.execute(cin);
		if (command == null) {
			// This line is never executed
			System.out.println("#Warning:NslInterpreter: Bad command " + cin);
		} else {
			command.execute(st);
	    		//System.out.println(cin);

    		}
 	}

	/**
	 * Translates user input string to command module
	 * @param command user input string
	 * @return command module
	 */
	 
	NslCmd getCommand(String command) {
		return system.getCommand(command);
	}

}
