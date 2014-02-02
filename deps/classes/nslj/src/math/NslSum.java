/*  SCCS - @(#)NslSum.java	1.3 --- 09/01/99 -- 00:18:13 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Summation routines
//
//

/**
 Summation routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a) -> c
 a is the parameter to evaluate the summation of
 a pointwise and the result is passed out as c
 2. eval(dest, a) -> c
 a is the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.
NslSum always returns a double.
*/

package nslj.src.math;

import nslj.src.lang.*;

public final class NslSum {

//native 0d double
public static double eval(double a) {
	return a;
}
//native 1d double
public static double eval(double dest, double[]_data) {
	int i;
	dest=0;
	for (i=0; i<_data.length; i++) {
         dest=dest+_data[i];
}
return dest;
}

public static double eval(double[] _data) {
	double dest=0;
      return (eval(dest,_data));
}

//native 2d double
public static  double eval(double dest, double[][] _data) {
    
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
	dest=0;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++)
          dest+=_data[i][j];
    }
    return dest;
  }

public static  double eval(double[][] _data) {
	double dest=0;
	return (eval(dest,_data));
}

// native 3d double

public static  double eval(double dest, double[][][] _data) {
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
	dest=0;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { dest+=_data[i][j][k];}
       }
     }
    return dest;
  }
public static  double eval(double[][][] _data) {
	double dest=0;
	return (eval(dest,_data));
}

// native 4d double
  public static  double eval(double dest, double[][][][] _data) {
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
	dest=0;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
  	 {
          for(l=0; l<size4; l++)	
           { dest+=_data[i][j][k][l];}
         } 
       }
     }
    return dest;
  }

public static  double eval(double[][][][] _data) {
	double dest=0;
	return (eval(dest,_data));
}
//------------------------------------------------------
//native 0d float
public static double eval(float a) {
	return a;
}
//native 1d float
public static double eval(double dest, float[]_data) {
	int i;
	dest=0;

	for (i=0; i<_data.length; i++) {
  dest=dest+_data[i];
}
return dest;
}
public static double eval(float[] _data) {
	double dest=0;
return (eval(dest,_data));
}

//native 2d float
public static  double eval(double dest, float[][] _data) {
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    dest = 0;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++)
          dest+=_data[i][j];
    }
    return dest;
  }

public static  double eval(float[][] _data) {
	double dest=0;
	return (eval(dest,_data));
}

// native 3d float

public static  double eval(double dest, float[][][] _data) {
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    dest = 0;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { dest+=_data[i][j][k];}
       }
     }
    return dest;
  }
public static  double eval(float[][][] _data) {
	double dest=0;
	return (eval(dest,_data));
}

// native 4d float
  public static  double eval(double dest, float[][][][] _data) {
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    dest = 0;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
  	 {
          for(l=0; l<size4; l++)	
           { dest+=_data[i][j][k][l];}
         } 
       }
     }
    return dest;
  }

public static  double eval(float[][][][] _data) {
	double dest=0;
	return (eval(dest,_data));
}
//-------------------------------------------------------
//native 0d int
public static double eval(int a) {
	return a;
}
//native 1d int
public static double eval(double dest, int[]_data) {
	int i;
    dest = 0;

	for (i=0; i<_data.length; i++) {
  dest=dest+_data[i];
}
return dest;
}
public static double eval(int[] _data) {
	double dest=0;
return (eval(dest,_data));
}

//native 2d int
public static double eval(double dest, int[][] _data) {
    
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    dest = 0;
    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++)
          dest+=_data[i][j];
    }
    return dest;
  }

public static double eval(int[][] _data) {
	double dest=0;
	return (eval(dest,_data));
}

// native 3d int

public static double eval(double dest, int[][][] _data) {
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    dest = 0;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { dest+=_data[i][j][k];}
       }
     }
    return dest;
  }
public static  double eval(int[][][] _data) {
	double dest=0;
	return (eval(dest,_data));
}

// native 4d int
  public static  double eval(double dest, int[][][][] _data) {

    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    dest = 0;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
  	 {
          for(l=0; l<size4; l++)	
           { dest+=_data[i][j][k][l];}
         } 
       }
     }
    return dest;
  }

public static  double eval(int[][][][] _data) {
	double dest=0;
	return (eval(dest,_data));
}
//--------------------------------------------------------
//NslNumeric 0d NslNumeric
public static double eval(NslNumeric0 a) {
	return a.getdouble();
}
//NslNumeric 1d NslNumeric
public static double eval(double dest, NslNumeric1 _data) {
	int i;

	dest = 0;
	int size =_data.getSize();

	for (i=0; i<size; i++) {
  		dest=dest+_data.getdouble(i);
	}
	return dest;
}
public static double eval(NslNumeric1 _data) {
	double dest=0;
return (eval(dest,_data));
}

//NslNumeric 2d NslNumeric
public static double eval(double dest, NslNumeric2 _data) {
    int i, j;
    int size1;
    int size2;
    int [] sizes=_data.getSizes();
    size1=sizes[0];
    size2=sizes[1];
    dest=0;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++)
          dest+=_data.getdouble(i,j);
    }
    return dest;
  }

public static double eval(NslNumeric2 _data) {
	double dest=0;
	return (eval(dest,_data));
}

// NslNumeric 3d 

public static double eval(double dest, NslNumeric3 _data) {
    int i, j, k;
    int size1 ;
    int size2 ;
    int size3 ;
    int [] sizes=_data.getSizes();
    size1=sizes[0];
    size2=sizes[1];
    size3=sizes[2];
    dest=0;
    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { dest+=_data.getdouble(i,j,k);}
       }
     }
    return dest;
  }
public static double eval(NslNumeric3 _data) {
	double dest=0;
	return (eval(dest,_data));
}

// NslNumeric 4d double
  public static  double eval(double dest, NslNumeric4 _data) {
    int i, j, k, l;
    int size1 ;
    int size2 ;
    int size3 ;
    int size4 ;

    int [] sizes=_data.getSizes();
    size1=sizes[0];
    size2=sizes[1];
    size3=sizes[2];
    size4=sizes[3];

    dest=0;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
  	 {
          for(l=0; l<size4; l++)	
           { dest+=_data.getdouble(i,j,k,l);}
         } 
       }
     }
    return dest;
  }

public static  double eval(NslNumeric4 _data) {
	double dest=0;
	return (eval(dest,_data));
}



}  // end NslSum




