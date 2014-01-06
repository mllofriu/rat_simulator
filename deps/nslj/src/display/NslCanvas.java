/* SCCS  %W% --- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslCanvas.java,v $
// Revision 1.5  1997/11/06 03:15:01  erhan
// nsl3.0.b
//
// Revision 1.4  1997/05/09 22:30:24  danjie
// add some comments and Log
//
//--------------------------------------

package nslj.src.display;

import java.awt.*;
import java.util.*;
import java.lang.*;
import java.awt.event.*;
import java.lang.reflect.*;

import nslj.src.lang.*;
import nslj.src.system.*;

/**
 * The Nsl Canvas
 *
 * Implement the general functions of nslj  canvases
 *
 * @version 1.0
 * @author Danjie Pan
 */


public class NslCanvas extends Canvas {

    // Variables

    public NslVariable var_in_canvas;

    public String canvas_name;
    public String windowName;

    public NslFrame nslDisplayFrame = null;
    public MouseAdapter mouseAdapter;

    protected  Vector variable_list;

    protected int drawX, drawY, drawW, drawH, dx, dy;
    protected int x_dimension, y_dimension;
    protected int max_data_pos;

    protected String canvas_type;

    protected double C_min_y=0.0 ,C_max_y=1.0; // for transfering scale between different plots
    protected double C_min_x=0   ,C_max_x=1;   // not used now

    private String name;
    private Color back_ground_color;
    private int mouseX0, mouseY0, mouseXcurrent, mouseYcurrent;
    private int mouseX1, mouseY1;
    private Stack zoomStack;

    private int colorIndex = 1;

    protected NslData nslCanvasData;

    // Constructors
 
    public NslCanvas() {  
       C_min_y=0; 
       C_max_y=1;
 
    }
    
    public NslCanvas(NslFrame nslDisplayFrame, NslCanvas pre) {
    }
    
    public NslCanvas(NslFrame nslDisplayFrame,double min,double max){   
        this.nslDisplayFrame = nslDisplayFrame;
	set_min_max(min,max);
    }
   
    public NslCanvas(NslFrame frame, String fullName,
		     NslVariableInfo varInfo) {
		     
	canvas_type = "Normal";
	canvas_name = fullName;
        name        = canvas_name.substring(canvas_name.indexOf(".",1) + 1);

        nslDisplayFrame = frame;
 

	variable_list = new Vector();
	  
        nslCanvasData= varInfo.getNslVar();	
 
	var_in_canvas = new NslVariable(nslDisplayFrame, this, varInfo);
        variable_list.addElement(var_in_canvas);
        
        mouseX0=0;
        mouseY0=0; 
        mouseX1=0;
        mouseY1=0;
 
    } // end constructor


   

   // Methods

    public String nslGetName() {
	return name;
    }
    
    // Event handeler

    public void mousePressed(MouseEvent evt) {

	if(canvas_type.equals("Zoom"))
{
            Graphics g = getGraphics();
            g.setXORMode(getBackground());
            
            g.drawRect(mouseX0, mouseY0, (mouseX1-mouseX0), (mouseY1-mouseY0));

            mouseX0 = evt.getX();
	    mouseY0 = evt.getY();
	    
	    mouseXcurrent = evt.getX();
	    mouseYcurrent = evt.getY();
	}
    }

    public void mouseDragged(MouseEvent evt)
{
    
	if (canvas_type.equals("Zoom"))
{
	
            Graphics g = getGraphics();
            g.setXORMode(getBackground());

	    // for flexible area selection - nikunj
	    int xLeft, xRight, yTop, yBottom;
	    xLeft = (mouseX0 < mouseXcurrent) ? mouseX0 : mouseXcurrent;
	    xRight = (mouseX0 > mouseXcurrent) ? mouseX0 : mouseXcurrent;
	    yTop = (mouseY0 < mouseYcurrent) ? mouseY0 : mouseYcurrent;
	    yBottom = (mouseY0 > mouseYcurrent) ? mouseY0 : mouseYcurrent;
	    g.drawRect(xLeft, yTop, (xRight - xLeft), (yBottom - yTop));

	    mouseXcurrent = evt.getX();
	    mouseYcurrent = evt.getY();

	    // for flexible area selection - nikunj
            g.setColor(Color.black);
	    xLeft = (mouseX0 < mouseXcurrent) ? mouseX0 : mouseXcurrent;
	    xRight = (mouseX0 > mouseXcurrent) ? mouseX0 : mouseXcurrent;
	    yTop = (mouseY0 < mouseYcurrent) ? mouseY0 : mouseYcurrent;
	    yBottom = (mouseY0 > mouseYcurrent) ? mouseY0 : mouseYcurrent;
	    g.drawRect(xLeft, yTop, (xRight - xLeft), (yBottom - yTop));

            g.dispose();
	    
	}
    }

    public void mouseReleased(MouseEvent evt)
{
	if (canvas_type.equals("Zoom"))
{
	    mouseX1 = evt.getX();
	    mouseY1 = evt.getY();
	    
	    // for flexible area selection - nikunj
	    int xLeft, xRight, yTop, yBottom;
	    
	    xLeft = (mouseX0 < mouseX1) ? mouseX0 : mouseX1;
	    xRight = (mouseX0 > mouseX1) ? mouseX0 : mouseX1;
	    yTop = (mouseY0 < mouseY1) ? mouseY0 : mouseY1;
	    yBottom = (mouseY0 > mouseY1) ? mouseY0 : mouseY1;
	    
	    mouseX0 = xLeft;
	    mouseX1 = xRight;
	    mouseY0 = yTop;
	    mouseY1 = yBottom;
	}

    }

    public void  set_min_max(double dmin, double dmax) { 
	C_min_y = dmin; 
	C_max_y = dmax;
    }

    public void initCanvas()
{
       // must be implemented in each canvas type
    }
    
    public void initEpochCanvas()
{
       // must be implemented in each canvas type
    }
    
    public void init()
{
       // must be implemented in each canvas type
    }
    
    public void refresh()
{
       // ERH: must be implemented in each canvas type
    }
 
    public void update()
{
       Graphics g = getGraphics(); 
       update(g);
    }

    public void paint(Graphics g, int val)
{
	Rectangle b = getBounds();

	if(canvas_type.equals("Normal"))
{
	    set_draw_size(b); 
	} else if(canvas_type.equals("Zoom"))
{
	    double[] lastVCDimen =(double[]) zoomStack.peek();
	    
	    double x0  = lastVCDimen[0];
	    double y0  = lastVCDimen[1];
	    double dw  = lastVCDimen[2];
	    double dh  = lastVCDimen[3];
	    double mx0 = lastVCDimen[4];
	    double my0 = lastVCDimen[5];
	    double mdw = lastVCDimen[6];
	    double mdh = lastVCDimen[7];

	    mouseX0 = (int)(mx0*(double)b.width);
	    mouseY0 = (int)(my0*(double)b.height);
	    mouseX1 = (int)((mx0+mdw)*(double)b.width);
	    mouseY1 = (int)((my0+mdh)*(double)b.height);

	    g.setColor(Color.black);

	    g.drawRect(mouseX0, mouseY0, (mouseX1-mouseX0), (mouseY1-mouseY0));     

     
	    zoom_draw_size(x0, y0, dw, dh);
	}

	if (val == 99)
	    draw_rectangle(g);
	else
if (val > 0) 
            draw_box(g);

        // draw title here

	g.setColor(Color.black);
	
	if (canvas_type.equals("Normal"))
	    g.drawString(name,10,20);
    }

    public void draw_rectangle(Graphics g)
{
        // draw box

        g.setColor(Color.lightGray);
        g.drawRect(drawX,drawY,
		   dx * var_in_canvas.info.getDimension(1),
		   dy * var_in_canvas.info.getDimension(0));

    }

    public void draw_box(Graphics g)
{
        // draw box
        g.setColor(Color.lightGray);
        g.drawRect(drawX,drawY,
		   dx * var_in_canvas.info.getDimension(1),
		   dy * var_in_canvas.info.getDimension(0));
        for(int i=0; i<var_in_canvas.info.getDimension(0)-1; i++)
        g.drawLine(drawX, drawY+dy*(i+1), 
		   drawX+dx * var_in_canvas.info.getDimension(1),
		   drawY+dy * (i+1));
        for(int i=0; i<var_in_canvas.info.getDimension(1)-1;i++)
        g.drawLine(drawX+dx*(i+1), drawY, 
		   drawX+dx*(i+1),
		   drawY+dy * var_in_canvas.info.getDimension(0));
    }

    public void nslAddVariable(NslData var) {
        var_in_canvas=(NslVariable)variable_list.elementAt(0);
        
	NslVariableInfo info = nslDisplayFrame.getVarInfo(var);
	info.setHistory(var_in_canvas.info.isHistoryOn());
	
	info.setColor(NslColor.getColorByIndex(colorIndex++));
	variable_list.addElement(new NslVariable(nslDisplayFrame,this,info));
    }

    public void add_variable(String full_name, NslVariableInfo info)
{
	var_in_canvas = new NslVariable(nslDisplayFrame,this,info);
	variable_list.addElement(var_in_canvas);
    }
    
    public void delete_variable(String full_name)
{
    }

    public NslVariable get_display_variable()
{
         return (NslVariable)variable_list.elementAt(0);
    }

    public void Print()
{
    
	PrintJob pjob = getToolkit().getPrintJob(nslDisplayFrame,"Current Canvas", null);
	if (pjob != null) {
	    Graphics pg = pjob.getGraphics(); 
	    if (pg != null) {
		printAll(pg);
		pg.dispose(); // flush page
	    }
	    pjob.end();
	}
    }

    // for debugging only - nikunj
    public String toString() {
         return super.toString() + ": " + canvas_name 
         	+ "{" + C_min_x + "," + C_min_y + "} - {" 
         	+C_max_x + "," + C_max_y + "}";
    }
  
    /**
     * Function to make a copy of canvas which is needed
     * when switch one type of canvas to another
     */

    public NslCanvas copy(String graph_type_name)
{
	NslCanvas ndc = null;
	
	try {
	    String frameType  = "nslj.src.display.NslFrame";
	    String canvasType = "nslj.src.display.Nsl" + graph_type_name + "Canvas";
	    
	    Class frameClass  = Class.forName(frameType);
	    Class canvasClass = Class.forName(canvasType);

	    Class typeList[] = new Class[2];
	    typeList[0] = frameClass;
	    typeList[1] = canvasClass.getSuperclass();
	   
	    Constructor copyConstructor = canvasClass.getConstructor(typeList);
	    Object param[] = new Object[2];
	    if (nslDisplayFrame==null) {
		System.err.println("NslCanvas: [Error] NslFrame is null");
	    }
	    
	    param[0] = nslDisplayFrame;
	    param[1] = this;
	    ndc = (NslCanvas) copyConstructor.newInstance(param);
	} catch (Exception e) {
	    System.err.println("NslCanvas: [Error] don't know this type "+graph_type_name);
	    return null;
	}
        
	ndc.variable_list = variable_list;
	ndc.canvas_name = canvas_name;
	ndc.name = new String(this.name);
	ndc.var_in_canvas = (NslVariable)var_in_canvas.clone(nslDisplayFrame,this);
	ndc.x_dimension = x_dimension;
	ndc.y_dimension = y_dimension;
	ndc.max_data_pos = max_data_pos;
	ndc.drawX = drawX;
	ndc.drawY = drawY;
	ndc.drawW = drawW;
	ndc.drawH = drawH;
	ndc.dx = dx;
	ndc.dy = dy;
	ndc.setBounds(getBounds());
	ndc.setBackground(Color.white);
	 
	set_min_max(this.getC_min_y(),this.getC_max_y()); //xfer min-max values


        return ndc;
    }

    public void set_background(String b)
{
    }

    public void set_draw_size(Rectangle b)
{
        
        var_in_canvas=(NslVariable)variable_list.elementAt(0);
        
	drawY = canvas_type.equals("Normal") ? 30 : 10;
	drawX = 10;
        drawW =b.width-20;
        drawH =b.height-50;
        dx = drawW/var_in_canvas.info.getDimension(1);
        dy = drawH/var_in_canvas.info.getDimension(0);
    }

    public void set_draw_size()
{
	set_draw_size(getBounds());
    }
 

    // Zoom function

    /**
     * implement zoom in function
     */

    public void zoom_in()
{
	Rectangle b = getBounds();

        // Normalize mouse position using current zoom canvas size

        double mx0 = (double)mouseX0/(double)b.width;
        double my0 = (double)mouseY0/(double)b.height;
        double mx1 = (double)mouseX1/(double)b.width;
        double my1 = (double)mouseY1/(double)b.height;
        double mdw = mx1-mx0;
        double mdh = my1-my0;

	// check for invalid zoom area - zero width or height
	double ratio = 1;
	if ( mdw == 0 || mdh == 0) {
	    System.err.println("NslCanvas: [Error] Invalid zoom area");
	    return;
	}
	
	double ratioX=0.9/(mx1-mx0);
	double ratioY=0.9/(my1-my0);

	if(ratioX<=ratioY)
	    ratio = ratioX;
	else
	    ratio = ratioY;
        double centerX = (mx1+mx0)/2.0;
        double centerY = (my1+my0)/2.0; 


        // Calculate  new virtual canvas position and size using new ratio

        double[] preVCDimen = (double[])zoomStack.pop();

        double x0_pre = preVCDimen[0];
        double y0_pre = preVCDimen[1];      
        double dw_pre = preVCDimen[2];
        double dh_pre = preVCDimen[3];
    
        double x0_curr = (x0_pre-centerX)*ratio+0.5;
        double y0_curr = (y0_pre-centerY)*ratio+0.5;
        double dw_curr = dw_pre*ratio;
        double dh_curr = dh_pre*ratio;
  
        // Calculate zoom box position and size

        double mx0_curr = (mx0-centerX)*ratio+0.5;
        double my0_curr = (my0-centerY)*ratio+0.5;
        double mdw_curr = mdw*ratio;
        double mdh_curr = mdh*ratio;

        // Save current virtual canvas parameters and zoom box parameters in zoomStack

        double[] currVCDimen = new double[8];
        currVCDimen[0] = x0_curr;
        currVCDimen[1] = y0_curr;
        currVCDimen[2] = dw_curr;
        currVCDimen[3] = dh_curr;
        currVCDimen[4] = mx0_curr;
        currVCDimen[5] = my0_curr;
        currVCDimen[6] = mdw_curr;
        currVCDimen[7] = mdh_curr;

        preVCDimen[4] = mx0;
        preVCDimen[5] = my0;
        preVCDimen[6] = mdw;
        preVCDimen[7] = mdh;

        zoomStack.push(preVCDimen);

        zoomStack.push(currVCDimen);

        zoom_draw_size(x0_curr, y0_curr, dw_curr, dh_curr);

        // redraw canvas and zoom box

        update();
    }


    /**
     * Implement zoom out function
     */

    public void zoom_out()
{
	if(zoomStack.size()>1)
{

            double[] lastVCDimen = (double[])zoomStack.pop(); //(double[])zoomStack.peek();

            double x0  = lastVCDimen[0];
            double y0  = lastVCDimen[1];      
            double dw  = lastVCDimen[2];
            double dh  = lastVCDimen[3];
            double mx0 = lastVCDimen[4];
            double my0 = lastVCDimen[5];
            double mdw = lastVCDimen[6];
            double mdh = lastVCDimen[7];

            zoom_draw_size(x0, y0, dw, dh);
            update();
	}
    }

    /**
     * To calculate the parameters for paint() accoding to virtual canvas parameters
     * and actrual zoom canvas size.
     */

    public void zoom_draw_size(double x0, double y0, double dw, double dh)
{
        Rectangle b = getBounds();

        var_in_canvas=(NslVariable)variable_list.elementAt(0);
        
        drawX = (int)((x0+dw/20.0)*(double)b.width);
        drawY = (int)((y0+dh/10.0)*(double)b.height);
        drawW = (int)((dw-2.0*dw/20.0)*(double)b.width);
        drawH = (int)((dh-3.0*dh/20.0)*(double)b.height);

        dx = (int)((dw-2.0*dw/20.0)*
		   (double)b.width/(double)var_in_canvas.info.getDimension(1));
        dy = (int)((dh-3.0*dh/20.0)*
		   (double)b.height/(double)var_in_canvas.info.getDimension(0));       
    }

    public void zoom_draw_size()
{
        double[] lastVCDimen = (double[]) zoomStack.peek();
        
        double x0 = lastVCDimen[0];
        double y0 = lastVCDimen[1];
        double dw = lastVCDimen[2];
        double dh = lastVCDimen[3];

        zoom_draw_size(x0, y0, dw, dh);
    }

    // Change the canvas type and create the zoomStack accordingly

    public void set_canvas_type(String ct)
{
	canvas_type = ct;
	
	if (canvas_type.equals("Zoom")) {
 	    zoomStack = new Stack();
            double[] initArray = new double[8];

	    initArray[0] = 0.0;   // virtual canvas left-up corner x0(0)
	    initArray[1] = 0.0;   // virtual canvas left-up corner y0(0)
	    initArray[2] = 1.0;   // virtual canvas width (normalized) 
	    initArray[3] = 1.0;   // virtual canvas height (normalized)
	    initArray[4] = 0.0;   // zoom box mx0(0)
	    initArray[5] = 0.0;   // zoom box my0(0)
	    initArray[6] = 1.0;   // zoom box width (normalized)
	    initArray[7] = 1.0;   // zoom box height (normalized)

 	    zoomStack.push(initArray); 
	}
    }

    public Vector get_variable_list(){
        return variable_list;
    }

    public void collect(){
        for(int i=0; i<variable_list.size(); i++)
	    ((NslVariable)variable_list.elementAt(i)).collect();
        repaint();
    }


    public void setSize(Dimension d){
	super.setSize(d);
    }

    public void setWindowName(String n) {
    	windowName = n;
    }
    
    public String getWindowName() {
    	return windowName;
    }
    
    public double getC_max_y() {
        return C_max_y; 
    }
    
    public double getC_min_y() { 
        return C_min_y; 
    }
}











