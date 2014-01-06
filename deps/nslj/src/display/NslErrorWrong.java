/* SCCS  @(#)NslErrorWrong.java	1.8---09/01/99--00:15:44 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslErrorWrong.java,v $
//
// Revision 1.1  1997/11/06 03:19:07  erhan
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


class NslErrorWrong extends Dialog implements ActionListener, ItemListener
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


    public NslErrorWrong(Frame parent)
    {

        super(parent, "Error",true);
	frame = parent;

        setLayout(new GridLayout(2,1));

	Panel panel = new Panel();
	panel.add(new Label("Error: Invalid value entered for variable!"));

	add(panel);

        Button b;

        Panel okbutton = new Panel();
        okbutton.add(b = new Button("Ok"));
        b.addActionListener(this);

        add(okbutton);
	setSize(300, 85);
	show();

   }

   static private Frame frame;
   private Color box_color = Color.black;  
   private TextField ymax, ymin, boxcolor, boxstyle;
   private Checkbox box_black, box_red, box_gray, box_blue;

}


