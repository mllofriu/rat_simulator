package edu.usf.ratsim.nsl.modules.qlearning.update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import nslj.src.lang.NslDinFloat0;
import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat2;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCell;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;

public class MultiStateProportionalQL extends NslModule implements PolicyDumper {

	private static final String DUMP_FILENAME = "policy.txt";

	private static final float EPS = 0.0f;

	private static PrintWriter writer;
	private NslDinFloat0 reward;
	private NslDinInt0 takenAction;
	private NslDoutFloat2 value;
	private NslDinFloat1 statesBefore;

	private float alpha;
	private float discountFactor;
	private NslDinFloat1 statesAfter;
	private int numStates;

	public MultiStateProportionalQL(String nslMain, NslModule nslParent,
			int numStates, int numActions, float discountFactor, float alpha,
			float initialValue) {
		super(nslMain, nslParent);

		this.discountFactor = discountFactor;
		this.alpha = alpha;
		this.numStates = numStates;

		takenAction = new NslDinInt0(this, "takenAction");
		reward = new NslDinFloat0(this, "reward");
		statesBefore = new NslDinFloat1(this, "statesBefore", numStates);
		statesAfter = new NslDinFloat1(this, "statesAfter", numStates);

		value = new NslDoutFloat2(this, "value", numStates, numActions);
		value.set(initialValue);
		// for (int s = 0; s < numStates; s++)
		// for (int a = 0; a < numActions; a++)
		// value.set(s,a,initialValue);

	}

	public void simRun() {
		// // Gets the active state as computed at the beginning of the cycle
		// int sBefore = getActiveState(statesBefore);
		// int sAfter = getActiveState(statesAfter);
		int a = takenAction.get();
//		System.out.println(a);
		// updateLastAction(sBefore, sAfter, a);
		for (int stateAfter = 0; stateAfter < numStates; stateAfter++) {
			if (statesAfter.get(stateAfter) != 0){
				float maxERNextState;
				if (a != -1)
					maxERNextState = getMaxExpectedReward(value, stateAfter);
				else
					maxERNextState = 0;
				for (int stateBefore = 0; stateBefore < numStates; stateBefore++)
					if (statesBefore.get(stateBefore) * statesAfter.get(stateAfter) > EPS)
						updateLastAction(stateBefore, stateAfter, a, maxERNextState);
			} 
		}
	}

	private void updateLastAction(int sBefore, int sAfter, int a, float maxERNextState) {
		

		float actionValue = value.get(sBefore, a);
		// Weight by the activity of both states
		float newValue = actionValue
				+ alpha
				* statesBefore.get(sBefore)
				* statesAfter.get(sAfter)
				* (reward.get() + discountFactor * maxERNextState - actionValue);

//		System.out.println("Updating action " + a);
		value.set(sBefore, a, newValue);
		// System.out.println(sBefore);
		// if (actionValue != value.get(sBefore, a))
		// System.out.println(actionValue + " " + value.get(sBefore, a));
	}

	private float getMaxExpectedReward(NslDoutFloat2 value, int s) {
		float maxER = value.get(s, 0);
		for (int a = 1; a < value.getSize2(); a++) {
			if (value.get(s, a) > maxER)
				maxER = value.get(s, a);
		}

		return maxER;
	}

	/**
	 * Dumps the qlearning policy to a file. The assumption of the alignment
	 * between pcl cells and ql states is assumed for efficiency purposes.
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
			String rep, ArtificialPlaceCellLayer pcl, int layer) {
		synchronized (QLSupport.class) {
			PrintWriter writer = MultiStateProportionalQL.getWriter();

			List<ArtificialPlaceCell> cells = pcl.getCells();
			for (int activeState = 0; activeState < numStates; activeState++) {
				// Get the policy angle for this state
				int angle = getMaxAngle(activeState);

				// Write to file
				String policyAngle;
				if (angle == -1)
					policyAngle = "NA";
				else
					policyAngle = new Float(Utiles.getAngle(angle)).toString();

				writer.println(trial + '\t' + groupName + '\t' + subName + '\t'
						+ rep + '\t' + cells.get(activeState).getCenter().x
						+ "\t" + (-cells.get(activeState).getCenter().z) + "\t"
						+ policyAngle + "\t" + layer);
			}
		}

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
			String rep, ArtificialPlaceCellLayerWithIntention pcl, int layer,
			int numIntentions) {
		synchronized (QLSupport.class) {
			PrintWriter writer = MultiStateProportionalQL.getWriter();
			for (int intention = 0; intention < numIntentions; intention++) {

				List<ArtificialPlaceCell> cells = pcl.getCells(intention);
				for (int activeState = 0; activeState < cells.size(); activeState++) {
					// Get the policy angle for this state
					// Offset with the intention
					int angle = getMaxAngle(intention * cells.size()
							+ activeState);

					// Write to file
					String policyAngle;
					if (angle == -1)
						policyAngle = "NA";
					else
						policyAngle = new Float(Utiles.getAngle(angle))
								.toString();

					writer.println(trial + '\t' + groupName + '\t' + subName
							+ '\t' + rep + '\t'
							+ cells.get(activeState).getCenter().x + "\t"
							+ (-cells.get(activeState).getCenter().z) + "\t"
							+ policyAngle + "\t" + layer + '\t' + intention);
				}
			}
		}

	}

	private static PrintWriter getWriter() {
		if (writer == null) {
			try {
				writer = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(new File(Configuration
								.getString("Log.DIRECTORY") + DUMP_FILENAME))),
						true);
				writer.println("trial\tgroup\tsubject\trepetition\tx\ty\tangle\tlayer\tintention");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return writer;
	}

	public int getMaxAngle(int s) {
		float[] vals = new float[Utiles.numAngles];
		int maxAngle = -1;
		float maxVal = 0;
		for (int angle = 0; angle < Utiles.numAngles; angle++) {
			vals[angle] = value.get(s, angle);
			if (vals[angle] > maxVal) {
				maxVal = vals[angle];
				maxAngle = angle;
			}
		}
		return maxAngle;
	}
}