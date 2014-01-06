/* SCCS  %W% --- %G% -- %U% */

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


public class NslTemporalCanvas extends NslCanvas implements ComponentListener {

  public NslTemporalCanvas() {
    super();
    addComponentListener(this);
  }
  public NslTemporalCanvas(NslFrame nslDisplayFrame,double min,double max) { 
    super(); //???
    addComponentListener(this);
    set_min_max(min,max);
  }

  public NslTemporalCanvas(NslFrame nslDisplayFrame,NslCanvas pre) {
    this(nslDisplayFrame,pre.canvas_name, 
	 ((NslVariable)pre.variable_list.elementAt(0)).info,
	 pre.getC_min_y(),
	 pre.getC_max_y());
    //addComponentListener(this);
    //init();
  }

  public NslTemporalCanvas(NslFrame nslDisplayFrame,String full_name,   NslVariableInfo data_info, double dmin,double dmax) {
    this(nslDisplayFrame,full_name, data_info);
    set_min_max(dmin,dmax);
    //addComponentListener(this);
    //init();
  }
  public NslTemporalCanvas( NslFrame nslDisplayFrame,String full_name, 
				  NslVariableInfo data_info) {
    super(nslDisplayFrame,full_name, data_info);
    addComponentListener(this);
    init();
  }


  public void  set_min_max(double dmin,double dmax) {  
    y_max =(int) dmax;
    y_min =(int) dmin;
    super.set_min_max(dmin,dmax);
  }   
  
  public void componentShown(ComponentEvent e) { }
  public void componentHidden(ComponentEvent e) { }
  public void componentMoved(ComponentEvent e) { }
  public void componentResized(ComponentEvent e) {    
    isZoom = canvas_type.equals("Zoom");
  }

  public void init() {
    draw_time = 0;
    NslVariable v = (NslVariable) variable_list.elementAt(0);
    max_data_pos = (int) v.get_max_cycles();
    if (max_data_pos<=0.0) {
	System.out.println("NslTemporalCanvas:max data position is zero");
	}    
    x_dimension = v.info.getDimension(0);
    y_dimension = v.info.getDimension(1);
  }

  public void paint(Graphics g) {
 
    paint_partial(g);
  }


  private class RoundedFloat {
    protected double value;
    protected int decimalPlaces;
    public RoundedFloat() { value = 0; decimalPlaces = 0;}
    public RoundedFloat(double f) { value = f; decimalPlaces = 0;}
    public RoundedFloat(double f, int i) {
      value = f; 
      decimalPlaces = (i > 0)? i : 0;
    }
    public String toString() {
      //value = value + Math.pow(.1,decimalPlaces)*5;
      String str = String.valueOf(value);
      String fraction = "";
      StringTokenizer st = new StringTokenizer(str, ".");
      String integral = st.nextToken();
      if (st.hasMoreTokens()) {
	fraction = st.nextToken();
	if (fraction.length() > decimalPlaces)
	  fraction = fraction.substring(0, decimalPlaces);
	fraction = "." + fraction;
      }
      if (fraction.equals(".")) {
      	fraction = ".0"; 
      }

      return integral + fraction;
    }
  }

  public void paint_partial(Graphics g) {
//     System.out.println("paint_partial() " + super.toString());
    // parameters for determining marks and labels on the display
    Rectangle r = getBounds();
    g.clearRect(r.x, r.y, r.width, r.height);
        
    int xMarkSize = r.height / 60 < 1 ? 1: 3;
    int yMarkSize = r.width / 40 < 1 ? 1: 3;
    int last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
    double quanta = ((NslVariable)variable_list.elementAt(0)).system.nslGetDelta();

    int yDecimals = 0;
    int xDecimals = 0;
     if (isZoom)
      zoom_draw_size();
    else
      set_draw_size();
    yMultiplier = ((float) dy)/(y_max - y_min);
    x_axis_pos =(int) (yMultiplier * y_max );  
    yBase = x_axis_pos + drawY;
    int noXMarks = drawW / xMarkPixelDistance;
    xMarkDistance = max_data_pos * quanta / (noXMarks > 0 ? noXMarks : 1);
    int noYMarks = drawH / (yMarkPixelDistance * x_dimension);
    yMarkDistance = (double) (y_max - y_min) / (noYMarks > 0 ? noYMarks : 1);
//     System.out.println("Marks: X = " + noXMarks + " Y = " + noYMarks);//
//     System.out.println("Distance: X = " + xMarkDistance + " Y = " + yMarkDistance);
    if (isZoom) {
      Font f = new Font("Courier", Font.PLAIN, 12);
      g.setFont(f);
      //yDecimals = (int) Math.round(Math.log(noYMarks) / Math.log(y_max - y_min));
      //xDecimals = (int) Math.round(Math.log(noXMarks) / Math.log(max_data_pos));
       yDecimals = (int) ((yMarkDistance*2)>=1?1:-Math.log(yMarkDistance));
       xDecimals =(int) ((xMarkDistance*2)>=1?1:-Math.log(xMarkDistance));
       /*System.out.println("-------------");
       System.out.println("Max data pos: "+max_data_pos+" quanta "+quanta+" *= "+(max_data_pos * quanta));
       System.out.println("Max Y = " + y_max + " Min Y = " + y_min);//
       System.out.println("Marks: X = " + noXMarks + " Y = " + noYMarks);
       System.out.println("Distance: X = " + xMarkDistance + " Y = " + yMarkDistance);
       System.out.println("Decimals: X = " + xDecimals + " Y = " + yDecimals);*/
    } 

    if ((NslSystem.init_run_char == 'B')||
	(NslSystem.init_run_char == 'D')) { //before or during init
      last_data_pos = 1;  // 98/9/14 aa: why not 0? // was 1
      draw_time=0;	
    }
//     g.setFont(new Font("Courier", Font.PLAIN, 12));

//    System.err.println("Paint_partial called "+(pT++));
    Rectangle b = getBounds();
    g.clearRect(0, 0, b.width, b.height);
    for(int l = 0; l < x_dimension; l++) {
      g.setColor(Color.lightGray);

      g.drawLine(drawX ,yBase + l * dy,
		 drawX + dx *y_dimension, yBase + l * dy); 

	

	
      // draw x marks
      for(int k=0; k < y_dimension; k++) {
	boolean showLabel = false;	  // label alternate x marks
	RoundedFloat f;
	for(double x = 0.0; x < max_data_pos * quanta; 
	    x += xMarkDistance, showLabel = !showLabel) {
	  
	  g.drawLine((int) (dx * x/(max_data_pos * quanta)+drawX+dx*k),
		     yBase + l * dy - xMarkSize, 
		     (int) (dx * x/(max_data_pos * quanta)+drawX+dx*k),
		     yBase + l * dy + xMarkSize);

	  if (isZoom && showLabel) {//* quanta
	    f = new RoundedFloat(x, xDecimals);
	    FontMetrics fm = g.getFontMetrics();
	    int labelXPosition = (int) (dx * x/(max_data_pos * quanta)+drawX+dx*k) - fm.stringWidth(f.toString())  / 2;
	    g.drawString(f.toString(), labelXPosition, yBase + l * dy - 2 * xMarkSize);
	  }
	}
         //g.setColor(Color.blue);
       

	// draw y marks here
	showLabel = true;	  // label alternate y marks
	for (double y = y_min; y < y_max;
	     y += yMarkDistance, showLabel = !showLabel) {
	  int x1=(drawX - yMarkSize);
	  int y1=(yBase - (int)(y * yMultiplier) + l * dy);
	  int x2=(drawX + yMarkSize);
	  
	   g.drawLine(x1,y1,x2,y1);
	   
	   if (isZoom && showLabel) {
	    f = new RoundedFloat(y, yDecimals);
	    FontMetrics fm = g.getFontMetrics();
	    int labelYPosition = yBase - (int)(y * yMultiplier) + l * dy
	      - (fm.getHeight()) / 2;
	    g.drawString(f.toString(), drawX - 3 * yMarkSize,
			 labelYPosition);
	    
	  }
	}
	    
      }
	    
      g.setColor(Color.black);
      for(int j = 1; j < last_data_pos; j++) {
	addPoint(g, j);
      }
/*      for(int j = 2; j < last_data_pos; j++) {
	addPoint(g, j-1);
      }
*/

    }

    super.paint(g, 1);
  }


    public void addPoint(Graphics g, int time) {


	// calculate bases
	float x1Base = drawX + 
	    (int)(dx * (double) ((time-1) % max_data_pos) / max_data_pos);
	float x2Base = drawX + 
	    (int)(dx * (double) (time % max_data_pos) / max_data_pos);

	// draw curves
	
	for(int k = 0; k < y_dimension; k++) {
	    int x1 = (int) (x1Base + dx * k);
	    int x2 = (int) (x2Base + dx * k);
	    for(int l = 0; l < x_dimension; l++) {
		for(int i = 0; i< variable_list.size(); i++) {
		    NslVariable v = (NslVariable) variable_list.elementAt(i);
		    g.setColor(v.info.getColor());
		    if (x1<x2) {
			g.drawLine(x1, yBase - (int) (v.data[l][k][time-1] * yMultiplier) + l * dy, 
			     x2, yBase - (int) (v.data[l][k][time] * yMultiplier) + l * dy);
			//System.err.println("("+time+") ("+v.data[l][k][time-1]+","+v.data[l][k][time]+")");
		    }
		}
	    }
	}
    }

  public void set_y_range(float ymin, float ymax) {
    y_min = (int)ymin;
    y_max = (int)ymax;
    super.set_min_max((double)ymin,(double)ymax);
  }

  public int get_ymin() { return y_min; }
    
  public int get_ymax() { return y_max; }

  public void set_curve_color(Color c) {

   ((NslVariable)variable_list.elementAt(0)).info.setColor(c);          
        
  }

    public void collect() {

	Graphics g = getGraphics();
	Enumeration E = variable_list.elements();
	NslVariable var;
	float value=-100;
	
	while (E.hasMoreElements()) {
	    var = (NslVariable)E.nextElement();
	    var.collect();
	
	    draw_time = var.last_data_pos;
	    value = var.data[0][0][draw_time];
	} 
	
	if (draw_time > 0 && draw_time < max_data_pos) {
	    //System.err.println("Adding point: "+value);
	    addPoint(g, draw_time);
        } else if (draw_time < 1) {
	    //System.err.println("Saving point: "+value);
	    update();
	}
	
	g.dispose();
	
    }
    

  private  int x_dimension=1;  // get the info from NslVariable
  private  int y_dimension=1;
  
  private int x_min, x_max, y_min=-100, y_max=100;
  private int x_axis_pos;
  private int yBase;
  private float yMultiplier;
  private double xMarkDistance;
  private double yMarkDistance;
  private int draw_time;
  private int max_data_pos;
  private int pT =0;

  static public double graphsize;
  private boolean isZoom;
  
  static protected int xMarkPixelDistance = 30;
  static protected int yMarkPixelDistance = 40;
}







