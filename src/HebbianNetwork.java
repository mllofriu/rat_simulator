


import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import support.Configuration;

import nslj.src.lang.NslDinDouble1;
import nslj.src.lang.NslDinDouble2;
import nslj.src.lang.NslDoutDouble1;
import nslj.src.lang.NslDoutDouble2;
import nslj.src.lang.NslModule;

/**
 * @author gtejera
 * @version 1
 */
public class HebbianNetwork extends NslModule {
	public static double LEARNING_RATE =  Configuration.getDouble("HebbianNetwork.LEARNING_RATE"); //0.0001; //0.00001; //
	public static double MAX_RANDOM_WEIGHT =  Configuration.getDouble("HebbianNetwork.MAX_RANDOM_WEIGHT"); //0.0001; //0.00001; //
	private static double CONNECTIVITY_RATE = 1; //0.5;
	private double weights[][]; // pesos de las aristas de la red
	private double activation[]; // activacion/salida de la red
	private int inSize; // cantidad de neuronas de entrada
	private int outSize; // cantidad de neuronas de la capa de salida
	private static final String DEFAULT_MODULE_NAME = "Hebbian Network (nombre por defecto)";
	private static final double BIAS = 0;
	private static final double ACTIVATION_THRESHOLD = Configuration.getDouble("HebbianNetwork.ACTIVATION_THRESHOLD"); //4.2; //5
	//private static final int GRIDS_PER_PLACE = 60;

	// las entradas a la red hebbiana
	public NslDinDouble1 inputsNSL;
	// la salida de esta capa es un array con los disparos de cada una de las celdas
	public NslDoutDouble1 activationNSL;

	/*	
	 * Reciebe como parametros la cantidad de neuronas de entrada y de salida
	 */
	public HebbianNetwork(NslModule nslParent, int inSize, int outSize) {
		this(DEFAULT_MODULE_NAME, nslParent, inSize, outSize, CONNECTIVITY_RATE);
	}
	
	public HebbianNetwork(String nslName, NslModule nslParent, int inSize, int outSize) {
		this(nslName, nslParent, inSize, outSize, CONNECTIVITY_RATE);
	}
	
	public HebbianNetwork(String nslName, NslModule nslParent, int inSize, int outSize, double connectivityRate) {
		super(nslName, nslParent);

		Random random;

		this.inSize = inSize;
		this.outSize = outSize;
		CONNECTIVITY_RATE = connectivityRate;
		weights = new double[inSize][outSize];
		activation = new double[outSize];
		inputsNSL = new NslDinDouble1("inputsNSL", this, inSize);
		activationNSL = new NslDoutDouble1("activationNSL", this, outSize);
				
		// inicializo los pesos de la red

		for (int iterOut = 0; iterOut < outSize; iterOut++) {
			random = new Random();
			for (int iterIn = 0; iterIn < inSize; iterIn++) {
				if (random.nextDouble() > CONNECTIVITY_RATE)
					weights[iterIn][iterOut] = 0;
				else {
					weights[iterIn][iterOut] = random.nextDouble() * HebbianNetwork.MAX_RANDOM_WEIGHT;
				}
			}
		}
		
		// conexiones sin repeticion
//		Vector<Integer> integerList = new Vector<Integer>();
//		for (int iterIn = 0; iterIn < inSize; iterIn++)
//			integerList.add(iterIn);
//		Collections.shuffle(integerList);
//
//		for (int iterOut = 0; iterOut < outSize; iterOut++) 
//			for (int iterIn = 0; iterIn < GRIDS_PER_PLACE; iterIn++) {
//				System.err.println("HOLA::"+iterOut + " " + iterIn+ " . "+ (iterOut*GRIDS_PER_PLACE+iterIn)+"."+integerList.elementAt(iterOut*GRIDS_PER_PLACE+iterIn));
//				weights[integerList.elementAt(iterOut*GRIDS_PER_PLACE+iterIn)][iterOut] = random.nextDouble() * 0.00000001;
//			}
		
		// Soldstar2009
//		for (int iterIn = 0; iterIn < inSize; iterIn++)
//		for (int iterOut = 0; iterOut < outSize; iterOut++) 
//				weights[iterIn][iterOut] = 0.09;
		
		// Normalizo pesos para cada neurona de salida
		for (int iterOut = 0; iterOut < outSize; iterOut++) {
			normalize(iterOut);
		}
		System.out.println("HebbianNetwork::Red: " + nslName + ". Size(in,out): " + inSize + ", " + outSize + ". connectivity: " + connectivityRate +"." );
	}


	public void simRun() {
		double[] input = inputsNSL.get();
		calculateActivation(input);
		train(input);
//		int numInputFire=0;
//		for (int iterIn = 0; iterIn < inSize; iterIn++) 
//			if (input[iterIn]!=0) numInputFire++;
//		System.err.println("HebbianLearning::cantidad de entradas activas" + numInputFire);

		// la activacion de salida usa los pesos anteriores no los actualizados en la llamada a train anterior
		activationNSL.set(activation);
	}

	void train(double[] input) {
		double aveInputs = 0;
		for (int iterIn = 0; iterIn < inSize; iterIn++)
			aveInputs = aveInputs +input[iterIn];
		aveInputs = aveInputs / inSize;
		
		for (int iterOut = 0; iterOut < outSize; iterOut++) {
			for (int iterIn = 0; iterIn < inSize; iterIn++) {
				// si no había conexion no permito que se cree
				if (weights[iterIn][iterOut]!=0) {
					weights[iterIn][iterOut] = weights[iterIn][iterOut] + LEARNING_RATE
					* (input[iterIn]-aveInputs) * activation[iterOut];
					weights[iterIn][iterOut] = weights[iterIn][iterOut]<0?0:weights[iterIn][iterOut]; //Pi2009 pesos positivos
				}
			}
			normalize(iterOut);
		}
	}

	/**
	 * @param iterOut
	 */
	private void normalize(int iterOut) {
		double sumWeights = 0;
		for (int iterIn = 0; iterIn < inSize; iterIn++)
			sumWeights = sumWeights + weights[iterIn][iterOut]; //Math.pow(weights[iterIn][iterOut],2); //Pi2009 normalizo l cuadrado de los pesos
		for (int iterIn = 0; iterIn < inSize; iterIn++)
//			weights[iterIn][iterOut] = Math.pow(weights[iterIn][iterOut],2) / sumWeights;
			weights[iterIn][iterOut] = weights[iterIn][iterOut] / sumWeights;
	}

	void calculateActivation(double[] input) {
		double maxActivation=0;
		for (int iterOut = 0; iterOut < outSize; iterOut++) {
			activation[iterOut] = 0;
			for (int iterIn = 0; iterIn < inSize; iterIn++) {
				if (input[iterIn]>HasselmoGridCellLayer.DEFAULT_MIN_ACTIVATION)
					activation[iterOut] = activation[iterOut] + input[iterIn]
							* weights[iterIn][iterOut];
			}
			activation[iterOut] = activation[iterOut] + BIAS;
			maxActivation=Math.max(maxActivation,activation[iterOut]);
			if (activation[iterOut]<ACTIVATION_THRESHOLD)
				activation[iterOut] = 0;
			else
				activation[iterOut] = Math.pow(activation[iterOut], 2);

		}
		//System.err.println("HebbianLearning::maxima activacion: " + maxActivation);
	}
}
