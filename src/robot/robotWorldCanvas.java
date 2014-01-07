package robot;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import support.Utiles;

public class robotWorldCanvas extends WorldCanvas {
    public static final int DESCARTE= Utiles.color2RGB(Color.ORANGE);
	private static final int WALL_COLOR = Utiles.color2RGB(Color.RED);
    
    
    public BufferedImage Matrix;
	private int MAX_ITER_STABLE=10;
    
    public robotWorldCanvas(GraphicsConfiguration gc, worldBranchGroup branchGroup) {
	super(gc, branchGroup);
    }
    
    public robotWorldCanvas(GraphicsConfiguration gc) {
	super(gc);
    }
    
    private static int pixel2rgb(int[] pixel) {
    	return (pixel[0]<<16)|(pixel[1]<<8)|pixel[2];
    }
    
    private static Color pixel2color(int[] pixel) {
    	return new Color(pixel[0],pixel[1],pixel[2]);
    }

    public void postSwap() {
        super.postSwap();
        int y,x, iterColor, color;       
        Matrix = new BufferedImage(80, 80, BufferedImage.TYPE_3BYTE_BGR);
		y = 0;
		Color [] landmarksColors = RobotFactory.getRobot().getColorsLandmarks();
		int[] p;
		
        for(int h=0; h<240; h+=3) {
            x = 0;
            for(int i=0; i<240; i+=3) {
                p= queryRaster(i, h);
                if (pixel2rgb(p)==WALL_COLOR)
                	color = WALL_COLOR;
                else {
	                for (iterColor=0; iterColor<landmarksColors.length;iterColor++) {
	                	if (landmarksColors[iterColor].equals(pixel2color(p)))
	                		break;
	                }

	                if (iterColor==landmarksColors.length)
	                	color = DESCARTE;
	                else
	                	color = pixel2rgb(p);
                }
                Matrix.setRGB(x, y,color);
                x++;
            }
            y++;
        }
    }
    
    public BufferedImage getColorMatrix() {
        return Matrix;
    }
    
    private int[] queryRaster(int x, int y){
        int[] q={0,0,0};
        
        return realraster.getPixel(x,y,q);
    }
    
    public void finalise() {
        super.finalise();
        
        TransformGroup viewTrans = simpleU.getViewingPlatform().getViewPlatformTransform();
        Transform3D myTransform3D = new Transform3D();
        myTransform3D.setTranslation(rootBranchGroup.getRobotCameraView());
        viewTrans.setTransform(myTransform3D);
    }
    
    public void moveCamera(double degrees) {
        TransformGroup viewTrans = simpleU.getViewingPlatform().getViewPlatformTransform();
        Transform3D myTransform3D2 = new Transform3D();
        myTransform3D2.rotY(Math.toRadians(degrees));
        
        Transform3D temp = new Transform3D();
        viewTrans.getTransform(temp);
        
        temp.mul(myTransform3D2);
        viewTrans.setTransform(temp);
        
        rootBranchGroup.rotateRobotCamera(degrees);        
    }
    
    
    public void moveCamera(Vector3f vector, float degrees) {
        moveCamera(degrees);
        moveCamera(vector);
    } 
    
////By Gonzalo
//    public boolean isCollided() {
//        return rootBranchGroup.isCollided();
//    }    
}
