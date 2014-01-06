/*  SCCS - @(#)NslRandom.java	1.6 - 09/01/99 - 00:18:23 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Random routines
//
//

/**
 Random routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a) -> c
 a is the input parameter to pass the threshold function:
	rand()*(b-a)+a
 2. eval(dest, a) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/

////////////////////////////////////////////////////////////////////////////////
// step functions
package nslj.src.math;
import nslj.src.lang.*;

public final class NslRandom {

// doubles

  private static double value(double start, double end) {
    return Math.random()*(end-start)+start;
  }
  
  public static double eval() {
    return Math.random();
  }

  public static double[] eval(double[] a) {
    return eval(a,0,1);
  }

  public static double[][] eval(double[][] a) {
    return eval(a,0,1);
  }

  public static double[][][] eval(double[][][] a) {
    return eval(a,0,1);
  }

  public static double[][][][] eval(double[][][][] a) {
    return eval(a,0,1);
  }

  public static double[] eval(double[] dest, double[] a) {
    return eval(dest,a,0,1);
  }

  public static double[][] eval(double[][] dest, double[][] a) {
    return eval(dest,a,0,1);
  }

  public static double[][][] eval(double[][][] dest, double[][][] a) {
    return eval(dest,a,0,1);
  }

  public static double[][][][] eval(double[][][][] dest, double[][][][] a) {
    return eval(dest,a,0,1);
  }

  public static double eval(double start, double end) {
    return value(start, end);
  }

  public static double[] eval (double[] a, double start, double end) {
 	double[] dest = new double[a.length];
	return eval(dest, a, start, end);
  }

  public static double[] eval (double[] dest, double[] a, double start, double end) {
    int i;    
    if (dest.length!=a.length) {
	dest = new double[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(start, end); 
    return dest;
  }

  public static double[][] eval (double[][] a, double start, double end) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a, start, end);
  }

  public static double[][] eval (double[][] dest, double[][] a, double start, double end) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new double[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(start, end);
    return dest;
  }

  public static double[][][] eval (double[][][] a, double start, double end) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, start, end);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double start, double end) {
    int i,j,k;
    int size1 = a.length;
    int size2 = a[0].length;
    int size3 = a[0][0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length
	|| dest[0][0].length!=a[0][0].length) {
	dest = new double[a.length][a[0].length][a[0][0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++)
	    for (k=0; k<size3; k++) dest[i][j][k]=value(start, end);
    return dest;
  }

  public static double[][][][] eval (double[][][][] a, double start, double end) {
    double[][][][] dest = new double[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, start, end);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double start, double end) {
    int i,j,k,l;
    int size1 = a.length;
    int size2 = a[0].length;
    int size3 = a[0][0].length;
    int size4 = a[0][0][0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length
	|| dest[0][0].length!=a[0][0].length || dest[0][0][0].length!=a[0][0][0].length) {
	dest = new double[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    }

    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++)
	    for (k=0; k<size3; k++) 
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(start, end);
    return dest;
  }

// Nsl doubles
  
// --------------------------

  public static double eval (NslDouble0 start, double end) {
    return eval(start.getdouble(), end);
  }

  public static double eval (double start, NslDouble0 end) {
    return eval(start, end.getdouble());
  }

  public static double eval (NslDouble0 start, NslDouble0 end) {
    return eval(start.getdouble(), end.getdouble());
  }

// --------------------------


  public static double[] eval (NslDouble1 a) {
    return eval(a.getdouble1());
  }

  public static double[] eval (double[] dest, NslDouble1 a) {
    return eval(dest, a.getdouble1());
  }

// --------------------------

  public static double[] eval (NslDouble1 a, double start, double end) {
    return eval(a.getdouble1(), start, end);
  }

  public static double[] eval (NslDouble1 a, NslDouble0 start, double end) {
    return eval(a.getdouble1(), start.getdouble(), end);
  }

  public static double[] eval (NslDouble1 a, double start, NslDouble0 end) {
    return eval(a.getdouble1(), start, end.getdouble());
  }

  public static double[] eval (NslDouble1 a, NslDouble0 start, NslDouble0 end) {
    return eval(a.getdouble1(), start.getdouble(), end.getdouble());
  }

  public static double[] eval (double[] a, NslDouble0 start, double end) {
    return eval(a, start.getdouble(), end);
  }

  public static double[] eval (double[] a, double start, NslDouble0 end) {
    return eval(a, start, end.getdouble());
  }

  public static double[] eval (double[] a, NslDouble0 start, NslDouble0 end) {
    return eval(a, start.getdouble(), end.getdouble());
  }

  public static double[] eval (double[] dest, NslDouble1 a, double start, double end) {
    return eval(dest, a.getdouble1(), start, end);
  }

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 start, double end) {
    return eval(dest, a.getdouble1(), start.getdouble(), end);
  }

  public static double[] eval (double[] dest, NslDouble1 a, double start, NslDouble0 end) {
    return eval(dest, a.getdouble1(), start, end.getdouble());
  }

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 start, NslDouble0 end) {
    return eval(dest, a.getdouble1(), start.getdouble(), end.getdouble());
  }

  public static double[] eval (double[] dest, double[] a, NslDouble0 start, double end) {
    return eval(dest, a, start.getdouble(), end);
  }

  public static double[] eval (double[] dest, double[] a, double start, NslDouble0 end) {
    return eval(dest, a, start, end.getdouble());
  }

  public static double[] eval (double[] dest, double[] a, NslDouble0 start, NslDouble0 end) {
    return eval(dest, a, start.getdouble(), end.getdouble());
  }

// --------------------------

  public static double[][] eval (NslDouble2 a) {
    return eval(a.getdouble2());
  }
  
  public static double[][] eval (double[][] dest, NslDouble2 a) {
    return eval(dest, a.getdouble2());
  }

// --------------------------

  public static double[][] eval (NslDouble2 a, double start, double end) {
    return eval(a.getdouble2(), start, end);
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 start, double end) {
    return eval(a.getdouble2(), start.getdouble(), end);
  }

  public static double[][] eval (NslDouble2 a, double start, NslDouble0 end) {
    return eval(a.getdouble2(), start, end.getdouble());
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 start, NslDouble0 end) {
    return eval(a.getdouble2(), start.getdouble(), end.getdouble());
  }

  public static double[][] eval (double[][] a, NslDouble0 start, double end) {
    return eval(a, start.getdouble(), end);
  }

  public static double[][] eval (double[][] a, double start, NslDouble0 end) {
    return eval(a, start, end.getdouble());
  }

  public static double[][] eval (double[][] a, NslDouble0 start, NslDouble0 end) {
    return eval(a, start.getdouble(), end.getdouble());
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double start, double end) {
    return eval(dest, a.getdouble2(), start, end);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 start, double end) {
    return eval(dest, a.getdouble2(), start.getdouble(), end);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double start, NslDouble0 end) {
    return eval(dest, a.getdouble2(), start, end.getdouble());
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 start, NslDouble0 end) {
    return eval(dest, a.getdouble2(), start.getdouble(), end.getdouble());
  }

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 start, double end) {
    return eval(dest, a, start.getdouble(), end);
  }

  public static double[][] eval (double[][] dest, double[][] a, double start, NslDouble0 end) {
    return eval(dest, a, start, end.getdouble());
  }

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 start, NslDouble0 end) {
    return eval(dest, a, start.getdouble(), end.getdouble());
  }


// --------------------------

  public static double[][][] eval (NslDouble3 a) {
    return eval(a.getdouble3());
  }
  
  public static double[][][] eval (double[][][] dest, NslDouble3 a) {
    return eval(dest, a.getdouble3());
  }

// --------------------------

  public static double[][][] eval (NslDouble3 a, double start, double end) {
    return eval(a.getdouble3(), start, end);
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 start, double end) {
    return eval(a.getdouble3(), start.getdouble(), end);
  }

  public static double[][][] eval (NslDouble3 a, double start, NslDouble0 end) {
    return eval(a.getdouble3(), start, end.getdouble());
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 start, NslDouble0 end) {
    return eval(a.getdouble3(), start.getdouble(), end.getdouble());
  }

  public static double[][][] eval (double[][][] a, NslDouble0 start, double end) {
    return eval(a, start.getdouble(), end);
  }

  public static double[][][] eval (double[][][] a, double start, NslDouble0 end) {
    return eval(a, start, end.getdouble());
  }

  public static double[][][] eval (double[][][] a, NslDouble0 start, NslDouble0 end) {
    return eval(a, start.getdouble(), end.getdouble());
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double start, double end) {
    return eval(dest, a.getdouble3(), start, end);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 start, double end) {
    return eval(dest, a.getdouble3(), start.getdouble(), end);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double start, NslDouble0 end) {
    return eval(dest, a.getdouble3(), start, end.getdouble());
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 start, NslDouble0 end) {
    return eval(dest, a.getdouble3(), start.getdouble(), end.getdouble());
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 start, double end) {
    return eval(dest, a, start.getdouble(), end);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double start, NslDouble0 end) {
    return eval(dest, a, start, end.getdouble());
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 start, NslDouble0 end) {
    return eval(dest, a, start.getdouble(), end.getdouble());
  }

// --------------------------

  public static double[][][][] eval (NslDouble4 a) {
    return eval(a.getdouble4());
  }
  
  public static double[][][][] eval (double[][][][] dest, NslDouble4 a) {
    return eval(dest, a.getdouble4());
  }

// --------------------------

  public static double[][][][] eval (NslDouble4 a, double start, double end) {
    return eval(a.getdouble4(), start, end);
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 start, double end) {
    return eval(a.getdouble4(), start.getdouble(), end);
  }

  public static double[][][][] eval (NslDouble4 a, double start, NslDouble0 end) {
    return eval(a.getdouble4(), start, end.getdouble());
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 start, NslDouble0 end) {
    return eval(a.getdouble4(), start.getdouble(), end.getdouble());
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 start, double end) {
    return eval(a, start.getdouble(), end);
  }

  public static double[][][][] eval (double[][][][] a, double start, NslDouble0 end) {
    return eval(a, start, end.getdouble());
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 start, NslDouble0 end) {
    return eval(a, start.getdouble(), end.getdouble());
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double start, double end) {
    return eval(dest, a.getdouble4(), start, end);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 start, double end) {
    return eval(dest, a.getdouble4(), start.getdouble(), end);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double start, NslDouble0 end) {
    return eval(dest, a.getdouble4(), start, end.getdouble());
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 start, NslDouble0 end) {
    return eval(dest, a.getdouble4(), start.getdouble(), end.getdouble());
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 start, double end) {
    return eval(dest, a, start.getdouble(), end);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double start, NslDouble0 end) {
    return eval(dest, a, start, end.getdouble());
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 start, NslDouble0 end) {
    return eval(dest, a, start.getdouble(), end.getdouble());
  }

// floats

  private static float value( float start, float end) {
    return (float)(Math.random()*(end-start)+start);
  }

  public static float[] eval(float[] a) {
    return eval(a,0,1);
  }

  public static float[][] eval(float[][] a) {
    return eval(a,0,1);
  }

  public static float[][][] eval(float[][][] a) {
    return eval(a,0,1);
  }

  public static float[][][][] eval(float[][][][] a) {
    return eval(a,0,1);
  }

  public static float[] eval(float[] dest, float[] a) {
    return eval(dest,a,0,1);
  }

  public static float[][] eval(float[][] dest, float[][] a) {
    return eval(dest,a,0,1);
  }

  public static float[][][] eval(float[][][] dest, float[][][] a) {
    return eval(dest,a,0,1);
  }

  public static float[][][][] eval(float[][][][] dest, float[][][][] a) {
    return eval(dest,a,0,1);
  }

  public static float eval(float start, float end) {
    return value(start, end);
  }

  public static float[] eval (float[] a, float start, float end) {
 	float[] dest = new float[a.length];
	return eval(dest, a, start, end);
  }

  public static float[] eval (float[] dest, float[] a, float start, float end) {
    int i;    
    if (dest.length!=a.length) {
	dest = new float[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value( start, end); 
    return dest;
  }

  public static float[][] eval (float[][] a, float start, float end) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a, start, end);
  }

  public static float[][] eval (float[][] dest, float[][] a, float start, float end) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new float[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value( start, end);
    return dest;
  }

  public static float[][][] eval (float[][][] a, float start, float end) {
    float[][][] dest = new float[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, start, end);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float start, float end) {
    int i,j,k;
    int size1 = a.length;
    int size2 = a[0].length;
    int size3 = a[0][0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length
	|| dest[0][0].length!=a[0][0].length) {
	dest = new float[a.length][a[0].length][a[0][0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++)
	    for (k=0; k<size3; k++) dest[i][j][k]=value( start, end);
    return dest;
  }

  public static float[][][][] eval (float[][][][] a, float start, float end) {
    float[][][][] dest = new float[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, start, end);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float start, float end) {
    int i,j,k,l;
    int size1 = a.length;
    int size2 = a[0].length;
    int size3 = a[0][0].length;
    int size4 = a[0][0][0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length
	|| dest[0][0].length!=a[0][0].length || dest[0][0][0].length!=a[0][0][0].length) {
	dest = new float[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    }

    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++)
	    for (k=0; k<size3; k++) 
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(start, end);
    return dest;
  }

// Nsl floats

// --------------------------

  public static float eval (NslFloat0 start, float end) {
    return eval(start.getfloat(), end);
  }

  public static float eval (float start, NslFloat0 end) {
    return eval(start, end.getfloat());
  }

  public static float eval (NslFloat0 start, NslFloat0 end) {
    return eval(start.getfloat(), end.getfloat());
  }

// --------------------------


  public static float[] eval (NslFloat1 a) {
    return eval(a.getfloat1());
  }

  public static float[] eval (float[] dest, NslFloat1 a) {
    return eval(dest, a.getfloat1());
  }

// --------------------------

  public static float[] eval (NslFloat1 a, float start, float end) {
    return eval(a.getfloat1(), start, end);
  }

  public static float[] eval (NslFloat1 a, NslFloat0 start, float end) {
    return eval(a.getfloat1(), start.getfloat(), end);
  }

  public static float[] eval (NslFloat1 a, float start, NslFloat0 end) {
    return eval(a.getfloat1(), start, end.getfloat());
  }

  public static float[] eval (NslFloat1 a, NslFloat0 start, NslFloat0 end) {
    return eval(a.getfloat1(), start.getfloat(), end.getfloat());
  }

  public static float[] eval (float[] a, NslFloat0 start, float end) {
    return eval(a, start.getfloat(), end);
  }

  public static float[] eval (float[] a, float start, NslFloat0 end) {
    return eval(a, start, end.getfloat());
  }

  public static float[] eval (float[] a, NslFloat0 start, NslFloat0 end) {
    return eval(a, start.getfloat(), end.getfloat());
  }

  public static float[] eval (float[] dest, NslFloat1 a, float start, float end) {
    return eval(dest, a.getfloat1(), start, end);
  }

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 start, float end) {
    return eval(dest, a.getfloat1(), start.getfloat(), end);
  }

  public static float[] eval (float[] dest, NslFloat1 a, float start, NslFloat0 end) {
    return eval(dest, a.getfloat1(), start, end.getfloat());
  }

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 start, NslFloat0 end) {
    return eval(dest, a.getfloat1(), start.getfloat(), end.getfloat());
  }

  public static float[] eval (float[] dest, float[] a, NslFloat0 start, float end) {
    return eval(dest, a, start.getfloat(), end);
  }

  public static float[] eval (float[] dest, float[] a, float start, NslFloat0 end) {
    return eval(dest, a, start, end.getfloat());
  }

  public static float[] eval (float[] dest, float[] a, NslFloat0 start, NslFloat0 end) {
    return eval(dest, a, start.getfloat(), end.getfloat());
  }

// --------------------------

  public static float[][] eval (NslFloat2 a) {
    return eval(a.getfloat2());
  }

  public static float[][] eval (float[][] dest, NslFloat2 a) {
    return eval(dest, a.getfloat2());
  }

// --------------------------

  public static float[][] eval (NslFloat2 a, float start, float end) {
    return eval(a.getfloat2(), start, end);
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 start, float end) {
    return eval(a.getfloat2(), start.getfloat(), end);
  }

  public static float[][] eval (NslFloat2 a, float start, NslFloat0 end) {
    return eval(a.getfloat2(), start, end.getfloat());
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 start, NslFloat0 end) {
    return eval(a.getfloat2(), start.getfloat(), end.getfloat());
  }

  public static float[][] eval (float[][] a, NslFloat0 start, float end) {
    return eval(a, start.getfloat(), end);
  }

  public static float[][] eval (float[][] a, float start, NslFloat0 end) {
    return eval(a, start, end.getfloat());
  }

  public static float[][] eval (float[][] a, NslFloat0 start, NslFloat0 end) {
    return eval(a, start.getfloat(), end.getfloat());
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float start, float end) {
    return eval(dest, a.getfloat2(), start, end);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 start, float end) {
    return eval(dest, a.getfloat2(), start.getfloat(), end);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float start, NslFloat0 end) {
    return eval(dest, a.getfloat2(), start, end.getfloat());
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 start, NslFloat0 end) {
    return eval(dest, a.getfloat2(), start.getfloat(), end.getfloat());
  }

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 start, float end) {
    return eval(dest, a, start.getfloat(), end);
  }

  public static float[][] eval (float[][] dest, float[][] a, float start, NslFloat0 end) {
    return eval(dest, a, start, end.getfloat());
  }

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 start, NslFloat0 end) {
    return eval(dest, a, start.getfloat(), end.getfloat());
  }


// --------------------------

  public static float[][][] eval (NslFloat3 a) {
    return eval(a.getfloat3());
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a) {
    return eval(dest, a.getfloat3());
  }

// --------------------------

  public static float[][][] eval (NslFloat3 a, float start, float end) {
    return eval(a.getfloat3(), start, end);
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 start, float end) {
    return eval(a.getfloat3(), start.getfloat(), end);
  }

  public static float[][][] eval (NslFloat3 a, float start, NslFloat0 end) {
    return eval(a.getfloat3(), start, end.getfloat());
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 start, NslFloat0 end) {
    return eval(a.getfloat3(), start.getfloat(), end.getfloat());
  }

  public static float[][][] eval (float[][][] a, NslFloat0 start, float end) {
    return eval(a, start.getfloat(), end);
  }

  public static float[][][] eval (float[][][] a, float start, NslFloat0 end) {
    return eval(a, start, end.getfloat());
  }

  public static float[][][] eval (float[][][] a, NslFloat0 start, NslFloat0 end) {
    return eval(a, start.getfloat(), end.getfloat());
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float start, float end) {
    return eval(dest, a.getfloat3(), start, end);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 start, float end) {
    return eval(dest, a.getfloat3(), start.getfloat(), end);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float start, NslFloat0 end) {
    return eval(dest, a.getfloat3(), start, end.getfloat());
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 start, NslFloat0 end) {
    return eval(dest, a.getfloat3(), start.getfloat(), end.getfloat());
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 start, float end) {
    return eval(dest, a, start.getfloat(), end);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float start, NslFloat0 end) {
    return eval(dest, a, start, end.getfloat());
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 start, NslFloat0 end) {
    return eval(dest, a, start.getfloat(), end.getfloat());
  }

// --------------------------

  public static float[][][][] eval (NslFloat4 a) {
    return eval(a.getfloat4());
  }
  
  public static float[][][][] eval (float[][][][] dest, NslFloat4 a) {
    return eval(dest, a.getfloat4());
  }

// --------------------------

  public static float[][][][] eval (NslFloat4 a, float start, float end) {
    return eval(a.getfloat4(), start, end);
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 start, float end) {
    return eval(a.getfloat4(), start.getfloat(), end);
  }

  public static float[][][][] eval (NslFloat4 a, float start, NslFloat0 end) {
    return eval(a.getfloat4(), start, end.getfloat());
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 start, NslFloat0 end) {
    return eval(a.getfloat4(), start.getfloat(), end.getfloat());
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 start, float end) {
    return eval(a, start.getfloat(), end);
  }

  public static float[][][][] eval (float[][][][] a, float start, NslFloat0 end) {
    return eval(a, start, end.getfloat());
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 start, NslFloat0 end) {
    return eval(a, start.getfloat(), end.getfloat());
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float start, float end) {
    return eval(dest, a.getfloat4(), start, end);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 start, float end) {
    return eval(dest, a.getfloat4(), start.getfloat(), end);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float start, NslFloat0 end) {
    return eval(dest, a.getfloat4(), start, end.getfloat());
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 start, NslFloat0 end) {
    return eval(dest, a.getfloat4(), start.getfloat(), end.getfloat());
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 start, float end) {
    return eval(dest, a, start.getfloat(), end);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float start, NslFloat0 end) {
    return eval(dest, a, start, end.getfloat());
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 start, NslFloat0 end) {
    return eval(dest, a, start.getfloat(), end.getfloat());
  }


// ints

  private static int value( int start, int end) {
    return (int)(Math.random()*(end-start)+start);
  }

  public static int[] eval(int[] a) {
    return eval(a,0,1);
  }

  public static int[][] eval(int[][] a) {
    return eval(a,0,1);
  }

  public static int[][][] eval(int[][][] a) {
    return eval(a,0,1);
  }

  public static int[][][][] eval(int[][][][] a) {
    return eval(a,0,1);
  }

  public static int[] eval(int[] dest, int[] a) {
    return eval(dest,a,0,1);
  }

  public static int[][] eval(int[][] dest, int[][] a) {
    return eval(dest,a,0,1);
  }

  public static int[][][] eval(int[][][] dest, int[][][] a) {
    return eval(dest,a,0,1);
  }

  public static int[][][][] eval(int[][][][] dest, int[][][][] a) {
    return eval(dest,a,0,1);
  }

  public static int eval(int start, int end) {
    return value( start, end);
  }

  public static int[] eval (int[] a, int start, int end) {
 	int[] dest = new int[a.length];
	return eval(dest, a, start, end);
  }

  public static int[] eval (int[] dest, int[] a, int start, int end) {
    int i;    
    if (dest.length!=a.length) {
	dest = new int[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(start, end); 
    return dest;
  }

  public static int[][] eval (int[][] a, int start, int end) {
    int[][] dest = new int[a.length][a[0].length];
    return eval(dest, a, start, end);
  }

  public static int[][] eval (int[][] dest, int[][] a, int start, int end) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new int[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value( start, end);
    return dest;
  }

  public static int[][][] eval (int[][][] a, int start, int end) {
    int[][][] dest = new int[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, start, end);
  }

  public static int[][][] eval (int[][][] dest, int[][][] a, int start, int end) {
    int i,j,k;
    int size1 = a.length;
    int size2 = a[0].length;
    int size3 = a[0][0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length
	|| dest[0][0].length!=a[0][0].length) {
	dest = new int[a.length][a[0].length][a[0][0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++)
	    for (k=0; k<size3; k++) dest[i][j][k]=value( start, end);
    return dest;
  }

  public static int[][][][] eval (int[][][][] a, int start, int end) {
    int[][][][] dest = new int[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, start, end);
  }

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, int start, int end) {
    int i,j,k,l;
    int size1 = a.length;
    int size2 = a[0].length;
    int size3 = a[0][0].length;
    int size4 = a[0][0][0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length
	|| dest[0][0].length!=a[0][0].length || dest[0][0][0].length!=a[0][0][0].length) {
	dest = new int[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    }

    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++)
	    for (k=0; k<size3; k++) 
		for (l=0; l<size4; l++) dest[i][j][k][l]=value( start, end);
    return dest;
  }

// Nsl ints

// --------------------------

  public static int eval (NslInt0 start, int end) {
    return eval(start.getint(), end);
  }

  public static int eval (int start, NslInt0 end) {
    return eval(start, end.getint());
  }

  public static int eval (NslInt0 start, NslInt0 end) {
    return eval(start.getint(), end.getint());
  }

// --------------------------

  public static int[] eval (NslInt1 a) {
    return eval(a.getint1());
  }

  public static int[] eval (int[] dest, NslInt1 a) {
    return eval(dest, a.getint1());
  }

// --------------------------

  public static int[] eval (NslInt1 a, int start, int end) {
    return eval(a.getint1(), start, end);
  }

  public static int[] eval (NslInt1 a, NslInt0 start, int end) {
    return eval(a.getint1(), start.getint(), end);
  }

  public static int[] eval (NslInt1 a, int start, NslInt0 end) {
    return eval(a.getint1(), start, end.getint());
  }

  public static int[] eval (NslInt1 a, NslInt0 start, NslInt0 end) {
    return eval(a.getint1(), start.getint(), end.getint());
  }

  public static int[] eval (int[] a, NslInt0 start, int end) {
    return eval(a, start.getint(), end);
  }

  public static int[] eval (int[] a, int start, NslInt0 end) {
    return eval(a, start, end.getint());
  }

  public static int[] eval (int[] a, NslInt0 start, NslInt0 end) {
    return eval(a, start.getint(), end.getint());
  }

  public static int[] eval (int[] dest, NslInt1 a, int start, int end) {
    return eval(dest, a.getint1(), start, end);
  }

  public static int[] eval (int[] dest, NslInt1 a, NslInt0 start, int end) {
    return eval(dest, a.getint1(), start.getint(), end);
  }

  public static int[] eval (int[] dest, NslInt1 a, int start, NslInt0 end) {
    return eval(dest, a.getint1(), start, end.getint());
  }

  public static int[] eval (int[] dest, NslInt1 a, NslInt0 start, NslInt0 end) {
    return eval(dest, a.getint1(), start.getint(), end.getint());
  }

  public static int[] eval (int[] dest, int[] a, NslInt0 start, int end) {
    return eval(dest, a, start.getint(), end);
  }

  public static int[] eval (int[] dest, int[] a, int start, NslInt0 end) {
    return eval(dest, a, start, end.getint());
  }

  public static int[] eval (int[] dest, int[] a, NslInt0 start, NslInt0 end) {
    return eval(dest, a, start.getint(), end.getint());
  }

// --------------------------

  public static int[][] eval (NslInt2 a) {
    return eval(a.getint2());
  }
  
  public static int[][] eval (int[][] dest, NslInt2 a) {
    return eval(dest, a.getint2());
  }


// --------------------------

  public static int[][] eval (NslInt2 a, int start, int end) {
    return eval(a.getint2(), start, end);
  }

  public static int[][] eval (NslInt2 a, NslInt0 start, int end) {
    return eval(a.getint2(), start.getint(), end);
  }

  public static int[][] eval (NslInt2 a, int start, NslInt0 end) {
    return eval(a.getint2(), start, end.getint());
  }

  public static int[][] eval (NslInt2 a, NslInt0 start, NslInt0 end) {
    return eval(a.getint2(), start.getint(), end.getint());
  }

  public static int[][] eval (int[][] a, NslInt0 start, int end) {
    return eval(a, start.getint(), end);
  }

  public static int[][] eval (int[][] a, int start, NslInt0 end) {
    return eval(a, start, end.getint());
  }

  public static int[][] eval (int[][] a, NslInt0 start, NslInt0 end) {
    return eval(a, start.getint(), end.getint());
  }

  public static int[][] eval (int[][] dest, NslInt2 a, int start, int end) {
    return eval(dest, a.getint2(), start, end);
  }

  public static int[][] eval (int[][] dest, NslInt2 a, NslInt0 start, int end) {
    return eval(dest, a.getint2(), start.getint(), end);
  }

  public static int[][] eval (int[][] dest, NslInt2 a, int start, NslInt0 end) {
    return eval(dest, a.getint2(), start, end.getint());
  }

  public static int[][] eval (int[][] dest, NslInt2 a, NslInt0 start, NslInt0 end) {
    return eval(dest, a.getint2(), start.getint(), end.getint());
  }

  public static int[][] eval (int[][] dest, int[][] a, NslInt0 start, int end) {
    return eval(dest, a, start.getint(), end);
  }

  public static int[][] eval (int[][] dest, int[][] a, int start, NslInt0 end) {
    return eval(dest, a, start, end.getint());
  }

  public static int[][] eval (int[][] dest, int[][] a, NslInt0 start, NslInt0 end) {
    return eval(dest, a, start.getint(), end.getint());
  }


// --------------------------

  public static int[][][] eval (NslInt3 a) {
    return eval(a.getint3());
  }
  
  public static int[][][] eval (int[][][] dest, NslInt3 a) {
    return eval(dest, a.getint3());
  }

// --------------------------

  public static int[][][] eval (NslInt3 a, int start, int end) {
    return eval(a.getint3(), start, end);
  }

  public static int[][][] eval (NslInt3 a, NslInt0 start, int end) {
    return eval(a.getint3(), start.getint(), end);
  }

  public static int[][][] eval (NslInt3 a, int start, NslInt0 end) {
    return eval(a.getint3(), start, end.getint());
  }

  public static int[][][] eval (NslInt3 a, NslInt0 start, NslInt0 end) {
    return eval(a.getint3(), start.getint(), end.getint());
  }

  public static int[][][] eval (int[][][] a, NslInt0 start, int end) {
    return eval(a, start.getint(), end);
  }

  public static int[][][] eval (int[][][] a, int start, NslInt0 end) {
    return eval(a, start, end.getint());
  }

  public static int[][][] eval (int[][][] a, NslInt0 start, NslInt0 end) {
    return eval(a, start.getint(), end.getint());
  }

  public static int[][][] eval (int[][][] dest, NslInt3 a, int start, int end) {
    return eval(dest, a.getint3(), start, end);
  }

  public static int[][][] eval (int[][][] dest, NslInt3 a, NslInt0 start, int end) {
    return eval(dest, a.getint3(), start.getint(), end);
  }

  public static int[][][] eval (int[][][] dest, NslInt3 a, int start, NslInt0 end) {
    return eval(dest, a.getint3(), start, end.getint());
  }

  public static int[][][] eval (int[][][] dest, NslInt3 a, NslInt0 start, NslInt0 end) {
    return eval(dest, a.getint3(), start.getint(), end.getint());
  }

  public static int[][][] eval (int[][][] dest, int[][][] a, NslInt0 start, int end) {
    return eval(dest, a, start.getint(), end);
  }

  public static int[][][] eval (int[][][] dest, int[][][] a, int start, NslInt0 end) {
    return eval(dest, a, start, end.getint());
  }

  public static int[][][] eval (int[][][] dest, int[][][] a, NslInt0 start, NslInt0 end) {
    return eval(dest, a, start.getint(), end.getint());
  }

// --------------------------

  public static int[][][][] eval (NslInt4 a) {
    return eval(a.getint4());
  }
  
  public static int[][][][] eval (int[][][][] dest, NslInt4 a) {
    return eval(dest, a.getint4());
  }

// --------------------------

  public static int[][][][] eval (NslInt4 a, int start, int end) {
    return eval(a.getint4(), start, end);
  }

  public static int[][][][] eval (NslInt4 a, NslInt0 start, int end) {
    return eval(a.getint4(), start.getint(), end);
  }

  public static int[][][][] eval (NslInt4 a, int start, NslInt0 end) {
    return eval(a.getint4(), start, end.getint());
  }

  public static int[][][][] eval (NslInt4 a, NslInt0 start, NslInt0 end) {
    return eval(a.getint4(), start.getint(), end.getint());
  }

  public static int[][][][] eval (int[][][][] a, NslInt0 start, int end) {
    return eval(a, start.getint(), end);
  }

  public static int[][][][] eval (int[][][][] a, int start, NslInt0 end) {
    return eval(a, start, end.getint());
  }

  public static int[][][][] eval (int[][][][] a, NslInt0 start, NslInt0 end) {
    return eval(a, start.getint(), end.getint());
  }

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, int start, int end) {
    return eval(dest, a.getint4(), start, end);
  }

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, NslInt0 start, int end) {
    return eval(dest, a.getint4(), start.getint(), end);
  }

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, int start, NslInt0 end) {
    return eval(dest, a.getint4(), start, end.getint());
  }

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, NslInt0 start, NslInt0 end) {
    return eval(dest, a.getint4(), start.getint(), end.getint());
  }

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, NslInt0 start, int end) {
    return eval(dest, a, start.getint(), end);
  }

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, int start, NslInt0 end) {
    return eval(dest, a, start, end.getint());
  }

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, NslInt0 start, NslInt0 end) {
    return eval(dest, a, start.getint(), end.getint());
  }

}




