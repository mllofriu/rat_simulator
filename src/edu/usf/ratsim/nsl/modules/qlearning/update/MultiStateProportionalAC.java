package edu.usf.ratsim.nsl.modules.qlearning.update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.universe.Universe;
import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.FloatMatrixPort;
import edu.usf.ratsim.micronsl.Float1dPort;
import edu.usf.ratsim.micronsl.Int1dPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.support.Configuration;

public class MultiStateProportionalAC extends Module implements QLAlgorithm {

	private static final String DUMP_FILENAME = "policy.txt";

	private static final float EPS = 0.2f;

	private static PrintWriter writer;

	private float alpha;
	private float discountFactor;
	private int numStates;

	private boolean update;

	private Subject subject;

	private int numActions;

	private float lambda;

	public MultiStateProportionalAC(String name, Subject subject,
			int numActions, float discountFactor, float alpha, float lambda,
			float initialValue) {
		super(name);
		this.discountFactor = discountFactor;
		this.alpha = alpha;
		this.lambda = lambda;
		this.subject = subject;

		this.numActions = numActions;

		update = true;
	}

	public void run() {
		// Updates may be disabled for data log reasons
		if (update) {
			Float1dPortArray reward = (Float1dPortArray) getInPort("reward");
			Int1dPort takenAction = (Int1dPort) getInPort("takenAction");
			Float1dPort statesBefore = (Float1dPort) getInPort("statesBefore");
			Float1dPort statesAfter = (Float1dPort) getInPort("statesAfter");
			Float1dPort valueEstBefore = (Float1dPort) getInPort("valueEstimationBefore");
			Float1dPort valueEstAfter = (Float1dPort) getInPort("valueEstimationAfter");
			FloatMatrixPort value = (FloatMatrixPort) getInPort("value");
			// Gets the active state as computed at the beginning of the cycle
			int a = takenAction.get();

			// Do the update once for each state
			for (int stateBefore = 0; stateBefore < statesBefore.getSize(); stateBefore++)
				// Dont bother if the activation is to small
				if (statesBefore.get(stateBefore) > EPS && a != -1)
					updateLastAction(stateBefore, a, reward, statesBefore,
							statesAfter, valueEstBefore, valueEstAfter, value);
		}
	}

	private void updateLastAction(int sBefore, int a, Float1dPortArray reward,
			Float1dPort statesBefore, Float1dPort statesAfter,
			Float1dPort valueEstBefore, Float1dPort valueEstAfter,
			FloatMatrixPort value) {
		// Error in estimation
		float delta = reward.get() + lambda * valueEstAfter.get(0)
				- valueEstBefore.get(0);
		
//		float delta = reward.get() + valueEstAfter.get(0)
//				- valueEstBefore.get(0);
		// Update action
		float actionVal = value.get(sBefore, a);
		float newActionValue = statesBefore.get(sBefore)
				* (actionVal + alpha * (delta - actionVal))
				+ (1 - statesBefore.get(sBefore)) * actionVal;
		
		if (Float.isInfinite(newActionValue) || Float.isNaN(newActionValue))
			System.out.println("Numeric Error");
		value.set(sBefore, a, newActionValue);

		// Update value
		float currValue = value.get(sBefore, numActions);
		float newValue = statesBefore.get(sBefore)
				* (currValue + alpha * (delta - currValue))
				+ (1 - statesBefore.get(sBefore)) * currValue;
		if (Float.isInfinite(newValue) || Float.isNaN(newValue))
			System.out.println("Numeric Error");
		value.set(sBefore, numActions, newValue);
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
			String rep, int numIntentions, Universe univ, Subject sub) {
		// TODO: get dumppolicy back
		// synchronized (MultiStateProportionalQL.class) {
		// // Deactivate updates
		// sub.setPassiveMode(true);
		// PrintWriter writer = MultiStateProportionalQL.getWriter();
		//
		// for (int intention = 0; intention < numIntentions; intention++) {
		// for (float xInc = MARGIN; xInc
		// - (univ.getBoundingRectangle().getWidth() - MARGIN / 2) < 1e-8; xInc
		// += INTERVAL) {
		// for (float yInc = MARGIN; yInc
		// - (univ.getBoundingRectangle().getHeight() - MARGIN / 2) < 1e-8; yInc
		// += INTERVAL) {
		// float x = (float) (univ.getBoundingRectangle()
		// .getMinX() + xInc);
		// float y = (float) (univ.getBoundingRectangle()
		// .getMinY() + yInc);
		//
		// // List<Float> preferredAngles = new
		// // LinkedList<Float>();
		// float maxVal = Float.NEGATIVE_INFINITY;
		// float bestAngle = 0;
		// for (float angle = 0; angle <= 2 * Math.PI; angle += ANGLE_INTERVAL)
		// {
		// univ.setRobotPosition(new Point2D.Float(x, y),
		// angle);
		// rat.stepCycle();
		// // // float forwardVal =
		// // ((MultiScaleMultiIntentionCooperativeModel) rat
		// // //
		// //
		// .getModel()).getQLVotes().getVotes().get(Utiles.discretizeAction(0));
		// // if( forwardVal > maxVal){
		// // maxVal = forwardVal;
		// // bestAngle = angle;
		// // }
		// for (int action = 0; action < subject.getNumActions(); action++) {
		// float angleVal = ((MultiScaleMultiIntentionCooperativeModel) rat
		// .getModel()).getQLVotes().getVotes()
		// .get(action);
		// if (angleVal > maxVal) {
		// maxVal = angleVal;
		// bestAngle = angle;
		// }
		// }
		//
		// // If goes forward, it is the preferred angle
		// }
		//
		// String preferredAngleString = new Float(bestAngle)
		// .toString();
		//
		// writer.println(trial + '\t' + groupName + '\t'
		// + subName + '\t' + rep + '\t' + x + "\t" + y
		// + "\t" + intention + "\t"
		// + preferredAngleString + "\t" + maxVal);
		//
		// }
		// }
		// }
		// // Re enable updates
		// ((RLRatModel) rat.getModel()).setPassiveMode(false);
		// univ.clearRobotAte();
		//
		// }
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

	@Override
	public void savePolicy() {
		// FileOutputStream fout;
		// try {
		// fout = new FileOutputStream("policy.obj");
		// ObjectOutputStream oos = new ObjectOutputStream(fout);
		// oos.writeObject(value.getData());
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}

	@Override
	public boolean usesRandom() {
		return false;
	}

}