/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import java.lang.Math;
 
public class NslInputImageCanvas extends NslCanvas {

    private float y_max=100, y_min=0;

    private int data_x_size, data_y_size, x_dimension, y_dimension;
    private int last_data_pos, draw_time;
    private float [][][] data;
    private Color boxColor=Color.black;

    public NslInputImageCanvas() {
        super();
    }
    
    public NslInputImageCanvas(NslFrame nslDisplayFrame, NslCanvas pre) {
        super();
	set_min_max(pre.getC_min_y(),pre.getC_max_y());
    }

    public NslInputImageCanvas(NslFrame nslDisplayFrame,String full_name, 
				NslVariableInfo data_info) {
        super(nslDisplayFrame,full_name, data_info);
        
	/*addMouseMotionListener(new MouseMotionAdapter() {
    	    public void mouseMoved(MouseEvent e) {
    	    	int mx = e.getX(), my=e.getY();
    	    	System.out.println("("+mx+","+my+") j="+(float)(mx-drawX-1)/(float)dx+" i="+(float)(my-drawY-1)/(float)dy);
    	    }
    	});  */  
    	
    	addMouseListener( new MouseAdapter () {
    	    public void mouseClicked(MouseEvent e) {  	
    	    	int mx = e.getX(), my=e.getY();
    	   	float posX = (float)(my-drawY-1)/(float)dy;
    	    	float posY = (float)(mx-drawX-1)/(float)dx;

		NslVariable v = (NslVariable)variable_list.elementAt(0);
		
		int x = v.info.getDimension(0);            	
            	int y = v.info.getDimension(1);

    	    	if (posX > x || posX < 0 || posY > y || posY < 0) {
    	            return;
    	    	} else {
    	    	    data = ((NslVariable)variable_list.elementAt(0)).data;
    	    	     
    	    	    int i = (int)posX, j = (int)posY;

    	    	    if (data[i][j][draw_time]==y_min) {
    	    	    	data[i][j][draw_time]=y_max; 
    	    	        boxColor = ((NslVariable) variable_list.elementAt(0)).info.getColor();
    	    	    } else {
    	    	        data[i][j][draw_time]=y_min;                 	        
    	    	        boxColor = getBackground();
                        
    	    	    }
    	    	    
    	    	    Graphics g = getGraphics();
    	    	    g.setColor(boxColor);
                    g.fillRect(drawX+j*dx+1, drawY+i*dy+1, dx-1, dy-1);      
    	    	    g.dispose();
    	    	    
    	    	    switch (v.info.getCountDimensions()) {
    	    	      	case 1:
    	    	       	    ((NslNumeric1)v.info.getNslVar())._set(i,data[i][j][draw_time]);
    	    	       	    break;
    	    	     	case 2:
    	    	       	    ((NslNumeric2)v.info.getNslVar())._set(i,j,data[i][j][draw_time]);
    	    	       	    break;
    	    	    }
    	    	    
	    	}  
	    }
    	});
    	
    	
    }

    public NslInputImageCanvas(NslFrame nslDisplayFrame, String full_name, 
			       NslVariableInfo data_info,
			       double dmin, double dmax) {

        super(nslDisplayFrame,full_name, data_info);
        set_min_max(dmin,dmax);
    }

    public void  set_min_max(double dmin,double dmax) {
    	y_max =(float) dmax;
	y_min =(float) dmin;
        C_max_y = dmax;
        C_min_y = dmin;     
    }   
    
    public void init() {
        Graphics g = getGraphics();
       	Rectangle b = getBounds();
        g.clearRect(0, 0, b.width, b.height);
        paint(g);
        g.dispose();
    }
    
    public void update() {
        Graphics g = getGraphics(); 
        update(g);
        g.dispose();
        draw_time = 0; 
    }

    public void repaint() {
        Graphics g = getGraphics();
        paint(g);
        g.dispose();
        draw_time = 0; 
    }
    
    public void paint_partial(Graphics g) {
    	if (mouseAdapter!=null) {
    	    removeMouseListener(mouseAdapter);
    	    mouseAdapter=null;    	    
    	} 

        NslVariable v = (NslVariable)variable_list.elementAt(0);
        x_dimension = v.info.getDimension(0);
        y_dimension = v.info.getDimension(1);

        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
        data = ((NslVariable)variable_list.elementAt(0)).data;

	if ((NslSystem.init_run_char == 'B')||
	   (NslSystem.init_run_char == 'D')){ //before or during
	   //System.out.println("debug: NslAreaCanvas bad flag "+ NslSystem.init_run_char);
	   //NslSystem.init_run_char = 'B';
	   last_data_pos = 0;
	}

	draw_time = last_data_pos;
        
        for(int i=0; i<x_dimension; i++) {
            for(int j=0; j<y_dimension; j++) {
                Color color = getBackground();
                g.setColor(color);

                g.fillRect(drawX+j*dx+1, drawY+i*dy+1, dx-1, dy-1);
                
                for(int v_count=0; v_count<variable_list.size(); v_count++) {

                    data = ((NslVariable)variable_list.elementAt(v_count)).data;
                
                    if(data[i][j][draw_time]>= y_max) {
                        data_x_size = dx;
                        data_y_size = dy;
                    } else if(data[i][j][draw_time] <= y_min) {
                        data_x_size = dx; //(int)y_min;
                        data_y_size = dy; //(int)y_min;
                    } else {
			double scale=0;
			if (data[i][j][draw_time]>=0) 
				scale = data[i][j][draw_time]/y_max;
			else
				scale = data[i][j][draw_time]/y_min;

			data_x_size = (int)(dx*scale);
			data_y_size = (int)(dy*scale);
                    }
                    
                    boxColor = ((NslVariable) variable_list.elementAt(v_count)).info.getColor();
                    g.setColor(boxColor);
                    
                    if (data[i][j][draw_time] <= 0) {
                    
                  	g.fillRect(drawX+j*dx+ dx/2, 
                  	    drawY+i*dy+ dy/2, 
                  	    1, 1);
                  	    
    		    } else if (data[i][j][draw_time] > 0) {
    		    
                  	g.fillRect(drawX+j*dx+ (dx-data_x_size)/2,
                             drawY+i*dy+ (dy-data_y_size)/2,
                             data_x_size, data_y_size);
    		    } /*else {

			g.drawRect(drawX+j*dx+ (dx-data_x_size)/2+1,
                             drawY+i*dy+ (dy-data_y_size)/2+1,
                             data_x_size-2,data_y_size-2);
    		    }*/
                  
                    g.setPaintMode();
                }
            }
        }
        
        super.paint(g,1);
    }
    
    
    public void paint(Graphics g) {
        paint_partial(g);
    }
    
    public void collect() {
    
    	/*for(int i=0; i<x_dimension; i++) {
            for(int j=0; j<y_dimension; j++) {
 	    	switch (v.info.getCountDimensions()) {
    	    	    case 1:
    	    	        ((NslNumeric1)v.info.getNslVar()).set(i,data[i][j][draw_time]);
    	    	        break;
    	    	    case 2:
    	    	        ((NslNumeric2)v.info.getNslVar()).set(i,j,data[i][j][draw_time]);
    	    	        break;
    	    	}    	    	    		
    	    }
    	}*/

    	for(int i=0; i<variable_list.size(); i++)
        	((NslVariable)variable_list.elementAt(i)).collect();
       	
   	Graphics g=getGraphics();
    	paint_partial(g);
    	g.dispose();
    }    
    
   /* public void mousePressed(MouseEvent e) {
    	System.out.println("ouch!");
    	
    	int mx = e.getX(), my=e.getY();
    	float i = (float)(my-drawY-1)/(float)dy;
    	float j = (float)(mx-drawX-1)/(float)dx;

        int x = v.info.getDimension(0);
        int y = v.info.getDimension(1);

    	if (i > x || i < 0 || j > y || j < 0) {
    	    return;
    	} else {
    	    System.out.println("("+(int)i+","+(int)j+")");
	}
    }
    
    public void mouseDragged(MouseEvent evt) {
    }
    
    public void mouseReleased(MouseEvent evt) {
    }*/
}


