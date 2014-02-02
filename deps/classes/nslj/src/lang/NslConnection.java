/*  SCCS - @(#)NslConnection.java	1.5 - 09/01/99 - 00:16:35 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslConnection.java,v $
 * Revision 1.1.1.1  1997/03/12 22:52:18  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:39  nsl
 *  Imported the Source directory
 *
*/
package nslj.src.lang;

class NslConnection {
  public NslModule child1;
  public NslModule child2;
  public String name1;
  public String name2;

  public NslConnection (NslModule l1, String n1, NslModule l2, String n2) {
    child1 = l1;
    child2 = l2;
    name1 = n1;
    name2 = n2;
  }
}

