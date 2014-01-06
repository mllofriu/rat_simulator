/*  SCCS - @(#)NslSome.java	1.3 - 09/01/99 - 00:18:24 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Some operator routines
//
//

package nslj.src.math;
import nslj.src.lang.*;

/**
 Some operator routines.
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

public final class NslSome {

// 0d native booleans
  
  public static boolean eval(boolean a) {
    return a;
  }
  
// 1d native booleans

  public static boolean eval(boolean[] a) {
    int i;
    int len = a.length;
    
    boolean dest = false;
    for (i=0; i<len; i++)
        dest = dest || a[i];
        
    return dest;
  }

// 2d native booleans

  public static boolean eval(boolean[][] a) {
    int i1, i2;
    int len1 = a.length;
    int len2 = a[0].length;

    boolean dest = false;
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            dest = dest || a[i1][i2];
            
    return dest;
  }

// 3d native booleans

  public static boolean eval(boolean[][][] a) {
    int i1, i2,i3;
    int len1 = a.length;
    int len2 = a[0].length;
    int len3 = a[0][0].length;

    boolean dest = false;
    for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	dest = dest || a[i1][i2][i3];
            	
    return dest;
  }

// 4d native booleans

  public static boolean eval(boolean[][][][] a) {
   int i1, i2, i3, i4;
   int len1 = a.length;
   int len2 = a[0].length;
   int len3 = a[0][0].length;
   int len4 = a[0][0][0].length;   

   boolean dest = false;
   for(i1=0; i1<len1; i1++)
        for(i2=0; i2<len2; i2++)
            for(i3=0; i3<len3; i3++)
            	for(i4=0; i4<len4; i4++)
            	    dest = dest || a[i1][i2][i3][i4];
            	    
    return dest;
  }

//0d nsl boolean 

  public static boolean eval(NslBoolean0 a) {
    return eval(a.getboolean());
  }

//1d nsl boolean 

  public static boolean eval(NslBoolean1 a) {
    return eval(a.getboolean1());
  }
    
// 2d nsl boolean

  public static boolean eval(NslBoolean2 a) {
    return eval(a.getboolean2());
  }
  
// 3d nsl boolean

  public static boolean eval(NslBoolean3 a) {
    return eval(a.getboolean3());
  }
  
// 4d nsl boolean

  public static boolean eval(NslBoolean4 a) {
    return eval(a.getboolean4());
  }
  
}






















