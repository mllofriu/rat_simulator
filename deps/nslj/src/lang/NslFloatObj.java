/*  SCCS - @(#)NslFloatObj.java	1.7 - 09/01/99 - 00:16:46 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslFloatObj.java,v $
 * Revision 1.2  1997/11/06 03:15:19  erhan
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
// NslFloatObj.java

package nslj.src.lang;

// get float class wrapper
public class NslFloatObj {
  public float value;
  
  public NslFloatObj() {
    value = (float)0.0;
  }
  
  public NslFloatObj(float v) {
    value = v;
  }
  public NslFloatObj(int v) {
    value = (float)v;
  }

  public NslFloatObj(double v) {
    value = (float)v;
  }

}


