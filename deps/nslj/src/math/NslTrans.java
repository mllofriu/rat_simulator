/*  SCCS - @(#)NslTrans.java	1.4 --- 09/01/99 --00:18:13 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// $Log$
//
// 
//
//

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


//public final class NslTrans {
public class NslTrans {  //99/6/24 aa: NslTranspose inherits

/* ints */

  public static int[][] eval(int[] a) {
    return eval(new int[a.length][1],a);
  }

  public static int[][] eval(int[][] dest, int[] a) {
    int org_rows=a.length;
    
    if (dest[0].length != org_rows) {
    	dest = new int[org_rows][1];
    }
    
    int i;
    for (i=0;i<org_rows;i++)
        dest[i][0]=a[i]; 
    return dest;
  }  

  public static int[][] eval(int[][] a) {
    return eval(new int[a[0].length][a.length],a);
  }

  public static int[][] eval(int[][] dest, int[][] a) {
    int org_rows=a.length;
    int org_cols=a[0].length;
    int des_rows = dest.length;
    int des_cols = dest[0].length;

    if(des_rows != org_cols || des_cols !=org_rows) {
    	dest = new int[org_cols][org_rows];
    }
    
    int i,j;
    for (i=0;i<org_rows;i++)
      for (j=0;j<org_cols;j++) 
        dest[j][i]=a[i][j]; 
    return dest;
  }
  
/* doubles */

  public static double[][] eval(double[] a) {
    return eval(new double[a.length][1],a);
  }

  public static double[][] eval(double[][] dest, double[] a) {
    int org_rows=a.length;
    
    if (dest[0].length != org_rows) {
    	dest = new double[org_rows][1];
    }
    
    int i;
    for (i=0;i<org_rows;i++)
        dest[i][0]=a[i]; 
    return dest;
  }  

  public static double[][] eval(double[][] a) {
    return eval(new double[a[0].length][a.length],a);
  }

  public static double[][] eval(double[][] dest, double[][] a) {
    int org_rows=a.length;
    int org_cols=a[0].length;
    int des_rows = dest.length;
    int des_cols = dest[0].length;

    if(des_rows != org_cols || des_cols !=org_rows) {
	dest = new double[org_cols][org_rows];
    }
    
    int i,j;
    for (i=0;i<org_rows;i++)
      for (j=0;j<org_cols;j++) 
        dest[j][i]=a[i][j]; 
    return dest;
  }
 
/* floats */

  public static float[][] eval(float[] a) {
    return eval(new float[a.length][1],a);
  }

  public static float[][] eval(float[][] dest, float[] a) {
    int org_rows=a.length;
    
    if (dest[0].length != org_rows) {
    	dest = new float[org_rows][1];
    }
    
    int i;
    for (i=0;i<org_rows;i++)
        dest[i][0]=a[i]; 
    return dest;
  }  

  public static float[][] eval(float[][] a) {
    return eval(new float[a[0].length][a.length],a);
  }

  public static float[][] eval(float[][] dest, float[][] a) {
    int org_rows=a.length;
    int org_cols=a[0].length;
    int des_rows = dest.length;
    int des_cols = dest[0].length;

    if(des_rows != org_cols || des_cols !=org_rows) {
	dest = new float[org_cols][org_rows];
    }
    
    int i,j;
    for (i=0;i<org_rows;i++)
      for (j=0;j<org_cols;j++) 
        dest[j][i]=a[i][j]; 
    return dest;
  }
 
/* booleans */

  public static boolean[][] eval(boolean[] a) {
    return eval(new boolean[a.length][1],a);
  }

  public static boolean[][] eval(boolean[][] dest, boolean[] a) {
    int org_rows=a.length;
    
    if (dest[0].length != org_rows) {
    	dest = new boolean[org_rows][1];
    }
    
    int i;
    for (i=0;i<org_rows;i++)
        dest[i][0]=a[i]; 
    return dest;
  }  

  public static boolean[][] eval(boolean[][] a) {
    return eval(new boolean[a[0].length][a.length],a);
  }

  public static boolean[][] eval(boolean[][] dest, boolean[][] a) {
    int org_rows=a.length;
    int org_cols=a[0].length;
    int des_rows = dest.length;
    int des_cols = dest[0].length;

    if(des_rows != org_cols || des_cols !=org_rows) {
	dest = new boolean[org_cols][org_rows];
    }
    
    int i,j;
    for (i=0;i<org_rows;i++)
      for (j=0;j<org_cols;j++) 
        dest[j][i]=a[i][j]; 
    return dest;
  }
  
// Nsl types

/* ints */

  public static int[][] eval(NslInt1 a) {
    return eval(a.getint1());
  }

  public static int[][] eval(int[][] dest, NslInt1 a) {
    return eval(dest, a.getint1());
  }
  
  public static int[][] eval(NslInt2 a) {
    return eval(a.getint2());
  }

  public static int[][] eval(int[][] dest, NslInt2 a) {
    return eval(dest, a.getint2());
  }
  
/* doubles */

  public static double[][] eval(NslDouble1 a) {
    return eval(a.getdouble1());
  }

  public static double[][] eval(double[][] dest, NslDouble1 a) {
    return eval(dest, a.getdouble1());
  }
  
  public static double[][] eval(NslDouble2 a) {
    return eval(a.getdouble2());
  }

  public static double[][] eval(double[][] dest, NslDouble2 a) {
    return eval(dest, a.getdouble2());
  }
  
/* floats */

  public static float[][] eval(NslFloat1 a) {
    return eval(a.getfloat1());
  }

  public static float[][] eval(float[][] dest, NslFloat1 a) {
    return eval(dest, a.getfloat1());
  }
  
  public static float[][] eval(NslFloat2 a) {
    return eval(a.getfloat2());
  }

  public static float[][] eval(float[][] dest, NslFloat2 a) {
    return eval(dest, a.getfloat2());
  }
  
/* booleans */

  public static boolean[][] eval(NslBoolean1 a) {
    return eval(a.getboolean1());
  }

  public static boolean[][] eval(boolean[][] dest, NslBoolean1 a) {
    return eval(dest, a.getboolean1());
  }
  
  public static boolean[][] eval(NslBoolean2 a) {
    return eval(a.getboolean2());
  }

  public static boolean[][] eval(boolean[][] dest, NslBoolean2 a) {
    return eval(dest, a.getboolean2());
  }
  
  
}









