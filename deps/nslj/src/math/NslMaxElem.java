/*  SCCS @(#)NslMaxElem.java	1.3 --- 09/01/99 -- 00:18:12 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Maximum element routines
//
//

/**
 Maximum element routines.
 There is only one format for the evaluation method in
 this routine:
 1, eval(a) -> i[]
 a is the parameter to evaluate the element of
  and the result is passed out as an array of indexes i
*/

package nslj.src.math;

import nslj.src.lang.NslNumeric1;
import nslj.src.lang.NslNumeric2;
import nslj.src.lang.NslNumeric3;
import nslj.src.lang.NslNumeric4;
import nslj.src.lang.NslDouble1;
import nslj.src.lang.NslDouble2;
import nslj.src.lang.NslDouble3;
import nslj.src.lang.NslDouble4;
import nslj.src.lang.NslFloat1;
import nslj.src.lang.NslFloat2;
import nslj.src.lang.NslFloat3;
import nslj.src.lang.NslFloat4;
import nslj.src.lang.NslInt1;
import nslj.src.lang.NslInt2;
import nslj.src.lang.NslInt3;
import nslj.src.lang.NslInt4;


public final class NslMaxElem {
//----------------------------------------------------------
// native double
public static int eval(double[] _data) {
    double value = java.lang.Double.NEGATIVE_INFINITY;
    int i;
    int pos=-1;
    
    for (i=0; i<_data.length; i++) {
      if (_data[i]>value) {
	pos = i;
	value = _data[i];
      }
    }
    return pos;
  }
//---------
  public static int[] eval(double[][] _data) {
    double value = java.lang.Double.NEGATIVE_INFINITY;
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int[] pos=new int[2];
	pos[0]=-1;
	pos[1]=-1;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++) {
          if (_data[i][j]>value) {
	        pos[0] = i; 
  pos[1] = j;
	        value = _data[i][j];
	      }
      }
    }
    return pos;
  }
//----------------------------

  public static int[] eval(double[][][] _data) {
    double value = java.lang.Double.NEGATIVE_INFINITY;
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int[] pos=new int[3];
	pos[0]=-1;
	pos[1]=-1;
	pos[2]=-1;


    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { 
          if (_data[i][j][k]>value) 
	   {
	    pos[0] = i; pos[1] = j; pos[2] = k;
	    value = _data[i][j][k];
	   }
         }
       }
     }
     return pos;
  }
//--------------------------------------------
  public static int[] eval(double[][][][] _data) {
    double value = java.lang.Double.NEGATIVE_INFINITY;
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    int[] pos=new int[4];
	pos[0]=-1;
	pos[1]=-1;
	pos[2]=-1;
	pos[3]=-1;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         {
          for(l=0; l<size4; k++)	
           { 
            if (_data[i][j][k][l]>value) 
       	    {
	           pos[0] = i; pos[1]= j; pos[2] = k; pos[3]= l;
	           value = _data[i][j][k][l];
     	          }
           }
         }
       }
     }
     return pos;
  }

//----------------------------------------------------------
// native float
//----------------------------------------------------------

public static int eval(float[] _data) {
    float value = java.lang.Float.NEGATIVE_INFINITY;
    int i;
    int pos=-1;
    
    for (i=0; i<_data.length; i++) {
      if (_data[i]>value) {
	pos = i;
	value = _data[i];
      }
    }
    return pos;
  }
//---------
  public static int[] eval(float[][] _data) {
    float value = java.lang.Float.NEGATIVE_INFINITY;
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int[] pos=new int[2];
	pos[0]=-1;
	pos[1]=-1;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++) {
          if (_data[i][j]>value) {
	        pos[0] = i; 
  pos[1] = j;
	        value = _data[i][j];
	      }
      }
    }
    return pos;
  }
//----------------------------

  public static int[] eval(float[][][] _data) {
    float value = java.lang.Float.NEGATIVE_INFINITY;
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int[] pos=new int[3];
	pos[0]=-1;
	pos[1]=-1;
	pos[2]=-1;


    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { 
          if (_data[i][j][k]>value) 
	   {
	    pos[0] = i; pos[1] = j; pos[2] = k;
	    value = _data[i][j][k];
	   }
         }
       }
     }
     return pos;
  }
//--------------------------------------------
  public static int[] eval(float[][][][] _data) {
    float value = java.lang.Float.NEGATIVE_INFINITY;
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    int[] pos=new int[4];
	pos[0]=-1;
	pos[1]=-1;
	pos[2]=-1;
	pos[3]=-1;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         {
          for(l=0; l<size4; k++)	
           { 
            if (_data[i][j][k][l]>value) 
       	    {
	           pos[0] = i; pos[1]= j; pos[2] = k; pos[3]= l;
	           value = _data[i][j][k][l];
     	          }
           }
         }
       }
     }
     return pos;
  }
//----------------------------------------------------------
// native int
//----------------------------------------------------------
public static int eval(int[] _data) {
    int value = java.lang.Integer.MIN_VALUE;
    int i;
	int pos=-1;
    
    for (i=0; i<_data.length; i++) {
      if (_data[i]>value) {
	pos = i;
	value = _data[i];
      }
    }
    return pos;
  }
//---------
  public static int[] eval(int[][] _data) {
    int value = java.lang.Integer.MIN_VALUE;
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int[] pos=new int[2];
	pos[0]=-1;
	pos[1]=-1;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++) {
          if (_data[i][j]>value) {
	        pos[0] = i; 
  pos[1] = j;
	        value = _data[i][j];
	      }
      }
    }
    return pos;
  }
//----------------------------

  public static int[] eval(int[][][] _data) {
    int value = java.lang.Integer.MIN_VALUE;
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int[] pos=new int[3];
	pos[0]=-1;
	pos[1]=-1;
	pos[2]=-1;


    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { 
          if (_data[i][j][k]>value) 
	   {
	    pos[0] = i; pos[1] = j; pos[2] = k;
	    value = _data[i][j][k];
	   }
         }
       }
     }
     return pos;
  }
//--------------------------------------------
  public static int[] eval(int[][][][] _data) {
    int value = java.lang.Integer.MIN_VALUE;
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    int[] pos=new int[4];
	pos[0]=-1;
	pos[1]=-1;
	pos[2]=-1;
	pos[3]=-1;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         {
          for(l=0; l<size4; k++)	
           { 
            if (_data[i][j][k][l]>value) 
       	    {
	           pos[0] = i; pos[1]= j; pos[2] = k; pos[3]= l;
	           value = _data[i][j][k][l];
     	          }
           }
         }
       }
     }
     return pos;
  }
//----------------------------------------------------------
// NslNumerics
//----------------------------------------------------------

public static int eval(NslNumeric1 nslnumeric) {

    if (nslnumeric instanceof NslDouble1) {
       return (eval(nslnumeric.getdouble1()));
    } else if (nslnumeric instanceof NslFloat1) {
       return (eval(nslnumeric.getfloat1()));
    } else if (nslnumeric instanceof NslInt1) {
      return (eval(nslnumeric.getint1()));
    } else {
        return -1;
    }
}
//---------
  public static int[] eval(NslNumeric2 nslnumeric) {
    if (nslnumeric instanceof NslDouble2) {
       return (eval(nslnumeric.getdouble2()));
    } else if (nslnumeric instanceof NslFloat2) {
       return (eval(nslnumeric.getfloat2()));
    } else if (nslnumeric instanceof NslInt2) {
      return (eval(nslnumeric.getint2()));
    } else {
        return null;
    }
}

//----------------------------

  public static int[] eval(NslNumeric3 nslnumeric) {
    if (nslnumeric instanceof NslDouble3) {
       return (eval(nslnumeric.getdouble3()));
    } else if (nslnumeric instanceof NslFloat3) {
       return (eval(nslnumeric.getfloat3()));
    } else if (nslnumeric instanceof NslInt3) {
      return (eval(nslnumeric.getint3()));
    } else {
        return null;
    }
}

//--------------------------------------------
  public static int[] eval(NslNumeric4 nslnumeric) {
    if (nslnumeric instanceof NslDouble4) {
       return (eval(nslnumeric.getdouble4()));
    } else if (nslnumeric instanceof NslFloat4) {
       return (eval(nslnumeric.getfloat4()));
    } else if (nslnumeric instanceof NslInt4) {
      return (eval(nslnumeric.getint4()));
    } else {
        return null;
    }
}


} //end NslMaxElem.java



