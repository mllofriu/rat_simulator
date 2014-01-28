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
import experiment.ExperimentUniverse;

public class MSMModel extends NslModel {
	private ActionPerformer actionPerf;
	private TaxicFoodFinderSchema actionSel;
	private ArtificialPlaceCellLayer pcl;
//	private RandomActionSelSchema actionSel;
	private QLearning qLearning;

	public MSMModel(String nslName, NslModule nslParent, IRobot robot,
			ExperimentUniverse univ) {
		super(nslName, nslParent);

		actionSel = new TaxicFoodFinderSchema("ActionSelector", this, robot, univ);
//		actionSel = new RandomActionSelSchema("ActionSelector", nslParent, robot);
		actionPerf = new ActionPerformer("ActionPerformer", this, robot);
		pcl = new ArtificialPlaceCellLayer("PlaceCellLayer", this, univ);
		qLearning = new QLearning("QLearning", this, pcl.getSize(), univ);
	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		nslConnect(actionPerf.actionTaken, actionSel.actionTaken);
		nslConnect(pcl, "activation", qLearning, "states");
		nslConnect(actionSel.actionTaken, qLearning.actionTaken);
	}

}
