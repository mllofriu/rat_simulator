/* Mdulo que gerera el esquema perceptual de affordances.	
   Alejandra Barrera Versin: 1 (enero, 2005)
   Gonzalo Tejera Versin: 1.2 (agosto, 2009)
 */

import robot.IRobot;
import robot.RobotFactory;
import support.Utiles;
import nslj.src.lang.*;

public class AffPerceptualSchema extends NslModule {
	/*
	 * -180 pos 4 -135 pos 13 -90 pos 22 -45 pos 31 0 pos 40 45 pos 90 pos 58
	 * 180 pos 76
	 */
	public NslDinInt0 headAngleRat;

	public NslDoutDouble1 affPS;
	public NslDoutInt0 currentHeadAngleRat;

	private int sizePS;
	private double affPSAux[];
	private int headAngle;
	private static boolean affCurrent[];
	private static boolean affPrev[];
	public static boolean sameAff;
	public static boolean differentAff;
	private IRobot robot;
	private boolean[] affRobot;

	public AffPerceptualSchema(String nslName, NslModule nslParent, int s) {
		super(nslName, nslParent);
		sizePS = s;
		headAngleRat = new NslDinInt0("headAngleRat", this);
		affPS = new NslDoutDouble1("affPS", this, sizePS);
		currentHeadAngleRat = new NslDoutInt0("currentHeadAngleRat", this);

		affPSAux = new double[sizePS];
//		affCurrent = new boolean[4];
//		affPrev = new boolean[4];
//		for (int i = 0; i < 4; i++) {
//			affCurrent[i] = false;
//			affPrev[i] = false;
//		}
	//	sameAff = false;
	//	differentAff = false;
	}

	public void simRun() {
		boolean hayAff = false;
		headAngle = headAngleRat.get();
//		for (int j = 0; j < 4; j++)
//			affPrev[j] = affCurrent[j];
		for (int i = 0; i < sizePS; i++)
			affPSAux[i] = 0.0;
		double d1 = 3D;

		affRobot = RobotFactory.getRobot().affordances();
		// el affordance de avanzar lo apago si llegue a la comida
		if  (RobotFactory.getRobot().findFood()&&!Rat.habituation)
			affRobot[Utiles.gradosRelative2Acccion(0)]=false;
		
		for (int i=0; i<PerceptualSchema.POS_GIROS.length;i++)
			if (affRobot[Utiles.gradosRelative2Acccion(PerceptualSchema.POS_GIROS[i])]){
				hayAff = true;
				
				for (int i1 = 0; i1 < sizePS; i1++)
					affPSAux[i1] += Math
						.exp((double) (-1
								* (i1 - PerceptualSchema.POS_GAUSS[i]) * (i1 - PerceptualSchema.POS_GAUSS[i]))
								/ (2D * (d1 * d1)));
			}
		
			
		for (int i1 = 0; i1 < sizePS; i1++) {
			if (affPSAux[i1] < 0.0D)
				affPSAux[i1] = 0.0D;
			if (affPSAux[i1] > 0.99999899999999997D)
				affPSAux[i1] = 1.0D;
		}

		// determina si cambiaron las affordances 
		// TODO: no está generalizado a todas las direcciones
//		Arrays.fills(affCurrent, false);
//		for (int i = 1; i <= 4; i++)
//			switch (i) {
//			case 1:
//				if (affPSAux[PerceptualSchema.POS_GAUSS_N180] == 1 && affPSAux[PerceptualSchema.POS_GAUSS_P180] == 1) {
//					affCurrent[0] = true;
//				}
//				break;
//			case 2:
//				if (affPSAux[PerceptualSchema.POS_GAUSS_N90] == 1) {
//					affCurrent[1] = true;
//				}
//				break;
//			case 3:
//				if (affPSAux[PerceptualSchema.POS_GAUSS_0] == 1) {
//					affCurrent[2] = true;
//				}
//				break;
//			case 4:
//				if (affPSAux[PerceptualSchema.POS_GAUSS_P90] == 1) {
//					affCurrent[3] = true;
//				}
//				break;
//			}
//		differentAff = false;
//		for (int j = 0; j < 4; j++) {
//			if (affPrev[j] != affCurrent[j]) {
//				differentAff = true;
//				break; // by Gonzalo j = 4;
//			}
//		}
		
		affPS.set(affPSAux);
		currentHeadAngleRat.set(headAngle);
	} // simRun
}
