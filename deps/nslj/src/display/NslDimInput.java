/* SCCS  @(#)NslDimInput.java	1.9---09/01/99--00:15:43 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.


package nslj.src.display;

import java.awt.*;
import java.awt.image.*;
//import java.util.*;
import java.awt.event.*;
import nslj.src.lang.*;
import nslj.src.system.*;

/* aa: notes: 
This routines needs to distinguish between plots for dot and string.
It also needs to call up NslDimValue if "next" chosen.
/*
         f.add(new MenuItem("Temporal"));
        f.add(new MenuItem("Area"));
        f.add(new MenuItem("Spatial2d"));
        f.add(new MenuItem("Spatial3d"));
        f.add(new MenuItem("Bar"));
        f.add(new MenuItem("Image"));
	f.add(new MenuItem("String"));
	f.add(new MenuItem("Dot"));
        //f.add(new MenuItem("X_Y"));
*/
class NslDimInput extends Dialog implements ActionListener, ItemListener {
 
  public static String dim_choice;
  private List dims = new List(4,true);
  private int num_dims=0;// number of dimensions
  private String[] di ;
  private static int ch = 1;

  private NslFrame nsl_display_frame1;
  private String var_sel_full_name1;
  private NslVariableInfo var_sel_info;
  private String plot_type_name1;
  private boolean replace_canvas;
  
/* NslDimInput is a dynamic window that popup when after
the NslVariableInput has appeared and the user selected "next".
Thus the items when the window is created should be current.
*/
	
  public NslDimInput(NslFrame nsl_display_frame, 
			    String var_sel_full_name, 
			    NslVariableInfo var_sel_info, 
			    String plot_type_name, 
			    boolean replace) {

    super(nsl_display_frame, "Select Dimensions",true);

    nsl_display_frame1 = nsl_display_frame;
    var_sel_full_name1 = var_sel_full_name;
    this.var_sel_info = var_sel_info;
    plot_type_name1 = plot_type_name;
    replace_canvas = replace;

	
    // 98/8/27 aa      
    int num_dims = (var_sel_info.getCountDimensions());

    if ((num_dims == 0) || (num_dims == 1) || (num_dims == 2) || ( num_dims > 4)) {
      System.err.println("Error. NslDinInput: should not have gotten here in the code.");
      return;
    } else if (num_dims == 3) {
      dims.add("H");
      dims.add("I");
      dims.add("J");


    } else if (num_dims == 4) {
      dims.add("G");
      dims.add("H");
      dims.add("I");
      dims.add("J");
    }
    
    Panel p = new Panel();
    p.add(new Label("Select two dimensions you wish to display:"));
    p.add(dims);
    dims.addItemListener(this);

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
  }// end constructor

  public void actionPerformed(ActionEvent evt) {
    String arg = evt.getActionCommand();

    if (arg.equals("Next>")) { 
      if (di.length == 0) {
	System.out.println("Warning:NslDimInput 0 - select two dimensions.");
        dispose();
      } else if (di.length == 1) {
        System.out.println("Warning:NslDimInput 1 - select two dimentions.");
        dispose();
      } else {  // two dimensions selected
	System.out.println("Debug:DimInput" + di.length + " " + di[0]);
	if ((di[0].equals("G") && di[1].equals("H")) || 
	    (di[0].equals("H") && di[1].equals("G"))) 
	  dim_choice = "GH";
	if ((di[0].equals("G") && di[1].equals("I")) || 
	    (di[0].equals("I") && di[1].equals("G"))) 
          dim_choice = "GI";
	if ((di[0].equals("G") && di[1].equals("J")) || 
	    (di[0].equals("J") && di[1].equals("G"))) 
	  dim_choice = "GJ";    
        if ((di[0].equals("H") && di[1].equals("I")) || 
	    (di[0].equals("I") && di[1].equals("H"))) 
          dim_choice = "HI";
        if ((di[0].equals("H") && di[1].equals("J")) || 
	    (di[0].equals("J") && di[1].equals("H"))) 
          dim_choice  =  "HJ";
        if ((di[0].equals("I") && di[1].equals("J")) || 
	    (di[0].equals("J") && di[1].equals("I"))) 
          dim_choice = "IJ";
        dispose();
			/* call dim values here : aa */
        NslDimValue dv = 
	  new NslDimValue(nsl_display_frame1, 
				 var_sel_full_name1, 
				 var_sel_info, 
				 plot_type_name1,
				 replace_canvas);
	dv.show();
      }
    } else if(arg.equals("Cancel")) {
      dispose();
    } else if (arg.equals("<Back")) {
		/* The Variable Input Window should still be open
		so we should not have to bring it up again. 
		*/
      dispose();
	    
    }
  }
  public void itemStateChanged(ItemEvent evt) {
      di = dims.getSelectedItems();
  }


}

