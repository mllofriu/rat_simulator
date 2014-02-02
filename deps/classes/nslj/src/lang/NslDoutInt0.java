/*  SCCS - @(#)NslDoutInt0.java	1.11 - 09/01/99 - 00:16:43 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslDoutInt.java
////////////////////////////////////////////////////////////////////////////////

package nslj.src.lang;

public class NslDoutInt0 extends NslInt0{

NslOutport outport=null;

  public NslDoutInt0(NslModule owner) {
        super(); attachPort(owner);
  }

  public NslDoutInt0(NslModule owner, int value) {
      super(value); attachPort(owner);
}

  public NslDoutInt0(NslModule owner,NslNumeric0 n) {
      super(n); attachPort(owner);
}

  public NslDoutInt0(NslModule owner,String name) {
       super(name); attachPort(owner);
}
  public NslDoutInt0(String name,NslModule owner) {
       super(name,owner); attachPort(owner);
}

  public NslDoutInt0(NslModule owner,String name, int value) {
    super(name,value); attachPort(owner);
  }

  public NslDoutInt0(NslModule owner,String name, NslNumeric0 value) {
    super(name,value); attachPort(owner);
  }
  public NslDoutInt0(String name, NslModule owner, int value) {
    super(name,owner,value); attachPort(owner);
  }

  public NslDoutInt0(String name, NslModule owner,NslNumeric0 value) {
    super(name,owner,value); attachPort(owner);
  }

private void attachPort(NslModule owner) {
  // System.out.println("Attaching "+this+" to module "+owner);
  outport=new NslOutport(this);
  outport.owner=owner;
  owner.nslAddExistingOutport(outport);  /* Add to owners outport list */
}

public NslOutport getOutport() { return(outport); }
public NslPort    nslGetPort() { return(outport); }

    public void nslSetBuffering(boolean v) {
    	outport.nslSetBuffering(v);
    }

}

// NslDoutInteger.java
////////////////////////////////////////////////////////////////////////////////
