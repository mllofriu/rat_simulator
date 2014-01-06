/* SCCS @(#)NslModule.java	1.34 --- 09/01/99 -- 16:12:06 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslModule.java
//
//////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////
// Documentation about documentation
//
// command      explanation
// -------      ------------
// @param       parameter list
// @return      return value
// @throw       exception that could be thrown in run-time
// @see         see also "class"#"method"
// <tt>...</tt> prints out text anchored in typewriter font
//              for parameter, method or class names
// <b>,,.</b>   prints out text anchored in bold type font
//              for warning messages
// <i>...</i>   prints out test anchored in italic type font
//              for concepts in the program
// <br>         new line
//////////////////////////////////////////////////////////////////////

/**
* A basic module.
          |###########|
   ----->o| NslModule |o---->
          |###########|
         ^             ^
         |             |
        Inport       Outport
*/

package nslj.src.lang;

import tcl.lang.*; 

import nslj.src.system.NslSystem;
import nslj.src.math.NslDiff;

import nslj.src.display.*;  // ERH


import nslj.src.nsls.struct.*;
// import EDU.gatech.cc.is.abstractrobot.*;extends ControlSystemMFN150
import java.lang.*;
import java.util.Vector;
import java.util.Enumeration;

public  class NslModule extends NslHierarchy implements Runnable{
 
  NslFrame _displayframe=null; //aa
 


  // testing purposes
//  public static int num = -1;
//  public static int num2 = -1;
//  public static int index = -1;


  // static protected NslSystem system; - now in NslHierarchy
  // static NslScheduler scheduler;

  //String name; // aa; not needed.  It is in base. name label of the module instance

//  protected NslDouble0 _runDelta;
//  protected NslDouble0 integration_time_step;

  protected double _runDelta; // delta t, dt
  protected double _trainDelta;
  
  protected boolean _trainDeltaChanged;
  protected boolean _runDeltaChanged;
  
  boolean _run_enable_fg; // this module executes when it is true,
                         // idle otherwise
                         
  boolean _train_enable_fg; // this module executes when it is true,
                         // idle otherwise

  protected double _timeConstantTM = 1.0;

  protected double _approximationDelta; // integration step size, tm
  protected NslDiff _diff_method; // differentiation method

  protected boolean _doubleBuffering=false; // Double buffering default value
  protected boolean _bufferingChanged;
  
  Vector _missing_links; // temporary vector to store the links
                        // not nslConnected in the instantiation stage
                        // for later reconstruction


 // We have all of these list here because it is faster to build the lists
 // once and have them available, than to keep rebuilding these lists
 // every time you need them.

  //protected String _protocolName="manual";

  protected Vector _protocols;  
  protected Vector _moduleChildren;  // list of modules inside this module
  protected Vector _inports;  // inport list
  protected Vector _outports;  // outport list
  // nslNumericVars and classInstances in NslHierarchy super class

/***********************/
  public String ID_str;
  public String order_str;
  public int ID=0;
  public int child_c =0;
  public boolean postorder;
  static final int MAXDIGIT=3; // max child=10^MAXDIGIT 
  /** 
   * Bare Constructor. Setup internal variables and lists.
   */

  public NslModule(){
	super();
	nslSetModuleSpecificParams();
  }
/**
   * Default constructor. Call bare constructor to setup internal
   variables and lists. Attach itself to parent  module. It calls
   <tt>makeinst</tt> for child modules creation.
   * @param label - the name of the module
   */
  public NslModule(String label) {
	  super(label);
	  nslSetModuleSpecificParams();
  }
/**
   * Default constructor. Call bare constructor to setup internal
   variables and lists. Attach itself to parent  module. It calls
   <tt>makeinst</tt> for child modules creation.
   * @param label - the name of the module
   * @param parent - the reference of the module's parent module
   */
  public NslModule(String label,NslModule parent) {
	  super(label,parent, parent.nslGetAccess());
	  nslSetModuleSpecificParams(label,parent);
  }



  public NslModule(String label, NslModule parent, String options) 
 {
  this(label,parent);
  System.err.println("Error in NslModule: option parsing not ready. options:"+options);

 }
public void nslSetModuleSpecificParams() {
	//system is set statically before the model is created in NslMain
    _runDelta = system.nslGetRunDelta();
    _trainDelta = system.nslGetTrainDelta();
    _approximationDelta = system.nslGetApproximationDelta();
    _timeConstantTM = 1.0;
    _diff_method = system.nslGetApproximationMethod();
    nslSetModuleSpecificParams2();
}

  public void nslSetModuleSpecificParams(String label, NslModule parent) {
      // modules are added to the parent here: models use above constructor
      // with parent== null.
      if (parent==null) {
	System.out.println("NslModule: nslSetModuleSpecificParams: parent is null");
      } else {
        _runDelta   = parent.nslGetRunDelta();
        _trainDelta = parent.nslGetTrainDelta();
        _approximationDelta = parent.nslGetApproximationDelta();
	parent.nslAddToModuleChildren(this);
      }
      nslSetModuleSpecificParams2();
  }

public void nslSetModuleSpecificParams2() {
    _moduleChildren = new Vector(10,10);
    // assume at least one port, increment one at a time.
    _inports = new Vector(1, 1);
    _outports = new Vector(1, 1);
    _protocols = new Vector(1, 1);

    _protocols.addElement("manual");

    _run_enable_fg = true;
    _train_enable_fg = true;


    _doubleBuffering  = false; // system.nslGetBuffering();
    _bufferingChanged = false;
    _trainDeltaChanged = false;
    _runDeltaChanged = false;

    
    ID=0;
    ID_str=makestr(ID);
    order_str =ID_str;
    child_c=0;
    // postorder=true; 98/9/11 aa changed
    // preorder - parent before children -- default
    // postorder - parent after children
}

/**
   * define the scheduler to use
   * @param schduler
   *
  public static void nslSetScheduler(NslSchduler sch) {
    scheduler = sch;
  }
  */
  public Vector nslGetModuleChildrenVector(){
    // list of modules inside this module
    return _moduleChildren;
  }

 public  Vector nslGetInportsVector() {
    return _inports;  // inport list
  }

  public Vector nslGetOutportsVector() {
    return _outports;  // outport list
  }

  private String makestr(int a)
  {String s;
   s="";
   for (int i=0;i<MAXDIGIT;i++)  {s=(a%10)+s; a/=10;}
   return s;
  } 
  public void nslSetId(NslModule parent,int id) {
   ID=id;
   ID_str=parent.ID_str+" "+makestr(id);
   order_str = ID_str;
   }

  public int    nslGetId()        { return(ID); } 
  public String nslGetId_str()    { return(ID_str); } 
  public String nslGetOrderStr() { return(order_str); } 
 
  /**
   * Add child module into this module and into the scheduler
   * @param child - child NslModule
   * @throw NullPointerException if the module supplied is null
   */
   public void nslAddToModuleChildren(NslModule child) {
//System.out.println("#debug: NslModule: Adding Module "+child.nslGetName()+" to "+nslGetName());
   	 if (child == null)  throw new NullPointerException();

         _moduleChildren.addElement(child);
         
	 child_c++;                  // Having one more baby
	 child.nslSetId(this,child_c);  // give new baby a name	 

	 /* */
	 if ((system.schedulerMethod.equals("pre")) ||
	     (system.schedulerMethod.equals("mixed"))) {
		//preorder - parent goes before childeren
	        //TODO: mixed not implemented yet
	 }
	 if (system.schedulerMethod.equals("post")) {
		//postorder - parent goes after childeren
		//TODO: change this so we are not dependent on string lengths
		order_str=child.nslGetOrderStr()+"$"; 
	 }
       //system.addToScheduler (child);  

       /*System.out.println("#debug: NslModule: add "+child.nslGetName()+"["+
                             child.nslGetOrderStr()+"]  to Parent "+
			     nslGetName()+"["+order_str+"]  (pre:"+ID_str+")");
       */
   }
   
   


  /**
   * Get a child module with  <tt>name</tt>
   * @param name- name of target child module
   * @return children module, null if not found
   */
   public NslModule nslGetModuleRef(String name) {
    Enumeration E = _moduleChildren.elements();
    NslModule child = null;

    if (E.hasMoreElements()) {
      while(E.hasMoreElements()) {
	  child = (NslModule)E.nextElement();
	  if (this.nslGetName().equals(child.nslGetName())) {
	    return child;
	  }
      }
    }
    return null;
  }

public void nslSetAccessRecursive(char new_desiredAccess) {
	Enumeration E;
	char old_desiredAccess;
	old_desiredAccess=nslGetAccess();
	if (old_desiredAccess==new_desiredAccess) {
	   // do nothing
	   return;
      }

	super.nslSetAccessRecursive(new_desiredAccess);

	E = _moduleChildren.elements();
	while(E.hasMoreElements()) {
	    NslModule child = (NslModule)E.nextElement();
          child.nslSetAccessRecursive (new_desiredAccess);
      } 
} 


   public boolean nslHasChildModule(String s) {
    Enumeration E = _moduleChildren.elements();
    NslModule child = null;
 
    if (E.hasMoreElements()) {
      while(E.hasMoreElements()) {
        child = (NslModule)E.nextElement();
        //System.out.println("Debug: NslModule: Adding Module "+child.nslGetName());
        if(s.equals(child.nslGetName()))
          return(true);
      }
    }
    return(false);
  }
   public void nslPrintChildModules() {
    Enumeration E = _moduleChildren.elements();
    NslModule child = null;
 
    if (E.hasMoreElements()) {
      while(E.hasMoreElements()) {
        child = (NslModule)E.nextElement();
        // System.out.println("Debug: NslModule: Adding Module "+child.nslGetName());
      }
    }
  }


  /**
   * Create an outport which makes reference to an Object
   * @param name name of this outport
   * @param n reference to a single object
   * @throws NullPointerException if n is not defined
   */
  // initialize outport of this object
  // The object itself should be fully initialized
  public NslNumeric nslAddOutport(String name, NslNumeric n) {
    if (n == null) {
      throw new NullPointerException();
    }
//    System.out.println("Debug: NslModule: ADDING TO OUTPORT OF "+nslGetName()+" element "+name);
    _outports.addElement(new NslOutport(name, n));
    return n;
  }

  /**
   * Create an outport which makes reference to a NslNumeric
   * @param name name of this outport
   * @param n reference to a NslNumeric, can be NslNumeric0, NslNumeric1 or NslNumeric2
   * @throws NullPointerException if n is not defined
   */
  // initialize outport of this object
  // The object itself should be fully initialized
  public NslNumeric nslAddOutport(NslNumeric n) {
    if (n == null) {
      throw new NullPointerException();
    }
//    System.out.println("ADDING TO OUTPORT OF "+nslGetName()+" element ");
    _outports.addElement(new NslOutport(n));
    return n;
  }

  /**
   * Add an Outport to the current outport list
   * @param outport the outport to be added
   * @throws NullPointerException if outport is null
   */
  public void nslAddExistingOutport(NslOutport outport) {
    if (outport == null) {
      throw new NullPointerException();
    }

//    System.out.println("ADDING TO OUTPORT OF "+nslGetName()+" with no-name element.");
    _outports.addElement(outport);
  }

  /**
   * Create an inport which makes reference to an Object
   * @param name name of this intport
   * @param n reference to a NslNumeric, can be NslNumeric0, NslNumeric1 or NslNumeric2
   * @throws NullPointerException if n is null
   */
  // initialize inport of this object
  public NslNumeric nslAddInport(String name, NslNumeric n) {
    if (n == null) {
      throw new NullPointerException();
    }
    _inports.addElement(new NslInport(name, n));
    return n;
  }
  /**
   * Create an inport which makes reference to a Numeric object
   * @param name name of this intport
   * @param n reference to a NslNumeric, 
   can be NslNumeric0, NslNumeric1 or NslNumeric2 or NslNumeric3 or NslNumeric4
   * @throws NullPointerException if n is null
   */
  // initialize inport of this object
  public NslNumeric nslAddInport(NslNumeric n) {
    if (n == null) {
      throw new NullPointerException();
    }
    _inports.addElement(new NslInport(n));
    return n;
  }

  /**
   * Add an inport to the current inport list
   * @param inport the inport to be added
   * @throws NullPointerException if inport is null
   */
  public void nslAddExistingInport(NslInport inport) {
    if (inport == null) {
      throw new NullPointerException();
    }
    _inports.addElement(inport);
  }

// <tt>...</tt> below prints typewriter font
  /**
   * Get an inport with name <tt>name</tt>
   * @param name name of target inport
   * @return inport, null if not found
   */

   NslInport getInport(String name) {
    Enumeration E = _inports.elements();
    NslInport port;

    if (E.hasMoreElements()) {
    while(E.hasMoreElements()) {
      port=(NslInport)E.nextElement();
      if(name.equals(port.nslGetName()))
	    return port;
      }
    }
    return null;
  }

  /**
   * Get an outport with name <tt>name</tt>
   * @param name name of target outport
   * @return outport, null if not found
   */

   NslOutport getOutport(String name) {
    Enumeration E = _outports.elements();
    NslOutport port;

    if (E.hasMoreElements()) {
      while(E.hasMoreElements()) {
	    port=(NslOutport)E.nextElement();
    	if(name.equals(port.nslGetName()))
	      return port;
      }
    }
    return null;
  }
/**
   * Get a port with name <tt>name</tt>
   * @param name name of target port
   * @return port, null if not found
   */
   public NslPort nslGetPort(String name) {
    NslPort  port = (NslPort)getOutport(name);
    if (port == null)
      port = (NslPort)getInport(name);
    return port;
  }
/* todo: remove nslDummy???*/
public NslNumeric nslDummy(NslNumeric n) { return n;}

/*
public void nslPutInGraphicsList(NslNumeric n) {
	NslOutport fake_port;
	fake_port=new NslOutport(n.nslGetName(),n);
 	 //System.out.println("Debug:NslModule:nslPutInGrahicsList: "+fake_port.nslGetName());
	fake_port.owner=this;
	this.nslAddExistingOutport(fake_port);
}
*/
/*
public void nslPutInGraphicsList(NslNumeric n1,NslNumeric n2) {
nslPutInGraphicsList(n1);
nslPutInGraphicsList(n2);
}
public void nslPutInGraphicsList(NslNumeric n1,NslNumeric n2,NslNumeric n3) {
nslPutInGraphicsList(n1);
nslPutInGraphicsList(n2);
nslPutInGraphicsList(n3);
}
public void nslPutInGraphicsList(NslNumeric n1,NslNumeric n2,NslNumeric n3,NslNumeric n4) {
nslPutInGraphicsList(n1);
nslPutInGraphicsList(n2);
nslPutInGraphicsList(n3);
nslPutInGraphicsList(n4);
}

public void nslPutInGraphicsList(NslNumeric n1,NslNumeric n2,NslNumeric n3,NslNumeric n4,NslNumeric n5) {
nslPutInGraphicsList(n1);
nslPutInGraphicsList(n2);
nslPutInGraphicsList(n3);
nslPutInGraphicsList(n4);
nslPutInGraphicsList(n5);
}
*/

/**
   * Connect two child modules and add the nslConnection to the scheduler<br>
     The nslConnection is done in two phases. First, it will nslConnect
     outport of a child module to the inport of another child module,
     and outport of a child module to the outport of the parent module
     (ie the wall of module in one level higher in hierarchy). Second,
     the nslConnection between the inport of the parent module and the
     intport of the child module is established. Two phases are needed
     because the content of the inport of the parent module is not
     available in the first phase. Here is an illustration: <br>
     <pre>
     +-NslModule Top-----------------------------------------------+
     | +-NslModule A----------+       +-NslModule B-------------+  |
     | | +-NslModule a---+    |       |     +-NslModule b-----+ |  |
     | | | variable x    | 1  | 2     | 3   | variable y      | |  |
     | | |               |o##o|o#####i|i###i|i                | |  |
     | | |               |    |       |     |                 | |  |
     | | +---------------+    |       |     +-----------------+ |  |
     | +----------------------+       +-------------------------+  |
     +-------------------------------------------------------------+
    o   outport
    i   intport
    ### nslConnection
    </pre>
    Assume we are at the top level in the hierarchy: NslModule Top
    and we are going to nslConnect two modules, NslModule A and
    NslModule B. All links #1, #2, #3 are not available. In general
    case, we need to nslConnect all the links internal to the child
    modules first, ie #1, and #3. <br>

    However, in our implementation of ports, the variable, instead
    of referencing through ports, references the corresponding
    variable directly. For instance, for a variable y in NslModule b
    to read a variable x in NslModule a, y makes a direct reference
    to x. <br>

    This implementation is very efficient in the simulation running
    phase, but not the initialization, as well as the nslConneciton,
    phase. The nslConnection #1 works fine, since it can reference
    variable x. But nslConnection #3 does not work, since at the time
    of nslConnection, NslModule b cannot see variable x in NslModule a.
    In this implementation, we "remember" this link and delay the
    construction. <br>

    Go back to the highest level. Link #2 can be done since #1 is
    established. Link #2 can see variable x in NslModule a now.
    At this time we can construct the <i>Missing Links</i>.<br>

    The missing links in the first phase is stored in <tt>
    _missing_links</tt>. The system will call another method
    <tt>nslConnMissingLinks</tt> to construct the links in the second
    phase.

   * @param child1 From module <tt>child1</tt>
   * @param name1 Port of child1
   * @param child2 To module <tt>child2</tt>
   * @param name2 Port of child2
   * @return true if nslConnection is successful
   * @see NslModule#nslConnChildren_callinitsys
   * @see NslModule#nslConnMissingLinks

   * Side Effect: change missing_link: all links not established
                  is stored in missing_link
   */
  // nslConnect from child1.name1 to child2.name2
  // return true is success

   public boolean connect(NslModule child1, String name1, NslModule child2, String name2) { 
	return(nslConnect(child1,name1,child2,name2));
   }

   public boolean nslConnect(NslNumeric num1, NslNumeric num2) {
    NslModule child1,child2; // these are the owners of num1 & num2
    NslInport inport1;
    NslInport inport2;

    NslOutport outport1;
    NslOutport outport2;

    NslPort port1;
    NslPort port2;

    String name1;
    String name2;

    boolean success = true;

    port1=num1.nslGetPort();
    port2=num2.nslGetPort();
    name1=port1.nslGetName();
    name2=port2.nslGetName();
    child1=port1.getOwner();
    child2=port2.getOwner();


    if (port1==null) {
      System.err.println("Error in NslModule: Cannot find port '"+name1+
			 "' in module '"+child1.nslGetName()+
			 "' in the nslConnection with port '"+name2+
			 "' of module '"+child2.nslGetName()+"'.");
      return false;
    }
    if (port2==null) {
      System.err.println("Error in NslModule: Cannot find port '"+name2+
			 "' in module '"+child2.nslGetName()+
			 "' in the nslConnection with port '"+name1+
			 "' of module '"+child1.nslGetName()+"'.");
      return false;
    }


    // inter child nslConnection
    // the outport of first child is nslConnected to the inport of the
    // second child.
    // todo: we have to ensure that both children are in the current
    //       module.
    if (port1.getType() == NslPort.OUTPORT
	&& port2.getType() == NslPort.INPORT) {
    		//System.out.println("Debug: NslModule: Link "+child1.nslGetName()+" outport "+name1+" w/ "+child2.nslGetName()+ " inport "+name2);

      ((NslInport)port2).setPort((NslOutport)port1);
        //system.scheduler.addConnection(new NslConnection(leaf1, name1, leaf2, name2));

    }
    // outport to outport nslConnection
    // The outport of child module is nslConnected to the outport of this
    // module.
    // todo: we have to ensure that port1 is the outport of child module of
    //       this module and port2 is the outport of this module.
    else if (port1.getType() ==  NslPort.OUTPORT
	     && port2.getType() == NslPort.OUTPORT) {
      if (((NslOutport)port1).isInitialized()) {
		//System.out.println("Debug: NslModule: Link "+child1.nslGetName()+" outport "+name1+" w/ "+child2.nslGetName()+ " outport "+name2);

      ((NslOutport)port2).setPort((NslOutport)port1);
        //system.scheduler.addConnection(new NslConnection(leaf1, name1, leaf2, name2));
      }
      else{
	if (_missing_links==null)
	  _missing_links = new Vector(1,1);
	_missing_links.addElement(new NslConnection(child1, name1, child2, name2));
	//System.out.println("Debug: NslModule: Link "+child1.nslGetName()+" outport "+name1+" w/ "+child2.nslGetName()+ " outport "+name2);
	success = false;

      }

    }
    // inport to inport nslConnection
    // The inport of this module is nslConnected to the outport of the
    // child module.
    // todo: we have to ensure that port1 is the inport of this module
    //       and port2 is the inport of the child module.
    else if (port1.getType() == NslPort.INPORT
	     && port2.getType() == NslPort.INPORT) {
      if (((NslInport)port1).isInitialized()) {
	 //System.out.println("Debug:NslModule: Link "+child1.nslGetName()+" inport "+name1+" w/ "+child2.nslGetName()+ " inport "+name2);
    	((NslInport)port2).setPort((NslInport)port1);
    	  //system.scheduler.addConnection(new NslConnection(leaf1, name1, leaf2, name2));
      }
      else{
	if (_missing_links==null)
	  _missing_links = new Vector(1,1);
	_missing_links.addElement(new NslConnection(child1, name1, child2, name2));
		//System.out.println("Debug: NslModule: Link "+child1.nslGetName()+" inport "+name1+" w/ "+child2.nslGetName()+ " inport "+name2);

	success = false;

      }

    }
    // nslConnect inport of this module to the outport of this module
    // it is only for the direct nslConnection from inport of this
    // module to the outport.
    // todo: establish the nslConnection
    //       ensure that both inport/outport are from this module
    else {
      success = false;
      System.err.println("Error in NslModule: in nslConnecting "+name1+" and "+name2);
    }
    return success;
}



   public boolean nslConnect(NslModule child1, String name1, NslModule child2, String name2) {
    NslInport inport1;
    NslInport inport2;

    NslOutport outport1;
    NslOutport outport2;

    NslPort port1;
    NslPort port2;

    boolean success = true;

   // System.out.println("Debug: NslModule: nslConn: child1:"+child1+"   child2:"+child2);
    port1 = child1.nslGetPort(name1);
    port2 = child2.nslGetPort(name2);
    if (port1==null) {
      System.err.println("Error in NslModule: Cannot find port '"+name1+
			 "' in module '"+child1.nslGetName()+
			 "' in the nslConnection with port '"+name2+
			 "' of module '"+child2.nslGetName()+"'.");
      return false;
    }
    if (port2==null) {
      System.err.println("Error in NslModule: Cannot find port '"+name2+
			 "' in module '"+child2.nslGetName()+
			 "' in the nslConnection with port '"+name1+
			 "' of module '"+child1.nslGetName()+"'.");
      return false;
    }

    // inter child nslConnection
    // the outport of first child is nslConnected to the inport of the
    // second child.
    // todo: we have to ensure that both children are in the current
    //       module.
    if (port1.getType() == NslPort.OUTPORT
	&& port2.getType() == NslPort.INPORT) {
    		//System.out.println("Debug: NslModule:  Link "+child1.nslGetName()+" outport "+name1+" w/ "+child2.nslGetName()+ " inport "+name2);
      ((NslInport)port2).setPort((NslOutport)port1);
	//system.scheduler.addConnection(new NslConnection(leaf1, name1, leaf2, name2));

    }
    // outport to outport nslConnection
    // The outport of child module is nslConnected to the outport of this
    // module.
    // todo: we have to ensure that port1 is the outport of child module of
    //       this module and port2 is the outport of this module.
    else if (port1.getType() ==  NslPort.OUTPORT
	     && port2.getType() == NslPort.OUTPORT) {
      if (((NslOutport)port1).isInitialized()) {
	//System.out.println("Debug: NslModule: Link "+child1.nslGetName()+" outport "+name1+" w/ "+child2.nslGetName()+ " outport "+name2);

	((NslOutport)port2).setPort((NslOutport)port1);
        //system.scheduler.addConnection(new NslConnection(leaf1, name1, leaf2, name2));
      }
      else{
	if (_missing_links==null)
	  _missing_links = new Vector(1,1);
	_missing_links.addElement(new NslConnection(child1, name1, child2, name2));
	//System.out.println("Debug: NslModule: Link "+child1.nslGetName()+" outport "+name1+" w/ "+child2.nslGetName()+ " outport "+name2);

	success = false;

      }

    }
    // inport to inport nslConnection
    // The inport of this module is nslConnected to the outport of the
    // child module.
    // todo: we have to ensure that port1 is the inport of this module
    //       and port2 is the inport of the child module.
    else if (port1.getType() == NslPort.INPORT
	     && port2.getType() == NslPort.INPORT) {
      if (((NslInport)port1).isInitialized()) {
    //#1 System.out.println("Debug:NslModule:Link "+child1.nslGetName()+" inport "+name1+" w/ "+child2.nslGetName()+ " inport "+name2);
    	((NslInport)port2).setPort((NslInport)port1);
    	  //system.scheduler.addConnection(new NslConnection(leaf1, name1, leaf2, name2));
      }
      else{
	if (_missing_links==null)
	  _missing_links = new Vector(1,1);
	_missing_links.addElement(new NslConnection(child1, name1, child2, name2));
    //#1 System.out.println("Debug:NslModule:Link "+child1.nslGetName()+" inport "+name1+" w/ "+child2.nslGetName()+ " inport "+name2);
	//System.out.println("Connection is delayed");
	success = false;

      }

    }
    // nslConnect inport of this module to the outport of this module
    // it is only for the direct nslConnection from inport of this
    // module to the outport.
    // todo: establish the nslConnection
    //       ensure that both inport/outport are from this module
    else {
      success = false;
      System.err.println("Error in NslModule nslConnecting "+name1+" and "+name2);
    }
    return success;
  }

  /**
   * Connect two modules using defined link
   * @param link The nslConnection between two modules
   * @return true if the nslConnection is successful
   */
  public boolean nslConnect(NslConnection link) {
    return nslConnect(link.child1, link.name1, link.child2, link.name2);
  }


  /**
   * Call child modules to nslConnect their own internal modules
     recursively. It is the first stage of nslConnection in makeinst.
     The links not well defined will be put in <tt>_missing_links</tt>
     vector for the second stage
   * @see NslModule#nslConn
   * @see NslModule#nslConnMissingLinks
   */
  // call makeConn recursively to nslConnect all modules in the
  // module

  // todo: ensure the nslConnection is valid. Otherwise, throw
  //       exception of do something else that will warning
  //       the programmer that the link is not in correct fashion

/* This was here for calling initsys within nslConnChildren ...
  public void nslConnChildren() {
    System.err.println("Error in NslModule: *WARNING* nslConnChildren() shouldn't be called anymore");
    nslConnChildren_callinitsys();
  }
*/
  public void nslConnChildren() {

//System.out.println("Debug: NslModule: nslConnChildren Called");
    int num3=0;
    Enumeration E = _moduleChildren.elements();
    Enumeration E2 = _moduleChildren.elements();
    NslModule child = null;

    // nslConnect the components inside the children of this module first
      while(E.hasMoreElements()) {
    	child = (NslModule)E.nextElement();
        //System.out.println("Debug: NslModule: Connecting "+nslGetName()+" with its child "+child.nslGetName());
    //System.out.println("Debug:NslModule "+nslGetName()+":  INPORTS "+_inports+" OUTPORTS "+_outports+" ENVIRON "+environ_vars);
	child.nslConnChildren();
      }
    // nslConnect the components of this module
    makeConn();
    // initsys();  //ERH july 13'97
    // ERH oct 21'97 
     //System.out.println("Completed Connection of "+nslGetName());
  }


  /**
   * Call child modules to nslConnect their missing links, if possible
     It checks the <tt>_missing_links</tt> vector for the nslConnection
     required.
   * @see NslModule#nslConn
   * @see NslModule#nslConnChildren_callinitsys
   */
  // todo: ensure the nslConnection is valid. Otherwise, throw
  //       exception or do something else that will warning
  //       the programmer that the link is not in correct fashion

   public void nslConnMissingLinks() {
    Enumeration E;
    NslConnection link = null;
    NslModule child = null;
      //# System.out.println("Connecting missing links of module "+nslGetName());

    if (_missing_links != null) {

      //System.out.println("Debug: NslModule: NOT NULL!!!: Connecting missing links of module "+ nslGetName());
      E  = _missing_links.elements();
      if (E.hasMoreElements()) {
	while(E.hasMoreElements()) {
	  link = (NslConnection)E.nextElement();
	  if(!nslConnect(link)) {
	    // some problem in nslConnection even in second phase
	    System.err.println("Error in NslModule: Cannot nslConnect module '"+link.child1+
			       "' port '"+link.name1+
			       "' -> module '"+link.child2+
			       "' port '"+link.name2+"'");
	  }
	}
      }

      _missing_links = null;
    }

    E = _moduleChildren.elements();
    // nslConnect the components inside the children of this module first
    if (E.hasMoreElements()) {
      while(E.hasMoreElements()) {
	child = (NslModule)E.nextElement();
	child.nslConnMissingLinks();
      }
    }
  }  
  
    /**
     * get run or train step size depending on simulation mode, delta t
     * @return step size
     */
   
    public double nslGetDelta() {
  
	switch (system.getScheduler().schedulerMode) {
            case 'R':
		//System.out.println("Taking run step size: "+_runDelta);
		return _runDelta;
	    case 'T':
            //System.out.println("Taking train step size: "+_trainDelta);
    	    	return _trainDelta;
    	    default:
    	    	System.out.println("Taking 0.0 step as size");
    	    	return 0.0;
    	}
    	
    }
    
    /**
     * get run step size, delta t
     * @return step size
     */
 
    public double nslGetRunDelta() {
	return _runDelta;
    }
  
    /**
     * set run step size, delta t.  The setting will propagate to all child modules
     * in the hierarchy if the module is an internal node.
     * @param t step size
     */
     
    public void nslSetRunDeltaRecursive(double t) {

	_runDelta = t;
	
	Enumeration E = _moduleChildren.elements();
	NslModule child;

	while(E.hasMoreElements()) {
	    child = (NslModule)E.nextElement();
            child.nslSetRunDelta(t);
        }
        
        _runDeltaChanged = true;
  	system._runDeltaChanged = true;

    }

    public void nslSetRunDelta(double t) {    
        nslSetRunDeltaRecursive(t);
    }
    
    public void nslResetRunDelta() {
    
	if (!_runDeltaChanged) {
            _runDelta = system.nslGetRunDelta();
	}

	Enumeration E = _moduleChildren.elements();
	NslModule child;

	while(E.hasMoreElements()) {
	    child = (NslModule)E.nextElement();
            child.nslResetRunDelta();
        }
        
    }
  
    /**
     * get train step size, delta t
     * @return step size
     */
  
    public double nslGetTrainDelta() {
	return _trainDelta;
    }
    
    /**
     * set train step size, delta t.  The setting will propagate to all child modules
     * in the hierarchy if the module is an internal node.
     * @param t step size
     */
     
    public void nslSetTrainDeltaRecursive(double t) {
    	
    	_trainDelta = t;
    	
    	Enumeration E = _moduleChildren.elements();
        NslModule child;

        while(E.hasMoreElements()) {
	    child = (NslModule)E.nextElement();
            child.nslSetTrainDelta(t);
        }
        
        _trainDeltaChanged = true;
  	system._trainDeltaChanged = true;
        
    }
    
    public void nslSetTrainDelta(double t) {  
    	nslSetTrainDeltaRecursive(t);
    }
    
    public void nslResetTrainDelta() {
    
	if (!_trainDeltaChanged) {
            _trainDelta = system.nslGetTrainDelta();
	}

	Enumeration E = _moduleChildren.elements();
	NslModule child;

	while(E.hasMoreElements()) {
	    child = (NslModule)E.nextElement();
            child.nslResetTrainDelta();
        }
        
    }

  /**
   * Examine whether the module runs on execution command <tt>run</tt>
     or <tt>step</tt>
   * @return true if it is active, false if idle on execution command
   */
  public boolean nslGetTrainEnableFlag() {
    return _train_enable_fg;
  }
  /**
   * To set if the module runs on execution command <tt>run</tt>
     or <tt>step</tt>. The setting will propagate to all child modules
     in the hierarchy if the module is an internal node.
   * @param b true if active, false if idle
   */
  public void nslSetTrainEnableFlag(boolean b) {
    Enumeration E = _moduleChildren.elements();
    NslModule child = null;
    _train_enable_fg = b;

    if (E.hasMoreElements()) {
      while(E.hasMoreElements()) {
	child = (NslModule)E.nextElement();
    child.nslSetTrainEnableFlag(b);
      }
    }

  }
  /**
   * Examine whether the module runs on execution command <tt>run</tt>
     or <tt>step</tt>
   * @return true if it is active, false if idle on execution command
   */
      
  public boolean nslGetRunEnableFlag() {
    return _run_enable_fg;
  }

  public boolean nslGetEnableFlag() {
	switch (system.getScheduler().schedulerMode) {
            case 'R':		
		return _run_enable_fg;
	    case 'T':
    	    	return _train_enable_fg;
    	    default:
    	    	System.out.println("Error: invalid module state");
    	    	return false;
    	}
  
  }

  /**
   * To set if the module runs on execution command <tt>run</tt>
     or <tt>step</tt>. The setting will propagate to all child modules
     in the hierarchy if the module is an internal node.
   * @param b true if active, false if idle
   */
  public void nslSetRunEnableFlag(boolean b) {
    Enumeration E = _moduleChildren.elements();
    NslModule child = null;
    _run_enable_fg = b;

    if (E.hasMoreElements()) {
      while(E.hasMoreElements()) {
	child = (NslModule)E.nextElement();
    child.nslSetRunEnableFlag(b);
      }
    }

  }
  /**
   * get integration time step / numerical method time step tm
   * @return time step size
   */
  public double nslGetApproximationDelta() {
    return _approximationDelta;
  }
  /**
   * To set the time step size
   * @param t time step size
   */
  public void nslSetApproximationDelta(double t) {
    _approximationDelta = t;
  }

  public NslDiff nslGetApproximationMethod() {
    return _diff_method;
  }

  public void nslSetApproximationMethod(NslDiff dm) {
    _diff_method = dm;
  }

  /*
     static NslNumMethod getNumericMethod() {
     return numeric_method;
     }
     static void getNumericMethod(NslNumMethod method) {
     numeric_method = method;
     }
     */



  /**
   * Update the status of all _outports. It is done after each major
     numerical calculation
   */
  public void nslUpdateBuffers() {
  
//    if (_doubleBuffering) {
      Enumeration E = _outports.elements();
      NslOutport port;

      if (E.hasMoreElements()) {
        while(E.hasMoreElements()) {
	  port=(NslOutport)E.nextElement();
	  port.nslUpdateBuffers();
        }
      }
 //   }
  }
  
    public void nslSetBuffering (boolean v) {
    	_doubleBuffering  = v;
    	_bufferingChanged = true;
    	system.resetPorts();
    }
    
    public boolean nslGetBuffering () {
    	return _doubleBuffering;
    }
    
    public void nslResetBuffering () {
	
	
	if (!_bufferingChanged) {
	    _doubleBuffering = system.nslGetBuffering();
	}

	NslModule child = null;
 	Enumeration e = _moduleChildren.elements();

    	nslUpdateBuffering();
    	
    	if (e.hasMoreElements()) {
	    while(e.hasMoreElements()) {
	    	child = (NslModule)e.nextElement();
            	child.nslResetBuffering();
            }
    	}
    	    
    }
    
    public void nslUpdateBuffering () {
    	
    	Enumeration e = _outports.elements();
      	NslOutport port;
	        
        while(e.hasMoreElements()) {
	    port=(NslOutport)e.nextElement();
	    if (port!=null) {
	    	port.nslResetBuffering();
	    }
        }

    }

  /**
   * Call when multi-threaded. It simply calls execute() defined by
     the user
   * This is needed by the Runnable interface to make the applets work
   * @see NslModule#execute
   */
  public void run() {
    runsim();
  }

  protected void finalize() {
    _missing_links.removeAllElements();
    nslNullifyParent();
    _moduleChildren.removeAllElements();
    _inports.removeAllElements();
    _outports.removeAllElements();
    super.nslRemoveAllClassInstances ();
    super.nslRemoveAllDataVars ();
  }

  /**
   * Instantiation the internal variable of this object. It is called
     automatically at the construction phase of this module. It must
     be defined by user.
   */
  public void makeinst(String name, NslModule p) { makeInst(name,p); }
  public void makeInst(String name, NslModule p) {}
 //ERH: why this is commented?  ERH: uncommented 
 //ERH: made it non abstract july 16'97

  public void initsys(){ initSys(); }
  public void initSys(){}

  public void endsys(){ endSys(); }
  public void endSys(){}

  public void initModule(){}
  public void endModule(){}

  public void callFromConstructorTop() {}
  public void callFromConstructorBottom() {}

/**
   * Initialization step of this module. It is called automatically
     at the start of the simulation, or by <tt>init</tt> command
     in interactive environment.
   */
  public void initrun() { initRun();}
  // abstract public void initRun();
  public void initRun() { }
 /* need to have initrun abstract, since NPP
    fills something inside through MethodNode. Unless you
    change this, they must be abstract */
  public void endrun(){ endRun(); }
  public void endRun(){  }


  /**
   * Make the nslConnection between child modules. It is automatically
     called at the construction phase of this module, but after the
     instantiation of those internal modules
   */
  public void makeConn(){ } // nslConnect between modules (using ports)

  /**
   * The <b>run</b> block of this module
   */
//note that schedular calls runsim()
  public void runsim(){
//register module's name for error reporting
	NslSystem.module_executing=this.nslGetName();
	simRun();
  }
    /* 98/9/11 aa TODO - remove */
    public void simrun(){ // main run method
	simRun();
    } 

    public void initRunEpoch() {}
    public void simRun() {} // this the latest syntax 01/19/98
    public void endRunEpoch() {}

    public void initTrainEpoch() {}
    public void initTrain() {}
    public void simTrain() {}
    public void endTrain() {} 
    public void endTrainEpoch() {}
      
    public Vector nslGetProtocols() {
    	return _protocols;
    }

    public void nslRemoveFromLocalProtocols(String name) {
            Enumeration e = _protocols.elements();
            String _protocolName;
            boolean found=false;
    	    int i=0;
    	    while(e.hasMoreElements() && !found) {

    	        _protocolName = (String)e.nextElement();
    	    
    	        if (name.equals(_protocolName)) {
    	    	    found=true;
    	        } else {
    	            i++;
    	        }
    	    } 
    	    if (found) { 
    	        //System.err.println("Removing "+name+" "+i);
    	    	_protocols.removeElementAt(i);
    	    }
     }
    
     public void nslAddProtocolRecursiveDown(String name) {
        if (system.protocolExist(name) || name.equals("manual")) {
            // insert only if it is not in list
            Enumeration e = _protocols.elements();
            String _protocolName;
            boolean found=false;
    	
    	    while(e.hasMoreElements() && !found) {

    	        _protocolName = (String)e.nextElement();
    	    
    	        if (name.equals(_protocolName)) {
    	    	    found=true;
    	         }
    	    }
    	
    	    if (!found) {
    	    	_protocols.addElement(name);
            } 
			
	    Enumeration E = _moduleChildren.elements();
	    NslModule child;
	
	    while(E.hasMoreElements()) {
	        child = (NslModule)E.nextElement();
                child.nslAddProtocolRecursiveDown(name);
	    }
        }
    }
   
    public void nslAddProtocolRecursiveUp(String name) {
        if (system.protocolExist(name) || name.equals("manual")) {
            // insert only if it is not in list
            Enumeration e = _protocols.elements();
            String _protocolName;
            boolean found=false;
    	
    	    while(e.hasMoreElements() && !found) {

    	        _protocolName = (String)e.nextElement();
    	    
    	        if (name.equals(_protocolName)) {
    	    	    found=true;
    	         }
    	    }
    	
    	    if (!found) {
    	    	//System.err.println("Adding "+name);
    	        _protocols.addElement(name);
    	        if (nslGetParentModule()!=null) {
                    nslGetParentModule().nslAddProtocolRecursiveUp(name);
                }
            } 
        }
    }
    
    public void nslDeclareProtocol(String name, String label) {
    	system.nslCreateProtocol(name, label, this);
    }     
    
    public void nslDeclareProtocol(String name) {
    	system.nslCreateProtocol(name, name, this);
    }     
    
    
    public void nslSetProtocolFlagRecursiveDown(String name) {  
    
    	Enumeration e = _protocols.elements();
    	String _protocolName;
    	boolean found = false;
    	
    	while(e.hasMoreElements() && !found) {
    	
    	    _protocolName = (String)e.nextElement();
    	    
    	   /* System.out.println("Reviewing protocol "+_protocolName);
    	    System.out.println("Has to be equal to "+name);
    	    System.out.println("Checking module    "+this.name);*/
    	    
    	    if (name.equals(_protocolName)) {
    	    	nslSetRunEnableFlag(true);
		nslSetTrainEnableFlag(true);
		
	    	e = _moduleChildren.elements();
  	    	NslModule child;
  	    
	    	while(e.hasMoreElements()) {
	    	    child = (NslModule)e.nextElement();
            	    child.nslSetProtocolFlagRecursiveDown(name);
            	}
            	found = true;
            	
            	/*if (nslGetParent()!=null) {
            	    nslGetParent().nslSetProtocolFlagRecursiveDown(name);
            	}*/
            } 
	} 
	if (!found) {
		nslSetRunEnableFlag(false);  
		nslSetTrainEnableFlag(false);  
	}
    }

  /**************************************************************************/
//----------------------------------------------
// nslValParent and nslRefParent to be replaced.
//

public NslData nslValParent(String name) {
	NslData nnn =null;
    if (nslGetParentModule() == null) {
	return null;
    } else {
	nnn  = nslGetParentModule().nslGetDataVar(name); 
      if (nnn==null) {
// 98/7/30 aa: if you did not find him in the parent then look in the grandparent 
		nnn=nslGetParentModule().nslValParent(name);
	}
    }
    if (nnn==null) {
	System.err.println("Error: NslModule: Could not find hierarchy variable >"+name);
	System.err.println("Went all the way to >"+this.nslGetName());
    }
    return nnn;
}
/*
public NslData nslRefParent(String name) {  // todo: remove 
	NslData nnn =null;

    if (nslGetParentModule() == null) {
    	return null;
    } else {
    	nnn= nslGetParentModule().nslGetDataVar(name);
	
      if (nnn==null) {
// 98/7/30 aa: if you did not find him in the parent then look in the grandparent 
		nnn=nslGetParentModule().nslRefParent(name);
	}
    }
    if (nnn==null) {
	System.err.println("Error in NslModule: Could not find hierarchy variable for reference >"+name);
	System.err.println("Went all the way to >"+this.nslGetName());
    }
    return nnn;
  }
*/
/**************************************************************************/
    
  public void nslSetDisplayFrame(NslFrame  df) { _displayframe=df; }
 
  public void nslAddModelPlots(NslFrame df) { nslinitwindow(df); }
  public void nslAddModelPlots(NslFrame df, String s) {nslinitwindow(df,s); }
  public void nslInitTempRun() {}
  public void nslInitTempTrain() {}
  public void nslInitTempModule() {}
  public void nslInitTempRunEpoch() {}
  public void nslInitTempTrainEpoch() {}

// aa: 99/9/1 : do we still need these?
  public void nslinitwindow(NslFrame df) { nslInitWindow(df);}
  public void nslinitwindow(NslFrame df, String s) {nslInitWindow(df,s);  }
  public void nslInitWindow(NslFrame df) { }
  public void nslInitWindow(NslFrame df, String s) { }
  public void nslInitWindow() {}
}




