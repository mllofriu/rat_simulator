/*  SCCS @(#)NslSetSector.java	1.5 --- 09/01/99 --U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// SetSector routines
//
//

package nslj.src.math;

//import java.util.Arrays;

import nslj.src.lang.*;

public final class NslSetSector {
// NslDoubles
//*****************************
/**
* Set the value of the array from <tt>startpos</tt> to d
*     If the array <tt>d</tt> longer than this array, those
*     out of array scope elements are ignored.
* @param _input - object NslNumeric 1-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslNumeric 1-D array
*/

  public static double[] eval(NslDouble1 _output, int startpos, NslDouble1 _input) {
    return eval(_output.getdouble1(), startpos,_input.getdouble1());
  }

  public static double[][] eval(NslDouble2 _output, int startpos1, int startpos2, NslDouble2 _input) {
    return eval(_output.getdouble2(), startpos1, startpos2,_input.getdouble2());
  }

  public static double[][][] eval(NslDouble3 _output, int startpos1, int startpos2, int startpos3, NslDouble3 _input) {
    return eval(_output.getdouble3(), startpos1, startpos2, startpos3, _input.getdouble3());
  }

  public static double[][][][] eval(NslDouble4 _output, int startpos1, int startpos2, int startpos3, int startpos4, NslDouble4 _input) {
    return eval(_output.getdouble4(), startpos1, startpos2, startpos3,  startpos4, _input.getdouble4());
  }
  
// NslInts
/*****************************
* Set the value of the array from <tt>startpos</tt> to d
*     If the array <tt>d</tt> longer than this array, those
*     out of array scope elements are ignored.
* @param _input - object NslNumeric 1-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslNumeric 1-D array
*/

  public static float[] eval(NslFloat1 _output, int startpos, NslFloat1 _input) {
    return eval(_output.getfloat1(), startpos,_input.getfloat1());
  }

  public static float[][] eval(NslFloat2 _output, int startpos1, int startpos2, NslFloat2 _input) {
    return eval(_output.getfloat2(), startpos1, startpos2,_input.getfloat2());
  }

  public static float[][][] eval(NslFloat3 _output, int startpos1, int startpos2, int startpos3, NslFloat3 _input) {
    return eval(_output.getfloat3(), startpos1, startpos2, startpos3, _input.getfloat3());
  }

  public static float[][][][] eval(NslFloat4 _output, int startpos1, int startpos2, int startpos3, int startpos4, NslFloat4 _input) {
    return eval(_output.getfloat4(), startpos1, startpos2, startpos3,  startpos4, _input.getfloat4());
  }
  // NslInts
//*****************************
/**
* Set the value of the array from <tt>startpos</tt> to d
*     If the array <tt>d</tt> longer than this array, those
*     out of array scope elements are ignored.
* @param _input - object NslNumeric 1-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslNumeric 1-D array
*/

  public static int[] eval(NslInt1 _output, int startpos, NslInt1 _input) {
    return eval(_output.getint1(), startpos,_input.getint1());
  }

  public static int[][] eval(NslInt2 _output, int startpos1, int startpos2, NslInt2 _input) {
    return eval(_output.getint2(), startpos1, startpos2,_input.getint2());
  }

  public static int[][][] eval(NslInt3 _output, int startpos1, int startpos2, int startpos3, NslInt3 _input) {
    return eval(_output.getint3(), startpos1, startpos2, startpos3, _input.getint3());
  }

  public static int[][][][] eval(NslInt4 _output, int startpos1, int startpos2, int startpos3, int startpos4, NslInt4 _input) {
    return eval(_output.getint4(), startpos1, startpos2, startpos3,  startpos4, _input.getint4());
  }
  
  
  
    // NslNumerics
//*****************************
/**
* Set the value of the array from <tt>startpos</tt> to d
*     If the array <tt>d</tt> longer than this array, those
*     out of array scope elements are ignored.
* @param _input - object NslNumeric 1-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslNumeric 1-D array
*/
  public static NslNumeric1 eval(NslNumeric1 _output, int startpos, NslNumeric1 _input) {
    if (_output instanceof NslDouble1) {
	_output._set(eval(_output.getdouble1(), startpos,_input.getdouble1()));
    } else if (_output instanceof NslFloat1) {
	_output._set(eval(_output.getfloat1(), startpos,_input.getfloat1()));
    } else if (_output instanceof NslInt1) {
	_output._set(eval(_output.getint1(), startpos,_input.getint1()));
    } else {
	_output=null;
    }

    return _output;
  }

///----------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object NslNumeric 2-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslNumeric 2-D array
*/

public static NslNumeric2 eval(NslNumeric2 _output, int startpos1, int startpos2, NslNumeric2 _input) {
    if (_output instanceof NslDouble2) {
	_output._set(eval(_output.getdouble2(), startpos1, startpos2,_input.getdouble2()));
    } else if (_output instanceof NslFloat2) {
	_output._set(eval(_output.getfloat2(), startpos1, startpos2,_input.getfloat2()));
    } else if (_output instanceof NslInt2) {
	_output._set(eval(_output.getint2(), startpos1, startpos2,_input.getint2()));
    } else {
	_output=null;
    }

    return _output;
}
//-------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object NslNumeric 3-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslNumeric 3-D array
*/

public static NslNumeric3 eval(NslNumeric3 _output, int startpos1, int startpos2, int startpos3, NslNumeric3 _input) {
     if (_output instanceof NslDouble3) {
	_output._set(eval(_output.getdouble3(), startpos1, startpos2, startpos3, _input.getdouble3()));
    } else if (_output instanceof NslFloat3) {
	_output._set(eval(_output.getfloat3(), startpos1, startpos2, startpos3, _input.getfloat3()));
    } else if (_output instanceof NslInt3) {
	_output._set(eval(_output.getint3(), startpos1, startpos2, startpos3, _input.getint3()));
    } else {
	_output=null;
    }
    return _output;
}
//------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object NslNumeric 4-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslNumeric 4-D array
*/

public static NslNumeric4 eval(NslNumeric4 _output, int startpos1, int startpos2, int startpos3, int startpos4, NslNumeric4 _input) {
if (_output instanceof NslDouble4) {
	_output._set(eval(_output.getdouble4(), startpos1, startpos2, startpos3,  startpos4, _input.getdouble4()));
    } else if (_output instanceof NslFloat4) {
	_output._set(eval(_output.getfloat4(), startpos1, startpos2, startpos3, startpos4, _input.getfloat4()));
    } else if (_output instanceof NslInt4) {
	_output._set(eval(_output.getint4(),  startpos1, startpos2, startpos3, startpos4, _input.getint4()));
    } else {
	_output=null;
    }

	return _output;

  }
// NslBooleans
//*****************************
/**
* Set the value of the array from <tt>startpos</tt> to d
*     If the array <tt>d</tt> longer than this array, those
*     out of array scope elements are ignored.
* @param _input - object NslBoolean 1-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslBoolean 1-D array
*/

  public static boolean[] eval(NslBoolean1 _output, int startpos, NslBoolean1 _input) {
    return eval(_output.getboolean1(), startpos, _input.getboolean1());
  }

///----------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object NslBoolean 2-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslBoolean 2-D array
*/

public static boolean[][] eval(NslBoolean2 _output, int startpos1, int startpos2, NslBoolean2 _input) {
    return eval(_output.getboolean2(), startpos1, startpos2,_input.getboolean2());
}
//-------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object NslBoolean 3-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslBoolean 3-D array
*/

public static boolean[][][] eval(NslBoolean3 _output, int startpos1, int startpos2, int startpos3, NslBoolean3 _input) {
    return eval(_output.getboolean3(), startpos1, startpos2, startpos3, _input.getboolean3());
}
//------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object NslBoolean 4-D array
* @param startpos - the element number to start copying
* @return _output - larger object NslBoolean 4-D array
*/

public static boolean[][][][] eval(NslBoolean4 _output, int startpos1, int startpos2, int startpos3, int startpos4, NslBoolean4 _input) {
    return eval(_output.getboolean4(), startpos1, startpos2, startpos3,  startpos4, _input.getboolean4());
}

//---------------------------------------------------------
//native doubles
/**
* Set the value of the array from <tt>startpos</tt> to d
*     If the array <tt>d</tt> longer than this array, those
*     out of array scope elements are ignored.
* @param _input - object double 1-D array
* @param startpos - the element number to start copying
* @return _output - larger object double 1-D array
*/
  public static double[] eval(double[] _output, int startpos, double[] _input) {
    int endpos = _input.length+startpos;
    int i;
    int j=0;

    if (startpos > _output.length)
        return null;
    if (endpos > _output.length)
        endpos = _output.length;
    for (i=startpos; i<endpos; i++, j++)
        _output[i] = _input[j];
	return _output;
  }

///----------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object double 2-D array
* @param startpos - the element number to start copying
* @return _output - larger object double 2-D array
*/

public static double[][] eval(double[][] _output, int startpos1, int startpos2, double[][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int i1, i2;
    int j1=0, j2=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) {
        j2=0;
        for (i2=startpos2; i2<endpos2; i2++, j2++)
            _output[i1][i2] = _input[j1][j2];
    }
	return _output;

  }

//-------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object double 3-D array
* @param startpos - the element number to start copying
* @return _output - larger object double 3-D array
*/

public static double[][][] eval(double[][][] _output, int startpos1, int startpos2, int startpos3, double[][][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int endpos3 = _input[0][0].length+startpos3;
    int i1, i2, i3;
    int j1=0, j2=0, j3=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (startpos3 > _output[0][0].length)
        return null;

    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;
    if (endpos3 > _output[0][0].length)
        endpos3 = _output[0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
	for (i3=startpos3; i3<endpos3; i3++, j3++)
         {_output[i1][i2][i3] = _input[j1][j2][j3];}
       }
     }
	return _output;

  }
//------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object double 4-D array
* @param startpos - the element number to start copying
* @return _output - larger object double 4-D array
*/

public static double[][][][] eval(double[][][][] _output, int startpos1, int startpos2, int startpos3, int startpos4, double[][][][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int endpos3 = _input[0][0].length+startpos3;
    int endpos4 = _input[0][0][0].length+startpos4;
    int i1, i2, i3, i4;
    int j1=0, j2=0, j3=0, j4=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (startpos3 > _output[0][0].length)
        return null;
    if (startpos4 > _output[0][0][0].length)
        return null;
        
    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;
    if (endpos3 > _output[0][0].length)
        endpos3 = _output[0][0].length;
    if (endpos4 > _output[0][0][0].length)
        endpos4 = _output[0][0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
	for (i3=startpos3; i3<endpos3; i3++, j3++)
         {
          j4=0;
	  for (i4=startpos4; i4<endpos4; i4++, j4++)
           {_output[i1][i2][i3][i4] = _input[j1][j2][j3][j4];}
         }
       }
     }
	return _output;

  }


//---------------------------------------------------------
//native floats
/**
* Set the value of the array from <tt>startpos</tt> to d
*     If the array <tt>d</tt> longer than this array, those
*     out of array scope elements are ignored.
* @param _input - object Float 1-D array
* @param startpos - the element number to start copying
* @return _output - larger object Float 1-D array
*/
  public static float[] eval(float[] _output, int startpos, float[] _input) {
    int endpos = _input.length+startpos;
    int i;
    int j=0;

    if (startpos > _output.length)
        return null;
    if (endpos > _output.length)
        endpos = _output.length;
    for (i=startpos; i<endpos; i++, j++)
        _output[i] = _input[j];
	return _output;
  }

///----------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object Float 2-D array
* @param startpos - the element number to start copying
* @return _output - larger object float 2-D array
*/

public static float[][] eval(float[][] _output, int startpos1, int startpos2, float[][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int i1, i2;
    int j1=0, j2=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) {
        j2=0;
        for (i2=startpos2; i2<endpos2; i2++, j2++)
            _output[i1][i2] = _input[j1][j2];
    }
	return _output;

  }

//-------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object float 3-D array
* @param startpos - the element number to start copying
* @return _output - larger object float 3-D array
*/

public static float[][][] eval(float[][][] _output, int startpos1, int startpos2, int startpos3, float[][][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int endpos3 = _input[0][0].length+startpos3;
    int i1, i2, i3;
    int j1=0, j2=0, j3=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (startpos3 > _output[0][0].length)
        return null;

    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;
    if (endpos3 > _output[0][0].length)
        endpos3 = _output[0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
	for (i3=startpos3; i3<endpos3; i3++, j3++)
         {_output[i1][i2][i3] = _input[j1][j2][j3];}
       }
     }
	return _output;

  }
//------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object float 4-D array
* @param startpos - the element number to start copying
* @return _output - larger object float 4-D array
*/

public static float[][][][] eval(float[][][][] _output, int startpos1, int startpos2, int startpos3, int startpos4, float[][][][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int endpos3 = _input[0][0].length+startpos3;
    int endpos4 = _input[0][0][0].length+startpos4;
    int i1, i2, i3, i4;
    int j1=0, j2=0, j3=0, j4=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (startpos3 > _output[0][0].length)
        return null;
    if (startpos4 > _output[0][0][0].length)
        return null;
        
    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;
    if (endpos3 > _output[0][0].length)
        endpos3 = _output[0][0].length;
    if (endpos4 > _output[0][0][0].length)
        endpos4 = _output[0][0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
	for (i3=startpos3; i3<endpos3; i3++, j3++)
         {
          j4=0;
	  for (i4=startpos4; i4<endpos4; i4++, j4++)
           {_output[i1][i2][i3][i4] = _input[j1][j2][j3][j4];}
         }
       }
     }
	return _output;

  }


//---------------------------------------------------------
//native ints
/**
* Set the value of the array from <tt>startpos</tt> to d
*     If the array <tt>d</tt> longer than this array, those
*     out of array scope elements are ignored.
* @param _input - object Float 1-D array
* @param startpos - the element number to start copying
* @return _output - larger object Float 1-D array
*/
  public static int[] eval(int[] _output, int startpos, int[] _input) {
    int endpos = _input.length+startpos;
    int i;
    int j=0;

    if (startpos > _output.length)
        return null;
    if (endpos > _output.length)
        endpos = _output.length;
    for (i=startpos; i<endpos; i++, j++)
        _output[i] = _input[j];
	return _output;
  }

///----------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object Float 2-D array
* @param startpos - the element number to start copying
* @return _output - larger object int 2-D array
*/

public static int[][] eval(int[][] _output, int startpos1, int startpos2, int[][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int i1, i2;
    int j1=0, j2=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) {
        j2=0;
        for (i2=startpos2; i2<endpos2; i2++, j2++)
            _output[i1][i2] = _input[j1][j2];
    }
	return _output;
  }

//-------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object int 3-D array
* @param startpos - the element number to start copying
* @return _output - larger object int 3-D array
*/

public static int[][][] eval(int[][][] _output, int startpos1, int startpos2, int startpos3, int[][][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int endpos3 = _input[0][0].length+startpos3;
    int i1, i2, i3;
    int j1=0, j2=0, j3=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (startpos3 > _output[0][0].length)
        return null;

    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;
    if (endpos3 > _output[0][0].length)
        endpos3 = _output[0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
	for (i3=startpos3; i3<endpos3; i3++, j3++)
         {_output[i1][i2][i3] = _input[j1][j2][j3];}
       }
     }
	return _output;

  }
//------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object int 4-D array
* @param startpos - the element number to start copying
* @return _output - larger object int 4-D array
*/

public static int[][][][] eval(int[][][][] _output, int startpos1, int startpos2, int startpos3, int startpos4, int[][][][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int endpos3 = _input[0][0].length+startpos3;
    int endpos4 = _input[0][0][0].length+startpos4;
    int i1, i2, i3, i4;
    int j1=0, j2=0, j3=0, j4=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (startpos3 > _output[0][0].length)
        return null;
    if (startpos4 > _output[0][0][0].length)
        return null;
        
    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;
    if (endpos3 > _output[0][0].length)
        endpos3 = _output[0][0].length;
    if (endpos4 > _output[0][0][0].length)
        endpos4 = _output[0][0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
	for (i3=startpos3; i3<endpos3; i3++, j3++)
         {
          j4=0;
	  for (i4=startpos4; i4<endpos4; i4++, j4++)
           {_output[i1][i2][i3][i4] = _input[j1][j2][j3][j4];}
         }
       }
     }
	return _output;

  }

//---------------------------------------------------------
//native booleans
/**
* Set the value of the array from <tt>startpos</tt> to d
*     If the array <tt>d</tt> longer than this array, those
*     out of array scope elements are ignored.
* @param _input - object boolean 1-D array
* @param startpos - the element number to start copying
* @return _output - larger object boolean 1-D array
*/
  public static boolean[] eval(boolean[] _output, int startpos, boolean[] _input) {
    int endpos = _input.length+startpos;
    int i;
    int j=0;

    if (startpos > _output.length)
        return null;
    if (endpos > _output.length)
        endpos = _output.length;
    for (i=startpos; i<endpos; i++, j++)
        _output[i] = _input[j];
	return _output;
  }

///----------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object boolean 2-D array
* @param startpos - the element number to start copying
* @return _output - larger object boolean 2-D array
*/

public static boolean[][] eval(boolean[][] _output, int startpos1, int startpos2, boolean[][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int i1, i2;
    int j1=0, j2=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) {
        j2=0;
        for (i2=startpos2; i2<endpos2; i2++, j2++)
            _output[i1][i2] = _input[j1][j2];
    }
	return _output;

  }

//-------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object boolean 3-D array
* @param startpos - the element number to start copying
* @return _output - larger object boolean 3-D array
*/

public static boolean[][][] eval(boolean[][][] _output, int startpos1, int startpos2, int startpos3, boolean[][][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int endpos3 = _input[0][0].length+startpos3;
    int i1, i2, i3;
    int j1=0, j2=0, j3=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (startpos3 > _output[0][0].length)
        return null;

    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;
    if (endpos3 > _output[0][0].length)
        endpos3 = _output[0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
	for (i3=startpos3; i3<endpos3; i3++, j3++)
         {_output[i1][i2][i3] = _input[j1][j2][j3];}
       }
     }
	return _output;

  }
//------------
/**
* Set the value of the array from <tt>startpos</tt> to d
* If the array <tt>d</tt> longer than this array, those
* out of array scope elements are ignored.
* @param _input - object boolean 4-D array
* @param startpos - the element number to start copying
* @return _output - larger object boolean 4-D array
*/

public static boolean[][][][] eval(boolean[][][][] _output, int startpos1, int startpos2, int startpos3, int startpos4, boolean[][][][] _input) {
    int endpos1 = _input.length+startpos1;
    int endpos2 = _input[0].length+startpos2;
    int endpos3 = _input[0][0].length+startpos3;
    int endpos4 = _input[0][0][0].length+startpos4;
    int i1, i2, i3, i4;
    int j1=0, j2=0, j3=0, j4=0;

    if (startpos1 > _output.length)
        return null;
    if (startpos2 > _output[0].length)
        return null;
    if (startpos3 > _output[0][0].length)
        return null;
    if (startpos4 > _output[0][0][0].length)
        return null;
        
    if (endpos1 > _output.length)
        endpos1 = _output.length;
    if (endpos2 > _output[0].length)
        endpos2 = _output[0].length;
    if (endpos3 > _output[0][0].length)
        endpos3 = _output[0][0].length;
    if (endpos4 > _output[0][0][0].length)
        endpos4 = _output[0][0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
	for (i3=startpos3; i3<endpos3; i3++, j3++)
         {
          j4=0;
	  for (i4=startpos4; i4<endpos4; i4++, j4++)
           {_output[i1][i2][i3][i4] = _input[j1][j2][j3][j4];}
         }
       }
     }
	return _output;

  }

//------------------------------------------------------------------
//---------------------------------------------------------

}  // end NslSetSector






