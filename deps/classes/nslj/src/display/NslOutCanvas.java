/* SCCS  @(#)NslOutCanvas.java	1.10---09/01/99--00:15:51 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.awt.*;
import java.util.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import nslj.src.nsls.struct.Executive;
import java.awt.event.*;
import java.lang.*;
import java.lang.reflect.*;

public class NslOutCanvas extends NslCanvas {
    
   
    protected NslSystem system;
    protected NslModule parentModule;

    private boolean canvasIsInitialized;

    public NslOutCanvas( String full_name, NslHierarchy parent) {
	super(null,full_name, null);

	system = Executive.system;
	parentModule = (NslModule)parent;
	
	nslCreateCanvas();	
    }
    
    public NslOutCanvas( String full_name, NslHierarchy parent,NslFrame f,
			    NslVariableInfo varInfo) {
	super(f,full_name, varInfo);

	system = Executive.system;
	parentModule = (NslModule)parent;

	//System.err.println("Create");
		
	nslCreateCanvas();	
    }
    
    public NslOutCanvas(String full_name, 
			    NslVariableInfo varInfo) {
	super(null,full_name, varInfo);

	system = Executive.system;
	
	nslCreateCanvas();
    }

    public NslOutCanvas(NslFrame frame, String name, NslVariableInfo vi) {
	super(frame, name, vi);

	system = Executive.system;
    }
    
    public void makeInst(String full_name, NslHierarchy parent) {
    }

    public NslHierarchy nslGetParentModule() {
    	return parentModule;
    }
    
    public void nslSetColor(NslColor color) {
	Graphics g = getGraphics();
    	try {
    	    g.setColor(color);
    	} finally {
    	    g.dispose();
    	}    
    }
   			    
    public void nslSetColor(String color) {
	Graphics g = getGraphics();
    	try {
    	    g.setColor(NslColor.getColor(color));
    	} finally {
    	    g.dispose();
    	}  
    }
    
    public void nslDrawLine(int x, int y, int w, int h, String color) {
	Graphics g = getGraphics();
    	try {
    	    g.setColor(NslColor.getColor(color));
    	    g.drawLine(x,y,w,h);
    	} finally {
    	    g.dispose();
    	}   
    }
    
    public void nslDrawLine(int x, int y, int w, int h) {
	Graphics g = getGraphics();
    	try {
    	    g.drawLine(x,y,w,h);
    	} finally {
    	    g.dispose();
    	}   
    }
      
    public void nslFillRect(int x, int y, int w, int h, NslColor color) {
	Graphics g = getGraphics();
    	try {
    	    g.setColor(color);
    	    g.fillRect(x,y,w,h);
    	} finally {
    	    g.dispose();
    	}    
    }    

    public void nslFillRect(int x, int y, int w, int h, String color) {
	Graphics g = getGraphics();
    	try {
    	    g.setColor(NslColor.getColor(color));
    	    g.fillRect(x,y,w,h);
    	} finally {
    	    g.dispose();
    	}    
    }
    
    public void nslFillRect(int x, int y, int w, int h) {
	Graphics g = getGraphics();
    	try {
    	    g.fillRect(x,y,w,h);
    	} finally {
    	    g.dispose();
    	} 
    }

    public void nslDrawString(String s, int x, int y, String color) {
	Graphics g = getGraphics();
    	try {
    	    g.setColor(NslColor.getColor(color));
    	    g.drawString(s,x,y);
    	} finally {
    	    g.dispose();
    	}         
    }
    
    public void nslDrawString(String s, int x, int y) {
	Graphics g = getGraphics();
    	try {
    	    g.drawString(s,x,y);
    	}  finally {
    	    g.dispose();
    	}       
    }
    
    public int nslGetWidth() {
    	Rectangle border = getBounds();
    	return border.width;
    }

    public int nslGetHeight() {
    	Rectangle border = getBounds();
    	return border.height;
    }
    
    public void paint(Graphics g) {
	if (canvasIsInitialized)
    	    nslRefresh();
    }
    
    public void nslUpdateDisplay() {
        Graphics g = getGraphics();
        try {
            paint(g); 
    	} finally {
    	    g.dispose();
    	}        
    }
        
    public void nslClearDisplay() {
	Graphics g = getGraphics();
       	Rectangle b = getBounds();
       	try { 
            g.clearRect(0, 0, b.width, b.height); 
            paint(g);
     	} finally {
    	    g.dispose();
    	}        
              
    }
    
    public Image nslCreateImageBuffer(int width, int height) {
        return createImage(width, height); 
    }
    
    public void nslDrawImageBuffer(Image image, int left, int top) {
	Graphics g = getGraphics();
        try { 
            g.drawImage(image, left, top, this); 
     	} finally {
    	    g.dispose();
    	}           	
    }  

    public void nslDrawImageBuffer(Image image, Graphics graph, int left, int top) {
        graph.drawImage(image, left, top, this); 
    }      

    public void collect() {
    	if (NslSystem.init_run_char=='A')
    	    nslCollect();
    }
    		
    public void initEpochCanvas() {
	//System.err.println("INIT");
   	nslInitEpochCanvas();
	canvasIsInitialized=true;
    }

    public void initCanvas() {
   	nslInitCanvas();
	canvasIsInitialized=true;
    }

    public void nslCollect() {
    }
    
    public void nslRefresh() {
    }
    
    public void nslCreateCanvas() {
    }
    
    public void nslInitCanvas() {
    }

    public void nslInitEpochCanvas() {
    }
    
    public void processEvent(AWTEvent event) {
    	/*System.out.println("Event: "+event.toString());
    	System.out.println("Param: "+event.paramString());	
    	System.out.println("Source: "+event.getSource());*/
    }

    public void callFromConstructorBottom() {
    }

    public void callFromConstructorTop() {
    }
}
