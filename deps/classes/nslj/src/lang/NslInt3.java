/*  SCCS - @(#)NslInt3.java	1.13 - 09/20/99 - 19:19:57 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.
////////////////////////////////////////////////////////////////////////////////
// NslInt3.java

package nslj.src.lang;


public class NslInt3 extends NslNumeric3 {
  public int[][][] _data;
  //public String _name;
  //boolean visibility = true;
  //NslHierarchy module;


  public NslInt3(int[][][] d) {
	super();
    _data = new int[d.length][d[0].length][d[0][0].length];
    set(d);
    //_name = null;
  }

  public NslInt3(NslNumeric3 n) {
	super();
    _data = new int[n.getSize1()][n.getSize2()][n.getSize3()];
    set(n.getint3());
    //_name = null;
  }

  public NslInt3(int size1, int size2, int size3) {
	super();
    _data = new int[size1][size2][size3];
    //_name = null;
  }

  public NslInt3() {
	super();
    _data=null;
    //_name = null;
  }

  /** 
    * This constructs a number with specified name
    * @param name - name of the variable
    */
  public NslInt3(String name) {
	super();
    _data = null;
    //_name = name;
  }
  public NslInt3(String name, NslHierarchy curParent) {
	super();
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
    * @param size3 - size of the array 3rd-Dimension
    */
  public NslInt3(String name, int size1, int size2, int size3) {
	super();
    _data = new int[size1][size2][size3];
    //_name = name;
  }

  public NslInt3(String name, NslHierarchy curParent, int size1, int size2, int size3) {
	super();
    _data = new int[size1][size2][size3];
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
  public NslInt3(String name,  NslHierarchy curParent, NslNumeric3 n) {
	super();
    _data = new int[n.getSize1()][n.getSize2()][n.getSize3()];
    set(n.getint3());
    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  
  }
  public NslInt3(String name, NslNumeric3 n) {
	super(name);
    _data = new int[n.getSize1()][n.getSize2()][n.getSize3()];
    set(n.getint3());
    //_name = name;
  
  }

  // 98/8/20 aa
  public NslInt3(String name, int[][][] d) {
	super(name);
    _data = new int[d.length][d[0].length][d[0][0].length];
    set(d);
    //_name = name;
  
  }
    
    public void nslMemAlloc(int size1, int size2, int size3) {
	_data = new int[size1][size2][size3];
    }
    

//------gets------------------------------------------
 public int[][][] get() {
    return _data;
  }
public int[][] get(int pos1) {
    return _data[pos1];
  }

public int[] get(int pos1, int pos2) {
    return _data[pos1][pos2];
  }
public int get(int pos1, int pos2, int pos3) {
    return _data[pos1][pos2][pos3];
  }
public int get(NslInt0 pos1, NslInt0 pos2, NslInt0 pos3) {
    return _data[pos1.getint()][pos2.getint()][pos3.getint()];
  }

public int[][][] getint3() {
    return _data;
  }

  public float[][][] getfloat3() {
    float[][][] floatdata;
    int i;
    int j;
    int k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    floatdata = new float[size1][size2][size3];
    for (i=0; i<size1; i++)
     { 
      for(j=0; j<size2; j++)
       {	
	for(k=0; k<size3; k++)
	{floatdata[i][j][k] = (float)_data[i][j][k];}
       }
     }	
    return floatdata;
  }

public double[][][] getdouble3() {
    double[][][] doubledata;
    int i;
    int j;
    int k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    doubledata = new double[size1][size2][size3];
    for (i=0; i<size1; i++)
     {
      for(j=0; j<size2; j++)
       {	
        for(k=0; k<size3; k++)
	  {
 	    doubledata[i][j][k] = (double)_data[i][j][k];
	  }   
       }
     }
     return doubledata;
  }
//-----------------------
/**
   * @param pos1 - left most index
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>double</tt>.
   */
  public double[][] getdouble2(int pos1) {
    int i,j;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    double[][] tmp = new double[size2][size3];
    for (i=0; i<size2; i++) {
      for (j=0; j<size3; j++) {
        tmp[i][j]=(double)_data[pos1][i][j];	
      }
    }
    return tmp;    
  } 
  /**
   * @param pos1 - left most index
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>float</tt>.
   */
  public float[][] getfloat2(int pos1) {
    int i,j;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    float[][] tmp = new float[size2][size3];
    for (i=0; i<size2; i++) {
      for (j=0; j<size3; j++) {
        tmp[i][j]=(float)_data[pos1][i][j];	
      }
    }
    return tmp;    
  } 

  /**
   * @param pos1 - left most index
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>int</tt>.
   */
  public int[][] getint2(int pos1) {
	return _data[pos1];
  } 

//-------------------
public int[] getint1(int pos1, int pos2) {
    return _data[pos1][pos2];
  }

public double[] getdouble1(int pos1, int pos2) {
    int j;
    int size3 = _data[0][0].length;
    double[] tmp = new double[size3];
    for (j=0; j<size3; j++) {
        tmp[j]=(double)_data[pos1][pos2][j];
    }
    return tmp;
  }

  public float[] getfloat1(int pos1, int pos2) {
    int j;
    int size3 = _data[0][0].length;
    float[] tmp = new float[size3];
    for (j=0; j<size3; j++) {
        tmp[j]=(float)_data[pos1][pos2][j];
    }
    return tmp;
  }
//--------

public double getdouble(int pos1, int pos2, int pos3) {
    return (double)_data[pos1][pos2][pos3];
  }
public float getfloat(int pos1, int pos2, int pos3) {
    return (float)_data[pos1][pos2][pos3];
  }
public int getint(int pos1, int pos2, int pos3) {
    return _data[pos1][pos2][pos3];
  }
//--------

  public NslFloat3 getNslFloat3() {
    return (new NslFloat3(getfloat3()));
  }

  public NslDouble3 getNslDouble3() {
    return (new NslDouble3(getdouble3()));
  }

  public NslInt3 getNslInt3() {
    return (this);
  }


//--------------


//----------------------------
   
    public int[][][] getSector(int start1, int start2, int start3, int end1, int end2, int end3) {
    int i1;
    int i2;
    int i3;
    int j1;
    int j2;
    int j3;
   
    int length1;
    int length2;
    int length3;

    int[][][] intdata;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;	

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;

    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
 
    intdata = new int[length1][length2][length3];
    i1 = start1;
    i2 = start2;
    i3 = start3;

    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
        j3=0; i3=start3;
        for(j3=0; j3<length3; j3++, i3++) 
         { 	
	  intdata[j1][j2][j3]=_data[i1][i2][i3];
         } 
       }
     }
    return intdata;
  }

//----------sets--------------------------------------

  /**
   * set the value of this object to <tt>value</tt>
   * @param value - three dimensional array
   */
  public void _set(double[][][] value) {
    set(value);
  }

  /**
   * set the value of this object to <tt>value</tt>
   * @param value - three dimensional array
   */
  public void _set(float[][][] value) {
    set(value);
  }

  /**
   * set the value of this object to <tt>value</tt>
   * @param value - three dimensional array
	   */
  public void _set(int[][][] value) {
    set(value);
  }

/**************************************************************/

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to left most of the element
   * @param pos3 - the height number of the element
   * @param value - scalar in double
   */
  public void _set(int pos1, int pos2, int pos3, double value) {
    set(pos1,pos2,pos3,value);
  }

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to left most of the element
   * @param pos3 - the height number of the element
   * @param value - scalar in float
   */
  public void _set(int pos1, int pos2, int pos3, float  value) {
    set(pos1,pos2,pos3,value);
  }

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to left most of the element
   * @param pos3 - the height number of the element
   * @param value - scalar in int
   */
  public void _set(int pos1, int pos2, int pos3, int value) {
    set(pos1,pos2,pos3,value);
  }

/*******************************************************************/

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

/********************************************************/

  /**
   * Set the value of this object to be <tt>value</tt>
   * @param value - in any of <tt>NslNumeric3</tt> type.
   */
  public void _set(NslNumeric3 value) {
    set(value);
  }


  // changing all inside the array
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value of NslNumeric0.
   */
  public void _set(NslNumeric0 value) {
    set(value);
  }

/**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to left most of the element
   * @param pos3 - the        number of the element
   * @param value - scalar in NslNumeric0
   */
  public void _set(int pos1, int pos2, int pos3, NslNumeric0 value) {
    set(pos1,pos2,pos3,value);
  }



  public int[][][] set(int[][][] value) {
    int i, j;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    if (size1 != value.length || size2 !=value[0].length || size3 !=value[0][0].length) {
      System.out.println("NslInt3: array size not equal");
      return _data;
    }

    for (i=0; i<size1; i++) 
     {
      for (j=0; j<size2; j++)
       {	 
	System.arraycopy(value[i][j], 0, _data[i][j], 0, size3);
       }
     }
    return _data;
  }

   public int[][][] set(float[][][] value) {
    int i;
    int j;
    int k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    if (size1 != value.length || size2 !=value[0].length || size3 !=value[0][0].length) {
      System.out.println("NslInt3:  array size not equal");
      return _data;
    }
    for (i=0; i<size1; i++)
     {
      for(j=0; j<size2; j++)
       {
 	for(k=0; k<size3; k++)
	{_data[i][j][k] = (int)value[i][j][k];}
       }
     }

    return _data;
  }

  public int[][][] set(double[][][] value) {
    int i;
    int j;
    int k; 
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;

    if (size1 != value.length || size2 !=value[0].length || size3 !=value[0][0].length) {
      System.out.println("NslInt3: data length not match");
      return _data;
    }

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
	for(k=0; k<size3; k++)
         {_data[i][j][k] = (int)value[i][j][k];}
       }    
     }
    return _data;
  }
//----------------------------------------------*----------

    public int[][] set(int pos, int[][] value) {
	int i,j;
	int size1 = _data[0].length;
	int size2 = _data[0][0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslInt3: array size not equal");
	    return _data[pos];
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[pos][i][j] = value[i][j];
	}
	return _data[pos];
    }
    
    public int[][] set(int pos, float[][] value) {
	int i;
	int j;
	int size1 = _data[0].length;
	int size2 = _data[0][0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslInt3:  array size not equal");
	    return _data[pos];
	}
	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[pos][i][j] = (int)value[i][j];
	}

	return _data[pos];
    }

    public int[][] set(int pos, double[][] value) {
	int i;
	int j;
	int size1 = _data[0].length;
	int size2 = _data[0][0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslInt3: array size not equal");
	    return _data[pos];
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[pos][i][j] = (int)value[i][j];
    	}
	return _data[pos];
    }
        
    public int[] set(int pos1, int pos2, double[] value) {

	if (_data[0][0].length != value.length) {
	    System.out.println("NslInt3: array size not match");
	    return _data[pos1][pos2];
	}

	for (int i=0; i<_data[0][0].length; i++) {
	    _data[pos1][pos2][i]=(int)value[i];
	}
	
	return _data[pos1][pos2];	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public int[] set(int pos1, int pos2, float[] value) {

	if (_data[0][0].length != value.length) {
	    System.out.println("NslInt3: array size not match");
	    return _data[pos1][pos2];
	}

	for (int i=0; i<_data[0][0].length; i++) {
	    _data[pos1][pos2][i]=(int)value[i];
	}
	
	return _data[pos1][pos2];	
    }
  
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public int[] set(int pos1, int pos2, int[] value) {

	if (_data[0][0].length != value.length) {
	    System.out.println("NslInt3: array size not match");
	    return _data[pos1][pos2];
	}

	for (int i=0; i<_data[0][0].length; i++) {
	    _data[pos1][pos2][i]=value[i];
	}
	
	return _data[pos1][pos2];	
    }

  public int set(int pos1, int pos2, int pos3, double value) {
    return _data[pos1][pos2][pos3]=(int)value;
  }
  public int set(int pos1, int pos2, int pos3, float value) {
    return _data[pos1][pos2][pos3]=(int)value;
  }

  public int set(int pos1, int pos2, int pos3, int value) {
    return _data[pos1][pos2][pos3]=(int)value;
  }
//----------------------------------------------*----------

  public int[][][] set(int value) {
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    for (i=0; i<size1; i++) 
     {
      for (j=0; j<size2; j++)
       {
        for (k=0; k<size3; k++)
  	 {_data[i][j][k]=value;}
       }
     }

    return _data;
  }
  public int[][][] set(float value) {
    set((int)value);
    return _data;
  }

  public int[][][] set(double value) {
    set((int)value);
    return _data;
  }
//----------------------------------------------*----------

  public int[][][] set(NslNumeric3 n) {
    int i;
 //   NslInt0 size1 = new NslInt0(0);
 //   NslInt0 size2 = new NslInt0(0);

  //  value.getNslSizes(size1, size2);
    if (_data.length != n.getSize1() || _data[0].length!=n.getSize2() || _data[0][0].length!=n.getSize3()) {
      System.out.println("NslInt3: array size not eqaul");
      return _data;
    }
    return set(n.getint3());
  }
  public int[][][] set(NslNumeric0 n) {
   return set(n.getint());
  }

  public int[][] set(int pos1, NslNumeric2 value) {
       return set(pos1, value.getint2());
  }

  public int[] set(int pos1, int pos2, NslNumeric1 value) {
       return set(pos1, pos2, value.getint1());
  }

  public int set(int pos1, int pos2, int pos3, NslNumeric0 value) {
       return set(pos1, pos2, pos3, value.getint());
  }


//----------------------------------------------*----------

  public void setSector(int[][][] d, int startpos1, int startpos2, int startpos3) {
    int endpos1 = d.length+startpos1;
    int endpos2 = d[0].length+startpos2;
    int endpos3 = d[0][0].length+startpos3;
    int i1, i2, i3;
    int j1=0, j2=0, j3=0;

    if (startpos1 > _data.length)
        return;
    if (startpos2 > _data[0].length)
        return;
    if (startpos3 > _data[0][0].length)
        return;

    if (endpos1 > _data.length)
        endpos1 = _data.length;
    if (endpos2 > _data[0].length)
        endpos2 = _data[0].length;
    if (endpos3 > _data[0][0].length)
        endpos3 = _data[0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
        for (i3=startpos3; i3<endpos3; i3++, j3++)      
         {_data[i1][i2][i3] = d[j1][j2][j3];}
       }
     }
  }
//------------various------------

  public void getNslSizes(NslInt0 size1, NslInt0 size2,NslInt0 size3) {
    size1.set((_data==null?0: _data.length));
    size2.set((_data==null?0: _data[0].length));
    size3.set( (_data==null?0:_data[0][0].length));

  }
  public int[] getSizes() {
	int[] size = new int[4];
      size[0]=(_data==null?0:_data.length);
      size[1]=(_data==null?0:_data[0].length);
      size[2]=(_data==null?0:_data[0][0].length);
      size[3]=0;

	return size;
  }



    public int getSize1() {
        return (_data==null?0:_data.length);
    }
    public int getSize2() {
        return (_data==null?0:_data[0].length);
    }
    public int getSize3() {
        return (_data==null?0:_data[0][0].length);
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
      set(((NslInt3)n).getint3());

    } catch (ClassCastException e) {
      System.out.println("NslInt3: setReference: Class Exception is caught in data duplication");
      System.out.println("between two copies of buffer.");
      System.out.println("Please check NslPort arrangement");
      throw e;
    }
  }

  public NslData duplicateThis() {
    if (isDataSet())
      return (NslData)(new NslInt3(getint3()));
    else
      return (NslData)(new NslInt3());
  }

  public NslData setReference(NslData n) {
    try {
      _data = ((NslInt3) n).getint3();
    } catch (ClassCastException e) {
      System.out.println("NslInt3: setReferece: Class Exception is caught in reference setting");
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

    public NslInt3 setReference(int[][][] value) {
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
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;

    // System.out.println(" ");
    for (i=0; i<size1; i++) {
        strbuf.append("{ ");
        for (j=0; j<size2; j++) {
            strbuf.append("{ ");
            for (k=0; k<size3; k++) {
                strbuf.append(_data[i][j][k]+" ");
            }
            strbuf.append("} ");
        }
        strbuf.append("} ");
    }  
    
    return strbuf.toString();    
  }

/* deleted
public int sum() {
    int sum = 0;
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)
         {sum+=_data[i][j][k];}
       }    
     }
    return sum;
  }

  public int maxElem(NslInt0 max_elem_pos1, NslInt0 max_elem_pos2, NslInt0 max_elem_pos3) {
    int value = java.lang.Integer.MIN_VALUE;
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int pos1 = -1;
    int pos2 = -1;
    int pos3 = -1;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++) 
       {
        for(k=0; k<size3; k++)
         {  
          if (_data[i][j][k]>value) 
           {
            pos1 = i; pos2 = j; pos3 = k;
            value = _data[i][j][k];
	   }
         }
       }
     }
    max_elem_pos1.set(pos1);
    max_elem_pos2.set(pos2);
    max_elem_pos3.set(pos3);
    return value;
  }

  public int maxValue() {
    return maxElem(new NslInt0(0), new NslInt0(0), new NslInt0(0));
  }


  public int minElem(NslInt0 max_elem_pos1, NslInt0 max_elem_pos2, NslInt0 max_elem_pos3) {
    int value = java.lang.Integer.MAX_VALUE;
    int i, j, k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int pos1 = -1;
    int pos2 = -1;
    int pos3 = -1;

    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++) 
       {
        for(k=0; k<size3; k++)
         {       
          if (_data[i][j][k]<value) 
           {
	    pos1 = i; pos2 = j; pos3=k;
	    value = _data[i][j][k];
	   }
         }     
       }
     }
    max_elem_pos1.set(pos1);
    max_elem_pos2.set(pos2);
    max_elem_pos3.set(pos3);
    return value;
  }

  public int minValue() {
    return minElem(new NslInt0(0), new NslInt0(0), new NslInt0(0));
  }
*/

}

// NslInt3.java
////////////////////////////////////////////////////////////////////////////////


