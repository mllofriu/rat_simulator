/*  SCCS - @(#)NslNumeric2.java	1.2 - 05/21/99 - 17:43:34 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslNumeric2.java
package nslj.src.lang;

public abstract class NslNumeric2 extends NslNumeric {
public NslNumeric2() {
	super();
}; //??
public NslNumeric2(String label) {
	super(label);
}
public NslNumeric2(String label, NslHierarchy parent) {
	super(label,parent);
}
//public NslNumeric2(String label, NslClass parent) {
//	super(label,parent);
//}

public NslNumeric2(String label, NslHierarchy parent, char desiredAccess) {
	super(label,parent,desiredAccess);
}
//public NslNumeric2(String label, NslClass parent, char desiredAccess) {
//	super(label,parent,desiredAccess);
//}

/* ------------- Abstract Methods -----------------------*/

	// public abstract native[][] get();
	// public abstract native[] get(pos1);
	// public abstract native get(pos1,pos2);
	
  /**
   * @return - the value of this object in java numerical array type
     <tt>double[][]</tt>.
	Override this method in NslDouble2 with more efficient one.
   */
  public abstract double[][] getdouble2() ;
/*
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
*/


  /**
   * @return - the value of this object in java numerical array type
     <tt>float[][]</tt>.
	Override this method in NslFloat2 with more efficient one.

   */
  public abstract float[][]  getfloat2();
/*
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
*/

  /**
   * @return - the value of this object in java numerical array type
     <tt>int[][]</tt>.
	Override this method in NslInt2 with more efficient one.
   */
public abstract int[][] getint2() ;
/*
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
*/
/**
   * @param pos - row number to be addressed.
   * @return - the value of <tt>pos</tt>th row of this object
     in java numerical array type <tt>double[]</tt>.
   */
public abstract double[] getdouble1(int pos) ;
/*
    int i;
    int size2 = _data[0].length;
    double[] tmp = new double[size2];
    for (i=0; i<size2; i++)
        tmp[i]=(double)_data[pos][i];
    return tmp;
  }
*/

  /**
   * @param pos - row number to be addressed.
   * @return - the value of <tt>pos</tt>th row of this object
     in java numerical array type <tt>float[]</tt>.
   */
   public abstract float[] getfloat1(int pos) ;
/*
{
    int i;
    int size2 = _data[0].length;
    float[] tmp = new float[size2];
    for (i=0; i<size2; i++)
        tmp[i]=(float)_data[pos][i];
    return tmp;
  }
*/
  /**
   * @param pos - row number to be addressed.
   * @return - the value of <tt>pos</tt>th row of this object
     in java numerical array type <tt>int[]</tt>.
   */ 
   public abstract int[] getint1(int pos) ;
/*
{
    int i;
    int size2 = _data[0].length;
    int tmp[] = new int[size2];
    for (i=0; i<size2; i++)
        tmp[i]=(int)_data[pos][i];
    return tmp;
  }
*/
/**
   * @param pos1 - row number
   * @param pos2 - column number
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>double</tt>.
   */
public abstract double getdouble(int pos1, int pos2) ;
/*
{
    return (double)_data[pos1][pos2];
  }
*/
  /**
   * @param pos1 - row number
   * @param pos2 - column number
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>float</tt>.
   */
public abstract float getfloat(int pos1, int pos2) ;
/*
{
    return (float)_data[pos1][pos2];
  }
*/
  /**
   * @param pos1 - row number
   * @param pos2 - column number
   * @return the value of the element in <tt>pos1</tt>th row and
     <tt>pos2</tt>th column in java numerical type <tt>int</tt>.
   */
/**
   * set the value of this object to <tt>value</tt>
   * @param value - two dimension array
   */

public abstract int getint(int pos1, int pos2) ;
/*
{
    return (int)_data[pos1][pos2];
  }
*/
// Interface with NslNumeric type
  /**
   * get the value of this object in <tt>NslDouble2</tt> form.
   */
// 	Override this method in NslDouble2 with more efficient one.

  public abstract NslDouble2 getNslDouble2();
/*
{
    return (new NslDouble2(getdouble2()));
  }
*/
// 	Override this method in NslFloat2 with more efficient one.

  public abstract NslFloat2 getNslFloat2();
/*
{
    return (new NslFloat2(getfloat2()));
  }
*/
// 	Override this method in NslFloat2 with more efficient one.

  public abstract NslInt2 getNslInt2();
/*
{
    return (new NslInt2(getint2()));
  }
*/

	// public abstract native[][] getSector(startpos1,startpos2);

//-------sets--------------------------------------
public abstract void _set(double[][] value);
  /**
   * set the value of this object to <tt>value</tt>
   * @param value - two dimension array
   */
  public abstract void _set(float[][]  value);
  /**
   * set the value of this object to <tt>value</tt>
   * @param value - two dimension array
   */
  public abstract void _set(int[][]    value);

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the row number of the element
   * @param pos2 - the column number of the element
   * @param value - scalar in double
   */
  public abstract void _set(int pos1, int pos2, double value);
  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the row number of the element
   * @param pos2 - the column number of the element
   * @param value - scalar in float
   */
  public abstract void _set(int pos1, int pos2, float  value);
  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the row number of the element
   * @param pos2 - the column number of the element
   * @param value - scalar in int
   */
  public abstract void _set(int pos1, int pos2, int    value);

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
/**
   * Set the value of this object to be <tt>value</tt>
   * @param value - in any of <tt>NslNumeric2</tt> type.
   */
  public abstract void _set(NslNumeric2 value);


  // changing all inside the array
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  public abstract void _set(NslNumeric0 value);

/**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the row number of the element
   * @param pos2 - the column number of the element
   * @param value - scalar in NslNumeric0
   */
  public abstract void _set(int pos1, int pos2, NslNumeric0 value);

//public void setSector(native[][] d, int startpos1, int startpos2);


// auxilery ---------------------------------------
/**
   * get the dimensions of this object
   * @return always 2
   */
  public int getDimensions() {
    return 2;
  }

  public abstract int[] getSizes();
/*
{
	int[] size =new int[4];
    size[0]=( _data.length);
    size[1]=( _data[0].length);
    size[2]=(0);
    size[3]=(0);

	return size;
  }
*/
  public abstract void getNslSizes(NslInt0 size1, NslInt0 size2) ;
/*
{
    size1.set( _data.length);
    size2.set( _data[0].length);
  }
*/
/**
   * Get the left most index (2st axis) in this array
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

}








