/*  SCCS - @(#)NslMaxMerge.java	1.4 - 09/01/99 - 00:18:14 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Maximum number merging routines
//
//

package nslj.src.math;

import java.math.*;
import nslj.src.lang.*;

/**
 Maximum number merging routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a, b) -> c
 a, b are the parameter to evaluate the maximum value of
 a and b pointwise and the result is passed out as c
 2. eval(dest, a, b) -> c
 a, b are the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/

public final class NslMaxMerge extends NslBinaryOperator {

  public int value(int a, int b) {
	return Math.max(a,b);
  }

  public float value(float a, float b) {
	return Math.max(a,b);
  }

  public double value(double a, double b) {
	return Math.max(a,b);
  }

}
