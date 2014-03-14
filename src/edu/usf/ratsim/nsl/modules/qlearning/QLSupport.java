package edu.usf.ratsim.nsl.modules.qlearning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslDoutFloat2;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCell;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;

public class QLSupport extends NslModule{

	private static final Float INITIAL_VALUE = 0f;
	private static final String DUMP_FILENAME = "policy.txt";

	public NslDoutFloat2 value;

	private LinkedList<StateActionReward> visitedStateActions;
	private int numStates;
	private static PrintWriter writer = null;

	public QLSupport(String nslName, NslModule nslParent, int numStates, int numActions) {
		super(nslName, nslParent);
		value = new NslDoutFloat2(this, "value", numStates, numActions);
		
		for (int s = 0; s < numStates; s++)
			for (int a = 0; a < numActions; a++)
				value.set(s,a,INITIAL_VALUE);

		this.numStates = numStates;

		visitedStateActions = new LinkedList<StateActionReward>();
	}

	public float getValue(StateActionReward sa) {
		return value.get(sa.getState(), sa.getAction());
	}

	public void setValue(StateActionReward sa, float newVal) {
		value.set(sa.getState(), sa.getAction(), newVal);
	}

	public void recordStateAction(StateActionReward sa) {
		// Add to the front to reverse update later
		visitedStateActions.add(0, sa);
	}

	public StateActionReward getVisitedSA(int index) {
		return visitedStateActions.get(index);
	}

	
	public int getMaxAngle(int s) {
		float[] vals = new float[Utiles.discreteAngles.length];
		int maxAngle = -1;
		float maxVal = 0;
		for (int angle = 0; angle < Utiles.discreteAngles.length; angle++) {
			vals[angle] = getValue(new StateActionReward(s, angle));
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

	public void popLastRecord() {
		visitedStateActions.pollLast();
	}

}
