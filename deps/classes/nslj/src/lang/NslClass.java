/* SCCS  @(#)NslClass.java	1.10 --- 09/01/99 -- 00:16:51 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslClass.java
//
//////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////
// Documentation about documentation
//
// command      explanation
// -------      ------------
// @param       parameter list
// @return      return value
// @throw       exception that could be thrown in run-time
// @see         see also "class"#"method"
// <tt>...</tt> prints out text anchored in typewriter font
//              for parameter, method or class names
// <b>,,.</b>   prints out text anchored in bold type font
//              for warning messages
// <i>...</i>   prints out test anchored in italic type font
//              for concepts in the program
// <br>         new line
//////////////////////////////////////////////////////////////////////

/**
* A basic model
* has not ports
* Calls child modules.
*/

package nslj.src.lang;

public class NslClass extends NslHierarchy {

    public NslClass(String label, NslModule parent) {
	super(label,parent);
        parent.nslAddToClassInstances(this); 
    }

    public NslClass(String label, NslClass parent) {
	super(label,parent);
        parent.nslAddToClassInstances(this); 
    }
   
    public void nslInitTempClass() {
    }

    public void callFromConstructorTop() {
    }

    public void callFromConstructorBottom() {
    }
}

