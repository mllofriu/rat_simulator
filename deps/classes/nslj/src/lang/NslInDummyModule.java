/* SCCS  @(#)NslInDummyModule.java	1.1---09/20/99--19:21:06 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.


package nslj.src.lang;

import nslj.src.lang.*;
import nslj.src.system.*;
import nslj.src.cmd.*;
import nslj.src.nsls.struct.*;
import java.util.Enumeration;
public class NslInDummyModule extends NslInModule {
    
    static int counter=0;
    String[] strs;    
    
    public NslInDummyModule (/*String[] strs*/) {
    	super("InModule"+counter, system.nslGetModelRef());
    	counter++;
    	//this.strs=strs;
    	
    }
    
    /*public void initTrainEpoch() {
    	nslRemoveFromLocalProtocols("manual");	 
    	
    	for (int i=0; i<strs.length; i++) {
    	    System.err.println("List has :"+_protocols.size()+" elements");
	    System.err.println("Adding: "+strs[i]);
	    nslAddProtocolRecursiveUp(strs[i]);
	}
	System.err.println("List has :"+_protocols.size()+" elements");
	Enumeration e = _protocols.elements();
        String _protocolName;
        System.err.println("Added");
	while(e.hasMoreElements()) {

    	        _protocolName = (String)e.nextElement();
    	    
    	        System.err.println(_protocolName);
    	    }
    }
    
    public void initRunEpoch() {
        nslRemoveFromLocalProtocols("manual");	    
    	for (int i=0; i<strs.length; i++) {
	    System.err.println("Adding: "+strs[i]);
	    nslAddProtocolRecursiveUp(strs[i]);
	}
    }*/
  
}
