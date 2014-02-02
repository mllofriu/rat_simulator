/*  SCCS - @(#)NslBoolean2.java	1.5 - 09/20/99 - 19:19:38 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslBoolean2.java


/**
 * NslBoolean2 - boolean vector
 */

package nslj.src.lang;

public class NslBoolean2 extends NslBoolean {

    public boolean[][] _data;

    /**
     * Constructor with default value null
     */
    public NslBoolean2() {
    	super();
	_data = null;
    }

    /**
     * Constructor, initialize the value to <tt>value</tt>
     * @param value - the init value
     */  
        
    public NslBoolean2(boolean[][] value) {
	super();
	_data = new boolean[value.length][value[0].length];
	set(value);
    }

    /**
     * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
     * @param n - a scalar number
     */
     
    public NslBoolean2(NslNumeric2 n) {
	super();
	_data = new boolean[n.getSize1()][n.getSize2()];
	set(n.getint2());
    }
    
    /**
     * Constructor, initialize the value to the same as another <tt>NslNumeric0</tt>
     * @param n - a scalar number
     */
     
    public NslBoolean2(NslBoolean2 n) {
	super();
	_data = new boolean[n.getSize1()][n.getSize2()];
	set(n.getboolean2());
    }

    /**
     * Constructor, initialize the number to be size <tt>size</tt> 1-D array
     * @param size - size of the new array
     */
  
    public NslBoolean2(int size1, int size2) {
	super();
	_data = new boolean[size1][size2];
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     */
     
    public NslBoolean2(String name) {
	super(name);
	_data = null;
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param size - size of the array
     */

    public NslBoolean2(String name, int size1, int size2) {
	super(name);
	_data = new boolean[size1][size2];
    }

    public NslBoolean2(String name, NslHierarchy curParent) {
	super(name,curParent,curParent.nslGetAccess());
	_data = null;
    }  

    public NslBoolean2(String name, NslHierarchy curParent, int size1, int size2) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[size1][size2];	
    }  
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param value - initialized values
     */

    public NslBoolean2(String name, NslNumeric2 value) {
	super(name);
        _data = new boolean[value.getSize1()][value.getSize2()];
        set(value.getint2());
    }
    
    /**
     * This constructs a number with specified name
     * @param name - name of the variable
     * @param value - initialized values
     */

    public NslBoolean2(String name, NslBoolean2 value) {
	super(name);
        _data = new boolean[value.getSize1()][value.getSize2()];
        set(value.getboolean2());   
    }
    
    public NslBoolean2(String name, NslHierarchy curParent, NslNumeric2 value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[value.getSize1()][value.getSize2()];
	set(value.getint2()); 
    }
    
    public NslBoolean2(String name, NslHierarchy curParent, NslBoolean2 value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[value.getSize1()][value.getSize2()];
	set(value.getboolean2()); 
    }

    public NslBoolean2(String name, NslHierarchy curParent, boolean[][] value) {
	super(name,curParent,curParent.nslGetAccess());
	_data = new boolean[value.length][value[0].length];
	set(value); 
    }
    
    /**
     * allocate memory dynamically
     */
     
    public void nslMemAlloc(int size1, int size2) {
	_data = new boolean[size1][size2];
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
	    set(((NslBoolean2)n).getboolean2());
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
	    return (NslData)(new NslBoolean2(getboolean2()));
	else return (NslData) (new NslBoolean2());
    }

    /**
     * Set the reference to the wrapped data of <tt>n</tt>
     * It is used in double buffered ports, to make the the ports
     * reference different number object at different time.
     * @param n - number concerned
     */

    public NslData setReference(NslData n) {
	try {
	    _data = ((NslBoolean2) n).getboolean2();
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

    public NslBoolean2 setReference(boolean[][] value) {
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
	int i, j;
	int size1 = _data.length;
	int size2 = _data[0].length;

	for (i=0; i<size1; i++) {
	    strbuf.append("{ ");
	    for (j=0; j<size2; j++)
		strbuf.append(_data[i][j]+" ");
            strbuf.append("} ");
	}
	
	return strbuf.toString();	
    }
    
    //----get-------------------------------------
    /**
     * Get the value of this number
     * @return value, in default type
     */
    
    public boolean[][] get() {
	return _data;
    }
    
    public boolean[] get(int pos) {
	return _data[pos];
    }

     public boolean get(int pos1, int pos2) {
	return _data[pos1][pos2];
    }
   /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */

    public double[][] getdouble2() {
	double[][] doubledata;
	int i, j;
	int size1 = _data.length;
        int size2 = _data[0].length;
	
	doubledata = new double[size1][size2];
	for (i=0; i<size1; i++) {
	    for (j=0; j<size2; j++) {
	        doubledata[i][j]=(double)(_data[i][j]?1.0:0.0);
	    }
	}

	return doubledata;
    }

    /**
     * Get the value of this number in single precision floating point number
     * @return value - in single precision pointing point
     */

    public float[][] getfloat2() {
	float[][] floatdata;
	int i, j;
	int size1 = _data.length;
        int size2 = _data[0].length;
	
	floatdata = new float[size1][size2];
	for (i=0; i<size1; i++) {
	    for (j=0; j<size2; j++) {
	    	floatdata[i][j]=(float)(_data[i][j]?1.0:0.0);
	    }
	}

	return floatdata;
    }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public int[][] getint2() {
	int[][] intdata;
	int i, j;
	int size1 = _data.length;
        int size2 = _data[0].length;

	intdata = new int[size1][size2];
	for (i=0; i<size1; i++) {
	    for (j=0; j<size2; j++) {
	        intdata[i][j]=(int)(_data[i][j]?1:0);
	    }
	}

	return intdata;
    }
    
    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public boolean[][] getboolean2() {
	return _data;
    }
     /**
     * Get the value of this number in double precision floating point number
     * @return value - in double precision pointing point
     */

    public double[] getdouble1(int pos) {
	double[] doubledata = new double[_data[0].length];

	for (int i=0; i<_data[0].length; i++) {
	    doubledata[i]=(double)(_data[pos][i]?1.0:0.0);
	}

	return doubledata;
    }

    /**
     * Get the value of this number in single precision floating point number
     * @return value - in single precision pointing point
     */

    public float[] getfloat1(int pos) {
	float[] floatdata = new float[_data[0].length];

	for (int i=0; i<_data[0].length; i++) {
	    floatdata[i]=(float)(_data[pos][i]?1.0:0.0);
	}

	return floatdata;
    }

    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public int[] getint1(int pos) {
	int[] intdata = new int[_data[0].length];

	for (int i=0; i<_data[0].length; i++) {
	    intdata[i]=(int)(_data[pos][i]?1:0);
	}

	return intdata;
    }
    
    /**
     * Get the value of this number in integer
     * @return value - in integer
     */

    public boolean[] getboolean1(int pos) {
	return _data[pos];
    }
   
    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>double</tt>.
     */
     
    public double getdouble(int pos1, int pos2){
	return (double)(_data[pos1][pos2]?1.0:0.0);
    }

    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>float</tt>.
     */
     
    public float getfloat(int pos1, int pos2) {
	return (float)(_data[pos1][pos2]?1.0:0.0);
    }
    
    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>int</tt>.
     */
  
    public int getint(int pos1, int pos2){
	return (int)(_data[pos1][pos2]?1:0);
    }

    /**
     * Get the <tt>pos</tt>th element of this one-dimensional array
     * @return - value in java numerical type <tt>int</tt>.
     */
  
    public boolean getboolean(int pos1, int pos2){
	return _data[pos1][pos2];
    }
    
    // Interface with NslNumeric type
    
    /**
     * Get the value of this number in double precision
     * floating point NslNumeric
     * @return NslNumeric - in double precision pointing point
     */
     
    public NslDouble2 getNslDouble2() {
	return (new NslDouble2(getdouble2()));
    }

    /**
     * Get the value of this number in single precision
     * floating point NslNumeric
     * @return NslNumeric - in single precision pointing point
     */
     
    public NslFloat2 getNslFloat2() {
	return (new NslFloat2(getfloat2()));
    }
    
    /**
     * Get the value of this number in integer
     * @return NslNumeric - in integer
     */
     
    public NslInt2 getNslInteger2() {
	return (new NslInt2(getint2()));
    }

    /**
     * Get the value of this number in integer
     * @return NslNumeric - in integer
     */
     
    public NslBoolean2 getNslBoolean2() {
	return this;
    }
    
    public boolean[][] getSector(int start1, int start2, int end1, int end2) {
	int i1, i2, j1, j2;
	int length1, length2;
	boolean[][] intdata;
	
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
	
	intdata = new boolean[length1][length2];
	i1 = start1;
	i2 = start2;
	
	for (j1=0; j1<length1; j1++, i1++) {
	    for (j2=0, i2=start2; j2<length2; j2++, i2++) {
		intdata[j1][j2]=_data[i1][i2];
	    }
    	}
    	
	return intdata;
    }
   
    //--------------set------------------------------------
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
 
    public boolean[][] set(int[][] value) {
	int i,j;
	int size1 = _data.length;
	int size2 = _data[0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslBoolean2: array size not equal");
	    return _data;
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[i][j] = value[i][j]!=0;
	}
	return _data;
    }
    
    public boolean[][] set(float[][] value) {
	int i;
	int j;
	int size1 = _data.length;
	int size2 = _data[0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslBoolean2:  array size not equal");
	    return _data;
	}
	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[i][j] = value[i][j]!=0.0;
	}

	return _data;
    }

    public boolean[][] set(double[][] value) {
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
		_data[i][j] = value[i][j]!=0.0;
    	}
	return _data;
    }
    
    public boolean[][] set(boolean[][] value) {
	int i,j;
	int size1 = _data.length;
	int size2 = _data[0].length;

	if (size1 != value.length || size2 !=value[0].length) {
	    System.out.println("NslBoolean2: array size not equal");
	    return _data;
	}

	for (i=0; i<size1; i++) {
	    for(j=0; j<size2; j++)
		_data[i][j] = value[i][j];
	}
	return _data;
    }
    
    public boolean[] set(int pos, double[] value) {

	if (_data[0].length != value.length) {
	    System.out.println("NslBoolean2: array size not match");
	    return _data[pos];
	}

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=value[i]!=0.0;
	}
	
	return _data[pos];	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */
     
    public boolean[] set(int pos, float[] value) {

	if (_data[0].length != value.length) {
	    System.out.println("NslBoolean2: array size not match");
	    return _data[pos];
	}

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=value[i]!=0.0;
	}
	
	return _data[pos];	
    }
  
    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public boolean[] set(int pos, int[] value) {

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=value[i]!=0;
	}
	
	return _data[pos];	
    }

    /**
     * Set the value of this number to <tt>value</tt>
     * @param value
     */

    public boolean[] set(int pos, boolean[] value) {
	if (_data[0].length != value.length) {
	    System.out.println("NslBoolean1: array size not match");
	    return _data[pos];
	}

	for (int i=0; i<_data[0].length; i++) {
	    _data[pos][i]=value[i];
	}
	
	return _data[pos];	
    }
    
    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
     
    public boolean set(int pos1, int pos2, double value) {
	_data[pos1][pos2]=value!=0.0;
	return _data[pos1][pos2];
    }
    
    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
     
    public boolean set(int pos1, int pos2, float value) {
        _data[pos1][pos2]=value!=0.0;
        return _data[pos1][pos2];
    }

    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
  
    public boolean set(int pos1, int pos2, int value) {
	_data[pos1][pos2]=value!=0;
	return _data[pos1][pos2];
    }

    /**
     * Set the <tt>pos</tt>th element of this array to <tt>value</tt>
     * @param pos
     * @param value
     */
  
    public boolean set(int pos1, int pos2, boolean value) {
	_data[pos1][pos2]=value;
	return _data[pos1][pos2];
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */

    public boolean[][] set(double value) {
	for (int i=0; i<_data.length; i++) {
	    for (int j=0; j<_data[0].length; j++) {
	        _data[i][j]=value!=0.0;
	    }
        }
       	return _data;
    }
    
    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[][] set(float value) {
	for (int i=0; i<_data.length; i++) {
	    for (int j=0; j<_data[0].length; j++) {
	        _data[i][j]=value!=0.0;
	    }
	}
	return _data;
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */

    public boolean[][] set(int value) {
	for (int i=0; i<_data.length; i++) {
	    for (int j=0; j<_data[0].length; j++) {
	        _data[i][j]=value!=0;
	    }
	}
	return _data;
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */

    public boolean[][] set(boolean value) {
	for (int i=0; i<_data.length; i++) {
	    for (int j=0; j<_data[0].length; j++) {
	        _data[i][j]=value;
	    }
	}
	return _data;
    }

    /**
     * Set the value of this array to <tt>value</tt>
     * @param value
     */

    public boolean[][] set(NslNumeric2 n) {
        return set(n.getint2());
    }
    
    public boolean[] set(int pos, NslNumeric1 value) {
	return set(pos, value.getint1());
    }
     
    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[][] set(NslNumeric0 value) {
	set(value.getint());
	return _data;
    }
        
    public boolean set(int pos1, int pos2, NslNumeric0 value) {	
        set(pos1,pos2,value.getint());	
	return _data[pos1][pos2];	
    }

    /**
     * Set the value of this array to <tt>value</tt>
     * @param value
     */
         
    public boolean[][] set(NslBoolean2 n) {
        return set(n.getboolean2());
    }

    /**
     * Set all elements of this array to <tt>value</tt>
     * @param value
     */
     
    public boolean[][] set(NslBoolean0 value) {
	return set(value.getboolean());
    }
        
    public boolean set(int pos1, int pos2, NslBoolean0 value) {	
        set(pos1,pos2,value.getboolean());	
	return _data[pos1][pos2];	
    }

    public boolean[] set(int pos1, NslBoolean1 n) {
        return set(pos1, n.getboolean1());
    }

    public void setSector(boolean[][] d, int startpos1, int startpos2) {
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
            for (i2=startpos2, j2=0; i2<endpos2; i2++, j2++)
            	_data[i1][i2] = d[j1][j2];
    	}
    }

    //---various ---------------------------------------
    /*-------------Non-Abstract methods ------------------*/

    /**
     * get the dimensions of this object
     * @return always zero
     */

    public int getDimensions() {
	return 2;
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

    public  void getNslSize(NslInt0 size1, NslInt0 size2) {
	size1.set((_data==null?0:_data.length));
	size2.set((_data==null?0:_data[0].length));
    }

    /**
     * get the size of this array and put it into <tt>size</tt>
     * @return size - always a vector of 4 elements
     */

    public int[] getSizes(){
	int[]size =new int[4];
     	size[0]=(_data==null?0:_data.length);
	size[1]=(_data==null?0:_data[0].length);
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
	return (_data==null?0:_data[0].length);
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

// NslBoolean2.java
////////////////////////////////////////////////////////////////////////////////



