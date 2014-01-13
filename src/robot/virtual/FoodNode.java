package robot.virtual;

import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.vecmath.Color3f;

import org.w3c.dom.Node;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

public class FoodNode extends ExpUniverseNode {

	public FoodNode(Node node){
		Map<String, Float> values = readValues(node);
		
		Color3f color = new Color3f(values.get("cr"),values.get("cg"),values.get("cb"));
		float xp = values.get("xp");
		float yp = values.get("yp");
		float zp = values.get("yp");
		float r = values.get("r");
		float h = values.get("h");
		
		Appearance app = new Appearance();
		app.setColoringAttributes(new ColoringAttributes (color,1));
		Primitive vol = new Cylinder(r, h, app);
		addVolume(null, vol, xp, yp, zp);
	}
}
