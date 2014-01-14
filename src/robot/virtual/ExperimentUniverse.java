package robot.virtual;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Locale;
import javax.media.j3d.Transform3D;
import javax.media.j3d.View;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import support.XMLDocReader;

/**
 * This universe class creates a universe from an XML file and exposes 
 * functionalities needed for performing experiments.
 * @author ludo
 *
 */
public class ExperimentUniverse extends VirtualUniverse{
	
	private View topView;
	private RobotNode robot;
	private FoodNode food;

	public ExperimentUniverse(String filename){
		super();
		
		Locale l = new Locale(this);
		
		BranchGroup bg = new BranchGroup();
		bg.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		bg.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		l.addBranchGraph(bg);

		Document doc = XMLDocReader.readDocument(filename);

		// Build the group
		NodeList list;
		org.w3c.dom.Node params;
		
		// Spheres
		list = doc.getElementsByTagName("sphere");
		for(int i = 0; i<list.getLength(); i++) {
			bg.addChild(new SphereNode(list.item(i)));
		}

		// Morris tanks
		list = doc.getElementsByTagName("pool");
		for(int i = 0; i<list.getLength(); i++) {
			bg.addChild(new PoolNode(list.item(i)));
		}

		// Cylinders
		list = doc.getElementsByTagName("cylinder");
		for(int i = 0; i<list.getLength(); i++) {
			bg.addChild(new CylinderNode(list.item(i)));
		}

		// Boxes
		list = doc.getElementsByTagName("box");
		for(int i = 0; i<list.getLength(); i++) {
			bg.addChild(new BoxNode(list.item(i)));
		}


		// Floor
		list = doc.getElementsByTagName("floor");
		params = list.item(0);
		bg.addChild(new CylinderNode(params));

		// Top view
		list = doc.getElementsByTagName("topview");
		params = list.item(0);
		ViewNode vn = new ViewNode(params);
		topView = vn.getView();
		bg.addChild(vn);
		
		// Robot
		list = doc.getElementsByTagName("robotview");
		params = list.item(0);
		robot = new RobotNode(params);
		bg.addChild(robot);

		//food
		list = doc.getElementsByTagName("food");
		params = list.item(0);
		food = new FoodNode(params);
		bg.addChild(food);

		bg.addChild(new DirectionalLightNode(new Vector3f(0f, 0f, -5), new Color3f(1f, 1f, 1f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, 0f, 5), new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, -5f, -5), new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, 5f, -5), new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, -5f, 5), new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, 5f, 5), new Color3f(.5f, .5f, .5f)));
		bg.addChild(new DirectionalLightNode(new Vector3f(0f, -5, 0), new Color3f(1f, 1f, 1f)));

//		bg.compile();
	}
	
	

	public View getTopView() {
		return topView;
	}

	public View[] getRobotViews() {
		return robot.getRobotViews();
	}
	
	public Canvas3D[] getRobotOffscreenCanvas() {
		return robot.getOffScreenCanvas();
	}

	public ImageComponent2D[] getRobotOffscreenImages() {
		return robot.getOffScreenImages();
	}
	/**
	 * Return the virtual robot's position
	 * @return
	 */
	public Vector3f getRobotPosition(){
		Transform3D t = new Transform3D();
		robot.getTransformGroup().getTransform(t);
		Vector3f pos = new Vector3f();
		t.get(pos);
		return pos;
	}
	
	/** 
	 * Sets the virtual robot position
	 * @param vector Robots position
	 */
	public void setRobotPosition(Vector3f vector) {
		Transform3D translate = new Transform3D();
		translate.setTranslation(new Vector3f(vector.x, vector.y + 0.05f, vector.z));
		robot.getTransformGroup().setTransform(translate);
	}
	
	/**
	 * Rotate the virtual world robot.
	 * @param degrees Amount to rotate in degrees.
	 */
	public void rotateRobot(double degrees) {
		// Create a new transforme with the translation
		Transform3D trans = new Transform3D();
		trans.rotY(degrees);
		// Get the old pos transform
		Transform3D rPos = new Transform3D();
		robot.getTransformGroup().getTransform(rPos);
		// Apply translation to old transform
		rPos.mul(trans);
		// Set the new transform
		robot.getTransformGroup().setTransform(rPos);
	}
	
	/** 
	 * Move the virtual robot a certain amount of distance
	 * @param vector Direction and magnitude of the movement
	 */
	public void moveRobot(Vector3f vector) {
		// Create a new transforme with the translation
		Transform3D trans = new Transform3D();
		trans.setTranslation(vector);
		// Get the old pos transform
		Transform3D rPos = new Transform3D();
		robot.getTransformGroup().getTransform(rPos);
		// Apply translation to old transform
		rPos.mul(trans);
		// Set the new transform
		robot.getTransformGroup().setTransform(rPos);
	}

	public Vector3f getFood() {
		return food.getLocation();
	}
}
