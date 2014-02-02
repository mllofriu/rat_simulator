/* SCCS  @(#)NslTemporalCanvasProperty.java	1.7---09/01/99--00:15:48 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslTemporalCanvasProperty.java,v $
// Revision 1.3  1997/05/09 22:30:26  danjie
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


class NslTemporalCanvasProperty extends Dialog implements ActionListener, ItemListener
{

   public void actionPerformed(ActionEvent evt)
   {
        String arg = evt.getActionCommand();

        if(arg.equals("Ok"))
        {
             dispose();
             float y_max = Float.valueOf(ymax.getText().trim()).floatValue();
             float y_min = Float.valueOf(ymin.getText().trim()).floatValue();

             currentCanvas.set_y_range(y_min, y_max);

             currentCanvas.set_curve_color(curveColor);                       
 


             // change all other properties here

             currentCanvas.update();

             System.out.println("INT"+y_max);



        }

        else 
        {
             dispose();

        }
  }


  public void itemStateChanged(ItemEvent evt)
  {
        if(evt.getSource().equals(line_black))
        {
             curveColor = Color.black;
	}
        else if(evt.getSource().equals(line_red))
        {
             curveColor = Color.red;
	}
        else if(evt.getSource().equals(line_gray))
        {
             curveColor = Color.gray;
	}
        else if(evt.getSource().equals(line_blue))
        {
             curveColor = Color.blue;
	}

   }







    public NslTemporalCanvasProperty(NslFrame parent)
    {
        super(parent, "Temporal Plot Properties",true);

        setLayout(new GridLayout(5,1));

        currentCanvas = (NslTemporalCanvas)((NslFrame)getParent()).getCurrentCanvas();        

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
        


        p2.add(new Label("Line Style:"));
        p2.add(lineStyle = new TextField("",8));
        CheckboxGroup gLine = new CheckboxGroup();
        p2.add(line_solid = new Checkbox("Solid",gLine, true));
        p2.add(line_dotted = new Checkbox("Dotted", gLine, false));
        p2.add(line_dashdot = new Checkbox("Dashdot", gLine, false));  
        p2.add(line_dashed  = new Checkbox("Dashed", gLine, false));        

        add(p2);


        Panel p3 = new Panel();

        p3.add(new Label("Line Color:"));
        p3.add(lineColor = new TextField("",8));


        p3.setLayout(new GridLayout(1,5));
        CheckboxGroup g = new CheckboxGroup();
        p3.add(line_black = new Checkbox("Black",g, true));
        line_black.addItemListener(this);
        p3.add(line_red = new Checkbox("Red", g, false));
        line_red.addItemListener(this);
        p3.add(line_gray = new Checkbox("Gray", g, false));  
        line_gray.addItemListener(this);
        p3.add(line_blue  = new Checkbox("Blue", g, false));
        line_blue.addItemListener(this);

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

   private NslTemporalCanvas currentCanvas;
   private TextField ymax, ymin, lineColor, lineStyle;
   private Checkbox line_black, line_red, line_gray, line_blue;
   private Checkbox line_solid, line_dotted, line_dashdot, line_dashed;
   private Color curveColor=Color.black;


}


