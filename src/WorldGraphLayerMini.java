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
import support.Utiles;

public class WorldGraphLayerMini extends NslModule {
	public static final double PATTERN_SD = Configuration
			.getDouble("WorldGraphLayer.UMBRAL_PATTERN_SD");
	private final double RPS_HEIGHT = Configuration
			.getDouble("MotivationalSchema.RPS_HEIGHT");
	final double PESO_A = Configuration
			.getDouble("WorldGraphLayer.PESO_PASADO_PROMEDIO_PATTERNS");
	private final int SPIKES = PathIntegrationFeatureDetectorLayer.SPIKES;
	private static final double DISTANCIA_ENTRE_NODOS = 0.05; // Distancia entre  nodos para determinar si en coordenadas globales coinciden
	private static final double RUIDO = 0.1;// UMBRAL_SD/(double)NodePattern.CANT_NEURONAS_DETECTORAS_PATRONES;
	private static final int POR_45_IZQ = 3;
	private static final int MIN_ACTIVE_GRIDS = 1;
	private static final double MIN_SUM_PATTERN = 100;

	public NslDinInt0 currentHeadAngleRat;
	public NslDinDouble1 pcl1dim;
	public NslDinDouble0 reward;
	public NslDinDouble0 distFood;
	public NslDinInt0 ActionTaken;
	public NslDoutDouble1 expCycling;
	public NslDoutDouble1 curiosityCycling;

	public static int idPreviousNode = -1;
	public static NodeMap currentNode;

	// lista de nodos del grafo y lista de patrones para cada nodo
	public static LinkedList<NodeMap> map_list;
	private LinkedList<NodePattern> pattern_list;

	private Frame WGFrame;
	private boolean inicio;
	private double expCyclingAux[];
	private double flagCyclingD[];
	Random rand = new Random();

	public WorldGraphLayerMini(String nslName, NslModule nslParent) {
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
		pattern_list = new LinkedList<NodePattern>();
		map_list = new LinkedList<NodeMap>();
		WGFrame = new WorldGraphFrame();
		WGFrame.setVisible(true);
	}

	private int previousDirection =0, previousGridAct = 0, cantVecesActivas=0;
	private String iteracion;
	
	public void simRun() {
		int currentDirection = 0;
		double [] inputPattern = pcl1dim.get();
		
		if (HasselmoGridCellLayer.cantActivas>MIN_ACTIVE_GRIDS)
//		if (sumPattern(inputPattern)>MIN_SUM_PATTERN) // da siempre cerca de 52
			patternActivation(inputPattern, Utiles.gradosAbsolute2Acccion(currentHeadAngleRat.get()));
		else 
			currentNode = null;

		for (int iterExp=0;iterExp<flagCyclingD.length;iterExp++)
		flagCyclingD[iterExp] = rand.nextDouble() * RPS_HEIGHT;

		WGFrame.repaint(); // para que pinte nodo winner y nodo seleccionado
		curiosityCycling.set(flagCyclingD);
		expCycling.set(expCyclingAux);
		previousDirection = currentDirection;
		previousGridAct = HasselmoGridCellLayer.cantActivas;
	}

	/**
	 * @param inputPattern
	 * @return
	 */
	private double sumPattern(double[] inputPattern) {
		double result=0;
		
		for (int iterPat=0;iterPat<inputPattern.length;iterPat++)
			result = result + inputPattern[iterPat];
		System.err.print("WGL::suma pattern: " + result + ".");
		return result;
	}

	public void simRun2() {
		int currentDirection = 0;

		currentDirection = Utiles.gradosAbsolute2Acccion(currentHeadAngleRat.get());
		if (HasselmoGridCellLayer.cantActivas<=MIN_ACTIVE_GRIDS) {
			currentNode = null;
			Arrays.fill(flagCyclingD,0);
			int giroDer = (Utiles.gradosAbsolute2Acccion(currentHeadAngleRat.get())+1)%flagCyclingD.length;
			flagCyclingD[giroDer] = 1; // obligo a girar para activar el asunto
		}	else {
			cantVecesActivas++;
			if (cantVecesActivas>8) {
				cantVecesActivas=0;
				Arrays.fill(flagCyclingD,0);
				flagCyclingD[Utiles.gradosAbsolute2Acccion(currentHeadAngleRat.get())] = 1; // salgo derecho
			} else {
				Arrays.fill(flagCyclingD,0);
				int giroDer = (Utiles.gradosAbsolute2Acccion(currentHeadAngleRat.get())+1)%flagCyclingD.length;
				flagCyclingD[giroDer] = 1; // obligo a girar para activar el asunto
//				for (int iterExp=0;iterExp<flagCyclingD.length;iterExp++)
//					flagCyclingD[iterExp] = rand.nextDouble() * RPS_HEIGHT;
			}
			currentDirection = Utiles.gradosAbsolute2Acccion(currentHeadAngleRat
					.get());
	
			patternActivation(pcl1dim.get(), currentDirection);
	
		}
		WGFrame.repaint(); // para que pinte nodo winner y nodo seleccionado
		curiosityCycling.set(flagCyclingD);
		expCycling.set(expCyclingAux);
		previousDirection = currentDirection;
		previousGridAct = HasselmoGridCellLayer.cantActivas;
	}

	NodePattern getNearestNodePattern(double[] patron) {
		double dist, distMin = Double.MAX_VALUE;
		NodePattern patNode = null, patWinner = null;

		for (ListIterator<NodePattern> patIterator = pattern_list
				.listIterator(); patIterator.hasNext();) {
			patNode = patIterator.next();

			dist = distanciaEuclidiana(patNode.getPattern(), patron);
			NodeMap node = getNodeMap(patNode.getNodeId());
			if (dist < distMin) {
				distMin = dist;
				patWinner = patNode;
			}
		}

		if (distMin > PATTERN_SD)
			return null;
		else
			return patWinner;
	}
	
	/*
	 * determina cual es el patron activo, setea lastPattern y decide cuando hacer
	 * el merge de dos nodos currentDir: direccion de la cabeza de la rata en el
	 * rango salidaPattern: patron de activacion de la PCL combinado de la DLR y
	 * LPS retorna si es necesario crear o no un nuevo nodo
	 */
	private void patternActivation(double []salidaPattern, int currentDirection) {
		NodePattern actualPattern = null;
		double inputPattern[] = new double[salidaPattern.length];
		// los patrones se almacenan multiplicados por SPIKES por lo que al
		// principio se realiza esa operacion
		for (int k1 = 0; k1 < inputPattern.length; k1++)
			inputPattern[k1] = salidaPattern[k1] * (double) SPIKES;

		actualPattern = getNearestNodePattern(inputPattern);
		// si no encuentro un patron cercano
		// crea un patron y lo agrega a la lista de patrones
		// crea un nodo y lo agrega a la lista, asocia nodo y patron
		if (actualPattern == null) {
			actualPattern = new NodePattern(inputPattern);
			// agrego el nuevo patron a la lista de patrones y actualizo el
			pattern_list.add(actualPattern);
			if ((currentNode==null)||(currentDirection==previousDirection)) {
				NodeMap newNode = new NodeMap();
				map_list.add(newNode);
				currentNode = newNode;
				System.err.println("WGL::new node: " + map_list.size());
			}
			actualPattern.setNodeId(currentNode.getId());
		} else {
			// si encontre un ganador
			promediar(actualPattern.getPattern(), inputPattern);
			actualPattern.updatePattern();

			// verifico si es necesario hacer el merge de dos nodos
			if ((currentNode!=null)&&(currentNode.getId()!=actualPattern.getNodeId())) {
				NodeMap nodeWinner = getNodeMap(actualPattern.getNodeId());
				mergeNodes(currentNode, nodeWinner);
				double distanciaEntreNodos = currentNode.getGlobalPoint()
						.distance(nodeWinner.getGlobalPoint());
				if (distanciaEntreNodos > DISTANCIA_ENTRE_NODOS) {
					System.out.println("WGL::merge error ********************************* iteración: "+ iteracion);
				}

			}
		}
		if (Rat.newTrial)
			System.err.println("WGL::cantidad de nodos: " + map_list.size()
					+ ". cantidad de patrones: " + pattern_list.size());
	}

	
	private void cambiarIDNodo(int i, int j) {
		ListIterator<NodePattern> patIteratorJ;
		NodePattern patNodeJ = null;

		// paso todos los patrones asociados al nodo j a i
		for (patIteratorJ = pattern_list.listIterator(); patIteratorJ.hasNext();) {
			patNodeJ = patIteratorJ.next();
			if (patNodeJ.getNodeId() == j)
				patNodeJ.setNodeId(i);
		}
	}

	private void mergeNodes(NodeMap mapNodeI, NodeMap mapNodeJ) {
		ListIterator<NodeMap> mapIterator;
		NodeMap mapNodeOtro;
		cambiarIDNodo(mapNodeI.getId(), mapNodeJ.getId());
		// si el nodo corriente es el seleccionado para eliminar cambio el id al no eliminado en el merge
		if (currentNode.getId() == mapNodeJ.getId()) {
			currentNode = mapNodeI;
		}

		// para las aristas del nodo i busca si una apunta a j y la elimina
		for (int k = 0; k < mapNodeI.pointerWGtoWG.size(); k++) {
			Edge edge = (Edge) mapNodeI.pointerWGtoWG.get(k);
			if (edge.points_to_node == mapNodeJ.getId()) {
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
			if (edgeJ.points_to_node != mapNodeI.getId()) {
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
			if ((mapNodeI.getId() != mapNodeOtro.getId()) && (mapNodeJ.getId() != mapNodeOtro.getId())){
				for (int j1 = 0; j1 < mapNodeOtro.pointerWGtoWG.size(); j1++) {
					aristaOtroAJ = (Edge) mapNodeOtro.pointerWGtoWG.get(j1);
					if (aristaOtroAJ.points_to_node == mapNodeJ.getId()) {
						otroTieneAristaAJ = true;
						break;
					}
				}
				// hay alguna arista de otro nodo (distinto a i) que apunta al
				// nodo j recien elim	inado
				if (otroTieneAristaAJ) {
					for (int k1 = 0; k1 < mapNodeOtro.pointerWGtoWG.size(); k1++) {
						aristaOtroAI = (Edge) mapNodeOtro.pointerWGtoWG.get(k1);
						if (aristaOtroAI.points_to_node == mapNodeI.getId()) {
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
						aristaOtroAJ.points_to_node = mapNodeI.getId();
				}
			}
		}
		//mapNodeI.setGlobalPoint();
		
		// elimino de la lista el nodo j
		map_list.remove(mapNodeJ);
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
			if (mapNode.getId() == idNode) {
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
			if (pattern.getId() == idPattern) {
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
			if (pattern.getNodeId() == mapNode.getId()) {
				toRemove.add(pattern);
			}
		}
		for (patIterator = toRemove.listIterator(); patIterator.hasNext();) {
			pattern_list.remove(patIterator.next());
		}
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

	// promedia los place_IDs de dos patrones dejando el promedio en el primero
	// de ellos
	private void promediar(double a[], double b[]) {
		for (int iter = 0; iter < a.length; iter++)
			// patNodeI.place_IDs[iter] = (patNodeI.place_IDs[iter] +
			// patNodeJ.place_IDs[iter]) / 2.0;
			a[iter] = promediar(a[iter], b[iter]);
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