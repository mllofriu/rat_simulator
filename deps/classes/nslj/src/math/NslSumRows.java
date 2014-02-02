/*  SCCS @(#)NslSumRows.java	1.7 ---09/01/99 --00:18:13 */

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
NslSumRows always returns a double.
*/

package nslj.src.math;

//import java.util.Arrays;

import nslj.src.lang.*;

public final class NslSumRows {

//native 0d double
public static double eval(double a) {
	return a;
}
//native 1d double
public static double eval(double dest, double[]_data) {
	int i;
	dest=(double)0.0;
	for (i=0; i<_data.length; i++) {
         dest=dest+_data[i];
}
return dest;
}

public static double eval(double[] _data) {
	double dest=(double)0.0; 
      return (eval(dest,_data));
}

//native 2d double
public static  double[] eval(double[] dest, double[][] _data) {
    
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    
    if (dest.length != size1 ) {
        dest = new double[size1];
    }

    for (i=0; i<size1; i++) {
		dest[i]=(double)0.0;
    }
    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++) {
          dest[i]+=_data[i][j];
	  }
    }
    return dest;
  }

public static  double[] eval(double[][] _data) {
	
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
	double[] dest =new double[size1];
	return (eval(dest,_data));
}

// native 3d double

public static  double[][] eval(double[][] dest, double[][][] _data) {
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
 
    if (dest.length != size1 || dest[0].length != size2) {
        dest = new double[size1][size2];
    }

    for (i=0; i<size1; i++) {
      for (j=0; j<size2; j++) {
	    dest[i][j]=(double)0.0;
	}
    }

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { dest[i][j]+=_data[i][j][k];}
       }
     }
    return dest;
  }
public static  double[][] eval(double[][][] _data) {
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;

	double[][] dest =new double[size1][size2];
	return (eval(dest,_data));
}

// native 4d double
  public static  double[][][] eval(double[][][] dest, double[][][][] _data) {
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    if (dest.length != size1 || dest[0].length != size2 || dest[0][0].length != size3) {
        dest = new double[size1][size2][size3];
    }
    for (i=0; i<size1; i++) {
      for (j=0; j<size2; j++) {
        for(k=0; k<size3; k++) {	
	    dest[i][j][k]=((double)0.0);
	  }
      }
    }


    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
  	 {
          for(l=0; l<size4; l++)	
           { dest[i][j][k]+=_data[i][j][k][l];}
         } 
       }
     }
    return dest;
  }

public static  double[][][] eval(double[][][][] _data) {
	
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;

	double[][][] dest =new double[size1][size2][size3];
	return (eval(dest,_data));
}

//0d nsl double 

  public static double eval(NslDouble0 a) {
    return eval(a.getdouble());
  }

//1d nsl double 

  public static double eval(NslDouble1 a) {
    return eval(a.getdouble1());
  }
  
  public static double eval(double dest, NslDouble1 a) {
    return eval(dest, a.getdouble1());
  }  
  
// 2d nsl double

  public static double[] eval(NslDouble2 a) {
    return eval(a.getdouble2());
  }
  
  public static double[] eval(double[] dest, NslDouble2 a) {
    return eval(dest, a.getdouble2());
  }

// 3d nsl double

  public static double[][] eval(NslDouble3 a) {
    return eval(a.getdouble3());
  }
  
  public static double[][] eval(double[][] dest, NslDouble3 a) {
    return eval(dest, a.getdouble3());
  }

// 4d nsl double

  public static double[][][] eval(NslDouble4 a) {
    return eval(a.getdouble4());
  }
  
  public static double[][][] eval(double[][][] dest, NslDouble4 a) {
    return eval(dest, a.getdouble4());
  }


//native 0d float
public static float eval(float a) {
	return a;
}
//native 1d float
public static float eval(float dest, float[]_data) {
	int i;
	dest=0;
	for (i=0; i<_data.length; i++) {
         dest=dest+_data[i];
}
return dest;
}

public static float eval(float[] _data) {
	float dest=0; 
      return (eval(dest,_data));
}

//native 2d float
public static  float[] eval(float[] dest, float[][] _data) {
    
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    
    if (dest.length != size1 ) {
        dest = new float[size1];
    }

    for (i=0; i<size1; i++) {
		dest[i]=0;
    }
    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++) {
          dest[i]+=_data[i][j];
	  }
    }
    return dest;
  }

public static  float[] eval(float[][] _data) {
	
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
	float[] dest =new float[size1];
	return (eval(dest,_data));
}

// native 3d float

public static  float[][] eval(float[][] dest, float[][][] _data) {
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
 
    if (dest.length != size1 || dest[0].length != size2) {
        dest = new float[size1][size2];
    }

    for (i=0; i<size1; i++) {
      for (j=0; j<size2; j++) {
	    dest[i][j]=0;
	}
    }

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { dest[i][j]+=_data[i][j][k];}
       }
     }
    return dest;
  }
public static  float[][] eval(float[][][] _data) {
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;

	float[][] dest =new float[size1][size2];
	return (eval(dest,_data));
}

// native 4d float
  public static  float[][][] eval(float[][][] dest, float[][][][] _data) {
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    if (dest.length != size1 || dest[0].length != size2 || dest[0][0].length != size3) {
        dest = new float[size1][size2][size3];
    }
    for (i=0; i<size1; i++) {
      for (j=0; j<size2; j++) {
        for(k=0; k<size3; k++) {	
	    dest[i][j][k]=0;
	  }
      }
    }


    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
  	 {
          for(l=0; l<size4; l++)	
           { dest[i][j][k]+=_data[i][j][k][l];}
         } 
       }
     }
    return dest;
  }

public static  float[][][] eval(float[][][][] _data) {
	
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;

	float[][][] dest =new float[size1][size2][size3];
	return (eval(dest,_data));
}
//0d nsl float 

  public static float eval(NslFloat0 a) {
    return eval(a.getfloat());
  }

//1d nsl float 

  public static float eval(NslFloat1 a) {
    return eval(a.getfloat1());
  }
  
  public static float eval(float dest, NslFloat1 a) {
    return eval(dest, a.getfloat1());
  }
  
  
// 2d nsl float

  public static float[] eval(NslFloat2 a) {
    return eval(a.getfloat2());
  }
  
  public static float[] eval(float[] dest, NslFloat2 a) {
    return eval(dest, a.getfloat2());
  }
  
// 3d nsl float

  public static float[][] eval(NslFloat3 a) {
    return eval(a.getfloat3());
  }

  public static float[][] eval(float[][] dest, NslFloat3 a) {
    return eval(dest, a.getfloat3());
  }
  
// 4d nsl float

  public static float[][][] eval(NslFloat4 a) {
    return eval(a.getfloat4());
  }

  public static float[][][] eval(float[][][] dest, NslFloat4 a) {
    return eval(dest, a.getfloat4());
  }

//native 0d boolean
public static boolean eval(boolean a) {
	return a;
}
//native 1d boolean
public static boolean eval(boolean dest, boolean[]_data) {
	int i;
	dest=false;
	for (i=0; i<_data.length; i++) {
         dest=dest||_data[i];
}
return dest;
}

public static boolean eval(boolean[] _data) {
	boolean dest=false; 
      return (eval(dest,_data));
}

//native 2d boolean
public static  boolean[] eval(boolean[] dest, boolean[][] _data) {
    
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    
    if (dest.length != size1 ) {
        dest = new boolean[size1];
    }

    for (i=0; i<size1; i++) {
		dest[i]=false;
    }
    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++) {
          dest[i]=dest[i]||_data[i][j];
	  }
    }
    return dest;
  }

public static  boolean[] eval(boolean[][] _data) {
	
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
	boolean[] dest =new boolean[size1];
	return (eval(dest,_data));
}

// native 3d boolean

public static  boolean[][] eval(boolean[][] dest, boolean[][][] _data) {
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
 
    if (dest.length != size1 || dest[0].length != size2) {
        dest = new boolean[size1][size2];
    }

    for (i=0; i<size1; i++) {
      for (j=0; j<size2; j++) {
	    dest[i][j]=false;
	}
    }

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { dest[i][j]=dest[i][j]||_data[i][j][k];}
       }
     }
    return dest;
  }
public static  boolean[][] eval(boolean[][][] _data) {
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;

	boolean[][] dest =new boolean[size1][size2];
	return (eval(dest,_data));
}

// native 4d boolean
  public static  boolean[][][] eval(boolean[][][] dest, boolean[][][][] _data) {
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    if (dest.length != size1 || dest[0].length != size2 || dest[0][0].length != size3) {
        dest = new boolean[size1][size2][size3];
    }
    for (i=0; i<size1; i++) {
      for (j=0; j<size2; j++) {
        for(k=0; k<size3; k++) {	
	    dest[i][j][k]=(false);
	  }
      }
    }


    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
  	 {
          for(l=0; l<size4; l++)	
           { dest[i][j][k]=dest[i][j][k]||_data[i][j][k][l];}
         } 
       }
     }
    return dest;
  }

public static  boolean[][][] eval(boolean[][][][] _data) {
	
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;

	boolean[][][] dest =new boolean[size1][size2][size3];
	return (eval(dest,_data));
}

//0d nsl boolean 

  public static boolean eval(NslBoolean0 a) {
    return eval(a.getboolean());
  }

//1d nsl boolean 

  public static boolean eval(NslBoolean1 a) {
    return eval(a.getboolean1());
  }
  
  public static boolean eval(boolean dest, NslBoolean1 a) {
    return eval(dest, a.getboolean1());
  }  
  
// 2d nsl boolean

  public static boolean[] eval(NslBoolean2 a) {
    return eval(a.getboolean2());
  }
  
  public static boolean[] eval(boolean[] dest, NslBoolean2 a) {
    return eval(dest, a.getboolean2());
  }

// 3d nsl boolean

  public static boolean[][] eval(NslBoolean3 a) {
    return eval(a.getboolean3());
  }
  
  public static boolean[][] eval(boolean[][] dest, NslBoolean3 a) {
    return eval(dest, a.getboolean3());
  }

// 4d nsl boolean

  public static boolean[][][] eval(NslBoolean4 a) {
    return eval(a.getboolean4());
  }
  
  public static boolean[][][] eval(boolean[][][] dest, NslBoolean4 a) {
    return eval(dest, a.getboolean4());
  }

//native 0d int

public static int eval(int a) {
	return a;
}

//native 1d int

public static int eval(int dest, int[]_data) {
	int i;
	dest=0;
	for (i=0; i<_data.length; i++) {
         dest=dest+_data[i];
}
return dest;
}

public static int eval(int[] _data) {
	int dest=0; 
      return (eval(dest,_data));
}

//native 2d int
public static  int[] eval(int[] dest, int[][] _data) {
    
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    
    if (dest.length != size1 ) {
        dest = new int[size1];
    }

    for (i=0; i<size1; i++) {
		dest[i]=0;
    }
    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++) {
          dest[i]=dest[i]+_data[i][j];
	  }
    }
    return dest;
  }

public static  int[] eval(int[][] _data) {
	
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
	int[] dest =new int[size1];
	return (eval(dest,_data));
}

// native 3d int

public static  int[][] eval(int[][] dest, int[][][] _data) {
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
 
    if (dest.length != size1 || dest[0].length != size2) {
        dest = new int[size1][size2];
    }

    for (i=0; i<size1; i++) {
      for (j=0; j<size2; j++) {
	    dest[i][j]=0;
	}
    }

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         { dest[i][j]=dest[i][j]+_data[i][j][k];}
       }
     }
    return dest;
  }

public static  int[][] eval(int[][][] _data) {
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;

	int[][] dest =new int[size1][size2];
	return (eval(dest,_data));
}

// native 4d int
  public static  int[][][] eval(int[][][] dest, int[][][][] _data) {
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    if (dest.length != size1 || dest[0].length != size2 || dest[0][0].length != size3) {
        dest = new int[size1][size2][size3];
    }
    for (i=0; i<size1; i++) {
      for (j=0; j<size2; j++) {
        for(k=0; k<size3; k++) {	
	    dest[i][j][k]=0;
	  }
      }
    }


    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
  	 {
          for(l=0; l<size4; l++)	
           { dest[i][j][k]=dest[i][j][k]+_data[i][j][k][l];}
         } 
       }
     }
    return dest;
  }

public static  int[][][] eval(int[][][][] _data) {
	
	int i,j,k;

    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;

	int[][][] dest =new int[size1][size2][size3];
	return (eval(dest,_data));
}
//0d nsl int 

  public static int eval(NslInt0 a) {
    return eval(a.getint());
  }

//1d nsl int 

  public static int eval(NslInt1 a) {
    return eval(a.getint1());
  }
  
  public static int eval(int dest, NslInt1 a) {
    return eval(dest, a.getint1());
  }
    
// 2d nsl int

  public static int[] eval(NslInt2 a) {
    return eval(a.getint2());
  }
  
  public static int[] eval(int[] dest, NslInt2 a) {
    return eval(dest, a.getint2());
  }

// 3d nsl int

  public static int[][] eval(NslInt3 a) {
    return eval(a.getint3());
  }
  
  public static int[][] eval(int[][] dest, NslInt3 a) {
    return eval(dest, a.getint3());
  }
  
// 4d nsl int

  public static int[][][] eval(NslInt4 a) {
    return eval(a.getint4());
  }
  
  public static int[][][] eval(int[][][] dest, NslInt4 a) {
    return eval(dest, a.getint4());
  }


}  // end NslSumRows





