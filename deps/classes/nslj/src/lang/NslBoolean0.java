/*  SCCS - @(#)NslBoolean0.java	1.5 - 02/09/00 - 13:49:14 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslBoolean0.java


/**
 * NslBoolean0 - boolean scalar
 */

package nslj.src.lang;

public class NslBoolean0 extends NslBoolean {

    public NslBooleanObj _data;

    /**
     * Constructor with default value null
     */
    public NslBoolean0() {
    	super();
	//_data = null;
	_data = new NslBooleanObj(false);
    }

    /**
     * Constructor, initialize the value to <tt>value</tt>
     * @param value - the init value
     */  
        
    public NslBoolean0(boolean value) {
	super();
	_data = new NslBooleanObj(value);
    }

    /**
     * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
     * @param n - a scalar number
     */
     
    public NslBoolean0(NslNumeric0 n) {
	super();
	_data = new NslBooleanObj(n.getint()!=0);
    }
    
    /**
     * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
     * @param n - a scalar number
     */
     
    public NslBoolean0(NslBoolean0 n) {
	super();
	_data = new NslBooleanObj(n.getboolean());
    }

    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     */
     
    public NslBoolean0(String name) {
	super(name);
	_data = new NslBooleanObj(false);
    }

    public NslBoolean0(String name, NslHierarchy curParent) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new NslBooleanObj(false);
    }
  

    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param value - initialized values
     */

    public NslBoolean0(String name, NslNumeric0 value) {
	super(name);
        _data = new NslBooleanObj(value.getint()!=0);
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param value - initialized values
     */

    public NslBoolean0(String name, NslBoolean0 value) {
	super(name);
        _data = new NslBooleanObj(value.getboolean());
    }
    
    public NslBoolean0(String name, NslHierarchy curParent, NslNumeric0 value) {
	super(name,curParent,curParent.nslGetAccess());

	_data = new NslBooleanObj(value.getint()!=0);  
    }
    
    public NslBoolean0(String name, NslHierarchy curParent, NslBoolean0 value) {
	super(name,curParent,curParent.nslGetAccess());

	_data = new NslBooleanObj(value.getboolean());  
    }
    
    /**
     * Constructor, initialize the value to <tt>value</tt>
     * @param name - name of the variable
     * @param value - the init value
     */
  
    public NslBoolean0(String name, int value) {
	super(name);
	_data = new NslBooleanObj(value!=0);
    }

    public NslBoolean0(String name, NslHierarchy curParent, int value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new NslBooleanObj(value!=0);
    }
    
    /**
     * Constructor, initialize the value to <tt>value</tt>
     * @param name - name of the variable
     * @param value - the init value
     */
  
    public NslBoolean0(String name, boolean value) {
	super(name);
	_data = new NslBooleanObj(value);
    }

    public NslBoolean0(String name, NslHierarchy curParent, boolean value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new NslBooleanObj(value);
    }
    
    //---various ---------
    /*
     * Duplicating data between buffers in double buffering port model.
     * Since we cannot ensure the copy is the original copy created
     * in instantiation, this code is to make a security check and
     * to make sure the program runs correctly in the latter step.
     */

    public void duplicateData(NslData n) {
	try {
	    /* Here we assume that the passed parameter is originally a
	       NslBoolean0 class. Otherwise, it will force a ClassCastException
  	       and notify the NslSystem.
 	     */
	    set(((NslBoolean0)n).getboolean());
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
	    return (NslData)(new NslBoolean0(getboolean()));
	else return (NslData) (new NslBoolean0());
    }

    /**
     * Set the reference to the wrapped data of <tt>n</tt>
     * It is used in double buffered ports, to make the the ports
     * reference different number object at different time.
     * @param n - number concerned
     */

    public NslData setReference(NslData n) {
	try {
	    _data = ((NslBoolean0) n)._data;
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
     * @param value - value concerned
     */

    public NslBoolean0 setReference(boolean value) {
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

    /** 
     * Reset the reference pointer to null
     */

    public void resetData() {
	_data = null;
    }

    public String toString() {
	return (new Boolean(_data.value)).toString();
    }
    
    //----get-------------------------------------
    /**
     * Get the value of this number
     * @return value, in default type
     */
    
    public boolean get() {
	return _data.value;
    }
    
    /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */
    //todo: change to getNslBoolean 
    
    public NslBooleanObj _getboolean() { return _data; }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */
    public boolean getbolean0() {
	return _data.value;
    }

    /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */

    public double getdouble() {
	return (double)(_data.value?1.0:0.0);
    }

    /**
     * Get the value of this number in single precision floating point number
     * @return value - in single precision pointing point
     */

    public float  getfloat() {
	return (float)(_data.value?1.0:0.0);
    }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public int getint() {
	return _data.value?1:0;
    }
    
    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public boolean getboolean() {
	return _data.value;
    }

    // Interface with NslNumeric type
    
    /**
     * Get the value of this number in double precision
     * floating point NslNumeric
     * @return NslNumeric - in double precision pointing point
     */
     
    public NslDouble0 getNslDouble0() {
	return (new NslDouble0(getdouble()));
    }

    /**
     * Get the value of this number in single precision
     * floating point NslNumeric
     * @return NslNumeric - in single precision pointing point
     */
     
    public NslFloat0  getNslFloat0() {
	return (new NslFloat0(getfloat()));
    }
    
    /**
     * Get the value of this number in integer
     * @return NslNumeric - in integer
     */
     
    public NslInt0 getNslInteger0() {
	return (new NslInt0(getint()));
    }

    /**
     * Get the value of this number in integer
     * @return NslNumeric - in integer
     */
     
    public NslBoolean0 getNslBoolean0() {
	return this;
    }
    
    //--------------set------------------------------------
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public boolean set(double value) {
	return _data.value = value!=0.0;
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public boolean set(float value) {
	return _data.value = value!=0.0;
    }
  
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public boolean set(int value) {
	return _data.value = value!=0;
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public boolean set(boolean value) {
	return _data.value = value;
    }

    /**
     * Set the value of this number to <tt>num</tt>
     * @param num, a scalar NslNumeric
     */

    public boolean set(NslNumeric0 num) {
	return _data.value = num.getint()!=0;
    }

/**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(boolean[] value) {
    if (value.length==1) {
	return _data.value = value[0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

/**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(double[] value) {
    if (value.length==1) {
	return _data.value = (boolean)(value[0]!=0);
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(float[] value) {
    if (value.length==1) {
	return _data.value = (boolean)(value[0]!=0);
    }  else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(int[] value) {
    if (value.length==1) {
	return _data.value = (boolean)(value[0]!=0);
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(double[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (boolean)(value[0][0]!=0);
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(boolean[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (boolean)value[0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(float[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (boolean)(value[0][0]!=0);
    }  else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(int[][] value) {
    if (value.length==1 && value[0].length==1) {
	return _data.value = (boolean)(value[0][0]!=0);
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(double[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = (boolean)(value[0][0][0]!=0);
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(boolean[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = value[0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(float[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = (boolean)(value[0][0][0]!=0);
    }  else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }   
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(int[][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1) {
	return _data.value = (boolean)(value[0][0][0]!=0);
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }  
  
  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(double[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	 return _data.value = (boolean)(value[0][0][0][0]!=0);
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(float[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	return _data.value = (boolean)(value[0][0][0][0]!=0);
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }  
  }

  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(int[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	return _data.value = (boolean)(value[0][0][0][0]!=0);
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }    


  /**
   * Set the value of this number to <tt>value</tt>
   * @param value
   */
  public boolean set(boolean[][][][] value) {
    if (value.length==1 && value[0].length==1 && value[0][0].length==1 && value[0][0][0].length==1) {
	return _data.value = value[0][0][0][0];
    } else {
    	System.out.println("Dimensions don't agree");
	return _data.value;
    }
  }
    /**
     * Set the value of this number to <tt>num</tt>
     * @param num, a scalar NslBoolean
     */

    public boolean set(NslBoolean0 num) {
	return _data.value = num.getboolean();
    }

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

}

// NslBoolean0.java
////////////////////////////////////////////////////////////////////////////////



