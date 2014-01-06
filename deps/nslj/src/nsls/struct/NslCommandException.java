/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.nsls.struct;


import tcl.lang.*;
import java.util.*;
import nslj.src.lang.*;
import nslj.src.system.NslSystem;

public class NslCommandException extends TclException  {

    public NslCommandException(int ccode) {
	super(ccode);
    }
    
    public NslCommandException(Interp interp, String msg, int ccode) {
	super(interp, msg, ccode);
    }
    
    public NslCommandException(Interp interp, String msg) {
	super(interp, msg);
    }
}


    
