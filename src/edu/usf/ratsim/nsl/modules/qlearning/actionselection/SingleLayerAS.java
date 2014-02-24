package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.nsl.modules.qlearning.StateActionReward;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;

public class SingleLayerAS extends NslModule {

	public NslDoutFloat1 actionVote;
	public NslDinFloat1 states;
	private IRobot robot;
	private ExperimentUniverse universe;
	private QLSupport value;

	public SingleLayerAS(String nslName, NslModule nslParent,
			QLSupport value, int stateSize, IRobot robot,
			ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.universe = universe;
		this.robot = robot;
		this.value = value;

		actionVote = new NslDoutFloat1(this, "vote",
				Utiles.discreteAngles.length);
		states = new NslDinFloat1(this, "states", stateSize);

	}

	public void simRun() {
		int s = getActiveState();

//		saveStateAction(s);

		// int nextAction = getNextStep(s);
		// actionVote.set(nextAction);
		setVotes(s);
	}

	private void setVotes(int state) {
		double[] values = new double[Utiles.discreteAngles.length];
		for (int angle = 0; angle < Utiles.discreteAngles.length; angle++)
			values[angle] = value.getValue(new StateActionReward(state, angle));
		actionVote.set(values);
	}

	// private int getNextStep(int s) {
	// // Store values in array
	// float[] vals = new float[Utiles.discreteAngles.length];
	// float maxVal = 0;
	// for (int angle = 0; angle < Utiles.discreteAngles.length; angle++) {
	// vals[angle] = value.getValue(new StateAction(s, angle));
	// if (vals[angle] > maxVal) {
	// maxVal = vals[angle];
	// }
	// }
	//
	// int action;
	// // Explore with probability 1 - maxExpectedVal / maxPossibleVal
	// // if (random.nextFloat() > maxVal / FOOD_REWARD ){
	// if (maxVal <= 0) {
	// action = -1;
	// } else {
	// // Exploit best angle
	// int nextAngle = value.getMaxAngle(s);
	//
	// // Get angle to that maximal direction
	// Quat4f nextRot = Utiles
	// .angleToRot(Utiles.discreteAngles[nextAngle]);
	//
	// // Get the action that better approximates that angle
	// // boolean[] affordances = robot.getAffordances();
	// action = Utiles.bestActionToRot(nextRot,
	// universe.getRobotOrientation());
	// }
	//
	// return action;
	// }

	private int getActiveState() {
		// Winner take all within the layer
		float maxVal = 0;
		int activeState = -1;
		for (int i = 0; i < states.getSize(); i++)
			if (states.get(i) > maxVal) {
				activeState = i;
				maxVal = states.get(i);
			}

		return activeState;
	}

}
