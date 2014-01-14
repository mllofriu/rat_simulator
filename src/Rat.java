/* Mdulo NSL que representa la funcionalidad de la rata simulada.
   Alejandra Barrera
   Fecha: 23 de marzo de 2005.
 */

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.text.DecimalFormat;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDinInt2;
import nslj.src.lang.NslDoutDouble0;
import nslj.src.lang.NslDoutDouble1;
import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import robot.RobotFactory;
import simulation.Simulation;
import simulation.SimulationItem;
import support.Configuration;
import support.LogerPlain;
import support.Percentage;
public class Rat extends NslModule {
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
	public NslDoutDouble1 angleLandmarks;

	private int angleAux;
	private double distFoodAux;
	private int turnToFoodAux;
	private double distLandmarksAux[];
	private double angleLandmarksAux[];

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
	private boolean hasFindFood= false;
	private LogerPlain log = new LogerPlain(Configuration.getString("Simulation.FILE"));
	private LogerPlain logResumen = new LogerPlain("Resumen"+Configuration.getString("Simulation.FILE"));
	// tiempo de inicio de la simulación
	private long milisInit = System.currentTimeMillis();
	
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
//		distLandmarks = new NslDoutDouble1("distLandmarks", this, LandmarksPerceptualSchema.LANDMARK_NUMBER);
//		angleLandmarks = new NslDoutDouble1("angleLandmarks", this, LandmarksPerceptualSchema.LANDMARK_NUMBER);
//		distLandmarksAux = new double[LandmarksPerceptualSchema.LANDMARK_NUMBER];
//		angleLandmarksAux = new double[LandmarksPerceptualSchema.LANDMARK_NUMBER];
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

		log.writeln("time"+log.SEPARATOR+"ensayo"+ log.SEPARATOR+"nombre"+log.SEPARATOR+"x"+log.SEPARATOR+"y"+log.SEPARATOR+"angulo");
		logResumen.writeln("ensayo"+ log.SEPARATOR+"epocas"+ log.SEPARATOR+"nombre"+log.SEPARATOR+"latencia"+log.SEPARATOR+"distancia"+log.SEPARATOR+"I"+log.SEPARATOR+"II"+log.SEPARATOR+"III"+log.SEPARATOR+"IV");
		coordAnt = RobotFactory.getRobot().getGlobalCoodinate();
	}

	// attributos usados para la estadísticas de morris
	DecimalFormat df = new DecimalFormat("#.##");
	private long latencia = 0; // lleva la latencia en milisegundos para cada ensayo
	private long tiempoPasoAnterior = System.currentTimeMillis(); // almacena el tiempo del sistema para el paso anterior de simulación
	private double largoCamino = 0; //lleva el largo del camino en las unidades dadas por RobotFactory.getRobot().getGlobalCoodinate() para cada ensayo
	private long [] tiempoCuadrantes = new long[4]; // lleva el porcentaje de tiempo en cada cuadrante
	private Double coordAnt;
	private long epocasPorEnsayo = 0; // lleva la cuenta de la cantidad de pasos de simulación para el ensayo actual
	private Percentage porcentaje = new Percentage("Rat", 10); // para mostrar porcentaje del avance de una estapa de tiempo
	
	public void simRun() {
//		long currentTime = System.currentTimeMillis();
//		int cuadrante = Utiles.getCuadrante(coordAnt); // Ojo que este metodo tiene memoria para balancear los bordes de los cuadrantes, no llamar varias veces para un mismo punto en una misma actualizacion
//		IRobot robot = RobotFactory.getRobot();
//		Double robotCoors = robot.getGlobalCoodinate();
//		angleAux = headAngleRat.get();
//		epocasPorEnsayo++;
//		
//		if (Rat.newTrial) {
//			latencia = 0;
//			largoCamino = 0;
//			Arrays.fill(tiempoCuadrantes, 0);
//			tiempoPasoAnterior = currentTime;
//		} else {
//			// actualizo información para estadísticas de morris
//			latencia = latencia + currentTime - tiempoPasoAnterior;
//			largoCamino = largoCamino + robotCoors.distance(coordAnt);
//			// tiempo en cuadrante
//			//tiempoCuadrantes[cuadrante]=tiempoCuadrantes[cuadrante]+currentTime - tiempoPasoAnterior;
//			//tiempoPasoAnterior = currentTime;
//			// cantidad de acciones por cuadrante
//			tiempoCuadrantes[cuadrante]=tiempoCuadrantes[cuadrante]+1;
//		}
//		coordAnt = robotCoors;
//		
//        // System.out.println("Rat::Pixeles de comida: " + contAzulFrente);
//		// Nmero de veces que llega a la meta en el entrenamiento
//		//System.err.println("WGL::porcentaje exploracion: " + (100*WorldGraphLayer.map_list.size()/WorldGraphLayer.MAX_NODOS)+". Latencia: "+(currentTime-milisInit));
//		// Cuando encuentra la comida se deja unos segundos reconociemdo el lugar para luego finalizar el ensayo
//		if ((robot.findFood())&&(simItem.getTime()==0)) {
//			simItem.setTime(epocasPorEnsayo+SEG_EXPLORAR_COMIDA);
//			//millisIni=System.currentTimeMillis();
//		}
//		//System.err.println("Rat::Coordenadas: "+robotCoors.x+", "+robotCoors.y + ". Direccion: "+ robot.getGlobalDirection());
//		
//		log.writeln(""+currentTime+log.SEPARATOR+(simulation.getCurrenTrial()-1)+log.SEPARATOR+simItem.getName()+ log.SEPARATOR+df.format(robotCoors.x)+log.SEPARATOR+df.format(robotCoors.y)+log.SEPARATOR+df.format(robot.getGlobalDirection()));
////		if ((simItem.getTime()>0)&&(simItem.getTime()*1000<(currentTime-millisIni))) { // en milisegundos
//		if ((simItem.getTime()>0)&&(simItem.getTime()<epocasPorEnsayo)) {
//	        	System.err.print("Rat::End trial "+(simulation.getCurrenTrial()-1)+"/"+simulation.getNumberTrials()+". "+simItem.getName()+" -> ");
//	        	// escribo en el archivo de loss las estadisticas de morris para este ensayo	
//	        	logResumen.writeln(""+ (simulation.getCurrenTrial()-1)+ log.SEPARATOR+epocasPorEnsayo+log.SEPARATOR+simItem.getName()+log.SEPARATOR+latencia+log.SEPARATOR+largoCamino+log.SEPARATOR+tiempoCuadrantes[0]+log.SEPARATOR+tiempoCuadrantes[1]+log.SEPARATOR+tiempoCuadrantes[2]+log.SEPARATOR+tiempoCuadrantes[3]);
//	        	simItem=simulation.next();
//	        	epocasPorEnsayo=0;
//	        	
//	        	if (simItem==null) {
//						System.out.println("Rat::Fin de la simulacion. Genero archivo con grafo.");
//						PajekFormat.generateGraph(WorldGraphLayer.map_list);
//						PajekFormat.generateMaxExpectedPathGraph(WorldGraphLayer.map_list);
//						Utiles.speak("finish");
//						log.close();
//						logResumen.close();
//						while (true); //TODO "tranca" simulacion.  
//				} else {
//					Utiles.speak("end trial");
//		        	System.err.println(simItem.getName()+".");
//
//					switch (simItem.getType()) {
//					case (SimulationItem.HABITUATION):
//						nextHabituation=true;
//						break;
//					case (SimulationItem.TRAINING):
//						nextTraining=true;
//						break;
//					case (SimulationItem.TESTING):
//						nextTesting = true;
//						break;
//					default:
//						assert true:"Tipo de ensayo erroneo";
//						System.out.println("Rat::Nueva etapa.");
//					
//					}
//				} // fin del switch por tipo de elemento de simulacion
//				System.out.println("Rat::Nueva etapa.");
//				millisIni=System.currentTimeMillis();
//		}
//
//		porcentaje .printPorcentage(epocasPorEnsayo, simItem.getTime());
//		
//		// hasFindFood se utiliza para refozar o decrementar la exploracion solo una vez por ensayo
//		if (Rat.newTrial)
//			hasFindFood = false;
//		
//		// Calculo el refuerzo por encontrar comida
//		if (robot.findFood()&&!hasFindFood) {
//			hasFindFood = true;
//			turnToFoodAux = -1;
//			distFoodAux = 0;
//		} else {
//			turnToFoodAux = -1;
//			distFoodAux = -1;
//		}
//		
//		Arrays.fill(distLandmarksAux, -1);
//		Arrays.fill(angleLandmarksAux, -1);
//
//		Double[] landmarks = robot.findLandmarks();
//		
//		// Calculo de Angulo y distancia para cada marca
//		for (int iterLand=0; iterLand<LandmarksPerceptualSchema.LANDMARK_NUMBER;iterLand++)
//			calculoAngDistLand(iterLand, landmarks[iterLand]);
//		
//		currentHeadAngleRat.set(angleAux);
//		distLandmarks.set(distLandmarksAux);
//		angleLandmarks.set(angleLandmarksAux);
//		distFood.set(distFoodAux);
//		turnToFood.set(turnToFoodAux);
		//System.out.println("RAT: Head direction= " + angleAux +". Comida(d,a)= " + distFoodAux + ", "+ turnToFoodAux);
	} // simRun

	void calculoAngDistLand(int index, Point2D.Double landPosSize) {
		if (LIGHT_ON && (landPosSize!=null)) {
			angleLandmarksAux[index] = landPosSize.x;
			distLandmarksAux[index] = landPosSize.y;
			// System.out.println("RAT:Max pixeles = " +pixeles +
			// "angulo: "+angleLandmarksAux[index] +
			// "(color: "+Utiles.toString(color) +").");
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
