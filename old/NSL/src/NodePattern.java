/* Clase que representa un nodo de la lista de patrones de
   activaci�n registrados en la capa PlaceCellLayer. Un
   nodo representa la vista de un lugar del ambiente que la
   rata obtiene cuando se encuentra en cierta direcci�n.
   Alejandra Barrera
   Fecha de creaci�n: abril 7, 2005.
*/

public class NodePattern {
    public static final int CANT_NEURONAS_DETECTORAS_PATRONES = PathIntegrationFeatureDetectorLayer.TOTAL_NEURONS;
    public int id;
    public int dir;
    public int mapNodeID;
    double place_IDs[];
    public long updateTimeStamp; // registra el momento de la ultima actualizacion
    
    public NodePattern(int i, int j)
    {
        place_IDs = new double[CANT_NEURONAS_DETECTORAS_PATRONES];
        id = i;
        mapNodeID = -1;
        dir = j;
        updateTimeStamp = System.currentTimeMillis();
        for(int k = 0; k < CANT_NEURONAS_DETECTORAS_PATRONES; k++)
            place_IDs[k] = 0;
    }
    

    public void updatePattern() {
		this.updateTimeStamp = System.currentTimeMillis();
	}
	
}