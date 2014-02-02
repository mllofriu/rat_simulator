/* SCCS  @(#)NslClockSchedulerModuleVector.java	1.11---09/01/99--00:19:51 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslClockSchedulerModuleVector.java,v $
 * Revision 1.4  1997/11/18 01:18:16  erhan
 * *** empty log message ***
 *
 * Revision 1.3  1997/11/06 03:15:25  erhan
 * nsl3.0.b
 *
 * Revision 1.2  1997/07/30 21:19:43  erhan
 * nsl3.0
 *
 * Revision 1.1.1.1  1997/03/12 22:52:21  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:40  nsl
 *  Imported the Source directory
 *
*/
//
// NslClockSchedulerModuleVector.java
//
//////////////////////////////////////////////////////////////////////

/**
 * NslClockSchedulerModuleVector - a vector structure helps
   the operation of <tt>NslMultiClockScheduler</tt>

   It stores <tt>NslModule</tt> with same run step size and
   activates those modules at appropiate time.
 * @see NslMultiClockScheduler
 */
package nslj.src.system;

import nslj.src.lang.NslModule;

import java.util.Vector;
import java.util.Enumeration;

class NslClockSchedulerModuleVector extends Vector {
    double _run_step_size; // the run step size of all modules in the
                           // vector
    double _last_run_time; // last time the modules ran.

    /**
     * @param rss - run step size
     */

    NslClockSchedulerModuleVector(double rss) {
        super();
        _run_step_size = rss;
    }

    /**
     * @param rss - run step size
     * @param initialCapacity - initial size of the vector
     */

    NslClockSchedulerModuleVector(double rss, int initialCapacity) {
        super(initialCapacity);
        _run_step_size = rss;
    }

    /**
     * @param rss - run step size
     * @param initialCapacity - initial size of the vector
     * @param capacityIncrement - vector size increase on overflow.
     */

    public NslClockSchedulerModuleVector(double rss, int initialCapacity, int capacityIncrement) {
        super(initialCapacity, capacityIncrement);
        _run_step_size = rss;
    }

    /**
     * Initialize the vector before simulation
     */

    void initRun() {
        _last_run_time = 0.0;
    }

    /**
     * Get the run step size of the modules in this vector
     * @return run step size.
     */

    double nslGetRunDelta() {
        return _run_step_size;
    }

    boolean isRunnable(double curTime) {
	if (_last_run_time+_run_step_size/2 > curTime &&
        	curTime !=0) {
            return false;
        }

        if (_run_step_size == 0.0 && curTime >0) {
            return false;
        }

	_last_run_time=curTime;
	return true;
    }

    /**
     * Run this group of modules at cur_time. If the time last run
     * plus run time step is bigger than the current time, this
     * group of module will be activated.
     * The module will run only if the run_enable_flag is true.
     * @param cur_time - current system time
     */

    void simRun(double cur_time) {
        if (_last_run_time+_run_step_size/2 > cur_time &&
        	cur_time !=0) {
            return;
        }

        if (_run_step_size == 0.0 && cur_time >0) {
            return;
        }
        
	Enumeration e = elements();
        NslModule module;

        _last_run_time=cur_time; //+=_run_step_size;
	while(e.hasMoreElements()) {
	    module = (NslModule)e.nextElement();
            // Run only if the run enable flag is set
            if(module.nslGetRunEnableFlag()) {
		module.simRun();
	    }
	}
    }

    /**
     * Update this group of modules at cur_time. If the time last run
     * smaller than the current time, this group of module is not executed 
     * in this clock and the outport value remains the same in the 
     * next cycle of execution. Otherwise, the values are nslUpdateBuffersd
     * The module will be nslUpdateBuffersd only if the run_enable_flag is true.
     * @param cur_time - current system time
     */

    void updateBuffers(double cur_time) {

	if (_last_run_time < cur_time) {
	    return;
	}

	if (_run_step_size == 0.0 && cur_time >0) {
	    return;
	}

        Enumeration e = elements();
	NslModule module;
	
	_last_run_time=cur_time; 
	while(e.hasMoreElements()) {
	    module = (NslModule)e.nextElement();

            // Run only if the run enable flag is set
            if(module.nslGetRunEnableFlag()) {
	      module.nslUpdateBuffers();
	    }
	}
    }

    /**
     * Remove all modules in the vector.
     */

    void reset() {
	removeAllElements();
    }

}
