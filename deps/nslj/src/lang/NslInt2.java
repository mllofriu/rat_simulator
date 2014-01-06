/*  SCCS - @(#)NslInt2.java	1.13 - 09/20/99 - 19:19:56 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.
////////////////////////////////////////////////////////////////////////////////
// NslInt2.java

package nslj.src.lang;


public class NslInt2 extends NslNumeric2 {
  public int[][] _data;
  //public String _name;
  //boolean visibility = true;
  //NslHierarchy module;


  public NslInt2(int[][] d) {
	super();
    _data = new int[d.length][d[0].length];
    set(d);
   // _name = null;
  }

  public NslInt2(NslNumeric2 n) {
	super();
    _data = new int[n.getSize1()][n.getSize2()];
    set(n.getint2());
    //_name = null;
  }

  public NslInt2(int size1, int size2) {
	super();
    _data = new int[size1][size2];
    //_name = null;
  }

  public NslInt2() {
	super();
    _data=null;
    //_name = null;
  }

  /** 
    * This constructs a number with specified name
    * @param name - name of the variable
    */
  public NslInt2(String name) {
	super(name);
    _data = null;
   // _name = name;
  }
  public NslInt2(String name, NslHierarchy curParent) {
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
    * @param size1 - size of the array 1st-Dimension
    * @param size2 - size of the array 2nd-Dimension
    */
  public NslInt2(String name, int size1, int size2) {
	super(name);
    _data = new int[size1][size2];
    //_name = name;
  }

  public NslInt2(String name, NslHierarchy curParent, int size1, int size2) {
	super(name,curParent,curParent.nslGetAccess());
    _data = new int[size1][size2];
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
  public NslInt2(String name, NslNumeric2 n) {
	super(name);
    _data = new int[n.getSize1()][n.getSize2()];
    set(n.getint2());
    //_name = name;
  
  }
  public NslInt2(String name, NslHierarchy curParent, NslNumeric2 n) {
	super(name,curParent,curParent.nslGetAccess());

    _data = new int[n.getSize1()][n.getSize2()];
    set(n.getint2());
    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  
  }
  // 98/8/20 aa
  public NslInt2(String name, int[][] d) {
	super(name);
    _data = new int[d.length][d[0].length];
    set(d);
    //_name = name;
  }
    
    public void nslMemAlloc(int size1, int size2) {
	_data = new int[size1][size2];
    }
    

//--------------gets-----------------------------------------
public int[][] get() {
    return _data;
  }
public int[] get(int pos1) {
    return _data[pos1];
  }

public int get(int pos1, int pos2) {
    return _data[pos1][pos2];
  }
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
System.out.println("---------> Copied:");
   for (i=0; i<size1; i++)
      { for(j=0; j<size2; j++) System.out.print( doubledata[i][j]+"  "); 
         System.out.println("");
      }
    return doubledata;
  }


  public float[][] getfloat2() {
    float[][] floatdata;
    int i;
    int j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    floatdata = new float[size1][size2];
    for (i=0; i<size1; i++)
      for(j=0; j<size2; j++)
	floatdata[i][j] = (float)_data[i][j];

    return floatdata;
  }
public int[][] getint2() {
    return _data;
  }

public double[] getdouble1(int pos) {
    int i;
    int size2 = _data[0].length;
    double tmp[] = new double[size2];
    for (i=0; i<size2; i++)
        tmp[i]=(double)_data[pos][i];
    return tmp;
  }

  public float[] getfloat1(int pos) {
    int i;
    int size2 = _data[0].length;
    float[] tmp = new float[size2];
    for (i=0; i<size2; i++)
        tmp[i]=(float)_data[pos][i];
    return tmp;
  }
public int[] getint1(int pos) {
    return _data[pos];
  }


public double getdouble(int pos1, int pos2) {
    return (double)_data[pos1][pos2];
}

  public float getfloat(int pos1, int pos2) {
    return (float)_data[pos1][pos2];
  }


public int getint(int pos1, int pos2) {
    return _data[pos1][pos2];
  }


  public int[][] getSector(int start1, int start2, int end1, int end2) {
    int i1;
    int i2;
    int j1;
    int j2;

    int length1;
    int length2;

    int[][] intdata;
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
    intdata = new int[length1][length2];
    i1 = start1;
    i2 = start2;
    for (j1=0; j1<length1; j1++, i1++) {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) {
	intdata[j1][j2]=_data[i1][i2];
      }
    }
    return intdata;
  }
//-----------sets------------------------------------------------


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


  public int[][] set(int[][] value) {
    int i;
    int size1 = _data.length;
    int size2 = _data[0].length;

    if (size1 != value.length || size2 !=value[0].length) {
      System.out.println("NslInt2: array size not equal");
      return _data;
    }

    for (i=0; i<size1; i++) {
      System.arraycopy(value[i], 0, _data[i], 0, size2);
    }
    return _data;
  }
  public int[][] set(float[][] value) {
    int i;
    int j;
    int size1 = _data.length;
    int size2 = _data[0].length;

    if (size1 != value.length || size2 !=value[0].length) {
      System.out.println("NslInt2:  array size not equal");
      return _data;
    }
    for (i=0; i<size1; i++) {
      for(j=0; j<size2; j++)
	_data[i][j] = (int)value[i][j];
    }

    return _data;
  }

  public int[][] set(double[][] value) {
    int i;
    int j;
    int size1 = _data.length;
    int size2 = _data[0].length;

    if (size1 != value.length || size2 !=value[0].length) {
      System.out.println("NslInt2: array size not equal");
      return _data;
    }

    for (i=0; i<size1; i++) {
      for(j=0; j<size2; j++)
	_data[i][j] = (int)value[i][j];
    }
    return _data;
  }


    public int[] set(int pos, double[] value) {

	if (_data[0].length != value.length) {
	    System.out.println("Nslfloat2: array size not match");
	    return _data[pos];
	}

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=(int)value[i];
	}
	
	return _data[pos];	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public int[] set(int pos, float[] value) {

	if (_data[0].length != value.length) {
	    System.out.println("Nslfloat2: array size not match");
	    return _data[pos];
	}

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=(int)value[i];
	}
	
	return _data[pos];	
    }
  
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public int[] set(int pos, int[] value) {

	if (_data[0].length != value.length) {
	    System.out.println("Nslfloat1: array size not match");
	    return _data[pos];
	}

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=(int)value[i];
	}
	
	return _data[pos];	
    }

    public int[] set(int pos1, NslNumeric1 n) {
        return set(pos1, n.getdouble1());
    }

  public int set(int pos1, int pos2, double value) {
    _data[pos1][pos2]=(int)value;
    return _data[pos1][pos2];
  }
  public int set(int pos1, int pos2, float value) {
    _data[pos1][pos2]=(int)value;
    return _data[pos1][pos2];
  }

  public int set(int pos1, int pos2, int value) {
    _data[pos1][pos2]=(int)value;
    return _data[pos1][pos2];
  }
  public int[][] set(int value) {
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    for (i=0; i<size1; i++) {
        for (j=0; j<size2; j++)
          _data[i][j]=value;
    }
    return _data;
  }
  public int[][] set(float value) {
    set((int)value);
    return _data;
  }

  public int[][] set(double value) {
    set((int)value);
    return _data;
  }

  public int[][] set(NslNumeric2 n) {
    int i;
 //   NslInt0 size1 = new NslInt0(0);
 //   NslInt0 size2 = new NslInt0(0);

  //  value.getSizes(size1, size2);
    if (_data.length != n.getSize1() || _data[0].length!=n.getSize2()) {
      System.out.println("NslInt1: array size not eqaul");
      return _data;
    }
    return set(n.getint2());
  }

  public int[][] set(NslNumeric0 n) {
    return set(n.getint());
  }

  public int set(int pos1, int pos2, NslNumeric0 value) {
    return    set(pos1, pos2, value.getint());
  }

  public void setSector(int[][] d, int startpos1, int startpos2) {
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


  public NslInt2 getNslInt2() {
    return this;
  }

  public NslFloat2 getNslFloat2() {
    return (new NslFloat2(getfloat2()));
  }

  public NslDouble2 getNslDouble2() {
    return (new NslDouble2(getdouble2()));
  }

//----------------various--------------------------------------
public int[]getSizes() {
	int[] size =new int[4];
    size[0]=((_data==null?0: _data.length));
    size[1]=( (_data==null?0:_data[0].length));
    size[2]=(0);
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
      set(((NslInt2)n).getint2());

    } catch (ClassCastException e) {
      System.out.println("Class exception is caught in data duplication");
      System.out.println("between two copies of buffer.");
      System.out.println("Please check NslPort arrangement");
      throw e;
    }
  }

  public NslData duplicateThis() {
    if (isDataSet())
      return (NslData)(new NslInt2(getint2()));
    else
      return (NslData)(new NslInt2());
  }

  public NslData setReference(NslData n) {
    try {
      _data = ((NslInt2) n).getint2();
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

    public NslInt2 setReference(int[][] value) {
	_data = value;
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

   public void 	print() {
     System.out.print(toString());

  }

  public String toString() {
    StringBuffer strbuf = new StringBuffer();
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    //System.out.println(" ");
    for (i=0; i<size1; i++) {
        strbuf.append("{ ");
        for (j=0; j<size2; j++)
            strbuf.append(_data[i][j]+" ");
        strbuf.append("} ");
    }
    return strbuf.toString();
  }

/*

  public int sum() {
    int sum = 0;
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;

    for (i=0; i<size1; i++) {
        for(j=0; j<size2; j++)
          sum+=_data[i][j];
    }
    return sum;
  }

  public int maxElem(NslInt0 max_elem_pos1, NslInt0 max_elem_pos2) {
    int value = java.lang.Integer.MIN_VALUE;
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

  public int maxValue() {
    return maxElem(new NslInt0(0), new NslInt0(0));
  }


  public int minElem(NslInt0 max_elem_pos1, NslInt0 max_elem_pos2) {
    int value = java.lang.Integer.MAX_VALUE;
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

  public int minValue() {
    return minElem(new NslInt0(0), new NslInt0(0));
  }


*/
}

// NslInt2.java
////////////////////////////////////////////////////////////////////////////////


