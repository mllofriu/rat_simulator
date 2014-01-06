/*  SCCS - @(#)NslDouble0.java	1.15 --- 02/09/00 --13:49:13 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslDouble.java

/**
* NslDouble0 - double precision floating point number
*/
package nslj.src.lang;

public class NslDouble0 extends NslNumeric0{
// 
  public NslDoubleObj _data; // wrapped double number
  //public String _name;
  //boolean desiredAccessibility = true;
  //NslHierarchy module;  // this should be parent?

  /**
   * Constructor with default value 0
   */
  public NslDouble0() {
	super();
    _data = new NslDoubleObj(0.0);
  }

  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param value - the init value
   */
  public NslDouble0(double value) {
	super();
    _data = new NslDoubleObj(value);
  }

  /**
   * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
   * @param n - a scalar number
   */
  public NslDouble0(NslNumeric0 n) {
	super();
    _data = new NslDoubleObj(n.getdouble()) ;
  }
/**
    * This constructs a number with specified name
    * @param name - name of the variable
    */
  public NslDouble0(String name) {
	super(name);
      _data = new NslDoubleObj(0.0);

  }
  public NslDouble0(String name, NslHierarchy curParent) {
	super(name,curParent,curParent.nslGetAccess());
    _data = new NslDoubleObj(0.0);
  }

  /**
    * This constructs a number with specified name
    * @param name - name of the variable
    * @param value - initialized values
    */
  public NslDouble0(String name, NslNumeric0 value) {
	super(name);
    _data = new NslDoubleObj(value.getdouble());
  }
  public NslDouble0(String name, NslHierarchy curParent, NslNumeric0 value) {
	super(name,curParent,curParent.nslGetAccess());
    _data = new NslDoubleObj(value.getdouble());
  }
  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param value - the init value
    * @param name - name of the variable
   */
  public NslDouble0(String name, double value) {
	super(name);
      _data = new NslDoubleObj(value);
  }
  public NslDouble0(String name, NslHierarchy curParent,double value) {
	super(name,curParent,curParent.nslGetAccess());
      _data = new NslDoubleObj(value);
  }
  //---------------------------------------------------------------
/*
     Duplicating data between buffers in double buffering port model.
     Since we cannot ensure the copy is the original copy created
     in instantiation, this code is to make a security check and
     to make sure the program runs correctly in the latter step.
     */
  public void duplicateData(NslData n) {
    try {
      /* Here we assume that the passed parameter is originally a
	 NslInt1 class. Otherwise, it will force a ClassCastException
	 and notify the NslSystem.
	 */
      set(((NslDouble0)n).getdouble());

    } catch (ClassCastException e) {
      System.out.println("Class exception is caught in data duplication");
      System.out.println("between two copies of buffer.");
      System.out.println("Please check NslPort arrangement");
      throw e;
    }
  }

  /**
   * Clone this number
   * @return - a copy of this number
   */
  public NslData duplicateThis() {
    if (isDataSet())
      return (NslNumeric) (new NslDouble0(getdouble()));
    else
      return (NslNumeric)(new NslDouble0());
  }

  /**
   * Set the reference to the wrapped data of <tt>n</tt>
     It is used in double buffered ports, to make the the ports
     reference different number object at different time.
   * @param n - number concerned
   */
  public NslData setReference(NslData n) {
    try {
      _data = ((NslDouble0) n)._data;
    } catch (ClassCastException e) {
      System.out.println("Class exception is caught in reference setting");
      System.out.println("between two copies of buffer.");
      System.out.println("Please check NslPort arrangement");
      throw e;
    } finally {
      return this;
    }
  }

    /**
     * Set the reference to the wrapped data of <tt>n</tt>
     * It is used in double buffered ports, to make the the ports
     * reference different number object at different time.
     * @param n - number concerned
     */

    public NslDouble0 setReference(double value) {
	_data.value = value;
	return this;
    }

  public boolean isDataSet() {
    return (_data != null);
  }
  /** Reset the reference pointer to null
   */
  public void resetData() {
    _data = null;
  }

  public String toString() {
    return Double.toString(_data.value);
  }


 //String Answer(String s) { 
 //return "don't know";
 //}
 //-------get------------------------------------------------
  /**
   * Get the value of this number
   * @return value, in default type
   */
  public double get() {
    return _data.value;
  }
/**
   * Get the value of this number in double precision
     floating point NslNumeric
   * @return NslNumeric - in double precision pointing point
   */
   //Check if __getdouble() is used.
  public NslDoubleObj _getdouble() { return _data; }

/**
   * Get the value of this number in double precision floating point number
   * @return value - in double precision pointing point
   */
  public double getdouble0() {
    return _data.value;
  }
/**
   * Get the value of this number in double precision floating point number
   * @return value - in double precision pointing point
   */
  public double getdouble() {
    return _data.value;
  }

/**
   * Get the value of this number in single precision floating point number
   * @return value - in single precision pointing point
   */
  public float  getfloat() {
    return (float)_data.value;
  }
  /**
   * Get the value of this number in integer
   * @return value - in integer
   */
  public int    getint() {
    return (int)_data.value;
  }
// Interface with NslNumeric type

/**
   * Get the value of this number in single precision
     floating point NslNumeric
   * @return NslNumeric - in single precision pointing point
   */
public NslDouble0 getNslDouble0() {
    return this;
  }

  public NslFloat0  getNslFloat0() {
    return (new NslFloat0((float)(_data.value)));
  }

  /**
   * Get the value of this number in integer
   * @return NslNumeric - in integer
   */
  public NslInt0    getNslInt0() {
    return  (new NslInt0((int)(_data.value)));
  }
//--- set ------------------------------
/**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(double value) {
    _data.value = (double)value;
    return _data.value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(float value) {
    _data.value = (double)value;
    return _data.value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(int value) {
    _data.value = (double) value;
    return _data.value;
  }

/**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public void _set(double value) {
    _data.value = (double)value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public void _set(float value) {
    _data.value = (double)value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public void _set(int value) {
    _data.value = (double) value;
  }

/**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(double[] value) {
    if (value.length==1) {
	_data.value = (double)value[0];
        return _data.value;
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(float[] value) {
    if (value.length==1) {
	_data.value = (double)value[0];
        return _data.value;
    }  else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(int[] value) {
    if (value.length==1) {
	_data.value = (double)value[0];
        return _data.value;
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(double[][] value) {
    if (value.length==1 && value[0].length==1) {
	_data.value = (double)value[0][0];
        return _data.value;
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(float[][] value) {
    if (value.length==1 && value[0].length==1) {
	_data.value = (double)value[0][0];
        return _data.value;
    }  else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(int[][] value) {
    if (value.length==1 && value[0].length==1) {
	_data.value = (double)value[0][0];
        return _data.value;
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(double[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	_data.value = (double)value[0][0][0];
        return _data.value;
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(float[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	_data.value = (double)value[0][0][0];
        return _data.value;
    }  else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(int[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	_data.value = (double)value[0][0][0];
        return _data.value;
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }  
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(double[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	_data.value = (double)value[0][0][0][0];
        return _data.value;
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(float[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	_data.value = (double)value[0][0][0][0];
        return _data.value;
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }  
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public double set(int[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	_data.value = (double)value[0][0][0][0];
        return _data.value;
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }    
  /**
   * Set the value of this number to <tt>num</tt>
   * @param num, a scalar NslNumeric
   */
  public double set(NslNumeric0 num) {
    _data.value = num.getdouble();
    return _data.value;
  }

  public void _set(NslNumeric0 num) {
    _data.value = num.getdouble();
  }

}


// NslDouble.java
////////////////////////////////////////////////////////////////////////////////



