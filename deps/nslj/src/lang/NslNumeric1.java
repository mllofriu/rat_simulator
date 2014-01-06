/*  SCCS @(#)NslNumeric1.java	1.8 --- 09/01/99 -- 00:16:49 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////////////////
// NslNumeric1.java
/**
 * NslNumeric1 class is the base class for all vector, or one-dimensional
   array, number objects used
   in NSL System.
 */
package nslj.src.lang;

public abstract class NslNumeric1 extends NslNumeric {
	protected double[] _data;

public NslNumeric1() {
	super();
}; //??
public NslNumeric1(String label) {
	super(label);
}
public NslNumeric1(String label, NslHierarchy parent) {
	
super(label,parent);
}
//public NslNumeric1(String label, NslClass parent) {
//	super(label,parent);
//}
public NslNumeric1(String label, NslHierarchy parent, char desiredAccess) {

	super(label,parent,desiredAccess);
}
//public NslNumeric1(String label, NslClass parent, char desiredAccess) {
//	super(label,parent,desiredAccess);
//}
/* -------------- Abstract methods -------------*/
//---------------------gets---------------------------------

/**
   * Get the array
	@return array - 
   */
//public abstract native[] get();
/**
 * Get the element
 * @return element 
   */
//public abstract native get(int pos);


/**
   * Get the array
     in double precision floating point
	@return array - in double precision pointing point
   */
// Should be overriden in NslDouble1 for efficiency

  public abstract double[] getdouble1() ;
/*
    double[] doubledata = new double[_data.length];
    int i;

    for (i=0; i<_data.length; i++) {
      doubledata[i]=(double)_data[i];
    }
    return doubledata;
  }
*/
  /**
   * Get the array
     in single precision floating point
   * @return array - in single precision pointing point
   */
// Should be overriden in NslFloat1 for efficiency

  public abstract float[] getfloat1() ;
/*
    float[] floatdata = new float[_data.length];
    int i;

    for (i=0; i<_data.length; i++) {
      floatdata[i]=(float)_data[i];
    }

    return floatdata;
  }
*/
/**
   * Get the array
     in single precision floating point
   * @return array - in int
   */
// Should be overriden in NslInt1 for efficiency

  public abstract int[] getint1() ;
/*
    int[] intdata = new int[_data.length];
    int i;

    for (i=0; i<_data.length; i++) {
      intdata[i]=(int)_data[i];
    }

    return intdata;
  }
*/
 //-----------------

/**
   * Get the <tt>pos</tt>th element of this one-dimensional array
   * @return - value in java numerical type <tt>double</tt>.
   */
  public abstract double getdouble(int pos);
/*
	return (double)_data[pos];
  }
*/
/**
   * Get the <tt>pos</tt>th element of this one-dimensional array
   * @return - value in java numerical type <tt>float</tt>.
   */
  public abstract float  getfloat(int pos) ;
/*
	return (float)_data[pos];
  }
*/
  /**
   * Get the <tt>pos</tt>th element of this one-dimensional array
   * @return - value in java numerical type <tt>int</tt>.
   */
  public abstract int    getint(int pos);
/*
	return (int)_data[pos];
  }
*/
  //-----------


  /**
   * Create a <tt>NslDouble1</tt> array with the same value as this array
   * @return array - in NslDouble1
   */
	//override in NslDouble1 for effeciency
  public abstract NslDouble1 getNslDouble1() ;
/*
    return (new NslDouble1(getdouble1()));
  }
*/
  /**
   * Create a <tt>NslFloat1</tt> array with the same value as this array
   * @return array - in NslFloat1
   */
//override in NslFloat1 for effeciency 
  public abstract NslFloat1 getNslFloat1() ;
/*
    return (new NslFloat1(getfloat1()));
  }
*/
/**
   * Create a <tt>NslInt1</tt> array with the same value as this array
   * @return array - in NslInt1
   */
//override in NslInt1 for effeciency 
  public abstract NslInt1 getNslInt1() ;
/*
    return (new NslInt1(getint1()));
  }
*/
//------------
/**
   * Create an array that captures element <tt>start</tt> to <tt>end</tt>
     if <tt>start</tt> is smaller than 0, <tt>start</tt> is default as 0;
     if <tt>end</tt> is greater than the length of the array,
        <tt>end</tt> is default as the length of the array
   * @param start - the element number start the capture
   * @param end - the element number ends the capture
   * @return a section of the original array
   */
  //public abstract native[] getSector(int start, int end) {


// ------------------sets----------------

/**
   * set the value of this object to one-dimensional array <tt>value</tt>
   * @param value - value to be defined.
   */
 abstract public void _set(double[] value);
  /**
   * set the value of this object to one-dimensional array <tt>value</tt>
   * @param value - value to be defined.
   */
 abstract public void _set(float[]  value);
  /**
   * set the value of this object to one-dimensional array <tt>value</tt>
   * @param value - value to be defined.
   */
 abstract public void _set(int[] value);

  /**
   * set the value of <tt>pos</tt>th element of this object to <tt>value</tt>
   * @param pos - position of the element to be defined
   * @param value - value to be defined.
   */
  abstract public void _set(int pos, double value);
  /**
   * set the value of <tt>pos</tt>th element of this object to <tt>value</tt>
   * @param pos - position of the element to be defined
   * @param value - value to be defined.
   */
  abstract public void _set(int pos, float  value);
  /**
   * set the value of <tt>pos</tt>th element of this object to <tt>value</tt>
   * @param pos - position of the element to be defined
   * @param value - value to be defined.
   */
 abstract public void _set(int pos, int    value);

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
   * @param value - in any of <tt>NslNumeric1</tt> type.
   */
  abstract public void _set(NslNumeric1 value);

  // changing all inside the array

  /**
   * Set the value of all elements of this object to be <tt>value</tt>
   */
  abstract public void _set(NslNumeric0 value);
/**
   * set the value of <tt>pos</tt>th element of this object to <tt>value</tt>
   * @param pos - position of the element to be defined
   * @param value - value to be defined.
   */
 abstract public void _set(int pos, NslNumeric0   value);

/**
   * Set the value of the array from <tt>startpos</tt> to d
     If the array <tt>d</tt> longer than this array, those
     out of array scope elements are ignored.
   * @param d - object 1-D array
   * @startpos - the element number to start copying
   */
 // public abstract void setSector(native[] value, int startpos);

//-------------------------------------------------------------------
// auxillary information

/**
   * get the dimensions of this object
   * @return always 1
   */
  public int getDimensions() {
    return 1;
  }


 public abstract int getSize(); // short handed format
/*
     return _data.length;
   }
*/

/**
   * get the size of this array and put it into <tt>size</tt>
   * @param size
   */
 public abstract void getNslSize(NslInt0 size) ;
/*
    size.set(_data.length);
  }
*/

/**
   * get the size of this array and put it into <tt>size</tt>
   * @return size - always a vector of 4 elements
   */
 public abstract int[] getSizes() ;
/*
public int[] getSizes() {
	int[]size =new int[4];
     	size[0]=_data.length;
	size[1]=0;
 	size[2]=0;
	size[3]=0;
	return(size);
  }
*/


/**
   * get the size of this array at the rightmost dimension
   * @return always zero
   */
 public abstract int getSize1() ;
/*
    return _data.length;
  }
*/

}




