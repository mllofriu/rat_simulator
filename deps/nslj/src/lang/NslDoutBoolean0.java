/*  SCCS - @(#)NslDoutBoolean0.java	1.3 - 09/01/99 - 00:16:57 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslDoutBoolean0.java
////////////////////////////////////////////////////////////////////////////////

package nslj.src.lang;

public class NslDoutBoolean0 extends NslBoolean0 {

    NslOutport outport=null;

    public NslDoutBoolean0(NslModule owner) {
	super(); 
	attachPort(owner);
    }

    public NslDoutBoolean0(NslModule owner, boolean value) {
      super(value); 
      attachPort(owner);
    }

    public NslDoutBoolean0(NslModule owner,NslBoolean0 n) {
      super(n); 
      attachPort(owner);
    }

    public NslDoutBoolean0(NslModule owner,String name) {
       super(name); 
       attachPort(owner);
    }
    
    public NslDoutBoolean0(String name,NslModule owner) {
       super(name,owner);
       attachPort(owner);
    }

    public NslDoutBoolean0(NslModule owner,String name, boolean value) {
	super(name,value);
	attachPort(owner);
    }

    public NslDoutBoolean0(NslModule owner,String name, NslBoolean0 value) {
    	super(name,value);
    	attachPort(owner);
    }

    public NslDoutBoolean0(String name, NslModule owner, boolean value) {
	super(name,owner,value);
	attachPort(owner);
    }

    public NslDoutBoolean0(String name, NslModule owner,NslBoolean0 value) {
	super(name,owner,value);
	attachPort(owner);
    }

    private void attachPort(NslModule owner) {
	// System.out.println("Attaching "+this+" to module "+owner);
	outport=new NslOutport(this);
	outport.owner=owner;
	owner.nslAddExistingOutport(outport);  /* Add to owners outport list */
    }

    public NslOutport getOutport() { 
    	return(outport); 
    }
    
    public NslPort nslGetPort() { 
    	return(outport); 
    }

    public void nslSetBuffering(boolean v) {
    	outport.nslSetBuffering(v);
    }

}

// NslDoutBoolean0.java
////////////////////////////////////////////////////////////////////////////////
