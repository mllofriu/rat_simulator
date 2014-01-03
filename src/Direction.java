/* Clase que representa una direcci�n de un nodo del mapa
   topol�gico. Su finalidad es la de soportar el despliegue
   gr�fico de los nodos del mapa.
   Alejandra Barrera
   Fecha de creaci�n: Abril 6, 2005
*/

public class Direction
{
	public int value;
	
    public Direction(int i)
    {
        value = i;
    }
	
    public int x()
    {
        if(value == 0)
            return 1;
        if(value == 1)
            return 1;
        if(value == 2)
            return 0;
        if(value == 3)
            return -1;
        if(value == 4)
            return -1;
        if(value == 5)
            return -1;
        if(value == 6)
            return 0;
        return value != 7 ? 0 : 1;
    }

    public int y()
    {
        if(value == 0)
            return 0;
        if(value == 1)
            return -1;
        if(value == 2)
            return -1;
        if(value == 3)
            return -1;
        if(value == 4)
            return 0;
        if(value == 5)
            return 1;
        if(value == 6)
            return 1;
        return value != 7 ? 0 : 1;
    }
}