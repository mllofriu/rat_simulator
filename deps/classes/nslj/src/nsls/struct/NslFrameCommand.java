/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.nsls.struct;

import tcl.lang.*;
import java.util.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import nslj.src.display.*;
import nslj.src.cmd.*;

public class NslFrameCommand implements Command  {

    private Interp tclInterp;

    // nsl frame options
    
    static final private String createOpts[] = {
       	"configure",
    };
    
    static final private String createSw[] = {
       	"-title",
       	"-rows",
       	"-column",
       	"-x0",
       	"-y0",
       	"-width",
       	"-height",
       	"-font",
       	"-background",
       	"-foreground",
       	"-freq",
    };

    public void cmdProc(Interp interp, TclObject argv[])
        throws TclException {
		
        tclInterp = interp;
        
        if (argv.length < 2) {
            echo("wrong # args: should be \""+argv[0]+"\" configure ?params?");
        }
        else {
            int index;
            
            index = TclIndex.get(tclInterp, argv[1], createOpts, "option", 0);
	    
	    switch (index) {
	    	case 0:
	    	    /*
	     	     * the following code implements the configure command
	     	     */

		    int ind, x0, y0, width, height, freq;
		    String name =argv[0].toString();
		    
		    NslCommandData nd = (NslCommandData)interp.getAssocData(name);
		    NslDisplaySystem ds = (NslDisplaySystem)nd.getData();
		    	
		    if (ds==null) {
		        echo("Display "+argv[0].toString()+" no longer exist");
		    }
		    
		    NslFrame frame = ds.frame;
		    
		    if (frame == null) {
		    	echo("Frame "+argv[0].toString()+" no longer exist");
		    }
		    
		    for (int i=2;i<argv.length;i++) {
		    	ind = TclIndex.get(tclInterp, argv[i], createSw, "sw", 0);
	    
			switch (ind) {
			    case 0:    
			        if (i+1<argv.length) {
			           i++;
			           frame.setTitle(argv[i].toString());
			        } else {
			           echo("Title name expected");
			        }
			        break;
			    case 1:			    
			        if (i+1<argv.length) {
			           i++;
			           frame.setRows(TclInteger.get(tclInterp, argv[i]));
			        } else {
			           echo("Number of rows expected");
			        }
			        break;
			    case 2:
			        if (i+1<argv.length) {
			           i++;
			           frame.setColumns(TclInteger.get(tclInterp, argv[i]));
			        } else {
			           echo("Number of columns expected");
			        }
			        break;
			    case 3:
			        if (i+1<argv.length) {
			           i++;
			           x0 = TclInteger.get(tclInterp, argv[i]);
			           y0 = frame.getY();
			           width = frame.getWidth();
			           height = frame.getHeight();
			           frame.setBounds(x0, y0, width, height);
			        } else {
			           echo("Position in x direction expected");
			        }
			        break;
			    case 4:
			        if (i+1<argv.length) {
			           i++;
			           y0 = TclInteger.get(tclInterp, argv[i]);
			           x0 = frame.getX();
			           width = frame.getWidth();
			           height = frame.getHeight();
			           frame.setBounds(x0, y0, width, height);
			        } else {
			           echo("Position in y direction expected");
			        }
			        break;
			    case 5:
			        if (i+1<argv.length) {
			           i++;
			           x0 = frame.getX();
			           y0 = frame.getY();
			           width = TclInteger.get(tclInterp, argv[i]);
			           height = frame.getHeight();
			           frame.setBounds(x0, y0, width, height);
			           
			        } else {
			           echo("Frame width expected");
			        }
			        break;
			    case 6:
			        if (i+1<argv.length) {
			           i++;
			           x0 = frame.getX();
			           y0 = frame.getY();
			           height = TclInteger.get(tclInterp, argv[i]);
			           width = frame.getWidth();
			           frame.setBounds(x0, y0, width, height);

			        } else {
			           echo("Frame height expected");
			        }
			        break;
			    case 7:
			       	if (i+1<argv.length) {
			           i++;
			           frame.setFontName(argv[i].toString());
			        } else {
			           echo("Font name expected");
			        }
			        break;
			    case 8:
			       	if (i+1<argv.length) {
			           i++;
			           
			           if (!NslColor.isValidColor(argv[i].toString())) {
			           	echo("Invalid color: "+argv[i].toString());
			           }
			           
			           frame.setBackgroundColor(argv[i].toString());
			           
			        } else {
			           echo("Background color expected");
			        }
			        break;
			    case 9:
			       	if (i+1<argv.length) {
			           i++;

			           if (!NslColor.isValidColor(argv[i].toString())) {
			           	echo("Invalid color: "+argv[i].toString());
			           }
			           
			           frame.setForegroundColor(argv[i].toString());
			           
			        } else {
			           echo("Foregroung name expected");
			        }
			        break;
			    case 10:
			        if (i+1<argv.length) {
			           i++;
			           freq = TclInteger.get(tclInterp, argv[i]);
			        } else {
			           echo("Update frequency expected");
			        }
			        break;
			}
			
			
		    }
	     }
		    
        }
    }
    
    private void echo(String s)
	throws TclException {

	tclInterp.setResult(s);
	throw new TclException(TCL.ERROR);
    }
	
}


