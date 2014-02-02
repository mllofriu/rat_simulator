/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslVariable.java,v $
// Revision 1.8  1998/01/30 20:02:29  erhan
// ver 5
//
// Revision 1.4  1997/05/09 22:30:26  danjie
// add some comments and Log
//
//--------------------------------------


package nslj.src.display;

import java.lang.*;
import java.awt.*;
import java.util.*;
import java.io.*;

import nslj.src.lang.*;
import nslj.src.system.*;


/**
 * The Nsl Variable
 * Maintain data and properties which are needed for displaying the variable 
 * in various canvases
 *
 * @version 1.0
 * @author  Danjie Pan
 */

public class NslVariable
{

    // Variables


    public NslVariableInfo info;  

    // Other

    public float  [][][] data;    //temporally set to public for testing
    public int max_data_pos=512;  //temporally defined

    public int    [][] n2d_int;
    public float  [][] n2d_float;
    public double [][] n2d_double;

    public int    [] n1d_int;
    public float  [] n1d_float;
    public double [] n1d_double;

    public NslIntegerObj n0d_int;
    public NslFloatObj   n0d_float;
    public NslDoubleObj  n0d_double;

    public static NslSystem system;

    public int last_data_pos;
    public int start=0;
    public int absolute_last_data_pos;

    public int total_time=0; //total run time

    static public int time_interval = 5000; //temporally defined
    
    private int totalSize;

    static public int properties=0; // 0: no history
				    // 1: bezier
				    // 2: history


    private NslFrame  nslDisplayFrame;
    private NslCanvas nslDisplayCanvas;

    private double tmin=0, tmax=25;  

    // Shared properties
    //private double min_cycles=0, max_cycles=256;  
    private double min_cycles=0, max_cycles=25;  

    // default properties
    private float defaultymin=0, defaultymax=25; // todo: this should be a double

    // Temporal properties
    private float t_ymin, t_ymax; // todo: this should be a double

    // Area propeerties
    private float  a_ymin, a_ymax; // todo: this should be a double
    
    
    public NslVariable(NslFrame nslDisplayFrame, NslCanvas nslDisplayCanvas, NslVariableInfo vi) {
        
        info = vi;	
        //todo: get these from the canvas not the frame
        tmin = nslDisplayFrame.tmin;
	tmax = nslDisplayFrame.tmax;

        // todo: get these from the canvas not the frame
        // todo: make these doubles not floats
        defaultymin=(float)nslDisplayFrame.ymin;
	defaultymax=(float)nslDisplayFrame.ymax;


        min_cycles = (double)(tmin / system.nslGetDelta());
	max_cycles = (double)(tmax / system.nslGetDelta());

        max_data_pos = (int)max_cycles;


        // todo: big memory hog -- what is data doing? we really should
	// minimize this.


	if (vi.isHistoryOn()) {
	
	    totalSize = max_data_pos+2;
	} else {
	    totalSize = 1;

	}

        data = new float[info.getDimension(0)][info.getDimension(1)][totalSize];
                
        last_data_pos = 0; //-1;
        start = 0;
        total_time = -1;
        absolute_last_data_pos = 0 ;//-1;
	
	switch (info.getCountDimensions()) {
	    case 0:
		switch(info.getType()) {
		    case NslVariableInfo.INT: 
			n0d_int = ((NslInt0)info.getNslVar())._getint();
			break;
	  	    case NslVariableInfo.FLOAT: 
			n0d_float = ((NslFloat0)info.getNslVar())._getfloat();
			break;
		    case NslVariableInfo.DOUBLE: 
			n0d_double = ((NslDouble0) info.getNslVar())._getdouble();
			break;
		}
	        break;

	    case 1:
		NslNumeric1 n1 = ((NslNumeric1) info.getNslVar());
	  	switch(info.getType()) {
		    case NslVariableInfo.INT: 
			n1d_int = n1.getint1();
			break;	    
		    case NslVariableInfo.FLOAT: 
			n1d_float = n1.getfloat1();
			break;
	  	    case NslVariableInfo.DOUBLE: 
			n1d_double = n1.getdouble1();
			break;
	  	}
		break;

	    case 2:
		NslNumeric2 n2 = ((NslNumeric2) info.getNslVar());
		switch (info.getType()) {
		    case NslVariableInfo.INT: 
			n2d_int = n2.getint2(); 
			break;
		    case NslVariableInfo.FLOAT: 
	      		n2d_float = n2.getfloat2(); 
			break;
		    case NslVariableInfo.DOUBLE: 
			n2d_double = n2.getdouble2(); 
			break;
		}
	  	break;

	    default:
		System.err.println("NslVariable: [Error] Dimensions not supported yet");
		break;
	}

       //System.err.println("NslVariable: Before collecting data from constructor");
        collect();
        
    }

    public Object clone(NslFrame nslDisplayFrame, NslCanvas nslDisplayCanvas) {

	NslVariableInfo vi = (NslVariableInfo)info.clone();
	NslVariable      v = new NslVariable(nslDisplayFrame,nslDisplayCanvas,vi);
      
	v.min_cycles = min_cycles;
	v.max_cycles = max_cycles;
	v.t_ymin     = t_ymin;
	v.t_ymax     = t_ymax;
	v.a_ymin     = a_ymax;
	v.data       = data;
	v.total_time = total_time;
	
	return v;
    }

    public void set_min_cycles(double ti) {
        min_cycles = ti ;
    }

    public void set_max_cycles(double ta)
{
        max_cycles = ta;
    } 


    public void set_tmin(double ti) {
        tmin = ti ;
    }

    public void set_tmax(double ta)
{
        tmax = ta;
    } 

    // todo: ymin and y max should be doubles

    public void set_ymin(float ymin) {
        defaultymin = ymin;
    }

    public void set_ymax(float ymax)
{
        defaultymax = ymax;
    }

    public void set_temporal_ymin(float ymin)
{
        t_ymin = ymin;
    }

    public void set_temporal_ymax(float ymax)
{
        t_ymax = ymax;
    }

    public void set_area_ymin(float ymin) {
        a_ymin = ymin;
    }

    public void set_area_ymax(float ymax)
{
        a_ymax = ymax;
    }

    public double get_min_cycles() {
        return min_cycles;
    }
    
    public double get_max_cycles() {
        return max_cycles;
    }

    public void set_last_data_pos(int ldp)
{
        last_data_pos = ldp;
    }

    public int get_last_data_position()
{
        return last_data_pos;
    }

    public int get_absolute_last_data_position()
{
        return absolute_last_data_pos;
    }

    private double getEndTime() {
    	double max1 = system.getRunEndTime();
    	double max2 = system.getTrainEndTime();
    	double max3 = system.getEndTime();    	

    	if ((max1>max2)&&(max1>max3)) {
    	    return max1;
    	} else if ((max2>max1)&&(max2>max3)) {
    	    return max2;
        } else {
    	    return max3;
        }
    }
  
    public void collect() {
   
	// Now for the case where we are just running and not at end
	int xDim = info.getDimension(0);
	int yDim = info.getDimension(1);

	if ((NslSystem.init_run_char == 'B')||
            (NslSystem.init_run_char == 'D')) {
	    last_data_pos = absolute_last_data_pos = 0;
	} else {
	    last_data_pos = absolute_last_data_pos = (system.getFinishedCycles()-1) % totalSize;
            /*last_data_pos = (last_data_pos + 1) % totalSize;
            absolute_last_data_pos = (absolute_last_data_pos + 1) % totalSize;
*/
	}

	if (xDim == 1 && yDim == 1) {
	    switch(info.getType()) {
	    case NslVariableInfo.INT:
		data[0][0][last_data_pos]=(float)n0d_int.value;
		break;
	    case NslVariableInfo.FLOAT:
		data[0][0][last_data_pos]=(float)n0d_float.value;
		break;
	    case NslVariableInfo.DOUBLE:
		data[0][0][last_data_pos]=(float)n0d_double.value;
		break;
	    } 
//System.err.println("Variable "+info.nslGetName()+"collected: "+data[0][0][last_data_pos]+" at pos: "+last_data_pos);
	} else if (yDim == 1) {
	    for(int i=0; i< xDim; i++) 
		switch (info.getType()) {
		    case NslVariableInfo.INT:
			data[i][0][last_data_pos]=(float)n1d_int[i];
			break;
		    case NslVariableInfo.FLOAT:
			data[i][0][last_data_pos]= n1d_float[i];
			break;
		    case NslVariableInfo.DOUBLE:
			data[i][0][last_data_pos]=(float)n1d_double[i];
			break;
		}
	} else {
	    for(int i=0; i< xDim; i++) 
		for(int j=0; j< yDim; j++) {
		    switch(info.getType()) {
			case NslVariableInfo.INT: 
			    data[i][j][last_data_pos]=(float)n2d_int[i][j];
			    break;
			case NslVariableInfo.FLOAT: 
			    data[i][j][last_data_pos]=(float)n2d_float[i][j];
			    break;
			case NslVariableInfo.DOUBLE: 
			    data[i][j][last_data_pos]=(float)n2d_double[i][j];
			    break;
		    }
		} 
	}
	
    } // end collect

    public double getElement(int x, int y) {
      return( n2d_double[x][y] );
    }


}



