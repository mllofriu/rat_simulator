/*  SCCS - @(#)NslTrainClockSchedulerModuleVector.java	1.7 - 09/01/99 - 00:19:52 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.


/**
 * NslTrainClockSchedulerModuleVector - a vector structure helps
   the operation of <tt>NslMultiClockScheduler</tt>

   It stores <tt>NslModule</tt> with same run step size and
   activates those modules at appropiate time.
 * @see NslMultiClockScheduler
 */
package nslj.src.system;

import nslj.src.lang.NslModule;

import java.util.Vector;
import java.util.Enumeration;

class NslTrainClockSchedulerModuleVector extends Vector {
    double _run_step_size; // the run step size of all modules in the
                            // vector
    double _last_run_time; // last time the modules ran.

    /**
    * @param rss - run step size
    */
    NslTrainClockSchedulerModuleVector(double rss) {
        super();
        _run_step_size = rss;
    }

    /**
    * @param rss - run step size
    * @param initialCapacity - initial size of the vector
    */
    NslTrainClockSchedulerModuleVector(double rss, int initialCapacity) {
        super(initialCapacity);
        _run_step_size = rss;
    }

    /**
    * @param rss - run step size
    * @param initialCapacity - initial size of the vector
    * @param capacityIncrement - vector size increase on overflow.
    */
    public NslTrainClockSchedulerModuleVector(double rss, int initialCapacity, int capacityIncrement) {
        super(initialCapacity, capacityIncrement);
        _run_step_size = rss;
    }

    /**
    * Initialize the vector before simulation
    */
    void initTrain() {
        _last_run_time = 0.0;
    }

    /**
    * Get the train step size of the modules in this vector
    * @return train step size.
    */
    double nslGetTrainDelta() {
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
      plus run time step is bigger than the current time, this
      group of module will be activated.
      The module will run only if the run_enable_flag is true.
    * @param cur_time - current system time
    */
    void run(double cur_time) {
        //System.out.println("_last_run_time: " + _last_run_time);
        //System.out.println("_run_step_size: " + _run_step_size);
        //System.out.println("cur_time:       " + cur_time);
        //System.out.println("cond1: "+ (_last_run_time+_run_step_size > cur_time));
        /*if (_last_run_time+_run_step_size > cur_time) {
            return;
        }*/
        //System.out.println("cond2: "+ (_run_step_size == 0.0 && cur_time >0));
        if (_run_step_size == 0.0 && cur_time >0) {
            return;
        }
        Enumeration E = elements();
        NslModule module;
        _last_run_time=cur_time; //+=_run_step_size;
	    while(E.hasMoreElements()) {
	        module = (NslModule)E.nextElement();
            // Run only if the run enable flag is set
            if(module.nslGetTrainEnableFlag()) {
                //System.out.println("Training!!!!!");
    	        module.simTrain();
	        }
	    }
	}


    /**
    * Update this group of modules at cur_time. If the time last run
      smaller than the current time, this group of module is not executed 
      in this clock and the outport value remains the same in the 
      next cycle of execution. Otherwise, the values are nslUpdateBuffersd
      The module will be nslUpdateBuffersd only if the run_enable_flag is true.
    * @param cur_time - current system time
    */
    void updateBuffers(double cur_time) {

        if (_last_run_time < cur_time) {
            return;
        }
        if (_run_step_size == 0.0 && cur_time >0) {
	  // System.out.println("step size 0");
            return;
        }
        Enumeration E = elements();
        NslModule module;
        _last_run_time=cur_time; //+=_run_step_size;
	    while(E.hasMoreElements()) {
	        module = (NslModule)E.nextElement();
            // Run only if the run enable flag is set
            if(module.nslGetTrainEnableFlag()) {
	      //   module.execute();
	
	      module.nslUpdateBuffers();
	      
	      // System.out.println("Updating module "+module.nslGetName());
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
