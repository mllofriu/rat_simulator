/* SCCS  %W% --- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.awt.*;
import java.lang.*;
import java.awt.image.*;
public abstract class NslImageBuffer extends Image {
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
    	} finally {
    	    g.dispose();
    	}        
    }
    
    public Graphics nslGetGraphics() {
    	return getGraphics();
    }
}


