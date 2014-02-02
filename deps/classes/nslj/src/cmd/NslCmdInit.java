/* SCCS  @(#)NslCmdInit.java	1.10---09/01/99--16:11:42 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//
// NslCmdInit.java
//
//////////////////////////////////////////////////////////////////////

/**
* initialize current / a specific model/module
*/
package nslj.src.cmd;
import nslj.src.lang.NslModule;
import nslj.src.system.NslSystem;
//import nslj.src.lang.NslEnv;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class NslCmdInit extends NslCmd {
  /**
   *Setup class name and help engine
   */
    public NslCmdInit() {
        _name = "init";
        _simple_help_string = "init [module_name]";
    }
  /**
   * Print complex help on standard out
   */
  public void printHelp() {
    System.out.println("Nsl command : init");
    System.out.println("usage: init [module_name]");
    System.out.println("The root module / module_name module would be initialized");
  }

  /**
   * Initialize the system and current active module
     including its child modules
   */
  public void execute() {
    // check NslSystem. The system is initialized together
    // with all modules registered in teh system.
    //    system.init();
        NslModule temp=system.nslGetModelRef();    
	if (temp==null) {
		System.err.println("Error: NslCmdInit: null module name.");
		return;
	}
        system.init_run_char='D';  //during
	//System.out.println("debug: NslCmdInit: init_run_char 2 "+    system.init_run_char);

   	system.initRun(); //call the schedulers initilization method

    	initrun(temp);    
	nslUpdateBuffers(temp);

	system.init_displays();
	
 	//currently, just sets the last_nslUpdateBuffers_time to zero
   
   	/* moved from scheduler init - 98/9/12 aa */
//         if (system.display_system !=null) {
//        		system.display_system.collect();  // if nslUpdateBuffers has collect
			// do I need this here?
//         }
	// sets the system.init_run_char
        system.init_run_char='A';  //after
	//System.out.println("debug: NslCmdInit: init_run_char 2 "+    system.init_run_char);

    }
  /**
   * Initialize module named <tt>module_name</tt>
   * @param module_name
   */
  public void execute(String module_name) {
    execute();
	}

  /**
   * Initialize a series of modules in the array <tt>module_list</tt> and
     their corresponding child modules
   * @param module_list
   */
  public void execute(StringTokenizer st) {
    execute();
  }

  /**
   * Initialize module <tt>module</tt> and its child modules
     recursively.
   * @param module
   */
  public void initrun(NslModule module) {
    Vector module_children = module.nslGetModuleChildrenVector();
    NslModule child = null;

    Enumeration E1 = module_children.elements();
    
    if ((system.schedulerMethod.equals("pre"))||
        (system.schedulerMethod.equals("mixed"))) {
	    module.nslInitTempRun();
	    module.initRun();

	    while(E1.hasMoreElements()) {
		child = (NslModule)E1.nextElement();
	        initrun(child);
	    }
    }
    if (system.schedulerMethod.equals("post")) {
	    while(E1.hasMoreElements()) {
		child = (NslModule)E1.nextElement();
	        initrun(child);
	    }
	    module.nslInitTempRun();
	    module.initRun();
    }//end if and init
    
    }//end method


  /**
   * Update module <tt>module</tt> and its child modules
     recursively.
   * @param module
   */

  public void nslUpdateBuffers(NslModule module) {
    Vector module_children = module.nslGetModuleChildrenVector();
    NslModule child = null;

    Enumeration E1 = module_children.elements();

    //98/9/12 aa move data to output ports using nslUpdateBuffers
    // order not important since it it internal to the module
    //if (system.doubleBuffering) {
	    module.nslUpdateBuffers();

	    while(E1.hasMoreElements()) {
		child = (NslModule)E1.nextElement();
	        nslUpdateBuffers(child);
	    }
    //} //if doubleBuffering
    } //end nslUpdateBuffers

}//end class
