package edu.usf.ratsim.experiment.model;

import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;

import org.w3c.dom.Element;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.GoalDecider;
import edu.usf.ratsim.nsl.modules.TaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.SingleLayerAS;
import edu.usf.ratsim.nsl.modules.qlearning.update.NormalUpdate;
import edu.usf.ratsim.robot.IRobot;

public class MultiScaleMultiIntentionModel extends NslModel implements
		RLRatModel {
	private List<ArtificialPlaceCellLayerWithIntention> pcls;
	private List<NormalUpdate> qLUpdVal;
	private ProportionalExplorer actionPerformerVote;
	private List<SingleLayerAS> qLActionSel;
	private List<QLSupport> qlData;
	private GoalDecider goalD;
	private TaxicFoodFinderSchema taxicDrive;

	public MultiScaleMultiIntentionModel(Element params, IRobot robot,
			ExperimentUniverse universe) {
		super("MSMIModel", (NslModule) null);

		// Get some configuration values for place cells + qlearning
		float minRadius = Float.parseFloat(params
				.getElementsByTagName("minRadius").item(0).getTextContent());
		float maxRadius = Float.parseFloat(params
				.getElementsByTagName("maxRadius").item(0).getTextContent());
		int numLayers = Integer.parseInt(params
				.getElementsByTagName("numLayers").item(0).getTextContent());
		int numIntentions = Integer
				.parseInt(params.getElementsByTagName("numIntentions").item(0)
						.getTextContent());

		pcls = new LinkedList<ArtificialPlaceCellLayerWithIntention>();
		qLUpdVal = new LinkedList<NormalUpdate>();
		qLActionSel = new LinkedList<SingleLayerAS>();
		qlData = new LinkedList<QLSupport>();

		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numLayers; i++) {
			ArtificialPlaceCellLayerWithIntention pcl = new ArtificialPlaceCellLayerWithIntention(
					"PlaceCellLayer", this, universe, numIntentions, radius);
			QLSupport qlSupport = new QLSupport(pcl.getSize());
			pcls.add(pcl);
			qlData.add(qlSupport);
			qLActionSel.add(new SingleLayerAS("QLActionSel", this, qlSupport,
					pcl.getSize(), robot, universe));
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
		// Created first to let Qlearning execute once when there is food
		actionPerformerVote = new ProportionalExplorer("ActionPerformer", this,
				numLayers, robot, universe);

		// Create taxic driver to override in case of flashing
		taxicDrive = new TaxicFoodFinderSchema("Taxic Driver", this, robot,
				universe);
		
		goalD = new GoalDecider("GoalDecider", this, universe);

		for (int i = 0; i < numLayers; i++) {
			qLUpdVal.add(new NormalUpdate("QLUpdVal", this, pcls.get(i)
					.getSize(), qlData.get(i), robot, universe));
		}
	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		for (int i = 0; i < pcls.size(); i++) {
			nslConnect(pcls.get(i), "activation", qLActionSel.get(i), "states");
			nslConnect(qLActionSel.get(i).actionVote,
					actionPerformerVote.votes[i]);
			nslConnect(goalD.goalFeeder, pcls.get(i).goalFeeder);
			nslConnect(pcls.get(i), "activation", qLUpdVal.get(i), "states");
		}
		nslConnect(goalD.goalFeeder, taxicDrive.goalFeeder);
	}

	public ProportionalExplorer getActionPerformer() {
		return actionPerformerVote;
	}

	public List<NormalUpdate> getQLValUpdaters() {
		return qLUpdVal;
	}

	public List<ArtificialPlaceCellLayerWithIntention> getPCLLayers() {
		return pcls;
	}

	public List<QLSupport> getQLDatas() {
		return qlData;
	}

}
