/* Mdulo NSL que construye los esquemas perceptuales de los landmarks incluidos
   en la vista actual de la rata. Tales esquemas codifican la distancia y el
   ngulo hacia el landmark en cuestin.
   Alejandra Barrera
   Fecha: 14 de agosto de 2006
   Fecha: 19 de febrero de 2007
 */

import java.util.Arrays;

import chart.LPSChats;

import nslj.src.lang.*;

public class LandmarksPerceptualSchema extends NslModule {
	public NslDinDouble1 distLandmarks;
	public NslDinDouble1 angleLandmarks;
	public NslDoutDouble1 psLand1;
	public NslDoutDouble1 psLand2;
	public NslDoutDouble1 psLand3;
	public NslDoutDouble1 psLand4;
	private int sizePS;
	private double ps1Aux[];
	private double ps2Aux[];
	private double ps3Aux[];
	private double ps4Aux[];
	private double distLandmarksAux[];
	private double angleLandmarksAux[];
	private LPSChats lps = new LPSChats();
	
	public static int LANDMARK_NUMBER = 4;
	double [][] landsPSs;

	public LandmarksPerceptualSchema(String nslName, NslModule nslParent, int t) {
		super(nslName, nslParent);
		sizePS = t;
		distLandmarks = new NslDinDouble1("distLandmarks", this, LANDMARK_NUMBER);
		angleLandmarks = new NslDinDouble1("angleLandmarks", this, LANDMARK_NUMBER);
		distLandmarksAux = new double[LANDMARK_NUMBER];
		angleLandmarksAux = new double[LANDMARK_NUMBER];

		psLand1 = new NslDoutDouble1("psLand1", this, sizePS);
		psLand2 = new NslDoutDouble1("psLand2", this, sizePS);
		psLand3 = new NslDoutDouble1("psLand3", this, sizePS);
		psLand4 = new NslDoutDouble1("psLand4", this, sizePS);
		landsPSs = new double[LANDMARK_NUMBER][sizePS];
	}

	public void simRun() {
		distLandmarksAux = distLandmarks.get();
		angleLandmarksAux = angleLandmarks.get();

		int distInPS, angInPS;
		double d = 3.0;
		
		for (int iterLands=0; iterLands<LANDMARK_NUMBER; iterLands++)
			
		if (distLandmarksAux[iterLands] != -1 && angleLandmarksAux[iterLands] != -1) {
			distInPS = (int) (distLandmarksAux[iterLands] * 76D + 2D);
			// cuando solo se usa el tamaño de la marca
//			distInPS = (int) (2*(distLandmarksAux[iterLands] * 76D + 2D));
			angInPS = (int) (angleLandmarksAux[iterLands] * 76D + 2D);

			for (int i = 0; i < sizePS; i++) {
				landsPSs[iterLands][i] = Math.exp((double) (-1 * Math.pow(i
						- angInPS, 2))
						/ (2D * Math.pow(d, 2)))
						+ Math.exp((double) (-1 * Math.pow(i - 80 - distInPS, 2))
								/ (2 * Math.pow(d, 2)));
//				landsPSs[iterLands][i] = Math.exp((double) (-1 * Math.pow(i- distInPS, 2))
//								/ (2 * Math.pow(d, 2)));
			}
			
		} else {
			Arrays.fill(landsPSs[iterLands], 0);
		}
		lps.setSchema(landsPSs);
		psLand1.set(landsPSs[0]);
		psLand2.set(landsPSs[1]);
		psLand3.set(landsPSs[2]);
		psLand4.set(landsPSs[3]);
	} // simRun
}
