/* SCCS  @(#)NslSpatialCanvas.java	1.9---09/01/99--00:15:48 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslSpatialCanvas.java,v $
// Revision 1.1  1997/11/06 03:18:57  erhan
// NSL3.0.b
//
// Revision 1.4  1997/05/09 22:30:23  danjie
// add some comments and Log
//
//--------------------------------------

package nslj.src.display;

import java.awt.*;
import java.util.*;
import java.io.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import java.lang.Math;
 




public class NslSpatialCanvas extends NslCanvas
{


 private Vector2 priv=new  Vector2();



    public NslSpatialCanvas()
    {
        super();
    }
    public NslSpatialCanvas(NslFrame nslDisplayFrame,NslCanvas pre)
    {
        super();
	set_min_max(pre.getC_min_y(),pre.getC_max_y());
    }
    public NslSpatialCanvas(NslFrame nslDisplayFrame,String full_name, 
				   NslVariableInfo data_info)
    {
        super(nslDisplayFrame,full_name, data_info);
        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
	//        data = ((NslVariable)variable_list.elementAt(0)).data;
	draw_time = 0;
    }

   public NslSpatialCanvas(NslFrame nslDisplayFrame,String full_name, 
				  NslVariableInfo data_info,
				  double dmax, double dmin)
   {
        super(nslDisplayFrame,full_name, data_info);
        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
	//  data = ((NslVariable)variable_list.elementAt(0)).data;
	draw_time = 0;
	y_max =(float) dmax;
	y_min =(float) dmin;
    }



    public void init()
    {
        Graphics g = getGraphics();
        Rectangle b = getBounds();
        g.clearRect(0, 0, b.width/2, b.height/2);
       // last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
        // data = ((NslVariable)variable_list.elementAt(0)).data;
        paint(g);
	// draw_time = -1;

    }

    public void update()
    {
      Graphics g2 = getGraphics(); 
      draw_time = 0; 
      update(g2);
    }

    public void repaint()
    {
        Graphics g2 = getGraphics();
        draw_time =0;
        paint(g2);

    }



    public void paint_partial(Graphics g)

    {
	     
	if(canvas_type.equals("Normal"))
        super.set_draw_size();
        else if(canvas_type.equals("Zoom"))
        super.zoom_draw_size();

        Rectangle b = getBounds();
  	g.clearRect(0, 0, b.width, b.height);

        g.setColor(Color.black);

        //draw variable

        NslVariable v = (NslVariable)variable_list.elementAt(0);
        x_dimension = v.info.getDimension(0);
        y_dimension = v.info.getDimension(1);


        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
        data = ((NslVariable)variable_list.elementAt(0)).data;

	 int [] xtemp;
	 int [] ytemp;
	 xtemp = new int[x_dimension * y_dimension * variable_list.size()];
	 ytemp = new int[x_dimension * y_dimension * variable_list.size()];



	draw_time = last_data_pos;
        //for( ; draw_time<=last_data_pos; draw_time++){

 //System.out.println("\n\n\nDRAWTIME = "+draw_time+priv.xval() ); 

         Color color = getBackground();
         g.setColor(color);

         g.fillRect(0, 0, x_dimension, y_dimension);

          for(int i=0; i<x_dimension; i++)
            for(int j=0; j<y_dimension; j++)
              {

  double x_size = drawX + (int)(dx * (data[i][j][draw_time] - y_min) / (y_max - y_min));
  double y_size = drawY + (int)(dy * (data[i][j][draw_time] - y_min) / (y_max - y_min));
                Polygon poly = new Polygon();	
// System.out.println("SPATIAL = "+variable_list.size()+" "+i+" "+j); 


                //to draw multiple variables
		double []pt_orig; double  []pt_final; 
		pt_orig = new double[3];
		pt_final = new double[3];
		pt_center = new double[3];

                for(int v_count=0; v_count<variable_list.size(); v_count++)
                {
		  double xt, yt;

                  data = ((NslVariable)variable_list.elementAt(v_count)).data;

		  pt_orig[0] = i ; // x coordinate
		  pt_orig[1] = j ; // y coordinate
		  //if (data[i][j][draw_time] > 100) pt_orig[2] = -100*3; else
		  //pt_orig[2] = data[i][j][draw_time] * (-3); // z (multiply by 10 for test purposes and to accentuate differences
//		  if (data[i][j][draw_time] > 100) pt_orig[2] = -100/5.0; else
		  pt_orig[2] = data[i][j][draw_time]/50.0 ; // z (multiply by 10 for test purposes and to accentuate differences

if (data[i][j][draw_time] > 0.001) 
  ;//System.out.println("YES! "+draw_time+" "+data[i][j][draw_time]);

		  pt_final[0] = 0.0; pt_final[1] = 0.0; pt_final[2] = 0.0; 
     calculate_center(pt_final, pt_center);
	pt_center[0] = drawX + (int)(pt_center[0] * dx);
	pt_center[1] = drawY + (int)(pt_center[1] * dy);
	xoff =0;
     calculate_xoffset(pt_final);
//	xoff -= drawX;
//	xoff *= dx;
        xoff -= 5;
     calculate_yoffset(pt_final);
//	yoff *= dy;
        yoff = drawY + dy* (v.info.getDimension(1)+ 1) - yoff ;

//System.out.print(" Center at "+pt_center[0]+" "+pt_center[1]+" "+pt_center[2]+" "+" xoff "+xoff+" yoff "+ yoff+" ("+v.get_x()+" "+v.get_y()+") ---- ");
		  project(pt_orig, pt_final);

		  xt = (pt_final[0] + pt_final[2]) * (1.0/Math.sqrt(2));
		  yt = (-1*pt_final[0] + pt_final[2]) * (1.0/Math.sqrt(2));

		xt = (pt_final[0] * Math.cos(3.14156/10.) + pt_final[2] * Math.sin(3.14156/10.0)) ;

		 //Rotate2d(pt_final, -3.14156/4.0, draw_X + (int)(x_size/2), (int)(y_size/2));
		 //Rotate2d(pt_final, -0.02, (int)pt_center[0], (int)pt_center[1]);
		  xtemp[ (i*x_dimension + j ) ] = drawX + (int)(pt_final[0]*dx);

		ytemp[ (i*x_dimension + j ) ] = drawY + (int)(pt_final[1]*dy) ;
		  pt_final[0] = xtemp [ (i*x_dimension + j) ];
		  pt_final[1] = ytemp [ (i*x_dimension + j) ];
		 Rotate2d(pt_final, 3.14156/6.0, (int)pt_center[0], (int)pt_center[1]);
		  xtemp[ i*x_dimension + j] = (int)pt_final[0] - xoff;
		  ytemp[ i*x_dimension + j] = (int)pt_final[1] + yoff;


  //System.out.println("BEFORE: xtemp "+xtemp[ i*x_dimension + j]+" ytemp "+ytemp[i*x_dimension + j]+" ztemp" +pt_final[2]);
/*
		 //Rotate2d(pt_final, -3.14156/4.0, (int)(x_size/2), (int)(y_size/2));
		 Rotate2d(pt_final, -3.1415927/4.0, (int)(x_size/2), (int)(y_size/2));
		 //Rotatex(pt_final, 0.0/6.0);

		  xtemp[ (i*x_dimension + j ) ] = drawX + (int)(pt_final[0]*dx) + 120;
		ytemp[ (i*x_dimension + j ) ] = drawY + (int)(pt_final[1]*dy) ;
*/
/*
		  xtemp[ (i*x_dimension + j ) ] = drawX + (int)(pt_final[1]*dx);
		ytemp[ (i*x_dimension + j ) ] = drawY + (int)(pt_final[0]*dy);



		  xtemp[ (i*x_dimension + j ) ] = drawX + (int)(xt*dx);
		  ytemp[ (i*x_dimension + j ) ] = drawY + (int)(yt*dy);
*/

  //System.out.println("xtemp "+xtemp[ i*x_dimension + j]+" ytemp "+ytemp[i*x_dimension + j]);


		poly.addPoint(xtemp[i*x_dimension +j], ytemp[i*x_dimension +j]);

//		  mygon.addPoint(pt_final[0], pt_final[1]);


              }
            }
          //}

	  g.setColor(Color.black);
          for(int i=1; i<x_dimension; i++)
            for(int j=1; j<y_dimension; j++){
		Polygon poly_temp = new Polygon();
		poly_temp.addPoint(xtemp[i*x_dimension +j], ytemp[i*x_dimension+j]);
		poly_temp.addPoint(xtemp[i*x_dimension +j-1], ytemp[i*x_dimension+(j-1)]);
		poly_temp.addPoint(xtemp[(i-1)*x_dimension +j-1], ytemp[(i-1)*x_dimension+j-1]);
		poly_temp.addPoint(xtemp[(i-1)*x_dimension +j], ytemp[(i-1)*x_dimension+j]);
		poly_temp.addPoint(xtemp[i*x_dimension +j], ytemp[i*x_dimension+j]);

//System.out.println("Polygon: ("+ (i*x_dimension+j)+" "+((i-1)*x_dimension+j)+" "+(i*x_dimension +j-1)+")"+xtemp[i*x_dimension+j]+" " +ytemp[i*x_dimension+j]+ " "+xtemp[(i-1)*x_dimension+j]+" "+ytemp[(i-1)*x_dimension+j]+" "+xtemp[i*x_dimension+j-1]+" "+ytemp[i*x_dimension+j-1]);


		g.drawPolygon(poly_temp);
            }


g.setColor(Color.black);
//System.out.println("AM I RIGHT? "+dx+" "+dy+" "+xtemp.length);
//for (int i=0;i<xtemp.length;i++)
 // System.out.print(" "+xtemp[i]+" "+ytemp[i]+"   ");

	  //g.drawPolygon(xtemp, ytemp, xtemp.length);
//	  g.drawPolygon(poly);
          super.paint(g, -1);       
   }


    public void paint_partial1(Graphics g)

    {
	     
	if(canvas_type.equals("Normal"))
        super.set_draw_size();
        else if(canvas_type.equals("Zoom"))
        super.zoom_draw_size();

        g.setColor(Color.black);

        //draw variable

        NslVariable v = (NslVariable)variable_list.elementAt(0);
        x_dimension = v.info.getDimension(0);
        y_dimension = v.info.getDimension(1);


        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
        data = ((NslVariable)variable_list.elementAt(0)).data;



        for( ; draw_time<=last_data_pos; draw_time++)
          for(int i=0; i<x_dimension; i++)
            for(int j=0; j<y_dimension; j++)
              {

                Color color = getBackground();
                g.setColor(color);

                g.fillRect(drawX+j*dx+1,
                   drawY+i*dy+1,
                   dx-1, dy-1);



                //to draw multiple variables

                for(int v_count=0; v_count<variable_list.size(); v_count++)
                {
                  data = ((NslVariable)variable_list.elementAt(v_count)).data;
                
                  if(data[i][j][draw_time]>= y_max)
                  {
                      data_x_size = dx;
                      data_y_size = dy;
                  }
                  else if(data[i][j][draw_time] <= y_min)
                  {
                      data_x_size = 0;
                      data_y_size = 0;
                  }
                  else
                  {
                      data_x_size = (int)(dx * (data[i][j][draw_time] - y_min) / (y_max - y_min));
                      data_y_size = (int)(dy * (data[i][j][draw_time] - y_min) / (y_max - y_min));
                  }


                  boxColor = ((NslVariable)variable_list.elementAt(v_count)).info.getColor();
                  g.setColor(boxColor);

                  g.setXORMode(getBackground());  // For overlaying the multiple box
                  g.fillRect(drawX+j*dx+(dx-data_x_size)/2+1,
                             drawY+i*dy+(dy-data_y_size)/2+1,
                             data_x_size, data_y_size);
                  g.setPaintMode();

              }
            }
          super.paint(g, -1);       
   }





    public void paint(Graphics g)

    {
	     
        draw_time =last_data_pos;
        paint_partial(g);
    }



    public void collect()
    {
        for(int i=0; i<variable_list.size(); i++)
        ((NslVariable)variable_list.elementAt(i)).collect();
	Graphics gr=getGraphics();
//        paint(gr);
        paint_partial(gr);
    }

    public void set_y_range(float ymin, float ymax)
    {
        y_min = ymin;
        y_max = ymax;
	super.set_min_max((double)ymin,(double)ymax);
    }



    public int get_ymin()
    {
        return (int)y_min;
    }
    
    public int get_ymax()
    {
        return (int)y_max;
    }

    public void set_box_color(Color c)
    {
            NslVariable ndv = (NslVariable)variable_list.elementAt(0);
            ndv.info.setColor(c);          
            boxColor = c;
        
    }


    public void calculate_center(double pt_orig[], double pt_final[])
	{
		pt_orig [0]= 4; pt_orig [1] = 4; pt_orig[2] = -2;
		project(pt_orig, pt_final);
	}

    public void calculate_xoffset(double pt_orig[])
	{
/*
		pt_orig [0]= 8; pt_orig [1] = 0; pt_orig[2] = -2;
		project(pt_orig, pt_final);
		xoff = (int)pt_final[0];
*/
   double xtemp, ytemp;
   double []pt_final; pt_final = new double[3];
   pt_orig [0]= 0; pt_orig [1] = 8; pt_orig[2] = -2;
 
   project(pt_orig, pt_final);
 
   xtemp = drawX + (int)(pt_final[0]*dx);
   ytemp= drawY + (int)(pt_final[1]*dy) ;
   pt_final[0] = xtemp;
   pt_final[1] = ytemp;
   Rotate2d(pt_final, 3.14156/6.0, (int)pt_center[0], (int)pt_center[1]);
   xoff = (int)pt_final[0];

	}
    public void calculate_yoffset(double pt_orig[])
	{
/*
		double [] pt_final; pt_final = new double[3];
		pt_orig [0]= 8; pt_orig [1] = 8; pt_orig[2] = -2;
		project(pt_orig, pt_final);
		yoff = (int)pt_final[1];
*/
   double xtemp, ytemp;
   double []pt_final; pt_final = new double[3];
   pt_orig [0]= 8; pt_orig [1] = 8; pt_orig[2] = -2;
   project(pt_orig, pt_final);
 
 
   xtemp = drawX + (int)(pt_final[0]*dx);
   ytemp = drawY + (int)(pt_final[1]*dy) ;
   pt_final[0] = xtemp;
   pt_final[1] = ytemp;
   Rotate2d(pt_final, 3.14156/6.0, (int)pt_center[0], (int)pt_center[1]);
   yoff = (int)pt_final[1];

	}



    public void project(double pt_orig[], double pt_final[])
	// pt_orig is the 3d original point (z is the value of the variable
	// pt_final is the x,y of the projection
    {

//System.out.print(" x "+ pt_orig[0]+" y "+pt_orig[1]+" z "+pt_orig[2]);
		pt_orig[2] -= 2;
		pt_final[0] = pt_orig[0]/1.0; pt_final[1] = pt_orig[1]/1.0;
	    // K = [ 1 - (x+y+z)/20]/3; Xn = x/20 +K; Yn= y/20 +K; Zn= z/20 +K;
	   double k = (8.0 - (pt_orig[0] + pt_orig[1] + pt_orig[2]))/3.0;
	   // double k = (100 - 2 *pt_orig[0] - 10*pt_orig[1] - 1*pt_orig[2])/105;

//    Rotatez(pt_orig, 3.14156/6.0); // better viewing? 

	//	pt_orig[1] += 90;
	

//System.out.print("K = "+k+" ");
	    for (int i=0;i<3;i++)
		pt_final[i] = pt_orig[i]+ k;


// System.out.println("BEFORE " +pt_orig[0]+" "+pt_orig[1]+" "+pt_orig[2]+" "+"AFTER "+pt_final[0]+" "+pt_final[1]);
    }


    private void Rotate2d(double []coord, double angle, int xof, int yof)
	{
		double x,y,z;
		
//System.out.println("SIN "+Math.sin(angle)+" COS "+Math.cos(angle));
		x = coord[0]; y = coord[1]; z = coord[2];	
		coord[0] = x * Math.cos(angle) - y * Math.sin(angle) +  (xof *(1 - Math.cos(angle)) + yof * Math.sin(angle) );
		coord[1] = x * Math.sin(angle) + y * Math.cos(angle) +  (yof * (1 - Math.cos(angle)) - xof * Math.sin(angle) );
		coord[2] = z;
	}

    private void Rotatex(double []coord, double angle)
	{
		double x,y,z;
		
		x = coord[0]; y = coord[1]; z = coord[2];	
		coord[0] = x;
		coord[1] = y * Math.cos(angle) - z * Math.sin(angle);
		coord[2] = y * Math.sin(angle) + z * Math.cos(angle);
	}


    private void Rotatez(double []coord, double angle)
	{
		double x,y,z;
		
		x = coord[0]; y = coord[1]; z = coord[2];	
		coord[0] = x * Math.cos(angle) - y * Math.sin(angle);
		coord[1] = x * Math.cos(angle) + y * Math.sin(angle);
		coord[2] = z;
	}



    private NslVariable v;


    private float y_max=100, y_min=0;

    private int xoff, yoff;
    private int data_x_size, data_y_size, x_dimension, y_dimension;
    private int last_data_pos, draw_time;
    private float [][][] data;
    private Color boxColor=Color.black;
    private int  VCount;

    private double []pt_center;

}










