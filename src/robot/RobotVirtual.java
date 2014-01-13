package robot;


//import Rat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.vecmath.Vector3f;

import support.Configuration;
import support.Utiles;

import com.sun.j3d.utils.universe.SimpleUniverse;



public class RobotVirtual extends java.awt.Frame implements IRobot {
	// esperas para que se estabilice la lectura de la camara luego de una
	// rotacion
	private int DELAY_CAMERA_ROTATE = Configuration
			.getInt("RobotVirtual.DELAY_CAMERA_ROTATE");

	private final String DEFAULT_MAZE_DIR = Configuration
			.getString("WorldFrame.MAZE_DIRECTORY");
	private final String DEFAULT_MAZE_FILE = Configuration
			.getString("WorldFrame.MAZE_FILE");
	private final String CURRENT_MAZE_DIR = System.getProperty("user.dir")
			+ File.separatorChar + DEFAULT_MAZE_DIR + File.separatorChar;
	// giro de la cabeza para armar la panoramica
	private final int ANGLE_HEAD_TURN = Configuration
			.getInt("Robot.ANGLE_HEAD_TURN");
	// altura de la imagen color
	public static final int IMAGE_HEIGHT = 80;
	// ancho de la imagen color
	public static final int IMAGE_WIDTH = 80;
	private final int MAX_PIXEL_LATERAL = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_LATERAL");
	private final int MAX_PIXEL_DIAGONAL = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_DIAGONAL");
	private final int MAX_PIXEL_FRENTE = Configuration
			.getInt("RobotVirtual.MAX_PIXEL_FRENTE");
	
	public static final double SPEED_ERROR = Configuration.getDouble("Robot.SPEED_ERROR"); 
	
	public static WorldBranchGroup world;

	private BufferedImage panoramica;
	private boolean[] affordances = new boolean[IRobot.CANT_ACCIONES];
	private boolean robotHasMoved;

	// panoramicas tomadas sin problemas con el render
	int picturesTakenWithoutIncrement=0;
	// 
	int currentCameraDelay=DELAY_CAMERA_ROTATE;
	private ImageComponent2D[] offScreenImages;
	private Canvas3D[] offScreenCanvas;
	
	public RobotVirtual() {
		world = new WorldBranchGroup(CURRENT_MAZE_DIR+DEFAULT_MAZE_FILE);
		
		// Create off-screen canvas to see through the robots views
		offScreenCanvas = new Canvas3D[world.NUM_ROBOT_VIEWS];
		offScreenImages = new ImageComponent2D[world.NUM_ROBOT_VIEWS];
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		for (int i=0; i<world.NUM_ROBOT_VIEWS; i++){
			offScreenCanvas[i] = new Canvas3D(config, true);
			offScreenImages[i] = new ImageComponent2D(ImageComponent2D.FORMAT_RGB, 
					new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB));
			offScreenImages[i].setCapability(ImageComponent2D.ALLOW_IMAGE_READ);
			world.getRobotView(i).addCanvas3D(offScreenCanvas[i]);
			offScreenCanvas[i].setOffScreenBuffer(offScreenImages[i]);
			offScreenCanvas[i].getScreen3D().setPhysicalScreenWidth(
					0.0254d/90.0*IMAGE_WIDTH);
			offScreenCanvas[i].getScreen3D().setPhysicalScreenHeight(
					0.0254d/90.0*IMAGE_HEIGHT);
			offScreenCanvas[i].getScreen3D().setSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
			// Not added to the actual screen
		}
		
		WorldFrame worldFrame = new WorldFrame(world);
		worldFrame.setVisible(true);
	}

	@Override
	public boolean[] affordances() {
		getPanoramica();
		
		Arrays.fill(affordances, false); // inicializo todos los affordances
		// como no disponibles
		// Si no hay mucho rojo a la izquierda entonces puedo girar en esa
		// direccin
		// if (redL.get() < 550 && whiteO.get() < 1000) {

		if (Utiles.contador(this.offScreenImages[0].getImage(), Color.red) < MAX_PIXEL_LATERAL)
			this.affordances[Utiles.gradosRelative2Acccion(-90)] = true;
		if (Utiles.contador(this.offScreenImages[4].getImage(), Color.red) < MAX_PIXEL_LATERAL)
			this.affordances[Utiles.gradosRelative2Acccion(90)] = true;
		// Si no hay mucho rojo al frente entonces puedo avanzar, en algunos
		// casos cuando esta muy cerca de la pared lee cero rojo
		// if (redO.get() < 1100 && redO.get() >0 && whiteO.get() < 2600 ) {
		if (Utiles.contador(this.offScreenImages[2].getImage(), Color.red) < MAX_PIXEL_FRENTE) { // && APS.redO.get() > 0)
			this.affordances[Utiles.gradosRelative2Acccion(0)] = true;
			// si no puedo avanzar tampoco puedo ir a 45 grados
			// this.affordances[Utiles.gradosRelative2Acccion(-45)] = true;
			// this.affordances[Utiles.gradosRelative2Acccion(45)] = true;
		}
		if (Utiles.contador(this.offScreenImages[1].getImage(), Color.red) < MAX_PIXEL_DIAGONAL)
			this.affordances[Utiles.gradosRelative2Acccion(-45)] = true;
		if (Utiles.contador(this.offScreenImages[3].getImage(), Color.red) < MAX_PIXEL_DIAGONAL)
			this.affordances[Utiles.gradosRelative2Acccion(45)] = true;
		// agrego los affordances para todas las posiciones que no veo
		this.affordances[Utiles.gradosRelative2Acccion(-180)] = true;
		this.affordances[Utiles.gradosRelative2Acccion(180)] = true;
		this.affordances[Utiles.gradosRelative2Acccion(-135)] = true;
		this.affordances[Utiles.gradosRelative2Acccion(135)] = true;

//		System.out.println("RobotVirtual::Affordances (" + redL + ", " + redO
//				+ ", " + redR + ")");
		return affordances;
	}

	private long minTakeTime=Long.MAX_VALUE;
	private long maxTakeTime=Long.MIN_VALUE;
	private long sumaTakeTime=0;
	private long cantTakes=0;
	private Double previusPoint = new Double();
	private Double currentPoint = new Double();
	
	@Override
	synchronized public BufferedImage getPanoramica() {
		panoramica = new BufferedImage(IMAGE_WIDTH*world.NUM_ROBOT_VIEWS,
				IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < world.NUM_ROBOT_VIEWS; i++){
			offScreenCanvas[i].renderOffScreenBuffer();
			offScreenCanvas[i].waitForOffScreenRendering();
			BufferedImage image = offScreenImages[i].getImage();
			for (int j = 0; j < IMAGE_WIDTH; j ++){
				for (int k = 0; k < IMAGE_HEIGHT; k++){
					panoramica.setRGB(j + IMAGE_WIDTH * i, k, image.getRGB(j, k));
				}
			}
		}
		return panoramica;
	}
	
	@Override
	public void doAction(int grados) {
		previusPoint = getGlobalCoodinate();

		// If no rotation, translate
		if (grados == 0)
			world.moveRobot(new Vector3f(0f,0f,-.1f));
		else
			rotateRobot(grados);

		robotHasMoved = true;
		currentPoint = getGlobalCoodinate();

	}
	
	@Override
	public Double getGlobalCoodinate() {
		// TODO Auto-generated method stub
		return new Double();
	}
	
	@Override
	public double getGlobalDirection() {
		// esto no camina => chanchada return world.getGlobalAngle();
//		return World.headAngle;
		return 0;
	}


	@Override
	public void startRobot() {
		// TODO Auto-generated method stub
//		world.startRobot(Rat.simItem.getInitialPosition());

		// TODO: seguro hay una mejor forma de hacerlo
//		while ()
//		actionDegrees = Utiles.acccion2GradosRelative(action);
//		RobotFactory.getRobot().rotateRobot(actionDegrees);	
		if(cantTakes>0)
			System.err.println("RVirtual::take min: " + minTakeTime + " . max: " + maxTakeTime + ". prom: " + (sumaTakeTime/cantTakes));
		robotHasMoved = true;
//		updateWorld();
	}

	public BufferedImage getColorMatrix() {
		try {
			Thread.sleep(currentCameraDelay);
		} catch (Exception e) {
			System.out.println(e);
		}

//		return world.getColorMatrix();
		return null;
	}

	public void rotateRobot(int actionDegrees) {
		// TODO Auto-generated method stub
		world.rotateRobot(Math.toRadians(actionDegrees));
		robotHasMoved = true;
	}

	@Override
	public boolean findFood() {
//		double distanciaAComida = world.getFood().distance(world.getGlobalCoodinate()) ;
//		//System.err.println("RV::distancia a la comida: " + distanciaAComida);
//		return distanciaAComida < DISTANCIA_ENTRE_PUNTOS_CERCANOS;
		return false;
	}
	public static final int MAX_PIXEL_MARCA = Configuration.getInt("Rat.MAX_PIXEL_MARCA");	
	public static final int MIN_PIXEL_MARCA = Configuration.getInt("Rat.MIN_PIXEL_MARCA");
	private static final float STEP = 0.1f;	

	/* (non-Javadoc)
	 * @see khepera.IRobot#findLandmarks()
	 */
	private final Color[] landmarkColors = {Color.GREEN, Color.CYAN, Color.MAGENTA, Color.YELLOW};

	Double [] landmarks = new Double[landmarkColors.length];

	@Override
	public Double[] findLandmarks() {
		Hashtable <Color,Integer> contadores;
		Enumeration <Color>keys;
        Double [] landWiew = new Double[landmarkColors.length];
	
		getPanoramica();
		contadores = Utiles.contadores(panoramica);
		keys = contadores.keys();
		Integer contador;
		double cantPX;
        for (int iterLand=0; iterLand<landmarkColors.length;iterLand++) {
        	contador = contadores.get(landmarkColors[iterLand]);
        	if (contador==null)
        		landWiew[iterLand] = null;
        	else {
        		cantPX = (double)contador.intValue()-MIN_PIXEL_MARCA;
        		landWiew[iterLand] = new Double(Utiles.anguloColor(panoramica, landmarkColors[iterLand]), cantPX/(double)(MAX_PIXEL_MARCA-MIN_PIXEL_MARCA));
        	}
        }
        mergeLandmarks(landmarks, landWiew);

		return landmarks;	
	}
	
	/**
	 * @param recibe dos arrays de marcas
	 * los mergea, de la siguiente manera: se queda para cada color con la marca mas grande
	 */
	private void mergeLandmarks(Double[] a, Double[] b) {
        for (int iterLand=0; iterLand<landmarkColors.length;iterLand++)
        	if ((a[iterLand]!=null)&&(b[iterLand]!=null)) {
        		// (position, size)
// se queda con el mas grande       		if (b[iterLand].y>a[iterLand].y)
//        			a[iterLand]=b[iterLand];
        		// tomo el promedio de los dos tama��os
        		a[iterLand].y = (a[iterLand].y + b[iterLand].y)/2; 
        	} else if (b[iterLand]!=null) {
        		a[iterLand]=b[iterLand];
        	} // else dejo a como estaba
	}
	/* (non-Javadoc)
	 * @see robot.IRobot#getColorsLandmarks()
	 */
	@Override
	public Color[] getColorsLandmarks() {
		// TODO Auto-generated method stub
		return landmarkColors;
	}

	/* (non-Javadoc)
	 * @see robot.IRobot#getSpeed()
	 */
	@Override
	public double getSpeed() {
		return Utiles.speed(previusPoint, currentPoint);
	}

	/* (non-Javadoc)
	 * @see robot.IRobot#getHeadDirection()
	 */
	@Override
	public double getHeadDirection() {
		//System.err.println("RV::head: " + Utiles.headDirection(previusPoint, currentPoint));
		return Utiles.headDirection(previusPoint, currentPoint);
	}

}
