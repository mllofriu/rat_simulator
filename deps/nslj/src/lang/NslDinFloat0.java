/*  SCCS - @(#)NslDinFloat0.java	1.11 - 09/01/99 - 00:16:36 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslDinFloat.java
////////////////////////////////////////////////////////////////////////////////


/**
* NslDinFloat0 - single precision floating point number
*/
package nslj.src.lang;

public class NslDinFloat0 extends NslFloat0 {

NslInport inport=null;

  public NslDinFloat0(NslModule owner) {
      super(); attachPort(owner);

  }

  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param value - the init value
   */
//  public NslDinFloat0(NslModule owner,float value) {
//    super(value); attachPort(owner);
//  }

// 98/7/27 aa - added because it was missing???
  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param value - the init value
   */
  public NslDinFloat0(NslModule owner,String name) {
    super(name); attachPort(owner);
  }

  public NslDinFloat0(String name, NslModule owner) {
    super(name,owner); attachPort(owner);
  }

private void attachPort(NslModule owner) {
  // System.out.println("Attaching "+this+" to module "+owner);

  inport=new NslInport(this);
  inport.owner=owner;
  owner.nslAddExistingInport(inport);  /* Add to owners inport list */
}

public NslInport getInport() { return(inport); }
public NslPort   nslGetPort() { return(inport); }


}

// NslDinFloat.java
////////////////////////////////////////////////////////////////////////////////



































































