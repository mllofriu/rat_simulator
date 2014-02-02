/*  SCCS - @(#)NslGaussian.java	1.3 - 09/01/99 - 00:18:21 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Gaussian routines
//
//

/**
 Gaussian routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a) -> c
 a is the input parameter to pass the threshold function:
	(1/sqrt(2*PI*pow(sig,2)))*exp(-pow(x-mean,2)/(2*pow(sig,2)))
 2. eval(dest, a) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/

////////////////////////////////////////////////////////////////////////////////
// step functions
package nslj.src.math;
import nslj.src.lang.*;

public final class NslGaussian {

// doubles

  private static double value(double x, double sig, double mean) {
    double temp=2.0*Math.pow(sig,2.0);
    return (1/Math.sqrt(Math.PI*temp))*Math.exp(-Math.pow(x-mean,2.0)/temp);
  }

  public static double eval(double a) {
    return eval(a,1,0);
  }

  public static double[] eval(double[] a) {
    return eval(a,1,0);
  }

  public static double[][] eval(double[][] a) {
    return eval(a,1,0);
  }

  public static double[][][] eval(double[][][] a) {
    return eval(a,1,0);
  }

  public static double[][][][] eval(double[][][][] a) {
    return eval(a,1,0);
  }

  public static double[] eval(double[] dest, double[] a) {
    return eval(dest,a,1,0);
  }

  public static double[][] eval(double[][] dest, double[][] a) {
    return eval(dest,a,1,0);
  }

  public static double[][][] eval(double[][][] dest, double[][][] a) {
    return eval(dest,a,1,0);
  }

  public static double[][][][] eval(double[][][][] dest, double[][][][] a) {
    return eval(dest,a,1,0);
  }

  public static double eval(double a, double sig, double mean) {
    return value(a, sig, mean);
  }

  public static double[] eval (double[] a, double sig, double mean) {
 	double[] dest = new double[a.length];
	return eval(dest, sig, mean);
  }

  public static double[] eval (double[] dest, double[] a, double sig, double mean) {
    int i;    
    if (dest.length!=a.length) {
	dest = new double[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(a[i], sig, mean); 
    return dest;
  }

  public static double[][] eval (double[][] a, double sig, double mean) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a, sig, mean);
  }

  public static double[][] eval (double[][] dest, double[][] a, double sig, double mean) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new double[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(a[i][j], sig, mean);
    return dest;
  }

  public static double[][][] eval (double[][][] a, double sig, double mean) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, sig, mean);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double sig, double mean) {
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
	    for (k=0; k<size3; k++) dest[i][j][k]=value(a[i][j][k], sig, mean);
    return dest;
  }

  public static double[][][][] eval (double[][][][] a, double sig, double mean) {
    double[][][][] dest = new double[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, sig, mean);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double sig, double mean) {
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
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(a[i][j][k][l], sig, mean);
    return dest;
  }

// Nsl doubles

  public static double eval (NslDouble0 a) {
    return eval(a.getdouble());
  }

// --------------------------

  public static double eval (NslDouble0 a, double sig, double mean) {
    return eval(a.getdouble(), sig, mean);
  }

  public static double eval (NslDouble0 a, NslDouble0 sig, double mean) {
    return eval(a.getdouble(), sig.getdouble(), mean);
  }

  public static double eval (NslDouble0 a, double sig, NslDouble0 mean) {
    return eval(a.getdouble(), sig, mean.getdouble());
  }

  public static double eval (NslDouble0 a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a.getdouble(), sig.getdouble(), mean.getdouble());
  }

  public static double eval (double a, NslDouble0 sig, double mean) {
    return eval(a, sig.getdouble(), mean);
  }

  public static double eval (double a, double sig, NslDouble0 mean) {
    return eval(a, sig, mean.getdouble());
  }

  public static double eval (double a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a, sig.getdouble(), mean.getdouble());
  }

// --------------------------


  public static double[] eval (NslDouble1 a) {
    return eval(a.getdouble1());
  }

// --------------------------

  public static double[] eval (NslDouble1 a, double sig, double mean) {
    return eval(a.getdouble1(), sig, mean);
  }

  public static double[] eval (NslDouble1 a, NslDouble0 sig, double mean) {
    return eval(a.getdouble1(), sig.getdouble(), mean);
  }

  public static double[] eval (NslDouble1 a, double sig, NslDouble0 mean) {
    return eval(a.getdouble1(), sig, mean.getdouble());
  }

  public static double[] eval (NslDouble1 a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a.getdouble1(), sig.getdouble(), mean.getdouble());
  }

  public static double[] eval (double[] a, NslDouble0 sig, double mean) {
    return eval(a, sig.getdouble(), mean);
  }

  public static double[] eval (double[] a, double sig, NslDouble0 mean) {
    return eval(a, sig, mean.getdouble());
  }

  public static double[] eval (double[] a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a, sig.getdouble(), mean.getdouble());
  }

  public static double[] eval (double[] dest, NslDouble1 a, double sig, double mean) {
    return eval(dest, a.getdouble1(), sig, mean);
  }

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 sig, double mean) {
    return eval(dest, a.getdouble1(), sig.getdouble(), mean);
  }

  public static double[] eval (double[] dest, NslDouble1 a, double sig, NslDouble0 mean) {
    return eval(dest, a.getdouble1(), sig, mean.getdouble());
  }

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 sig, NslDouble0 mean) {
    return eval(dest, a.getdouble1(), sig.getdouble(), mean.getdouble());
  }

  public static double[] eval (double[] dest, double[] a, NslDouble0 sig, double mean) {
    return eval(dest, a, sig.getdouble(), mean);
  }

  public static double[] eval (double[] dest, double[] a, double sig, NslDouble0 mean) {
    return eval(dest, a, sig, mean.getdouble());
  }

  public static double[] eval (double[] dest, double[] a, NslDouble0 sig, NslDouble0 mean) {
    return eval(dest, a, sig.getdouble(), mean.getdouble());
  }

// --------------------------

  public static double[][] eval (NslDouble2 a) {
    return eval(a.getdouble2());
  }

// --------------------------

  public static double[][] eval (NslDouble2 a, double sig, double mean) {
    return eval(a.getdouble2(), sig, mean);
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 sig, double mean) {
    return eval(a.getdouble2(), sig.getdouble(), mean);
  }

  public static double[][] eval (NslDouble2 a, double sig, NslDouble0 mean) {
    return eval(a.getdouble2(), sig, mean.getdouble());
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a.getdouble2(), sig.getdouble(), mean.getdouble());
  }

  public static double[][] eval (double[][] a, NslDouble0 sig, double mean) {
    return eval(a, sig.getdouble(), mean);
  }

  public static double[][] eval (double[][] a, double sig, NslDouble0 mean) {
    return eval(a, sig, mean.getdouble());
  }

  public static double[][] eval (double[][] a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a, sig.getdouble(), mean.getdouble());
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double sig, double mean) {
    return eval(dest, a.getdouble2(), sig, mean);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 sig, double mean) {
    return eval(dest, a.getdouble2(), sig.getdouble(), mean);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double sig, NslDouble0 mean) {
    return eval(dest, a.getdouble2(), sig, mean.getdouble());
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 sig, NslDouble0 mean) {
    return eval(dest, a.getdouble2(), sig.getdouble(), mean.getdouble());
  }

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 sig, double mean) {
    return eval(dest, a, sig.getdouble(), mean);
  }

  public static double[][] eval (double[][] dest, double[][] a, double sig, NslDouble0 mean) {
    return eval(dest, a, sig, mean.getdouble());
  }

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 sig, NslDouble0 mean) {
    return eval(dest, a, sig.getdouble(), mean.getdouble());
  }


// --------------------------

  public static double[][][] eval (NslDouble3 a) {
    return eval(a.getdouble3());
  }

// --------------------------

  public static double[][][] eval (NslDouble3 a, double sig, double mean) {
    return eval(a.getdouble3(), sig, mean);
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 sig, double mean) {
    return eval(a.getdouble3(), sig.getdouble(), mean);
  }

  public static double[][][] eval (NslDouble3 a, double sig, NslDouble0 mean) {
    return eval(a.getdouble3(), sig, mean.getdouble());
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a.getdouble3(), sig.getdouble(), mean.getdouble());
  }

  public static double[][][] eval (double[][][] a, NslDouble0 sig, double mean) {
    return eval(a, sig.getdouble(), mean);
  }

  public static double[][][] eval (double[][][] a, double sig, NslDouble0 mean) {
    return eval(a, sig, mean.getdouble());
  }

  public static double[][][] eval (double[][][] a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a, sig.getdouble(), mean.getdouble());
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double sig, double mean) {
    return eval(dest, a.getdouble3(), sig, mean);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 sig, double mean) {
    return eval(dest, a.getdouble3(), sig.getdouble(), mean);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double sig, NslDouble0 mean) {
    return eval(dest, a.getdouble3(), sig, mean.getdouble());
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 sig, NslDouble0 mean) {
    return eval(dest, a.getdouble3(), sig.getdouble(), mean.getdouble());
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 sig, double mean) {
    return eval(dest, a, sig.getdouble(), mean);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double sig, NslDouble0 mean) {
    return eval(dest, a, sig, mean.getdouble());
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 sig, NslDouble0 mean) {
    return eval(dest, a, sig.getdouble(), mean.getdouble());
  }

// --------------------------

  public static double[][][][] eval (NslDouble4 a) {
    return eval(a.getdouble4());
  }

// --------------------------

  public static double[][][][] eval (NslDouble4 a, double sig, double mean) {
    return eval(a.getdouble4(), sig, mean);
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 sig, double mean) {
    return eval(a.getdouble4(), sig.getdouble(), mean);
  }

  public static double[][][][] eval (NslDouble4 a, double sig, NslDouble0 mean) {
    return eval(a.getdouble4(), sig, mean.getdouble());
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a.getdouble4(), sig.getdouble(), mean.getdouble());
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 sig, double mean) {
    return eval(a, sig.getdouble(), mean);
  }

  public static double[][][][] eval (double[][][][] a, double sig, NslDouble0 mean) {
    return eval(a, sig, mean.getdouble());
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 sig, NslDouble0 mean) {
    return eval(a, sig.getdouble(), mean.getdouble());
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double sig, double mean) {
    return eval(dest, a.getdouble4(), sig, mean);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 sig, double mean) {
    return eval(dest, a.getdouble4(), sig.getdouble(), mean);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double sig, NslDouble0 mean) {
    return eval(dest, a.getdouble4(), sig, mean.getdouble());
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 sig, NslDouble0 mean) {
    return eval(dest, a.getdouble4(), sig.getdouble(), mean.getdouble());
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 sig, double mean) {
    return eval(dest, a, sig.getdouble(), mean);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double sig, NslDouble0 mean) {
    return eval(dest, a, sig, mean.getdouble());
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 sig, NslDouble0 mean) {
    return eval(dest, a, sig.getdouble(), mean.getdouble());
  }

// floats

  private static float value(float x, float sig, float mean) {
    float temp=(float)(2.0*Math.pow(sig,2.0));
    return (float)((1/Math.sqrt(Math.PI*temp))*Math.exp(-Math.pow(x-mean,2.0)/temp));
  }

  public static float eval(float a) {
    return eval(a,1,0);
  }

  public static float[] eval(float[] a) {
    return eval(a,1,0);
  }

  public static float[][] eval(float[][] a) {
    return eval(a,1,0);
  }

  public static float[][][] eval(float[][][] a) {
    return eval(a,1,0);
  }

  public static float[][][][] eval(float[][][][] a) {
    return eval(a,1,0);
  }

  public static float[] eval(float[] dest, float[] a) {
    return eval(dest,a,1,0);
  }

  public static float[][] eval(float[][] dest, float[][] a) {
    return eval(dest,a,1,0);
  }

  public static float[][][] eval(float[][][] dest, float[][][] a) {
    return eval(dest,a,1,0);
  }

  public static float[][][][] eval(float[][][][] dest, float[][][][] a) {
    return eval(dest,a,1,0);
  }

  public static float eval(float a, float sig, float mean) {
    return value(a, sig, mean);
  }

  public static float[] eval (float[] a, float sig, float mean) {
 	float[] dest = new float[a.length];
	return eval(dest, sig, mean);
  }

  public static float[] eval (float[] dest, float[] a, float sig, float mean) {
    int i;    
    if (dest.length!=a.length) {
	dest = new float[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(a[i], sig, mean); 
    return dest;
  }

  public static float[][] eval (float[][] a, float sig, float mean) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a, sig, mean);
  }

  public static float[][] eval (float[][] dest, float[][] a, float sig, float mean) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new float[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(a[i][j], sig, mean);
    return dest;
  }

  public static float[][][] eval (float[][][] a, float sig, float mean) {
    float[][][] dest = new float[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, sig, mean);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float sig, float mean) {
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
	    for (k=0; k<size3; k++) dest[i][j][k]=value(a[i][j][k], sig, mean);
    return dest;
  }

  public static float[][][][] eval (float[][][][] a, float sig, float mean) {
    float[][][][] dest = new float[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, sig, mean);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float sig, float mean) {
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
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(a[i][j][k][l], sig, mean);
    return dest;
  }

// Nsl floats

  public static float eval (NslFloat0 a) {
    return eval(a.getfloat());
  }

// --------------------------

  public static float eval (NslFloat0 a, float sig, float mean) {
    return eval(a.getfloat(), sig, mean);
  }

  public static float eval (NslFloat0 a, NslFloat0 sig, float mean) {
    return eval(a.getfloat(), sig.getfloat(), mean);
  }

  public static float eval (NslFloat0 a, float sig, NslFloat0 mean) {
    return eval(a.getfloat(), sig, mean.getfloat());
  }

  public static float eval (NslFloat0 a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a.getfloat(), sig.getfloat(), mean.getfloat());
  }

  public static float eval (float a, NslFloat0 sig, float mean) {
    return eval(a, sig.getfloat(), mean);
  }

  public static float eval (float a, float sig, NslFloat0 mean) {
    return eval(a, sig, mean.getfloat());
  }

  public static float eval (float a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a, sig.getfloat(), mean.getfloat());
  }

// --------------------------


  public static float[] eval (NslFloat1 a) {
    return eval(a.getfloat1());
  }

// --------------------------

  public static float[] eval (NslFloat1 a, float sig, float mean) {
    return eval(a.getfloat1(), sig, mean);
  }

  public static float[] eval (NslFloat1 a, NslFloat0 sig, float mean) {
    return eval(a.getfloat1(), sig.getfloat(), mean);
  }

  public static float[] eval (NslFloat1 a, float sig, NslFloat0 mean) {
    return eval(a.getfloat1(), sig, mean.getfloat());
  }

  public static float[] eval (NslFloat1 a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a.getfloat1(), sig.getfloat(), mean.getfloat());
  }

  public static float[] eval (float[] a, NslFloat0 sig, float mean) {
    return eval(a, sig.getfloat(), mean);
  }

  public static float[] eval (float[] a, float sig, NslFloat0 mean) {
    return eval(a, sig, mean.getfloat());
  }

  public static float[] eval (float[] a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a, sig.getfloat(), mean.getfloat());
  }

  public static float[] eval (float[] dest, NslFloat1 a, float sig, float mean) {
    return eval(dest, a.getfloat1(), sig, mean);
  }

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 sig, float mean) {
    return eval(dest, a.getfloat1(), sig.getfloat(), mean);
  }

  public static float[] eval (float[] dest, NslFloat1 a, float sig, NslFloat0 mean) {
    return eval(dest, a.getfloat1(), sig, mean.getfloat());
  }

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 sig, NslFloat0 mean) {
    return eval(dest, a.getfloat1(), sig.getfloat(), mean.getfloat());
  }

  public static float[] eval (float[] dest, float[] a, NslFloat0 sig, float mean) {
    return eval(dest, a, sig.getfloat(), mean);
  }

  public static float[] eval (float[] dest, float[] a, float sig, NslFloat0 mean) {
    return eval(dest, a, sig, mean.getfloat());
  }

  public static float[] eval (float[] dest, float[] a, NslFloat0 sig, NslFloat0 mean) {
    return eval(dest, a, sig.getfloat(), mean.getfloat());
  }

// --------------------------

  public static float[][] eval (NslFloat2 a) {
    return eval(a.getfloat2());
  }

// --------------------------

  public static float[][] eval (NslFloat2 a, float sig, float mean) {
    return eval(a.getfloat2(), sig, mean);
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 sig, float mean) {
    return eval(a.getfloat2(), sig.getfloat(), mean);
  }

  public static float[][] eval (NslFloat2 a, float sig, NslFloat0 mean) {
    return eval(a.getfloat2(), sig, mean.getfloat());
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a.getfloat2(), sig.getfloat(), mean.getfloat());
  }

  public static float[][] eval (float[][] a, NslFloat0 sig, float mean) {
    return eval(a, sig.getfloat(), mean);
  }

  public static float[][] eval (float[][] a, float sig, NslFloat0 mean) {
    return eval(a, sig, mean.getfloat());
  }

  public static float[][] eval (float[][] a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a, sig.getfloat(), mean.getfloat());
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float sig, float mean) {
    return eval(dest, a.getfloat2(), sig, mean);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 sig, float mean) {
    return eval(dest, a.getfloat2(), sig.getfloat(), mean);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float sig, NslFloat0 mean) {
    return eval(dest, a.getfloat2(), sig, mean.getfloat());
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 sig, NslFloat0 mean) {
    return eval(dest, a.getfloat2(), sig.getfloat(), mean.getfloat());
  }

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 sig, float mean) {
    return eval(dest, a, sig.getfloat(), mean);
  }

  public static float[][] eval (float[][] dest, float[][] a, float sig, NslFloat0 mean) {
    return eval(dest, a, sig, mean.getfloat());
  }

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 sig, NslFloat0 mean) {
    return eval(dest, a, sig.getfloat(), mean.getfloat());
  }


// --------------------------

  public static float[][][] eval (NslFloat3 a) {
    return eval(a.getfloat3());
  }

// --------------------------

  public static float[][][] eval (NslFloat3 a, float sig, float mean) {
    return eval(a.getfloat3(), sig, mean);
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 sig, float mean) {
    return eval(a.getfloat3(), sig.getfloat(), mean);
  }

  public static float[][][] eval (NslFloat3 a, float sig, NslFloat0 mean) {
    return eval(a.getfloat3(), sig, mean.getfloat());
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a.getfloat3(), sig.getfloat(), mean.getfloat());
  }

  public static float[][][] eval (float[][][] a, NslFloat0 sig, float mean) {
    return eval(a, sig.getfloat(), mean);
  }

  public static float[][][] eval (float[][][] a, float sig, NslFloat0 mean) {
    return eval(a, sig, mean.getfloat());
  }

  public static float[][][] eval (float[][][] a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a, sig.getfloat(), mean.getfloat());
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float sig, float mean) {
    return eval(dest, a.getfloat3(), sig, mean);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 sig, float mean) {
    return eval(dest, a.getfloat3(), sig.getfloat(), mean);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float sig, NslFloat0 mean) {
    return eval(dest, a.getfloat3(), sig, mean.getfloat());
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 sig, NslFloat0 mean) {
    return eval(dest, a.getfloat3(), sig.getfloat(), mean.getfloat());
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 sig, float mean) {
    return eval(dest, a, sig.getfloat(), mean);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float sig, NslFloat0 mean) {
    return eval(dest, a, sig, mean.getfloat());
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 sig, NslFloat0 mean) {
    return eval(dest, a, sig.getfloat(), mean.getfloat());
  }

// --------------------------

  public static float[][][][] eval (NslFloat4 a) {
    return eval(a.getfloat4());
  }

// --------------------------

  public static float[][][][] eval (NslFloat4 a, float sig, float mean) {
    return eval(a.getfloat4(), sig, mean);
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 sig, float mean) {
    return eval(a.getfloat4(), sig.getfloat(), mean);
  }

  public static float[][][][] eval (NslFloat4 a, float sig, NslFloat0 mean) {
    return eval(a.getfloat4(), sig, mean.getfloat());
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a.getfloat4(), sig.getfloat(), mean.getfloat());
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 sig, float mean) {
    return eval(a, sig.getfloat(), mean);
  }

  public static float[][][][] eval (float[][][][] a, float sig, NslFloat0 mean) {
    return eval(a, sig, mean.getfloat());
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 sig, NslFloat0 mean) {
    return eval(a, sig.getfloat(), mean.getfloat());
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float sig, float mean) {
    return eval(dest, a.getfloat4(), sig, mean);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 sig, float mean) {
    return eval(dest, a.getfloat4(), sig.getfloat(), mean);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float sig, NslFloat0 mean) {
    return eval(dest, a.getfloat4(), sig, mean.getfloat());
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 sig, NslFloat0 mean) {
    return eval(dest, a.getfloat4(), sig.getfloat(), mean.getfloat());
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 sig, float mean) {
    return eval(dest, a, sig.getfloat(), mean);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float sig, NslFloat0 mean) {
    return eval(dest, a, sig, mean.getfloat());
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 sig, NslFloat0 mean) {
    return eval(dest, a, sig.getfloat(), mean.getfloat());
  }


}



