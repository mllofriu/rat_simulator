/* SCCS  %W% --- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.


package nslj.src.display;

import java.lang.*;
import java.lang.reflect.*;
import java.awt.*;
import java.util.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import java.awt.event.*;

/**
 * The Nsl Frame
 *
 * @version 1.0
 * @author  Danjie Pan
 */

public class NslFrame extends Frame implements ActionListener, ItemListener
{
  Color color;
  NslVariableInput variable_input_window=null;


//ERHMOD
  public NslFrame() { 
    System.err.println("Error: NslFrame: Should not call this constructor");
    //NslCanvas.Frame = this;
    NslOutOfBoundsBezier.frame = this;
  }

  public NslFrame(NslModule module,NslSystem sys) {
    //String frameName;
    frameName="";
    if (module!=null) {
	    frameName=module.nslGetName();
    }
    if (frameName.equals("")) 
	{
	    setTitle(".nsl.NslOutFrame");
	} 
    else {
	    frameName=".nsl."+frameName;
	    setTitle(frameName);
	}
		

    addWindowListener(new WindowAdapter() 
	{
	      public void windowClosing(WindowEvent e) { 
		system.remove((NslFrame) e.getWindow()); 
		dispose(); 
	     }
	}
    );  //keep for (new WindowAdapter() 



    MenuBar mbar = new MenuBar();
    Menu m;
    NslRadioMenu columnsmenu;
    MenuItem fomi; //frame options
    MenuItem fpmi; //frame print


    // Frame
    m = createMenu("Frame", new String[] { "New Canvas", "Remove Canvas"});
    m.addSeparator(); //---------------
    columnsmenu = new NslRadioMenu("Columns", new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
    columnsmenu.addItemListener(new ColumnAdapter());
    m.add(columnsmenu);
    fomi=new MenuItem("Frame Options");
    m.add(fomi);
    fpmi = new MenuItem("Frame Print");
    m.add(fpmi);
    m.addSeparator(); //---------------
    m.add(new MenuItem("Close"));
    m.addActionListener(new DisplayFrameActionAdapter(this));
    mbar.add(m);

    //------------------------
    // Canvas
    // all of these menu options need to be disabled until a canvas is selected
    m = new Menu("Canvas");
    //m = createMenu("Canvas", new String[] {"Zoom","Export Data"});
    zmi=new MenuItem("Zoom");
    zmi.setEnabled(false);
    m.add(zmi);
    typeMenu = new NslRadioMenu("Change Type", new String[] {"Area", "Bar", "Dot", "Image", "Spatial", "String", "Temporal"} );
    m.add(typeMenu);
    typeMenu.addItemListener(this);
    typeMenu.setEnabled(false);
    comi=new MenuItem("Canvas Options");
    comi.setEnabled(false);
    m.add(comi);
    cpmi = new MenuItem("Canvas Print");
    cpmi.setEnabled(false);
    m.add(cpmi);
    emi=new MenuItem("Export Data");
    emi.setEnabled(false);
    m.add(emi);

    m.addActionListener(new DisplayCanvasActionAdapter(this)); 
    mbar.add(m);

    //------------------------
    // Help
    m = new Menu("Help");
    mbar.add(m);
    mbar.setHelpMenu(m);
    
//98/9/9 aa took out - will make like add_graph
/*        
        Menu r = new Menu("Reports");
        Menu at = new Menu("Apply_To");
        at.add(new MenuItem("Current_Window_Selected"));
        at.add(new MenuItem("All_Visible_Windows"));
        r.add(at);
        
        Menu rt = new Menu("Report_Type");        
        rt.add(new MenuItem("Complete_History"));
        rt.add(new MenuItem("Bezier"));
        rt.add(new MenuItem("No_History"));
        r.add(rt);
        mbar.add(r);     
*/        
//     m = new Menu("Help");
//     // Test for multithread event handling
//     mbar.add(m);
        
        //finally put up the menu bar
    setMenuBar(mbar);

    pStatus = new Panel();
    pStatus.setBackground(Color.lightGray);
    pStatus.setLayout(new FlowLayout(FlowLayout.LEFT));
    pStatus.add(new Label("End Cycle time: "));
    // This label would be used to display the simulation time.
    pStatus.add(new Label("0.0 "));
    pStatus.add(new Label("Finished Cycles:"));
    pStatus.add(new Label("0 "));
    pStatus.add(new Label("Finished Epochs:"));
    pStatus.add(new Label("0 "));
    add("South",pStatus);
    Dimension d = pStatus.getSize();
    
    drawPanel = new NslPanel(this);
//    act_model= module;
//    if (module != null) {
//      module.nslAddModelPlots(this);
//        System.err.println("Calling InitWindow");
//        module.initWindow(); 
//    } else {
//       System.err.println("Error: NslFrame: Need a NslModule.");     
//    }

/*  
    int plotCount = drawPanel.getComponentCount();
    if (plotCount > 0) {
      int colCount = (plotCount > 2) ? 3 : plotCount;
      drawPanel.setColumns(colCount);
      ((CheckboxMenuItem) columnsmenu.getItem(colCount - 1)).setState(true);
    } else 
      drawPanel.setColumns(1);
*/
    
    
    add("Center",drawPanel);

    if (sys!=null) { // setting tmax just as soon as we know system not null
	tmax=sys.getEndTime(); 
	//System.out.println("NslFrame:tmax:"+tmax);
    }

  } // end constructor

  private Menu createMenu(String name, String[] items) {
    Menu m = new Menu(name);
    for (int i = 0; i < items.length; i++) 
      m.add(new MenuItem(items[i]));

    return m;
    }

  public NslFrame getFrame(ActionEvent evt) {
    MenuContainer menu =(MenuContainer) (evt.getSource());
  
    MenuBar top = null;
    NslFrame frame = null;
    
    while (!(menu instanceof MenuBar)) {
      menu = ((MenuComponent) menu).getParent();
    }
    frame = (NslFrame) (((MenuBar) menu).getParent());
    return frame;
  }

  public void actionPerformed(ActionEvent evt) { // for double click servicing
    try {
      drawPanel.zoomCanvas();
    } catch (Exception e) {
      System.err.println(e.toString());
    }
  }

  public void itemStateChanged(ItemEvent evt) {
    CheckboxMenuItem item = (CheckboxMenuItem) evt.getItemSelectable();
    String type = item.getLabel();
    
    try {
      drawPanel.changeCanvas(type);
      ((NslRadioMenu) item.getParent()).selectItem(type);
    } catch (Exception e) {
      System.out.println("#Warning: NslFrame: " + e.toString());
    }
  }

  public void plotSelectionChanged(String type) {
    if (type.equals("")) {
      typeMenu.setEnabled(false);
      comi.setEnabled(false); //canvas options
      cpmi.setEnabled(false); //canvas print
      zmi.setEnabled(false); //zoom
      emi.setEnabled(false); //export
    } else {
      typeMenu.setEnabled(true);
      typeMenu.selectItem(type);
      comi.setEnabled(true); //canvas options
      cpmi.setEnabled(true); //canvas print
      zmi.setEnabled(true); //zoom
      emi.setEnabled(true); //export
    }
  }
 
/*  99/5/11 aa: took out
  private class PrintActionAdapter implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
      NslFrame frame = getFrame(evt);
      if (evt.getActionCommand().equals("Current Canvas")) {
      System.out.println("NslFrame: CurrentCanvas selected");
      	try {
	  NslCanvas c = drawPanel.getCurrentCanvas();
	  PrintJob pjob = getToolkit().getPrintJob(frame,c.canvas_name, null);
	  if (pjob != null) {          
	    Graphics pg = pjob.getGraphics();
	    
	    if (pg != null) {
	      c.printAll(pg);
	      pg.dispose(); // flush page
	    }
	    pjob.end();
	  }
  	} catch (NoCanvasSelectedException e) {
	  System.err.println(e.toString());
	}

    } else if (evt.getActionCommand().equals("All Canvases")) {
      System.out.println("NslFrame: All Canvases selected");
	PrintJob pjob = getToolkit().getPrintJob(frame,"Simulation", null);

	if (pjob != null)    {          
	  Graphics pg = pjob.getGraphics();

	  if (pg != null) {

	    frame.printAll(pg);
	    pg.dispose(); // flush page
	  }
	  pjob.end();
	}
      } 	  
    }
  }
*/

  private class DisplayFrameActionAdapter implements ActionListener {
    NslFrame frame;
    DisplayFrameActionAdapter(NslFrame f) {frame = f;}

    public void actionPerformed(ActionEvent evt) {
      if (evt.getActionCommand().equals("Close")) {
	system.remove(getFrame(evt));
	dispose();
      } else if (evt.getActionCommand().equals("New Canvas")) {
	variable_input_window = new NslVariableInput(frame);
	variable_input_window.show();
      } else  if (evt.getActionCommand().equals("Remove Canvas")) {
	try {
	  drawPanel.removeCanvas();
	} catch (NoCanvasSelectedException e) {
	  System.err.println(e.toString());
	}
      } else if (evt.getActionCommand().equals("Frame Options")) {
	    NslFrameProperty fp = new NslFrameProperty(frame);
	    fp.show();
      } else if (evt.getActionCommand().equals("Frame Print")) {
	PrintJob pjob = getToolkit().getPrintJob(frame,frame.frameName, null);
	if (pjob != null)    {          
	  Graphics pg = pjob.getGraphics();
	  if (pg != null) {
	    frame.printAll(pg);
	    pg.dispose(); // flush page
	  }
	  pjob.end();
	}
      }
    }
  }

  private class ColumnAdapter implements ItemListener {
    public void itemStateChanged(ItemEvent evt) {
      try {
	CheckboxMenuItem item = (CheckboxMenuItem) evt.getItemSelectable();
	String label = item.getLabel();
	int newColumns = Integer.parseInt(label);
	if (!drawPanel.setColumns(newColumns)) {
	  item.setState(false);
	  return;
	}
	((NslRadioMenu) item.getParent()).selectItem(label);
      } catch (Exception e) { 
	System.err.println("NslFrame:Exception: Invalid number format:"+e.toString());
      }
    }
  }  

/* 99/5/11 aa: took out and added DisplayFrameActionAdapter
  private class FrameOptionActionAdapter implements ActionListener {
    NslFrame frame;

    FrameOptionActionAdapter(NslFrame f) { frame = f;}

    public void actionPerformed(ActionEvent evt) {
      if (evt.getActionCommand().equals("Frame Options")) {
	    System.out.println("NslFrame:Frame Options selected.");
	    NslFrameProperty fp = new NslFrameProperty(frame);
	    fp.show();
      } else if (evt.getActionCommand().equals("Frame Print")) {
	PrintJob pjob = getToolkit().getPrintJob(frame,"Simulation", null);
	if (pjob != null)    {          
	  Graphics pg = pjob.getGraphics();
	  if (pg != null) {
	    frame.printAll(pg);
	    pg.dispose(); // flush page
	  }
	  pjob.end();
	}
      }
    }
  }
*/

  private class DisplayCanvasActionAdapter implements ActionListener {
    NslFrame frame;

    DisplayCanvasActionAdapter(NslFrame f) { frame = f;}

    public void actionPerformed(ActionEvent evt) {
      try {
	  if (evt.getActionCommand().equals("Canvas Options")) {
	    drawPanel.setCanvasProperties();
	  } else if (evt.getActionCommand().equals("Export Data")) {
	    // 	NslCanvas ndc = (NslCanvas)canvas_list.elementAt(current_canvas_index);
	  
	    NslOutFileProperty ndofp = new NslOutFileProperty(frame);
	    ndofp.show();
	
	  } else if (evt.getActionCommand().equals("Zoom")) {
	    try {
      		  drawPanel.zoomCanvas();
    		} catch (Exception e) {
      		  System.err.println("NslFrame:Exception: "+e.toString());
            }
	  } else if (evt.getActionCommand().equals("Canvas Print")) {
	    NslCanvas c = drawPanel.getCurrentCanvas();
	    PrintJob pjob = getToolkit().getPrintJob(frame,c.canvas_name, null);
	    if (pjob != null) {          
	       Graphics pg = pjob.getGraphics();
	    
	       if (pg != null) {
	         c.printAll(pg);
	         pg.dispose(); // flush page
	       }
	    pjob.end();
	  }
	}
      } catch (NoCanvasSelectedException e) {  //end try
	  System.out.println("NslFrame:Exception: No Canvas Selected");
	  System.out.println("NslFrame:Exception: " + e.toString());
      }  // end catch
    }
  }

    
  public static void nslSetSystem(NslSystem sys) {
    system=sys;
    interpreter = sys.getInterpreter();
    NslVariable.system = sys;
    NslVariableInput.system = sys;
  }

  public void startCycle() {
    //((Label) pStatus.getComponent(0)).setText("Simulation running at: ");
  }

  public void collect(double time) {
        // For displaying status
	((Label) pStatus.getComponent(1)).setText(String.valueOf(rounded(time))+" ");    

        //if (system.getCurTime()<system.getEndTime()) {
        //  ((Label) pStatus.getComponent(3)).setText(String.valueOf(system.getCurrentCycle())+" "); 
	//} else {
          ((Label) pStatus.getComponent(3)).setText(String.valueOf(system.getFinishedCycles())+" ");    
	//}

        //if (system.getCurrentEpoch()<=system.getEpochs()) {
        //  ((Label) pStatus.getComponent(5)).setText(String.valueOf(system.getCurrentEpoch())+" "); 
	//} else {
          ((Label) pStatus.getComponent(5)).setText(String.valueOf(system.getFinishedEpochs())+" ");    
	//}

	drawPanel.collect();
  }

  public void refresh() {
    double curtime=0.0;
    curtime=system.getCurTime();
    drawPanel.init();
    collect(curtime);
  }

  public void refreshEpoch() {
    double curtime=0.0;
   //System.err.println("Frame epoch");

    curtime=system.getCurTime();
    drawPanel.initEpoch();
    collect(curtime);
  }

  public NslCanvas getCurrentCanvas() {
    try {
      return drawPanel.getCurrentCanvas();
    } catch (NoCanvasSelectedException e) {
      System.out.println("NslFrame:Exception: " + e.toString());
      return null;
    }
  }

  public Vector getCanvasList() {
    return drawPanel.getCanvasList();
  }

  public void addDisplayCanvas(String name, 
			       NslVariableInfo varInfo, 
			       String plotType, 
			       boolean replace){
	    
//     if(repace_canvas_flag == 1)  {  //replace current canvas
//       ((NslCanvas)canvas_list.elementAt(current_canvas_index)).add_variable(var_sel_full_name, var_sel_info);
//     } else 
    if (!replace) 
            // append to the canvas_list
      try {
	drawPanel.addCanvas(name, varInfo, plotType, 0, 1);
	drawPanel.repaint();
      } catch (Exception e) {
	System.out.println("NslFrame:Exception: unknown plot:" + e.toString());
      }
      
  }
	
  public void add_variable(String varName, boolean replace, String plotType) {
    NslVariableInfo varInfo = getVarInfo(varName);
	    // 98/8/27 aa - in development
	    //pass name,dimensions, and plot type to queryPlotWizard
    queryPlotWizardA(varName, varInfo, replace, plotType);
  }//end add_variable
 


    /**
     * Get variable reference number and other properties from  Nsl System
     * @param   varName  The variable's full name
     * @return  A vector which contains variable's reference number and dimensions
     */

    public NslVariableInfo getVarInfo(String varName) {

	NslData var_sel = system.nslGetDataVar(varName);
	return getVarInfo(var_sel);
    }
  
    public NslVariableInfo getVarInfo(NslData var_sel) {
    
	NslVariableInfo info = null;

	if (var_sel==null){
	    System.err.println("Error: NslFrame: Variable not found ");
	} else if (var_sel instanceof NslNumeric4) {
	    if (var_sel instanceof NslFloat4)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.FLOAT,   4);
	    else if (var_sel instanceof NslDouble4) 
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.DOUBLE,  4);
	    else if (var_sel instanceof NslInt4)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.INT,     4);
	    else if (var_sel instanceof NslBoolean4)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.BOOLEAN, 4);

	    /*info.setDimension(0,((NslNumeric4)var_sel).getSize1());
	    info.setDimension(1,((NslNumeric4)var_sel).getSize2());
	    info.setDimension(2,((NslNumeric4)var_sel).getSize3());
	    info.setDimension(3,((NslNumeric4)var_sel).getSize4());*/
	}
else if (var_sel instanceof NslNumeric3) {
	    if (var_sel instanceof NslFloat3)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.FLOAT,   3);
	    else if (var_sel instanceof NslDouble3) 
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.DOUBLE,  3);
	    else if (var_sel instanceof NslInt3)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.INT,     3);
	    else if (var_sel instanceof NslBoolean3)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.BOOLEAN, 3);

	    /*info.setDimension(0,((NslNumeric3)var_sel).getSize1());
	    info.setDimension(1,((NslNumeric3)var_sel).getSize2());
	    info.setDimension(2,((NslNumeric3)var_sel).getSize3());
*/

	} else if (var_sel instanceof NslNumeric2) {
	    if (var_sel instanceof NslFloat2)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.FLOAT,   2);
	    else if (var_sel instanceof NslDouble2) 
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.DOUBLE,  2);
	    else if (var_sel instanceof NslInt2)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.INT,     2);
	    else if (var_sel instanceof NslBoolean2)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.BOOLEAN, 2);


	    /*info.setDimension(0,((NslNumeric2)var_sel).getSize1());
	    info.setDimension(1,((NslNumeric2)var_sel).getSize2());

*/
	} else if(var_sel instanceof NslNumeric1) {
	    if (var_sel instanceof NslFloat1)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.FLOAT,   1);
	    else if (var_sel instanceof NslDouble1) 
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.DOUBLE,  1);
	    else if (var_sel instanceof NslInt1)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.INT,     1);
	    else if (var_sel instanceof NslBoolean1)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.BOOLEAN, 1);

	    //info.setDimension(0,((NslNumeric1)var_sel).getSize1());
	} else if ( var_sel instanceof NslNumeric0) {
	    if (var_sel instanceof NslFloat0)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.FLOAT,   0);
	    else if (var_sel instanceof NslDouble0) 
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.DOUBLE,  0);
	    else if (var_sel instanceof NslInt0)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.INT,     0);
	    else if (var_sel instanceof NslBoolean0)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.BOOLEAN, 0);
	    else if (var_sel instanceof NslString0)
		info = new NslVariableInfo(var_sel, var_sel.nslGetName(), NslVariableInfo.STRING,  0);
	}
    
	return info;
    }
    
    /**
     * Add a canvas into the panel given a NslVariable.
     * @param variable to be displayed, its range and its type
     * @return new created canvas.
     */
     
    public NslCanvas addPlot(NslData var, double minv, double maxv, String type) {

	NslCanvas c = null;
	NslVariableInfo vi = getVarInfo(var);

	try {
	    c = drawPanel.addCanvas(var.nslGetName(), vi, type, minv, maxv);
	} catch (Exception e) {
	    System.err.println("NslFrame: addPlot Error: Plot could not be created. " + var.nslGetName());
	    System.err.println("Error: "+e.toString());
	    e.printStackTrace();
	} finally {  
	    return c;
	}  
    }
 
  public void queryPlotWizardA(String name, NslVariableInfo varInfo, boolean replace, String plotType) {
    //need to know what type of plot they want
        // 98/9/4 aa
        // varInfo can change
    if (varInfo==null) {
      System.err.println("NslFrame:Error: " + name + ": No variable information.");
    return;
    }

    int dimsize = varInfo.getCountDimensions(); 	   
	    
    if (plotType.equals("Area") ||
	 plotType.equals("Bar") ||
	 plotType.equals("Image") ||
	 plotType.equals("Spatial") ||
	 plotType.equals("Temporal")) {
      if (dimsize == 0 || dimsize == 1 || dimsize == 2)
	addDisplayCanvas(name, varInfo, plotType, replace);
      else {
	NslDimInput dm = 
	  new NslDimInput(this, name, varInfo, 
				 plotType, replace);
	dm.show();
      }
    } else if ((plotType.equals("Dot") || 
		plotType.equals("String")) && 
	       dimsize == 2) {
	        // two-dimensional data only
      addDisplayCanvas(name,varInfo,plotType,replace);
    } else if (plotType.equals("InputImage")) {
      addDisplayCanvas(name, varInfo, plotType, replace);
    } else if (plotType.equals("NumericEditor")) {
      addVariableInfo(name, varInfo);
    } else {
      System.err.println("Error: bad plot type :" + plotType);
    } 
    
  } //end queryDataWizardA

   /*---------------------------------------------------------*/
   /*ERH:if you need n decimal points call this with v=10^n.  */
  private String rounded(double t,double v,int dec) {
    String s=Double.toString(((long)(0.5+t*v))/v);
    //unfortunately s may still contain round off stuff...
    int ix=s.indexOf(".");
    if (ix==-1) return s;
    if (s.length()-ix-1 < dec) dec=s.length()-ix-1;
    return s.substring(0,ix)+s.substring(ix,ix+dec+1);
  }

  private String rounded(double t) { return rounded(t,1000,3);}


    // called from nslm code

  public void  addPlot(String name) {
    addPlot(name,0.0,1.0,"Area");
  }
  
  public void  addVariableInfo(NslNumeric var) {
    try {
      NslVariableInfo vi = getVarInfo(var);
      if (vi!=null) {
          drawPanel.addVariable(var.nslGetName(), vi);
      }
    } catch (Exception e) {
      System.err.println("Error: NslFrame: Variable info could not be created. " +var.nslGetName());
      System.err.println("Error: "+e.toString());
	    e.printStackTrace();    } 
  }
  
  public void  addVariableInfo(String name) {
    
    try {
      NslVariableInfo vi = getVarInfo(name);
      if (vi!=null) {
          drawPanel.addVariable(name, vi);
      }
    } catch (Exception e) {
      System.err.println("Error: NslFrame: Variable info could not be created. " + name);
      System.err.println("Error: "+e.toString());
	    e.printStackTrace();    }   

  }
  
  public void  addVariableInfo(String name, NslVariableInfo vi) {
    
    try {
      if (vi!=null) {
          drawPanel.addVariable(name, vi);
      }
    } catch (Exception e) {
      System.err.println("Error: NslFrame: Variable info could not be created. " + name);
      System.err.println("Error: "+e.toString());
	    e.printStackTrace();    }   

  }    
  public NslCanvas addPlot(String name, double minv,double maxv,String type) {

    NslCanvas c = null;
    NslVariableInfo vi = getVarInfo(name);
    try {
      c = drawPanel.addCanvas(name, vi, type, minv, maxv);
   } catch (Exception e) {
      System.err.println("Error: NslFrame: Plot could not be created. " + name);
      System.err.println("Error: "+e.toString());
// should not need.
/*      System.out.println("NslFrame: addPlot: Error: Plot could not be created. " + name);
      System.out.println("Error: "+e.toString());*/

	    e.printStackTrace();
    } finally {  
      return c;
    }  
    
  }  //end addPlotsccs
  
  
  public NslCanvas  addPlot(String windowName, String name, double minv,double maxv,String type) {
    NslCanvas c = null;
    NslVariableInfo vi = getVarInfo(name);
    try {
      c = drawPanel.addCanvas(name, vi, type, minv, maxv);
      c.setWindowName(windowName);
      
   } catch (Exception e) {
      System.err.println("Error: NslFrame: Plot could not be created. " + name);
      System.err.println("Error: "+e.toString());
// should not need.
/*      System.out.println("NslFrame: addPlot: Error: Plot could not be created. " + name);
      System.out.println("Error: "+e.toString());*/

	    e.printStackTrace();
    } finally {  
      return c;
    }
  }  //end addPlotsccs
  
  public NslCanvas addPlot(String windowName, NslNumeric var, double minv,double maxv,String type) {
    NslCanvas c = null;
    NslVariableInfo vi = getVarInfo(var);
    try {
      c = drawPanel.addCanvas(var.nslGetName(), vi, type, minv, maxv);
      c.setWindowName(windowName);
      return c;
   } catch (Exception e) {
      System.err.println("Error: NslFrame: Plot could not be created. " + var.nslGetName());
      System.err.println("Error: "+e.toString());
// should not need.
/*      System.out.println("NslFrame: addPlot: Error: Plot could not be created. " + var.nslGetName());
      System.out.println("Error: "+e.toString());*/

	    e.printStackTrace();
    } finally {  
      return c;
    }  
    
  } 
      
 // public void  addUserPlot(NslNumeric var, double minv,double maxv,String type) {
  public NslCanvas  addUserPlot(NslNumeric var, double minv,double maxv,String type) {
    String name="Independent";
    //NslVariableInfo vi = getVarInfo(var);
    NslVariableInfo vi = null;
    if (var!=null) {
    	vi = getVarInfo(var);
	name = var.nslGetName();
    }
    try {
      //drawPanel.addUserCanvas(var.nslGetName(), vi, type, minv, maxv);
      return drawPanel.addUserCanvas(name, vi, type, minv, maxv);
   } catch (Exception e) {
      System.err.println("Error: NslFrame: Plot could not be created. " + var.nslGetName());
      System.err.println("Error: "+e.toString());
	    e.printStackTrace();
    }   
    return null;
  }    

  
  public NslCanvas addUserPlot(String windowName, NslNumeric var, double minv,double maxv,String type) {
    NslCanvas c = null;
    NslVariableInfo vi = getVarInfo(var);
    try {
      c = drawPanel.addUserCanvas(var.nslGetName(), vi, type, minv, maxv);
      c.setWindowName(windowName);
      return c;
   } catch (Exception e) {
      System.err.println("Error: NslFrame: Plot could not be created. " + var.nslGetName());
      System.err.println("Error: "+e.toString());
	    e.printStackTrace();
    } finally {  
      return c;
    }  
    
  }
  
  public void addComponent(Component c) {
  	drawPanel.add(c);      
  	int plotCount = drawPanel.getComponentCount();
        if (plotCount > 0) {
      	   int colCount = (plotCount > 2) ? 3 : plotCount;
      	   drawPanel.setColumns(colCount);
        } else {
      	   drawPanel.setColumns(1);
        }
  	drawPanel.validate();
  }
  
  public void setColumns(int columns) {
  	drawPanel.setColumns(columns);
  }
  
  public void setRows(int rows) {
  	drawPanel.setRows(rows);
  }
  
  public void setFontName(String name) {
      //System.out.println("NslFrame"+"Font: "+name);
      fontName = name;
  }
  
  public String getFontName() {
      return fontName;  
  }
  
  public void setBackgroundColor(String name) {
      //System.out.println(""NslFrame:Bg  : "+name);
      bg = name;      
      //setBackground(NslColor.getColor(bg));
  }
  
  public String getBackgroundColor() {
      return bg;  
  }
  
  public void setForegroundColor(String name) {
      //System.out.println(""NslFrame:Fg  : "+name);
      fg = name;
  }
  
  public String getForegroundColor() {
      return fg;  
  }
  
  public NslPanel getPanel() {
  	return drawPanel;
  }

  public void setInputFrame() {
	outputFrame = false;
  }

  public boolean isOutputFrame() {
	return outputFrame;
  }

  public NslSystem getNslSystem() {
	return system;
  }

  private boolean outputFrame = true;
  
  public static NslSystem system;
  static NslInterpreter interpreter;
  public String frameName;
  private String fontName, fg, bg;
  private NslPanel drawPanel;
  private Panel pStatus;
  private NslModule act_model;
  private NslRadioMenu typeMenu;
  private MenuItem comi; //canvas options
  private MenuItem cpmi; //canvas print
  private MenuItem zmi; //zoom
  private MenuItem emi; //export

  // time min and time max
  public double tmin=0.0;
  public double tmax=25; 
  public double ymin=-1000;
  public double ymax=1000;
  public Color drawingColor=Color.black;
  public Color backgroundColor=Color.white;
  public Color gridColor=Color.black;  //todo: add to displayFrameProperty later
}

