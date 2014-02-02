/*  SCCS @(#)NslNumeric.java	1.11 --- 09/01/99 --00:16:49 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslNumeric.java
//
//////////////////////////////////////////////////////////////////////

/**
 * NslNumeric class is the base class for all number objects used
 * in NSL system.
 * A NslNumeric may or may not have a port attached to it. 
 * The NslPort class is a subclass of NslNumeric. 
 */

package nslj.src.lang;

public abstract class NslNumeric 
    extends NslData {

    public NslNumeric() {
	super();
    } //??
        
    public NslNumeric(String label) {
	super(label);
    }
    
    public NslNumeric(String label, NslHierarchy parent) {
	super(label,parent);
    }

    public NslNumeric(String label, NslHierarchy parent, char desiredAccess) {
	super(label,parent,desiredAccess);
    }

}



