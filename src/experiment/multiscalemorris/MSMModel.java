package experiment.multiscalemorris;

/* Mdulo NSL que implementa el modelo de cognicin espacial.
 Alejandra Barrera
 Versin: 1
 Fecha: 10 de marzo de 2005.
 */

import nsl.modules.ActionPerformer;
import nsl.modules.ArtificialPlaceCellLayer;
import nsl.modules.QLearning;
import nsl.modules.RandomActionSelSchema;
import nsl.modules.TaxicFoodFinderSchema;
import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import robot.IRobot;
import support.Configuration;
import experiment.ExperimentUniverse;

public class MSMModel extends NslModel {
	private ActionPerformer actionPerf;
	private TaxicFoodFinderSchema actionSel;
	private ArtificialPlaceCellLayer[] pcls;
	// private RandomActionSelSchema actionSel;
	private QLearning[] qLearnings;

	public MSMModel(String nslName, NslModule nslParent, IRobot robot,
			ExperimentUniverse univ) {
		super(nslName, nslParent);

		actionSel = new TaxicFoodFinderSchema("ActionSelector", this, robot,
				univ);
		// actionSel = new RandomActionSelSchema("ActionSelector", nslParent,
		// robot);
		actionPerf = new ActionPerformer("ActionPerformer", this, robot);

		// Get some configuration values for place cells + qlearning
		int numLayers = Configuration.getInt("ArtificialPlaceCells.numLayers");
		float minRadius = Configuration
				.getFloat("ArtificialPlaceCells.minRadius");
		float maxRadius = Configuration
				.getFloat("ArtificialPlaceCells.maxRadius");
		float minX = Configuration.getFloat("ArtificialPlaceCells.minX");
		float minY = Configuration.getFloat("ArtificialPlaceCells.minY");

		// Create the layers
		float radius = minRadius;
		pcls = new ArtificialPlaceCellLayer[numLayers * 2];
		qLearnings = new QLearning[numLayers * 2];
		// For each layer
		int i = 0;
		while (i < numLayers * 2) {
			// Create the normal and phased out layer
			for (float phase = 0; phase <= radius; phase += radius) {
				pcls[i] = new ArtificialPlaceCellLayer("PlaceCellLayer", this,
						univ, radius, minX + phase, minY + phase);
				qLearnings[i] = new QLearning("QLearning", this,
						pcls[i].getSize(), univ);
				i++;
			}

			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		nslConnect(actionPerf.actionTaken, actionSel.actionTaken);
		for (int i = 0; i < pcls.length; i++) {
			nslConnect(pcls[i], "activation", qLearnings[i], "states");
			nslConnect(actionSel.actionTaken, qLearnings[i].actionTaken);
		}
	}

}
