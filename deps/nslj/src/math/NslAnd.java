/* SCCS  @(#)NslAnd.java	1.3 --- 09/01/99 -- 00:18:19 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// And routines
//
//

/**
 And routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a, b) -> c
 a, b are the parameter to evaluate the boolean value of
 a and b pointwise and the result is passed out as c
 2. eval(dest, a, b) -> c
 a, b are the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

*/

package nslj.src.math;

import nslj.src.lang.*;


public final class NslAnd {

    public static boolean eval(boolean a, boolean b) {
        return a&&b;
    }

    public static boolean[] eval(boolean[]a, boolean[]b) {
        return eval(new boolean[a.length], a, b);
    }

    public static boolean[] eval(boolean[] dest, boolean[] a, boolean[] b) {
        int i;
        int len = dest.length;
        
	if (a.length != b.length) {
            System.out.println("NslAnd: boolean[] eval(boolean[] a, boolean[] b): Array size inconsistent");
            return dest;
        }

        if (len!=a.length || len!= b.length) {
            len  = a.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
             dest[i] = a[i]&&b[i];
        }
        return dest;
    }

    public static boolean[] eval(boolean[] a, boolean b) {
        return eval(new boolean[a.length], a, b);
    }

    public static boolean[] eval(boolean[] dest, boolean[] a, boolean b) {
        int i;
        int len = dest.length;

        if (len!=a.length) {
            len  = a.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
            dest[i] = a[i]&&b;
        }
        
        return dest;
    }
    
    public static boolean[] eval(boolean[] dest, boolean a, boolean[] b) {
        int i;
        int len = dest.length;

        if (len!=b.length) {
            len  = b.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
            dest[i] = a&&b[i];
        }
        
        return dest;
    }


    public static boolean[] eval(boolean a, boolean[] b) {
        return eval(new boolean[b.length], a, b);
    }


    public static boolean[][] eval(boolean[][] a, boolean[][] b) {
        return eval(new boolean[a.length][a[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, boolean[][] a, boolean[][] b) {
        int i1, i2;
        int len1 = dest.length;
        int len2 = dest[0].length;
        
	if (a.length != b.length || a[0].length != b[0].length) {
            System.out.println("NslAnd: boolean[][] eval(boolean[][] a, boolean[][] b): Array size inconsistent");
            return dest;
        }

        if (len1!=a.length || len1!= b.length ||
            len2!=a[0].length || len2!=b[0].length) {
            len1 = b.length;
            len2 = b[0].length;
            dest = new boolean[len1][len2];
        }

        for (i1=0; i1<len1; i1++) {
            for(i2=0; i2<len2; i2++){
                dest[i1][i2] = a[i1][i2]&&b[i1][i2];
            }
        }
        
        return dest;
    }

    public static boolean[][] eval(boolean[][] a, boolean b) {
        return eval(new boolean[a.length][a[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, boolean[][] a, boolean b) {
        int i1, i2;
        int len1 = dest.length;
        int len2 = dest[0].length;

        if (len1!=a.length || len2!=a[0].length) {
            len1 = a.length;
            len2 = a[0].length;
            dest = new boolean[len1][len2];
        }

        for (i1=0; i1<len1; i1++) {
            for(i2=0; i2<len2; i2++){
                dest[i1][i2] = a[i1][i2]&&b;
            }
        }
        
        return dest;
    }
    
    public static boolean[][] eval(boolean a, boolean[][] b) {
        return eval(new boolean[b.length][b[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, boolean a, boolean[][] b) {
        int i1, i2;
        int len1 = dest.length;
        int len2 = dest[0].length;

        if (len1!=b.length || len2!=b[0].length) {
            len1 = b.length;
            len2 = b[0].length;
            dest = new boolean[len1][len2];
        }

        for (i1=0; i1<len1; i1++) {
            for(i2=0; i2<len2; i2++){
                dest[i1][i2] = a&&b[i1][i2];
            }
        }
        
        return dest;
    }

    public static boolean[][][] eval(boolean[][][] a, boolean[][][] b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, boolean[][][] a, boolean[][][] b) {
        int i1, i2, i3;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        
	if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length) {
            System.out.println("NslAnd: boolean[][][] eval(boolean[][][] a, boolean[][][] b): Array size inconsistent");
            return dest;
        }

        if (len1!=a.length || len1!= b.length ||
            len2!=a[0].length || len2!=b[0].length||
            len3!=a[0][0].length || len3!=b[0][0].length) {
            len1 = b.length;
            len2 = b[0].length;
            len3 = b[0][0].length;
            dest = new boolean[len1][len2][len3];
        }

        for (i1=0; i1<len1; i1++) {
            for(i2=0; i2<len2; i2++){
                for(i3=0; i3<len3; i3++){
                    dest[i1][i2][i3] = a[i1][i2][i3]&&b[i1][i2][i3];
                }
            }
        }
        
        return dest;
    }

    public static boolean[][][] eval(boolean[][][] a, boolean b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, boolean[][][] a, boolean b) {
        int i1, i2, i3;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;

        if (len1!=a.length || len2!=a[0].length || len3!=a[0][0].length){
            len1 = a.length;
            len2 = a[0].length;
            len3 = a[0][0].length;
            dest = new boolean[len1][len2][len3];
        }

        for (i1=0; i1<len1; i1++) {
            for(i2=0; i2<len2; i2++){
                for(i3=0; i3<len3; i3++){
                    dest[i1][i2][i3] = a[i1][i2][i3]&&b;
                }
           }
        }
        
        return dest;
    }
    
    public static boolean[][][] eval(boolean a, boolean[][][] b) {
        return eval(new boolean[b.length][b[0].length][b[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, boolean a, boolean[][][] b) {
        int i1, i2, i3;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;

        if (len1!=b.length || len2!=b[0].length || len3!=b[0].length) {
            len1 = b.length;
            len2 = b[0].length;
            len3 = b[0][0].length;
            dest = new boolean[len1][len2][len3];
        }

        for (i1=0; i1<len1; i1++) {
            for(i2=0; i2<len2; i2++){
                for(i3=0; i3<len3; i3++){
                    dest[i1][i2][i3] = a&&b[i1][i2][i3];
                }
            }
        }
        
        return dest;
    }
    public static boolean[][][][] eval(boolean[][][][] a, boolean[][][][] b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, boolean[][][][] a, boolean[][][][] b) {
        int i1, i2, i3, i4;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        int len4 = dest[0][0][0].length;
        
	if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length
	    || a[0][0][0].length != b[0][0][0].length) {
            System.out.println("NslAnd: boolean[][][][] eval(boolean[][][][] a, boolean[][][][] b): Array size inconsistent");
            return dest;
        }

        if (len1!=a.length || len1!= b.length ||
            len2!=a[0].length || len2!=b[0].length ||
            len3!=a[0][0].length || len3!=b[0][0].length ||
            len4!=a[0][0][0].length || len4!=b[0][0][0].length) {
            len1 = b.length;
            len2 = b[0].length;
            len3 = b[0][0].length;
            len4 = b[0][0][0].length;
            dest = new boolean[len1][len2][len3][len4];
        }

        for (i1=0; i1<len1; i1++) {
            for(i2=0; i2<len2; i2++){
                for(i3=0; i3<len3; i3++){
                    for(i4=0; i4<len4; i4++){
                        dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]&&b[i1][i2][i3][i4];
                    }
                }
            }
        }
        
        return dest;
    }

    public static boolean[][][][] eval(boolean[][][][] a, boolean b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, boolean[][][][] a, boolean b) {
        int i1, i2, i3, i4;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        int len4 = dest[0][0][0].length;

        if (len1!=a.length || len2!=a[0].length 
            || len3!=a[0][0].length || len4!=a[0][0][0].length){
            len1 = a.length;
            len2 = a[0].length;
            len3 = a[0][0].length;
            len4 = a[0][0][0].length;
            dest = new boolean[len1][len2][len3][len4];
        }

        for (i1=0; i1<len1; i1++) {
            for(i2=0; i2<len2; i2++){
                for(i3=0; i3<len3; i3++){
                    for(i4=0; i4<len4; i4++){
                        dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]&&b;
                    }
                }
           }
        }
        
        return dest;
    }
    
    public static boolean[][][][] eval(boolean a, boolean[][][][] b) {
        return eval(new boolean[b.length][b[0].length][b[0][0].length][b[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, boolean a, boolean[][][][] b) {
        int i1, i2, i3, i4;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        int len4 = dest[0][0][0].length;

        if (len1!=b.length || len2!=b[0].length 
            || len3!=b[0][0].length || len4!=b[0][0][0].length) {
            len1 = b.length;
            len2 = b[0].length;
            len3 = b[0][0].length;
            len4 = b[0][0][0].length;
        }

        for (i1=0; i1<len1; i1++) {
            for(i2=0; i2<len2; i2++){
                for(i3=0; i3<len3; i3++){
                    for(i4=0; i4<len4; i4++){
                        dest[i1][i2][i3][i4] = a&&b[i1][i2][i3][i4];
                    }
                }
            }
        }
        
        return dest;
    }
    
    
    public static boolean eval(NslBoolean0 a, NslBoolean0 b) {
	return eval(a.getboolean(),b.getboolean());
    }

    public static boolean eval(NslBoolean0 a, boolean b) {
	return eval(a.getboolean(),b);
    }

    public static boolean eval(boolean a, NslBoolean0 b) {
	return eval(a,b.getboolean());
    }

    public static boolean[] eval(NslBoolean1 a, NslBoolean0 b) {
	return eval(a.getboolean1(),b.getboolean());
    }

    public static boolean[] eval(NslBoolean1 a, boolean b) {
	return eval(a.getboolean1(),b);
    }

    public static boolean[] eval(boolean[] a, NslBoolean0 b) {
	return eval(a,b.getboolean());
    }

    public static boolean[] eval(boolean[] dest, NslBoolean1 a, NslBoolean0 b) {
	return eval(dest,a.getboolean1(),b.getboolean());
    }

    public static boolean[] eval(boolean[] dest, NslBoolean1 a, boolean b) {
	return eval(dest,a.getboolean1(),b);
    }

    public static boolean[] eval(boolean[] dest, boolean[] a, NslBoolean0 b) {
	return eval(dest,a,b.getboolean());
    }

    public static boolean[][] eval(NslBoolean2 a, NslBoolean0 b) {
	return eval(a.getboolean2(),b.getboolean());
    }

    public static boolean[][] eval(NslBoolean2 a, boolean b) {
	return eval(a.getboolean2(),b);
    }

    public static boolean[][] eval(boolean[][] a, NslBoolean0 b) {
	return eval(a,b.getboolean());
    }

    public static boolean[][] eval(boolean[][] dest, NslBoolean2 a, NslBoolean0 b) {
	return eval(dest,a.getboolean2(),b.getboolean());
    }

    public static boolean[][] eval(boolean[][] dest, NslBoolean2 a, boolean b) {
	return eval(dest,a.getboolean2(),b);
    }

    public static boolean[][] eval(boolean[][] dest, boolean[][] a, NslBoolean0 b) {
	return eval(dest,a,b.getboolean());
    }

    public static boolean[][][] eval(NslBoolean3 a, NslBoolean0 b) {
	return eval(a.getboolean3(),b.getboolean());
    }

    public static boolean[][][] eval(NslBoolean3 a, boolean b) {
	return eval(a.getboolean3(),b);
    }

    public static boolean[][][] eval(boolean[][][] a, NslBoolean0 b) {
	return eval(a,b.getboolean());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslBoolean3 a, NslBoolean0 b) {
	return eval(dest,a.getboolean3(),b.getboolean());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslBoolean3 a, boolean b) {
	return eval(dest,a.getboolean3(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, boolean[][][] a, NslBoolean0 b) {
	return eval(dest,a,b.getboolean());
    }

    public static boolean[][][][] eval(NslBoolean4 a, NslBoolean0 b) {
	return eval(a.getboolean4(),b.getboolean());
    }

    public static boolean[][][][] eval(NslBoolean4 a, boolean b) {
	return eval(a.getboolean4(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] a, NslBoolean0 b) {
	return eval(a,b.getboolean());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslBoolean4 a, NslBoolean0 b) {
	return eval(dest,a.getboolean4(),b.getboolean());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslBoolean4 a, boolean b) {
	return eval(dest,a.getboolean4(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, boolean[][][][] a, NslBoolean0 b) {
	return eval(dest,a,b.getboolean());
    }

}
