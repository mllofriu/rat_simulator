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

import java.util.*;
import java.awt.*;

public class QLearning extends WorldGraphLayer {

	public QLearning(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		NODE_HISTORY_SIZE = 2;
	}
	// aplico la regla de aprendizaje
	protected void reinforce() {
		NodeMap previousNode;
		Edge edge;
		//double r = Drive.ateFood ? reward.get() : 0;
		double r = Drive.ateFood ? 1 : 0;
		ListIterator<NodeMap> mapIterator = map_list.listIterator();
		int idNodoActual = nodosVisitados.getLast();
		double mQ = maxQ(idNodoActual);
//		System.out.println("Qlearning: " + nodosVisitados.getFirst()
//				+ " -> " +  nodosVisitados.getLast() + ". Qmax: " + mQ);
		// Busco el nodo anterior
		while (mapIterator.hasNext()) {
			previousNode = mapIterator.next();
			if (previousNode.id == nodosVisitados.getFirst()) {
				for (int l2 = 0; l2 < previousNode.pointerWGtoWG.size(); l2++) {
					edge = (Edge) previousNode.pointerWGtoWG.get(l2);
					// busco la arista entre el nodo anterior y el actual
					if (edge.points_to_node == idNodoActual) {
						// aplico regla de aprendizaje Q
						// determinista 
						// previousNode.e[edge.direction] = r + GAMA * mQ;
						// no determinista
						previousNode.e[edge.direction] = previousNode.e[edge.direction] + ALFA * (r + GAMMA * (mQ-previousNode.e[edge.direction]));
						
//						System.out.println("Qlearning: nodo ant: " + nodosVisitados.getFirst()
//								+". Q(s,a): "+ previousNode.e[edge.direction]);
						break;
					}
				}
				break;
			}
		}
	}

}