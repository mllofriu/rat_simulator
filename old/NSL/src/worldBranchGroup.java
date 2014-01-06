import java.awt.Font;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.*;

public class worldBranchGroup extends BranchGroup {
	private static final float MOVE_REMOVE = 10; // un valor que quede fuera del mapa
	public static final String STRING_FOOD = "food";
	private Vector3f food;
	private Vector3f topView;
	private Vector3f robotView;
	private Vector3f robotCameraView;
	private Vector3f thirdView;
	private TransformGroup robotBodyTranslationGroup;
	private TransformGroup robotBodyRotationGroup;
	private TransformGroup robotCameraTranslationGroup;
	private TransformGroup robotCameraRotationGroup;
	private Hashtable<String, TransformGroup> boxs = new Hashtable<String, TransformGroup>();
	private Hashtable<String, Transform3D> boxsTransdorm = new Hashtable<String, Transform3D>();
	private Hashtable<String, Vector3f> boxsPosition = new Hashtable<String, Vector3f>();

	private Set<String> boxHiden = new HashSet<String>();
	// By Gonzalo private CollisionReporter reporter = new CollisionReporter();
	public worldBranchGroup() {
		super();
		this.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		this.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
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

		food = new Vector3f(xp, yp, zp);
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
		boxsTransdorm.remove(nameVolume); // borro de la lista de posiciones de objetos la posición anterior
		boxsTransdorm.put(nameVolume,translate); // coloco en la lista la nueva posicion
		boxsPosition.remove(nameVolume); // borro de la lista de posiciones de objetos la posición anterior
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

	protected Material createMaterial(Color3f color) {
		Material mat = new Material();
		mat.setDiffuseColor(color);
		mat.setSpecularColor(color);
		mat.setShininess(0f);
//		// by gonzalo: apago reflejos ambiente y especular en los cuerpos pues molesta bastante para el conteo de pixeles.
		mat.setEmissiveColor(color);
		return mat;
	}

	public void setTopView(Vector3f vector) {
		topView = vector;
	}

	public void setThirdView(Vector3f vector) {
		thirdView = vector;
	}


	public void setRobotView(Vector3f vector) {
		Primitive cylinder;

		robotView = vector;
		robotCameraView = new Vector3f(vector.x, vector.y + 0.05f, vector.z);

		Appearance app = new Appearance();
		Material mat = createMaterial(new Color3f(0.5f, 0f, 1f));
		app.setMaterial(mat);

		cylinder = new Cylinder(0.025f, 0.1f, app);

		Transform3D rotate = new Transform3D();
		rotate.rotX(Math.PI / 180.0d * 90);
		this.robotBodyRotationGroup = new TransformGroup();
		this.robotBodyRotationGroup
				.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.robotBodyRotationGroup
				.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.robotBodyRotationGroup.setTransform(rotate);
		this.robotBodyRotationGroup.addChild(cylinder);

		Transform3D translate = new Transform3D();
		translate.setTranslation(robotView);
		this.robotBodyTranslationGroup = new TransformGroup();
		this.robotBodyTranslationGroup
				.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.robotBodyTranslationGroup
				.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.robotBodyTranslationGroup.setTransform(translate);
		this.robotBodyTranslationGroup.addChild(this.robotBodyRotationGroup);

		this.addChild(this.robotBodyTranslationGroup);

		app = new Appearance();
		mat = createMaterial(new Color3f(0.5f, 0.5f, 0f));
		app.setMaterial(mat);

		cylinder = new Cone(0.025f, 0.1f, app);

		rotate = new Transform3D();
		rotate.rotX(Math.PI / 180.0d * 90);
		this.robotCameraRotationGroup = new TransformGroup();
		this.robotCameraRotationGroup
				.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.robotCameraRotationGroup
				.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.robotCameraRotationGroup.setTransform(rotate);
		this.robotCameraRotationGroup.addChild(cylinder);

		translate = new Transform3D();
		translate.setTranslation(robotCameraView);
		this.robotCameraTranslationGroup = new TransformGroup();
		this.robotCameraTranslationGroup
				.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.robotCameraTranslationGroup
				.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.robotCameraTranslationGroup.setTransform(translate);
		this.robotCameraTranslationGroup
				.addChild(this.robotCameraRotationGroup);
		this.addChild(this.robotCameraTranslationGroup);

	}

	public void moveRobot(Vector3f vector) {
		robotView.x += vector.x;
		robotView.y += vector.y;
		robotView.z += vector.z;

		robotCameraView.x += vector.x;
		robotCameraView.y += vector.y;
		robotCameraView.z += vector.z;

		Transform3D translate = new Transform3D();
		translate.setTranslation(robotView);

		this.robotBodyTranslationGroup.setTransform(translate);

		translate = new Transform3D();
		translate.setTranslation(robotCameraView);

		this.robotCameraTranslationGroup.setTransform(translate);
	}

	public void startRobot(Vector3f vector) {
		robotView = vector;
		robotCameraView = new Vector3f(vector.x, vector.y + 0.05f, vector.z);
		Transform3D translate = new Transform3D();
		translate.setTranslation(robotView);
		this.robotBodyTranslationGroup.setTransform(translate);
		translate = new Transform3D();
		translate.setTranslation(robotCameraView);
		this.robotCameraTranslationGroup.setTransform(translate);
	}


	public void rotateRobotBody(double degrees) {

		Transform3D rotate = new Transform3D();
		rotate.rotZ(Math.toRadians(-degrees));

		Transform3D temp = new Transform3D();
		this.robotBodyRotationGroup.getTransform(temp);

		temp.mul(rotate);
		this.robotBodyRotationGroup.setTransform(temp);
		rotate = new Transform3D();
		rotate.rotZ(Math.toRadians(-degrees));

		temp = new Transform3D();
		this.robotCameraRotationGroup.getTransform(temp);

		temp.mul(rotate);
		this.robotCameraRotationGroup.setTransform(temp);
	}

	public void rotateRobotCamera(double degrees) {

		Transform3D rotate = new Transform3D();
		rotate.rotZ(Math.toRadians(-degrees));

		Transform3D temp = new Transform3D();
		this.robotCameraRotationGroup.getTransform(temp);

		temp.mul(rotate);
		this.robotCameraRotationGroup.setTransform(temp);
	}

	public Vector3f getTopView() {
		return topView;
	}

	public Point2d getRobotView() {
		Point2d coord = new Point2d(robotView.x, robotView.z);
		return coord;
	}

	public Vector3f getRobotCameraView() {
		return robotCameraView;
	}

	public Vector3f getThirdView() {
		return thirdView;
	}

	public Vector3f getFood() {
		Vector3f result;
		if(boxHiden.contains(STRING_FOOD)) // si la comida fue eliminada del entorno devuelvo las coordendas de eliminacion
			result = new Vector3f(MOVE_REMOVE,MOVE_REMOVE,MOVE_REMOVE);
		else // si no fue eliminada devuelvo su pocición actual
			result = boxsPosition.get(STRING_FOOD);
			
		return result;
	}

}
