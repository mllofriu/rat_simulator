package edu.usf.ratsim.nsl.modules.qlearning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.usf.ratsim.nsl.modules.ArtificialPlaceCell;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;

public class QLSupport {

	private static final Float INITIAL_VALUE = 0f;
	private static final String DUMP_FILENAME = "policy.txt";

	private HashMap<StateAction, Float> value;

	private LinkedList<StateAction> visitedStateActions;
	private int numStates;
	private static PrintWriter writer = null;

	public QLSupport(int numStates) {
		value = new HashMap<StateAction, Float>(numStates
				* Utiles.discreteAngles.length);
		for (int s = 0; s < numStates; s++)
			for (int a = 0; a < Utiles.discreteAngles.length; a++)
				value.put(new StateAction(s, a), INITIAL_VALUE);

		this.numStates = numStates;

		visitedStateActions = new LinkedList<StateAction>();
	}

	public float getValue(StateAction sa) {
		return value.get(sa);
	}

	public void setValue(StateAction sa, float newVal) {
		value.put(sa, newVal);
	}

	public void recordStateAction(StateAction sa) {
		// Add to the front to reverse update later
		visitedStateActions.add(0, sa);
	}

	public StateAction getVisitedSA(int index) {
		return visitedStateActions.get(index);
	}

	/**
	 * Dumps the qlearning policy to a file. The assumption of the alignment
	 * between pcl cells and ql states is assumed for efficiency purposes.
	 * 
	 * @param rep
	 * @param subName
	 * @param trial
	 * 
	 * @param writer
	 * @param pcl
	 */
	public void dumpPolicy(String trial, String subName, String rep,
			ArtificialPlaceCellLayer pcl, int layer) {
		synchronized (QLSupport.class) {
			PrintWriter writer = QLSupport.getWriter();

			List<ArtificialPlaceCell> cells = pcl.getCells();
			for (int activeState = 0; activeState < numStates; activeState++) {
				// Get the policy angle for this state
				int angle = getMaxAngle(activeState);

				// Write to file
				String policyAngle;
				if (angle == -1)
					policyAngle = "NA";
				else
					policyAngle = new Float(Utiles.discreteAngles[angle])
							.toString();

					writer.println(trial + '\t' + subName + '\t' + rep + '\t'
							+ cells.get(activeState).getCenter().x + "\t"
							+ (-cells.get(activeState).getCenter().z) + "\t"
							+ policyAngle + "\t"
							+ layer);
			}
		}
		
	}

	private static PrintWriter getWriter() {
		if (writer == null) {
			try {
				writer = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(new File(Configuration
								.getString("Log.DIRECTORY") + DUMP_FILENAME))), true);
				writer.println("trial\tsubject\trepetition\tx\ty\tangle\tlayer");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return writer;
	}

	public int getMaxAngle(int s) {
		float[] vals = new float[Utiles.discreteAngles.length];
		int maxAngle = -1;
		float maxVal = 0;
		for (int angle = 0; angle < Utiles.discreteAngles.length; angle++) {
			vals[angle] = getValue(new StateAction(s, angle));
			if (vals[angle] > maxVal) {
				maxVal = vals[angle];
				maxAngle = angle;
			}
		}
		return maxAngle;
	}

	public int numVisitedSA() {
		return visitedStateActions.size();
	}

	public void clearRecord() {
		visitedStateActions.clear();
	}

	
}
