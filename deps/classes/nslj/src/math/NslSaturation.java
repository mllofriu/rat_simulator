/*  SCCS - @(#)NslSaturation.java	1.6 - 09/01/99 - 00:18:08 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Saturation threshold routines
//
//



/**
 Saturation threshold routines.
 There are four basic format for the evaluation method in
 this routine:
 1, eval(a) -> c
 a is the input parameter to pass the threshold function:
    if a < 0,           c = 0,
    else if 0 <= a < 1, c = a;
    else                c = 1.

 2. eval(dest, a) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

 3. eval(a, kx1, kx2, ky1, ky2) -> c
    if a < kx1,                 c = ky1,
    else if kx1 <= a < kx2,     c=(ky2-ky1)(a-kx1)/(kx2-kx1)+ky1,
    else,                       c = ky2.

 2. eval(dest, a, kx1, kx2, ky1, ky2) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/

////////////////////////////////////////////////////////////////////////////////
// saturation functions
package nslj.src.math;
import nslj.src.lang.*;

public final class NslSaturation {

// doubles

  private static double value(double x, double slope, double offset) {    
    return slope*(x-offset);
  }

  public static double value(double x, double kx1, double kx2, double ky1, double ky2) {
    if (x<kx1) 
	return ky1;
    if (x>=kx2)
	return ky2;
    return (((ky2-ky1)*(x-kx1))/(kx2-kx1))+ky1;
  }

  public static double eval(double a) {
    return eval(a,0,1,0,1);
  }

  public static double[] eval(double[] a) {
    return eval(a,0,1,0,1);
  }

  public static double[][] eval(double[][] a) {
    return eval(a,0,1,0,1);
  }

  public static double[][][] eval(double[][][] a) {
    return eval(a,0,1,0,1);
  }

  public static double[][][][] eval(double[][][][] a) {
    return eval(a,0,1,0,1);
  }

  public static double[] eval(double[] dest, double[] a) {
    return eval(dest,a,0,1,0,1);
  }

  public static double[][] eval(double[][] dest, double[][] a) {
    return eval(dest,a,0,1,0,1);
  }

  public static double[][][] eval(double[][][] dest, double[][][] a) {
    return eval(dest,a,0,1,0,1);
  }

  public static double[][][][] eval(double[][][][] dest, double[][][][] a) {
    return eval(dest,a,0,1,0,1);
  }

  public static double eval(double a, double slope, double offset) {
    return value(a,slope,offset);
  }

  public static double eval(double a, double kx1, double kx2, double ky1, double ky2) {
    return value(a, kx1, kx2, ky1, ky2);
  }

  public static double[] eval (double[] a, double slope, double offset) {
 	double[] dest = new double[a.length];
	return eval(dest, a, slope, offset);
  }

  public static double[] eval (double[] dest, double[] a, double slope, double offset) {
    int i;    
    if (dest.length!=a.length) {
	dest = new double[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(a[i], slope, offset); 
    return dest;
  }

  public static double[] eval (double[] a, double kx1, double kx2, double ky1, double ky2) {
 	double[] dest = new double[a.length];
	return eval(dest, a, kx1, kx2, ky1, ky2);
  }

  public static double[] eval (double[] dest, double[] a, double kx1, double kx2, double ky1, double ky2) {
    int i;    
    if (dest.length!=a.length) {
	dest = new double[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(a[i], kx1, kx2, ky1, ky2); 
    return dest;
  }

  public static double[][] eval (double[][] a, double slope, double offset) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a, slope, offset);
  }

  public static double[][] eval (double[][] dest, double[][] a, double slope, double offset) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new double[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(a[i][j], slope, offset);
    return dest;
  }

  public static double[][] eval (double[][] a, double kx1, double kx2, double ky1, double ky2) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a, kx1, kx2, ky1, ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, double kx1, double kx2, double ky1, double ky2) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new double[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(a[i][j], kx1, kx2, ky1, ky2);
    return dest;
  }

  public static double[][][] eval (double[][][] a, double slope, double offset) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, slope, offset);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double slope, double offset) {
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
	    for (k=0; k<size3; k++) dest[i][j][k]=value(a[i][j][k], slope, offset);
    return dest;
  }

  public static double[][][] eval (double[][][] a, double kx1, double kx2, double ky1, double ky2) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, kx1, kx2, ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, double kx2, double ky1, double ky2) {
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
	    for (k=0; k<size3; k++) dest[i][j][k]=value(a[i][j][k], kx1, kx2, ky1, ky2);
    return dest;
  }

  public static double[][][][] eval (double[][][][] a, double slope, double offset) {
    double[][][][] dest = new double[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, slope, offset);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double slope, double offset) {
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
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(a[i][j][k][l], slope, offset);
    return dest;
  }

  public static double[][][][] eval (double[][][][] a, double kx1, double kx2, double ky1, double ky2) {
    double[][][][] dest = new double[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, kx1, kx2, ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, double kx2, double ky1, double ky2) {
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
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(a[i][j][k][l], kx1, kx2, ky1, ky2);
    return dest;
  }

// Nsl doubles

  public static double eval (NslDouble0 a) {
    return eval(a.getdouble());
  }

  public static double eval (NslDouble0 a, double slope, double offset) {
    return eval(a.getdouble(), slope, offset);
  }

  public static double eval (NslDouble0 a, NslDouble0 slope, double offset) {
    return eval(a.getdouble(), slope.getdouble(), offset);
  }

  public static double eval (NslDouble0 a, double slope, NslDouble0 offset) {
    return eval(a.getdouble(), slope, offset.getdouble());
  }

  public static double eval (NslDouble0 a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a.getdouble(), slope.getdouble(), offset.getdouble());
  }

  public static double eval (double a, NslDouble0 slope, double offset) {
    return eval(a, slope.getdouble(), offset);
  }

  public static double eval (double a, double slope, NslDouble0 offset) {
    return eval(a, slope, offset.getdouble());
  }

  public static double eval (double a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a, slope.getdouble(), offset.getdouble());
  }

// --------------------------

  public static double eval (NslDouble0 a, double kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble(), kx1, kx2, ky1, ky2);
  }

// 1

  public static double eval (NslDouble0 a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble(), kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double eval (NslDouble0 a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble(), kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double eval (NslDouble0 a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble(), kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double eval (NslDouble0 a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double eval (NslDouble0 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble(), kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double eval (NslDouble0 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble(), kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double eval (NslDouble0 a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double eval (NslDouble0 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble(), kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double eval (NslDouble0 a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double eval (NslDouble0 a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double eval (NslDouble0 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double eval (NslDouble0 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double eval (NslDouble0 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double eval (NslDouble0 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double eval (NslDouble0 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double eval (double a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double eval (double a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double eval (double a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double eval (double a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double eval (double a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double eval (double a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double eval (double a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double eval (double a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double eval (double a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double eval (double a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double eval (double a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double eval (double a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double eval (double a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double eval (double a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double eval (double a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

  public static double[] eval (NslDouble1 a) {
    return eval(a.getdouble1());
  }

  public static double[] eval (NslDouble1 a, double slope, double offset) {
    return eval(a.getdouble1(), slope, offset);
  }

  public static double[] eval (NslDouble1 a, NslDouble0 slope, double offset) {
    return eval(a.getdouble1(), slope.getdouble(), offset);
  }

  public static double[] eval (NslDouble1 a, double slope, NslDouble0 offset) {
    return eval(a.getdouble1(), slope, offset.getdouble());
  }

  public static double[] eval (NslDouble1 a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a.getdouble1(), slope.getdouble(), offset.getdouble());
  }

  public static double[] eval (double[] a, NslDouble0 slope, double offset) {
    return eval(a, slope.getdouble(), offset);
  }

  public static double[] eval (double[] a, double slope, NslDouble0 offset) {
    return eval(a, slope, offset.getdouble());
  }

  public static double[] eval (double[] a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a, slope.getdouble(), offset.getdouble());
  }

// --------------------------

  public static double[] eval (NslDouble1 a, double kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble1(), kx1, kx2, ky1, ky2);
  }

// 1

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[] eval (NslDouble1 a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble1(), kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[] eval (NslDouble1 a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble1(), kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[] eval (NslDouble1 a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[] eval (NslDouble1 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble1(), kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (NslDouble1 a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[] eval (NslDouble1 a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[] eval (NslDouble1 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[] eval (double[] a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[] eval (double[] a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[] eval (double[] a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[] eval (double[] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[] eval (double[] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[] eval (double[] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[] eval (double[] a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[] eval (double[] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[] eval (double[] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[] eval (double[] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[] eval (double[] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1, kx2, ky1, ky2);
  }

// 1

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[] eval (double[] dest, double[] a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[] eval (double[] dest, double[] a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, double[] a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[] eval (double[] dest, double[] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, double[] a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[] eval (double[] dest, double[] a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[] eval (double[] dest, double[] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

  public static double[][] eval (NslDouble2 a) {
    return eval(a.getdouble2());
  }

  public static double[][] eval (NslDouble2 a, double slope, double offset) {
    return eval(a.getdouble2(), slope, offset);
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 slope, double offset) {
    return eval(a.getdouble2(), slope.getdouble(), offset);
  }

  public static double[][] eval (NslDouble2 a, double slope, NslDouble0 offset) {
    return eval(a.getdouble2(), slope, offset.getdouble());
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a.getdouble2(), slope.getdouble(), offset.getdouble());
  }

  public static double[][] eval (double[][] a, NslDouble0 slope, double offset) {
    return eval(a, slope.getdouble(), offset);
  }

  public static double[][] eval (double[][] a, double slope, NslDouble0 offset) {
    return eval(a, slope, offset.getdouble());
  }

  public static double[][] eval (double[][] a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a, slope.getdouble(), offset.getdouble());
  }

// --------------------------

  public static double[][] eval (NslDouble2 a, double kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble2(), kx1, kx2, ky1, ky2);
  }

// 1

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][] eval (NslDouble2 a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble2(), kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][] eval (NslDouble2 a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble2(), kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][] eval (NslDouble2 a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][] eval (NslDouble2 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble2(), kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (NslDouble2 a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][] eval (NslDouble2 a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][] eval (NslDouble2 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][] eval (double[][] a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][] eval (double[][] a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][] eval (double[][] a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][] eval (double[][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][] eval (double[][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][] eval (double[][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][] eval (double[][] a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][] eval (double[][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][] eval (double[][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][] eval (double[][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][] eval (double[][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1, kx2, ky1, ky2);
  }

// 1

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][] eval (double[][] dest, double[][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][] eval (double[][] dest, double[][] a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][] eval (double[][] dest, double[][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

  public static double[][][] eval (NslDouble3 a) {
    return eval(a.getdouble3());
  }

  public static double[][][] eval (NslDouble3 a, double slope, double offset) {
    return eval(a.getdouble3(), slope, offset);
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 slope, double offset) {
    return eval(a.getdouble3(), slope.getdouble(), offset);
  }

  public static double[][][] eval (NslDouble3 a, double slope, NslDouble0 offset) {
    return eval(a.getdouble3(), slope, offset.getdouble());
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a.getdouble3(), slope.getdouble(), offset.getdouble());
  }

  public static double[][][] eval (double[][][] a, NslDouble0 slope, double offset) {
    return eval(a, slope.getdouble(), offset);
  }

  public static double[][][] eval (double[][][] a, double slope, NslDouble0 offset) {
    return eval(a, slope, offset.getdouble());
  }

  public static double[][][] eval (double[][][] a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a, slope.getdouble(), offset.getdouble());
  }

// --------------------------

  public static double[][][] eval (NslDouble3 a, double kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble3(), kx1, kx2, ky1, ky2);
  }

// 1

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][][] eval (NslDouble3 a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble3(), kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (NslDouble3 a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble3(), kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (NslDouble3 a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][][] eval (NslDouble3 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble3(), kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (NslDouble3 a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][] eval (NslDouble3 a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][][] eval (NslDouble3 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][][] eval (double[][][] a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (double[][][] a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][][] eval (double[][][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][] eval (double[][][] a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][][] eval (double[][][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1, kx2, ky1, ky2);
  }

// 1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

  public static double[][][][] eval (NslDouble4 a) {
    return eval(a.getdouble4());
  }

  public static double[][][][] eval (NslDouble4 a, double slope, double offset) {
    return eval(a.getdouble4(), slope, offset);
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 slope, double offset) {
    return eval(a.getdouble4(), slope.getdouble(), offset);
  }

  public static double[][][][] eval (NslDouble4 a, double slope, NslDouble0 offset) {
    return eval(a.getdouble4(), slope, offset.getdouble());
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a.getdouble4(), slope.getdouble(), offset.getdouble());
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 slope, double offset) {
    return eval(a, slope.getdouble(), offset);
  }

  public static double[][][][] eval (double[][][][] a, double slope, NslDouble0 offset) {
    return eval(a, slope, offset.getdouble());
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 slope, NslDouble0 offset) {
    return eval(a, slope.getdouble(), offset.getdouble());
  }

// --------------------------

  public static double[][][][] eval (NslDouble4 a, double kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble4(), kx1, kx2, ky1, ky2);
  }

// 1

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][][][] eval (NslDouble4 a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble4(), kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (NslDouble4 a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble4(), kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (NslDouble4 a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][][][] eval (NslDouble4 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble4(), kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (NslDouble4 a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][][] eval (NslDouble4 a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][][][] eval (NslDouble4 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][][][] eval (double[][][][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][][] eval (double[][][][] a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][][][] eval (double[][][][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1, kx2, ky1, ky2);
  }

// 1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, double kx2, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, kx2, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, double kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1, ky2.getdouble());
  }

// 2 a kx2

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 kx2

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, NslDouble0 kx2, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1, ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, double kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx2 ky1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 4 a kx1 kx2 ky1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), kx2.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// floats

  private static float value(float x, float slope, float offset) {    
    return slope*(x-offset);
  }

  public static float value(float x, float kx1, float kx2, float ky1, float ky2) {
    if (x<kx1) 
	return ky1;
    if (x>=kx2)
	return ky2;
    return (((ky2-ky1)*(x-kx1))/(kx2-kx1))+ky1;
  }

  public static float eval(float a) {
    return eval(a,0,1,0,1);
  }

  public static float[] eval(float[] a) {
    return eval(a,0,1,0,1);
  }

  public static float[][] eval(float[][] a) {
    return eval(a,0,1,0,1);
  }

  public static float[][][] eval(float[][][] a) {
    return eval(a,0,1,0,1);
  }

  public static float[][][][] eval(float[][][][] a) {
    return eval(a,0,1,0,1);
  }

  public static float[] eval(float[] dest, float[] a) {
    return eval(dest,a,0,1,0,1);
  }

  public static float[][] eval(float[][] dest, float[][] a) {
    return eval(dest,a,0,1,0,1);
  }

  public static float[][][] eval(float[][][] dest, float[][][] a) {
    return eval(dest,a,0,1,0,1);
  }

  public static float[][][][] eval(float[][][][] dest, float[][][][] a) {
    return eval(dest,a,0,1,0,1);
  }

  public static float eval(float a, float slope, float offset) {
    return value(a,slope,offset);
  }

  public static float eval(float a, float kx1, float kx2, float ky1, float ky2) {
    return value(a, kx1, kx2, ky1, ky2);
  }

  public static float[] eval (float[] a, float slope, float offset) {
 	float[] dest = new float[a.length];
	return eval(dest, a, slope, offset);
  }

  public static float[] eval (float[] dest, float[] a, float slope, float offset) {
    int i;    
    if (dest.length!=a.length) {
	dest = new float[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(a[i], slope, offset); 
    return dest;
  }

  public static float[] eval (float[] a, float kx1, float kx2, float ky1, float ky2) {
 	float[] dest = new float[a.length];
	return eval(dest, a, kx1, kx2, ky1, ky2);
  }

  public static float[] eval (float[] dest, float[] a, float kx1, float kx2, float ky1, float ky2) {
    int i;    
    if (dest.length!=a.length) {
	dest = new float[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(a[i], kx1, kx2, ky1, ky2); 
    return dest;
  }

  public static float[][] eval (float[][] a, float slope, float offset) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a, slope, offset);
  }

  public static float[][] eval (float[][] dest, float[][] a, float slope, float offset) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new float[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(a[i][j], slope, offset);
    return dest;
  }

  public static float[][] eval (float[][] a, float kx1, float kx2, float ky1, float ky2) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a, kx1, kx2, ky1, ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, float kx1, float kx2, float ky1, float ky2) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new float[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(a[i][j], kx1, kx2, ky1, ky2);
    return dest;
  }

  public static float[][][] eval (float[][][] a, float slope, float offset) {
    float[][][] dest = new float[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, slope, offset);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float slope, float offset) {
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
	    for (k=0; k<size3; k++) dest[i][j][k]=value(a[i][j][k], slope, offset);
    return dest;
  }

  public static float[][][] eval (float[][][] a, float kx1, float kx2, float ky1, float ky2) {
    float[][][] dest = new float[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, kx1, kx2, ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, float kx2, float ky1, float ky2) {
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
	    for (k=0; k<size3; k++) dest[i][j][k]=value(a[i][j][k], kx1, kx2, ky1, ky2);
    return dest;
  }

  public static float[][][][] eval (float[][][][] a, float slope, float offset) {
    float[][][][] dest = new float[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, slope, offset);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float slope, float offset) {
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
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(a[i][j][k][l], slope, offset);
    return dest;
  }

  public static float[][][][] eval (float[][][][] a, float kx1, float kx2, float ky1, float ky2) {
    float[][][][] dest = new float[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, kx1, kx2, ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, float kx2, float ky1, float ky2) {
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
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(a[i][j][k][l], kx1, kx2, ky1, ky2);
    return dest;
  }

// Nsl floats

  public static float eval (NslFloat0 a) {
    return eval(a.getfloat());
  }

  public static float eval (NslFloat0 a, float slope, float offset) {
    return eval(a.getfloat(), slope, offset);
  }

  public static float eval (NslFloat0 a, NslFloat0 slope, float offset) {
    return eval(a.getfloat(), slope.getfloat(), offset);
  }

  public static float eval (NslFloat0 a, float slope, NslFloat0 offset) {
    return eval(a.getfloat(), slope, offset.getfloat());
  }

  public static float eval (NslFloat0 a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a.getfloat(), slope.getfloat(), offset.getfloat());
  }

  public static float eval (float a, NslFloat0 slope, float offset) {
    return eval(a, slope.getfloat(), offset);
  }

  public static float eval (float a, float slope, NslFloat0 offset) {
    return eval(a, slope, offset.getfloat());
  }

  public static float eval (float a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a, slope.getfloat(), offset.getfloat());
  }

// --------------------------

  public static float eval (NslFloat0 a, float kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat(), kx1, kx2, ky1, ky2);
  }

// 1

  public static float eval (NslFloat0 a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat(), kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float eval (NslFloat0 a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat(), kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float eval (NslFloat0 a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat(), kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float eval (NslFloat0 a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float eval (NslFloat0 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat(), kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float eval (NslFloat0 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat(), kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float eval (NslFloat0 a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float eval (NslFloat0 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat(), kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float eval (NslFloat0 a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float eval (NslFloat0 a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float eval (NslFloat0 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float eval (NslFloat0 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float eval (NslFloat0 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float eval (NslFloat0 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float eval (NslFloat0 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float eval (float a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float eval (float a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float eval (float a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float eval (float a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float eval (float a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float eval (float a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float eval (float a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float eval (float a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float eval (float a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float eval (float a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float eval (float a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float eval (float a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float eval (float a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float eval (float a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float eval (float a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

  public static float[] eval (NslFloat1 a) {
    return eval(a.getfloat1());
  }

  public static float[] eval (NslFloat1 a, float slope, float offset) {
    return eval(a.getfloat1(), slope, offset);
  }

  public static float[] eval (NslFloat1 a, NslFloat0 slope, float offset) {
    return eval(a.getfloat1(), slope.getfloat(), offset);
  }

  public static float[] eval (NslFloat1 a, float slope, NslFloat0 offset) {
    return eval(a.getfloat1(), slope, offset.getfloat());
  }

  public static float[] eval (NslFloat1 a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a.getfloat1(), slope.getfloat(), offset.getfloat());
  }

  public static float[] eval (float[] a, NslFloat0 slope, float offset) {
    return eval(a, slope.getfloat(), offset);
  }

  public static float[] eval (float[] a, float slope, NslFloat0 offset) {
    return eval(a, slope, offset.getfloat());
  }

  public static float[] eval (float[] a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a, slope.getfloat(), offset.getfloat());
  }

// --------------------------

  public static float[] eval (NslFloat1 a, float kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat1(), kx1, kx2, ky1, ky2);
  }

// 1

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[] eval (NslFloat1 a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat1(), kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[] eval (NslFloat1 a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat1(), kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[] eval (NslFloat1 a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[] eval (NslFloat1 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat1(), kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (NslFloat1 a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[] eval (NslFloat1 a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[] eval (NslFloat1 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[] eval (float[] a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[] eval (float[] a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[] eval (float[] a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[] eval (float[] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[] eval (float[] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[] eval (float[] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[] eval (float[] a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[] eval (float[] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[] eval (float[] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[] eval (float[] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[] eval (float[] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1, kx2, ky1, ky2);
  }

// 1

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[] eval (float[] dest, float[] a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[] eval (float[] dest, float[] a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, float[] a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[] eval (float[] dest, float[] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, float[] a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[] eval (float[] dest, float[] a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[] eval (float[] dest, float[] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

  public static float[][] eval (NslFloat2 a) {
    return eval(a.getfloat2());
  }

  public static float[][] eval (NslFloat2 a, float slope, float offset) {
    return eval(a.getfloat2(), slope, offset);
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 slope, float offset) {
    return eval(a.getfloat2(), slope.getfloat(), offset);
  }

  public static float[][] eval (NslFloat2 a, float slope, NslFloat0 offset) {
    return eval(a.getfloat2(), slope, offset.getfloat());
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a.getfloat2(), slope.getfloat(), offset.getfloat());
  }

  public static float[][] eval (float[][] a, NslFloat0 slope, float offset) {
    return eval(a, slope.getfloat(), offset);
  }

  public static float[][] eval (float[][] a, float slope, NslFloat0 offset) {
    return eval(a, slope, offset.getfloat());
  }

  public static float[][] eval (float[][] a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a, slope.getfloat(), offset.getfloat());
  }

// --------------------------

  public static float[][] eval (NslFloat2 a, float kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat2(), kx1, kx2, ky1, ky2);
  }

// 1

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][] eval (NslFloat2 a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat2(), kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][] eval (NslFloat2 a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat2(), kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][] eval (NslFloat2 a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][] eval (NslFloat2 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat2(), kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (NslFloat2 a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][] eval (NslFloat2 a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][] eval (NslFloat2 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][] eval (float[][] a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][] eval (float[][] a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][] eval (float[][] a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][] eval (float[][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][] eval (float[][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][] eval (float[][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][] eval (float[][] a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][] eval (float[][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][] eval (float[][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][] eval (float[][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][] eval (float[][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1, kx2, ky1, ky2);
  }

// 1

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][] eval (float[][] dest, float[][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][] eval (float[][] dest, float[][] a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][] eval (float[][] dest, float[][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

  public static float[][][] eval (NslFloat3 a) {
    return eval(a.getfloat3());
  }

  public static float[][][] eval (NslFloat3 a, float slope, float offset) {
    return eval(a.getfloat3(), slope, offset);
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 slope, float offset) {
    return eval(a.getfloat3(), slope.getfloat(), offset);
  }

  public static float[][][] eval (NslFloat3 a, float slope, NslFloat0 offset) {
    return eval(a.getfloat3(), slope, offset.getfloat());
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a.getfloat3(), slope.getfloat(), offset.getfloat());
  }

  public static float[][][] eval (float[][][] a, NslFloat0 slope, float offset) {
    return eval(a, slope.getfloat(), offset);
  }

  public static float[][][] eval (float[][][] a, float slope, NslFloat0 offset) {
    return eval(a, slope, offset.getfloat());
  }

  public static float[][][] eval (float[][][] a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a, slope.getfloat(), offset.getfloat());
  }

// --------------------------

  public static float[][][] eval (NslFloat3 a, float kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat3(), kx1, kx2, ky1, ky2);
  }

// 1

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][][] eval (NslFloat3 a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat3(), kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (NslFloat3 a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat3(), kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (NslFloat3 a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][][] eval (NslFloat3 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat3(), kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (NslFloat3 a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][] eval (NslFloat3 a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][][] eval (NslFloat3 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][][] eval (float[][][] a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (float[][][] a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][][] eval (float[][][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][] eval (float[][][] a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][][] eval (float[][][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1, kx2, ky1, ky2);
  }

// 1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

  public static float[][][][] eval (NslFloat4 a) {
    return eval(a.getfloat4());
  }

  public static float[][][][] eval (NslFloat4 a, float slope, float offset) {
    return eval(a.getfloat4(), slope, offset);
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 slope, float offset) {
    return eval(a.getfloat4(), slope.getfloat(), offset);
  }

  public static float[][][][] eval (NslFloat4 a, float slope, NslFloat0 offset) {
    return eval(a.getfloat4(), slope, offset.getfloat());
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a.getfloat4(), slope.getfloat(), offset.getfloat());
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 slope, float offset) {
    return eval(a, slope.getfloat(), offset);
  }

  public static float[][][][] eval (float[][][][] a, float slope, NslFloat0 offset) {
    return eval(a, slope, offset.getfloat());
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 slope, NslFloat0 offset) {
    return eval(a, slope.getfloat(), offset.getfloat());
  }

// --------------------------

  public static float[][][][] eval (NslFloat4 a, float kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat4(), kx1, kx2, ky1, ky2);
  }

// 1

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][][][] eval (NslFloat4 a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat4(), kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (NslFloat4 a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat4(), kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (NslFloat4 a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][][][] eval (NslFloat4 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat4(), kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (NslFloat4 a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][][] eval (NslFloat4 a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][][][] eval (NslFloat4 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][][][] eval (float[][][][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][][] eval (float[][][][] a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][][][] eval (float[][][][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1, kx2, ky1, ky2);
  }

// 1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, float kx2, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, kx2, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, float kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1, ky2.getfloat());
  }

// 2 a kx2

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 kx2

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, NslFloat0 kx2, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1, ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, float kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx2 ky1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 4 a kx1 kx2 ky1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), kx2.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

}

