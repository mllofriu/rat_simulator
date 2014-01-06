/*  SCCS - @(#)NslDoutDouble3.java	1.12 - 09/01/99 - 00:16:41 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslDoutDouble3.java
////////////////////////////////////////////////////////////////////////////////

package nslj.src.lang;

//import nslj.src.math.NslSigmoid;



public class NslDoutDouble3 extends NslDouble3 {
NslOutport outport=null;


  public NslDoutDouble3(NslModule owner,double[][][] d) {
	super(d); attachPort(owner);
  }

  public NslDoutDouble3(NslModule owner,NslNumeric3 n) {
	super(n); attachPort(owner);
  }

  public NslDoutDouble3(NslModule owner, int size1, int size2, int size3) {
	super(size1,size2,size3); attachPort(owner);
  }

  public NslDoutDouble3(NslModule owner) {
	super(); attachPort(owner);
  }

  public NslDoutDouble3(NslModule owner,String name) {
      super(name); attachPort(owner);
  }
  public NslDoutDouble3(String name, NslModule owner) {
      super(name,owner); attachPort(owner);
  }
    
  public NslDoutDouble3(NslModule owner,String name, int size1, int size2, int size3) {
      super(name,size1,size2, size3); attachPort(owner);
  }
  public NslDoutDouble3(String name,NslModule owner, int size1, int size2, int size3) {
      super(name,owner,size1,size2, size3); attachPort(owner);
  }

    
  public NslDoutDouble3(NslModule owner,String name, NslNumeric3 n) {
      super(name,n); attachPort(owner);
  }
  public NslDoutDouble3(String name, NslModule owner,NslNumeric3 n) {
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

// NslDouble3.java
////////////////////////////////////////////////////////////////////////////////

