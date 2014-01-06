/* SCCS  @(#)NslBoolean.java	1.3 --- 09/01/99 -- 00:16:53 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslBoolean.java
//

/**
 * NslBoolean class is the base class for all boolean objects used
 * in NSL system.
 * A NslBoolean may or may not have a port attached to it. 
 * The NslPort class is a subclass of NslNumeric. 
 */
 
package nslj.src.lang;

public abstract class NslBoolean 
    extends NslData {

    public NslBoolean() {
	super();
    } //??
        
    public NslBoolean(String label) {
	super(label);
    }

    public NslBoolean(String label, NslHierarchy parent) {
	super(label,parent);
    }

    public NslBoolean(String label, NslHierarchy parent, char desiredAccess) {
	super(label,parent,desiredAccess);
    }

}
