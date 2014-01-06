/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

/**
   @author Nikunj Mehta
*/

package nslj.src.display;

import java.awt.*;
import java.lang.*;
import java.io.*;

import nslj.src.lang.*;
import nslj.src.system.*;

public class NslDisplaySystem extends Thread {
  
  private NslDoubleSync displayMonitor;
  //private NslSync displayMonitorAck;
  private String name;
  private NslModule module;
  NslSystem system;
  
  private static int counter=0;
  private int id;  
    
  public NslDisplaySystem(NslModule module, NslSystem system) {
    this.module = module;
    this.system = system;
    frame = new NslFrame(module,system);
    //frame = new NslFrame(module);
    frame.setBounds(left, top, 500, 500);
    left += 30;
    top += 30;
    frame.setFontName("Times");
    frame.setBackgroundColor("white");
    frame.setForegroundColor("black");
    frame.nslSetSystem(system);
    //frame.show();
    displayMonitor = new NslDoubleSync();
    //displayMonitorAck = new NslDoubleSync();
   // displayMonitor = new NslSync();
    //displayMonitorAck = new NslSync();
    
    id = counter++;
  }
  
  public NslDisplaySystem(NslSystem system, String n, String title,
    int rows, int columns, int x0, int y0, int width, int height,
    String font, String background, String foreground,int freq) {
	
    
    //frame = new NslFrame(null);
    this.module = module;
    this.system = system;
    frame = new NslFrame(null,system);
    // aa: why isnt what follows in the constructor?
    frame.frameName = n;
    frame.setBounds(x0, y0, width, height);
    frame.setTitle(title);
    frame.setRows(rows);
    frame.setColumns(columns);
    frame.setFontName(font);
    frame.setBackgroundColor(background);
    frame.setForegroundColor(foreground);
    frame.nslSetSystem(system);
    //frame.show();
    displayMonitor = new NslDoubleSync();
    //displayMonitorAck = new NslDoubleSync();
    //displayMonitor = new NslSync();
    //displayMonitorAck = new NslSync();

    id = counter++;
  }
  
  public String getDisplayName() {
      return name;
  }
  
  public synchronized void run() {
    mutex = true;
    double prevtime, time;
    prevtime = time = NslFrame.system.getCurTime();
    
    for (; ;) {
		
	//system.waitTheScheduler();
	waitTheScheduler();
	
	//System.out.println(getClass().getName()+": Notifyed from scheduler");	

	time = system.getCurTime();
	
	//System.err.println("Collecting for time: "+ time + " for ds: "+id);
  	//System.err.flush();

	if (module.nslGetEnableFlag())
		frame.collect(time);

	//System.out.println("Finished for time: "+ time + " for ds: "+id);
  	//System.out.flush();
  
  	/*try {
  	    yield();
	    sleep(0);
 	} catch (InterruptedException e) {}
*/
	//System.out.println(getClass().getName()+": Notifying scheduler");
	system.notifySchedulerAck();	
    }
  }
  
  public void show() {
  	frame.setVisible(true);
  }

  public void hide() {
  	frame.setVisible(false);
  }

  public void displayCheck() {
  	frame.setVisible(module.nslGetEnableFlag());
  }

  public void waitTheScheduler() {
  	//System.out.println("Waiting "+id);
  	//System.out.flush();
  	//displayMonitor.nslWait();
  	displayMonitor.nslRecv();
  	//displayMonitor.nslSend();
  	//System.out.println("I am free"+id);
  }

  public void notifyDisplay() {
  	//System.out.println("Unlocking"+id);
  	//System.out.flush();
  	//displayMonitor.nslNotify();
  	displayMonitor.nslSend();
  	//displayMonitor.nslRecv();
  }
  
/*  public void waitDisplayAck() {
  	System.out.println("Waiting Ack"+id);
  	System.out.flush();
  	displayMonitorAck.nslWait();
  	//displayMonitorAck.nslRecv();
  	//displayMonitor.nslSend();
  	//System.out.println("I am free");
  }

  public void notifySchedulerAck() {
  	System.out.println("Unlocking Ack"+id);
  	System.out.flush();
  	displayMonitorAck.nslNotify();
  	//displayMonitorAck.nslSend();
  	//displayMonitor.nslRecv();
  }*/
  
  public void oneStep() {
    if (module.nslGetEnableFlag())
    	frame.collect(NslFrame.system.getCurTime());
  }

  public void initialize() {
    if (module.nslGetEnableFlag()) {
	//frame.startCycle();
	frame.refresh();
    }
  }

  public void initializeEpoch() {
	//System.err.println("Display epoch 2");
    if (module.nslGetEnableFlag()) {
	//frame.startCycle();
	//System.err.println("Display epoch 2 YES");
	frame.refreshEpoch();
    }
  }
/*  public void refresh() {
    if (module.nslGetEnableFlag()) {
	frame.refresh();
    }
  }*/

  /*public static void endCycle(double endTime) {    
    System.out.println();
    System.out.println("### Simulation stopped at: "+ 
		       endTime);
    System.out.println("### Simulation step size : "+
		       NslFrame.system.nslGetDelta());
    System.out.println("### Cycles:                "+
		       endTime/NslFrame.system.nslGetDelta());		       
    System.out.println("### Epochs:                "+
		       NslFrame.system.getEpochs());
  }*/

  public NslFrame frame;
  public boolean mutex;
  public static int top = 0, left = 500;
}
