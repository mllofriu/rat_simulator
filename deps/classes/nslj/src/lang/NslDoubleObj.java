/*  SCCS - @(#)NslDoubleObj.java	1.8 - 09/01/99 - 00:16:40 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslDoubleObj.java,v $
 * Revision 1.2  1997/11/06 03:15:16  erhan
 * nsl3.0.b
 *
 * Revision 1.1.1.1  1997/03/12 22:52:18  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:39  nsl
 *  Imported the Source directory
 *
*/
////////////////////////////////////////////////////////////////////////////////
// NslDoubleObj.java
package nslj.src.lang;


// get Double class wrapper
public class NslDoubleObj {
  public double value;
  
  public NslDoubleObj() {
    value = (double)0.0;
  }
  
  public NslDoubleObj(double v) {
    value = v;
  }
}
