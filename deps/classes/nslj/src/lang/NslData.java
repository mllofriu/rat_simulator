/* SCCS %W% -- %G%  -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslData
//////////////////////////////////////////////////////////////////////

/**
 * NslData class is the base class for all data types used
 * in NSL system.
 * A NslData may or may not have a port attached to it. 
 */

package nslj.src.lang;

public abstract class NslData extends NslBase {
    // Number types 

    static final int Inport = 1;
    static final int Outport = 2;

    public NslData() {
	super();
    } //??
    
    public NslData(String label) {
	super(label);
    }
     
    public NslData(String label, NslHierarchy parent) {
	super(label,parent);
 	if (parent!=null) {
	    parent.nslAddToDataVars(this);
	}
    }
    
    public NslData(String label, NslHierarchy parent, char desiredAccess) {
	super(label,parent,desiredAccess);
        if (parent!=null) {
                parent.nslAddToDataVars(this);
        }         
    }
        /**
     * Copy all data from <tt>n</tt> to this number object.
     * It is used in <tt>NslPort<tt> and relevant classes
     * only.
     * @param n - number to be copied
     * @see NslPort, NslOutport
     */
 
    public abstract void duplicateData(NslData d);

    /**
     * This method is equivalent to <tt>clone()</tt> method
     * in java.lang.Object. It duplicates a copy of this number
     * object. It is used in double buffered outport only.
     * @see NslPort, NslOutport.
     */
     
    public abstract NslData duplicateThis();

    /**
     * Set the reference pointer of this number object to the
     * data value of <tt>n</tt>. It is similar to <i>two pointers
     * pointing to a same object<i> in C/C++. Whenenver the data
     * value of one side is changed, the other side is changed as
     * well. It is used only in <tt>NslPort</tt>
     * @param n - number that the reference pointer to the "true"
     * numerical data is copied.
     * @see NslPort, NslInport
     */

    public abstract NslData setReference(NslData d);

    /**
     * Check if the number is well-defined and the corresponding
     * numerical data is set.
     * @return true - if the data is well-defined. false - if the     
     *  data is null.
     */
     
    public abstract boolean isDataSet();

    /** 
     * Reset the reference pointer to null
     */
     
    public abstract void resetData();
    
    /**
     * Print the value of the numberic
     */

    public abstract String toString();

    /**
     * Get number of dimensions of the Nsl Type
     * @ return number of dimensions
     */
    
    public abstract int getDimensions();

    /* --------------------- Non-abstract -----------------------*/
    
    /* The following nslGetPort, getInPort, and getOutPort return null
     * because no port is usually associated with a NslNumeric.
     */

    /**
     * Get the Port object
     * @return NslPort port
     */
 
    public NslPort nslGetPort() {
	return null;
    }
 
    /**
     * Get the port object
     * @return NslOutPort 
     */
	
    public NslOutport getOutport() {
	return null;
    }

    /**
     * Get the port object
     * @return NslInPort 
     */

    public NslInport getInport() {
       return null;
    }

    /**
    */
    
    public int[] getSizes() {
	int[] size = new int[4];
        size[0]=0;
        size[1]=0;
        size[2]=0;
        size[3]=0;
	return size;
    }

    /**
     */
     
    public void getNslSizes(NslInt0 size) {
        size.set(0);
    }

    /**    
     * Get the left most index (2st axis) in this array
     */
     
    public int getSize1() {
        return 0;
    }
    
    /**
     * Get the second left most index (2st axis) in this array
     */
     
    public int getSize2() {
        return 0;
    }
    
    /**
     * Get the third left most index (3rd axis) in this array
     */
  
    public int getSize3() {
        return 0;
    }
    
    /**
     * Get the fourth left most index (4th axis) in this array
     */
    
    public int getSize4() {
        return 0;
    }

    public String getDataType() {
    	String dataType = getClass().getName();
    	
    	if (dataType.indexOf("Int")!=-1) {
    	    return "Int";
    	} else if (dataType.indexOf("Float")!=-1) {
    	    return "Float";
    	} else if (dataType.indexOf("Double")!=-1) {
    	    return "Double";
    	} else if (dataType.indexOf("Boolean")!=-1) {
    	    return "Boolean";
    	} else if (dataType.indexOf("String")!=-1) {
    	    return "String";
    	} 
    	
    	return null;
    }
}

