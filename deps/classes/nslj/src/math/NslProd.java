/*  SCCS - @(#)NslProd.java	1.6 --- 09/01/99 -- 00:18:08 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Production routines
//
//

/**
 Production routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a, b) -> c
 a, b are the parameter of the evaluation function to do
 matrix multiplication with a and  b and the result is
 passed out as c
 2. eval(dest, a, b) -> c
 a, b are the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

*/


package nslj.src.math;
import nslj.src.lang.*;


//public final class NslProd {  //99/6/24 aa: new long name
public class NslProd {

// product

/* doubles */

public static double[][] eval(double[][] x, double[] w) {
    return eval(new double[x.length][w.length], x, w);
}

public static double[][] eval(double[][] dest, double[][] x, double[] w)
{
	int size = x.length;
	int vsize = x[0].length;

	if (vsize != 1)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != size || dest[0].length != w.length) {
        	dest = new double[size][w.length];
    	}

	for (int i = 0; i < size; i++) 
	{
	    for (int j = 0; j < w.length; j++)
		dest[i][j] = x[i][0]*w[j];
	}

	return dest;
}


public static double[] eval(double[] x,double[][] w) {
    return eval(new double[w[0].length], x, w);
}

public static double[] eval(double[] dest, double[] x, double[][] w)
{
	int size = w.length;
	int vsize = w[0].length;

	if (x.length != size)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != vsize) {
        	dest = new double[vsize];
    	}

	for (int i = 0; i < vsize; i++)
	{
	    dest[i] = 0;
	    for (int j = 0; j < size; j++)
		dest[i] += x[j] * w[j][i];
	}

	return dest;
}

public static double[][] eval(double[][] w, double[][] x) {
    return eval(new double[w.length][x[0].length], w, x);
}

public static double[][] eval(double[][] dest, double[][] w, double[][] x)
{
	int isize = w.length;
	int jsize = x[0].length;

	int size = w[0].length;

	if (size != x.length)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != isize || dest[0].length != jsize) {
        	dest = new double[isize][jsize];
    	}

	for (int i = 0; i < isize; i++)
	    for (int j = 0; j < jsize; j++)
	    {
	    	dest[i][j] = 0;
	    	for (int k = 0; k < size; k++)
		   dest[i][j] = dest[i][j] + w[i][k]*x[k][j];
	    }

	return dest;
}

/* Added by Weifang */

public static double[][] eval(NslDouble2 w, NslDouble1 x) {
    return eval(w.getdouble2(),x.getdouble1());
}

public static double[][] eval(NslDouble2 w, double[] x) {
    return eval(w.getdouble2(),x);
}

public static double[][] eval(double[][] w, NslDouble1 x) {
    return eval(w,x.getdouble1());
}

public static double[][] eval(double[][] dest, NslDouble2 w, NslDouble1 x) {
    return eval(dest, w.getdouble2(),x.getdouble1());
}

public static double[][] eval(double[][] dest, NslDouble2 w, double[] x) {
    return eval(dest, w.getdouble2(),x);
}

public static double[][] eval(double[][] dest, double[][] w, NslDouble1 x) {
    return eval(dest, w,x.getdouble1());
}

//----

public static double[] eval(NslDouble1 w, NslDouble2 x) {
    return eval(w.getdouble1(), x.getdouble2());
}

public static double[] eval(NslDouble1 w, double[][] x) {
    return eval(w.getdouble1(), x);
}

public static double[] eval(double[] w, NslDouble2 x) {
    return eval(w, x.getdouble2());
}

public static double[] eval(double[] dest, NslDouble1 w, NslDouble2 x) {
    return eval(dest, w.getdouble1(), x.getdouble2());
}

public static double[] eval(double[] dest, NslDouble1 w, double[][] x) {
    return eval(dest, w.getdouble1(), x);
}

public static double[] eval(double[] dest, double[] w, NslDouble2 x) {
    return eval(dest, w, x.getdouble2());
}

//----

public static double[][] eval(NslDouble2 w, NslDouble2 x) {
    return eval(w.getdouble2(), x.getdouble2());
}

public static double[][] eval(NslDouble2 w, double[][] x) {
    return eval(w.getdouble2(), x);
}

public static double[][] eval(double[][] w, NslDouble2 x) {
    return eval(w, x.getdouble2());
}

public static double[][] eval(double[][] dest, NslDouble2 w, NslDouble2 x) {
    return eval(dest, w.getdouble2(), x.getdouble2());
}

public static double[][] eval(double[][] dest, NslDouble2 w, double[][] x) {
    return eval(dest, w.getdouble2(), x);
}

public static double[][] eval(double[][] dest, double[][] w, NslDouble2 x) {
    return eval(dest, w, x.getdouble2());
}

/* floats */

public static float[][] eval(float[][] x, float[] w) {
    return eval(new float[x.length][w.length], x, w);
}

public static float[][] eval(float[][] dest, float[][] x, float[] w)
{
	int size = x.length;
	int vsize = x[0].length;

	if (vsize != 1)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != size || dest[0].length != w.length) {
        	dest = new float[size][w.length];
    	}

	for (int i = 0; i < size; i++) 
	{
	    for (int j = 0; j < w.length; j++)
		dest[i][j] = x[i][0]*w[j];
	}

	return dest;
}


public static float[] eval(float[] x,float[][] w) {
    return eval(new float[w[0].length], x, w);
}

public static float[] eval(float[] dest, float[] x, float[][] w)
{
	int size = w.length;
	int vsize = w[0].length;

	if (x.length != size)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != vsize) {
        	dest = new float[vsize];
    	}

	for (int i = 0; i < vsize; i++)
	{
	    dest[i] = 0;
	    for (int j = 0; j < size; j++)
		dest[i] += x[j] * w[j][i];
	}

	return dest;
}

public static float[][] eval(float[][] w, float[][] x) {
    return eval(new float[w.length][x[0].length], w, x);
}

public static float[][] eval(float[][] dest, float[][] w, float[][] x)
{
	int isize = w.length;
	int jsize = x[0].length;

	int size = w[0].length;

	if (size != x.length)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != isize || dest[0].length != jsize) {
        	dest = new float[isize][jsize];
    	}

	for (int i = 0; i < isize; i++)
	    for (int j = 0; j < jsize; j++)
	    {
	    	dest[i][j] = 0;
	    	for (int k = 0; k < size; k++)
		   dest[i][j] = dest[i][j] + w[i][k]*x[k][j];
	    }

	return dest;
}

/* Added by Weifang */

public static float[][] eval(NslFloat2 w, NslFloat1 x) {
    return eval(w.getfloat2(),x.getfloat1());
}

public static float[][] eval(NslFloat2 w, float[] x) {
    return eval(w.getfloat2(),x);
}

public static float[][] eval(float[][] w, NslFloat1 x) {
    return eval(w,x.getfloat1());
}

public static float[][] eval(float[][] dest, NslFloat2 w, NslFloat1 x) {
    return eval(dest, w.getfloat2(),x.getfloat1());
}

public static float[][] eval(float[][] dest, NslFloat2 w, float[] x) {
    return eval(dest, w.getfloat2(),x);
}

public static float[][] eval(float[][] dest, float[][] w, NslFloat1 x) {
    return eval(dest, w,x.getfloat1());
}

//----

public static float[] eval(NslFloat1 w, NslFloat2 x) {
    return eval(w.getfloat1(), x.getfloat2());
}

public static float[] eval(NslFloat1 w, float[][] x) {
    return eval(w.getfloat1(), x);
}

public static float[] eval(float[] w, NslFloat2 x) {
    return eval(w, x.getfloat2());
}

public static float[] eval(float[] dest, NslFloat1 w, NslFloat2 x) {
    return eval(dest, w.getfloat1(), x.getfloat2());
}

public static float[] eval(float[] dest, NslFloat1 w, float[][] x) {
    return eval(dest, w.getfloat1(), x);
}

public static float[] eval(float[] dest, float[] w, NslFloat2 x) {
    return eval(dest, w, x.getfloat2());
}

//----

public static float[][] eval(NslFloat2 w, NslFloat2 x) {
    return eval(w.getfloat2(), x.getfloat2());
}

public static float[][] eval(NslFloat2 w, float[][] x) {
    return eval(w.getfloat2(), x);
}

public static float[][] eval(float[][] w, NslFloat2 x) {
    return eval(w, x.getfloat2());
}

public static float[][] eval(float[][] dest, NslFloat2 w, NslFloat2 x) {
    return eval(dest, w.getfloat2(), x.getfloat2());
}

public static float[][] eval(float[][] dest, NslFloat2 w, float[][] x) {
    return eval(dest, w.getfloat2(), x);
}

public static float[][] eval(float[][] dest, float[][] w, NslFloat2 x) {
    return eval(dest, w, x.getfloat2());
}
/* ints */

public static int[][] eval(int[][] x, int[] w) {
    return eval(new int[x.length][w.length], x, w);
}

public static int[][] eval(int[][] dest, int[][] x, int[] w)
{
	int size = x.length;
	int vsize = x[0].length;

	if (vsize != 1)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != size || dest[0].length != w.length) {
        	dest = new int[size][w.length];
    	}

	for (int i = 0; i < size; i++) 
	{
	    for (int j = 0; j < w.length; j++)
		dest[i][j] = x[i][0]*w[j];
	}

	return dest;
}


public static int[] eval(int[] x,int[][] w) {
    return eval(new int[w[0].length], x, w);
}

public static int[] eval(int[] dest, int[] x, int[][] w)
{
	int size = w.length;
	int vsize = w[0].length;

	if (x.length != size)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != vsize) {
        	dest = new int[vsize];
    	}

	for (int i = 0; i < vsize; i++)
	{
	    dest[i] = 0;
	    for (int j = 0; j < size; j++)
		dest[i] += x[j] * w[j][i];
	}

	return dest;
}

public static int[][] eval(int[][] w, int[][] x) {
    return eval(new int[w.length][x[0].length], w, x);
}

public static int[][] eval(int[][] dest, int[][] w, int[][] x)
{
	int isize = w.length;
	int jsize = x[0].length;

	int size = w[0].length;

	if (size != x.length)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != isize || dest[0].length != jsize) {
        	dest = new int[isize][jsize];
    	}

	for (int i = 0; i < isize; i++)
	    for (int j = 0; j < jsize; j++)
	    {
	    	dest[i][j] = 0;
	    	for (int k = 0; k < size; k++)
		   dest[i][j] = dest[i][j] + w[i][k]*x[k][j];
	    }

	return dest;
}

/* Added by Weifang */

public static int[][] eval(NslInt2 w, NslInt1 x) {
    return eval(w.getint2(),x.getint1());
}

public static int[][] eval(NslInt2 w, int[] x) {
    return eval(w.getint2(),x);
}

public static int[][] eval(int[][] w, NslInt1 x) {
    return eval(w,x.getint1());
}

public static int[][] eval(int[][] dest, NslInt2 w, NslInt1 x) {
    return eval(dest, w.getint2(),x.getint1());
}

public static int[][] eval(int[][] dest, NslInt2 w, int[] x) {
    return eval(dest, w.getint2(),x);
}

public static int[][] eval(int[][] dest, int[][] w, NslInt1 x) {
    return eval(dest, w,x.getint1());
}

//----

public static int[] eval(NslInt1 w, NslInt2 x) {
    return eval(w.getint1(), x.getint2());
}

public static int[] eval(NslInt1 w, int[][] x) {
    return eval(w.getint1(), x);
}

public static int[] eval(int[] w, NslInt2 x) {
    return eval(w, x.getint2());
}

public static int[] eval(int[] dest, NslInt1 w, NslInt2 x) {
    return eval(dest, w.getint1(), x.getint2());
}

public static int[] eval(int[] dest, NslInt1 w, int[][] x) {
    return eval(dest, w.getint1(), x);
}

public static int[] eval(int[] dest, int[] w, NslInt2 x) {
    return eval(dest, w, x.getint2());
}

//----

public static int[][] eval(NslInt2 w, NslInt2 x) {
    return eval(w.getint2(), x.getint2());
}

public static int[][] eval(NslInt2 w, int[][] x) {
    return eval(w.getint2(), x);
}

public static int[][] eval(int[][] w, NslInt2 x) {
    return eval(w, x.getint2());
}

public static int[][] eval(int[][] dest, NslInt2 w, NslInt2 x) {
    return eval(dest, w.getint2(), x.getint2());
}

public static int[][] eval(int[][] dest, NslInt2 w, int[][] x) {
    return eval(dest, w.getint2(), x);
}

public static int[][] eval(int[][] dest, int[][] w, NslInt2 x) {
    return eval(dest, w, x.getint2());
}

/* boolean */

public static boolean[][] eval(boolean[][] x, boolean[] w) {
    return eval(new boolean[x.length][w.length], x, w);
}

public static boolean[][] eval(boolean[][] dest, boolean[][] x, boolean[] w)
{
	int size = x.length;
	int vsize = x[0].length;

	if (vsize != 1)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != size || dest[0].length != w.length) {
        	dest = new boolean[size][w.length];
    	}

	for (int i = 0; i < size; i++) 
	{
	    for (int j = 0; j < w.length; j++)
		dest[i][j] = x[i][0] && w[j];
	}

	return dest;
}


public static boolean[] eval(boolean[] x,boolean[][] w) {
    return eval(new boolean[w[0].length], x, w);
}

public static boolean[] eval(boolean[] dest, boolean[] x, boolean[][] w)
{
	int size = w.length;
	int vsize = w[0].length;

	if (x.length != size)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != vsize) {
        	dest = new boolean[vsize];
    	}

	for (int i = 0; i < vsize; i++)
	{
	    dest[i] = false;
	    for (int j = 0; j < size; j++)
		dest[i] = dest[i] || x[j] && w[j][i];
	}

	return dest;
}

public static boolean[][] eval(boolean[][] w, boolean[][] x) {
    return eval(new boolean[w.length][x[0].length], w, x);
}

public static boolean[][] eval(boolean[][] dest, boolean[][] w, boolean[][] x)
{
	int isize = w.length;
	int jsize = x[0].length;

	int size = w[0].length;

	if (size != x.length)
	{
		System.out.println("Inconsistent Array dimensions");
		return dest;
	}
	
	if (dest.length != isize || dest[0].length != jsize) {
        	dest = new boolean[isize][jsize];
    	}

	for (int i = 0; i < isize; i++)
	    for (int j = 0; j < jsize; j++)
	    {
	    	dest[i][j] = false;
	    	for (int k = 0; k < size; k++)
		   dest[i][j] = dest[i][j] || w[i][k] && x[k][j];
	    }

	return dest;
}

/* Added by Weifang */

public static boolean[][] eval(NslBoolean2 w, NslBoolean1 x) {
    return eval(w.getboolean2(),x.getboolean1());
}

public static boolean[][] eval(NslBoolean2 w, boolean[] x) {
    return eval(w.getboolean2(),x);
}

public static boolean[][] eval(boolean[][] w, NslBoolean1 x) {
    return eval(w,x.getboolean1());
}

public static boolean[][] eval(boolean[][] dest, NslBoolean2 w, NslBoolean1 x) {
    return eval(dest, w.getboolean2(),x.getboolean1());
}

public static boolean[][] eval(boolean[][] dest, NslBoolean2 w, boolean[] x) {
    return eval(dest, w.getboolean2(),x);
}

public static boolean[][] eval(boolean[][] dest, boolean[][] w, NslBoolean1 x) {
    return eval(dest, w,x.getboolean1());
}

//----

public static boolean[] eval(NslBoolean1 w, NslBoolean2 x) {
    return eval(w.getboolean1(), x.getboolean2());
}

public static boolean[] eval(NslBoolean1 w, boolean[][] x) {
    return eval(w.getboolean1(), x);
}

public static boolean[] eval(boolean[] w, NslBoolean2 x) {
    return eval(w, x.getboolean2());
}

public static boolean[] eval(boolean[] dest, NslBoolean1 w, NslBoolean2 x) {
    return eval(dest, w.getboolean1(), x.getboolean2());
}

public static boolean[] eval(boolean[] dest, NslBoolean1 w, boolean[][] x) {
    return eval(dest, w.getboolean1(), x);
}

public static boolean[] eval(boolean[] dest, boolean[] w, NslBoolean2 x) {
    return eval(dest, w, x.getboolean2());
}

//----

public static boolean[][] eval(NslBoolean2 w, NslBoolean2 x) {
    return eval(w.getboolean2(), x.getboolean2());
}

public static boolean[][] eval(NslBoolean2 w, boolean[][] x) {
    return eval(w.getboolean2(), x);
}

public static boolean[][] eval(boolean[][] w, NslBoolean2 x) {
    return eval(w, x.getboolean2());
}

public static boolean[][] eval(boolean[][] dest, NslBoolean2 w, NslBoolean2 x) {
    return eval(dest, w.getboolean2(), x.getboolean2());
}

public static boolean[][] eval(boolean[][] dest, NslBoolean2 w, boolean[][] x) {
    return eval(dest, w.getboolean2(), x);
}

public static boolean[][] eval(boolean[][] dest, boolean[][] w, NslBoolean2 x) {
    return eval(dest, w, x.getboolean2());
}

/* Nsl Dot product added here for compatibility with previous models */
/* this methods should be erased. */

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










