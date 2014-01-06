/*  SCCS @(#)NslInt1.java	1.13 --- 09/20/99 --19:19:56 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslInt1.java

/**
* NslInt1 - one dimensional integer array
*/
package nslj.src.lang;


public class NslInt1 extends NslNumeric1 {
  public int[] _data;
  //public String _name;
  //boolean visibility = true;  //_accessChar
  //NslHierarchy module;  //_parent

  ////////////////////////////////////////////////////////////////////
  // constructors

  /**
   * Constructor, copy the contain of array <tt>d</tt> to
     the new number
   * @param d - the value of new number
   */
  public NslInt1(int[] d) {
	super();
    _data = new int[d.length];
    set(d);
    //_name = null;
  }

  /**
   * Constructor, initialize the value to the same as another <tt>NslNumeric1</tt>
   * @param n - a 1-D array
   */
  public NslInt1(NslNumeric1 n) {
	super();
    _data = new int[n.getSize()];
    set(n.getint1());
    //_name = null;
  }

  /**
   * Constructor, initialize the number to be size <tt>size</tt> 1-D array
   * @param size - size of the new array
   */
  public NslInt1(int size) {
	super();
    _data = new int[size];
    //_name = null;
  }

  /**
   * Constructor - initialize the reference of the number to be null
   * @see NslPort, NslInport
   */
  public NslInt1() {
	super();
    _data=null;
    //_name = null;
  }

  /** 
    * This constructs a number with specified name
    * @param name - name of the variable
    */
  public NslInt1(String name) {
	super();
    _data = null;
    _name = name;
  }//
  public NslInt1(String name, NslHierarchy curParent) {
	super(name,curParent,curParent.nslGetAccess());
    _data = null;

    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  }

  /**
    * This constructs a number with specified name
    * @param name - name of the variable
    * @param size - size of the array
    */
  public NslInt1(String name, int size) {
	super(name);
    _data = new int[size];
    //_name = name;
  }

  public NslInt1(String name, NslHierarchy curParent, int size) {
	super(name,curParent,curParent.nslGetAccess());
    _data = new int[size];
    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  }

  /**
    * This constructs a number with specified name
    * @param name - name of the variable
    * @param n - initialized values
    */
  public NslInt1(String name, NslNumeric1 n) {
	super(name);
    _data = new int[n.getSize()];
    set(n.getint1());
    //_name = name;
  
  }
  public NslInt1(String name,  NslHierarchy curParent, NslNumeric1 n) {
	super(name,curParent,curParent.nslGetAccess());

    _data = new int[n.getSize()];
    set(n.getint1());
   // _name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  
  }
  /**
   * Constructor, copy the contain of array <tt>d</tt> to
     the new number
   * @param name - name of the variable
   * @param d - the value of new number
   */
  public NslInt1(String name, int[] d) {
	super(name);
    _data = new int[d.length];
    set(d);
    //_name = name;
  }
    public void nslMemAlloc(int size) {
	_data = new int[size];
    }
//-------gets--------------------------------
/**
   * Get the contain of 1D array
     in integer
   * @return array - in integer
   */
  public int[] get() {
    return _data;
  }

public int get(int pos1) {
    return _data[pos1];
  }
/**
   * Get the array
     in double precision floating point
	@return array - in double precision pointing point
   */
// Should be overriden in NslDouble1 for efficiency
  public double[] getdouble1() {
    double[] doubledata = new double[_data.length];
    int i;

    for (i=0; i<_data.length; i++) {
      doubledata[i]=(double)_data[i];
    }
    return doubledata;
  }
  /**
   * Get the array
     in single precision floating point
   * @return array - in single precision pointing point
   */
// Should be overriden in NslFloat1 for efficiency

  public float[] getfloat1() {
    float[] floatdata = new float[_data.length];
    int i;

    for (i=0; i<_data.length; i++) {
      floatdata[i]=(float)_data[i];
    }

    return floatdata;
  }
/**
   * Get the array
     in single precision floating point
   * @return array - in int
   */
  public int[] getint1() {
	return _data;
  }
 //-----------------

/**
   * Get the <tt>pos</tt>th element of this one-dimensional array
   * @return - value in java numerical type <tt>double</tt>.
   */
  public double getdouble(int pos){
	return (double)_data[pos];
  }
/**
   * Get the <tt>pos</tt>th element of this one-dimensional array
   * @return - value in java numerical type <tt>float</tt>.
   */
  public float  getfloat(int pos) {
	return (float)_data[pos];
  }
  /**
   * Get the <tt>pos</tt>th element of this one-dimensional array
   * @return - value in java numerical type <tt>int</tt>.
   */
  public int    getint(int pos){
	return _data[pos];
  }
  //-----------
/**
   * Get a <tt>NslInt1</tt> array with the same value as this array
   * @return array - in NslInt1
   */
  public NslInt1 getNslInt1() {
    return this;
  }

  /**
   * Create a <tt>NslDouble1</tt> array with the same value as this array
   * @return array - in NslDouble1
   */
  public NslDouble1 getNslDouble1() {
    return (new NslDouble1(getdouble1()));
  }

  /**
   * Create a <tt>NslFloat1</tt> array with the same value as this array
   * @return array - in NslFloat1
   */

  public NslFloat1 getNslFloat1() {
    return (new NslFloat1(getfloat1()));
  }


//----------------
  /**
   * Create an array that captures element <tt>start</tt> to <tt>end</tt>
     if <tt>start</tt> is smaller than 0, <tt>start</tt> is default as 0;
     if <tt>end</tt> is greater than the length of the array,
        <tt>end</tt> is default as the length of the array
   * @param start - the element number start the capture
   * @param end - the element number ends the capture
   * @return a section of the original array
   */
  public int[] getSector(int start, int end) {
    int i;
    int j;
    int length;
    int[] intdata;
    if (start < 0)
      start = 0;
    if (end > _data.length)
      end = _data.length;
    length = end-start+1;
    intdata = new int[length];
    i = start;
    for (j=0; j<length; j++, i++) {
      intdata[j]=_data[i];
    }
    return intdata;
  }



  ///////////////////////////////////////////////////////////////////
  // sets 


  /**
   * set the value of this object to one-dimensional array <tt>value</tt>
   * @param value - value to be defined.
   */
 public void _set(double[] value) {
    set(value);
 }

  /**
   * set the value of this object to one-dimensional array <tt>value</tt>
   * @param value - value to be defined.
   */
 public void _set(float[]  value) {
    set(value);
 }

  /**
   * set the value of this object to one-dimensional array <tt>value</tt>
   * @param value - value to be defined.
   */
 public void _set(int[] value) {
    set(value);
 }

  /**
   * set the value of <tt>pos</tt>th element of this object to <tt>value</tt>
   * @param pos - position of the element to be defined
   * @param value - value to be defined.
   */
  public void _set(int pos, double value) {
    set(pos, value);
 }

  /**
   * set the value of <tt>pos</tt>th element of this object to <tt>value</tt>
   * @param pos - position of the element to be defined
   * @param value - value to be defined.
   */
  public void _set(int pos, float  value) {
    set(pos, value);
 }

  /**
   * set the value of <tt>pos</tt>th element of this object to <tt>value</tt>
   * @param pos - position of the element to be defined
   * @param value - value to be defined.
   */
 public void _set(int pos, int    value) {
    set(pos, value);
 }

  // changing all inside the array
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
 public void _set(double value) {
    set(value);
 }

  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
 public void _set(float  value) {
    set(value);
 }

  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
 public void _set(int    value) {
    set(value);
 }

  /**
   * Set the value of this object to be <tt>value</tt>
   * @param value - in any of <tt>NslNumeric1</tt> type.
   */
  public void _set(NslNumeric1 value) {
    set(value);
  }

  // changing all inside the array

  /**
   * Set the value of all elements of this object to be <tt>value</tt>
   */
  public void _set(NslNumeric0 value) {
    set(value);
 }

  /**
   * set the value of <tt>pos</tt>th element of this object to <tt>value</tt>
   * @param pos - position of the element to be defined
   * @param value - value to be defined.
   */
 public void _set(int pos, NslNumeric0   value) {
    set(pos,value);
 }

  /**
   * Set the value of this array to <tt>value</tt>
   * @param value
   */
  public int[] set(double[] value) {
    int i;

    if (_data.length != value.length) {
      System.out.println("NslInt1: array size not match");
      return _data;
    }

    for (i=0; i<_data.length; i++) {
      _data[i]=(int)value[i];
    }
    return _data;
  }


  /**
   * Set the value of this array to <tt>value</tt>
   * @param value
   */
  public int[] set(float[] value) {
    int i;

    if (_data.length != value.length) {
      System.out.println("NslInt1: array size not match");
      return _data;
    }

    for (i=0; i<_data.length; i++) {
      _data[i]=(int)value[i];
    }
    return _data;
  }


  /**
   * Set the value of this array to <tt>value</tt>
   * @param value
   */
  public int[] set(int[] value) {
    int i;

    if (_data.length != value.length) {
      System.out.println("NslInt1: array size not match");
      return _data;
    }

    for (i=0; i<_data.length; i++) {
      _data[i]=(int)value[i];
    }
    return _data;
  }

  /**
   * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
   * @param pos
   * @param value
   */
  public int set(int pos, double value) {
    _data[pos]=(int)value;
    return _data[pos];
  }
  /**
   * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
   * @param pos
   * @param value
   */
  public int set(int pos, float value) {
    _data[pos]=(int)value;
    return _data[pos];
  }

  /**
   * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
   * @param pos
   * @param value
   */
  public int set(int pos, int value) {
    _data[pos]=(int)value;
    return _data[pos];
  }

  /**
   * Set all elements of this array to <tt>value</tt>
   * @param value
   */
  public int[] set(double value) {
    int i;
    for (i=0; i<_data.length; i++) {
      _data[i]=(int)value;
    }
    return _data;
  }
  /**
   * Set all elements of this array to <tt>value</tt>
   * @param value
   */
  public int[] set(float value) {
    int i;
    for (i=0; i<_data.length; i++) {
      _data[i]=(int)value;
    }
    return _data;
  }

  /**
   * Set all elements of this array to <tt>value</tt>
   * @param value
   */
  public int[] set(int value) {
    int i;
    for (i=0; i<_data.length; i++) {
      _data[i]=(int)value;
    }
    return _data;
  }

  /**
   * Set the value of this array to <tt>value</tt>
   * @param value
   */
  public int[] set(NslNumeric1 value) {
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
  public int[] set(NslNumeric0 value) {
    int i;
    for (i=0; i<_data.length; i++) {
      _data[i]=value.getint();
    }
    return _data;
  }
public int set(int pos, NslNumeric0 value) {
    _data[pos]=value.getint();
    return _data[pos];
  }

  /**
   * Set the value of the array from <tt>startpos</tt> to d
     If the array <tt>d</tt> longer than this array, those
     out of array scope elements are ignored.
   * @param d - object 1-D array
   * @startpos - the element number to start copying
   */
  public void setSector(int[] d, int startpos) {
    int endpos = d.length+startpos;
    int i;
    int j=0;

    if (startpos > _data.length)
        return;
    if (endpos > _data.length)
        endpos = _data.length;
    for (i=startpos; i<endpos; i++, j++)
        _data[i] = d[j];
  }

///----various----------------
public int getSize(){ // short handed format

     return (_data==null?0:_data.length);
}


/**
   * get the size of this array and put it into <tt>size</tt>
   * @param size
   */
 public void getNslSize(NslInt0 size) {
 
    size.set((_data==null?0:_data.length));
  }


/**
   * get the size of this array and put it into <tt>size</tt>
   * @return size - always a vector of 4 elements
   */
public int[] getSizes() {
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

//----------------more------------------------
/**
        Duplicating data from <tt>_new</tt> data of double
        buffered port to <tt>_old</tt> data object
        @param n - number with <i>new</i> value
        @see NslOutPort#nslUpdateBuffers
    */
  public void duplicateData(NslData n) {
    try {
      /* Here we assume that the passed parameter is originally a
	 NslInt1 class. Otherwise, it will force a ClassCastException
	 and notify the NslSystem.
	 */
      set(((NslInt1)n).getint1());

    } catch (ClassCastException e) {
      System.out.println("Class exception is caught in data duplication");
      System.out.println("between two copies of buffer.");
      System.out.println("Please check NslPort arrangement");
      throw e;
    }
  }

  /**
   * Copy itself
   * @return a copy of this array, casted to NslNumeric
   * @see NslOutport
   */
  public NslData duplicateThis() {
    if(isDataSet())
      return (NslData)(new NslInt1(getint1()));
    else	
      return (NslData)(new NslInt1());
  }

   /**
    Reset the reference of <tt>_data</tt> to a number object
    @param n number to be reference
    */
  public NslData setReference(NslData n) {
    try {
      _data = ((NslInt1) n).getint1();
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

    public NslInt1 setReference(int[] value) {
	_data = value;
	return this;
    }

  /**
   * Check if the reference is well-defined
   * @return true - if the reference is defined; false - if reference is null
   */
  public boolean isDataSet() {
    return (_data != null);
  }
  /** Reset the reference pointer to null
   */
  public void resetData() {
    _data = null;
  }
  public void print() {
    System.out.print(toString());
  }

  public String toString() {
    StringBuffer strbuf = new StringBuffer();
    int i;
    for(i=0; i<_data.length; i++)
      strbuf.append(_data[i]+" ");
    //strbuf.append("\n");
    return strbuf.toString();
  }

/**
   * sum all elements of the array
   * @return the sum
   */
/*
  public int sum() {
    int sum = 0;
    int i;

    for (i=0; i<_data.length; i++) {
      sum+=_data[i];
    }
    return sum;
  }
*/
  /**
   * Get the element with maximum value
   * @param max_elem_pos - the position of the element with maximum value
   * @return the maximum value
   */
/*
  public int maxElem(NslInt0 max_elem_pos) {
    int value = java.lang.Integer.MIN_VALUE;
    int i;
    int pos =-1;

    for (i=0; i<_data.length; i++) {
      if (_data[i]>value) {
	pos = i;
	value = _data[i];
      }
    }
    max_elem_pos.set(pos);
    return value;
  }
*/
  /**
   * Get the maximum value of this array
   * @return the maximum value
   */
/*
  public int maxValue() {
    return maxElem(new NslInt0(0));
  }
*/
  /**
   * Get the element with minimum value
   * @param min_elem_pos - the position of the element with minimum value
   * @return the minimum value
   */
/*
  public int minElem(NslInt0 min_elem_pos) {
    int value = java.lang.Integer.MAX_VALUE;
    int i;
    int pos = -1;

    for (i=0; i<_data.length; i++) {
      if (_data[i]<value) {
	pos = i;
	value = _data[i];
      }
    }
    min_elem_pos.set(pos);
    return value;
  }
*/
  /**
   * Get the minimum value of this array
   * @return the minimum value
   */
/*
  public int minValue() {
    return minElem(new NslInt0(0));
  }
*/

}

// NslInt1.java
////////////////////////////////////////////////////////////////////////////////



