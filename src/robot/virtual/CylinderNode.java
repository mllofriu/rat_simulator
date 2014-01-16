package robot.virtual;

import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.vecmath.Color3f;

import org.w3c.dom.Node;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class CylinderNode extends ExpUniverseNode {

	public CylinderNode(Node node) {
		Map<String, Float> values = readValues(node);

		Color3f color = new Color3f(values.get("cr"), values.get("cg"),
				values.get("cb"));
		float xp = values.get("xp");
		float yp = values.get("yp");
		float zp = values.get("zp");
		float r = values.get("r");
		float h = values.get("h");

		Appearance app = new Appearance();
		app.setColoringAttributes(new ColoringAttributes(color, 1));
		Primitive vol = new Cylinder(r, h, app);
		addVolume(null, vol, xp, yp, zp);
	}

	/**
	 * @param r
	 *            Cylinder radius
	 * @param h
	 *            Cylinder height
	 * @param color
	 *            Cylinder color
	 * @param xp
	 *            Cylinder center x coordinate
	 * @param yp
	 *            Cylinder center y coordinate
	 * @param zp
	 *            Cylinder center z coordinate
	 */
	public CylinderNode(float r, float h, Color3f color, float xp, float yp,
			float zp) {
		Appearance app = new Appearance();
		app.setColoringAttributes(new ColoringAttributes(color, 1));
		Primitive vol = new Cylinder(r, h, app);
		addVolume(null, vol, xp, yp, zp);
	}
}
