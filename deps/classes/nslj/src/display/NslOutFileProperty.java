/* SCCS  @(#)NslOutFileProperty.java	1.8---09/01/99--00:15:46 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.awt.event.*;
import nslj.src.lang.*;
import nslj.src.system.*;


class NslOutFileProperty extends Dialog implements ActionListener, ItemListener
{

   public void actionPerformed(ActionEvent evt)
   {
        String arg = evt.getActionCommand();

        if(arg.equals("Ok"))
        {
             //System.out.println(evt.toString());


             dispose();
		//98/9/30 aa need catch and try
	     if (file_name==null) {
		System.out.println("NslOutFileProperty:Error: file_name is null");
	     }
	     if (file_format==null) {
		System.out.println("NslOutFileProperty:Error: file_format is null");
	     }

             String fileName = file_name.getText();

             if(file_format.equals("Matlab"))
             {
		System.out.println("debug: OutFileProperty: Calling matlab routine...");
                 NslOutFile.outToMatlab(currentCanvas, fileName);
             } 
             else if(file_format.equals("Gnuplot"))
             {
             }
             else if(file_format.equals("Plotmtv"))
             {
             }
             // other format here

        }

	else 
        {
           dispose();

        }

  }

  public void itemStateChanged(ItemEvent evt)
  {
      
        if(evt.getSource().equals(matlab))
        {
             file_format = "Matlab";
	}
        else if(evt.getSource().equals(gunplot))
        {
             file_format = "Gnuplot";
	}
        else if(evt.getSource().equals(plotmtv))
        {
             file_format = "Plotmtv";
	}
        else if(evt.getSource().equals(plplot))
        {
             file_format = "Plplot";
	}
	else
		System.out.println("NO FORMAT WAS SELECTED!!");


        
   }


    public NslOutFileProperty(NslFrame parent)
    {
        super(parent, "Output File Properties",true);

        setLayout(new GridLayout(5,1));

        currentCanvas = (NslCanvas)((NslFrame)getParent()).getCurrentCanvas();

        Panel p1 = new Panel();
        p1.setLayout(new GridLayout(1,2));

        p1.add(new Label("File Name:"));
        p1.add(file_name = new TextField("", 8));



        Panel p2 = new Panel();
        p2.setLayout(new GridLayout(1,5));
        
        Panel p3 = new Panel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.add(new Label("Save file as:"));



        CheckboxGroup gBox = new CheckboxGroup();

        p2.add(matlab = new Checkbox("Mat",gBox, true));
        matlab.addItemListener(this);
        p2.add(gunplot = new Checkbox("Gnuplot", gBox, false));
        gunplot.addItemListener(this);
        p2.add(plotmtv = new Checkbox("Plotmtv", gBox, false));  
        plotmtv.addItemListener(this);
        p2.add(plplot  = new Checkbox("PLplot", gBox, false));        
        plplot.addItemListener(this);


        add(p1);
        add(p3);
        add(p2);

        Button b;

        Panel p4 = new Panel();
        p4.add(b = new Button("Ok"));
        b.addActionListener(this);
        p4.add(b = new Button("Cancel"));
        b.addActionListener(this);
        add(p4);
        setSize(400,180);



   }



   private NslCanvas currentCanvas;
   private TextField file_name;
   private Checkbox canvas, frame;
   private Checkbox matlab, gunplot, plotmtv, plplot; 
	//98/9/30 aa added Matlab
   private String file_format = "Matlab";
}
