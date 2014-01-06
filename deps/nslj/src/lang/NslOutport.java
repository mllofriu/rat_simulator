/* SCCS  @(#)NslOutport.java	1.10---09/01/99--00:16:50 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/*
 * $Log: NslOutport.java,v $
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
// NslOutPort.java
//
//////////////////////////////////////////////////////////////////////
/**
* Output ports of a nsl module (Double buffered)
*/
package nslj.src.lang;

public class NslOutport extends NslPort{
//  public NslData _new;  // new value at t+dt
//  public NslData _old;  // old value at t
  public NslData _new;
  public NslData _old;
  public NslData _temp;

  public NslOutport() {}
  /**
   * setup this inport. The supplied NslData <tt>n</tt> will be
     duplicated if it is an instance of NslData classes
     and both will be pointed by this port. Otherwise, both buffers
     will point to the same data item.
   * @param name name of this port
   * @n NslData to be referred
   * @throws NsllPointerException if the number supplied is not well-defined.
   */
  public NslOutport(String name, NslData n) {
    super(name, n);
    if(n==null)
      throw new NullPointerException();
    _new = n;
    _temp = _old = n.duplicateThis();

  }
  /**
   * setup this inport. The supplied number <tt>n</tt> will be
     duplicated and both will be pointed by this port
   * @n number to be referred
   * @throws NsllPointerException if the number supplied is not well-defined.
   */
  public NslOutport(NslData n) {
    super(n);
    if(n == null)
      throw new NullPointerException();
    _new = n;
    _temp = _old = n.duplicateThis();

  }

  /**
   * construct this port without initializing the number to refer
   * @param name name of the port
   */
  public NslOutport(String name) {
    super(name, null);
  }
  /**
   * setup this inport. The internal variable of this port will point
     to the corresponding variable of the supplied port.

   * @param outport Outport to be refered
   * @throws NsllPointerException if the port supplied is not well-defined.
   */

  public void setPort(NslOutport outport) {
    //    _new=outport._new;
    //    _old=outport._old;
    _new.setReference(outport._new);
    _old.setReference(outport._old);

  }
  /**
   * Check if this port is initialized
   * @return true if initialized.
   */

  public boolean isInitialized() {
    if(_old == null)
      return false;
    return (_new.isDataSet() && 
	    _old.isDataSet());

  }

  public void nslResetBuffering() {
  
    if (!bufferingChanged) {
        if (owner!=null) {
	    doubleBuffering = owner.nslGetBuffering();
	} else {
	    System.out.println("There is no owner of port "+nslGetName());
	}
    }

    /*System.out.println("Modifing port "+_name+" in module "+
    	owner.nslGetName()+" to "+doubleBuffering);*/

    if (!doubleBuffering) {
        _old=_new;
        //_old.setReference(_new);
    } else {
        _old=_temp;
        //nslUpdateBuffers();
    }
  }

  /**
   * nslUpdateBuffers the pointer at the end of each "step"
   */
  synchronized public void nslUpdateBuffers() {
    //#1 System.out.println("Updating port  "+_name);
    if (doubleBuffering) {
        // System.out.println("Modifying");
        _old.duplicateData(_new);    // C ~  *old=*new
    }
   //#1 System.out.println("Updated port  "+_name);
  }

  public NslData getData() {
    return _new;
  }
}







