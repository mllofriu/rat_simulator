/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
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
 




public class NslImageCanvas extends NslCanvas
{


 private Vector2 priv=new  Vector2();



    public NslImageCanvas()
    {
        super();
    }
    public NslImageCanvas(NslFrame nslDisplayFrame, NslCanvas pre)
    {
        super();
	set_min_max(pre.getC_min_y(),pre.getC_max_y());
        System.err.println("1 Max: "+y_max+" Min:"+y_min);
    }
    public NslImageCanvas(NslFrame nslDisplayFrame,String full_name, 
				 NslVariableInfo data_info)
    {
        super(nslDisplayFrame,full_name, data_info);
        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
	//        data = ((NslVariable)variable_list.elementAt(0)).data;
	draw_time = 0;
	init_graycolors();
	y_max = 1;
	y_min = 0;
        //System.err.println("2 Max: "+y_max+" Min:"+y_min);
    }

   public NslImageCanvas(NslFrame nslDisplayFrame,String full_name, 
				NslVariableInfo data_info,
				double dmax, double dmin)
   {
        super( nslDisplayFrame,full_name, data_info);
        last_data_pos = ((NslVariable)variable_list.elementAt(0)).last_data_pos;
	//  data = ((NslVariable)variable_list.elementAt(0)).data;
	draw_time = 0;
	y_max =(float) dmax;
	y_min =(float) dmin;
        System.err.println("3 Max: "+y_max+" Min:"+y_min);
    }


    private int init_graycolors()
    {
       double red, green, blue;
       double x;

       gray_color = new int[257][3];

       for (int i=0; i< 256; i++)
 	{
		x = (double)i;
		//red = 255. * (.4*Math.cos(x/160. * M_PI) + .5);
		//red = 255. * (.5*Math.cos(x/160. * M_PI) + 0.5 );
		red = 255 - i;
		green = 255. * (.4*Math.sin(x/180. * M_PI - .8) + .5);
		blue = 255. * (.4*Math.sin(x/230. * M_PI - 1.8) + .5);

		//gray_color[i][0] = (int)(red / 256.0);
		//gray_color[i][0] = (int)red;
		gray_color[i][0] = (int)red;
		//gray_color[i][1] = (int)green;
		gray_color[i][1] = (int)red;
		//gray_color[i][2] = (int)blue;
		gray_color[i][2] = (int)red;


//System.out.println(" "+gray_color[i][0]+" "+gray_color[i][1]+" "+gray_color[i][2]+" "+red+" "+green+" "+blue);
	}

       return 1;
    }


    public void init()
    {
        Graphics g = getGraphics();
        Rectangle b = getBounds();
        g.clearRect(0, 0, b.width, b.height);

System.out.println("\n\n\n\n\n\n\n\n\nENTERING COLORS\n\n\n\n\n\n\n");

        init_graycolors();


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


    private int getcolor(float value)
	{
		int index;
		
		index = (int)value; //(value * 2.);
		if (index > 254) index = 255; // max value has been reached

		return index;
	}



    public void paint_partial(Graphics g)

    {


        //init_graycolors();
	     
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
	 //System.out.println("Image data: Y_min="+y_min+" Y_max="+y_max);
          for(int i=0; i<x_dimension; i++) {
            for(int j=0; j<y_dimension; j++)
              {

                Color color = getBackground();
                g.setColor(color);

                g.fillRect(drawX+j*dx+1, drawY+i*dy+1, dx-1, dy-1);

	        //System.out.print(data[i][j][draw_time]+"-");

                //to draw multiple variables

                for(int v_count=0; v_count<variable_list.size(); v_count++)
                {		  
                  data = ((NslVariable)variable_list.elementAt(v_count)).data;
                  //System.out.println("1 Data="+data[i][j][draw_time]);
                  /*if(data[i][j][draw_time]>= y_max)
                  {
                  //System.out.println("1");
                      data_x_size = dx;
                      data_y_size = dy;
                  }
                  else if(data[i][j][draw_time] <= y_min)
                  {
                  //System.out.println("2");
                      data_x_size = 0;
                      data_y_size = 0;
                  }
                  else
                  {
                  //System.out.println("3");
                      data_x_size = (int)(dx * (data[i][j][draw_time] - y_min) / (y_max - y_min));
                      data_y_size = (int)(dy * (data[i][j][draw_time] - y_min) / (y_max - y_min));
                  }*/
		  data_y_size = (int) (data[i][j][draw_time] % 256);
                  //System.out.println("2 Data="+data[i][j][draw_time]);

                  //boxColor = ((NslVariable)variable_list.elementAt(v_count)).get_color();

		  int indx = getcolor(data_y_size);
		  //System.out.println("index="+indx+" data="+data_y_size);
		  //System.out.println("Gray color: R="+gray_color[indx][0]+" G="+gray_color[indx][1]+" B="+gray_color[indx][2]+" index="+indx);
		  //System.out.print(indx+" ");
                  boxColor = new Color(gray_color[indx][0], gray_color[indx][1], gray_color[indx][2]);
                  //boxColor = new Color(data_y_size);
                  g.setColor(boxColor);

                  g.setXORMode(getBackground());  // For overlaying the multiple box

		  g.fillRect(drawX+j*dx+1, drawY+i*dy+1, dx-1, dy-1);
 

                //  g.fillRect(drawX+j*dx+(dx-data_x_size)/2+1,
                 //            drawY+i*dy+(dy-data_y_size)/2+1,
                  //           data_x_size, data_y_size);
                  g.setPaintMode();

              }
            }
		//System.out.println();
          }

          super.paint(g, 1);       
   }

    public void paint(Graphics g)

    {
	     
        draw_time =last_data_pos;

//	init_graycolors();
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




    private double M_PI = 3.1415926;
    private int [][]gray_color;


    private float y_max=100, y_min=0;

    private int data_x_size, data_y_size, x_dimension, y_dimension;
    private int last_data_pos, draw_time;
    private float [][][] data;
    private Color boxColor=Color.black;
    private int  VCount;


}










