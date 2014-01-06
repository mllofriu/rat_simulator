/* SCCS  @(#)NslVariableInput.java	1.14---09/20/99--19:23:04 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslVariableInput.java,v $
// Revision 1.5  1997/11/06 03:15:11  erhan
// nsl3.0.b
//
// Revision 1.4  1997/05/09 22:30:27  danjie
// add some comments and Log
//
//--------------------------------------

package nslj.src.display;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import java.awt.event.*;


class NslVariableInput extends Dialog {
  TextArea mTextArea;
  public static NslSystem system;
  private String plotType = "Area";
  private CheckboxGroup checkboxgroup1;
  public Checkbox current_canvas;//part of group
  public Checkbox new_canvas; //part of group
  private String[] var_selected_name;
  private TextField variablename;
  private TextField position;
  boolean replace = false;
  private int XY_var;
  private Panel lPanel;
  Tree  mTree;
  private Image lDefault;
  private Image lFile;
  private Image lCollapse;
  private Choice graphChoice;
    
  public NslVariableInput(NslFrame parent) {
    super(parent, "Select Plot",false);
    
    var_selected_name = new String[2];
    XY_var = 0;
	    
    // variable name
    Panel p1 = new Panel();
    //98/9/22 aa: changed from 3,2 to 6,2 and added more instructions
    p1.setLayout(new GridLayout(6,2));
    p1.add(new Label("Select the variable either by"));
    p1.add(new Label("typing or tree selection."));
    p1.add(new Label("Expanded variable name:"));
    p1.add(variablename = new TextField("",8));
    checkboxgroup1 = new CheckboxGroup();
    p1.add(new_canvas = new Checkbox("New Canvas",checkboxgroup1, true));
    new_canvas.addItemListener(new ItemListener() { 
      public void itemStateChanged(ItemEvent evt) { 
	replace = false;
      }
    });
    /*                12*/    
    p1.add(current_canvas = new Checkbox("Current Canvas", checkboxgroup1, false));
    current_canvas.addItemListener(new ItemListener() { 
      public void itemStateChanged(ItemEvent evt) { 
	replace = true;
      }
    });
    /*                12*/    
    p1.add(new Label("  Select Graph type:"));
    //graphChoice is defined above but instantiated here
    graphChoice = new Choice();  //plot choices
    
    if (parent.isOutputFrame()) {
	plotType = "Area";

        graphChoice.add("Area");	//default
        graphChoice.add("Bar");
        graphChoice.add("Dot");
        graphChoice.add("Image");
        graphChoice.add("Spatial");
        graphChoice.add("String");
        graphChoice.add("Temporal");
       //graphChoice.add("X_Y");
    } else {
	plotType = "NumericEditor";

        graphChoice.add("NumericEditor");	//default
        graphChoice.add("InputImage");
    }

    p1.add(graphChoice);
    graphChoice.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
	plotType = evt.getItem().toString();
      }
    });
    //98/9/22 aa: added instructions
    p1.add(new Label("  Tree selection below."));
    p1.add(new Label("  "));
    p1.add(new Label("  Click + to find name."));
    p1.add(new Label("  "));
    
    
    add("North",p1);
    
    // creat tree structure
    // load images
    lDefault =Toolkit.getDefaultToolkit().getImage("nslj/src/display/images/default.gif");
    lFile =Toolkit.getDefaultToolkit().getImage("nslj/src/display/images/file.gif");
    lCollapse =Toolkit.getDefaultToolkit().getImage("nslj/src/display/images/collapse.gif");
    lPanel = new Panel();
    lPanel.setLayout(new GridLayout(0,1));
    
    mTextArea = new TextArea();
	
    mTree = new Tree();

    TreeNode lSuper;
    TreeNode lSub;
    TreeNode lSubSub;
    
    NslModule topModule= system.nslGetModelRef();

    //System.out.println("debug: NslVariableInput: Model Name:"+topModule.nslGetName());

    lSuper = new NslTreeNode(topModule.nslGetName(), lDefault, lCollapse, this);
    mTree.addTreeNode(lSuper);
    treeBuilder(topModule, lSuper);

    mTree.refresh();
    lPanel.add(mTree);
    // lPanel.add(mTextArea);
    add("Center", lPanel);
    
    Panel p2 = new Panel();
    Button b = new Button("Close Window");
    p2.add(b);
    b.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
	dispose();
      }
    });
    
    //p2.add(new Button("Ok_XY"));

    b = new Button("Next");
    p2.add(b);
    b.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
	String variableName = mTree.variableSelect;
	if (variableName==null) {
	  System.out.println("#Warning: select a leaf variable before hitting next.");
	}
	((NslFrame)getParent()).add_variable(variableName, replace, plotType);
      }
    });
    add("South",p2);
    setSize(340,520);
    
  }

  // Find the submodules recursively until leaf module reached
  private void treeBuilder(NslModule parentModule, TreeNode lSuper) {

    Vector childrenModuleVector;
    TreeNode lSub;
    NslModule nm;
    Vector variableList;
    String varName;
    int varCount=0;
    int childCount=0;

    nm=parentModule;
    variableList = nm.nslGetDataVarsVector();
    varCount=variableList.size();

    childrenModuleVector = nm.nslGetModuleChildrenVector();
    childCount=childrenModuleVector.size();

    //System.out.println("NslVariableInput:vars for "+nm.nslGetName()+" is "+varCount);
    // do  NSL variables
    for(int j=0; j<varCount; j++) {
      varName=((NslNumeric)variableList.elementAt(j)).nslGetName();
      //System.out.println("NslVariableInput:var: "+varName);
      lSub = new NslTreeNode(varName,lFile, this);
      mTree.addTreeNode(lSuper, lSub);
    }
    // 99/5/13 aa: I guess modules are not variables
    // thus we must now list the modules    
    //System.out.println("NslVariableInput:children for "+nm.nslGetName()+" is "+childCount);
    for (int i=0; i<childCount; i++)
    {
      nm = (NslModule)childrenModuleVector.elementAt(i);
      //System.out.println("NslVariableInput:child: " + nm.nslGetName());
      lSub = new NslTreeNode(nm.nslGetName(),lDefault, lCollapse, this);
      mTree.addTreeNode(lSuper, lSub);
      // if module has variables or children
      //if (((nm.getVariableVector()).size()>0)||
      //if  ((nm.nslGetModuleChildrenVector()).size()>0)) {
        treeBuilder(nm, lSub);
      //}
    }
  }
  
  public String xvariable() { return var_selected_name[0]; }
  
  public String yvariable() { return var_selected_name[1]; }
  
  public int inputvariableindex() { return(XY_var-1); }
  
}//end class
