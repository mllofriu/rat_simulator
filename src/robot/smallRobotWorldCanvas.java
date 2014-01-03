package robot;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.TransformGroup;

public class smallRobotWorldCanvas extends Canvas {
	
	
	private final int IMAGE_SIZE=80;
	BufferedImage image = new BufferedImage(5*IMAGE_SIZE, IMAGE_SIZE,BufferedImage.TYPE_3BYTE_BGR);
   
    
    public smallRobotWorldCanvas() {
    }

    public void update(Graphics g) {
		image = RobotFactory.getRobot().getPanoramica();
		g.drawImage(image, 0, 0, null);
		
    }
}
