package graph;
import java.util.Arrays;

import support.Configuration;

/* Clase que representa un nodo de la lista de patrones de
   activaci�n registrados en la capa PlaceCellLayer. Un
   nodo representa la vista de un lugar del ambiente que la
   rata obtiene cuando se encuentra en cierta direcci�n.
   Alejandra Barrera
   Fecha de creaci�n: abril 7, 2005.
*/

public class NodePattern {
    public static final int CANT_NEURONAS_DETECTORAS_PATRONES = Configuration.getInt("PathIntegrationFeatureDetectorLayer.TOTAL_NEURONS");
    public int id;
    public int dir;
    public int mapNodeID;
    public double place_IDs[];
    private long updateTimeStamp; // registra el momento de la ultima actualizacion
    
    public NodePattern(int i, int j)
    {
        place_IDs = new double[CANT_NEURONAS_DETECTORAS_PATRONES];
        Arrays.fill(place_IDs,0);
        id = i;
        mapNodeID = -1;
        dir = j;
        setUpdateTimeStamp(System.currentTimeMillis());
    }
    

    public void updatePattern() {
		this.setUpdateTimeStamp(System.currentTimeMillis());
	}


	/**
	 * @return the updateTimeStamp
	 */
	public long getUpdateTimeStamp() {
		return updateTimeStamp;
	}


	/**
	 * @param updateTimeStamp the updateTimeStamp to set
	 */
	private void setUpdateTimeStamp(long updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	
}