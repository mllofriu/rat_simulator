package edu.usf.ratsim.experiment.subject;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point3f;

import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.utils.ElementWrapper;
import edu.usf.ratsim.micronsl.Float1dPort;
import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.FloatMatrixPort;
import edu.usf.ratsim.micronsl.Model;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.micronsl.Port;
import edu.usf.ratsim.nsl.modules.ArtificialHDCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCell;
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
import edu.usf.ratsim.nsl.modules.StillExplorer;
import edu.usf.ratsim.nsl.modules.SubjectAte;
import edu.usf.ratsim.nsl.modules.SubjectTriedToEat;
import edu.usf.ratsim.nsl.modules.Voter;
import edu.usf.ratsim.nsl.modules.qlearning.Reward;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.GradientVotes;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.HalfAndHalfConnectionValue;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.HalfAndHalfConnectionVotes;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.NoExploration;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalVotes;
import edu.usf.ratsim.nsl.modules.qlearning.update.MultiStateProportionalAC;
import edu.usf.ratsim.nsl.modules.qlearning.update.MultiStateProportionalQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.QLAlgorithm;
import edu.usf.ratsim.nsl.modules.taxic.FlashingTaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.taxic.FlashingTaxicValueSchema;
import edu.usf.ratsim.nsl.modules.taxic.TaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.taxic.TaxicValueSchema;

public class MultiScaleArtificialPCModel extends Model {

	private List<ArtificialPlaceCellLayer> beforePcls;
	private List<QLAlgorithm> qLUpdVal;
	// private ProportionalExplorer actionPerformerVote;
	// private List<WTAVotes> qLActionSel;
	private int numPCLayers;
	private LastAteGoalDecider lastAteGoalDecider;
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
	private HalfAndHalfConnectionValue rlValue;

	public MultiScaleArtificialPCModel() {
	}

	public MultiScaleArtificialPCModel(ElementWrapper params, Subject subject,
			LocalizableRobot lRobot) {
		// Get some configuration values for place cells + qlearning
		float minPCRadius = params.getChildFloat("minPCRadius");
		float maxPCRadius = params.getChildFloat("maxPCRadius");

		numPCLayers = params.getChildInt("numPCLayers");
		int numPCCellsPerLayer = params.getChildInt("numPCCellsPerLayer");
		float minHDRadius = params.getChildFloat("minHDRadius");
		float maxHDRadius = params.getChildFloat("maxHDRadius");
		int numHDLayers = params.getChildInt("numHDLayers");
		int numHDCellsPerLayer = params.getChildInt("numHDCellsPerLayer");
		String placeCellType = params.getChildText("placeCells");
		float goalCellProportion = params.getChildFloat("goalCellProportion");
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

		beforePcls = new LinkedList<ArtificialPlaceCellLayer>();
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
		float radius = minPCRadius;
		// For each layer
		for (int i = 0; i < numPCLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer("PCL "
					+ i, lRobot, radius, numPCCellsPerLayer, placeCellType,
					xmin, ymin, xmax, ymax, lRobot.getAllFeeders(),
					goalCellProportion);
			beforePcls.add(pcl);
			addModule(pcl);
			// JointStates placeIntention = new JointStates(
			// BEFORE_PLACE_INTENTION_STR + i, this, universe,
			// pcl.getSize(), numIntentions);
			// Update radius
			radius += (maxPCRadius - minPCRadius) / (numPCLayers - 1);
		}

		// Create the layers
		float hdRadius = minHDRadius;
		beforeHDs = new LinkedList<ArtificialHDCellLayer>();
		for (int i = 0; i < numHDLayers; i++) {
			ArtificialHDCellLayer hd = new ArtificialHDCellLayer("HD " + i,
					numHDCellsPerLayer, hdRadius, lRobot);
			beforeHDs.add(hd);
			addModule(hd);
			radius += (maxHDRadius - minHDRadius) / (numHDLayers - 1);
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
				states.add((Float1dPort) pcl.getOutPort("activation"));
				states.add((Float1dPort) intention.getOutPort("intention"));
				states.add((Float1dPort) hd.getOutPort("activation"));

				JointStatesManyMultiply jStates = new JointStatesManyMultiply(
						"Joint State Multiply " + jointStateMultiplyNum);
				jStates.addInPorts(states);
				addModule(jStates);

				jointStateMultiplyNum++;
				jStateList.add(jStates);
				pclHDIntentionPortList.add((Float1dPort) jStates
						.getOutPort("jointState"));
			}

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
		// RL votes are based on previous state
		rlVotes.addInPort("states",
				jointPCHDIntentionState.getOutPort("jointState"), true);
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
				lastTriedToEatGoalDecider.getOutPort("goalFeeder"), true);
		addModule(taxicff);
		votesPorts.add((Float1dPort) taxicff.getOutPort("votes"));

		FlashingTaxicFoodFinderSchema flashingTaxicFF = new FlashingTaxicFoodFinderSchema(
				"Flashing Taxic Food Finder", subject, lRobot, flashingReward,
				discountFactor, estimateValue);
		flashingTaxicFF.addInPort("goalFeeder",
				lastAteGoalDecider.getOutPort("goalFeeder"), true);
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

		// Get votes from QL and other behaviors and perform an action
		// One vote per layer (one now) + taxic + wf
		actionPerformer = new NoExploration("Action Performer", subject);
		actionPerformer.addInPort("votes", jointVotes.getOutPort("jointState"));
		addModule(actionPerformer);
		// State calculation should be done after movement
		for (ArtificialPlaceCellLayer pcl : beforePcls)
			pcl.addPreReq(actionPerformer);
		for (ArtificialHDCellLayer hd : beforeHDs)
			hd.addPreReq(actionPerformer);
		intention.addPreReq(actionPerformer);

		Port takenActionPort = actionPerformer.getOutPort("takenAction");
		// Add the taken action ports to some previous exploration modules
		attExpl.addInPort("takenAction", takenActionPort, true);
		stillExpl.addInPort("takenAction", takenActionPort, true);

		List<Port> valueEstimationPorts = new LinkedList<Port>();
		TaxicValueSchema taxVal = new TaxicValueSchema("Taxic Value Estimator",
				subject, lRobot, nonFlashingReward, discountFactor,
				estimateValue);
		taxVal.addInPort("goalFeeder",
				lastTriedToEatGoalDecider.getOutPort("goalFeeder"));
		taxVal.addInPort("takenAction", takenActionPort); // just for dependency
		valueEstimationPorts.add(taxVal.getOutPort("value"));
		addModule(taxVal);

		FlashingTaxicValueSchema flashTaxVal = new FlashingTaxicValueSchema(
				"Flashing Taxic Value Estimator", subject, lRobot,
				flashingReward, discountFactor, estimateValue);
		flashTaxVal.addInPort("goalFeeder",
				lastAteGoalDecider.getOutPort("goalFeeder"));
		flashTaxVal.addInPort("takenAction", takenActionPort); // just for
																// dependency
		valueEstimationPorts.add(flashTaxVal.getOutPort("value"));
		addModule(flashTaxVal);

		if (voteType.equals("halfAndHalfConnection"))
			rlValue = new HalfAndHalfConnectionValue("RL value estimation",
					numActions);
		else
			throw new RuntimeException("Vote mechanism not implemented");
		rlValue.addInPort("states",
				jointPCHDIntentionState.getOutPort("jointState"));
		rlValue.addInPort("value", valuePort);
		rlValue.addInPort("takenAction", takenActionPort); // just for
															// dependency
		valueEstimationPorts.add(rlValue.getOutPort("valueEst"));
		addModule(rlValue);

		JointStatesManySum sumValue = new JointStatesManySum(
				"Joint value estimation");
		sumValue.addInPorts(valueEstimationPorts);
		addModule(sumValue);

		CopyStateModule valueCopy = new CopyStateModule(
				"Value Estimation Before");
		valueCopy.addInPort("toCopy",
				(Float1dPort) sumValue.getOutPort("jointState"), true);
		addModule(valueCopy);

		SubjectAte subAte = new SubjectAte("Subject Ate", subject);
		subAte.addInPort("takenAction", takenActionPort); // just for dependency
		addModule(subAte);

		SubjectTriedToEat subTriedToEat = new SubjectTriedToEat(
				"Subject Tried To Eat", subject);
		subTriedToEat.addInPort("takenAction", takenActionPort); // just for
																	// dependency
		addModule(subTriedToEat);

		ClosestFeeder closestFeeder = new ClosestFeeder(
				"Closest Feeder After Move", subject);
		closestFeeder.addInPort("takenAction", takenActionPort);
		addModule(closestFeeder);

		lastAteGoalDecider.addInPort("subAte", subAte.getOutPort("subAte"));
		lastAteGoalDecider.addInPort("closestFeeder",
				closestFeeder.getOutPort("closestFeeder"));
		lastTriedToEatGoalDecider.addInPort("subTriedToEat",
				subTriedToEat.getOutPort("subTriedToEat"));
		lastTriedToEatGoalDecider.addInPort("closestFeeder",
				closestFeeder.getOutPort("closestFeeder"));

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
			mspac.addInPort("valueEstimationAfter",
					sumValue.getOutPort("jointState"));
			mspac.addInPort("valueEstimationBefore",
					getModule("Value Estimation Before").getOutPort("copy"));
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

		float avgVal = 0f;
		int numAngles = 0;
		for (float angle = 0; angle <= 2 * Math.PI; angle += angleInterval) {
			for (ArtificialHDCellLayer hdcl : beforeHDs)
				hdcl.simRun(angle);

			for (JointStatesManyMultiply jsmm : jStateList)
				jsmm.run();

			jointPCHDIntentionState.run();

			rlValue.run();

			float[] votes = ((Voter) rlValue).getVotes();
			float val = votes[0];

			// if (Math.abs(val) > Math.abs(avgVal)) {
			// // if (val > maxVal) {
			// avgVal = val;
			// }
			avgVal += votes[0];
			numAngles++;
		}
		avgVal /= numAngles;

		for (ArtificialPlaceCellLayer pcl : beforePcls)
			// TODO: add feeder cells to policies
			pcl.clear();

		for (ArtificialHDCellLayer hdcl : beforeHDs)
			hdcl.clear();

		return avgVal;
	}

	public List<ArtificialPlaceCell> getPlaceCells() {
		List <ArtificialPlaceCell> res = new LinkedList<ArtificialPlaceCell>();
		for (ArtificialPlaceCellLayer pcl : beforePcls){
			res.addAll(pcl.getCells());
		}
	
		return res;
	}
}
