/*
 Alejandra Barrera
 Versin 1
 Fecha: 5 de abril de 2005
 
 Gonzalo Tejera
 Versin 1.1
 Fecha: 30 de julio de 2010
 Agregado de comentarios, varios megtodos se pasaron a private y dudas sobre if innecesarios.

 Gonzalo Tejera
 Versin 2.0
 Fecha: 10 de agosto de 2010
 Se implementa Q-Learning para el ajuste de la recompensa usando los pesos e de las aristas.
 */
import neural.HDGridCell;
import nslj.src.lang.*;

import graph.Edge;
import graph.NodeMap;
import graph.NodePattern;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;

import javax.vecmath.Point2d;

import robot.IRobot;
import robot.RobotFactory;
import support.Configuration;
import support.Loger;
import support.LogerPlain;
import support.Utiles;

public abstract class WorldGraphLayer extends NslModule {
	public static final double PATTERN_SD = Configuration
			.getDouble("WorldGraphLayer.UMBRAL_PATTERN_SD");
	public static final double NODE_SD = Configuration
			.getDouble("WorldGraphLayer.UMBRAL_NODE_SD");
	public static final double GAMMA = Configuration
			.getDouble("WorldGraphLayer.GAMMA");
	public static final double ALFA = Configuration
			.getDouble("WorldGraphLayer.ALFA");
	public static final double K_DILEMA = Configuration
			.getDouble("WorldGraphLayer.K_DILEMA");
	public static final double CURIOSITY_NEW_NODE = Configuration
			.getDouble("WorldGraphLayer.CURIOSITY_NEW_NODE");
	public static final double CURIOSITY_NEW_AFF = Configuration
			.getDouble("WorldGraphLayer.CURIOSITY_NEW_AFF");
	private final double RPS_HEIGHT = Configuration
			.getDouble("MotivationalSchema.RPS_HEIGHT");
	private final static int SPIKES = PathIntegrationFeatureDetectorLayer.SPIKES;
	final double PESO_A = Configuration
			.getDouble("WorldGraphLayer.PESO_PASADO_PROMEDIO_PATTERNS");
	public static final double SMALL_Q_VALUE = Configuration
			.getDouble("WorldGraphLayer.SMALL_Q_VALUE"); // si el valor de Q es meno;
	private static final double DECREMENT_FACTOR = Configuration
			.getDouble("WorldGraphLayer.Q_DECREMENT_FACTOR"); //0.99; // mas grande, mas demora en desaparecer el modelo

	public NslDinInt0 currentHeadAngleRat;
	public NslDinDouble1 pcl1dim;
	public NslDinDouble0 reward;
	public NslDinDouble0 distFood;
	public NslDinInt0 ActionTaken;
	public NslDoutDouble1 expCycling;
	public NslDoutDouble1 curiosityCycling;

	private int patternID;
	private int node_id_WG;
	private double number_of_steps;
	private double edge_to_direction[];
	private int previous_direction;
	public static int idPreviousNode = -1;

	public static int currentNode;
	private NodePattern lastPattern;

	// lista de nodos del grafo y lista de patrones para cada nodo
	public static LinkedList<NodeMap> map_list;
	private LinkedList<NodePattern> pattern_list;
	// lista ordenada de nodos visitados recientemente
	public static LinkedList<Integer> nodosVisitados = new LinkedList<Integer>();

	private Frame WGFrame;

	private boolean inicio;

	private double expCyclingAux[];
	private double flagCyclingD[];

	public static boolean explotando;
	Random rand = new Random();
	protected int NODE_HISTORY_SIZE;
	private NodePattern patronMismasCoordenadas;
	private boolean mergeDone = false;
	private long iteracion=0;
	public static boolean updateSchemas;
	public static boolean cambioNodoActual;

	public static int idNodeWinner = -1;
	public static int MAX_NODOS = 69; // 69 para morris

	private static final double DISTANCIA_ENTRE_NODOS = 0.06; // Distancia entre
																// nodos para
																// determinar si
																// en
																// coordenadas
																// globales
																// coinciden
	private static final double RUIDO = 0.05*(double)SPIKES;// UMBRAL_SD/(double)NodePattern.CANT_NEURONAS_DETECTORAS_PATRONES;
	private static final long TTL = 1000 * (long) Configuration
			.getDouble("WorldGraphLayer.PATTERN_TTL");
	private static final double ALFA_LEARN = 0.2;
	private static final double UMBRAL_CS = 0.4*(double)SPIKES; // umbral de similitud entre celdas del un patron

	public WorldGraphLayer(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		currentHeadAngleRat = new NslDinInt0("currentHeadAngleRat", this);
		pcl1dim = new NslDinDouble1("pcl1dim", this,
				NodePattern.CANT_NEURONAS_DETECTORAS_PATRONES);
		distFood = new NslDinDouble0("distFood", this);
		reward = new NslDinDouble0("reward", this);
		ActionTaken = new NslDinInt0("ActionTaken", this);
		expCycling = new NslDoutDouble1("expCycling", this, 8);
		curiosityCycling = new NslDoutDouble1("curiosityCycling", this, 8);
		flagCyclingD = new double[IRobot.CANT_ACCIONES];
		expCyclingAux = new double[IRobot.CANT_ACCIONES];
		inicio = true;
		patternID = 0;
		node_id_WG = 0;
		number_of_steps = 0.0D;
		edge_to_direction = new double[IRobot.CANT_ACCIONES];
		previous_direction = -1;
		currentNode = -1;
		pattern_list = new LinkedList<NodePattern>();
		map_list = new LinkedList<NodeMap>();
		for (int i = 0; i < 8; i++)
			edge_to_direction[i] = 0.0D;

		WGFrame = new WorldGraphFrame();
		WGFrame.setVisible(true);
		
		assert SMALL_Q_VALUE<=CURIOSITY_NEW_NODE : "La curiosidad de realizar nuevos caminos no debe ser mayor al menor valor de Q";
	}

	private int lastGridAct =0;
	private int currentDirection;
	public static boolean resetGrids=false;
	public static NodePattern mostSimilarPAttern;
	private LogerPlain log = new LogerPlain("errorCloseLoop-SE."+Utiles.SPEED_ERROR+".AE."+Utiles.ANGLE_ERROR+".DR."+HDGridCell.DECAY_RESET);
	private int numbreMergeErrors;
	private int lastLoopError =0;
	
	public void simRun() {
		currentDirection = Utiles.gradosAbsolute2Acccion(currentHeadAngleRat.get());
		boolean newNode;
		double[] inputPattern=pcl1dim.get();
		
		//leaveMaxActiveCell(inputPattern);
		// los patrones se almacenan multiplicados por SPIKES por lo que al
		// principio se realiza esa operacion
		for (int k1 = 0; k1 < inputPattern.length; k1++)
			inputPattern[k1] = inputPattern[k1] * (double) SPIKES;
		
		Arrays.fill(flagCyclingD, 1);
//		if (mostSimilarPAttern!=null)
//			System.out.print("WorldGraphLayer::no null");

		for (int iterExp=0;iterExp<expCyclingAux.length;iterExp++)
			expCyclingAux[iterExp] = rand.nextDouble() * RPS_HEIGHT;
		
		if (HasselmoGridCellLayer.cantActivas>=HasselmoGridCellLayer.GRIDS_PER_POINT) {
		
		iteracion++;
		if (Rat.newTrial||(lastGridAct < HasselmoGridCellLayer.GRIDS_PER_POINT)) {
			nodosVisitados.clear();
			idPreviousNode = -1;
			currentNode = -1;
		}

		// eliminarPatronesViejos();

		//.err.println("WGL::current direction: " + currentDirection);
		newNode = patternActivation(inputPattern);
		map_activation(Rat.newTrial||(lastGridAct ==0), newNode, Drive.ateFood);

		int ultimo = nodosVisitados.size() > 0 ? nodosVisitados.getLast() : -1;
		cambioNodoActual = currentNode != ultimo;
		if (cambioNodoActual&&!mergeDone) {
			// actualizo historial de nodos visitados
			nodosVisitados.add(currentNode);
			if (nodosVisitados.size() > NODE_HISTORY_SIZE)
				nodosVisitados.removeFirst();
			// ejecuto regla de aprendizaje solo si cambié de nodo y ...
			// if ((!Rat.habituation)
		}

		updateSchemas = mergeDone || cambioNodoActual; 
		if (updateSchemas) {
			mergeDone = false;
			if (nodosVisitados.size() == NODE_HISTORY_SIZE)
				reinforce();
			setExpREpsilon(currentDirection, expCyclingAux);
		}
		int currentLoopError = (numberNotMerged()+numbreMergeErrors);
		log.writeln(""+iteracion+log.SEPARATOR+currentLoopError); // cheequeo errores de close loop
		if (currentLoopError!=lastLoopError)
			System.out.println("WGL::cantidad de errores al cerrar ciclo: " + currentLoopError);

		lastLoopError = currentLoopError;

		setCuriosity(currentDirection, flagCyclingD);
		previous_direction = currentDirection;

		// if (!Rat.newTrial && (idPreviousNode != -1) && (CurrentWGNode != -1))
		// {
		// Setea la curiosidad y la espectativa

		}
		curiosityCycling.set(flagCyclingD);
		expCycling.set(expCyclingAux);
		mostSimilarPAttern = getNearestPattern(inputPattern, 1.5 * PATTERN_SD);
		resetGrids = ((mostSimilarPAttern!=null)&&(distanciaEuclidiana(inputPattern,mostSimilarPAttern.place_IDs)<PATTERN_SD));
		WGFrame.repaint(); // para que pinte nodo winner y nodo seleccionado
		lastGridAct = HasselmoGridCellLayer.cantActivas;
	}
	
	/**
	 * @param inputPattern
	 */
	private void leaveMaxActiveCell(double[] inputPattern) {
		int iterCeldas;
		int posMaxCell = 0;
		
		for (iterCeldas=1; iterCeldas<inputPattern.length;iterCeldas++) {
			if (inputPattern[iterCeldas]>inputPattern[posMaxCell]) {
				inputPattern[posMaxCell] = 0;
				posMaxCell = iterCeldas;
			} else 
				inputPattern[iterCeldas] = 0;
	
		}
		System.err.println("WGL::celda ganadora: " + posMaxCell);
	}

	// aplico la regla de aprendizaje
	protected abstract void reinforce();

	protected static double maxQ(int idNode) {
		NodeMap actualNode;
		double maxActual = Double.MIN_VALUE;

		ListIterator<NodeMap> mapIterator = map_list.listIterator();
		while (mapIterator.hasNext()) {
			actualNode = mapIterator.next();
			if (actualNode.id == idNode) {
				for (int direction = 0; direction < IRobot.CANT_ACCIONES; direction++) {
					if (maxActual < actualNode.e[direction])
						maxActual = actualNode.e[direction];
				}
				break;
			}
		}
		return (maxActual == Double.MIN_VALUE) ? 0 : maxActual;
	}

	// coloca un 1 en las direcciones no vistas por la rata aun.
	private void setCuriosity(int currentDirection, double[] flagCycling) {
		ListIterator<NodePattern> patIterator = pattern_list.listIterator();
		NodePattern patNode;
		NodeMap mapNode = getNodeMap(nodosVisitados.getLast());
		Edge edge;
		// inicializo todas las direcciones como posibles a girar, menos la
		// direccion antual
		Arrays.fill(flagCycling, CURIOSITY_NEW_NODE);
		// flagCycling[currentDirection] = 0;

		// flagCycling[7] = 0; // elimino el giro 180

		// para todas las si existe un patron seteo la curiosidad en
		// CURIOSITY_NEW_AFF
		while (patIterator.hasNext()) {
			patNode = patIterator.next();
			if (nodosVisitados.getLast() == patNode.mapNodeID)
				flagCycling[patNode.dir] = CURIOSITY_NEW_AFF;
		}

		/*
		 * para el nodo actuak seteo en crioidad en las direcciones recorridas
		 */
		for (int iterEdge = 0; iterEdge < mapNode.pointerWGtoWG.size(); iterEdge++) {
			edge = mapNode.pointerWGtoWG.get(iterEdge);
			flagCycling[edge.direction] = 0;
		}
	}

	public void decReinforce() {
		NodeMap mapNode = null;
		Edge edge;
		for (ListIterator<NodeMap> mapIterator = map_list.listIterator(); mapIterator
				.hasNext();) {
			mapNode = (NodeMap) mapIterator.next();
			for (int iterEdge = 0; iterEdge < mapNode.pointerWGtoWG.size(); iterEdge++) {
				edge = mapNode.pointerWGtoWG.get(iterEdge);
				mapNode.e[edge.direction] *= DECREMENT_FACTOR;
				if (mapNode.e[edge.direction] < SMALL_Q_VALUE) // si el valor de q es muy chico lo cereo
					mapNode.e[edge.direction] = 0;

			}
		}
	}

	// lleva adelante la solución al dilema de exploración-explotación con un umbral
	private void setExpREpsilon(int currentDirection, double[] expReward) {
		double epsilon = Rat.simItem.getExploration();
		
		NodeMap mapNode = getNodeMap(nodosVisitados.getLast());
		Arrays.fill(expReward, 0);
		if (DECREMENT_FACTOR>0)
			decReinforce(); // desaprendizaje
		// si el umbral de hambre es mayor que un numero aleatorio y no estoy en etapa de habituacion => debería explorar
		boolean explotar = (rand.nextDouble() < epsilon) && !Rat.habituation;
		double maxQ = Double.MIN_VALUE;
		Edge edge=null; 
		// busco el maximo Q
		if (explotar) {
			for (int iterEdge = 0; iterEdge < mapNode.pointerWGtoWG.size(); iterEdge++) {
				edge = mapNode.pointerWGtoWG.get(iterEdge);
				expReward[edge.direction] = mapNode.e[edge.direction]+rand.nextDouble() * SMALL_Q_VALUE; // el rand es por si hay dos iguales, particularmente en cero
				if (mapNode.e[edge.direction] > maxQ)
					maxQ = mapNode.e[edge.direction];
			}
	
		}
		if (!explotar || (maxQ == Double.MIN_VALUE)||mapNode.pointerWGtoWG.size()==0) { // si estoy explorando
															// , no encontré un
															// maximo Q o el nodo actual no tiene aristas
			explotando=false;
			for (int iterEdge = 0; iterEdge < mapNode.pointerWGtoWG.size(); iterEdge++) {
				edge = mapNode.pointerWGtoWG.get(iterEdge);
				expReward[edge.direction] = rand.nextDouble() * RPS_HEIGHT;
			}
			//System.out.print("WorldGraphLayer::Q EXPLORANDO. epsilon: " + epsilon+". MaxQ: " + maxQ);
		} else {
			explotando=true;
			//System.out.print("WorldGraphLayer::Q exploTando. epsilon: " + epsilon+". MaxQ: " + maxQ);
		}
	}
	
	private void setExpRDrive(int currentDirection, double[] expReward) {
		NodeMap mapNode = getNodeMap(nodosVisitados.getLast());
		Arrays.fill(expReward, 0);
		if (DECREMENT_FACTOR>0)
			decReinforce(); // desaprendizaje
		// si el umbral de hambre es mayor que un numero aleatorio y no estoy en etapa de habituacion => debería explorar
		boolean explotar = (rand.nextDouble() < reward.get()) && !Rat.habituation;
		double maxQ = Double.MIN_VALUE;
		Edge edge=null; 
		// busco el maximo Q
		if (explotar) {
			for (int iterEdge = 0; iterEdge < mapNode.pointerWGtoWG.size(); iterEdge++) {
				edge = mapNode.pointerWGtoWG.get(iterEdge);
				expReward[edge.direction] = mapNode.e[edge.direction]+rand.nextDouble() * SMALL_Q_VALUE; // el rand es por si hay dos iguales, particularmente en cero
				if (mapNode.e[edge.direction] > maxQ)
					maxQ = mapNode.e[edge.direction];
			}
	
		}
		if (!explotar || (maxQ == Double.MIN_VALUE)||mapNode.pointerWGtoWG.size()==0) { // si estoy explorando
															// , no encontré un
															// maximo Q o el nodo actual no tiene aristas
			explotando=false;
			for (int iterEdge = 0; iterEdge < mapNode.pointerWGtoWG.size(); iterEdge++) {
				edge = mapNode.pointerWGtoWG.get(iterEdge);
				expReward[edge.direction] = rand.nextDouble() * RPS_HEIGHT;
			}
			System.out.print("WorldGraphLayer::Q EXPLORANDO. epsilon: " + reward.get()+". MaxQ: " + maxQ);
		} else {
			explotando=true;
			System.out.print("WorldGraphLayer::Q exploTando. epsilon: " + reward.get()+". MaxQ: " + maxQ);
		}
		//if(mapNode.pointerWGtoWG.size()>8).err.print("WorldGraphLayer::Muchas aristas");
	}

	private void setExpRPie(int currentDirection, double[] expReward) {
		double sumaExpExp=0; // almacena la suma exponecial de las expectativas
		double maxQ = Double.MIN_VALUE;
		int dirMaxQ = -1;
		Edge edge=null;
		NodeMap mapNode = getNodeMap(nodosVisitados.getLast());

		Arrays.fill(expReward, 0);
		if (DECREMENT_FACTOR>0)
			decReinforce(); // desaprendizaje
		// busco el maximo Q y calculo la suma exponencial de los Q para el nodo actual
		for (int iterEdge = 0; iterEdge < mapNode.pointerWGtoWG.size(); iterEdge++) {
			edge = mapNode.pointerWGtoWG.get(iterEdge);
			if (mapNode.e[edge.direction] > maxQ) {
				dirMaxQ = edge.direction;
				maxQ = mapNode.e[edge.direction];
			}
			sumaExpExp=sumaExpExp+Math.pow(K_DILEMA, mapNode.e[edge.direction]);
		}

		// implemento dilema segun torta proporcional al refuerzo de las transiciones actuales
		double sorteoQExp = rand.nextDouble();
		double acumuloQ = 0;
		int iterEdge=0;
		for (iterEdge = 0; iterEdge < mapNode.pointerWGtoWG.size(); iterEdge++) {
			edge = mapNode.pointerWGtoWG.get(iterEdge);
			acumuloQ = acumuloQ + Math.pow(K_DILEMA, mapNode.e[edge.direction])/sumaExpExp;
			if (acumuloQ >= sorteoQExp)
				break;
		}
		if (edge != null)
			expReward[edge.direction] = CURIOSITY_NEW_AFF/2.0; // pongo un valor positivo en esa dirección más chico que los valores posibles de curiosidad
		
		if ((edge!=null)&&(edge.direction==dirMaxQ)) { // si estoy explotando
			explotando=true;
			System.out.print("WorldGraphLayer::Q exploTando. MaxQ: " + maxQ);
				if (maxQ==Double.MIN_VALUE) {
					int foo=mapNode.pointerWGtoWG.size();
					System.err.println("WGL:: "+edge.points_to_node);
					foo=0;
				}
		} else {
			explotando=false;
			System.out.print("WorldGraphLayer::Q EXPLORANDO.");
		}
	}
	
	private NodePattern getNearestPattern(double[] patron, double umbral) {
		double dist, distMin = Double.MAX_VALUE;
		NodePattern patNode = null, patWinner = null, patCercanoCoord = null;
		ListIterator<NodeMap> mapIterator;
		NodeMap mapNode = null, nodeMin;

		for (ListIterator<NodePattern> patIterator = pattern_list
				.listIterator(); patIterator.hasNext();) {
			patNode = patIterator.next();

			if ((patNode.dir == currentDirection)) { // || Rat.newTrial) {
				dist = distanciaEuclidiana(patNode.place_IDs, patron);
				NodeMap node = getNodeMap(patNode.mapNodeID);
				if (dist < distMin) {
					distMin = dist;
					patWinner = patNode;
				}
			}
		}
		// .err.println("WDL:: distancia winner: " + distMin);
		if (distMin > umbral)
			return null;
		else
			return patWinner;

		// .err.println("WGL::SD: " + distMin + ". Dir: " + currentDir
		// + " X=" + globalCood.x + " Y=" + globalCood.y + " Winner:"
		// + winner + " Candidatos: " + candidatosSD
		// + " Cantidad cercanos: " + cantidadNodosCercanos);
	}

	// devuelve si existe, el patron de ese nodo para la dirección dada
	private NodePattern getPatternInDir(NodeMap mapNode, int direction) {
		ListIterator<NodeMap> mapIterator;
		NodePattern patNode, result = null;

		for (ListIterator<NodePattern> patIterator = pattern_list
				.listIterator(); patIterator.hasNext();) {
			patNode = patIterator.next();

			if ((mapNode.id == patNode.mapNodeID) && (patNode.dir == direction)) {
				result = patNode;
				break;
			}
		}

		return result;
	}
	
	/*
	 * determina cual es el patron activo, setea lastPattern y decide cuando hacer
	 * el merge de dos nodos currentDir: direccion de la cabeza de la rata en el
	 * rango salidaPattern: patron de activacion de la PCL combinado de la DLR y
	 * LPS retorna si es necesario crear o no un nuevo nodo
	 */
	private boolean patternActivation(double inputPattern[]) {
		boolean newNode = false;
		NodePattern patWinner = null;

		patWinner = getNearestPattern(inputPattern, PATTERN_SD);
		// si no encuentro un patron cercano
		// crea un patron y lo agrega a la lista de patrones
		if (patWinner == null) {
			NodePattern patNode = new NodePattern(patternID, currentDirection);
			// seteo el patron con el patron de entrada actual
			for (int k1 = 0; k1 < inputPattern.length; k1++) {
				patNode.place_IDs[k1] = inputPattern[k1];
			}
			// agrego el nuevo patron a la lista de patrones y actualizo el
			// contador global de identificadores de patrons
			pattern_list.add(patNode);
			patternID++;
			lastPattern = patNode;
			// si no me moví de lugar (estoy girando) y tengo al menos un nodo
			// en la lista de nodos visitados
			if (currentDirection != previous_direction && nodosVisitados.size() > 0) {
				// asocio al nuevo patron el nodo corriente
				patNode.mapNodeID = nodosVisitados.getLast();
			} else
				// solicito la creacion de un nuevo nodo
				newNode = true;
		} else {
			// si encontre un ganador
			lastPattern = patWinner;

			// verifico si es necesario hacer el merge de dos nodos
//			if (!(Rat.newTrial||(lastGridAct ==0))) {
			if ((!Rat.newTrial)&&(!nodosVisitados.isEmpty())) {
				int idUltimoNodo = nodosVisitados.getLast();
				// si cambio la dirección y el patron ganador no es del nodo anterior
				if ((patWinner.mapNodeID != idUltimoNodo)
						&& (currentDirection != previous_direction)) {

					NodeMap nodeWinner = getNodeMap(patWinner.mapNodeID);
					NodeMap nodeLast = getNodeMap(idUltimoNodo);
					double distNodes = patternNodeDistance(nodeWinner,nodeLast);
//					if (distNodes<NODE_SD) {
						mergeNodes(nodeWinner, nodeLast);
						mergeDone=true;
						nodosVisitados.removeLast();
						nodosVisitados.addLast(nodeWinner.id);
						cambioNodoActual = true;
						currentNode = nodeWinner.id;
				
						double distanciaEntreNodos = nodeLast.getGlobalPoint()
								.distance(nodeWinner.getGlobalPoint());
						if (distanciaEntreNodos > DISTANCIA_ENTRE_NODOS) {
							numbreMergeErrors++;
							System.out
									.println("WGL::merge error ********************************* iteración: "+ iteracion);
							System.out.println("WGL::cantidad de nodos: " + map_list.size()
									+ ". cantidad de patrones: " + pattern_list.size());
							System.out.println("WGL::node position: " +nodeWinner.getGlobalPoint().x + ", "+nodeWinner.getGlobalPoint().y+".");
							
							}
//					}
				} else {
					promediar(patWinner.place_IDs, inputPattern);
					patWinner.updatePattern();
				}
			}
		}
		if (Rat.newTrial||(lastGridAct ==0))
			System.err.println("WGL::cantidad de nodos: " + map_list.size()
					+ ". cantidad de patrones: " + pattern_list.size());
		return newNode;
	}

	/*
	 * calculo el grado de similutud de dos nodos para lo cual compara todos los
	 * patrones para misma direccion y luego promedia
	 */
	private double patternNodeDistance(NodeMap a, NodeMap b) {
		ListIterator<NodePattern> patIterator;
		double result = 0;
		int patronesSumados = 0;
		NodePattern patNodeIter;
		// arreglos para almacenar los patrones de a y b en todas las
		// direcciones
		NodePattern[] patronesA = new NodePattern[IRobot.CANT_ACCIONES];
		NodePattern[] patronesB = new NodePattern[IRobot.CANT_ACCIONES];

		// recorro todos los patrones y los clasifico por direccion para los
		// nodos a y b
		for (patIterator = pattern_list.listIterator(); patIterator.hasNext();) {
			patNodeIter = patIterator.next();
			if (patNodeIter.mapNodeID == a.id)
				patronesA[patNodeIter.dir] = patNodeIter;
			else if (patNodeIter.mapNodeID == b.id)
				patronesB[patNodeIter.dir] = patNodeIter;
		}

		// para las direcciones en que ambos nodos tienen patron, actualizo el
		// promedio para la similitud
		for (int iterDir = 0; iterDir < IRobot.CANT_ACCIONES; iterDir++) {
			if ((patronesA[iterDir] != null) && (patronesB[iterDir] != null)) {
				result = result
						+ distanciaEuclidiana(patronesA[iterDir].place_IDs,
								patronesB[iterDir].place_IDs);
				patronesSumados++;
			}

		}
		if (patronesSumados > 0)
			System.err.println("WGL::SD entre nodos: "+result / (double) patronesSumados + ". sumados: " + patronesSumados);

		// return
		// patronesSumados>0?result/(double)patronesSumados:Double.MAX_VALUE;
		return patronesSumados > 0 ? result / (double) patronesSumados : Double.MAX_VALUE;

	}

	// promedia patrones con igual identificador de nodo y direccion, y
	// actualiza lastPattern si corresponde
	private void combinarPatrones(NodeMap mapNodeI, NodeMap mapNodeJ) {
		ListIterator<NodePattern> patIteratorJ;
		NodePattern patNodeI = null;
		NodePattern patNodeJ = null;
		LinkedList<NodePattern> nuevaLista = new LinkedList<NodePattern>();
		LinkedList<NodePattern> toRemove = new LinkedList<NodePattern>();
		int patronesBorrados = 0;

		// paso todos los patrones asociados al nodo j a i
		cambiarIDNodo(mapNodeI.id,mapNodeJ.id);

		// busco patrones con igual identificador y dirección, si lo encuentra
		// se promedian y elimina uno
		while (!pattern_list.isEmpty()) {
			patNodeI = pattern_list.remove();
			if (patNodeI.mapNodeID == mapNodeI.id) {
				for (patIteratorJ = pattern_list.listIterator(); patIteratorJ
						.hasNext();) {
					patNodeJ = patIteratorJ.next();
					if ((patNodeJ.mapNodeID == mapNodeI.id)
							&& (patNodeI.dir == patNodeJ.dir)) {
						if (patNodeJ.id == lastPattern.id)
							lastPattern = patNodeI;
						toRemove.add(patNodeJ);
					}
				}
			}
			for (patIteratorJ = toRemove.listIterator(); patIteratorJ.hasNext();) {
				patNodeJ = patIteratorJ.next();
				pattern_list.remove(patNodeJ);
				//mostActivePattern(patNodeI, patNodeJ);
				//selectMaxActiveCells(patNodeI.place_IDs, patNodeJ.place_IDs);
				//promediar(patNodeI.place_IDs, patNodeJ.place_IDs);
				olderPattern(patNodeI, patNodeJ);
			}

			patronesBorrados = patronesBorrados + toRemove.size();
			toRemove.clear();

			nuevaLista.add(patNodeI);
		}
		if (patronesBorrados > 0) {
			System.err.println("WGL::Borrando " + patronesBorrados
					+ " patron(es) repetido(s)");
		}
		pattern_list = nuevaLista;
	}

	/**
	 * @param patNodeI
	 * @param patNodeJ
	 */
	private void mostActivePattern(NodePattern patNodeI, NodePattern patNodeJ) {
		double activationNodeI = activation(patNodeI.place_IDs);
		double activationNodeJ = activation(patNodeJ.place_IDs);
		
		if (activationNodeJ>activationNodeI)
			patNodeI.place_IDs = patNodeJ.place_IDs;
	}

	/**
	 * @param place_IDs
	 * @return
	 */
	private double activation(double[] place_IDs) {
		double result=0;
		
		for (int iterPat=0; iterPat<place_IDs.length;iterPat++)
			result = result + place_IDs[iterPat];
		
		return result;
	}

	/**
	 * @param patNodeI
	 * @param patNodeJ
	 */
	private void newerPattern(NodePattern patNodeI, NodePattern patNodeJ) {
		if (patNodeJ.getUpdateTimeStamp()>patNodeI.getUpdateTimeStamp()) // si el patron J es mas nuevo que el I me quedo con el J
			patNodeI.place_IDs = patNodeJ.place_IDs;
	}
	
	/**
	 * @param patNodeI
	 * @param patNodeJ
	 */
	private void olderPattern(NodePattern patNodeI, NodePattern patNodeJ) {
		if (patNodeJ.getUpdateTimeStamp()<patNodeI.getUpdateTimeStamp()) // si el patron J es mas nuevo que el I me quedo con el J
			patNodeI.place_IDs = patNodeJ.place_IDs;
	}

	private void cambiarIDNodo(int i, int j) {
		ListIterator<NodePattern> patIteratorJ;
		NodePattern patNodeJ = null;

		// paso todos los patrones asociados al nodo j a i
		for (patIteratorJ = pattern_list.listIterator(); patIteratorJ.hasNext();) {
			patNodeJ = patIteratorJ.next();
			if (patNodeJ.mapNodeID == j)
				patNodeJ.mapNodeID = i;
		}
	}

	int numberNotMerged() {
		ListIterator<NodeMap> mapIterator;
		Iterator<Integer> counterIterator;
		int result = 0;
		
		Hashtable<java.awt.geom.Point2D.Double, Integer> positionAndNumber = new Hashtable<java.awt.geom.Point2D.Double, Integer>();
		NodeMap mapNode;
		java.awt.geom.Point2D.Double nodePostition;
		Integer counter;
		for (mapIterator = map_list.listIterator(); mapIterator.hasNext();) {
			mapNode = mapIterator.next();
			if (mapNode.pointerWGtoWG.size()>0) {
				nodePostition = new java.awt.geom.Point2D.Double(Math.round(mapNode.getGlobalPoint().x*10),Math.round(mapNode.getGlobalPoint().y*10)); 
				counter = positionAndNumber.get(nodePostition);
				if (counter==null)
					positionAndNumber.put(nodePostition, 0);
				else {
					counter++;
					positionAndNumber.put(nodePostition, counter);
					//System.out.println("WGL::BIP BIP "+ counter);
				}
			}
		}

		for (counterIterator = positionAndNumber.values().iterator(); counterIterator.hasNext();) {
			counter = counterIterator.next();
//			System.out.println("WGL::BIP BIP "+ counter);
			result = result + counter;
		}
//		if (result>0)
//			System.out.println("WGL::cantidad de errores al cerrar ciclo: " + result);
		return result;
	}

	private void mergeNodes(NodeMap mapNodeI, NodeMap mapNodeJ) {
		ListIterator<NodeMap> mapIterator;
		NodeMap mapNodeOtro;
		
		combinarPatrones(mapNodeI, mapNodeJ);
		// ahora permito tener varios patrones en una misma dirección para intentar resolver el tema de las grid que no disparan siempre.
		// pero al menos debo ajustar los ids de nodo
		//cambiarIDNodo(mapNodeI.id, mapNodeJ.id);
		// si el nodo corriente es el seleccionado para eliminar cambio el id al no eliminado en el merge
		if (currentNode == mapNodeJ.id) {
			currentNode = mapNodeI.id;
		}

		// para las aristas del nodo i busca si una apunta a j y la elimina
		for (int k = 0; k < mapNodeI.pointerWGtoWG.size(); k++) {
			Edge edge = (Edge) mapNodeI.pointerWGtoWG.get(k);
			if (edge.points_to_node == mapNodeJ.id) {
				// elimina la arista i-j
				mapNodeI.pointerWGtoWG.remove(k);
				break;
			}
		}

		boolean exitsEdge;
		// recorre las arista de j para agregarlas al nodo i
		for (int l = 0; l < mapNodeJ.pointerWGtoWG.size(); l++) {
			Edge edgeJ = mapNodeJ.pointerWGtoWG.get(l);
			// si la arista actual apunta a un nodo diferente de i
			if (edgeJ.points_to_node != mapNodeI.id) {
				exitsEdge = false;
				for (int i1 = 0; i1 < mapNodeI.pointerWGtoWG.size(); i1++) {
					Edge edgeI = (Edge) mapNodeI.pointerWGtoWG.get(i1);
					// si alguna arista de i apunta al mismo nodo
					if (edgeI.points_to_node == edgeJ.points_to_node) {
						// se queda con el valor de la arista con mayor de Q,
						// siendo la primera conservada en
						// el modelo y la segunda eliminada
						mapNodeI.e[edgeI.direction] = Math.max(
								mapNodeI.e[edgeI.direction],
								mapNodeJ.e[edgeJ.direction]);
						exitsEdge = true;
						break;
					}
				}
				if (!exitsEdge) { // si no habia en i una arista que apunte a ese nodo
					// lo agrego en i
					mapNodeI.add_edge(edgeJ.direction, edgeJ.points_to_node,
							edgeJ.steps, edgeJ.trace);
					//.err.println("WGL::agregando arista en merge");
				}
			}
		}
		/* mergeo a I las aristas de otros nodos que apuntan a J 
		 / para cada nodo del mapa distinto de i busco si alguna arista apunta
		 / al ex j y tomo la accion correspondiente
		*/
		Edge aristaOtroAI;
		Edge aristaOtroAJ = null; // arista que apunta a J de otro nodo
		for (mapIterator = map_list.listIterator(); mapIterator.hasNext();) {
			mapNodeOtro = mapIterator.next();
			boolean otroTieneAristaAJ = false;
			boolean otroTieneAristaAI = false;
			// si el nodo no es ni J ni I busco una arista para mergear
			if ((mapNodeI.id != mapNodeOtro.id) && (mapNodeJ.id != mapNodeOtro.id)){
				for (int j1 = 0; j1 < mapNodeOtro.pointerWGtoWG.size(); j1++) {
					aristaOtroAJ = (Edge) mapNodeOtro.pointerWGtoWG.get(j1);
					if (aristaOtroAJ.points_to_node == mapNodeJ.id) {
						otroTieneAristaAJ = true;
						break;
					}
				}
				// hay alguna arista de otro nodo (distinto a i) que apunta al
				// nodo j recien elim	inado
				if (otroTieneAristaAJ) {
					for (int k1 = 0; k1 < mapNodeOtro.pointerWGtoWG.size(); k1++) {
						aristaOtroAI = (Edge) mapNodeOtro.pointerWGtoWG.get(k1);
						if (aristaOtroAI.points_to_node == mapNodeI.id) {
							otroTieneAristaAI = true;
							break;
						}
					}

					if (otroTieneAristaAI) {// si hay un nodo que tenía una
											// arista a I y otra a J, promedio
											// refuerzo y elimino la
											// que apuntaba a J
						mapNodeOtro.e[aristaOtroAJ.direction] = Math.max(
								mapNodeI.e[aristaOtroAJ.direction],
								mapNodeOtro.e[aristaOtroAJ.direction]);
						mapNodeOtro.pointerWGtoWG.remove(aristaOtroAJ);
					} else
						// otro tenía una arista a J pero no I, "agrego" (solo
						// cambio el ID de nodo) la arista a I y elimino la de J
						aristaOtroAJ.points_to_node = mapNodeI.id;
				}
			}
		}

		
		// elimino de la lista el nodo j
		map_list.remove(mapNodeJ);

		// actualizo la lista de visitados sacando al nodo eliminado si
		// corresponde ... parece que sí
		for (int iterNodos = 0; iterNodos < nodosVisitados.size(); iterNodos++) {
			if (nodosVisitados.get(iterNodos) == mapNodeJ.id) {
				nodosVisitados.remove(iterNodos);
				nodosVisitados.add(iterNodos, mapNodeJ.id);
			}
		}
	}

	/*
	 * Gestiona los nodos del grafo(crea y elimina) currentDir: direccion actual
	 * newTrial: nuevo ensayo newPatNode: se genero un patron nuevo ateFood:
	 * comio
	 */
	private void map_activation(boolean newTrial,
			boolean newNode, boolean ateFood) {
		ListIterator<NodeMap> mapIterator;
		NodeMap mapNodeSource, newMapNode, mapNode;
		NodePattern patNode = null;

		if (map_list.isEmpty()) {
			newMapNode = new NodeMap(node_id_WG);
			map_list.add(newMapNode);
			patNode = getNodePattern(lastPattern.id);
			patNode.mapNodeID = newMapNode.id;
			newMapNode.wg_x = (int) ((RobotFactory.getRobot()
					.getGlobalCoodinate().x + 0.6) * 500.0);
			newMapNode.wg_y = (int) ((RobotFactory.getRobot()
					.getGlobalCoodinate().y + 0.6) * 500.0);
			currentNode = newMapNode.id;
		} else if (newNode) {
			patNode = getNodePattern(lastPattern.id);
			node_id_WG++;
			if (!newTrial && (currentNode!=-1)) { // si no es un nuevo ensayo agrego una arista
				mapNodeSource = getNodeMap(currentNode);
				edge_to_direction[currentDirection]++;
				number_of_steps = edge_to_direction[currentDirection];
				//.err.println("WGL::agregando arista en new node");
				mapNodeSource.add_edge(currentDirection, node_id_WG,
						(int) number_of_steps, true);
			}

			Arrays.fill(edge_to_direction, 0);
			number_of_steps = 0.0D;
			newMapNode = new NodeMap(node_id_WG);
			map_list.add(newMapNode);
			patNode.mapNodeID = newMapNode.id;
			currentNode = newMapNode.id;
		} else { // no es es un nodo nuevo y no estaba vacia la lista de nodos
			patNode = getNodePattern(lastPattern.id);
			// si no es un nuevo ensayo y el robot avanzo, evaluo agregar una
			// arista si ya o existia una entre CurrentWGNode y el nodo ganador
			if (!newTrial && (previous_direction == currentDirection)&&(currentNode!=-1)) {
				mapNode = getNodeMap(currentNode);
				//.err.println("WGL::agregando arista existentes");
				mapNode.add_edge(currentDirection, patNode.mapNodeID,
						(int) number_of_steps, true);

			}
			currentNode = patNode.mapNodeID;
		}
	}

	/*
	 * a partir de un identificador de nodo en el mapa devuelve el nodo, null si
	 * no lo encuentra
	 */
	public static NodeMap getNodeMap(int idNode) {
		ListIterator<NodeMap> mapIterator;
		NodeMap mapNode;
		NodeMap result = null;

		for (mapIterator = map_list.listIterator(); mapIterator.hasNext();) {
			mapNode = mapIterator.next();
			if (mapNode.id == idNode) {
				result = mapNode;
				break;
			}
		}

		return result;
	}

	/*
	 * Devuelve un NodePartter de la lista de patrones actuales a partir del
	 * identificador del NodePattern, si no lo encuentra devuelve null
	 */
	private NodePattern getNodePattern(int idPattern) {
		NodePattern pattern = null, result = null;

		for (ListIterator<NodePattern> patIterator = pattern_list
				.listIterator(); patIterator.hasNext();) {
			pattern = patIterator.next();
			if (pattern.id == idPattern) {
				result = pattern;
				break;
			}
		}

		return result;
	}

	private void eliminarPatronesNodo(NodeMap mapNode) {
		NodePattern pattern = null;
		ListIterator<NodePattern> patIterator;
		LinkedList<NodePattern> toRemove = new LinkedList<NodePattern>();

		for (patIterator = pattern_list.listIterator(); patIterator.hasNext();) {
			pattern = patIterator.next();
			if (pattern.mapNodeID == mapNode.id) {
				toRemove.add(pattern);
			}
		}
		for (patIterator = toRemove.listIterator(); patIterator.hasNext();) {
			pattern_list.remove(patIterator.next());
		}
	}

	private void eliminarPatronesViejos() {
		NodePattern pattern = null, result = null;
		ListIterator<NodePattern> patIterator;
		long currentTime = System.currentTimeMillis();
		LinkedList<NodePattern> toRemove = new LinkedList<NodePattern>();

		for (patIterator = pattern_list.listIterator(); patIterator.hasNext();) {
			pattern = patIterator.next();
			if (((pattern.getUpdateTimeStamp() + TTL) < currentTime)
					&& pattern.id != lastPattern.id) {
				toRemove.add(pattern);
			}
		}

		for (patIterator = toRemove.listIterator(); patIterator.hasNext();) {
			// borro el patron de la lista de patrones
			pattern = patIterator.next();
			pattern_list.remove(pattern);

			NodeMap mapNode = getNodeMap(pattern.mapNodeID);
			if (mapNode != null) { // TODO: raro pero aparentemente pasa ...
				// busco la arista que pudiera existir en la dirección del
				// patron y la elimino
				for (int iterEdge = 0; iterEdge < mapNode.pointerWGtoWG.size(); iterEdge++) {
					Edge edge = mapNode.pointerWGtoWG.get(iterEdge);

					if (edge.direction == pattern.dir) {
						mapNode.pointerWGtoWG.remove(edge);
						break;
					}
				}

				if (mapNode.pointerWGtoWG.isEmpty()) {
					deleteNode(mapNode);
					System.err
							.println("WDL:: Borro NODO! viejo <<<<<<>>>>>>>>");

				}
			}
			System.err.println("WDL:: Borro patron viejo <<<<<<>>>>>>>>");
		}

	}

	private void deleteNode(NodeMap mapNode) {
		ListIterator<NodeMap> mapIterator;
		NodeMap sourceMapNode;

		// para cada nodo de la lista elimino las aristas que apuntan a mapNode
		for (mapIterator = map_list.listIterator(); mapIterator.hasNext();) {
			sourceMapNode = mapIterator.next();
			// recorro las aristas en busca de una que apunte al nodo a borrar
			for (int iterEdge = 0; iterEdge < sourceMapNode.pointerWGtoWG
					.size(); iterEdge++) {
				Edge edge = sourceMapNode.pointerWGtoWG.get(iterEdge);
				if (edge.points_to_node == mapNode.id) {
					sourceMapNode.pointerWGtoWG.remove(iterEdge);
					break;
				}
			}
		}
		// TODO: esto no deberia ser necesario pero alguien esta dejando
		// patrones huerfanos
		eliminarPatronesNodo(mapNode);

		// elimino el nodo de la lista
		map_list.remove(mapNode);

	}
	

	private double promediarMin(double a, double b) {
		return Math.min(a, b);
	}

	private double promediarMax(double a, double b) {
		if ((a > RUIDO) || (b > RUIDO))
			return Math.max(a, b);
		else
			return 0;
	}

	private double promediarP(double a, double b) {
		if ((a != 0) && (b != 0))
			return PESO_A * a + (1 - PESO_A) * b;
		else
			return 0;
	}

	private double reglaAdaptacion(double a, double b) {
//		 return Math.min(a, b);
		return a + ALFA_LEARN * (b-a);
	}

	// primedio eliminado ruido
	private double promediar(double a, double b) {
//		 return Math.min(a, b);

		if ((a <= RUIDO) && (b <= RUIDO))
			return 0;
		if (a <= RUIDO)
			return b;
		else if (b <= RUIDO)
			return a;
		else
			return PESO_A * a + (1 - PESO_A) * b;
	}

	// promedio pero solo si son similares
	private double promediarSimilares(double a, double b) {
		double diff = Math.abs(a-b);
		double result =diff<UMBRAL_CS?(a+b)/2.0:0;
		if (result <= RUIDO) result = 0;

		return result;
	}

	// se queda con el patron con menos celdas activas
	private void selectMinActiveCells(double a[], double b[]) {
		int iter;
		double cantActivationB = 0, cantActivationA = 0;

		for (iter = 0; iter < a.length; iter++) {
			if (a[iter] > RUIDO) cantActivationA++;
			if (b[iter] > RUIDO) cantActivationB++;
		}

		// si debo retornar b, lo copia a a
		if (cantActivationB < cantActivationA)
			for (iter = 0; iter < a.length; iter++)			
				a[iter] = b[iter];
	}
	
	// se queda con el patron con menos celdas activas
	private void selectMaxActiveCells(double a[], double b[]) {
		int iter;
		double cantActivationB = 0, cantActivationA = 0;

		for (iter = 0; iter < a.length; iter++) {
			if (a[iter] > RUIDO)
				cantActivationA++;
			if (b[iter] > RUIDO)
				cantActivationB++;
		}

		// si debo retornar b, lo copia a a
		if (cantActivationB > cantActivationA)
			for (iter = 0; iter < a.length; iter++)
				a[iter] = b[iter];
	}
		
	// promedia los place_IDs de dos patrones dejando el promedio en el primero
	// de ellos
	private void promediar(double a[], double b[]) {
		int iter;
		double sumaActivationB=0, sumaActivationA=0, sumaProm = 0;
				
		for (iter = 0; iter < a.length; iter++)
			sumaActivationB =sumaActivationB+b[iter];
		for (iter = 0; iter < a.length; iter++) {
			// patNodeI.place_IDs[iter] = (patNodeI.place_IDs[iter] +
			// patNodeJ.place_IDs[iter]) / 2.0;
			//a[iter] = reglaAdaptacion(a[iter], b[iter]);
			a[iter] = promediar(a[iter], b[iter]);
//			a[iter] = promediarSimilares(a[iter], b[iter]);
			sumaProm = sumaProm +a[iter];
		}
		// normalizo a 1050 :D
//		for (iter = 0; iter < a.length; iter++) {
//			a[iter] = a[iter] * 1050.0 / sumaProm;
//			sumaActivationA =sumaActivationA+a[iter];
//		}
		System.err.println("WGL::activationB: "+b[0]+","+b[1]+","+b[2]+" ... " + sumaActivationB+ ", " + sumaActivationA);
	}

	
	double distanciaMinPromedio(double[] a, double[] b) {
		double dist = 0, sumaPC = 0;
		for (int k2 = 0; k2 < a.length; k2++) {
			dist = dist + Math.min(a[k2], b[k2]);
			sumaPC = sumaPC + b[k2];
		}
		dist = dist / sumaPC;
		return 1 - dist;
	}

	double distanciaEuclidianaNormalizada(double[] a, double[] b) {

		double dist = 0, sumaPC = 0;
		for (int k2 = 0; k2 < a.length; k2++) {
			dist = dist + Math.min(a[k2], b[k2]);
			sumaPC = sumaPC + Math.max(a[k2], b[k2]);
		}
		dist = dist / sumaPC;
		return 1 - dist;
	}

	double distanciaEuclidiana(double[] a, double[] b) {
		double dist = 0;
		for (int k2 = 0; k2 < a.length; k2++) {
			dist = dist + Math.pow(a[k2] - b[k2], 2);
		}
		dist = Math.sqrt(dist);
		return dist;
	}

}