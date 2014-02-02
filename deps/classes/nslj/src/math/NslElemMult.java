/*  SCCS - @(#)NslElemMult.java	1.5 - 09/01/99 - 00:18:15 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Multiplication routines
//
//

package nslj.src.math;
import nslj.src.lang.*;

/**
 Multiplication routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a, b) -> c
 a, b are the parameter of the evaluation function to do
 a divides b pointwise and the result is passed out as c
 2. eval(dest, a, b) -> c
 a, b are the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

*/

public final class NslElemMult {

// 0d native ints
  
  public static int eval(int a, int b) {
    return a*b;
  }
  
// 1d native ints

  public static int[] eval(int[] a, int b) {
    int[] dest = new int[a.length];
    return eval(dest, a, b);
  }

  public static int[] eval(int[] dest, int[] a, int b) {
    int i;
    int len = dest.length;
    if (len != a.length ) {
        len = a.length;
        dest = new int[len];
    }
    for (i=0; i<len; i++)
        dest[i] = a[i]*b;
    return dest;
  }

  public static int[] eval(int a, int[] b) {
    int[] dest = new int[b.length];
    return eval(dest, a, b);
  }

  public static int[] eval(int[] dest, int a, int[] b) {
    int i;
    int len = dest.length;
    if (len != b.length ) {
        len = b.length;
        dest = new int[len];
    }
    for (i=0; i<len; i++)
        dest[i] = a*b[i];
    return dest;
  }

  public static int[] eval(int[] a, int[] b) {
    int[] dest = new int[a.length];
    return eval(dest, a, b);
  }


  public static int[] eval(int[] dest, int[] a, int[] b) {
    
    if (a.length != b.length) {
        System.out.println("NslSub:int[] eval(int[] a, int[] b): Array size inconsistent");
        return dest;
    }
    
    int i;
    int len = dest.length;
    if (len != a.length || len != b.length) {
        len  = a.length;
        dest = new int[len];
    } 
    for (i=0; i<len; i++) {
        dest[i] = a[i]*b[i];
    }
    return dest;
  }

// 2d native ints

  public static int[][] eval(int[][] a, int b) {
    int[][] dest = new int[a.length][a[0].length];
    return eval(dest, a, b);
  }

  public static int[][] eval(int[][] dest, int[][] a, int b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != a.length || len2 !=a[0].length) {
        len1 = a.length;
        len2 = a[0].length;
        dest = new int[len1][len2];
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a[i1][i2]*b;
    return dest;
  }

  public static int[][] eval(int a, int[][] b) {
    int[][] dest = new int[b.length][b[0].length];
    return eval(dest, a, b);
  }

  public static int[][] eval(int[][] dest, int a, int[][] b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != b.length || len2 !=b[0].length) {
        len1 = b.length;
        len2 = b[0].length;
        dest = new int[len1][len2];
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a*b[i1][i2];
    return dest;
  }

  public static int[][] eval(int[][] a, int[][] b) {
    int[][] dest = new int[a.length][a[0].length];
    return eval(dest, a, b);
  }
  
  public static int[][] eval(int[][] dest, int[][] a, int[][] b) {
    
    if (a.length != b.length || a[0].length != b[0].length) {
        System.out.println("NslSub:int[][] eval(int[][] a, int[][] b): Array size inconsistent");
        return dest;
    }
    
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if (len1 != a.length || len2 != a[0].length) {
        len1 = a.length;
        len2 = a[0].length;
        dest = new int[len1][len2];
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = a[i1][i2]*b[i1][i2];
    return dest;
  }

// 3d native by Karan KARAN

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
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	dest = new int[len1][len2][len3]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3]=a[i1][i2][i3]*b;
    return dest;
  }

  public static int[][][] eval(int a, int[][][] b) {
    int[][][] dest = new int[b.length][b[0].length][b[0][0].length];
    return eval(dest, a, b);
  }

  public static int[][][] eval(int[][][] dest, int a, int[][][] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if(len1 != b.length || len2 !=b[0].length || len3 !=b[0][0].length) {
        len1 = b.length;
    	len2 = b[0].length;
    	len3 = b[0][0].length;
    	dest = new int[len1][len2][len3]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3]=a*b[i1][i2][i3];
    return dest;
  }

  public static int[][][] eval(int[][][] a, int[][][] b) {
    int[][][] dest = new int[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }
  
  public static int[][][] eval(int[][][] dest, int[][][] a, int[][][] b) {
    if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length ) {
        System.out.println("NslSub:int[][][] eval(int[][][] a, int[][][] b): Array size inconsistent");
        return dest;
    }
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length ||
        len1 != b.length || len2 != b[0].length || len3 != b[0][0].length ) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	dest = new int[len1][len2][len3]; 	
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3] = a[i1][i2][i3]*b[i1][i2][i3];
            	
    return dest;
  }

// 4d native ints

  public static int[][][][] eval(int[][][][] a, int b) {
    int[][][][] dest = new int[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, b);
  }

  public static int[][][][] eval(int[][][][] dest, int[][][][] a, int b) {
   int i1, i2, i3, i4;
   int len1 = dest.length;
   int len2 = dest[0].length;
   int len3 = dest[0][0].length;
   int len4 = dest[0][0][0].length;   
   if(len1 != a.length || len2 !=a[0].length || len3 !=a[0][0].length || len4 !=a[0][0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	len4 = a[0][0][0].length;
    	dest = new int[len1][len2][len3][len4]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	for(i4=0; i4<len4; i4++)
            	    dest[i1][i2][i3][i4]=a[i1][i2][i3][i4]*b;
    return dest;
  }

  public static int[][][][] eval(int a, int[][][][] b) {
    int[][][][] dest = new int[b.length][b[0].length][b[0][0].length][b[0][0][0].length];
    return eval(dest, a, b);
  }

  public static int[][][][] eval(int[][][][] dest, int a, int[][][][] b) {
   int i1, i2, i3, i4;
   int len1 = dest.length;
   int len2 = dest[0].length;
   int len3 = dest[0][0].length;
   int len4 = dest[0][0][0].length;   
   if(len1 != b.length || len2 !=b[0].length || len3 !=b[0][0].length || len4 !=b[0][0][0].length) {
        len1 = b.length;
    	len2 = b[0].length;
    	len3 = b[0][0].length;
    	len4 = b[0][0][0].length;
    	dest = new int[len1][len2][len3][len4]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	for(i4=0; i4<len4; i4++)
            	    dest[i1][i2][i3][i4]=a*b[i1][i2][i3][i4];
    return dest;
  }

  public static int[][][][] eval(int[][][][] a, int[][][][] b) {
    int[][][][] dest = new int[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, b);
  }
  
  public static int[][][][] eval(int[][][][] dest, int[][][][] a, int[][][][] b) {
    if (a.length != b.length || a[0].length != b[0].length 
        || a[0][0].length != b[0][0].length || a[0][0][0].length != b[0][0][0].length) {
        System.out.println("NslSub:int[][][][] eval(int[][][][] a, int[][][][] b): Array size inconsistent");
        return dest;
    }
    int i1, i2, i3, i4;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int len4 = dest[0][0][0].length;   
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length || len4 !=a[0][0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	len4 = a[0][0][0].length;
    	dest = new int[len1][len2][len3][len4]; 	
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
             	for(i4=0; i4<len4; i4++)
           	    dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]*b[i1][i2][i3][i4];
            	
    return dest;
  }

//0d nsl int by Weifang

  public static int eval(NslInt0 a, int b) {
    return a.getint()*b;
  }

  public static int eval(int a, NslInt0 b) {
    return a*b.getint();
  }

  public static int eval(NslInt0 a, NslInt0 b) {
    return a.getint()*b.getint();
  }

//1d nsl int by Weifang

  public static int[] eval(NslInt1 a, int b) {
    return eval(a.getint1(), b);
  }
  
  public static int[] eval(int a, NslInt1 b) {
    return eval(a, b.getint1());
  }

  public static int[] eval(int[] dest, NslInt1 a, int b) {
    return eval(dest, a.getint1(), b);
  }
  
  public static int[] eval(int[] dest, int a, NslInt1 b) {
    return eval(dest, a, b.getint1());
  }

  public static int[] eval(int[] a, NslInt0 b) {
    return eval(a, b.getint());
  }
  
  public static int[] eval(NslInt0 a, int[] b) {
    return eval(a.getint(), b);
  }

  public static int[] eval(int[] dest, int[] a, NslInt0 b) {
    return eval(dest, a, b.getint());
  }

  public static int[] eval(int[] dest, NslInt0 a, int[] b) {
    return eval(dest, a.getint(), b);
  }
  
  public static int[] eval(NslInt1 a, NslInt0 b) {
    return eval(a.getint1(), b.getint());
  }
  
  public static int[] eval(NslInt0 a, NslInt1 b) {
    return eval(a.getint(), b.getint1());
  }

  public static int[] eval(int[] dest, NslInt1 a, NslInt0 b) {
    return eval(dest,a.getint1(),b.getint());
  }

  public static int[] eval(int[] dest, NslInt0 a, NslInt1 b) {
    return eval(dest, a.getint(), b.getint1());
  }

  public static int[] eval(NslInt1 a, NslInt1 b) {
    return eval(a.getint1(), b.getint1());
  }

  public static int[] eval(int[] dest, NslInt1 a, NslInt1 b) {
    return eval(dest, a.getint1(), b.getint1());
  }
  
// 2d nsl int

  public static int[][] eval(NslInt2 a, int b) {
    return eval(a.getint2(), b);
  }
  
  public static int[][] eval(int a, NslInt2 b) {
    return eval(a, b.getint2());
  }

  public static int[][] eval(int[][] dest, NslInt2 a, int b) {
    return eval(dest, a.getint2(), b);
  }
  
  public static int[][] eval(int[][] dest, int a, NslInt2 b) {
    return eval(dest, a, b.getint2());
  }

  public static int[][] eval(int[][] a, NslInt0 b) {
    return eval(a, b.getint());
  }
  
  public static int[][] eval(NslInt0 a, int[][] b) {
    return eval(a.getint(), b);
  }

  public static int[][] eval(int[][] dest, int[][] a, NslInt0 b) {
    return eval(dest, a, b.getint());
  }

  public static int[][] eval(int[][] dest, NslInt0 a, int[][] b) {
    return eval(dest, a.getint(), b);
  }
  
  public static int[][] eval(NslInt2 a, NslInt0 b) {
    return eval(a.getint2(), b.getint());
  }
  
  public static int[][] eval(NslInt0 a, NslInt2 b) {
    return eval(a.getint(), b.getint2());
  }

  public static int[][] eval(int[][] dest, NslInt2 a, NslInt0 b) {
    return eval(dest,a.getint2(),b.getint());
  }

  public static int[][] eval(int[][] dest, NslInt0 a, NslInt2 b) {
    return eval(dest, a.getint(), b.getint2());
  }

  public static int[][] eval(NslInt2 a, NslInt2 b) {
    return eval(a.getint2(), b.getint2());
  }

  public static int[][] eval(int[][] dest, NslInt2 a, NslInt2 b) {
    return eval(dest, a.getint2(), b.getint2());
  }

// 3d nsl int

  public static int[][][] eval(NslInt3 a, int b) {
    return eval(a.getint3(), b);
  }
  
  public static int[][][] eval(int a, NslInt3 b) {
    return eval(a, b.getint3());
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, int b) {
    return eval(dest, a.getint3(), b);
  }
  
  public static int[][][] eval(int[][][] dest, int a, NslInt3 b) {
    return eval(dest, a, b.getint3());
  }

  public static int[][][] eval(int[][][] a, NslInt0 b) {
    return eval(a, b.getint());
  }
  
  public static int[][][] eval(NslInt0 a, int[][][] b) {
    return eval(a.getint(), b);
  }

  public static int[][][] eval(int[][][] dest, int[][][] a, NslInt0 b) {
    return eval(dest, a, b.getint());
  }

  public static int[][][] eval(int[][][] dest, NslInt0 a, int[][][] b) {
    return eval(dest, a.getint(), b);
  }
  
  public static int[][][] eval(NslInt3 a, NslInt0 b) {
    return eval(a.getint3(), b.getint());
  }
  
  public static int[][][] eval(NslInt0 a, NslInt3 b) {
    return eval(a.getint(), b.getint3());
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, NslInt0 b) {
    return eval(dest,a.getint3(),b.getint());
  }

  public static int[][][] eval(int[][][] dest, NslInt0 a, NslInt3 b) {
    return eval(dest, a.getint(), b.getint3());
  }

  public static int[][][] eval(NslInt3 a, NslInt3 b) {
    return eval(a.getint3(), b.getint3());
  }

  public static int[][][] eval(int[][][] dest, NslInt3 a, NslInt3 b) {
    return eval(dest, a.getint3(), b.getint3());
  }
  
// 4d nsl int

  public static int[][][][] eval(NslInt4 a, int b) {
    return eval(a.getint4(), b);
  }
  
  public static int[][][][] eval(int a, NslInt4 b) {
    return eval(a, b.getint4());
  }

  public static int[][][][] eval(int[][][][] dest, NslInt4 a, int b) {
    return eval(dest, a.getint4(), b);
  }
  
  public static int[][][][] eval(int[][][][] dest, int a, NslInt4 b) {
    return eval(dest, a, b.getint4());
  }

  public static int[][][][] eval(int[][][][] a, NslInt0 b) {
    return eval(a, b.getint());
  }
  
  public static int[][][][] eval(NslInt0 a, int[][][][] b) {
    return eval(a.getint(), b);
  }

  public static int[][][][] eval(int[][][][] dest, int[][][][] a, NslInt0 b) {
    return eval(dest, a, b.getint());
  }

  public static int[][][][] eval(int[][][][] dest, NslInt0 a, int[][][][] b) {
    return eval(dest, a.getint(), b);
  }
  
  public static int[][][][] eval(NslInt4 a, NslInt0 b) {
    return eval(a.getint4(), b.getint());
  }
  
  public static int[][][][] eval(NslInt0 a, NslInt4 b) {
    return eval(a.getint(), b.getint4());
  }

  public static int[][][][] eval(int[][][][] dest, NslInt4 a, NslInt0 b) {
    return eval(dest,a.getint4(),b.getint());
  }

  public static int[][][][] eval(int[][][][] dest, NslInt0 a, NslInt4 b) {
    return eval(dest, a.getint(), b.getint4());
  }

  public static int[][][][] eval(NslInt4 a, NslInt4 b) {
    return eval(a.getint4(), b.getint4());
  }

  public static int[][][][] eval(int[][][][] dest, NslInt4 a, NslInt4 b) {
    return eval(dest, a.getint4(), b.getint4());
  }


// 0d native floats
  
  public static float eval(float a, float b) {
    return a*b;
  }
  
// 1d native floats

  public static float[] eval(float[] a, float b) {
    float[] dest = new float[a.length];
    return eval(dest, a, b);
  }

  public static float[] eval(float[] dest, float[] a, float b) {
    int i;
    int len = dest.length;
    if (len != a.length ) {
        len = a.length;
        dest = new float[len];
    }
    for (i=0; i<len; i++)
        dest[i] = a[i]*b;
    return dest;
  }

  public static float[] eval(float a, float[] b) {
    float[] dest = new float[b.length];
    return eval(dest, a, b);
  }

  public static float[] eval(float[] dest, float a, float[] b) {
    int i;
    int len = dest.length;
    if (len != b.length ) {
        len = b.length;
        dest = new float[len];
    }
    for (i=0; i<len; i++)
        dest[i] = a*b[i];
    return dest;
  }

  public static float[] eval(float[] a, float[] b) {
    float[] dest = new float[a.length];
    return eval(dest, a, b);
  }


  public static float[] eval(float[] dest, float[] a, float[] b) {
    
    if (a.length != b.length) {
        System.out.println("NslSub:float[] eval(float[] a, float[] b): Array size inconsistent");
        return dest;
    }
    
    int i;
    int len = dest.length;
    if (len != a.length || len != b.length) {
        len  = a.length;
        dest = new float[len];
    } 
    for (i=0; i<len; i++) {
        dest[i] = a[i]*b[i];
    }
    return dest;
  }

// 2d native floats

  public static float[][] eval(float[][] a, float b) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a, b);
  }

  public static float[][] eval(float[][] dest, float[][] a, float b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != a.length || len2 !=a[0].length) {
        len1 = a.length;
        len2 = a[0].length;
        dest = new float[len1][len2];
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a[i1][i2]*b;
    return dest;
  }

  public static float[][] eval(float a, float[][] b) {
    float[][] dest = new float[b.length][b[0].length];
    return eval(dest, a, b);
  }

  public static float[][] eval(float[][] dest, float a, float[][] b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != b.length || len2 !=b[0].length) {
        len1 = b.length;
        len2 = b[0].length;
        dest = new float[len1][len2];
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a*b[i1][i2];
    return dest;
  }

  public static float[][] eval(float[][] a, float[][] b) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a, b);
  }
  
  public static float[][] eval(float[][] dest, float[][] a, float[][] b) {
    
    if (a.length != b.length || a[0].length != b[0].length) {
        System.out.println("NslSub:float[][] eval(float[][] a, float[][] b): Array size inconsistent");
        return dest;
    }
    
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if (len1 != a.length || len2 != a[0].length) {
        len1 = a.length;
        len2 = a[0].length;
        dest = new float[len1][len2];
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = a[i1][i2]*b[i1][i2];
    return dest;
  }

// 3d native by Karan KARAN

  public static float[][][] eval(float[][][] a, float b) {
    float[][][] dest = new float[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }

  public static float[][][] eval(float[][][] dest, float[][][] a, float b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if(len1 != a.length || len2 !=a[0].length || len3 !=a[0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	dest = new float[len1][len2][len3]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3]=a[i1][i2][i3]*b;
    return dest;
  }

  public static float[][][] eval(float a, float[][][] b) {
    float[][][] dest = new float[b.length][b[0].length][b[0][0].length];
    return eval(dest, a, b);
  }

  public static float[][][] eval(float[][][] dest, float a, float[][][] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if(len1 != b.length || len2 !=b[0].length || len3 !=b[0][0].length) {
        len1 = b.length;
    	len2 = b[0].length;
    	len3 = b[0][0].length;
    	dest = new float[len1][len2][len3]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3]=a*b[i1][i2][i3];
    return dest;
  }

  public static float[][][] eval(float[][][] a, float[][][] b) {
    float[][][] dest = new float[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }
  
  public static float[][][] eval(float[][][] dest, float[][][] a, float[][][] b) {
    if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length ) {
        System.out.println("NslSub:float[][][] eval(float[][][] a, float[][][] b): Array size inconsistent");
        return dest;
    }
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length ||
        len1 != b.length || len2 != b[0].length || len3 != b[0][0].length ) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	dest = new float[len1][len2][len3]; 	
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3] = a[i1][i2][i3]*b[i1][i2][i3];
            	
    return dest;
  }

// 4d native floats

  public static float[][][][] eval(float[][][][] a, float b) {
    float[][][][] dest = new float[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, b);
  }

  public static float[][][][] eval(float[][][][] dest, float[][][][] a, float b) {
   int i1, i2, i3, i4;
   int len1 = dest.length;
   int len2 = dest[0].length;
   int len3 = dest[0][0].length;
   int len4 = dest[0][0][0].length;   
   if(len1 != a.length || len2 !=a[0].length || len3 !=a[0][0].length || len4 !=a[0][0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	len4 = a[0][0][0].length;
    	dest = new float[len1][len2][len3][len4]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	for(i4=0; i4<len4; i4++)
            	    dest[i1][i2][i3][i4]=a[i1][i2][i3][i4]*b;
    return dest;
  }

  public static float[][][][] eval(float a, float[][][][] b) {
    float[][][][] dest = new float[b.length][b[0].length][b[0][0].length][b[0][0][0].length];
    return eval(dest, a, b);
  }

  public static float[][][][] eval(float[][][][] dest, float a, float[][][][] b) {
   int i1, i2, i3, i4;
   int len1 = dest.length;
   int len2 = dest[0].length;
   int len3 = dest[0][0].length;
   int len4 = dest[0][0][0].length;   
   if(len1 != b.length || len2 !=b[0].length || len3 !=b[0][0].length || len4 !=b[0][0][0].length) {
        len1 = b.length;
    	len2 = b[0].length;
    	len3 = b[0][0].length;
    	len4 = b[0][0][0].length;
    	dest = new float[len1][len2][len3][len4]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	for(i4=0; i4<len4; i4++)
            	    dest[i1][i2][i3][i4]=a*b[i1][i2][i3][i4];
    return dest;
  }

  public static float[][][][] eval(float[][][][] a, float[][][][] b) {
    float[][][][] dest = new float[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, b);
  }
  
  public static float[][][][] eval(float[][][][] dest, float[][][][] a, float[][][][] b) {
    if (a.length != b.length || a[0].length != b[0].length 
        || a[0][0].length != b[0][0].length || a[0][0][0].length != b[0][0][0].length) {
        System.out.println("NslSub:float[][][][] eval(float[][][][] a, float[][][][] b): Array size inconsistent");
        return dest;
    }
    int i1, i2, i3, i4;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int len4 = dest[0][0][0].length;   
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length || len4 !=a[0][0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	len4 = a[0][0][0].length;
    	dest = new float[len1][len2][len3][len4]; 	
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
             	for(i4=0; i4<len4; i4++)
           	    dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]*b[i1][i2][i3][i4];
            	
    return dest;
  }

//0d nsl float by Weifang

  public static float eval(NslFloat0 a, float b) {
    return a.getfloat()*b;
  }

  public static float eval(float a, NslFloat0 b) {
    return a*b.getfloat();
  }

  public static float eval(NslFloat0 a, NslFloat0 b) {
    return a.getfloat()*b.getfloat();
  }

//1d nsl float by Weifang

  public static float[] eval(NslFloat1 a, float b) {
    return eval(a.getfloat1(), b);
  }
  
  public static float[] eval(float a, NslFloat1 b) {
    return eval(a, b.getfloat1());
  }

  public static float[] eval(float[] dest, NslFloat1 a, float b) {
    return eval(dest, a.getfloat1(), b);
  }
  
  public static float[] eval(float[] dest, float a, NslFloat1 b) {
    return eval(dest, a, b.getfloat1());
  }

  public static float[] eval(float[] a, NslFloat0 b) {
    return eval(a, b.getfloat());
  }
  
  public static float[] eval(NslFloat0 a, float[] b) {
    return eval(a.getfloat(), b);
  }

  public static float[] eval(float[] dest, float[] a, NslFloat0 b) {
    return eval(dest, a, b.getfloat());
  }

  public static float[] eval(float[] dest, NslFloat0 a, float[] b) {
    return eval(dest, a.getfloat(), b);
  }
  
  public static float[] eval(NslFloat1 a, NslFloat0 b) {
    return eval(a.getfloat1(), b.getfloat());
  }
  
  public static float[] eval(NslFloat0 a, NslFloat1 b) {
    return eval(a.getfloat(), b.getfloat1());
  }

  public static float[] eval(float[] dest, NslFloat1 a, NslFloat0 b) {
    return eval(dest,a.getfloat1(),b.getfloat());
  }

  public static float[] eval(float[] dest, NslFloat0 a, NslFloat1 b) {
    return eval(dest, a.getfloat(), b.getfloat1());
  }

  public static float[] eval(NslFloat1 a, NslFloat1 b) {
    return eval(a.getfloat1(), b.getfloat1());
  }

  public static float[] eval(float[] dest, NslFloat1 a, NslFloat1 b) {
    return eval(dest, a.getfloat1(), b.getfloat1());
  }
  
// 2d nsl int

  public static float[][] eval(NslFloat2 a, float b) {
    return eval(a.getfloat2(), b);
  }
  
  public static float[][] eval(float a, NslFloat2 b) {
    return eval(a, b.getfloat2());
  }

  public static float[][] eval(float[][] dest, NslFloat2 a, float b) {
    return eval(dest, a.getfloat2(), b);
  }
  
  public static float[][] eval(float[][] dest, float a, NslFloat2 b) {
    return eval(dest, a, b.getfloat2());
  }

  public static float[][] eval(float[][] a, NslFloat0 b) {
    return eval(a, b.getfloat());
  }
  
  public static float[][] eval(NslFloat0 a, float[][] b) {
    return eval(a.getfloat(), b);
  }

  public static float[][] eval(float[][] dest, float[][] a, NslFloat0 b) {
    return eval(dest, a, b.getfloat());
  }

  public static float[][] eval(float[][] dest, NslFloat0 a, float[][] b) {
    return eval(dest, a.getfloat(), b);
  }
  
  public static float[][] eval(NslFloat2 a, NslFloat0 b) {
    return eval(a.getfloat2(), b.getfloat());
  }
  
  public static float[][] eval(NslFloat0 a, NslFloat2 b) {
    return eval(a.getfloat(), b.getfloat2());
  }

  public static float[][] eval(float[][] dest, NslFloat2 a, NslFloat0 b) {
    return eval(dest,a.getfloat2(),b.getfloat());
  }

  public static float[][] eval(float[][] dest, NslFloat0 a, NslFloat2 b) {
    return eval(dest, a.getfloat(), b.getfloat2());
  }

  public static float[][] eval(NslFloat2 a, NslFloat2 b) {
    return eval(a.getfloat2(), b.getfloat2());
  }

  public static float[][] eval(float[][] dest, NslFloat2 a, NslFloat2 b) {
    return eval(dest, a.getfloat2(), b.getfloat2());
  }

// 3d nsl int

  public static float[][][] eval(NslFloat3 a, float b) {
    return eval(a.getfloat3(), b);
  }
  
  public static float[][][] eval(float a, NslFloat3 b) {
    return eval(a, b.getfloat3());
  }

  public static float[][][] eval(float[][][] dest, NslFloat3 a, float b) {
    return eval(dest, a.getfloat3(), b);
  }
  
  public static float[][][] eval(float[][][] dest, float a, NslFloat3 b) {
    return eval(dest, a, b.getfloat3());
  }

  public static float[][][] eval(float[][][] a, NslFloat0 b) {
    return eval(a, b.getfloat());
  }
  
  public static float[][][] eval(NslFloat0 a, float[][][] b) {
    return eval(a.getfloat(), b);
  }

  public static float[][][] eval(float[][][] dest, float[][][] a, NslFloat0 b) {
    return eval(dest, a, b.getfloat());
  }

  public static float[][][] eval(float[][][] dest, NslFloat0 a, float[][][] b) {
    return eval(dest, a.getfloat(), b);
  }
  
  public static float[][][] eval(NslFloat3 a, NslFloat0 b) {
    return eval(a.getfloat3(), b.getfloat());
  }
  
  public static float[][][] eval(NslFloat0 a, NslFloat3 b) {
    return eval(a.getfloat(), b.getfloat3());
  }

  public static float[][][] eval(float[][][] dest, NslFloat3 a, NslFloat0 b) {
    return eval(dest,a.getfloat3(),b.getfloat());
  }

  public static float[][][] eval(float[][][] dest, NslFloat0 a, NslFloat3 b) {
    return eval(dest, a.getfloat(), b.getfloat3());
  }

  public static float[][][] eval(NslFloat3 a, NslFloat3 b) {
    return eval(a.getfloat3(), b.getfloat3());
  }

  public static float[][][] eval(float[][][] dest, NslFloat3 a, NslFloat3 b) {
    return eval(dest, a.getfloat3(), b.getfloat3());
  }
  
// 4d nsl int

  public static float[][][][] eval(NslFloat4 a, float b) {
    return eval(a.getfloat4(), b);
  }
  
  public static float[][][][] eval(float a, NslFloat4 b) {
    return eval(a, b.getfloat4());
  }

  public static float[][][][] eval(float[][][][] dest, NslFloat4 a, float b) {
    return eval(dest, a.getfloat4(), b);
  }
  
  public static float[][][][] eval(float[][][][] dest, float a, NslFloat4 b) {
    return eval(dest, a, b.getfloat4());
  }

  public static float[][][][] eval(float[][][][] a, NslFloat0 b) {
    return eval(a, b.getfloat());
  }
  
  public static float[][][][] eval(NslFloat0 a, float[][][][] b) {
    return eval(a.getfloat(), b);
  }

  public static float[][][][] eval(float[][][][] dest, float[][][][] a, NslFloat0 b) {
    return eval(dest, a, b.getfloat());
  }

  public static float[][][][] eval(float[][][][] dest, NslFloat0 a, float[][][][] b) {
    return eval(dest, a.getfloat(), b);
  }
  
  public static float[][][][] eval(NslFloat4 a, NslFloat0 b) {
    return eval(a.getfloat4(), b.getfloat());
  }
  
  public static float[][][][] eval(NslFloat0 a, NslFloat4 b) {
    return eval(a.getfloat(), b.getfloat4());
  }

  public static float[][][][] eval(float[][][][] dest, NslFloat4 a, NslFloat0 b) {
    return eval(dest,a.getfloat4(),b.getfloat());
  }

  public static float[][][][] eval(float[][][][] dest, NslFloat0 a, NslFloat4 b) {
    return eval(dest, a.getfloat(), b.getfloat4());
  }

  public static float[][][][] eval(NslFloat4 a, NslFloat4 b) {
    return eval(a.getfloat4(), b.getfloat4());
  }

  public static float[][][][] eval(float[][][][] dest, NslFloat4 a, NslFloat4 b) {
    return eval(dest, a.getfloat4(), b.getfloat4());
  }


// 0d native doubles
  
  public static double eval(double a, double b) {
    return a*b;
  }
  
// 1d native doubles

  public static double[] eval(double[] a, double b) {
    double[] dest = new double[a.length];
    return eval(dest, a, b);
  }

  public static double[] eval(double[] dest, double[] a, double b) {
    int i;
    int len = dest.length;
    if (len != a.length ) {
        len = a.length;
        dest = new double[len];
    }
    for (i=0; i<len; i++)
        dest[i] = a[i]*b;
    return dest;
  }

  public static double[] eval(double a, double[] b) {
    double[] dest = new double[b.length];
    return eval(dest, a, b);
  }

  public static double[] eval(double[] dest, double a, double[] b) {
    int i;
    int len = dest.length;
    if (len != b.length ) {
        len = b.length;
        dest = new double[len];
    }
    for (i=0; i<len; i++)
        dest[i] = a*b[i];
    return dest;
  }

  public static double[] eval(double[] a, double[] b) {
    double[] dest = new double[a.length];
    return eval(dest, a, b);
  }


  public static double[] eval(double[] dest, double[] a, double[] b) {
    
    if (a.length != b.length) {
        System.out.println("NslSub:double[] eval(double[] a, double[] b): Array size inconsistent");
        return dest;
    }
    
    int i;
    int len = dest.length;
    if (len != a.length || len != b.length) {
        len  = a.length;
        dest = new double[len];
    } 
    for (i=0; i<len; i++) {
        dest[i] = a[i]*b[i];
    }
    return dest;
  }

// 2d native doubles

  public static double[][] eval(double[][] a, double b) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a, b);
  }

  public static double[][] eval(double[][] dest, double[][] a, double b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != a.length || len2 !=a[0].length) {
        len1 = a.length;
        len2 = a[0].length;
        dest = new double[len1][len2];
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a[i1][i2]*b;
    return dest;
  }

  public static double[][] eval(double a, double[][] b) {
    double[][] dest = new double[b.length][b[0].length];
    return eval(dest, a, b);
  }

  public static double[][] eval(double[][] dest, double a, double[][] b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != b.length || len2 !=b[0].length) {
        len1 = b.length;
        len2 = b[0].length;
        dest = new double[len1][len2];
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a*b[i1][i2];
    return dest;
  }

  public static double[][] eval(double[][] a, double[][] b) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a, b);
  }
  
  public static double[][] eval(double[][] dest, double[][] a, double[][] b) {
    
    if (a.length != b.length || a[0].length != b[0].length) {
        System.out.println("NslSub:double[][] eval(double[][] a, double[][] b): Array size inconsistent");
        return dest;
    }
    
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if (len1 != a.length || len2 != a[0].length) {
        len1 = a.length;
        len2 = a[0].length;
        dest = new double[len1][len2];
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = a[i1][i2]*b[i1][i2];
    return dest;
  }

// 3d native by Karan KARAN

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
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	dest = new double[len1][len2][len3]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3]=a[i1][i2][i3]*b;
    return dest;
  }

  public static double[][][] eval(double a, double[][][] b) {
    double[][][] dest = new double[b.length][b[0].length][b[0][0].length];
    return eval(dest, a, b);
  }

  public static double[][][] eval(double[][][] dest, double a, double[][][] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if(len1 != b.length || len2 !=b[0].length || len3 !=b[0][0].length) {
        len1 = b.length;
    	len2 = b[0].length;
    	len3 = b[0][0].length;
    	dest = new double[len1][len2][len3]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3]=a*b[i1][i2][i3];
    return dest;
  }

  public static double[][][] eval(double[][][] a, double[][][] b) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }
  
  public static double[][][] eval(double[][][] dest, double[][][] a, double[][][] b) {
    if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length ) {
        System.out.println("NslSub:double[][][] eval(double[][][] a, double[][][] b): Array size inconsistent");
        return dest;
    }
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length ||
        len1 != b.length || len2 != b[0].length || len3 != b[0][0].length ) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	dest = new double[len1][len2][len3]; 	
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3] = a[i1][i2][i3]*b[i1][i2][i3];
            	
    return dest;
  }

// 4d native doubles

  public static double[][][][] eval(double[][][][] a, double b) {
    double[][][][] dest = new double[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, b);
  }

  public static double[][][][] eval(double[][][][] dest, double[][][][] a, double b) {
   int i1, i2, i3, i4;
   int len1 = dest.length;
   int len2 = dest[0].length;
   int len3 = dest[0][0].length;
   int len4 = dest[0][0][0].length;   
   if(len1 != a.length || len2 !=a[0].length || len3 !=a[0][0].length || len4 !=a[0][0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	len4 = a[0][0][0].length;
    	dest = new double[len1][len2][len3][len4]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	for(i4=0; i4<len4; i4++)
            	    dest[i1][i2][i3][i4]=a[i1][i2][i3][i4]*b;
    return dest;
  }

  public static double[][][][] eval(double a, double[][][][] b) {
    double[][][][] dest = new double[b.length][b[0].length][b[0][0].length][b[0][0][0].length];
    return eval(dest, a, b);
  }

  public static double[][][][] eval(double[][][][] dest, double a, double[][][][] b) {
   int i1, i2, i3, i4;
   int len1 = dest.length;
   int len2 = dest[0].length;
   int len3 = dest[0][0].length;
   int len4 = dest[0][0][0].length;   
   if(len1 != b.length || len2 !=b[0].length || len3 !=b[0][0].length || len4 !=b[0][0][0].length) {
        len1 = b.length;
    	len2 = b[0].length;
    	len3 = b[0][0].length;
    	len4 = b[0][0][0].length;
    	dest = new double[len1][len2][len3][len4]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	for(i4=0; i4<len4; i4++)
            	    dest[i1][i2][i3][i4]=a*b[i1][i2][i3][i4];
    return dest;
  }

  public static double[][][][] eval(double[][][][] a, double[][][][] b) {
    double[][][][] dest = new double[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, b);
  }
  
  public static double[][][][] eval(double[][][][] dest, double[][][][] a, double[][][][] b) {
    if (a.length != b.length || a[0].length != b[0].length 
        || a[0][0].length != b[0][0].length || a[0][0][0].length != b[0][0][0].length) {
        System.out.println("NslSub:double[][][][] eval(double[][][][] a, double[][][][] b): Array size inconsistent");
        return dest;
    }
    int i1, i2, i3, i4;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int len4 = dest[0][0][0].length;   
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length || len4 !=a[0][0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	len4 = a[0][0][0].length;
    	dest = new double[len1][len2][len3][len4]; 	
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
             	for(i4=0; i4<len4; i4++)
           	    dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]*b[i1][i2][i3][i4];
            	
    return dest;
  }

//0d nsl double by Weifang

  public static double eval(NslDouble0 a, double b) {
    return a.getdouble()*b;
  }

  public static double eval(double a, NslDouble0 b) {
    return a*b.getdouble();
  }

  public static double eval(NslDouble0 a, NslDouble0 b) {
    return a.getdouble()*b.getdouble();
  }

//1d nsl double by Weifang

  public static double[] eval(NslDouble1 a, double b) {
    return eval(a.getdouble1(), b);
  }
  
  public static double[] eval(double a, NslDouble1 b) {
    return eval(a, b.getdouble1());
  }

  public static double[] eval(double[] dest, NslDouble1 a, double b) {
    return eval(dest, a.getdouble1(), b);
  }
  
  public static double[] eval(double[] dest, double a, NslDouble1 b) {
    return eval(dest, a, b.getdouble1());
  }

  public static double[] eval(double[] a, NslDouble0 b) {
    return eval(a, b.getdouble());
  }
  
  public static double[] eval(NslDouble0 a, double[] b) {
    return eval(a.getdouble(), b);
  }

  public static double[] eval(double[] dest, double[] a, NslDouble0 b) {
    return eval(dest, a, b.getdouble());
  }

  public static double[] eval(double[] dest, NslDouble0 a, double[] b) {
    return eval(dest, a.getdouble(), b);
  }
  
  public static double[] eval(NslDouble1 a, NslDouble0 b) {
    return eval(a.getdouble1(), b.getdouble());
  }
  
  public static double[] eval(NslDouble0 a, NslDouble1 b) {
    return eval(a.getdouble(), b.getdouble1());
  }

  public static double[] eval(double[] dest, NslDouble1 a, NslDouble0 b) {
    return eval(dest,a.getdouble1(),b.getdouble());
  }

  public static double[] eval(double[] dest, NslDouble0 a, NslDouble1 b) {
    return eval(dest, a.getdouble(), b.getdouble1());
  }

  public static double[] eval(NslDouble1 a, NslDouble1 b) {
    return eval(a.getdouble1(), b.getdouble1());
  }

  public static double[] eval(double[] dest, NslDouble1 a, NslDouble1 b) {
    return eval(dest, a.getdouble1(), b.getdouble1());
  }
  
// 2d nsl int

  public static double[][] eval(NslDouble2 a, double b) {
    return eval(a.getdouble2(), b);
  }
  
  public static double[][] eval(double a, NslDouble2 b) {
    return eval(a, b.getdouble2());
  }

  public static double[][] eval(double[][] dest, NslDouble2 a, double b) {
    return eval(dest, a.getdouble2(), b);
  }
  
  public static double[][] eval(double[][] dest, double a, NslDouble2 b) {
    return eval(dest, a, b.getdouble2());
  }

  public static double[][] eval(double[][] a, NslDouble0 b) {
    return eval(a, b.getdouble());
  }
  
  public static double[][] eval(NslDouble0 a, double[][] b) {
    return eval(a.getdouble(), b);
  }

  public static double[][] eval(double[][] dest, double[][] a, NslDouble0 b) {
    return eval(dest, a, b.getdouble());
  }

  public static double[][] eval(double[][] dest, NslDouble0 a, double[][] b) {
    return eval(dest, a.getdouble(), b);
  }
  
  public static double[][] eval(NslDouble2 a, NslDouble0 b) {
    return eval(a.getdouble2(), b.getdouble());
  }
  
  public static double[][] eval(NslDouble0 a, NslDouble2 b) {
    return eval(a.getdouble(), b.getdouble2());
  }

  public static double[][] eval(double[][] dest, NslDouble2 a, NslDouble0 b) {
    return eval(dest,a.getdouble2(),b.getdouble());
  }

  public static double[][] eval(double[][] dest, NslDouble0 a, NslDouble2 b) {
    return eval(dest, a.getdouble(), b.getdouble2());
  }

  public static double[][] eval(NslDouble2 a, NslDouble2 b) {
    return eval(a.getdouble2(), b.getdouble2());
  }

  public static double[][] eval(double[][] dest, NslDouble2 a, NslDouble2 b) {
    return eval(dest, a.getdouble2(), b.getdouble2());
  }

// 3d nsl int

  public static double[][][] eval(NslDouble3 a, double b) {
    return eval(a.getdouble3(), b);
  }
  
  public static double[][][] eval(double a, NslDouble3 b) {
    return eval(a, b.getdouble3());
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, double b) {
    return eval(dest, a.getdouble3(), b);
  }
  
  public static double[][][] eval(double[][][] dest, double a, NslDouble3 b) {
    return eval(dest, a, b.getdouble3());
  }

  public static double[][][] eval(double[][][] a, NslDouble0 b) {
    return eval(a, b.getdouble());
  }
  
  public static double[][][] eval(NslDouble0 a, double[][][] b) {
    return eval(a.getdouble(), b);
  }

  public static double[][][] eval(double[][][] dest, double[][][] a, NslDouble0 b) {
    return eval(dest, a, b.getdouble());
  }

  public static double[][][] eval(double[][][] dest, NslDouble0 a, double[][][] b) {
    return eval(dest, a.getdouble(), b);
  }
  
  public static double[][][] eval(NslDouble3 a, NslDouble0 b) {
    return eval(a.getdouble3(), b.getdouble());
  }
  
  public static double[][][] eval(NslDouble0 a, NslDouble3 b) {
    return eval(a.getdouble(), b.getdouble3());
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, NslDouble0 b) {
    return eval(dest,a.getdouble3(),b.getdouble());
  }

  public static double[][][] eval(double[][][] dest, NslDouble0 a, NslDouble3 b) {
    return eval(dest, a.getdouble(), b.getdouble3());
  }

  public static double[][][] eval(NslDouble3 a, NslDouble3 b) {
    return eval(a.getdouble3(), b.getdouble3());
  }

  public static double[][][] eval(double[][][] dest, NslDouble3 a, NslDouble3 b) {
    return eval(dest, a.getdouble3(), b.getdouble3());
  }
  
// 4d nsl int

  public static double[][][][] eval(NslDouble4 a, double b) {
    return eval(a.getdouble4(), b);
  }
  
  public static double[][][][] eval(double a, NslDouble4 b) {
    return eval(a, b.getdouble4());
  }

  public static double[][][][] eval(double[][][][] dest, NslDouble4 a, double b) {
    return eval(dest, a.getdouble4(), b);
  }
  
  public static double[][][][] eval(double[][][][] dest, double a, NslDouble4 b) {
    return eval(dest, a, b.getdouble4());
  }

  public static double[][][][] eval(double[][][][] a, NslDouble0 b) {
    return eval(a, b.getdouble());
  }
  
  public static double[][][][] eval(NslDouble0 a, double[][][][] b) {
    return eval(a.getdouble(), b);
  }

  public static double[][][][] eval(double[][][][] dest, double[][][][] a, NslDouble0 b) {
    return eval(dest, a, b.getdouble());
  }

  public static double[][][][] eval(double[][][][] dest, NslDouble0 a, double[][][][] b) {
    return eval(dest, a.getdouble(), b);
  }
  
  public static double[][][][] eval(NslDouble4 a, NslDouble0 b) {
    return eval(a.getdouble4(), b.getdouble());
  }
  
  public static double[][][][] eval(NslDouble0 a, NslDouble4 b) {
    return eval(a.getdouble(), b.getdouble4());
  }

  public static double[][][][] eval(double[][][][] dest, NslDouble4 a, NslDouble0 b) {
    return eval(dest,a.getdouble4(),b.getdouble());
  }

  public static double[][][][] eval(double[][][][] dest, NslDouble0 a, NslDouble4 b) {
    return eval(dest, a.getdouble(), b.getdouble4());
  }

  public static double[][][][] eval(NslDouble4 a, NslDouble4 b) {
    return eval(a.getdouble4(), b.getdouble4());
  }

  public static double[][][][] eval(double[][][][] dest, NslDouble4 a, NslDouble4 b) {
    return eval(dest, a.getdouble4(), b.getdouble4());
  }
  
// 0d native booleans
  
  public static boolean eval(boolean a, boolean b) {
    return a && b;
  }
  
// 1d native booleans

  public static boolean[] eval(boolean[] a, boolean b) {
    boolean[] dest = new boolean[a.length];
    return eval(dest, a, b);
  }

  public static boolean[] eval(boolean[] dest, boolean[] a, boolean b) {
    int i;
    int len = dest.length;
    if (len != a.length ) {
        len = a.length;
        dest = new boolean[len];
    }
    for (i=0; i<len; i++)
        dest[i] = a[i] && b;
    return dest;
  }

  public static boolean[] eval(boolean a, boolean[] b) {
    boolean[] dest = new boolean[b.length];
    return eval(dest, a, b);
  }

  public static boolean[] eval(boolean[] dest, boolean a, boolean[] b) {
    int i;
    int len = dest.length;
    if (len != b.length ) {
        len = b.length;
        dest = new boolean[len];
    }
    for (i=0; i<len; i++)
        dest[i] = a && b[i];
    return dest;
  }

  public static boolean[] eval(boolean[] a, boolean[] b) {
    boolean[] dest = new boolean[a.length];
    return eval(dest, a, b);
  }


  public static boolean[] eval(boolean[] dest, boolean[] a, boolean[] b) {
    
    if (a.length != b.length) {
        System.out.println("NslSub:boolean[] eval(boolean[] a, boolean[] b): Array size inconsistent");
        return dest;
    }
    
    int i;
    int len = dest.length;
    if (len != a.length || len != b.length) {
        len  = a.length;
        dest = new boolean[len];
    } 
    for (i=0; i<len; i++) {
        dest[i] = a[i] && b[i];
    }
    return dest;
  }

// 2d native booleans

  public static boolean[][] eval(boolean[][] a, boolean b) {
    boolean[][] dest = new boolean[a.length][a[0].length];
    return eval(dest, a, b);
  }

  public static boolean[][] eval(boolean[][] dest, boolean[][] a, boolean b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != a.length || len2 !=a[0].length) {
        len1 = a.length;
        len2 = a[0].length;
        dest = new boolean[len1][len2];
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a[i1][i2] && b;
    return dest;
  }

  public static boolean[][] eval(boolean a, boolean[][] b) {
    boolean[][] dest = new boolean[b.length][b[0].length];
    return eval(dest, a, b);
  }

  public static boolean[][] eval(boolean[][] dest, boolean a, boolean[][] b) {
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if(len1 != b.length || len2 !=b[0].length) {
        len1 = b.length;
        len2 = b[0].length;
        dest = new boolean[len1][len2];
    }

    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2]=a && b[i1][i2];
    return dest;
  }

  public static boolean[][] eval(boolean[][] a, boolean[][] b) {
    boolean[][] dest = new boolean[a.length][a[0].length];
    return eval(dest, a, b);
  }
  
  public static boolean[][] eval(boolean[][] dest, boolean[][] a, boolean[][] b) {
    
    if (a.length != b.length || a[0].length != b[0].length) {
        System.out.println("NslSub:boolean[][] eval(boolean[][] a, boolean[][] b): Array size inconsistent");
        return dest;
    }
    
    int i1, i2;
    int len1 = dest.length;
    int len2 = dest[0].length;

    if (len1 != a.length || len2 != a[0].length) {
        len1 = a.length;
        len2 = a[0].length;
        dest = new boolean[len1][len2];
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest[i1][i2] = a[i1][i2] && b[i1][i2];
    return dest;
  }

// 3d native

  public static boolean[][][] eval(boolean[][][] a, boolean b) {
    boolean[][][] dest = new boolean[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }

  public static boolean[][][] eval(boolean[][][] dest, boolean[][][] a, boolean b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if(len1 != a.length || len2 !=a[0].length || len3 !=a[0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	dest = new boolean[len1][len2][len3]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3]=a[i1][i2][i3] && b;
    return dest;
  }

  public static boolean[][][] eval(boolean a, boolean[][][] b) {
    boolean[][][] dest = new boolean[b.length][b[0].length][b[0][0].length];
    return eval(dest, a, b);
  }

  public static boolean[][][] eval(boolean[][][] dest, boolean a, boolean[][][] b) {
    int i1, i2,i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if(len1 != b.length || len2 !=b[0].length || len3 !=b[0][0].length) {
        len1 = b.length;
    	len2 = b[0].length;
    	len3 = b[0][0].length;
    	dest = new boolean[len1][len2][len3]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3]=a && b[i1][i2][i3];
    return dest;
  }

  public static boolean[][][] eval(boolean[][][] a, boolean[][][] b) {
    boolean[][][] dest = new boolean[a.length][a[0].length][a[0][0].length];
    return eval(dest, a, b);
  }
  
  public static boolean[][][] eval(boolean[][][] dest, boolean[][][] a, boolean[][][] b) {
    if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length ) {
        System.out.println("NslSub:boolean[][][] eval(boolean[][][] a, boolean[][][] b): Array size inconsistent");
        return dest;
    }
    int i1, i2, i3;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length ||
        len1 != b.length || len2 != b[0].length || len3 != b[0][0].length ) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	dest = new boolean[len1][len2][len3]; 	
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest[i1][i2][i3] = a[i1][i2][i3] && b[i1][i2][i3];
            	
    return dest;
  }

// 4d native booleans

  public static boolean[][][][] eval(boolean[][][][] a, boolean b) {
    boolean[][][][] dest = new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, b);
  }

  public static boolean[][][][] eval(boolean[][][][] dest, boolean[][][][] a, boolean b) {
   int i1, i2, i3, i4;
   int len1 = dest.length;
   int len2 = dest[0].length;
   int len3 = dest[0][0].length;
   int len4 = dest[0][0][0].length;   
   if(len1 != a.length || len2 !=a[0].length || len3 !=a[0][0].length || len4 !=a[0][0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	len4 = a[0][0][0].length;
    	dest = new boolean[len1][len2][len3][len4]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	for(i4=0; i4<len4; i4++)
            	    dest[i1][i2][i3][i4]=a[i1][i2][i3][i4] && b;
    return dest;
  }

  public static boolean[][][][] eval(boolean a, boolean[][][][] b) {
    boolean[][][][] dest = new boolean[b.length][b[0].length][b[0][0].length][b[0][0][0].length];
    return eval(dest, a, b);
  }

  public static boolean[][][][] eval(boolean[][][][] dest, boolean a, boolean[][][][] b) {
   int i1, i2, i3, i4;
   int len1 = dest.length;
   int len2 = dest[0].length;
   int len3 = dest[0][0].length;
   int len4 = dest[0][0][0].length;   
   if(len1 != b.length || len2 !=b[0].length || len3 !=b[0][0].length || len4 !=b[0][0][0].length) {
        len1 = b.length;
    	len2 = b[0].length;
    	len3 = b[0][0].length;
    	len4 = b[0][0][0].length;
    	dest = new boolean[len1][len2][len3][len4]; 	
    }
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	for(i4=0; i4<len4; i4++)
            	    dest[i1][i2][i3][i4]=a && b[i1][i2][i3][i4];
    return dest;
  }

  public static boolean[][][][] eval(boolean[][][][] a, boolean[][][][] b) {
    boolean[][][][] dest = new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a, b);
  }
  
  public static boolean[][][][] eval(boolean[][][][] dest, boolean[][][][] a, boolean[][][][] b) {
    if (a.length != b.length || a[0].length != b[0].length 
        || a[0][0].length != b[0][0].length || a[0][0][0].length != b[0][0][0].length) {
        System.out.println("NslSub:boolean[][][][] eval(boolean[][][][] a, boolean[][][][] b): Array size inconsistent");
        return dest;
    }
    int i1, i2, i3, i4;
    int len1 = dest.length;
    int len2 = dest[0].length;
    int len3 = dest[0][0].length;
    int len4 = dest[0][0][0].length;   
    if (len1 != a.length || len2 != a[0].length || len3 != a[0][0].length || len4 !=a[0][0][0].length) {
        len1 = a.length;
    	len2 = a[0].length;
    	len3 = a[0][0].length;
    	len4 = a[0][0][0].length;
    	dest = new boolean[len1][len2][len3][len4]; 	
    }

    for (i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
             	for(i4=0; i4<len4; i4++)
           	    dest[i1][i2][i3][i4] = a[i1][i2][i3][i4] && b[i1][i2][i3][i4];
            	
    return dest;
  }

//0d nsl boolean 

  public static boolean eval(NslBoolean0 a, boolean b) {
    return a.getboolean() && b;
  }

  public static boolean eval(boolean a, NslBoolean0 b) {
    return a && b.getboolean();
  }

  public static boolean eval(NslBoolean0 a, NslBoolean0 b) {
    return a.getboolean() && b.getboolean();
  }

//1d nsl boolean 

  public static boolean[] eval(NslBoolean1 a, boolean b) {
    return eval(a.getboolean1(), b);
  }
  
  public static boolean[] eval(boolean a, NslBoolean1 b) {
    return eval(a, b.getboolean1());
  }

  public static boolean[] eval(boolean[] dest, NslBoolean1 a, boolean b) {
    return eval(dest, a.getboolean1(), b);
  }
  
  public static boolean[] eval(boolean[] dest, boolean a, NslBoolean1 b) {
    return eval(dest, a, b.getboolean1());
  }

  public static boolean[] eval(boolean[] a, NslBoolean0 b) {
    return eval(a, b.getboolean());
  }
  
  public static boolean[] eval(NslBoolean0 a, boolean[] b) {
    return eval(a.getboolean(), b);
  }

  public static boolean[] eval(boolean[] dest, boolean[] a, NslBoolean0 b) {
    return eval(dest, a, b.getboolean());
  }

  public static boolean[] eval(boolean[] dest, NslBoolean0 a, boolean[] b) {
    return eval(dest, a.getboolean(), b);
  }
  
  public static boolean[] eval(NslBoolean1 a, NslBoolean0 b) {
    return eval(a.getboolean1(), b.getboolean());
  }
  
  public static boolean[] eval(NslBoolean0 a, NslBoolean1 b) {
    return eval(a.getboolean(), b.getboolean1());
  }

  public static boolean[] eval(boolean[] dest, NslBoolean1 a, NslBoolean0 b) {
    return eval(dest,a.getboolean1(),b.getboolean());
  }

  public static boolean[] eval(boolean[] dest, NslBoolean0 a, NslBoolean1 b) {
    return eval(dest, a.getboolean(), b.getboolean1());
  }

  public static boolean[] eval(NslBoolean1 a, NslBoolean1 b) {
    return eval(a.getboolean1(), b.getboolean1());
  }

  public static boolean[] eval(boolean[] dest, NslBoolean1 a, NslBoolean1 b) {
    return eval(dest, a.getboolean1(), b.getboolean1());
  }
  
// 2d nsl 

  public static boolean[][] eval(NslBoolean2 a, boolean b) {
    return eval(a.getboolean2(), b);
  }
  
  public static boolean[][] eval(boolean a, NslBoolean2 b) {
    return eval(a, b.getboolean2());
  }

  public static boolean[][] eval(boolean[][] dest, NslBoolean2 a, boolean b) {
    return eval(dest, a.getboolean2(), b);
  }
  
  public static boolean[][] eval(boolean[][] dest, boolean a, NslBoolean2 b) {
    return eval(dest, a, b.getboolean2());
  }

  public static boolean[][] eval(boolean[][] a, NslBoolean0 b) {
    return eval(a, b.getboolean());
  }
  
  public static boolean[][] eval(NslBoolean0 a, boolean[][] b) {
    return eval(a.getboolean(), b);
  }

  public static boolean[][] eval(boolean[][] dest, boolean[][] a, NslBoolean0 b) {
    return eval(dest, a, b.getboolean());
  }

  public static boolean[][] eval(boolean[][] dest, NslBoolean0 a, boolean[][] b) {
    return eval(dest, a.getboolean(), b);
  }
  
  public static boolean[][] eval(NslBoolean2 a, NslBoolean0 b) {
    return eval(a.getboolean2(), b.getboolean());
  }
  
  public static boolean[][] eval(NslBoolean0 a, NslBoolean2 b) {
    return eval(a.getboolean(), b.getboolean2());
  }

  public static boolean[][] eval(boolean[][] dest, NslBoolean2 a, NslBoolean0 b) {
    return eval(dest,a.getboolean2(),b.getboolean());
  }

  public static boolean[][] eval(boolean[][] dest, NslBoolean0 a, NslBoolean2 b) {
    return eval(dest, a.getboolean(), b.getboolean2());
  }

  public static boolean[][] eval(NslBoolean2 a, NslBoolean2 b) {
    return eval(a.getboolean2(), b.getboolean2());
  }

  public static boolean[][] eval(boolean[][] dest, NslBoolean2 a, NslBoolean2 b) {
    return eval(dest, a.getboolean2(), b.getboolean2());
  }

// 3d nsl 

  public static boolean[][][] eval(NslBoolean3 a, boolean b) {
    return eval(a.getboolean3(), b);
  }
  
  public static boolean[][][] eval(boolean a, NslBoolean3 b) {
    return eval(a, b.getboolean3());
  }

  public static boolean[][][] eval(boolean[][][] dest, NslBoolean3 a, boolean b) {
    return eval(dest, a.getboolean3(), b);
  }
  
  public static boolean[][][] eval(boolean[][][] dest, boolean a, NslBoolean3 b) {
    return eval(dest, a, b.getboolean3());
  }

  public static boolean[][][] eval(boolean[][][] a, NslBoolean0 b) {
    return eval(a, b.getboolean());
  }
  
  public static boolean[][][] eval(NslBoolean0 a, boolean[][][] b) {
    return eval(a.getboolean(), b);
  }

  public static boolean[][][] eval(boolean[][][] dest, boolean[][][] a, NslBoolean0 b) {
    return eval(dest, a, b.getboolean());
  }

  public static boolean[][][] eval(boolean[][][] dest, NslBoolean0 a, boolean[][][] b) {
    return eval(dest, a.getboolean(), b);
  }
  
  public static boolean[][][] eval(NslBoolean3 a, NslBoolean0 b) {
    return eval(a.getboolean3(), b.getboolean());
  }
  
  public static boolean[][][] eval(NslBoolean0 a, NslBoolean3 b) {
    return eval(a.getboolean(), b.getboolean3());
  }

  public static boolean[][][] eval(boolean[][][] dest, NslBoolean3 a, NslBoolean0 b) {
    return eval(dest,a.getboolean3(),b.getboolean());
  }

  public static boolean[][][] eval(boolean[][][] dest, NslBoolean0 a, NslBoolean3 b) {
    return eval(dest, a.getboolean(), b.getboolean3());
  }

  public static boolean[][][] eval(NslBoolean3 a, NslBoolean3 b) {
    return eval(a.getboolean3(), b.getboolean3());
  }

  public static boolean[][][] eval(boolean[][][] dest, NslBoolean3 a, NslBoolean3 b) {
    return eval(dest, a.getboolean3(), b.getboolean3());
  }
  
// 4d nsl 

  public static boolean[][][][] eval(NslBoolean4 a, boolean b) {
    return eval(a.getboolean4(), b);
  }
  
  public static boolean[][][][] eval(boolean a, NslBoolean4 b) {
    return eval(a, b.getboolean4());
  }

  public static boolean[][][][] eval(boolean[][][][] dest, NslBoolean4 a, boolean b) {
    return eval(dest, a.getboolean4(), b);
  }
  
  public static boolean[][][][] eval(boolean[][][][] dest, boolean a, NslBoolean4 b) {
    return eval(dest, a, b.getboolean4());
  }

  public static boolean[][][][] eval(boolean[][][][] a, NslBoolean0 b) {
    return eval(a, b.getboolean());
  }
  
  public static boolean[][][][] eval(NslBoolean0 a, boolean[][][][] b) {
    return eval(a.getboolean(), b);
  }

  public static boolean[][][][] eval(boolean[][][][] dest, boolean[][][][] a, NslBoolean0 b) {
    return eval(dest, a, b.getboolean());
  }

  public static boolean[][][][] eval(boolean[][][][] dest, NslBoolean0 a, boolean[][][][] b) {
    return eval(dest, a.getboolean(), b);
  }
  
  public static boolean[][][][] eval(NslBoolean4 a, NslBoolean0 b) {
    return eval(a.getboolean4(), b.getboolean());
  }
  
  public static boolean[][][][] eval(NslBoolean0 a, NslBoolean4 b) {
    return eval(a.getboolean(), b.getboolean4());
  }

  public static boolean[][][][] eval(boolean[][][][] dest, NslBoolean4 a, NslBoolean0 b) {
    return eval(dest,a.getboolean4(),b.getboolean());
  }

  public static boolean[][][][] eval(boolean[][][][] dest, NslBoolean0 a, NslBoolean4 b) {
    return eval(dest, a.getboolean(), b.getboolean4());
  }

  public static boolean[][][][] eval(NslBoolean4 a, NslBoolean4 b) {
    return eval(a.getboolean4(), b.getboolean4());
  }

  public static boolean[][][][] eval(boolean[][][][] dest, NslBoolean4 a, NslBoolean4 b) {
    return eval(dest, a.getboolean4(), b.getboolean4());
  }



}






















