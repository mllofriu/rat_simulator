/*  SCCS @(#)Nsl2Step.java	1.3 --- 09/01/99 - 00:18:18 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
* the old NSL2.1.7 used <= instead of < when checking bounds.
*/
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
    if a <= 0, c = 0,
    else      c = 1.

 2. eval(dest, a) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

 3. eval(a, kx1, ky1, ky2) -> c
    if a <= kx1, c = ky1,
    else,       c = ky2.

 4. eval(dest, a, kx1, ky1, ky2) -> c
 a is the parameter of the threshold function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/


////////////////////////////////////////////////////////////////////////////////
// step functions
package nslj.src.math;

import nslj.src.lang.NslFloat0;
import nslj.src.lang.NslFloat1;
import nslj.src.lang.NslFloat2;
import nslj.src.lang.NslDouble0;
import nslj.src.lang.NslDouble1;
import nslj.src.lang.NslDouble2;



public final class Nsl2Step {
 
  public static double eval (double a) {
    if (a>0)
      return 1;
    else
      return 0;
  }
  public static double[] eval (double[] a) {
    double[] tmp = new double[a.length];
    return eval (tmp, a);
  }
  public static double[] eval (double[] tmp, double[] a) {
    int i;
    for (i=0; i<a.length; i++) {
      if (a[i]>0)
	tmp[i]=1;
      else
	tmp[i]=0;
    }
    return tmp;
  }
  public static double[][] eval (double[][] a) {
    double[][] tmp = new double[a.length][a[0].length];
    return eval (tmp, a);
  }
  public static double[][] eval (double[][] tmp, double[][] a) {
    int i1, i2;
    int len1=a.length;
    int len2=a[0].length;
    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++){
          if (a[i1][i2]>0)
	    tmp[i1][i2]=1;
          else
	    tmp[i1][i2]=0;
        }
    return tmp;
  }

  public static double eval (double a, double kx1, double ky1, double ky2) {
    if (a>kx1)
      return ky2;
    else
      return ky1;
  }

  public static double[] eval (double[] a, double kx1, double ky1, double ky2) {
    double[] tmp = new double[a.length];
    return eval (tmp, a, kx1,ky1, ky2);
  }
  public static double[] eval (double[] tmp, double[] a, double kx1, double ky1, double ky2) {
    int i;
    for (i=0; i<a.length; i++) {
      if (a[i]>kx1)
	tmp[i]=ky2;
      else
	tmp[i]=ky1;
    }
    return tmp;
  }
  public static double[][] eval (double[][] a, double kx1, double ky1, double ky2) {
    double[][] tmp = new double[a.length][a[0].length];
    return eval (tmp, a, kx1,ky1, ky2);
  }
  public static double[][] eval (double[][] tmp, double[][] a, double kx1, double ky1, double ky2) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;

    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++) {
            if (a[i][j]>kx1)
	            tmp[i][j]=ky2;
            else
	            tmp[i][j]=ky1;
            }
    return tmp;
  }


/* floats */

  public static float eval (float a) {
    if (a>0)
      return 1;
    else
      return 0;
  }
  public static float[] eval (float[] a) {
    float[] tmp = new float[a.length];
    return eval (tmp, a);
  }
  public static float[] eval (float[] tmp, float[] a) {
    int i;
    for (i=0; i<a.length; i++) {
      if (a[i]>0)
	tmp[i]=1;
      else
	tmp[i]=0;
    }
    return tmp;
  }
  public static float[][] eval (float[][] a) {
    float[][] tmp = new float[a.length][a[0].length];
    return eval (tmp, a);
  }
  public static float[][] eval (float[][] tmp, float[][] a) {
    int i1, i2;
    int len1=a.length;
    int len2=a[0].length;
    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++){
          if (a[i1][i2]>0)
	    tmp[i1][i2]=1;
          else
	    tmp[i1][i2]=0;
        }
    return tmp;
  }

  public static float eval (float a, float kx1, float ky1, float ky2) {
    if (a>kx1)
      return ky2;
    else
      return ky1;
  }

  public static float[] eval (float[] a, float kx1, float ky1, float ky2) {
    float[] tmp = new float[a.length];
    return eval (tmp, a, kx1,ky1, ky2);
  }
  public static float[] eval (float[] tmp, float[] a, float kx1, float ky1, float ky2) {
    int i;
    for (i=0; i<a.length; i++) {
      if (a[i]>kx1)
	tmp[i]=ky2;
      else
	tmp[i]=ky1;
    }
    return tmp;
  }
  public static float[][] eval (float[][] a, float kx1, float ky1, float ky2) {
    float[][] tmp = new float[a.length][a[0].length];
    return eval (tmp, a, kx1,ky1, ky2);
  }
  public static float[][] eval (float[][] tmp, float[][] a, float kx1, float ky1, float ky2) {
    int i,j;
    int size1 = a.length;
    int size2 = a[0].length;

    for (i=0; i<size1; i++)
        for(j=0; j<size2; j++) {
            if (a[i][j]>kx1)
	            tmp[i][j]=ky2;
            else
	            tmp[i][j]=ky1;
            }
    return tmp;
  }

//----------------nsl types -----------------------

  public static double eval (NslDouble0 a) {
    if (a.get()>0)
      return (1.0);
    else
      return (0.0);
  }
  public static double eval (NslDouble0 tmp, NslDouble0 a) {
    if (a.get()>0)
      tmp.set(1.0);
    else
      tmp.set(0.0);
     return tmp.get();
  }

  public static double[] eval (NslDouble1 a) {
    return(eval (a.get()));
   }
  public static double[] eval (NslDouble1 tmp, NslDouble1 a) {
	return(eval(tmp.get(),a.get()));
  }
  public static double[][] eval (NslDouble2 a) {
    return(eval (a.get()));
  }
  public static double[][] eval (NslDouble2 tmp, NslDouble2 a) {
	return(eval(tmp.get(),a.get()));	
  }
  // 3 native args
  public static double eval (NslDouble0 a, double kx1, double ky1, double ky2) {
	return(eval(a.get(),kx1,ky1,ky2));
  }
  public static double eval (NslDouble0 tmp, NslDouble0 a, double kx1, double ky1, double ky2) {
	tmp.set(eval(a.get(),kx1,ky1,ky2));
	return(tmp.get());
  }

  public static double[] eval (NslDouble1 a, double kx1, double ky1, double ky2) {
	return(eval(a.get(),kx1,ky1,ky2));
  }
  public static double[] eval (NslDouble1 tmp, NslDouble1 a, double kx1, double ky1, double ky2) {
	return(eval(tmp.get(),a.get(),kx1,ky1,ky2));
  }
  public static double[][] eval (NslDouble2 a, double kx1, double ky1, double ky2) {
    return eval (a.get(), kx1,ky1, ky2);
  }
  public static double[][] eval (NslDouble2 tmp, NslDouble2 a, double kx1, double ky1, double ky2) {
	return eval(tmp.get(),a.get(),kx1,ky1,ky2);
  }

// floats


  public static float eval (NslFloat0 a) {
    if (a.get()>0)
      return (float)(1.0);
    else
      return (float)(0.0);
  }


  public static float eval (NslFloat0 tmp, NslFloat0 a) {
    if (a.get()>0)
      tmp.set((float)(1.0));
    else
      tmp.set((float)(0.0));
    return tmp.get();
  }

  public static float[] eval (NslFloat1 a) {
    return(eval (a.get()));
   }
  public static float[] eval (NslFloat1 tmp, NslFloat1 a) {
	return(eval(tmp.get(),a.get()));
  }
  public static float[][] eval (NslFloat2 a) {
    return(eval (a.get()));
  }
  public static float[][] eval (NslFloat2 tmp, NslFloat2 a) {
	return(eval(tmp.get(),a.get()));	
  }
  // 3 native args
  public static float eval (NslFloat0 a, float kx1, float ky1, float ky2) {
	return(eval(a.get(),kx1,ky1,ky2));
  }
  public static float eval (NslFloat0 tmp, NslFloat0 a, float kx1, float ky1, float ky2) {
	tmp.set(eval(a.get(),kx1,ky1,ky2));
	return(tmp.get());
  }

  public static float[] eval (NslFloat1 a, float kx1, float ky1, float ky2) {
	return(eval(a.get(),kx1,ky1,ky2));
  }
  public static float[] eval (NslFloat1 tmp, NslFloat1 a, float kx1, float ky1, float ky2) {
	return(eval(tmp.get(),a.get(),kx1,ky1,ky2));
  }
  public static float[][] eval (NslFloat2 a, float kx1, float ky1, float ky2) {
    return (eval (a.get(), kx1,ky1, ky2));
  }
  public static float[][] eval (NslFloat2 tmp, NslFloat2 a, float kx1, float ky1, float ky2) {
	return (eval(tmp.get(),a.get(),kx1,ky1,ky2));
  }


//-------* nsl types and nsl types as args ---------

  // 3 nsl types as  args
  public static double eval (NslDouble0 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
	return(eval(a.get(),kx1.get(),ky1.get(),ky2.get()));
  }
  public static double eval (NslDouble0 tmp, NslDouble0 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
	tmp.set(eval(a.get(),kx1.get(),ky1.get(),ky2.get()));
	return (tmp.get());
  }

  public static double[] eval (NslDouble1 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
	return(eval(a.get(),kx1.get(),ky1.get(),ky2.get()));
  }
  public static double[] eval (NslDouble1 tmp, NslDouble1 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
	return(eval(tmp.get(),a.get(),kx1.get(),ky1.get(),ky2.get()));
  }
  public static double[][] eval (NslDouble2 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
    return (eval (a.get(), kx1.get(),ky1.get(), ky2.get()));
  }
  public static double[][] eval (NslDouble2 tmp, NslDouble2 a, NslDouble0 kx1, NslDouble0 ky1, NslDouble0 ky2) {
	return (eval(tmp.get(),a.get(),kx1.get(),ky1.get(),ky2.get()));
  }

// floats
  // 3 native args
  public static float eval (NslFloat0 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
	return(eval(a.get(),kx1.get(),ky1.get(),ky2.get()));
  }
  public static float eval (NslFloat0 tmp, NslFloat0 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
	tmp.set(eval(a.get(),kx1.get(),ky1.get(),ky2.get()));
	return(tmp.get());
  }

  public static float[] eval (NslFloat1 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
	return(eval(a.get(),kx1.get(),ky1.get(),ky2.get()));
  }
  public static float[] eval (NslFloat1 tmp, NslFloat1 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
	return(eval(tmp.get(),a.get(),kx1.get(),ky1.get(),ky2.get()));
  }
  public static float[][] eval (NslFloat2 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
    return (eval (a.get(), kx1.get(),ky1.get(), ky2.get()));
  }
  public static float[][] eval (NslFloat2 tmp, NslFloat2 a, NslFloat0 kx1, NslFloat0 ky1, NslFloat0 ky2) {
	return (eval(tmp.get(),a.get(),kx1.get(),ky1.get(),ky2.get()));
  }
}





