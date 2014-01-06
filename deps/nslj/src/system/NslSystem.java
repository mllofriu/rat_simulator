/* SCCS  %W% --- %G% -- %U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslSystem.java
//
//////////////////////////////////////////////////////////////////////

/**
* The main system. Checks functional modules like interpreter,
  file system, command system, simulation module, numerical method,
  simulation status.
*/
// 99/6/28 got rid of other instance of math classes 
// except things related to differential method: setApproximationMethod stuff

package nslj.src.system;

import nslj.src.cmd.*;
import nslj.src.lang.*;
import nslj.src.math.*;
import nslj.src.display.*;
import nslj.src.nsls.struct.*;
import tcl.lang.*;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.math.BigDecimal;
import java.lang.reflect.*;

public class NslSystem {

  NslInterpreter interpreter;
  public NslScheduler scheduler;
  
  public NslSync interpMonitor;
  public NslSync stepMonitor;
  public NslDoubleSync displayMonitor;
  public NslDoubleSync displayMonitorAck;
  
  int displayCounter;
  
  public boolean breakState=true;
  public boolean stepCmdRun=false;

  public boolean _runDeltaChanged=false;
  public boolean _trainDeltaChanged=false;
  
  public char _accessibilityChar='R';
  
  public static boolean applet = false;
 
  // NslFileSys file_system;
  // NslCmdSys cmd_system;
  // NslGraphicSys graphic_system;
  // public NslDisplaySystem display_system;

  public NslDisplaySystemVector display_system_list;
  // public NslExecutive executive;
  // 98/7/21 aa: do we need these math instances since they are defined below?
  // with nslmul and nsladd etc?
//  NslMul mul=new NslMul();
//  NslDiv div=new NslDiv();
//  NslAdd add=new NslAdd();
///  NslSub sub=new NslSub();

  Vector module_list;
  Vector cmd_list;
  Vector nsldiff_list;
  
  Vector nslsCommandNames;
  Vector nslsCommandObjects;
  
  public static String module_executing="";
  public static char init_run_char='B'; // B=before init, D=during, A=after
					// After means after init&nslUpdateBuffers
  //public static char Pscheduler.schedulerMode='R';  // T=Train, R=running, N=none

  NslModule modelRef;  // current module in context. It is for faster
                        // actions like inspection.

  long totalEpochTimer;	// Number of seconds taken by all executed epochs
  long totalCycleTimer;	// Number of seconds taken by all executed cycles

  long epochStartingTime; // Starting time when current epoch execution began
  long cycleStartingTime; // Starting time when current cycle execution began
                        
  double cur_time;      // current simulation time
  
  int numRunEpochs = 1;      // Number of running epochs
  int runEpoch = 1;	     // Actual run epoch
  
  int    cur_cycle;     // current run simulation cycle
  double end_time;      // run simulation "end" time
  double _runDelta; // delta t, dt
 
  int numTrainEpochs = 1;     // Number of training epochs
  int trainEpoch = 1;	      // Actual train epoch

  public boolean endEpochChanged=false;

  int    train_cur_cycle;     // current train simulation cycle
  double train_end_time;      // train "end" time
  double _trainDelta;     // delta t, dt

  public NslDiff nsldiff;  // current differential method
  double _approximationTimeConstantTM=1.0; // time constant tm default
  double _approximationDelta; // integration delta or time step size
  boolean _approximationDeltaManuallySet=false; // 

  public boolean doubleBuffering=false;  //98/9/12 aa: flag -TODO move to module

  public boolean smallScreen=false;  //98/9/22 aa: for 800x600 screens
	//98/8/4 aa added noDisplay flag
  public boolean noDisplay=false;      //noDisplay mode: run without graphics windows
			    // decide to make debug public for speed
  public int debug=0;      //debug mode: has levels 0=none 1=some 
  public boolean stdout; 
  public boolean stderr; 
  // should add a nsldebug flag to get actual nslj debug statements

  //98/9/11 aa added schedulerMethod string
  public String schedulerMethod="pre";  // "pre" parent before child
					// "post" parent after child
					// "mixed" init methods do p before c; 
					//         run methods do p after c.

  protected String protocolName="manual";

/* 99/6/28 taking out
public NslAdd  nsladd ;
public NslAbs nslabs;
public NslConv nslconv ;
public NslConvZero nslconvzero ;
public NslConvW nslconvw;
public NslConvC nslconvc;

public NslDiv  nsldiv ;

//public NslDiff nsldifftrain ; 
public NslElemMult nslelemmult;
public NslElemDiv nslelemdiv;
public NslExp nslexp;

public NslFillRows nslfillrows;
public NslFillColumns nslfillcolumns;
public NslGuassian nslguassian;
public NslGetRow nslgetrow;
public NslGetColumn nslgetcolumns;
public NslGetSector nslgetsector;

public NslMath nslmath ;  ///???????????
public NslMaxElem  nslmaxelem ;
public NslMaxValue  nslmaxvalue ;
public NslMinElem  nslminelem ;
public NslMinValue  nslminvalue ;

public NslMul  nslmul ;
public NslProd nslprod ;
public NslProduct nslproduct ;

public NslRamp nslramp ;
public NslRandom nslrandom;
public NslRint nslrint;
public NslSaturation nslsaturation ;

public NslSetSector nslsetsector;
public NslSetRow nslsetrow;
public NslSetColumn nslsetcolumn;

public NslSigmoid nslsigmoid ;
public NslSigmoid2 nslsigmoid2 ;
public NslStep nslstep ;
public NslSub  nslsub ;
public NslSum nslsum;
public NslSumRows nslsumrows;
public NslSumColumns nslsumcolumns;
public NslTrans nsltrans;
public NslTranspose nsltranspose ;
*/

    NslExecutive executive;
/* you can use below line to get instantiation statements deom the above:
   awk '{print $2 "= new " $1 "();"}'
*/

  /**
   * Set up all system lists for module and command registration.
     Set up simulation clock and other system parameter to a
     default value
   */
 
public NslSystem() {
	double deltaDefault = 0.1;
 	modelRef=null;
    //module_list         = new Vector(20, 10);
    cmd_list            = new Vector(30, 10);
    nsldiff_list    = new Vector(2,1);
    
    nslsCommandNames    = new Vector(20, 10);
    nslsCommandObjects  = new Vector(20, 10);    
    
    display_system_list = new NslDisplaySystemVector(1,1);
    
    modelRef = null;
 
    cur_time      = 0.0;
    
    cur_cycle     = 1;
    end_time      = 0.0;
    _runDelta = deltaDefault;
    
    train_cur_cycle     = 1;
    train_end_time      = 0.0;
    _trainDelta = deltaDefault;
    
    init_run_char   = 'B';
    schedulerMethod = "pre";
    
    //System.out.println(Double.toString(_runDelta));
    
    _approximationDelta = deltaDefault;
    _approximationTimeConstantTM = 1.0;  

    // begin aa: 99/9/1 : this was in addModel
    nsldiff = (NslDiff)new NslDiffEuler(this);//98/7/21 aa : passes in system
    nsldiff.nslSetApproximationDelta(_approximationDelta);//98/7/21 aa
    nsldiff.nslSetApproximationTimeConstant(_approximationTimeConstantTM);
    //end aa: 99/9/1
  
//    instantiateMath();
    
// command line input parameters
    smallScreen=false; //98/9/22 aa for 800x600 resolution	
    noDisplay=false;   //98/8/4 aa: added noDisplay flag
    debug=0;           //98/8/4 aa: added debug level   
//  stdin="script";
//  stderr="console";
    
    interpMonitor = new NslSync();   
    stepMonitor = new NslSync();   
    displayMonitor = new NslDoubleSync();   
    displayMonitorAck = new NslDoubleSync();   
  }

// private void instantiateMath()
// {
//
//nsldifftrain = (NslDiff)new NslDiffEuler(this);//98/7/21 aa : passes in system
//nsldifftrain.nslSetRunDelta(_runDelta);
//nsldifftrain.nslSetApproximationDelta(_approximationDelta);//98/7/21 aa

/* taking out 99/6/28 aa
nslabs = new NslAbs();
nsladd = new NslAdd();
nslconcatenaterows = new NslConcatenateRows();
nslconcatenatecolumns = new NslConcatenateColumns();
nslconv = new NslConv();
nslconvzero = new NslConvZero();
nslconvw = new NslConvW();
nslconvC =new NslConvC();
nsldiv = new NslDiv();
nslelemmult = new NslElemMult();
nslelemdiv = new NslElemDiv();
nslexp = new NslExp();
nslfillrows = new NslFillRows();
nslfillcolumns = new NslFillColumns();
nslgaussian = new NslGaussian();
nslgetcolumn = new NslGetColumn();
nslgetrow = new NslGetRow();
nslgetsector = new NslGetSector();
nslmath = new NslMath();  ///???????????????????????
nslmaxvalue = new NslMaxValue();
nslminvalue = new NslMinValue();
nslmaxelem = new NslMaxElem();
nslminelem = new NslMinElem();
nslmul = new NslMul();
nslprod = new NslProd();
nslproduct = new NslProduct();
nslramp = new NslRamp();
nslrandom = new NslRandom();
nslrint = new NslRint();
nslsaturation = new NslSaturation();
nslsetsector = new NslSetSector();
nslsetrow = new NslSetRow();
nslsetcolumn = new NslSetColumn();
nslsigmoid = new NslSigmoid();
nslsigmoid2 = new NslSigmoid2();
nslstep = new NslStep();
nslsub = new NslSub();
nslsum = new NslSum();
nslsumrows = new NslSumRows();
nslsumcolumns = new NslSumColumns();
nsltrans = new NslTrans();
nsltranspose = new NslTranspose();
*/
// }

// toString - print some values of system

  public String toString() {
	String tmp ;
	if (nslGetModelRef()!=null) {
	  tmp = new String("System: model name: "+nslGetModelRef().nslGetName());
	} else {
	  tmp = new String("System: no model yet");
	}
	return tmp;
  }

  // Old End Methods

  public void SetEndTime(double val) {
	end_time = val;
  }

  public double endTime() {
	return end_time ;
  }

  public void initSys() {
  }

  public void endSys() {
  }

  public void initModule() {
	modelRef.nslSetProtocolFlagRecursiveDown(protocolName);
	display_system_list.init();
  }

  public void initRunEpoch() {
        totalEpochTimer = 0;
	runEpoch = 1;
	init_run_char = 'D';  //during
	//System.err.println("System Epoch");
modelRef.nslSetProtocolFlagRecursiveDown(protocolName);	
  	display_system_list.initEpoch();
	init_run_char = 'A';  //after
  }

  public void initTrainEpoch() {
        totalEpochTimer = 0;
	trainEpoch = 1;
	init_run_char = 'D';  //during
modelRef.nslSetProtocolFlagRecursiveDown(protocolName);	
  	display_system_list.initEpoch();
	init_run_char = 'A';  //after
  }

/*  public void initAll() {
        cur_time   = 0.0;
        cur_cycle  = 1;
	runEpoch   = 1;
	trainEpoch = 1;
        totalCycleTimer = 0;	
	totalEpochTimer = 0;
  }*/

  /**
   * Initialize the system. Set the clock to zero. 
   * Ready for next simulation to run.
   * init_run_flag=before, during, after B,D,A - after = after nslUpdateBuffers
   */
  public void initRun() {  
        init_run_char = 'D';  //during

        cur_time  = 0.0;
        cur_cycle = 1;

        totalCycleTimer = 0;

        nsldiff.setDelta(_runDelta);	
	modelRef.nslSetProtocolFlagRecursiveDown(protocolName);	
  }

  public void endRun() {
  	//System.out.println("Printing last run");
  	//System.out.flush();	
  	display_system_list.init();
  }

  public void endRunEpoch() {
  	display_system_list.init();
  }

  /**
   * Initialize the system. Set the clock to zero. Initialize
   *  scheduler. Ready for next simulation to run.
   *  init_run_flag=before, during, after B,D,A - after = after nslUpdateBuffers
   */
  public void initTrain() {
        
        init_run_char = 'D';  //during
        
        cur_time  = 0.0;
        cur_cycle = 1;
        
        totalCycleTimer = 0;

        nsldiff.setDelta(_trainDelta);// _trainDelta
	modelRef.nslSetProtocolFlagRecursiveDown(protocolName);
  }

  public void endTrain() {
  	//System.out.println("Printing last train");
  	//System.out.flush();	
  	display_system_list.init();
  }

  public void endTrainEpoch() {
  	display_system_list.init();
  }    

  public void endModule() {
  }

 public boolean isSchedulerInRunMode() {
 	return scheduler.schedulerMode=='R';
 }
 
 public boolean isSchedulerInTrainMode() {
 	return scheduler.schedulerMode=='T';
 }
 
  /**
   * Add a well-defined module into the system. Connect the internal
     modules.
   * @param module module to be added
   */
  public void addModel(NslModule module) {
    //System.out.println("NslSystem:addModel: Adding Model "+module.nslGetName());
    //NslCmdInitSys is = new NslCmdInitSys();

    modelRef=module; 
    //NslCmdInitModule im = new NslCmdInitModule();
    
    reflect();
    //addModelToScheduler(module);
    modelRef=module;
  
    module.nslResetBuffering();
    module.nslConnChildren();
    module.nslConnMissingLinks();
    
    // initlize this new module
    //System.out.println("NslSystem: doing execute!!");
    
    //is.execute(module);  //execute everyone's initSys - should be done in constructor
    //im.execute(module);  //execute everyone's initModule

    if (!scheduler.isAlive()) {
	scheduler.start();
    }       
    //System.out.println("NslSystem: done execute!!");
  }

  /**
   * Get the module list at the root level
   * @return a vector of NslModules
   */

  /*
  public Vector getModuleVector() {
    return module_list;
  }
  */

/* used for testing nslGetModuleRef() 
public NslModule _nslGetModuleRef(String name) {
  System.out.println(">>>>>>>> Asking for ["+name+"]");
  NslModule ret=_nslGetModuleRef(name);
  if (ret!=null)
    System.out.println(">>>>>>>> Reply      :"+ret.nslGetName());
  else
    System.out.println(">>>>>>>> Reply      :*NULL*");
  return ret;
}
*/
  /**
   * Get the module with label name
   * @param name The name to search
   * @return module, null if not found
   */
  public NslModule nslGetModuleRef(String name) {
    StringTokenizer str_token = new StringTokenizer(name, ".");
    Enumeration E = module_list.elements();
    NslModule leaf = null;
    String token;
    int i=0;
    if(!str_token.hasMoreTokens())
      return null;

    token = str_token.nextToken();
    // skip first token if it is null string
    if(token.equals("")) {
      if(!str_token.hasMoreTokens())
	return null;      
      token = str_token.nextToken();
    }

    if (E.hasMoreElements()) {
      while(E.hasMoreElements()) {
	leaf = (NslModule)E.nextElement();
	//System.out.println("NslSystem:debug:COMPARE "+leaf.nslGetName()+" x "+token);
	if (token.equals(leaf.nslGetName())) {
	  // found it!
	  if (str_token.hasMoreTokens()) {
	    // search in recursively
	    if (leaf.nslGetModuleChildrenVector() == null) {
	      // dead end, it is a leaf module
	      return null;
	    }
	    // keep searching next level
	    E = leaf.nslGetModuleChildrenVector().elements();
	    token = str_token.nextToken();
	  }
	  else {
	    // bingo
	    return leaf;
	    
	  }
	
	} // if match name	
      }
    }
    return null;
  }


  /**
   * Get the module with label name
   * @param name The name to search
   * @return module, null if not found
   */
  public NslModule nslGetModuleRef(String name,char desiredAccess) {
    NslModule leaf = null;
    String token;
    int i=0;

    StringTokenizer str_token = new StringTokenizer(name, ".");
	// todo: ?? system knows models modules ??
    Enumeration E = module_list.elements();

    if(!str_token.hasMoreTokens())
      return null;

    token = str_token.nextToken();
    // skip first token if it is null string
    if(token.equals("")) {
      if(!str_token.hasMoreTokens())
	  return null;      
      token = str_token.nextToken();
    }
    while(E.hasMoreElements()) {
	leaf = (NslModule)E.nextElement();
	//System.out.println("NslSystem:debug:COMPARE "+leaf.nslGetName()+" x "+token);
	if (token.equals(leaf.nslGetName())) {
        // found it!
	  if (((desiredAccess=='R') && (leaf.nslGetAccess()=='R') ||(leaf.nslGetAccess()=='W'))||
            ((desiredAccess=='W')&&(leaf.nslGetAccess()=='W'))){
	    // good access!
	    if (!str_token.hasMoreTokens()) {
		 // end of string
		  return leaf; 
	    // else search recursively
	    } else if (leaf.nslGetModuleChildrenVector() == null) {
	      // dead end, it is a leaf module
	      return null;
	    } else {
	      // keep searching next level
	      E = leaf.nslGetModuleChildrenVector().elements();
	      token = str_token.nextToken();
	    } // end if (!str_token.hasMoreTokens
        } // end if (((access
	} // end if (token.equals.	
    } // end while (E.hasMoreElements
    return null;
  }
//----------------------------------
/**
   	* Get the ClassInstance or NslModule with label name
* @param name -The name to search
* @param desiredAccess - what type of desiredAccess is desired
	* @param parent - where are we starting from
   	* @return moduleOrClass - NslBase, or null if not found
 */
public NslHierarchy getRefToModuleOrClass(String name, char desiredAccess) {
   
   NslHierarchy parent = modelRef;
    
    NslModule moduleOrClass1 = null;
    NslClass moduleOrClass2 = null;
    NslHierarchy moduleOrClass=null;
    char moduleOrClassAccess='N';

    boolean found=false;
    String token;
    int i=0;
    Vector parentmodulevector=null;
    Vector parentclassvector=null;

    Enumeration moduleinstances=null;
    Enumeration classinstances=null;
    NslHierarchy modelref=null;

    //System.err.println("NslSystem: getModuleOrClassInst: the name is: "+name);
    StringTokenizer str_token = new StringTokenizer(name, ".");
    if (!str_token.hasMoreTokens()){
      //System.err.println("NslSystem: getModuleOrClassInst: model name was null");
      return null;
    } else {
        // get first token which should be the model name
	token = str_token.nextToken();
    }
      // skip first token if it is null string due to dot
    // the string name must start with model or .model
    if (token.equals("")) {
          if(!str_token.hasMoreTokens()){
	    return null;   
          } else {
		token=str_token.nextToken();
                // System.err.println("NslSystem: getRefToModuleOrClass: dot.token1 is:"+token);
          }
    }
    // process the model name
    modelref=nslGetModelRef();
    if (!(modelref.nslGetName().equals(token))) {
	           //System.err.println("NslSystem: getRefToModuleOrClass: first token is not the model name.");
		return null;		  
    } else if (((desiredAccess=='R')&&(modelref.nslGetAccess()=='N'))
	||((desiredAccess=='W')&&(modelref.nslGetAccess()!='W'))) {
	//System.err.println("NslSystem: getRefToModuleOrClass: model does not have read access.");
		return null;
    } else if(!str_token.hasMoreTokens()){ // model was all there was
	   return modelref;
    }

    while (str_token.hasMoreTokens()) {
	token = str_token.nextToken();
	// System.err.println("NslSystem: getRefToModuleOrClass: token1: "+token);


      // System.err.println("NslSystem: getRefToModuleOrClass: parent is:"+parent.getClass().nslGetName());
      if (parent instanceof NslModule) {
           parentmodulevector = ((NslModule)parent).nslGetModuleChildrenVector();
	   parentclassvector = ((NslModule)parent).nslGetClassInstancesVector();
           // System.err.println("NslSystem: getRefToModuleOrClass: parent is NslModule");
      } else {
	   parentclassvector = ((NslClass)parent).nslGetClassInstancesVector();
           // System.err.println("NslSystem: getRefToModuleOrClass: parent is NslClass");

      }

      found=false;
      if ((parentmodulevector==null) && (parentclassvector==null)){	
           //  System.err.println("NslSystem: getRefToModuleOrClass: parent is vectors are both null");
		return null;		
      }

	if (parentmodulevector!=null){
          moduleinstances = parentmodulevector.elements();
          while (moduleinstances.hasMoreElements()) {
	    moduleOrClass1 = (NslModule)moduleinstances.nextElement();
	    //  System.out.println("NslSystem:debug:getRefToModuleOrClass 1 "+moduleOrClass1.nslGetName()+" - token is: "+token);
	    if (token.equals(moduleOrClass1.nslGetName())) {
	 	found=true;
		moduleOrClass=moduleOrClass1;
		break;  //found it
	    }
        }//end while
      }
      if (!found) {
	  if (parentclassvector!=null){
            classinstances = parentclassvector.elements();
            while(classinstances.hasMoreElements()) {
	       moduleOrClass2 = (NslClass)classinstances.nextElement();
	       //  System.out.println("NslSyStem:debug:getRefToModuleOrClass 2 "+moduleOrClass2.nslGetName()+" x "+token);
	       if (token.equals(moduleOrClass2.nslGetName())) {
	 	   found=true;
                   moduleOrClass=moduleOrClass2;
		   break;  //found it
	       }
	     } // end while
	   } // end if parentclassvector not null
      }// end if not found 
      if (!found) {
          //   System.err.println("NslSystem: getRefToModuleOrClass:returning not found");
        return null;
      } else {
          //   System.err.println("NslSystem: getRefToModuleOrClass:did find");
	  moduleOrClassAccess=moduleOrClass.nslGetAccess();
      if ((moduleOrClassAccess=='N')||((desiredAccess=='W') && (moduleOrClassAccess=='R'))){ 
          //   System.err.println("NslSystem: getRefToModuleOrClass:did find, but access denied.");
	     return null;
        } else {
           //  System.err.println("NslSystem: getRefToModuleOrClass:did find, access approved, getting next token.");
          // good access!
	    if (!str_token.hasMoreTokens()) {  // if at end
		  return moduleOrClass;  //success 
	    // else search recursively
	    } else {
		//keep searching next level by doing next outer loop
		parent=moduleOrClass;
	        //token=str_token.nextToken();
                //System.err.println("NslSystem: getRefToModuleOrClass: token3: "+token);
	    } // end if more tokens
	  } // end if good access
	} // end if (found) 
    } // end while more tokens
    System.err.println("NslSystem:getRefToModuleOrClass:bottom returning null");
    return null;
  }

    //-------------------------------
    
    public NslData nslGetDataVar(String name) {
	char desiredAccess = 'R';
	NslData nslnum = null;
	nslnum= nslGetDataVar(name,desiredAccess);
        return nslnum;
    }


    public NslData nslGetDataVar(String name, char desiredAccess) {
	int index;	
	String moduleOrClass_name;
	String nsl_data_name;
	NslHierarchy moduleOrClass;

	index = name.lastIndexOf('.');

	if (index == -1) {
	    // parse error
	    return null;
	}
	
	moduleOrClass_name = name.substring(0, index);
	nsl_data_name      = name.substring(index+1);

	//System.err.println("NslSystem:debug:index: "+index+" :moduleOrClass: "+moduleOrClass_name+" :numeric: "+nsl_data_name);

	moduleOrClass = getRefToModuleOrClass(moduleOrClass_name,desiredAccess);

	if (moduleOrClass == null) {
	    //System.err.println("NslSystem:nslGetDataVar: parent module or class is null.");
	    return null;
	} else {
	    //System.err.println("NslSystem:nslGetDataVar: going for parent module or classes child numeric.");
	    return moduleOrClass.nslGetDataVar(nsl_data_name,desiredAccess);
    	}
    }


    public NslData nslGetValue(String name) {
	NslData temp = null;

	temp = nslGetDataVar(name,'R');
	
	if (temp==null) {
	    System.err.println("NslSystem: nslGetValue: variable is wrong or is not readable "+name);
	    System.err.println("Note: Use whole name (hierarchical name) starting with the model name.");
	    return null;
	} else {
	    return temp.duplicateThis();
	}
    }


    public boolean nslSetValue(String target, NslData value) {
	boolean success = false;
	NslData temp = null;

	temp = nslGetDataVar(target, 'W');

	if (temp==null) {
	    return false;
 	} else {
	    return nslSetValueGeneric(temp,value);
	}
    }

    public boolean nslSetValue(NslData target, String name) {
	boolean success = false;
	NslData temp = null;

	if (target.nslGetAccess()!='W') {
	    return false;
	}
	
	// todo: do not allow write if parents, grandparent access is not write.
	// else
	
	temp = nslGetDataVar(name, 'R');
	if (temp==null) {
	    return false;
	} else {
	    return nslSetValueGeneric(target,temp);
	}
    }

    public boolean nslSetValueGeneric(NslData target, NslData provider) {

	int targetdim   = target.getDimensions();
	int providerdim = provider.getDimensions();
	
	if (target   instanceof NslNumeric &&
	    provider instanceof NslNumeric) {

	    if (targetdim==providerdim) {
	        switch(targetdim) {
		    case 0: ((NslNumeric0)target)._set((NslNumeric0)provider); 
			    break;
		    case 1: ((NslNumeric1)target)._set((NslNumeric1)provider);
			    break;
		    case 2: ((NslNumeric2)target)._set((NslNumeric2)provider);
			    break;
		    case 3: ((NslNumeric3)target)._set((NslNumeric3)provider);
			    break;
		    case 4: ((NslNumeric4)target)._set((NslNumeric4)provider);
			    break;
	        }
	    
	        return true;
	    
	    } else if (providerdim == 0) {
	        switch(targetdim) {
		    case 0: ((NslNumeric0)target)._set((NslNumeric0)provider); 
			    break;
		    case 1: ((NslNumeric1)target)._set((NslNumeric0)provider);
			    break;
		    case 2: ((NslNumeric2)target)._set((NslNumeric0)provider);
			    break;
		    case 3: ((NslNumeric3)target)._set((NslNumeric0)provider);
			    break;
		    case 4: ((NslNumeric4)target)._set((NslNumeric0)provider);
			    break;
	        }
	        
		return true;
	    } // else 
	    
	} else if (target   instanceof NslString &&
		   provider instanceof NslString) {
		   
	    if (targetdim == providerdim) {
	        switch(targetdim) {
		    case 0: ((NslString0)target).set((NslString0)provider); 
			    break;
	        }
	    
	        return true;
	    }
	    
	} else if (target   instanceof NslBoolean &&
		   provider instanceof NslBoolean) {
		   
	    if (targetdim == providerdim) {
	        switch(targetdim) {
		    case 0: ((NslBoolean0)target).set((NslBoolean0)provider); 
		            break;
		    case 1: ((NslBoolean1)target).set((NslBoolean1)provider);
		            break;
		    case 2: ((NslBoolean2)target).set((NslBoolean2)provider);
		            break;
		    case 3: ((NslBoolean3)target).set((NslBoolean3)provider);
		            break;
		    case 4: ((NslBoolean4)target).set((NslBoolean4)provider);
		            break;
	        }
	    
	        return true;
	        
	    } else if (providerdim == 0) {
	        
	        switch(targetdim) {
		    case 0: ((NslBoolean0)target).set((NslBoolean0)provider); 
			    break;
		    case 1: ((NslBoolean1)target).set((NslBoolean0)provider);
			    break;
		    case 2: ((NslBoolean2)target).set((NslBoolean0)provider);
			    break;
		    case 3: ((NslBoolean3)target).set((NslBoolean0)provider);
		            break;
		    case 4: ((NslBoolean4)target).set((NslBoolean0)provider);
		            break;
	        }
	        
		return true;
	    }
	
	}
	
	return false;
    }

    public void nslPrintAllVariables() {
    	printModuleVariablesRecursively(modelRef);
    }
    
    public void printModuleVariablesRecursively(NslModule module) {
    	Vector moduleChildren = module.nslGetModuleChildrenVector();
    	Vector dataChildrenVector = module.nslGetDataVarsVector();
    	Vector classChildren = module.nslGetClassInstancesVector();
    	System.out.println("Module "+module.nslGetName());
    	System.out.println("");
    	Enumeration d = dataChildrenVector.elements();
    	NslData data;
	while(d.hasMoreElements()) {
	    data = (NslData)d.nextElement();
	    System.out.println(data.nslGetName());
	    System.out.println(data);
    	    System.out.println("");
    	}	
	
	Enumeration c = classChildren.elements();
	while(c.hasMoreElements()) {
	    printClassVariablesRecursively((NslClass)c.nextElement());
	}
	
	Enumeration e = moduleChildren.elements();
	while(e.hasMoreElements()) {
	    printModuleVariablesRecursively((NslModule)e.nextElement());
	}
    } 
    
    public void printClassVariablesRecursively(NslClass cl) {
    	Vector dataChildrenVector = cl.nslGetDataVarsVector();
    	Vector classChildren = cl.nslGetClassInstancesVector();
    	
    	Enumeration d = dataChildrenVector.elements();
    	NslData data;
	while(d.hasMoreElements()) {
	    data = (NslData)d.nextElement();
	    System.out.println(data.nslGetName());
	    System.out.println(data);
    	    System.out.println("");
	}	
	
	Enumeration c = classChildren.elements();
	while(c.hasMoreElements()) {
	    printClassVariablesRecursively((NslClass)c.nextElement());
	}	
    }   
    /**
     * Get the current module context
     * @return currnet module
     */
     
    public NslModule nslGetModelRef() {
	return modelRef;
    }

  /**
   * Set the current module context
   * @param module the module to be defined as current module
   */
  /*
  public void nslSetModelRef (NslModule model) {
      modelRef = model;
  }
  */

  /**
   * Add user command into the system
   * @param command
   */
  public void addCommand(NslCmd command) {
    cmd_list.addElement(command);
  }

  /**
   * get the user command with name <tt>name</tt>
   * @param name the command name
   * @return the command with the name, null if not found
   */
  public NslCmd getCommand(String name) {
    Enumeration E = cmd_list.elements();
    NslCmd command = null;

    if (E.hasMoreElements()) {
      while(E.hasMoreElements()) {
	command = (NslCmd)E.nextElement();
// 	System.out.println("== "+command.nslGetName()+" ===");
	if (name.equals(command.nslGetName()))
	  return command;
      }
    }
    return null;
  }

  /**
   * Set the current user command interpreter
   * @param interp
   */
  public void setInterpreter(NslInterpreter interp) {
      interpreter = interp;    	
  }
  
  /**
   * Get the current user command interpreter
   * @return Interpreter
   */
  public NslInterpreter getInterpreter() {
    
    return interpreter;
  }

  public void waitScheduler() {
  	//System.out.println("Waiting");
  	interpMonitor.nslWait();
  	//System.out.println("I am free");
  }

  public void continueCmd() {
  	//System.out.println("Unlocking");
  	interpMonitor.nslNotify();
  }
  
  public void waitStep() {
  	//System.out.println("Waiting");
    	stepMonitor.nslWait();
  	//System.out.println("I am free");
  }

  public void notifyStep() {
  	//System.out.println("Unlocking");
  	stepMonitor.nslNotify();
  }
  public void waitTheScheduler() {
  	//System.out.println("Waiting ");
  	//System.out.flush();
  	//displayMonitor.nslWait();
  	displayMonitor.nslRecv();
  	//displayMonitor.nslSend();
  	//System.out.println("I am free"+id);
  }

  public synchronized void notifyDisplays() {
  	//System.out.println("Unlocking");
  	//System.out.flush();
  	//displayMonitor.nslNotifyAll();
  	//displayMonitor.nslNotify();
  	displayMonitor.nslSend();
  	//displayMonitor.nslRecv();
  }
  
  public void waitDisplayAck() {  	  	
   	//System.out.println("Waiting one display");
   	//System.out.flush();
       	displayMonitorAck.nslRecv();       	   	
   	//System.out.println("One display has finished");
   	//System.out.flush();
  }

  public synchronized void notifySchedulerAck() {
  	//displayMonitor.nslSetMonitor(true);
  	displayMonitorAck.nslSend();
  }

  public synchronized boolean isStepHalted() {
        //System.out.println("Step break status: "+breakState);
  	return breakState;
  }

  public synchronized void breakStep() {
        //System.out.println("Breaking step");
  	breakState = true;
  }
  
  public synchronized void continueStep() {
        //System.out.println("Continue Step");
  	breakState = false;
  	notifyStep();	
  }
  
  public synchronized boolean isStepCmdRunning() {
        //System.out.println("Step break status: "+breakState);
  	return stepCmdRun;
  }

  public synchronized void stepCmdStarted() {
        //System.out.println("Breaking step");
        
  	stepCmdRun = true;
  	continueStep();
  }
  
  public synchronized void stepCmdFinished() {
        //System.out.println("Continue Step");
  	stepCmdRun = false;
  }
  
 /**
   * Set the current run-time scheduler
   * @param interp
   */
   
  public void nslSetScheduler(NslScheduler sch) {
    scheduler = sch;
  }
  
  /**
   * Get the current run-time scheduler
   * @return Scheduler
   */
   
  public NslScheduler getScheduler() {
    return scheduler;
  }
    
  /**
   * Add a numerical method to the database
   * @param m the numerical method
   */
  public void addApproximationMethod(NslDiff m) {
    nsldiff_list.addElement(m);
  }

  /**
   * Set current differetial (numerical method) to use
   * @param m numerical method.
   */
  public void nslSetApproximationMethod(NslDiff m) {
    nsldiff = m;
  }

  /**
   * Get current differential method in use
   */
  public NslDiff nslGetApproximationMethod() {
    return nsldiff;
  }

  public double getEpochTimer() {
    return (double)totalEpochTimer;
  }

  public double getCycleTimer() {
    return (double)totalCycleTimer;
  }

  public double getEpochAvgTime() {
    return (double)totalEpochTimer/(double)(getFinishedEpochs()==0?1:getFinishedEpochs());
  }

  public double getCycleAvgTime() {
    return (double)totalCycleTimer/(double)getFinishedCycles();
  }

  public void startCycleTimer() {
    cycleStartingTime = System.currentTimeMillis();
  }

  public void startEpochTimer() {
    epochStartingTime = System.currentTimeMillis();
  }

  public void stopCycleTimer() {
    long finishTime = System.currentTimeMillis();
    totalCycleTimer += finishTime - cycleStartingTime;  
  }

  public void stopEpochTimer() {
    long finishTime = System.currentTimeMillis();
    totalEpochTimer += finishTime - epochStartingTime;  
  }

  public void resetCycleTimer() {
    totalCycleTimer = 0;  
  }

  public void resetEpochTimer() {
    totalEpochTimer = 0;  
  }

  // Old Current time methods

  /**
   * Get the current time in simulation environment
   * @return cur_time - current time
   */
  public synchronized double getCurTime() {
    return cur_time;
  }
  
  /**
   * Set the current time in simulation environment
   * @param t - current time
   */
  public synchronized void setCurTime(double t) {
    cur_time = t;
  }
  
  // New Current time methods
  
  /**
   * Get the current time in simulation environment
   * @return cur_time - current time
   */
  public synchronized double getCurrentTime() {
    return cur_time;
  }
  
  /**
   * Set the current time in simulation environment
   * @param t - current time
   */
  public synchronized void setCurrentTime(double t) {
    cur_time = t;
    /* must also set the current cycle */
  }  

  /**
   * Get the current cycle in simulation environment
   * @return cur_cycle - current cycle
   */
  public synchronized int getCurrentCycle() {
    return cur_cycle;
    /* must also set the current time */
  }
  /**
   * Get the current cycle in simulation environment
   * @return cur_cycle - current cycle
   */
  public synchronized int getFinishedCycles() {
    return cur_cycle - 1 ;
    /* must also set the current time */
  }
  /**
   * Set the current cycle in simulation environment
   * @param t - current cycle int
   */
  public synchronized void setCurCycle(int cyc) {
    cur_cycle = cyc;
  }
  public synchronized void setCurrentCycle(int cyc) {
    cur_cycle = cyc;
  }

  /**
   * Increment by a one the cycle
   */
  public synchronized void incCycle() {
    cur_cycle += 1;
    
    /*double temp;
    switch (scheduler.schedulerMode) {
        case 'R':
    	    temp = _runDelta;
    	    break;
        case 'T':
    	    temp = _trainDelta;
    	    break;
    	default:
    	    System.out.println("Error incCycle");
    	    temp = 0.0;
    }
    cur_time += temp;
   
    return (cur_cycle);*/
  }
  
  public int getRunEpoch() {
  	return runEpoch;
  }
  
  public int getTrainEpoch() {
  	return trainEpoch;
  }

  public void setRunEpoch(int epoch) {
  	runEpoch = epoch;
  }

  public void setTrainEpoch(int epoch) {
  	trainEpoch = epoch;
  }

  public void incRunEpoch() {
  	runEpoch++;
  }
  
  public void incTrainEpoch() {
  	trainEpoch++;
  }

  /**
   * Increment by a <tt>run_time_step</tt>.
   */
  public void incTime() {
    double temp;
    //BigDecimal operand1,operand2;
        
    switch (scheduler.schedulerMode) {
        case 'R':
    	    temp = _runDelta;
    	    break;
        case 'T':
    	    temp = _trainDelta;
    	    break;
    	default:
    	    System.out.println("Error incTime");
    	    temp = 0.0;
    }
    //System.out.println("I have "+cur_time+" and "+temp);
    //operand1 = new BigDecimal((new Double(cur_time)).toString());
    //operand2 = new BigDecimal((new Double(temp)).toString());

    //cur_time = operand1.add(operand2).doubleValue(); 
    cur_time += temp;
    //cur_cycle += 1;

    //if (cur_cycle==1) {
      //System.out.println("DELTA "+temp);
    //}
    //System.out.println("NEWTIME "+cur_time);
    //System.out.println("NEWCYCLE "+cur_cycle);
  }

  public void setNumTrainEpochs(int n) {
      numTrainEpochs = n;
	endEpochChanged = true;
  }
  
  public void setNumRunEpochs(int n) {
      numRunEpochs = n;
	endEpochChanged = true;
  }
  
  public int getNumTrainEpochs() {
      return numTrainEpochs;
  }
  
  public int getNumRunEpochs() {
      return numRunEpochs;
  }

  public int getEpochs() {    
  
    switch (scheduler.schedulerMode) {
        case 'R':
    	    return numRunEpochs;
        case 'T':
    	    return numTrainEpochs;
    	default:
    	    System.out.println("error: Get Epochs");
    	    return 0;
    }
  }
  /**
   * Get the end time in simulation environment
   * @return end_time - end time
   */

  public double getEndTime() {    
  
    switch (scheduler.schedulerMode) {
        case 'R':
            //System.out.println("Getting end time: run "+end_time);
    	    return end_time;
        case 'T':
            //System.out.println("Getting end time: train "+train_end_time);
    	    return train_end_time;
    	default:
    	    System.out.println("errorGetEndTime");
    	    return 0.0;
    }
  }
  /**
   * Set the end time in simulation environment
   * @param t - end time
   */

  public void setEndTime(double t) {
    end_time = t;
    //System.out.println("Setting end time: "+end_time);
    NslTemporalCanvas.graphsize=t;
  } 
    
  public void setTrainEndTime(double val) {
    train_end_time = val;    
    if (train_end_time > end_time) {
        //System.out.println("Setting Trainend time: train "+train_end_time);
    	NslTemporalCanvas.graphsize=train_end_time;
    } else {
        //System.out.println("Setting Trainend time: run "+end_time);
    	NslTemporalCanvas.graphsize=end_time;
    }
  }
  
  public double getTrainEndTime(){
	return train_end_time ;
  }
  
   /**
   * Get the end time in simulation environment
   * @return end_time - end time
   */

  public double getRunEndTime() {
    return end_time;
  }
  /**
   * Set the end time in simulation environment
   * @param t - end time
   */

  public void setRunEndTime(double t) {
    end_time = t;
    if (end_time > train_end_time) {
        //System.out.println("Setting Runend time: run "+end_time);
    	NslTemporalCanvas.graphsize=end_time;
    } else {
        //System.out.println("Setting Runend time: train "+train_end_time);
        NslTemporalCanvas.graphsize=train_end_time;
    }
  }  
  
  /**
   * get run step size, delta t
   * @return step size
   */
  public double nslGetDelta() {
      switch (scheduler.schedulerMode) {
        case 'R':
            //System.out.println("Getting step size: run "+_runDelta);
    	    return _runDelta;
        case 'T':
            //System.out.println("Getting step size: train "+_trainDelta);
    	    return _trainDelta;
    	default:
    	    System.out.println("Error nslGetDelta");
    	    return 0.0;
    }
  }
  public int getCurrentEpoch() {
      switch (scheduler.schedulerMode) {
        case 'R':
            //System.out.println("Getting step size: run "+_runDelta);
    	    return runEpoch;
        case 'T':
            //System.out.println("Getting step size: train "+_trainDelta);
    	    return trainEpoch;
    	default:
    	    System.out.println("Error nslGetDelta");
    	    return 0;
    }

  }
  public int getFinishedEpochs() {
      switch (scheduler.schedulerMode) {
        case 'R':
            //System.out.println("Getting step size: run "+(runEpoch-1));
    	    return runEpoch-1;
        case 'T':
            //System.out.println("Getting step size: train "+(trainEpoch-1));
    	    return trainEpoch-1;
    	default:
    	    System.out.println("Error nslGetDelta");
    	    return 0;
    }

  }

  /**
   * get run step size, delta t
   * @return step size
   */
  
  public double nslGetRunDelta() {
    //System.out.println("Getting RunStep time: "+_runDelta);
    return _runDelta;
  }
  
  /**
   * set run step size, delta t
   * @param t step size
   */
  public void nslSetRunDelta(double t) {
    _runDelta = t;
    /*if (!_approximationDeltaManuallySet) {
	if (_trainDelta < _runDelta) {
	   _approximationDelta=_trainDelta;
	} else {
	   _approximationDelta=_runDelta;
	}
        nsldiff.nslSetApproximationDelta(_approximationDelta); 
    	//nsldiff.setDelta(t);
    }*/
    if (scheduler.schedulerMode == 'T') {
    	nsldiff.setDelta(t);
    }
    //System.out.println("Setting RunStep time: "+_runDelta);
    if (modelRef!=null) {
    	modelRef.nslResetRunDelta();
    }
    _runDeltaChanged = true;
  }
  
  /**
   * get train step size, delta t
   * @return step size
   */
  public double nslGetTrainDelta() {
    return _trainDelta;
  }

  /**
   * set train step size, delta t
   * @param t step size
   */
  public void nslSetTrainDelta(double t) {

	// aa: 99/9/1 what about the display delta as well???
    _trainDelta = t;
    /*if (!_approximationDeltaManuallySet) {
	if (_trainDelta < _runDelta) {
	   _approximationDelta=_trainDelta;
	} else {
	   _approximationDelta=_runDelta;
	}
        nsldiff.nslSetApproximationDelta(_approximationDelta); 
    	//nsldiff.setDelta(t);
    }*/
    if (scheduler.schedulerMode == 'T') {
    	nsldiff.setDelta(t);
    }

    if (modelRef!=null) {
    	modelRef.nslResetTrainDelta();
    }
    _trainDeltaChanged = true;
  }


  /**
   * get approximation delta - integration time step / numerical method time step tm
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
    _approximationDeltaManuallySet = true;
    //98/7/21 aa: had to do since NslDiff is dependent on delta and tm
    nsldiff.nslSetApproximationDelta(_approximationDelta); //new
//    nsldifftrain.nslSetApproximationDelta(t); //new???
  }

  /**
   * get approximation timeConstant - integration time step / numerical method time step tm
   * @return time step size
   */
  public double nslGetApproximationTimeConstant() {
    return _approximationTimeConstantTM;
  }
  /**
   * To set the time step size
   * @param t time step size
   */
  public void nslSetApproximationTimeConstant(double t) {
    _approximationTimeConstantTM = t;
    //99/9/1 aa: had to do since NslDiff is dependent on delta and tm
    nsldiff.nslSetApproximationTimeConstant(_approximationTimeConstantTM); //new
  }


  public synchronized void addDisplaySystem(NslDisplaySystem ds) {
//     display_system=ds;
    display_system_list.addElement(ds);
    ds.start();
  }

  public synchronized void remove(NslFrame df) {
    for (int i = 0; i< display_system_list.size(); i++) {
      NslDisplaySystem ds = (NslDisplaySystem) display_system_list.elementAt(i);
      if (ds.frame == df) {
	display_system_list.removeElementAt(i);
	break;
      }
    }
  }
  /**
   * getSmallScreen
   * @return smallScreen
   * 98/8/4 aa
   */
  public boolean getSmallScreen() {
    return smallScreen;
  }
  /**
   * setSmallScreen
   * @param boolean flag
   */
  public void setSmallScreen(boolean flag) {
    smallScreen = flag;
  }

  /**
   * getNoDisplay
   * @return noDisplay
   * 98/8/4 aa
   */
  public boolean getNoDisplay() {
    return noDisplay;
  }
  /**
   * setNoDisplay
   * @param boolean flag
   */
  public void setNoDisplay(boolean flag) {
    noDisplay = flag;
  }
  
    /**
     * Sets the variable that defines if the standard error 
     * is going to be redirected to the executive or not
     * @param flag The boolean value to be assigned
     */
     
    public void setStdErr(boolean flag) {
	stderr = flag;
    }  

    /**
     * Sets the variable that defines if the standard output
     * is going to be redirected to the executive or not
     * @param flag The boolean value to be assigned
     */
     
    public void setStdOut(boolean flag) {
	stdout = flag;
    }  

  /**
   * getDebug
   * @return debug 
   * 98/8/4 aa
   */
  public int getDebug() {
    return debug;
  }
  /**
   * setDebug
   * @param int flag
   */
  public void setDebug(int flag) {
	// note: 0=off; 1=some debug; 2= some more debug messages , etc
	// use if (system.debug>=x) in code to print debug messages
    debug = flag;
  }

  /**
   * getSchedulerMethod
   * @return schedulerMethod 
   * 98/9/11 aa
   */
  public String getSchedulerMethod() {
    return schedulerMethod;
  }
  /**
   * nslSetSchedulerMethod
   * @param int flag
	* note: 
	* 
 	* "pre" parent before child
	* "post" parent after child
	* "mixed" init methods do p before c; 
	*         run methods do p after c.
   */
  public void nslSetSchedulerMethod(String method) {

    schedulerMethod = method;
  }

    public void nslSetAccess(char v) {
    	_accessibilityChar = v;
    	nslSetAccessRecursive(v);
    }
    
    public char nslGetAccess () {
    	return _accessibilityChar;
    }      
    
    public void nslSetAccessRecursive(char v) {
        if (modelRef!=null) {
            modelRef.nslSetAccessRecursive(v);
        }
    }
    
    public void nslSetBuffering (boolean v) {
    	doubleBuffering = v;
    	resetPorts();
    }
    
    public boolean nslGetBuffering () {
    	return doubleBuffering;
    }
      
    public void resetPorts() {
        if (modelRef!=null) {
    	    modelRef.nslResetBuffering();   
    	    modelRef.nslConnChildren();
    	    modelRef.nslConnMissingLinks();
    	}
    }
      
    public void addNslsCommand(String name, String object) {
    
    	nslsCommandNames.addElement(name);
    	nslsCommandObjects.addElement(object);
    
    }
    
    public String getNslsObject(String name) {
    	
    	String temp, object = "";
    	int pos = 0;
    	
    	Enumeration e = nslsCommandNames.elements();
        while(e.hasMoreElements()) {
            temp = (String)e.nextElement();
            if (temp.equals(name)) {
            	object = (String)nslsCommandObjects.elementAt(pos);
            	break;
            }
            pos++;
        }
        
        return object;
    	
    }
    
    public void nslAddCommand(String name, String className) {
    	try {
    	    Class commandType = Class.forName(className);
    	    Class paramTypes[] = new Class[2];
      	    paramTypes[0] = Class.forName("java.lang.String");
      	    paramTypes[1] = Class.forName("nslj.src.lang.NslModule");
      	    Constructor commandConstructor = commandType.getConstructor(paramTypes);
	    Object params[] = new Object[2];
            params[0] = null;
            params[1] = nslGetModelRef();
      
	    NslCommand  command = (NslCommand) commandConstructor.newInstance(params);
	    
	    Executive.interp.createCommand(name, command);
	    
    	} catch (Exception e) {
    	    nslPrintln("Command \""+name+"\" couldn't be created");
    	    nslPrintln("Class \""+className+"\" was not found");
        }
    }

    public void nslAddCommand(String name, NslCommand command) {
    	try {
	    Executive.interp.createCommand(name, command);	    
    	} catch (Exception e) {
    	    nslPrintln("Command \""+name+"\" couldn't be created");
        }
    }    

    public void reflect() {
	try {
	    String objectName = ReflectObject.newInstance(Executive.interp, this.getClass(), this).toString();
	    addNslsCommand("system",objectName);
            //Executive.interp.eval("set system " + objectName);
            //Executive.interp.eval("rename " + objectName +" system");
        } catch (TclException e) {
        }
    }
    
    public void setExecutive(NslExecutive e) {
    	executive = e;
    }
    
    public void addProtocolToAll(String name) {
	modelRef.nslAddProtocolRecursiveDown(name);
    }
    
    public void addProtocol(String name, NslModule module) {
     	if (!noDisplay) {
    	    executive.addProtocol(name, module);
    	}
    }  
  
    public void nslCreateProtocol(String name, String label, NslModule module) {
     	if (!noDisplay) {
    	    executive.addProtocol(name, label, module);
    	}
    }    
    
    public boolean protocolExist(String name) {
     	if (!noDisplay) {
    	    return executive.protocolInList(name);
    	}    
    	return false;
    }
    
    public String nslGetProtocol() {
    	return protocolName;
    }

    public void nslSetProtocol(String name) {
	protocolName = name;
     	if (!noDisplay) {
    	    executive.execProtocol(name);
    	}    
	
	//cur_model.nslSetProtocolFlagRecursiveDown(name);
    }
    
    public void nslPrint(String msg) {
     	if (!noDisplay && executive !=null) {
     	    executive.shell.nslPrint(msg);
     	} else {
     	    System.out.print(msg);
     	}
    }
    
    public void nslPrintln(String msg) {
     	if (!noDisplay && executive !=null) {
     	    executive.shell.nslPrintln(msg);
     	} else {
     	    System.out.println(msg);
     	}
    }
    
    public void nslDebug(String className, String msg) {
        if (debug<=0) {
            return;
        }
        
     	if (!noDisplay && executive !=null) {
     	    executive.shell.nslPrintln(className + " [Debug]: "+msg);
     	} else {
     	    System.out.println(className + " [Debug]: "+msg);
     	}
    }
    
    public void redirectMessages() {
	if (!noDisplay) {
	    				
	    NslOut out = new NslOut(System.out);
    				
    	    if (stderr) {    				
    	        System.setErr(out);
    	    }

   	    if (stdout) {    				
    	        System.setOut(out);
    	    }
	}
    }
    
    public void nslPrintStatistics() {    
        nslPrintln("#");
        nslPrintln("# Model Name: \t\t"+modelRef.nslGetName());
        nslPrintln("# Protocol Name: \t\t"+nslGetProtocol());
	if (isSchedulerInTrainMode()) {
	        nslPrintln("# Phase: \t\tTrain");
	} else if (isSchedulerInRunMode()) {
	        nslPrintln("# Phase: \t\tRun");
	} else {
	        nslPrintln("# Phase: \t\tEnd");
	}
        nslPrintln("# Finished Epochs:\t"+ getFinishedEpochs());
        nslPrintln("# Simulation stopped at:\t"+ rounded(getCurrentTime()));
        nslPrintln("# Simulation delta size :\t"+ nslGetDelta());
        nslPrintln("# Finished Cycles:\t\t"+ getFinishedCycles());

        nslPrintln("# Average Cycle Time (ms):\t"+ getCycleAvgTime());
        //nslPrintln("# Average Epoch Time:\t"+ getEpochAvgTime());
        nslPrintln("#");
    }

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

    //
    // System methods for display system managment
    //
    
    public void init_displays() {
    
	display_system_list.init();
	
    }
    
    public int getNumberOfDisplays() {
    
    	return display_system_list.size();
    	
    }

    public boolean frameExist(String name) {
      
	Enumeration E = display_system_list.elements();
	NslDisplaySystem ds;
      
	while(E.hasMoreElements()) {
	    ds = (NslDisplaySystem) E.nextElement();
	    if (ds != null && ds.frame != null
		&& ds.frame.frameName.equals(name)) {
		return true;
	    }
	}
        
	return false;
    }
    
    public NslFrame getFrame(String name) {
      
	Enumeration E = display_system_list.elements();
	NslDisplaySystem ds;
      
	while(E.hasMoreElements()) {
	    ds = (NslDisplaySystem) E.nextElement();
	    if (ds != null && ds.frame != null
		&& ds.frame.frameName.equals(name)) {
		return ds.frame;
	    }
	}
        
	return null;
    }
}



