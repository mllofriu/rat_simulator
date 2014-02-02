/*  SCCS - @(#)NslSigmoid2.java	1.4 - 09/01/99 - 00:18:10 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Sigmoid2 threshold routines
//
//


/**
 Sigmoid threshold routines.
 There are four basic format for the evaluation method in
 this routine:
 1. eval(a) -> c
 a is the input parameter to pass the threshold function:
    if a < 0,           c = 0,
    else if 0 <= a < 1, c = a*a*(3-2a),
    else                c = 1.

 2. eval(dest, a) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

 3. eval(a, kx1, kx2, ky1, ky2) -> c
    if a < kx1,                 c = ky1,
    else if kx1 <= a < kx2,     c = s*s(3-2s), s = [(a-kx1)/(kx2-kx1)]
    else,                       c = ky2.

 2. eval(dest, a, kx1, kx2, ky1, ky2) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/


////////////////////////////////////////////////////////////////////////////////
// sigmoid functions
package nslj.src.math;
import nslj.src.lang.*;

public final class NslSigmoid2 {
  private static double value(double x)
  {
   return (1.0/(1.0+Math.exp(-x)));
  }
  public static double eval (double a) {
      return value(a);
  }
  public static double[] eval (double[] a) {
    int i;
    double[] tmp = new double[a.length];

    for (i=0; i<a.length; i++)  tmp[i]=value(a[i]); 
    return tmp;
  }
  public static double[][] eval (double[][] a) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    double[][] tmp = new double[size1][size2];

    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) tmp[i][j]=value(a[i][j]);
    return tmp;
  }

/*
  public static double eval (double a, double kx1, double kx2, double ky1, double ky2 ) {
    double s;

    if (a>=kx2)
      return ky2;
    else if (a>=kx1) {
      s = (a-kx1)/(kx2-kx1);
      return (ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
    }
    else
      return ky1;

  }
  public static double[] eval (double[] a, double kx1, double kx2, double ky1, double ky2 ) {
    return eval( new double[a.length], a, kx1, kx2, ky1, ky2);
  }
  public static double[] eval (double[] tmp, double[] a, double kx1, double kx2, double ky1, double ky2 ) {
    int i;
    int size1 = a.length;
    double s;
    for (i=0; i<size1; i++) {
            if (a[i]>=kx2)
	            tmp[i]=ky2;
            else if (a[i]>=kx1) {
	            s = (a[i]-kx1)/(kx2-kx1);
	            tmp[i]=(ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
            }
            else
	            tmp[i]=ky1;
            }
    return tmp;
  }


  public static double[][] eval (double[][] a, double kx1, double kx2, double ky1, double ky2 ) {
    return eval( new double[a.length][a[0].length], a, kx1, kx2, ky1, ky2);
  }
  public static double[][] eval (double[][] tmp, double[][] a, double kx1, double kx2, double ky1, double ky2 ) {
    int i, j;
    int size1 = a.length;
    int size2 = a[0].length;
    double s;
    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++){
            if (a[i][j]>=kx2)
	            tmp[i][j]=ky2;
            else if (a[i][j]>=kx1) {
	            s = (a[i][j]-kx1)/(kx2-kx1);
	            tmp[i][j]=(ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
            }
            else
	            tmp[i][j]=ky1;
            }
    return tmp;
  }
*/

/* Added by Weifang */

  public static double eval (NslDouble0 a) {
    double tmpa=a.getdouble();
    return value(tmpa);
  }

  public static double[] eval (NslDouble1 a) {
    int i;
    double[] tmpa = a.getdouble1();
    double[] tmp = new double[a.getSize()];

    for (i=0; i<a.getSize(); i++)  tmp[i]=value(tmpa[i]);
    return tmp;
  }

  public static double[][] eval (NslDouble2 a) {
    int i,j;
    int size1 = a.getSize1();
    int size2 = a.getSize2();
    double[][] tmp = new double[size1][size2];
    double[][] tmpa = a.getdouble2();

    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) tmp[i][j]=value(tmpa[i][j]);
    return tmp;
  }

/*
  public static double eval (NslDouble0 a, double kx1, double kx2, double ky1, double ky2 ) {
    double s;

    if (a.getdouble()>=kx2)
      return ky2;
    else if (a.getdouble()>=kx1) {
      s = (a.getdouble()-kx1)/(kx2-kx1);
      return (ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
    }
    else
      return ky1;
  }

  public static double eval (NslDouble0 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2 ) {
    double s;

    if (a.getdouble()>=kx2.getdouble())
      return ky2.getdouble();
    else if (a.getdouble()>=kx1.getdouble()) {
      s = (a.getdouble()-kx1.getdouble())/(kx2.getdouble()-kx1.getdouble());
      return (ky2.getdouble()-ky1.getdouble())*s*s*(3.0-2.0*s)+ky1.getdouble();
    }
    else
      return ky1.getdouble();
  }

  public static double[] eval (NslDouble1 a, double kx1, double kx2, double ky1, double ky2 ) {
    return eval( new double[a.getSize()], a, kx1, kx2, ky1, ky2);
  }

  public static double[] eval (double[] tmp, NslDouble1 a, double kx1, double kx2, double ky1, double ky2 ) {
    int i;
    int size = a.getSize();
    double[] tmpa=a.getdouble1();
    double s;

    for (i=0; i<size; i++) {
            if (tmpa[i]>=kx2)
	            tmp[i]=ky2;
            else if (tmpa[i]>=kx1) {
	            s = (tmpa[i]-kx1)/(kx2-kx1);
	            tmp[i]=(ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
            }
            else
	            tmp[i]=ky1;
            }
    return tmp;
  }

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2 ) {
    return eval( new double[a.getSize()], a, kx1, kx2, ky1, ky2);
  }

  public static double[] eval (double[] tmp, NslDouble1 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2 ) {
    int i;
    int size = a.getSize();
    double[] tmpa=a.getdouble1();
    double tmpkx1=kx1.getdouble();
    double tmpkx2=kx2.getdouble();
    double tmpky1=ky1.getdouble();
    double tmpky2=ky2.getdouble();
    double s;

    for (i=0; i<size; i++) {
            if (tmpa[i]>=tmpkx2)
	            tmp[i]=tmpky2;
            else if (tmpa[i]>=tmpkx1) {
	            s = (tmpa[i]-tmpkx1)/(tmpkx2-tmpkx1);
	            tmp[i]=(tmpky2-tmpky1)*s*s*(3.0-2.0*s)+tmpky1;
            }
            else
	            tmp[i]=tmpky1;
            }
    return tmp;
  }


  public static double[][] eval (NslDouble2 a, double kx1, double kx2, double ky1, double ky2 ) {
    return eval( new double[a.getSize1()][a.getSize2()], a, kx1, kx2, ky1, ky2);
  }

  public static double[][] eval (double[][] tmp, NslDouble2 a, double kx1, double kx2, double ky1, double ky2 ) {
    int i, j;
    int size1 = a.getSize1();
    int size2 = a.getSize2();
    double[][] tmpa=a.getdouble2();
    double s;

    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++){
            if (tmpa[i][j]>=kx2)
	            tmp[i][j]=ky2;
            else if (tmpa[i][j]>=kx1) {
	            s = (tmpa[i][j]-kx1)/(kx2-kx1);
	            tmp[i][j]=(ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
            }
            else
	            tmp[i][j]=ky1;
            }
    return tmp;
  }

  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2 ) {
    return eval( new double[a.getSize1()][a.getSize2()], a, kx1, kx2, ky1, ky2);
  }

  public static double[][] eval (double[][] tmp, NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2 ) {
    int i, j;
    int size1 = a.getSize1();
    int size2 = a.getSize2();
    double[][] tmpa=a.getdouble2();
    double tmpkx1=kx1.getdouble();
    double tmpkx2=kx2.getdouble();
    double tmpky1=ky1.getdouble();
    double tmpky2=ky2.getdouble();
    double s;

    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++){
            if (tmpa[i][j]>=tmpkx2)
	            tmp[i][j]=tmpky2;
            else if (tmpa[i][j]>=tmpkx1) {
	            s = (tmpa[i][j]-tmpkx1)/(tmpkx2-tmpkx1);
	            tmp[i][j]=(tmpky2-tmpky1)*s*s*(3.0-2.0*s)+tmpky1;
            }
            else
	            tmp[i][j]=tmpky1;
            }
    return tmp;
  }
*/

/* floats */
  private static float value(float x)
  {
	float tmp;
	tmp=(float)(1/(1+Math.exp(-x)));
	return tmp;
  }
  public static float eval (float a) {
      return value(a);
  }
  public static float[] eval (float[] a) {
    int i;
    float[] tmp = new float[a.length];

    for (i=0; i<a.length; i++)  tmp[i]=value(a[i]); 
    return tmp;
  }
  public static float[][] eval (float[][] a) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;
    float[][] tmp = new float[size1][size2];

    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) tmp[i][j]=value(a[i][j]);
    return tmp;
  }

/*
  public static float eval (float a, float kx1, float kx2, float ky1, float ky2 ) {
    float s;

    if (a>=kx2)
      return ky2;
    else if (a>=kx1) {
      s = (a-kx1)/(kx2-kx1);
      return (ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
    }
    else
      return ky1;

  }
  public static float[] eval (float[] a, float kx1, float kx2, float ky1, float ky2 ) {
    return eval( new float[a.length], a, kx1, kx2, ky1, ky2);
  }
  public static float[] eval (float[] tmp, float[] a, float kx1, float kx2, float ky1, float ky2 ) {
    int i;
    int size1 = a.length;
    float s;
    for (i=0; i<size1; i++) {
            if (a[i]>=kx2)
	            tmp[i]=ky2;
            else if (a[i]>=kx1) {
	            s = (a[i]-kx1)/(kx2-kx1);
	            tmp[i]=(ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
            }
            else
	            tmp[i]=ky1;
            }
    return tmp;
  }


  public static float[][] eval (float[][] a, float kx1, float kx2, float ky1, float ky2 ) {
    return eval( new float[a.length][a[0].length], a, kx1, kx2, ky1, ky2);
  }
  public static float[][] eval (float[][] tmp, float[][] a, float kx1, float kx2, float ky1, float ky2 ) {
    int i, j;
    int size1 = a.length;
    int size2 = a[0].length;
    float s;
    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++){
            if (a[i][j]>=kx2)
	            tmp[i][j]=ky2;
            else if (a[i][j]>=kx1) {
	            s = (a[i][j]-kx1)/(kx2-kx1);
	            tmp[i][j]=(ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
            }
            else
	            tmp[i][j]=ky1;
            }
    return tmp;
  }
*/

/* Added by Weifang */

  public static float eval (NslFloat0 a) {
    float tmpa=a.getfloat();
    return value(tmpa);
  }

  public static float[] eval (NslFloat1 a) {
    int i;
    float[] tmpa = a.getfloat1();
    float[] tmp = new float[a.getSize()];

    for (i=0; i<a.getSize(); i++)  tmp[i]=value(tmpa[i]);
    return tmp;
  }

  public static float[][] eval (NslFloat2 a) {
    int i,j;
    int size1 = a.getSize1();
    int size2 = a.getSize2();
    float[][] tmp = new float[size1][size2];
    float[][] tmpa = a.getfloat2();

    for (i=0; i<size1; i++)
        for (j=0; j<size2; j++) tmp[i][j]=value(tmpa[i][j]);
    return tmp;
  }

/*
  public static float eval (NslFloat0 a, float kx1, float kx2, float ky1, float ky2 ) {
    float s;

    if (a.getfloat()>=kx2)
      return ky2;
    else if (a.getfloat()>=kx1) {
      s = (a.getfloat()-kx1)/(kx2-kx1);
      return (ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
    }
    else
      return ky1;
  }

  public static float eval (NslFloat0 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2 ) {
    float s;

    if (a.getfloat()>=kx2.getfloat())
      return ky2.getfloat();
    else if (a.getfloat()>=kx1.getfloat()) {
      s = (a.getfloat()-kx1.getfloat())/(kx2.getfloat()-kx1.getfloat());
      return (ky2.getfloat()-ky1.getfloat())*s*s*(3.0-2.0*s)+ky1.getfloat();
    }
    else
      return ky1.getfloat();
  }

  public static float[] eval (NslFloat1 a, float kx1, float kx2, float ky1, float ky2 ) {
    return eval( new float[a.getSize()], a, kx1, kx2, ky1, ky2);
  }

  public static float[] eval (float[] tmp, NslFloat1 a, float kx1, float kx2, float ky1, float ky2 ) {
    int i;
    int size = a.getSize();
    float[] tmpa=a.getfloat1();
    float s;

    for (i=0; i<size; i++) {
            if (tmpa[i]>=kx2)
	            tmp[i]=ky2;
            else if (tmpa[i]>=kx1) {
	            s = (tmpa[i]-kx1)/(kx2-kx1);
	            tmp[i]=(ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
            }
            else
	            tmp[i]=ky1;
            }
    return tmp;
  }

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2 ) {
    return eval( new float[a.getSize()], a, kx1, kx2, ky1, ky2);
  }

  public static float[] eval (float[] tmp, NslFloat1 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2 ) {
    int i;
    int size = a.getSize();
    float[] tmpa=a.getfloat1();
    float tmpkx1=kx1.getfloat();
    float tmpkx2=kx2.getfloat();
    float tmpky1=ky1.getfloat();
    float tmpky2=ky2.getfloat();
    float s;

    for (i=0; i<size; i++) {
            if (tmpa[i]>=tmpkx2)
	            tmp[i]=tmpky2;
            else if (tmpa[i]>=tmpkx1) {
	            s = (tmpa[i]-tmpkx1)/(tmpkx2-tmpkx1);
	            tmp[i]=(tmpky2-tmpky1)*s*s*(3.0-2.0*s)+tmpky1;
            }
            else
	            tmp[i]=tmpky1;
            }
    return tmp;
  }


  public static float[][] eval (NslFloat2 a, float kx1, float kx2, float ky1, float ky2 ) {
    return eval( new float[a.getSize1()][a.getSize2()], a, kx1, kx2, ky1, ky2);
  }

  public static float[][] eval (float[][] tmp, NslFloat2 a, float kx1, float kx2, float ky1, float ky2 ) {
    int i, j;
    int size1 = a.getSize1();
    int size2 = a.getSize2();
    float[][] tmpa=a.getfloat2();
    float s;

    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++){
            if (tmpa[i][j]>=kx2)
	            tmp[i][j]=ky2;
            else if (tmpa[i][j]>=kx1) {
	            s = (tmpa[i][j]-kx1)/(kx2-kx1);
	            tmp[i][j]=(ky2-ky1)*s*s*(3.0-2.0*s)+ky1;
            }
            else
	            tmp[i][j]=ky1;
            }
    return tmp;
  }

  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2 ) {
    return eval( new float[a.getSize1()][a.getSize2()], a, kx1, kx2, ky1, ky2);
  }

  public static float[][] eval (float[][] tmp, NslFloat2 a, NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2 ) {
    int i, j;
    int size1 = a.getSize1();
    int size2 = a.getSize2();
    float[][] tmpa=a.getfloat2();
    float tmpkx1=kx1.getfloat();
    float tmpkx2=kx2.getfloat();
    float tmpky1=ky1.getfloat();
    float tmpky2=ky2.getfloat();
    float s;

    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++){
            if (tmpa[i][j]>=tmpkx2)
	            tmp[i][j]=tmpky2;
            else if (tmpa[i][j]>=tmpkx1) {
	            s = (tmpa[i][j]-tmpkx1)/(tmpkx2-tmpkx1);
	            tmp[i][j]=(tmpky2-tmpky1)*s*s*(3.0-2.0*s)+tmpky1;
            }
            else
	            tmp[i][j]=tmpky1;
            }
    return tmp;
  }
*/
}
