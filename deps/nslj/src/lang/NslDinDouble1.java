/*  SCCS - @(#)NslDinDouble1.java	1.12 - 09/01/99 - 00:16:35 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslDinDouble1.java
////////////////////////////////////////////////////////////////////////////////


package nslj.src.lang;

public class NslDinDouble1 extends  NslDouble1 {

NslInport inport=null;

  ////////////////////////////////////////////////////////////////////
  // constructors
 
  public NslDinDouble1(NslModule owner) {
     super(); attachPort(owner);
  }
 
  public NslDinDouble1(NslModule owner,String name) {
    super(name); attachPort(owner);
  }
  public NslDinDouble1(String name,NslModule owner) {
    super(name,owner); attachPort(owner);
  }

  public NslDinDouble1(NslModule owner,String name, int dum1) {
    super(name); attachPort(owner);
  }
  public NslDinDouble1(String name, NslModule owner, int dum1) {
    super(name,owner); attachPort(owner);
  }

private void attachPort(NslModule owner) {
  // System.out.println("Attaching "+this+" to module "+owner);
  inport=new NslInport(this);
  inport.owner=owner;
  owner.nslAddExistingInport(inport);  /* Add to owner's inport list */
}

public NslInport getInport() { return(inport); }
public NslPort nslGetPort() { return(inport); }


}

// NslDinDouble1.java
////////////////////////////////////////////////////////////////////////////////
