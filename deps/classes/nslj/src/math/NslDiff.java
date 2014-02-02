/* SCCS @(#)NslDiff.java	1.7 --- %% -- %% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslDiff.java,v $
 * Revision 1.2  1997/06/13 22:47:10  erhan
 * 'NslMain takes model name as a parameter, npp:verbatim_on verbatim_off semi-column stuff, NslModule: method to setup display variables '
 *
 * Revision 1.1.1.1  1997/03/12 22:52:20  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:40  nsl
 *  Imported the Source directory
 *
*/
//
// NslDiff.java
//
//////////////////////////////////////////////////////////////////////
/**
* Differentiation methods. Refer to nsl_diff_method.C in NSL2.8
*/
package nslj.src.math;

import nslj.src.lang.*;
import nslj.src.system.NslSystem;

public abstract class NslDiff {

    double _dt = 0.1; //Approximation Delta
    double _tm = 1.0; //TimeConstant

    NslSystem system;

    /**
     * Constructor: set default parameter of the differentiation
     * method from the system.
     * @param sys - system it belongs
     */

    public NslDiff(NslSystem sys) {
	system=sys;
	_dt=system.nslGetApproximationDelta();
	_tm=system.nslGetApproximationTimeConstant();
    }    

    /**
     * Constructor.
     * @param dt - default delta or run step size
     * @param tm - default time constant
     */

    public NslDiff(double dt, double tm) {
	_dt = dt;
	_tm = tm;
    }

    public void setDelta(double step) {
	_dt = step;
    }

    public void nslSetApproximationDelta(double time) {
	_dt = time;
    }

    public void nslSetApproximationTimeConstant(double time) {
	_tm = time;
    }

    // 0d doubles

    public abstract double value(double out, double input, double dt, double tm);

    public double eval(double out, double inputexpr) {
	return value(out, inputexpr, _dt, _tm);
    }

    public double eval(double out, double tm, double inputexpr) {
	return value(out, inputexpr, _dt, tm);
    }

    public double eval(double out, double tm, double dt, double inputexpr) {
	return value(out, inputexpr, dt, tm);
    }

    public double eval(NslModule module, double out, double inputexpr) {
	return value(out, inputexpr, module.nslGetDelta(), module.nslGetApproximationDelta());
    }

    public double eval(NslModule module, double out, double tm, double inputexpr) {
	return value(out, inputexpr, module.nslGetDelta(), tm);
    }

    // 1d doubles

    public double[] eval(double[] out, double[] inputexpr) {
	return eval(out, _tm, _dt, inputexpr);
    }

    public double[] eval(double[] out, double tm, double[] inputexpr) {
	return eval(out, tm, _dt, inputexpr);
    }

    public double[] eval(double[] out, double tm, double dt, double[] inputexpr) {

	int length = out.length;
	if (length!=inputexpr.length) {
		System.err.println("NslDiffEuler: different length for LHS("+
			 length+") and RHS("+inputexpr.length+")");
	}

	int i;
	for(i=0; i<length; i++) {
	    out[i]=value(out[i],inputexpr[i],dt,tm);
	}

	return out;	
    }

    public double[] eval(double[] out, NslModule module, double[] inputexpr) {
	return eval(out, module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr);
    }

    public double[] eval(double[] out, NslModule module, double tm, double[] inputexpr) {
	return eval(out, tm, module.nslGetDelta(), inputexpr);
    }

    // 2d doubles

    public double[][] eval(double[][] out, double[][] inputexpr) {
	return eval(out, _tm, _dt, inputexpr);
    }

    public double[][] eval(double[][] out, double tm, double[][] inputexpr) {
	return eval(out, tm, _dt, inputexpr);
    }

    public double[][] eval(double[][] out, double tm, double dt, double[][] inputexpr) {

	int length1 = out.length;
	int length2 = out[0].length;
	if (length1!=inputexpr.length || length2!=inputexpr[0].length) {
		System.err.println("NslDiffEuler: different length for LHS("+
			 length1+","+length2+") and RHS("+inputexpr.length+","+
			 inputexpr[0].length+")");
	}

	int i,j;
	for(i=0; i<length1; i++) {
	    for(j=0; j<length2; j++) {
		out[i][j]=value(out[i][j],inputexpr[i][j],dt,tm);
	    }
	}

	return out;	
    }

    public double[][] eval(double[][] out, NslModule module, double[][] inputexpr) {
	return eval(out, module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr);
    }

    public double[][] eval(double[][] out, NslModule module, double tm, double[][] inputexpr) {
	return eval(out, tm, module.nslGetDelta(), inputexpr);
    }

    // 3d doubles

    public double[][][] eval(double[][][] out, double[][][] inputexpr) {
	return eval(out, _tm, _dt, inputexpr);
    }

    public double[][][] eval(double[][][] out, double tm, double[][][] inputexpr) {
	return eval(out, tm, _dt, inputexpr);
    }

    public double[][][] eval(double[][][] out, double tm, double dt, double[][][] inputexpr) {

	int length1 = out.length;
	int length2 = out[0].length;
	int length3 = out[0][0].length;
	if (length1!=inputexpr.length || length2!=inputexpr[0].length || 
	    length3!=inputexpr[0][0].length) {
		System.err.println("NslDiffEuler: different length for LHS("+
			 length1+","+length2+","+length3+") and RHS("+inputexpr.length+","+
			 inputexpr[0].length+","+inputexpr[0][0].length+")");
	}

	int i,j,k;
	for(i=0; i<length1; i++) {
	    for(j=0; j<length2; j++) {
		for(k=0; k<length3; k++) {
		    out[i][j][k]=value(out[i][j][k],inputexpr[i][j][k],dt,tm);
		}
	    }
	}

	return out;	
    }

    public double[][][] eval(double[][][] out, NslModule module, double[][][] inputexpr) {
	return eval(out, module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr);
    }

    public double[][][] eval(double[][][] out, NslModule module, double tm, double[][][] inputexpr) {
	return eval(out, tm, module.nslGetDelta(), inputexpr);
    }

    // 4d doubles

    public double[][][][] eval(double[][][][] out, double[][][][] inputexpr) {
	return eval(out, _tm, _dt, inputexpr);
    }

    public double[][][][] eval(double[][][][] out, double tm, double[][][][] inputexpr) {
	return eval(out, tm, _dt, inputexpr);
    }

    public double[][][][] eval(double[][][][] out, double tm, double dt, double[][][][] inputexpr) {

	int length1 = out.length;
	int length2 = out[0].length;
	int length3 = out[0][0].length;
	int length4 = out[0][0][0].length;
	if (length1!=inputexpr.length || length2!=inputexpr[0].length) {
		System.err.println("NslDiffEuler: different length for LHS("+
			 length1+","+length2+","+length3+","+length4+") and RHS("+inputexpr.length+","+
			 inputexpr[0].length+","+inputexpr[0][0].length+","+inputexpr[0][0][0].length+")");
	}

	int i,j,k,l;
	for(i=0; i<length1; i++) {
	    for(j=0; j<length2; j++) {
		for(k=0; k<length3; k++) {
		    for(l=0; l<length4; l++) {
			out[i][j][k][l]=value(out[i][j][k][l],inputexpr[i][j][k][l],dt,tm);
		    }
		}
	    }
	}

	return out;	
    }

    public double[][][][] eval(double[][][][] out, NslModule module, double[][][][] inputexpr) {
	return eval(out, module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr);
    }

    public double[][][][] eval(double[][][][] out, NslModule module, double tm, double[][][][] inputexpr) {
	return eval(out, tm, module.nslGetDelta(), inputexpr);
    }

    // Nsl types

    // 0d doubles

    public double eval(NslDouble0 out, double inputexpr) {
	return eval(out.getdouble(), _tm, _dt, inputexpr);
    }

    public double eval(double out, NslDouble0 inputexpr) {
	return eval(out, _tm, _dt, inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslDouble0 inputexpr) {
	return eval(out.getdouble(), _tm, _dt, inputexpr.getdouble());
    }

    //

    public double eval(NslDouble0 out, double tm, double inputexpr) {
	return eval(out.getdouble(), tm, _dt, inputexpr);
    }

    public double eval(double out, double tm, NslDouble0 inputexpr) {
	return eval(out, tm, _dt, inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, double tm, NslDouble0 inputexpr) {
	return eval(out.getdouble(), tm, _dt, inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslDouble0 tm, double inputexpr) {
	return eval(out.getdouble(), tm.getdouble(), _dt, inputexpr);
    }

    public double eval(double out, NslDouble0 tm, NslDouble0 inputexpr) {
	return eval(out, tm.getdouble(), _dt, inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslDouble0 tm, NslDouble0 inputexpr) {
	return eval(out.getdouble(), tm.getdouble(), _dt, inputexpr.getdouble());
    }

    //

    public double eval(NslDouble0 out, double tm, double dt, double inputexpr) {
	return eval(out.getdouble(), tm, dt, inputexpr);
    }

    public double eval(double out, double tm, double dt, NslDouble0 inputexpr) {
	return eval(out, tm, dt, inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, double tm, double dt, NslDouble0 inputexpr) {
	return eval(out.getdouble(), tm, dt, inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslDouble0 tm, double dt, double inputexpr) {
	return eval(out.getdouble(), tm.getdouble(), dt, inputexpr);
    }

    public double eval(double out, NslDouble0 tm, double dt, NslDouble0 inputexpr) {
	return eval(out, tm.getdouble(), dt, inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslDouble0 tm, double dt, NslDouble0 inputexpr) {
	return eval(out.getdouble(), tm.getdouble(), dt, inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, double tm, NslDouble0 dt, double inputexpr) {
	return eval(out.getdouble(), tm, dt.getdouble(), inputexpr);
    }

    public double eval(double out, double tm, NslDouble0 dt, NslDouble0 inputexpr) {
	return eval(out, tm, dt.getdouble(), inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, double tm, NslDouble0 dt, NslDouble0 inputexpr) {
	return eval(out.getdouble(), tm, dt.getdouble(), inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslDouble0 tm, NslDouble0 dt, double inputexpr) {
	return eval(out.getdouble(), tm.getdouble(), dt.getdouble(), inputexpr);
    }

    public double eval(double out, NslDouble0 tm, NslDouble0 dt, NslDouble0 inputexpr) {
	return eval(out, tm.getdouble(), dt.getdouble(), inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslDouble0 tm, NslDouble0 dt, NslDouble0 inputexpr) {
	return eval(out.getdouble(), tm.getdouble(), dt.getdouble(), inputexpr.getdouble());
    }

    //

    public double eval(NslDouble0 out, NslModule module, double inputexpr) {
	return eval(out.getdouble(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr);
    }

    public double eval(double out, NslModule module, NslDouble0 inputexpr) {
	return eval(out, module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslModule module, NslDouble0 inputexpr) {
	return eval(out.getdouble(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble());
    }
    
    //

    public double eval(NslDouble0 out, NslModule module, double tm, double inputexpr) {
	return eval(out.getdouble(), tm, module.nslGetDelta(), inputexpr);
    }

    public double eval(double out, NslModule module, double tm, NslDouble0 inputexpr) {
	return eval(out, tm, module.nslGetDelta(), inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslModule module, double tm, NslDouble0 inputexpr) {
	return eval(out.getdouble(), tm, module.nslGetDelta(), inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslModule module, NslDouble0 tm, double inputexpr) {
	return eval(out.getdouble(), tm.getdouble(), module.nslGetDelta(), inputexpr);
    }

    public double eval(double out, NslModule module, NslDouble0 tm, NslDouble0 inputexpr) {
	return eval(out, tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble());
    }

    public double eval(NslDouble0 out, NslModule module, NslDouble0 tm, NslDouble0 inputexpr) {
	return eval(out.getdouble(), tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble());
    }

    // 1d doubles

    public double[] eval(NslDouble1 out, double[] inputexpr) {
	return eval(out.getdouble1(), _tm, _dt, inputexpr);
    }

    public double[] eval(double[] out, NslDouble1 inputexpr) {
	return eval(out, _tm, _dt, inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), _tm, _dt, inputexpr.getdouble1());
    }

    //

    public double[] eval(NslDouble1 out, double tm, double[] inputexpr) {
	return eval(out.getdouble1(), tm, _dt, inputexpr);
    }

    public double[] eval(double[] out, double tm, NslDouble1 inputexpr) {
	return eval(out, tm, _dt, inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, double tm, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), tm, _dt, inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslDouble0 tm, double[] inputexpr) {
	return eval(out.getdouble1(), tm.getdouble(), _dt, inputexpr);
    }

    public double[] eval(double[] out, NslDouble0 tm, NslDouble1 inputexpr) {
	return eval(out, tm.getdouble(), _dt, inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslDouble0 tm, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), tm.getdouble(), _dt, inputexpr.getdouble1());
    }

    //

    public double[] eval(NslDouble1 out, double tm, double dt, double[] inputexpr) {
	return eval(out.getdouble1(), tm, dt, inputexpr);
    }

    public double[] eval(double[] out, double tm, double dt, NslDouble1 inputexpr) {
	return eval(out, tm, dt, inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, double tm, double dt, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), tm, dt, inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslDouble0 tm, double dt, double[] inputexpr) {
	return eval(out.getdouble1(), tm.getdouble(), dt, inputexpr);
    }

    public double[] eval(double[] out, NslDouble0 tm, double dt, NslDouble1 inputexpr) {
	return eval(out, tm.getdouble(), dt, inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslDouble0 tm, double dt, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), tm.getdouble(), dt, inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, double tm, NslDouble0 dt, double[] inputexpr) {
	return eval(out.getdouble1(), tm, dt.getdouble(), inputexpr);
    }

    public double[] eval(double[] out, double tm, NslDouble0 dt, NslDouble1 inputexpr) {
	return eval(out, tm, dt.getdouble(), inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, double tm, NslDouble0 dt, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), tm, dt.getdouble(), inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslDouble0 tm, NslDouble0 dt, double[] inputexpr) {
	return eval(out.getdouble1(), tm.getdouble(), dt.getdouble(), inputexpr);
    }

    public double[] eval(double[] out, NslDouble0 tm, NslDouble0 dt, NslDouble1 inputexpr) {
	return eval(out, tm.getdouble(), dt.getdouble(), inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslDouble0 tm, NslDouble0 dt, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), tm.getdouble(), dt.getdouble(), inputexpr.getdouble1());
    }

    //

    public double[] eval(NslDouble1 out, NslModule module, double[] inputexpr) {
	return eval(out.getdouble1(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr);
    }

    public double[] eval(double[] out, NslModule module, NslDouble1 inputexpr) {
	return eval(out, module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslModule module, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble1());
    }
    
    //

    public double[] eval(NslDouble1 out, NslModule module, double tm, double[] inputexpr) {
	return eval(out.getdouble1(), tm, module.nslGetDelta(), inputexpr);
    }

    public double[] eval(double[] out, NslModule module, double tm, NslDouble1 inputexpr) {
	return eval(out, tm, module.nslGetDelta(), inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslModule module, double tm, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), tm, module.nslGetDelta(), inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslModule module, NslDouble0 tm, double[] inputexpr) {
	return eval(out.getdouble1(), tm.getdouble(), module.nslGetDelta(), inputexpr);
    }

    public double[] eval(double[] out, NslModule module, NslDouble0 tm, NslDouble1 inputexpr) {
	return eval(out, tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble1());
    }

    public double[] eval(NslDouble1 out, NslModule module, NslDouble0 tm, NslDouble1 inputexpr) {
	return eval(out.getdouble1(), tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble1());
    }

    // 2d doubles

    public double[][] eval(NslDouble2 out, double[][] inputexpr) {
	return eval(out.getdouble2(), _tm, _dt, inputexpr);
    }

    public double[][] eval(double[][] out, NslDouble2 inputexpr) {
	return eval(out, _tm, _dt, inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), _tm, _dt, inputexpr.getdouble2());
    }

    //

    public double[][] eval(NslDouble2 out, double tm, double[][] inputexpr) {
	return eval(out.getdouble2(), tm, _dt, inputexpr);
    }

    public double[][] eval(double[][] out, double tm, NslDouble2 inputexpr) {
	return eval(out, tm, _dt, inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, double tm, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), tm, _dt, inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslDouble0 tm, double[][] inputexpr) {
	return eval(out.getdouble2(), tm.getdouble(), _dt, inputexpr);
    }

    public double[][] eval(double[][] out, NslDouble0 tm, NslDouble2 inputexpr) {
	return eval(out, tm.getdouble(), _dt, inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslDouble0 tm, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), tm.getdouble(), _dt, inputexpr.getdouble2());
    }

    //

    public double[][] eval(NslDouble2 out, double tm, double dt, double[][] inputexpr) {
	return eval(out.getdouble2(), tm, dt, inputexpr);
    }

    public double[][] eval(double[][] out, double tm, double dt, NslDouble2 inputexpr) {
	return eval(out, tm, dt, inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, double tm, double dt, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), tm, dt, inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslDouble0 tm, double dt, double[][] inputexpr) {
	return eval(out.getdouble2(), tm.getdouble(), dt, inputexpr);
    }

    public double[][] eval(double[][] out, NslDouble0 tm, double dt, NslDouble2 inputexpr) {
	return eval(out, tm.getdouble(), dt, inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslDouble0 tm, double dt, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), tm.getdouble(), dt, inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, double tm, NslDouble0 dt, double[][] inputexpr) {
	return eval(out.getdouble2(), tm, dt.getdouble(), inputexpr);
    }

    public double[][] eval(double[][] out, double tm, NslDouble0 dt, NslDouble2 inputexpr) {
	return eval(out, tm, dt.getdouble(), inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, double tm, NslDouble0 dt, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), tm, dt.getdouble(), inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslDouble0 tm, NslDouble0 dt, double[][] inputexpr) {
	return eval(out.getdouble2(), tm.getdouble(), dt.getdouble(), inputexpr);
    }

    public double[][] eval(double[][] out, NslDouble0 tm, NslDouble0 dt, NslDouble2 inputexpr) {
	return eval(out, tm.getdouble(), dt.getdouble(), inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslDouble0 tm, NslDouble0 dt, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), tm.getdouble(), dt.getdouble(), inputexpr.getdouble2());
    }

    //

    public double[][] eval(NslDouble2 out, NslModule module, double[][] inputexpr) {
	return eval(out.getdouble2(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr);
    }

    public double[][] eval(double[][] out, NslModule module, NslDouble2 inputexpr) {
	return eval(out, module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslModule module, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble2());
    }
    
    //

    public double[][] eval(NslDouble2 out, NslModule module, double tm, double[][] inputexpr) {
	return eval(out.getdouble2(), tm, module.nslGetDelta(), inputexpr);
    }

    public double[][] eval(double[][] out, NslModule module, double tm, NslDouble2 inputexpr) {
	return eval(out, tm, module.nslGetDelta(), inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslModule module, double tm, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), tm, module.nslGetDelta(), inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslModule module, NslDouble0 tm, double[][] inputexpr) {
	return eval(out.getdouble2(), tm.getdouble(), module.nslGetDelta(), inputexpr);
    }

    public double[][] eval(double[][] out, NslModule module, NslDouble0 tm, NslDouble2 inputexpr) {
	return eval(out, tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble2());
    }

    public double[][] eval(NslDouble2 out, NslModule module, NslDouble0 tm, NslDouble2 inputexpr) {
	return eval(out.getdouble2(), tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble2());
    }

    // 3d doubles

    public double[][][] eval(NslDouble3 out, double[][][] inputexpr) {
	return eval(out.getdouble3(), _tm, _dt, inputexpr);
    }

    public double[][][] eval(double[][][] out, NslDouble3 inputexpr) {
	return eval(out, _tm, _dt, inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), _tm, _dt, inputexpr.getdouble3());
    }

    //

    public double[][][] eval(NslDouble3 out, double tm, double[][][] inputexpr) {
	return eval(out.getdouble3(), tm, _dt, inputexpr);
    }

    public double[][][] eval(double[][][] out, double tm, NslDouble3 inputexpr) {
	return eval(out, tm, _dt, inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, double tm, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), tm, _dt, inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslDouble0 tm, double[][][] inputexpr) {
	return eval(out.getdouble3(), tm.getdouble(), _dt, inputexpr);
    }

    public double[][][] eval(double[][][] out, NslDouble0 tm, NslDouble3 inputexpr) {
	return eval(out, tm.getdouble(), _dt, inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslDouble0 tm, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), tm.getdouble(), _dt, inputexpr.getdouble3());
    }

    //

    public double[][][] eval(NslDouble3 out, double tm, double dt, double[][][] inputexpr) {
	return eval(out.getdouble3(), tm, dt, inputexpr);
    }

    public double[][][] eval(double[][][] out, double tm, double dt, NslDouble3 inputexpr) {
	return eval(out, tm, dt, inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, double tm, double dt, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), tm, dt, inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslDouble0 tm, double dt, double[][][] inputexpr) {
	return eval(out.getdouble3(), tm.getdouble(), dt, inputexpr);
    }

    public double[][][] eval(double[][][] out, NslDouble0 tm, double dt, NslDouble3 inputexpr) {
	return eval(out, tm.getdouble(), dt, inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslDouble0 tm, double dt, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), tm.getdouble(), dt, inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, double tm, NslDouble0 dt, double[][][] inputexpr) {
	return eval(out.getdouble3(), tm, dt.getdouble(), inputexpr);
    }

    public double[][][] eval(double[][][] out, double tm, NslDouble0 dt, NslDouble3 inputexpr) {
	return eval(out, tm, dt.getdouble(), inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, double tm, NslDouble0 dt, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), tm, dt.getdouble(), inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslDouble0 tm, NslDouble0 dt, double[][][] inputexpr) {
	return eval(out.getdouble3(), tm.getdouble(), dt.getdouble(), inputexpr);
    }

    public double[][][] eval(double[][][] out, NslDouble0 tm, NslDouble0 dt, NslDouble3 inputexpr) {
	return eval(out, tm.getdouble(), dt.getdouble(), inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslDouble0 tm, NslDouble0 dt, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), tm.getdouble(), dt.getdouble(), inputexpr.getdouble3());
    }

    //

    public double[][][] eval(NslDouble3 out, NslModule module, double[][][] inputexpr) {
	return eval(out.getdouble3(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr);
    }

    public double[][][] eval(double[][][] out, NslModule module, NslDouble3 inputexpr) {
	return eval(out, module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslModule module, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble3());
    }
    
    //

    public double[][][] eval(NslDouble3 out, NslModule module, double tm, double[][][] inputexpr) {
	return eval(out.getdouble3(), tm, module.nslGetDelta(), inputexpr);
    }

    public double[][][] eval(double[][][] out, NslModule module, double tm, NslDouble3 inputexpr) {
	return eval(out, tm, module.nslGetDelta(), inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslModule module, double tm, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), tm, module.nslGetDelta(), inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslModule module, NslDouble0 tm, double[][][] inputexpr) {
	return eval(out.getdouble3(), tm.getdouble(), module.nslGetDelta(), inputexpr);
    }

    public double[][][] eval(double[][][] out, NslModule module, NslDouble0 tm, NslDouble3 inputexpr) {
	return eval(out, tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble3());
    }

    public double[][][] eval(NslDouble3 out, NslModule module, NslDouble0 tm, NslDouble3 inputexpr) {
	return eval(out.getdouble3(), tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble3());
    }

    // 4d doubles

    public double[][][][] eval(NslDouble4 out, double[][][][] inputexpr) {
	return eval(out.getdouble4(), _tm, _dt, inputexpr);
    }

    public double[][][][] eval(double[][][][] out, NslDouble4 inputexpr) {
	return eval(out, _tm, _dt, inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), _tm, _dt, inputexpr.getdouble4());
    }

    //

    public double[][][][] eval(NslDouble4 out, double tm, double[][][][] inputexpr) {
	return eval(out.getdouble4(), tm, _dt, inputexpr);
    }

    public double[][][][] eval(double[][][][] out, double tm, NslDouble4 inputexpr) {
	return eval(out, tm, _dt, inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, double tm, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), tm, _dt, inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslDouble0 tm, double[][][][] inputexpr) {
	return eval(out.getdouble4(), tm.getdouble(), _dt, inputexpr);
    }

    public double[][][][] eval(double[][][][] out, NslDouble0 tm, NslDouble4 inputexpr) {
	return eval(out, tm.getdouble(), _dt, inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslDouble0 tm, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), tm.getdouble(), _dt, inputexpr.getdouble4());
    }

    //

    public double[][][][] eval(NslDouble4 out, double tm, double dt, double[][][][] inputexpr) {
	return eval(out.getdouble4(), tm, dt, inputexpr);
    }

    public double[][][][] eval(double[][][][] out, double tm, double dt, NslDouble4 inputexpr) {
	return eval(out, tm, dt, inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, double tm, double dt, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), tm, dt, inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslDouble0 tm, double dt, double[][][][] inputexpr) {
	return eval(out.getdouble4(), tm.getdouble(), dt, inputexpr);
    }

    public double[][][][] eval(double[][][][] out, NslDouble0 tm, double dt, NslDouble4 inputexpr) {
	return eval(out, tm.getdouble(), dt, inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslDouble0 tm, double dt, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), tm.getdouble(), dt, inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, double tm, NslDouble0 dt, double[][][][] inputexpr) {
	return eval(out.getdouble4(), tm, dt.getdouble(), inputexpr);
    }

    public double[][][][] eval(double[][][][] out, double tm, NslDouble0 dt, NslDouble4 inputexpr) {
	return eval(out, tm, dt.getdouble(), inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, double tm, NslDouble0 dt, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), tm, dt.getdouble(), inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslDouble0 tm, NslDouble0 dt, double[][][][] inputexpr) {
	return eval(out.getdouble4(), tm.getdouble(), dt.getdouble(), inputexpr);
    }

    public double[][][][] eval(double[][][][] out, NslDouble0 tm, NslDouble0 dt, NslDouble4 inputexpr) {
	return eval(out, tm.getdouble(), dt.getdouble(), inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslDouble0 tm, NslDouble0 dt, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), tm.getdouble(), dt.getdouble(), inputexpr.getdouble4());
    }

    //

    public double[][][][] eval(NslDouble4 out, NslModule module, double[][][][] inputexpr) {
	return eval(out.getdouble4(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr);
    }

    public double[][][][] eval(double[][][][] out, NslModule module, NslDouble4 inputexpr) {
	return eval(out, module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslModule module, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), module.nslGetApproximationDelta(), module.nslGetDelta(), inputexpr.getdouble4());
    }
    
    //

    public double[][][][] eval(NslDouble4 out, NslModule module, double tm, double[][][][] inputexpr) {
	return eval(out.getdouble4(), tm, module.nslGetDelta(), inputexpr);
    }

    public double[][][][] eval(double[][][][] out, NslModule module, double tm, NslDouble4 inputexpr) {
	return eval(out, tm, module.nslGetDelta(), inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslModule module, double tm, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), tm, module.nslGetDelta(), inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslModule module, NslDouble0 tm, double[][][][] inputexpr) {
	return eval(out.getdouble4(), tm.getdouble(), module.nslGetDelta(), inputexpr);
    }

    public double[][][][] eval(double[][][][] out, NslModule module, NslDouble0 tm, NslDouble4 inputexpr) {
	return eval(out, tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble4());
    }

    public double[][][][] eval(NslDouble4 out, NslModule module, NslDouble0 tm, NslDouble4 inputexpr) {
	return eval(out.getdouble4(), tm.getdouble(), module.nslGetDelta(), inputexpr.getdouble4());
    }

    // floats

    // 0d floats

    public abstract float value(float out, float input, float dt, float tm);

    public float eval(float out, float inputexpr) {
	return value(out, inputexpr, (float)_dt, (float)_tm);
    }

    public float eval(float out, float tm, float inputexpr) {
	return value(out, inputexpr, (float)_dt, tm);
    }

    public float eval(float out, float tm, float dt, float inputexpr) {
	return value(out, inputexpr, (float)dt, tm);
    }

    public float eval(NslModule module, float out, float inputexpr) {
	return value(out, inputexpr, (float)module.nslGetDelta(), (float)module.nslGetApproximationDelta());
    }

    public float eval(NslModule module, float out, float tm, float inputexpr) {
	return value(out, inputexpr, (float)module.nslGetDelta(), tm);
    }

    // 1d floats

    public float[] eval(float[] out, float[] inputexpr) {
	return eval(out, (float)_tm, (float)_dt, inputexpr);
    }

    public float[] eval(float[] out, float tm, float[] inputexpr) {
	return eval(out, (float)tm, (float)_dt, inputexpr);
    }

    public float[] eval(float[] out, float tm, float dt, float[] inputexpr) {

	int length = out.length;
	if (length!=inputexpr.length) {
		System.err.println("NslDiffEuler: different length for LHS("+
			 length+") and RHS("+inputexpr.length+")");
	}

	int i;
	for(i=0; i<length; i++) {
	    out[i]=value(out[i],inputexpr[i],dt,tm);
	}

	return out;	
    }

    public float[] eval(float[] out, NslModule module, float[] inputexpr) {
	return eval(out, (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[] eval(float[] out, NslModule module, float tm, float[] inputexpr) {
	return eval(out, (float)tm, (float)module.nslGetDelta(), inputexpr);
    }

    // 2d floats

    public float[][] eval(float[][] out, float[][] inputexpr) {
	return eval(out, (float)_tm, (float)_dt, inputexpr);
    }

    public float[][] eval(float[][] out, float tm, float[][] inputexpr) {
	return eval(out, (float)tm, (float)_dt, inputexpr);
    }

    public float[][] eval(float[][] out, float tm, float dt, float[][] inputexpr) {

	int length1 = out.length;
	int length2 = out[0].length;
	if (length1!=inputexpr.length || length2!=inputexpr[0].length) {
		System.err.println("NslDiffEuler: different length for LHS("+
			 length1+","+length2+") and RHS("+inputexpr.length+","+
			 inputexpr[0].length+")");
	}


	int i,j;
	for(i=0; i<length1; i++) {
	    for(j=0; j<length2; j++) {
		out[i][j]=value(out[i][j],inputexpr[i][j],dt,tm);
	    }
	}

	return out;	
    }

    public float[][] eval(float[][] out, NslModule module, float[][] inputexpr) {
	return eval(out, (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[][] eval(float[][] out, NslModule module, float tm, float[][] inputexpr) {
	return eval(out, (float)tm, (float)module.nslGetDelta(), inputexpr);
    }

    // 3d floats

    public float[][][] eval(float[][][] out, float[][][] inputexpr) {
	return eval(out, (float)_tm, (float)_dt, inputexpr);
    }

    public float[][][] eval(float[][][] out, float tm, float[][][] inputexpr) {
	return eval(out, (float)tm, (float)_dt, inputexpr);
    }

    public float[][][] eval(float[][][] out, float tm, float dt, float[][][] inputexpr) {

	int length1 = out.length;
	int length2 = out[0].length;
	int length3 = out[0][0].length;
	if (length1!=inputexpr.length || length2!=inputexpr[0].length || 
	    length3!=inputexpr[0][0].length) {
		System.err.println("NslDiffEuler: different length for LHS("+
			 length1+","+length2+","+length3+") and RHS("+inputexpr.length+","+
			 inputexpr[0].length+","+inputexpr[0][0].length+")");
	}

	int i,j,k;
	for(i=0; i<length1; i++) {
	    for(j=0; j<length2; j++) {
		for(k=0; k<length3; k++) {
		    out[i][j][k]=value(out[i][j][k],inputexpr[i][j][k],dt,tm);
		}
	    }
	}

	return out;	
    }

    public float[][][] eval(float[][][] out, NslModule module, float[][][] inputexpr) {
	return eval(out, (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[][][] eval(float[][][] out, NslModule module, float tm, float[][][] inputexpr) {
	return eval(out, (float)tm, (float)module.nslGetDelta(), inputexpr);
    }

    // 4d floats

    public float[][][][] eval(float[][][][] out, float[][][][] inputexpr) {
	return eval(out, (float)_tm, (float)_dt, inputexpr);
    }

    public float[][][][] eval(float[][][][] out, float tm, float[][][][] inputexpr) {
	return eval(out, (float)tm, (float)_dt, inputexpr);
    }

    public float[][][][] eval(float[][][][] out, float tm, float dt, float[][][][] inputexpr) {

	int length1 = out.length;
	int length2 = out[0].length;
	int length3 = out[0][0].length;
	int length4 = out[0][0][0].length;
	if (length1!=inputexpr.length || length2!=inputexpr[0].length) {
		System.err.println("NslDiffEuler: different length for LHS("+
			 length1+","+length2+","+length3+","+length4+") and RHS("+inputexpr.length+","+
			 inputexpr[0].length+","+inputexpr[0][0].length+","+inputexpr[0][0][0].length+")");
	}

	int i,j,k,l;
	for(i=0; i<length1; i++) {
	    for(j=0; j<length2; j++) {
		for(k=0; k<length3; k++) {
		    for(l=0; l<length4; l++) {
			out[i][j][k][l]=value(out[i][j][k][l],inputexpr[i][j][k][l],dt,tm);
		    }
		}
	    }
	}

	return out;	
    }

    public float[][][][] eval(float[][][][] out, NslModule module, float[][][][] inputexpr) {
	return eval(out, (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[][][][] eval(float[][][][] out, NslModule module, float tm, float[][][][] inputexpr) {
	return eval(out, (float)tm, (float)module.nslGetDelta(), inputexpr);
    }

    // Nsl types

    // 0d floats

    public float eval(NslFloat0 out, float inputexpr) {
	return eval(out.getfloat(), (float)_tm, (float)_dt, inputexpr);
    }

    public float eval(float out, NslFloat0 inputexpr) {
	return eval(out, (float)_tm, (float)_dt, inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslFloat0 inputexpr) {
	return eval(out.getfloat(), (float)_tm, (float)_dt, inputexpr.getfloat());
    }

    //

    public float eval(NslFloat0 out, float tm, float inputexpr) {
	return eval(out.getfloat(), (float)tm, (float)_dt, inputexpr);
    }

    public float eval(float out, float tm, NslFloat0 inputexpr) {
	return eval(out, (float)tm, (float)_dt, inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, float tm, NslFloat0 inputexpr) {
	return eval(out.getfloat(), (float)tm, (float)_dt, inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslFloat0 tm, float inputexpr) {
	return eval(out.getfloat(), tm.getfloat(), (float)_dt, inputexpr);
    }

    public float eval(float out, NslFloat0 tm, NslFloat0 inputexpr) {
	return eval(out, tm.getfloat(), (float)_dt, inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslFloat0 tm, NslFloat0 inputexpr) {
	return eval(out.getfloat(), tm.getfloat(), (float)_dt, inputexpr.getfloat());
    }

    //

    public float eval(NslFloat0 out, float tm, float dt, float inputexpr) {
	return eval(out.getfloat(), (float)tm, (float)dt, inputexpr);
    }

    public float eval(float out, float tm, float dt, NslFloat0 inputexpr) {
	return eval(out, (float)tm, (float)dt, inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, float tm, float dt, NslFloat0 inputexpr) {
	return eval(out.getfloat(), (float)tm, (float)dt, inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslFloat0 tm, float dt, float inputexpr) {
	return eval(out.getfloat(), tm.getfloat(), (float)dt, inputexpr);
    }

    public float eval(float out, NslFloat0 tm, float dt, NslFloat0 inputexpr) {
	return eval(out, tm.getfloat(), (float)dt, inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslFloat0 tm, float dt, NslFloat0 inputexpr) {
	return eval(out.getfloat(), tm.getfloat(), (float)dt, inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, float tm, NslFloat0 dt, float inputexpr) {
	return eval(out.getfloat(), (float)tm, dt.getfloat(), inputexpr);
    }

    public float eval(float out, float tm, NslFloat0 dt, NslFloat0 inputexpr) {
	return eval(out, (float)tm, dt.getfloat(), inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, float tm, NslFloat0 dt, NslFloat0 inputexpr) {
	return eval(out.getfloat(), (float)tm, dt.getfloat(), inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslFloat0 tm, NslFloat0 dt, float inputexpr) {
	return eval(out.getfloat(), tm.getfloat(), dt.getfloat(), inputexpr);
    }

    public float eval(float out, NslFloat0 tm, NslFloat0 dt, NslFloat0 inputexpr) {
	return eval(out, tm.getfloat(), dt.getfloat(), inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslFloat0 tm, NslFloat0 dt, NslFloat0 inputexpr) {
	return eval(out.getfloat(), tm.getfloat(), dt.getfloat(), inputexpr.getfloat());
    }

    //

    public float eval(NslFloat0 out, NslModule module, float inputexpr) {
	return eval(out.getfloat(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr);
    }

    public float eval(float out, NslModule module, NslFloat0 inputexpr) {
	return eval(out, (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslModule module, NslFloat0 inputexpr) {
	return eval(out.getfloat(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat());
    }
    
    //

    public float eval(NslFloat0 out, NslModule module, float tm, float inputexpr) {
	return eval(out.getfloat(), (float)tm, (float)module.nslGetDelta(), inputexpr);
    }

    public float eval(float out, NslModule module, float tm, NslFloat0 inputexpr) {
	return eval(out, (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslModule module, float tm, NslFloat0 inputexpr) {
	return eval(out.getfloat(), (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslModule module, NslFloat0 tm, float inputexpr) {
	return eval(out.getfloat(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr);
    }

    public float eval(float out, NslModule module, NslFloat0 tm, NslFloat0 inputexpr) {
	return eval(out, tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat());
    }

    public float eval(NslFloat0 out, NslModule module, NslFloat0 tm, NslFloat0 inputexpr) {
	return eval(out.getfloat(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat());
    }

    // 1d floats

    public float[] eval(NslFloat1 out, float[] inputexpr) {
	return eval(out.getfloat1(), (float)_tm, (float)_dt, inputexpr);
    }

    public float[] eval(float[] out, NslFloat1 inputexpr) {
	return eval(out, (float)_tm, (float)_dt, inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), (float)_tm, (float)_dt, inputexpr.getfloat1());
    }

    //

    public float[] eval(NslFloat1 out, float tm, float[] inputexpr) {
	return eval(out.getfloat1(), (float)tm, (float)_dt, inputexpr);
    }

    public float[] eval(float[] out, float tm, NslFloat1 inputexpr) {
	return eval(out, (float)tm, (float)_dt, inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, float tm, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), (float)tm, (float)_dt, inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslFloat0 tm, float[] inputexpr) {
	return eval(out.getfloat1(), tm.getfloat(), (float)_dt, inputexpr);
    }

    public float[] eval(float[] out, NslFloat0 tm, NslFloat1 inputexpr) {
	return eval(out, tm.getfloat(), (float)_dt, inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslFloat0 tm, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), tm.getfloat(), (float)_dt, inputexpr.getfloat1());
    }

    //

    public float[] eval(NslFloat1 out, float tm, float dt, float[] inputexpr) {
	return eval(out.getfloat1(), (float)tm, (float)dt, inputexpr);
    }

    public float[] eval(float[] out, float tm, float dt, NslFloat1 inputexpr) {
	return eval(out, (float)tm, (float)dt, inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, float tm, float dt, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), (float)tm, (float)dt, inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslFloat0 tm, float dt, float[] inputexpr) {
	return eval(out.getfloat1(), tm.getfloat(), (float)dt, inputexpr);
    }

    public float[] eval(float[] out, NslFloat0 tm, float dt, NslFloat1 inputexpr) {
	return eval(out, tm.getfloat(), (float)dt, inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslFloat0 tm, float dt, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), tm.getfloat(), (float)dt, inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, float tm, NslFloat0 dt, float[] inputexpr) {
	return eval(out.getfloat1(), (float)tm, dt.getfloat(), inputexpr);
    }

    public float[] eval(float[] out, float tm, NslFloat0 dt, NslFloat1 inputexpr) {
	return eval(out, (float)tm, dt.getfloat(), inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, float tm, NslFloat0 dt, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), (float)tm, dt.getfloat(), inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslFloat0 tm, NslFloat0 dt, float[] inputexpr) {
	return eval(out.getfloat1(), tm.getfloat(), dt.getfloat(), inputexpr);
    }

    public float[] eval(float[] out, NslFloat0 tm, NslFloat0 dt, NslFloat1 inputexpr) {
	return eval(out, tm.getfloat(), dt.getfloat(), inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslFloat0 tm, NslFloat0 dt, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), tm.getfloat(), dt.getfloat(), inputexpr.getfloat1());
    }

    //

    public float[] eval(NslFloat1 out, NslModule module, float[] inputexpr) {
	return eval(out.getfloat1(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[] eval(float[] out, NslModule module, NslFloat1 inputexpr) {
	return eval(out, (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslModule module, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat1());
    }
    
    //

    public float[] eval(NslFloat1 out, NslModule module, float tm, float[] inputexpr) {
	return eval(out.getfloat1(), (float)tm, (float)module.nslGetDelta(), inputexpr);
    }

    public float[] eval(float[] out, NslModule module, float tm, NslFloat1 inputexpr) {
	return eval(out, (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslModule module, float tm, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslModule module, NslFloat0 tm, float[] inputexpr) {
	return eval(out.getfloat1(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[] eval(float[] out, NslModule module, NslFloat0 tm, NslFloat1 inputexpr) {
	return eval(out, tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat1());
    }

    public float[] eval(NslFloat1 out, NslModule module, NslFloat0 tm, NslFloat1 inputexpr) {
	return eval(out.getfloat1(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat1());
    }

    // 2d floats

    public float[][] eval(NslFloat2 out, float[][] inputexpr) {
	return eval(out.getfloat2(), (float)_tm, (float)_dt, inputexpr);
    }

    public float[][] eval(float[][] out, NslFloat2 inputexpr) {
	return eval(out, (float)_tm, (float)_dt, inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), (float)_tm, (float)_dt, inputexpr.getfloat2());
    }

    //

    public float[][] eval(NslFloat2 out, float tm, float[][] inputexpr) {
	return eval(out.getfloat2(), (float)tm, (float)_dt, inputexpr);
    }

    public float[][] eval(float[][] out, float tm, NslFloat2 inputexpr) {
	return eval(out, (float)tm, (float)_dt, inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, float tm, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), (float)tm, (float)_dt, inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslFloat0 tm, float[][] inputexpr) {
	return eval(out.getfloat2(), tm.getfloat(), (float)_dt, inputexpr);
    }

    public float[][] eval(float[][] out, NslFloat0 tm, NslFloat2 inputexpr) {
	return eval(out, tm.getfloat(), (float)_dt, inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslFloat0 tm, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), tm.getfloat(), (float)_dt, inputexpr.getfloat2());
    }

    //

    public float[][] eval(NslFloat2 out, float tm, float dt, float[][] inputexpr) {
	return eval(out.getfloat2(), (float)tm, (float)dt, inputexpr);
    }

    public float[][] eval(float[][] out, float tm, float dt, NslFloat2 inputexpr) {
	return eval(out, (float)tm, (float)dt, inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, float tm, float dt, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), (float)tm, (float)dt, inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslFloat0 tm, float dt, float[][] inputexpr) {
	return eval(out.getfloat2(), tm.getfloat(), (float)dt, inputexpr);
    }

    public float[][] eval(float[][] out, NslFloat0 tm, float dt, NslFloat2 inputexpr) {
	return eval(out, tm.getfloat(), (float)dt, inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslFloat0 tm, float dt, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), tm.getfloat(), (float)dt, inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, float tm, NslFloat0 dt, float[][] inputexpr) {
	return eval(out.getfloat2(), (float)tm, dt.getfloat(), inputexpr);
    }

    public float[][] eval(float[][] out, float tm, NslFloat0 dt, NslFloat2 inputexpr) {
	return eval(out, (float)tm, dt.getfloat(), inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, float tm, NslFloat0 dt, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), (float)tm, dt.getfloat(), inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslFloat0 tm, NslFloat0 dt, float[][] inputexpr) {
	return eval(out.getfloat2(), tm.getfloat(), dt.getfloat(), inputexpr);
    }

    public float[][] eval(float[][] out, NslFloat0 tm, NslFloat0 dt, NslFloat2 inputexpr) {
	return eval(out, tm.getfloat(), dt.getfloat(), inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslFloat0 tm, NslFloat0 dt, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), tm.getfloat(), dt.getfloat(), inputexpr.getfloat2());
    }

    //

    public float[][] eval(NslFloat2 out, NslModule module, float[][] inputexpr) {
	return eval(out.getfloat2(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[][] eval(float[][] out, NslModule module, NslFloat2 inputexpr) {
	return eval(out, (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslModule module, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat2());
    }
    
    //

    public float[][] eval(NslFloat2 out, NslModule module, float tm, float[][] inputexpr) {
	return eval(out.getfloat2(), (float)tm, (float)module.nslGetDelta(), inputexpr);
    }

    public float[][] eval(float[][] out, NslModule module, float tm, NslFloat2 inputexpr) {
	return eval(out, (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslModule module, float tm, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslModule module, NslFloat0 tm, float[][] inputexpr) {
	return eval(out.getfloat2(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[][] eval(float[][] out, NslModule module, NslFloat0 tm, NslFloat2 inputexpr) {
	return eval(out, tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat2());
    }

    public float[][] eval(NslFloat2 out, NslModule module, NslFloat0 tm, NslFloat2 inputexpr) {
	return eval(out.getfloat2(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat2());
    }

    // 3d floats

    public float[][][] eval(NslFloat3 out, float[][][] inputexpr) {
	return eval(out.getfloat3(), (float)_tm, (float)_dt, inputexpr);
    }

    public float[][][] eval(float[][][] out, NslFloat3 inputexpr) {
	return eval(out, (float)_tm, (float)_dt, inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), (float)_tm, (float)_dt, inputexpr.getfloat3());
    }

    //

    public float[][][] eval(NslFloat3 out, float tm, float[][][] inputexpr) {
	return eval(out.getfloat3(), (float)tm, (float)_dt, inputexpr);
    }

    public float[][][] eval(float[][][] out, float tm, NslFloat3 inputexpr) {
	return eval(out, (float)tm, (float)_dt, inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, float tm, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), (float)tm, (float)_dt, inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslFloat0 tm, float[][][] inputexpr) {
	return eval(out.getfloat3(), tm.getfloat(), (float)_dt, inputexpr);
    }

    public float[][][] eval(float[][][] out, NslFloat0 tm, NslFloat3 inputexpr) {
	return eval(out, tm.getfloat(), (float)_dt, inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslFloat0 tm, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), tm.getfloat(), (float)_dt, inputexpr.getfloat3());
    }

    //

    public float[][][] eval(NslFloat3 out, float tm, float dt, float[][][] inputexpr) {
	return eval(out.getfloat3(), (float)tm, (float)dt, inputexpr);
    }

    public float[][][] eval(float[][][] out, float tm, float dt, NslFloat3 inputexpr) {
	return eval(out, (float)tm, (float)dt, inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, float tm, float dt, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), (float)tm, (float)dt, inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslFloat0 tm, float dt, float[][][] inputexpr) {
	return eval(out.getfloat3(), tm.getfloat(), (float)dt, inputexpr);
    }

    public float[][][] eval(float[][][] out, NslFloat0 tm, float dt, NslFloat3 inputexpr) {
	return eval(out, tm.getfloat(), (float)dt, inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslFloat0 tm, float dt, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), tm.getfloat(), (float)dt, inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, float tm, NslFloat0 dt, float[][][] inputexpr) {
	return eval(out.getfloat3(), (float)tm, dt.getfloat(), inputexpr);
    }

    public float[][][] eval(float[][][] out, float tm, NslFloat0 dt, NslFloat3 inputexpr) {
	return eval(out, (float)tm, dt.getfloat(), inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, float tm, NslFloat0 dt, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), (float)tm, dt.getfloat(), inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslFloat0 tm, NslFloat0 dt, float[][][] inputexpr) {
	return eval(out.getfloat3(), tm.getfloat(), dt.getfloat(), inputexpr);
    }

    public float[][][] eval(float[][][] out, NslFloat0 tm, NslFloat0 dt, NslFloat3 inputexpr) {
	return eval(out, tm.getfloat(), dt.getfloat(), inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslFloat0 tm, NslFloat0 dt, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), tm.getfloat(), dt.getfloat(), inputexpr.getfloat3());
    }

    //

    public float[][][] eval(NslFloat3 out, NslModule module, float[][][] inputexpr) {
	return eval(out.getfloat3(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[][][] eval(float[][][] out, NslModule module, NslFloat3 inputexpr) {
	return eval(out, (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslModule module, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat3());
    }
    
    //

    public float[][][] eval(NslFloat3 out, NslModule module, float tm, float[][][] inputexpr) {
	return eval(out.getfloat3(), (float)tm, (float)module.nslGetDelta(), inputexpr);
    }

    public float[][][] eval(float[][][] out, NslModule module, float tm, NslFloat3 inputexpr) {
	return eval(out, (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslModule module, float tm, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslModule module, NslFloat0 tm, float[][][] inputexpr) {
	return eval(out.getfloat3(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[][][] eval(float[][][] out, NslModule module, NslFloat0 tm, NslFloat3 inputexpr) {
	return eval(out, tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat3());
    }

    public float[][][] eval(NslFloat3 out, NslModule module, NslFloat0 tm, NslFloat3 inputexpr) {
	return eval(out.getfloat3(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat3());
    }

    // 4d floats

    public float[][][][] eval(NslFloat4 out, float[][][][] inputexpr) {
	return eval(out.getfloat4(), (float)_tm, (float)_dt, inputexpr);
    }

    public float[][][][] eval(float[][][][] out, NslFloat4 inputexpr) {
	return eval(out, (float)_tm, (float)_dt, inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), (float)_tm, (float)_dt, inputexpr.getfloat4());
    }

    //

    public float[][][][] eval(NslFloat4 out, float tm, float[][][][] inputexpr) {
	return eval(out.getfloat4(), (float)tm, (float)_dt, inputexpr);
    }

    public float[][][][] eval(float[][][][] out, float tm, NslFloat4 inputexpr) {
	return eval(out, (float)tm, (float)_dt, inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, float tm, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), (float)tm, (float)_dt, inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslFloat0 tm, float[][][][] inputexpr) {
	return eval(out.getfloat4(), tm.getfloat(), (float)_dt, inputexpr);
    }

    public float[][][][] eval(float[][][][] out, NslFloat0 tm, NslFloat4 inputexpr) {
	return eval(out, tm.getfloat(), (float)_dt, inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslFloat0 tm, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), tm.getfloat(), (float)_dt, inputexpr.getfloat4());
    }

    //

    public float[][][][] eval(NslFloat4 out, float tm, float dt, float[][][][] inputexpr) {
	return eval(out.getfloat4(), (float)tm, (float)dt, inputexpr);
    }

    public float[][][][] eval(float[][][][] out, float tm, float dt, NslFloat4 inputexpr) {
	return eval(out, (float)tm, (float)dt, inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, float tm, float dt, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), (float)tm, (float)dt, inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslFloat0 tm, float dt, float[][][][] inputexpr) {
	return eval(out.getfloat4(), tm.getfloat(), (float)dt, inputexpr);
    }

    public float[][][][] eval(float[][][][] out, NslFloat0 tm, float dt, NslFloat4 inputexpr) {
	return eval(out, tm.getfloat(), (float)dt, inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslFloat0 tm, float dt, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), tm.getfloat(), (float)dt, inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, float tm, NslFloat0 dt, float[][][][] inputexpr) {
	return eval(out.getfloat4(), (float)tm, dt.getfloat(), inputexpr);
    }

    public float[][][][] eval(float[][][][] out, float tm, NslFloat0 dt, NslFloat4 inputexpr) {
	return eval(out, (float)tm, dt.getfloat(), inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, float tm, NslFloat0 dt, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), (float)tm, dt.getfloat(), inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslFloat0 tm, NslFloat0 dt, float[][][][] inputexpr) {
	return eval(out.getfloat4(), tm.getfloat(), dt.getfloat(), inputexpr);
    }

    public float[][][][] eval(float[][][][] out, NslFloat0 tm, NslFloat0 dt, NslFloat4 inputexpr) {
	return eval(out, tm.getfloat(), dt.getfloat(), inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslFloat0 tm, NslFloat0 dt, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), tm.getfloat(), dt.getfloat(), inputexpr.getfloat4());
    }

    //

    public float[][][][] eval(NslFloat4 out, NslModule module, float[][][][] inputexpr) {
	return eval(out.getfloat4(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[][][][] eval(float[][][][] out, NslModule module, NslFloat4 inputexpr) {
	return eval(out, (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslModule module, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), (float)module.nslGetApproximationDelta(), (float)module.nslGetDelta(), inputexpr.getfloat4());
    }
    
    //

    public float[][][][] eval(NslFloat4 out, NslModule module, float tm, float[][][][] inputexpr) {
	return eval(out.getfloat4(), (float)tm, (float)module.nslGetDelta(), inputexpr);
    }

    public float[][][][] eval(float[][][][] out, NslModule module, float tm, NslFloat4 inputexpr) {
	return eval(out, (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslModule module, float tm, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), (float)tm, (float)module.nslGetDelta(), inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslModule module, NslFloat0 tm, float[][][][] inputexpr) {
	return eval(out.getfloat4(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr);
    }

    public float[][][][] eval(float[][][][] out, NslModule module, NslFloat0 tm, NslFloat4 inputexpr) {
	return eval(out, tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat4());
    }

    public float[][][][] eval(NslFloat4 out, NslModule module, NslFloat0 tm, NslFloat4 inputexpr) {
	return eval(out.getfloat4(), tm.getfloat(), (float)module.nslGetDelta(), inputexpr.getfloat4());
    }


}

