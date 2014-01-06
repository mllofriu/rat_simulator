/*  SCCS - @(#)NslFloat0.java	1.14 --- 02/09/00 --13:49:13 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslFloat0.java


/**
* NslFloat0 - single precision floating point number
*/
package nslj.src.lang;

public class NslFloat0 extends NslNumeric0 {
// 
  public NslFloatObj _data;
 // public String _name;
 // boolean visibility = true;
 // NslHierarchy module;
  /**
   * Constructor with default value 0
   */

  public NslFloat0() {
	super();
    //_data = null;
    _data = new NslFloatObj(0);
    //_name = null;
  }

  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param value - the init value
   */
  public NslFloat0(float value) {
	super();
    _data = new NslFloatObj(value);
    //_name = null;
  }

  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param value - the init value
   */
  public NslFloat0(int value) {
	super();

    _data = new NslFloatObj(value);
    //_name = null;
  }

  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param value - the init value
   */
  public NslFloat0(double value) {
	super();

    _data = new NslFloatObj(value);
    _name = null;
  }//

  /**
   * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
   * @param n - a scalar number
   */
  public NslFloat0(NslNumeric0 n) {
	super();

    _data = new NslFloatObj(n.getfloat());
    //_name = null;
  }

  public NslFloat0(String name) {
	super(name);
	_data = new NslFloatObj(0);
    //_name = name;
  }

  public NslFloat0(String name, NslHierarchy curParent) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new NslFloatObj(0);
    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  }
  
/**
    * This constructs a number with specified name
    * @param name - name of the variable
    * @param value - initialized values
    */
  public NslFloat0(String name, NslNumeric0 value) {
     super(name);
    _data = new NslFloatObj(value.getfloat());
    //_name = name;
  }
  public NslFloat0(String name, NslHierarchy curParent, NslNumeric0 value) {
 	super(name,curParent,curParent.nslGetAccess());
	_data = new NslFloatObj(value.getfloat());
    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  
  }
  //98/8/21 aa
  /**
   * Constructor, initialize the value to <tt>value</tt>
   * @param name - name of the variable
   * @param value - the init value
   */
  public NslFloat0(String name, NslHierarchy curParent, float value) {
	super(name,curParent,curParent.nslGetAccess());
    _data = new NslFloatObj(value);
    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  }
  public NslFloat0(String name, float value) {
	super(name);
    _data = new NslFloatObj(value);
    //_name = name;
  }
//98/7/26 aa
  // use c=a.nslNew();
  //public NslFloat0 nslNew() {
  //	NslFloat0 c=new NslFloat0();
  //	return c;
  // }
//----------- various -------------------------


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
      set(((NslFloat0)n).getfloat());

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
      return (NslNumeric) (new NslFloat0(getfloat()));
    else
      return (NslNumeric) (new NslFloat0());
  }

  /**
   * Set the reference to the wrapped data of <tt>n</tt>
     It is used in double buffered ports, to make the the ports
     reference different number object at different time.
   * @param n - number concerned
   */
  public NslData setReference(NslData n) {
    try {
      _data = ((NslFloat0) n)._data;
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

    public NslFloat0 setReference(float value) {
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
//
  public String toString() {
    return Float.toString(_data.value);
  }



// ------------get-------------------------------
  /**
   * Get the value of this number
   * @return value, in default type
   */
// 98/7/21 aa: changed return type from double to float
  public float get() {
    return _data.value;
  }
/**
   * Get the value of this number
   * @return value, in NslNumeric type
   */
  public NslFloatObj _getfloat() { return _data; }
/**
   * Get the value of this number 
   * @return value - 
   */
  public float getfloat0() {
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
    return _data.value;
  }

/**
   * Get the value of this number in integer
   * @return value - in integer
   */
  public int    getint() {
    return (int)_data.value;
  }

/**
   * Get the value of this number in single precision
     floating point NslNumeric
   * @return NslNumeric - in single precision pointing point
   */
  public NslDouble0  getNslDouble0() {
    return (new NslDouble0(this.getdouble()));
  }

/**
   * Get the value of this number in single precision
     floating point NslNumeric
   * @return NslNumeric - in single precision pointing point
   */
  public NslFloat0  getNslFloat0() {
    return this;
  }
/**
   * Get the value of this number in single precision
     floating point NslNumeric
   * @return NslNumeric - in single precision pointing point
   */
  public NslInt0  getNslInt0() {
    return (new NslInt0(this.getint()));
  }

//-------set-------------------------------------------

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(double value) {
    return _data.value = (float)value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(float value) {
    return _data.value = value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(int value) {
    return _data.value = (float) value;
  }


  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public void _set(double value) {
    _data.value = (float)value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public void _set(float value) {
    _data.value = value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public void _set(int value) {
    _data.value = (float) value;
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(double[] value) {
    if (value.length==1) {
	return _data.value = (float)value[0];
    } else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(float[] value) {
    if (value.length==1) {
	return _data.value = (float)value[0];
    }  else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(int[] value) {
    if (value.length==1) {
	return _data.value = (float)value[0];
    } else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }
  }
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(double[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (float)value[0][0];
    } else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(float[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (float)value[0][0];
    }  else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(int[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (float)value[0][0];
    } else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }
  }
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(double[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = (float)value[0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(float[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = (float)value[0][0][0];
    }  else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(int[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = (float)value[0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }
  }  
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(double[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	 return _data.value = (float)value[0][0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(float[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	return _data.value = (float)value[0][0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }  
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public float set(int[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	return _data.value = (float)value[0][0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
        return _data.value;
    }
  }    
  /**
   * Set the value of this number to <tt>num</tt>
   * @param num, a scalar NslNumeric
   */
  public float set(NslNumeric0 num) {
    return _data.value = num.getfloat();
  }
  /**
   * Set the value of this number to <tt>num</tt>
   * @param num, a scalar NslNumeric
   */
  public void _set(NslNumeric0 num) {
    _data.value = num.getfloat();
  }


// old methods, for java language only, obsolete later.
/*
  public double doubleValue() {
    return (double)_data.value;
  }

  public float  floatValue() {
    return _data.value;
  }

  public int    intValue() {
    return (int)_data.value;
  }
*/

}

// NslFloat.java
////////////////////////////////////////////////////////////////////////////////



