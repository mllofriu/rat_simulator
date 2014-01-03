/**
 * 
 */
package neural;

/**
 * @author gtejera
 *
 */
public class Utiles {
	// calcula la frecuencia según la distancia desde el área postrhynal
		public static double calculateFrequency(double z) {
			return 1/(0.094*(z+3.9)-0.25);
		}

}
