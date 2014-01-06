/*  SCCS - @(#)NslUnaryOperator.java	1.3 - 09/01/99 - 00:18:24 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Unary operator routines
//
//

package nslj.src.math;
import nslj.src.lang.*;

/**
 Unary operator routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a) -> c
 a is the parameter of the evaluation function to do
 operator(a) pointwise and the result is passed out as c
 2. eval(dest, a) -> c
 a is the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
*/

public abstract class NslUnaryOperator extends NslBase {

  public NslUnaryOperator() {
	super();
  }

  public NslUnaryOperator(String label, NslHierarchy parent) {
	super(label,parent);
  }

  public abstract int value(int a);
  public abstract float value(float a);
  public abstract double value(double a);

  public boolean value(boolean a) {
	return false;
  }

// 0d native ints
  
  public int eval(int a) {
	return value(a);
  }

// 1d native ints

  public int[] eval(int[] a) {
    int[] dest = new int[a.length];
    return eval(dest, a);
  }

  public int[] eval(int[] dest, int[] a) {
    int i;
    int len = dest.length;
    if (len != a.length) {
        len = a.length;
        dest = new int[len];
    }
    for (i=0; i<len; i++)
        dest[i] = value(a[i]);
    return dest;
  }

// 2d native ints

  public int[][] eval(int[][] a) {
    int[][] dest = new int[a.length][a[0].length];
    return eval(dest, a);
  }

  public int[][] eval(int[][] dest, int[][] a) {
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
            dest[i1][i2]=value(a[i1][i2]);
    return dest;
  }

// 3d native ints

  public int[][][] eval(int[][][] a) {
    int[][][] dest = new int[a.length][a[0].length][a[0][0].length];
    return eval(dest, a);
  }

  public int[][][] eval(int[][][] dest, int[][][] a) {
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
            	dest[i1][i2][i3]=value(a[i1][i2][i3]);
    return dest;
  }

// 4d native ints

  public int[][][][] eval(int[][][][] a) {
    int[][][][] dest = new int[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a);
  }

  public int[][][][] eval(int[][][][] dest, int[][][][] a) {
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
            	    dest[i1][i2][i3][i4]=value(a[i1][i2][i3][i4]);
    return dest;
  }


//0d nsl int 

  public int eval(NslInt0 a) {
    return eval(a.getint());
  }

//1d nsl int 

  public int[] eval(NslInt1 a) {
    return eval(a.getint1());
  }
  
  public int[] eval(int[] dest, NslInt1 a) {
    return eval(dest, a.getint1());
  }
    
// 2d nsl int

  public int[][] eval(NslInt2 a) {
    return eval(a.getint2());
  }
  
  public int[][] eval(int[][] dest, NslInt2 a) {
    return eval(dest, a.getint2());
  }

// 3d nsl int

  public int[][][] eval(NslInt3 a) {
    return eval(a.getint3());
  }
  
  public int[][][] eval(int[][][] dest, NslInt3 a) {
    return eval(dest, a.getint3());
  }
  
// 4d nsl int

  public int[][][][] eval(NslInt4 a) {
    return eval(a.getint4());
  }
  
  public int[][][][] eval(int[][][][] dest, NslInt4 a) {
    return eval(dest, a.getint4());
  }
  

// 0d native floats
  
  public float eval(float a) {
    return value(a);
  }
  
// 1d native floats

  public float[] eval(float[] a) {
    float[] dest = new float[a.length];
    return eval(dest, a);
  }

  public float[] eval(float[] dest, float[] a) {
    int i;
    int len = dest.length;
    if (len != a.length ) {
        len = a.length;
        dest = new float[len];
    }
    for (i=0; i<len; i++)
        dest[i] = value(a[i]);
    return dest;
  }

// 2d native floats

  public float[][] eval(float[][] a) {
    float[][] dest = new float[a.length][a[0].length];
    return eval(dest, a);
  }

  public float[][] eval(float[][] dest, float[][] a) {
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
            dest[i1][i2]=value(a[i1][i2]);
    return dest;
  }

// 3d native floats

  public float[][][] eval(float[][][] a) {
    float[][][] dest = new float[a.length][a[0].length][a[0][0].length];
    return eval(dest, a);
  }

  public float[][][] eval(float[][][] dest, float[][][] a) {
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
            	dest[i1][i2][i3]=value(a[i1][i2][i3]);
    return dest;
  }

// 4d native floats

  public float[][][][] eval(float[][][][] a) {
    float[][][][] dest = new float[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a);
  }

  public float[][][][] eval(float[][][][] dest, float[][][][] a) {
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
            	    dest[i1][i2][i3][i4]=value(a[i1][i2][i3][i4]);
    return dest;
  }

//0d nsl float 

  public float eval(NslFloat0 a) {
    return eval(a.getfloat());
  }

//1d nsl float 

  public float[] eval(NslFloat1 a) {
    return eval(a.getfloat1());
  }
  
  public float[] eval(float[] dest, NslFloat1 a) {
    return eval(dest, a.getfloat1());
  }
  
  
// 2d nsl float

  public float[][] eval(NslFloat2 a) {
    return eval(a.getfloat2());
  }
  
  public float[][] eval(float[][] dest, NslFloat2 a) {
    return eval(dest, a.getfloat2());
  }
  
// 3d nsl float

  public float[][][] eval(NslFloat3 a) {
    return eval(a.getfloat3());
  }

  public float[][][] eval(float[][][] dest, NslFloat3 a) {
    return eval(dest, a.getfloat3());
  }
  
// 4d nsl float

  public float[][][][] eval(NslFloat4 a) {
    return eval(a.getfloat4());
  }

  public float[][][][] eval(float[][][][] dest, NslFloat4 a) {
    return eval(dest, a.getfloat4());
  }

// 0d native doubles
  
  public double eval(double a) {
    return value(a);
  }
  
// 1d native doubles

  public double[] eval(double[] a) {
    double[] dest = new double[a.length];
    return eval(dest, a);
  }

  public double[] eval(double[] dest, double[] a) {
    int i;
    int len = dest.length;
    if (len != a.length ) {
        len = a.length;
        dest = new double[len];
    }
    for (i=0; i<len; i++)
        dest[i] = value(a[i]);
    return dest;
  }

// 2d native doubles

  public double[][] eval(double[][] a) {
    double[][] dest = new double[a.length][a[0].length];
    return eval(dest, a);
  }

  public double[][] eval(double[][] dest, double[][] a) {
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
            dest[i1][i2]=value(a[i1][i2]);
    return dest;
  }

// 3d native doubles

  public double[][][] eval(double[][][] a) {
    double[][][] dest = new double[a.length][a[0].length][a[0][0].length];
    return eval(dest, a);
  }

  public double[][][] eval(double[][][] dest, double[][][] a) {
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
            	dest[i1][i2][i3]=value(a[i1][i2][i3]);
    return dest;
  }

// 4d native doubles

  public double[][][][] eval(double[][][][] a) {
    double[][][][] dest = new double[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a);
  }

  public double[][][][] eval(double[][][][] dest, double[][][][] a) {
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
            	    dest[i1][i2][i3][i4]=value(a[i1][i2][i3][i4]);
    return dest;
  }

//0d nsl double 

  public double eval(NslDouble0 a) {
    return eval(a.getdouble());
  }

//1d nsl double 

  public double[] eval(NslDouble1 a) {
    return eval(a.getdouble1());
  }
  
  public double[] eval(double[] dest, NslDouble1 a) {
    return eval(dest, a.getdouble1());
  }  
  
// 2d nsl double

  public double[][] eval(NslDouble2 a) {
    return eval(a.getdouble2());
  }
  
  public double[][] eval(double[][] dest, NslDouble2 a) {
    return eval(dest, a.getdouble2());
  }

// 3d nsl double

  public double[][][] eval(NslDouble3 a) {
    return eval(a.getdouble3());
  }
  
  public double[][][] eval(double[][][] dest, NslDouble3 a) {
    return eval(dest, a.getdouble3());
  }

// 4d nsl double

  public double[][][][] eval(NslDouble4 a) {
    return eval(a.getdouble4());
  }
  
  public double[][][][] eval(double[][][][] dest, NslDouble4 a) {
    return eval(dest, a.getdouble4());
  }

// 0d native booleans
  
  public boolean eval(boolean a) {
    return value(a);
  }
  
// 1d native booleans

  public boolean[] eval(boolean[] a) {
    boolean[] dest = new boolean[a.length];
    return eval(dest, a);
  }

  public boolean[] eval(boolean[] dest, boolean[] a) {
    int i;
    int len = dest.length;
    if (len != a.length ) {
        len = a.length;
        dest = new boolean[len];
    }
    for (i=0; i<len; i++)
        dest[i] = value(a[i]);
    return dest;
  }

// 2d native booleans

  public boolean[][] eval(boolean[][] a) {
    boolean[][] dest = new boolean[a.length][a[0].length];
    return eval(dest, a);
  }

  public boolean[][] eval(boolean[][] dest, boolean[][] a) {
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
            dest[i1][i2]=value(a[i1][i2]);
    return dest;
  }

// 3d native booleans

  public boolean[][][] eval(boolean[][][] a) {
    boolean[][][] dest = new boolean[a.length][a[0].length][a[0][0].length];
    return eval(dest, a);
  }

  public boolean[][][] eval(boolean[][][] dest, boolean[][][] a) {
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
            	dest[i1][i2][i3]=value(a[i1][i2][i3]);
    return dest;
  }

// 4d native booleans

  public boolean[][][][] eval(boolean[][][][] a) {
    boolean[][][][] dest = new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a);
  }

  public boolean[][][][] eval(boolean[][][][] dest, boolean[][][][] a) {
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
            	    dest[i1][i2][i3][i4]=value(a[i1][i2][i3][i4]);
    return dest;
  }

//0d nsl boolean 

  public boolean eval(NslBoolean0 a) {
    return eval(a.getboolean());
  }

//1d nsl boolean 

  public boolean[] eval(NslBoolean1 a) {
    return eval(a.getboolean1());
  }
  
  public boolean[] eval(boolean[] dest, NslBoolean1 a) {
    return eval(dest, a.getboolean1());
  }  
  
// 2d nsl boolean

  public boolean[][] eval(NslBoolean2 a) {
    return eval(a.getboolean2());
  }
  
  public boolean[][] eval(boolean[][] dest, NslBoolean2 a) {
    return eval(dest, a.getboolean2());
  }

// 3d nsl boolean

  public boolean[][][] eval(NslBoolean3 a) {
    return eval(a.getboolean3());
  }
  
  public boolean[][][] eval(boolean[][][] dest, NslBoolean3 a) {
    return eval(dest, a.getboolean3());
  }

// 4d nsl boolean

  public boolean[][][][] eval(NslBoolean4 a) {
    return eval(a.getboolean4());
  }
  
  public boolean[][][][] eval(boolean[][][][] dest, NslBoolean4 a) {
    return eval(dest, a.getboolean4());
  }
}






















