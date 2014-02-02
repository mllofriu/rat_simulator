/*  SCCS - @(#)NslDoutFloat2.java	1.11 - 09/01/99 - 00:16:42 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslDoutFloat2.java
////////////////////////////////////////////////////////////////////////////////

package nslj.src.lang;


public class NslDoutFloat2 extends NslFloat2 {
NslOutport outport=null;

  public NslDoutFloat2(NslModule owner,float[][] d) {
        super(d); attachPort(owner);
  }
  public NslDoutFloat2(NslModule owner,NslNumeric2 n) {
        super(n); attachPort(owner);
  }

  public NslDoutFloat2(NslModule owner, int size1, int size2) {
        super(size1,size2); attachPort(owner);
  }

  public NslDoutFloat2(NslModule owner) {
        super(); attachPort(owner);
  }
 
  public NslDoutFloat2(NslModule owner,String name) {
     super(name); attachPort(owner);
  }
  public NslDoutFloat2(String name,NslModule owner) {
     super(name,owner); attachPort(owner);
  }

  public NslDoutFloat2(NslModule owner,String name, int size1, int size2) {
     super(name,size1,size2); attachPort(owner);
  }

  public NslDoutFloat2(String name, NslModule owner, int size1, int size2) {
     super(name,owner,size1,size2); attachPort(owner);
  }
    
  public NslDoutFloat2(NslModule owner,String name, NslNumeric2 n) {
      super(name,n); attachPort(owner);
  }
  public NslDoutFloat2(String name, NslModule owner,NslNumeric2 n) {
      super(name,owner,n); attachPort(owner);
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

// NslFloat2.java
////////////////////////////////////////////////////////////////////////////////
