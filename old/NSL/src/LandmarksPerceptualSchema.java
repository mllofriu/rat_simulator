/* Mdulo NSL que construye los esquemas perceptuales de los landmarks incluidos
   en la vista actual de la rata. Tales esquemas codifican la distancia y el
   ngulo hacia el landmark en cuestin.
   Alejandra Barrera
   Fecha: 14 de agosto de 2006
   Fecha: 19 de febrero de 2007
 */

import nslj.src.lang.*;

public class LandmarksPerceptualSchema extends NslModule {
	public NslDinDouble1 distLandmarks;
	public NslDinInt1 angleLandmarks;
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
	private int angleLandmarksAux[];
	private LPSChats lps = new LPSChats();

	public LandmarksPerceptualSchema(String nslName, NslModule nslParent, int t) {
		super(nslName, nslParent);
		sizePS = t;
		distLandmarks = new NslDinDouble1("distLandmarks", this, 4);
		angleLandmarks = new NslDinInt1("angleLandmarks", this, 4);
		distLandmarksAux = new double[4];
		angleLandmarksAux = new int[4];

		psLand1 = new NslDoutDouble1("psLand1", this, sizePS);
		psLand2 = new NslDoutDouble1("psLand2", this, sizePS);
		psLand3 = new NslDoutDouble1("psLand3", this, sizePS);
		psLand4 = new NslDoutDouble1("psLand4", this, sizePS);
		ps1Aux = new double[sizePS];
		ps2Aux = new double[sizePS];
		ps3Aux = new double[sizePS];
		ps4Aux = new double[sizePS];
	}

	public void simRun() {
		distLandmarksAux = distLandmarks.get();
		angleLandmarksAux = angleLandmarks.get();
		for (int i = 0; i < sizePS; i++) {
			ps1Aux[i] = 0.0;
			ps2Aux[i] = 0.0;
			ps3Aux[i] = 0.0;
			ps4Aux[i] = 0.0;
		}
		int daux2;
		double d = 3.0;
		if (distLandmarksAux[0] != -1 && angleLandmarksAux[0] != -1) {
			daux2 = (int) (distLandmarksAux[0] * 76D + 2D);

			for (int i = 0; i < ps1Aux.length; i++) {
				ps1Aux[i] = Math.exp((double) (-1 * Math.pow(i
						- angleLandmarksAux[0], 2))
						/ (2D * Math.pow(d, 2)))
						+ Math.exp((double) (-1 * Math.pow(i - 80 - daux2, 2))
								/ (2 * Math.pow(d, 2)));
			}
		}
		if (distLandmarksAux[1] != -1 && angleLandmarksAux[1] != -1) {
			daux2 = (int) (distLandmarksAux[1] * 76D + 2D);

			for (int i = 0; i < ps2Aux.length; i++) {
				ps2Aux[i] = Math.exp((double) (-1 * Math.pow(i
						- angleLandmarksAux[1], 2))
						/ (2 * Math.pow(d, 2)))
						+ Math.exp((double) (-1 * Math.pow(i - 80 - daux2, 2))
								/ (2 * Math.pow(d, 2)));
			}
		}
		if (distLandmarksAux[2] != -1 && angleLandmarksAux[2] != -1) {
			daux2 = (int) (distLandmarksAux[2] * 76D + 2D);

			for (int i = 0; i < ps3Aux.length; i++) {
				ps3Aux[i] = Math.exp((double) (-1 * Math.pow(i
						- angleLandmarksAux[2], 2))
						/ (2 * Math.pow(d, 2)))
						+ Math.exp((double) (-1 * Math.pow(i - 80 - daux2, 2))
								/ (2 * Math.pow(d, 2)));
			}
		}
		if (distLandmarksAux[3] != -1 && angleLandmarksAux[3] != -1) {
			daux2 = (int) (distLandmarksAux[3] * 76D + 2D);

			for (int i = 0; i < ps4Aux.length; i++) {
				ps4Aux[i] = Math.exp((double) (-1 * Math.pow(i
						- angleLandmarksAux[3], 2))
						/ (2D * Math.pow(d, 2)))
						+ Math.exp((double) (-1 * Math.pow(i - 80 - daux2, 2))
								/ (2 * Math.pow(d, 2)));
			}
		}
		lps.setSchema(ps1Aux, ps2Aux, ps3Aux, ps4Aux);
		psLand1.set(ps1Aux);
		psLand2.set(ps2Aux);
		psLand3.set(ps3Aux);
		psLand4.set(ps4Aux);
	} // simRun
}
