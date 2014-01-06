/*  SCCS - @(#)NslShellThread.java	1.7 - 09/01/99 - 00:19:29 */

/* Copyright: 
   Copyright (c) 1998 University of Southern California Brain Project.
   This software may be freely copied provided the top level
   COPYRIGHT file is included with each such copy.
   Emai:l nsl@java.usc.edu.
*/
/**
   @author Nikunj Mehta
*/

package nslj.src.nsls.struct;

import nslj.src.system.*;
import java.awt.*;

public class NslShellThread extends Thread {
  NslShell shell;
  NslSystem system;
  Frame frame;

  public NslShellThread(Frame f, NslSystem s) {
    frame = f;
    system = s;
  }
  
  public synchronized void run() {
    
    // Wait for the construction of the interpreter

    // system.interpWait();
    	
    // executive = new Executive(system,system.getInterpreter());

    //System.out.println("Corriendo thread");
    NslShell temp = new NslShell(system.getInterpreter().executive);
       	
    frame.add(temp);
    
    try {
      sleep(1000);
    } catch (Exception e) { } 
    
    //temp.show();

  }
}
