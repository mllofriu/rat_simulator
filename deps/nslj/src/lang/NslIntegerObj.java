/*  SCCS - @(#)NslIntegerObj.java	1.7 - 09/01/99 - 00:16:48 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslIntegerObj.java,v $
 * Revision 1.2  1997/11/06 03:15:22  erhan
 * nsl3.0.b
 *
 * Revision 1.1.1.1  1997/03/12 22:52:19  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:39  nsl
 *  Imported the Source directory
 *
*/
////////////////////////////////////////////////////////////////////////////////
// NslIntegerObj.java
package nslj.src.lang;


// get integer class wrapper
public class NslIntegerObj {
  public int value;
  
  public NslIntegerObj() {
    value = 0;
  }
  
  public NslIntegerObj(int v) {
    value = v;
  }
}
