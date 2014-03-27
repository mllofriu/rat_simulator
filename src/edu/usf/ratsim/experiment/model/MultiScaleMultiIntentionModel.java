package edu.usf.ratsim.experiment.model;

import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.GoalDecider;
import edu.usf.ratsim.nsl.modules.TaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.SingleLayerAS;
import edu.usf.ratsim.nsl.modules.qlearning.update.NormalQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;
import edu.usf.ratsim.support.Utiles;

public class MultiScaleMultiIntentionModel extends NslModel implements
		RLRatModel {
	private List<ArtificialPlaceCellLayerWithIntention> pcls;
	private List<PolicyDumper> qLUpdVal;
	private ProportionalExplorer actionPerformerVote;
	private List<SingleLayerAS> qLActionSel;
	private GoalDecider goalD;
	private TaxicFoodFinderSchema taxicDrive;

	public MultiScaleMultiIntentionModel(ElementWrapper params, IRobot robot,
			ExperimentUniverse universe) {
		super("MSMIModel", (NslModule) null);

		// Get some configuration values for place cells + qlearning
		float minRadius = params.getChildFloat("minRadius");
		float maxRadius = params.getChildFloat("maxRadius");
		int numLayers = params.getChildInt("numLayers");
		int numIntentions = params.getChildInt("numIntentions");
		float maxPossibleReward = params.getChildFloat("maxPossibleReward");
		int numActions = Utiles.discreteAngles.length;
		float discountFactor = params.getChildFloat("discountFactor");
		float alpha = params.getChildFloat("alpha");
		float initialValue = params.getChildFloat("initialValue");
		
		pcls = new LinkedList<ArtificialPlaceCellLayerWithIntention>();
		qLUpdVal = new LinkedList<PolicyDumper>();
		qLActionSel = new LinkedList<SingleLayerAS>();

		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numLayers; i++) {
			ArtificialPlaceCellLayerWithIntention pcl = new ArtificialPlaceCellLayerWithIntention(
					"PlaceCellLayer", this, universe, numIntentions, radius);
			pcls.add(pcl);
			qLActionSel.add(new SingleLayerAS("QLActionSel", this, pcl
					.getSize()));
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
		// Created first to let Qlearning execute once when there is food
		actionPerformerVote = new ProportionalExplorer("ActionPerformer", this,
				numLayers, maxPossibleReward, robot, universe);

		// Create taxic driver to override in case of flashing
		taxicDrive = new TaxicFoodFinderSchema("Taxic Driver", this, robot,
				universe);

		goalD = new GoalDecider("GoalDecider", this, universe);

		for (int i = 0; i < numLayers; i++) {
			qLUpdVal.add(new NormalQL("QLUpdVal", this, pcls.get(i).getSize(),
					numActions, discountFactor, alpha, initialValue));
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
		}
		nslConnect(goalD.goalFeeder, taxicDrive.goalFeeder);
	}

	public ProportionalExplorer getActionPerformer() {
		return actionPerformerVote;
	}

	public List<ArtificialPlaceCellLayerWithIntention> getPCLLayersIntention() {
		return pcls;
	}

	public List<PolicyDumper> getPolicyDumpers() {
		throw new RuntimeException("Method not implemented");
	}

}
