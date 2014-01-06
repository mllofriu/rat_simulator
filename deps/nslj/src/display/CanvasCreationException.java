/* SCCS  @(#)CanvasCreationException.java	1.6---09/01/99--00:15:54 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.lang.*;

public class CanvasCreationException extends Exception {
  CanvasCreationException() { super(); }
  public String toString() { return "Failed to create requested canvas."; }
}
