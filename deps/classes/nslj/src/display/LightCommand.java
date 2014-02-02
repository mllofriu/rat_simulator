//
// Copyright (c) 2002 University of Southern California Brain Project.
//
// This software may be freely copied provided the toplevel
// COPYRIGHT file is included with each such copy.
//
// E-mail nsl@java.usc.edu.
//
//    File: LightCommand.java
//  Author: Salvador Marmol
// 
// History: Date     Description
//          9/4/02   Start of development
//
//////////////////////////////////////////////////////////////////////

package nslj.src.display;

import javax.vecmath.*;
import javax.media.j3d.*;
import java.awt.Color;

import nslj.src.nsls.struct.*;

class LightCommand extends NslCommand {

    private TransformGroup lightsRoot;
    private int lightIndex;

    static final private String opts[] = {
	"set",
	"get",
    };

    static final private String opts2[] = {
	"color",
	"position",
    };

    public LightCommand(int lightIndex, TransformGroup lightsRoot) {
	super();
	this.lightIndex = lightIndex;
	this.lightsRoot = lightsRoot;
    }

    public void nslCmdProc() {

	int len = nslGetNumberOfParameters();

        if (len < 2) {
            System.out.println("wrong # args: should be \""+nslArgv[0]+"\" ?opts?");
            return;
        } else {
	    int index = nslGetCmdIndex(nslArgv[1], opts, "opts");
	    switch (index) {
	        case 0: /* set command */
			if (len < 6) {
			    System.out.println("wrong # args: should be \""+nslArgv[0]+"\" set ?opts? ?v1? ?v2? ?v3?");
            		    return;
        		} else {
			    index = nslGetCmdIndex(nslArgv[2], opts2, "opts");
			    DirectionalLight lgt = (DirectionalLight)lightsRoot.getChild(lightIndex);
			    switch (index) {
			    	case 0: /* color option */
				        float red   = nslFloat(nslArgv[3]),
					      green = nslFloat(nslArgv[4]),
					      blue  = nslFloat(nslArgv[5]);
   				        Color3f lColor = new Color3f(red, green, blue);
					lgt.setColor(lColor);
			    	        break;
			        case 1: /* position option */
				        float x = nslFloat(nslArgv[3]),
					      y = nslFloat(nslArgv[4]),
					      z = nslFloat(nslArgv[5]);
			                Vector3f lDir  = new Vector3f(x, y, z);
					lgt.setDirection(lDir);
			    	        break;
			    }
			}
                        break;


	        case 1: /* get command */
			if (len < 3) {
			    System.out.println("wrong # args: should be \""+nslArgv[0]+"\" set ?opts?");
            		    return;
        		} else {
			    index = nslGetCmdIndex(nslArgv[2], opts2, "opts");
			    DirectionalLight lgt = (DirectionalLight)lightsRoot.getChild(lightIndex);
			    switch (index) {
			    	case 0: /* color option */
   				        Color3f lColor = new Color3f();
					lgt.getColor(lColor);
					Color color = lColor.get();
					float red   = color.getRed()/255,
					      green = color.getGreen()/255,
					      blue  = color.getBlue()/255;
					nslReturnError("{ "+red+" "+green+" "+blue+" }");
			    	        break;
			        case 1: /* position option */
			                Vector3f lDir  = new Vector3f();
					lgt.getDirection(lDir);
				        float x = lDir.x,
					      y = lDir.y,
					      z = lDir.z;
					nslReturnError("{ "+x+" "+y+" "+z+" }");
			    	        break;
			    }
			}
                        break;
	    }
	}

    }
}