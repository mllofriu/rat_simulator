/*  SCCS @(#)NslNumeric0.java	1.8 --- 09/01/99 --00:16:49 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslNumeric0.java
//

/**
 * NslNumeric0 class is the base class for all scalar number objects used
   in NSL System.
 */

package nslj.src.lang;

public abstract class NslNumeric0 extends NslNumeric {

public NslNumeric0() {
	super();
}; //??
public NslNumeric0(String label) {
	super(label);
}
public NslNumeric0(String label, NslHierarchy parent) {
	super(label,parent);
}
//public NslNumeric0(String label, NslClass parent) {
//	super(label,parent);
//}

public NslNumeric0(String label, NslHierarchy parent, char desiredAccess) {
	super(label,parent,desiredAccess);
}
//public NslNumeric0(String label, NslClass parent, char desiredAccess) {
//	super(label,parent,desiredAccess);
//}

/* -------------- Abstract methods -------------*/
/**
   * Get the value of this number 
   * @return value - its native value
   */
// public abstract native get();
/**
   * Get the value of this number in NslNumeric
   * @return NslNumeric - in correct type
   */
//Check if _getnative() is used.
  //public abstract NslNativeObj _getnative() { return _data; }
/**
   * Get the value of this number in native plus 0
   * @return native - in correct type
   */
//Check if getnative0() is used.
  //public abstract native getnative0() { return _data; }
/**
   * Get the value of this number in double precision floating point number
   * @return value - in double precision pointing point
   */
  public abstract double getdouble() ;
    // return (double)_data.value;
  // }

  /**
   * Get the value of this number in single precision floating point number
   * @// return value - in single precision pointing point
   */
  public abstract float  getfloat() ;
    // return (float)_data.value;
  // }
  /**
   * Get the value of this number in integer
   * @return value - in integer
   */
  public abstract int    getint() ;
    // return (int)_data.value;
  // }
// Interface with NslNumeric type

  /**
   * Get the value of this number in double precision
   * floating point NslNumeric
    * override for NslDouble0 for efficency
   * @return NslNumeric - in double precision pointing point
   */
  public abstract NslDouble0 getNslDouble0() ;
    // return (new NslDouble0((double)(_data.value)));
  // }

  /**
   * Get the value of this number in 
   * floating point NslNumeric
    * override for NslFloat0 for efficency
   * @return NslNumeric - in single precision pointing point
   */
  public abstract NslFloat0 getNslFloat0() ;
    // return (new NslFloat0((float)(_data.value)));
  // }

  /**
* Get the value of this number in integer
* override for NslInt0 for efficency
* @return NslNumeric - in integer
*/
  public abstract NslInt0    getNslInt0() ;
    // return (new NslInt0((int)(_data.value)));
  // }

//----------sets---------------------------------
/**
   * set the value of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  abstract public void _set(double value);
  /**
   * set the value of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  abstract public void _set(float  value);
  /**
   * set the value of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  abstract public void _set(int value);
/**
   * Set the value of this object to <tt>value</tt>
   * @param value - value to be set.
   */
  abstract public void _set(NslNumeric0 value);


//---various ---------------------------------------
/*-------------Non-Abstract methods ------------------*/
/**
   * get the dimensions of this object
   * @return always zero
   */
  public int getDimensions() {
    return 0;
  }

/**
   * get the size of this array 
   * @return always zero
   */
  public int getSize() {
    return 0;
  }
/**
   * get the size of this array and put it into <tt>size</tt>
   * @param size
   */
 public  void getNslSize(NslInt0 size) {
    size.set(0);
  }


/**
   * get the size of this array and put it into <tt>size</tt>
   * @return size - always a vector of 4 elements
   */
 public int[] getSizes(){
	int[]size =new int[4];
     	size[0]=0;
	size[1]=0;
 	size[2]=0;
	size[3]=0;
	return(size);
 }

/**
   * get the size of this array at the rightmost dimension
   * @return always zero
   */
  public int getSize1() {
    return 0;
  }
  /**
   * get the size of this array at the second to right dimension
   * @return always zero
   */
  public int getSize2() {
    return 0;
  }
/**
   * get the size of this array at the third to right dimension
   * @return always zero
   */
  public int getSize3() {
    return 0;
  }
  /**
   * get the size of this array at the fourth to right dimension
   * @return always zero
   */
  public int getSize4() {
    return 0;
  }
// todo: check if obsolete doubleValue, floatValue, intValue
// same as getdouble, getint, getfloat.
/**
   * @return the value of this object in java basic numerical type <tt>double</tt>
   */  
//public double doubleValue() {
//    return (double)_data.value;
//  }
/**
   * @return the value of this object in java basic numerical type <tt>float</tt>
   */

  //public float  floatValue() {
  //  return (float)_data.value;
  ///}
/**
   * @return the value of this object in java basic numerical type <tt>int</tt>
   */
  //public int    intValue() {
  //  return (int)_data.value;
  //}



}




