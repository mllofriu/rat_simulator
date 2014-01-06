/*  SCCS - @(#)NslBooleanObj.java	1.3 - 09/01/99 - 00:16:58 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslBooleanObj.java

package nslj.src.lang;

// Boolean class wrapper

public class NslBooleanObj {
  
  public boolean value;
  
  public NslBooleanObj() {
    value = false;
  }
  
  public NslBooleanObj(boolean v) {
    value = v;
  }
}
