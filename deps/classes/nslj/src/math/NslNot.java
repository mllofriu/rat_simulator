/*  SCCS - @(#)NslNot.java	1.3 - 09/01/99 - 00:18:22 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Not operator routines
//
//

package nslj.src.math;
import nslj.src.lang.*;

/**
 Not operator routines.
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

public final class NslNot {

// 0d native booleans
  
  public static boolean eval(boolean a) {
    return !a;
  }
  
// 1d native booleans

  public static boolean[] eval(boolean[] a) {
    boolean[] dest = new boolean[a.length];
    return eval(dest, a);
  }

  public static boolean[] eval(boolean[] dest, boolean[] a) {
    int i;
    int len = dest.length;
    if (len != a.length ) {
        len = a.length;
        dest = new boolean[len];
    }
    for (i=0; i<len; i++)
        dest[i] = !a[i];
    return dest;
  }

// 2d native booleans

  public static boolean[][] eval(boolean[][] a) {
    boolean[][] dest = new boolean[a.length][a[0].length];
    return eval(dest, a);
  }

  public static boolean[][] eval(boolean[][] dest, boolean[][] a) {
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
            dest[i1][i2]=!a[i1][i2];
    return dest;
  }

// 3d native booleans

  public static boolean[][][] eval(boolean[][][] a) {
    boolean[][][] dest = new boolean[a.length][a[0].length][a[0][0].length];
    return eval(dest, a);
  }

  public static boolean[][][] eval(boolean[][][] dest, boolean[][][] a) {
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
            	dest[i1][i2][i3]=!a[i1][i2][i3];
    return dest;
  }

// 4d native booleans

  public static boolean[][][][] eval(boolean[][][][] a) {
    boolean[][][][] dest = new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length];
    return eval(dest, a);
  }

  public static boolean[][][][] eval(boolean[][][][] dest, boolean[][][][] a) {
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
            	    dest[i1][i2][i3][i4]=!a[i1][i2][i3][i4];
    return dest;
  }

//0d nsl boolean 

  public static boolean eval(NslBoolean0 a) {
    return eval(a.getboolean());
  }

//1d nsl boolean 

  public static boolean[] eval(NslBoolean1 a) {
    return eval(a.getboolean1());
  }
  
  public static boolean[] eval(boolean[] dest, NslBoolean1 a) {
    return eval(dest, a.getboolean1());
  }  
  
// 2d nsl boolean

  public static boolean[][] eval(NslBoolean2 a) {
    return eval(a.getboolean2());
  }
  
  public static boolean[][] eval(boolean[][] dest, NslBoolean2 a) {
    return eval(dest, a.getboolean2());
  }

// 3d nsl boolean

  public static boolean[][][] eval(NslBoolean3 a) {
    return eval(a.getboolean3());
  }
  
  public static boolean[][][] eval(boolean[][][] dest, NslBoolean3 a) {
    return eval(dest, a.getboolean3());
  }

// 4d nsl boolean

  public static boolean[][][][] eval(NslBoolean4 a) {
    return eval(a.getboolean4());
  }
  
  public static boolean[][][][] eval(boolean[][][][] dest, NslBoolean4 a) {
    return eval(dest, a.getboolean4());
  }
}






















