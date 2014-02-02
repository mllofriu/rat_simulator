/*  SCCS - @(#)NslMul.java	1.4 - 09/01/99 - 00:18:07 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslMul.java,v $
 * Revision 1.2  1997/11/18 01:28:56  erhan
 * NslMin/Max now works on doubles also
 *
 * Revision 1.1  1997/07/30 21:19:32  erhan
 * nsl3.0
 *
 * Revision 1.1.1.1  1997/03/12 22:52:20  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:40  nsl
 *  Imported the Source directory
 *
*/
////////////////////////////////////////////////////////////
//
// Mulitiplication routines
//
//

/**
 Multiplication routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a, b) -> c
 a, b are the parameter of the evaluation function to do
 a multiplies b pointwise and the result is passed out as c
 2. eval(dest, a, b) -> c
 a, b are the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

*/

package nslj.src.math;
import nslj.src.lang.*;

public final class NslMul {
  public static int eval(int a, int b) {
    return a*b;
  }

  public static int[] eval(int[] a, int b) {
    int[] dest = new int[a.length];
    return eval(dest, a, b);
  }

  public static int[] eval(int[] dest, int[] a, int b) {
    int i;
    int len = dest.length;
    for (i=0; i<len; i++)
        dest[i] = a[i]*b;
    return dest;
  }

  public static int[] eval(int a, int[] b) {
    return eval(b, a);
  }

  public static int[] eval(int[] dest, int a, int[] b) {
    return eval(dest, b, a);
  }

  public static int[] eval(int[] a, int[] b) {
    int[] dest = new int[a.length];
    if (a.length != b.length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    return eval(dest, a, b);
  }


  public static int[] eval(int[] dest, int[] a, int[] b) {
    int i;
    int len = dest.length;
    if (len != a.length || len != b.length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    for (i=0; i<len; i++) {
        dest[i] = a[i]*b[i];
    }
    return dest;
  }

/* Nsl types Added by Weifang Xie*/

  public static int eval(NslInt0 a, int b) {
    return a.getint()*b;
  }

  public static int eval(int a, NslInt0 b) {
    return a*b.getint();
  }

  public static int eval(NslInt0 a, NslInt0 b) {
    return a.getint()*b.getint();
  }

  public static int[] eval(NslInt1 a, int b) {
    int[] dest = new int[a.getSize()];
    return eval(dest, a, b);
  }

  public static int[] eval(int[] dest, NslInt1 a, int b) {
    int i;
    int len = dest.length;
    int[] tmpa=a.getint1();
    for (i=0; i<len; i++)
        dest[i]=tmpa[i]*b;
    return dest;
  }

  public static int[] eval(NslInt1 a, NslInt0 b) {
    int[] dest = new int[a.getSize()];
    return eval(dest, a, b);
  }

  public static int[] eval(int[] dest, NslInt1 a, NslInt0 b) {
    int i;
    int len=dest.length;
    int[] tmpa=a.getint1();
    int tmpb=b.getint();
    for (i=0; i<len; i++)
        dest[i]=tmpa[i]*tmpb;
    return dest;
  }

  public static int[] eval(int a, NslInt1 b) {
    return eval(b, a);
  }

  public static int[] eval(int[] dest, int a, NslInt1 b) {
    return eval(dest, b, a);
  }

  public static int[] eval(NslInt0 a, NslInt1 b) {
    return eval(b, a);
  }

  public static int[] eval(int[] dest, NslInt0 a, NslInt1 b) {
    return eval(dest, b, a);
  }

  public static int[] eval(NslInt1 a, NslInt1 b) {
    int[] dest = new int[a.getSize()];
    if (a.getSize() != b.getSize()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    return eval(dest, a, b);
  }


  public static int[] eval(int[] dest, NslInt1 a, NslInt1 b) {
    int i;
    int len = dest.length;
    int[] tmpa=a.getint1();
    int[] tmpb=b.getint1();

    if (len != a.getSize() || len != b.getSize()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    for (i=0; i<len; i++) {
        dest[i] = tmpa[i]*tmpb[i];
    }
    return dest;
  }

// int [][] -------------------------------------


  public static int[][] eval(int[][] a, int b) {
    int[][] dest = new int[a.length][a[0].length];
    return eval(dest, a, b);
  }

  public static int[][] eval(int[][] dest, int[][] a, int b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != a.length || len2 !=a[0].length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a[i1][i2]*b;
    return dest;
  }



  public static int[][] eval(int a, int[][] b) {
    return eval(b, a);
  }

  public static int[][] eval(int[][] dest, int a, int[][] b) {
    return eval(dest, b, a);
  }

  public static int[][] eval(int[][] a, int[][] b) {
    int[][] dest = new int[a.length][a[0].length];
    return eval(dest, a, b);
  }
  public static int[][] eval(int[][] dest, int[][] a, int[][] b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if (len1 != a.length || len2 != a[0].length ||
        len1 != b.length || len2 != b[0].length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = a[i1][i2]*b[i1][i2];
    return dest;
  }

  public static int[][] eval(NslInt2 a, int b) {
    int[][] dest = new int[a.getSize1()][a.getSize2()];
    return eval(dest, a, b);
  }

  public static int[][] eval(int[][] dest, NslInt2 a, int b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int[][] tmpa=a.getint2();

    if(len1 != a.getSize1() || len2 != a.getSize2()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=tmpa[i1][i2]*b;
    return dest;
  }

  public static int[][] eval(NslInt2 a, NslInt0 b) {
    int[][] dest = new int[a.getSize1()][a.getSize2()];
    return eval(dest, a, b);
  }

  public static int[][] eval(int[][] dest, NslInt2 a, NslInt0 b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int[][] tmpa=a.getint2();
    int tmpb=b.getint();

    if(len1 != a.getSize1() || len2 != a.getSize2()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=tmpa[i1][i2]*tmpb;
    return dest;
  }

  public static int[][] eval(int a, NslInt2 b) {
    return eval(b, a);
  }

  public static int[][] eval(int[][] dest, int a, NslInt2 b) {
    return eval(dest, b, a);
  }

  public static int[][] eval(NslInt0 a, NslInt2 b) {
    return eval(b, a);
  }

  public static int[][] eval(int[][] dest, NslInt0 a, NslInt2 b) {
    return eval(dest, b, a);
  }

  public static int[][] eval(NslInt2 a, NslInt2 b) {
    int[][] dest = new int[a.getSize1()][a.getSize2()];
    return eval(dest, a, b);
  }

  public static int[][] eval(int[][] dest, NslInt2 a, NslInt2 b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int[][] tmpa=a.getint2();
    int[][] tmpb=b.getint2();

    if (len1 != a.getSize1() || len2 != a.getSize2() ||
        len1 != b.getSize1() || len2 != b.getSize2()) 
    {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = tmpa[i1][i2]*tmpb[i1][i2];
    return dest;
  }

/* doubles */


  public static double eval(double a, double b) {
    return a*b;
  }

  public static double[] eval(double[] a, double b) {
    double[] dest = new double[a.length];
    return eval(dest, a, b);
  }

  public static double[] eval(double[] dest, double[] a, double b) {
    int i;
    int len = dest.length;
    for (i=0; i<len; i++)
        dest[i] = a[i]*b;
    return dest;
  }

  public static double[] eval(double a, double[] b) {
    return eval(b, a);
  }

  public static double[] eval(double[] dest, double a, double[] b) {
    return eval(dest, b, a);
  }

  public static double[] eval(double[] a, double[] b) {
    double[] dest = new double[a.length];
    if (a.length != b.length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    return eval(dest, a, b);
  }


  public static double[] eval(double[] dest, double[] a, double[] b) {
    int i;
    int len = dest.length;
    if (len != a.length || len != b.length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    for (i=0; i<len; i++) {
        dest[i] = a[i]*b[i];
    }
    return dest;
  }



//double[][]-----------
  public static double[][] eval(double[][] a, double b) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a, b);
  }

  public static double[][] eval(double[][] dest, double[][] a, double b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != a.length || len2 !=a[0].length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a[i1][i2]*b;
    return dest;
  }



  public static double[][] eval(double a, double[][] b) {
    return eval(b, a);
  }

  public static double[][] eval(double[][] dest, double a, double[][] b) {
    return eval(dest, b, a);
  }

  public static double[][] eval(double[][] a, double[][] b) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a, b);
  }
  public static double[][] eval(double[][] dest, double[][] a, double[][] b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if (len1 != a.length || len2 != a[0].length ||
        len1 != b.length || len2 != b[0].length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = a[i1][i2]*b[i1][i2];
    return dest;
  }


// NslDoubles -------------
  public static double eval(NslDouble0 a, double b) {
    return a.getdouble()*b;
  }

  public static double eval(double a, NslDouble0 b) {
    return a*b.getdouble();
  }

  public static double eval(NslDouble0 a, NslDouble0 b) {
    return a.getdouble()*b.getdouble();
  }

  public static double[] eval(NslDouble1 a, double b) {
    double[] dest = new double[a.getSize()];
    return eval(dest, a, b);
  }

  public static double[] eval(double[] dest, NslDouble1 a, double b) {
    int i;
    int len = dest.length;
    double[] tmpa=a.getdouble1();

    for (i=0; i<len; i++)
        dest[i] = tmpa[i]*b;
    return dest;
  }

  public static double[] eval(NslDouble1 a, NslDouble0 b) {
    double[] dest = new double[a.getSize()];
    return eval(dest, a, b);
  }

  public static double[] eval(double[] dest, NslDouble1 a, NslDouble0 b) {
    int i;
    int len = dest.length;
    double[] tmpa=a.getdouble1();
    double tmpb=b.getdouble();

    for (i=0; i<len; i++)
        dest[i] = tmpa[i]*tmpb;
    return dest;
  }

  public static double[] eval(double a, NslDouble1 b) {
    return eval(b, a);
  }

  public static double[] eval(double[] dest, double a, NslDouble1 b) {
    return eval(dest, b, a);
  }

  public static double[] eval(NslDouble0 a, NslDouble1 b) {
    return eval(b, a);
  }

  public static double[] eval(double[] dest, NslDouble0 a, NslDouble1 b) {
    return eval(dest, b, a);
  }

  public static double[] eval(NslDouble1 a, NslDouble1 b) {
    double[] dest = new double[a.getSize()];
    if (a.getSize() != b.getSize()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    return eval(dest, a, b);
  }

  public static double[] eval(double[] dest, NslDouble1 a, NslDouble1 b) {
    int i;
    int len = dest.length;
    double[] tmpa=a.getdouble1();
    double[] tmpb=b.getdouble1();

    if (len != a.getSize() || len != b.getSize()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    for (i=0; i<len; i++) {
        dest[i] = tmpa[i]*tmpb[i];
    }
    return dest;
  }

// Erhan Added:
  public static double[] eval(double[] dest, double[] a, NslDouble1 b) {
    int i;
    int len = dest.length;
    double[] tmpb=b.getdouble1();
 
    if (len != a.length || len != b.getSize()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    for (i=0; i<len; i++) {
        dest[i] = a[i]*tmpb[i];
    }
    return dest;
  }
  public static double[] eval(double[] dest,  NslDouble1 b,double[] a) {
  return eval(dest,a,b);
}

/* more doubles again */

  public static double[][] eval(NslDouble2 a, double b) {
    double[][] dest = new double[a.getSize1()][a.getSize2()];
    return eval(dest, a, b);
  }

  public static double[][] eval(double[][] dest, NslDouble2 a, double b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;
    double[][] tmpa=a.getdouble2();

    if(len1 != a.getSize1() || len2 !=a.getSize2()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=tmpa[i1][i2]*b;
    return dest;
  }

  public static double[][] eval(NslDouble2 a, NslDouble0 b) {
    double[][] dest = new double[a.getSize1()][a.getSize2()];
    return eval(dest, a, b);
  }

  public static double[][] eval(double[][] dest, NslDouble2 a, NslDouble0 b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;
    double[][] tmpa=a.getdouble2();
    double tmpb=b.getdouble();

    if(len1 != a.getSize1() || len2 !=a.getSize2()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=tmpa[i1][i2]*tmpb;
    return dest;
  }

  public static double[][] eval(double a, NslDouble2 b) {
    return eval(b, a);
  }

  public static double[][] eval(double[][] dest, double a, NslDouble2 b) {
    return eval(dest, b, a);
  }

  public static double[][] eval(NslDouble0 a, NslDouble2 b) {
    return eval(b, a);
  }

  public static double[][] eval(double[][] dest, NslDouble0 a, NslDouble2 b) {
    return eval(dest, b, a);
  }

  public static double[][] eval(NslDouble2 a, NslDouble2 b) {
    double[][] dest = new double[a.getSize1()][a.getSize2()];
    return eval(dest, a, b);
  }
  public static double[][] eval(double[][] dest, NslDouble2 a, double[][] b) {
    return eval(dest, a.get(), b);
  }
  public static double[][] eval(double[][] dest, double[][] a, NslDouble2 b) {
    return eval(dest, a, b.get());
  }

  public static double[][] eval(double[][] dest, NslDouble2 a, NslDouble2 b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;
    double[][] tmpa=a.getdouble2();
    double[][] tmpb=b.getdouble2();

    if (len1 != a.getSize1() || len2 != a.getSize2() ||
        len1 != b.getSize1() || len2 != b.getSize2()) 
    {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = tmpa[i1][i2]*tmpb[i1][i2];
    return dest;
  }

/* floats */

  public static float eval(float a, float b) {
    return a*b;
  }

  public static float[] eval(float[] a, float b) {
    float[] dest = new float[a.length];
    return eval(dest, a, b);
  }

  public static float[] eval(float[] dest, float[] a, float b) {
    int i;
    int len = dest.length;
    for (i=0; i<len; i++)
        dest[i] = a[i]*b;
    return dest;
  }

  public static float[] eval(float a, float[] b) {
    return eval(b, a);
  }

  public static float[] eval(float[] dest, float a, float[] b) {
    return eval(dest, b, a);
  }

  public static float[] eval(float[] a, float[] b) {
    float[] dest = new float[a.length];
    if (a.length != b.length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    return eval(dest, a, b);
  }


  public static float[] eval(float[] dest, float[] a, float[] b) {
    int i;
    int len = dest.length;
    if (len != a.length || len != b.length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    for (i=0; i<len; i++) {
        dest[i] = a[i]*b[i];
    }
    return dest;
  }



/* more floats -----------*/
  public static float[][] eval(float[][] a, float b) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a, b);
  }

  public static float[][] eval(float[][] dest, float[][] a, float b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != a.length || len2 !=a[0].length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a[i1][i2]*b;
    return dest;
  }



  public static float[][] eval(float a, float[][] b) {
    return eval(b, a);
  }

  public static float[][] eval(float[][] dest, float a, float[][] b) {
    return eval(dest, b, a);
  }

  public static float[][] eval(float[][] a, float[][] b) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a, b);
  }
  public static float[][] eval(float[][] dest, float[][] a, float[][] b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if (len1 != a.length || len2 != a[0].length ||
        len1 != b.length || len2 != b[0].length) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = a[i1][i2]*b[i1][i2];
    return dest;
  }


/* NslFloats */
  public static float eval(NslFloat0 a, float b) {
    return a.getfloat()*b;
  }

  public static float eval(float a, NslFloat0 b) {
    return a*b.getfloat();
  }

  public static float eval(NslFloat0 a, NslFloat0 b) {
    return a.getfloat()*b.getfloat();
  }

  public static float[] eval(NslFloat1 a, float b) {
    float[] dest = new float[a.getSize()];
    return eval(dest, a, b);
  }

  public static float[] eval(float[] dest, NslFloat1 a, float b) {
    int i;
    int len = dest.length;
    float[] tmpa=a.getfloat1();

    for (i=0; i<len; i++)
        dest[i] = tmpa[i]*b;
    return dest;
  }

  public static float[] eval(NslFloat1 a, NslFloat0 b) {
    float[] dest = new float[a.getSize()];
    return eval(dest, a, b);
  }

  public static float[] eval(float[] dest, NslFloat1 a, NslFloat0 b) {
    int i;
    int len = dest.length;
    float[] tmpa=a.getfloat1();
    float tmpb=b.getfloat();

    for (i=0; i<len; i++)
        dest[i] = tmpa[i]*tmpb;
    return dest;
  }

  public static float[] eval(float a, NslFloat1 b) {
    return eval(b, a);
  }

  public static float[] eval(float[] dest, float a, NslFloat1 b) {
    return eval(dest, b, a);
  }

  public static float[] eval(NslFloat0 a, NslFloat1 b) {
    return eval(b, a);
  }

  public static float[] eval(float[] dest, NslFloat0 a, NslFloat1 b) {
    return eval(dest, b, a);
  }

  public static float[] eval(NslFloat1 a, NslFloat1 b) {
    float[] dest = new float[a.getSize()];
    if (a.getSize() != b.getSize()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    return eval(dest, a, b);
  }

  public static float[] eval(float[] dest, NslFloat1 a, NslFloat1 b) {
    int i;
    int len = dest.length;
    float[] tmpa=a.getfloat1();
    float[] tmpb=b.getfloat1();

    if (len != a.getSize() || len != b.getSize()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    for (i=0; i<len; i++) {
        dest[i] = tmpa[i]*tmpb[i];
    }
    return dest;
  }

// Erhan Added:
  public static float[] eval(float[] dest, float[] a, NslFloat1 b) {
    int i;
    int len = dest.length;
    float[] tmpb=b.getfloat1();
 
    if (len != a.length || len != b.getSize()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }
    for (i=0; i<len; i++) {
        dest[i] = a[i]*tmpb[i];
    }
    return dest;
  }
  public static float[] eval(float[] dest,  NslFloat1 b,float[] a) {
  return eval(dest,a,b);
}

// float[][] ---------------

  public static float[][] eval(NslFloat2 a, float b) {
    float[][] dest = new float[a.getSize1()][a.getSize2()];
    return eval(dest, a, b);
  }

  public static float[][] eval(float[][] dest, NslFloat2 a, float b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;
    float[][] tmpa=a.getfloat2();

    if(len1 != a.getSize1() || len2 !=a.getSize2()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=tmpa[i1][i2]*b;
    return dest;
  }

  public static float[][] eval(NslFloat2 a, NslFloat0 b) {
    float[][] dest = new float[a.getSize1()][a.getSize2()];
    return eval(dest, a, b);
  }

  public static float[][] eval(float[][] dest, NslFloat2 a, NslFloat0 b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;
    float[][] tmpa=a.getfloat2();
    float tmpb=b.getfloat();

    if(len1 != a.getSize1() || len2 !=a.getSize2()) {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=tmpa[i1][i2]*tmpb;
    return dest;
  }

  public static float[][] eval(float a, NslFloat2 b) {
    return eval(b, a);
  }

  public static float[][] eval(float[][] dest, float a, NslFloat2 b) {
    return eval(dest, b, a);
  }

  public static float[][] eval(NslFloat0 a, NslFloat2 b) {
    return eval(b, a);
  }

  public static float[][] eval(float[][] dest, NslFloat0 a, NslFloat2 b) {
    return eval(dest, b, a);
  }

  public static float[][] eval(NslFloat2 a, NslFloat2 b) {
    float[][] dest = new float[a.getSize1()][a.getSize2()];
    return eval(dest, a, b);
  }
  public static float[][] eval(float[][] dest, NslFloat2 a, float[][] b) {
    return eval(dest, a.get(), b);
  }
  public static float[][] eval(float[][] dest, float[][] a, NslFloat2 b) {
    return eval(dest, a, b.get());
  }

  public static float[][] eval(float[][] dest, NslFloat2 a, NslFloat2 b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;
    float[][] tmpa=a.getfloat2();
    float[][] tmpb=b.getfloat2();

    if (len1 != a.getSize1() || len2 != a.getSize2() ||
        len1 != b.getSize1() || len2 != b.getSize2()) 
    {
        System.out.println("(MUL)Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = tmpa[i1][i2]*tmpb[i1][i2];
    return dest;
  }


// 3d native ints by Karan, karan, KARAN
// int[][][]

public static int[][][] eval(int[][][] a, int b) {
    int[][][] dest = new int[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, int[][][] a, int b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if(len1 != a.length || len2 !=a[0].length || len3 !=a[0][0].length) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3]=a[i1][i2][i3]*b;
    return dest;
  }



  public static int[][][] eval(int a, int[][][] b) {
    return eval(b, a);
  }

  public static int[][][] eval(int[][][] dest, int a, int[][][] b) {
    return eval(dest, b, a);
  }

  public static int[][][] eval(int[][][] a, int[][] b) {
    int[][][] dest = new int[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, int[][][] a, int[][] b) {
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length ||
        len1 != b.length || len2 != b[0].length ) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = a[i1][i2][i3]*b[i2][i3];
    return dest;
  }


  public static int[][][] eval(int[][][] a, int[][][] b) {
    int[][][] dest = new int[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }
  public static int[][][] eval(int[][][] dest, int[][][] a, int[][][] b) {
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length ||
        len1 != b.length || len2 != b[0].length || len3 !=b[0][0].length ) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = a[i1][i2][i3]*b[i1][i2][i3];
    return dest;
  }



// double[][][] -------------


public static double[][][] eval(double[][][] a, double b) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, double[][][] a, double b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if(len1 != a.length || len2 !=a[0].length || len3 !=a[0][0].length) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3]=a[i1][i2][i3]*b;
    return dest;
  }



  public static double[][][] eval(double a, double[][][] b) {
    return eval(b, a);
  }

  public static double[][][] eval(double[][][] dest, double a, double[][][] b) {
    return eval(dest, b, a);
  }

  public static double[][][] eval(double[][][] a, double[][] b) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, double[][][] a, double[][] b) {
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length ||
        len1 != b.length || len2 != b[0].length ) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = a[i1][i2][i3]*b[i2][i3];
    return dest;
  }


  public static double[][][] eval(double[][][] a, double[][][] b) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }
  public static double[][][] eval(double[][][] dest, double[][][] a, double[][][] b) {
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length ||
        len1 != b.length || len2 != b[0].length || len3 !=b[0][0].length ) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = a[i1][i2][i3]*b[i1][i2][i3];
    return dest;
  }



public static double[][][] eval(double[][][] a, double[] b) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, double[][][] a, double[] b) {
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length ||
        len1 != b.length  ) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = a[i1][i2][i3]*b[i3];
    return dest;
  }

public static double[][][] eval(double[] b,double [][][] a) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }

public static double[][][] eval(double[][][] dest, double[] b,double [][][] a) {
    return eval(dest, a, b);
  }


// NslInt3 

public static int[][][] eval(NslInt3 a, int b) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, int b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length; 
    int[][][] tmpa=a.getint3();

    if(len1 != a.getSize1() || len2 != a.getSize2() ||len3 != a.getSize3()) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
           for (i3=0; i3<len3; i3++)
            dest[i1][i2][i3]=tmpa[i1][i2][i3]*b;
    return dest;
  }

public static int[][][] eval(int b, NslInt3 a) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static int[][][] eval(int[][][] dest,int b,NslInt3 a){
    return eval(dest,a,b); 
  }

  public static int[][][] eval(NslInt3 a, NslInt0 b) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, NslInt0 b) {
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int[][][] tmpa=a.getint3();
    int tmpb=b.getint();

    if(len1 != a.getSize1() || len2 != a.getSize2() || len3 != a.getSize3()) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
           for(i3=0; i3<len3; i3++) 
            dest[i1][i2][i3]=tmpa[i1][i2][i3]*tmpb;
    return dest;
  }

public static int[][][] eval(NslInt0 b, NslInt3 a) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static int[][][] eval(int[][][] dest,NslInt0 b, NslInt3 a){
    return eval(dest,a,b);
} 

 
public static int[][][] eval(NslInt3 a, NslInt2 b) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, NslInt2 b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int[][][] tmpa=a.getint3();
    int[][] tmpb=b.getint2();

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.getSize1() || len2 != b.getSize2()) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*tmpb[i2][i3];
    return dest;
  }

public static int[][][] eval(NslInt2 b, NslInt3 a) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static int[][][] eval(int[][][] dest,NslInt2 b, NslInt3 a) {
    return eval(dest,a,b);
  }



public static int[][][] eval(NslInt3 a, NslInt1 b) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, NslInt1 b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int[][][] tmpa=a.getint3();
    int[] tmpb=b.getint1();

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.getSize1()) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*tmpb[i3];
    return dest;
  }

public static int[][][] eval(NslInt1 b, NslInt3 a) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static int[][][] eval(int[][][] dest,NslInt1 b, NslInt3 a) {
    return eval(dest,a,b);
  }

public static int[][][] eval(NslInt3 a, NslInt3 b) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static int[][][] eval(int[][][] dest, NslInt3 a, NslInt3 b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int[][][] tmpa=a.getint3();
    int[][][] tmpb=b.getint3();

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a.getSize3() ||
        len1 != b.getSize1() || len2 != b.getSize2() || len3 != b.getSize3() )
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*tmpb[i1][i2][i3];
    return dest;
  }

public static int[][][] eval(NslInt3 a, int [] b) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, int[] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int[][][] tmpa=a.getint3();
    

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.length) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*b[i3];
    return dest;
  }


public static int[][][] eval(NslInt3 a, int[][] b) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, int[][] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int[][][] tmpa=a.getint3();
    

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.length || len2 != b[0].length) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*b[i2][i3];
    return dest;
  }

public static int[][][] eval(int[][] b, NslInt3 a) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static int[][][] eval(int[][][] dest,int[][] b, NslInt3 a) {
    return eval(dest,a,b);
  }

public static int[][][] eval(NslInt3 a, int[][][] b) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, int[][][] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int[][][] tmpa=a.getint3();
    

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.length || len2 != b[0].length || len3 != b[0][0].length) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*b[i1][i2][i3];
    return dest;
  }

public static int[][][] eval(int[][][] b, NslInt3 a) {
    int[][][] dest = new int[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static int[][][] eval(int[][][] dest,int[][][] b, NslInt3 a) {
    return eval(dest,a,b);
  }

//NslDouble

public static double[][][] eval(NslDouble3 a, double b) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, double b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length; 
    double[][][] tmpa=a.getdouble3();

    if(len1 != a.getSize1() || len2 != a.getSize2() ||len3 != a.getSize3()) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
           for (i3=0; i3<len3; i3++)
            dest[i1][i2][i3]=tmpa[i1][i2][i3]*b;
    return dest;
  }

public static double[][][] eval(double b, NslDouble3 a) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static double[][][] eval(double[][][] dest,double b,NslDouble3 a){
    return eval(dest,a,b); 
  }

  public static double[][][] eval(NslDouble3 a, NslDouble0 b) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, NslDouble0 b) {
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    double[][][] tmpa=a.getdouble3();
    double tmpb=b.getdouble();

    if(len1 != a.getSize1() || len2 != a.getSize2() || len3 != a.getSize3()) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
           for(i3=0; i3<len3; i3++) 
            dest[i1][i2][i3]=tmpa[i1][i2][i3]*tmpb;
    return dest;
  }

public static double[][][] eval(NslDouble0 b, NslDouble3 a) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static double[][][] eval(double[][][] dest,NslDouble0 b, NslDouble3 a){
    return eval(dest,a,b);
} 

 
public static double[][][] eval(NslDouble3 a, NslDouble2 b) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, NslDouble2 b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    double[][][] tmpa=a.getdouble3();
    double[][] tmpb=b.getdouble2();

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.getSize1() || len2 != b.getSize2()) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*tmpb[i2][i3];
    return dest;
  }

public static double[][][] eval(NslDouble2 b, NslDouble3 a) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static double[][][] eval(double[][][] dest,NslDouble2 b, NslDouble3 a) {
    return eval(dest,a,b);
  }



public static double[][][] eval(NslDouble3 a, NslDouble1 b) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, NslDouble1 b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    double[][][] tmpa=a.getdouble3();
    double[] tmpb=b.getdouble1();

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.getSize1()) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*tmpb[i3];
    return dest;
  }

public static double[][][] eval(NslDouble1 b, NslDouble3 a) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static double[][][] eval(double[][][] dest,NslDouble1 b, NslDouble3 a) {
    return eval(dest,a,b);
  }

public static double[][][] eval(NslDouble3 a, NslDouble3 b) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static double[][][] eval(double[][][] dest, NslDouble3 a, NslDouble3 b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    double[][][] tmpa=a.getdouble3();
    double[][][] tmpb=b.getdouble3();

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a.getSize3() ||
        len1 != b.getSize1() || len2 != b.getSize2() || len3 != b.getSize3() )
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*tmpb[i1][i2][i3];
    return dest;
  }

public static double[][][] eval(NslDouble3 a, double [] b) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, double[] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    double[][][] tmpa=a.getdouble3();
    

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.length) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*b[i3];
    return dest;
  }

public static double[][][] eval(double[] b, NslDouble3 a) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static double[][][] eval(double[][][] dest,double[] b, NslDouble3 a) {
    return eval(dest,a,b);
  }



public static double[][][] eval(NslDouble3 a, double[][] b) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, double[][] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    double[][][] tmpa=a.getdouble3();
    

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.length || len2 != b[0].length) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*b[i2][i3];
    return dest;
  }

public static double[][][] eval(double[][] b, NslDouble3 a) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static double[][][] eval(double[][][] dest,double[][] b, NslDouble3 a) {
    return eval(dest,a,b);
  }

public static double[][][] eval(NslDouble3 a, double[][][] b) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, double[][][] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    double[][][] tmpa=a.getdouble3();
    

    if (len1 != a.getSize1() || len2 != a.getSize2() || len3 != a. getSize3() ||
        len1 != b.length || len2 != b[0].length || len3 != b[0][0].length) 
    {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            dest[i1][i2][i3] = tmpa[i1][i2][i3]*b[i1][i2][i3];
    return dest;
  }

public static double[][][] eval(double[][][] b, NslDouble3 a) {
    double[][][] dest = new double[a.getSize1()][a.getSize2()][a.getSize3()];
    return eval(dest, a, b);
  }

public static double[][][] eval(double[][][] dest,double[][][] b, NslDouble3 a) {
    return eval(dest,a,b);
  }

} //end class





