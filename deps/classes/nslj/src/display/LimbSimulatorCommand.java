//
// Copyright (c) 2002 University of Southern California Brain Project.
//
// This software may be freely copied provided the toplevel
// COPYRIGHT file is included with each such copy.
//
// E-mail nsl@java.usc.edu.
//
//    File: LimbSimulatorCommand.java
//  Author: Salvador Marmol
// 
// History: Date     Description
//          9/4/02   Start of development
//
//////////////////////////////////////////////////////////////////////


package nslj.src.display;

import javax.vecmath.*;
import javax.media.j3d.*;

import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.Cylinder;

import nslj.src.nsls.struct.*;

class LimbSimulatorCommand extends NslCommand {

    private static final String opts[] = {
	"create",
	"delete",
	"show",
    };

    private static final String createOpts[] = {
	"cylinder",
	"light",
    };

    private static int lightIndex=0;

    private Canvas3D       canvas;
    private BranchGroup    root;
    private TransformGroup lightsRoot;
    private TransformGroup limbsRoot;
    private BoundingSphere bounds;

/*    class LightData {
	int index;
	TransformGroup lightsRoot;

	LightData(int index, TransformGroup lightsRoot) {
	    this.index = index;
	    this.lightsRoot = lightsRoot;
	}
	
	public int getIndex() {
	    return index;
	}

	public TransformGroup getLight() {
	    return lightsRoot;
	}
    }*/

    public LimbSimulatorCommand(Canvas3D canvas, BranchGroup root, TransformGroup limbsRoot, 
			        TransformGroup lightsRoot, BoundingSphere bounds) {
	super();
	this.limbsRoot  = limbsRoot;
	this.lightsRoot = lightsRoot;
	this.bounds     = bounds;
	this.canvas     = canvas;
	this.root       = root;
    }

    public void nslCmdProc() {
	int len = nslGetNumberOfParameters();

        if (len < 2) {
            System.out.println("wrong # args: should be \""+nslArgv[0]+"\" simulator_command ?opts?");
            return;
        }

	int index = nslGetCmdIndex(nslArgv[1], opts, "opts");

        switch (index) {

	    case 0: /* create command */
                    if (len < 3) {
                        System.out.println("wrong # args: should be \""+nslArgv[0]+" create\" element ?opts?");
                    } else {
			index = nslGetCmdIndex(nslArgv[2], createOpts, "opts");

			switch (index) {
			    case 0: /* cylinder option */
			    	    break;

			    case 1: /* light option */

				    Color3f lColor = new Color3f(1.0f, 1.0f, 1.0f);
				    Vector3f lDir  = new Vector3f(0.0f, -1.0f, 0.0f);

				    DirectionalLight lgt = new DirectionalLight(lColor, lDir);
				    lgt.setInfluencingBounds(bounds);
				    lgt.setCapability(DirectionalLight.ALLOW_COLOR_READ);
				    lgt.setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
				    lgt.setCapability(DirectionalLight.ALLOW_DIRECTION_READ);
				    lgt.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
				    lightsRoot.addChild(lgt);

				    String name = "light" + lightIndex;
			            nslCreateCommand(name, 
					new LightCommand(lightsRoot.indexOfChild(lgt), lightsRoot));

				    lightIndex++;
				    nslReturnError(name);
				    break;
			}
                    }

                    break;

            case 1: /* delete command */
                    if (len < 3) {
                        System.out.println("wrong # args: should be \""+nslArgv[0]+" delete\" element");
                    } else {
                    }

                    break;

            case 2: /* show command */

		    // Let Java 3D perform optimizations on this scene graph.
		    root.compile();

		    // Create a simple scene and attach it to the virtual universe		    
	  	    SimpleUniverse su = new SimpleUniverse(canvas);
	
		    // This will move the ViewPlatform back a bit so the
		    // objects in the scene can be viewed.
		    su.getViewingPlatform().setNominalViewingTransform();
	
		    su.addBranchGraph(root);
                    break;
	}
    }
}