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


public class WorldCanvas extends Canvas3D{
    
    /**
	 * Automatic gererated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	protected SimpleUniverse simpleU;
    protected worldBranchGroup rootBranchGroup;
    protected boolean set = false;
    protected java.awt.image.Raster realraster;
    
    public WorldCanvas(GraphicsConfiguration gc, worldBranchGroup branchGroup) {
	super(gc);
        rootBranchGroup = branchGroup;
        initialSetup();
        finalise();
    }
    
    
    public WorldCanvas(GraphicsConfiguration gc) {
	super(gc);
    }
    
    public void setBranchGroup(worldBranchGroup branchGroup) {
        rootBranchGroup = branchGroup;
        initialSetup();
        finalise();
    }
    
    
    public void postSwap() {
	GraphicsContext3D  ctx = getGraphicsContext3D();
	
	Raster ras = new Raster( new Point3f(0f,0f,0f), Raster.RASTER_COLOR,
                          0,0,
                          400,400,
                          new ImageComponent2D(
                                ImageComponent.FORMAT_RGB,
                                new BufferedImage(400,400,
                                BufferedImage.TYPE_INT_RGB)), 
                          null);

        ctx.readRaster(ras);
        realraster = ras.getImage().getImage().getRaster();
        
    }
    
    protected void initialSetup() {
        if(!set) {
            simpleU = new SimpleUniverse((Canvas3D)this);
            set = true;
        }
    }
    
    public void finalise() {
	simpleU.addBranchGraph(rootBranchGroup);

        Viewer v[] = simpleU.getViewingPlatform().getViewers();
        v[0].getView().setFrontClipDistance(0.0001);
	simpleU.getViewingPlatform().setNominalViewingTransform();
    }
    
    public void moveCamera(Vector3f vector){
        TransformGroup viewTrans = simpleU.getViewingPlatform().getViewPlatformTransform();
        Vector3f vec = new Vector3f();
        
        Transform3D temp = new Transform3D();
        viewTrans.getTransform(temp);
        temp.get(vec);
        
        vector.x += vec.x;
        vector.y += vec.y;
        vector.z += vec.z;
        
        temp.setTranslation(vector);     
        
        viewTrans.setTransform(temp);
    }
    public void startCamera(Vector3f vector){
        TransformGroup viewTrans = simpleU.getViewingPlatform().getViewPlatformTransform();     
        Transform3D temp = new Transform3D();
        viewTrans.getTransform(temp);
        temp.setTranslation(vector);     
        viewTrans.setTransform(temp);
    }
    public void moveRobotCamera(double degrees) {
        rootBranchGroup.rotateRobotCamera(degrees);
    }
    
    public void moveRobotBody(double degrees) {
        rootBranchGroup.rotateRobotBody(degrees);
    }
    
    public void moveRobot(Vector3f vector) {
        rootBranchGroup.moveRobot(vector);
    }

    public void startRobot(Vector3f vector) {
        rootBranchGroup.startRobot(vector);
    }
 
 
}
