import javax.vecmath.Point2d;

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
	
	// Devuelve la imagen panoramica de 80*5 pixeles en int RGB en la horizontal y 80 en la horizontal
	public abstract int[][]getPanoramica();
	
	// devuelve la posicion global del robot
	public abstract Point2d getGlobalCoodinate();
	
	// Devuelve los posibles giros que puede realizar el robot en la posición actual
	public abstract boolean [] affordances();
	
	// Devuelve true si se encuentra en posición de alimentarse
	public abstract boolean findFood();

	// invocada cada vez que se empueza una nueva operacion dentro de la sesion
	public abstract void startRobot();

	public abstract void rotateRobot(int actionDegrees);	
}
