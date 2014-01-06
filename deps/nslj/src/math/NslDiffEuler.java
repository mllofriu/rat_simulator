/*  SCCS @(#)NslDiffEuler.java	1.8 --- 09/01/99 - - 17:27:02 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslDiffEuler.java,v $
 * Revision 1.2  1997/06/13 22:47:11  erhan
 *
 * Revision 1.1.1.1  1997/03/12 22:52:20  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:40  nsl
 *  Imported the Source directory
 *
*/

/**
* NslDiffEuler.java
*  tm(dm/dt)= -m(t) +Sm(t)
*  inputexpr = -m(t) +Sm(t)
*  m(t+delta_t)=  m(t) + (delta_t/tm)*inputexpr
*  m(t+delta_t)=  m(t) + (delta_t/tm)*(-m(t)+Sm(t))
*  m(t+delta_t) = m(t)*(1-delta_t/tm)+(delta_t/tm)*Sm(t)
*  m(t+delta_t) = (1-delta_t/tm)*m(t)+(delta_t/tm)*Sm(t)
**/


package nslj.src.math;

import nslj.src.system.NslSystem;

/**
* Euler's Differentiation methods
*/

public class NslDiffEuler extends NslDiff {

    public NslDiffEuler(NslSystem sys) {
	super(sys);
    }    

    public NslDiffEuler(double dt, double tm) {
	super(dt,tm);
    }

    public double value(double out, double input, double dt, double tm) {
	return out+(dt/tm)*input;
    } 

    public float value(float out, float input, float dt, float tm) {
	return out+(dt/tm)*input;
    }

}

