/* SCCS  @(#)NslCommandData.java	1.3---09/01/99--00:19:29 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.nsls.struct;

import tcl.lang.*;

public class NslCommandData implements AssocData  {
    
    private Object obj;
    
    public NslCommandData(Object data) {
    	obj = data;
    }
    
    public Object getData() {
    	return obj;
    }
    
    public void disposeAssocData(Interp interp) {
    }
}


