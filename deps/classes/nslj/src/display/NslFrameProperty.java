/* SCCS  @(#)NslFrameProperty.java	1.11---09/01/99--00:15:45 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import java.awt.event.*;

class NslFrameProperty extends Dialog implements ActionListener {


  public NslFrameProperty(NslFrame parent) {
    super(parent, "NslOutFrame Properties",true);
    parentframe=parent;

    setLayout(new GridLayout(5,1));

    // 99/5/11 aa: Frame Properties should not need a canvas
    //currentCanvas = (NslCanvas)((NslFrame)getParent()).getCurrentCanvas();             
    //Vector variable_list = currentCanvas.get_variable_list();
    //NslVariable ndv = (NslVariable)variable_list.elementAt(0);
    //double tmin = ndv.get_tmin();
    //double tmax = ndv.get_tmax();

    tmin = (double)parentframe.tmin;
    tmax = (double)parentframe.tmax;

    Panel panelt = new Panel();
    panelt.setLayout(new GridLayout(1,2));
    panelt.add(new Label("time min:"));
    panelt.add(t_min = new TextField(""+tmin, 8));
    panelt.add(new Label("time max:"));
    panelt.add(t_max = new TextField(""+tmax, 8));


    ymin = (double)parentframe.ymin;
    ymax = (double)parentframe.ymax;

    Panel panely = new Panel();
    panely.setLayout(new GridLayout(1,2));
    panely.add(new Label("y min:"));
    panely.add(t_min = new TextField(""+ymin, 8));
    panely.add(new Label("y max:"));
    panely.add(t_max = new TextField(""+ymax, 8));


    //todo: aa: change all these check boxes menus to list menus
    //todo: aa: need to do the same with the graph options menus as well

    Panel panelbg = new Panel();
    panelbg.setLayout(new GridLayout(1,5));
    panelbg.add(new Label("Background:"));    
    panelbg.add(bgcolor = new TextField("",8));
    CheckboxGroup bgcheckboxgroup = new CheckboxGroup();
    panelbg.add(bgc_white = new Checkbox("White",bgcheckboxgroup, false));
    panelbg.add(bgc_yellow = new Checkbox("Yellow", bgcheckboxgroup, false));
    panelbg.add(bgc_pink = new Checkbox("Pink", bgcheckboxgroup, false));  
    panelbg.add(bgc_orange  = new Checkbox("Orange", bgcheckboxgroup, false));     
    add(panelbg);

    backgroundColor=parentframe.backgroundColor;
    if (backgroundColor == Color.white) {
	bgc_white.setState(true);
    } else if (backgroundColor == Color.yellow) {	
	bgc_yellow.setState(true);
    } else if (backgroundColor == Color.pink) {
	bgc_pink.setState(true);
    } else if (backgroundColor == Color.orange) {
	bgc_orange.setState(true);
    }

    // added for deprecation
    bgc_white.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
	backgroundColor = Color.white;
      }
    });
    bgc_yellow.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
	backgroundColor = Color.yellow;
	}
    });
    bgc_pink.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
	backgroundColor = Color.pink;
	}
    });
    bgc_orange.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
	backgroundColor = Color.orange;

	}
    });


    Panel paneldrawing = new Panel();
    paneldrawing.add(new Label("Drawing Color:"));
    paneldrawing.add(lineColor = new TextField("",8));
    paneldrawing.setLayout(new GridLayout(1,5));
    CheckboxGroup g = new CheckboxGroup();
    paneldrawing.add(object_black = new Checkbox("Black",g, false));
    paneldrawing.add(object_red = new Checkbox("Red", g, false));
    paneldrawing.add(object_gray = new Checkbox("Gray", g, false));  
    paneldrawing.add(object_blue  = new Checkbox("Blue", g, false));

    objectColor=parentframe.drawingColor;
    if (objectColor == Color.black) {
	object_black.setState(true);
    } else if (objectColor == Color.red) {	
	object_red.setState(true);
    } else if (objectColor == Color.gray) {
	object_gray.setState(true);
    } else if (objectColor == Color.blue) {
	object_blue.setState(true);
    }

    // added for deprecation
    object_black.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
	objectColor = Color.black;
	}
    });
    object_red.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
	objectColor = Color.red;
	}
    });
    object_gray.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
	objectColor = Color.gray;
	}
    });
    object_blue.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
	objectColor = Color.blue;
	}
    });

    /*
    Panel p4 = new Panel();
    p4.setLayout(new GridLayout(1,5));
    p4.add(new Label("Line Style:"));    
    p4.add(lineStyle = new TextField("",8));
    CheckboxGroup gLine = new CheckboxGroup();
    p4.add(line_solid = new Checkbox("solid",gLine, true));
    p4.add(line_dotted = new Checkbox("dotted", gLine, false));
    p4.add(line_dashdot = new Checkbox("dashdot", gLine, false));  
    p4.add(line_dashed  = new Checkbox("dashed", gLine, false));        
    add(p4);
    */
    
    
    add(panelt);
    add(panely);
    add(panelbg);
    add(paneldrawing);
    //add(p4);
    
    Panel p4 = new Panel();
    Button b;
    b = new Button("Apply To Future");
    p4.add(b);
    b.addActionListener(this);

    b = new Button("Apply To All");
    p4.add(b);
    b.addActionListener(this);

    b = new Button("Cancel");
    p4.add(b);
    b.addActionListener(this);

    add(p4);
    setSize(600, 180);
  }

  private void setValues(ActionEvent evt) {
      tmax = Double.valueOf(t_max.getText().trim()).doubleValue();
      tmin = Double.valueOf(t_min.getText().trim()).doubleValue();
      // todo: put in warning about out of bound conditions
      if (tmin<0.0) {tmin=parentframe.tmin;}
      if (tmax>parentframe.system.getEndTime()) {tmax=parentframe.tmax;}

      ymax = Double.valueOf(y_max.getText().trim()).doubleValue();
      ymin = Double.valueOf(y_min.getText().trim()).doubleValue();
      // todo: put in warning about out of bound conditions

      parentframe.tmax=tmax;
      parentframe.tmin=tmin;
      parentframe.ymax=ymax;
      parentframe.ymin=ymin;
      parentframe.backgroundColor=backgroundColor;
      parentframe.drawingColor=objectColor;
      dispose();
  }

  public void actionPerformed(ActionEvent evt) {
    if (evt.getActionCommand().equals("Cancel")) {
      dispose();
      return;
    } else if (evt.getActionCommand().equals("Apply To Future")) {
	setValues(evt);
      return;
    } else if (evt.getActionCommand().equals("Apply To All")) {
	setValues(evt);

      // now nslUpdateBuffers all canvases
      Vector canvas_list = ((NslFrame)getParent()).getCanvasList();

      // todo: we will want to let the user change one of the settings, say tmax
      // but the other may differ from canvas to canvas and the user
      // does not want these to change.
      for(int i=0; i<canvas_list.size(); i++) {
	NslCanvas ndc = (NslCanvas)canvas_list.elementAt(i);
	Vector ndc_variable_list = ndc.get_variable_list();

	for(int j=0; j<ndc_variable_list.size(); j++) {
	  NslVariable ndv = (NslVariable)ndc_variable_list.elementAt(j);
	  ndv.set_tmin(tmin);
	  ndv.set_tmax(tmax);

	  ndv.set_ymin((float)ymin); //todo: change to double
	  ndv.set_ymax((float)ymax);

	  // todo: add the other color settings
	  ndv.info.setColor(objectColor);
	}
	ndc.update();
      }  
    } 
  }

  //private NslCanvas currentCanvas;
  private TextField t_max, t_min;
  private TextField y_max, y_min;
  private TextField lineColor, bgcolor, lineStyle;

  //private Checkbox line_solid, line_dotted, line_dashdot, line_dashed;

  private Checkbox bgc_white,bgc_yellow, bgc_pink, bgc_orange;
  private Color backgroundColor=null;

  private Checkbox object_black, object_red, object_gray, object_blue;
  private Color objectColor=null;

  private NslFrame parentframe=null;
  
  private  double tmin = (double)0.0; //real vars stored in NslFrame
  private  double tmax = (double)1000.0;

  private  double ymin = (double)-1000.0; //real vars stored in NslFrame
  private  double ymax = (double)1000.0;
}





