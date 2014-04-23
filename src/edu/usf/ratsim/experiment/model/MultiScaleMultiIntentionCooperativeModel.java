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
import edu.usf.ratsim.nsl.modules.FlashingOrAnyGoalDecider;
import edu.usf.ratsim.nsl.modules.GeneralTaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.HeadingAngle;
import edu.usf.ratsim.nsl.modules.PlaceIntention;
import edu.usf.ratsim.nsl.modules.FlashingTaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.WallFollower;
import edu.usf.ratsim.nsl.modules.qlearning.Reward;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.NoExploration;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.SingleLayerAS;
import edu.usf.ratsim.nsl.modules.qlearning.update.NormalQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;
import edu.usf.ratsim.support.Utiles;

// TODO: works but does not learn in this version
public class MultiScaleMultiIntentionCooperativeModel extends NslModel implements RLRatModel {
	private static final String ACTION_SELECTION_STR = "ASL";
	private static final String ACTION_PERFORMER_STR = "AP";
	private static final String FOOD_FINDER_STR = "TD";
	private static final String BEFORE_STATE_STR = "BeforePCL";
	private static final String AFTER_STATE_STR = "AfterPCL";
	private static final String QL_STR = "NQL";
	private static final String TAKEN_ACTION_STR = "TA";
	private static final String REWARD_STR = "R";
	private static final String ACTIVE_GOAL_DECIDER_STR = "ACTIVEGD";
	private static final String ANY_GOAL_DECIDER_STR = "ANYGD";
	private static final String AFTER_PLACE_INTENTION_STR = "API";
	private static final String BEFORE_PLACE_INTENTION_STR = "BPI";
	private static final String WALLFW_STR = "WF";
	private List<ArtificialPlaceCellLayer> beforePcls;
	private List<PolicyDumper> qLUpdVal;
	private NoExploration actionPerformerVote;
	private List<SingleLayerAS> qLActionSel;
	private LinkedList<ArtificialPlaceCellLayer> afterPcls;
	private int numLayers;
	private LinkedList<PlaceIntention> beforePI;
	private LinkedList<PlaceIntention> afterPI;
	private FlashingActiveGoalDecider activeGoalDecider;
	private FlashingOrAnyGoalDecider anyGoalDecider;

	public MultiScaleMultiIntentionCooperativeModel(ElementWrapper params, IRobot robot,
			ExperimentUniverse universe) {
		super("MSMIModel", (NslModule) null);

		// Get some configuration values for place cells + qlearning
		float minRadius = params.getChildFloat("minRadius");
		float maxRadius = params.getChildFloat("maxRadius");
		numLayers = params.getChildInt("numLayers");
		float maxPossibleReward = params.getChildFloat("maxPossibleReward");
		int numActions = Utiles.numAngles;
		float discountFactor = params.getChildFloat("discountFactor");
		float alpha = params.getChildFloat("alpha");
		float initialValue = params.getChildFloat("initialValue");
		float foodReward = params.getChildFloat("foodReward");
		float nonFoodReward = params.getChildFloat("nonFoodReward");
		int numIntentions = params.getChildInt("numIntentions");
		float flashingReward = params.getChildFloat("flashingReward");
		float nonFlashingReward = params.getChildFloat("nonFlashingReward");
		float wallFollowingVal = params.getChildFloat("wallFollowingVal");

		beforePcls = new LinkedList<ArtificialPlaceCellLayer>();
		beforePI = new LinkedList<PlaceIntention>();
		afterPcls = new LinkedList<ArtificialPlaceCellLayer>();
		afterPI	= new LinkedList<PlaceIntention>();
		qLUpdVal = new LinkedList<PolicyDumper>();
		qLActionSel = new LinkedList<SingleLayerAS>();
		
		

		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					BEFORE_STATE_STR + i, this, universe, radius);
			beforePcls.add(pcl);
			PlaceIntention pIntention = new PlaceIntention(this, BEFORE_PLACE_INTENTION_STR + i, pcl.getSize(), numIntentions);
			beforePI.add(pIntention);
			qLActionSel.add(new SingleLayerAS(ACTION_SELECTION_STR + i, this,
					pIntention.getSize()));
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}

		// Create taxic driver to override in case of flashing
		new GeneralTaxicFoodFinderSchema(FOOD_FINDER_STR, this, robot,universe, numActions, flashingReward, nonFlashingReward);
		
		// Wall following for obst. avoidance
		new WallFollower(WALLFW_STR, this, robot, universe, numActions, wallFollowingVal);
		
		// Get votes from QL and other behaviors and perform an action
		// One vote per layer + taxic + wf
		actionPerformerVote = new NoExploration(ACTION_PERFORMER_STR,
				this, numLayers + 2, robot, universe);
//		actionPerformerVote = new ProportionalExplorer(ACTION_PERFORMER_STR, this, numLayers + 2, robot, universe);

		radius = minRadius;
		new Reward(REWARD_STR, this, universe, foodReward, nonFoodReward);
		new HeadingAngle(TAKEN_ACTION_STR, this, universe);
		for (int i = 0; i < numLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					AFTER_STATE_STR + i, this, universe, radius);
			afterPcls.add(pcl);
			PlaceIntention pIntention = new PlaceIntention(this, AFTER_PLACE_INTENTION_STR + i, pcl.getSize(), numIntentions);
			afterPI.add(pIntention);
			qLUpdVal.add(new NormalQL(QL_STR + i, this, pIntention
					.getSize(), numActions, discountFactor, alpha, initialValue));
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
		
		activeGoalDecider = new FlashingActiveGoalDecider(ACTIVE_GOAL_DECIDER_STR, this, universe);
		anyGoalDecider =new FlashingOrAnyGoalDecider(ANY_GOAL_DECIDER_STR, this, universe);
	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		nslConnect(getChild(ANY_GOAL_DECIDER_STR), "goalFeeder",
				getChild(FOOD_FINDER_STR), "goalFeeder");
		nslConnect(getChild(FOOD_FINDER_STR), "votes",
				getChild(ACTION_PERFORMER_STR), "votes" + numLayers);
		nslConnect(getChild(WALLFW_STR), "votes",
				getChild(ACTION_PERFORMER_STR), "votes" + (numLayers + 1));
		
		for (int i = 0; i < numLayers; i++) {
			
			nslConnect(getChild(ACTIVE_GOAL_DECIDER_STR), "goalFeeder",
					getChild(BEFORE_PLACE_INTENTION_STR + i), "goalFeeder");
			nslConnect(getChild(BEFORE_STATE_STR + i), "activation",
					getChild(BEFORE_PLACE_INTENTION_STR + i), "places");
			nslConnect(getChild(BEFORE_PLACE_INTENTION_STR + i), "states",
					getChild(ACTION_SELECTION_STR + i), "states");
//			nslConnect(getChild(BEFORE_STATE_STR + i), "activation",
//					getChild(ACTION_SELECTION_STR + i), "states");
			nslConnect(getChild(QL_STR + i), "value",
					getChild(ACTION_SELECTION_STR + i), "value");
			nslConnect(getChild(ACTION_SELECTION_STR + i), "votes",
					getChild(ACTION_PERFORMER_STR), "votes" + i);
			nslConnect(getChild(TAKEN_ACTION_STR), "headingAngle",
					getChild(QL_STR + i), "takenAction");
			nslConnect(getChild(REWARD_STR), "reward",
					getChild(QL_STR + i), "reward");
			nslConnect(getChild(BEFORE_PLACE_INTENTION_STR + i), "states",
					getChild(QL_STR + i), "statesBefore");
//			nslConnect(getChild(BEFORE_STATE_STR + i), "activation",
//					getChild(QL_STR + i), "statesBefore");
			nslConnect(getChild(ACTIVE_GOAL_DECIDER_STR), "goalFeeder",
					getChild(AFTER_PLACE_INTENTION_STR + i), "goalFeeder");
			nslConnect(getChild(AFTER_STATE_STR + i), "activation",
					getChild(AFTER_PLACE_INTENTION_STR + i), "places");
			nslConnect(getChild(AFTER_PLACE_INTENTION_STR + i), "states",
					getChild(QL_STR + i), "statesAfter");
//			nslConnect(getChild(AFTER_STATE_STR + i), "activation",
//					getChild(QL_STR + i), "statesAfter");
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
		return null;
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
	
	public void newTrial(){
		activeGoalDecider.newTrial();
		anyGoalDecider.newTrial();
	}

}
