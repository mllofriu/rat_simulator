package graph;
/* Clase que representa un nodo del mapa topol�gico del
   ambiente.
   Alejandra Barrera
   Fecha de creaci�n: abril 7, 2005.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;

import javax.vecmath.Point2d;

import robot.IRobot;
import robot.RobotFactory;



public class NodeMap {
	public int id; // identificador de nodo
	public int wg_x; // posiciones x e y en el frame de nodos (visualizacion)
	public int wg_y;
	public LinkedList<Edge> pointerWGtoWG; // lista de aristas
	double w[];
	public double e[];
	private double globalX, globalY; // coordenadas globales, porque no usa
										// globales para dibujar (wg_x, wg_y)

	public NodeMap(int i) {
		pointerWGtoWG = new LinkedList<Edge>();
		w = new double[IRobot.NUM_POSSIBLE_ACTIONS];
		e = new double[IRobot.NUM_POSSIBLE_ACTIONS];
		Arrays.fill(w, 0);
		Arrays.fill(e, 0);
		id = i;

		// posicion en el frame - el centinela -1 indica que todavía no fue
		// asiganda una posicion en el frame.
		wg_x = -1;
		wg_y = -1;

		globalX = RobotFactory.getRobot().getGlobalCoodinate().x;
		globalY = RobotFactory.getRobot().getGlobalCoodinate().y;
	}

	public void add_edge(int direction, int destNode, int step, boolean trace) {
		Edge edge;
		boolean error = false;

		for (int iterEdges = 0; iterEdges < pointerWGtoWG.size(); iterEdges++) {
			edge = pointerWGtoWG.get(iterEdges);
			if (edge.direction == direction) {// ya hay una arista en esa
												// direccion
				if (edge.points_to_node == destNode) {
					error = true;
					System.err.println("NodeMap::ya existe arista");
					break;
				} else {
					error = true;
					edge.points_to_node = destNode;
					System.err.println("NodeMap::ya existe arista pero apunta a otro nodo ... ");
					break;
				}
			}
		}

		if (!error)
			pointerWGtoWG.add(new Edge(direction, destNode, step, trace));

	}

	public void draw(Graphics g, int i, int j, boolean flag, Color color,
			boolean flag1) {
		g.setColor(color);
		g.fillOval(i, j, 20, 20);
		g.setColor(Color.black);
		g.drawOval(i, j, 20, 20);
		if (flag1) {
			g.setColor(Color.black);
			g.drawLine(i + 2, j + 2, i + 18, j + 18);
			g.drawLine(i + 2, j + 18, i + 18, j + 2);
		}
	}

	public Point2d getGlobalPoint() {
		// TODO Auto-generated method stub
		return new Point2d(globalX, globalY);
	}
}