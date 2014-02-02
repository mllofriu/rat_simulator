/*  SCCS - @(#)NslModel.java	1.7 --- 09/01/99 -- 00:16:52 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslModel.java
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

import nslj.src.system.NslSystem;
import nslj.src.math.NslDiff;
import nslj.src.display.*;  // ERH
import java.lang.*;
import java.util.Vector;
import java.util.Enumeration;

abstract public  class NslModel extends NslModule{

  /** 
   * Bare Constructor. Setup internal variables and lists.
   */
  //the parser still calls this method, 
  // todo: change so parser calls NslModel without parent=null
  public NslModel(String name, NslModule parent){
    super(name);
  }
  /** 
   * Bare Constructor. Setup internal variables and lists.
   */
/*
  public NslModel(String name, NslModule parent){
    super(name,parent);
  }
*/
}
