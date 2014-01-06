/* SCCS  @(#)NslPort.java	1.11---09/01/99--00:16:50 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslPort.java,v $
 * Revision 1.2  1997/07/30 21:19:22  erhan
 * nsl3.0
 *
 * Revision 1.1.1.1  1997/03/12 22:52:20  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:40  nsl
 *  Imported the Source directory
 *
*/
//
// NslPort.java
//
//////////////////////////////////////////////////////////////////////
/**
* Ports of a nsl module
  Ports are served as the communication point between modules, either
  between parent and child module or between sibling modules.
  The port that supplies data is called outport, while that receives
  data is called inport. The link between ports is called nslConnection.
  Outports can have zero or more nslConnection and Inports must have
  one and only one nslConnection. See NslModule for the nslConnection
  requirement,

  @see NslModule, NslConnection
*/
package nslj.src.lang;

abstract public class NslPort {
  public static final int OUTPORT = 0;
  public static final int INPORT = 1;

  protected boolean doubleBuffering = false; // Double buffering
  protected boolean bufferingChanged = false;
  
  public NslModule owner;
  String _name;
//  int _type; // 0->outport 1->inport
/*
  public NslPort(String name, NslData n, int type) {
    _name = name;
    _type = type;
  }
*/
  public NslPort(String name, NslData n) {
    _name = name;
    doubleBuffering=false;
    bufferingChanged = false;
  }

 public NslPort() {}
  public NslPort(NslData n) {
    _name = n.nslGetName();
    doubleBuffering=false;
    bufferingChanged = false;
  }

  /**
   *get the name of this port
   */
  public String nslGetName() {
    return _name;
  }

  public void nslSetBuffering(boolean v) {
    doubleBuffering=v;
    bufferingChanged =true;
    owner.nslGetSystem().resetPorts();
  }
  
  public boolean bufferingHasChanged() {
    return bufferingChanged;
  }
  
  public boolean nslGetBuffering() {
    return doubleBuffering;
  }

  /**
   * get the type (INport or OUTport) of this port
   * @return NslPort.OUTPORT / NslPort.INPORT
   */
  public int getType() {
    if(this instanceof NslOutport) return OUTPORT;
    else return INPORT;
  }

  /**
   * Check if this port is initialized
   * @return true if initialized.
   */
  abstract public boolean isInitialized();
  abstract public NslData getData();
  public NslModule getOwner() {return owner;}
}
