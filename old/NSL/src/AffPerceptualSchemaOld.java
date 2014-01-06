/* Módulo que gerera el esquema perceptual de
   affordances.	
   Alejandra Barrera
   Versión: 1 (enero, 2005)
 */

import nslj.src.system.*;
import nslj.src.cmd.*;
import nslj.src.lang.*;
import nslj.src.math.*;
import nslj.src.display.*;
import java.lang.*;

public class AffPerceptualSchemaOld extends NslModule {
	public NslDinInt0 headAngleRat;
	public NslDinInt0 yellowO;
	public NslDinInt0 yellowR;
	public NslDinInt0 yellowL;
	public NslDinInt0 blueO;
	public NslDinInt0 blueR;
	public NslDinInt0 blueL;
	public NslDinInt0 redO;
	public NslDinInt0 redR;
	public NslDinInt0 redL;
	public NslDinInt0 roseO;
	public NslDinInt0 roseR;
	public NslDinInt0 roseL;
	public NslDinInt0 whiteO;
	public NslDinInt0 whiteR;
	public NslDinInt0 whiteL;
	public NslDoutDouble1 affPS;
	public NslDoutInt0 currentHeadAngleRat;

	private int sizePS;
	private double affPSAux[];
	private int headAngle;
	private static boolean affCurrent[];
	private static boolean affPrev[];
	public static boolean sameAff;
	public static boolean differentAff;

	public AffPerceptualSchemaOld(String nslName, NslModule nslParent, int s) {
		super(nslName, nslParent);
		sizePS = s;
		headAngleRat = new NslDinInt0("headAngleRat", this);
		yellowO = new NslDinInt0("yellowO", this);
		yellowR = new NslDinInt0("yellowR", this);
		yellowL = new NslDinInt0("yellowL", this);
		blueO = new NslDinInt0("blueO", this);
		blueR = new NslDinInt0("blueR", this);
		blueL = new NslDinInt0("blueL", this);
		redO = new NslDinInt0("redO", this);
		redR = new NslDinInt0("redR", this);
		redL = new NslDinInt0("redL", this);
		roseO = new NslDinInt0("roseO", this);
		roseR = new NslDinInt0("roseR", this);
		roseL = new NslDinInt0("roseL", this);
		whiteO = new NslDinInt0("whiteO", this);
		whiteR = new NslDinInt0("whiteR", this);
		whiteL = new NslDinInt0("whiteL", this);
		affPS = new NslDoutDouble1("affPS", this, sizePS);
		currentHeadAngleRat = new NslDoutInt0("currentHeadAngleRat", this);
		
		/* -180 pos 4
		 * -90 pos 22
		 * 0 pos 40 
		 * 90 pos 58
		 * 180 pos 76
		 *  
		 */
		affPSAux = new double[sizePS];
		affCurrent = new boolean[4];
		affPrev = new boolean[4];
		for (int i = 0; i < 4; i++) {
			affCurrent[i] = false;
			affPrev[i] = false;
		}
		sameAff = false;
		differentAff = false;
	}

	public void simRun() {
		headAngle = headAngleRat.get();
		for (int j = 0; j < 4; j++)
			affPrev[j] = affCurrent[j];
		for (int i = 0; i < sizePS; i++)
			affPSAux[i] = 0.0;
		double d1 = 3D;
		switch (headAngle) {
		default:
			break;
		case 0:
			// Hay poco azul al frente o hay amarillo al frente => coloco gaussiana en el medio del array (pos 40)
			if ((blueO.get() > 0 && blueO.get() <= 250)
					|| (yellowO.get() > 0 && yellowO.get() < 1950))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 40) * (i1 - 40))
									/ (2D * (d1 * d1)));
			/*/ hay amarillo al frente y no hay mucho blanco a la derecha o
			 *  hay poco mas de azul al frente o
			 *  hay poco amarillo al frente, hay rosa al derecha y no hay azul, o
			 *  hay poco amarillo al frente, hay rosa al izquierda 
			 *  => coloco gaussiana en 180 y -180 (pos 4 y 76)
			 */
			if ((yellowO.get() > 50 && yellowO.get() < 4100 && whiteR.get() < 2000)
					|| (blueO.get() > 20 && blueO.get() < 600)
					|| (yellowO.get() > 0 && yellowO.get() < 50
							&& roseR.get() < 2000 && blueO.get() == 0)
					|| (yellowO.get() > 0 && yellowO.get() < 50 && roseL.get() < 2000))
				for (int i1 = 0; i1 < 80; i1++) {
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 4) * (i1 - 4))
									/ (2D * (d1 * d1)));
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 76) * (i1 - 76))
									/ (2D * (d1 * d1)));
				}
			/* hay blanco a la derecha y no mucho rojo a la derecha o
			 * hay rosa a la derecha y no mucho rojo a la derecha o
			 * hay rosa a la derecha y mucho rosa a la izquierda 
			 *  => coloco gaussiana en 90 (pos 58)
			 */
			if ((whiteR.get() > 0 && whiteR.get() < 2400 && redR.get() < 2000)
					|| (roseR.get() > 250 && redR.get() < 2000)
					|| (roseR.get() > 100 && roseL.get() > 3100))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 58) * (i1 - 58))
									/ (2D * (d1 * d1)));
			/* hay rosa a la izquierda y mucho rojo a la izquierda o
			 * hay rosa a la izquierda y algo de rojo a la izquierda o
			 * hay rosa a la izquierda y mucho amarillo al centro
			 * => coloco gaussiana en -90 (pos 22)
			 */
			if ((roseL.get() > 400 && roseL.get() < 3100 && redL.get() > 3000)
					|| (roseL.get() > 50 && roseL.get() < 3100 && redL.get() < 1500)
					|| (roseL.get() > 50 && roseL.get() < 3100 && yellowO.get() > 2000))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 22) * (i1 - 22))
									/ (2D * (d1 * d1)));
			break;
		case 90:
			if ((roseO.get() > 0 && roseO.get() < 3100)
					|| (roseO.get() > 0 && roseO.get() < 3500 && blueR.get() > 0))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 40) * (i1 - 40))
									/ (2D * (d1 * d1)));
			if ((roseO.get() > 145 && roseO.get() < 4500
					&& yellowR.get() < 2000 && yellowL.get() < 2000)
					|| (roseO.get() > 3000))
				for (int i1 = 0; i1 < 80; i1++) {
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 4) * (i1 - 4))
									/ (2D * (d1 * d1)));
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 76) * (i1 - 76))
									/ (2D * (d1 * d1)));
				}
			if ((yellowR.get() > 50 && yellowR.get() < 300 && redR.get() < 2900 && yellowL
					.get() > 2000)
					|| (yellowR.get() > 0 && yellowR.get() < 50 && yellowL
							.get() > 2500)
					|| (yellowR.get() > 50 && yellowR.get() < 300
							&& redR.get() < 2900 && blueR.get() > 0
							&& blueR.get() < 15 && roseO.get() > 2000)
					|| (yellowR.get() > 50 && yellowR.get() < 300
							&& redR.get() < 2900 && blueR.get() == 0))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 58) * (i1 - 58))
									/ (2D * (d1 * d1)));
			if (yellowL.get() > 45 && yellowL.get() < 300 && redL.get() < 2900)
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 22) * (i1 - 22))
									/ (2D * (d1 * d1)));
			break;
		case 180:
			if (yellowO.get() > 0 && yellowO.get() < 1600)
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 40) * (i1 - 40))
									/ (2D * (d1 * d1)));
			if ((yellowO.get() > 58 && yellowO.get() < 4000 && whiteR.get() < 80))
				for (int i1 = 0; i1 < 80; i1++) {
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 4) * (i1 - 4))
									/ (2D * (d1 * d1)));
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 76) * (i1 - 76))
									/ (2D * (d1 * d1)));
				}
			if ((roseR.get() > 400 && roseR.get() < 3100 && redR.get() > 3000)
					|| (roseR.get() > 50 && roseR.get() < 3100 && redR.get() < 1500)
					|| (roseR.get() > 50 && roseR.get() < 3500 && roseL.get() > 1500))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 58) * (i1 - 58))
									/ (2D * (d1 * d1)));
			if ((whiteL.get() > 0 && whiteL.get() < 2400 && redL.get() < 2000)
					|| (roseL.get() > 380 && roseL.get() < 3100 && redL.get() < 1800)
					|| (roseL.get() > 50 && roseL.get() < 3100 && roseR.get() > 3000))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 22) * (i1 - 22))
									/ (2D * (d1 * d1)));
			break;
		case 270:
			if ((whiteO.get() > 0 && whiteO.get() < 1500)
					|| (roseO.get() > 100 && roseO.get() < 2700 && yellowL
							.get() < 2000)
					|| (roseO.get() > 100 && roseO.get() < 2700
							&& yellowL.get() > 2000 && blueR.get() > 40 && blueR
							.get() < 50))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 40) * (i1 - 40))
									/ (2D * (d1 * d1)));
			if ((whiteO.get() > 40 && whiteO.get() < 4000)
					|| (roseO.get() > 201 && roseO.get() < 4500))
				for (int i1 = 0; i1 < 80; i1++) {
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 4) * (i1 - 4))
									/ (2D * (d1 * d1)));
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 76) * (i1 - 76))
									/ (2D * (d1 * d1)));
				}
			if ((yellowR.get() > 50 && yellowR.get() < 300 && redR.get() < 2900)
					|| (yellowR.get() > 0 && yellowR.get() < 50
							&& redR.get() < 2900 && yellowL.get() > 2200))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 58) * (i1 - 58))
									/ (2D * (d1 * d1)));
			if ((yellowL.get() > 50 && yellowL.get() < 300 && redL.get() < 2900
					&& yellowR.get() < 2000 && redR.get() < 4000)
					|| (yellowL.get() > 0 && yellowL.get() < 100 && yellowR
							.get() > 3100))
				for (int i1 = 0; i1 < 80; i1++)
					affPSAux[i1] += Math
							.exp((double) (-1 * (i1 - 22) * (i1 - 22))
									/ (2D * (d1 * d1)));
			break;
		}
		for (int i1 = 0; i1 < 80; i1++) {
			if (affPSAux[i1] < 0.0D)
				affPSAux[i1] = 0.0D;
			if (affPSAux[i1] > 0.99999899999999997D)
				affPSAux[i1] = 1.0D;
		}
		for (int j = 0; j < 4; j++)
			affCurrent[j] = false;

		for (int i = 1; i <= 4; i++)
			switch (i) {
			case 1:
				if (affPSAux[4] == 1 && affPSAux[76] == 1) {
					affCurrent[0] = true;
				}
				break;
			case 2:
				if (affPSAux[22] == 1) {
					affCurrent[1] = true;
				}
				break;
			case 3:
				if (affPSAux[40] == 1) {
					affCurrent[2] = true;
				}
				break;
			case 4:
				if (affPSAux[58] == 1) {
					affCurrent[3] = true;
				}
				break;
			}
		differentAff = false;
		for (int j = 0; j < 4; j++) {
			if (affPrev[j] != affCurrent[j]) {
				differentAff = true;
				j = 4;
			}
		}
		affPS.set(affPSAux);
		currentHeadAngleRat.set(headAngle);
	} // simRun
}
