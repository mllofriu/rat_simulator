/* SCCS  @(#)NslImageCanvasProperty.java	1.7---09/01/99--00:15:45 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslImageCanvasProperty.java,v $
// Revision 1.1  1997/11/06 03:19:13  erhan
// NSL3.0.b
//
// Revision 1.3  1997/05/09 22:30:23  danjie
// add some comments and Log
//
//--------------------------------------

package nslj.src.display;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.awt.event.*;
import nslj.src.lang.*;
import nslj.src.system.*;


class NslImageCanvasProperty extends Dialog implements ActionListener, ItemListener
{

   public void actionPerformed(ActionEvent evt)
   {
        String arg = evt.getActionCommand();

        if(arg.equals("Ok"))
        {
             System.out.println(evt.toString());

             dispose();
             float y_max = Float.valueOf(ymax.getText().trim()).floatValue();
             float y_min = Float.valueOf(ymin.getText().trim()).floatValue();

             currentCanvas.set_y_range(y_min, y_max);

             currentCanvas.set_box_color(box_color);                       
 


             // change all other properties here

             currentCanvas.update();


        }

	else 
        {
           dispose();

        }

  }

  public void itemStateChanged(ItemEvent evt)
  {
      
        if(evt.getSource().equals(box_black))
        {
             box_color = Color.black;
	}
        else if(evt.getSource().equals(box_red))
        {
             box_color = Color.red;
	}
        else if(evt.getSource().equals(box_gray))
        {
             box_color = Color.gray;
	}
        else if(evt.getSource().equals(box_blue))
        {
             box_color = Color.blue;
	}


        
   }







    public NslImageCanvasProperty(NslFrame parent)
    {
        super(parent, "Image Plot Properties",true);

        setLayout(new GridLayout(5,1));

        currentCanvas = (NslImageCanvas)((NslFrame)getParent()).getCurrentCanvas();        

        int y_min = currentCanvas.get_ymin();
        int y_max = currentCanvas.get_ymax();


        Panel p1 = new Panel();
        p1.setLayout(new GridLayout(1,2));

        p1.add(new Label("Ymin:"));
        p1.add(ymin = new TextField(""+y_min, 8));
        p1.add(new Label("Ymax:"));
        p1.add(ymax = new TextField(""+y_max, 8));


        Panel p2 = new Panel();
        p2.setLayout(new GridLayout(1,5));
        


        p2.add(new Label("Box Style:"));
        p2.add(boxstyle = new TextField("",8));
        CheckboxGroup gBox = new CheckboxGroup();

        p2.add(box_solid = new Checkbox("Solid",gBox, true));
        box_solid.addItemListener(this);
        p2.add(box_dotted = new Checkbox("Dotted", gBox, false));
        box_dotted.addItemListener(this);
        p2.add(box_dashdot = new Checkbox("Dashdot", gBox, false));  
        box_dashdot.addItemListener(this);
        p2.add(box_dashed  = new Checkbox("Dashed", gBox, false));        
        box_dashed.addItemListener(this);

        add(p2);


        Panel p3 = new Panel();

        p3.add(new Label("Box Color:"));
        p3.add(boxcolor = new TextField("",8));


        p3.setLayout(new GridLayout(1,5));
        CheckboxGroup g = new CheckboxGroup();
        p3.add(box_black = new Checkbox("Black",g, true));
        box_black.addItemListener(this);
        p3.add(box_red = new Checkbox("Red", g, false));
        box_red.addItemListener(this);   
        p3.add(box_gray = new Checkbox("Gray", g, false));  
        box_gray.addItemListener(this);
        p3.add(box_blue  = new Checkbox("Blue", g, false));
        box_blue.addItemListener(this);

        add(p1);
        add(p2);
        add(p3);

        Button b;

        Panel p4 = new Panel();
        p4.add(b = new Button("Ok"));
        b.addActionListener(this);
        p4.add(b = new Button("Cancel"));
        b.addActionListener(this);
        add(p4);
        setSize(450,180);



   }



   private NslImageCanvas currentCanvas;
   private TextField ymax, ymin, boxcolor, boxstyle;
   private Checkbox box_black, box_red, box_gray, box_blue;
   private Checkbox box_solid, box_dotted, box_dashdot, box_dashed;
   private Color box_color = Color.black;  
}
