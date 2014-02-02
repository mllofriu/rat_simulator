/* SCCS  @(#)NslString0.java	1.6 --- 02/09/00 -- 13:49:14 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslString0.java
//

package nslj.src.lang;

import java.lang.*;

public class NslString0 extends NslString {

    public String _data = "";

    /**
     * Constructor with default value null
     */
     
    public NslString0() {
    	super();
	_data = new String("");
    }
    
    /**
     * Constructor, initialize the value to <tt>value</tt>
     * @param value - the init value
     */  
        
    public NslString0(String value) {
	super();
	_data = new String(value);
    }

    /**
     * Constructor, initialize the value to <tt>value</tt>
     * @param value - the init value
     */  
        
    public NslString0(NslString0 value) {
	super();
	_data = new String(value.getstring());
    }

    public NslString0(String label, NslHierarchy parent) {
	super(label, parent, parent.nslGetAccess());
    }
    
    public NslString0(String label, NslHierarchy parent, NslString0 value) {
	super(label, parent, parent.nslGetAccess());
	_data = new String(value.getstring());
    }

    public NslString0(String label, NslHierarchy parent, String value) {
	super(label, parent, parent.nslGetAccess());
	_data = new String(value);
    }
    
    //----get-------------------------------------
    /**
     * Get the value of this number
     * @return value, in default type
     */
    
    public String get() {
	return _data;
    }
      /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */

    public double getdouble() {
	return Double.valueOf(_data).doubleValue();
    }

    /**
     * Get the value of this number in single precision floating point number
     * @return value - in single precision pointing point
     */

    public float  getfloat() {
	return Float.valueOf(_data).floatValue();
    }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public int getint() {
	return Integer.getInteger(_data).intValue();
    }
    
    /**
     * Get the value of this number in boolean
     * @return value - in integer
     */

    public boolean getboolean() {
	return Boolean.getBoolean(_data);
    }
      
    /**
     * Get the value of this number in string
     * @return value - in integer
     */
   
    public String getstring() {
    	return _data;
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
	return (new NslBoolean0(getboolean()));
    }
    
    /**
     * Get the value of this number in integer
     * @return NslNumeric - in integer
     */
     
    public NslString0 getNslString0() {
	return this;
    }
  
    //--------------set------------------------------------
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public String set(double value) {
	return _data = new String(""+value);
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public String set(float value) {
	return _data = new String(""+value);
    }
  
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public String set(int value) {
	return _data = new String(""+value);
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public String set(boolean value) {
	return _data = new String(""+value);
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public String set(String value) {
	return _data = new String(value);
    }

    /**
     * Set the value of this number to <tt>num</tt>
     * @param num, a scalar NslNumeric
     */

    public String set(NslNumeric0 num) {
	return _data = new String(""+num.getdouble());
    }

    /**
     * Set the value of this number to <tt>num</tt>
     * @param num, a scalar NslBoolean
     */

    public String set(NslBoolean0 num) {
	return _data = new String(""+num.getboolean());
    }

    /**
     * Set the value of this number to <tt>num</tt>
     * @param num, a scalar NslBoolean
     */

    public String set(NslString0 num) {
	return _data = new String(num.getstring());
    }
    
    /**
     * Copy all data from <tt>n</tt> to this number object.
     * It is used in <tt>NslPort<tt> and relevant classes
     * only.
     * @param n - number to be copied
     * @see NslPort, NslOutport
     */
 
    public void duplicateData(NslData string) {
    	_data = new String(string.toString());
    }

    /**
     * This method is equivalent to <tt>clone()</tt> method
     * in java.lang.Object. It duplicates a copy of this number
     * object. It is used in double buffered outport only.
     * @see NslPort, NslOutport.
     */
  
    public NslData duplicateThis() {
    	if (nslGetParent() instanceof NslClass) {
    	    return new NslString0(nslGetName(),(NslClass)nslGetParent(),_data);
    	} else {
    	    return new NslString0(nslGetName(),(NslModule)nslGetParent(),_data);
    	}
    }
    
    /**
     * Set the reference pointer of this string object to the
     * data value of <tt>n</tt>. It is similar to <i>two pointers
     * pointing to a same object<i> in C/C++. Whenenver the data
     * value of one side is changed, the other side is changed as
     * well. It is used only in <tt>NslPort</tt>
     * @param n - number that the reference pointer to the "true"
     * numerical data is copied.
     * @see NslPort, NslInport
     */
     
    public NslData setReference(NslData string) {
     	if (string instanceof NslString0) {
	    _data = string.toString();
     	}
	return this;
    }

    /**
     * Set the reference pointer of this string object to the
     * data value of <tt>n</tt>. It is similar to <i>two pointers
     * pointing to a same object<i> in C/C++. Whenenver the data
     * value of one side is changed, the other side is changed as
     * well. It is used only in <tt>NslPort</tt>
     * @param n - number that the reference pointer to the "true"
     * numerical data is copied.
     * @see NslPort, NslInport
     */
     
    public NslString0 setReference(String string) {
	_data = string;
	return this;
    }

    /**
     * Check if the string is well-defined and the corresponding
     * string data is set.
     * @return true - if the data is well-defined. false - if the
     * data is null.
     */
     
    public boolean isDataSet() {
   	return !_data.equals("");
    }

    /** Reset the reference pointer to null
     */

    public void resetData() {
    	_data="";
    }
     
    /**
     * Print the value of the string
     */
  
    public String toString() {
     	return _data;
    }
     
    public int getDimensions() {
     	return 0;
    }
     
    public int getSize1() {
     	return _data.length();
    }  
     
}

