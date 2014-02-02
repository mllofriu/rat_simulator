/*  SCCS - @(#)NslMin.java	1.2 - 05/21/99 - 17:43:12 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslMin.java,v $
 * Revision 1.2  1997/11/18 01:28:54  erhan
 * NslMin/Max now works on doubles also
 *
 * Revision 1.1  1997/07/30 21:19:30  erhan
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
// Minimum number routines
//
//

/**
 Minimum number routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a, b) -> c
 a, b are the parameter to evaluate the Minimum value of
 a and b pointwise and the result is passed out as c
 2. eval(dest, a, b) -> c
 a, b are the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

*/

package nslj.src.math;

public final class NslMin {
    public static int eval(int a, int b) {
        if (a<b)
            return a;
        else
            return b;
    }

    public static int[] eval(int[] a, int[] b) {
        return eval(new int[a.length], a, b);
    }

    public static int[] eval(int[] dest, int[] a, int[] b) {
        int i;
        int len = dest.length;

        if (len!=a.length || len!= b.length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i=0; i<len; i++) {
            if (a[i]<b[i])
                dest[i] = a[i];
            else
                dest[i] = b[i];
        }
        return dest;
    }

    public static int[] eval(int[] a, int b) {
        return eval(new int[a.length], a, b);
    }

    public static int[] eval(int[] dest, int[] a, int b) {
        int i;
        int len = dest.length;

        if (len!=a.length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i=0; i<len; i++) {
            if (a[i]<b)
                dest[i] = a[i];
            else
                dest[i] = b;
        }
        return dest;
    }


    public static int[] eval(int a, int[] b) {
        return eval(b, a);
    }
    public static int[] eval(int[] dest, int a, int[] b) {
        return eval(dest, b, a);
    }

    public static int[][] eval(int[][] a, int[][] b) {
        return eval(new int[a.length][a[0].length], a, b);
    }

    public static int[][] eval(int[][] dest, int[][] a, int[][] b) {
        int i1, i2;
        int len1 = dest.length;
        int len2 = dest[0].length;

        if (len1!=a.length || len1!= b.length ||
            len2!=a[0].length || len2!=b[0].length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i1=0; i1<len1; i1++)
            for(i2=0; i2<len2; i2++){
                if (a[i1][i2]<b[i1][i2])
                    dest[i1][i2] = a[i1][i2];
                else
                    dest[i1][i2] = b[i1][i2];
            }
        return dest;
    }

    public static int[][] eval(int[][] a, int b) {
        return eval(new int[a.length][a[0].length], a, b);
    }

    public static int[][] eval(int[][] dest, int[][] a, int b) {
        int i1, i2;
        int len1 = dest.length;
        int len2 = dest[0].length;

        if (len1!=a.length || len2!=a[0].length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i1=0; i1<len1; i1++)
            for(i2=0; i2<len2; i2++){
                if (a[i1][i2]<b)
                    dest[i1][i2] = a[i1][i2];
                else
                    dest[i1][i2] = b;
            }
        return dest;
    }
    public static int[][] eval(int a, int[][] b) {
        return eval(b, a);
    }
    public static int[][] eval(int[][] dest, int a, int[][] b) {
        return eval(dest, b, a);
    }

/* doubles */
    public static double eval(double a, double b) {
        if (a<b)
            return a;
        else
            return b;
    }

    public static double[] eval(double[] a, double[] b) {
        return eval(new double[a.length], a, b);
    }

    public static double[] eval(double[] dest, double[] a, double[] b) {
        int i;
        double len = dest.length;

        if (len!=a.length || len!= b.length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i=0; i<len; i++) {
            if (a[i]<b[i])
                dest[i] = a[i];
            else
                dest[i] = b[i];
        }
        return dest;
    }

    public static double[] eval(double[] a, double b) {
        return eval(new double[a.length], a, b);
    }

    public static double[] eval(double[] dest, double[] a, double b) {
        int i;
        double len = dest.length;

        if (len!=a.length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i=0; i<len; i++) {
            if (a[i]<b)
                dest[i] = a[i];
            else
                dest[i] = b;
        }
        return dest;
    }


    public static double[] eval(double a, double[] b) {
        return eval(b, a);
    }
    public static double[] eval(double[] dest, double a, double[] b) {
        return eval(dest, b, a);
    }

    public static double[][] eval(double[][] a, double[][] b) {
        return eval(new double[a.length][a[0].length], a, b);
    }

    public static double[][] eval(double[][] dest, double[][] a, double[][] b) {
        int i1, i2;
        double len1 = dest.length;
        double len2 = dest[0].length;

        if (len1!=a.length || len1!= b.length ||
            len2!=a[0].length || len2!=b[0].length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i1=0; i1<len1; i1++)
            for(i2=0; i2<len2; i2++){
                if (a[i1][i2]<b[i1][i2])
                    dest[i1][i2] = a[i1][i2];
                else
                    dest[i1][i2] = b[i1][i2];
            }
        return dest;
    }

    public static double[][] eval(double[][] a, double b) {
        return eval(new double[a.length][a[0].length], a, b);
    }

    public static double[][] eval(double[][] dest, double[][] a, double b) {
        int i1, i2;
        double len1 = dest.length;
        double len2 = dest[0].length;

        if (len1!=a.length || len2!=a[0].length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i1=0; i1<len1; i1++)
            for(i2=0; i2<len2; i2++){
                if (a[i1][i2]<b)
                    dest[i1][i2] = a[i1][i2];
                else
                    dest[i1][i2] = b;
            }
        return dest;
    }
    public static double[][] eval(double a, double[][] b) {
        return eval(b, a);
    }
    public static double[][] eval(double[][] dest, double a, double[][] b) {
        return eval(dest, b, a);
    }


/* floats */
    public static float eval(float a, float b) {
        if (a<b)
            return a;
        else
            return b;
    }

    public static float[] eval(float[] a, float[] b) {
        return eval(new float[a.length], a, b);
    }

    public static float[] eval(float[] dest, float[] a, float[] b) {
        int i;
        float len = dest.length;

        if (len!=a.length || len!= b.length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i=0; i<len; i++) {
            if (a[i]<b[i])
                dest[i] = a[i];
            else
                dest[i] = b[i];
        }
        return dest;
    }

    public static float[] eval(float[] a, float b) {
        return eval(new float[a.length], a, b);
    }

    public static float[] eval(float[] dest, float[] a, float b) {
        int i;
        float len = dest.length;

        if (len!=a.length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i=0; i<len; i++) {
            if (a[i]<b)
                dest[i] = a[i];
            else
                dest[i] = b;
        }
        return dest;
    }


    public static float[] eval(float a, float[] b) {
        return eval(b, a);
    }
    public static float[] eval(float[] dest, float a, float[] b) {
        return eval(dest, b, a);
    }

    public static float[][] eval(float[][] a, float[][] b) {
        return eval(new float[a.length][a[0].length], a, b);
    }

    public static float[][] eval(float[][] dest, float[][] a, float[][] b) {
        int i1, i2;
        float len1 = dest.length;
        float len2 = dest[0].length;

        if (len1!=a.length || len1!= b.length ||
            len2!=a[0].length || len2!=b[0].length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i1=0; i1<len1; i1++)
            for(i2=0; i2<len2; i2++){
                if (a[i1][i2]<b[i1][i2])
                    dest[i1][i2] = a[i1][i2];
                else
                    dest[i1][i2] = b[i1][i2];
            }
        return dest;
    }

    public static float[][] eval(float[][] a, float b) {
        return eval(new float[a.length][a[0].length], a, b);
    }

    public static float[][] eval(float[][] dest, float[][] a, float b) {
        int i1, i2;
        float len1 = dest.length;
        float len2 = dest[0].length;

        if (len1!=a.length || len2!=a[0].length) {
            System.out.println("Inconsistent array size");
            return dest;
        }

        for (i1=0; i1<len1; i1++)
            for(i2=0; i2<len2; i2++){
                if (a[i1][i2]<b)
                    dest[i1][i2] = a[i1][i2];
                else
                    dest[i1][i2] = b;
            }
        return dest;
    }
    public static float[][] eval(float a, float[][] b) {
        return eval(b, a);
    }
    public static float[][] eval(float[][] dest, float a, float[][] b) {
        return eval(dest, b, a);
    }



// native 3d by karan, Karan, KARAN DOUBLE

  public static double[][][] eval(double[][][] a, double b)  {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }
  public static double[][][] eval(double[][][] dest, double[][][] a, double b) 
  {
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
             if ( a[i1][i2][i3]<b)
            dest[i1][i2][i3]=a[i1][i2][i3];
            else  dest[i1][i2][i3]=b;
    return dest;
  }



  public static double[][][] eval(double b, double[][][] a) {
    return eval(a, b);
  }

  public static double[][][] eval(double[][][] dest, double a, double[][][] b) {
    return eval(dest, b, a);
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
	      if (a[i1][i2][i3]<b[i1][i2][i3])
              dest[i1][i2][i3] = a[i1][i2][i3];
	      else dest[i1][i2][i3]=b[i1][i2][i3];
    return dest;
  }



//------------------------------------
// 3d native ints by karan, Karan, KARAN


  public static int[][][] eval(int[][][] a, int b) {
    int[][][] dest = new int[a.length][a[0].length][a[0][0].length];
    return eval(dest, a,b);
  }

  public static int[][][] eval(int[][][] dest, int[][][] a, int b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length) {
        System.out.println("Array size inconsistent");
        return dest;
    }

    for (i1=0; i1<len1; i1++) {
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
	      if  (a[i1][i2][i3]<b)
		dest[i1][i2][i3] = a[i1][i2][i3];
	      else dest[i1][i2][i3] = b;
	}
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
	      if (a[i1][i2][i3]<b[i1][i2][i3])
              dest[i1][i2][i3] = a[i1][i2][i3];
	      else dest[i1][i2][i3]=b[i1][i2][i3];
    return dest;
  }

// 3d nsltypes
// 4d natives
// 4d nsltypes

}

