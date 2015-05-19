package edu.usf.ratsim.nsl.modules.qlearning.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import nslj.src.lang.NslDinFloat0;
import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat2;
import nslj.src.lang.NslModule;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.universe.Universe;
import edu.usf.ratsim.support.Configuration;

public class MultiStateProportionalAC extends NslModule implements QLAlgorithm {

	private static final String DUMP_FILENAME = "policy.txt";

	private static final float EPS = 0.2f;

	private static PrintWriter writer;
	private NslDinFloat0 reward;
	private NslDinInt0 takenAction;
	private NslDoutFloat2 value;
	private NslDinFloat1 statesBefore;

	private float alpha;
	private float discountFactor;
	private NslDinFloat1 statesAfter;
	private int numStates;

	private NslDinFloat1 valueAfter;

	private NslDinFloat1 valueBefore;

	private boolean update;

	private Subject subject;

	private int numActions;

	private float lambda;

	public MultiStateProportionalAC(String nslMain, NslModule nslParent,
			Subject subject, int numStates, int numActions,
			float discountFactor, float alpha, float lambda, float initialValue) {
		super(nslMain, nslParent);

		this.discountFactor = discountFactor;
		this.alpha = alpha;
		this.lambda = lambda;
		this.numStates = numStates;
		this.subject = subject;

		takenAction = new NslDinInt0(this, "takenAction");
		reward = new NslDinFloat0(this, "reward");
		statesBefore = new NslDinFloat1(this, "statesBefore", numStates);
		statesAfter = new NslDinFloat1(this, "statesAfter", numStates);

		// Last value holds the critic estimate of the value
		value = new NslDoutFloat2(this, "value", numStates, numActions + 1);
		this.numActions = numActions;

		File f = new File("policy.obj");
		if (f.exists()
				&& Configuration.getBoolean("Experiment.loadSavedPolicy")) {

			try {
				System.out.println("Reading saved policy...");
				FileInputStream fin;
				fin = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fin);
				value.set((float[][]) ois.readObject());
			} catch (FileNotFoundException e) {
				value.set(initialValue);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			value.set(initialValue);
		}

		valueAfter = new NslDinFloat1(this, "actionVotesAfter");
		valueBefore = new NslDinFloat1(this, "actionVotesBefore");

		update = true;
	}

	public void simRun() {
		// Updates may be disabled for data log reasons
		if (update) {
			// Gets the active state as computed at the beginning of the cycle
			int a = takenAction.get();

			// Do the update once for each state
			for (int stateBefore = 0; stateBefore < numStates; stateBefore++)
				// Dont bother if the activation is to small
				if (statesBefore.get(stateBefore) > EPS && a != -1)
					updateLastAction(stateBefore, a);
		}
	}

	private void updateLastAction(int sBefore, int a) {
		// Error in estimation
		float delta = reward.get() + lambda * valueAfter.get(numActions)
				- valueBefore.get(numActions);

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
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("policy.obj");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(value._data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}