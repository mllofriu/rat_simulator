import javax.media.j3d.Canvas3D;
import java.io.*;
import javax.media.j3d.*;
import javax.vecmath.*;
//import com.sun.image.codec.jpeg.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.Canvas3D;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.TransformGroup;


public class TopWorldCanvas extends WorldCanvas {
    
    public TopWorldCanvas(GraphicsConfiguration gc, worldBranchGroup branchGroup) {
	super(gc, branchGroup);
    }
    
     public TopWorldCanvas(GraphicsConfiguration gc) {
	super(gc);
    }
    
    public void finalise() {
	simpleU.addBranchGraph(rootBranchGroup);

        TransformGroup viewTrans = simpleU.getViewingPlatform().getViewPlatformTransform();
        Transform3D myTransform3D2 = new Transform3D();
        myTransform3D2.rotX(Math.PI/180.0d*-90);
        
        Viewer v[] = simpleU.getViewingPlatform().getViewers();
        v[0].getView().setFrontClipDistance(0.0001);
	simpleU.getViewingPlatform().setNominalViewingTransform();
        
        Transform3D myTransform3D = new Transform3D();
        myTransform3D.setTranslation(rootBranchGroup.getTopView());
        myTransform3D.mul(myTransform3D2);
        viewTrans.setTransform(myTransform3D);
    }
    
    public void moveRobot(Vector3f vector) {
        rootBranchGroup.moveRobot(vector);
    }
    
    public  Point2d getGlobalPosition() {
    	return rootBranchGroup.getRobotView();
    }

    public  Point2d getFood() {
    	Point2d result = new Point2d(rootBranchGroup.getFood().x,rootBranchGroup.getFood().z);
    	return result;
    }

}

