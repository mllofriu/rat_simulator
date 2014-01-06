/*  SCCS - @(#)NslNumeric3.java	1.2 - 05/21/99 - 17:43:34 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslNumeric3.java
package nslj.src.lang;


public abstract class NslNumeric3 extends NslNumeric {

public NslNumeric3() {
	super();
}; //??
public NslNumeric3(String label) {
	super(label);
}
public NslNumeric3(String label, NslHierarchy parent) {
	super(label,parent);
}
//public NslNumeric3(String label, NslClass parent) {
//	super(label,parent);
//}

public NslNumeric3(String label, NslHierarchy parent, char desiredAccess) {
	super(label,parent,desiredAccess);
}
//public NslNumeric3(String label, NslClass parent, char desiredAccess) {
//	super(label,parent,desiredAccess);
//}
/* ------------- Abstract Methods -----------------------*/
//-----------------gets--------------------------------------
// the following methods would be abstract if we could return
  // multiple types.
  // public abstract native[][][] get() ;
  // public abstract native[][] get(int pos1) {
  // public abstract native[] get(int pos1, int pos2) {
  // public abstract native get(int pos1, int pos2, int pos3) {
  // the following method is because we currently do not parse the
  // the parameters for 3d and 4d.
  // public abstract native get(NslInt0 pos1, NslInt0 pos2, NslInt0 pos3) 

/**
   * @return - the value of this object in java numerical array type
     <tt>double[][][]</tt>.
   */
  public abstract double[][][] getdouble3() ;
/*
{
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
         { doubledata[i][j][k] = (double)_data[i][j][k];}
       }
     }
    return doubledata;
  }
*/
  /**
   * @return - the value of this object in java numerical array type
     <tt>float[][][]</tt>.
   */
  public abstract float[][][] getfloat3();
/*
 {
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
         { floatdata[i][j][k] = (float)_data[i][j][k];}
       }
     }
    return floatdata;
  }
*/
  /**
   * @return - the value of this object in java numerical array type
     <tt>int[][][]</tt>.
   */
  public abstract int[][][] getint3();
/*
 {
    int[][][] intdata;
    int i;
    int j;
    int k;
    int size1 = _data.length;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    intdata = new int[size1][size2][size3];
    for (i=0; i<size1; i++)
     {
      for(j=0; j<size2; j++)
       {
	for(k=0; k<size3; k++)
	 { intdata[i][j][k] = (int)_data[i][j][k];}
       }
     } 
    return intdata;
  }
*/
//--------------------------------------------------
/**
   * @param pos1 - left most index
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>double</tt>.
   */
  public abstract double[][] getdouble2(int pos1) ;
/*
{
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
*/
  /**
   * @param pos1 - left most index
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>float</tt>.
   */
  public abstract float[][] getfloat2(int pos1) ;
/*
{
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
*/
  /**
   * @param pos1 - left most index
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>int</tt>.
   */
  public abstract int[][] getint2(int pos1) ;
/*
{
    int i,j;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int[][] tmp = new int[size2][size3];
    for (i=0; i<size2; i++) {
      for (j=0; j<size3; j++) {
        tmp[i][j]=(int)_data[pos1][i][j];	
      }
    }
    return tmp;    
  } 
*/
/**************************************************/
  public abstract double[] getdouble1(int pos1, int pos2) ;
/*
 {
    int j;
    int size3 = _data[0][0].length;
    double[] tmp = new double[size3];
    for (j=0; j<size3; j++)
        tmp[j]=(double)_data[pos1][pos2][j];
    return tmp;
  }
*/
  public abstract float[] getfloat1(int pos1, int pos2) ;
/*
 {
    int j;
    int size3 = _data[0][0].length;
    float[] tmp = new float[size3];
    for (j=0; j<size3; j++)
        tmp[j]=(float)_data[pos1][pos2][j];
    return tmp;
  }
*/
  public abstract int[] getint1(int pos1, int pos2) ;
/*
 {
    int j;
    int size3 = _data[0][0].length;
    int []tmp = new int[size3];
    for (j=0; j<size3; j++)
        tmp[j]=(int)_data[pos1][pos2][j];
    return tmp;
  }
*/
/**************************************************/
 /**
   * @param pos1 - left most index
   * @param pos2 - second to left most
   * @param pos3 - height number
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column and <tt>pos3</tt>th height in java numerical type <tt>double</tt>.
   */
  //override in NslDouble3 for better efficiency
  public abstract double getdouble(int pos1, int pos2, int pos3) ;
/*
{
    return (double)_data[pos1][pos2][pos3];
  }
*/
  /**
   * @param pos1 - left most index
   * @param pos2 - second to left most
   * @param pos3 - height number
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column and <tt>pos3</tt>th height in java numerical type <tt>float</tt>.
   */
  //override in NslFloat3 for better efficiency
  public abstract float  getfloat(int pos1, int pos2, int pos3) ;
/*
{
    return (float)_data[pos1][pos2][pos3];
  }
*/
  /**
   * @param pos1 - left most index
   * @param pos2 - second to left most
   * @param pos3 - height number
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column and <tt>pos3</tt>th height in java numerical type <tt>int</tt>.
   */

  public abstract int    getint(int pos1, int pos2, int pos3) ;
/*
{
    return (int)_data[pos1][pos2][pos3];
  }
*/
/*************************************************************************/

  // Interface with NslNumeric type
  /**
   * get the value of this object in <tt>NslDouble3</tt> form.
   */
  public abstract NslDouble3 getNslDouble3();
/*
{
    return (new NslDouble3(getdouble3()));
  }
*/
  public abstract NslFloat3 getNslFloat3();
/*
{
    return (new NslFloat3(getfloat3()));
  }
*/
  public abstract NslInt3 getNslInt3();
/*
 {
    return (new NslInt3(getint3()));
  }
*/
// public abstract native[][][] getSector(int start1, int start2, int start3, int end1, int end2, int end3) 

//-------------------------sets-----------------------------------------

  /**
   * set the value of this object to <tt>value</tt>
   * @param value - three dimensional array
   */
  public abstract void _set(double[][][] value);
  /**
   * set the value of this object to <tt>value</tt>
   * @param value - three dimensional array
   */
  public abstract void _set(float[][][] value);
  /**
   * set the value of this object to <tt>value</tt>
   * @param value - three dimensional array
	   */
  public abstract void _set(int[][][] value);

/**************************************************************/

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to left most of the element
   * @param pos3 - the height number of the element
   * @param value - scalar in double
   */
  public abstract void _set(int pos1, int pos2, int pos3, double value);
  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to left most of the element
   * @param pos3 - the height number of the element
   * @param value - scalar in float
   */
  public abstract void _set(int pos1, int pos2, int pos3, float  value);
  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to left most of the element
   * @param pos3 - the height number of the element
   * @param value - scalar in int
   */
  public abstract void _set(int pos1, int pos2, int pos3, int value);

/*******************************************************************/

  // changing all inside the array
  /**

   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  public abstract void _set(double value);
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  public abstract void _set(float  value);
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  public abstract void _set(int    value);

/********************************************************/

  /**
   * Set the value of this object to be <tt>value</tt>
   * @param value - in any of <tt>NslNumeric3</tt> type.
   */
  public abstract void _set(NslNumeric3 value);


  // changing all inside the array
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value of NslNumeric0.
   */
  public abstract void _set(NslNumeric0 value);

/**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to left most of the element
   * @param pos3 - the        number of the element
   * @param value - scalar in NslNumeric0
   */
  public abstract void _set(int pos1, int pos2, int pos3, NslNumeric0 value);


// public abstract void setSector(native[][][] d, int startpos1, int startpos2, int startpos3) {



/*********************************************************/
  // auxillary information
/**
   * get the dimensions of this object
   * @return always 3
   */
  public int getDimensions() {
    return 3;
  }


  public abstract void getNslSizes(NslInt0 size1, NslInt0 size2, NslInt0 size3);
/* {
    size1.set( _data.length);
    size2.set( _data[0].length);
    size3.set( _data[0][0].length);
  }
*/
  public abstract int[] getSizes();
/*
 {
	int[] size = new int[3];
      size[0]=_data.length;
      size[1]=_data[0].length;
      size[2]=_data[0][0].length;
	return size;
  }
*/
  /**
   * Get the left most index (1st axis) in this array
   */

    public abstract int getSize1();
/*
 {
        return _data.length;
    }
*/
  /**
   * Get the second left most index (2st axis) in this array
   */
    public abstract int getSize2();
/*
 {
        return _data[0].length;
    }
*/
  /**
   * Get the third left most index (3st axis) in this array
   */

    public abstract int getSize3();
/*
{
        return _data[0][0].length;
    }
*/
}




