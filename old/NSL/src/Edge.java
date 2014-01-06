/*
	Clase que representa un arco del mapa topol�gico del
	ambiente.
	Versi�n 1
	Alejandra Barrera
	Fecha de creaci�n: abril 7, 2005.
*/

public class Edge
{
    public int points_to_node;
    public int direction;
    public int steps;
    public boolean trace;

    public Edge(int k, int l, int i1, boolean tr)
    {
        direction = k;
        points_to_node = l;
        steps = i1;
        trace= tr;
    }
}