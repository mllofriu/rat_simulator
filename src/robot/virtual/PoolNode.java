package robot.virtual;

import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.vecmath.Color3f;

import org.w3c.dom.Node;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

public class PoolNode extends ExpUniverseNode {

	final float RADIO = 0.005f;
	final int CANTIDAD_CILINDROS = 2000;
	
	public PoolNode(Node node){
		Map<String, Float> values = readValues(node);
		
		Color3f color = new Color3f(values.get("cr"),values.get("cg"),values.get("cb"));
		float xp = values.get("xp");
		float yp = values.get("yp");
		float zp = values.get("yp");
		float r = values.get("r");
		float h = values.get("h");
		
		
		int iterCantCilindros;
		double currentAngle = 0;

		for (iterCantCilindros=0;iterCantCilindros<CANTIDAD_CILINDROS;iterCantCilindros++) {
			addChild(new CylinderNode(RADIO, h, color,
					xp+(float)(r*Math.sin(currentAngle)),
					yp,zp+(float)(r*Math.cos(currentAngle))));
			currentAngle = currentAngle + 360.0/CANTIDAD_CILINDROS;
		}

	}
}
