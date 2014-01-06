/*
  Clase que representa el frame en que se desplegar� el
  mapa del ambiente.
  Versi�n 1
  Alejandra Barrera
  Fecha de creaci�n: abril 8, 2005.
 */

import java.awt.*;
import java.util.*;

public class WorldGraphFrame extends Frame {
	private final int NODE_DIAMETER = 10;
	private final int ARROW_DIAMETER = 5;

	public WorldGraphFrame() {
		setTitle("The World Graph");
		setSize(640, 640);
	}

	public void paint(Graphics g) {
		ListIterator<NodeMap> mapIterator;
		NodeMap sourceNode, destNode;
		// System.out.println("WorldGraphFrame::nodos: "+
		// WorldGraphLayer.map_list.size()+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		setBackground(Color.white);
		try {
			for (mapIterator = WorldGraphLayer.map_list.listIterator(); mapIterator
					.hasNext();) {
				sourceNode = mapIterator.next();
				if (sourceNode.wg_x == -1) // si todavía no fue asignada una
											// posicion en el frame ... busco un
											// lugar
					this.setNodePosition(sourceNode);
				// dibujo aristas y puntos de sentido (arrow)
				g.setColor(Color.black);
				if (sourceNode.pointerWGtoWG.size() > 9)
					System.err.println("WGF::"+ sourceNode.pointerWGtoWG.size() + " aristas!!!!!!");
				for (int i = 0; i < sourceNode.pointerWGtoWG.size(); i++) {
					Edge edge = (Edge) sourceNode.pointerWGtoWG.get(i);
					destNode = WorldGraphLayer.getNodeMap(edge.points_to_node);

					// TODO Hay que ver porque pasa esto
					if (destNode != null && destNode.wg_x != -1) {
						g.setColor(Color.BLACK);
						g.drawLine(sourceNode.wg_x + 5, sourceNode.wg_y + 5,
								destNode.wg_x + 5, destNode.wg_y + 5);
						double d2 = destNode.wg_x - sourceNode.wg_x;
						double d3 = destNode.wg_y - sourceNode.wg_y;
						double d1 = (1.0D / Math.sqrt(d2 * d2 + d3 * d3)) * d2;
						double d = (1.0D / Math.sqrt(d2 * d2 + d3 * d3)) * d3;
						int j = (int) ((double) (destNode.wg_x + 5) - 7 * d1) - 3;
						int k = (int) ((double) (destNode.wg_y + 5) - 7 * d) - 3;
						if (sourceNode.e[edge.direction]>0)
							g.setColor(Color.red);
						else 
							g.setColor(Color.black);
						g.fillOval(j, k, ARROW_DIAMETER, ARROW_DIAMETER);
						g.drawOval(j, k, ARROW_DIAMETER, ARROW_DIAMETER);
					}
				}
			}
			for (mapIterator = WorldGraphLayer.map_list.listIterator(); mapIterator
					.hasNext();) {
				sourceNode = (NodeMap) mapIterator.next();
				if (sourceNode.id == WorldGraphLayer.idNodeWinner)
					drawNode(g, sourceNode.wg_x, sourceNode.wg_y, false,
							Color.yellow.darker().darker(), true);
				else if (sourceNode.id == getIdNode(2))
					drawNode(g, sourceNode.wg_x, sourceNode.wg_y, false,
							Color.yellow.darker(), true);
				else if (sourceNode.id == getIdNode(1))
					drawNode(g, sourceNode.wg_x, sourceNode.wg_y, false,
							Color.yellow, true);
				else if (sourceNode.id == getIdNode(0))
					drawNode(g, sourceNode.wg_x, sourceNode.wg_y, false,
							Color.yellow.brighter(), false);
				else {
					double maxQ = WorldGraphLayer.maxQ(sourceNode.id);
					if (maxQ > 0)
						drawNode(g, sourceNode.wg_x, sourceNode.wg_y, false,Color.red, false);
					else
						drawNode(g, sourceNode.wg_x, sourceNode.wg_y, false,
								Color.green, false);

				}
			}
		} catch (Exception ex) {
			// se dispara una excepccion por acceso concurrente a las
			// estructuras de WGL que no he podido resolver ...
			ex.printStackTrace();
		}
	}

	int getIdNode(int pos) {
		int result;

		if (pos < WorldGraphLayer.nodosVisitados.size())
			result = WorldGraphLayer.nodosVisitados.get(pos);
		else
			result = -1;
		return result;
	}

	public void drawNode(Graphics g, int i, int j, boolean flag, Color color,
			boolean flag1) {
		g.setColor(color);
		g.fillOval(i, j, NODE_DIAMETER, NODE_DIAMETER);
		g.setColor(Color.black);
		g.drawOval(i, j, NODE_DIAMETER, NODE_DIAMETER);
		if (flag1) {
			g.setColor(Color.black);
			g.drawLine(i + 1, j + 1, i + 9, j + 9);
			g.drawLine(i + 1, j + 9, i + 9, j + 1);
		}
	}

	private void setNodePosition(NodeMap mapNode) {
		ListIterator<NodeMap> mapIterator;

		Direction direction = new Direction(0);
		boolean end;
		int l2;
		mapNode.wg_x = (int) ((RobotFactory.getRobot().getGlobalCoodinate().x + 0.6) * 500.0);
		mapNode.wg_y = (int) ((RobotFactory.getRobot().getGlobalCoodinate().y + 0.6) * 500.0);

		l2 = check_boundaries_wg(mapNode.wg_x, mapNode.wg_y,
				Utiles.acccion2GradosAbsolute(0));
		direction.value = Utiles.gradosAbsolute2Acccion(l2);
		int i3 = mapNode.wg_x + 25 * direction.x();
		int j3 = mapNode.wg_y + 25 * direction.y();
		do {
			end = true;
			for (mapIterator = WorldGraphLayer.map_list.listIterator(); mapIterator
					.hasNext();) {
				NodeMap mapNodeAux = mapIterator.next();
				if (mapNodeAux.wg_x == i3 && mapNodeAux.wg_y == j3) {
					if (Math.random() > 0.5D) {
						if (l2 == 360)
							l2 = 45;
						else
							l2 += 45;
					} else if (l2 == 45)
						l2 = 360;
					else
						l2 -= 45;
					l2 = check_boundaries_wg(i3, j3, l2);
					direction.value = Utiles.gradosAbsolute2Acccion(l2);
					i3 += 25 * direction.x();
					j3 += 25 * direction.y();
					end = false;
					break;
				}
			}
		} while (!end);
		mapNode.wg_x = i3;
		mapNode.wg_y = j3;

	}

	private int check_boundaries_wg(int i, int j, int k) {
		if (i <= 80) {
			if (j <= 80)
				k = 315;
			else if (j >= 550)
				k = 45;
			else
				k = 360;
		} else if (i >= 550) {
			if (j <= 80)
				k = 225;
			else if (j >= 550)
				k = 135;
			else
				k = 180;
		} else if (j <= 80)
			k = 270;
		else if (j >= 550)
			k = 90;
		return k;
	}

}