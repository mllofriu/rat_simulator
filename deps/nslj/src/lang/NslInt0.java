/*  SCCS  @(#)NslInt0.java	1.12 --- 09/01/99 --00:16:46 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslInt0.java


/**
* NslInt0 -integer number
*/
package nslj.src.lang;

public class NslInt0 extends NslNumeric0{

  public NslIntegerObj _data;
  //public String _name;
  //char desiredAccessibility = 'R'; defined in NslNumeric
  //NslHierarchy module;

  /**
   * Constructor with default value 0
   */
  public NslInt0() {
	super();
    //_data = null;
    _data = new NslIntegerObj(0);
    _name = null;
  }

  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param value - the init value
   */
  public NslInt0(int value) {
	super();
    _data = new NslIntegerObj(value);
    _name = null;
  }

  /**
   * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
   * @param n - a scalar number
   */
  public NslInt0(NslNumeric0 n) {
	super();
    _data = new NslIntegerObj(n.getint());
    _name = null;
  }

  /**
    * This constructs a number with specified name
    * @param name - name of the variable
    */
  public NslInt0(String name) {
	super();
    _data = new NslIntegerObj(0);
    _name = name;
  }

  public NslInt0(String name, NslHierarchy curParent) {
	super(name,curParent,curParent.nslGetAccess());
    _data = new NslIntegerObj(0);
    //_name = name;
    //module = curParent;
    //desiredAccessibility = module.nslGetAccess();
    //module.enableAccess(this);
  }
  

  /**
    * This constructs a number with specified name
    * @param name - name of the variable
    * @param value - initialized values
    */
  public NslInt0(String name, NslNumeric0 value) {
	super(name);
    _data = new NslIntegerObj(value.getint());
    _name = name;
  
  }
  public NslInt0(String name, NslHierarchy curParent, NslNumeric0 value) {
	super(name,curParent,curParent.nslGetAccess());

    _data = new NslIntegerObj(value.getint());
    //_name = name;
    //module = curParent;
    //desiredAccessibility = module.nslGetAccess();
    //module.enableAccess(this);
  
  }
  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param name - name of the variable
   * @param value - the init value
   */
  // aa
  public NslInt0(String name, int value) {
	super(name);
    _data = new NslIntegerObj(value);
    //_name = name;
  }

  public NslInt0(String name, NslHierarchy curParent, int value) {
	super(name,curParent,curParent.nslGetAccess());
    _data = new NslIntegerObj(value);
    //_name = name;
    //module = curParent;
    //desiredAccessibility = module.nslGetAccess();
    //module.enableAccess(this);
  }
//---various ---------
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
      set(((NslInt0)n).getint());

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
    if(isDataSet())
      return (NslData)(new NslInt0(getint()));
    else return (NslData) (new NslInt0());
  }

  /**
   * Set the reference to the wrapped data of <tt>n</tt>
     It is used in double buffered ports, to make the the ports
     reference different number object at different time.
   * @param n - number concerned
   */
  public NslData setReference(NslData n) {
    try {
      _data = ((NslInt0) n)._data;
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

    public NslInt0 setReference(int value) {
	_data.value = value;
	return this;
    }

    /**
     * Check if the internal number data is null
     * @return - true if it is defined, false if the reference is null
     */
  public boolean isDataSet() {
    return (_data != null);
  }
  /** Reset the reference pointer to null
   */
  public void resetData() {
    _data = null;
  }

  //<CHHANGED>
  public String toString() {
    return Integer.toString(_data.value);
  }
//----get-------------------------------------
  /**
   * Get the value of this number
   * @return value, in default type
   */
  public int get() {
    return _data.value;
  }
/**
   * Get the value of this number in double precision floating point number
   * @return value - in double precision pointing point
   */
//todo: change to getNslInt 
public NslIntegerObj _getint() { return _data; }

/**
   * Get the value of this number in integer
   * @return value - in integer
   */
  public int    getint0() {
    return _data.value;
  }


  /**
   * Get the value of this number in double precision floating point number
   * @return value - in double precision pointing point
   */
  public double getdouble() {
    return (double)_data.value;
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
    return _data.value;
  }

// Interface with NslNumeric type

  // Interface with NslNumeric type
  /**
   * Get the value of this number in double precision
     floating point NslNumeric
   * @return NslNumeric - in double precision pointing point
   */
  public NslDouble0 getNslDouble0() {
    return (new NslDouble0((double)(_data.value)));
  }

  /**
   * Get the value of this number in single precision
     floating point NslNumeric
   * @return NslNumeric - in single precision pointing point
   */
  public NslFloat0  getNslFloat0() {
    return (new NslFloat0((float)(_data.value)));
  }

  /**
   * Get the value of this number in integer
   * @return NslNumeric - in integer
   */
  public NslInt0    getNslInt0() {
    return this;
  }
//--------------set------------------------------------
/**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(double value) {
    return _data.value = (int)value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(float value) {
    return _data.value = (int)value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(int value) {
    return _data.value = value;
  }

/**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public void _set(double value) {
    _data.value = (int)value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public void _set(float value) {
    _data.value = (int)value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public void _set(int value) {
    _data.value = value;
  }

/**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(double[] value) {
    if (value.length==1) {
	return _data.value = (int)value[0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(float[] value) {
    if (value.length==1) {
	return _data.value = (int)value[0];
    }  else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(int[] value) {
    if (value.length==1) {
	return _data.value = (int)value[0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(double[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (int)value[0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(float[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (int)value[0][0];
    }  else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(int[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (int)value[0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(double[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = (int)value[0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(float[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = (int)value[0][0][0];
    }  else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(int[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = (int)value[0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }  
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(double[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	 return _data.value = (int)value[0][0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(float[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	return _data.value = (int)value[0][0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }  
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public int set(int[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	return _data.value = (int)value[0][0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }    

  /**
   * Set the value of this number to <tt>num</tt>
   * @param num, a scalar NslNumeric
   */
  public int set(NslNumeric0 num) {
    return _data.value = num.getint();
  }

  /**
   * Set the value of this number to <tt>num</tt>
   * @param num, a scalar NslNumeric
   */
  public void _set(NslNumeric0 num) {
    _data.value = num.getint();
  }


// old methods, for java language only, obsolete later.
/*
  public double doubleValue() {
    return (double)_data.value;
  }

  public float  floatValue() {
    return (float)_data.value;
  }

  public int    intValue() {
    return _data.value;
  }
*/

}

// NslInteger.java
////////////////////////////////////////////////////////////////////////////////



