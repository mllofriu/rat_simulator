package robot;
import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

/**
 * 
 */

/**
 * @author gtejera
 *
 */
public interface IRobot {
	
	public static int CANT_ACCIONES = 8; // cantidad de acciones relacionada con los giros que puede realizar de a 45 grados
	
	/* hace que el robot ejecute una accion determinada cantidad de grados en el rango [-180,180]
	 * si el angulo es cero avanza un paso y en caso cotrario solo gira el angulo indicado
	 */
	public abstract void doAction(int grados);
	
	/* Devuelve las marcas encontradas a cada marca le corresponde un color
	 * El elemento en un tupla formada por posicion y tamaño, ambos normalizados
	 */
	Double[] findLandmarks();
	
	// devuelve la posicion global del robot
	public abstract Double getGlobalCoodinate();

	// devuelve la direccion global (angulo absoluto) del robot
	public abstract double getGlobalDirection();

	// Devuelve los posibles giros que puede realizar el robot en la posición actual
	public abstract boolean [] affordances();
	
	// Devuelve true si se encuentra en posición de alimentarse
	public abstract boolean findFood();

	// invocada cada vez que se empueza una nueva operacion dentro de la sesion
	public abstract void startRobot();
	
	/** se usa solamente para poder visualizar
	 * @return
	 */
	public abstract BufferedImage getPanoramica();

	public abstract Color [] getColorsLandmarks();

	/**
	 * @return
	 */
	public abstract double getSpeed();

	/**
	 * @return
	 */
	public abstract double getHeadDirection();

}
