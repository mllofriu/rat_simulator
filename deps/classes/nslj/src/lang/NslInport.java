/*  SCCS - @(#)NslInport.java	1.6 - 09/01/99 - 00:16:46 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslInport.java,v $
 * Revision 1.1.1.1  1997/03/12 22:52:19  nsl
 * new dir structure
 *
 * Revision 1.1.1.1  1997/02/08 00:40:39  nsl
 *  Imported the Source directory
 *
*/
//
// NslInPort.java
//
//////////////////////////////////////////////////////////////////////
/**
* Input ports of a nsl module
*/
package nslj.src.lang;

public class NslInport extends NslPort{
  NslData _var;

  public NslInport(String name, NslData n) {
    super(name, n);
    _var = n;
    // force reset of the nsl numeric object to un-initialized state
    // this will make sure the NslModule#nslConn() method runs correctly
    if (n!=null)n.resetData();
  }

  public NslInport(NslData n) {
    super(n);
    _var = n;
    // force reset of the nsl numeric object to un-initialized state
    // this will make sure the NslModule#nslConn() method runs correctly
    if (n!=null)n.resetData();
  }

  /**
   * setup this inport. Internal variable of this port will make
     a reference to the internal variable of <tt>inport</tt>.
   * @param inport Inport to be refered
   * @throws NsllPointerException if the port supplied is not well-defined.
   */
  public void setPort(NslInport inport) {
    if (inport._var==null) {
      System.out.println("Null _var in Inport "+inport.nslGetName());
      throw new NullPointerException();
    }
      if (!inport._var.isDataSet()){
	System.out.println("Null data in Inport "+inport.nslGetName());
	throw new NullPointerException();
      }
      
      _var.setReference(inport._var);

  }

  /**
   * setup this inport. Internal variable of this port will make
     a reference to the internal variable of <tt>outport</tt>.
   * @param outport Outport to be refered
   * @throws NsllPointerException if the port supplied is not well-defined.
   */

  public void setPort(NslOutport outport) {
    if (outport._old == null) {
      System.out.println("Null _old in Outport "+outport.nslGetName());
      throw new NullPointerException();
    }

    if (!outport._old.isDataSet()) {
      System.out.println("Null data in Outport "+outport.nslGetName());
      throw new NullPointerException();
    }

    _var.setReference(outport._old);

  }

  /**
   * Check if this port is initialized
   * @return true if initialized.
   */
  public boolean isInitialized() {
    if(_var==null)
      return false;
    return (((NslData)_var).isDataSet());
  }

  public NslData getData() {
    return _var;
  }
}




