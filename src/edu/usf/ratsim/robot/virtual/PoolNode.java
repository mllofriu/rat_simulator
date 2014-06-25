package edu.usf.ratsim.robot.virtual;

import java.util.Map;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import org.w3c.dom.Node;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineSegment;

public class PoolNode extends ExpUniverseNode {

	final float RADIO = 0.005f;
	final int CANTIDAD_CILINDROS = 2000;
	private Float xp;
	private Float zp;
	private Float r;
	private Float yp;

	public PoolNode(Node node) {
		Map<String, Float> values = readValues(node);

		Color3f color = new Color3f(values.get("cr"), values.get("cg"),
				values.get("cb"));
		xp = values.get("xp");
		yp = values.get("yp");
		zp = values.get("yp");
		r = values.get("r");
		float h = values.get("h");

		int iterCantCilindros;
		double currentAngle = 0;

		for (iterCantCilindros = 0; iterCantCilindros < CANTIDAD_CILINDROS; iterCantCilindros++) {
			addChild(new CylinderNode(RADIO, h, color, xp
					+ (float) (r * Math.sin(currentAngle)), yp, zp
					+ (float) (r * Math.cos(currentAngle))));
			currentAngle = currentAngle + 360.0 / CANTIDAD_CILINDROS;
		}

	}

	public boolean isInside(Point3f point) {
		return point.distance(new Point3f(xp, yp, zp)) < r;
	}

	public float distanceToWall(LineSegment wall) {
		float minDistance = Float.MAX_VALUE;
		double currentAngle = 0;
		for (int iterCantCilindros = 0; iterCantCilindros < CANTIDAD_CILINDROS; iterCantCilindros++) {
			Coordinate p = new Coordinate(xp
					+ (float) (r * Math.sin(currentAngle)), zp
					+ (float) (r * Math.cos(currentAngle)));
			currentAngle = currentAngle + 360.0 / CANTIDAD_CILINDROS;
			float distance = (float) wall.distance(p);
			if (distance < minDistance)
				minDistance = distance;
		}
		
//		System.out.println(minDistance);
		return minDistance;
	}
}
