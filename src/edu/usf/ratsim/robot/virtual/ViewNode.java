package edu.usf.ratsim.robot.virtual;

import java.util.Map;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Vector3f;

import org.w3c.dom.Node;

public class ViewNode extends ExpUniverseNode {

	private View view;

	public ViewNode(Node node) {
		Map<String, Float> values = readValues(node);

		float x = values.get("x");
		float y = values.get("y");
		float z = values.get("z");

		Transform3D rPos = new Transform3D();
		rPos.setTranslation(new Vector3f(x, y, z));
		Transform3D rRot = new Transform3D();
		rRot.rotX(Math.PI / 180.0d * -90);
		rPos.mul(rRot);
		TransformGroup tg = new TransformGroup();
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setTransform(rPos);

		CameraView cv = new CameraView(false);
		view = cv.getView();
		tg.addChild(cv.getRootBG());

		// Add the transform group to the world branch group
		this.addChild(tg);
	}

	public View getView() {
		return view;
	}
}

/* Taken from https://www.java.net//node/647937 */
class CameraView {

	private View view;
	private BranchGroup rootBG;

	public CameraView(boolean close) {
		PhysicalBody physBody = new PhysicalBody();
		PhysicalEnvironment physEnv = new PhysicalEnvironment();
		view = new View();

		ViewPlatform viewPlatform = new ViewPlatform();

		view.setPhysicalBody(physBody);
		view.setPhysicalEnvironment(physEnv);
		view.attachViewPlatform(viewPlatform);
		// Modify front clip distance if seeing close objects
		if (close)
			view.setFrontClipDistance(0.0005);

		TransformGroup vpTG = new TransformGroup();
		vpTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		vpTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		vpTG.addChild(viewPlatform);

		rootBG = new BranchGroup();
		rootBG.setCapability(BranchGroup.ALLOW_DETACH);
		rootBG.addChild(vpTG);
	}

	public javax.media.j3d.Node getRootBG() {
		return rootBG;
	}

	public View getView() {
		return this.view;
	}
}
