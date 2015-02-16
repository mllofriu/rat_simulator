package edu.usf.ratsim.nsl.modules.qlearning.update;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslDinFloat0;
import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat2;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.MultiScaleMultiIntentionCooperativeModel;
import edu.usf.ratsim.experiment.model.RLRatModel;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;

public class MultiStateProportionalQL extends NslModule implements PolicyDumper, QLUpdater {

	private static final String DUMP_FILENAME = "policy.txt";

	private static final float EPS = 0.2f;

	private static final float INTERVAL = 0.05f;
	// Margin for ignoring inside maze
	private static final float MARGIN = 0.1f;

	private static final float ANGLE_INTERVAL = 0.314f;

	private static PrintWriter writer;
	private NslDinFloat0 reward;
	private NslDinInt0 takenAction;
	private NslDoutFloat2 value;
	private NslDinFloat1 statesBefore;

	private float alpha;
	private float discountFactor;
	private NslDinFloat1 statesAfter;
	private int numStates;

	private NslDinFloat1 actionVotesAfter;

	private NslDinFloat1 actionVotesBefore;

	private IRobot robot;

	private boolean update;

	public MultiStateProportionalQL(String nslMain, NslModule nslParent,
			int numStates, int numActions, float discountFactor, float alpha,
			float initialValue, IRobot robot) {
		super(nslMain, nslParent);

		this.discountFactor = discountFactor;
		this.alpha = alpha;
		this.numStates = numStates;
		this.robot = robot;

		takenAction = new NslDinInt0(this, "takenAction");
		reward = new NslDinFloat0(this, "reward");
		statesBefore = new NslDinFloat1(this, "statesBefore", numStates);
		statesAfter = new NslDinFloat1(this, "statesAfter", numStates);

		value = new NslDoutFloat2(this, "value", numStates, numActions);
		value.set(initialValue);

		actionVotesAfter = new NslDinFloat1(this, "actionVotesAfter",
				numActions);
		actionVotesBefore = new NslDinFloat1(this, "actionVotesBefore",
				numActions);

		update = true;
	}

	public void simRun() {
		// Updates may be disabled for data log reasons
		if (update) {
			// Gets the active state as computed at the beginning of the cycle
			int a = takenAction.get();

			// Maximize the action value after the movement
			float maxExpectedR = Float.NEGATIVE_INFINITY;
			for (int action = 0; action < actionVotesAfter.getSize(); action++)
				if (maxExpectedR < actionVotesAfter.get(action))
					maxExpectedR = actionVotesAfter.get(action);

			// Do the update once for each state
			for (int stateBefore = 0; stateBefore < numStates; stateBefore++)
				// Dont bother if the activation is to small
				if (statesBefore.get(stateBefore) > EPS)
					updateLastAction(stateBefore, a, maxExpectedR);
		}
	}

	private void updateLastAction(int sBefore, int a, float maxERNextState) {
		float val = value.get(sBefore, a);
		float delta;
		// If eating cut the cycle - episodic ql
		if (a == Utiles.eatAction)
			// Just look at eating future prediction
			delta = alpha
					* (reward.get() + discountFactor
							* actionVotesAfter.get(Utiles.eatAction) - (val + actionVotesBefore
							.get(a)));
		// For all other actions - normal ql
		else
			delta = alpha
					* (reward.get() + discountFactor * (maxERNextState) - (val + actionVotesBefore
							.get(a)));
		float newValue = statesBefore.get(sBefore) * (val + delta)
				+ (1 - statesBefore.get(sBefore)) * val;

		if (Float.isInfinite(newValue) || Float.isNaN(newValue))
			System.out.println("Numeric Error");
		value.set(sBefore, a, newValue);
	}

	/**
	 * Dumps the qlearning policy with a certain intention to a file. The
	 * alignment between pcl cells and ql states is assumed for efficiency
	 * purposes.
	 * 
	 * @param rep
	 * @param subName
	 * @param trial
	 * @param rep
	 * 
	 * @param writer
	 * @param pcl
	 */
	public void dumpPolicy(String trial, String groupName, String subName,
			String rep, int numIntentions, ExperimentUniverse univ,
			ExpSubject rat) {
		synchronized (QLSupport.class) {
			// Deactivate updates
			((RLRatModel) rat.getModel()).setPassiveMode(true);
			PrintWriter writer = MultiStateProportionalQL.getWriter();

			for (int intention = 0; intention < numIntentions; intention++) {
				for (float xInc = MARGIN; xInc
						- (univ.getBoundingRectangle().getWidth() - MARGIN/2) < 1e-8; xInc += INTERVAL) {
					for (float yInc = MARGIN; yInc
							- (univ.getBoundingRectangle().getHeight() - MARGIN/2) < 1e-8; yInc += INTERVAL) {
						float x = (float) (univ.getBoundingRectangle()
								.getMinX() + xInc);
						float y = (float) (univ.getBoundingRectangle()
								.getMinY() + yInc);

//						List<Float> preferredAngles = new LinkedList<Float>();
						float maxVal = Float.NEGATIVE_INFINITY;
						float bestAngle = 0;
						for (float angle = 0; angle <= 2 * Math.PI; angle += ANGLE_INTERVAL) {
							univ.setRobotPosition(new Point2D.Float(x, y),
									angle);
							rat.stepCycle();
////							float forwardVal = ((MultiScaleMultiIntentionCooperativeModel) rat
////									.getModel()).getQLVotes().getVotes().get(Utiles.discretizeAction(0));
//							if( forwardVal > maxVal){
//								maxVal = forwardVal;
//								bestAngle = angle;
//							}
							for (int action = 0; action < Utiles.numActions; action++){
								float angleVal = ((MultiScaleMultiIntentionCooperativeModel) rat
									.getModel()).getQLVotes().getVotes().get(action);
								if (angleVal > maxVal){
									maxVal = angleVal;
									bestAngle = angle;
								}
							}
								
							// If goes forward, it is the preferred angle
						}

						String preferredAngleString = new Float(bestAngle).toString();

						writer.println(trial + '\t' + groupName + '\t'
								+ subName + '\t' + rep + '\t' + x + "\t" + y
								+ "\t" + intention + "\t"
								+ preferredAngleString + "\t" + maxVal);

					}
				}
			}
			// Re enable updates
			((RLRatModel) rat.getModel()).setPassiveMode(false);
			univ.clearRobotAte();

		}
	}

	private static PrintWriter getWriter() {
		if (writer == null) {
			try {
				writer = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(new File(Configuration
								.getString("Log.DIRECTORY") + DUMP_FILENAME))),
						true);
				writer.println("trial\tgroup\tsubject\trepetition\tx\ty\tintention\theading\tval");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return writer;
	}

	public void setUpdatesEnabled(boolean enabled) {
		update = enabled;
	}

}