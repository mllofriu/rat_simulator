/* SCCS  @(#)NslSetVariable.java	1.5---05/21/99--17:42:37 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslSetVariable.java,v $
// Revision 1.1  1997/11/06 03:19:15  erhan
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
import nslj.src.nsls.struct.*;
import tcl.lang.*;

class NslSetVariable extends Dialog implements ActionListener, ItemListener
{

   public void actionPerformed(ActionEvent evt)
   {
        String arg = evt.getActionCommand();

        if(arg.equals("OK")) {
        
	   try {
		Executive.interp.eval("nsl call system set"+name+" "+txt.getText().trim());
	   } catch (TclException e) {
           }
           dispose();
        }
   }

   public void itemStateChanged(ItemEvent evt) {
   }
   
    public NslSetVariable(Frame parent, String name, NslSystem system)
    {

        super(parent, "Set Variable "+name,true);
	frame = parent;
	this.name = name;

        setLayout(new GridLayout(2,1));

	Panel panel = new Panel();
        panel.setLayout(new GridLayout(1,2));

	panel.add(new Label(name+":"));
	// We have to set the text field with the current value
	String value="";
	try {
		Executive.interp.eval("nsl call system get"+name);
		value = Executive.interp.getResult().toString();
	} catch (TclException e) {
        }
        
        panel.add(txt = new TextField(value, 8));

	add(panel);

        Button b;

        Panel okbutton = new Panel();
        okbutton.add(b = new Button("OK"));
        b.addActionListener(this);

        add(okbutton);
        setSize(250,100);
	setVisible(true);
   }

   static private Frame frame;

   private TextField txt;
   String name;
}


