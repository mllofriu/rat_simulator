/* SCCS  @(#)NslApplet.java	1.3---09/01/99--00:17:32 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////////////
//
// NslApplet.java
//

/**
 Transform java application NslMain to java applet NslApplet
 */
 
import java.applet.Applet;
import nslj.src.system.NslSystem;

public class NslApplet extends Applet {

    String modelname = null;   // the model's name to be run

    public void stop() {
        NslMain.TopLoaded= false;
    }

    public void init() {
	modelname=this.getParameter ("MODEL");
	if (modelname == null) {
	    System.out.println("ERROR: No model name specified in html file.");
  	    stop();
 	} else {
  	    System.out.println("Loading model:"+modelname);
  	    String[] fakeargs= new String[1];
	    fakeargs[0]=modelname;
	    NslSystem.applet = true;
	    NslMain.main(fakeargs);
 	}
    }
}

