/* Mdulo NSL que representa la funcionalidad de la rata simulada.
   Alejandra Barrera
   Fecha: 23 de marzo de 2005.
 */

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;

import nslj.src.lang.*;
public class Rat extends NslModule {
	private final int INDEX_CYAN = 0;
	private final int INDEX_MAGENTA = 1;
	private final int INDEX_WHITE = 2;
	private final int INDEX_YELLOW = 3;
	
	
	public static final int MAX_PIXEL_MARCA = Configuration.getInt("Rat.MAX_PIXEL_MARCA");	
	public static final int PIXELES_RUIDO = Configuration.getInt("Rat.PIXELES_RUIDO");
	public static final boolean LIGHT_ON=Configuration.getBoolean("Rat.LIGHT_ON");
	private static final int SEG_EXPLORAR_COMIDA = Configuration.getInt("Rat.SEG_EXPLORAR_COMIDA"); // segundos a explorar la zona de la comida luego de llegar a ella
	public long millisIni;
	public static Simulation simulation= new Simulation();
	
	public NslDinInt0 headAngleRat;
	public NslDinInt2 colorMatrixO;
	public NslDinInt2 colorMatrixR;
	public NslDinInt2 colorMatrixL;

	public NslDoutInt0 currentHeadAngleRat;
	public NslDoutDouble0 distFood;
	public NslDoutInt0 turnToFood;
	public NslDoutDouble1 distLandmarks;
	public NslDoutInt1 angleLandmarks;

	private int angleAux;
	private double distFoodAux;
	private int turnToFoodAux;
	private double distLandmarksAux[];
	private int angleLandmarksAux[];

	public static boolean returning;
	public static int totalTrials;
	public static boolean habituation;
	public static boolean training;
	public static boolean testing;
	public static int leftTrials;
	public static int rightTrials;
	public static boolean newTrial;
	public static boolean reinforcePath;
	public static boolean nextHabituation;
	public static boolean nextTesting;
	public static boolean nextTraining;
	private int actualSimItem=0;
	public static SimulationItem simItem;
	private int panoramica[][];
	private Hashtable <Color, Integer> contadores;
	private boolean hasFindFood= false;
	private LogerPlain log = new LogerPlain(Configuration.getString("Simulation.FILE"));
	
	public Rat(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		//System.out.println("Rat::Inicio");
		totalTrials = 0;
		habituation = false;
		training = false;
		testing = false;
		nextHabituation = false;
		nextTraining = false;
		nextTesting = false;
		leftTrials = 0;
		rightTrials = 0;
		newTrial = true;
		reinforcePath = false;
		returning = false;
		headAngleRat = new NslDinInt0("headAngleRat", this);

		currentHeadAngleRat = new NslDoutInt0("currentHeadAngleRat", this);
		distFood = new NslDoutDouble0("distFood", this);
		distFoodAux = -1D;
		turnToFood = new NslDoutInt0("turnToFood", this);
		turnToFoodAux = -1;
		distLandmarks = new NslDoutDouble1("distLandmarks", this, 4);
		angleLandmarks = new NslDoutInt1("angleLandmarks", this, 4);
		distLandmarksAux = new double[4];
		angleLandmarksAux = new int[4];
		// cargo el primer elemento de habituacion
		simItem=simulation.next();
		System.out.println("Rat::estado: " +simItem.getType()+". Total trials:"+simulation.getNumberTrials());
		millisIni=System.currentTimeMillis();
		switch (simItem.getType()) {
		case (SimulationItem.HABITUATION):
			habituation = true;
			break;
		case (SimulationItem.TRAINING):
			training = true;
			break;
		case (SimulationItem.TESTING):
			testing = true;
			break;
		}
	}

	public void simRun() {
		long currentTime = System.currentTimeMillis();
		
		angleAux = headAngleRat.get();

		panoramica=RobotFactory.getRobot().getPanoramica();
		contadores = Utiles.contadores(panoramica);
        // System.out.println("Rat::Pixeles de comida: " + contAzulFrente);
		// Nmero de veces que llega a la meta en el entrenamiento
		
		if ((RobotFactory.getRobot().findFood())&&(simItem.getTime()==0)) {
			simItem.setTime(SEG_EXPLORAR_COMIDA);
			millisIni=System.currentTimeMillis();
		}

		log.writeln(""+currentTime+log.SEPARATOR+simItem.getName()+log.SEPARATOR+RobotFactory.getRobot().getGlobalCoodinate().x+log.SEPARATOR+RobotFactory.getRobot().getGlobalCoodinate().y);
		if ((simItem.getTime()>0)&&(simItem.getTime()*1000<(currentTime-millisIni))) {
	        	System.err.print("Rat::End trial "+simulation.getCurrenTrial()+"/"+simulation.getNumberTrials()+". "+simItem.getName()+" -> ");
	        	simItem=simulation.next();
	        	//Toolkit.getDefaultToolkit().beep();
	        	Utiles.speak("end trial");
	        	if (simItem==null) {
						System.out.println("Rat::Fin de la simulacion. Genero archivo con grafo.");
						PajekFormat.generateGraph(WorldGraphLayer.map_list);
						PajekFormat.generateMaxExpectedPathGraph(WorldGraphLayer.map_list);
						while (true); //TODO "tranca" simulacion.  
				} else {
		        	System.err.println(simItem.getName()+".");

					switch (simItem.getType()) {
					case (SimulationItem.HABITUATION):
						nextHabituation=true;
						break;
					case (SimulationItem.TRAINING):
						nextTraining=true;
						break;
					case (SimulationItem.TESTING):
						nextTesting = true;
						break;
					}
				} // fin del switch por tipo de elemento de simulacion
				System.out.println("Rat::Nueva etapa.");
				millisIni=System.currentTimeMillis();
		}

		// hasFindFood se utiliza para refozar o decrementar la exploracion solo una vez por ensayo
		if (Rat.newTrial)
			hasFindFood = false;
		
		// Calculo el refuerzo por encontrar comida
		if (RobotFactory.getRobot().findFood()&&!hasFindFood) {
			hasFindFood = true;
			turnToFoodAux = -1;
			distFoodAux = 0;
		} else {
			turnToFoodAux = -1;
			distFoodAux = -1;
		}
		
		Arrays.fill(distLandmarksAux, -1);
		Arrays.fill(angleLandmarksAux, -1);

		// Gonzalo: elimine el case por angulo de la cabeza y creo funcion
		// calculoAngDistLand
		// Calculo de Angulo y distancia para el BLANCO
		calculoAngDistLand(Color.WHITE, INDEX_WHITE);
		// Calculo de Angulo y distancia para el CELESTE
		calculoAngDistLand(Color.CYAN, INDEX_CYAN);
		//System.out.println("Rat::Distancia al celeste: " + distLandmarksAux[INDEX_CYAN]); 
		// Calculo de Angulo y distancia para el AMARILLO
		calculoAngDistLand(Color.YELLOW, INDEX_YELLOW);
		//System.out.println("Rat::Distancia al amarillo: " + distLandmarksAux[INDEX_YELLOW]); 
		// Calculo de Angulo y distancia para el ROSA
		calculoAngDistLand(Color.MAGENTA, INDEX_MAGENTA);
		currentHeadAngleRat.set(angleAux);
		distLandmarks.set(distLandmarksAux);
		angleLandmarks.set(angleLandmarksAux);
		distFood.set(distFoodAux);
		turnToFood.set(turnToFoodAux);
		//System.out.println("RAT: Head direction= " + angleAux +". Comida(d,a)= " + distFoodAux + ", "+ turnToFoodAux);
	} // simRun

	void calculoAngDistLand(Color color, int index) {
		int pixeles=(contadores.get(color)==null)?0:contadores.get(color);
		int posMaxColor;
		if (LIGHT_ON){
			if (pixeles > PIXELES_RUIDO) {
				posMaxColor=Utiles.anguloColor(panoramica, color);
				angleLandmarksAux[index] = posMaxColor;
				distLandmarksAux[index] = pesoCantidadPixeles(pixeles);
				//System.out.println("RAT:Max pixeles = " +pixeles + "angulo: "+angleLandmarksAux[index] + "(color: "+Utiles.toString(color) +").");
			} 
			//else System.out.println("Ruido (color: "+Utiles.toString(color) +").");
		} else {
			angleLandmarksAux[index] = -1;
			distLandmarksAux[index] = -1;			
		}
	}

	int [] rangosDePixeles={PIXELES_RUIDO,220,300,380,460,550,650,800,950,1210};
	
	/* lineal por rangos */
	double pesoCantidadPixelesRango(int cantidad) {
		double result=0;
		int iterRango=1;
		while (result==0&&iterRango<rangosDePixeles.length) {
			result = linealEnRango(cantidad, rangosDePixeles[iterRango-1], rangosDePixeles[iterRango]);
			iterRango++;
		}
		
		result=result+iterRango-1;
		if (cantidad>rangosDePixeles[rangosDePixeles.length-1]) {
			result=1;
		}
		//result = 7+linealEnRango(cantidad, 1300, 2000);
		//result = 8+linealEnRango(cantidad, 2000, MAX_PIXEL_MARCA);
		return result/rangosDePixeles.length;
	}

	private double linealEnRango(int valor, int inicio, int fin) {
		double result;
		if (valor>fin||valor<inicio) result=0;
		else result=(valor-inicio)/(double)(fin-inicio);
		return result;
	}
	
	/* lineal acotado a MAX_VALOR_PESO_PIXEL */
	double pesoCantidadPixeles(int cantidad) {
		double result = MAX_PIXEL_MARCA-cantidad;
				
        if (result<0) result = 0;
        else result=result/(double) MAX_PIXEL_MARCA;
        return result;
	}

}
