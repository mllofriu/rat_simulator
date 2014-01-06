/*  SCCS -  */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/**
* NslRungeKutta.java
**/


package nslj.src.math;

import nslj.src.system.NslSystem;

/**
* Runge-Kutta's Differentiation methods
*/

public class NslDiffRungeKutta2 extends NslDiff {

    public NslDiffRungeKutta2(NslSystem sys) {
	super(sys);
    }    

    public NslDiffRungeKutta2(double dt, double tm) {
	super(dt,tm);
    }

    public double value(double out, double input, double dt, double tm) {
	double delta1 = dt/(2*tm);
	double delta2 = delta1*delta1;
	return (1-delta2)*out + delta1*input*(1+delta1);
    } 

    public float value(float out, float input, float dt, float tm) {
	float delta1 = dt/(2*tm);
	float delta2 = delta1*delta1;
	return (1-delta2)*out + delta1*input*(1+delta1);
    }

}


