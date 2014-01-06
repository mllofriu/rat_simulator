/*  SCCS - %W% - %G% - %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

// NslFile.java
////////////////////////////////////////////////////////////////////////////////

package nslj.src.lang;

import java.io.*;
import java.util.*;

import nslj.src.nsls.struct.*;
import tcl.lang.*;

public class NslTextFile {

    private String name;
    private char access;
    
    private FileReader fileIn;
    private FileWriter fileOut;
                
    private BufferedReader  brd;
    private BufferedWriter  bwr;
    
    private PrintWriter pwr;
        
    public NslTextFile(String moduleName, NslHierarchy Parent, String name) {
	this.name   = name;
    }
        
    public NslTextFile(String name) {
	this.name   = name;
    }
    
    public void open(char access) {
	this.access = access;
	
    	switch (access) {
    	    case 'A':
	    case 'W': 
	        try {
    		    fileOut = new FileWriter(name, access=='A');
    		    bwr = new BufferedWriter(fileOut);
    		    pwr = new PrintWriter(bwr);
    		} catch(FileNotFoundException e1) {
    		    System.err.println("NslFile: Can't create or open for writing file "+name);
    		} catch (IOException e2) {
    	    	    System.err.println("NslFile: Can't read line in file "+name);
    		}
    		
    		break;
  	    case 'R': 
	        try {
    		    fileIn = new FileReader(name);
    		    brd = new BufferedReader(fileIn);
    		} catch(FileNotFoundException e) {
    		    System.err.println("NslFile: Can't open for reading file "+name);
    		}
    		break;   		
    	}
    }

    /* Putf (Only Nsl types) */
    
    public void putf(NslData data) {
    	try {
    	    StringBuffer strbuf = new StringBuffer();
    	    
    	    strbuf.append(data.nslGetParent().nslGetRealName()); //  Object name
    	    strbuf.append("."+data.nslGetName()+" ");
    	    strbuf.append(data.getDataType()+" ");  //  Class name
    	    strbuf.append(data.getDimensions()+" ");	   //  Dimension    	    
    	    
    	    strbuf.append("{ ");                           //  Sizes
    	    int dim[] = data.getSizes();
    	    for (int i=0;i<data.getDimensions();i++) {
    	    	strbuf.append(dim[i]+" ");
    	    }
    	    strbuf.append("} ");

    	    strbuf.append("{ ");                           //  Data
    	    strbuf.append(data.toString());
    	    strbuf.append("} ");
    	        	    
    	    pwr.println(strbuf.toString());
    	    
    	    if (pwr.checkError()) {
    	    	System.err.println("NslFile: An error happened while writing in file: "+name);
    	    }
    	} catch (Exception e) {
    	    System.err.println("NslFile: Can't write line in file "+name);
    	}	
    }
    
    /* Getf (Only Nsl types) */
    
    /*public NslData getf() {
    	try {
    	    StringBuffer strbuf = new StringBuffer();
    	    
    	    strbuf.append(data.nslGetParent().nslGetRealName()); //  Object name
    	    strbuf.append("."+data.nslGetName()+" ");
    	    strbuf.append(data.getDataType()+" ");  //  Class name
    	    strbuf.append(data.getDimensions()+" ");	   //  Dimension    	    
    	    
    	    strbuf.append("{ ");                           //  Sizes
    	    int dim[] = data.getSizes();
    	    for (int i=0;i<data.getDimensions();i++) {
    	    	strbuf.append(dim[i]+" ");
    	    }
    	    strbuf.append("} ");

    	    strbuf.append("{ ");                           //  Data
    	    strbuf.append(data.toString());
    	    strbuf.append("} ");
    	        	    
    	    pwr.println(strbuf.toString());
    	    
    	    if (pwr.checkError()) {
    	    	System.err.println("NslFile: An error happened while writing in file: "+name);
    	    }
    	} catch (Exception e) {
    	    System.err.println("NslFile: Can't write line in file "+name);
    	}	
    }*/

    /* Puts */

    public void puts(int value) {
    	try {
    	    pwr.println(value);    	    
    	    if (pwr.checkError()) {
    	    	System.err.println("NslFile: An error happened while writing in file: "+name);
    	    }
    	} catch (Exception e) {
    	    System.err.println("NslFile: Can't write line in file "+name);
    	} 	
    }

    public void puts(float value) {
    	try {
    	    pwr.println(value);    	    
    	    if (pwr.checkError()) {
    	    	System.err.println("NslFile: An error happened while writing in file: "+name);
    	    }
    	} catch (Exception e) {
    	    System.err.println("NslFile: Can't write line in file "+name);
    	} 	
    }

    public void puts(double value) {
    	try {
    	    pwr.println(value);    	    
    	    if (pwr.checkError()) {
    	    	System.err.println("NslFile: An error happened while writing in file: "+name);
    	    }
    	} catch (Exception e) {
    	    System.err.println("NslFile: Can't write line in file "+name);
    	} 	
    }
    
    public void puts(boolean value) {
    	try {
    	    pwr.println(value);    	    
    	    if (pwr.checkError()) {
    	    	System.err.println("NslFile: An error happened while writing in file: "+name);
    	    }
    	} catch (Exception e) {
    	    System.err.println("NslFile: Can't write line in file "+name);
    	} 	
    }
   
    public void puts(Object obj) {
    	try {
    	    Object temp=transform(obj);
    	    pwr.println(temp.toString());    	    
    	    if (pwr.checkError()) {
    	    	System.err.println("NslFile: An error happened while writing in file: "+name);
    	    }
    	} catch (Exception e) {
    	    System.err.println("NslFile: Can't write line in file "+name);
    	} 	
    }
   
    public void puts(NslData data) {
    	try {
    	    pwr.println(data.toString());    	    
    	    if (pwr.checkError()) {
    	    	System.err.println("NslFile: An error happened while writing in file: "+name);
    	    }
    	} catch (Exception e) {
    	    System.err.println("NslFile: Can't write line in file "+name);
    	} 	
    }
    
    /* Gets */
    
    public int getsInt() {
    	try { 
    	    String temp = brd.readLine();
    	    StringTokenizer st = new StringTokenizer(temp);    	    
    	    return Integer.parseInt(st.nextToken());
    	} catch (IOException e) {
    	    System.err.println("NslFile: Can't read line in file "+name);
    	} catch (Exception e2) {
     	    System.err.println("NslFile: Can't read line in file "+name);
     	    e2.printStackTrace();
   	}
   	return 0;
    }
    
   /* public float getsFloat() {
    }
    
    public double getsDouble() {
    }
    
    public boolean getsBoolean() {
    }*/
    
    public void gets(NslData data) {
    	try { 
    	    String temp = brd.readLine();
    	    String command = "nsl set " + data.nslGetParent().nslGetRealName() +
    	    	"." + data.nslGetName() + " { " + temp + " }";
    	    //System.err.println(command);
    	    Executive.interp.eval(command);
    	} catch (IOException e) {
    	    System.err.println("NslFile: Can't read line in file "+name);
    	} catch (TclException e) {
    	    System.err.println("NslFile: Asignation error reading "+name);
        }
    }
           
    public void flush() {
    	switch (access) {
    	    case 'A':
	    case 'W':
	        try {
    		    fileOut.flush();
    		} catch (IOException e) {
    	    	    System.err.println("NslFile: Can't close file "+name);
    		}
    		break;
    	}
    }
    
    public void close() {
    	switch (access) {
    	    case 'A':
	    case 'W':
	        try {
    		    fileOut.close();
    		} catch (IOException e) {
    	    	    System.err.println("NslFile: Can't close file "+name);
    		}
    		break;
  	    case 'R': 
	        try {
    		    fileIn.close();
    		} catch (IOException e) {
    	    	    System.err.println("NslFile: Can't close file "+name);
    		}
    	}
    }
    
    public NslData transform(Object obj) {
  	Object temp=obj;
    	if (obj instanceof double[]) {
	    return new NslDouble1((double[])obj);   	    	
	} else if (obj instanceof double[][]) {
	    return new NslDouble2((double[][])obj);
	} else if (obj instanceof double[][][]) {
	    return new NslDouble3((double[][][])obj);
    	} else if (obj instanceof double[][][][]) {
	    return new NslDouble4((double[][][][])obj);
    	} else if (obj instanceof float[]) {
	    return new NslFloat1((float[])obj);   	    	
	} else if (obj instanceof float[][]) {
	    return new NslFloat2((float[][])obj);
	} else if (obj instanceof float[][][]) {
	    return new NslFloat3((float[][][])obj);
    	} else if (obj instanceof float[][][][]) {
	    return new NslFloat4((float[][][][])obj);
    	} else if (obj instanceof boolean[]) {
	    return new NslBoolean1((boolean[])obj);   	    	
	} else if (obj instanceof boolean[][]) {
	    return new NslBoolean2((boolean[][])obj);
	} else if (obj instanceof int[]) {
	    return new NslInt1((int[])obj);   	    	
	} else if (obj instanceof int[][]) {
	    return new NslInt2((int[][])obj);
	} else if (obj instanceof int[][][]) {
	    return new NslInt3((int[][][])obj);
    	} else if (obj instanceof int[][][][]) {
	    return new NslInt4((int[][][][])obj);
    	}
    	
    	return (NslData)obj;
    }
	
}


