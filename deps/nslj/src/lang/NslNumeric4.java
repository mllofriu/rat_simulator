/*  SCCS - @(#)NslNumeric4.java	1.2 - 05/21/99 - 17:43:35 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslNumeric4.java
package nslj.src.lang;

public abstract class NslNumeric4 extends NslNumeric {
public NslNumeric4() {
	super();
}; //??
public NslNumeric4(String label) {
	super(label);
}
public NslNumeric4(String label, NslHierarchy parent) {
	super(label,parent);
}
//public NslNumeric4(String label, NslClass parent) {
//	super(label,parent);
//}

public NslNumeric4(String label, NslHierarchy parent, char desiredAccess) {
	super(label,parent,desiredAccess);
}
//public NslNumeric4(String label, NslClass parent, char desiredAccess) {
//	super(label,parent,desiredAccess);
//}
/* ------------- Abstract Methods -----------------------*/
//public abstract native[][][][] get();
//public abstract native[][][] get(int pos1);
//public abstract native[][] get(int pos1, int pos2);
//public abstract native[] get(int pos1, int pos2, int pos3);
//public abstract native get(int pos1, int pos2, int pos3, int pos4);
// because the 4d are not parsed yet:
//public abstract native get(NslInt0 pos1, NslInt0 pos2, NslInt0 pos3, NslInt0 pos4);

  /**
   * @return - the value of this object in java numerical array type
     <tt>double[][][][]</tt>.
   */
public abstract double[][][][] getdouble4() ;
/*
{
    double[][][][] doubledata;
    int i;
    int j;
    int k;
    int l;
    int size1 = _data.length;  //left most index
    int size2 = _data[0].length; //second to the left most index
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    doubledata = new double[size1][size2][size3][size4];
    for (i=0; i<size1; i++)
     {
      for(j=0; j<size2; j++)
       {	
        for(k=0; k<size3; k++)
         { 
          for(l=0;l<size4;l++)
           {
      	    doubledata[i][j][k][l] = (double)_data[i][j][k][l];
           }
         }
       }
     }
    return doubledata;
  }
*/
  /**
   * @return - the value of this object in java numerical array type
     <tt>float[][][][]</tt>.
   */

  // override in NslFloat4 for better efficiency
  public abstract float[][][][] getfloat4();
/*
{
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
*/
  /**
   * @return - the value of this object in java numerical array type
     <tt>int[][][][]</tt>.
   */

  // override in NslInt4 for better efficiency
  public abstract int[][][][] getint4();
/*
{
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
*/
//--3--------------------------------------------*----
  /**
   * @param pos1 - left most number
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th and <tt>pos3</tt>th in java numerical type <tt>double[][][]</tt>.
   */
  // override in NslDouble4 for better efficiency
  public abstract double[][][] getdouble3(int pos1) ;
/*
 {
    int h, i, j;
    int size2 = _data[0].length;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    double[][][] tmp = new double[size2][size3][size4];
    for (h=0; h<size2; h++) {
      for (i=0; i<size3; i++) {
         for (j=0; j<size4; j++){
           tmp[h][i][j]=(double)_data[pos1][h][i][j];	
  	 }
      }
    }
    return tmp;    
  } 
*/
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third from left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>float</tt>.
   */
  // override in NslFloat4 for better efficiency
  public abstract float[][][] getfloat3(int pos1) ;
/*
{
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
*/
  /**
   * @param pos1 - left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>int</tt>.
   */
  // override in NslInt4 for better efficiency 
  public abstract int[][][] getint3(int pos1) ;
/*
 {
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
*/
/**2--------------------------------------------*----
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>double[][]</tt>.
   */
  // override in NslDouble4 for better efficiency
  public abstract double[][] getdouble2(int pos1, int pos2) ;
/*
 {
    int i, j;
    int size3 = _data[0][0].length;
    int size4 = _data[0][0][0].length;
    double[][] tmp = new double[size3][size4];
    for (i=0; i<size3; i++) {
      for (j=0; j<size4; j++) {
        tmp[i][j]=(double)_data[pos1][pos2][i][j];	
      }
    }
    return tmp;    
  } 
*/
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third from left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>float[][]</tt>.
   */
  // override in NslFloat4 for better efficiency
  public abstract float[][] getfloat2(int pos1, int pos2) ;
/*
 {
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
*/
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>int[][]</tt>.
   */
  // override in NslInt4 for better efficiency 
  public abstract int[][] getint2(int pos1, int pos2) ;
/*
 {
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
*/
/**1--------------------------------------------*----
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third to the left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>double[]</tt>.
   */
  // override in NslDouble4 for better efficiency
  public abstract double[] getdouble1(int pos1, int pos2,int pos3) ;
/*
{
    int j;
    int size4 = _data[0][0][0].length;
    double[] tmp = new double[size4];
      for (j=0; j<size4; j++) {
        tmp[j]=(double)_data[pos1][pos2][pos3][j];	
      }
    }
    return tmp;    
  } 
*/
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third from left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>float[]</tt>.
   */
  // override in NslFloat4 for better efficiency
  public abstract float[] getfloat1(int pos1, int pos2, int pos3) ;
/*
 {
    int j;
    int size4 = _data[0][0][0].length;
    float[] tmp = new float[size4];
      for (j=0; j<size4; j++) {
        tmp[j]=(float)_data[pos1][pos2][pos3][j];	
      }
    }
    return tmp;    
  } 
*/
  /**
   * @param pos1 - left most index
   * @param pos2 - second to the left most index
   * @param pos3 - third from left most index
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th  and <tt>pos3</tt>th  in java numerical type <tt>int[]</tt>.
   */
  // override in NslInt4 for better efficiency 
  public abstract int[] getint1(int pos1, int pos2, int pos3) ;
/*
 {
    int j;
    int size4 = _data[0][0][0].length;
    int[] tmp = new int[size4];
      for (j=0; j<size4; j++) {
        tmp[j]=(int)_data[pos1][pos2][pos3][j];	
      }
    }
    return tmp;    
  } 
*/

  //---------------------

 /**
   * @param pos1 - left most
   * @param pos2 - second from left
   * @param pos3 - third from left
   * @param pos4 - fourth from left
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th and <tt>pos3</tt>th  and <tt>pos4</tt>th  in java numerical type <tt>double</tt>.
   */
  // override in NslDouble4 for better efficiency 
  public abstract double getdouble(int pos1, int pos2, int pos3, int pos4) ;
/*
{
    return (double)_data[pos1][pos2][pos3][pos4];
  }
*/
 /**
   * @param pos1 - left most
   * @param pos2 - second from left
   * @param pos3 - third from left
   * @param pos4 - fourth from left
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th and <tt>pos3</tt>th  and <tt>pos4</tt>th  in java numerical type <tt>float</tt>.
   */
  // override in NslFloat4 for better efficiency 
  public abstract float getfloat(int pos1, int pos2, int pos3, int pos4) ;
/*
 {
    return (float)_data[pos1][pos2][pos3][pos4];
  }
*/

 /**
   * @param pos1 - left most
   * @param pos2 - second from left
   * @param pos3 - third from left
   * @param pos4 - fourth from left
   * @return the value of the element in <tt>pos1</tt>th  and
     <tt>pos2</tt>th and <tt>pos3</tt>th  and <tt>pos4</tt>th  in java numerical type <tt>int</tt>.
   */
  public abstract int getint(int pos1, int pos2, int pos3, int pos4) ;
/*
 {
    return (int)_data[pos1][pos2][pos3][pos4];
  }
*/

//------------------------------------------------------------------***----
  /**
   * get the value of this object in <tt>NslDouble4</tt> form.
   */
  // override in NslDouble4 for better efficiency 
  public abstract NslDouble4 getNslDouble4();
/*
 {
    return (new NslDouble4(getdouble4()));
  }
*/
  /**
   * get the value of this object in <tt>NslFloat4</tt> form.
   */
  // override in NslFloat4 for better efficiency 
  public abstract NslFloat4 getNslFloat4();
/*
{
    return (new NslFloat4(getfloat4()));
  }
*/
  /**
   * get the value of this object in <tt>NslInt4</tt> form.
   */
  // override in NslInt4 for better efficiency 
  public abstract NslInt4 getNslInt4();
/*
 {
    return (new NslInt4(getint4()));
  }
*/
//-------------------sets--------------------------
/**
   * set the value of this object to <tt>value</tt>
   * @param value - four dimension array
   */
  abstract public void _set(double[][][][] value);
  /**
   * set the value of this object to <tt>value</tt>
   * @param value - four dimension array
   */
  abstract public void _set(float[][][][] value);
  /**
   * set the value of this object to <tt>value</tt>
   * @param value - four dimension array
   */
  abstract public void _set(int[][][][] value);

//--------------------------------------------------------------

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to the left most index of the element
   * @param pos3 - the third from left most index of the element
   * @param pos4 - the 4th axis number of the element
   * @param value - scalar in double
   */
  abstract public void _set(int pos1, int pos2, int pos3, int pos4, double value);
  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to the left most index of the element
   * @param pos3 - the third from left most index of the element
   * @param pos4 - the 4th axis number of the element
   * @param value - scalar in float
   */
  abstract public void _set(int pos1, int pos2, int pos3, int pos4, float  value);
  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to the left most index of the element
   * @param pos3 - the third from left most index of the element
   * @param pos4 - the 4th axis number of the element
   * @param value - scalar in int
   */
  abstract public void _set(int pos1, int pos2, int pos3, int pos4, int value);

//--------------------------------------------------------------*----

  // changing all inside the array
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  abstract public void _set(double value);
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  abstract public void _set(float  value);
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  abstract public void _set(int    value);

  /**
   * Set the value of this object to be <tt>value</tt>
   * @param value - in any of <tt>NslNumeric4</tt> type.
   */
  abstract public void _set(NslNumeric4 value);
// changing all inside the array
  /**
   * set the value of all elements of this object to <tt>value</tt>
   * @param value - value to be defined.
   */
  abstract public void _set(NslNumeric0 value);

  /**
   * set the value of an element in this object to <tt>value</tt>
   * @param pos1 - the left most index of the element
   * @param pos2 - the second to the left most index of the element
   * @param pos3 - the third from left most index of the element
   * @param pos4 - the 4th axis number of the element
   * @param value - scalar in NslNumeric0
   */
  abstract public void _set(int pos1, int pos2, int pos3, int pos4, NslNumeric0 value);



//-----------------------------------------------------

  // auxillary information
//-------------------------getSizes--------------------

/**
   * get the dimensions of this object
   * @return always 4
   */
  public int getDimensions() {
    return 4;
  }

public abstract void getNslSizes(NslInt0 size1, NslInt0 size2, NslInt0 size3, NslInt0 size4) ;
/*
{
    size1.set( _data.length);
    size2.set( _data[0].length);
    size3.set( _data[0][0].length);
	size4.set( _data[0]0][0].length);
  }
*/
  public abstract int[] getSizes();
/*
 {
	int[] size = new int[4];
      size[0]=_data.length;
      size[1]=_data[0].length;
      size[2]=_data[0][0].length;
	size[3]=_data[0][0][0].length;

	return size;
  }
*/
  /**
   * Get the left most index
   */
  public abstract int getSize1();
/*
{  // 1st axis
        return _data.length;
	}
*/
  /**
   * Get the second to the left most index
   */
  public abstract int getSize2();

/*
{
// 2nd axis
        return _data[0].length;
	}
*/
  /**
   * Get the third to the left most index
   */
  public abstract int getSize3();
/*
	{
 // 3rd axis
       return _data[0][0].length;
	}
*/
/**
   * Get the fourth to the left most index
   */
  public abstract int getSize4();
/* { 

// 4th axis
        return _data[0][0][0].length;
    }
*/

//----------------------------------------------------------


}




