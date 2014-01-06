/* SCCS  %W% --- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.awt.*;
import nslj.src.lang.*;
import nslj.src.system.*;

public class NslVariableInfo {

    // Constants

    public static final int INT     = 1,
    			    FLOAT   = 2, 
    			    DOUBLE  = 3, 
    			    BOOLEAN = 4, 
    			    STRING  = 5;

    // Variables

    private NslData data;
    private String name;
  
    private int type;

    private boolean history;
    private boolean overwrite;

    private int miny, maxy, minx, maxx;
 
    private Color color = Color.black;
    protected String style;

    private int countDims;
    private int dimensions[];
    private int slicingDims[];  
    private String choiceDimensions;
  
    // Constructors
  
    public NslVariableInfo(NslData data, String name, int countDimensions) {
	this(data, name, DOUBLE, countDimensions);
    }

    public NslVariableInfo(NslData data, String name, int type, int countDimensions) {
	this.data = data;
	this.name = name;
	this.type = type;
        countDims = countDimensions;
 
 	history   = false;
 	overwrite = false;
 
	dimensions  = new int [4];
	slicingDims = new int [2];
	slicingDims[0] = slicingDims[1] = 1;
	choiceDimensions = new String();
 
	switch (countDims) {
	    case 0: dimensions[0] = 1;
	    case 1: dimensions[1] = 1;
	    case 2: dimensions[2] = 1;
	    case 3: dimensions[3] = 1;
        }
	switch (countDims) {
	    case 4: 
		setDimension(0,((NslNumeric4)data).getSize1());
		setDimension(1,((NslNumeric4)data).getSize2());
		setDimension(2,((NslNumeric4)data).getSize3());
		setDimension(3,((NslNumeric4)data).getSize4());
		break;   
	    case 3: 
		setDimension(0,((NslNumeric3)data).getSize1());
		setDimension(1,((NslNumeric3)data).getSize2());
		setDimension(2,((NslNumeric3)data).getSize3());
		break;   
	    case 2: 
		setDimension(0,((NslNumeric2)data).getSize1());
		setDimension(1,((NslNumeric2)data).getSize2());
		break;   
	    case 1: 
		setDimension(0,((NslNumeric1)data).getSize1());
		break;   
        }
    }
 
    // Methods

    public Object clone() {
	String name        = new String(this.name);


	


	NslVariableInfo vi = new NslVariableInfo(data, name, type, countDims);
	vi.history = history;
	vi.overwrite = overwrite;
	vi.color = color;
	vi.style = style;
	vi.slicingDims[0] = slicingDims[0];
	vi.slicingDims[1] = slicingDims[1];
  	vi.miny = miny;
	vi.maxy = maxy;
	vi.minx = minx;
	vi.maxx = maxx;
	vi.choiceDimensions = new String(choiceDimensions);

	return vi;
    }

    public void setDimension(int i, int dim) {
	if (i < countDims)
	   dimensions[i] = dim;
    }

    public void setHistory(boolean history) {
	this.history = history;
    }

    public void setOverwrite(boolean overwrite) {
	this.overwrite = overwrite;
    }
 
    public void setColor(Color color) {
	this.color = color;
    }
  
    public void setStyle(String s) {
	style = s;
    }

    public void setDimensionChoice(String dimChoice) { 
 	choiceDimensions = new String(dimChoice);
    }

    public void setSlicingPoint(int index, int point) { 
	try {
	    slicingDims[index] = point; 
	} catch (Exception e) { 
	    System.err.println("NslVariableInfo: [Error] Cannot set slicing point.");
	}
    }

    public int getDimension(int i) {
	if (i < countDims)
	    return dimensions[i]; 
    	else
	    return 1;
    }
  
    public boolean isHistoryOn() { return history; }

    public boolean isOverwriteOn() { return overwrite; }

    public NslData getNslVar() { return data; }

    public String nslGetName() { return name; }

    public int getCountDimensions() { return countDims; }

    public int getType() { return type; }
  
    public Color getColor() { return color; }

    public String getStyle() { return style; }

    public String toString() {
	return "NslVariableInfo: " + name + " " + countDims + " " + type;
    }

    public String getDimensionChoice() { return choiceDimensions; }

    public int getSlicingPoint(int index) { 
	try {
  	  return slicingDims[index]; 
 	} catch (Exception e) { 
	    System.err.println("NslVariableInfo: [Error] Cannot get slicing point.");
 	} finally {
 	    return 0;
 	}
 	
    }

}
