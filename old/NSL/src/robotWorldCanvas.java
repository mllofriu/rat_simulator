import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.TransformGroup;

public class robotWorldCanvas extends WorldCanvas {
    public static final int ROJO=255<<16;
    public static final int AZUL=255;
    public static final int AMARILLO=255<<16|255<<8;
    public static final int ROSA=255<<16|255;
    public static final int BLANCO=255<<16|255<<8|255;
    public static final int CYAN=255<<8|255;
    public static final int DESCARTE=0;
    
    
    public int Matrix[][] = new int[80][80];
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
    
    public void postSwap() {
        super.postSwap();
    	int[][] oldMatrix;
    	    		
    	oldMatrix=Matrix;
        Matrix = new int[80][80];
        int y,x;       
       
		y = 0;
        for(int h=0; h<240; h+=3) {
            x = 0;
            for(int i=0; i<240; i+=3) {
                int[] p = queryRaster(i, h);
                if (pixel2rgb(p)==ROJO)
                    Matrix[x][y] = ROJO;
                else if (pixel2rgb(p)==AZUL) 
                    Matrix[x][y] = AZUL;
                else if (pixel2rgb(p)==AMARILLO)
                     Matrix[x][y] = AMARILLO;
                else if (pixel2rgb(p)==ROSA)
                     Matrix[x][y] = ROSA;
                else if (pixel2rgb(p)==BLANCO)
                     Matrix[x][y] = BLANCO;
                else if (pixel2rgb(p)==CYAN)
                     Matrix[x][y] = CYAN;
                else
                	Matrix[x][y] = DESCARTE;
                x++;
            }
            y++;
        }
    }
    
    public int[][] getColorMatrix() {
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
