package edu.usf.ratsim.experiment.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.FlashingActiveGoalDecider;
import edu.usf.ratsim.nsl.modules.FlashingTaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.HeadingAngle;
import edu.usf.ratsim.nsl.modules.qlearning.Reward;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.SingleLayerAS;
import edu.usf.ratsim.nsl.modules.qlearning.update.NormalQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;
import edu.usf.ratsim.support.Utiles;

// TODO: works but does not learn in this version
public class MultiScaleModel extends NslModel implements RLRatModel {
	private static final String ACTION_SELECTION_STR = "ASL";
	private static final String ACTION_PERFORMER_STR = "AP";
	private static final String FOOD_FINDER_STR = "TD";
	private static final String BEFORE_STATE_STR = "BeforePCL";
	private static final String AFTER_STATE_STR = "AfterPCL";
	private static final String QL_STR = "NQL";
	private static final String TAKEN_ACTION_STR = "TA";
	private static final String REWARD_STR = "R";
	private static final String GOAL_DECIDER_STR = "GD";
	private List<ArtificialPlaceCellLayer> beforePcls;
	private List<PolicyDumper> qLUpdVal;
	private ProportionalExplorer actionPerformerVote;
	private List<SingleLayerAS> qLActionSel;
	private LinkedList<ArtificialPlaceCellLayer> afterPcls;
	private int numLayers;

	public MultiScaleModel(ElementWrapper params, IRobot robot,
			ExperimentUniverse universe) {
		super("MSModel", (NslModule) null);

		new FlashingActiveGoalDecider(GOAL_DECIDER_STR, this, universe);

		// Get some configuration values for place cells + qlearning
		float minRadius = params.getChildFloat("minRadius");
		float maxRadius = params.getChildFloat("maxRadius");
		numLayers = params.getChildInt("numLayers");
		float maxPossibleReward = params.getChildFloat("maxPossibleReward");
		int numActions = Utiles.numAngles;
		float discountFactor = params.getChildFloat("discountFactor");
		float alpha = params.getChildFloat("alpha");
		float initialValue = params.getChildFloat("initialValue");
		float aprioriExploreVar = params.getChildFloat("aprioriExploreVar");
		float foodReward = params.getChildFloat("foodReward");
		float nonFoodReward = params.getChildFloat("nonFoodReward");

		beforePcls = new LinkedList<ArtificialPlaceCellLayer>();
		afterPcls = new LinkedList<ArtificialPlaceCellLayer>();
		qLUpdVal = new LinkedList<PolicyDumper>();
		qLActionSel = new LinkedList<SingleLayerAS>();

		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					BEFORE_STATE_STR + i, this, universe, radius);
			beforePcls.add(pcl);
			qLActionSel.add(new SingleLayerAS(ACTION_SELECTION_STR + i, this,
					pcl.getSize()));
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
		// Created first to let Qlearning execute once when there is food
		actionPerformerVote = new ProportionalExplorer(ACTION_PERFORMER_STR,
				this, numLayers, maxPossibleReward, aprioriExploreVar, robot, universe);

		// Create taxic driver to override in case of flashing
		new FlashingTaxicFoodFinderSchema(FOOD_FINDER_STR, this, robot,
				universe, numActions, maxPossibleReward);

		new Reward(REWARD_STR, this, universe, foodReward, nonFoodReward);
		new HeadingAngle(TAKEN_ACTION_STR, this, universe);
		radius = minRadius;
		for (int i = 0; i < numLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					AFTER_STATE_STR + i, this, universe, radius);
			afterPcls.add(pcl);
			qLUpdVal.add(new NormalQL(QL_STR + i, this, beforePcls.get(i)
					.getSize(), numActions, discountFactor, alpha, initialValue));
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		for (int i = 0; i < numLayers; i++) {
			nslConnect(getChild(GOAL_DECIDER_STR), "goalFeeder",
					getChild(FOOD_FINDER_STR), "goalFeeder");
			nslConnect(getChild(BEFORE_STATE_STR + i), "activation",
					getChild(ACTION_SELECTION_STR + i), "states");
			nslConnect(getChild(QL_STR + i), "value",
					getChild(ACTION_SELECTION_STR + i), "value");
			nslConnect(getChild(ACTION_SELECTION_STR + i), "votes",
					getChild(ACTION_PERFORMER_STR), "votes" + i);
			nslConnect(getChild(TAKEN_ACTION_STR), "headingAngle",
					getChild(QL_STR + i), "takenAction");
			nslConnect(getChild(REWARD_STR), "reward",
					getChild(QL_STR + i), "reward");
			nslConnect(getChild(BEFORE_STATE_STR + i), "activation",
					getChild(QL_STR + i), "statesBefore");
			nslConnect(getChild(AFTER_STATE_STR + i), "activation",
					getChild(QL_STR + i), "statesAfter");
		}
	}

	@SuppressWarnings("unchecked")
	private NslModule getChild(String name) {
		for (NslModule module : (Vector<NslModule>) nslGetModuleChildrenVector()) {
			if (module.nslGetName() != null && module.nslGetName().equals(name))
				return module;
		}

		return null;
	}

	public ProportionalExplorer getActionPerformer() {
		return actionPerformerVote;
	}

	public List<ArtificialPlaceCellLayer> getPCLLayers() {
		return beforePcls;
	}

	@Override
	public List<ArtificialPlaceCellLayerWithIntention> getPCLLayersIntention() {
		throw new RuntimeException("Method not implemented");
	}

	@Override
	public List<PolicyDumper> getPolicyDumpers() {
		return qLUpdVal;
	}

}
