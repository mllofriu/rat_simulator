/*  SCCS - %W% - %G% - %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Step threshold routines
//
//

/**
 Step threshold routines.
 There are four basic format for the evaluation method in
 this routine:
 1, eval(a) -> c
 a is the input parameter to pass the threshold function:
    if a < 0, c = 0,
    else      c = 1.

 2. eval(dest, a) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

 3. eval(a, kx1, ky1, ky2) -> c
    if a < kx1, c = ky1,
    else,       c = ky2.

 2. eval(dest, a, kx1, ky1, ky2) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/

////////////////////////////////////////////////////////////////////////////////
// step functions
package nslj.src.math;
import nslj.src.lang.*;

public final class NslStep {

// doubles

  private static double value(double x, double kx1, double ky1, double ky2) {
    if (x<kx1) 
	return ky1;
    return ky2;
  }

  public static double eval(double a) {
    return eval(a,0,0,1);
  }

  public static double[] eval(double[] a) {
    return eval(a,0,0,1);
  }

  public static double[][] eval(double[][] a) {
    return eval(a,0,0,1);
  }

  public static double[][][] eval(double[][][] a) {
    return eval(a,0,0,1);
  }

  public static double[][][][] eval(double[][][][] a) {
    return eval(a,0,0,1);
  }

  public static double[] eval(double[] dest, double[] a) {
    return eval(dest,a,0,0,1);
  }

  public static double[][] eval(double[][] dest, double[][] a) {
    return eval(dest,a,0,0,1);
  }

  public static double[][][] eval(double[][][] dest, double[][][] a) {
    return eval(dest,a,0,0,1);
  }

  public static double[][][][] eval(double[][][][] dest, double[][][][] a) {
    return eval(dest,a,0,0,1);
  }

  public static double eval(double a, double kx1, double ky1, double ky2) {
    return value(a, kx1, ky1, ky2);
  }

  public static double[] eval (double[] a, double kx1, double ky1, double ky2) {
 	double[] dest = new double[a.length];
	return eval(dest, a, kx1, ky1, ky2);
  }

  public static double[] eval (double[] dest, double[] a, double kx1, double ky1, double ky2) {
    int i;    
    if (dest.length!=a.length) {
	dest = new double[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(a[i], kx1, ky1, ky2); 
    return dest;
  }

  public static double[][] eval (double[][] a, double kx1, double ky1, double ky2) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a, kx1, ky1, ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, double kx1, double ky1, double ky2) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new double[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(a[i][j], kx1, ky1, ky2);
    return dest;
  }

  public static double[][][] eval (double[][][] a, double kx1, double ky1, double ky2) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, kx1, ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, double ky1, double ky2) {
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
	    for (k=0; k<size3; k++) dest[i][j][k]=value(a[i][j][k], kx1, ky1, ky2);
    return dest;
  }

  public static double[][][][] eval (double[][][][] a, double kx1, double ky1, double ky2) {
    double[][][][] dest = new double[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, kx1, ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, double ky1, double ky2) {
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
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(a[i][j][k][l], kx1, ky1, ky2);
    return dest;
  }

// Nsl doubles

  public static double eval (NslDouble0 a) {
    return eval(a.getdouble());
  }

// --------------------------

  public static double eval (NslDouble0 a, double kx1, double ky1, double ky2) {
    return eval(a.getdouble(), kx1, ky1, ky2);
  }

// 1

  public static double eval (NslDouble0 a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a.getdouble(), kx1.getdouble(), ky1, ky2);
  }

  public static double eval (NslDouble0 a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble(), kx1, ky1.getdouble(), ky2);
  }

  public static double eval (NslDouble0 a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double eval (NslDouble0 a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble(), kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double eval (NslDouble0 a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double eval (NslDouble0 a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double eval (NslDouble0 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble(), kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double eval (double a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2);
  }

  public static double eval (double a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2);
  }

  public static double eval (double a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double eval (double a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double eval (double a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double eval (double a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double eval (double a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// --------------------------


  public static double[] eval (NslDouble1 a) {
    return eval(a.getdouble1());
  }

// --------------------------

  public static double[] eval (NslDouble1 a, double kx1, double ky1, double ky2) {
    return eval(a.getdouble1(), kx1, ky1, ky2);
  }

// 1

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), ky1, ky2);
  }

  public static double[] eval (NslDouble1 a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble1(), kx1, ky1.getdouble(), ky2);
  }

  public static double[] eval (NslDouble1 a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[] eval (NslDouble1 a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble1(), kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[] eval (double[] a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2);
  }

  public static double[] eval (double[] a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[] eval (double[] a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[] eval (double[] a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[] eval (double[] a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// dest

  public static double[] eval (double[] dest, NslDouble1 a) {
    return eval(dest, a.getdouble1());
  }

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, double ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1, ky1, ky2);
  }

// 1

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), ky1, ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1, ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[] eval (double[] dest, NslDouble1 a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[] eval (double[] dest, NslDouble1 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble1(), kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), ky1, ky2);
  }

  public static double[] eval (double[] dest, double[] a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, double[] a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[] eval (double[] dest, double[] a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[] eval (double[] dest, double[] a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// --------------------------


  public static double[][] eval (NslDouble2 a) {
    return eval(a.getdouble2());
  }

// --------------------------

  public static double[][] eval (NslDouble2 a, double kx1, double ky1, double ky2) {
    return eval(a.getdouble2(), kx1, ky1, ky2);
  }

// 1

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), ky1, ky2);
  }

  public static double[][] eval (NslDouble2 a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble2(), kx1, ky1.getdouble(), ky2);
  }

  public static double[][] eval (NslDouble2 a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][] eval (NslDouble2 a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble2(), kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][] eval (double[][] a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2);
  }

  public static double[][] eval (double[][] a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][] eval (double[][] a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][] eval (double[][] a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][] eval (double[][] a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// dest

  public static double[][] eval (double[][] dest, NslDouble2 a) {
    return eval(dest, a.getdouble2());
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, double ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1, ky1, ky2);
  }

// 1

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), ky1, ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1, ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][] eval (double[][] dest, NslDouble2 a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][] eval (double[][] dest, NslDouble2 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble2(), kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), ky1, ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][] eval (double[][] dest, double[][] a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][] eval (double[][] dest, double[][] a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// --------------------------

  public static double[][][] eval (NslDouble3 a) {
    return eval(a.getdouble3());
  }

// --------------------------

  public static double[][][] eval (NslDouble3 a, double kx1, double ky1, double ky2) {
    return eval(a.getdouble3(), kx1, ky1, ky2);
  }

// 1

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (NslDouble3 a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble3(), kx1, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (NslDouble3 a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][] eval (NslDouble3 a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][] eval (NslDouble3 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble3(), kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (double[][][] a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][] eval (double[][][] a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][] eval (double[][][] a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// dest

  public static double[][][] eval (double[][][] dest, NslDouble3 a) {
    return eval(dest, a.getdouble3());
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, double ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1, ky1, ky2);
  }

// 1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][] eval (double[][][] dest, NslDouble3 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble3(), kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), ky1, ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][] eval (double[][][] dest, double[][][] a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][] eval (double[][][] dest, double[][][] a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// --------------------------



  public static double[][][][] eval (NslDouble4 a) {
    return eval(a.getdouble4());
  }

// --------------------------

  public static double[][][][] eval (NslDouble4 a, double kx1, double ky1, double ky2) {
    return eval(a.getdouble4(), kx1, ky1, ky2);
  }

// 1

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (NslDouble4 a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble4(), kx1, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (NslDouble4 a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][][] eval (NslDouble4 a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][][] eval (NslDouble4 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a.getdouble4(), kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][][] eval (double[][][][] a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][][] eval (double[][][][] a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(a, kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// dest

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a) {
    return eval(dest, a.getdouble4());
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, double ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1, ky1, ky2);
  }

// 1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][][] eval (double[][][][] dest, NslDouble4 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a.getdouble4(), kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// 1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, double ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), ky1, ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1, ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getdouble());
  }

// 2 a kx1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, NslDouble0 ky1, double ky2) {
    return eval(dest, a, kx1.getdouble(), ky1.getdouble(), ky2);
  }

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, double ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), ky1, ky2.getdouble());
  }

// 2 a ky1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, double kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1, ky1.getdouble(), ky2.getdouble());
  }

// 3 a kx1 ky1

  public static double[][][][] eval (double[][][][] dest, double[][][][] a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return eval(dest, a, kx1.getdouble(), ky1.getdouble(), ky2.getdouble());
  }

// --------------------------

// floats

  private static float value(float x, float kx1, float ky1, float ky2) {
    if (x<kx1) 
	return ky1;
    return ky2;
  }

  public static float eval(float a) {
    return eval(a,0,0,1);
  }

  public static float[] eval(float[] a) {
    return eval(a,0,0,1);
  }

  public static float[][] eval(float[][] a) {
    return eval(a,0,0,1);
  }

  public static float[][][] eval(float[][][] a) {
    return eval(a,0,0,1);
  }

  public static float[][][][] eval(float[][][][] a) {
    return eval(a,0,0,1);
  }

  public static float[] eval(float[] dest, float[] a) {
    return eval(dest,a,0,0,1);
  }

  public static float[][] eval(float[][] dest, float[][] a) {
    return eval(dest,a,0,0,1);
  }

  public static float[][][] eval(float[][][] dest, float[][][] a) {
    return eval(dest,a,0,0,1);
  }

  public static float[][][][] eval(float[][][][] dest, float[][][][] a) {
    return eval(dest,a,0,0,1);
  }

  public static float eval(float a, float kx1, float ky1, float ky2) {
    return value(a, kx1, ky1, ky2);
  }

  public static float[] eval (float[] a, float kx1, float ky1, float ky2) {
 	float[] dest = new float[a.length];
	return eval(dest, a, kx1, ky1, ky2);
  }

  public static float[] eval (float[] dest, float[] a, float kx1, float ky1, float ky2) {
    int i;    
    if (dest.length!=a.length) {
	dest = new float[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(a[i], kx1, ky1, ky2); 
    return dest;
  }

  public static float[][] eval (float[][] a, float kx1, float ky1, float ky2) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a, kx1, ky1, ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, float kx1, float ky1, float ky2) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new float[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(a[i][j], kx1, ky1, ky2);
    return dest;
  }

  public static float[][][] eval (float[][][] a, float kx1, float ky1, float ky2) {
    float[][][] dest = new float[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, kx1, ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, float ky1, float ky2) {
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
	    for (k=0; k<size3; k++) dest[i][j][k]=value(a[i][j][k], kx1, ky1, ky2);
    return dest;
  }

  public static float[][][][] eval (float[][][][] a, float kx1, float ky1, float ky2) {
    float[][][][] dest = new float[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, kx1, ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, float ky1, float ky2) {
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
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(a[i][j][k][l], kx1, ky1, ky2);
    return dest;
  }

// Nsl floats

  public static float eval (NslFloat0 a) {
    return eval(a.getfloat());
  }

// --------------------------

  public static float eval (NslFloat0 a, float kx1, float ky1, float ky2) {
    return eval(a.getfloat(), kx1, ky1, ky2);
  }

// 1

  public static float eval (NslFloat0 a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a.getfloat(), kx1.getfloat(), ky1, ky2);
  }

  public static float eval (NslFloat0 a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat(), kx1, ky1.getfloat(), ky2);
  }

  public static float eval (NslFloat0 a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float eval (NslFloat0 a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat(), kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float eval (NslFloat0 a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float eval (NslFloat0 a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float eval (NslFloat0 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat(), kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float eval (float a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2);
  }

  public static float eval (float a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2);
  }

  public static float eval (float a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float eval (float a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float eval (float a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float eval (float a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float eval (float a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// --------------------------


  public static float[] eval (NslFloat1 a) {
    return eval(a.getfloat1());
  }

// --------------------------

  public static float[] eval (NslFloat1 a, float kx1, float ky1, float ky2) {
    return eval(a.getfloat1(), kx1, ky1, ky2);
  }

// 1

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), ky1, ky2);
  }

  public static float[] eval (NslFloat1 a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat1(), kx1, ky1.getfloat(), ky2);
  }

  public static float[] eval (NslFloat1 a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[] eval (NslFloat1 a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat1(), kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[] eval (float[] a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2);
  }

  public static float[] eval (float[] a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[] eval (float[] a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[] eval (float[] a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[] eval (float[] a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// dest

  public static float[] eval (float[] dest, NslFloat1 a) {
    return eval(dest, a.getfloat1());
  }

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, float ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1, ky1, ky2);
  }

// 1

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), ky1, ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1, ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[] eval (float[] dest, NslFloat1 a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[] eval (float[] dest, NslFloat1 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat1(), kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), ky1, ky2);
  }

  public static float[] eval (float[] dest, float[] a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, float[] a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[] eval (float[] dest, float[] a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[] eval (float[] dest, float[] a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// --------------------------


  public static float[][] eval (NslFloat2 a) {
    return eval(a.getfloat2());
  }

// --------------------------

  public static float[][] eval (NslFloat2 a, float kx1, float ky1, float ky2) {
    return eval(a.getfloat2(), kx1, ky1, ky2);
  }

// 1

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), ky1, ky2);
  }

  public static float[][] eval (NslFloat2 a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat2(), kx1, ky1.getfloat(), ky2);
  }

  public static float[][] eval (NslFloat2 a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][] eval (NslFloat2 a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat2(), kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][] eval (float[][] a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2);
  }

  public static float[][] eval (float[][] a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][] eval (float[][] a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][] eval (float[][] a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][] eval (float[][] a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// dest

  public static float[][] eval (float[][] dest, NslFloat2 a) {
    return eval(dest, a.getfloat2());
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, float ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1, ky1, ky2);
  }

// 1

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), ky1, ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1, ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][] eval (float[][] dest, NslFloat2 a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][] eval (float[][] dest, NslFloat2 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat2(), kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), ky1, ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][] eval (float[][] dest, float[][] a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][] eval (float[][] dest, float[][] a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// --------------------------

  public static float[][][] eval (NslFloat3 a) {
    return eval(a.getfloat3());
  }

// --------------------------

  public static float[][][] eval (NslFloat3 a, float kx1, float ky1, float ky2) {
    return eval(a.getfloat3(), kx1, ky1, ky2);
  }

// 1

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (NslFloat3 a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat3(), kx1, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (NslFloat3 a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][] eval (NslFloat3 a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][] eval (NslFloat3 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat3(), kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (float[][][] a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][] eval (float[][][] a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][] eval (float[][][] a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// dest

  public static float[][][] eval (float[][][] dest, NslFloat3 a) {
    return eval(dest, a.getfloat3());
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, float ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1, ky1, ky2);
  }

// 1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][] eval (float[][][] dest, NslFloat3 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat3(), kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), ky1, ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][] eval (float[][][] dest, float[][][] a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][] eval (float[][][] dest, float[][][] a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// --------------------------



  public static float[][][][] eval (NslFloat4 a) {
    return eval(a.getfloat4());
  }

// --------------------------

  public static float[][][][] eval (NslFloat4 a, float kx1, float ky1, float ky2) {
    return eval(a.getfloat4(), kx1, ky1, ky2);
  }

// 1

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (NslFloat4 a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat4(), kx1, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (NslFloat4 a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][][] eval (NslFloat4 a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][][] eval (NslFloat4 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a.getfloat4(), kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][][] eval (float[][][][] a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][][] eval (float[][][][] a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(a, kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// dest

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a) {
    return eval(dest, a.getfloat4());
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, float ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1, ky1, ky2);
  }

// 1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][][] eval (float[][][][] dest, NslFloat4 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a.getfloat4(), kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// 1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, float ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), ky1, ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1, ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getfloat());
  }

// 2 a kx1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, NslFloat0 ky1, float ky2) {
    return eval(dest, a, kx1.getfloat(), ky1.getfloat(), ky2);
  }

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, float ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), ky1, ky2.getfloat());
  }

// 2 a ky1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, float kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1, ky1.getfloat(), ky2.getfloat());
  }

// 3 a kx1 ky1

  public static float[][][][] eval (float[][][][] dest, float[][][][] a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return eval(dest, a, kx1.getfloat(), ky1.getfloat(), ky2.getfloat());
  }

// --------------------------



// ints

  private static int value(int x, int kx1, int ky1, int ky2) {
    if (x<kx1) 
	return ky1;
    return ky2;
  }

  public static int eval(int a) {
    return eval(a,0,0,1);
  }

  public static int[] eval(int[] a) {
    return eval(a,0,0,1);
  }

  public static int[][] eval(int[][] a) {
    return eval(a,0,0,1);
  }

  public static int[][][] eval(int[][][] a) {
    return eval(a,0,0,1);
  }

  public static int[][][][] eval(int[][][][] a) {
    return eval(a,0,0,1);
  }

  public static int[] eval(int[] dest, int[] a) {
    return eval(dest,a,0,0,1);
  }

  public static int[][] eval(int[][] dest, int[][] a) {
    return eval(dest,a,0,0,1);
  }

  public static int[][][] eval(int[][][] dest, int[][][] a) {
    return eval(dest,a,0,0,1);
  }

  public static int[][][][] eval(int[][][][] dest, int[][][][] a) {
    return eval(dest,a,0,0,1);
  }

  public static int eval(int a, int kx1, int ky1, int ky2) {
    return value(a, kx1, ky1, ky2);
  }

  public static int[] eval (int[] a, int kx1, int ky1, int ky2) {
 	int[] dest = new int[a.length];
	return eval(dest, a, kx1, ky1, ky2);
  }

  public static int[] eval (int[] dest, int[] a, int kx1, int ky1, int ky2) {
    int i;    
    if (dest.length!=a.length) {
	dest = new int[a.length];
    }
    for (i=0; i<a.length; i++)  dest[i]=value(a[i], kx1, ky1, ky2); 
    return dest;
  }

  public static int[][] eval (int[][] a, int kx1, int ky1, int ky2) {
    int[][] dest = new int[a.length][a[0].length];
    return eval(dest, a, kx1, ky1, ky2);
  }

  public static int[][] eval (int[][] dest, int[][] a, int kx1, int ky1, int ky2) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    if (dest.length!=a.length || dest[0].length!=a[0].length) {
	dest = new int[a.length][a[0].length];
    }
    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) dest[i][j]=value(a[i][j], kx1, ky1, ky2);
    return dest;
  }

  public static int[][][] eval (int[][][] a, int kx1, int ky1, int ky2) {
    int[][][] dest = new int[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, kx1, ky1, ky2);
  }

  public static int[][][] eval (int[][][] dest, int[][][] a, int kx1, int ky1, int ky2) {
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
	    for (k=0; k<size3; k++) dest[i][j][k]=value(a[i][j][k], kx1, ky1, ky2);
    return dest;
  }

  public static int[][][][] eval (int[][][][] a, int kx1, int ky1, int ky2) {
    int[][][][] dest = new int[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, kx1, ky1, ky2);
  }

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, int kx1, int ky1, int ky2) {
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
		for (l=0; l<size4; l++) dest[i][j][k][l]=value(a[i][j][k][l], kx1, ky1, ky2);
    return dest;
  }

// Nsl ints

  public static int eval (NslInt0 a) {
    return eval(a.getint());
  }

// --------------------------

  public static int eval (NslInt0 a, int kx1, int ky1, int ky2) {
    return eval(a.getint(), kx1, ky1, ky2);
  }

// 1

  public static int eval (NslInt0 a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a.getint(), kx1.getint(), ky1, ky2);
  }

  public static int eval (NslInt0 a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint(), kx1, ky1.getint(), ky2);
  }

  public static int eval (NslInt0 a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint(), kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int eval (NslInt0 a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint(), kx1.getint(), ky1.getint(), ky2);
  }

  public static int eval (NslInt0 a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint(), kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int eval (NslInt0 a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint(), kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int eval (NslInt0 a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint(), kx1.getint(), ky1.getint(), ky2.getint());
  }

// 1

  public static int eval (int a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a, kx1.getint(), ky1, ky2);
  }

  public static int eval (int a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1, ky1.getint(), ky2);
  }

  public static int eval (int a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int eval (int a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2);
  }

  public static int eval (int a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int eval (int a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int eval (int a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2.getint());
  }

// --------------------------


  public static int[] eval (NslInt1 a) {
    return eval(a.getint1());
  }

// --------------------------

  public static int[] eval (NslInt1 a, int kx1, int ky1, int ky2) {
    return eval(a.getint1(), kx1, ky1, ky2);
  }

// 1

  public static int[] eval (NslInt1 a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a.getint1(), kx1.getint(), ky1, ky2);
  }

  public static int[] eval (NslInt1 a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint1(), kx1, ky1.getint(), ky2);
  }

  public static int[] eval (NslInt1 a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint1(), kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[] eval (NslInt1 a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint1(), kx1.getint(), ky1.getint(), ky2);
  }

  public static int[] eval (NslInt1 a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint1(), kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[] eval (NslInt1 a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint1(), kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[] eval (NslInt1 a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint1(), kx1.getint(), ky1.getint(), ky2.getint());
  }

// 1

  public static int[] eval (int[] a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a, kx1.getint(), ky1, ky2);
  }

  public static int[] eval (int[] a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1, ky1.getint(), ky2);
  }

  public static int[] eval (int[] a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[] eval (int[] a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2);
  }

  public static int[] eval (int[] a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[] eval (int[] a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[] eval (int[] a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2.getint());
  }

// dest

  public static int[] eval (int[] dest, NslInt1 a) {
    return eval(dest, a.getint1());
  }

  public static int[] eval (int[] dest, NslInt1 a, int kx1, int ky1, int ky2) {
    return eval(dest, a.getint1(), kx1, ky1, ky2);
  }

// 1

  public static int[] eval (int[] dest, NslInt1 a, NslInt0 kx1, int ky1, int ky2) {
    return eval(dest, a.getint1(), kx1.getint(), ky1, ky2);
  }

  public static int[] eval (int[] dest, NslInt1 a, int kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a.getint1(), kx1, ky1.getint(), ky2);
  }

  public static int[] eval (int[] dest, NslInt1 a, int kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a.getint1(), kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[] eval (int[] dest, NslInt1 a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a.getint1(), kx1.getint(), ky1.getint(), ky2);
  }

  public static int[] eval (int[] dest, NslInt1 a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a.getint1(), kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[] eval (int[] dest, NslInt1 a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a.getint1(), kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[] eval (int[] dest, NslInt1 a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a.getint1(), kx1.getint(), ky1.getint(), ky2.getint());
  }

// 1

  public static int[] eval (int[] dest, int[] a, NslInt0 kx1, int ky1, int ky2) {
    return eval(dest, a, kx1.getint(), ky1, ky2);
  }

  public static int[] eval (int[] dest, int[] a, int kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a, kx1, ky1.getint(), ky2);
  }

  public static int[] eval (int[] dest, int[] a, int kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[] eval (int[] dest, int[] a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a, kx1.getint(), ky1.getint(), ky2);
  }

  public static int[] eval (int[] dest, int[] a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a, kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[] eval (int[] dest, int[] a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a, kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[] eval (int[] dest, int[] a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a, kx1.getint(), ky1.getint(), ky2.getint());
  }

// --------------------------


  public static int[][] eval (NslInt2 a) {
    return eval(a.getint2());
  }

// --------------------------

  public static int[][] eval (NslInt2 a, int kx1, int ky1, int ky2) {
    return eval(a.getint2(), kx1, ky1, ky2);
  }

// 1

  public static int[][] eval (NslInt2 a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a.getint2(), kx1.getint(), ky1, ky2);
  }

  public static int[][] eval (NslInt2 a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint2(), kx1, ky1.getint(), ky2);
  }

  public static int[][] eval (NslInt2 a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint2(), kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][] eval (NslInt2 a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint2(), kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][] eval (NslInt2 a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint2(), kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][] eval (NslInt2 a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint2(), kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][] eval (NslInt2 a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint2(), kx1.getint(), ky1.getint(), ky2.getint());
  }

// 1

  public static int[][] eval (int[][] a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a, kx1.getint(), ky1, ky2);
  }

  public static int[][] eval (int[][] a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1, ky1.getint(), ky2);
  }

  public static int[][] eval (int[][] a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][] eval (int[][] a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][] eval (int[][] a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][] eval (int[][] a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][] eval (int[][] a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2.getint());
  }

// dest

  public static int[][] eval (int[][] dest, NslInt2 a) {
    return eval(dest, a.getint2());
  }

  public static int[][] eval (int[][] dest, NslInt2 a, int kx1, int ky1, int ky2) {
    return eval(dest, a.getint2(), kx1, ky1, ky2);
  }

// 1

  public static int[][] eval (int[][] dest, NslInt2 a, NslInt0 kx1, int ky1, int ky2) {
    return eval(dest, a.getint2(), kx1.getint(), ky1, ky2);
  }

  public static int[][] eval (int[][] dest, NslInt2 a, int kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a.getint2(), kx1, ky1.getint(), ky2);
  }

  public static int[][] eval (int[][] dest, NslInt2 a, int kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a.getint2(), kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][] eval (int[][] dest, NslInt2 a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a.getint2(), kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][] eval (int[][] dest, NslInt2 a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a.getint2(), kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][] eval (int[][] dest, NslInt2 a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a.getint2(), kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][] eval (int[][] dest, NslInt2 a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a.getint2(), kx1.getint(), ky1.getint(), ky2.getint());
  }

// 1

  public static int[][] eval (int[][] dest, int[][] a, NslInt0 kx1, int ky1, int ky2) {
    return eval(dest, a, kx1.getint(), ky1, ky2);
  }

  public static int[][] eval (int[][] dest, int[][] a, int kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a, kx1, ky1.getint(), ky2);
  }

  public static int[][] eval (int[][] dest, int[][] a, int kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][] eval (int[][] dest, int[][] a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a, kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][] eval (int[][] dest, int[][] a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a, kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][] eval (int[][] dest, int[][] a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a, kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][] eval (int[][] dest, int[][] a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a, kx1.getint(), ky1.getint(), ky2.getint());
  }

// --------------------------

  public static int[][][] eval (NslInt3 a) {
    return eval(a.getint3());
  }

// --------------------------

  public static int[][][] eval (NslInt3 a, int kx1, int ky1, int ky2) {
    return eval(a.getint3(), kx1, ky1, ky2);
  }

// 1

  public static int[][][] eval (NslInt3 a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a.getint3(), kx1.getint(), ky1, ky2);
  }

  public static int[][][] eval (NslInt3 a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint3(), kx1, ky1.getint(), ky2);
  }

  public static int[][][] eval (NslInt3 a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint3(), kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][][] eval (NslInt3 a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint3(), kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][][] eval (NslInt3 a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint3(), kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][][] eval (NslInt3 a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint3(), kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][][] eval (NslInt3 a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint3(), kx1.getint(), ky1.getint(), ky2.getint());
  }

// 1

  public static int[][][] eval (int[][][] a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a, kx1.getint(), ky1, ky2);
  }

  public static int[][][] eval (int[][][] a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1, ky1.getint(), ky2);
  }

  public static int[][][] eval (int[][][] a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][][] eval (int[][][] a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][][] eval (int[][][] a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][][] eval (int[][][] a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][][] eval (int[][][] a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2.getint());
  }

// dest

  public static int[][][] eval (int[][][] dest, NslInt3 a) {
    return eval(dest, a.getint3());
  }

  public static int[][][] eval (int[][][] dest, NslInt3 a, int kx1, int ky1, int ky2) {
    return eval(dest, a.getint3(), kx1, ky1, ky2);
  }

// 1

  public static int[][][] eval (int[][][] dest, NslInt3 a, NslInt0 kx1, int ky1, int ky2) {
    return eval(dest, a.getint3(), kx1.getint(), ky1, ky2);
  }

  public static int[][][] eval (int[][][] dest, NslInt3 a, int kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a.getint3(), kx1, ky1.getint(), ky2);
  }

  public static int[][][] eval (int[][][] dest, NslInt3 a, int kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a.getint3(), kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][][] eval (int[][][] dest, NslInt3 a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a.getint3(), kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][][] eval (int[][][] dest, NslInt3 a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a.getint3(), kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][][] eval (int[][][] dest, NslInt3 a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a.getint3(), kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][][] eval (int[][][] dest, NslInt3 a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a.getint3(), kx1.getint(), ky1.getint(), ky2.getint());
  }

// 1

  public static int[][][] eval (int[][][] dest, int[][][] a, NslInt0 kx1, int ky1, int ky2) {
    return eval(dest, a, kx1.getint(), ky1, ky2);
  }

  public static int[][][] eval (int[][][] dest, int[][][] a, int kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a, kx1, ky1.getint(), ky2);
  }

  public static int[][][] eval (int[][][] dest, int[][][] a, int kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][][] eval (int[][][] dest, int[][][] a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a, kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][][] eval (int[][][] dest, int[][][] a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a, kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][][] eval (int[][][] dest, int[][][] a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a, kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][][] eval (int[][][] dest, int[][][] a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a, kx1.getint(), ky1.getint(), ky2.getint());
  }

// --------------------------



  public static int[][][][] eval (NslInt4 a) {
    return eval(a.getint4());
  }

// --------------------------

  public static int[][][][] eval (NslInt4 a, int kx1, int ky1, int ky2) {
    return eval(a.getint4(), kx1, ky1, ky2);
  }

// 1

  public static int[][][][] eval (NslInt4 a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a.getint4(), kx1.getint(), ky1, ky2);
  }

  public static int[][][][] eval (NslInt4 a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint4(), kx1, ky1.getint(), ky2);
  }

  public static int[][][][] eval (NslInt4 a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint4(), kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][][][] eval (NslInt4 a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a.getint4(), kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][][][] eval (NslInt4 a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a.getint4(), kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][][][] eval (NslInt4 a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint4(), kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][][][] eval (NslInt4 a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a.getint4(), kx1.getint(), ky1.getint(), ky2.getint());
  }

// 1

  public static int[][][][] eval (int[][][][] a, NslInt0 kx1, int ky1, int ky2) {
    return eval(a, kx1.getint(), ky1, ky2);
  }

  public static int[][][][] eval (int[][][][] a, int kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1, ky1.getint(), ky2);
  }

  public static int[][][][] eval (int[][][][] a, int kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][][][] eval (int[][][][] a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][][][] eval (int[][][][] a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][][][] eval (int[][][][] a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][][][] eval (int[][][][] a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(a, kx1.getint(), ky1.getint(), ky2.getint());
  }

// dest

  public static int[][][][] eval (int[][][][] dest, NslInt4 a) {
    return eval(dest, a.getint4());
  }

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, int kx1, int ky1, int ky2) {
    return eval(dest, a.getint4(), kx1, ky1, ky2);
  }

// 1

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, NslInt0 kx1, int ky1, int ky2) {
    return eval(dest, a.getint4(), kx1.getint(), ky1, ky2);
  }

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, int kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a.getint4(), kx1, ky1.getint(), ky2);
  }

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, int kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a.getint4(), kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a.getint4(), kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a.getint4(), kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a.getint4(), kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][][][] eval (int[][][][] dest, NslInt4 a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a.getint4(), kx1.getint(), ky1.getint(), ky2.getint());
  }

// 1

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, NslInt0 kx1, int ky1, int ky2) {
    return eval(dest, a, kx1.getint(), ky1, ky2);
  }

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, int kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a, kx1, ky1.getint(), ky2);
  }

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, int kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a, kx1, ky1, ky2.getint());
  }

// 2 a kx1

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, NslInt0 kx1, NslInt0 ky1, int ky2) {
    return eval(dest, a, kx1.getint(), ky1.getint(), ky2);
  }

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, NslInt0 kx1, int ky1, NslInt0 ky2) {
    return eval(dest, a, kx1.getint(), ky1, ky2.getint());
  }

// 2 a ky1

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, int kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a, kx1, ky1.getint(), ky2.getint());
  }

// 3 a kx1 ky1

  public static int[][][][] eval (int[][][][] dest, int[][][][] a, NslInt0 kx1, NslInt0 ky1, NslInt0 ky2) {
    return eval(dest, a, kx1.getint(), ky1.getint(), ky2.getint());
  }

// --------------------------

}


