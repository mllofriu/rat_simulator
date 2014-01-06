/*  SCCS - @(#)NslDotProd.java	1.3 --- 09/01/99 -- 00:18:16 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Dot Product routines
//
//

/**
 Production routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a, b) -> c
 a, b are the parameter of the evaluation function to do
 dot vector multiplication with a and  b and the result is
 passed out as c
 2. eval(dest, a, b) -> c
 a, b are the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

*/


package nslj.src.math;
import nslj.src.lang.*;


public class NslDotProd {

/* doubles */

public static double eval(double[] w, double[] x)
{
	int size = w.length;

	double c = 0.0;

	if (size != x.length)
	{
		System.out.println("Inconsistent Array dimensions");
		return c;
	}

	for (int i = 0; i < size; i++)
		c = c + w[i]*x[i];

	return c;
}

public static double eval(NslDouble1 w, NslDouble1 x) {
    return eval(x.getdouble1(),w.getdouble1());
}

/* floats */

public static float eval(float[] w,float[] x)
{
	int size = w.length;

	float c = 0;

	if (size != x.length)
	{
		System.out.println("Inconsistent Array dimensions");
		return c;
	}

	for (int i = 0; i < size; i++)
		c = c + w[i]*x[i];

	return c;
}

public static float eval(NslFloat1 w, NslFloat1 x) {
    return eval(x.getfloat1(),w.getfloat1());
}

/* ints */

public static int eval(int[] w,int[] x)
{
	int size = w.length;

	int c = 0;

	if (size != x.length)
	{
		System.out.println("Inconsistent Array dimensions");
		return c;
	}

	for (int i = 0; i < size; i++)
		c = c + w[i]*x[i];

	return c;
}

public static int eval(NslInt1 w, NslInt1 x) {
    return eval(x.getint1(),w.getint1());
}

/* booleans */

public static boolean eval(boolean[] w, boolean[] x)
{
	int size = w.length;

	boolean c = false;

	if (size != x.length)
	{
		System.out.println("Inconsistent Array dimensions");
		return c;
	}

	for (int i = 0; i < size; i++)
		c = c || w[i] && x[i];

	return c;
}

public static boolean eval(NslBoolean1 w, NslBoolean1 x) {
    return eval(x.getboolean1(),w.getboolean1());
}

}










