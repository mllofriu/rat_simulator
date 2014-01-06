/*  SCCS - @(#)NslDouble4.java	1.14 - 09/20/99 - 19:19:28 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslDouble4.java
package nslj.src.lang;

public class NslDouble4 extends NslNumeric4 {
  public double[][][][] _data;
//  public String _name;
//  boolean visibility = true;
//  NslHierarchy module;


  public NslDouble4(double[][][][] d) {
    super();
    _data = new double[d.length][d[0].length][d[0][0].length][d[0][0][0].length];
    set(d);
    //_name = null;
  }

  public NslDouble4(NslNumeric4 n) {
    super();
    _data = new double[n.getSize1()][n.getSize2()][n.getSize3()][n.getSize4()];
    set(n.getdouble4());
    //_name = null;
  }

  public NslDouble4(int size1, int size2, int size3, int size4) {
    super();
    _data = new double[size1][size2][size3][size4];
    //_name = null;
  }

  public NslDouble4() {
    super();
    _data=null;
    //_name = null;
  }
  /** 
    * This constructs a number with specified name
    * @param name - name of the variable
    */
  public NslDouble4(String name) {
    super(name);
    _data = null;
    //_name = name;
  }
  public NslDouble4(String name, NslHierarchy curParent) {
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
    * @param size3 - size of the array 3rd-Dimension
    * @param size4 - size of the array 4th-Dimension
    */
  public NslDouble4(String name, int size1, int size2, int size3, int size4) {
    super(name);
    _data = new double[size1][size2][size3][size4];
    //_name = name;
  }
  public NslDouble4(String name, NslHierarchy curParent, int size1, int size2, int size3, int size4) {
    super(name,curParent,curParent.nslGetAccess());
    _data = new double[size1][size2][size3][size4];
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
  public NslDouble4(String name, NslHierarchy curParent,NslNumeric4 n) {
	super(name,curParent,curParent.nslGetAccess());

    _data = new double[n.getSize1()][n.getSize2()][n.getSize3()][n.getSize4()];
    set(n.getdouble4());
    //_name = name;
    //module = curParent;
    //visibility = module.getVisibility();
    //module.enableAccess(this);
  
  }
  public NslDouble4(String name, NslNumeric4 n) {
	super(name);
    _data = new double[n.getSize1()][n.getSize2()][n.getSize3()][n.getSize4()];
    set(n.getdouble4());
    //_name = name;
  
  }
  /**
    * This constructs a number with specified name
    * @param name - name of the variable
    * @param n - initialized values
    */
  public NslDouble4(String name, double[][][][] d) {
	super(name);
    _data = new double[d.length][d[0].length][d[0][0].length][d[0][0][0].length];
    set(d);
    //_name = name;
  }
    
    public void nslMemAlloc(int size1, int size2, int size3, int size4) {
	_data = new double[size1][size2][size3][size4];
    }


//----------gets--------------------------------

  public double[][][][] get() {
    return _data;
  }

public double[][][] get(int pos1){
	return _data[pos1];
  }

public double[][] get(int pos1, int pos2){
	return _data[pos1][pos2];
  }

public double[] get(int pos1, int pos2, int pos3){
	return _data[pos1][pos2][pos3];
  }

public double get(int pos1, int pos2, int pos3, int pos4) {
	return _data[pos1][pos2][pos3][pos4];
  }
// this method here because parser does not change
// NslInt0 to ints for 3d and 4d types
public double get(NslInt0 pos1, NslInt0 pos2, NslInt0 pos3, NslInt0 pos4){
  return _data[pos1.get()][pos2.get()][pos3.get()][pos4.get()];
  }

 //------------------------------------

  public double[][][][] getdouble4() {
    return _data;
  }
/**
   * @return - the value of this object in java numerical array type
     <tt>float[][][][]</tt>.
   */

  // override in NslFloat4 for better efficiency
  public float[][][][] getfloat4() {
    float[][][][] floatdata;
    int i;
    int j;
    int k;
    int l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    floatdata = new float[size1][size2][size3][size4];
    for (i=0; i<size1; i++)
     {
      for(j=0; j<size2; j++)
       {	
        for(k=0; k<size3; k++)
         { 
          for(l=0;l<size4;l++)
           {
      	    floatdata[i][j][k][l] = (float)_data[i][j][k][l];
           }
         }
       }
     }
    return floatdata;
  }
  /**
   * @return - the value of this object in java numerical array type
     <tt>int[][][][]</tt>.
   */

  // override in NslInt4 for better efficiency
  public int[][][][] getint4() {
    int[][][][] intdata;
    int i;
    int j;
    int k;
    int l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    intdata = new int[size1][size2][size3][size4];
    for (i=0; i<size1; i++)
     {
      for(j=0; j<size2; j++)
       {
	for(k=0; k<size3; k++)
  	 {
  	  for(l=0; l<size4; l++)
       	   { intdata[i][j][k][l] = (int)_data[i][j][k][l];}
         }
       }
     } 
    return intdata;
  }

///------------3-----------------------------------------------
  /**
   * @param pos1 - left most number
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th and <tt>pos3</tt>th in java numerical type <tt>double[][][]</tt>.
   */
public double[][][] getdouble3(int pos1) {
	return _data[pos1];
  }

/**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third from left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>float</tt>.
   */

  public float[][][] getfloat3(int pos1) {
    int h, i, j;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    float[][][] tmp = new float[size2][size3][size4];
    for (h=0; h<size2; h++) {
      for (i=0; i<size3; i++) {
         for (j=0; j<size4; j++){
           tmp[h][i][j]=(float)_data[pos1][h][i][j];	
  	 }
      }
    }
    return tmp;    
  } 
  /**
   * @param pos1 - left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>int</tt>.
   */

  public int[][][] getint3(int pos1) {
    int h, i, j;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    int[][][] tmp = new int[size2][size3][size4];
    for (h=0; h<size2; h++) {
      for (i=0; i<size3; i++) {
         for (j=0; j<size4; j++){
           tmp[h][i][j]=(int)_data[pos1][h][i][j];	
  	 }
      }
    }
    return tmp;    
  } 

//--2-----------------------------------------------
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>double[][]</tt>.
   */
   public double[][] getdouble2(int pos1, int pos2) {
	return _data[pos1][pos2];
  }

  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third from left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>float[][]</tt>.
   */
  // override in NslFloat4 for better efficiency
  public float[][] getfloat2(int pos1, int pos2) {
    int i, j;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    float[][] tmp = new float[size3][size4];
    for (i=0; i<size3; i++) {
      for (j=0; j<size4; j++) {
        tmp[i][j]=(float)_data[pos1][pos2][i][j];	
      }
    }
    return tmp;    
  } 
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>int[][]</tt>.
   */
  // override in NslInt4 for better efficiency 
  public int[][] getint2(int pos1, int pos2) {
    int i, j;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    int[][] tmp = new int[size3][size4];
    for (i=0; i<size3; i++) {
      for (j=0; j<size4; j++) {
        tmp[i][j]=(int)_data[pos1][pos2][i][j];	
      }
    }
    return tmp;    
  } 

///------1------------------------------------------------
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third to the left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>double[]</tt>.
   */
   public double[] getdouble1(int pos1, int pos2, int pos3) {
	return _data[pos1][pos2][pos3];
  }

/**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third from left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>float[]</tt>.
   */

  public float[] getfloat1(int pos1, int pos2, int pos3) {
    int j;
    int size4 = _data[0][0][0].length;
    float[] tmp = new float[size4];
    for (j=0; j<size4; j++) {
        tmp[j]=(float)_data[pos1][pos2][pos3][j];	
    }
    return tmp;    
  } 
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third from left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>int[]</tt>.
   */
   public int[] getint1(int pos1, int pos2, int pos3) {
    int j;
    int size4 = _data[0][0][0].length;
    int[] tmp = new int[size4];
      for (j=0; j<size4; j++) {
        tmp[j]=(int)_data[pos1][pos2][pos3][j];	
      }
    return tmp;    
  } 

  //-------0--------------

 /**
   * @param pos1 - left most
   * @param pos2 - second from left
   * @param pos3 - third from left
   * @param pos4 - fourth from left
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th and <tt>pos3</tt>th  and <tt>pos4</tt>th  in java numerical type <tt>double</tt>.
   */

  // override in NslDouble4 for better efficiency 
  public double getdouble(int pos1, int pos2, int pos3, int pos4) {
    return _data[pos1][pos2][pos3][pos4];
  }
 /**
   * @param pos1 - left most
   * @param pos2 - second from left
   * @param pos3 - third from left
   * @param pos4 - fourth from left
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th and <tt>pos3</tt>th  and <tt>pos4</tt>th  in java numerical type <tt>float</tt>.
   */
  // override in NslFloat4 for better efficiency 
  public float getfloat(int pos1, int pos2, int pos3, int pos4) {
    return (float)_data[pos1][pos2][pos3][pos4];
  }

 /**
   * @param pos1 - left most
   * @param pos2 - second from left
   * @param pos3 - third from left
   * @param pos4 - fourth from left
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th and <tt>pos3</tt>th  and <tt>pos4</tt>th  in java numerical type <tt>int</tt>.
   */
  public int getint(int pos1, int pos2, int pos3, int pos4) {
    return (int)_data[pos1][pos2][pos3][pos4];
  }

//------------------------------------------------------------------***----
  /**
   * get the value of this object in <tt>NslDouble4</tt> form.
   */
public NslDouble4 getNslDouble4() {
    return this;
  }

/**
   * get the value of this object in <tt>NslFloat4</tt> form.
   */
public NslFloat4 getNslFloat4() {
    return (new NslFloat4(getfloat4()));
  }
  /**
   * get the value of this object in <tt>NslInt4</tt> form.
   */
public NslInt4 getNslInt4() {
    return (new NslInt4(getint4()));
  }

//----------------------------------------------------

  public double[][][][] getSector(int start1, int start2, int start3, int start4, int end1, int end2, int end3, int end4) {
    int i1;
    int i2;
    int i3;
    int i4;
    int j1;
    int j2;
    int j3;
    int j4;
    
    int length1;
    int length2;
    int length3;
    int length4;
    
    double[][][][] doubledata;
    if (start1 < 0)
      start1 = 0;
    if (start2 < 0)
      start2 = 0;
    if (start3 < 0)
      start3 = 0;
    if (start4 < 0)
      start4 = 0;

    if (end1 > _data.length)
      end1 = _data.length;
    if (end2 > _data[0].length)
      end2 = _data[0].length;
    if (end3 > _data[0][0].length)
      end3 = _data[0][0].length;
    if (end4 > _data[0][0][0].length)
      end4 = _data[0][0][0].length;


    length1 = end1-start1+1;
    length2 = end2-start2+1;
    length3 = end3-start3+1;
    length4 = end4-start4+1;

    doubledata = new double[length1][length2][length3][length4];
    i1 = start1;
    i2 = start2;
    i3 = start3;
    i4 = start4;
    
    for (j1=0; j1<length1; j1++, i1++) 
     {
      j2=0; i2=start2;
      for (j2=0; j2<length2; j2++, i2++) 
       {
	j3=0; i3=start3;
	for (j3=0; j3<length3; j3++, i3++)
	 { 
	  j4=0; i4=start4;
	  for (j4=0; j4<length4; j4++,i4++)
           {doubledata[j1][j2][j3][j4]=_data[i1][i2][i3][i4];}
         }   
       }
     }
    return doubledata;
  }


//-----------sets-----------------

  /**
   * set the value of this object to <tt>value</tt>
   * @param value - four dimension array
   */
  public void _set(double[][][][] value) {
    set(value);
  }

  /**
   * set the value of this object to <tt>value</tt>
   * @param value - four dimension array
   */
  public void _set(float[][][][] value) {
    set(value);
  }

  /**
   * set the value of this object to <tt>value</tt>
   * @param value - four dimension array
   */
  public void _set(int[][][][] value) {
    set(value);
  }

//--------------------------------------------------------------

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to the left most index of the element
   * @param pos3 - the third from left most index of the element
   * @param pos4 - the 4th axis number of the element
   * @param value - scalar in double
   */
  public void _set(int pos1, int pos2, int pos3, int pos4, double value) {
    set(pos1, pos2,pos3, pos4, value);
  }

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to the left most index of the element
   * @param pos3 - the third from left most index of the element
   * @param pos4 - the 4th axis number of the element
   * @param value - scalar in float
   */
  public void _set(int pos1, int pos2, int pos3, int pos4, float  value) {
    set(pos1, pos2,pos3, pos4, value);
  }

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to the left most index of the element
   * @param pos3 - the third from left most index of the element
   * @param pos4 - the 4th axis number of the element
   * @param value - scalar in int
   */
  public void _set(int pos1, int pos2, int pos3, int pos4, int value) {
    set(pos1, pos2,pos3, pos4, value);
  }

//--------------------------------------------------------------*----

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
   * @param value - in any of <tt>NslNumeric4</tt> type.
   */
  public void _set(NslNumeric4 value) {
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
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to the left most index of the element
   * @param pos3 - the third from left most index of the element
   * @param pos4 - the 4th axis number of the element
   * @param value - scalar in NslNumeric0
   */
  public void _set(int pos1, int pos2, int pos3, int pos4, NslNumeric0 value) {
    set(pos1, pos2,pos3, pos4, value);
  }


  public double[][][][] set(double[][][][] value) {
    int i, j,k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    
    if (size1 != value.length || size2 !=value[0].length || size3 !=value[0][0].length || size4 !=value[0][0][0].length) {
      System.out.println("NslDouble4: array size not equal");
      return _data;
    }

    for (i=0; i<size1; i++) 
     {
      for (j=0; j<size2; j++)
       {	 
	for (k=0; k<size3; k++)
	 { System.arraycopy(value[i][j][k], 0, _data[i][j][k], 0, size4);}
       }
     }
    return _data;
  }


  public double[][][][] set(float[][][][] value) {
    int i, j,k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    
    if (size1 != value.length || size2 !=value[0].length || size3 !=value[0][0].length || size4 !=value[0][0][0].length) {
      System.out.println("NslDouble4: array size not equal");
      return _data;
    }

    for (i=0; i<size1; i++) 
     {
      for (j=0; j<size2; j++)
       {	 
	for (k=0; k<size3; k++)
	 { System.arraycopy(value[i][j][k], 0, _data[i][j][k], 0, size4);}
       }
     }
    return _data;
  }


  public double[][][][] set(int[][][][] value) {
    int i, j,k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    
    if (size1 != value.length || size2 !=value[0].length || size3 !=value[0][0].length || size4 !=value[0][0][0].length) {
      System.out.println("NslDouble4: array size not equal");
      return _data;
    }

    for (i=0; i<size1; i++) 
     {
      for (j=0; j<size2; j++)
       {	 
	for (k=0; k<size3; k++)
	 { System.arraycopy(value[i][j][k], 0, _data[i][j][k], 0, size4);}
       }
     }
    return _data;
  }

//--------------------------------

    public double[][][] set(int pos, int[][][] value) {
	int i,j,k;
	int size1 = _data[0].length;
	int size2 = _data[0][0].length;
	int size3 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 !=value[0][0].length) {
	    System.out.println("NslDouble4: array size not equal");
	    return _data[pos];
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++) {
	        for(k=0; k<size3; k++) {
		     _data[pos][i][j][k] = value[i][j][k];
		  }
	    }
	}
	return _data[pos];
    }
    
    public double[][][] set(int pos, float[][][] value) {
	int i;
	int j;
	int k;
	int size1 = _data[0].length;
	int size2 = _data[0][0].length;
	int size3 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 != value[0][0].length) {
	    System.out.println("NslDouble4:  array size not equal");
	    return _data[pos];
	}
	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
	        for(k=0; k<size3; k++) {
			_data[pos][i][j][k] = value[i][j][k];
		  }
	}

	return _data[pos];
    }

    public double[][][] set(int pos, double[][][] value) {
	int i;
	int j;
	int k;
	int size1 = _data[0].length;
	int size2 = _data[0][0].length;
	int size3 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 !=value[0][0].length) {
	    System.out.println("NslInt3: array size not equal");
	    return _data[pos];
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
	        for(k=0; k<size3; k++) {
		     _data[pos][i][j][k] = value[i][j][k];
		  }
    	}
	return _data[pos];
    }
    
    public double[][] set(int pos1, int pos2, int[][] value) {
	int i,j;
	int size1 = _data[0][0].length;
	int size2 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslDouble4: array size not equal");
	    return _data[pos1][pos2];
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[pos1][pos2][i][j] = value[i][j];
	}
	return _data[pos1][pos2];
    }
    
    public double[][] set(int pos1, int pos2, float[][] value) {
	int i;
	int j;
	int size1 = _data[0][0].length;
	int size2 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslDouble4:  array size not equal");
	    return _data[pos1][pos2];
	}
	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[pos1][pos2][i][j] = value[i][j];
	}

	return _data[pos1][pos2];
    }

    public double[][] set(int pos1, int pos2, double[][] value) {
	int i;
	int j;
	int size1 = _data[0][0].length;
	int size2 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslInt3: array size not equal");
	    return _data[pos1][pos2];
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[pos1][pos2][i][j] = value[i][j];
    	}
	return _data[pos1][pos2];
    }
    
   
    public double[] set(int pos1, int pos2, int pos3, double[] value) {

	if (_data[0][0][0].length != value.length) {
	    System.out.println("NslDouble4: array size not match");
	    return _data[pos1][pos2][pos3];
	}

	for (int i=0; i<_data[0][0][0].length; i++) {
	    _data[pos1][pos2][pos3][i]=value[i];
	}
	
	return _data[pos1][pos2][pos3];	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public double[] set(int pos1, int pos2, int pos3, float[] value) {

	if (_data[0][0][0].length != value.length) {
	    System.out.println("NslDouble4: array size not match");
	    return _data[pos1][pos2][pos3];
	}

	for (int i=0; i<_data[0][0][0].length; i++) {
	    _data[pos1][pos2][pos3][i]=value[i];
	}
	
	return _data[pos1][pos2][pos3];	
    }
  
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public double[] set(int pos1, int pos2, int pos3, int[] value) {

	if (_data[0][0][0].length != value.length) {
	    System.out.println("NslDouble4: array size not match");
	    return _data[pos1][pos2][pos3];
	}

	for (int i=0; i<_data[0][0][0].length; i++) {
	    _data[pos1][pos2][pos3][i]=value[i];
	}
	
	return _data[pos1][pos2][pos3];	
    }


  public double set(int pos1, int pos2, int pos3, int pos4, double value) {
    return _data[pos1][pos2][pos3][pos4]=value;
  }
  public double set(int pos1, int pos2, int pos3, int pos4, float value) {
    return _data[pos1][pos2][pos3][pos4]=(double)value;
  }

  public double set(int pos1, int pos2, int pos3, int pos4, int value) {
    return _data[pos1][pos2][pos3][pos4]=(double)value;
  }
//-----------------------
  public double[][][][] set(double value) {
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    
    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
	 {
	  for(l=0; l<size4; l++)
           { _data[i][j][k][l] = value;}
         } 
       }
     }
    return _data;
  }



  public double[][][][] set(float value) {
    set((double)value);
    return _data;
  }

  public double[][][][] set(int value) {
    set((double)value);
    return _data;
  }
//-----------------------------

  public double[][][][] set(NslNumeric4 n) {
    int i;
 //   NslInt0 size1 = new NslInt0(0);
 //   NslInt0 size2 = new NslInt0(0);

  //  value.getSize(size1, size2, size3);
    if (_data.length != n.getSize1() || _data[0].length!=n.getSize2() || _data[0][0].length!=n.getSize3() || _data[0][0][0].length!=n.getSize4()) {
      System.out.println("NslDouble4: array size not eqaul");
      return _data;
    }
    return set(n.getdouble4());
  }


    public double[][][] set(int pos, NslNumeric3 value) {
	return set(pos, value.getdouble3());
    }

    public double[][] set(int pos1, int pos2, NslNumeric2 value) {
	return set(pos1, pos2, value.getdouble2());
    }

    public double[] set(int pos1, int pos2, int pos3, NslNumeric1 value) {
	return set(pos1, pos2, pos3, value.getdouble1());
    }

  public double[][][][] set(NslNumeric0 n) {
    return set(n.getdouble());
  }

  public double set(int pos1, int pos2, int pos3, int pos4, NslNumeric0 value) {
       return set(pos1, pos2, pos3, pos4, value.getdouble());
  }

//--------------------------------

  public void setSector(double[][][][] d, int startpos1, int startpos2, int startpos3, int startpos4) {
    int endpos1 = d.length+startpos1;
    int endpos2 = d[0].length+startpos2;
    int endpos3 = d[0][0].length+startpos3;
    int endpos4 = d[0][0][0].length+startpos4;
    int i1, i2, i3, i4;
    int j1=0, j2=0, j3=0, j4=0;

    if (startpos1 > _data.length)
        return;
    if (startpos2 > _data[0].length)
        return;
    if (startpos3 > _data[0][0].length)
        return;
    if (startpos4 > _data[0][0][0].length)
        return;
        
    if (endpos1 > _data.length)
        endpos1 = _data.length;
    if (endpos2 > _data[0].length)
        endpos2 = _data[0].length;
    if (endpos3 > _data[0][0].length)
        endpos3 = _data[0][0].length;
    if (endpos4 > _data[0][0][0].length)
        endpos4 = _data[0][0][0].length;

    for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) 
     {
      j2=0;
      for (i2=startpos2; i2<endpos2; i2++, j2++)
       {
        j3=0;
	for (i3=startpos3; i3<endpos3; i3++, j3++)
         {
          j4=0;
	  for (i4=startpos4; i4<endpos4; i4++, j4++)
           {_data[i1][i2][i3][i4] = d[j1][j2][j3][j4];}
         }
       }
     }
  }

//---------------various--------------------------------

public void getNslSizes(NslInt0 size1, NslInt0 size2, NslInt0 size3, NslInt0 size4) {
    size1.set((_data==null?0:_data.length));
    size2.set((_data==null?0: _data[0].length));
    size3.set((_data==null?0: _data[0][0].length));
	size4.set( (_data==null?0:_data[0][0][0].length));
  }

  public int[] getSizes() {
	int[] size = new int[4];
      size[0]=(_data==null?0:_data.length);
      size[1]=(_data==null?0:_data[0].length);
      size[2]=(_data==null?0:_data[0][0].length);
	size[3]=(_data==null?0:_data[0][0][0].length);

	return size;
  }

  /**
   * Get the left most index
   */
  public int getSize1(){  // 1st axis
        return (_data==null?0:_data.length);
	}
  /**
   * Get the second to the left most index
   */
  public int getSize2(){  // 2nd axis
        return (_data==null?0:_data[0].length);
	}
  /**
   * Get the third to the left most index
   */
  public int getSize3(){  // 3rd axis
       return (_data==null?0:_data[0][0].length);
	}
/**
   * Get the fourth to the left most index
   */
  public int getSize4() { // 4th axis
        return (_data==null?0:_data[0][0][0].length);
    }

//----------------------------------------------------------


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
      set(((NslDouble4)n).getdouble4());

    } catch (ClassCastException e) {
      System.out.println("Class exception is caught in data duplication");
      System.out.println("between two copies of buffer.");
      System.out.println("Please check NslPort arrangement");
      throw e;
    }
  }

  public NslData duplicateThis() {
    if(isDataSet())
      return (NslData)(new NslDouble4(getdouble4()));
    else
      return (NslData)(new NslDouble4());
  }

  public NslData setReference(NslData n) {

    try {
      _data = ((NslDouble4) n).getdouble4();
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

    public NslDouble4 setReference(double[][][][] value) {
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

  public void print() {
 System.out.print(toString());

  }

  public String toString() {
    StringBuffer strbuf = new StringBuffer();
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
        
    // System.out.println(" ");
    
    //strbuf.append("{ ");
    for (i=0; i<size1; i++) {
        strbuf.append("{ ");
        for (j=0; j<size2; j++) {
            strbuf.append("{ ");
            for (k=0; k<size3; k++) {
                strbuf.append("{ ");
                for (l=0; l<size4; l++) {     
                    strbuf.append(_data[i][j][k][l]+" ");
                }
                strbuf.append("} ");
            }
            strbuf.append("} ");
        }
        strbuf.append("} ");
    } 
    //strbuf.append("}");
    
    return strbuf.toString();     
  }
/*    
  public double sum() {
    double sum = 0;
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    
    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
  	 {
          for(l=0; l<size4; l++)	
           { sum+=_data[i][j][k][l];}
         } 
       }
     }
    return sum;
  }

  public double maxElem(NslInt0 max_elem_pos1, NslInt0 max_elem_pos2, NslInt0 max_elem_pos3, NslInt0 max_elem_pos4) {
    double value = java.lang.Double.NEGATIVE_INFINITY;
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    int pos1 = -1;
    int pos2 = -1;
    int pos3 = -1;    
    int pos4 = -1;
    
    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         {
          for(l=0; l<size4; k++)	
           { 
            if (_data[i][j][k][l]>value) 
       	    {
	    pos1 = i; pos2 = j; pos3 = k; pos4 = l;
	    value = _data[i][j][k][l];
     	    }
           }
         }
       }
     }
    max_elem_pos1.set(pos1);
    max_elem_pos2.set(pos2);
    max_elem_pos3.set(pos3);
    max_elem_pos4.set(pos4);
    return value;
  }

  public double max() {
    return maxElem(new NslInt0(0), new NslInt0(0), new NslInt0(0), new NslInt0(0));
  }


  public double minElem(NslInt0 max_elem_pos1, NslInt0 max_elem_pos2, NslInt0 max_elem_pos3, NslInt0 max_elem_pos4) {
    double value = java.lang.Double.POSITIVE_INFINITY;
    int i, j, k, l;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;

    int pos1 = -1;
    int pos2 = -1;
    int pos3 = -1;
    int pos4 = -1;
    
    for (i=0; i<size1; i++) 
     {
      for(j=0; j<size2; j++)
       {
        for(k=0; k<size3; k++)	
         {
          for(l=0; l<size4; l++)	
           { 
            if (_data[i][j][k][l]<value) 
   	     {
	      pos1 = i; pos2 = j; pos3 = k; pos4 = l;
	      value = _data[i][j][k][l];
	     }
	   }
         }
       }
     }
    max_elem_pos1.set(pos1);
    max_elem_pos2.set(pos2);
    max_elem_pos3.set(pos3);
    max_elem_pos4.set(pos4);
    return value;
  }

  public double min() {
    return minElem(new NslInt0(0), new NslInt0(0), new NslInt0(0), new NslInt0(0));
  }


  ////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////
  /* Threshold functions */


/*  public double[][][][] sigmoid(){
    return nslj.src.math.NslSigmoid.eval(this);
  }
*/
/*
  public double[][][][] sigmoid(double kx1, double kx2, double ky1, double ky2) {
    return nslj.src.math.NslSigmoid.eval(this, kx1, kx2, ky1, ky2);
  }
*/


}

// NslDouble4.java
////////////////////////////////////////////////////////////////////////////////


