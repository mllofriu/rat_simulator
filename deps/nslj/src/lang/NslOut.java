/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.lang;

import nslj.src.nsls.struct.Executive;
import nslj.src.system.NslSystem;
import java.io.*;


public class NslOut extends PrintStream  {

    NslSystem system;
    
    public NslOut(OutputStream out) {
    	super(out);
    	system = Executive.system;
    }
    
    public boolean checkError() { return false; }
    
    public void close() {}

    public void flush() {}    

    protected void setError() {}
    
    public void write(byte[] buf, int off, int len) {}
    
    public void write(int b) {}
    
    public synchronized void print(boolean b) {
    	system.nslPrint(""+b);
    }
    
    public synchronized void print(char c) {
    	system.nslPrint(""+c);
    } 
    
    public synchronized void print(char[] s)  {
    	system.nslPrint(new String(s));
    }
    
    public synchronized void print(double d)  {
    	system.nslPrint(""+d);
    }
    
    public synchronized void print(float f)  {
    	system.nslPrint(""+f);
    }
    
    public synchronized void print(int i)  {
    	system.nslPrint(""+i);
    }
    
    public synchronized void print(long l)  {
    	system.nslPrint(""+l);
    }
    
    public synchronized void print(Object obj) {
    	Object temp = transform(obj);
    	system.nslPrint(temp.toString());
    } 
    
    public synchronized void print(String s)  {
    	system.nslPrint(s);
    }
    
    public synchronized void println(boolean b) {
    	system.nslPrintln(""+b);
    }
    
    public synchronized void println(char c) {
    	system.nslPrintln(""+c);
    } 
    
    public synchronized void println(char[] s)  {
    	system.nslPrintln(new String(s));
    }
    
    public synchronized void println(double d)  {
    	system.nslPrintln(""+d);
    }
    
    public synchronized void println(float f)  {
    	system.nslPrintln(""+f);
    }
    
    public synchronized void println(int i)  {
    	system.nslPrintln(""+i);
    }
    
    public synchronized void println(long l)  {
    	system.nslPrintln(""+l);
    }
    
    public synchronized void println(Object obj) {
    	system.nslPrintln(obj.toString());
    } 
    
    public synchronized void println(String s)  {
    	system.nslPrintln(s);
    }
    
    public synchronized NslData transform(Object obj) {
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


