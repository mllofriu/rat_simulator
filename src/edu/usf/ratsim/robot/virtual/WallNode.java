package edu.usf.ratsim.robot.virtual;

import java.util.Map;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import org.w3c.dom.Node;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineSegment;


public class WallNode extends ExpUniverseNode {

	final float RADIO = 0.005f;
	private float x1;
	private float y1;
	private float z1;
	private float x2;
	private float y2;
	private float z2;
	
	public  LineSegment segment;

	public WallNode(Node node) {
		Map<String, Float> values = readValues(node);

		Color3f color = new Color3f(values.get("cr"), values.get("cg"),
				values.get("cb"));
		x1 = values.get("x1");
		y1 = values.get("y1");
		z1 = values.get("z1");
		x2 = values.get("x2");
		y2 = values.get("y2");
		z2 = values.get("z2");
		float h = values.get("h");

		float wallLength = new Point3f(x1, y1, z1).distance(new Point3f(x2, y2,
				z2));
		for (int cylinder = 0; cylinder < wallLength / RADIO; cylinder++) {
			float lambda = cylinder / (wallLength / RADIO);
			addChild(new CylinderNode(RADIO, h, color, x1 + (x2 - x1) * lambda,
					y1 + (y2 - y1) * lambda, z1 + (z2 - z1) * lambda));
		}
		
		segment = new LineSegment(new Coordinate(x1, z1), new Coordinate(x2, z2));

	}

	public WallNode(float x1, float y1, float z1, float x2, float y2, float z2,
			float h) {
		Color3f color = new Color3f(1, 0, 0);

		float wallLength = new Point3f(x1, y1, z1).distance(new Point3f(x2, y2,
				z2));
		for (int cylinder = 0; cylinder < wallLength / RADIO; cylinder++) {
			float lambda = cylinder / (wallLength / RADIO);
			addChild(new CylinderNode(RADIO, h, color, x1 + (x2 - x1) * lambda,
					y1 + (y2 - y1) * lambda, z1 + (z2 - z1) * lambda));
		}
		
		segment = new LineSegment(new Coordinate(x1, z1), new Coordinate(x2, z2));

	}
}
