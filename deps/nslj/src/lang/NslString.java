/* SCCS  %W% --- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslString.java
//

/**
 * NslString class is the base class for all string objects used
 * in NSL system.
 * A NslString may or may not have a port attached to it. 
 * The NslPort class is a subclass of NslNumeric. 
 */
 
package nslj.src.lang;

public abstract class NslString 
    extends NslData {

    public NslString() {
	super();
    } //??
        
    public NslString(String label) {
	super(label);
    }

    public NslString(String label, NslHierarchy parent) {
	super(label,parent);
    }

    public NslString(String label, NslHierarchy parent, char desiredAccess) {
	super(label,parent,desiredAccess);
    }

}
