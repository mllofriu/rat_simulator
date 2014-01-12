package robot;
import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Material;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Color3f;
import javax.vecmath.Point4d;
import javax.vecmath.Vector3f;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import simulation.Simulation;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

public class WorldBranchGroup extends BranchGroup {
	private static final float MOVE_REMOVE = 10; // un valor que quede fuera del mapa
	public static final String STRING_FOOD = "food";

	private TransformGroup robotTransGroup;
	private Hashtable<String, TransformGroup> boxs = new Hashtable<String, TransformGroup>();
	private Hashtable<String, Transform3D> boxsTransdorm = new Hashtable<String, Transform3D>();
	private Hashtable<String, Vector3f> boxsPosition = new Hashtable<String, Vector3f>();

	private Set<String> boxHiden = new HashSet<String>();
	private View topView;
	// By Gonzalo private CollisionReporter reporter = new CollisionReporter();

	public WorldBranchGroup(String filename){
		super();

		this.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		this.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		
		Document doc = readDocument(filename);

		// Build the group
		NodeList list;
		org.w3c.dom.Node node;
		NamedNodeMap attributes;
		int i;
		String blockName=null;
		list = doc.getElementsByTagName("sphere");
		for(i=0; i<list.getLength(); i++) {
			float r, cr, cg, cb, xp, yp, zp;

			node = list.item(i);
			attributes = node.getAttributes();
			r = getFParam(attributes, "r");

			//color
			cr = getFParam(attributes, "cr");
			cg = getFParam(attributes, "cg");
			cb = getFParam(attributes, "cb");

			//position
			xp = getFParam(attributes, "xp");
			yp = getFParam(attributes, "yp");
			zp = getFParam(attributes, "zp");

			addSphere(r, new Color3f(cr, cg, cb), xp, yp, zp);
		}

		// busco tanques de morris
		list = doc.getElementsByTagName("pool");
		for(i=0; i<list.getLength(); i++) {
			float r, h, cr, cg, cb, xp, yp, zp;

			node = list.item(i);
			attributes = node.getAttributes();

			r = getFParam(attributes, "r");
			h = getFParam(attributes, "h");

			//color
			cr = getFParam(attributes, "cr");
			cg = getFParam(attributes, "cg");
			cb = getFParam(attributes, "cb");

			//position
			xp = getFParam(attributes, "xp");
			yp = getFParam(attributes, "yp");
			zp = getFParam(attributes, "zp");

			addCylinderWall(r, h, new Color3f(cr, cg, cb), xp, yp, zp);
		}

		//Cylinders
		list = doc.getElementsByTagName("cylinder");
		for(i=0; i<list.getLength(); i++) {
			float r, h, cr, cg, cb, xp, yp, zp;

			node = list.item(i);
			attributes = node.getAttributes();

			r = getFParam(attributes, "r");
			h = getFParam(attributes, "h");

			//color
			cr = getFParam(attributes, "cr");
			cg = getFParam(attributes, "cg");
			cb = getFParam(attributes, "cb");

			//position
			xp = getFParam(attributes, "xp");
			yp = getFParam(attributes, "yp");
			zp = getFParam(attributes, "zp");

			addCylinder(r, h, new Color3f(cr, cg, cb), xp, yp, zp);
		}

		//Boxes
		list = doc.getElementsByTagName("box");
		for(i=0; i<list.getLength(); i++) {
			// x,y,z: length, width, and height
			float x, y, z, cr, cg, cb, xp, yp, zp;

			node = list.item(i);
			attributes = node.getAttributes();

			x = getFParam(attributes, "x");
			y = getFParam(attributes, "y");
			z = getFParam(attributes, "z");

			//color
			cr = getFParam(attributes, "cr");
			cg = getFParam(attributes, "cg");
			cb = getFParam(attributes, "cb");

			//position
			xp = getFParam(attributes, "xp");
			yp = getFParam(attributes, "yp");
			zp = getFParam(attributes, "zp");

			if (attributes.getNamedItem(Simulation.STR_NAME)!=null)
				blockName=attributes.getNamedItem(Simulation.STR_NAME).getNodeValue();
			// TODO: ojo que estaba en true
			addBox(blockName,x, y, z, new Color3f(cr, cg, cb), xp, yp, zp);
		}


		//floor
		list = doc.getElementsByTagName("floor");
		node = list.item(0);
		attributes = node.getAttributes();

		float r, cr,cg,cb,xp,yp,zp,h;
		r = getFParam(attributes, "r");

		//color
		cr = getFParam(attributes, "cr");
		cg = getFParam(attributes, "cg");
		cb = getFParam(attributes, "cb");

		//position
		xp = getFParam(attributes, "xp");
		yp = getFParam(attributes, "yp");
		zp = getFParam(attributes, "zp");

		addCylinder(r, 0, new Color3f(cr, cg, cb), xp, yp, zp);


		//top view
		float x, y, z;
		list = doc.getElementsByTagName("topview");
		node = list.item(0);
		attributes = node.getAttributes();
		x = getFParam(attributes, "x");
		y = getFParam(attributes, "y");
		z = getFParam(attributes, "z");
		addTopView(new Vector3f(x,y,z));
		//
		//		//thirdview
		//		list = doc.getElementsByTagName("thirdview");
		//		node = list.item(0);
		//		x = getFParam(attributes, "x");
		//		y = getFParam(attributes, "y");
		//		z = getFParam(attributes, "z");
		//		wbg.setThirdView(new Vector3f(x,y,z));

		//robot view
		list = doc.getElementsByTagName("robotview");
		node = list.item(0);
		attributes = node.getAttributes();
		x = getFParam(attributes, "x");
		y = getFParam(attributes, "y");
		z = getFParam(attributes, "z");
		addRobot(new Vector3f(x,y,z));

		//food
		list = doc.getElementsByTagName(WorldBranchGroup.STRING_FOOD);
		node = list.item(0);
		attributes = node.getAttributes();
		r = getFParam(attributes, "r");
		h = getFParam(attributes, "h");

		//color
		cr = getFParam(attributes, "cr");
		cg = getFParam(attributes, "cg");
		cb = getFParam(attributes, "cb");

		//position
		xp = getFParam(attributes, "xp");
		yp = getFParam(attributes, "yp");
		zp = getFParam(attributes, "zp");


		addFood(r, h, new Color3f(cr, cg, cb), x, y, z);


		addDirectionalLight(new Vector3f(0f, 0f, -5), new Color3f(1f, 1f, 1f));
		addDirectionalLight(new Vector3f(0f, 0f, 5), new Color3f(.5f, .5f, .5f));
		addDirectionalLight(new Vector3f(0f, -5f, -5), new Color3f(.5f, .5f, .5f));
		addDirectionalLight(new Vector3f(0f, 5f, -5), new Color3f(.5f, .5f, .5f));
		addDirectionalLight(new Vector3f(0f, -5f, 5), new Color3f(.5f, .5f, .5f));
		addDirectionalLight(new Vector3f(0f, 5f, 5), new Color3f(.5f, .5f, .5f));
		addDirectionalLight(new Vector3f(0f, -5, 0), new Color3f(1f, 1f, 1f));

	}

	private void addTopView(Vector3f initialPos) {
		Transform3D rPos = new Transform3D();
		rPos.setTranslation(initialPos);
		Transform3D rRot = new Transform3D();
		rRot.rotX(Math.PI / 180.0d * -90);
		rPos.mul(rRot);
		TransformGroup tg = new TransformGroup();
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setTransform(rPos);
		
		CameraView cv = new CameraView();
		this.topView = cv.getView();
		tg.addChild(cv.getRootBG());
		
		// Add the transform group to the world branch group
		this.addChild(tg);
	}

	public View getTopView() {
		return topView;
	}

	public void setTopView(View topView) {
		this.topView = topView;
	}

	private Document readDocument(String filename) {
		// Read the XML
		Document doc = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			doc = docBuilder.parse(new File(filename));

			doc.getDocumentElement ().normalize ();

		} catch (SAXParseException err) {
			System.out.println ("** Parsing error" 
					+ ", line " + err.getLineNumber ()
					+ ", uri " + err.getSystemId ());
			System.out.println("   " + err.getMessage ());
		} catch (SAXException e) {
			Exception	x = e.getException ();
			((x == null) ? e : x).printStackTrace ();
		} catch (Throwable t) {
			t.printStackTrace ();
		}

		return doc;

	}

	private float getFParam(NamedNodeMap attributes, String name){
		Node nodeaux = attributes.getNamedItem(name);
		return Float.parseFloat(nodeaux.getNodeValue());
	}

	public void addSphere(float r, Color3f color, float xp, float yp, float zp) {
		Appearance app = new Appearance();
		app.setColoringAttributes(new ColoringAttributes (color,1));
		Primitive vol = new Sphere(r, app);
		addVolume(null, vol, xp, yp, zp);
	}

	public void addCylinder(float r, float h, Color3f color, float xp,
			float yp, float zp) {
		Appearance app = new Appearance();
		app.setColoringAttributes(new ColoringAttributes (color,1));
		Primitive vol = new Cylinder(r, h, app);
		addVolume(null, vol, xp, yp, zp);
	}


	public void addBox(String name, float x, float y, float z, Color3f color, float xp,
			float yp, float zp) {
		Appearance app = new Appearance();
		TransparencyAttributes ta= new TransparencyAttributes(TransparencyAttributes.FASTEST,0);
		app.setColoringAttributes(new ColoringAttributes (color,1));
		app.setTransparencyAttributes(ta);
		// x,y,z: length, width, and height.
		Primitive vol = new Box(x, y, z, app);
		addVolume(name, vol, xp, yp, zp);
	}

	public void addFood(float r, float h, Color3f color, float xp, float yp,
			float zp) {
		Appearance app = new Appearance();
		app.setColoringAttributes(new ColoringAttributes (color,1));

		Primitive vol = new Cylinder(r, h, app);

		addVolume(STRING_FOOD, vol, xp, yp, zp);
	}

	// intento por hacer arenas con bordes circulares (morris)
	public void addCylinderWall(float r, float h, Color3f color, float xp,
			float yp, float zp) {
		final float RADIO = 0.005f;
		final int CANTIDAD_CILINDROS = 2000;
		int iterCantCilindros;
		double currentAngle = 0;

		for (iterCantCilindros=0;iterCantCilindros<CANTIDAD_CILINDROS;iterCantCilindros++) {
			addCylinder(RADIO, h, color,xp+(float)(r*Math.sin(currentAngle)),yp,zp+(float)(r*Math.cos(currentAngle)));
			currentAngle = currentAngle + 360.0/CANTIDAD_CILINDROS;
		}

	}


	public void addVolume(String name, Primitive vol, float x, float y, float z) {
		Transform3D translate = new Transform3D();
		Vector3f position = new Vector3f(x, y, z);
		translate.setTranslation(position);
		TransformGroup tg = new TransformGroup(translate);
		tg.addChild(vol);
		this.addChild(tg);

		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		if (name!=null) {
			boxs.put(name, tg);
			boxsTransdorm.put(name,translate);
			boxsPosition.put(name, position);
		}
	}

	public void remove(String nameVolume) {
		Transform3D translate = new Transform3D();
		Vector3f vector=new Vector3f(MOVE_REMOVE,MOVE_REMOVE,MOVE_REMOVE);
		translate.setTranslation(vector);
		TransformGroup tg =	boxs.get(nameVolume);
		tg.setTransform(translate);
		boxHiden.add(nameVolume);
	}

	public void add(String nameVolume) {
		TransformGroup tg =	boxs.get(nameVolume);
		tg.setTransform(boxsTransdorm.get(nameVolume));
		boxHiden.remove(nameVolume);
	}

	public void move(String nameVolume, Point4d point) {
		Transform3D translate = new Transform3D();
		Vector3f vector=new Vector3f((float)point.x,(float)point.y,(float)point.z);
		translate.setTranslation(vector);

		TransformGroup tg =	boxs.get(nameVolume);
		tg.setTransform(translate);
		boxsTransdorm.remove(nameVolume); // borro de la lista de posiciones de objetos la posici??n anterior
		boxsTransdorm.put(nameVolume,translate); // coloco en la lista la nueva posicion
		boxsPosition.remove(nameVolume); // borro de la lista de posiciones de objetos la posici??n anterior
		boxsPosition.put(nameVolume,vector); // coloco en la lista la nueva posicion

	}

	int id = 0;
	////By Gonzalo
	//	public void addCollisionBehavior(Primitive vol, double x, double y, double z) {
	//		Behavior collisionbehavior = new CollisionBehavior(reporter, vol, id);
	//		collisionbehavior.setSchedulingBounds(new BoundingSphere(new Point3d(x,
	//				y, z), 2d));
	//		this.addChild(collisionbehavior);
	//		id++;
	//	}
	private CameraView rView;
	private CameraView leftRobotView;
	private CameraView rightRobotView;
	private CameraView leftMostRobotView;
	private CameraView rightMostRobotView;

	protected Material createMaterial(Color3f color) {
		Material mat = new Material();
		mat.setDiffuseColor(color);
		mat.setSpecularColor(color);
		mat.setShininess(0f);
		//		// by gonzalo: apago reflejos ambiente y especular en los cuerpos pues molesta bastante para el conteo de pixeles.
		mat.setEmissiveColor(color);
		return mat;
	}

	/**
	 * Adds a robot and generates a view for it, associated to the canvas
	 * @param initialPos
	 * @param canvas
	 */
	public void addRobot(Vector3f initialPos) {
		// Initialize the transform group
		// Keep it public to move the robot in the future
		Transform3D rPos = new Transform3D();
		rPos.setTranslation(initialPos);
//		Transform3D rRot = new Transform3D();
//		rRot.rotY(Math.PI / 180.0d * 90);
//		rPos.mul(rRot);
		this.robotTransGroup = new TransformGroup();
		this.robotTransGroup
		.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.robotTransGroup
		.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.robotTransGroup.setTransform(rPos);
		// Add the transform group to the world branch group
		this.addChild(this.robotTransGroup);

		// Create the robot itself		
		BranchGroup robotBG = new BranchGroup();
		robotTransGroup.addChild(robotBG);

		// Create the cylinder for the robot
		Appearance app = new Appearance();
		Material mat = createMaterial(new Color3f(0.5f, 0f, 1f));
		app.setMaterial(mat);		
		TransformGroup cylTG = new TransformGroup();
		Transform3D cylT = new Transform3D();
		cylT.rotX(Math.toRadians(90));
		cylTG.setTransform(cylT);
		Cylinder bodyCylinder = new Cylinder(0.025f, 0.1f, app);
		cylTG.addChild(bodyCylinder);
		robotBG.addChild(cylTG);
		
		// Camera cone
		app = new Appearance();
		mat = createMaterial(new Color3f(0.5f, 0.5f, 0f));
		app.setMaterial(mat);
		Cone viewCone = new Cone(0.025f, 0.11f, app);
		cylTG.addChild(viewCone);

		// Transforms
		Vector3f robotCameraOffset = new Vector3f(0, 0.05f, 0);
		TransformGroup camTG = new TransformGroup();
		Transform3D camT = new Transform3D();
		camT.setTranslation(robotCameraOffset);
		camTG.setTransform(camT);
		robotBG.addChild(camTG);
		
		// Add center view
		rView = new CameraView();
		camTG.addChild(rView.getRootBG());
		
		// Add left views
		TransformGroup leftTG = new TransformGroup();
		Transform3D leftT = new Transform3D();
		leftT.rotY(Math.toRadians(45));
		leftTG.setTransform(leftT);
		leftRobotView = new CameraView();
		leftTG.addChild(leftRobotView.getRootBG());
		camTG.addChild(leftTG);
		
		// Add right views
		TransformGroup rightTG = new TransformGroup();
		Transform3D rightT = new Transform3D();
		rightT.rotY(Math.toRadians(-45));
		rightTG.setTransform(rightT);
		rightRobotView = new CameraView();
		rightTG.addChild(rightRobotView.getRootBG());
		camTG.addChild(rightTG);
		
		// Add left views
		TransformGroup leftMostTG = new TransformGroup();
		Transform3D leftMostT = new Transform3D();
		leftMostT.rotY(Math.toRadians(90));
		leftMostTG.setTransform(leftMostT);
		leftMostRobotView = new CameraView();
		leftMostTG.addChild(leftMostRobotView.getRootBG());
		camTG.addChild(leftMostTG);
		
		// Add right views
		TransformGroup rightMostTG = new TransformGroup();
		Transform3D rightMostT = new Transform3D();
		rightMostT.rotY(Math.toRadians(-90));
		rightMostTG.setTransform(rightMostT);
		rightMostRobotView = new CameraView();
		rightMostTG.addChild(rightMostRobotView.getRootBG());
		camTG.addChild(rightMostTG); 
	}



	public View getLeftMostRobotView() {
		return leftMostRobotView.view;
	}

	public View getRightMostRobotView() {
		return rightMostRobotView.view;
	}

	public View getLeftRobotView() {
		return leftRobotView.getView();
	}

	public View getRightRobotView() {
		return rightRobotView.getView();
	}

	public View getRobotView() {
		return rView.getView();
	}

	public void moveRobot(Vector3f vector) {
		// Create a new transforme with the translation
		Transform3D trans = new Transform3D();
		trans.setTranslation(vector);
		// Get the old pos transform
		Transform3D rPos = new Transform3D();
		robotTransGroup.getTransform(rPos);
		// Apply translation to old transform
		rPos.mul(trans);

		robotTransGroup.setTransform(rPos);
	}

	public void startRobot(Vector3f vector) {
		Transform3D translate = new Transform3D();
		translate.setTranslation(new Vector3f(vector.x, vector.y + 0.05f, vector.z));
		this.robotTransGroup.setTransform(translate);
	}

	public void rotateRobot(double degrees) {
		// Create a new transforme with the translation
		Transform3D trans = new Transform3D();
		trans.rotY(degrees);
		// Get the old pos transform
		Transform3D rPos = new Transform3D();
		robotTransGroup.getTransform(rPos);
		// Apply translation to old transform
		rPos.mul(trans);

		robotTransGroup.setTransform(rPos);
	}

	public Vector3f getFood() {
		Vector3f result;
		if(boxHiden.contains(STRING_FOOD)) // si la comida fue eliminada del entorno devuelvo las coordendas de eliminacion
			result = new Vector3f(MOVE_REMOVE,MOVE_REMOVE,MOVE_REMOVE);
		else // si no fue eliminada devuelvo su pocici??n actual
			result = boxsPosition.get(STRING_FOOD);

		return result;
	}

	public void addDirectionalLight(Vector3f direction, Color3f color) {
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(1000d);
		BoundingSphere bounds1 = new BoundingSphere();
		bounds1.setRadius(1000d);
		BoundingSphere bounds2 = new BoundingSphere();
		bounds2.setRadius(1000d);

		DirectionalLight lightD = new DirectionalLight(color, direction);
		lightD.setInfluencingBounds(bounds);
		DirectionalLight lightD1 = new DirectionalLight(color, direction);
		lightD1.setInfluencingBounds(bounds1);
		DirectionalLight lightD2 = new DirectionalLight(color, direction);
		lightD2.setInfluencingBounds(bounds2);

		addChild(lightD);
	}

	public Vector3f getRobotPosition(){
		Transform3D rPos = new Transform3D();
		robotTransGroup.getTransform(rPos);
		Vector3f ret = new Vector3f();
		rPos.get(ret);
		return ret;
	}

}

class CameraView {

	protected static final PhysicalBody physBody = new PhysicalBody();
	protected static final PhysicalEnvironment physEnv =
			new PhysicalEnvironment();

	protected BranchGroup rootBG = null;
	protected TransformGroup vpTG = null;
	protected ViewPlatform viewPlatform = null;
	protected View view = null;
	protected Canvas3D canvas = null;

	public CameraView() {

		GraphicsConfigTemplate3D gconfigTempl =
				new GraphicsConfigTemplate3D();

//		canvas = new Canvas3D( gconfig );

		view = new View();

		viewPlatform = new ViewPlatform();

		view.setPhysicalBody( physBody );
		view.setPhysicalEnvironment( physEnv );
		view.attachViewPlatform( viewPlatform );
		view.setFrontClipDistance(0.0001);
//		view.addCanvas3D( canvas );

		vpTG = new TransformGroup();
		vpTG.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
		vpTG.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		vpTG.addChild( viewPlatform );

		rootBG = new BranchGroup();
		rootBG.setCapability( BranchGroup.ALLOW_DETACH );
		rootBG.addChild(vpTG);

	}

	public TransformGroup getViewPlatformTransformGroup() {
		return this.vpTG;
	}

	public BranchGroup getRootBG() {
		return this.rootBG;
	}

	public View getView() {
		return this.view;
	}

	public Canvas3D getCanvas3D() {
		return this.canvas;
	}

}
