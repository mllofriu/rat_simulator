/*  SCCS - @(#)NslDinBoolean1.java	1.3 - 09/01/99 - 00:16:56 */
// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslDinBoolean1.java

////////////////////////////////////////////////////////////////////////////////

package nslj.src.lang;


public class NslDinBoolean1 extends NslBoolean1 {

NslInport inport=null;

  ////////////////////////////////////////////////////////////////////
  // constructors

  public NslDinBoolean1(NslModule owner) {
    super(); attachPort(owner);
  }

  public NslDinBoolean1(NslModule owner,String name) {
    super(name); attachPort(owner);
  }
  public NslDinBoolean1(String name,NslModule owner) {
    super(name,owner); attachPort(owner);
  }

  public NslDinBoolean1(NslModule owner,String name, int dum1) {
    super(name); attachPort(owner);
  }

  public NslDinBoolean1(String name, NslModule owner, int dum1) {
    super(name,owner); attachPort(owner);
  }

private void attachPort(NslModule owner) {
  // System.out.println("Attaching "+this+" to module "+owner);
  inport=new NslInport(this);
  inport.owner=owner;
  owner.nslAddExistingInport(inport);  /* Add to owner's inport list */
}

public NslInport getInport() { return(inport); }
public NslPort   nslGetPort() { return(inport); }


}

// NslDinBoolean1.java
////////////////////////////////////////////////////////////////////////////////
