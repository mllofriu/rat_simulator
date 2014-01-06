/*  SCCS - @(#)NslDinFloat3.java	1.11 - 09/01/99 - 00:16:37 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslDinFloat3.java
////////////////////////////////////////////////////////////////////////////////

package nslj.src.lang;


public class NslDinFloat3 extends NslFloat3 {
NslInport inport=null;

  public NslDinFloat3(NslModule owner) {
        super(); attachPort(owner);
  }
 
  public NslDinFloat3(NslModule owner,String name) {
     super(name); attachPort(owner);
  }
  public NslDinFloat3(String name,NslModule owner) {
     super(name,owner); attachPort(owner);
  }

  public NslDinFloat3(String name,NslModule owner, int dummy1,int dummy2,int dummy3) {
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

// NslFloat3.java
