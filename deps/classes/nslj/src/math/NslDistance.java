/*  SCCS - @(#)NslDistance.java	1.3 --- 09/01/99 --00:18:25 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.


////////////////////////////////////////////////////////////
//
// Distance routines
//
//

package nslj.src.math;

import java.math.*;
import nslj.src.lang.*;

/**
 Distance routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a, b) -> c
 a, b are the parameter of the evaluation function to do
 a pow b pointwise and the result is passed out as c
 2. eval(dest, a, b) -> c
 a, b are the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/

public final class NslDistance extends NslBinaryOperator {

  public int value(int a, int b) {
	return (int)Math.sqrt((double)(a*a)+(double)(b*b));
  }

  public float value(float a, float b) {
	return (float)Math.sqrt((double)(a*a)+(double)(b*b));
  }

  public double value(double a, double b) {
	return Math.sqrt((a*a)+(b*b));
  }

}
