package neural;
import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

/* Clase que implementa los disparos de una celda lugar basado en la
   propuesta de Solstad2006
   Gonzalo Tejera
   Versión: 1 (Julio, 2012)
 */
public class PlaceCell {
	private double C = 15; // inhibición para balancear la exitación
	private double [] A;	
	/*summing input from grid cells with identical spatial phases, random orientations, 
	 * and random (logarithmically sampled) spacings between 28 and 73 cm
	 */
	
	final double MAX_Z = 0.1; //0.001; //1.5;
	final double MAX_ORIENTATION = 120*Math.PI/180.0;
	final double MAX_SPATIAL_PHASE = 2.0 * Math.PI;
	private static final Point2D.Double DEFAULT_INITIAL_POINT = new Point2D.Double(-20, 10);
	
	public void setLambdas(double [] lambdas) {
		A = new double[lambdas.length]; // pesos para cada una de las salidas de las celdas grillas
		for (int iterCells=0; iterCells<lambdas.length;iterCells++) 
			A[iterCells] = 0.09; //calculateWeight(lambdas[iterCells]); //randA.nextDouble();
	}

	public 	double doStep(double [] gridsActivation) {
		double result = 0, gridActivation;
		
		for (int iterCells=0; iterCells<gridsActivation.length;iterCells++) {
			// To avoid negative firing rates we use the half-wave rectification operation in the conversion from somatic activity to firing rate, i.e., [z]1 1⁄4 z for z ! 0 and 0 otherwise.
			gridActivation = gridsActivation[iterCells];
			if (gridActivation  >= GridCell.ACTIVATION_BIAS)
				result = result + gridActivation * A[iterCells];
		}	
		//System.err.println("PlaceCell::result: "+(result-C)+ ". A[0]:" + A[4]);
		return result - C;
	}
	
	/* Calculo de los pesos para la suma ponderada en funcion de lambda (espaciado de la grilla)
	 * según ecuación 3 de Solstad2006
	 */
	private double calculateWeight(double lambda) {
		final double fMax = Utiles.calculateFrequency(MAX_Z);
		final double gMax = 2; // valor máximo retornado por una grid hasselmo
		final double sigma = 12; //12 mm?
		final double lambdaLower = 28;
		final double lambdaUpper = 73;
		return (fMax/gMax)*2.0*Math.PI*Math.pow(sigma, 2)*Math.exp(-3/4*Math.pow(Math.PI, 2)*Math.pow(sigma, 2)/Math.pow(lambda, 2))/Math.pow(lambda, 2)*2.0*Math.PI/A.length*Math.log(lambdaUpper/lambdaLower);
	}
}
