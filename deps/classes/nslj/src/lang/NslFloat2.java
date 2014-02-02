/*  SCCS @(#)NslFloat2.java	1.14 -- 09/20/99 -- 19:19:49 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslFloat2.java

package nslj.src.lang;


public class NslFloat2 extends NslNumeric2 {
// 
  public float[][] _data;
  //public String _name;
  //boolean visibility = true;
  //NslHierarchy module;


  public NslFloat2(float[][] d) {
	super();
    _data = new float[d.length][d[0].length];
    set(d);
    //_name = null;
  }

  public NslFloat2(NslNumeric2 n) {
	super();

    _data = new float[n.getSize1()][n.getSize2()];
    set(n.getfloat2());
    //_name = null;
  }

  public NslFloat2(int size1, int size2) {
	super();

    _data = new float[size1][size2];
    //_name = null;
  }

  public NslFloat2() {
	super();

    _data=null;
    //_name = null;
  }
 
  /** 
    * This constructs a number with specified name
    * @param name - name of the variable
    */
  public NslFloat2(String name, NslHierarchy curParent) {
	super(name,curParent,curParent.nslGetAccess());

    _data = null;
    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  }
  public NslFloat2(String name) {
	super(name);
    _data = null;
    //_name = name;
  }
  /**
    * This constructs a number with specified name
    * @param name - name of the variable
    * @param size1 - size of the array 1st-Dimension
    * @param size2 - size of the array 2nd-Dimension
    */
  public NslFloat2(String name, int size1, int size2) {
	super(name);
    _data = new float[size1][size2];
    //_name = name;
  }
  public NslFloat2(String name, NslHierarchy curParent, int size1, int size2) {
	super(name,curParent,curParent.nslGetAccess());
    _data = new float[size1][size2];
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
  public NslFloat2(String name, NslHierarchy curParent,NslNumeric2 n) {
	super(name,curParent,curParent.nslGetAccess());
    _data = new float[n.getSize1()][n.getSize2()];
    set(n.getfloat2());
    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  
  }
  public NslFloat2(String name, NslNumeric2 n) {
	super(name);

    _data = new float[n.getSize1()][n.getSize2()];
    set(n.getfloat2());
    //_name = name;
  
  }
  // aa
  public NslFloat2(String name, float[][] d) {
	super(name);

    _data = new float[d.length][d[0].length];
    set(d);
    //_name = name;
  }
//
    
    public void nslMemAlloc(int size1, int size2) {
	_data = new float[size1][size2];
    }
    
// ------------gets---------------------------------------
public float[][] get() {
    return _data;
  }
public float[] get(int pos1) {
    return _data[pos1];
  }

public float get(int pos1, int pos2) {
    return _data[pos1][pos2];
  }

/**
   * @return - the value of this object in java numerical array type
     <tt>double[][]</tt>.
	Override this method in NslDouble2 with more efficient one.
   */
  public double[][] getdouble2() {
    double[][] doubledata;
    int i;
    int j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    doubledata = new double[size1][size2];
    for (i=0; i<size1; i++)
      for(j=0; j<size2; j++)
	doubledata[i][j] = (double)_data[i][j];

    return doubledata;
  }


  /**
   * @return - the value of this object in java numerical array type
     <tt>float[][]</tt>.
	Override this method in NslFloat2 with more efficient one.

   */
  public float[][]  getfloat2(){
	return _data;
  }

/**
   * @return - the value of this object in java numerical array type
     <tt>int[][]</tt>.
	Override this method in NslInt2 with more efficient one.
   */
public int[][] getint2() {
    int[][] intdata;
    int i;
    int j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    intdata = new int[size1][size2];
    for (i=0; i<size1; i++)
      for(j=0; j<size2; j++)
	intdata[i][j] = (int)_data[i][j];

    return intdata;
  }
/**
   * @param pos - row number to be addressed.
   * @return - the value of <tt>pos</tt>th row of this object
     in java numerical array type <tt>double[]</tt>.
   */
public double[] getdouble1(int pos) {
    int i;
    int size2 = _data[0].length;
    double[] tmp = new double[size2];
    for (i=0; i<size2; i++)
        tmp[i]=(double)_data[pos][i];
    return tmp;
  }

public float[] getfloat1(int pos) {
    return _data[pos];
  }
/**
   * @param pos - row number to be addressed.
   * @return - the value of <tt>pos</tt>th row of this object
     in java numerical array type <tt>int[]</tt>.
   */ 
   public int[] getint1(int pos) {
    int i;
    int size2 = _data[0].length;
    int tmp[] = new int[size2];
    for (i=0; i<size2; i++)
        tmp[i]=(int)_data[pos][i];
    return tmp;
  }

/**
   * @param pos1 - row number
   * @param pos2 - column number
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>double</tt>.
   */
public double getdouble(int pos1, int pos2) {
    return (double)_data[pos1][pos2];
  }

  /**
   * @param pos1 - row number
   * @param pos2 - column number
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>float</tt>.
   */
public float getfloat(int pos1, int pos2) {
    return _data[pos1][pos2];
  }
  /**
   * @param pos1 - row number
   * @param pos2 - column number
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>int</tt>.
   */
public int getint(int pos1, int pos2) {
    return (int)_data[pos1][pos2];
  }
// Interface with NslNumeric type
  /**
   * get the value of this object in <tt>NslDouble2</tt> form.
   */
// 	Override this method in NslDouble2 with more efficient one.

  public NslDouble2 getNslDouble2() {
    return (new NslDouble2(getdouble2()));
  }
// 	Override this method in NslFloat2 with more efficient one.

  public NslFloat2 getNslFloat2() {
    return this;
  }
// 	Override this method in NslFloat2 with more efficient one.

  public NslInt2 getNslInt2() {
    return (new NslInt2(getint2()));
  }
  public float[][] getSector(int start1, int start2, int end1, int end2) {
    int i1;
    int i2;
    int j1;
    int j2;

    int length1;
    int length2;

    float[][] floatdata;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    length1 = end1-start1+1;
    length2 = end2-start2+1;
    floatdata = new float[length1][length2];
    i1 = start1;
    i2 = start2;
    for (j1=0; j1<length1; j1++, i1++) {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) {
	floatdata[j1][j2]=_data[i1][i2];
      }
    }
    return floatdata;
  }



//-----sets--------------------------------------------------


  public void _set(double[][] value) {
    set(value);
  }

  /**
   * set the value of this object to <tt>value</tt>
   * @param value - two dimension array
   */
  public void _set(float[][]  value) {
    set(value);
  }

  /**
   * set the value of this object to <tt>value</tt>
   * @param value - two dimension array
   */
  public void _set(int[][]    value) {
    set(value);
  }

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the row number of the element
   * @param pos2 - the column number of the element
   * @param value - scalar in double
   */
  public void _set(int pos1, int pos2, double value) {
    set(pos1,pos2,value);
  }

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the row number of the element
   * @param pos2 - the column number of the element
   * @param value - scalar in float
   */
  public void _set(int pos1, int pos2, float  value) {
    set(pos1,pos2,value);
  }

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the row number of the element
   * @param pos2 - the column number of the element
   * @param value - scalar in int
   */
  public void _set(int pos1, int pos2, int    value) {
    set(pos1,pos2,value);
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
   * @param value - in any of <tt>NslNumeric2</tt> type.
   */
  public void _set(NslNumeric2 value) {
    set(value);
  }


  // changing all inside the array
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  public void _set(NslNumeric0 value) {
    set(value);
  }

/**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the row number of the element
   * @param pos2 - the column number of the element
   * @param value - scalar in NslNumeric0
   */
  public void _set(int pos1, int pos2, NslNumeric0 value) {
    set(pos1,pos2,value);
  }


public float[][] set(float[][] value) {
    int i;
    int size1 = _data.length;
    int size2 = _data[0].length;

    if (size1 != value.length || size2 !=value[0].length) {
      System.out.println("NslFloat2: array size not equal");
      return _data;
    }

    for (i=0; i<size1; i++) {
      System.arraycopy(value[i], 0, _data[i], 0, size2);
    }
    return _data;
  }
  public float[][] set(double[][] value) {
    int i;
    int j;
    int size1 = _data.length;
    int size2 = _data[0].length;

    if (size1 != value.length || size2 !=value[0].length) {
      System.out.println("NslFloat2:  array size not equal");
      return _data;
    }
    for (i=0; i<size1; i++)
      for(j=0; j<size2; j++)
	_data[i][j] = (float)value[i][j];


    return _data;
  }

  public float[][] set(int[][] value) {
    int i;
    int j;
    int size1 = _data.length;
    int size2 = _data[0].length;

    if (size1 != value.length || size2 !=value[0].length) {
      System.out.println("NslFloat2: data length not match");
      return _data;
    }

    for (i=0; i<size1; i++) {
      for(j=0; j<size2; j++)
	_data[i][j] = (int)value[i][j];
    }
    return _data;
  }



    public float[] set(int pos, double[] value) {

	if (_data[0].length != value.length) {
	    System.out.println("Nslfloat2: array size not match");
	    return _data[pos];
	}

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=(float)value[i];
	}
	
	return _data[pos];	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public float[] set(int pos, float[] value) {

	if (_data[0].length != value.length) {
	    System.out.println("Nslfloat2: array size not match");
	    return _data[pos];
	}

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=value[i];
	}
	
	return _data[pos];	
    }
  
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public float[] set(int pos, int[] value) {

	if (_data[0].length != value.length) {
	    System.out.println("Nslfloat1: array size not match");
	    return _data[pos];
	}

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=value[i];
	}
	
	return _data[pos];	
    }


  public float set(int pos1, int pos2, double value) {
    _data[pos1][pos2]=(float)value;
    return _data[pos1][pos2];
  }
  public float set(int pos1, int pos2, float value) {
    _data[pos1][pos2]=value;
    return _data[pos1][pos2];
  }

  public float set(int pos1, int pos2, int value) {
    _data[pos1][pos2]=(float)value;
    return _data[pos1][pos2];
  }
  public float[][] set(float value) {
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    for (i=0; i<size1; i++) {
        for (j=0; j<size2; j++)
          _data[i][j]=value;
    }
    return _data;
  }
  public float[][] set(double value) {
    set((float)value);
    return _data;
  }

  public float[][] set(int value) {
    set((float)value);
    return _data;
  }

  public float[][] set(NslNumeric2 n) {
    int i;
 //   NslInt0 size1 = new NslInt0(0);
 //   NslInt0 size2 = new NslInt0(0);

  //  value.getSize(size1, size2);
    if (_data.length != n.getSize1() || _data[0].length!=n.getSize2()) {
      System.out.println("NslFloat1: array size not eqaul");
      return _data;
    }
    return set(n.getfloat2());
  }
  public float[][] set(NslNumeric0 n) {
    return set(n.getfloat());
  }

    public float[] set(int pos1, NslNumeric1 n) {
        return set(pos1, n.getdouble1());
    }


  public float set(int pos1, int pos2, NslNumeric0 value) {
    return set(pos1, pos2, value.getfloat());
  }

  public void setSector(float[][] d, int startpos1, int startpos2) {
    int endpos1 = d.length+startpos1;
    int endpos2 = d[0].length+startpos2;
    int i1, i2;
    int j1=0, j2=0;

    if (startpos1 > _data.length)
        return;
    if (startpos2 > _data[0].length)
        return;
    if (endpos1 > _data.length)
        endpos1 = _data.length;
    if (endpos2 > _data[0].length)
        endpos2 = _data[0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) {
        j2=0;
        for (i2=startpos2; i2<endpos2; i2++, j2++)
            _data[i1][i2] = d[j1][j2];
    }
  }
///--------------various-------------------------------------------

public int[] getSizes() {
	int[] size =new int[4];
      size[0]=((_data==null?0: _data.length));
      size[1]=( (_data==null?0:_data[0].length));
      size[2]=( 0);
      size[3]=(0);
	return size;
  }

  public void getNslSizes(NslInt0 size1, NslInt0 size2) {
    size1.set((_data==null?0: _data.length));
    size2.set( (_data==null?0:_data[0].length));
  }
/**
   * Get the left most index (2st axis) in this array
   */
    public int getSize1() {
        return (_data==null?0:_data.length);
    }

/**
   * Get the second left most index (2st axis) in this array
   */
    public int getSize2() {
        return (_data==null?0:_data[0].length);
    }



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
      set(((NslFloat2)n).getfloat2());

    } catch (ClassCastException e) {
      System.out.println("Class exception is caught in data duplication");
      System.out.println("between two copies of buffer.");
      System.out.println("Please check NslPort arrangement");
      throw e;
    }
  }

  public NslData duplicateThis() {
    if(isDataSet())
      return (NslData)(new NslFloat2(getfloat2()));
    else
      return(NslData)( new NslFloat2());
  }
//-----setViaReference--------------------------------------------------
    /**
     * Set the reference to the wrapped data of <tt>n</tt>
     * It is used in double buffered ports, to make the the ports
     * reference different number object at different time.
     * @param n - number concerned
     */

    public NslFloat2 setReference(float[][] value) {
	_data = value;
	return this;
    }
/*
public void setReference(float[][] value) {
    int i;
    int size1 = _data.length;
    int size2 = _data[0].length;

    if (size1 != value.length || size2 !=value[0].length) {
      System.out.println("NslFloat2: array size not equal "+nslGetName()); 
      return;
    }
    _data=value;

    return;
  }*/

  public NslData setReference(NslData n) {
    try {
      _data = ((NslFloat2) n).getfloat2();
    } catch (ClassCastException e) {
      System.out.println("Class exception is caught in reference setting");
      System.out.println("between two copies of buffer.");
      System.out.println("Please check NslPort arrangement");
      throw e;
    } finally {
      return this;
    }
  }
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
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    //System.out.println(" ");
    for(i=0; i<size1; i++) {
        strbuf.append("{ ");
        for(j=0; j<size2; j++)
          strbuf.append(_data[i][j]+" ");
        strbuf.append("} ");
    }
    return strbuf.toString();
  }

/*
  public float sum() {
    float sum = 0;
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++)
          sum+=_data[i][j];
    }
    return sum;
  }

  public float maxElem(NslInt0 max_elem_pos1, NslInt0 max_elem_pos2) {
    float value = java.lang.Float.NEGATIVE_INFINITY;
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;

    int pos1 = -1;
    int pos2 = -1;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++) {
          if (_data[i][j]>value) {
	        pos1 = i; pos2 = j;
	        value = _data[i][j];
	      }
      }
    }
    max_elem_pos1.set(pos1);
    max_elem_pos2.set(pos2);
    return value;
  }

  public float maxValue() {
    return maxElem(new NslInt0(0), new NslInt0(0));
  }


  public float minElem(NslInt0 max_elem_pos1, NslInt0 max_elem_pos2) {
    float value = java.lang.Float.POSITIVE_INFINITY;
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;

    int pos1 = -1;
    int pos2 = -1;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++) {
          if (_data[i][j]<value) {
	        pos1 = i; pos2 = j;
	        value = _data[i][j];
	      }
      }
    }
    max_elem_pos1.set(pos1);
    max_elem_pos2.set(pos2);
    return value;
  }

  public float minValue() {
    return minElem(new NslInt0(0), new NslInt0(0));
  }
*/


}

// NslFloat2.java
////////////////////////////////////////////////////////////////////////////////




