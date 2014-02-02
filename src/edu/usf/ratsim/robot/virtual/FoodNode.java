package edu.usf.ratsim.robot.virtual;

import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import org.w3c.dom.Node;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class FoodNode extends ExpUniverseNode {

	private Vector3f position;

	public FoodNode(Node node) {
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

		position = new Vector3f(xp, yp, zp);
	}

	public FoodNode(float x, float y, float z) {
		Color3f color = new Color3f(0, 0, 1);

		Appearance app = new Appearance();
		app.setColoringAttributes(new ColoringAttributes(color, 1));
		Primitive vol = new Cylinder(0.038f, 0, app);
		addVolume(null, vol, x, y, z);

		position = new Vector3f(x, y, z);
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}
}