/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.lang;

import nslj.src.system.NslSystem;
import nslj.src.display.*;
import java.util.*;

abstract public  class NslInModule extends NslModule {
    NslDisplaySystem ds; 
    Vector p;

    public NslInModule(String label, NslModule parent) {
    	super(label,parent); 
    	if (!system.getNoDisplay()) {
    	    ds = new NslDisplaySystem(this, system);
	    ds.frame.setInputFrame();
    	    system.addDisplaySystem(ds);
    	    p = new Vector(10);
    	}
    }
    
    public void nslAddNumericEditor(NslNumeric numvar) {
    	if (ds==null) {
	    System.err.println("NslInModule: nslAddNumericEditor: Error display system is null");		} else {
    	    ds.frame.addVariableInfo(numvar);    
    	}
    }

    public void addInputImageCanvas(NslNumeric n, double min, double max) {
    	if (ds!=null) {
    	    ds.frame.addPlot(n,min,max,"InputImage");
    	}
    } 
       
    public void addUserCanvas(NslNumeric n, double min, double max,String type){
    	if (ds!=null) {
    	    ds.frame.addUserPlot(n,min,max,type);    
    	}
    }   
   
    public void addButton(String name, String label, String panel) {
    	if (ds!=null) {
    	    Enumeration E = p.elements();
    	    while(E.hasMoreElements()) {
    	        NslUserPanel np = (NslUserPanel)E.nextElement();
      	        if (np.nslGetName().equals(panel)) {
      	    	    np.addComponent(new NslButton(name, label, this));
      	    	    np.validate();
      	    	    return;
      	        }
    	    }
    	    System.out.println("Error: Panel " +panel+ " was not found");
    	}
    }
    
    public void addPanel(String name) {
    	if (ds!=null) {
    	    NslUserPanel np = new NslUserPanel(name,this);
    	    p.addElement(np);
    	    ds.frame.addComponent(np);
    	}
    }
    
    public void nslUpdateBuffers() {
    	if (ds!=null) {
    	    ds.frame.collect(system.getCurTime());
    	}    
    }

    public NslFrame getNslFrame() {
	  return ds.frame;
    }
    
    public void show() {
    	ds.show();
    }
}

