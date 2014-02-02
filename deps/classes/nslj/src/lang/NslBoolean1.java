/*  SCCS - @(#)NslBoolean1.java	1.5 - 09/20/99 - 19:19:37 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslBoolean1.java


/**
 * NslBoolean1 - boolean vector
 */

package nslj.src.lang;

public class NslBoolean1 extends NslBoolean {

    public boolean[] _data;

    /**
     * Constructor with default value null
     */
    public NslBoolean1() {
    	super();
	_data = null;
    }

    /**
     * Constructor, initialize the value to <tt>value</tt>
     * @param value - the init value
     */  
        
    public NslBoolean1(boolean[] value) {
	super();
	_data = new boolean[value.length];
	set(value);
    }

    /**
     * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
     * @param n - a scalar number
     */
     
    public NslBoolean1(NslNumeric1 n) {
	super();
	_data = new boolean[n.getSize()];
	set(n.getint1());
    }
    
    /**
     * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
     * @param n - a scalar number
     */
     
    public NslBoolean1(NslBoolean1 n) {
	super();
	_data = new boolean[n.getSize()];
	set(n.getboolean1());
    }

    /**
     * Constructor, initialize the number to be size <tt>size</tt> 1-D array
     * @param size - size of the new array
     */
  
    public NslBoolean1(int size) {
	super();
	_data = new boolean[size];
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     */
     
    public NslBoolean1(String name) {
	super(name);
	_data = null;
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param size - size of the array
     */

    public NslBoolean1(String name, int size) {
	super(name);
	_data = new boolean[size];
    }

    public NslBoolean1(String name, NslHierarchy curParent) {
	super(name,curParent,curParent.nslGetAccess());
	_data = null;
    }  

    public NslBoolean1(String name, NslHierarchy curParent, int size) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[size];	
    }  
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param value - initialized values
     */

    public NslBoolean1(String name, NslNumeric1 value) {
	super(name);
        _data = new boolean[value.getSize()];
        set(value.getint1());
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param value - initialized values
     */

    public NslBoolean1(String name, NslBoolean1 value) {
	super(name);
        _data = new boolean[value.getSize()];
        set(value.getboolean1());   
    }
    
    public NslBoolean1(String name, NslHierarchy curParent, NslNumeric1 value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[value.getSize()];
	set(value.getint1()); 
    }
    
    public NslBoolean1(String name, NslHierarchy curParent, NslBoolean1 value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[value.getSize()];
	set(value.getboolean1()); 
    }

    public NslBoolean1(String name, NslHierarchy curParent, boolean[] value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[value.length];
	set(value); 
    }
    
    /**
     * allocate memory dynamically
     */
     
    public void nslMemAlloc(int size) {
	_data = new boolean[size];
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
	    set(((NslBoolean1)n).getboolean1());
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
	    return (NslData)(new NslBoolean1(getboolean1()));
	else return (NslData) (new NslBoolean1());
    }

    /**
     * Set the reference to the wrapped data of <tt>n</tt>
     * It is used in double buffered ports, to make the the ports
     * reference different number object at different time.
     * @param n - number concerned
     */

    public NslData setReference(NslData n) {
	try {
	    _data = ((NslBoolean1) n).getboolean1();
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

    public NslBoolean1 setReference(boolean[] value) {
	_data = value;
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
	StringBuffer strbuf = new StringBuffer();

	for(int i=0; i<_data.length; i++)
	    strbuf.append(_data[i]+" ");
	
	return strbuf.toString();
    }
    
    //----get-------------------------------------
    /**
     * Get the value of this number
     * @return value, in default type
     */
    
    public boolean[] get() {
	return _data;
    }
    
    public boolean get(int pos) {
	return _data[pos];
    }

    /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */

    public double[] getdouble1() {
	double[] doubledata = new double[_data.length];

	for (int i=0; i<_data.length; i++) {
	    doubledata[i]=(double)(_data[i]?1.0:0.0);
	}

	return doubledata;
    }

    /**
     * Get the value of this number in single precision floating point number
     * @return value - in single precision pointing point
     */

    public float[] getfloat1() {
	float[] floatdata = new float[_data.length];

	for (int i=0; i<_data.length; i++) {
	    floatdata[i]=(float)(_data[i]?1.0:0.0);
	}

	return floatdata;
    }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public int[] getint1() {
	int[] intdata = new int[_data.length];

	for (int i=0; i<_data.length; i++) {
	    intdata[i]=(int)(_data[i]?1:0);
	}

	return intdata;
    }
    
    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public boolean[] getboolean1() {
	return _data;
    }
    
    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>double</tt>.
     */
     
    public double getdouble(int pos){
	return (double)(_data[pos]?1.0:0.0);
    }

    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>float</tt>.
     */
     
    public float getfloat(int pos) {
	return (float)(_data[pos]?1.0:0.0);
    }
    
    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>int</tt>.
     */
  
    public int getint(int pos){
	return (int)(_data[pos]?1:0);
    }

    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>int</tt>.
     */
  
    public boolean getboolean(int pos){
	return _data[pos];
    }
    
    // Interface with NslNumeric type
    
    /**
     * Get the value of this number in double precision
     * floating point NslNumeric
     * @return NslNumeric - in double precision pointing point
     */
     
    public NslDouble1 getNslDouble1() {
	return (new NslDouble1(getdouble1()));
    }

    /**
     * Get the value of this number in single precision
     * floating point NslNumeric
     * @return NslNumeric - in single precision pointing point
     */
     
    public NslFloat1 getNslFloat1() {
	return (new NslFloat1(getfloat1()));
    }
    
    /**
     * Get the value of this number in integer
     * @return NslNumeric - in integer
     */
     
    public NslInt1 getNslInteger1() {
	return (new NslInt1(getint1()));
    }

    /**
     * Get the value of this number in integer
     * @return NslNumeric - in integer
     */
     
    public NslBoolean1 getNslBoolean1() {
	return this;
    }

    //----------------
    /**
     * Create an array that captures element <tt>start</tt> to <tt>end</tt>
     * if <tt>start</tt> is smaller than 0, <tt>start</tt> is default as 0;
     * if <tt>end</tt> is greater than the length of the array,
     *  <tt>end</tt> is default as the length of the array
     * @param start - the element number start the capture
     * @param end - the element number ends the capture
     * @return a section of the original array
     */

    public boolean[] getSector(int start, int end) {
	int i, j;
        int length;
	boolean[] intdata;

	if (start < 0)
	    start = 0;
	if (end > _data.length)
	    end = _data.length;
	length = end-start+1;
	intdata = new boolean[length];
	i = start;
	
	for (j=0; j<length; j++, i++) {
	    intdata[j]=_data[i];
	}
	
	return intdata;
    }
    
    //--------------set------------------------------------
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public boolean[] set(double[] value) {

	if (_data.length != value.length) {
	    System.out.println("NslBoolean1: array size not match");
	    return _data;
	}

	for (int i=0; i<_data.length; i++) {
	    _data[i]=value[i]!=0.0;
	}
	
	return _data;	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public boolean[] set(float[] value) {

	if (_data.length != value.length) {
	    System.out.println("NslBoolean1: array size not match");
	    return _data;
	}

	for (int i=0; i<_data.length; i++) {
	    _data[i]=value[i]!=0.0;
	}
	
	return _data;	
    }
  
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public boolean[] set(int[] value) {

	if (_data.length != value.length) {
	    System.out.println("NslBoolean1: array size not match");
	    return _data;
	}

	for (int i=0; i<_data.length; i++) {
	    _data[i]=value[i]!=0;
	}
	
	return _data;	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public boolean[] set(boolean[] value) {
	if (_data.length != value.length) {
	    System.out.println("NslBoolean1: array size not match");
	    return _data;
	}

	for (int i=0; i<_data.length; i++) {
	    _data[i]=value[i];
	}
	
	return _data;	
    }
    
    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
     
    public boolean set(int pos, double value) {
	_data[pos]=value!=0.0;
	return _data[pos];
    }
    
    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
     
    public boolean set(int pos, float value) {
        _data[pos]=value!=0.0;
        return _data[pos];
    }

    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
  
    public boolean set(int pos, int value) {
	_data[pos]=value!=0;
	return _data[pos];
    }

    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
  
    public boolean set(int pos, boolean value) {
	_data[pos]=value;
	return _data[pos];
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */

    public boolean[] set(double value) {
	for (int i=0; i<_data.length; i++) {
	    _data[i]=value!=0.0;
        }
       	return _data;
    }
    
    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[] set(float value) {
	for (int i=0; i<_data.length; i++) {
	    _data[i]=value!=0.0;
	}
	return _data;
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */

    public boolean[] set(int value) {
	for (int i=0; i<_data.length; i++) {
	    _data[i]=value!=0;
	}
	return _data;
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */

    public boolean[] set(boolean value) {
	for (int i=0; i<_data.length; i++) {
	    _data[i]=value;
	}
	return _data;
    }

    /**
     * Set the value of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[] set(NslNumeric1 value) {
	if (_data.length != value.getSize()) {
	    System.out.println("NslInt1: internal data length not equal set value length. set() ignored");
	    return _data;
	}
	set(value.getint1());
	return _data;
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[] set(NslNumeric0 value) {
	for (int i=0; i<_data.length; i++) {
	    _data[i]=value.getint()!=0;
    	}
    	
	return _data;
    }
    
    public boolean set(int pos, NslNumeric0 value) {
	_data[pos]=value.getint()!=0;
	return _data[pos];
    }
    
    /**
     * Set the value of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[] set(NslBoolean1 value) {
	if (_data.length != value.getSize()) {
	    System.out.println("NslInt1: internal data length not equal set value length. set() ignored");
	    return _data;
	}
	set(value.getboolean1());
	return _data;
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[] set(NslBoolean0 value) {
	for (int i=0; i<_data.length; i++) {
	    _data[i]=value.getboolean();
    	}    	
	return _data;
    }
    
    public boolean set(int pos, NslBoolean0 value) {
	_data[pos]=value.getboolean();
	return _data[pos];
    }

    /**
     * Set the value of the array from <tt>startpos</tt> to d
     * If the array <tt>d</tt> longer than this array, those
     * out of array scope elements are ignored.
     * @param d - object 1-D array
     * @startpos - the element number to start copying
     */
     
    public void setSector(boolean[] d, int startpos) {
	int endpos = d.length+startpos;
	int i, j=0;

	if (startpos > _data.length)
	    return;
	    
	if (endpos > _data.length)
	    endpos = _data.length;
	    
	for (i=startpos; i<endpos; i++, j++)
	    _data[i] = d[j];
    }
  
    //---various ---------------------------------------
    /*-------------Non-Abstract methods ------------------*/

    /**
     * get the dimensions of this object
     * @return always zero
     */

    public int getDimensions() {
	return 1;
    }

    /**
     * get the size of this array 
     * @return always zero
     */
    public int getSize() {
	return (_data==null?0:_data.length);
    }
    
    /**
     * get the size of this array and put it into <tt>size</tt>
     * @param size
     */

    public  void getNslSize(NslInt0 size) {
	size.set((_data==null?0:_data.length));
    }

    /**
     * get the size of this array and put it into <tt>size</tt>
     * @return size - always a vector of 4 elements
     */

    public int[] getSizes(){
	int[]size =new int[4];
     	size[0]=(_data==null?0:_data.length);
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
	return (_data==null?0:_data.length);
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

// NslBoolean1.java
////////////////////////////////////////////////////////////////////////////////



