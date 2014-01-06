/* SCCS  @(#)NslOutOfBoundsBezier.java	1.7---09/01/99--00:15:46 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslOutOfBoundsBezier.java,v $
// Revision 1.1  1998/02/02 23:52:38  erhan
// TemporaryBezier & etc
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


class NslOutOfBoundsBezier extends Dialog implements ActionListener, ItemListener
{

   public void actionPerformed(ActionEvent evt)
   {
        String arg = evt.getActionCommand();

        if(arg.equals("Ok")) {
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


    public NslOutOfBoundsBezier()
    {

//        super(parent, "Error",true);
//	frame = parent;
	super(frame, "Erorr",true);

        setLayout(new GridLayout(2,1));

	Panel panel = new Panel();
	panel.add( new Label("Use HISTORY instead of BEZIER: more control points than the ones on the curve are required to regenerate the curve!") );

	add(panel);

        Button b;

        Panel okbutton = new Panel();
        okbutton.add(b = new Button("Ok"));
        b.addActionListener(this);

        add(okbutton);
        setSize(750,85);
	setVisible(true);

   }

   static public NslFrame frame;
   private Color box_color = Color.black;  
   private TextField ymax, ymin, boxcolor, boxstyle;
   private Checkbox box_black, box_red, box_gray, box_blue;

  

}


