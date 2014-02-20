package edu.usf.ratsim.robot.virtual;

import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import org.w3c.dom.Node;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class FeederNode extends ExpUniverseNode {

	private Vector3f position;
	/**
	 * Determines wheather the feeder can provide food
	 */
	private boolean active;
	/**
	 * Determines if the feeder is calling the animals atention throughout flashing
	 */
	private boolean flashing;
	private Color3f flashingColor;
	private Color3f color;
	private Appearance app;

	class FlashThread implements Runnable {

		public void run() {
			while (true) {
				if (flashing) {
					try {
						app.setColoringAttributes(new ColoringAttributes(
								flashingColor, 1));
						Thread.sleep(50);
						app.setColoringAttributes(new ColoringAttributes(
								color, 1));
						if (flashing) {
							Thread.sleep(50);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public FeederNode(Node node) {
		active = false;
		flashing = false;

		Map<String, Float> values = readValues(node);

		color = new Color3f(values.get("cr"), values.get("cg"),
				values.get("cb"));
		flashingColor = new Color3f(255 - values.get("cr"),
				255 - values.get("cg"), 255 - values.get("cb"));
		float xp = values.get("xp");
		float yp = values.get("yp");
		float zp = values.get("zp");
		float r = values.get("r");
		float h = values.get("h");

		app = new Appearance();
		app.setColoringAttributes(new ColoringAttributes(color, 1));
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		Primitive vol = new Cylinder(r, h, app);
		addVolume(null, vol, xp, yp, zp);

		position = new Vector3f(xp, yp, zp);

		new Thread(new FlashThread()).start();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
//		flashing = active;
	}

	public boolean isFlashing() {
		return flashing;
	}

	public void setFlashing(boolean flashing) {
		this.flashing = flashing;
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}
}
