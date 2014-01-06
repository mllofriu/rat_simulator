/* SCCS  %W% - %G% - %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Determinant routines
//
//

package nslj.src.math;
import nslj.src.lang.*;

public class NslDeterminant {

    private static final double TINY=1.0e-20;
    
    private static int LUDecomposition(double[][] a, int[] index) {
    	int i, imax, j, k, d, n;
    	double big, dum, sum, temp;
    	double vv[];
    	
    	n = a.length;
    	d = 1;

    	vv = new double[n];
    	for (i=0; i<n; i++) {
    	    big = 0.0;
    	    for (j=0; j<n; j++) {
    	    	if ((temp=Math.abs(a[i][j])) > big) big=temp;
    	    }
    	    if (big == 0.0) {
    	    	System.err.println("Singular matrix in rutine NslInverse");
    	    }
    	    vv[i] = 1.0/big;
    	}

    	imax=0;
    	for (j=0; j<n; j++) {
    	    for (i=0; i<j; i++) {
    	    	sum=a[i][j];
    	    	for (k=0; k<i; k++) {
    	    	    sum -= a[i][k]*a[k][j];
    	    	}
    	    	a[i][j]=sum;
    	    }
    	    big = 0.0;
    	    for (i=j; i<n; i++) {
    	    	sum = a[i][j];
    	    	for (k=0; k<j; k++) {
    	    	    sum -= a[i][k]*a[k][j];
    	    	}
    	    	a[i][j]=sum;
    	    	if ((dum=vv[i]*Math.abs(sum)) >= big) {
    	    	    big = dum;
    	    	    imax = i;
    	    	}
    	    }
    	
	    if (j != imax) {
    	    	for (k=0; k<n; k++) {
    	    	    dum = a[imax][k];
    	    	    a[imax][k] = a[j][k];
    	    	    a[j][k]=dum;
    	    	}
    	    	d=-d;
    	    	vv[imax] = vv[j];
    	    }
    	    	
    	    index[j] = imax;
    	
    	    if (a[j][j] == 0.0) {
    	    	a[j][j] = TINY;
    	    }
    	
    	    if (j != n) {
    	    	dum = 1.0/a[j][j];
    	    	for (i=j+1;i<n;i++) {
    	    	    a[i][j] *= dum;
    	        }
    	    }
    	    
    	}
    	
    	return d;
    }

    private static void LUBackSubstitution(double[][] a, int[] index, double[] b) {
        int i, ii=0, ip, j, n;
        double sum;
        
        n = a.length;
        
        for (i=0; i<n; i++) {
            ip = index[i];
            sum = b[ip];
            b[ip] = b[i];
            if (ii!=0) {            
            	for(j=ii; j<i-1; j++) {
            	    sum -= a[i][j]*b[j];
            	}
            } else {
            	if (sum!=0) {
            	    ii = i;
            	}
            }
            b[i] = sum;
        }
        
        for (i=n-1; i>=0; i--) {
            sum=b[i];
            for (j=i+1; j<n; j++) {
            	sum -= a[i][j]*b[j];
            }
            b[i] = sum/a[i][j];
        }
    }

    public static double eval(double[][] x) {

    	int j, d, n=x.length, index[];
    	
    	index = new int[n];
    	
    	d = LUDecomposition(x,index);
    	for (j=0; j<n; j++) {
    		d *= x[j][j];
    	}
    	
    	return d;
    }

    public static float eval(float[][] x) {
	return (float)eval(floatToDouble(x));
    }

    public static int eval(int[][] x) {
	return (int)eval(intToDouble(x));
    }
    

    public static double eval(NslDouble2 x) {
        return eval(x.getdouble2());
    }
    
    public static float eval(NslFloat2 x) {
        return eval(x.getfloat2());
    }
    
    public static int eval(NslInt2 x) {
        return eval(x.getint2());
    }
    
    private static double[][] floatToDouble(float[][] x) {
        int size1 = x.length, 
            size2 = x[0].length;
    	
    	double [][]temp = new double[size1][size2];
    	
    	for (int i=0; i<size1; i++) {
    	    for (int j=0; j<size2; j++) {
    	        temp[i][j] = (double)x[i][j];
    	    }
    	}
    	
    	return temp;
    }
    
    private static double[][] intToDouble(int[][] x) {
        int size1 = x.length, 
            size2 = x[0].length;
    	
    	double [][]temp = new double[size1][size2];
    	
    	for (int i=0; i<size1; i++) {
    	    for (int j=0; j<size2; j++) {
    	        temp[i][j] = (double)x[i][j];
    	    }
    	}
    	
    	return temp;
    }
}
