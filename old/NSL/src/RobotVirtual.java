import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.vecmath.*;
import javax.swing.*;

public class RobotVirtual extends java.awt.Frame implements IRobot {
	// esperas para que se estabilice la lectura de la camara luego de una
	// rotacion
	private int DELAY_CAMERA_ROTATE = Configuration
			.getInt("RobotVirtual.DELAY_CAMERA_ROTATE");
	private int MAX_TAKES_TO_DECREMENT_DELAY_CAMERA = 300;
	
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
	// Distancia maxima posible entre dos puntos globales para decidir si se
	// trata del mismo punto
	private static final double DISTANCIA_ENTRE_PUNTOS_CERCANOS = 0.015;
	private static final int MAX_COUNTER_DIFFERENCE = 120;

	// las imagenes obtenidas en las distintas direcciones que tiene que mirar
	// el robot para armar la panoramica
	private int[][] colorMatrixOAux = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
	private int[][] colorMatrixRAux = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
	private int[][] colorMatrixLAux = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
	private int[][] colorMatrixAffR = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
	private int[][] colorMatrixAffL = new int[IMAGE_WIDTH][IMAGE_HEIGHT];

	public final Color[] IMAGE_COLORS = { Color.CYAN, Color.MAGENTA,
			Color.WHITE, Color.YELLOW, Color.RED };

	// sin el static no camina el asunto :S
	static private int redL, redO, redR, redDL, redDR; // contadores de pixeles

	public static WorldFrame world;

	private int panoramica[][] = new int[5 * IMAGE_WIDTH][IMAGE_HEIGHT];
	private boolean[] affordances = new boolean[IRobot.CANT_ACCIONES];
	private boolean robotHasMoved;

	// panoramicas tomadas sin problemas con el render
	int picturesTakenWithoutIncrement=0;
	// 
	int currentCameraDelay=DELAY_CAMERA_ROTATE;
	
	public RobotVirtual() {
		world = new WorldFrame();
		world.show();
	}

	@Override
	public boolean[] affordances() {

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
		// siempre puedo girar 180
		this.affordances[Utiles.gradosRelative2Acccion(-180)] = true;
		this.affordances[Utiles.gradosRelative2Acccion(180)] = true;

//		System.out.println("RobotVirtual::Affordances (" + redL + ", " + redO
//				+ ", " + redR + ")");
		return affordances;
	}

	@Override
	synchronized public int[][] getPanoramica() {
		int panoramicaTestigo[][];
		boolean consistente;
		// si el robot se movio (avanzo o giro) debo actualizar la panoramica
		if (robotHasMoved) {
			panoramicaTestigo = new int[5 * IMAGE_WIDTH][IMAGE_HEIGHT];
			// para controlar inconcistencias tomo dos panoramicas y las
			// comparo, repito hasta que sean casi iguales
			do {
				takePanorama(panoramica);
				consistente = checkConsistency(panoramica);

				// si no giro un poquito java 3d cachea la imagen se ve y
				// devuelve las dos imagenes con posibles inconsistencias (dos
				// marcas del mismo color, marcas cortadas, ...)
				if (consistente) {
					world.rotateRobotCamera(1);
					takePanorama(panoramicaTestigo);
					world.rotateRobotCamera(-1); // vuelvo a dejar el robot en su posicion origianal
				}
				
			} while (!consistente || aBitDifferents(panoramicaTestigo, panoramica));
			picturesTakenWithoutIncrement++;
			if (picturesTakenWithoutIncrement>MAX_TAKES_TO_DECREMENT_DELAY_CAMERA) {
				picturesTakenWithoutIncrement=0;
				if (currentCameraDelay>DELAY_CAMERA_ROTATE) {
					currentCameraDelay--;
					System.err.println("RV::decrementando delay de camara a " + currentCameraDelay);
				}
			}
				
			robotHasMoved = false;
		}

		return panoramica;
	}

	// devuelve si es consistente la panoramica
	private boolean checkConsistency(int[][] panoramica) {
		final int OFF = 0;
		final int ON = 1;
		final int ON_OFF = 2;
		int itColor;
		int contadorColumna[] = new int [IMAGE_COLORS.length];
		int checkStatus[] = new int [IMAGE_COLORS.length];
		Arrays.fill(checkStatus, OFF);
		boolean error = false;
		
		for (int iterW = 0; iterW < IMAGE_WIDTH*5; iterW++) {
			Arrays.fill(contadorColumna, 0);
			for (int iterH = 0; iterH < IMAGE_HEIGHT; iterH++) {
				for (itColor = 0; itColor < IMAGE_COLORS.length; itColor++) {
					if (panoramica[iterW][iterH]==Utiles.color2RGB(IMAGE_COLORS[itColor]))
						contadorColumna[itColor]++;
							
				}	
			}
			
			// termine en este punto de procesar una columna para todos los colores
			// entonces se implementa la máquina de estados
			for (itColor = 0; itColor < IMAGE_COLORS.length; itColor++) {
				switch (checkStatus[itColor]) {
				case OFF:
					if (contadorColumna[itColor] > 0) // si encontre el color actual en esta columna
						checkStatus[itColor] = ON; // no habia encontrado este color hasta ahora
					break;
				case ON:
					if (contadorColumna[itColor] == 0) // si no encontre el color actual en esta columna
						checkStatus[itColor] = ON_OFF; // ya lo encontre y ahora no lo veo más
					break;
				case ON_OFF:
					if (contadorColumna[itColor] > 0) // si encontre el color actual en esta columna
						error = true;  //vuelvo a ver una marca -> error
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
	}

	private boolean aBitDifferents(int[][] a, int[][] b) {
		Hashtable<Color, Integer> contadoresA = Utiles.contadores(a);
		Hashtable<Color, Integer> contadoresB = Utiles.contadores(b);
		int iterColors = 0, pixelsA, pixelsB;
		Integer currentContador;
		boolean end = false;

		// recorro los posibles colores a encontrar en la imagen y verifico que
		// su cantidad sea similar
		while ((iterColors < IMAGE_COLORS.length) && !end) {
			currentContador = contadoresA.get(IMAGE_COLORS[iterColors]);
			pixelsA = currentContador == null ? 0 : currentContador.intValue();
			currentContador = contadoresB.get(IMAGE_COLORS[iterColors]);
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

	// se le pasa una matriz creada y carga la imagenes para formar una
	// panoramica
	void takePanorama(int image[][]) {
		// tomo imagen al frente
		colorMatrixOAux = getColorMatrix();

		// miro para un lado
		world.rotateRobotCamera(ANGLE_HEAD_TURN);
		colorMatrixRAux = getColorMatrix();

		// miro para el otro lado
		world.rotateRobotCamera(-2 * ANGLE_HEAD_TURN);
		colorMatrixLAux = getColorMatrix();

		// obtengo los laterales para los affordances
		world.rotateRobotCamera(ANGLE_HEAD_TURN + 90);
		colorMatrixAffR = getColorMatrix();

		world.rotateRobotCamera(180);
		colorMatrixAffL = getColorMatrix();

		// miro para adelante
		world.rotateRobotCamera(90);

		// seteo contadores para affordances
		redL = Utiles.contador(colorMatrixAffL, Color.RED);
		redDL = Utiles.contador(colorMatrixLAux, Color.RED);
		redO = Utiles.contador(colorMatrixOAux, Color.RED);
		redDR = Utiles.contador(colorMatrixRAux, Color.RED);
		redR = Utiles.contador(colorMatrixAffR, Color.RED);

		// cargo la matriz panoramica
		for (int iterW = 0; iterW < IMAGE_WIDTH; iterW++)
			for (int iterH = 0; iterH < IMAGE_HEIGHT; iterH++) {
				image[iterW][iterH] = colorMatrixAffL[iterW][iterH];
				image[iterW + IMAGE_WIDTH][iterH] = colorMatrixLAux[iterW][iterH];
				image[iterW + 2 * IMAGE_WIDTH][iterH] = colorMatrixOAux[iterW][iterH];
				image[iterW + 3 * IMAGE_WIDTH][iterH] = colorMatrixRAux[iterW][iterH];
				image[iterW + 4 * IMAGE_WIDTH][iterH] = colorMatrixAffR[iterW][iterH];
			}

	}

	@Override
	public void doAction(int grados) {
		// TODO Auto-generated method stub11
		double x = 0, y = 0, z = 0;
		// rotateRobot(grados);
		world.moveRobot(new Point3d(ActionSelectionSchema.x,
				ActionSelectionSchema.y, ActionSelectionSchema.z));
		// fin del if avanzar robot
		robotHasMoved = true;
	}

	@Override
	public Point2d getGlobalCoodinate() {
		// TODO Auto-generated method stub
		return world.getGlobalCoodinate();
	}

	@Override
	public void startRobot() {
		// TODO Auto-generated method stub
		world.startRobot(Rat.simItem.getInitialPosition());

		// TODO: seguro hay una mejor forma de hacerlo
//		while ()
//		actionDegrees = Utiles.acccion2GradosRelative(action);
//		RobotFactory.getRobot().rotateRobot(actionDegrees);				

		robotHasMoved = true;
		updateWorld();
	}
	
	
	// Realiza la gestion del mundo agregando y eliminando elementos si corresponde segun archivo de simulacion utilizado
	private void updateWorld() {
		Vector<SimulationOperation> operations = Simulation.getOperations();
		SimulationOperation operation;
		int operIter = 0;

		if (Rat.simItem.getType()==SimulationItem.HABITUATION)
			world.constr.remove(worldBranchGroup.STRING_FOOD);
		else
			world.constr.add(worldBranchGroup.STRING_FOOD);

		System.out.println("World::operation size: " + operations.size()
				+ ". Item name: " + Rat.simItem.getName());
		while (operIter < operations.size()) {
			operation = operations.elementAt(operIter);
			if (operation.getTrialApply().equals(Rat.simItem.getName())) {
				System.out.println("World::trial: " + operation.getTrialApply()
						+ "/" + Simulation.getCurrenTrial() + ". ope: "
						+ operation.getOperation() + "box: "
						+ operation.getPrimitiveName());
				if (operation.getOperation().equals(SimulationOperation.ADD))
					world.constr.add(operation.getPrimitiveName());
				else if (operation.getOperation().equals(
						SimulationOperation.REMOVE))
					world.constr.remove(operation.getPrimitiveName());
				else if (operation.getOperation().equals(
						SimulationOperation.MOVE))
					world.constr.move(operation.getPrimitiveName(),Simulation.getPoint(operation.getPointName()));
				operations.remove(operIter);
			} else
				operIter++;
		}
	}

	public int[][] getColorMatrix() {
		try {
			Thread.sleep(currentCameraDelay);
		} catch (Exception e) {
			System.out.println(e);
		}

		return world.getColorMatrix();
	}

	@Override
	public void rotateRobot(int actionDegrees) {
		// TODO Auto-generated method stub
		world.rotateRobot(actionDegrees);
		robotHasMoved = true;
	}

	@Override
	public boolean findFood() {

		return world.getFood().distance(world.getGlobalCoodinate()) < DISTANCIA_ENTRE_PUNTOS_CERCANOS;
	}

}
