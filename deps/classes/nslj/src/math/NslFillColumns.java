/*  SCCS - @(#)NslFillColumns.java	1.5 - 09/01/99 - 00:18:06 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log$
*/
/**
Calls to these class methods will cause a 2 dimensional array
to be filled column by column by the single vector input.
Thus R[i][j]=I[i].
*/
package nslj.src.math;

import nslj.src.lang.NslInt0;
import nslj.src.lang.NslInt1;
import nslj.src.lang.NslInt2;
import nslj.src.lang.NslFloat0;
import nslj.src.lang.NslFloat1;
import nslj.src.lang.NslFloat2;
import nslj.src.lang.NslDouble0;
import nslj.src.lang.NslDouble1;
import nslj.src.lang.NslDouble2;
import nslj.src.lang.NslBoolean0;
import nslj.src.lang.NslBoolean1;
import nslj.src.lang.NslBoolean2;



public final class NslFillColumns {

  public static int[][] eval(int[][] dest,int[] b) {
    int org_rows=dest.length;
    int org_cols=dest[0].length;  
    int org_vector=b.length;

    if (org_rows!=org_vector) {
		// error
		throw new ArrayStoreException("column length must match vector");
	}
	else {
	    int i,j;
	    for (i=0;i<org_rows;i++)
	      for (j=0;j<org_cols;j++) 
	        dest[i][j]=b[i]; 
	    return dest;
	} // end else
  }

  public static int[][] eval(int[] b, int[][] dest) {
  	return eval(dest, b);
  }
  
  public static int[][] eval(NslInt2 dest, NslInt1 b) {
  	return eval(dest.getint2(), b.getint1());
  }

  public static int[][] eval(int[][] dest, NslInt1 b) {
  	return eval(dest, b.getint1());
  }
  
  public static int[][] eval(NslInt2 dest, int[] b) {
  	return eval(dest.getint2(), b);
  }

  public static int[][] eval(NslInt1 b, NslInt2 dest) {
  	return eval(dest.getint2(), b.getint1());
  }

  public static int[][] eval(NslInt1 b, int[][] dest) {
  	return eval(dest, b.getint1());
  }
  
  public static int[][] eval(int[] b, NslInt2 dest) {
  	return eval(dest.getint2(), b);
  }


  public static float[][] eval(float[][] dest,float[] b) {
    int org_rows=dest.length;
    int org_cols=dest[0].length;  
    int org_vector=b.length;

    if (org_rows!=org_vector) {
		// error
		throw new ArrayStoreException("column length must match vector");
	}
	else {
    int i,j;
    for (i=0;i<org_rows;i++)
      for (j=0;j<org_cols;j++) 
        dest[i][j]=b[i]; 
    return dest;
	} // end else
  }
  
  public static float[][] eval(NslFloat2 dest, NslFloat1 b) {
  	return eval(dest.getfloat2(), b.getfloat1());
  }

  public static float[][] eval(float[][] dest, NslFloat1 b) {
  	return eval(dest, b.getfloat1());
  }
  
  public static float[][] eval(NslFloat2 dest, float[] b) {
  	return eval(dest.getfloat2(), b);
  }

  public static float[][] eval(NslFloat1 b, NslFloat2 dest) {
  	return eval(dest.getfloat2(), b.getfloat1());
  }

  public static float[][] eval(NslFloat1 b, float[][] dest) {
  	return eval(dest, b.getfloat1());
  }
  
  public static float[][] eval(float[] b, NslFloat2 dest) {
  	return eval(dest.getfloat2(), b);
  }


  public static double[][] eval(double[][] dest,double[] b) {
    int org_rows=dest.length;
    int org_cols=dest[0].length;  
    int org_vector=b.length;
    if (org_rows!=org_vector) {
		// error
		throw new ArrayStoreException("column length must match vector");
	}
	else {
    int i,j;
    for (i=0;i<org_rows;i++)
      for (j=0;j<org_cols;j++) 
        dest[i][j]=b[i]; 
    return dest;
	} // end else
  }

    public static double[][] eval(double[] b, double[][] dest) {
  	return eval(dest, b);
  }
  
  public static double[][] eval(NslDouble2 dest, NslDouble1 b) {
  	return eval(dest.getdouble2(), b.getdouble1());
  }

  public static double[][] eval(double[][] dest, NslDouble1 b) {
  	return eval(dest, b.getdouble1());
  }
  
  public static double[][] eval(NslDouble2 dest, double[] b) {
  	return eval(dest.getdouble2(), b);
  }

  public static double[][] eval(NslDouble1 b, NslDouble2 dest) {
  	return eval(dest.getdouble2(), b.getdouble1());
  }

  public static double[][] eval(NslDouble1 b, double[][] dest) {
  	return eval(dest, b.getdouble1());
  }
  
  public static double[][] eval(double[] b, NslDouble2 dest) {
  	return eval(dest.getdouble2(), b);
  }
  public static boolean[][] eval(boolean[][] dest,boolean[] b) {
    int org_rows=dest.length;
    int org_cols=dest[0].length;  
    int org_vector=b.length;
    if (org_rows!=org_vector) {
		// error
		throw new ArrayStoreException("column length must match vector");
	}
	else {
    int i,j;
    for (i=0;i<org_rows;i++)
      for (j=0;j<org_cols;j++) 
        dest[i][j]=b[i]; 
    return dest;
	} // end else
  }

    public static boolean[][] eval(boolean[] b, boolean[][] dest) {
  	return eval(dest, b);
  }
  
  public static boolean[][] eval(NslBoolean2 dest, NslBoolean1 b) {
  	return eval(dest.getboolean2(), b.getboolean1());
  }

  public static boolean[][] eval(boolean[][] dest, NslBoolean1 b) {
  	return eval(dest, b.getboolean1());
  }
  
  public static boolean[][] eval(NslBoolean2 dest, boolean[] b) {
  	return eval(dest.getboolean2(), b);
  }

  public static boolean[][] eval(NslBoolean1 b, NslBoolean2 dest) {
  	return eval(dest.getboolean2(), b.getboolean1());
  }

  public static boolean[][] eval(NslBoolean1 b, boolean[][] dest) {
  	return eval(dest, b.getboolean1());
  }
  
  public static boolean[][] eval(boolean[] b, NslBoolean2 dest) {
  	return eval(dest.getboolean2(), b);
  }


}


