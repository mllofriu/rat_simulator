/* SCCS  @(#)NslExecutive.java	1.34---09/02/99--21:33:10 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.io.*;
import java.lang.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.lang.reflect.*;

import nslj.src.lang.*;
import nslj.src.system.*;
import nslj.src.cmd.*;
import nslj.src.nsls.struct.*;


/** NSL Executive window
    @author Nikunj Mehta
*/

public class NslExecutive extends Frame 
    implements ActionListener, ClipboardOwner {

    Frame myFrame;
    Vector menuOptions;
    
    static final public int trainIndex=5;
    static final public int runIndex=12;

    Menu protocol;
    Vector protocols;
    Clipboard clipboard;
    
    public NslExecutive (NslSystem s) {
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { 
      	dispose(); 
      	if (!NslSystem.applet) {
      	    System.exit(0);
      	}
      }
    });
    
    system = s;
    s.setExecutive(this);
    setTitle("NSL Executive");
    setBounds(0,0,500,250);
    MenuBar mbar = new MenuBar();
    Menu m,md,sm;
    myFrame=this;
    NslThreadCommands.setExecutive(this);
    
    menuOptions = new Vector(10);
    protocols = new Vector();
    
    NslSystemActionAdapter systemListener = new NslSystemActionAdapter(this);
    NslEditActionAdapter   editListener   = new NslEditActionAdapter();

    // System
    m = createMenu("System", new String[] {"Nsls file..."}, false);
    m.addActionListener(systemListener);
    mbar.add(m);
    
    sm = createMenu("Set", new String[] {"NumTrainEpochs","TrainEndTime","TrainDelta", 
    	"NumRunEpochs","RunEndTime","RunDelta",
    	 "DiffApproxMethod", "DiffDelta","DiffTimeConstant","DisplayDelta", "Logging"}, 
    	false);
    sm.addActionListener(systemListener);	
    m.add(sm);
    m.add(new MenuItem("Exit"));

    // Edit
    m = createMenu("Edit", new String[] {"Select","Copy","Paste"}, true);
    m.addActionListener(editListener);
    mbar.add(m);
    
    // Protocol
    protocol = createMenu("Protocol", new String[] {"Manual","Separator"}, true);
    protocol.addActionListener(new NslProtocolActionListener());
    mbar.add(protocol);

    // Simulation
    m = createMenu("Simulation", new String[] {"InitSys","InitModule","TrainAndRunAll","EndModule","EndSys"}, true);
    m.addActionListener(this);
    mbar.add(m);

    // Simulation menu commands run in the following sequence
    // "Initialize", "Run", "Break","Continue", "Step" 
    // the sequence of commands affects a lot of the code!!!
    // To change the simulation menu, change code for enabling menuitems

    /*m = createMenu("Train", 
    	new String[] {"InitTrain", "SimTrain", "EndTrain", "Separator", 
    	"Train", "DoTrainEpochTimes", "Separator", "Break", "Continue"}, true);
    m.addActionListener(this);
    sm = createMenu("Step", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(this);
    Vector menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);
   
    m.add(sm);
    mbar.add(m);*/

    m = createMenu("Train", 
    	new String[] { "InitTrainEpoch", "InitTrain", "SimTrain", "EndTrain", "EndTrainEpoch", "Separator", 
    	"Train", "DoTrainEpochTimes", "Separator", "Break", "BreakModules", "BreakCycles", "BreakEpochs", "Continue", "ContinueModule", "ContinueCycle", "ContinueEpoch"}, true);
    m.addActionListener(this);

    /*sm = createMenu("ContinueUntilModule", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslContinueModuleActionListener());
    Vector menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);
    m.add(sm);

    sm = createMenu("ContinueUntilCycle", 
    	new String[] { "1.0", "2.0", "3.0", "4.0", "5.0", "10.0", "20.0", "30.0", "40.0", "50.0" }, 
    	true);
    sm.addActionListener(new NslContinueCycleActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);
    m.add(sm);

    sm = createMenu("ContinueUntilEpoch", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslContinueEpochActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);    
    m.add(sm);*/

    sm = createMenu("StepModule", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslStepModuleActionListener());
    Vector menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);
    m.add(sm);

    sm = createMenu("StepCycle", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslStepCycleActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);
    m.add(sm);

    sm = createMenu("StepEpoch", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslStepEpochActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);    
    m.add(sm);

    mbar.add(m);
    
    m = createMenu("Run", 
    	new String[] { "InitRunEpoch", "InitRun", "SimRun", "EndRun", "EndRunEpoch", "Separator", 
    	"Run", "DoRunEpochTimes", "Separator", "Break", "BreakModules", "BreakCycles", "BreakEpochs", "Continue", "ContinueModule", "ContinueCycle", "ContinueEpoch"}, true);
    m.addActionListener(this);

    /*sm = createMenu("ContinueUntilModule", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslContinueModuleActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);
    m.add(sm);

    sm = createMenu("ContinueUntilCycle", 
    	new String[] { "1.0", "2.0", "3.0", "4.0", "5.0", "10.0", "20.0", "30.0", "40.0", "50.0" }, 
    	true);
    sm.addActionListener(new NslContinueCycleActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);
    m.add(sm);

    sm = createMenu("ContinueUntilEpoch", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslContinueEpochActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);    
    m.add(sm);*/

    sm = createMenu("StepModule", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslStepModuleActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);
    m.add(sm);

    sm = createMenu("StepCycle", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslStepCycleActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);
    m.add(sm);

    sm = createMenu("StepEpoch", 
    	new String[] { "1", "2", "3", "4", "5", "10", "20", "30", "40", "50" }, 
    	true);
    sm.addActionListener(new NslStepEpochActionListener());
    menuItems = new Vector();
    menuItems.addElement(sm);
    menuOptions.addElement(menuItems);    
    m.add(sm);

    mbar.add(m);
    
    // Display - may add input displays later
    md = createMenu("Display", new String[] { "New Output Frame", "New Input Frame"}, false);
    md.addActionListener(new NslActionAdapter());
    mbar.add(md);
    
    /*m = createMenu("Options", new String[] {"Run Time and Run Step Size"}, false);
    m.addActionListener(new OptionActionAdapter(this));
    mbar.add(m);*/

	// Add link to bring up HTML Help page
    m =createMenu("Help", new String[] {"How To","Command Help","Setup"}, false);
    mbar.add(m);
    mbar.setHelpMenu(m);
    
    setMenuBar(mbar);

    disable(trainIndex,2); // simRun
    disable(trainIndex,3); // endRun 
    disable(trainIndex,4); // endRunEpoch 
    disable(trainIndex,7); // break
    disable(trainIndex,8); // breakModule
    disable(trainIndex,9); // breakCycle
    disable(trainIndex,10); // breakEpoch
    disable(trainIndex,11); // continue
    disable(trainIndex,12); // continueModule
    disable(trainIndex,13); // continueCycle
    disable(trainIndex,14); // continueEpoch
    disable(trainIndex+2,0); // stepModule
    disable(trainIndex+4,0); // stepCycle
    disable(trainIndex+6,0); // stepEpoch
        
    disable(runIndex,2); // simRun
    disable(runIndex,3); // endRun 
    disable(runIndex,4); // endRunEpoch 
    disable(runIndex,7); // break
    disable(runIndex,8); // breakModule
    disable(runIndex,9); // breakCycle
    disable(runIndex,10); // breakEpoch
    disable(runIndex,11); // continue
    disable(runIndex,12); // continueModule
    disable(runIndex,13); // continueCycle
    disable(runIndex,14); // continueEpoch
    disable(runIndex+2,0); // stepModule
    disable(runIndex+4,0); // stepCycle
    disable(runIndex+6,0); // stepEpoch


    
    if (!NslSystem.applet) {
        clipboard = getToolkit().getSystemClipboard();
        disable(2,1); // Copy
    } else {
    	system.nslPrintln("Edit menu disabled for security reasons");
    	disable(2,0); // Select
    	disable(2,1); // Copy
    	disable(2,2); // Paste
    }
    

    setLayout(new GridLayout(1,1));
    add(shell = new NslShell(system.getInterpreter().executive));
    
    //shell = new NslShellThread(this, system);
    //shell.start();
  }

  public void enable(int m, int s){
    ((MenuItem)((Vector)(menuOptions.elementAt(m))).elementAt(s)).setEnabled(true);
  }
  
  public void disable(int m, int s){
    ((MenuItem)((Vector)(menuOptions.elementAt(m))).elementAt(s)).setEnabled(false);
  }
  
    private class Protocol {
        public String name;
        public NslModule module;
        public boolean methodFound = true;
        
        Protocol(String n, NslModule m) {
           name = n;
           module = m;
        }
        
    }  
    
    public boolean protocolInList(String name) {
    
    	Enumeration e = protocols.elements();
    
    	Protocol p;
    	String protocolName;
    	
    	while(e.hasMoreElements()) {
    	
    	    p = (Protocol) e.nextElement();
    	    protocolName = (String)p.name;
    	    
    	    if (name.equals(protocolName)) {
    	    	return true;
    	    }
    	}
    	
    	return false;
    }
    
    public void addProtocol(String name, NslModule module) {
    
    	if (!protocolInList(name)) {
    	    MenuItem m = new MenuItem(name);
    	    protocol.add(m);
    	    protocols.addElement(new Protocol(name,module));
    	    // We have to add it to the enable vector 
    	}
    }
    
    public void addProtocol(String name, String label, NslModule module) {
    
    	if (!protocolInList(name)) {
    	    MenuItem m = new MenuItem(label);
    	    m.setActionCommand(name);
    	    protocol.add(m);
    	    protocols.addElement(new Protocol(name,module));
    	    // We have to add it to the enable vector 
    	}
    }

    
  private Menu createMenu(String name, String[] items, boolean tearOff) {
    Menu m = new Menu(name,tearOff);
    
    Vector menuItems = new Vector();
    
    for (int i = 0; i < items.length; i++) 
      if  (items[i].equals("Separator")) {
        m.addSeparator();
      } else {
        MenuItem mi = new MenuItem(items[i]);
      	m.add(mi);
      	menuItems.addElement(mi);
      }
     
    menuOptions.addElement(menuItems);
    
    return m;
    }
    
  private class NslDotNslsFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
       return name.endsWith(".nsls");
    }
  }
  
  public void execProtocol(String command) {
      Enumeration e = protocols.elements();
      	    	
      Protocol p;
      NslModule module=null;
      while(e.hasMoreElements()) {
          p = (Protocol)e.nextElement();
          if (p.name.equals(command)) {
              try {
      	      	  String n = command+"Protocol";
	       	  Method m = p.module.getClass().getMethod(n,null);
      	       	  m.invoke(p.module,null);
      	       	  break;
      	      } catch (Exception ex) {
      	          if (p.methodFound) {
      	              System.out.println("Warning: Couldn't find method "+command+
      	                  "Protocol() in module "+p.module.nslGetRealName());	
      	          }
      	          p.methodFound = false;
	      }
          }
      }  
  }
  
    private class NslProtocolActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            MenuItem menu = ((MenuItem)evt.getSource());
      	    String command = evt.getActionCommand();
      	    
      	    if (command.equals("Manual")) {
      	    	// System.out.println("Manual mode");
      	    	command = "manual";
      	    } 
      	    system.nslSetProtocol(command);
        }
    }

    private class NslEditActionAdapter implements ActionListener {
    	
    	public void actionPerformed(ActionEvent evt) {
    	
    	   String command = evt.getActionCommand();
    	   if (command.equals("Paste")) {
    	       //System.out.println("Paste");
    	       if (!NslSystem.applet) {
    	       	   Executive.readingFile=true;
    	           Transferable contents = clipboard.getContents(this);
    	    
    	           if (contents != null)/* && DataFlavor.stringFlavor != null &&
    	               contents.isDataFlavorSupported(
    	        	    DataFlavor.stringFlavor))*/ {
    	               try {
    	                   String string;
    	            
    	                   string = (String) contents.getTransferData(
    	            	    		DataFlavor.stringFlavor);
    	                   shell.paste(string);
    	               
    	               } catch (Exception e) {    	      
    	                  // e.printStackTrace();
    	               }
    	           }
    	           Executive.readingFile=false;
    	       }
    	       
    	    } else if (command.equals("Copy")) {
    	        if (!NslSystem.applet) { 
    	            String string = shell.getSelectedText();
    	           // System.out.println("Copying "+ string);    	    
    	            shell.setSelectFlag(false);

    	            StringSelection contents = new StringSelection(string);
    	            clipboard.setContents(contents, NslExecutive.this);
    	        }
    	        disable(2,1);
    	        enable(2,0);
    	        enable(2,2);
    	    } else {
    	        //System.out.println("Select");
    	        shell.setSelectFlag(true);
    	        disable(2,0);
    	        disable(2,2);
    	        enable(2,1);
            }
    	
        }
    }
    
    public void lostOwnership(Clipboard clip, Transferable transferable) {
    }
    
  private class NslSystemActionAdapter implements ActionListener {
      public NslSystemActionAdapter(Frame frame) {
      	this.frame=frame;
      }    
            Frame frame;
      public void actionPerformed(ActionEvent evt) {
      MenuItem menu = ((MenuItem)evt.getSource());


      
      if (evt.getActionCommand().equals("Exit")) {
	dispose();
	System.exit(0);
      } else if (evt.getActionCommand().equals("Nsls file...")) {
         FileDialog dialog = new FileDialog(myFrame, "Source nsls file");
         dialog.setFilenameFilter(new NslDotNslsFilter());
         dialog.setVisible(true);
         String fileName;
         if ((fileName=dialog.getFile()) != null) {
                //System.out.println("Sourcing file "+fileName);
                //Executive.sourceFile(fileName);
                
                NslThreadCommands cmdExec = new NslThreadCommands(
    		    "Source", fileName, system,
    		    system.getInterpreter());
		cmdExec.start();
         //} else {
         //	System.out.println("Source file cancelled");
         }
      } else {
         NslSetVariable window = new NslSetVariable(frame, evt.getActionCommand(),system);
      }
    }
  }

  private class NslActionAdapter implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
      if (evt.getActionCommand().equals("New Output Frame")) {
	// NslDisplaySystem ds = new NslDisplaySystem(null, system);      
	
	if (protocols.size()> 0) {
	
	String leftStrs[] = new String[protocols.size()];
	String rightStrs[] = new String[1];
	
	rightStrs[0] = "manual";
	
        Enumeration e = protocols.elements();
      	    	
        Protocol p;
        NslModule module=null;
        int i=0;
        while(e.hasMoreElements()) {
            p = (Protocol) e.nextElement();
            //System.err.println("Adding: "+p.name);
            leftStrs[i] = p.name;
            i++;
	}
	
	NslFrameProtocols dialog = new NslFrameProtocols((NslExecutive)myFrame, leftStrs, rightStrs,0);
	dialog.show();
	} else {
	    //String strs[] = new String[1];
	    //strs[0] = "manual";
	    NslOutModule m = new NslOutDummyModule();
	    m.show();
	}
	
      } else {
	// NslDisplaySystem ds = new NslDisplaySystem(null, system);      
	
	if (protocols.size()> 0) {
	
	String leftStrs[] = new String[protocols.size()];
	String rightStrs[] = new String[1];
	
	rightStrs[0] = "manual";
	
        Enumeration e = protocols.elements();
      	    	
        Protocol p;
        NslModule module=null;
        int i=0;
        while(e.hasMoreElements()) {
            p = (Protocol) e.nextElement();
            //System.err.println("Adding: "+p.name);
            leftStrs[i] = p.name;
            i++;
	}
	
	NslFrameProtocols dialog = new NslFrameProtocols((NslExecutive)myFrame, leftStrs, rightStrs,1);
	dialog.show();
	} else {
	    //String strs[] = new String[1];
	    //strs[0] = "manual";
	    NslInModule m = new NslInDummyModule();
	    m.show();
	}
	
      }
    }
  }
  
  public void createOutModuleWithProtocols(String[] strs) {
	NslOutModule m = new NslOutDummyModule();
	m.nslRemoveFromLocalProtocols("manual");
	for (int i=0; i<strs.length; i++) {
	    System.err.println("Adding: "+strs[i]);
	    m.nslAddProtocolRecursiveUp(strs[i]);
	}
	m.show();
  }

  public void createInModuleWithProtocols(String[] strs) {
	NslInModule m = new NslInDummyModule();
	m.nslRemoveFromLocalProtocols("manual");
	for (int i=0; i<strs.length; i++) {
	    System.err.println("Adding: "+strs[i]);
	    m.nslAddProtocolRecursiveUp(strs[i]);
	}
	m.show();
  }

  private class NslContinueModuleActionListener implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
    	String command = evt.getActionCommand();
    
    	NslThreadCommands cmdExec = new NslThreadCommands("ContinueModule",
    		command, system,
    		system.getInterpreter());
    	
    	cmdExec.start();
    }
  }
  
  private class NslContinueCycleActionListener implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
    	String command = evt.getActionCommand();
    
    	NslThreadCommands cmdExec = new NslThreadCommands("ContinueCycle",
    		command, system,
    		system.getInterpreter());
    	
    	cmdExec.start();
    }
  }

  private class NslContinueEpochActionListener implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
    	String command = evt.getActionCommand();
    
    	NslThreadCommands cmdExec = new NslThreadCommands("ContinueEpoch",
    		command, system,
    		system.getInterpreter());
    	
    	cmdExec.start();
    }
  }

  private class NslStepModuleActionListener implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
    	String command = evt.getActionCommand();
    
    	NslThreadCommands cmdExec = new NslThreadCommands("StepModule",
    		command, system,
    		system.getInterpreter());
    	
    	cmdExec.start();
    }
  }
  
  private class NslStepCycleActionListener implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
    	String command = evt.getActionCommand();
    
    	NslThreadCommands cmdExec = new NslThreadCommands("StepCycle",
    		command, system,
    		system.getInterpreter());
    	
    	cmdExec.start();
    }
  }

  private class NslStepEpochActionListener implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
    	String command = evt.getActionCommand();
    
    	NslThreadCommands cmdExec = new NslThreadCommands("StepEpoch",
    		command, system,
    		system.getInterpreter());
    	
    	cmdExec.start();
    }
  }
  public void actionPerformed(ActionEvent evt) {
    
    String command = evt.getActionCommand();
    
    NslThreadCommands cmdExec = new NslThreadCommands(
    	command, system,
    	system.getInterpreter());
    	
    cmdExec.start();
  }  

 /* private class OptionActionAdapter implements ActionListener {
    Frame frame;
    OptionActionAdapter(Frame f) { frame = f;}
    public void actionPerformed(ActionEvent evt) {
      if (evt.getActionCommand().equals("Run Time and Run Step Size")) { 

	NslSetVariable ap ; 

	do {
	  
	  ap = new NslSetVariable(frame, 
					 system.getEndTime(), 
					 system.nslGetRunDelta(),
					 system.getEndTime() );

	  system.setEndTime( ap.value );
	  system.nslSetRunDelta( ap.secvalue );
	  
	  NslTemporalCanvas.graphsize = ap.thirdvalue;
	  
	  if ((system.endTime() < 0) || (system.nslGetRunDelta() < 0) || 
	      (ap.thirdvalue < 0)) {
	    NslErrorWrong errorwindow = new NslErrorWrong(frame);
	  } else {
	    System.out.println("nsl set system.endTime  "+ap.value);
	    System.out.println("nsl set system.stepSize "+ap.secvalue);
	  }
	  
	}
	while ((system.endTime() < 0) && (system.nslGetRunDelta() < 0));
	
      }
    }
  }*/

  static NslSystem system;
  public NslShell shell;
}

