package experiment.multiscalemorris;
/* Mdulo NSL que implementa el modelo de cognicin espacial.
   Alejandra Barrera
   Versin: 1
   Fecha: 10 de marzo de 2005.
 */


import nsl.modules.ActionSelectionSchema;
import nsl.modules.ActionPerformer;
import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import robot.IRobot;

public class MSMModel extends NslModel {
	private ActionPerformer actionPerf;
	private ActionSelectionSchema actionSel;

	public MSMModel(String nslName, NslModule nslParent, IRobot robot) {
		super(nslName, nslParent);
		
		actionSel = new ActionSelectionSchema("actionSel", this, robot);
		System.out.println("WGModel::Action ... OK");
		actionPerf = new ActionPerformer("ActionPerforme", this, robot);
	}
	
	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		nslConnect(actionPerf.actionTaken, actionSel.actionTaken);
	}

}
