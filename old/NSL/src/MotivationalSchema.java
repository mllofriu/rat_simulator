/* M�dulo NSL que representa el esquema motivacional de la rata. 
   Alejandra Barrera
   Versi�n: 2
   Fecha: 19 de abril de 2005
 */

import nslj.src.lang.*;

import java.util.Arrays;
import java.util.Random;

public class MotivationalSchema extends NslModule {
	public NslDinInt0 turnToFood;
	public NslDinDouble1 affPS;
	public NslDinInt0 currentHeadAngleRat;
	public NslDinDouble1 expCycling;
	public NslDinDouble1 curiosityCycling;
	public NslDoutInt0 AngleToGo;
	public NslDoutDouble1 wg;
	public NslDoutDouble1 curiosity;
	public NslDoutDouble1 sum;
	public NslDoutDouble1 food;
	private PerceptualSchema APS;
	private PerceptualSchema EMR;
	private PerceptualSchema F4;
	private PerceptualSchema RPS;
	private PerceptualSchema F7;
	private PerceptualSchema F5;
	private PerceptualSchema CPS;
	private PerceptualSchema F18;
	private PerceptualSchema F19;
	private PerceptualSchema F20;
	private double u[];
	private double U[];
	private double curiosityAux[];
	private double foodAux[];
	private double wgAux[];
	private double sumAux[];
	private double affAux[];
	private int Angle;
	private double Gradient[] = { 15D, 15D, 15D, 14D, 14D, 13D, 13D, 12D, 12D,
			11D, 11D, 11D, 10D, 10D, 10D, 9D, 9D, 9D, 8D, 8D, 8D, 7D, 7D, 7D,
			6D, 6D, 6D, 5D, 5D, 5D, 4D, 4D, 4D, 3D, 3D, 3D, 2D, 2D, 1.0D, 1.0D,
			0, -1D, -1D, -2D, -2D, -3D, -3D, -3D, -4D, -4D, -4D, -5D, -5D, -5D,
			-6D, -6D, -6D, -7D, -7D, -7D, -8D, -8D, -8D, -9D, -9D, -9D, -10D,
			-10D, -10D, -11D, -11D, -11D, -12D, -12D, -13D, -13D, -14D, -14D,
			-15D, -15D };
	private int turnToFoodAux;
	private boolean inicio;

	// private final double RPS_TESTING =
	// Configuration.getDouble("MotivationalSchema.RPS_TESTING");
	// private final double RPS_TRAINING_MAX =
	// Configuration.getDouble("MotivationalSchema.RPS_TRAINING_MAX");
	// private final double RPS_TRAINING_MIN =
	// Configuration.getDouble("MotivationalSchema.RPS_TRAINING_MIN");
	// private final double RPS_HEIGHT = Configuration
	// .getDouble("MotivationalSchema.RPS_HEIGHT");
	// TODO: el RPS es debe ser muy chico para desempatar campanas iguales
	private final double RPS_HEIGHT = Configuration.getDouble("MotivationalSchema.RPS_HEIGHT");// Double.MIN_NORMAL;

	private int currentDir;

	private MotivationalChats motChats;
	private int absoluteRPSdir;
	private int posibleAff;

	public MotivationalSchema(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		turnToFood = new NslDinInt0("turnToFood", this);
		affPS = new NslDinDouble1("affPS", this, 80);
		expCycling = new NslDinDouble1("expCycling", this, 8);
		curiosityCycling = new NslDinDouble1("curiosityCycling", this, 8);
		currentHeadAngleRat = new NslDinInt0("currentHeadAngleRat", this);
		AngleToGo = new NslDoutInt0("AngleToGo", this);
		wg = new NslDoutDouble1("wg", this, 5);
		curiosity = new NslDoutDouble1("curiosity", this, 5);
		food = new NslDoutDouble1("food", this, 5);
		sum = new NslDoutDouble1("sum", this, 5);

		APS = new PerceptualSchema(80, 1);
		EMR = new PerceptualSchema(80, 1);
		F4 = new PerceptualSchema(80, 1);
		RPS = new PerceptualSchema(80, 1);
		F7 = new PerceptualSchema(80, 1);
		F5 = new PerceptualSchema(80, 1);
		CPS = new PerceptualSchema();
		F18 = new PerceptualSchema(80, 1);
		F19 = new PerceptualSchema(80, 1);
		F20 = new PerceptualSchema(80, 1);
		u = new double[80];
		U = new double[80];
		affAux = new double[80];
		curiosityAux = new double[5];
		foodAux = new double[5];
		wgAux = new double[5];
		sumAux = new double[5];

		turnToFoodAux = -1;
		inicio = true;
		motChats = new MotivationalChats();
	}

	public int angle2direction(int i) {
		switch (i) {
		case 0:
			return 0;
		case 45:
			return 1;
		case 90:
			return 2;
		case 135:
			return 3;
		case 180:
			return 4;
		case 225:
			return 5;
		case 270:
			return 6;
		case 315:
			return 7;
		}
		return -1;
	}

	// genera el esquema perceptual de affordances (APS) y lo deja en APS
	public void initConditions(double psAff[]) {
		APS.zero();
		// // TODO: si estoy en un corredor no permito giros 180
		// if (Rat.newTrial || (psAff[PerceptualSchema.POS_GAUSS_N90] != 1)
		// && (psAff[PerceptualSchema.POS_GAUSS_P90] != 1)
		// && (psAff[PerceptualSchema.POS_GAUSS_0] == 1)) {
		// TODO: solo si es la única opción permito el giro 180
		 if ((psAff[PerceptualSchema.POS_GAUSS_N90] == 1)
		 || (psAff[PerceptualSchema.POS_GAUSS_P90] == 1)
		 || (psAff[PerceptualSchema.POS_GAUSS_0] == 1)) {
		//if (!WorldGraphLayer.cambioNodoActual) {
			psAff[PerceptualSchema.POS_GAUSS_N180] = 0;
			psAff[PerceptualSchema.POS_GAUSS_P180] = 0;
			posibleAff = 0;
		} else
			posibleAff = 1;
		if (!WorldGraphLayer.cambioNodoActual) {
			psAff[PerceptualSchema.POS_GAUSS_N180] = 1;
			psAff[PerceptualSchema.POS_GAUSS_P180] = 1;
		}
			
		for (int i1 = 0; i1 < IRobot.CANT_ACCIONES; i1++)
			if (psAff[PerceptualSchema.POS_GAUSS[i1]] == 1) {
				APS.addLineal(1, PerceptualSchema.POS_GAUSS[i1], 2);
				posibleAff++;
			}
		
		//System.out.println("MotivationalSchema::"+posibleAff);
		F19.zero();
	}
	public void createRPS(int turnToFoodAux) {
		double altura = RPS_HEIGHT, d = 4D;
		Random random = new Random();
		int currenAff=0;
		int accionesRandom = IRobot.CANT_ACCIONES - 1; // -1 para evitar que 180
		// y -180 se consideren
		// acciones diferetes y
		// le asigne entonces
		// mayor probabilidad a
		// los giros 180.
		int relativeRPSdir;
		// aff contiene las direcciones absolutas de giros
		int[] aff = new int[IRobot.CANT_ACCIONES];

		RPS.zero();
		// Si puedo girar hacia la comida entonces coloco en RPS una gaussiana
		// que obligue el giro hacia ella
		switch (turnToFoodAux) {
		case -90:
			RPS.addLineal(altura, PerceptualSchema.POS_GAUSS_N90, d);
			break;
		case 0:
			RPS.addLineal(altura, PerceptualSchema.POS_GAUSS_0, d);
			break;
		case 90:
			RPS.addLineal(altura, PerceptualSchema.POS_GAUSS_P90, d);
			break;
		// si no puedo girara hacia la comida
		case -1:
			// if (posibleAff > 1) { // si no hay opciones que elegir entoneces
			// no gasto un random
//			if (WorldGraphLayer.cambioNodoActual) {// si no cambio el nodo mantengo accion random elegida
			// el siguiente if no se en que momento aparecio pero esta mal :D
//			if ((APS.act[PerceptualSchema.POS_GAUSS[Utiles.absolute2relative(
//					currentDir, absoluteRPSdir)]][0] < 1)) {
				// elijo una direccion a azar dentro de los posibles giros
				currenAff=0;
				// NO elimino 180s
				for (int direction = 0; direction < (IRobot.CANT_ACCIONES); direction++) {
					if (APS.act[PerceptualSchema.POS_GAUSS[direction]][0] >= 1) {
						aff[currenAff] =direction ;
						currenAff++;
					}
				}
				
				//System.out.println("MotivationalSchema::"+currenAff);
				// sorteo una de las gaussianas posibles
				relativeRPSdir = aff[random.nextInt(currenAff)];

				absoluteRPSdir = Utiles.relativa2absolute(currentDir,
						relativeRPSdir);

//				System.out.println("MS::SET abs: " + currentDir + ". relGir: "
//						+ relativeRPSdir + " . absRPS: " + absoluteRPSdir+" fAbsRel: "+ Utiles.absolute2relative(currentDir, absoluteRPSdir));
//			} // cambio nodo actual
//			if (APS.act[PerceptualSchema.POS_GAUSS[Utiles.absolute2relative(
//					currentDir, absoluteRPSdir)]][0] < 1)
//				System.out.println("~~~~~~~~~~~~~~~~~~MS::GET abs: "
//						+ currentDir
//						+ ". relGir(f): "
//						+ Utiles.absolute2relative(currentDir, absoluteRPSdir)
//						+ " . absRPS: "
//						+ absoluteRPSdir
//						+ ". APS: "
//						+ APS.act[PerceptualSchema.POS_GAUSS[Utiles.absolute2relative(currentDir,
//								absoluteRPSdir)]][0]);
//			else System.out.println("MS::GET abs: "
//					+ currentDir
//					+ ". relGir(f): "
//					+ Utiles.absolute2relative(currentDir, absoluteRPSdir)
//					+ " . absRPS: "
//					+ absoluteRPSdir
//					+ ". APS: "
//					+ APS.act[PerceptualSchema.POS_GAUSS[Utiles.absolute2relative(currentDir,
//							absoluteRPSdir)]][0]);

			RPS.addLineal(altura, PerceptualSchema.POS_GAUSS[Utiles
					.absolute2relative(currentDir, absoluteRPSdir)], d);
			// } // if posibleAff > 1?
		} // switch
	}


	/*
	 * i: direccion actual de la rata
	 */
	public void compute_curiosity(double aflag[]) {
		double d1 = 4;
		int relativeDir;
		CPS.zero();
		double d = 1;

		int girosPosibles = 0;

		for (int direction = 0; direction < 8; direction++) {
			relativeDir = Utiles.absolute2relative(currentDir, direction);
			if ((relativeDir != 0)
					&& (relativeDir != (IRobot.CANT_ACCIONES - 1))
					&& (aflag[direction] != 0)
					&& APS.act[PerceptualSchema.POS_GAUSS[relativeDir]][0] >= 1) {
				CPS.addLineal(d * (double) aflag[direction],
						PerceptualSchema.POS_GAUSS[relativeDir], d1);
				girosPosibles++;
			}
		}
		// System.out.println("MotivationalSchema::curiosidad: "+d+". #giros: "+girosPosibles+". dirAct: "+currentDir);
	}

	/*
	 * calcula EMR viejo pp 36 dirAct:direccion actual, exp: expCycling,
	 * flagIni: falg cycling, psAff: affordance
	 */
	public void computeEMR(double exp[]) {
		double d1 = 4;
		EMR.zero();
		int relativeDir;
		// elimino los bordes 1 y IRobot.CANT_ACCIONES-1 para eliminar giros 180

		EMR.zero();
		for (int direction = 0; direction < IRobot.CANT_ACCIONES; direction++) {
			relativeDir = Utiles.absolute2relative(currentDir, direction);
			if (APS.act[PerceptualSchema.POS_GAUSS[relativeDir]][0] >= 1.0) {
				EMR.addLineal(exp[direction],
						PerceptualSchema.POS_GAUSS[relativeDir], d1);
			}
		}

	}

	public void computeInputToActionSelection() {
		Angle = 0;
		APS.max = 0.0D;
		APS.min = 0.0D;
		EMR.max = 0.0D;
		EMR.min = 0.0D;
		F4.max = 0.0D;
		F4.min = 0.0D;
		F5.max = 0.0D;
		F5.min = 0.0D;
		RPS.max = 0.0D;
		RPS.min = 0.0D;
		F7.max = 0.0D;
		F7.min = 0.0D;
		CPS.max = 0.0D;
		CPS.min = 0.0D;
		F18.max = 0.0D;
		F18.min = 0.0D;
		F19.max = 0.0D;
		F19.min = 0.0D;
		F20.max = 0.0D;
		F20.min = 0.0D;
		for (int k = 0; k < 80; k++)
			affAux[k] = APS.act[k][0];
		for (int k = 0; k <= 8; k++)
			affAux[k] -= (affAux[k] / 2);
		for (int k = 72; k <= 79; k++)
			affAux[k] -= (affAux[k] / 2);
		// if (Rat.habituation)
		// for (int k = 0; k < 80; k++)
		// // u[k] = APS.act[k][0] + RPS.act[k][0] + CPS.act[k][0];
		// u[k] = RPS.act[k][0] + CPS.act[k][0];
		// else {
		// for (int k = 0; k < 80; k++)
		// u[k] = RPS.act[k][0] + CPS.act[k][0] + EMR.act[k][0];
		// //
		// System.out.println("MS:sumando todo ......................................");
		// }

		for (int k = 0; k < 80; k++)
			// u[k] = RPS.act[k][0] + CPS.act[k][0] + EMR.act[k][0];
			u[k] = APS.act[k][0] + RPS.act[k][0] + CPS.act[k][0]
					+ EMR.act[k][0];

		double d4 = Integer.MIN_VALUE;
		int i4 = 0;
		for (int l1 = 0; l1 < 80; l1++) {
			U[l1] = 0.0D;
			if (u[l1] > d4) {
				d4 = u[l1];
				i4 = l1;
			}
		}
		if (d4 > 0) {
			for (int i2 = 0; i2 < 80; i2++)
				if (i2 != i4)
					U[i2] = 0.0D;
				else
					U[i2] = 1.0D;
		}
		// By Gonzalo
		// for (int j2 = 0; j2 < 80; j2++) {
		// F5.act[j2][0] = Gradient[j2] * U[j2];
		// Angle += (int) F5.act[j2][0];
		// }
		
		// i4 es el índice con mayor utilidad u[i4] es el elemento más grande.
//		if (Rat.newTrial)
//			System.err.println("MS::index: " +i4+ ", angulo: "+Angle);
		
		Angle = (int) Gradient[i4];

		for (int k2 = 0; k2 < 80; k2++)
			F5.act[k2][0] = u[k2];
		for (int l2 = 0; l2 < 80; l2++) {
			if (APS.max < APS.act[l2][0])
				APS.max = APS.act[l2][0];
			if (EMR.max < EMR.act[l2][0])
				EMR.max = EMR.act[l2][0];
			if (RPS.max < RPS.act[l2][0])
				RPS.max = RPS.act[l2][0];
			if (F18.max < F18.act[l2][0])
				F18.max = F18.act[l2][0];
			if (CPS.max < CPS.act[l2][0])
				CPS.max = CPS.act[l2][0];
			if (F19.max < F19.act[l2][0])
				F19.max = F19.act[l2][0];
			if (F20.max < F20.act[l2][0])
				F20.max = F20.act[l2][0];
			if (F7.min > F7.act[l2][0])
				F7.min = F7.act[l2][0];
			if (F4.min > F4.act[l2][0])
				F4.min = F4.act[l2][0];
			if (F19.min > F19.act[l2][0])
				F19.min = F19.act[l2][0];
			if (EMR.min > EMR.act[l2][0])
				EMR.min = EMR.act[l2][0];
			double d = F5.act[l2][0];
			if (d < 0.0D)
				d *= -1D;
			if (F5.max < d)
				F5.max = d;
		}

		for (int i3 = 0; i3 < 80; i3++)
			if (F5.max > 0.0D && F5.act[i3][0] < 0.0D)
				F5.act[i3][0] *= -1D;

		for (int j3 = 0; j3 < 80; j3++) {
			F19.act[j3][0] += -1D * F19.min;
			if (F19.max < F19.act[j3][0])
				F19.max = F19.act[j3][0];
			F4.act[j3][0] += -1D * F4.min;
			if (F4.max < F4.act[j3][0])
				F4.max = F4.act[j3][0];
			F7.act[j3][0] += -1D * F7.min;
			if (F7.max < F7.act[j3][0])
				F7.max = F7.act[j3][0];
		}
	}

	public void simRun() {
		turnToFoodAux = turnToFood.get();

		currentDir = angle2direction(currentHeadAngleRat.get());

		initConditions(affPS.get());

		compute_curiosity(curiosityCycling.get());
		createRPS(turnToFoodAux);

		if (!Rat.habituation) {
			computeEMR(expCycling.get());
			// WG_influence(currentDir, expCycling.get(), flagCyclingAuxB,
			// affPS.get());
		}

		computeInputToActionSelection();
		motChats.setPerceptualSchema(APS.act, RPS.act, CPS.act, EMR.act, u);

		int aux = 0;

		while (aux < 80 && U[aux] != 1)
			aux++;
		if (aux < 80) {
//			System.out.println("Mayor-U[" + aux + "]= " + U[aux] + ". Angle: "
//					+ Angle);
			if (APS.act[aux][0] < 1.0)
				System.out.println("$$$$$$$ APSmax: " + APS.act[aux][0]
						+ "RPSmax: " + RPS.act[aux][0] + "CPSmax: "
						+ CPS.act[aux][0] + "EMRmax: " + EMR.act[aux][0]);
		} else
			System.out.println("$$$##############$$$$$$$$$$$$$$$$$$$$$$ aux: " + aux + "Angle: " + Angle);

		int m, n;
		for (m = 0, n = 4; m < 5 && n < 80; m++, n += 18) {
			curiosityAux[m] = CPS.act[n][0];
			foodAux[m] = RPS.act[n][0];
			wgAux[m] = EMR.act[n][0];
			sumAux[m] = u[n];
		}
		
		AngleToGo.set(Angle);
		wg.set(wgAux);
		curiosity.set(curiosityAux);
		food.set(foodAux);
		sum.set(sumAux);
	} // simRun
}
