package edu.usf.ratsim.experiment.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ArtificialHDCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.FlashingActiveGoalDecider;
import edu.usf.ratsim.nsl.modules.FlashingOrAnyGoalDecider;
import edu.usf.ratsim.nsl.modules.GeneralTaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.Intention;
import edu.usf.ratsim.nsl.modules.JointStates;
import edu.usf.ratsim.nsl.modules.JointStatesMany;
import edu.usf.ratsim.nsl.modules.PlaceIntention;
import edu.usf.ratsim.nsl.modules.WallFollower;
import edu.usf.ratsim.nsl.modules.qlearning.Reward;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.NoExploration;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalVotes;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.WTAVotes;
import edu.usf.ratsim.nsl.modules.qlearning.update.MultiStateProportionalQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.SingleStateQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;
import edu.usf.ratsim.support.Utiles;

// TODO: works but does not learn in this version
public class MultiScaleMultiIntentionCooperativeModel extends NslModel
		implements RLRatModel {
	private static final String ACTION_SELECTION_STR = "ASL";
	private static final String ACTION_PERFORMER_STR = "AP";
	private static final String FOOD_FINDER_STR = "TD";
	private static final String BEFORE_STATE_STR = "BeforePCL";
	private static final String AFTER_STATE_STR = "AfterPCL";
	private static final String QL_STR = "NQL";
	private static final String TAKEN_ACTION_STR = "TA";
	private static final String REWARD_STR = "R";
	private static final String BEFORE_ACTIVE_GOAL_DECIDER_STR = "BACTIVEGD";
	private static final String AFTER_ACTIVE_GOAL_DECIDER_STR = "AACTIVEGD";
	private static final String BEFORE_ANY_GOAL_DECIDER_STR = "BANYGD";
	private static final String AFTER_ANY_GOAL_DECIDER_STR = "AANYGD";
	private static final String AFTER_PLACE_INTENTION_STR = "API";
	private static final String BEFORE_PLACE_INTENTION_STR = "BPI";
	private static final String WALLFW_STR = "WF";
	private static final String BEFORE_INTENTION_STR = "BINT";
	private static final String AFTER_INTENTION_STR = "AINT";
	private static final String BEFORE_HD_LAYER_STR = "BHDL";
	private static final String AFTER_HD_LAYER_STR = "AHDL";
	private static final String AFTER_PIHD = "APIHD";
	private static final String BEFORE_PIHD = "BPIHD";
	private List<ArtificialPlaceCellLayer> beforePcls;
	private List<PolicyDumper> qLUpdVal;
	// private ProportionalExplorer actionPerformerVote;
	private List<WTAVotes> qLActionSel;
	private LinkedList<ArtificialPlaceCellLayer> afterPcls;
	private int numPCLayers;
	private LinkedList<PlaceIntention> beforePI;
	private LinkedList<PlaceIntention> afterPI;
	private FlashingActiveGoalDecider activeGoalDecider;
	private FlashingOrAnyGoalDecider anyGoalDecider;
	private int numHDLayers;
	private List<ArtificialHDCellLayer> afterHDs;
	private LinkedList<ArtificialHDCellLayer> beforeHDs;

	public MultiScaleMultiIntentionCooperativeModel(ElementWrapper params,
			IRobot robot, ExperimentUniverse universe) {
		super("MSMIModel", (NslModule) null);

		// Get some configuration values for place cells + qlearning
		float minRadius = params.getChildFloat("minRadius");
		float maxRadius = params.getChildFloat("maxRadius");
		numPCLayers = params.getChildInt("numPCLayers");
		numHDLayers = params.getChildInt("numHDLayers");
		int minHDCellsPerLayer = params.getChildInt("minHDCellsPerLayer");
		int stepHDCellsPerLayer = params.getChildInt("stepHDCellsPerLayer");
		int numActions = Utiles.numActions;
		float discountFactor = params.getChildFloat("discountFactor");
		float alpha = params.getChildFloat("alpha");
		float initialValue = params.getChildFloat("initialValue");
		float foodReward = params.getChildFloat("foodReward");
		float nonFoodReward = params.getChildFloat("nonFoodReward");
		int numIntentions = params.getChildInt("numIntentions");
		float flashingReward = params.getChildFloat("flashingReward");
		float nonFlashingReward = params.getChildFloat("nonFlashingReward");
		float wallFollowingVal = params.getChildFloat("wallFollowingVal");
		boolean deterministic = params
				.getChildBoolean("deterministicActionSelection");
		boolean proportionalQl = params.getChildBoolean("proportionalQL");
		beforePcls = new LinkedList<ArtificialPlaceCellLayer>();
		beforePI = new LinkedList<PlaceIntention>();
		afterPcls = new LinkedList<ArtificialPlaceCellLayer>();
		afterPI = new LinkedList<PlaceIntention>();
		qLUpdVal = new LinkedList<PolicyDumper>();
		qLActionSel = new LinkedList<WTAVotes>();

		activeGoalDecider = new FlashingActiveGoalDecider(
				BEFORE_ACTIVE_GOAL_DECIDER_STR, this, universe);
		anyGoalDecider = new FlashingOrAnyGoalDecider(
				BEFORE_ANY_GOAL_DECIDER_STR, this, universe);

		new Intention(this, BEFORE_INTENTION_STR, numIntentions);

		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numPCLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					BEFORE_STATE_STR + i, this, universe, radius);
			beforePcls.add(pcl);
			// JointStates placeIntention = new JointStates(
			// BEFORE_PLACE_INTENTION_STR + i, this, universe,
			// pcl.getSize(), numIntentions);
			// Update radius
			radius += (maxRadius - minRadius) / (numPCLayers - 1);
		}

		beforeHDs = new LinkedList<ArtificialHDCellLayer>();
		int numHDCells = minHDCellsPerLayer;
		for (int i = 0; i < numHDLayers; i++) {
			beforeHDs.add(new ArtificialHDCellLayer(BEFORE_HD_LAYER_STR + i,
					this, universe, numHDCells));
			numHDCells += stepHDCellsPerLayer;
		}

		for (int i = 0; i < numPCLayers; i++)
			for (int j = 0; j < numHDLayers; j++) {
				// JointStates jStates = new JointStates(BEFORE_PIHD
				// + (i * numHDLayers + j), this, universe, beforePcls
				// .get(i).getSize() * numIntentions,
				// beforeHDs.get(j).getSize());
				List<Integer> statesSizes = new LinkedList<Integer>();
				statesSizes.add(numIntentions);
				statesSizes.add(beforeHDs.get(j).getSize());
				statesSizes.add(beforePcls.get(i).getSize());
				JointStatesMany jStates = new JointStatesMany(BEFORE_PIHD
						+ (i * numHDLayers + j), this, universe, statesSizes);
				if (proportionalQl)
					new ProportionalVotes(ACTION_SELECTION_STR
							+ (i * numHDLayers + j), this, jStates.getSize());
				else
					new WTAVotes(ACTION_SELECTION_STR + (i * numHDLayers + j),
							this, jStates.getSize());
			}

		// Create taxic driver to override in case of flashing
		new GeneralTaxicFoodFinderSchema(FOOD_FINDER_STR, this, robot,
				universe, numActions, flashingReward, nonFlashingReward);

		// Wall following for obst. avoidance
		new WallFollower(WALLFW_STR, this, robot, universe, numActions,
				wallFollowingVal);

		// Get votes from QL and other behaviors and perform an action
		// One vote per layer + taxic + wf
		if (deterministic) {
			new NoExploration(ACTION_PERFORMER_STR, this, numPCLayers
					* numHDLayers + 2, robot, universe);
		} else {
			new ProportionalExplorer(ACTION_PERFORMER_STR, this, numPCLayers
					* numHDLayers + 2, robot, universe);
		}

		new Reward(REWARD_STR, this, universe, foodReward, nonFoodReward);

		// Second goal deciders after the robot has moved
		activeGoalDecider = new FlashingActiveGoalDecider(
				AFTER_ACTIVE_GOAL_DECIDER_STR, this, universe);
		anyGoalDecider = new FlashingOrAnyGoalDecider(
				AFTER_ANY_GOAL_DECIDER_STR, this, universe);

		new Intention(this, AFTER_INTENTION_STR, numIntentions);

		radius = minRadius;
		for (int i = 0; i < numPCLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					AFTER_STATE_STR + i, this, universe, radius);
			afterPcls.add(pcl);
//			JointStates placeIntention = new JointStates(
//					AFTER_PLACE_INTENTION_STR + i, this, universe,
//					pcl.getSize(), numIntentions);
			radius += (maxRadius - minRadius) / (numPCLayers - 1);
		}

		afterHDs = new LinkedList<ArtificialHDCellLayer>();
		numHDCells = minHDCellsPerLayer;
		for (int i = 0; i < numHDLayers; i++) {
			afterHDs.add(new ArtificialHDCellLayer(AFTER_HD_LAYER_STR + i,
					this, universe, numHDCells));
			numHDCells += stepHDCellsPerLayer;
		}

		for (int i = 0; i < numPCLayers; i++)
			for (int j = 0; j < numHDLayers; j++) {
//				JointStates pihd = new JointStates(AFTER_PIHD
//						+ (i * numHDLayers + j), this, universe, afterPcls.get(
//						i).getSize()
//						* numIntentions, afterHDs.get(j).getSize());
				List<Integer> statesSizes = new LinkedList<Integer>();
				statesSizes.add(numIntentions);
				statesSizes.add(afterHDs.get(j).getSize());
				statesSizes.add(afterPcls.get(i).getSize());
				JointStatesMany jStates = new JointStatesMany(AFTER_PIHD
						+ (i * numHDLayers + j), this, universe, statesSizes);
				if (proportionalQl)
					new MultiStateProportionalQL(
							QL_STR + (i * numHDLayers + j), this,
							jStates.getSize(), numActions, discountFactor, alpha,
							initialValue);
				else
					new SingleStateQL(QL_STR + (i * numHDLayers + j), this,
							jStates.getSize(), numActions, discountFactor, alpha,
							initialValue);
			}

	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		nslConnect(getChild(BEFORE_ANY_GOAL_DECIDER_STR), "goalFeeder",
				getChild(FOOD_FINDER_STR), "goalFeeder");
		nslConnect(getChild(FOOD_FINDER_STR), "votes",
				getChild(ACTION_PERFORMER_STR), "votes" + numPCLayers
						* numHDLayers);
		nslConnect(getChild(WALLFW_STR), "votes",
				getChild(ACTION_PERFORMER_STR), "votes"
						+ (numPCLayers * numHDLayers + 1));
		nslConnect(getChild(BEFORE_ACTIVE_GOAL_DECIDER_STR), "goalFeeder",
				getChild(BEFORE_INTENTION_STR), "goalFeeder");
		nslConnect(getChild(AFTER_ACTIVE_GOAL_DECIDER_STR), "goalFeeder",
				getChild(AFTER_INTENTION_STR), "goalFeeder");
		nslConnect(getChild(BEFORE_INTENTION_STR), "goalFeeder",
				getChild(AFTER_INTENTION_STR), "goalFeeder");

		// Build intention hd place layers
		for (int i = 0; i < numPCLayers; i++)
			for (int j = 0; j < numHDLayers; j++) {
				nslConnect(getChild(BEFORE_STATE_STR + i), "activation",
						getChild(BEFORE_PIHD + (i * numHDLayers + j)), "state3");
				nslConnect(getChild(BEFORE_INTENTION_STR), "intention",
						getChild(BEFORE_PIHD + (i * numHDLayers + j)), "state1");
				nslConnect(getChild(BEFORE_HD_LAYER_STR + j), "activation",
						getChild(BEFORE_PIHD + (i * numHDLayers + j)), "state2");

				nslConnect(getChild(AFTER_STATE_STR + i), "activation",
						getChild(AFTER_PIHD + (i * numHDLayers + j)), "state3");
				nslConnect(getChild(AFTER_INTENTION_STR), "intention",
						getChild(AFTER_PIHD + (i * numHDLayers + j)), "state1");
				nslConnect(getChild(AFTER_HD_LAYER_STR + j), "activation",
						getChild(AFTER_PIHD + (i * numHDLayers + j)), "state2");
		
			}

		// Connect the joint states to the QL system
		for (int i = 0; i < numPCLayers; i++)
			for (int j = 0; j < numHDLayers; j++) {
				nslConnect(getChild(BEFORE_PIHD + (i * numHDLayers + j)),
						"jointState", getChild(ACTION_SELECTION_STR
								+ (i * numHDLayers + j)), "states");
				nslConnect(getChild(QL_STR + (i * numHDLayers + j)), "value",
						getChild(ACTION_SELECTION_STR + (i * numHDLayers + j)),
						"value");
				nslConnect(getChild(ACTION_SELECTION_STR
						+ (i * numHDLayers + j)), "votes",
						getChild(ACTION_PERFORMER_STR), "votes"
								+ (i * numHDLayers + j));
				nslConnect(getChild(ACTION_PERFORMER_STR), "takenAction",
						getChild(QL_STR + (i * numHDLayers + j)), "takenAction");
				nslConnect(getChild(REWARD_STR), "reward", getChild(QL_STR
						+ (i * numHDLayers + j)), "reward");
				nslConnect(getChild(BEFORE_PIHD + (i * numHDLayers + j)),
						"jointState", getChild(QL_STR + (i * numHDLayers + j)),
						"statesBefore");
				nslConnect(getChild(AFTER_PIHD + (i * numHDLayers + j)),
						"jointState", getChild(QL_STR + (i * numHDLayers + j)),
						"statesAfter");
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

	public void newTrial() {
		activeGoalDecider.newTrial();
		anyGoalDecider.newTrial();
	}

}
