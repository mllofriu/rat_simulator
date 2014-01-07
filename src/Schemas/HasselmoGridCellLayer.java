package Schemas;
import WorldGraphLayer;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import neural.GridCell;
import nslj.src.lang.NslDinDouble0;
import nslj.src.lang.NslDoutDouble1;
import nslj.src.lang.NslDoutDouble2;
import nslj.src.lang.NslModule;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import robot.RobotFactory;
import support.Configuration;
import support.Utiles;

/**
 * 
 */

/**
 * @author gtejera Version: 1 Fecha: 10 de agosto de 2012.
 */
public class HasselmoGridCellLayer extends APathIntegrationLayer {
	private static final String DEFAULT_MODULE_NAME = "HasselmoGridCellLayer (nombre por defecto)";

	private static final int ANCHO = 100; // 200
	private static final int LARGO = 100; // 200

	private static final int NUMBER_ENSEMBLES = 20;

	private static final int NUMBER_RANDOM_POINTS = Configuration
			.getInt("HasselmoGridCellLayer.DefaultGridsNumber");

	final double MIN_Z = 0;
	final double MAX_Z = 1.5;

	final double MAX_ORIENTATION = 120 * Math.PI / 180.0;
	final double MAX_SPATIAL_PHASE = 2.0 * Math.PI;
	public static double DEFAULT_MIN_ACTIVATION = 3; // 1.8 hasselmo
	public static double DEFAULT_MAX_ACTIVATION = 8; // 8 es el maximo teórico
	public static final int DEFAULT_LAYER_SIZE = DynamicRemappingLayer.DYNAMIC_REMAPPING_HEIGHT
			* DynamicRemappingLayer.DYNAMIC_REMAPPING_WIDTH * 2 * 2;
	// private static final int DEFAULT_LAYER_SIZE =
	// Configuration.getInt("GridCellLayer.DefaultGridsNumber");;
	public static final boolean PI_ON = Configuration
			.getBoolean("DynamicRemappingLayer.PI_ON");

	private static final double MAX_OUTPUT = 1;

	public static final int GRIDS_PER_POINT = 10;

	/*
	 * las entradas a la capa de celdas grilla son velocidad y dirección de la
	 * cabeza que es enviada a todas las celdas grilla de esta capa
	 */
	// public NslDinDouble0 speed = new NslDinDouble0("speed", this);
	// public NslDinDouble0 headDirection = new NslDinDouble0("headDirection",
	// this);
	// la salida de esta capa es un array con los disparos de cada una de las
	// celdas grilla y la posicion donde se encuentra
	// public NslDoutDouble1 outputPIL;
	public NslDoutDouble1 gridCellsPositionNSL;
	// estructura para almacenar las celdas grilla de esta capa
	private GridCell[] gridCells;
	private double[] gridCellsActivation;
	private double[] gridCellsPosition;
	private int[] numberActivation;
	private int[] numberNotActivation;
	private Double[] centerCoords;

	private boolean firstStep = true;

	public HasselmoGridCellLayer(String nslName, NslModule nslParent) {
		this(DEFAULT_MODULE_NAME, nslParent, DEFAULT_LAYER_SIZE);
	}

	public HasselmoGridCellLayer(NslModule nslParent, int layerSize) {
		this(DEFAULT_MODULE_NAME, nslParent, layerSize);
	}

	public HasselmoGridCellLayer(String nslName, NslModule nslParent,
			int layerSize) {
		super(nslName, nslParent);
		outputPIL = new NslDoutDouble1("Grid Cells Activation NSL", this,
				layerSize);
		gridCellsPositionNSL = new NslDoutDouble1("Grid Cells Position NSL",
				this, layerSize);
		gridCells = new GridCell[layerSize];
		gridCellsActivation = new double[layerSize];
		gridCellsPosition = new double[layerSize];
		numberActivation = new int[layerSize];
		numberNotActivation = new int[layerSize];
		centerCoords = new Double[layerSize];
		generateRegularGrid();

		// generateSavelli2010();
		// generateRandomGrids();
		// generateSi2009();
	}

	private Vector<Point2D.Double> generetaRandomPoints() {
		Vector<Point2D.Double> result = new Vector<Point2D.Double>();

		for (int iterPoints = 0; iterPoints < NUMBER_RANDOM_POINTS; iterPoints++) {
			result.add(new Point2D.Double(-Math.random() * ANCHO, Math.random()
					* LARGO));
		}

		return result;
	}

	Point2D.Double UN_POINT = new Point2D.Double(-0.6, 0.6);

	// private Vector<Point2D.Double> generetaPointsInGrid() {
	// Point2D.Double newPoint;
	// Vector<Point2D.Double> result = new Vector<Point2D.Double>();
	// // newPoint = new Point2D.Double(0.4, 0);result.add(newPoint);
	// // newPoint = new Point2D.Double(0.3, 0);result.add(newPoint);
	// // newPoint = new Point2D.Double(0.2, 0);result.add(newPoint);
	// // newPoint = new Point2D.Double(0.1, 0);result.add(newPoint);
	// // result.add(UN_POINT);
	//
	// return result;
	// }

	private Vector<Point2D.Double> generetaPointsInGrid() {
		Vector<Point2D.Double> result = new Vector<Point2D.Double>();
		// Para rutas de HAsselmo
		// Point2D.Double newPoint,o = new Point2D.Double(-0.6,0.6);
		// Para cuando arranca en cero, como en las pruebas de morris básicas
		Point2D.Double newPoint, o = new Point2D.Double(0, 0);
		final double RADIUS_DISTANCE = 0.45; // 0.448 es el radio para uno de
												// los puntos mas alejados 0.4,
												// 0.2
		final double RADIUS = 0.4; // 0.448 es el radio para uno de los puntos
									// mas alejados 0.4, 0.2

		double MIN_X = o.x - RADIUS, MAX_X = o.x + RADIUS;
		double MIN_Y = o.y - RADIUS, MAX_Y = o.y + RADIUS;
		double STEP = 0.1;

		for (double x = MIN_X; x <= MAX_X; x = x + STEP)
			for (double y = MIN_Y; y <= MAX_Y; y = y + STEP) {
				newPoint = new Point2D.Double(x, y);
				if (newPoint.distance(o) <= RADIUS_DISTANCE) {
					result.add(newPoint);
					System.err.println("HGCL::point: (" + x + " ," + y + ").");

				}
			}
		return result;
	}

	// double ZETAS[] = {0, 0.2, 0.4, 0.6};
	double ZETAS[] = { 1. };

	private void generateRegularGrid() {
		Random randZd = new Random();
		Random randOrientation = new Random();
		Random randSpatialPhase = new Random();
		Random randA = new Random();

		double frequency, orientation, a, b, lambda, z;
		Vector<Point2D.Double> points = generetaPointsInGrid();
		double Z; // entre 0 y 1.5
		int NUMBER_Os = GRIDS_PER_POINT;

		Point2D.Double point;
		int iterCell = 0;

		for (int iterZs = 0; iterZs < ZETAS.length; iterZs++) {
			Z = ZETAS[iterZs];
			lambda = 30.0 * Z + 37.09;
			frequency = calculateFrequency(Z);
			for (int iterPoints = 0; iterPoints < points.size(); iterPoints++) {
				orientation = 0; // randOrientation.nextDouble() * 6 * Math.PI /
									// 180.0; // la primera orientacion al azar
									// entre 0 y 6
				point = points.get(iterPoints);
				for (int iterOrientation = 0; iterOrientation < NUMBER_Os; iterOrientation++) {
					gridCells[iterCell] = new GridCell(frequency, orientation,
							point);
					centerCoords[iterCell] = point;
					orientation = orientation + 60.0 / ((double) NUMBER_Os)
							* Math.PI / 180.0;
					iterCell++;
					System.err
							.println("" + frequency + " ." + orientation * 180
									/ Math.PI + " ." + point.x + ". y:"
									+ point.y);
				}
			}
		}
		System.out.println("HasselmoGridCellLayer::used grids cells (%): "
				+ (iterCell * 100 / gridCells.length));
		System.out.println("HasselmoGridCellLayer::used grids cells: "
				+ iterCell + "/" + gridCells.length + ".");
		// Utiles.shuffleList(gridCells);
	}

	// genera la capa de neuronas grilla totalmente aleatoria
	private void generateRandomGrids() {
		Random randZd = new Random();
		Random randOrientation = new Random();
		Random randSpatialPhase = new Random();
		Random randA = new Random();
		// espaciado: inicializo celdas grilla con espaciado entre 0 and 1.5 mm
		// desde el borde postrhinal The dorsal border between entorhinal and
		// post-rhinal
		// cortex is located at about 3.8–4 mm. T(zd) = 0.094zd - 0.25 y fSoma =
		// 1/T.
		// orientación: ídem orientación entre 0 y 120 grados.
		// fase: la fase se inicializa en 0 para todas las grilas.
		double z, frequency, orientation, a, b, lambda;
		Vector<Point2D.Double> points = generetaRandomPoints();
		Point2D.Double point;

		for (int iterCells = 0; iterCells < gridCells.length; iterCells++) {
			z = 1.5; // random(0, MAX_Z); // randZd.nextDouble()*MAX_Z;
			frequency = calculateFrequency(z);
			lambda = 30.0 * z + 37.09;
			point = points.get((int) Math.round(randSpatialPhase.nextDouble()
					* (points.size() - 1)));
			gridCellsPosition[iterCells] = lambda;
			orientation = randOrientation.nextDouble() * MAX_ORIENTATION;
			gridCells[iterCells] = new GridCell(frequency, orientation, point);
			System.out.println("HasselmoGridCellLayer::frequency: " + frequency
					+ ". espaciado: " + lambda + ". orientation: "
					+ orientation + ". point: " + point.x + ", " + point.y
					+ ".");
		}

	}

	/*
	 * Propuesta de generacion de celdas grilla de Savelli and Knierim (2010)
	 * The basic version contained 1,000 grid-cell spike-train generators as
	 * input to 500 integrate-and-fire units, representing potential place cells
	 * of the hippocampus. The phase, orientation, and scale of the grids were
	 * uniformly sampled as follows. The 1,000 units were first divided into 10
	 * groups of 100 units that corresponded to 10 different scales of
	 * intervertex spacing, ranging from 30 to 53 cm by constant increments.
	 * This was the range of intervertex spacings observed in roughly the most
	 * dorsal 1 mm extension of the dorsocaudal MEC (Hafting et al. 2005). Each
	 * group was in turn split into 10 subgroups, each of 10 units,
	 * corresponding to 10 different orientations separated by 6° increments.
	 * The first orientation value was sampled randomly in the 0 – 6° range
	 * independently for each different scale. Finally, the phases of the 10
	 * units in each of these scaleϩorientation subgroups were uniformly sampled
	 * over the entire enclosure covered by the rat’s trajectory. Each of the
	 * 500 integrate-and-fire hippocampal units received 100 excitatory synaptic
	 * inputs that were uniformly sampled without repetition from the available
	 * pool of 1,000 grid-cell spike- train generators. Hence the same input
	 * unit was generally shared by different hippocampal cells.
	 */
	private void generateSavelli2010() {
		Random randZd = new Random();
		Random randOrientation = new Random();
		Random randSpatialPhase = new Random();
		Random randA = new Random();

		double frequency, orientation, a, b, lambda, z;
		Vector<Point2D.Double> points = generetaPointsInGrid();

		Point2D.Double point;
		int iterCell;
		for (int iterZ = 0; iterZ < 10; iterZ++) {
			z = iterZ / 100.0;
			frequency = calculateFrequency(z);
			lambda = 30.0 * z + 37.09;
			orientation = randOrientation.nextDouble() * 6 * Math.PI / 180.0; // la
																				// primera
																				// orientacion
																				// al
																				// azar
																				// entre
																				// 0
																				// y
																				// 6
			for (int iterOrientation = 0; iterOrientation < 10; iterOrientation++) {

				for (int iterPoints = 0; iterPoints < points.size(); iterPoints++) {
					iterCell = iterZ * 100 + iterOrientation * 10 + iterPoints;
					gridCellsPosition[iterCell] = lambda;
					point = points.get(iterPoints);
					gridCells[iterCell] = new GridCell(frequency, orientation,
							point);
					System.out.println("HasselmoGridCellLayer::cell: "
							+ iterCell + " frequency: " + frequency
							+ ". espaciado: " + lambda + ". orientation: "
							+ orientation + ". point: " + point.x + ", "
							+ point.y + ".");
				}
				orientation = orientation + 6 * Math.PI / 180.0;
			}

		}

	}

	// genera la capa de neuronas grilla segun la propuesta de Si2009
	private void generateSi2009() {
		Random randZd = new Random();
		Random randOrientation = new Random();
		Random randSpatialPhase = new Random();
		Random randA = new Random();
		// espaciado: inicializo celdas grilla con espaciado entre 0 and 1.5 mm
		// desde el borde postrhinal The dorsal border between entorhinal and
		// post-rhinal
		// cortex is located at about 3.8–4 mm. T(zd) = 0.094zd - 0.25 y fSoma =
		// 1/T.
		// orientación: ídem orientación entre 0 y 120 grados.
		// fase: la fase se inicializa en 0 para todas las grilas.
		double z, frequency, orientation, a, b, lambda;
		Point2D.Double point;
		int iterCell;
		for (int iterEnsemble = 0; iterEnsemble < NUMBER_ENSEMBLES; iterEnsemble++) {
			z = random(0, MAX_Z); // randZd.nextDouble()*MAX_Z;
			frequency = calculateFrequency(z);
			lambda = 30.0 * z + 37.09;
			point = new Point2D.Double(Math.random() * (ANCHO - ANCHO / 2),
					Math.random() * (LARGO - LARGO / 2));

			for (int iterInEnsemble = 0; iterInEnsemble < gridCells.length
					/ NUMBER_ENSEMBLES; iterInEnsemble++) {
				iterCell = iterEnsemble * gridCells.length / NUMBER_ENSEMBLES
						+ iterInEnsemble;
				gridCellsPosition[iterCell] = lambda;
				orientation = randOrientation.nextDouble() * MAX_ORIENTATION;
				gridCells[iterCell] = new GridCell(frequency, orientation,
						point);
				System.out.println("PlaceCell::cell: " + iterCell
						+ " frequency: " + frequency + ". espaciado: " + lambda
						+ ". orientation: " + orientation + ". point: "
						+ point.x + ", " + point.y + ".");

			}
		}

	}

	public static int cantActivas = GRIDS_PER_POINT;

	void doSilenceStep(Double headAndSpeed) {

		// System.err.println("HasselmoGridCellLayer::head: " +
		// headDirectionValue + ". speed: "+ speedValue);
		for (int iterCellsOrientations = 0; iterCellsOrientations < gridCells.length; iterCellsOrientations++) {
			if (gridCells[iterCellsOrientations] != null)
				gridCells[iterCellsOrientations].doStep(headAndSpeed.x,
						headAndSpeed.y);
			// normalizar
			// gridCellsActivation[iterCellsOrientations]=(gridCellsActivation[iterCellsOrientations]>DEFAULT_MIN_ACTIVATION)?1:gridCellsActivation[iterCellsOrientations]/DEFAULT_MIN_ACTIVATION;

		}

	}

	void doMaxStep(Double headAndSpeed) {

		// System.err.println("HasselmoGridCellLayer::head: " +
		// headDirectionValue + ". speed: "+ speedValue);
		for (int iterCellsOrientations = 0; iterCellsOrientations < gridCells.length; iterCellsOrientations++) {
			if (gridCells[iterCellsOrientations] != null)
				gridCellsActivation[iterCellsOrientations] = Math.max(
						gridCellsActivation[iterCellsOrientations], Math
								.abs(gridCells[iterCellsOrientations].doStep(
										headAndSpeed.x, headAndSpeed.y)));
		}

	}

	// el near viene por que promedia por el numero ideal de grillas y no por
	// las que suma :(
	void doNearAveStep(Double headAndSpeed) {
		// System.err.println("HasselmoGridCellLayer::head: " +
		// headDirectionValue + ". speed: "+ speedValue);
		for (int iterCellsOrientations = 0; iterCellsOrientations < gridCells.length; iterCellsOrientations++) {
			if (gridCells[iterCellsOrientations] != null)
				if (gridCellsActivation[iterCellsOrientations] == 0)
					gridCellsActivation[iterCellsOrientations] = Math
							.abs(gridCells[iterCellsOrientations].doStep(
									headAndSpeed.x, headAndSpeed.y))
							/ (double) GRIDS_PER_POINT;
				else
					// promedio
					gridCellsActivation[iterCellsOrientations] = gridCellsActivation[iterCellsOrientations]
							+ Math.abs(gridCells[iterCellsOrientations].doStep(
									headAndSpeed.x, headAndSpeed.y))
							/ (double) GRIDS_PER_POINT;
			// normalizar
			// gridCellsActivation[iterCellsOrientations]=(gridCellsActivation[iterCellsOrientations]>DEFAULT_MIN_ACTIVATION)?1:gridCellsActivation[iterCellsOrientations]/DEFAULT_MIN_ACTIVATION;

		}
	}

	public void simRun2() {
		double FINAL_DELTA_SPEED = 0.5; // fragmento que se deja para el final a
										// ser dividido en varios pasos
		double STEPS = 20; // cantidad de pasos en que sera dividido el
							// fragmento final de la velocidad
		// TODO: esto lo tiene que pasar alguien
		// double headDirectionValue = headDirection.get();
		// double speedValue = speed.get();
		double headDirectionValue = RobotFactory.getRobot().getHeadDirection();
		double speedValue = RobotFactory.getRobot().getSpeed();
		Double headAndSpeed = new Double(headDirectionValue, speedValue
				- FINAL_DELTA_SPEED), arista = new Double(headDirectionValue,
				FINAL_DELTA_SPEED / STEPS);

		Arrays.fill(gridCellsActivation, 0);

		boolean newActiveGrids = true;
		int previusActive = -1;
		// System.err.println("HasselmoGridCellLayer::head: " +
		// headDirectionValue + ". speed: "+ speedValue);
		if ((speedValue != 0) || firstStep) {
			doMaxStep(headAndSpeed);
			for (int iterMoves = 0; iterMoves < STEPS; iterMoves++) {
				doMaxStep(arista);
			}

			for (int iterCellsOrientations = 0; iterCellsOrientations < gridCells.length; iterCellsOrientations++) {
				if (gridCellsActivation[iterCellsOrientations] >= DEFAULT_MIN_ACTIVATION)
					cantActivas++;
			}
		}

		for (int iterCellsOrientations = 0; iterCellsOrientations < gridCells.length; iterCellsOrientations++) {
			// on/off
			// gridCellsActivation[iterCellsOrientations]=gridCellsActivation[iterCellsOrientations]>=DEFAULT_MIN_ACTIVATION?MAX_OUTPUT:0;
			// normalizar
			gridCellsActivation[iterCellsOrientations] = (gridCellsActivation[iterCellsOrientations] > DEFAULT_MAX_ACTIVATION) ? 1
					: (gridCellsActivation[iterCellsOrientations] - DEFAULT_MIN_ACTIVATION)
							/ (DEFAULT_MAX_ACTIVATION - DEFAULT_MIN_ACTIVATION);

		}
		// System.err.println("HGCL::activas POFFFF: "+ cantActivas);
		firstStep = false;

		gridCellsPositionNSL.set(gridCellsPosition);
		outputPIL.set(gridCellsActivation);
	}

	public void simRunSpeed() {
		double FINAL_DELTA_SPEED = 0.5; // fragmento que se deja para el final a
										// ser dividido en varios pasos
		double STEPS = 20; // cantidad de pasos en que sera dividido el
							// fragmento final de la velocidad
		// TODO: esto lo tiene que pasar alguien
		// double headDirectionValue = headDirection.get();
		// double speedValue = speed.get();
		double headDirectionValue = RobotFactory.getRobot().getHeadDirection();
		double speedValue = RobotFactory.getRobot().getSpeed();
		Double headAndSpeed = new Double(headDirectionValue, speedValue
				- FINAL_DELTA_SPEED), arista = new Double(headDirectionValue,
				FINAL_DELTA_SPEED / STEPS);

		Arrays.fill(gridCellsActivation, 0);

		boolean newActiveGrids = true;
		int previusActive = -1;
		// System.err.println("HasselmoGridCellLayer::head: " +
		// headDirectionValue + ". speed: "+ speedValue);
		if ((speedValue != 0) || firstStep) {
			doMaxStep(headAndSpeed);
			for (int iterMoves = 0; iterMoves < STEPS; iterMoves++) {
				doMaxStep(arista);
			}

			for (int iterCellsOrientations = 0; iterCellsOrientations < gridCells.length; iterCellsOrientations++) {
				if (gridCellsActivation[iterCellsOrientations] >= DEFAULT_MIN_ACTIVATION)
					cantActivas++;
			}
		}

		for (int iterCellsOrientations = 0; iterCellsOrientations < gridCells.length; iterCellsOrientations++) {
			// on/off
			// gridCellsActivation[iterCellsOrientations]=gridCellsActivation[iterCellsOrientations]>=DEFAULT_MIN_ACTIVATION?MAX_OUTPUT:0;
			// normalizar
			if (gridCellsActivation[iterCellsOrientations] < DEFAULT_MIN_ACTIVATION)
				gridCellsActivation[iterCellsOrientations] = 0;
			else if (gridCellsActivation[iterCellsOrientations] > DEFAULT_MAX_ACTIVATION)
				gridCellsActivation[iterCellsOrientations] = 1;
			else
				gridCellsActivation[iterCellsOrientations] = (gridCellsActivation[iterCellsOrientations] - DEFAULT_MIN_ACTIVATION)
						/ (DEFAULT_MAX_ACTIVATION - DEFAULT_MIN_ACTIVATION);
			if (gridCellsActivation[iterCellsOrientations] < 0)
				System.err.println("HGCL::ERROR!");

		}
		// System.err.println("HGCL::activas POFFFF: "+ cantActivas);
		firstStep = false;

		gridCellsPositionNSL.set(gridCellsPosition);
		outputPIL.set(gridCellsActivation);
	}

	public void simRun() {
		int LADOS_FIGURA = 4;
		double ANGLE = 360.0/((double)LADOS_FIGURA)* Math.PI / 180.0, speedFigure;
		double MAX_SPEED_FIGURE = 1;
		
		//TODO: esto lo tiene que pasar alguien
//		double headDirectionValue = headDirection.get();
//		double speedValue = speed.get();
		double headDirectionValue = RobotFactory.getRobot().getHeadDirection();
		double speedValue = RobotFactory.getRobot().getSpeed();
		Double headAndSpeed = new Double(headDirectionValue, speedValue), arista;
		if (PI_ON) {
			if (WorldGraphLayer.resetGrids)
				resetGrids();
			Arrays.fill(gridCellsActivation, 0);
			
			boolean newActiveGrids = true;
			int previusActive = -1;
			//System.err.println("HasselmoGridCellLayer::head: " + headDirectionValue + ". speed: "+ speedValue);
	//		if ((speedValue!=0)||firstStep) {
				doMaxStep(headAndSpeed);
				while (newActiveGrids) {
					cantActivas = 0;
					speedFigure = Math.random()*MAX_SPEED_FIGURE;
					arista = new Double(headDirectionValue, speedFigure);
					for (int iterMoves=0; iterMoves<LADOS_FIGURA;iterMoves++) {
						// ojo que el ángulo es absoluto por lo que voy acumulando los grados de la figura
						arista.x = arista.x+ANGLE;
						if (iterMoves!=(LADOS_FIGURA-1))
	//						doSilenceStep(arista);
							doMaxStep(arista);
						else
							doMaxStep(arista);
			//			if (cantActivas<4) {
			////				System.err.println("HGCL::activas pocas");
			//				Rat.newTrial = true;
			//			}
					}
					for (int iterCellsOrientations=0; iterCellsOrientations<gridCells.length;iterCellsOrientations++) {
						if (gridCellsActivation[iterCellsOrientations]>=DEFAULT_MIN_ACTIVATION) cantActivas++;
					}
					newActiveGrids = previusActive != cantActivas;
					previusActive = cantActivas;
	//				System.err.println("HGCL::activas: "+ cantActivas);
				}
				// cerca y ! activas :(
	//			if (cantActivas>0)
	//				System.err.println("HGCL::activas: "+ cantActivas+ ". posicion: "+RobotFactory.getRobot().getGlobalCoodinate().x+", "+RobotFactory.getRobot().getGlobalCoodinate().y+".");
				
	//		}
			updateActivationNumbers();
	
			for (int iterCellsOrientations=0; iterCellsOrientations<gridCells.length;iterCellsOrientations++) {
				// on/off 
	//			gridCellsActivation[iterCellsOrientations]=gridCellsActivation[iterCellsOrientations]>=DEFAULT_MIN_ACTIVATION?MAX_OUTPUT:0;
				// normalizar
				if (gridCellsActivation[iterCellsOrientations]<DEFAULT_MIN_ACTIVATION)gridCellsActivation[iterCellsOrientations] = 0;
				else  if (gridCellsActivation[iterCellsOrientations]>DEFAULT_MAX_ACTIVATION) gridCellsActivation[iterCellsOrientations] = 1;
				else gridCellsActivation[iterCellsOrientations]=(gridCellsActivation[iterCellsOrientations]-DEFAULT_MIN_ACTIVATION)/(DEFAULT_MAX_ACTIVATION-DEFAULT_MIN_ACTIVATION);
				if (gridCellsActivation[iterCellsOrientations]<0) System.err.println("HGCL::ERROR!");
	
			}
//			System.err.println("HGCL::activas POFFFF: "+ cantActivas);
			firstStep=false;
	
			} // fib PI_ON
		//cantActivas=GRIDS_PER_POINT;
		gridCellsPositionNSL.set(gridCellsPosition);
		outputPIL.set(gridCellsActivation);
	}

	/**
	 * 
	 */
	private void resetGrids() {
		int cantResets =0;
		for (int iterCells = 0; iterCells < gridCells.length; iterCells++) {
			if (gridCellsActivation[iterCells]>0) {
				gridCells[iterCells].reset();
				cantResets++;
			}
		}
//		System.err.println("HGCL::reseteando "+cantResets+"celda(s).");
	}

	/**
	 * 
	 */
	private void updateActivationNumbers() {
		Double robotPosition = RobotFactory.getRobot().getGlobalCoodinate();
		for (int iterCells = 0; iterCells < gridCellsActivation.length; iterCells++) {
			if ((centerCoords[iterCells] != null)
					&& (robotPosition.distance(centerCoords[iterCells]) < 0.05)) {
				if (gridCellsActivation[iterCells] >= DEFAULT_MIN_ACTIVATION)
					numberActivation[iterCells]++;
				else
					numberNotActivation[iterCells]++;
//				System.err
//						.println("HGCL::cell: "
//								+ iterCells
//								+ "relation act: "
//								+ numberActivation[iterCells]
//								+ "/"
//								+ (numberActivation[iterCells] + numberNotActivation[iterCells]));
			}
		}

	}

	// calcula la frecuencia según la distancia desde el área postrhynal
	public static double calculateFrequency(double z) {
		return 1 / (0.094 * (z + 3.9) - 0.25);
	}

	Random rand = new Random();

	double random(double ini, double fin) {
		return ini + rand.nextDouble() * (fin - ini);
	}

}
