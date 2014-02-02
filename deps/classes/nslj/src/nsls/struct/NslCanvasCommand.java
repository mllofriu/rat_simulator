/* SCCS  @(#)NslCanvasCommand.java	1.6---09/01/99--00:19:28 */

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

public class NslCanvasCommand implements Command  {

    private Interp tclInterp;

    // nsl frame options
    
    static final private String createOpts[] = {
       	"configure",
    };
    
    static final private String createSwCanvas[] = {
       	"-title",
       	"-var",
       	"-graph",
       	"-position",
       	"-xlabel",
       	"-ylabel",
       	"-wymin",
       	"-wymax",
       	"-wxmin",
       	"-wxmax",
       	"-freq",
       	"-curvecolor",
       	"-curvestyle",
       	"-option",
    }; 
    
    public void cmdProc(Interp interp, TclObject argv[])
        throws TclException {
		
        tclInterp = interp;
        
        if (argv.length < 2) {
            echo("wrong # args: should be \""+argv[0]+"\" configure ?params?");
        } else {
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
		    NslCanvas c = (NslCanvas)nd.getData();
		    
		    if (c==null) {
		        echo("Canvas "+name+" no longer exist");
		    }
		    
		    for (int i=2;i<argv.length;i++) {
		    	ind = TclIndex.get(tclInterp, argv[i], createSwCanvas, "sw", 0);

			switch (ind) {
			    case 0:
			        if (i+1<argv.length) {
			            i++;
			            //title = argv[i].toString();
			        } else {
			            echo("Title name expected");
			        }
			        break;
			        
			    case 1:
			        if (i+1<argv.length) {
			            i++;
			            //varName = argv[i].toString();
			            //varDefined = true;
			        } else {
			            echo("Variable name expected");
			        }
			        break;
			        
			    case 2:
			        if (i+1<argv.length) {
			            i++;
			            //graph = TclIndex.get(tclInterp, argv[i], graphSw, "sw", 0);
			        } else {
			            echo("Graph type expected");
			        }
			        break;
			        
			    case 3:
			        if (i+1<argv.length) {
			            i++;
			            //position = TclIndex.get(tclInterp, argv[i], positionSw, "sw", 0);
			        } else {
			            echo("Position place expected");
			        }
			        break;
			        
			    case 4:
			        if (i+1<argv.length) {
			            i++;
			            //xlabel = argv[i].toString();
			        } else {
			            echo("X axis label expected");
			        }
			        break;
			         
			    case 5:
			        if (i+1<argv.length) {
			            i++;
			            //ylabel= argv[i].toString();
			        } else {
			            echo("Y axis label expected");
			        }
			        break;
			          
			    case 6:
			        if (i+1<argv.length) {
			            i++;
			            //wymin = TclDouble.get(tclInterp, argv[i]);
			        } else {
			            echo("Minimum Y axis value expected");
			        }
			        break;

			    case 7:
			        if (i+1<argv.length) {
			            i++;
			            //wymax = TclDouble.get(tclInterp, argv[i]);
			        } else {
			            echo("Maximum Y axis value expected");
			        }
			        break;

			    case 8:
			        if (i+1<argv.length) {
			            i++;
			            //wxmin = TclDouble.get(tclInterp, argv[i]);
			        } else {
			            echo("Minimum x axis value expected");
			        }
			        break;
			        
			    case 9:
			        if (i+1<argv.length) {
			            i++;
			            //wxmax = TclDouble.get(tclInterp, argv[i]);
			        } else {
			            echo("Maximum X axis value expected");
			        }
			        break;
			        
			    case 10:
			        if (i+1<argv.length) {
			            i++;
			            //freq = TclInteger.get(tclInterp, argv[i]);
			        } else {
			            echo("Update frequency expected");
			        }
			        break;
			        
			    case 11:
			       	if (i+1<argv.length) {
			            i++;
			            //curveColor = argv[i].toString();
			           
			            //if (!NslColor.isValidColor(curveColor)) {
			            //    echo("Invalid color: "+curveColor);
			            //}
			           
			        } else {
			            echo("Curve color expected");
			        }
			        break;

			    case 13:
			        if (i+1<argv.length) {
			       	    i++;
			            //option = TclIndex.get(tclInterp, argv[i], optionSw, "sw", 0);
			        } else {
			            echo("Option expected");
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
