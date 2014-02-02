/* SCCS  @(#)NslLeq.java	1.5 --- 09/01/99 -- 00:18:17 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Less equal routines
//
//

/**
 Less equal routines.
 There are two basic format for the evaluation method in
 this routine:
 1, eval(a, b) -> c
 a, b are the parameter to evaluate the maximum value of
 a and b pointwise and the result is passed out as c
 2. eval(dest, a, b) -> c
 a, b are the parameter of the evaluation function and
 <tt>dest</tt> is the temporary space to hold the result.
 The method returns the reference to <tt>dest</tt>.

*/

package nslj.src.math;

import nslj.src.lang.*;


public final class NslLeq {

    public static boolean eval(int a, int b) {
        return a<=b;
    }

    public static boolean[] eval(int[] a, int[] b) {
        return eval(new boolean[a.length], a, b);
    }

    public static boolean[] eval(boolean[] dest, int[] a, int[] b) {
        int i;
        int len = dest.length;
        
	if (a.length != b.length) {
            System.out.println("NslLeq: boolean[] eval(int[] a, int[] b): Array size inconsistent");
            return dest;
        }

        if (len!=a.length || len!= b.length) {
            len  = a.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
             dest[i] = a[i]<=b[i];
        }
        return dest;
    }

    public static boolean[] eval(int[] a, int b) {
        return eval(new boolean[a.length], a, b);
    }

    public static boolean[] eval(boolean[] dest, int[] a, int b) {
        int i;
        int len = dest.length;

        if (len!=a.length) {
            len  = a.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
            dest[i] = a[i]<=b;
        }
        
        return dest;
    }
    
    public static boolean[] eval(boolean[] dest, int a, int[] b) {
        int i;
        int len = dest.length;

        if (len!=b.length) {
            len  = b.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
            dest[i] = a<=b[i];
        }
        
        return dest;
    }


    public static boolean[] eval(int a, int[] b) {
        return eval(new boolean[b.length], a, b);
    }


    public static boolean[][] eval(int[][] a, int[][] b) {
        return eval(new boolean[a.length][a[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, int[][] a, int[][] b) {
        int i1, i2;
        int len1 = dest.length;
        int len2 = dest[0].length;
        
	if (a.length != b.length || a[0].length != b[0].length) {
            System.out.println("NslLeq: boolean[][] eval(int[][] a, int[][] b): Array size inconsistent");
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
                dest[i1][i2] = a[i1][i2]<=b[i1][i2];
            }
        }
        
        return dest;
    }

    public static boolean[][] eval(int[][] a, int b) {
        return eval(new boolean[a.length][a[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, int[][] a, int b) {
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
                dest[i1][i2] = a[i1][i2]<=b;
            }
        }
        
        return dest;
    }
    
    public static boolean[][] eval(int a, int[][] b) {
        return eval(new boolean[b.length][b[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, int a, int[][] b) {
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
                dest[i1][i2] = a<=b[i1][i2];
            }
        }
        
        return dest;
    }

    public static boolean[][][] eval(int[][][] a, int[][][] b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, int[][][] a, int[][][] b) {
        int i1, i2, i3;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        
	if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length) {
            System.out.println("NslLeq: boolean[][][] eval(int[][][] a, int[][][] b): Array size inconsistent");
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
                    dest[i1][i2][i3] = a[i1][i2][i3]<=b[i1][i2][i3];
                }
            }
        }
        
        return dest;
    }

    public static boolean[][][] eval(int[][][] a, int b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, int[][][] a, int b) {
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
                    dest[i1][i2][i3] = a[i1][i2][i3]<=b;
                }
           }
        }
        
        return dest;
    }
    
    public static boolean[][][] eval(int a, int[][][] b) {
        return eval(new boolean[b.length][b[0].length][b[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, int a, int[][][] b) {
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
                    dest[i1][i2][i3] = a<=b[i1][i2][i3];
                }
            }
        }
        
        return dest;
    }
    public static boolean[][][][] eval(int[][][][] a, int[][][][] b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, int[][][][] a, int[][][][] b) {
        int i1, i2, i3, i4;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        int len4 = dest[0][0][0].length;
        
	if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length
	    || a[0][0][0].length != b[0][0][0].length) {
            System.out.println("NslLeq: boolean[][][][] eval(int[][][][] a, int[][][][] b): Array size inconsistent");
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
                        dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]<=b[i1][i2][i3][i4];
                    }
                }
            }
        }
        
        return dest;
    }

    public static boolean[][][][] eval(int[][][][] a, int b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, int[][][][] a, int b) {
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
                        dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]<=b;
                    }
                }
           }
        }
        
        return dest;
    }
    
    public static boolean[][][][] eval(int a, int[][][][] b) {
        return eval(new boolean[b.length][b[0].length][b[0][0].length][b[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, int a, int[][][][] b) {
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
                        dest[i1][i2][i3][i4] = a<=b[i1][i2][i3][i4];
                    }
                }
            }
        }
        
        return dest;
    }

/* floats */

    public static boolean eval(float a, float b) {
        return a<=b;
    }

    public static boolean[] eval(float[]a, float[]b) {
        return eval(new boolean[a.length], a, b);
    }

    public static boolean[] eval(boolean[] dest, float[] a, float[] b) {
        int i;
        int len = dest.length;
        
	if (a.length != b.length) {
            System.out.println("NslLeq: boolean[] eval(float[] a, float[] b): Array size inconsistent");
            return dest;
        }

        if (len!=a.length || len!= b.length) {
            len  = a.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
             dest[i] = a[i]<=b[i];
        }
        return dest;
    }

    public static boolean[] eval(float[] a, float b) {
        return eval(new boolean[a.length], a, b);
    }

    public static boolean[] eval(boolean[] dest, float[] a, float b) {
        int i;
        int len = dest.length;

        if (len!=a.length) {
            len  = a.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
            dest[i] = a[i]<=b;
        }
        
        return dest;
    }
    
    public static boolean[] eval(boolean[] dest, float a, float[] b) {
        int i;
        int len = dest.length;

        if (len!=b.length) {
            len  = b.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
            dest[i] = a<=b[i];
        }
        
        return dest;
    }


    public static boolean[] eval(float a, float[] b) {
        return eval(new boolean[b.length], a, b);
    }


    public static boolean[][] eval(float[][] a, float[][] b) {
        return eval(new boolean[a.length][a[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, float[][] a, float[][] b) {
        int i1, i2;
        int len1 = dest.length;
        int len2 = dest[0].length;
        
	if (a.length != b.length || a[0].length != b[0].length) {
            System.out.println("NslLeq: boolean[][] eval(float[][] a, float[][] b): Array size inconsistent");
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
                dest[i1][i2] = a[i1][i2]<=b[i1][i2];
            }
        }
        
        return dest;
    }

    public static boolean[][] eval(float[][] a, float b) {
        return eval(new boolean[a.length][a[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, float[][] a, float b) {
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
                dest[i1][i2] = a[i1][i2]<=b;
            }
        }
        
        return dest;
    }
    
    public static boolean[][] eval(float a, float[][] b) {
        return eval(new boolean[b.length][b[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, float a, float[][] b) {
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
                dest[i1][i2] = a<=b[i1][i2];
            }
        }
        
        return dest;
    }

    public static boolean[][][] eval(float[][][] a, float[][][] b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, float[][][] a, float[][][] b) {
        int i1, i2, i3;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        
	if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length) {
            System.out.println("NslLeq: boolean[][][] eval(float[][][] a, float[][][] b): Array size inconsistent");
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
                    dest[i1][i2][i3] = a[i1][i2][i3]<=b[i1][i2][i3];
                }
            }
        }
        
        return dest;
    }

    public static boolean[][][] eval(float[][][] a, float b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, float[][][] a, float b) {
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
                    dest[i1][i2][i3] = a[i1][i2][i3]<=b;
                }
           }
        }
        
        return dest;
    }
    
    public static boolean[][][] eval(float a, float[][][] b) {
        return eval(new boolean[b.length][b[0].length][b[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, float a, float[][][] b) {
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
                    dest[i1][i2][i3] = a<=b[i1][i2][i3];
                }
            }
        }
        
        return dest;
    }
    public static boolean[][][][] eval(float[][][][] a, float[][][][] b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, float[][][][] a, float[][][][] b) {
        int i1, i2, i3, i4;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        int len4 = dest[0][0][0].length;
        
	if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length
	    || a[0][0][0].length != b[0][0][0].length) {
            System.out.println("NslLeq: boolean[][][][] eval(float[][][][] a, float[][][][] b): Array size inconsistent");
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
                        dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]<=b[i1][i2][i3][i4];
                    }
                }
            }
        }
        
        return dest;
    }

    public static boolean[][][][] eval(float[][][][] a, float b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, float[][][][] a, float b) {
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
                        dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]<=b;
                    }
                }
           }
        }
        
        return dest;
    }
    
    public static boolean[][][][] eval(float a, float[][][][] b) {
        return eval(new boolean[b.length][b[0].length][b[0][0].length][b[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, float a, float[][][][] b) {
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
                        dest[i1][i2][i3][i4] = a<=b[i1][i2][i3][i4];
                    }
                }
            }
        }
        
        return dest;
    }


/* doubles */

    public static boolean eval(double a, double b) {
        return a<=b;
    }

    public static boolean[] eval(double[]a, double[]b) {
        return eval(new boolean[a.length], a, b);
    }

    public static boolean[] eval(boolean[] dest, double[] a, double[] b) {
        int i;
        int len = dest.length;
        
	if (a.length != b.length) {
            System.out.println("NslLeq: boolean[] eval(double[] a, double[] b): Array size inconsistent");
            return dest;
        }

        if (len!=a.length || len!= b.length) {
            len  = a.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
             dest[i] = a[i]<=b[i];
        }
        return dest;
    }

    public static boolean[] eval(double[] a, double b) {
        return eval(new boolean[a.length], a, b);
    }

    public static boolean[] eval(boolean[] dest, double[] a, double b) {
        int i;
        int len = dest.length;

        if (len!=a.length) {
            len  = a.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
            dest[i] = a[i]<=b;
        }
        
        return dest;
    }
    
    public static boolean[] eval(boolean[] dest, double a, double[] b) {
        int i;
        int len = dest.length;

        if (len!=b.length) {
            len  = b.length;
            dest = new boolean[len];
        }

        for (i=0; i<len; i++) {
            dest[i] = a<=b[i];
        }
        
        return dest;
    }


    public static boolean[] eval(double a, double[] b) {
        return eval(new boolean[b.length], a, b);
    }


    public static boolean[][] eval(double[][] a, double[][] b) {
        return eval(new boolean[a.length][a[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, double[][] a, double[][] b) {
        int i1, i2;
        int len1 = dest.length;
        int len2 = dest[0].length;
        
	if (a.length != b.length || a[0].length != b[0].length) {
            System.out.println("NslLeq: boolean[][] eval(double[][] a, double[][] b): Array size inconsistent");
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
                dest[i1][i2] = a[i1][i2]<=b[i1][i2];
            }
        }
        
        return dest;
    }

    public static boolean[][] eval(double[][] a, double b) {
        return eval(new boolean[a.length][a[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, double[][] a, double b) {
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
                dest[i1][i2] = a[i1][i2]<=b;
            }
        }
        
        return dest;
    }
    
    public static boolean[][] eval(double a, double[][] b) {
        return eval(new boolean[b.length][b[0].length], a, b);
    }

    public static boolean[][] eval(boolean[][] dest, double a, double[][] b) {
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
                dest[i1][i2] = a<=b[i1][i2];
            }
        }
        
        return dest;
    }

    public static boolean[][][] eval(double[][][] a, double[][][] b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, double[][][] a, double[][][] b) {
        int i1, i2, i3;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        
	if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length) {
            System.out.println("NslLeq: boolean[][][] eval(double[][][] a, double[][][] b): Array size inconsistent");
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
                    dest[i1][i2][i3] = a[i1][i2][i3]<=b[i1][i2][i3];
                }
            }
        }
        
        return dest;
    }

    public static boolean[][][] eval(double[][][] a, double b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, double[][][] a, double b) {
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
                    dest[i1][i2][i3] = a[i1][i2][i3]<=b;
                }
           }
        }
        
        return dest;
    }
    
    public static boolean[][][] eval(double a, double[][][] b) {
        return eval(new boolean[b.length][b[0].length][b[0][0].length], a, b);
    }

    public static boolean[][][] eval(boolean[][][] dest, double a, double[][][] b) {
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
                    dest[i1][i2][i3] = a<=b[i1][i2][i3];
                }
            }
        }
        
        return dest;
    }
    public static boolean[][][][] eval(double[][][][] a, double[][][][] b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, double[][][][] a, double[][][][] b) {
        int i1, i2, i3, i4;
        int len1 = dest.length;
        int len2 = dest[0].length;
        int len3 = dest[0][0].length;
        int len4 = dest[0][0][0].length;
        
	if (a.length != b.length || a[0].length != b[0].length || a[0][0].length != b[0][0].length
	    || a[0][0][0].length != b[0][0][0].length) {
            System.out.println("NslLeq: boolean[][][][] eval(double[][][][] a, double[][][][] b): Array size inconsistent");
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
                        dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]<=b[i1][i2][i3][i4];
                    }
                }
            }
        }
        
        return dest;
    }

    public static boolean[][][][] eval(double[][][][] a, double b) {
        return eval(new boolean[a.length][a[0].length][a[0][0].length][a[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, double[][][][] a, double b) {
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
                        dest[i1][i2][i3][i4] = a[i1][i2][i3][i4]<=b;
                    }
                }
           }
        }
        
        return dest;
    }
    
    public static boolean[][][][] eval(double a, double[][][][] b) {
        return eval(new boolean[b.length][b[0].length][b[0][0].length][b[0][0][0].length], a, b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, double a, double[][][][] b) {
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
                        dest[i1][i2][i3][i4] = a<=b[i1][i2][i3][i4];
                    }
                }
            }
        }
        
        return dest;
    }

// NslNumerics

    public static boolean eval(NslInt0 a, NslInt0 b) {
	return eval(a.getint(),b.getint());
    }

    public static boolean eval(NslInt0 a, int b) {
	return eval(a.getint(),b);
    }

    public static boolean eval(int a, NslInt0 b) {
	return eval(a,b.getint());
    }

    public static boolean[] eval(NslInt1 a, NslInt0 b) {
	return eval(a.getint1(),b.getint());
    }

    public static boolean[] eval(NslInt1 a, int b) {
	return eval(a.getint1(),b);
    }

    public static boolean[] eval(int[] a, NslInt0 b) {
	return eval(a,b.getint());
    }

    public static boolean[] eval(boolean[] dest, NslInt1 a, NslInt0 b) {
	return eval(dest,a.getint1(),b.getint());
    }

    public static boolean[] eval(boolean[] dest, NslInt1 a, int b) {
	return eval(dest,a.getint1(),b);
    }

    public static boolean[] eval(boolean[] dest, int[] a, NslInt0 b) {
	return eval(dest,a,b.getint());
    }

    public static boolean[][] eval(NslInt2 a, NslInt0 b) {
	return eval(a.getint2(),b.getint());
    }

    public static boolean[][] eval(NslInt2 a, int b) {
	return eval(a.getint2(),b);
    }

    public static boolean[][] eval(int[][] a, NslInt0 b) {
	return eval(a,b.getint());
    }

    public static boolean[][] eval(boolean[][] dest, NslInt2 a, NslInt0 b) {
	return eval(dest,a.getint2(),b.getint());
    }

    public static boolean[][] eval(boolean[][] dest, NslInt2 a, int b) {
	return eval(dest,a.getint2(),b);
    }

    public static boolean[][] eval(boolean[][] dest, int[][] a, NslInt0 b) {
	return eval(dest,a,b.getint());
    }

    public static boolean[][][] eval(NslInt3 a, NslInt0 b) {
	return eval(a.getint3(),b.getint());
    }

    public static boolean[][][] eval(NslInt3 a, int b) {
	return eval(a.getint3(),b);
    }

    public static boolean[][][] eval(int[][][] a, NslInt0 b) {
	return eval(a,b.getint());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslInt3 a, NslInt0 b) {
	return eval(dest,a.getint3(),b.getint());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslInt3 a, int b) {
	return eval(dest,a.getint3(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, int[][][] a, NslInt0 b) {
	return eval(dest,a,b.getint());
    }

    public static boolean[][][][] eval(NslInt4 a, NslInt0 b) {
	return eval(a.getint4(),b.getint());
    }

    public static boolean[][][][] eval(NslInt4 a, int b) {
	return eval(a.getint4(),b);
    }

    public static boolean[][][][] eval(int[][][][] a, NslInt0 b) {
	return eval(a,b.getint());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslInt4 a, NslInt0 b) {
	return eval(dest,a.getint4(),b.getint());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslInt4 a, int b) {
	return eval(dest,a.getint4(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, int[][][][] a, NslInt0 b) {
	return eval(dest,a,b.getint());
    }

    public static boolean eval(NslFloat0 a, NslFloat0 b) {
	return eval(a.getfloat(),b.getfloat());
    }

    public static boolean eval(NslFloat0 a, float b) {
	return eval(a.getfloat(),b);
    }

    public static boolean eval(float a, NslFloat0 b) {
	return eval(a,b.getfloat());
    }

    public static boolean[] eval(NslFloat1 a, NslFloat0 b) {
	return eval(a.getfloat1(),b.getfloat());
    }

    public static boolean[] eval(NslFloat1 a, float b) {
	return eval(a.getfloat1(),b);
    }

    public static boolean[] eval(float[] a, NslFloat0 b) {
	return eval(a,b.getfloat());
    }

    public static boolean[] eval(boolean[] dest, NslFloat1 a, NslFloat0 b) {
	return eval(dest,a.getfloat1(),b.getfloat());
    }

    public static boolean[] eval(boolean[] dest, NslFloat1 a, float b) {
	return eval(dest,a.getfloat1(),b);
    }

    public static boolean[] eval(boolean[] dest, float[] a, NslFloat0 b) {
	return eval(dest,a,b.getfloat());
    }

    public static boolean[][] eval(NslFloat2 a, NslFloat0 b) {
	return eval(a.getfloat2(),b.getfloat());
    }

    public static boolean[][] eval(NslFloat2 a, float b) {
	return eval(a.getfloat2(),b);
    }

    public static boolean[][] eval(float[][] a, NslFloat0 b) {
	return eval(a,b.getfloat());
    }

    public static boolean[][] eval(boolean[][] dest, NslFloat2 a, NslFloat0 b) {
	return eval(dest,a.getfloat2(),b.getfloat());
    }

    public static boolean[][] eval(boolean[][] dest, NslFloat2 a, float b) {
	return eval(dest,a.getfloat2(),b);
    }

    public static boolean[][] eval(boolean[][] dest, float[][] a, NslFloat0 b) {
	return eval(dest,a,b.getfloat());
    }

    public static boolean[][][] eval(NslFloat3 a, NslFloat0 b) {
	return eval(a.getfloat3(),b.getfloat());
    }

    public static boolean[][][] eval(NslFloat3 a, float b) {
	return eval(a.getfloat3(),b);
    }

    public static boolean[][][] eval(float[][][] a, NslFloat0 b) {
	return eval(a,b.getfloat());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslFloat3 a, NslFloat0 b) {
	return eval(dest,a.getfloat3(),b.getfloat());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslFloat3 a, float b) {
	return eval(dest,a.getfloat3(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, float[][][] a, NslFloat0 b) {
	return eval(dest,a,b.getfloat());
    }

    public static boolean[][][][] eval(NslFloat4 a, NslFloat0 b) {
	return eval(a.getfloat4(),b.getfloat());
    }

    public static boolean[][][][] eval(NslFloat4 a, float b) {
	return eval(a.getfloat4(),b);
    }

    public static boolean[][][][] eval(float[][][][] a, NslFloat0 b) {
	return eval(a,b.getfloat());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslFloat4 a, NslFloat0 b) {
	return eval(dest,a.getfloat4(),b.getfloat());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslFloat4 a, float b) {
	return eval(dest,a.getfloat4(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, float[][][][] a, NslFloat0 b) {
	return eval(dest,a,b.getfloat());
    }

    public static boolean eval(NslDouble0 a, NslDouble0 b) {
	return eval(a.getdouble(),b.getdouble());
    }

    public static boolean eval(NslDouble0 a, double b) {
	return eval(a.getdouble(),b);
    }

    public static boolean eval(double a, NslDouble0 b) {
	return eval(a,b.getdouble());
    }

    public static boolean[] eval(NslDouble1 a, NslDouble0 b) {
	return eval(a.getdouble1(),b.getdouble());
    }

    public static boolean[] eval(NslDouble1 a, double b) {
	return eval(a.getdouble1(),b);
    }

    public static boolean[] eval(double[] a, NslDouble0 b) {
	return eval(a,b.getdouble());
    }

    public static boolean[] eval(boolean[] dest, NslDouble1 a, NslDouble0 b) {
	return eval(dest,a.getdouble1(),b.getdouble());
    }

    public static boolean[] eval(boolean[] dest, NslDouble1 a, double b) {
	return eval(dest,a.getdouble1(),b);
    }

    public static boolean[] eval(boolean[] dest, double[] a, NslDouble0 b) {
	return eval(dest,a,b.getdouble());
    }

    public static boolean[][] eval(NslDouble2 a, NslDouble0 b) {
	return eval(a.getdouble2(),b.getdouble());
    }

    public static boolean[][] eval(NslDouble2 a, double b) {
	return eval(a.getdouble2(),b);
    }

    public static boolean[][] eval(double[][] a, NslDouble0 b) {
	return eval(a,b.getdouble());
    }

    public static boolean[][] eval(boolean[][] dest, NslDouble2 a, NslDouble0 b) {
	return eval(dest,a.getdouble2(),b.getdouble());
    }

    public static boolean[][] eval(boolean[][] dest, NslDouble2 a, double b) {
	return eval(dest,a.getdouble2(),b);
    }

    public static boolean[][] eval(boolean[][] dest, double[][] a, NslDouble0 b) {
	return eval(dest,a,b.getdouble());
    }

    public static boolean[][][] eval(NslDouble3 a, NslDouble0 b) {
	return eval(a.getdouble3(),b.getdouble());
    }

    public static boolean[][][] eval(NslDouble3 a, double b) {
	return eval(a.getdouble3(),b);
    }

    public static boolean[][][] eval(double[][][] a, NslDouble0 b) {
	return eval(a,b.getdouble());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslDouble3 a, NslDouble0 b) {
	return eval(dest,a.getdouble3(),b.getdouble());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslDouble3 a, double b) {
	return eval(dest,a.getdouble3(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, double[][][] a, NslDouble0 b) {
	return eval(dest,a,b.getdouble());
    }

    public static boolean[][][][] eval(NslDouble4 a, NslDouble0 b) {
	return eval(a.getdouble4(),b.getdouble());
    }

    public static boolean[][][][] eval(NslDouble4 a, double b) {
	return eval(a.getdouble4(),b);
    }

    public static boolean[][][][] eval(double[][][][] a, NslDouble0 b) {
	return eval(a,b.getdouble());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslDouble4 a, NslDouble0 b) {
	return eval(dest,a.getdouble4(),b.getdouble());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslDouble4 a, double b) {
	return eval(dest,a.getdouble4(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, double[][][][] a, NslDouble0 b) {
	return eval(dest,a,b.getdouble());
    }    

    public static boolean[] eval(NslInt1 a, NslInt1 b) {
	return eval(a.getint1(),b.getint1());
    }

    public static boolean[] eval(int[] a, NslInt1 b) {
	return eval(a,b.getint1());
    }

    public static boolean[] eval(NslInt1 a, int[] b) {
	return eval(a.getint1(),b);
    }

    public static boolean[] eval(boolean[] dest, NslInt1 a, NslInt1 b) {
	return eval(dest, a.getint1(),b.getint1());
    }

    public static boolean[] eval(boolean[] dest, int[] a, NslInt1 b) {
	return eval(dest, a,b.getint1());
    }

    public static boolean[] eval(boolean[] dest, NslInt1 a, int[] b) {
	return eval(dest, a.getint1(),b);
    }
    
    public static boolean[] eval(NslFloat1 a, NslFloat1 b) {
	return eval(a.getfloat1(),b.getfloat1());
    }

    public static boolean[] eval(float[] a, NslFloat1 b) {
	return eval(a,b.getfloat1());
    }

    public static boolean[] eval(NslFloat1 a, float[] b) {
	return eval(a.getfloat1(),b);
    }

    public static boolean[] eval(boolean[] dest, NslFloat1 a, NslFloat1 b) {
	return eval(dest, a.getfloat1(),b.getfloat1());
    }

    public static boolean[] eval(boolean[] dest, float[] a, NslFloat1 b) {
	return eval(dest, a,b.getfloat1());
    }

    public static boolean[] eval(boolean[] dest, NslFloat1 a, float[] b) {
	return eval(dest, a.getfloat1(),b);
    }
    
    public static boolean[] eval(NslDouble1 a, NslDouble1 b) {
	return eval(a.getdouble1(),b.getdouble1());
    }

    public static boolean[] eval(double[] a, NslDouble1 b) {
	return eval(a,b.getdouble1());
    }

    public static boolean[] eval(NslDouble1 a, double[] b) {
	return eval(a.getdouble1(),b);
    }

    public static boolean[] eval(boolean[] dest, NslDouble1 a, NslDouble1 b) {
	return eval(dest, a.getdouble1(),b.getdouble1());
    }

    public static boolean[] eval(boolean[] dest, double[] a, NslDouble1 b) {
	return eval(dest, a,b.getdouble1());
    }

    public static boolean[] eval(boolean[] dest, NslDouble1 a, double[] b) {
	return eval(dest, a.getdouble1(),b);
    }
        
    public static boolean[][] eval(NslInt2 a, NslInt2 b) {
	return eval(a.getint2(),b.getint2());
    }

    public static boolean[][] eval(int[][] a, NslInt2 b) {
	return eval(a,b.getint2());
    }

    public static boolean[][] eval(NslInt2 a, int[][] b) {
	return eval(a.getint2(),b);
    }

    public static boolean[][] eval(boolean[][] dest, NslInt2 a, NslInt2 b) {
	return eval(dest, a.getint2(),b.getint2());
    }

    public static boolean[][] eval(boolean[][] dest, int[][] a, NslInt2 b) {
	return eval(dest, a,b.getint2());
    }

    public static boolean[][] eval(boolean[][] dest, NslInt2 a, int[][] b) {
	return eval(dest, a.getint2(),b);
    }
    
    public static boolean[][] eval(NslFloat2 a, NslFloat2 b) {
	return eval(a.getfloat2(),b.getfloat2());
    }

    public static boolean[][] eval(float[][] a, NslFloat2 b) {
	return eval(a,b.getfloat2());
    }

    public static boolean[][] eval(NslFloat2 a, float[][] b) {
	return eval(a.getfloat2(),b);
    }

    public static boolean[][] eval(boolean[][] dest, NslFloat2 a, NslFloat2 b) {
	return eval(dest, a.getfloat2(),b.getfloat2());
    }

    public static boolean[][] eval(boolean[][] dest, float[][] a, NslFloat2 b) {
	return eval(dest, a,b.getfloat2());
    }

    public static boolean[][] eval(boolean[][] dest, NslFloat2 a, float[][] b) {
	return eval(dest, a.getfloat2(),b);
    }
    
    public static boolean[][] eval(NslDouble2 a, NslDouble2 b) {
	return eval(a.getdouble2(),b.getdouble2());
    }

    public static boolean[][] eval(double[][] a, NslDouble2 b) {
	return eval(a,b.getdouble2());
    }

    public static boolean[][] eval(NslDouble2 a, double[][] b) {
	return eval(a.getdouble2(),b);
    }

    public static boolean[][] eval(boolean[][] dest, NslDouble2 a, NslDouble2 b) {
	return eval(dest, a.getdouble2(),b.getdouble2());
    }

    public static boolean[][] eval(boolean[][] dest, double[][] a, NslDouble2 b) {
	return eval(dest, a,b.getdouble2());
    }

    public static boolean[][] eval(boolean[][] dest, NslDouble2 a, double[][] b) {
	return eval(dest, a.getdouble2(),b);
    }
        
    public static boolean[][][] eval(NslInt3 a, NslInt3 b) {
	return eval(a.getint3(),b.getint3());
    }

    public static boolean[][][] eval(int[][][] a, NslInt3 b) {
	return eval(a,b.getint3());
    }

    public static boolean[][][] eval(NslInt3 a, int[][][] b) {
	return eval(a.getint3(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, NslInt3 a, NslInt3 b) {
	return eval(dest, a.getint3(),b.getint3());
    }

    public static boolean[][][] eval(boolean[][][] dest, int[][][] a, NslInt3 b) {
	return eval(dest, a,b.getint3());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslInt3 a, int[][][] b) {
	return eval(dest, a.getint3(),b);
    }
    
    public static boolean[][][] eval(NslFloat3 a, NslFloat3 b) {
	return eval(a.getfloat3(),b.getfloat3());
    }

    public static boolean[][][] eval(float[][][] a, NslFloat3 b) {
	return eval(a,b.getfloat3());
    }

    public static boolean[][][] eval(NslFloat3 a, float[][][] b) {
	return eval(a.getfloat3(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, NslFloat3 a, NslFloat3 b) {
	return eval(dest, a.getfloat3(),b.getfloat3());
    }

    public static boolean[][][] eval(boolean[][][] dest, float[][][] a, NslFloat3 b) {
	return eval(dest, a,b.getfloat3());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslFloat3 a, float[][][] b) {
	return eval(dest, a.getfloat3(),b);
    }
    
    public static boolean[][][] eval(NslDouble3 a, NslDouble3 b) {
	return eval(a.getdouble3(),b.getdouble3());
    }

    public static boolean[][][] eval(double[][][] a, NslDouble3 b) {
	return eval(a,b.getdouble3());
    }

    public static boolean[][][] eval(NslDouble3 a, double[][][] b) {
	return eval(a.getdouble3(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, NslDouble3 a, NslDouble3 b) {
	return eval(dest, a.getdouble3(),b.getdouble3());
    }

    public static boolean[][][] eval(boolean[][][] dest, double[][][] a, NslDouble3 b) {
	return eval(dest, a,b.getdouble3());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslDouble3 a, double[][][] b) {
	return eval(dest, a.getdouble3(),b);
    }
        
    public static boolean[][][][] eval(NslInt4 a, NslInt4 b) {
	return eval(a.getint4(),b.getint4());
    }

    public static boolean[][][][] eval(int[][][][] a, NslInt4 b) {
	return eval(a,b.getint4());
    }

    public static boolean[][][][] eval(NslInt4 a, int[][][][] b) {
	return eval(a.getint4(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslInt4 a, NslInt4 b) {
	return eval(dest, a.getint4(),b.getint4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, int[][][][] a, NslInt4 b) {
	return eval(dest, a,b.getint4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslInt4 a, int[][][][] b) {
	return eval(dest, a.getint4(),b);
    }
    
    public static boolean[][][][] eval(NslFloat4 a, NslFloat4 b) {
	return eval(a.getfloat4(),b.getfloat4());
    }

    public static boolean[][][][] eval(float[][][][] a, NslFloat4 b) {
	return eval(a,b.getfloat4());
    }

    public static boolean[][][][] eval(NslFloat4 a, float[][][][] b) {
	return eval(a.getfloat4(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslFloat4 a, NslFloat4 b) {
	return eval(dest, a.getfloat4(),b.getfloat4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, float[][][][] a, NslFloat4 b) {
	return eval(dest, a,b.getfloat4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslFloat4 a, float[][][][] b) {
	return eval(dest, a.getfloat4(),b);
    }
    
    public static boolean[][][][] eval(NslDouble4 a, NslDouble4 b) {
	return eval(a.getdouble4(),b.getdouble4());
    }

    public static boolean[][][][] eval(double[][][][] a, NslDouble4 b) {
	return eval(a,b.getdouble4());
    }

    public static boolean[][][][] eval(NslDouble4 a, double[][][][] b) {
	return eval(a.getdouble4(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslDouble4 a, NslDouble4 b) {
	return eval(dest, a.getdouble4(),b.getdouble4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, double[][][][] a, NslDouble4 b) {
	return eval(dest, a,b.getdouble4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslDouble4 a, double[][][][] b) {
	return eval(dest, a.getdouble4(),b);
    }
    
    public static boolean[] eval(NslInt0 a, NslInt1 b) {
	return eval(a.getint(),b.getint1());
    }

    public static boolean[] eval(int a, NslInt1 b) {
	return eval(a,b.getint1());
    }

    public static boolean[] eval(NslInt0 a, int[] b) {
	return eval(a.getint(),b);
    }

    public static boolean[] eval(boolean[] dest, NslInt0 a, NslInt1 b) {
	return eval(dest,a.getint(),b.getint1());
    }

    public static boolean[] eval(boolean[] dest, int a, NslInt1 b) {
	return eval(dest,a,b.getint1());
    }

    public static boolean[] eval(boolean[] dest, NslInt0 a, int[] b) {
	return eval(dest,a.getint(),b);
    }  
    
    public static boolean[] eval(NslFloat0 a, NslFloat1 b) {
	return eval(a.getfloat(),b.getfloat1());
    }

    public static boolean[] eval(float a, NslFloat1 b) {
	return eval(a,b.getfloat1());
    }

    public static boolean[] eval(NslFloat0 a, float[] b) {
	return eval(a.getfloat(),b);
    }

    public static boolean[] eval(boolean[] dest, NslFloat0 a, NslFloat1 b) {
	return eval(dest,a.getfloat(),b.getfloat1());
    }

    public static boolean[] eval(boolean[] dest, float a, NslFloat1 b) {
	return eval(dest,a,b.getfloat1());
    }

    public static boolean[] eval(boolean[] dest, NslFloat0 a, float[] b) {
	return eval(dest,a.getfloat(),b);
    }
    
    public static boolean[] eval(NslDouble0 a, NslDouble1 b) {
	return eval(a.getdouble(),b.getdouble1());
    }

    public static boolean[] eval(double a, NslDouble1 b) {
	return eval(a,b.getdouble1());
    }

    public static boolean[] eval(NslDouble0 a, double[] b) {
	return eval(a.getdouble(),b);
    }

    public static boolean[] eval(boolean[] dest, NslDouble0 a, NslDouble1 b) {
	return eval(dest,a.getdouble(),b.getdouble1());
    }

    public static boolean[] eval(boolean[] dest, double a, NslDouble1 b) {
	return eval(dest,a,b.getdouble1());
    }

    public static boolean[] eval(boolean[] dest, NslDouble0 a, double[] b) {
	return eval(dest,a.getdouble(),b);
    }
        
    public static boolean[][] eval(NslInt0 a, NslInt2 b) {
	return eval(a.getint(),b.getint2());
    }

    public static boolean[][] eval(int a, NslInt2 b) {
	return eval(a,b.getint2());
    }

    public static boolean[][] eval(NslInt0 a, int[][] b) {
	return eval(a.getint(),b);
    }

    public static boolean[][] eval(boolean[][] dest, NslInt0 a, NslInt2 b) {
	return eval(dest,a.getint(),b.getint2());
    }

    public static boolean[][] eval(boolean[][] dest, int a, NslInt2 b) {
	return eval(dest,a,b.getint2());
    }

    public static boolean[][] eval(boolean[][] dest, NslInt0 a, int[][] b) {
	return eval(dest,a.getint(),b);
    }  
    
    public static boolean[][] eval(NslFloat0 a, NslFloat2 b) {
	return eval(a.getfloat(),b.getfloat2());
    }

    public static boolean[][] eval(float a, NslFloat2 b) {
	return eval(a,b.getfloat2());
    }

    public static boolean[][] eval(NslFloat0 a, float[][] b) {
	return eval(a.getfloat(),b);
    }

    public static boolean[][] eval(boolean[][] dest, NslFloat0 a, NslFloat2 b) {
	return eval(dest,a.getfloat(),b.getfloat2());
    }

    public static boolean[][] eval(boolean[][] dest, float a, NslFloat2 b) {
	return eval(dest,a,b.getfloat2());
    }

    public static boolean[][] eval(boolean[][] dest, NslFloat0 a, float[][] b) {
	return eval(dest,a.getfloat(),b);
    }
    
    public static boolean[][] eval(NslDouble0 a, NslDouble2 b) {
	return eval(a.getdouble(),b.getdouble2());
    }

    public static boolean[][] eval(double a, NslDouble2 b) {
	return eval(a,b.getdouble2());
    }

    public static boolean[][] eval(NslDouble0 a, double[][] b) {
	return eval(a.getdouble(),b);
    }

    public static boolean[][] eval(boolean[][] dest, NslDouble0 a, NslDouble2 b) {
	return eval(dest,a.getdouble(),b.getdouble2());
    }

    public static boolean[][] eval(boolean[][] dest, double a, NslDouble2 b) {
	return eval(dest,a,b.getdouble2());
    }

    public static boolean[][] eval(boolean[][] dest, NslDouble0 a, double[][] b) {
	return eval(dest,a.getdouble(),b);
    }
    
    public static boolean[][][] eval(NslInt0 a, NslInt3 b) {
	return eval(a.getint(),b.getint3());
    }

    public static boolean[][][] eval(int a, NslInt3 b) {
	return eval(a,b.getint3());
    }

    public static boolean[][][] eval(NslInt0 a, int[][][] b) {
	return eval(a.getint(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, NslInt0 a, NslInt3 b) {
	return eval(dest,a.getint(),b.getint3());
    }

    public static boolean[][][] eval(boolean[][][] dest, int a, NslInt3 b) {
	return eval(dest,a,b.getint3());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslInt0 a, int[][][] b) {
	return eval(dest,a.getint(),b);
    }  
    
    public static boolean[][][] eval(NslFloat0 a, NslFloat3 b) {
	return eval(a.getfloat(),b.getfloat3());
    }

    public static boolean[][][] eval(float a, NslFloat3 b) {
	return eval(a,b.getfloat3());
    }

    public static boolean[][][] eval(NslFloat0 a, float[][][] b) {
	return eval(a.getfloat(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, NslFloat0 a, NslFloat3 b) {
	return eval(dest,a.getfloat(),b.getfloat3());
    }

    public static boolean[][][] eval(boolean[][][] dest, float a, NslFloat3 b) {
	return eval(dest,a,b.getfloat3());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslFloat0 a, float[][][] b) {
	return eval(dest,a.getfloat(),b);
    }
    
    public static boolean[][][] eval(NslDouble0 a, NslDouble3 b) {
	return eval(a.getdouble(),b.getdouble3());
    }

    public static boolean[][][] eval(double a, NslDouble3 b) {
	return eval(a,b.getdouble3());
    }

    public static boolean[][][] eval(NslDouble0 a, double[][][] b) {
	return eval(a.getdouble(),b);
    }

    public static boolean[][][] eval(boolean[][][] dest, NslDouble0 a, NslDouble3 b) {
	return eval(dest,a.getdouble(),b.getdouble3());
    }

    public static boolean[][][] eval(boolean[][][] dest, double a, NslDouble3 b) {
	return eval(dest,a,b.getdouble3());
    }

    public static boolean[][][] eval(boolean[][][] dest, NslDouble0 a, double[][][] b) {
	return eval(dest,a.getdouble(),b);
    }
        
    public static boolean[][][][] eval(NslInt0 a, NslInt4 b) {
	return eval(a.getint(),b.getint4());
    }

    public static boolean[][][][] eval(int a, NslInt4 b) {
	return eval(a,b.getint4());
    }

    public static boolean[][][][] eval(NslInt0 a, int[][][][] b) {
	return eval(a.getint(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslInt0 a, NslInt4 b) {
	return eval(dest,a.getint(),b.getint4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, int a, NslInt4 b) {
	return eval(dest,a,b.getint4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslInt0 a, int[][][][] b) {
	return eval(dest,a.getint(),b);
    }  
    
        public static boolean[][][][] eval(NslFloat0 a, NslFloat4 b) {
	return eval(a.getfloat(),b.getfloat4());
    }

    public static boolean[][][][] eval(float a, NslFloat4 b) {
	return eval(a,b.getfloat4());
    }

    public static boolean[][][][] eval(NslFloat0 a, float[][][][] b) {
	return eval(a.getfloat(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslFloat0 a, NslFloat4 b) {
	return eval(dest,a.getfloat(),b.getfloat4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, float a, NslFloat4 b) {
	return eval(dest,a,b.getfloat4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslFloat0 a, float[][][][] b) {
	return eval(dest,a.getfloat(),b);
    }
    
        public static boolean[][][][] eval(NslDouble0 a, NslDouble4 b) {
	return eval(a.getdouble(),b.getdouble4());
    }

    public static boolean[][][][] eval(double a, NslDouble4 b) {
	return eval(a,b.getdouble4());
    }

    public static boolean[][][][] eval(NslDouble0 a, double[][][][] b) {
	return eval(a.getdouble(),b);
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslDouble0 a, NslDouble4 b) {
	return eval(dest,a.getdouble(),b.getdouble4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, double a, NslDouble4 b) {
	return eval(dest,a,b.getdouble4());
    }

    public static boolean[][][][] eval(boolean[][][][] dest, NslDouble0 a, double[][][][] b) {
	return eval(dest,a.getdouble(),b);
    }
    

}


