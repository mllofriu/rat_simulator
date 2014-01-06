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
import java.util.*;
import java.awt.*;


public class SARSA extends WorldGraphLayer {
	public static int prePreNode = -1;

	public SARSA(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		NODE_HISTORY_SIZE=3; // la regla de sarsa necesita considar los tres ultimos nodos
	}

	// aplico la regla de aprendizaje
	protected void reinforce() {
			//System.out.println("SARSA::prePreNode: " + prePreNode+"\n\n\n");
			NodeMap previousNode;
			Edge edge;
			double qS1A1=0;
			double r = Drive.ateFood ? reward.get() : 0;
			// double r = Drive.ateFood ? 1 : 0;
			ListIterator<NodeMap> mapIterator = map_list.listIterator();
			int idNodoActual = nodosVisitados.getLast();
			// Busco el nodo anterior para encontrar la arista Q(st+1, at+1)
			while (mapIterator.hasNext()) {
				previousNode = mapIterator.next();
				if (previousNode.id == nodosVisitados.get(1)) {
					for (int l2 = 0; l2 < previousNode.pointerWGtoWG.size(); l2++) {
						edge = (Edge) previousNode.pointerWGtoWG.get(l2);
						// busco la arista entre el nodo anterior y el actual
						if (edge.points_to_node == idNodoActual) {
							// encuentro q(st+1,at+1)
							qS1A1=previousNode.e[edge.direction];
							
							mapIterator = map_list.listIterator();
							// Busco el nodo anterior para encontrar la arista Q(st, at)
							while (mapIterator.hasNext()) {
								previousNode = mapIterator.next();
								if (previousNode.id == nodosVisitados.getFirst()) {
									for (l2 = 0; l2 < previousNode.pointerWGtoWG.size(); l2++) {
										edge = (Edge) previousNode.pointerWGtoWG.get(l2);
										// busco la arista entre el nodo anterior y el actual
										if (edge.points_to_node == nodosVisitados.get(1)) {
											// aplico regla de aprendizaje Q
											// NO DETERMINISTA 
											//previousNode.e[edge.direction]=previousNode.e[edge.direction]+ALFA*(r+GAMMA*(qS1A1-previousNode.e[edge.direction));
											// DETERMINSTA? 
											previousNode.e[edge.direction]=r+GAMMA*qS1A1;
											System.out.println("SARSA: Q updated: "+previousNode.e[edge.direction]);
											break;
										}
									}
									break;
								}
							}

							break;
						}
					}
					break;
				}
			}
			
		}
}