/* SCCS  @(#)NslDimValue.java	1.9---09/01/99--00:15:43 */

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

class NslDimValue extends Dialog implements ActionListener
{
int num_dims=0;
NslFrame nsl_display_frame;
String var_sel_full_name;
NslVariableInfo var_sel_info;
String plot_type_name;
boolean replace_canvas;

public NslDimValue(NslFrame nsl_display_frame,
			  String var_sel_full_name, 
			  NslVariableInfo var_sel_info, 
			  String plot_type_name,
			  boolean replace_canvas)
    {
        super(nsl_display_frame, "Enter Values",true);

	/* these variables are needed in the action method */
	this.nsl_display_frame = nsl_display_frame;
	this.var_sel_full_name = var_sel_full_name;
	this.var_sel_info = var_sel_info;
	this.plot_type_name = plot_type_name;
	this.replace_canvas = replace_canvas;

	num_dims=(var_sel_info.getCountDimensions());

	if ((num_dims==0) ||(num_dims==1) ||(num_dims==2) || (num_dims>=4)) {
		System.err.println("Error: NslDimValue: should not have gotten here in the code.");
		return;
		}

	Panel p = new Panel();
	if (num_dims==3) {     
	         if ( NslDimInput.dim_choice.equals("HI")){
		addEnterOneValue(p,"J");
	  } else if ( NslDimInput.dim_choice.equals("HJ")){
		addEnterOneValue(p,"I");
	  } else if ( NslDimInput.dim_choice.equals("IJ")){
		addEnterOneValue(p,"H");
	  } else {
		System.err.println("Error: NslDimVal: bad input choice.");
		dispose();
		return;
	  }
	} // end if (num_dims==3)
	else if (num_dims==4) {     
	         if ( NslDimInput.dim_choice.equals("GH")){
		addEnterTwoValues(p,"I","J");
	  } else if ( NslDimInput.dim_choice.equals("GI")){
		addEnterTwoValues(p,"H","J");
	  } else if ( NslDimInput.dim_choice.equals("GJ")){
		addEnterTwoValues(p,"H","I");
	  } else if ( NslDimInput.dim_choice.equals("HI")){
		addEnterTwoValues(p,"G","J");
	  } else if ( NslDimInput.dim_choice.equals("HJ")){
		addEnterTwoValues(p,"G","I");
	  } else if ( NslDimInput.dim_choice.equals("IJ")){
		addEnterTwoValues(p,"G","H");
	  } else {
		System.err.println("Error: NslDimVal:bad input choice");
		dispose();
		return;
	  }

	} // end if (num_dims==4)

	  add("Center",p);
      
	  Button b;
	  Panel p4 = new Panel();
	  p4.setLayout(new GridLayout(1,5));
	  p4.add(b = new Button("<Back"));
	  b.addActionListener(this);
	  p4.add(new Label(""));
	  p4.add(b = new Button("Cancel"));
	  b.addActionListener(this);
	  p4.add(new Label(""));
	  p4.add(b = new Button("Next>"));
	  b.addActionListener(this);
	  add("South",p4);
	  setSize(450, 180);
} //end constructor


private void addEnterOneValue(Panel p,String one) {
      p.add(new Label("Enter value for Dimension "+one));
      TextField dim1 = new TextField(8);
      p.add(dim1);
}

private void addEnterTwoValues(Panel p,String one, String two) {
      p.add(new Label("Enter value for Dimension "+one));
      TextField dim1 = new TextField(8);
      p.add(dim1);
      p.add(new Label("Enter value for Dimension "+two));
      TextField dim2 = new TextField(8);
      p.add(dim2);
}

/*----------------------------------------------------*/
public void actionPerformed(ActionEvent evt)
{
        String arg = evt.getActionCommand();

        if(arg.equals("Next>"))
        {
            // 98/9/11 aa 
            nsl_display_frame.addDisplayCanvas(var_sel_full_name,
					       var_sel_info,
					       plot_type_name,
					       replace_canvas);
        }
        else if(arg.equals("Cancel"))
        {
           dispose();
        }
        else if(arg.equals("<Back"))
        {
           /* TODO - bring back NslDimInput Window */
           dispose();
	}

}

} // end class
