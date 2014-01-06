/*  SCCS - @(#)NslDinString0.java	1.3 - 09/01/99 - 00:16:56 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslDinString0.java
////////////////////////////////////////////////////////////////////////////////

package nslj.src.lang;

public class NslDinString0 extends NslString0 {

    NslInport inport=null;

    public NslDinString0(NslModule owner) {
        super(); 
        attachPort(owner);
    }

    public NslDinString0(NslModule owner,String name) {
       super(name); 
       attachPort(owner);
    }

    public NslDinString0(String name,NslModule owner) {
       super(name,owner); 
       attachPort(owner);
    }

    private void attachPort(NslModule owner) {
	// System.out.println("Attaching "+this+" to module "+owner);
	inport=new NslInport(this);
	inport.owner=owner;
	owner.nslAddExistingInport(inport);  /* Add to owner's inport list */
    }

    public NslInport getInport() {
    	return(inport); 
    }
    
    public NslPort   nslGetPort() {
    	return(inport); 
    }

}

// NslDinString0.java
////////////////////////////////////////////////////////////////////////////////
