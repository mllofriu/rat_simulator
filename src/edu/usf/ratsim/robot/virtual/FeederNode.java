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
	 * Determines if the feeder is calling the animals atention throughout
	 * flashing
	 */
	private boolean flashing;
	private Color3f flashingColor;
	private Color3f normalColor;
	private Color3f wantedColor;
	private Appearance app;
	private boolean wanted;
	
	private boolean terminated;
	private Thread flashThread;

	class FlashThread implements Runnable {

		

		public void run() {
			terminated = false;
			while (!terminated) {
				if (flashing) {
					try {
						app.setColoringAttributes(new ColoringAttributes(
								flashingColor, 1));
						Thread.sleep(50);
						if (!wanted)
							app.setColoringAttributes(new ColoringAttributes(
									normalColor, 1));
						else {
							app.setColoringAttributes(new ColoringAttributes(
									wantedColor, 1));
						}
						if (flashing) {
							Thread.sleep(50);
//							System.out.println("flashing");
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
		wanted = false;

		Map<String, Float> values = readValues(node);

		normalColor = new Color3f(values.get("cr"), values.get("cg"),
				values.get("cb"));
		flashingColor = new Color3f(255, 255, 255);
		wantedColor = new Color3f(255, 50, 0);
		float xp = values.get("xp");
		float yp = values.get("yp");
		float zp = values.get("zp");
		float r = values.get("r");
		float h = values.get("h");

		app = new Appearance();
		app.setColoringAttributes(new ColoringAttributes(normalColor, 1));
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		Primitive vol = new Cylinder(r, h, app);
		addVolume(null, vol, xp, yp, zp);

		position = new Vector3f(xp, yp, zp);

		flashThread = new Thread(new FlashThread());
		flashThread.start();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		// flashing = active;
	}

	public void setWanted(boolean wanted) {
		this.wanted = wanted;
		// When flashing, just let the node do the work
		if (!flashing)
			if (!wanted)
				app.setColoringAttributes(new ColoringAttributes(normalColor, 1));
			else {
				app.setColoringAttributes(new ColoringAttributes(wantedColor, 1));
			}
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

	public boolean isWanted() {
		return wanted;
	}
	
	public void terminate(){
		terminated = true;
		try {
			flashThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		flashThread = null;
	}
}
