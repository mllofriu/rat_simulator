/*  SCCS - @(#)NslCommandList.java	1.4 - 09/01/99 - 00:19:28 */

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

import java.util.*;

public class NslCommandList extends Vector {
  private static final int maxHistory = 50;
  private int current = 0;
  public NslCommandList(int initial, int increment) {
    super(initial, increment);
  }

  public NslCommandList(int initial) {
    super(initial);
  }

  public NslCommandList() {
    super(1, 1);
    addElement(new String(""));
  }

  public void add(String s) {
    setElementAt(s, elementCount - 1);
    if (elementCount == maxHistory)
      removeElementAt(0);
    addElement(new String(""));
    current = elementCount - 1;
  }
  
  public String next() {
    String command = "";
    try {
      command = (String) elementData[current + 1];
      ++current;
    } catch (ArrayIndexOutOfBoundsException e) {
    } finally { 
     return command;
    }
  }

  public String prev() {
    String command = "";
    try {
      command = (String) elementData[current - 1];
      --current;
    } catch (ArrayIndexOutOfBoundsException e) {
      command = (String) elementData[0];
    } finally {
      return command;
    }
  }
}
