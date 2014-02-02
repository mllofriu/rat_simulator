/*  SCCS @(#)NslGetSector.java	1.8 --- 09/01/99 --U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// GetSector routines
//
//

package nslj.src.math;


/**
* Create an array that captures element <tt>start</tt> to <tt>end</tt>
*      if <tt>start</tt> is smaller than 0, <tt>start</tt> is default as 0;
*      if <tt>end</tt> is greater than the length of the array,
*         <tt>end</tt> is default as the length of the array
*	@param start - the element number start the capture
*	@param end - the element number ends the capture
*	@_data - input matrix
* @return a section of the original array
*/

//import java.util.Arrays;

import nslj.src.lang.*;

public final class NslGetSector {

//---------------------------------------------------------
// NslNumerics
   
   
//NslDouble1

public static double[] eval(double[] dest, NslDouble1 num, int start, int end) {

    double[] _data = num.get();

    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
      
    return eval(dest, _data, start, end);
}

//2
public static double[] eval(NslDouble1 num, int start, int end) {

    double[] _data = num.get();
    
    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
      
    int length = end-start+1;
    
    return eval(new double[length], _data , start, end);
}

//NslDouble2

  public static double[][] eval(double[][] dest,  NslDouble2 num, int start1, int start2, int end1, int end2) {

    double[][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
      
    return eval(dest,_data,start1,start2,end1,end2);

}

//2

public static double[][] eval(NslDouble2 num, int start1, int start2, int end1, int end2) {
    
    double[][] _data= num.get();
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;

    return eval(new double[length1][length2], _data, start1, start2, end1, end2);
}

// NslDouble3

  public static double[][][] eval(double[][][] dest, NslDouble3 num, int start1, int start2, int start3, int end1, int end2, int end3) {

    double[][][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    
    return eval(dest,_data,start1,start2,start3,end1,end2,end3);
  }
  
  //2
  public static double[][][] eval(NslDouble3 num, int start1, int start2, int start3, int end1, int end2, int end3) {
    
    double[][][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;

    return eval(new double[length1][length2][length3],_data,start1,start2,start3,end1,end2,end3);
  }

  //NslDouble4
  public static double[][][][] eval(double[][][][] dest, NslDouble4 num,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {
    double[][][][] _data= num.get();
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    int length4 = end4-start4+1;

    return eval(dest,_data,start1, start2, start3, start4, end1, end2, end3, end4);
  }
  
  //2
  public static double[][][][] eval(NslDouble4 num,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {

    double[][][][] _data=num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    int length4 = end4-start4+1;

    return eval(new double[length1][length2][length3][length4],_data,start1, start2, start3, start4, end1, end2, end3, end4);
  }
   
//NslFloat1

public static float[] eval(float[] dest, NslFloat1 num, int start, int end) {

    float[] _data = num.get();

    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
      
    return eval(dest, _data, start, end);
}

//2
public static float[] eval(NslFloat1 num, int start, int end) {

    float[] _data = num.get();
    
    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
      
    int length = end-start+1;
    
    return eval(new float[length], _data , start, end);
}

//NslFloat2

  public static float[][] eval(float[][] dest,  NslFloat2 num, int start1, int start2, int end1, int end2) {

    float[][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
      
    return eval(dest,_data,start1,start2,end1,end2);

}

//2

public static float[][] eval(NslFloat2 num, int start1, int start2, int end1, int end2) {
    
    float[][] _data= num.get();
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;

    return eval(new float[length1][length2], _data, start1, start2, end1, end2);
}

// NslFloat3

  public static float[][][] eval(float[][][] dest, NslFloat3 num, int start1, int start2, int start3, int end1, int end2, int end3) {

    float[][][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    
    return eval(dest,_data,start1,start2,start3,end1,end2,end3);
  }
  
  //2
  public static float[][][] eval(NslFloat3 num, int start1, int start2, int start3, int end1, int end2, int end3) {
    
    float[][][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;

    return eval(new float[length1][length2][length3],_data,start1,start2,start3,end1,end2,end3);
  }

  //NslFloat4
  public static float[][][][] eval(float[][][][] dest, NslFloat4 num,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {
    float[][][][] _data= num.get();
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    int length4 = end4-start4+1;

    return eval(dest,_data,start1, start2, start3, start4, end1, end2, end3, end4);
  }
  
  //2
  public static float[][][][] eval(NslFloat4 num,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {

    float[][][][] _data=num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    int length4 = end4-start4+1;

    return eval(new float[length1][length2][length3][length4],_data,start1, start2, start3, start4, end1, end2, end3, end4);
  }

   
//NslBoolean1

public static boolean[] eval(boolean[] dest, NslBoolean1 num, int start, int end) {

    boolean[] _data = num.get();

    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
      
    return eval(dest, _data, start, end);
}

//2
public static boolean[] eval(NslBoolean1 num, int start, int end) {

    boolean[] _data = num.get();
    
    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
      
    int length = end-start+1;
    
    return eval(new boolean[length], _data , start, end);
}

//NslBoolean2

  public static boolean[][] eval(boolean[][] dest,  NslBoolean2 num, int start1, int start2, int end1, int end2) {

    boolean[][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
      
    return eval(dest,_data,start1,start2,end1,end2);

}

//2

public static boolean[][] eval(NslBoolean2 num, int start1, int start2, int end1, int end2) {
    
    boolean[][] _data= num.get();
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;

    return eval(new boolean[length1][length2], _data, start1, start2, end1, end2);
}

// NslBoolean3

  public static boolean[][][] eval(boolean[][][] dest, NslBoolean3 num, int start1, int start2, int start3, int end1, int end2, int end3) {

    boolean[][][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    
    return eval(dest,_data,start1,start2,start3,end1,end2,end3);
  }
  
  //2
  public static boolean[][][] eval(NslBoolean3 num, int start1, int start2, int start3, int end1, int end2, int end3) {
    
    boolean[][][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;

    return eval(new boolean[length1][length2][length3],_data,start1,start2,start3,end1,end2,end3);
  }

  //NslBoolean4
  public static boolean[][][][] eval(boolean[][][][] dest, NslBoolean4 num,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {
    boolean[][][][] _data= num.get();
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    int length4 = end4-start4+1;

    return eval(dest,_data,start1, start2, start3, start4, end1, end2, end3, end4);
  }
  
  //2
  public static boolean[][][][] eval(NslBoolean4 num,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {

    boolean[][][][] _data=num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    int length4 = end4-start4+1;

    return eval(new boolean[length1][length2][length3][length4],_data,start1, start2, start3, start4, end1, end2, end3, end4);
  }

   
//NslInt1

public static int[] eval(int[] dest, NslInt1 num, int start, int end) {

    int[] _data = num.get();

    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
      
    return eval(dest, _data, start, end);
}

//2
public static int[] eval(NslInt1 num, int start, int end) {

    int[] _data = num.get();
    
    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
      
    int length = end-start+1;
    
    return eval(new int[length], _data , start, end);
}

//NslInt2

  public static int[][] eval(int[][] dest,  NslInt2 num, int start1, int start2, int end1, int end2) {

    int[][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
      
    return eval(dest,_data,start1,start2,end1,end2);

}

//2

public static int[][] eval(NslInt2 num, int start1, int start2, int end1, int end2) {
    
    int[][] _data= num.get();
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;

    return eval(new int[length1][length2], _data, start1, start2, end1, end2);
}

// NslInt3

  public static int[][][] eval(int[][][] dest, NslInt3 num, int start1, int start2, int start3, int end1, int end2, int end3) {

    int[][][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    
    return eval(dest,_data,start1,start2,start3,end1,end2,end3);
  }
  
  //2
  public static int[][][] eval(NslInt3 num, int start1, int start2, int start3, int end1, int end2, int end3) {
    
    int[][][] _data= num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;

    return eval(new int[length1][length2][length3],_data,start1,start2,start3,end1,end2,end3);
  }

  //NslInt4
  public static int[][][][] eval(int[][][][] dest, NslInt4 num,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {
    int[][][][] _data= num.get();
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    int length4 = end4-start4+1;

    return eval(dest,_data,start1, start2, start3, start4, end1, end2, end3, end4);
  }
  
  //2
  public static int[][][][] eval(NslInt4 num,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {

    int[][][][] _data=num.get();

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;
    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;

    int length1 = end1-start1+1;
    int length2 = end2-start2+1;
    int length3 = end3-start3+1;
    int length4 = end4-start4+1;

    return eval(new int[length1][length2][length3][length4],_data,start1, start2, start3, start4, end1, end2, end3, end4);
  }



  //--------------------------------------------------------------
  //native 1d double
  /**
   * Create an array that captures element <tt>start</tt> to <tt>end</tt>
*      if <tt>start</tt> is smaller than 0, <tt>start</tt> is default as 0;
*      if <tt>end</tt> is greater than the length of the array,
*         <tt>end</tt> is default as the length of the array
*	@param start - the element number start the capture
*	@param end - the element number ends the capture
*	@_data - input matrix
   * @return a section of the original array
   */
//native 1d double
public static double[] eval(double[] dest, double[] _data,int start, int end) {
    int i;
    int j;
    int length;
    if (start < 0) start = 0;
    if (end > _data.length)
      end = _data.length;
    length = end-start+1;

    if (dest.length != length) {
    	dest = new double[length];
    }

    i = start;
    for (j=0; j<length; j++, i++) {
      dest[j]=_data[i];
    }
    return dest;
  }
//double[]
public static double[] eval( double[] _data,int start, int end) {
    int length;
    double[] dest;

    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
    length = end-start+1;
    dest = new double[length];
    return eval(dest, _data,start, end );

}

//native 2d double
public static double[][] eval(double[][] dest,double[][] _data,int start1, int start2, int end1, int end2) {

    int i1;
    int i2;
    int j1;
    int j2;

    int length1;
    int length2;

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    
    length1 = end1-start1+1;
    length2 = end2-start2+1;
    if (dest.length != length1 || dest[0].length != length2) {
    	dest = new double[length1][length2];
    }
    
    i1 = start1;
    i2 = start2;
    for (j1=0; j1<length1; j1++, i1++) {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) {
	  dest[j1][j2]=_data[i1][i2];
      }
    }
    return dest;
  }
//double[][]
public static double[][] eval(double[][] _data,int start1, int start2, int end1, int end2) {
    int length1;
    int length2;

    double[][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    length1 = end1-start1+1;
    length2 = end2-start2+1;
    
    dest = new double[length1][length2];
    return eval(dest,_data,start1,start2,end1,end2);
}

// native 3d double
  public static double[][][] eval(double[][][] dest, double[][][] _data, int start1, int start2, int start3, int end1, int end2, int end3) {
    int i1;
    int i2;
    int i3;
    int j1;
    int j2;
    int j3;
 
    int length1;
    int length2;
    int length3;
 
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    if (dest.length != length1 || dest[0].length != length2 || dest[0][0].length != length3) {
    	dest = new double[length1][length2][length3];
    }

    i1 = start1;
    i2 = start2;
    i3 = start3;

    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
	j3=0; i3=start3;
	for (j3=0; j3<length3; j3++, i3++)
	 { 
          dest[j1][j2][j3]=_data[i1][i2][i3];
         }   
       }
     }
    return dest;
  }

//double[][][]
public static double[][][] eval(double[][][] _data,int start1, int start2, int start3, int end1, int end2, int end3) {
    int length1;
    int length2;
    int length3;
 
    double[][][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    
    dest = new double[length1][length2][length3];
    return eval(dest,_data,start1,start2,start3,end1,end2,end3);
}
//native 4d double
public static double[][][][] eval(double[][][][] dest, double[][][][] _data,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {
    int i1;
    int i2;
    int i3;
    int i4;
    int j1;
    int j2;
    int j3;
    int j4;
    
    int length1;
    int length2;
    int length3;
    int length4;
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;


    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    length4 = end4-start4+1;
    if (dest.length != length1 || dest[0].length != length2 || dest[0][0].length != length3|| dest[0][0][0].length != length4) {
    	dest = new double[length1][length2][length3][length4];
    }
    
    i1 = start1;
    i2 = start2;
    i3 = start3;
    i4 = start4;
    
    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
	j3=0; i3=start3;
	for (j3=0; j3<length3; j3++, i3++)
	 { 
	  j4=0; i4=start4;
	  for (j4=0; j4<length4; j4++,i4++)
           {dest[j1][j2][j3][j4]=_data[i1][i2][i3][i4];}
         }   
       }
     }
    return dest;
  }

//double[][][][]
public static double[][][][] eval(double[][][][] _data,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4 ) {
    int length1;
    int length2;
    int length3;
    int length4;
    
    double[][][][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;


    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    length4 = end4-start4+1;

    dest = new double[length1][length2][length3][length4];
	return eval(dest, _data,start1, start2, start3, start3, end1, end2, end3, end4);
}
//----------------------------------------------------------
// floats
/**
* Create an array that captures element <tt>start</tt> to <tt>end</tt>
*      if <tt>start</tt> is smaller than 0, <tt>start</tt> is default as 0;
*      if <tt>end</tt> is greater than the length of the array,
*         <tt>end</tt> is default as the length of the array
*	@param start - the element number start the capture
*	@param end - the element number ends the capture
*	@_data - input matrix
* @return a section of the original array
*/
//native 1d float
public static float[] eval(float[] dest, float[] _data,int start, int end) {
    int i;
    int j;
    int length;
    if (start < 0) start = 0;
    if (end > _data.length)
      end = _data.length;
    length = end-start+1;
    if (dest.length != length) {
    	dest = new float[length];
    }

    i = start;
    for (j=0; j<length; j++, i++) {
      dest[j]=_data[i];
    }
    return dest;
  }
//float[]
public static float[] eval( float[] _data,int start, int end) {
    int length;
    float[] dest;

    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
    length = end-start+1;
    dest = new float[length];
    return eval(dest, _data,start, end );

}

//native 2d float
public static float[][] eval(float[][] dest,float[][] _data,int start1, int start2, int end1, int end2) {

    int i1;
    int i2;
    int j1;
    int j2;

    int length1;
    int length2;

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    length1 = end1-start1+1;
    length2 = end2-start2+1;
    if (dest.length != length1 || dest[0].length != length2) {
    	dest = new float[length1][length2];
    }
    i1 = start1;
    i2 = start2;
    for (j1=0; j1<length1; j1++, i1++) {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) {
	  dest[j1][j2]=_data[i1][i2];
      }
    }
    return dest;
  }
//float[][]
public static float[][] eval(float[][] _data,int start1, int start2, int end1, int end2) {
    int length1;
    int length2;

    float[][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    length1 = end1-start1+1;
    length2 = end2-start2+1;
    dest = new float[length1][length2];
    return eval(dest,_data,start1,start2,end1,end2);
}

// native 3d float
  public static float[][][] eval(float[][][] dest, float[][][] _data, int start1, int start2, int start3, int end1, int end2, int end3) {
    int i1;
    int i2;
    int i3;
    int j1;
    int j2;
    int j3;
 
    int length1;
    int length2;
    int length3;
 
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    if (dest.length != length1 || dest[0].length != length2 || dest[0][0].length != length3) {
    	dest = new float[length1][length2][length3];
    }
    
    i1 = start1;
    i2 = start2;
    i3 = start3;

    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
	j3=0; i3=start3;
	for (j3=0; j3<length3; j3++, i3++)
	 { 
          dest[j1][j2][j3]=_data[i1][i2][i3];
         }   
       }
     }
    return dest;
  }

//float[][][]
public static float[][][] eval(float[][][] _data,int start1, int start2, int start3, int end1, int end2, int end3) {
    int length1;
    int length2;
    int length3;
 
    float[][][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    
    dest = new float[length1][length2][length3];
    return eval(dest,_data,start1,start2,start3,end1,end2,end3);
}
//native 4d float
public static float[][][][] eval(float[][][][] dest, float[][][][] _data,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {
    int i1;
    int i2;
    int i3;
    int i4;
    int j1;
    int j2;
    int j3;
    int j4;
    
    int length1;
    int length2;
    int length3;
    int length4;
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;


    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    length4 = end4-start4+1;
    
    if (dest.length != length1 || dest[0].length != length2 || dest[0][0].length != length3|| dest[0][0][0].length != length4) {
    	dest = new float[length1][length2][length3][length4];
    }

    i1 = start1;
    i2 = start2;
    i3 = start3;
    i4 = start4;
    
    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
	j3=0; i3=start3;
	for (j3=0; j3<length3; j3++, i3++)
	 { 
	  j4=0; i4=start4;
	  for (j4=0; j4<length4; j4++,i4++)
           {dest[j1][j2][j3][j4]=_data[i1][i2][i3][i4];}
         }   
       }
     }
    return dest;
  }

//float[][][][]
public static float[][][][] eval(float[][][][] _data,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4 ) {
    int length1;
    int length2;
    int length3;
    int length4;
    
    float[][][][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;


    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    length4 = end4-start4+1;

    dest = new float[length1][length2][length3][length4];
	return eval(dest, _data,start1, start2, start3, start3, end1, end2, end3, end4);
}

//----------------------------------------------------------
// ints
/**
* Create an array that captures element <tt>start</tt> to <tt>end</tt>
*      if <tt>start</tt> is smaller than 0, <tt>start</tt> is default as 0;
*      if <tt>end</tt> is greater than the length of the array,
*         <tt>end</tt> is default as the length of the array
*	@param start - the element number start the capture
*	@param end - the element number ends the capture
*	@_data - input matrix
* @return a section of the original array
*/
//native 1d int
public static int[] eval(int[] dest, int[] _data,int start, int end) {
    int i;
    int j;
    int length;
    if (start < 0) start = 0;
    if (end > _data.length)
      end = _data.length;
    length = end-start+1;
    
    if (dest.length != length) {
    	dest = new int[length];
    }
    
    i = start;
    for (j=0; j<length; j++, i++) {
      dest[j]=_data[i];
    }
    return dest;
  }
//int[]
public static int[] eval( int[] _data,int start, int end) {
    int length;
    int[] dest;

    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
    length = end-start+1;
    dest = new int[length];
    return eval(dest, _data,start, end );

}

//native 2d int
public static int[][] eval(int[][] dest,int[][] _data,int start1, int start2, int end1, int end2) {

    int i1;
    int i2;
    int j1;
    int j2;

    int length1;
    int length2;

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    length1 = end1-start1+1;
    length2 = end2-start2+1;
    if (dest.length != length1 || dest[0].length != length2) {
    	dest = new int[length1][length2];
    }
    i1 = start1;
    i2 = start2;
    for (j1=0; j1<length1; j1++, i1++) {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) {
	  dest[j1][j2]=_data[i1][i2];
      }
    }
    return dest;
  }
//int[][]
public static int[][] eval(int[][] _data,int start1, int start2, int end1, int end2) {
    int length1;
    int length2;

    int[][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    length1 = end1-start1+1;
    length2 = end2-start2+1;
    dest = new int[length1][length2];
    return eval(dest,_data,start1,start2,end1,end2);
}

// native 3d int
  public static int[][][] eval(int[][][] dest, int[][][] _data, int start1, int start2, int start3, int end1, int end2, int end3) {
    int i1;
    int i2;
    int i3;
    int j1;
    int j2;
    int j3;
 
    int length1;
    int length2;
    int length3;
 
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;

    if (dest.length != length1 || dest[0].length != length2 || dest[0][0].length != length3) {
    	dest = new int[length1][length2][length3];
    }    
   
    i1 = start1;
    i2 = start2;
    i3 = start3;

    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
	j3=0; i3=start3;
	for (j3=0; j3<length3; j3++, i3++)
	 { 
          dest[j1][j2][j3]=_data[i1][i2][i3];
         }   
       }
     }
    return dest;
  }

//int[][][]
public static int[][][] eval(int[][][] _data,int start1, int start2, int start3, int end1, int end2, int end3) {
    int length1;
    int length2;
    int length3;
 
    int[][][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    
    dest = new int[length1][length2][length3];
    return eval(dest,_data,start1,start2,start3,end1,end2,end3);
}
//native 4d int
public static int[][][][] eval(int[][][][] dest, int[][][][] _data,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {
    int i1;
    int i2;
    int i3;
    int i4;
    int j1;
    int j2;
    int j3;
    int j4;
    
    int length1;
    int length2;
    int length3;
    int length4;
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    length4 = end4-start4+1;

    if (dest.length != length1 || dest[0].length != length2 || dest[0][0].length != length3|| dest[0][0][0].length != length4) {
    	dest = new int[length1][length2][length3][length4];
    }

    i1 = start1;
    i2 = start2;
    i3 = start3;
    i4 = start4;
    
    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
	j3=0; i3=start3;
	for (j3=0; j3<length3; j3++, i3++)
	 { 
	  j4=0; i4=start4;
	  for (j4=0; j4<length4; j4++,i4++)
           {dest[j1][j2][j3][j4]=_data[i1][i2][i3][i4];}
         }   
       }
     }
    return dest;
  }

//int[][][][]
public static int[][][][] eval(int[][][][] _data,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4 ) {
    int length1;
    int length2;
    int length3;
    int length4;
    
    int[][][][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;


    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    length4 = end4-start4+1;

    dest = new int[length1][length2][length3][length4];
	return eval(dest, _data,start1, start2, start3, start3, end1, end2, end3, end4);
}
//----------------------------------------------------------
// booleans
/**
* Create an array that captures element <tt>start</tt> to <tt>end</tt>
*      if <tt>start</tt> is smaller than 0, <tt>start</tt> is default as 0;
*      if <tt>end</tt> is greater than the length of the array,
*         <tt>end</tt> is default as the length of the array
*	@param start - the element number start the capture
*	@param end - the element number ends the capture
*	@_data - input matrix
* @return a section of the original array
*/
//native 1d boolean
public static boolean[] eval(boolean[] dest, boolean[] _data,int start, int end) {
    int i;
    int j;
    int length;
    if (start < 0) start = 0;
    if (end > _data.length)
      end = _data.length;
    length = end-start+1;
    if (dest.length != length) {
    	dest = new boolean[length];
    }

    i = start;
    for (j=0; j<length; j++, i++) {
      dest[j]=_data[i];
    }
    return dest;
  }
//boolean[]
public static boolean[] eval( boolean[] _data,int start, int end) {
    int length;
    boolean[] dest;

    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
    length = end-start+1;
    dest = new boolean[length];
    return eval(dest, _data,start, end );

}

//native 2d boolean
public static boolean[][] eval(boolean[][] dest,boolean[][] _data,int start1, int start2, int end1, int end2) {

    int i1;
    int i2;
    int j1;
    int j2;

    int length1;
    int length2;

    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    length1 = end1-start1+1;
    length2 = end2-start2+1;
    if (dest.length != length1 || dest[0].length != length2) {
    	dest = new boolean[length1][length2];
    }
    i1 = start1;
    i2 = start2;
    for (j1=0; j1<length1; j1++, i1++) {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) {
	  dest[j1][j2]=_data[i1][i2];
      }
    }
    return dest;
  }
//boolean[][]
public static boolean[][] eval(boolean[][] _data,int start1, int start2, int end1, int end2) {
    int length1;
    int length2;

    boolean[][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    length1 = end1-start1+1;
    length2 = end2-start2+1;
    dest = new boolean[length1][length2];
    return eval(dest,_data,start1,start2,end1,end2);
}

// native 3d boolean
  public static boolean[][][] eval(boolean[][][] dest, boolean[][][] _data, int start1, int start2, int start3, int end1, int end2, int end3) {
    int i1;
    int i2;
    int i3;
    int j1;
    int j2;
    int j3;
 
    int length1;
    int length2;
    int length3;
 
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    
    if (dest.length != length1 || dest[0].length != length2 || dest[0][0].length != length3) {
    	dest = new boolean[length1][length2][length3];
    }
    
    i1 = start1;
    i2 = start2;
    i3 = start3;

    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
	j3=0; i3=start3;
	for (j3=0; j3<length3; j3++, i3++)
	 { 
          dest[j1][j2][j3]=_data[i1][i2][i3];
         }   
       }
     }
    return dest;
  }

//boolean[][][]
public static boolean[][][] eval(boolean[][][] _data,int start1, int start2, int start3, int end1, int end2, int end3) {
    int length1;
    int length2;
    int length3;
 
    boolean[][][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    
    dest = new boolean[length1][length2][length3];
    return eval(dest,_data,start1,start2,start3,end1,end2,end3);
}
//native 4d boolean
public static boolean[][][][] eval(boolean[][][][] dest, boolean[][][][] _data,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {
    int i1;
    int i2;
    int i3;
    int i4;
    int j1;
    int j2;
    int j3;
    int j4;
    
    int length1;
    int length2;
    int length3;
    int length4;
    
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;


    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    length4 = end4-start4+1;

    if (dest.length != length1 || dest[0].length != length2 || dest[0][0].length != length3|| dest[0][0][0].length != length4) {
    	dest = new boolean[length1][length2][length3][length4];
    }
    
    i1 = start1;
    i2 = start2;
    i3 = start3;
    i4 = start4;
    
    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
	j3=0; i3=start3;
	for (j3=0; j3<length3; j3++, i3++)
	 { 
	  j4=0; i4=start4;
	  for (j4=0; j4<length4; j4++,i4++)
           {dest[j1][j2][j3][j4]=_data[i1][i2][i3][i4];}
         }   
       }
     }
    return dest;
  }

//boolean[][][][]
public static boolean[][][][] eval(boolean[][][][] _data,int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4 ) {
    int length1;
    int length2;
    int length3;
    int length4;
    
    boolean[][][][] dest;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;


    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    length4 = end4-start4+1;

    dest = new boolean[length1][length2][length3][length4];
	return eval(dest, _data,start1, start2, start3, start3, end1, end2, end3, end4);
}


}  // end NslGetSector 









