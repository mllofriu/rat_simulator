/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.awt.*;
import java.util.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import java.awt.event.*;
import java.lang.*;
import java.lang.reflect.*;

public class NslButton extends Button {
    private String name;
    private NslModule module;
    
    public NslButton(String name, String label, NslModule module) {
    	super(label);    	

    	this.name = name;
    	this.module = module;
    	
    	setActionCommand(name);
    	addActionListener(new NslButtonListener());
    	
    	System.out.println("Creating button "+name+" for "+module.nslGetName());
    }
    
    class NslButtonListener implements ActionListener {
        boolean found=true;
    	public void actionPerformed(ActionEvent event) {
    	    if (found) {

	    	try {
		    String n = name+"Pushed";
	    	    Method m = module.getClass().getMethod(n,null);
		    m.invoke(module,null);
	    	} catch (Exception ex) {
		    System.out.println("Error: Couldn't find method "+name+"Pushed() {}");	
		    found = false;    
	    	}

	    } else {
		System.out.println("Error: Not executing the method");	
	    }
    	}
    }
}

