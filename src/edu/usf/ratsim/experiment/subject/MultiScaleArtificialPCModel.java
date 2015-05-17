package edu.usf.ratsim.experiment.subject;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.vecmath.Point3f;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;
import edu.usf.experiment.subject.affordance.TurnAffordance;
import edu.usf.experiment.utils.ElementWrapper;
import edu.usf.ratsim.nsl.modules.ArtificialHDCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.AttentionalExplorer;
import edu.usf.ratsim.nsl.modules.DecayingExplorationSchema;
import edu.usf.ratsim.nsl.modules.Intention;
import edu.usf.ratsim.nsl.modules.JointStatesManyConcatenate;
import edu.usf.ratsim.nsl.modules.JointStatesManyMultiply;
import edu.usf.ratsim.nsl.modules.JointStatesManySum;
import edu.usf.ratsim.nsl.modules.LastAteGoalDecider;
import edu.usf.ratsim.nsl.modules.LastAteIntention;
import edu.usf.ratsim.nsl.modules.LastTriedToEatGoalDecider;
import edu.usf.ratsim.nsl.modules.NoIntention;
import edu.usf.ratsim.nsl.modules.PlaceIntention;
import edu.usf.ratsim.nsl.modules.StillExplorer;
import edu.usf.ratsim.nsl.modules.Voter;
import edu.usf.ratsim.nsl.modules.WallAvoider;
import edu.usf.ratsim.nsl.modules.qlearning.Reward;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.NoExploration;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalVotes;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.WTAVotes;
import edu.usf.ratsim.nsl.modules.qlearning.update.MultiStateProportionalAC;
import edu.usf.ratsim.nsl.modules.qlearning.update.MultiStateProportionalQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.QLAlgorithm;
import edu.usf.ratsim.nsl.modules.taxic.FlashingTaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.taxic.TaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.taxic.TaxicWallOpeningsSchema;

public class MultiScaleArtificialPCModel extends NslModel {

	private static final String BEFORE_ACTION_SELECTION_STR = "BASL";
	private static final String AFTER_ACTION_SELECTION_STR = "AASL";
	private static final String ACTION_PERFORMER_STR = "AP";
	private static final String BEFORE_FOOD_FINDER_STR = "BTD";
	private static final String AFTER_FOOD_FINDER_STR = "ATD";
	private static final String BEFORE_STATE_STR = "BeforePCL";
	private static final String AFTER_STATE_STR = "AfterPCL";
	private static final String RL_STR = "NQL";
	private static final String TAKEN_ACTION_STR = "TA";
	private static final String REWARD_STR = "R";
	private static final String BEFORE_LASTATE_GOAL_DECIDER_STR = "BANYGD";
	private static final String AFTER_LASTATE_GOAL_DECIDER_STR = "AANYGD";
	private static final String AFTER_PLACE_INTENTION_STR = "API";
	private static final String BEFORE_PLACE_INTENTION_STR = "BPI";
	private static final String BEFORE_WALLFOLLOW_STR = "B_WALL_AVOID";
	private static final String AFTER_WALLFOLLOW_STR = "A_WALL_AVOID";
	private static final String BEFORE_INTENTION_STR = "BINT";
	private static final String AFTER_INTENTION_STR = "AINT";
	private static final String BEFORE_HD_LAYER_STR = "BHDL";
	private static final String AFTER_HD_LAYER_STR = "AHDL";
	private static final String AFTER_PIHD = "APIHD";
	private static final String BEFORE_PIHD = "BPIHD";
	private static final String BEFORE_CONCAT = "BALL";
	private static final String AFTER_CONCAT = "AALL";
	private static final String BEFORE_JOINT_VOTES = "BJVOTES";
	private static final String AFTER_JOINT_VOTES = "AJVOTES";
	private static final String BEFORE_EXPLORATION = "BEXP";
	private static final String AFTER_EXPLORATION = "AEXP";
	private static final String AFTER_FLASHING_FOOD_FINDER_STR = "AFFF";
	private static final String BEFORE_FLASHING_FOOD_FINDER_STR = "BFFF";
	private static final String BEFORE_STILL_EXPLORATION = "BSE";
	private static final String BEFORE_LASTTRIEDTOEAT_GOAL_DECIDER_STR = "BLTTEGD";
	private static final String AFTER_LASTTRIEDTOEAT_GOAL_DECIDER_STR = "ALTTEGD";
	private static final String BEFORE_FEEDER_CELL_LAYER = "BFCL";
	private static final String AFTER_FEEDER_CELL_LAYER = "AFCL";
	private static final String BEFORE_ATTENTIONAL = "BATT";

	private List<ArtificialPlaceCellLayer> beforePcls;
	private List<QLAlgorithm> qLUpdVal;
	// private ProportionalExplorer actionPerformerVote;
	private List<WTAVotes> qLActionSel;
	private LinkedList<ArtificialPlaceCellLayer> afterPcls;
	private int numPCLayers;
	private LinkedList<PlaceIntention> beforePI;
	private LinkedList<PlaceIntention> afterPI;
	private LastAteGoalDecider lastAteGoalDecider;
	private int numHDLayers;
	private List<ArtificialHDCellLayer> afterHDs;
	private LinkedList<ArtificialHDCellLayer> beforeHDs;
	private QLAlgorithm ql;
	private NoExploration actionPerformer;
	private JointStatesManySum jointVotes;
	private Voter qlVotes;
	private String rlType;
	private List<DecayingExplorationSchema> exploration;
	private JointStatesManyConcatenate bAll;
	private Intention intention;
	private LinkedList<JointStatesManyMultiply> jStateList;
	private float explorationReward;

	public MultiScaleArtificialPCModel(String name, NslModule parent) {
		super(name, parent);
	}

	public MultiScaleArtificialPCModel(String name, NslModule parent,
			ElementWrapper params, Subject subject, LocalizableRobot lRobot) {
		super(name, parent);

		// Get some configuration values for place cells + qlearning
		float minRadius = params.getChildFloat("minRadius");
		float maxRadius = params.getChildFloat("maxRadius");
		numPCLayers = params.getChildInt("numPCLayers");
		int numPCCellsPerLayer = params.getChildInt("numPCCellsPerLayer");
		numHDLayers = params.getChildInt("numHDLayers");
		String placeCellType = params.getChildText("placeCells");
		float goalCellProportion = params.getChildFloat("goalCellProportion");
		int minHDCellsPerLayer = params.getChildInt("minHDCellsPerLayer");
		int stepHDCellsPerLayer = params.getChildInt("stepHDCellsPerLayer");
		float discountFactor = params.getChildFloat("discountFactor");
		float alpha = params.getChildFloat("alpha");
		float initialValue = params.getChildFloat("initialValue");
		float foodReward = params.getChildFloat("foodReward");
		float nonFoodReward = params.getChildFloat("nonFoodReward");
		int numIntentions = params.getChildInt("numIntentions");
		float flashingReward = params.getChildFloat("flashingReward");
		float nonFlashingReward = params.getChildFloat("nonFlashingReward");
		explorationReward = params.getChildFloat("explorationReward");
		//float wallFollowingVal = params.getChildFloat("wallFollowingVal");
		float attentionExploringVal = params.getChildFloat("attentionExploringVal");
		int maxAttentionSpan = params.getChildInt("maxAttentionSpan");
		
		float explorationHalfLifeVal = params
				.getChildFloat("explorationHalfLifeVal");
		boolean deterministic = params
				.getChildBoolean("deterministicActionSelection");
		float xmin = params.getChildFloat("xmin");
		float ymin = params.getChildFloat("ymin");
		float xmax = params.getChildFloat("xmax");
		float ymax = params.getChildFloat("ymax");
		rlType = params.getChildText("rlType");
		String voteType = params.getChildText("voteType");
		int maxActionsSinceForward = params
				.getChildInt("maxActionsSinceForward");
		float stillExplorationVal = params.getChildFloat("stillExplorationVal");

		int numActions = subject.getPossibleAffordances().size();

		Random r = new Random();
		long pclSeed = r.nextLong();

		beforePcls = new LinkedList<ArtificialPlaceCellLayer>();
		beforePI = new LinkedList<PlaceIntention>();
		afterPcls = new LinkedList<ArtificialPlaceCellLayer>();
		afterPI = new LinkedList<PlaceIntention>();
		qLUpdVal = new LinkedList<QLAlgorithm>();
		qLActionSel = new LinkedList<WTAVotes>();
		exploration = new LinkedList<DecayingExplorationSchema>();

		// beforeActiveGoalDecider = new ActiveGoalDecider(
		// BEFORE_ACTIVE_GOAL_DECIDER_STR, this);
		lastAteGoalDecider = new LastAteGoalDecider(
				BEFORE_LASTATE_GOAL_DECIDER_STR, this, subject);
		new LastTriedToEatGoalDecider(BEFORE_LASTTRIEDTOEAT_GOAL_DECIDER_STR,
				this, subject);

		if (numIntentions > 1)
			intention = new LastAteIntention(BEFORE_INTENTION_STR, this,
					numIntentions);
		else
			intention = new NoIntention(BEFORE_INTENTION_STR, this,
					numIntentions);

		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numPCLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					BEFORE_STATE_STR + i, this, lRobot, radius,
					numPCCellsPerLayer, pclSeed, placeCellType, xmin, ymin,
					xmax, ymax, lRobot.getAllFeeders(), goalCellProportion);
			beforePcls.add(pcl);
			// JointStates placeIntention = new JointStates(
			// BEFORE_PLACE_INTENTION_STR + i, this, universe,
			// pcl.getSize(), numIntentions);
			// Update radius
			radius += (maxRadius - minRadius) / (numPCLayers - 1);
		}

		// TODO: seed?
		beforeHDs = new LinkedList<ArtificialHDCellLayer>();
		int numHDCells = minHDCellsPerLayer;
		for (int i = 0; i < numHDLayers; i++) {
			beforeHDs.add(new ArtificialHDCellLayer(BEFORE_HD_LAYER_STR + i,
					this, numHDCells, lRobot));
			numHDCells += stepHDCellsPerLayer;
		}

		List<Integer> bpihdSizes = new LinkedList<Integer>();
		jStateList = new LinkedList<JointStatesManyMultiply>();
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
				JointStatesManyMultiply jStates = new JointStatesManyMultiply(
						BEFORE_PIHD + (i * numHDLayers + j), this, statesSizes);
				jStateList.add(jStates);
				bpihdSizes.add(jStates.getSize());

			}

		// Add feeder cells
		// TODO: pcl seed?
		// new ArtificialFeederCellLayer(BEFORE_FEEDER_CELL_LAYER, this, lRobot,
		// numIntentions, pclSeed);
		// List<Integer> statesSizes = new LinkedList<Integer>();
		// statesSizes.add(numIntentions);
		// statesSizes.add(numIntentions);
		// JointStatesManyMultiply jStates = new JointStatesManyMultiply(
		// BEFORE_PIHD + (numPCLayers * numHDLayers), this, statesSizes);
		// jStateList.add(jStates);
		// bpihdSizes.add(jStates.getSize());

		// Concatenate all layers
		bAll = new JointStatesManyConcatenate(BEFORE_CONCAT, this, bpihdSizes);

		// Take the value of each state and vote for an action
		if (voteType.equals("proportional"))
			if (rlType.equals("actorCritic"))
				qlVotes = new ProportionalVotes(BEFORE_ACTION_SELECTION_STR,
						this, bAll.getSize(), numActions + 1);
			else
				qlVotes = new ProportionalVotes(BEFORE_ACTION_SELECTION_STR,
						this, bAll.getSize(), numActions);
		else if (voteType.equals("gradientConnection"))
			qlVotes = new GradientVotes(BEFORE_ACTION_SELECTION_STR,
						this, bAll.getSize(), numActions);
		else if (voteType.equals("wta"))
			qlVotes = new WTAVotes(BEFORE_ACTION_SELECTION_STR, this,
					bAll.getSize(), numActions);
		else
			throw new RuntimeException("Vote mechanism not implemented");

		// Create taxic driver
		// new GeneralTaxicFoodFinderSchema(BEFORE_FOOD_FINDER_STR, this, robot,
		// universe, numActions, flashingReward, nonFlashingReward);
		new TaxicFoodFinderSchema(BEFORE_FOOD_FINDER_STR, this, subject,
				lRobot, nonFlashingReward, discountFactor);

		new FlashingTaxicFoodFinderSchema(BEFORE_FLASHING_FOOD_FINDER_STR,
				this, subject, lRobot, flashingReward, discountFactor);

		exploration.add(new DecayingExplorationSchema(BEFORE_EXPLORATION, this,
				subject, lRobot, explorationReward, explorationHalfLifeVal));

		new StillExplorer(BEFORE_STILL_EXPLORATION, this,
				maxActionsSinceForward, subject, stillExplorationVal);

		// Wall following for obst. avoidance
//		new WallAvoider(BEFORE_WALLAVOID_STR, this, subject, wallFollowingVal,
//				numActions);
//		new TaxicWallOpeningsSchema(BEFORE_WALLFOLLOW_STR, this, subject, lRobot, wallFollowingVal);

		new AttentionalExplorer(BEFORE_ATTENTIONAL, this, subject, attentionExploringVal, maxAttentionSpan);
		// Three joint states - QL Votes, Taxic, WallAvoider
		jointVotes = new JointStatesManySum(BEFORE_JOINT_VOTES, this, 6,
				numActions + 1);

		// Get votes from QL and other behaviors and perform an action
		// One vote per layer (one now) + taxic + wf
		if (deterministic) {
			actionPerformer = new NoExploration(ACTION_PERFORMER_STR, this,
					subject, numActions);
		}
		// else {
		// new ProportionalExplorer(ACTION_PERFORMER_STR, this, subject, 1 + 2);
		// }

		new Reward(REWARD_STR, this, subject, foodReward, nonFoodReward);

		// Second goal deciders after the robot has moved
		// afterActiveGoalDecider = new ActiveGoalDecider(
		// AFTER_ACTIVE_GOAL_DECIDER_STR, this, universe);
		lastAteGoalDecider = new LastAteGoalDecider(
				AFTER_LASTATE_GOAL_DECIDER_STR, this, subject);
		new LastTriedToEatGoalDecider(AFTER_LASTTRIEDTOEAT_GOAL_DECIDER_STR,
				this, subject);

		if (numIntentions > 1)
			new LastAteIntention(AFTER_INTENTION_STR, this, numIntentions);
		else
			new NoIntention(AFTER_INTENTION_STR, this, numIntentions);

		radius = minRadius;
		for (int i = 0; i < numPCLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					AFTER_STATE_STR + i, this, lRobot, radius,
					numPCCellsPerLayer, pclSeed, placeCellType, xmin, ymin,
					xmax, ymax, lRobot.getAllFeeders(), goalCellProportion);
			afterPcls.add(pcl);
			// JointStates placeIntention = new JointStates(
			// AFTER_PLACE_INTENTION_STR + i, this, universe,
			// pcl.getSize(), numIntentions);
			radius += (maxRadius - minRadius) / (numPCLayers - 1);
		}

		afterHDs = new LinkedList<ArtificialHDCellLayer>();
		numHDCells = minHDCellsPerLayer;
		for (int i = 0; i < numHDLayers; i++) {
			afterHDs.add(new ArtificialHDCellLayer(AFTER_HD_LAYER_STR + i,
					this, numHDCells, lRobot));
			numHDCells += stepHDCellsPerLayer;
		}

		List<Integer> apihdSizes = new LinkedList<Integer>();
		LinkedList<Integer> statesSizes;
		JointStatesManyMultiply jStates;
		for (int i = 0; i < numPCLayers; i++)
			for (int j = 0; j < numHDLayers; j++) {
				// JointStates pihd = new JointStates(AFTER_PIHD
				// + (i * numHDLayers + j), this, universe, afterPcls.get(
				// i).getSize()
				// * numIntentions, afterHDs.get(j).getSize());
				statesSizes = new LinkedList<Integer>();
				statesSizes.add(numIntentions);
				statesSizes.add(afterHDs.get(j).getSize());
				statesSizes.add(afterPcls.get(i).getSize());
				jStates = new JointStatesManyMultiply(AFTER_PIHD
						+ (i * numHDLayers + j), this, statesSizes);
				apihdSizes.add(jStates.getSize());

			}

		// Add feeder cells
		// TODO: pcl seed?
		// new ArtificialFeederCellLayer(AFTER_FEEDER_CELL_LAYER, this, lRobot,
		// numIntentions, pclSeed);
		// statesSizes = new LinkedList<Integer>();
		// statesSizes.add(numIntentions);
		// statesSizes.add(numIntentions);
		// jStates = new JointStatesManyMultiply(
		// AFTER_PIHD + (numPCLayers * numHDLayers), this, statesSizes);
		// jStateList.add(jStates);
		// apihdSizes.add(jStates.getSize());

		JointStatesManyConcatenate aAll = new JointStatesManyConcatenate(
				AFTER_CONCAT, this, apihdSizes);

		// Take the value of each state and vote for an action
		if (rlType.equals("proportionalQl"))
			new ProportionalVotes(AFTER_ACTION_SELECTION_STR, this,
					aAll.getSize(), numActions);
		else if (rlType.equals("actorCritic"))
			new ProportionalVotes(AFTER_ACTION_SELECTION_STR, this,
					aAll.getSize(), numActions + 1);
		else if (rlType.equals("wtaQl"))
			new WTAVotes(AFTER_ACTION_SELECTION_STR, this, aAll.getSize(),
					numActions);
		else
			throw new RuntimeException("RL mechanism not implemented");

		// Create taxic driver
		// new GeneralTaxicFoodFinderSchema(AFTER_FOOD_FINDER_STR, this, robot,
		// universe, numActions, flashingReward, nonFlashingReward);
		new TaxicFoodFinderSchema(AFTER_FOOD_FINDER_STR, this, subject, lRobot,
				nonFlashingReward, discountFactor);

		new FlashingTaxicFoodFinderSchema(AFTER_FLASHING_FOOD_FINDER_STR, this,
				subject, lRobot, flashingReward, discountFactor);

		// Wall following for obst. avoidance
//		new WallAvoider(AFTER_WALLAVOID_STR, this, subject, wallFollowingVal,
//				numActions);
//		new TaxicWallOpeningsSchema(AFTER_WALLFOLLOW_STR, this, subject, lRobot, wallFollowingVal);
		// exploration.add(new DecayingExplorationSchema(AFTER_EXPLORATION,
		// this,
		// subject, lRobot, explorationReward, explorationHalfLifeVal));

		// Three joint states - QL Votes, Taxic, WallAvoider
		new JointStatesManySum(AFTER_JOINT_VOTES, this, 6, numActions + 1);

		if (rlType.equals("proportionalQl")) {
			// MultiStateProportionalQLReplay mspql = new
			// MultiStateProportionalQLReplay(
			// QL_STR, this, subject, bAll.getSize(), numActions,
			// discountFactor, alpha, initialValue);
			MultiStateProportionalQL mspql = new MultiStateProportionalQL(
					RL_STR, this, subject, bAll.getSize(), numActions,
					discountFactor, alpha, initialValue);
			ql = mspql;
			qLUpdVal.add(mspql);
		} else if (rlType.equals("actorCritic")) {
			MultiStateProportionalAC mspac = new MultiStateProportionalAC(
					RL_STR, this, subject, bAll.getSize(), numActions,
					discountFactor, alpha, discountFactor, initialValue);
			// TODO: recover this assginments
			// ql = mspql;
			// qLUpdVal.add(mspql);
		} else if (rlType.equals("wtaQl")) {
			// TODO: get back
			// SingleStateQL ssql = new SingleStateQL(QL_STR, this,
			// bAll.getSize(), numActions, discountFactor,
			// alpha, initialValue);
			// ql = ssql;
			// qLUpdVal.add(ssql);
		} else
			throw new RuntimeException("RL mechanism not implemented");
	}

	public void makeConn() {
		// Connect tried to eat to taxic bh
		nslConnect(getChild(BEFORE_LASTTRIEDTOEAT_GOAL_DECIDER_STR),
				"goalFeeder", getChild(BEFORE_FOOD_FINDER_STR), "goalFeeder");
		nslConnect(getChild(AFTER_LASTTRIEDTOEAT_GOAL_DECIDER_STR),
				"goalFeeder", getChild(AFTER_FOOD_FINDER_STR), "goalFeeder");
		nslConnect(getChild(BEFORE_LASTATE_GOAL_DECIDER_STR), "goalFeeder",
				getChild(BEFORE_FLASHING_FOOD_FINDER_STR), "goalFeeder");
		nslConnect(getChild(AFTER_LASTATE_GOAL_DECIDER_STR), "goalFeeder",
				getChild(AFTER_FLASHING_FOOD_FINDER_STR), "goalFeeder");
		// Connect taxic behaviors to vote_adder
		nslConnect(getChild(BEFORE_FOOD_FINDER_STR), "votes",
				getChild(BEFORE_JOINT_VOTES), "state" + 0);
		nslConnect(getChild(BEFORE_FLASHING_FOOD_FINDER_STR), "votes",
				getChild(BEFORE_JOINT_VOTES), "state" + 1);
//		nslConnect(getChild(BEFORE_WALLFOLLOW_STR), "votes",
//				getChild(BEFORE_JOINT_VOTES), "state" + 2);
		nslConnect(getChild(BEFORE_ATTENTIONAL), "votes",
				getChild(BEFORE_JOINT_VOTES), "state" + 2);
		
		nslConnect(getChild(BEFORE_EXPLORATION), "votes",
				getChild(BEFORE_JOINT_VOTES), "state" + 4);
		nslConnect(getChild(BEFORE_STILL_EXPLORATION), "votes",
				getChild(BEFORE_JOINT_VOTES), "state" + 5);
		nslConnect(getChild(AFTER_FOOD_FINDER_STR), "votes",
				getChild(AFTER_JOINT_VOTES), "state" + 0);
		nslConnect(getChild(AFTER_FLASHING_FOOD_FINDER_STR), "votes",
				getChild(AFTER_JOINT_VOTES), "state" + 1);
//		nslConnect(getChild(AFTER_WALLFOLLOW_STR), "votes",
//				getChild(AFTER_JOINT_VOTES), "state" + 2);
		nslConnect(getChild(BEFORE_ATTENTIONAL), "votes",
				getChild(AFTER_JOINT_VOTES), "state" + 2);
		// BEFORE exploration is connected to after votes to nullify value
		// estimation
		nslConnect(getChild(BEFORE_EXPLORATION), "votes",
				getChild(AFTER_JOINT_VOTES), "state" + 4);
		nslConnect(getChild(BEFORE_STILL_EXPLORATION), "votes",
				getChild(AFTER_JOINT_VOTES), "state" + 5);
		// Connect active goal to intention
		nslConnect(getChild(BEFORE_LASTATE_GOAL_DECIDER_STR), "goalFeeder",
				getChild(BEFORE_INTENTION_STR), "goalFeeder");
		nslConnect(getChild(AFTER_LASTATE_GOAL_DECIDER_STR), "goalFeeder",
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
				nslConnect(getChild(BEFORE_PIHD + (i * numHDLayers + j)),
						"jointState", getChild(BEFORE_CONCAT), "state"
								+ (i * numHDLayers + j));

				nslConnect(getChild(AFTER_STATE_STR + i), "activation",
						getChild(AFTER_PIHD + (i * numHDLayers + j)), "state3");
				nslConnect(getChild(AFTER_INTENTION_STR), "intention",
						getChild(AFTER_PIHD + (i * numHDLayers + j)), "state1");
				nslConnect(getChild(AFTER_HD_LAYER_STR + j), "activation",
						getChild(AFTER_PIHD + (i * numHDLayers + j)), "state2");
				nslConnect(getChild(AFTER_PIHD + (i * numHDLayers + j)),
						"jointState", getChild(AFTER_CONCAT), "state"
								+ (i * numHDLayers + j));

			}

		// nslConnect(getChild(BEFORE_FEEDER_CELL_LAYER), "activation",
		// getChild(BEFORE_PIHD + (numPCLayers * numHDLayers)), "state1");
		// nslConnect(getChild(BEFORE_INTENTION_STR), "intention",
		// getChild(BEFORE_PIHD + (numPCLayers * numHDLayers)), "state2");
		// nslConnect(getChild(AFTER_FEEDER_CELL_LAYER), "activation",
		// getChild(AFTER_PIHD + (numPCLayers * numHDLayers)), "state1");
		// nslConnect(getChild(AFTER_INTENTION_STR), "intention",
		// getChild(AFTER_PIHD + (numPCLayers * numHDLayers)), "state2");
		// nslConnect(getChild(BEFORE_PIHD + (numPCLayers * numHDLayers)),
		// "jointState", getChild(BEFORE_CONCAT), "state"
		// + (numPCLayers * numHDLayers));
		// nslConnect(getChild(AFTER_PIHD + (numPCLayers * numHDLayers)),
		// "jointState", getChild(AFTER_CONCAT), "state"
		// + (numPCLayers * numHDLayers));

		// Connect the joint states to the QL system
		nslConnect(getChild(BEFORE_CONCAT), "jointState",
				getChild(BEFORE_ACTION_SELECTION_STR), "states");
		nslConnect(getChild(AFTER_CONCAT), "jointState",
				getChild(AFTER_ACTION_SELECTION_STR), "states");
		nslConnect(getChild(RL_STR), "value",
				getChild(BEFORE_ACTION_SELECTION_STR), "value");
		nslConnect(getChild(RL_STR), "value",
				getChild(AFTER_ACTION_SELECTION_STR), "value");
		nslConnect(getChild(BEFORE_ACTION_SELECTION_STR), "votes",
				getChild(BEFORE_JOINT_VOTES), "state" + 3);
		nslConnect(getChild(AFTER_ACTION_SELECTION_STR), "votes",
				getChild(AFTER_JOINT_VOTES), "state" + 3);
		nslConnect(getChild(BEFORE_JOINT_VOTES), "jointState",
				getChild(ACTION_PERFORMER_STR), "votes");
		nslConnect(getChild(ACTION_PERFORMER_STR), "takenAction",
				getChild(RL_STR), "takenAction");
		nslConnect(getChild(ACTION_PERFORMER_STR), "takenAction",
				getChild(BEFORE_STILL_EXPLORATION), "takenAction");
		nslConnect(getChild(ACTION_PERFORMER_STR), "takenAction",
				getChild(BEFORE_ATTENTIONAL), "takenAction");
		// nslConnect(getChild(BEFORE_FOOD_FINDER_STR), "votes",
		// getChild(QL_STR),
		// "taxonExpectedValues");
		nslConnect(getChild(REWARD_STR), "reward", getChild(RL_STR), "reward");
		nslConnect(getChild(BEFORE_CONCAT), "jointState", getChild(RL_STR),
				"statesBefore");
		nslConnect(getChild(AFTER_CONCAT), "jointState", getChild(RL_STR),
				"statesAfter");
		// nslConnect(getChild(BEFORE_FOOD_FINDER_STR), "votes",
		// getChild(QL_STR),
		// "actionVotesBefore");
		//
		if (rlType.equals("proportionalQl")) {
			// nslConnect(getChild(AFTER_ACTION_SELECTION_STR), "votes",
			// getChild(RL_STR), "actionVotesAfter");
			// nslConnect(getChild(BEFORE_ACTION_SELECTION_STR), "votes",
			// getChild(RL_STR), "actionVotesBefore");
			nslConnect(getChild(AFTER_JOINT_VOTES), "jointState",
					getChild(RL_STR), "actionVotesAfter");
			nslConnect(getChild(BEFORE_JOINT_VOTES), "jointState",
					getChild(RL_STR), "actionVotesBefore");
		} else if (rlType.equals("actorCritic")) {
			nslConnect(getChild(AFTER_JOINT_VOTES), "jointState",
					getChild(RL_STR), "actionVotesAfter");
			nslConnect(getChild(BEFORE_JOINT_VOTES), "jointState",
					getChild(RL_STR), "actionVotesBefore");
		} else
			throw new RuntimeException("RL mechanism not implemented");
		// nslConnect(getChild(AFTER_JOINT_VOTES), "jointState",
		// getChild(QL_STR),
		// "actionVotesAfter");

	}

	@SuppressWarnings("unchecked")
	private NslModule getChild(String name) {
		for (NslModule module : (Vector<NslModule>) this
				.nslGetModuleChildrenVector()) {
			if (module.nslGetName() != null && module.nslGetName().equals(name))
				return module;
		}

		return null;
	}

	public NoExploration getActionPerformer() {
		return actionPerformer;
	}

	public List<ArtificialPlaceCellLayer> getPCLLayers() {
		return beforePcls;
	}

	public List<QLAlgorithm> getPolicyDumpers() {
		return qLUpdVal;
	}

	public void newTrial() {
		// anyGoalDecider.newTrial();
		// for(GoalTaxicFoodFinderSchema gs : taxic)
		// gs.newTrial();
		
		for (DecayingExplorationSchema gs : exploration)
			gs.newTrial();
	}

	public void deactivatePCL(List<Integer> feedersToDeactivate) {
		for (Integer layer : feedersToDeactivate) {
			beforePcls.get(layer).deactivate();
			afterPcls.get(layer).deactivate();
		}
	}

	protected void finalize() {
		super.finalize();

		// System.out.println("NsL model being finalized");
	}

	public void savePolicy() {
		ql.savePolicy();
	}

	public void setPassiveMode(boolean passive) {
		ql.setUpdatesEnabled(!passive);
	}

	public JointStatesManySum getJointVoites() {
		return jointVotes;
	}

	public Voter getQLVotes() {
		return qlVotes;
	}

	public void newEpisode() {
		for (DecayingExplorationSchema gs : exploration)
			gs.newEpisode();
	}

	/**
	 * Simulates being at position pos with heading theta and returns the action
	 * the model would perform
	 * 
	 * @param pos
	 * @param theta
	 * @param affs
	 * @param intention
	 * @return
	 */
	public Affordance getHypotheticAction(Point3f pos, float theta,
			List<Affordance> affs, int inte) {
		intention.simRun(inte);

		for (ArtificialPlaceCellLayer pcl : beforePcls)
			// TODO: add feeder cells to policies
			pcl.simRun(pos, false);
		for (ArtificialHDCellLayer hdcl : beforeHDs)
			hdcl.simRun(theta);

		for (JointStatesManyMultiply jsmm : jStateList)
			jsmm.simRun();

		bAll.simRun();

		qlVotes.simRun();

		NslDoutFloat1 votes = qlVotes.getVotes();
		float max = Float.NEGATIVE_INFINITY;
		int maxIndex = 0;
		for (int i = 0; i < votes.getSize(); i++)
			// Only consider motion affordances
			if ((affs.get(i) instanceof TurnAffordance || affs.get(i) instanceof ForwardAffordance)
					&& votes.get(i) > max) {
				max = votes.get(i);
				maxIndex = i;
			}

		Affordance picked = affs.get(maxIndex);
		picked.setValue(max);
		return picked;
	}

	public void setExplorationVal(float val) {
		for (DecayingExplorationSchema e : exploration)
			e.setExplorationVal(val);

	}

	public void restoreExplorationVal() {
		for (DecayingExplorationSchema e : exploration)
			e.setExplorationVal(explorationReward);
	}
}
