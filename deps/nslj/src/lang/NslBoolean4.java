/*  SCCS - @(#)NslBoolean4.java	1.4 - 09/20/99 - 19:19:39 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslBoolean4.java


/**
 * NslBoolean4 - boolean vector
 */

package nslj.src.lang;

public class NslBoolean4 extends NslBoolean {

    public boolean[][][][] _data;

    /**
     * Constructor with default value null
     */
    public NslBoolean4() {
    	super();
	_data = null;
    }

    /**
     * Constructor, initialize the value to <tt>value</tt>
     * @param value - the init value
     */  
        
    public NslBoolean4(boolean[][][][] value) {
	super();
	_data = new boolean[value.length][value[0].length][value[0][0].length][value[0][0][0].length];
	set(value);
    }

    /**
     * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
     * @param n - a scalar number
     */
     
    public NslBoolean4(NslNumeric4 n) {
	super();
	_data = new boolean[n.getSize1()][n.getSize2()][n.getSize3()][n.getSize4()];
	set(n.getint4());
    }
    
    /**
     * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
     * @param n - a scalar number
     */
     
    public NslBoolean4(NslBoolean4 n) {
	super();
	_data = new boolean[n.getSize1()][n.getSize2()][n.getSize3()][n.getSize4()];
	set(n.getboolean4());
    }

    /**
     * Constructor, initialize the number to be size <tt>size</tt> 1-D array
     * @param size - size of the new array
     */
  
    public NslBoolean4(int size1, int size2, int size3, int size4) {
	super();
	_data = new boolean[size1][size2][size3][size4];
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     */
     
    public NslBoolean4(String name) {
	super(name);
	_data = null;
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param size - size of the array
     */

    public NslBoolean4(String name, int size1, int size2, int size3, int size4) {
	super(name);
	_data = new boolean[size1][size2][size3][size4];
    }

    public NslBoolean4(String name, NslHierarchy curParent) {
	super(name,curParent,curParent.nslGetAccess());
	_data = null;
    }  

    public NslBoolean4(String name, NslHierarchy curParent, int size1, int size2, int size3, int size4) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[size1][size2][size3][size4];	
    }  
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param value - initialized values
     */

    public NslBoolean4(String name, NslNumeric4 value) {
	super(name);
        _data = new boolean[value.getSize1()][value.getSize2()][value.getSize3()][value.getSize4()];
        set(value.getint4());
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param value - initialized values
     */

    public NslBoolean4(String name, NslBoolean4 value) {
	super(name);
        _data = new boolean[value.getSize1()][value.getSize2()][value.getSize3()][value.getSize4()];
        set(value.getboolean4());   
    }
    
    public NslBoolean4(String name, NslHierarchy curParent, NslNumeric4 value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[value.getSize1()][value.getSize2()][value.getSize3()][value.getSize4()];
	set(value.getint4()); 
    }
    
    public NslBoolean4(String name, NslHierarchy curParent, NslBoolean4 value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[value.getSize1()][value.getSize2()][value.getSize3()][value.getSize4()];
	set(value.getboolean4()); 
    }

    public NslBoolean4(String name, NslHierarchy curParent, boolean[][][][] value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[value.length][value[0].length][value[0][0].length][value[0][0][0].length];
	set(value); 
    }
    
    /**
     * allocate memory dynamically
     */
     
    public void nslMemAlloc(int size1, int size2, int size3, int size4) {
	_data = new boolean[size1][size2][size3][size4];
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
	    set(((NslBoolean4)n).getboolean4());
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
	    return (NslData)(new NslBoolean4(getboolean4()));
	else return (NslData) (new NslBoolean4());
    }

    /**
     * Set the reference to the wrapped data of <tt>n</tt>
     * It is used in double buffered ports, to make the the ports
     * reference different number object at different time.
     * @param n - number concerned
     */

    public NslData setReference(NslData n) {
	try {
	    _data = ((NslBoolean4) n).getboolean4();
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

    public NslBoolean4 setReference(boolean[][][][] value) {
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
	int i, j, k, l;
	int size1 = _data.length;
	int size2 = _data[0].length;
	int size3 = _data[0][0].length;
	int size4 = _data[0][0][0].length;

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
    
	return strbuf.toString();    
    }
    
    //----get-------------------------------------
    /**
     * Get the value of this number
     * @return value, in default type
     */
    
    public boolean[][][][] get() {
	return _data;
    }
    
    public boolean[][][] get(int pos) {
	return _data[pos];
    }

    public boolean[][] get(int pos1, int pos2) {
	return _data[pos1][pos2];
    }

    public boolean[] get(int pos1, int pos2, int pos3) {
	return _data[pos1][pos2][pos3];
    }

    public boolean get(int pos1, int pos2, int pos3, int pos4) {
	return _data[pos1][pos2][pos3][pos4];
    }

   /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */

    public double[][][][] getdouble4() {
	double[][][][] doubledata;
	int i, j, k, l;
	int size1 = _data.length;
        int size2 = _data[0].length;
        int size3 = _data[0][0].length;
        int size4 = _data[0][0][0].length;
	
	doubledata = new double[size1][size2][size3][size4];
	for (i=0; i<size1; i++) {
	    for (j=0; j<size2; j++) {
	        for (k=0; k<size3; k++) {
		      for (l=0; l<size4; l++) {
	                doubledata[i][j][k][l]=(double)(_data[i][j][k][l]?1.0:0.0);
			}
	        }
   	    }
	}

	return doubledata;
    }

    /**
     * Get the value of this number in single precision floating point number
     * @return value - in single precision pointing point
     */

    public float[][][][] getfloat4() {
	float[][][][] floatdata;
	int i, j, k, l;
	int size1 = _data.length;
        int size2 = _data[0].length;
        int size3 = _data[0][0].length;
        int size4 = _data[0][0][0].length;
	
	floatdata = new float[size1][size2][size3][size4];
	for (i=0; i<size1; i++) {
	    for (j=0; j<size2; j++) {
	        for (k=0; k<size3; k++) {
		      for (l=0; l<size4; l++) {
	    	    	    floatdata[i][j][k][l]=(float)(_data[i][j][k][l]?1.0:0.0);
			}
		  }
	    }
	}

	return floatdata;
    }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public int[][][][] getint4() {
	int[][][][] intdata;
	int i, j, k,l ;
	int size1 = _data.length;
        int size2 = _data[0].length;
        int size3 = _data[0][0].length;
        int size4 = _data[0][0][0].length;

	intdata = new int[size1][size2][size3][size4];
	for (i=0; i<size1; i++) {
	    for (j=0; j<size2; j++) {
	        for (k=0; k<size3; k++) {
		      for (l=0; l<size4; l++) {
	                intdata[i][j][k][l]=(int)(_data[i][j][k][l]?1:0);
			}
		  }
	    }
	}

	return intdata;
    }
    
    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public boolean[][][][] getboolean4() {
	return _data;
    }

   /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */

    public double[][][] getdouble3(int pos) {
	double[][][] doubledata;
	int i, j, k;
	int size1 = _data[0].length;
        int size2 = _data[0][0].length;
        int size3 = _data[0][0][0].length;
	
	doubledata = new double[size1][size2][size3];
	for (i=0; i<size1; i++) {
	    for (j=0; j<size2; j++) {
	        for (k=0; k<size3; k++) {
	            doubledata[i][j][k]=(double)(_data[pos][i][j][k]?1.0:0.0);
	        }
   	    }
	}

	return doubledata;
    }

    /**
     * Get the value of this number in single precision floating point number
     * @return value - in single precision pointing point
     */

    public float[][][] getfloat3(int pos) {
	float[][][] floatdata;
	int i, j, k;
	int size1 = _data[0].length;
        int size2 = _data[0][0].length;
        int size3 = _data[0][0][0].length;
	
	floatdata = new float[size1][size2][size3];
	for (i=0; i<size1; i++) {
	    for (j=0; j<size2; j++) {
	        for (k=0; k<size3; k++) {
	    	    floatdata[i][j][k]=(float)(_data[pos][i][j][k]?1.0:0.0);
		  }
	    }
	}

	return floatdata;
    }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public int[][][] getint3(int pos) {
	int[][][] intdata;
	int i, j, k;
	int size1 = _data[0].length;
        int size2 = _data[0][0].length;
        int size3 = _data[0][0][0].length;

	intdata = new int[size1][size2][size3];
	for (i=0; i<size1; i++) {
	    for (j=0; j<size2; j++) {
	        for (k=0; k<size3; k++) {
	            intdata[i][j][k]=(int)(_data[pos][i][j][k]?1:0);
		  }
	    }
	}

	return intdata;
    }
    
    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public boolean[][][] getboolean3(int pos) {
	return _data[pos];
    }
     /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */

    public double[][] getdouble2(int pos1, int pos2) {
	int i,j;
	int size2 = _data[0][0].length;
	int size3 = _data[0][0][0].length;
	double[][] doubledata = new double[size2][size3];
	for (i=0; i<size2; i++) {
	    for (j=0; j<size3; j++) {
		doubledata[i][j]=(double)(_data[pos1][pos2][i][j]?1.0:0.0);	
	    }
	}

	return doubledata;
    }

    /**
     * Get the value of this number in single precision floating point number
     * @return value - in single precision pointing point
     */

    public float[][] getfloat2(int pos1, int pos2) {
	int i,j;
	int size2 = _data[0][0].length;
	int size3 = _data[0][0][0].length;
	float[][] floatdata = new float[size2][size3];
	for (i=0; i<size2; i++) {
	    for (j=0; j<size3; j++) {
		floatdata[i][j]=(float)(_data[pos1][pos2][i][j]?1.0:0.0);	
	    }
	}
	return floatdata;
    }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public int[][] getint2(int pos1, int pos2) {
	int i,j;
	int size2 = _data[0][0].length;
	int size3 = _data[0][0][0].length;
	int[][] intdata = new int[size2][size3];
	for (i=0; i<size2; i++) {
	    for (j=0; j<size3; j++) {
		intdata[i][j]=(int)(_data[pos1][pos2][i][j]?1:0);	
	    }
	}
	return intdata;
    }
    
    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public boolean[][] getboolean2(int pos1, int pos2) {
	return _data[pos1][pos2];
    }

    /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */

    public double[] getdouble1(int pos1, int pos2, int pos3) {
	double[] doubledata = new double[_data[0][0][0].length];

	for (int i=0; i<_data[0][0][0].length; i++) {
	    doubledata[i]=(double)(_data[pos1][pos2][pos3][i]?1.0:0.0);
	}

	return doubledata;
    }

    /**
     * Get the value of this number in single precision floating point number
     * @return value - in single precision pointing point
     */

    public float[] getfloat1(int pos1, int pos2, int pos3) {
	float[] floatdata = new float[_data[0][0][0].length];

	for (int i=0; i<_data[0][0][0].length; i++) {
	    floatdata[i]=(float)(_data[pos1][pos2][pos3][i]?1.0:0.0);
	}

	return floatdata;
    }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public int[] getint1(int pos1, int pos2, int pos3) {
	int[] intdata = new int[_data[0][0][0].length];

	for (int i=0; i<_data[0][0][0].length; i++) {
	    intdata[i]=(int)(_data[pos1][pos2][pos3][i]?1:0);
	}

	return intdata;
    }
    
    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public boolean[] getboolean1(int pos1, int pos2, int pos3) {
	return _data[pos1][pos2][pos3];
    }   
    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>double</tt>.
     */
     
    public double getdouble(int pos1, int pos2, int pos3, int pos4){
	return (double)(_data[pos1][pos2][pos3][pos4]?1.0:0.0);
    }

    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>float</tt>.
     */
     
    public float getfloat(int pos1, int pos2, int pos3, int pos4) {
	return (float)(_data[pos1][pos2][pos3][pos4]?1.0:0.0);
    }
    
    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>int</tt>.
     */
  
    public int getint(int pos1, int pos2, int pos3, int pos4){
	return (int)(_data[pos1][pos2][pos3][pos4]?1:0);
    }

    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>int</tt>.
     */
  
    public boolean getboolean(int pos1, int pos2, int pos3, int pos4){
	return _data[pos1][pos2][pos3][pos4];
    }
    
    // Interface with NslNumeric type
    
    /**
     * Get the value of this number in double precision
     * floating point NslNumeric
     * @return NslNumeric - in double precision pointing point
     */
     
    public NslDouble4 getNslDouble4() {
	return (new NslDouble4(getdouble4()));
    }

    /**
     * Get the value of this number in single precision
     * floating point NslNumeric
     * @return NslNumeric - in single precision pointing point
     */
     
    public NslFloat4 getNslFloat4() {
	return (new NslFloat4(getfloat4()));
    }
    
    /**
     * Get the value of this number in integer
     * @return NslNumeric - in integer
     */
     
    public NslInt4 getNslInteger4() {
	return (new NslInt4(getint4()));
    }

    /**
     * Get the value of this number in integer
     * @return NslNumeric - in integer
     */
     
    public NslBoolean4 getNslBoolean4() {
	return this;
    }
    
    public boolean[][][][] getSector(int start1, int start2, int start3, int start4, 
	int end1, int end2, int end3, int end4) {
	int i1, i2, i3, i4, j1, j2, j3, j4;
	int length1, length2, length3, length4;
	boolean[][][][] intdata;
	
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
	
	intdata = new boolean[length1][length2][length3][length4];
	i1 = start1;
	i2 = start2;
	i3 = start3;
	i4 = start4;
	
	for (j1=0; j1<length1; j1++, i1++) {
	    for (j2=0, i2=start2; j2<length2; j2++, i2++) {
	        for (j3=0, i3=start3; j3<length3; j3++, i3++) {
	            for (j4=0, i4=start4; j4<length4; j4++, i4++) {
		          intdata[j1][j2][j3][j4]=_data[i1][i2][i3][i4];
			}
		  }
	    }
    	}
    	
	return intdata;
    }
   
    //--------------set------------------------------------
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
 
    public boolean[][][][] set(int[][][][] value) {
	int i,j,k,l;
	int size1 = _data.length;
	int size2 = _data[0].length;
	int size3 = _data[0][0].length;
	int size4 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 !=value[0][0].length || size4 !=value[0][0][0].length) {
	    System.out.println("NslBoolean4: array size not equal");
	    return _data;
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++) {
	        for(k=0; k<size3; k++) {
	            for(l=0; l<size4; l++) {
		          _data[i][j][k][l] = value[i][j][k][l]!=0;
			}
		  }
	    }
	}
	return _data;
    }
    
    public boolean[][][][] set(float[][][][] value) {
	int i;
	int j;
	int k;
	int l;
	int size1 = _data.length;
	int size2 = _data[0].length;
	int size3 = _data[0][0].length;
	int size4 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 != value[0][0].length || size4 != value[0][0][0].length) {
	    System.out.println("NslBoolean4:  array size not equal");
	    return _data;
	}
	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
	        for(k=0; k<size3; k++) {
	            for(l=0; l<size4; l++) {
	 		    _data[i][j][k][l] = value[i][j][k][l]!=0.0;
			}
		  }
	}

	return _data;
    }

    public boolean[][][][] set(double[][][][] value) {
	int i;
	int j;
	int k;
	int l;
	int size1 = _data.length;
	int size2 = _data[0].length;
	int size3 = _data[0][0].length;
	int size4 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 !=value[0][0].length|| size4 !=value[0][0][0].length) {
	    System.out.println("NslDouble4: array size not equal");
	    return _data;
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
	        for(k=0; k<size3; k++) {
	            for(l=0; l<size4; l++) {
		         _data[i][j][k][l] = value[i][j][k][l]!=0.0;
			}
		  }
    	}
	return _data;
    }
    
    public boolean[][][][] set(boolean[][][][] value) {
	int i,j,k,l;
	int size1 = _data.length;
	int size2 = _data[0].length;
	int size3 = _data[0][0].length;
	int size4 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 !=value[0][0].length || size4 !=value[0][0][0].length) {
	    System.out.println("NslBoolean4: array size not equal");
	    return _data;
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
	        for(k=0; k<size3; k++) {
	            for(l=0; l<size4; l++) {
			    _data[i][j][k][l] = value[i][j][k][l];
			}
		  }
	}
	return _data;
    }
 
    public boolean[][][] set(int pos, int[][][] value) {
	int i,j,k;
	int size1 = _data[0].length;
	int size2 = _data[0][0].length;
	int size3 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 !=value[0][0].length) {
	    System.out.println("NslBoolean4: array size not equal");
	    return _data[pos];
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++) {
	        for(k=0; k<size3; k++) {
		     _data[pos][i][j][k] = value[i][j][k]!=0;
		  }
	    }
	}
	return _data[pos];
    }
    
    public boolean[][][] set(int pos, float[][][] value) {
	int i;
	int j;
	int k;
	int size1 = _data[0].length;
	int size2 = _data[0][0].length;
	int size3 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 != value[0][0].length) {
	    System.out.println("NslBoolean4:  array size not equal");
	    return _data[pos];
	}
	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
	        for(k=0; k<size3; k++) {
			_data[pos][i][j][k] = value[i][j][k]!=0.0;
		  }
	}

	return _data[pos];
    }

    public boolean[][][] set(int pos, double[][][] value) {
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
		     _data[pos][i][j][k] = value[i][j][k]!=0.0;
		  }
    	}
	return _data[pos];
    }
    
    public boolean[][][] set(int pos, boolean[][][] value) {
	int i,j,k;
	int size1 = _data[0].length;
	int size2 = _data[0][0].length;
	int size3 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length
	    || size3 !=value[0][0].length) {
	    System.out.println("NslBoolean4: array size not equal");
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

    public boolean[][] set(int pos1, int pos2, int[][] value) {
	int i,j;
	int size1 = _data[0][0].length;
	int size2 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslBoolean4: array size not equal");
	    return _data[pos1][pos2];
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[pos1][pos2][i][j] = value[i][j]!=0;
	}
	return _data[pos1][pos2];
    }
    
    public boolean[][] set(int pos1, int pos2, float[][] value) {
	int i;
	int j;
	int size1 = _data[0][0].length;
	int size2 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslBoolean4:  array size not equal");
	    return _data[pos1][pos2];
	}
	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[pos1][pos2][i][j] = value[i][j]!=0.0;
	}

	return _data[pos1][pos2];
    }

    public boolean[][] set(int pos1, int pos2, double[][] value) {
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
		_data[pos1][pos2][i][j] = value[i][j]!=0.0;
    	}
	return _data[pos1][pos2];
    }
    
    public boolean[][] set(int pos1, int pos2, boolean[][] value) {
	int i,j;
	int size1 = _data[0][0].length;
	int size2 = _data[0][0][0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslBoolean4: array size not equal");
	    return _data[pos1][pos2];
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[pos1][pos2][i][j] = value[i][j];
	}
	return _data[pos1][pos2];
    }
    
    public boolean[] set(int pos1, int pos2, int pos3, double[] value) {

	if (_data[0][0][0].length != value.length) {
	    System.out.println("NslBoolean4: array size not match");
	    return _data[pos1][pos2][pos3];
	}

	for (int i=0; i<_data[0][0][0].length; i++) {
	    _data[pos1][pos2][pos3][i]=value[i]!=0.0;
	}
	
	return _data[pos1][pos2][pos3];	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public boolean[] set(int pos1, int pos2, int pos3, float[] value) {

	if (_data[0][0][0].length != value.length) {
	    System.out.println("NslBoolean4: array size not match");
	    return _data[pos1][pos2][pos3];
	}

	for (int i=0; i<_data[0][0][0].length; i++) {
	    _data[pos1][pos2][pos3][i]=value[i]!=0.0;
	}
	
	return _data[pos1][pos2][pos3];	
    }
  
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public boolean[] set(int pos1, int pos2, int pos3, int[] value) {

	if (_data[0][0][0].length != value.length) {
	    System.out.println("NslBoolean4: array size not match");
	    return _data[pos1][pos2][pos3];
	}

	for (int i=0; i<_data[0][0][0].length; i++) {
	    _data[pos1][pos2][pos3][i]=value[i]!=0;
	}
	
	return _data[pos1][pos2][pos3];	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public boolean[] set(int pos1, int pos2, int pos3, boolean[] value) {
	if (_data[0][0][0].length != value.length) {
	    System.out.println("NslBoolean4: array size not match");
	    return _data[pos1][pos2][pos3];
	}

	for (int i=0; i<_data[0][0][0].length; i++) {
	    _data[pos1][pos2][pos3][i]=value[i];
	}
	
	return _data[pos1][pos2][pos3];	
    }
    
    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
     
    public boolean set(int pos1, int pos2, int pos3, int pos4, double value) {
	_data[pos1][pos2][pos3][pos4]=value!=0.0;
	return _data[pos1][pos2][pos3][pos4];
    }
    
    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
     
    public boolean set(int pos1, int pos2, int pos3, int pos4, float value) {
        _data[pos1][pos2][pos3][pos4]=value!=0.0;
        return _data[pos1][pos2][pos3][pos4];
    }

    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
  
    public boolean set(int pos1, int pos2, int pos3, int pos4, int value) {
	_data[pos1][pos2][pos3][pos4]=value!=0;
	return _data[pos1][pos2][pos3][pos4];
    }

    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
  
    public boolean set(int pos1, int pos2, int pos3, int pos4, boolean value) {
	_data[pos1][pos2][pos3][pos4]=value;
	return _data[pos1][pos2][pos3][pos4];
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */

    public boolean[][][][] set(double value) {
	for (int i=0; i<_data.length; i++) {
	    for (int j=0; j<_data[0].length; j++) {
		for (int k=0; k<_data[0][0].length; k++) {
		    for (int l=0; l<_data[0][0][0].length; l++) {
	              _data[i][j][k][l]=value!=0.0;
		    }
		}
	    }
        }
       	return _data;
    }
    
    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[][][][] set(float value) {
	for (int i=0; i<_data.length; i++) {
	    for (int j=0; j<_data[0].length; j++) {
		for (int k=0; k<_data[0][0].length; k++) {
		    for (int l=0; l<_data[0][0][0].length; l++) {
	              _data[i][j][k][l]=value!=0.0;
		    }
		}
	    }
	}
	return _data;
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */

    public boolean[][][][] set(int value) {
	for (int i=0; i<_data.length; i++) {
	    for (int j=0; j<_data[0].length; j++) {
		for (int k=0; k<_data[0][0].length; k++) {
		    for (int l=0; l<_data[0][0][0].length; l++) {
	              _data[i][j][k][l]=value!=0;
		    }
		}
	    }
	}
	return _data;
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */

    public boolean[][][][] set(boolean value) {
	for (int i=0; i<_data.length; i++) {
	    for (int j=0; j<_data[0].length; j++) {
		for (int k=0; k<_data[0][0].length; k++) {
		    for (int l=0; l<_data[0][0][0].length; l++) {
	              _data[i][j][k][l]=value;
		    }
		}
	    }
	}
	return _data;
    }

    /**
     * Set the value of this array to <tt>value</tt>
     * @param value
     */

    public boolean[][][][] set(NslNumeric4 n) {
        return set(n.getint4());
    }
    
    public boolean[][][] set(int pos, NslNumeric3 value) {
	return set(pos, value.getint3());
    }

    public boolean[][] set(int pos1, int pos2, NslNumeric2 value) {
	return set(pos1, pos2, value.getint2());
    }

    public boolean[] set(int pos1, int pos2, int pos3, NslNumeric1 value) {
	return set(pos1, pos2, pos3, value.getint1());
    }
     
    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[][][][] set(NslNumeric0 value) {
	set(value.getint());
	return _data;
    }
        
    public boolean set(int pos1, int pos2, int pos3, int pos4, NslNumeric0 value) {	
        return set(pos1,pos2,pos3,pos4,value.getint());	
    }

    /**
     * Set the value of this array to <tt>value</tt>
     * @param value
     */
         
    public boolean[][][][] set(NslBoolean4 n) {
        return set(n.getboolean4());
    }

    public boolean[][][][] set(int pos, NslBoolean3 value) {
	set(pos, value.getboolean3());
	return _data;
    }

    public boolean[][][][] set(int pos1, int pos2, NslBoolean2 value) {
	set(pos1, pos2, value.getboolean2());
	return _data;
    }

    public boolean[][][][] set(int pos1, int pos2, int pos3, NslBoolean1 value) {
	set(pos1, pos2, pos3, value.getboolean1());
	return _data;
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[][][][] set(NslBoolean0 value) {
	return set(value.getboolean());
    }
        
    public boolean[][][][] set(int pos1, int pos2, int pos3, int pos4, NslBoolean0 value) {	
        set(pos1,pos2,pos3,pos4,value.getboolean());	
	return _data;	
    }

    public void setSector(boolean[][][][] d, int startpos1, int startpos2, int startpos3, int startpos4) {
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

	for (i1=startpos1, j1=0; i1<endpos1; i1++, j1++) {
            for (i2=startpos2, j2=0; i2<endpos2; i2++, j2++)
                for (i3=startpos3, j3=0; i3<endpos3; i3++, j3++)
   	              for (i4=startpos4, j4=0; i4<endpos4; i4++, j4++)
            	      _data[i1][i2][i3][i4] = d[j1][j2][j3][j4];
    	}
    }

    //---various ---------------------------------------
    /*-------------Non-Abstract methods ------------------*/

    /**
     * get the dimensions of this object
     * @return always zero
     */

    public int getDimensions() {
	return 4;
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

    public  void getNslSize(NslInt0 size1, NslInt0 size2, NslInt0 size3, NslInt0 size4) {
	size1.set((_data==null?0:_data.length));
	size2.set((_data==null?0:_data[0].length));
	size3.set((_data==null?0:_data[0][0].length));
	size4.set((_data==null?0:_data[0][0][0].length));
    }

    /**
     * get the size of this array and put it into <tt>size</tt>
     * @return size - always a vector of 4 elements
     */

    public int[] getSizes(){
	int[]size =new int[4];
     	size[0]=(_data==null?0:_data.length);
	size[1]=(_data==null?0:_data[0].length);
 	size[2]=(_data==null?0:_data[0][0].length);
	size[3]=(_data==null?0:_data[0][0][0].length);
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
	return (_data==null?0:_data[0].length);
    }

    /**
     * get the size of this array at the third to right dimension
     * @return always zero
     */

    public int getSize3() {
	return (_data==null?0:_data[0][0].length);
    }
  
    /**
     * get the size of this array at the fourth to right dimension
     * @return always zero
     */

    public int getSize4() {
	return (_data==null?0:_data[0][0][0].length);
    }

}

// NslBoolean4.java
////////////////////////////////////////////////////////////////////////////////

