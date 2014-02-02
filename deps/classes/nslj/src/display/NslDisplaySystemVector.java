/* 
 *Copyright(c) 1997 USC Brain Project. email nsl@java.usc.edu
*/

/**
   @author Nikunj Mehta
*/

package nslj.src.display;

import java.util.*;

public class NslDisplaySystemVector extends Vector {
  public NslDisplaySystemVector(int initial, int increment) {
    super(initial, increment);
  }

  public NslDisplaySystemVector(int initial) {
    super(initial);
  }

  public void init() {
    for (int i = 0 ; i < elementCount; i++) {
      NslDisplaySystem ds = (NslDisplaySystem) elementData[i];
      ds.displayCheck();
      ds.initialize();
    }
  }
  
  public void initEpoch() {
	//System.err.println("Display epoch");
    for (int i = 0 ; i < elementCount; i++) {
      NslDisplaySystem ds = (NslDisplaySystem) elementData[i];
      ds.displayCheck();
      ds.initializeEpoch();
    }
  }  

  public synchronized void awake() {
  
      System.out.println("Notifying displays");
      //System.out.flush();
      
      NslFrame.system.notifyDisplays();
      
       /*for (int i = 0 ; i < elementCount; i++) {
          NslFrame.system.notifyDisplays();
      }*/
      
  }
  
  public synchronized void collect() {
  
  
      //System.out.println("Notifying displays");
      //System.out.flush();
      
      //NslFrame.system.notifyDisplays();
      for (int i = 0 ; i < elementCount; i++) {
      	  NslDisplaySystem ds = (NslDisplaySystem) elementData[i];
          //NslFrame.system.notifyDisplays();
          ds.notifyDisplay();
      }
      //System.out.println("Waiting displays");
      //System.out.flush();
      
       for (int i = 0 ; i < elementCount; i++) {
          NslFrame.system.waitDisplayAck();
      }
      
      //System.out.println("Collecting displays done");
      //System.out.flush();
  }  

  /*public void refresh() {
    for (int i = 0 ; i < elementCount; i++) {
      NslDisplaySystem ds = (NslDisplaySystem) elementData[i];      
    }
  }*/
/*    public void refresh() {
	for (int i = 0 ; i < elementCount; i++) {
	    NslDisplaySystem ds = (NslDisplaySystem) elementData[i];      
	    ds.refresh();
	}
    }*/

  public void oneStep() {
    for (int i = 0 ; i < elementCount; i++) {
      NslDisplaySystem ds = (NslDisplaySystem) elementData[i];
      ds.oneStep();
    }
  }
}
