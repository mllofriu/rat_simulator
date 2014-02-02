/* SCCS  @(#)NslBarCanvas.java	1.9---09/01/99--00:15:41 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslBarCanvas.java,v $
// Revision 1.1  1997/11/06 03:19:01  erhan
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
 




public class NslBarCanvas extends NslCanvas
{


 private Vector2 priv=new  Vector2();



    public NslBarCanvas()
    {
        super();
    }
    public NslBarCanvas(NslFrame nslDisplayFrame, NslCanvas pre)
    {
        super();
	set_min_max(pre.getC_min_y(),pre.getC_max_y());
    }

    public NslBarCanvas(NslFrame nslDisplayFrame,String full_name, 
			       NslVariableInfo data_info)
    {
        super( nslDisplayFrame,full_name, data_info);
        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
	//        data = ((NslVariable)variable_list.elementAt(0)).data;
	draw_time = 0;
    }

   public NslBarCanvas(NslFrame nslDisplayFrame,String full_name, 
			      NslVariableInfo data_info,
			      double dmin, double dmax)
   {
        super(nslDisplayFrame,full_name, data_info);
        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
	//  data = ((NslVariable)variable_list.elementAt(0)).data;
	draw_time = 0;
        set_min_max(dmin,dmax);
	//y_max =(float) dmax;
	//y_min =(float) dmin;
    }

    public void  set_min_max(double dmin,double dmax)
{
        y_max =(float) dmax;
        y_min =(float) dmin;
        C_max_y = dmax;
        C_min_y = dmin;
}  



    public void init()
    {
        Graphics g = getGraphics();
        Rectangle b = getBounds();
	g.clearRect(0, 0, b.width, b.height);
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

        g.setColor(Color.black);

        //draw variable

        NslVariable v = (NslVariable)variable_list.elementAt(0);
        x_dimension = v.info.getDimension(0);
        y_dimension = v.info.getDimension(1);


        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
        data = ((NslVariable)variable_list.elementAt(0)).data;


	draw_time = last_data_pos;
        //for( ; draw_time<=last_data_pos; draw_time++){

 //System.out.println("\n\n\nDRAWTIME = "+draw_time+priv.xval() ); 

	//  if (draw_time > 0 && (draw_time % 30 == 0)) 
		//fitting_main(draw_time); // just for testing purposes

          for(int i=0; i<x_dimension; i++)
            for(int j=0; j<y_dimension; j++)
              {

                Color color = getBackground();
                g.setColor(color);

                g.fillRect(drawX+j*dx+1, drawY+i*dy+1, dx-1, dy-1);



                //to draw multiple variables

                for(int v_count=0; v_count<variable_list.size(); v_count++)
                {

                  data = ((NslVariable)variable_list.elementAt(v_count)).data;

                  x_pos = (int)(dy * ( 0 - y_min)/(y_max - y_min) );
                
                  if(data[i][j][draw_time]>= y_max )
                  {
                      data_x_size = dx ;
                      data_y_size = dy ;
                  }
                  else if(data[i][j][draw_time] <= y_min)
                  {
                      data_x_size = 0;
                      data_y_size = 0;
                  }
                  else {

// actual value + 1 pixel to be able to see the zero... 
                      data_x_size = 1+ (int)(dx * (data[i][j][draw_time] - y_min) / (y_max - y_min));
                      data_y_size = 1+ (int)(dy * (data[i][j][draw_time] - y_min) / (y_max - y_min));
                  }

//	System.out.println("X_pos is at "+ x_pos + " ( " + dy +" ) data_x_size "+ data_x_size + " data "+ data[i][j][draw_time] + " "+v.get_x() + " y  "+ v.get_y());

                  boxColor = ((NslVariable)variable_list.elementAt(v_count)).info.getColor();
                  g.setColor(boxColor);

                  g.setXORMode(getBackground());  // For overlaying the multiple box

  		  if (data[i][j][draw_time] >= 0.0)
		    g.fillRect(drawX+ j*dx,
		 	       drawY + (i+1)*dy - data_y_size,
			       dx,
			       (int)(data_y_size - x_pos) );
		  if (data[i][j][draw_time] <= 0.0)
		    g.drawRect(drawX+ j*dx,
		  	       drawY + (i+1)*dy - x_pos,
			       dx,
			       -1*(int)(data_y_size - x_pos) );

                  g.setPaintMode();

              }
            }
          super.paint(g, 1);       
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


 public Vector2 V2SubII(Vector2 a, Vector2 b)
  {
        Vector2 c=new Vector2();
 
        c.xset( a.xval() - b.xval() );
        c.yset( a.yval() - b.yval() );
        return(c);
 
  }

 


    private float y_max=100, y_min=0;

    private int data_x_size, data_y_size, x_dimension, y_dimension;
    private int last_data_pos, draw_time;
    private float [][][] data;
    private Color boxColor=Color.black;
    private int  VCount;

    private int x_pos;


}










