/* SCCS  %W% --- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.lang;

import nslj.src.system.NslSystem;
import nslj.src.display.*;

abstract public  class NslOutModule extends NslModule {
    NslDisplaySystem ds; 

    public NslOutModule(String label, NslModule parent) {
    	super(label,parent); 
    	if (!system.getNoDisplay()) {
    	    ds = new NslDisplaySystem(this, system);
    	    system.addDisplaySystem(ds);  	
    	}
    }
    
    public void nslAddAreaCanvas(NslNumeric numvar, double min, double max) {
    
   if (system.getNoDisplay()) {
    
       return;
    
   }
    
   
    	if (ds==null) {
		System.out.println("NslOutModule: nslAddAreaCanvas: Error display system is null");
	} else {
    	    ds.frame.addPlot(numvar,min,max,"Area");
    	}
    }    
    
    public void nslAddBarCanvas(NslNumeric numvar, double min, double max) {
    
   if (system.getNoDisplay()) {
    
       return;
    
   }
    	if (ds==null) {
		System.out.println("NslOutModule: nslAddBarCanvas: Error display system is null");
	} else {
    	    ds.frame.addPlot(numvar,min,max,"Bar");
    	}
    }
    
    public void nslAddDotCanvas(NslNumeric numvar, double min, double max){
    
   if (system.getNoDisplay()) {
    
       return;
    
   }
    	if (ds==null) {
		System.out.println("NslOutModule: nslAddDotCanvas: Error display system is null");
	} else {
    	    ds.frame.addPlot(numvar,min,max,"Dot");
    	}
    }
    
    public void nslAddImageCanvas(NslNumeric numvar, double min, double max){
    
   if (system.getNoDisplay()) {
    
       return;
    
   }
    	if (ds==null) {
		System.out.println("NslOutModule: nslAddImageCanvas: Error display system is null");
	} else {
    	    ds.frame.addPlot(numvar,min,max,"Image");    
    	}
    }
    
    public void nslAddSpatialCanvas(NslNumeric numvar, double min, double max){
     
  if (system.getNoDisplay()) {
    
       return;
    
   }
   	if (ds==null) {
		System.out.println("NslOutModule: nslAddSpatialCanvas: Error display system is null");
	} else {
       	    ds.frame.addPlot(numvar,min,max,"Spatial");
       	}
    }
    
    public void nslAddStringCanvas(NslNumeric numvar, double min, double max){
     
  if (system.getNoDisplay()) {
    
       return;
    
   }
    	if (ds==null) {
		System.out.println("NslOutModule: nslAddStringCanvas: Error display system is null");
	} else {
    	    ds.frame.addPlot(numvar,min,max,"String");
    	}
    }
    
    public NslCanvas nslAddTemporalCanvas(NslNumeric numvar, double min, double max){
     
  if (system.getNoDisplay()) {
    
       return null;
    
   }
    	if (ds==null) {
		System.err.println("NslOutModule: nslAddTemporalCanvas: Error display system is null");
                return null;
	} else {
            return ds.frame.addPlot(numvar,min,max,"Temporal");    
    	}
    }
    
    public NslCanvas nslAddLimbSimulatorCanvas(NslNumeric numvar) {
	if (system.getNoDisplay()) {
	    return null;
	}
    	if (ds==null) {
		System.err.println("NslOutModule: nslAddLimbSimulatorCanvas: Error display system is null");
                return null;
	} else {		                    
            return ds.frame.addPlot(numvar,0.0,0.0,"LimbSimulator");
    	}
    }

    public void nslAddNumericEditor(NslNumeric numvar) {
     
  if (system.getNoDisplay()) {
    
       return;
    
   }
    	if (ds==null) {
	    System.err.println("NslOutModule: nslAddNumericEditor: Error display system is null");
	} else {
    	    ds.frame.addVariableInfo(numvar);    
    	}
    }
          
    public NslCanvas nslAddUserCanvas(NslNumeric numvar, double min, double max,String type){
     
  if (system.getNoDisplay()) {
    
       return null;
    
   }
    	if (ds==null) {
		System.err.println("NslOutModule: nslAddUserCanvas: Error display system is null");
	} else {
    	    return ds.frame.addUserPlot(numvar,min,max,type);    
    	}
      return null;
    }   
    
    public NslFrame getNslFrame() {
	  return ds.frame;
    }
    
    public void nslSetColumns(int numCols) {
	ds.frame.setColumns(1);
	ds.frame.getPanel().validate();
    }
    
    public void show() {
    	ds.show();
    }
}
