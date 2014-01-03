package support;


import graph.Edge;
import graph.NodeMap;

import java.io.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/* 
 * PajekFormat.java
 * Este modulo genera archivos de grafos compatibles con el software pajek http://pajek.imfm.si/
 * 
 * Autor: Gonzalo Tejera
 * Fecha: 17 de agosto de 2010
 */


/* Formato de los archivos .net

 *vertices n      	n is the number of vertices
 1 "name_node_1" 	the label of vertex 1 is name_node_1
 ...
 n "name_node_n" 	the label of vertex n is name_node_n
 
 *edges
 p q v_p_q			the edge from p to q has value v_p_q
 
 */

public class PajekFormat {
	private static final boolean APPEND = false;
	private static final String DEFAULT_DIR=Configuration.getString("PajekFormat.DIRECTORY");
	private static final String DEFAULT_FILE_NAME=Configuration.getString("WorldFrame.MAZE_FILE");
	private static final String DEFAULT_FILE= System.getProperty("user.dir")+File.separatorChar+DEFAULT_DIR+File.separatorChar+DEFAULT_FILE_NAME+System.currentTimeMillis()+".net";
	private static final String DEFAULT_FILE_MAX_EXP= System.getProperty("user.dir")+File.separatorChar+DEFAULT_DIR+File.separatorChar+DEFAULT_FILE_NAME+"MaxExp"+System.currentTimeMillis()+".net";

	public static void generateGraph(List<NodeMap> grafo) {
		PrintWriter pw=null;
		int iterNodos, iterAsistas;
		NodeMap nodo;
		Edge arista;
		Hashtable<Integer, Integer> secNumberNodes = new Hashtable<Integer, Integer>(); // Pajek obliga a que los nombres de los nodos vayan de 1 a n, y en la lista esto no es asi
		
		/* Pajek no permite que nos nombres de los nodos sean 0 por eso suma uno por todos lados */
		
		try {
			pw = new PrintWriter(new FileWriter(DEFAULT_FILE, APPEND));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.print("*vertices "+grafo.size()+"\r\n");  // palabra clave y cantidad de nodos
		
		for (iterNodos=0;iterNodos<grafo.size();iterNodos++) {
			nodo=grafo.get(iterNodos);
			//pw.print(""+(iterNodos+1)+" \""+(nodo.id)+"\"\r\n"); // para cada nodo idPajek y idWGL
			pw.print(""+(iterNodos+1)+" \""+(nodo.id)+"\""+" 0 "+nodo.getGlobalPoint().x+" "+nodo.getGlobalPoint().y+"\r\n"); // para cada nodo idPajek y idWGL
			secNumberNodes.put(nodo.id, iterNodos+1);
		}

		pw.print("*arcs\r\n"); // palabra clave empiezan aristas

		for (iterNodos=0;iterNodos<grafo.size();iterNodos++) {
			nodo=grafo.get(iterNodos);
			for (iterAsistas=0;iterAsistas<nodo.pointerWGtoWG.size();iterAsistas++) {
				arista = nodo.pointerWGtoWG.get(iterAsistas);
				pw.print(""+secNumberNodes.get(nodo.id)+" "+secNumberNodes.get(arista.points_to_node)+"\r\n");
			}
		}
		pw.close();
	}
	
	public static void generateMaxExpectedPathGraph(List<NodeMap> grafo) {
		PrintWriter pw=null;
		int iterNodos, iterAsistas;
		NodeMap nodo;
		Edge arista;
		Hashtable<Integer, Integer> secNumberNodes = new Hashtable<Integer, Integer>(); // Pajek obliga a que los nombres de los nodos vayan de 1 a n, y en la lista esto no es asi
		/* Pajek no permite que nos nombres de los nodos sean 0 por eso suma uno por todos lados */
		try {
			pw = new PrintWriter(new FileWriter(DEFAULT_FILE_MAX_EXP, APPEND));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.print("*vertices "+grafo.size()+"\r\n");  // palabra clave y cantidad de nodos
		for (iterNodos=0;iterNodos<grafo.size();iterNodos++) {
			nodo=grafo.get(iterNodos);
			//pw.print(""+(iterNodos+1)+" \""+(nodo.id)+"\""+" "+nodo.wg_x+" "+nodo.wg_y+" 0\r\n"); // para cada nodo idPajek y idWGL
			pw.print(""+(iterNodos+1)+" \""+(nodo.id)+"\""+" 0 "+nodo.getGlobalPoint().x+" "+nodo.getGlobalPoint().y+"\r\n"); // para cada nodo idPajek y idWGL
			secNumberNodes.put(nodo.id, iterNodos+1);
		}
		pw.print("*arcs\r\n"); // palabra clave empiezan aristas
		/*
		for (iterNodos=0;iterNodos<grafo.size();iterNodos++) {
			nodo=grafo.get(iterNodos);
			for (iterAsistas=0;iterAsistas<nodo.pointerWGtoWG.size();iterAsistas++) {
				arista = nodo.pointerWGtoWG.get(iterAsistas);
				pw.print(""+secNumberNodes.get(nodo.id)+" "+secNumberNodes.get(arista.points_to_node)+"\r\n");
			}
		}
		*/

		double maxQ;int nodeMax=0;
		for (iterNodos=0;iterNodos<grafo.size();iterNodos++) {
			nodo=grafo.get(iterNodos); maxQ=Double.MIN_VALUE;
			for (iterAsistas=0;iterAsistas<nodo.pointerWGtoWG.size();iterAsistas++) {
				if (nodo.e[nodo.pointerWGtoWG.get(iterAsistas).direction]>maxQ) {
					arista = nodo.pointerWGtoWG.get(iterAsistas);
					maxQ=nodo.e[arista.direction];
					nodeMax=arista.points_to_node;
				}
			}
			pw.print(""+secNumberNodes.get(nodo.id)+" "+secNumberNodes.get(nodeMax)+" "+maxQ+"\r\n");
		}
		pw.close();
	}
	
	
}
