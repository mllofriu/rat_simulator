/* SCCS  %W% --- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslBase.java
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
* The NslBase class is inherited by NslNumeric, NslModel, NslModule, and NslClass.
* It exists so that we have the same structure as C++, which does
* not have reflection.
*/

package nslj.src.lang;
//import EDU.gatech.cc.is.abstractrobot.*;extends ControlSystemMFN150
public class NslBase {
  protected String _name=null;   //instance name
  protected NslHierarchy _parent=null;  //module or class

  // Valid desiredAccessiblity charaters are: R for read, W for read and write,
  // and N for neither.
  protected char _accessChar = 'R';


    public NslBase() {
    }

    //called by NslModel.java indirectly

    public NslBase(String label) {
	_name=label;
    }
 

    public NslBase(String label, NslHierarchy parent) {
	_name=label;
	_parent=parent;
    }

//    public NslBase(String label, NslClass parent) {
//	_name=label;
//	_parent=parent;
//    }

    public NslBase(String label, NslHierarchy parent, char desiredAccess) {
	_name=label;
	_parent=parent;
	_accessChar = desiredAccess;
    }
//    public NslBase(String label, NslClass parent, char desiredAccess) {
//	_name=label;
//	_parent=parent;
//	_accessChar = desiredAccess;
//    }
 

/**
    * Get the name of this Numeric object
    * @return name name  
    */

    public String nslGetName() {
	return _name;
    }

    public void nslSetName(String name) {
	_name=name;
    }

    public void nslSetParent (NslHierarchy parent) {
	_parent=parent;
    }
    //----------------
//    public void nslSetParent (NslClass parent) {
//	_parent=parent;
//    }

public void nslNullifyParent () {
	_parent=null;
    }

public NslHierarchy nslGetParent () {
	return _parent;
    }
public NslModule nslGetParentModule () {
	//if (_parent.getClass().nslGetName().equals("NslModule")) {
	if (_parent==null) return null;
	if (_parent instanceof NslModule){
	   return (NslModule)_parent;
	} else {return null;}
    }
public NslClass nslGetParentClass () {
	//if (_parent.getClass().nslGetName().equals("NslClass")) {
	if (_parent==null) return null;
	if (_parent instanceof NslClass){
	   return (NslClass)_parent;
	} else {return null;}
    }
    
    public String nslGetRealName() {
  	if (nslGetParent()!=null) {
  	    return nslGetParent().nslGetRealName()+"."+this.nslGetName();  	    
  	}
  	return this.nslGetName();
    }
    
  /**
    * Set desiredAccessiblity
    * @return accessChar
    */

  public void nslSetAccess(char v) {

	_accessChar=v;

  	/* if (v=='R') {
  	   module.enableAccRead(this);  
  	} else if (v=='W') {
  	   module.enableAccWrite(this);
	}
	*/
  }
  /**
    * 
    * @return accessChar
    */
   public char nslGetAccess() {
  	return _accessChar;
  }


}



