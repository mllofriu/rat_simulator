package robot;


//import Rat;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.vecmath.Point3d;

import Schemas.ActionSelectionSchema;
import simulation.Simulation;
import simulation.SimulationItem;
import simulation.SimulationOperation;
import support.Configuration;
import support.Utiles;



public class RobotVirtual extends java.awt.Frame implements IRobot {
	// esperas para que se estabilice la lectura de la camara luego de una
	// rotacion
	private int DELAY_CAMERA_ROTATE = Configuration
			.getInt("RobotVirtual.DELAY_CAMERA_ROTATE");
	private int MAX_TAKES_TO_DECREMENT_DELAY_CAMERA = 30;
	private final double DELTA_MOVE = 0.1;

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
	// Distancia maxima posible entre dos puntos globales para decidir si se
	// trata del mismo punto
	private static final double DISTANCIA_ENTRE_PUNTOS_CERCANOS = 0.015;
	private static final int CANT_IMAGES2PANORAMA =5;

	
	private static final int ANGLE2CHECK_CONSISTENCY = 3;
	private static final int MAX_COUNTER_DIFFERENCE = 120*ANGLE2CHECK_CONSISTENCY;

	// las imagenes obtenidas en las distintas direcciones que tiene que mirar
	// el robot para armar la panoramica
	private BufferedImage colorMatrixOAux;
	private BufferedImage colorMatrixRAux;
	private BufferedImage colorMatrixLAux;
	private BufferedImage colorMatrixAffR;
	private BufferedImage colorMatrixAffL;


	// sin el static no camina el asunto :S
	private int redL, redO, redR, redDL, redDR; // contadores de pixeles

	public static WorldBranchGroup world;

	private BufferedImage panoramica = new BufferedImage(IMAGE_WIDTH*CANT_IMAGES2PANORAMA, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private boolean[] affordances = new boolean[IRobot.CANT_ACCIONES];
	private boolean robotHasMoved;

	// panoramicas tomadas sin problemas con el render
	int picturesTakenWithoutIncrement=0;
	// 
	int currentCameraDelay=DELAY_CAMERA_ROTATE;
	
	public RobotVirtual() {
		WorldBranchGroup bg = new WorldBranchGroup(CURRENT_MAZE_DIR+DEFAULT_MAZE_FILE);
		WorldFrame worldFrame = new WorldFrame(bg);
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

		if (redL < MAX_PIXEL_LATERAL)
			this.affordances[Utiles.gradosRelative2Acccion(-90)] = true;
		if (redR < MAX_PIXEL_LATERAL)
			this.affordances[Utiles.gradosRelative2Acccion(90)] = true;
		// Si no hay mucho rojo al frente entonces puedo avanzar, en algunos
		// casos cuando esta muy cerca de la pared lee cero rojo
		// if (redO.get() < 1100 && redO.get() >0 && whiteO.get() < 2600 ) {
		if (redO < MAX_PIXEL_FRENTE) { // && APS.redO.get() > 0)
			this.affordances[Utiles.gradosRelative2Acccion(0)] = true;
			// si no puedo avanzar tampoco puedo ir a 45 grados
			// this.affordances[Utiles.gradosRelative2Acccion(-45)] = true;
			// this.affordances[Utiles.gradosRelative2Acccion(45)] = true;
		}
		if (redDL < MAX_PIXEL_DIAGONAL)
			this.affordances[Utiles.gradosRelative2Acccion(-45)] = true;
		if (redDR < MAX_PIXEL_DIAGONAL)
			this.affordances[Utiles.gradosRelative2Acccion(45)] = true;
		// agrego los affordances para todas las posiciones que no veo
		//this.affordances[Utiles.gradosRelative2Acccion(-180)] = true;
		//this.affordances[Utiles.gradosRelative2Acccion(180)] = true;
		//this.affordances[Utiles.gradosRelative2Acccion(-135)] = true;
		//this.affordances[Utiles.gradosRelative2Acccion(135)] = true;

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
	private boolean horario=true;
	
	@Override
	synchronized public BufferedImage getPanoramica() {
		BufferedImage panoramicaTestigo1, panoramicaTestigo2;
		boolean consistente;
		long currentTake, iniTime = System.currentTimeMillis();
		
		// si el robot se movio (avanzo o giro) debo actualizar la panoramica
		if (robotHasMoved) {
			panoramicaTestigo1 = new BufferedImage(IMAGE_WIDTH*CANT_IMAGES2PANORAMA, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
			panoramicaTestigo2 = new BufferedImage(IMAGE_WIDTH*CANT_IMAGES2PANORAMA, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
			// para controlar inconcistencias tomo dos panoramicas y las
			// comparo, repito hasta que sean casi iguales
			do {
				takePanorama(panoramica);
				consistente = checkConsistency(panoramica);

				// si no giro un poquito java 3d cachea la imagen se ve y
				// devuelve las dos imagenes con posibles inconsistencias (dos
				// marcas del mismo color, marcas cortadas, ...)
				if (consistente) {
					world.rotateRobot(-ANGLE2CHECK_CONSISTENCY);
					takePanorama(panoramicaTestigo1);
					world.rotateRobot(-ANGLE2CHECK_CONSISTENCY);
					takePanorama(panoramicaTestigo2);
					world.rotateRobot(2*ANGLE2CHECK_CONSISTENCY); // vuelvo a dejar el robot en su posicion origianal
				}
				
			} while (!consistente || aBitDifferents(panoramicaTestigo1, panoramica) || aBitDifferents(panoramicaTestigo1, panoramicaTestigo2));
			picturesTakenWithoutIncrement++;
			if (picturesTakenWithoutIncrement>MAX_TAKES_TO_DECREMENT_DELAY_CAMERA) {
				picturesTakenWithoutIncrement=0;
				if (currentCameraDelay>DELAY_CAMERA_ROTATE) {
					currentCameraDelay--;
					System.err.println("RV::decrementando delay de camara a " + currentCameraDelay);
				}
			}
				
			robotHasMoved = false;
			currentTake = System.currentTimeMillis()-iniTime;
			if (currentTake > maxTakeTime) maxTakeTime=currentTake;
			if (currentTake < minTakeTime) minTakeTime=currentTake;
			cantTakes++;
			sumaTakeTime=sumaTakeTime+currentTake;
		}
		return panoramica;
//		return new BufferedImage(IMAGE_WIDTH*CANT_IMAGES2PANORAMA, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}

	// devuelve si es consistente la panoramica
	private boolean checkConsistency(BufferedImage panoramica) {
		final int OFF = 0;
		final int ON = 1;
		final int ON_OFF = 2;
		int itColor;
		int contadorColumna[] = new int [landmarkColors.length];
		int checkStatus[] = new int [landmarkColors.length];
		Arrays.fill(checkStatus, OFF);
		boolean error = false;
		
		for (int iterW = 0; iterW < IMAGE_WIDTH*CANT_IMAGES2PANORAMA; iterW++) {
			Arrays.fill(contadorColumna, 0);
			for (int iterH = 0; iterH < IMAGE_HEIGHT; iterH++) {
				for (itColor = 0; itColor < landmarkColors.length; itColor++) {
					int colo1 = panoramica.getRGB(iterW,iterH);
					int colo2 = Utiles.color2RGB(landmarkColors[itColor]);

					if (Utiles.rgb2Color(panoramica.getRGB(iterW,iterH)).equals(landmarkColors[itColor]))
						contadorColumna[itColor]++;
							
				}	
			}
			
			// termine en este punto de procesar una columna para todos los colores
			// entonces se implementa la m��quina de estados
			for (itColor = 0; itColor < landmarkColors.length; itColor++) {
				switch (checkStatus[itColor]) {
				case OFF:
					if (contadorColumna[itColor] > 0) // si encontre el color actual en esta columna
						checkStatus[itColor] = ON; // no habia encontrado este color hasta ahora
					break;
				case ON:
					if (contadorColumna[itColor] == 0) // si no encontre el color actual en esta columna
						checkStatus[itColor] = ON_OFF; // ya lo encontre y ahora no lo veo m��s
					break;
				case ON_OFF:
					if (contadorColumna[itColor] > 0) {// si encontre el color actual en esta columna
						error = true;  //vuelvo a ver una marca -> error
						//System.err.println("RV::inconsistencia::PEPE PPPPPPPPPP*******************************");
					}
					break;
				} // fin del case
			}
			
			if (error) {
				currentCameraDelay++;
				picturesTakenWithoutIncrement=0;
				System.err.println("RV::inconsistencia::incrementando delay de camara a "
						+ currentCameraDelay);
				break; // si encontre alguna inconsistencia entonces corto el bucle
			}
			
		}
		return !error;
//		return true;
	}

	private boolean aBitDifferents(BufferedImage a, BufferedImage b) {
		Hashtable<Color, Integer> contadoresA = Utiles.contadores(a);
		Hashtable<Color, Integer> contadoresB = Utiles.contadores(b);
		int iterColors = 0, pixelsA, pixelsB;
		Integer currentContador;
		boolean end = false;

		// recorro los posibles colores a encontrar en la imagen y verifico que
		// su cantidad sea similar
		while ((iterColors < landmarkColors.length) && !end) {
			currentContador = contadoresA.get(landmarkColors[iterColors]);
			pixelsA = currentContador == null ? 0 : currentContador.intValue();
			currentContador = contadoresB.get(landmarkColors[iterColors]);
			pixelsB = currentContador == null ? 0 : currentContador.intValue();
			if (Math.abs(pixelsA - pixelsB) > MAX_COUNTER_DIFFERENCE)
				end = true;
			iterColors++;
		}
		// si son diferentes incremento el delay entre giros TODO: habria que
		// intentar bajarlo en algun caso
		if (end) {
			currentCameraDelay++;
			picturesTakenWithoutIncrement=0;
			System.err.println("RV::incrementando delay de camara a "
					+ currentCameraDelay);
		}
		return end;
	}

	private boolean differents(int[][] a, int[][] b) {
		int iterW = 0, iterH = 0;
		boolean end = false;
		while ((iterW < IMAGE_WIDTH - 1) && !end) {
			while ((iterH < IMAGE_HEIGHT) && !end) {
				end = a[iterW][iterH] != b[iterW][iterH];
				iterH++;
			}
			iterW++;
		}
		// si son diferentes incremento el delay entre giros TODO: habria que
		// intentar bajarlo en algun caso
		if (end) {
			currentCameraDelay++;
			picturesTakenWithoutIncrement=0;
			System.err.println("RV::incrementando delay de camara a "
					+ currentCameraDelay);
		}
		return end;
	}
	
	// TODO: seguro se puede mejorar
	void takePanorama(BufferedImage image) {
		if(CANT_IMAGES2PANORAMA==1)
			takePanorama1(image);
		else if(CANT_IMAGES2PANORAMA==5)
				takePanorama5(image);
		else {
			System.out.println("ERROR A MEJORAR");
			System.err.println("ERROR A MEJORAR");

			System.exit(0);
		}

	}
	
	// se le pasa una matriz creada y carga la imagenes para formar una
	// panoramica
	void takePanorama1(BufferedImage image) {
		// tomo imagen al frente
		colorMatrixOAux = getColorMatrix();

		// miro para un lado
		world.rotateRobot(ANGLE_HEAD_TURN);
		colorMatrixRAux = getColorMatrix();

		// miro para el otro lado
		world.rotateRobot(-2 * ANGLE_HEAD_TURN);
		colorMatrixLAux = getColorMatrix();

		// obtengo los laterales para los affordances
		world.rotateRobot(ANGLE_HEAD_TURN + 90);
		colorMatrixAffR = getColorMatrix();

		world.rotateRobot(180);
		colorMatrixAffL = getColorMatrix();

		// miro para adelante
		world.rotateRobot(90);

		// seteo contadores para affordances
		redL = Utiles.contador(colorMatrixAffL, Color.RED);
		redDL = Utiles.contador(colorMatrixLAux, Color.RED);
		redO = Utiles.contador(colorMatrixOAux, Color.RED);
		redDR = Utiles.contador(colorMatrixRAux, Color.RED);
		redR = Utiles.contador(colorMatrixAffR, Color.RED);

		// cargo la matriz panoramica
		for (int iterW = 0; iterW < IMAGE_WIDTH; iterW++)
			for (int iterH = 0; iterH < IMAGE_HEIGHT; iterH++) {
				image.setRGB(iterW,iterH, colorMatrixOAux.getRGB(iterW, iterH));
			}
	}
	
	// se le pasa una matriz creada y carga la imagenes para formar una
	// panoramica
	void takePanorama5(BufferedImage image) {

		if (horario) {
			// imagen 0
			world.rotateRobot(-2 * ANGLE_HEAD_TURN);
			colorMatrixAffL = getColorMatrix();	 
			world.rotateRobot(ANGLE_HEAD_TURN);
			colorMatrixLAux = getColorMatrix();
			world.rotateRobot(ANGLE_HEAD_TURN);
			colorMatrixOAux = getColorMatrix();
			world.rotateRobot(ANGLE_HEAD_TURN);
			colorMatrixRAux = getColorMatrix();			
			world.rotateRobot(ANGLE_HEAD_TURN);
			colorMatrixAffR = getColorMatrix();
			world.rotateRobot(-2 * ANGLE_HEAD_TURN);
		} else {
			// imagen 0
			world.rotateRobot(2 * ANGLE_HEAD_TURN);
			colorMatrixAffR = getColorMatrix();
			world.rotateRobot(-ANGLE_HEAD_TURN);
			colorMatrixRAux = getColorMatrix();			
			world.rotateRobot(-ANGLE_HEAD_TURN);
			colorMatrixOAux = getColorMatrix();
			world.rotateRobot(-ANGLE_HEAD_TURN);
			colorMatrixLAux = getColorMatrix();
			world.rotateRobot(-ANGLE_HEAD_TURN);
			colorMatrixAffL = getColorMatrix();	 
			world.rotateRobot(2 * ANGLE_HEAD_TURN);			
		}
		horario=!horario;

		// seteo contadores para affordances
		redL = Utiles.contador(colorMatrixAffL, Color.RED);
		redDL = Utiles.contador(colorMatrixLAux, Color.RED);
		redO = Utiles.contador(colorMatrixOAux, Color.RED);
		redDR = Utiles.contador(colorMatrixRAux, Color.RED);
		redR = Utiles.contador(colorMatrixAffR, Color.RED);

		// cargo la matriz panoramica
		for (int iterW = 0; iterW < IMAGE_WIDTH; iterW++)
			for (int iterH = 0; iterH < IMAGE_HEIGHT; iterH++) {
				image.setRGB(iterW,iterH, colorMatrixAffL.getRGB(iterW, iterH));
				image.setRGB(iterW + IMAGE_WIDTH,iterH, colorMatrixLAux.getRGB(iterW, iterH));
				image.setRGB(iterW + 2 * IMAGE_WIDTH,iterH, colorMatrixOAux.getRGB(iterW, iterH));
				image.setRGB(iterW + 3 * IMAGE_WIDTH,iterH, colorMatrixRAux.getRGB(iterW, iterH));
				image.setRGB(iterW + 4 * IMAGE_WIDTH,iterH, colorMatrixAffR.getRGB(iterW, iterH));
			}
	}
	
	@Override
	public void doAction(int grados) {
		previusPoint = getGlobalCoodinate();

		// If no rotation, translate
//		if (grados == 0)
//			world.moveRobotForward();
//		else
//			rotateRobot(grados);

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
		world.rotateRobot(actionDegrees);
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
