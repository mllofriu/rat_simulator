/* SCCS  @(#)NslNumericEditor.java	1.6---09/20/99--21:19:01 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import nslj.src.lang.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class NslNumericEditor extends Panel {

    NslGrid grid;
    NslVariableInfo vi;
    boolean firstTime = true;
    
    public NslNumericEditor(NslFrame frame, String full_name, 
				  NslVariableInfo data_info) {
	super();
	
	vi = data_info;
		
	setLayout(new BorderLayout());	

	int x,y;
	switch (vi.getCountDimensions()) {
	    case 0:
		grid = new NslGrid(frame, 1, 1);    	
	    	break ;

	    case 1:	    
	    	x = vi.getDimension(0);
		grid = new NslGrid(frame, 1, x);	    		    	
	    	break ;

	    case 2:	    	        
	    	x = vi.getDimension(0);
	    	y = vi.getDimension(1);
	        grid = new NslGrid(frame, x, y);
	    	break;
	}

	Label label = new Label(full_name,Label.CENTER);
	Button button = new Button("Apply changes in "+full_name);
	button.addActionListener(new updateActionListener());

	writeValues();

	add("North",label);
	add("Center",grid);
	add("South",button);
    }
    
    public void readValues() {  	
  	int x,y;
	double temp;
  	switch (vi.getCountDimensions()) {
 	    case 0:

		temp = Double.parseDouble(grid.getComponentValue(1,1));
		((NslNumeric0)vi.getNslVar())._set(temp);
		break;

	    case 1:
	    	x = vi.getDimension(0);
	    	
	    	for (int i=0;i<x;i++) {
		    temp = Double.parseDouble(grid.getComponentValue(1,i+1));
		    ((NslNumeric1)vi.getNslVar())._set(i,temp);		    
		}
		
	    	break;
	    	
	    case 2:
	    
	        x = vi.getDimension(0);
	    	y = vi.getDimension(1);
	    	
	    	for (int i=0;i<x;i++) {
	    	    for (int j=0;j<y;j++) {
		        temp = Double.parseDouble(grid.getComponentValue(i+1,j+1));
		        ((NslNumeric2)vi.getNslVar())._set(i,j,temp);
	            }
	   	}

	    	break;
	}
    }  
     
    public void writeValues() {  	
  	int x,y;
  	switch (vi.getCountDimensions()) {
 	    case 0:
		grid.setValue(1,1,""+((NslNumeric0)vi.getNslVar()).getdouble());
		break;

	    case 1:
	    	x = vi.getDimension(0);
	    	
	    	for (int i=0;i<x;i++) {
		    grid.setValue(1,i+1,""+((NslNumeric1)vi.getNslVar()).getdouble(i));
		}
		
	    	break;
	    	
	    case 2:
	    
	        x = vi.getDimension(0);
	    	y = vi.getDimension(1);
	    	
	    	for (int i=0;i<x;i++) {
	    	    for (int j=0;j<y;j++) {
	    	        grid.setValue(i+1,j+1,""+((NslNumeric2)vi.getNslVar()).getdouble(i,j));
	            }
	   	}

	    	break;
	}
    }
    
    public void initEpochCanvas() {
	//System.out.println("InitEpoch Canvas");
	writeValues();
    }

    public void initCanvas() {
	//System.out.println("Init Canvas");
	writeValues();
    }
    
    public void collect() {
	//System.out.println("Collecting");
	writeValues();
    }

    private class updateActionListener implements ActionListener {
	public void actionPerformed(ActionEvent evt) {
	    grid.cleanCell();
	    readValues();
	}
    }
}


