package edu.usf.ratsim.experiment.model;

import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslModule;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.SingleLayerAS;
import edu.usf.ratsim.nsl.modules.qlearning.update.NormalQL;
import edu.usf.ratsim.support.ElementWrapper;

public class QLearningModule {
	
	private List<QLSupport> qlData;
	private List<NormalQL> qLUpdVal;
	private List<SingleLayerAS> qLActionSel;

	public QLearningModule(NslModule parent, ElementWrapper params ) {
		
		float minRadius = params.getChildFloat("minRadius");
		float maxRadius = params.getChildFloat("maxRadius");
		int numLayers = params.getChildInt("numLayers");
		int numIntentions = params.getChildInt("numIntentions");
		
		qlData = new LinkedList<QLSupport>();
		qLUpdVal = new LinkedList<NormalQL>();
		qLActionSel = new LinkedList<SingleLayerAS>();
		
		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numLayers; i++) {
			QLSupport qlSupport = new QLSupport();
			qlData.add(qlSupport);
			qLActionSel.add(new SingleLayerAS("QLActionSel", this, qlSupport,
					pcl.getSize(), robot, universe));
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}

		for (int i = 0; i < numLayers; i++) {
			qLUpdVal.add(new NormalQL("QLUpdVal", this, pcls.get(i)
					.getSize(), qlData.get(i), robot, universe));
		}
	}

}
