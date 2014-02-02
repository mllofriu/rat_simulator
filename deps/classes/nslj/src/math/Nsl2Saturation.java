/*  SCCS @(#)Nsl2Saturation.java	1.3 --- 09/01/99 -- 00:18:18 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// 2Saturation threshold routines
// NSL2.1.7 used <= instead of < a lot of places
//
//


/**
 2Saturation threshold routines.
 There are four basic format for the evaluation method in
 this routine:
 1, eval(a) -> c
 a is the input parameter to pass the threshold function:
    if a <= 0,           c = 0,
    else if 0 < a <= 1, c = a;
    else                c = 1.

 2. eval(dest, a) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

 3. eval(a, kx1, kx2, ky1, ky2) -> c
    if a <= kx1,                 c = ky1,
    else if kx1 < a <= kx2,     c=(ky2-ky1)(a-kx1)/(kx2-kx1)+ky1,
    else,                       c = ky2.

 2. eval(dest, a, kx1, kx2, ky1, ky2) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/


// 2Saturation functions
package nslj.src.math;
import nslj.src.lang.NslDouble0;
import nslj.src.lang.NslDouble1;
import nslj.src.lang.NslDouble2;
import nslj.src.lang.NslFloat0;
import nslj.src.lang.NslFloat1;
import nslj.src.lang.NslFloat2;


public final class Nsl2Saturation {
   public static double eval (double a) {
    if (a>1)
      return 1;
    else if (a>0)
      return a;
    else
      return 0;
  }

   public static double eval (double tmp, double a) {
    if (a>1.0)
      tmp=1.0;
    else if (a>0)
      tmp=a;
    else
      tmp=0;
    return tmp;
  }

  public static double[] eval (double[] a) {
    return eval(new double[a.length], a);
  }
  public static double[] eval (double[] tmp, double[] a) {
    int i;
    for (i=0; i<a.length; i++) {
      if (a[i]>1)
	tmp[i]=1;
      else if (a[i]>0)
	tmp[i]=a[i];
      else
	tmp[i]=0;
    }
    return tmp;
  }
  public static double[][] eval (double[][] a) {
    return eval(new double[a.length][a[0].length], a);
  }
  public static double[][] eval (double[][] tmp, double[][] a) {
    int i, j;
    int size1 = a.length;
    int size2 = a[0].length;
    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++){
            if (a[i][j]>1)
	            tmp[i][j]=1;
            else if (a[i][j]>0)
	            tmp[i][j]=a[i][j];
            else
	            tmp[i][j]=0;
        }
    return tmp;
  }
// floats no arguements ------------
   public static float eval (float a) {
    if (a>1)
      return 1;
    else if (a>0)
      return a;
    else
      return 0;
  }

   public static float eval (float tmp, float a) {
    if (a>1.0)
      tmp=(float)(1.0);
    else if (a>0)
      tmp=a;
    else
      tmp=0;
    return tmp;
  }

  public static float[] eval (float[] a) {
    return eval(new float[a.length], a);
  }
  public static float[] eval (float[] tmp, float[] a) {
    int i;
    for (i=0; i<a.length; i++) {
      if (a[i]>1)
	tmp[i]=1;
      else if (a[i]>0)
	tmp[i]=a[i];
      else
	tmp[i]=0;
    }
    return tmp;
  }
  public static float[][] eval (float[][] a) {
    return eval(new float[a.length][a[0].length], a);
  }
  public static float[][] eval (float[][] tmp, float[][] a) {
    int i, j;
    int size1 = a.length;
    int size2 = a[0].length;
    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++){
            if (a[i][j]>1)
	            tmp[i][j]=1;
            else if (a[i][j]>0)
	            tmp[i][j]=a[i][j];
            else
	            tmp[i][j]=0;
        }
    return tmp;
  }

// --- doubles with arguments -----------------------
  public static double eval (double a, double kx1, double kx2, double ky1, double ky2) {
    if (a>kx2)
      return ky2;
    else if (a>kx1)
      return (ky2-ky1)*(a-kx1)/(kx2-kx1)+ky1;
    else
      return ky1;
  }
  public static double eval (double tmp, double a, double kx1, double kx2, double ky1, double ky2) {
    if (a>kx2)
      tmp=ky2;
    else if (a>kx1)
      tmp=(ky2-ky1)*(a-kx1)/(kx2-kx1)+ky1;
    else
     tmp=ky1;
    return tmp;
  }

  public static double[] eval (double[] a, double kx1, double kx2, double ky1, double ky2 ) {
    return eval(new double[a.length], a, kx1, kx2, ky1, ky2);
  }
  public static double[] eval (double[] tmp, double[] a, double kx1, double kx2, double ky1, double ky2 ) {
    int i;

    for (i=0; i<a.length; i++) {
      if (a[i]>kx2)
	tmp[i]=ky2;
      else if (a[i]>kx1)
	tmp[i]=(ky2-ky1)*(a[i]-kx1)/(kx2-kx1)+ky1;
      else
	tmp[i]=ky1;
    }
    return tmp;
  }
 public static double[][] eval (double[][] a, double kx1, double kx2, double ky1, double ky2 ) {
    return eval(new double[a.length][a[0].length], a, kx1, kx2, ky1, ky2);
  }
  public static double[][] eval (double[][] tmp, double[][] a, double kx1, double kx2, double ky1, double ky2 ) {
    int i, j;
    int size1 = a.length;
    int size2 = a[0].length;

    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++) {
            if (a[i][j]>kx2)
	            tmp[i][j]=ky2;
            else if (a[i][j]>kx1)
            	tmp[i][j]=(ky2-ky1)*(a[i][j]-kx1)/(kx2-kx1)+ky1;
            else
            	tmp[i][j]=ky1;
        }
    return tmp;
  }
// --- float with arguments -----------------------
  public static float eval (float a, float kx1, float kx2, float ky1, float ky2) {
    if (a>kx2)
      return ky2;
    else if (a>kx1)
      return (ky2-ky1)*(a-kx1)/(kx2-kx1)+ky1;
    else
      return ky1;
  }
  public static float eval (float tmp, float a, float kx1, float kx2, float ky1, float ky2) {
    if (a>kx2)
      tmp=ky2;
    else if (a>kx1)
      tmp=(ky2-ky1)*(a-kx1)/(kx2-kx1)+ky1;
    else
     tmp=ky1;
    return tmp;
  }

  public static float[] eval (float[] a, float kx1, float kx2, float ky1, float ky2 ) {
    return eval(new float[a.length], a, kx1, kx2, ky1, ky2);
  }
  public static float[] eval (float[] tmp, float[] a, float kx1, float kx2, float ky1, float ky2 ) {
    int i;

    for (i=0; i<a.length; i++) {
      if (a[i]>kx2)
	tmp[i]=ky2;
      else if (a[i]>kx1)
	tmp[i]=(ky2-ky1)*(a[i]-kx1)/(kx2-kx1)+ky1;
      else
	tmp[i]=ky1;
    }
    return tmp;
  }
 public static float[][] eval (float[][] a, float kx1, float kx2, float ky1, float ky2 ) {
    return eval(new float[a.length][a[0].length], a, kx1, kx2, ky1, ky2);
  }
  public static float[][] eval (float[][] tmp, float[][] a, float kx1, float kx2, float ky1, float ky2 ) {
    int i, j;
    int size1 = a.length;
    int size2 = a[0].length;

    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++) {
            if (a[i][j]>kx2)
	            tmp[i][j]=ky2;
            else if (a[i][j]>kx1)
            	tmp[i][j]=(ky2-ky1)*(a[i][j]-kx1)/(kx2-kx1)+ky1;
            else
            	tmp[i][j]=ky1;
        }
    return tmp;
  }


// double nsl types and no args -----------------------------------

   public static double eval (NslDouble0 a) {
	double tmp=a.get();
    if (tmp>1.0)
      return 1.0;
    else if (tmp>0)
      return a.get();
    else
      return 0;
  }

   public static double eval (NslDouble0 tmp, NslDouble0 a) {
    if (a.get()>1.0)
      tmp.set(1.0);
    else if (a.get()>0)
      tmp.set(a.get());
    else
      tmp.set(0);
    return tmp.get();
  }
	

  public static double[] eval (NslDouble1 a) {
    return eval(new double[a.getSize()], a.get());
  }
  public static double[] eval (NslDouble1 tmp, NslDouble1 a) {
    return (eval(tmp.get(), a.get()));
  }
  public static double[][] eval (NslDouble2 a) {
    return eval(new double[a.getSize1()][a.getSize2()], a.get());
  }
  public static double[][] eval (NslDouble2 tmp, NslDouble2 a) {
    return eval(tmp.get(), a.get());
  }

// float nsl types with no args-------------------------------------

   public static float eval (NslFloat0 a) {
	float tmp=a.get();
    if (tmp>1.0)
      return (float)(1.0);
    else if (tmp>0)
      return tmp;
    else
      return (float)(0);
  }

   public static float eval (NslFloat0 tmp, NslFloat0 a) {
    float c=a.get();
    if (c>1.0)
      tmp.set(1.0);
    else if (c>0)
      tmp.set(c);
    else
      tmp.set(0);
    return tmp.get();
  }
	
  public static float[] eval (NslFloat1 a) {
    return eval(new float[a.getSize()], a.get());
  }
  public static float[] eval (NslFloat1 tmp, NslFloat1 a) {
    return (eval(tmp.get(), a.get()));
  }
  public static float[][] eval (NslFloat2 a) {
    return eval(new float[a.getSize1()][a.getSize2()], a.get());
  }
  public static float[][] eval (NslFloat2 tmp, NslFloat2 a) {
    return eval(tmp.get(), a.get());
  }


//-----------------------nsl types with native args------
    public static double[][] eval(NslDouble2 a,  double kx1, double kx2, double ky1, double ky2 ) {
    return eval(new double[a.getSize1()][a.getSize2()], a.get(), kx1, kx2, ky1, ky2);
  }
  
   public static double[][] eval(NslDouble2 c,  NslDouble2 a, double kx1, double kx2, double ky1, double ky2 ) {
    return eval(c.get(), a.get(), kx1, kx2, ky1, ky2);
  }
  public static float[][] eval(NslFloat2 a,  float kx1, float kx2, float ky1, float ky2 ) {
    return eval(new float[a.getSize1()][a.getSize2()], a.get(), kx1, kx2, ky1, ky2);
  }

  public static float[][] eval(NslFloat2 c,NslFloat2 a,  float kx1, float kx2, float ky1, float ky2 ) {
    return eval(c.get(), a.get(), kx1, kx2, ky1, ky2);
  }
 
//-----------------nsl types with nsl types as args-----------------------
  public static double[][] eval(NslDouble2 a,  NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2 ) {
    return eval(new double[a.getSize1()][a.getSize2()], a.get(), kx1.get(), kx2.get(), ky1.get(), ky2.get());
  }
  
   public static double[][] eval(NslDouble2 c,  NslDouble2 a, NslDouble0 kx1, NslDouble0 kx2, NslDouble0 ky1, NslDouble0 ky2 ) {
    return eval(c.get(), a.get(), kx1.get(), kx2.get(), ky1.get(), ky2.get());
  }
  public static float[][] eval(NslFloat2 a,  NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2 ) {
    return eval(new float[a.getSize1()][a.getSize2()], a.get(), kx1.get(), kx2.get(), ky1.get(), ky2.get());
  }

  public static float[][] eval(NslFloat2 c,NslFloat2 a,  NslFloat0 kx1, NslFloat0 kx2, NslFloat0 ky1, NslFloat0 ky2 ) {
    return eval(c.get(), a.get(), kx1.get(), kx2.get(), ky1.get(), ky2.get());
  }
 

}
  
