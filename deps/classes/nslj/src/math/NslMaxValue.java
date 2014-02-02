/*  SCCS @(#)NslMaxValue.java	1.6 -- 09/01/99 -- 00:18:15 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Maximum value number routines
//
//

/**
 Maximum value number routines.
 There is one format for the evaluation method in
 this routine:
 1, eval(a) -> c
 a is the parameter to evaluate the maximum value of
 a and the result is passed out as c
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

public final class NslMaxValue {
//----------------------------------------------------------
// NslNumerics
//----------------------------------------------------------

public static double eval(NslDouble1 nslnumeric) {
return (eval(nslnumeric.getdouble1()));
}

public static float eval(NslFloat1 nslnumeric) {
return (eval(nslnumeric.getfloat1()));
}
public static int eval(NslInt1 nslnumeric) {
return (eval(nslnumeric.getint1()));
}

//---------
public static double eval(NslDouble2 nslnumeric) {
return (eval(nslnumeric.getdouble2()));
}

public static float eval(NslFloat2 nslnumeric) {
return (eval(nslnumeric.getfloat2()));
}
public static int eval(NslInt2 nslnumeric) {
return (eval(nslnumeric.getint2()));
}

//---------
public static double eval(NslDouble3 nslnumeric) {
return (eval(nslnumeric.getdouble3()));
}

public static float eval(NslFloat3 nslnumeric) {
return (eval(nslnumeric.getfloat3()));
}
public static int eval(NslInt3 nslnumeric) {
return (eval(nslnumeric.getint3()));
}

//---------
public static double eval(NslDouble4 nslnumeric) {
return (eval(nslnumeric.getdouble4()));
}

public static float eval(NslFloat4 nslnumeric) {
return (eval(nslnumeric.getfloat4()));
}
public static int eval(NslInt4 nslnumeric) {
return (eval(nslnumeric.getint4()));
}



//----------------------------------------------------------
// native double
//----------------------------------------------------------

public static double eval(double[] _data) {
      int pos;
	pos=NslMaxElem.eval(_data);
	return (_data[pos] );
  }
//---------
  public static double eval(double[][] _data) {
      int[] pos;
	pos=NslMaxElem.eval(_data);
	return (_data[pos[0]] [pos[1]]);
}
//----------------------------

  public static double eval(double[][][] _data) {
      int[] pos; 
	pos=NslMaxElem.eval(_data);
	return (_data[pos[0]] [pos[1]] [pos[2]]);
}
//--------------------------------------------
  public static double eval(double[][][][] _data) {
      int[] pos;
	pos=NslMaxElem.eval(_data);
	return (_data[pos[0]] [pos[1]] [pos[2]] [pos[3]]);
}

//----------------------------------------------------------
// native float
//----------------------------------------------------------
public static float eval(float[] _data) {
      int pos;
	pos=NslMaxElem.eval(_data);
	return (_data[pos] );
  }
//---------
  public static float eval(float[][] _data) {
      int[] pos;
	pos=NslMaxElem.eval(_data);
	return (_data[pos[0]] [pos[1]]);
}
//----------------------------

  public static float eval(float[][][] _data) {
      int[] pos; 
	pos=NslMaxElem.eval(_data);
	return (_data[pos[0]] [pos[1]] [pos[2]]);
}
//--------------------------------------------
  public static float eval(float[][][][] _data) {
      int[] pos;
	pos=NslMaxElem.eval(_data);
	return (_data[pos[0]] [pos[1]] [pos[2]] [pos[3]]);
}

//----------------------------------------------------------
// native int
//----------------------------------------------------------
public static int eval(int[] _data) {
      int pos;
	pos=NslMaxElem.eval(_data);
	return (_data[pos] );
  }
//---------
  public static int eval(int[][] _data) {
      int[] pos;
	pos=NslMaxElem.eval(_data);
	return (_data[pos[0]] [pos[1]]);
}
//----------------------------

  public static int eval(int[][][] _data) {
      int[] pos; 
	pos=NslMaxElem.eval(_data);
	return (_data[pos[0]] [pos[1]] [pos[2]]);
}
//--------------------------------------------
  public static int eval(int[][][][] _data) {
      int[] pos;
	pos=NslMaxElem.eval(_data);
	return (_data[pos[0]] [pos[1]] [pos[2]] [pos[3]]);
}


}  // end NslMaxValue




