package neural;

import support.Configuration;
/* Clase que implementa los disparos de una celda grilla direccional basado en las
   propuestas de Burgess2007 y Hasselmo2005.
   Gonzalo Tejera
   Versión: 1 (Junio, 2012)
 */


public class HDGridCell {
	private static double H = 300; 
	private static double BH = 2.0/(Math.sqrt(3)*H);
	private static double INITIAL_PHASE = 0;
	private double preferedDirection; // direccion preferida de esta neurona
	private double phaseDendrite = INITIAL_PHASE;
	private double phaseSoma = INITIAL_PHASE;
	private double fSoma;
	private static final double DELTA_STEP = Configuration.getDouble("Simulation.DeltaStep");
	public static final double DECAY_RESET = Configuration.getDouble("HDGridCell.DecayReset");
	
	public HDGridCell(double fSoma, double preferedDirection, double initialPhase) {
		this.fSoma=fSoma;
		this.preferedDirection = preferedDirection;
		this.phaseSoma = initialPhase;
	}

	// ejecuta un desplazamiento del animal y retorna el potencial de accion
	double doStep(double headDirection, double speed) {
		double fDendrite = fSoma + fSoma*BH*speed*Math.cos(headDirection-preferedDirection);
		phaseDendrite = phaseDendrite + fDendrite*2.0*Math.PI*DELTA_STEP;
		phaseSoma = phaseSoma + fSoma*2.0*Math.PI*DELTA_STEP;
		return Math.cos(phaseDendrite)+Math.cos(phaseSoma);
	}

	/**
	 * 
	 */
	public void reset() {
		phaseDendrite = phaseDendrite * DECAY_RESET;
		phaseSoma = phaseSoma * DECAY_RESET;	
//		phaseDendrite = phaseDendrite - phaseDendrite * DECAY_RESET;
//		phaseSoma = phaseSoma - phaseSoma * DECAY_RESET;	
	}
}