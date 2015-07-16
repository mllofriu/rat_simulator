package edu.usf.ratsim.experiment.subject;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;

import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.utils.ElementWrapper;
import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.FloatMatrixPort;
import edu.usf.ratsim.micronsl.Float1dPort;
import edu.usf.ratsim.micronsl.Int1dPort;
import edu.usf.ratsim.micronsl.Model;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.micronsl.Port;
import edu.usf.ratsim.nsl.modules.ArtificialHDCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.AttentionalExplorer;
import edu.usf.ratsim.nsl.modules.ClosestFeeder;
import edu.usf.ratsim.nsl.modules.CopyStateModule;
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
import edu.usf.ratsim.nsl.modules.SubjectAte;
import edu.usf.ratsim.nsl.modules.SubjectTriedToEat;
import edu.usf.ratsim.nsl.modules.Voter;
import edu.usf.ratsim.nsl.modules.qlearning.Reward;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.GradientVotes;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.HalfAndHalfConnectionVotes;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.NoExploration;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalVotes;
import edu.usf.ratsim.nsl.modules.qlearning.update.MultiStateProportionalAC;
import edu.usf.ratsim.nsl.modules.qlearning.update.MultiStateProportionalQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.QLAlgorithm;
import edu.usf.ratsim.nsl.modules.taxic.FlashingTaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.taxic.TaxicFoodFinderSchema;

public class MultiScaleArtificialPCModel extends Model {

	private List<ArtificialPlaceCellLayer> beforePcls;
	private List<QLAlgorithm> qLUpdVal;
	// private ProportionalExplorer actionPerformerVote;
	// private List<WTAVotes> qLActionSel;
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
	private Module rlVotes;
	private String rlType;
	private List<DecayingExplorationSchema> exploration;
	private JointStatesManyConcatenate jointPCHDIntentionState;
	private Intention intentionGetter;
	private LinkedList<JointStatesManyMultiply> jStateList;
	private float explorationReward;
	private List<Port> pclHDIntentionPortList;

	public MultiScaleArtificialPCModel() {
	}

	public MultiScaleArtificialPCModel(ElementWrapper params, Subject subject,
			LocalizableRobot lRobot) {
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
		boolean estimateValue = params.getChildBoolean("estimateValue");
		explorationReward = params.getChildFloat("explorationReward");
		// float wallFollowingVal = params.getChildFloat("wallFollowingVal");
		float attentionExploringVal = params
				.getChildFloat("attentionExploringVal");
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
		// qLActionSel = new LinkedList<WTAVotes>();
		exploration = new LinkedList<DecayingExplorationSchema>();

		// beforeActiveGoalDecider = new ActiveGoalDecider(
		// BEFORE_ACTIVE_GOAL_DECIDER_STR, this);
		lastAteGoalDecider = new LastAteGoalDecider("Last Ate Goal Decider");
		addModule(lastAteGoalDecider);

		LastTriedToEatGoalDecider lastTriedToEatGoalDecider = new LastTriedToEatGoalDecider(
				"Last Tried To Eat Goal Decider");
		addModule(lastTriedToEatGoalDecider);

		Module intention;
		if (numIntentions > 1) {
			intention = new LastAteIntention("Intention", numIntentions);
			intention.addInPort("goalFeeder",
					lastAteGoalDecider.getOutPort("goalFeeder"));
		} else {
			intention = new NoIntention("Intention", numIntentions);
		}
		addModule(intention);
		intentionGetter = (Intention) intention;

		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numPCLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer("PCL "
					+ i, lRobot, radius, numPCCellsPerLayer, pclSeed,
					placeCellType, xmin, ymin, xmax, ymax,
					lRobot.getAllFeeders(), goalCellProportion);
			beforePcls.add(pcl);
			addModule(pcl);
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
			ArtificialHDCellLayer hd = new ArtificialHDCellLayer("HD " + i,
					numHDCells, lRobot);
			beforeHDs.add(hd);
			addModule(hd);
			numHDCells += stepHDCellsPerLayer;
		}

		List<Integer> bpihdSizes = new LinkedList<Integer>();
		jStateList = new LinkedList<JointStatesManyMultiply>();
		pclHDIntentionPortList = new LinkedList<Port>();
		int jointStateMultiplyNum = 0;
		for (ArtificialPlaceCellLayer pcl : beforePcls)
			for (ArtificialHDCellLayer hd : beforeHDs) {
				// JointStates jStates = new JointStates(BEFORE_PIHD
				// + (i * numHDLayers + j), this, universe, beforePcls
				// .get(i).getSize() * numIntentions,
				// beforeHDs.get(j).getSize());
				List<Port> states = new LinkedList<Port>();
				states.add((Float1dPort) intention.getOutPort("intention"));
				states.add((Float1dPort) pcl.getOutPort("activation"));
				states.add((Float1dPort) hd.getOutPort("activation"));

				JointStatesManyMultiply jStates = new JointStatesManyMultiply(
						"Joint State Multiply " + jointStateMultiplyNum);
				jStates.addInPorts(states);
				jointStateMultiplyNum++;
				jStateList.add(jStates);
				pclHDIntentionPortList.add((Float1dPort) jStates
						.getOutPort("jointState"));
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
		jointPCHDIntentionState = new JointStatesManyConcatenate(
				"Joint PC HD Intention State");
		jointPCHDIntentionState.addInPorts(pclHDIntentionPortList);
		addModule(jointPCHDIntentionState);

		// Create value matrix
		int numStates = ((Float1dPort) jointPCHDIntentionState
				.getOutPort("jointState")).getSize();
		float[][] value = new float[numStates][numActions + 1];
		FloatMatrixPort valuePort = new FloatMatrixPort((Module) null, value);

		List<Port> votesPorts = new LinkedList<Port>();
		// Take the value of each state and vote for an action
		if (voteType.equals("proportional"))
			if (rlType.equals("actorCritic"))
				rlVotes = new ProportionalVotes("RL votes");
			else
				rlVotes = new ProportionalVotes("RL votes");
		else if (voteType.equals("gradientConnection"))
			rlVotes = new GradientVotes("RL votes",
					(Float1dPortArray) jointPCHDIntentionState
							.getOutPort("jointState"), valuePort, numActions);
		else if (voteType.equals("halfAndHalfConnection"))
			rlVotes = new HalfAndHalfConnectionVotes("RL votes", numActions);
		else
			throw new RuntimeException("Vote mechanism not implemented");
		rlVotes.addInPort("states",
				jointPCHDIntentionState.getOutPort("jointState"));
		rlVotes.addInPort("value", valuePort);

		addModule(rlVotes);
		votesPorts.add((Float1dPort) rlVotes.getOutPort("votes"));

		// Create taxic driver
		// new GeneralTaxicFoodFinderSchema(BEFORE_FOOD_FINDER_STR, this, robot,
		// universe, numActions, flashingReward, nonFlashingReward);
		TaxicFoodFinderSchema taxicff = new TaxicFoodFinderSchema(
				"Taxic Food Finder", subject, lRobot, nonFlashingReward,
				discountFactor, estimateValue);
		taxicff.addInPort("goalFeeder",
				lastTriedToEatGoalDecider.getOutPort("goalFeeder"));
		addModule(taxicff);
		votesPorts.add((Float1dPort) taxicff.getOutPort("votes"));

		FlashingTaxicFoodFinderSchema flashingTaxicFF = new FlashingTaxicFoodFinderSchema(
				"Flashing Taxic Food Finder", subject, lRobot, flashingReward,
				discountFactor, estimateValue);
		flashingTaxicFF.addInPort("goalFeeder",
				lastAteGoalDecider.getOutPort("goalFeeder"));
		addModule(flashingTaxicFF);
		votesPorts.add((Float1dPort) flashingTaxicFF.getOutPort("votes"));

		DecayingExplorationSchema decayExpl = new DecayingExplorationSchema(
				"Decay Explorer", subject, lRobot, explorationReward,
				explorationHalfLifeVal);
		exploration.add(decayExpl);
		addModule(decayExpl);
		votesPorts.add((Float1dPort) decayExpl.getOutPort("votes"));

		StillExplorer stillExpl = new StillExplorer("Still Explorer",
				maxActionsSinceForward, subject, stillExplorationVal);
		addModule(stillExpl);
		votesPorts.add((Float1dPort) stillExpl.getOutPort("votes"));
		// Wall following for obst. avoidance
		// new WallAvoider(BEFORE_WALLAVOID_STR, this, subject,
		// wallFollowingVal,
		// numActions);
		// new TaxicWallOpeningsSchema(BEFORE_WALLFOLLOW_STR, this, subject,
		// lRobot, wallFollowingVal);

		AttentionalExplorer attExpl = new AttentionalExplorer(
				"Attentional Explorer", subject, attentionExploringVal,
				maxAttentionSpan);
		addModule(attExpl);
		votesPorts.add((Float1dPort) attExpl.getOutPort("votes"));
		
		// Joint votes
		jointVotes = new JointStatesManySum("Votes");
		jointVotes.addInPorts(votesPorts);
		addModule(jointVotes);

		// Copy last state and votes before recomputing to use in RL algorithm
		CopyStateModule stateCopy = new CopyStateModule("States Before");
		stateCopy.addInPort("toCopy",
				jointPCHDIntentionState.getOutPort("jointState"), true);
		addModule(stateCopy);

		CopyStateModule votesCopy = new CopyStateModule("Votes Before");
		votesCopy.addInPort("toCopy",
				(Float1dPort) jointVotes.getOutPort("jointState"), true);
		addModule(votesCopy);

		// Get votes from QL and other behaviors and perform an action
		// One vote per layer (one now) + taxic + wf
		if (deterministic) {
			actionPerformer = new NoExploration("Action Performer", subject);
			actionPerformer.addInPort("votes",
					jointVotes.getOutPort("jointState"));
			addModule(actionPerformer);
		}
		Port takenActionPort = actionPerformer.getOutPort("takenAction");
		// Add the taken action ports to some previous exploration modules
		attExpl.addInPort("takenAction", takenActionPort, true);
		stillExpl.addInPort("takenAction", takenActionPort, true);

		SubjectAte subAte = new SubjectAte("Subject Ate", subject);
		subAte.addInPort("takenAction", takenActionPort); // just for dependency
		addModule(subAte);
		
		SubjectTriedToEat subTriedToEat = new SubjectTriedToEat("Subject Tried To Eat", subject);
		subTriedToEat.addInPort("takenAction", takenActionPort); // just for dependency
		addModule(subTriedToEat);
		
		ClosestFeeder closestFeeder = new ClosestFeeder("Closest Feeder After Move", subject);
		closestFeeder.addInPort("takenAction", takenActionPort);
		addModule(closestFeeder);
		
		// Reversed dependencies because I need to base my desicion on the last cycle
		lastAteGoalDecider.addInPort("subAte", subAte.getOutPort("subAte"), true);
		lastAteGoalDecider.addInPort("closestFeeder", closestFeeder.getOutPort("closestFeeder"), true);
		lastTriedToEatGoalDecider.addInPort("subTriedToEat", subTriedToEat.getOutPort("subTriedToEat"), true);
		lastTriedToEatGoalDecider.addInPort("closestFeeder", closestFeeder.getOutPort("closestFeeder"), true);
		
		Reward reward = new Reward("Reward", foodReward, nonFoodReward);
		reward.addInPort("subAte", subAte.getOutPort("subAte"));
		addModule(reward);

		if (rlType.equals("proportionalQl")) {
			// MultiStateProportionalQLReplay mspql = new
			// MultiStateProportionalQLReplay(
			// QL_STR, this, subject, bAll.getSize(), numActions,
			// discountFactor, alpha, initialValue);
			MultiStateProportionalQL mspql = new MultiStateProportionalQL(
					"RL Module",

					subject, numActions, discountFactor, alpha, initialValue);

			mspql.addInPort("reward",
					(Float1dPortArray) reward.getOutPort("reward"));
			mspql.addInPort("takenAction", takenActionPort);
			mspql.addInPort("statesBefore", getModule("States Before")
					.getOutPort("copy"));
			mspql.addInPort("statesAfter",
					jointPCHDIntentionState.getOutPort("jointState"));
			mspql.addInPort("value", valuePort);
			mspql.addInPort("votesBefore", getModule("Votes Before")
					.getOutPort("copy"));
			mspql.addInPort("votesAfter", jointVotes.getOutPort("jointState"));
			ql = mspql;
			qLUpdVal.add(mspql);
			addModule(mspql);
		} else if (rlType.equals("actorCritic")) {
			MultiStateProportionalAC mspac = new MultiStateProportionalAC(
					"RL Module", subject, numActions, discountFactor, alpha,
					discountFactor, initialValue);
			mspac.addInPort("reward",
					(Float1dPortArray) reward.getOutPort("reward"));
			mspac.addInPort("takenAction", takenActionPort);
			mspac.addInPort("statesBefore", getModule("States Before")
					.getOutPort("copy"));
			mspac.addInPort("statesAfter",
					jointPCHDIntentionState.getOutPort("jointState"));
			mspac.addInPort("value", valuePort);
			mspac.addInPort("votesBefore", getModule("Votes Before")
					.getOutPort("copy"));
			mspac.addInPort("votesAfter", jointVotes.getOutPort("jointState"));
			addModule(mspac);
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

		System.out.println("Building run order");
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

	// protected void finalize() {
	// super.finalize();
	//
	// // System.out.println("NsL model being finalized");
	// }

	public void savePolicy() {
		ql.savePolicy();
	}

	public void setPassiveMode(boolean passive) {
		ql.setUpdatesEnabled(!passive);
	}

	public JointStatesManySum getJointVoites() {
		return jointVotes;
	}

	// public Voter getQLVotes() {
	// return rlVotes;
	// }

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
	 * @param intentionGetter
	 * @return
	 */
	// public Affordance getHypotheticAction(Point3f pos, float theta,
	// List<Affordance> affs, int inte) {
	// intention.simRun(inte);
	//
	// for (ArtificialPlaceCellLayer pcl : beforePcls)
	// // TODO: add feeder cells to policies
	// pcl.simRun(pos, false);
	// for (ArtificialHDCellLayer hdcl : beforeHDs)
	// hdcl.simRun(theta);
	//
	// for (JointStatesManyMultiply jsmm : jStateList)
	// jsmm.simRun();
	//
	// jointPCHDIntentionState.simRun();
	//
	// rlVotes.simRun();
	//
	// NslDoutFloat1 votes = rlVotes.getVotes();
	// float max = Float.NEGATIVE_INFINITY;
	// int maxIndex = 0;
	// for (int i = 0; i < votes.getSize(); i++)
	// // Only consider motion affordances
	// if ((affs.get(i) instanceof TurnAffordance || affs.get(i) instanceof
	// ForwardAffordance)
	// && votes.get(i) > max) {
	// max = votes.get(i);
	// maxIndex = i;
	// }
	//
	// Affordance picked = affs.get(maxIndex);
	// picked.setValue(max);
	// return picked;
	// }

	public void setExplorationVal(float val) {
		for (DecayingExplorationSchema e : exploration)
			e.setExplorationVal(val);

	}

	public void restoreExplorationVal() {
		for (DecayingExplorationSchema e : exploration)
			e.setExplorationVal(explorationReward);
	}

	public float getValue(Point3f point, int inte, float angleInterval,
			float distToWall) {
		intentionGetter.simRun(inte);

		for (ArtificialPlaceCellLayer pcl : beforePcls)
			// TODO: add feeder cells to policies
			pcl.simRun(point, false, distToWall);

		float maxVal = Float.NEGATIVE_INFINITY;
		for (float angle = 0; angle <= 2 * Math.PI; angle += angleInterval) {
			for (ArtificialHDCellLayer hdcl : beforeHDs)
				hdcl.simRun(angle);

			for (JointStatesManyMultiply jsmm : jStateList)
				jsmm.simRun();

			jointPCHDIntentionState.simRun();

			rlVotes.simRun();

			float[] votes = ((Voter) rlVotes).getVotes();
			float val = votes[votes.length - 1];

			if (val > maxVal) {
				maxVal = val;
			}
		}

		return maxVal;
	}
}
