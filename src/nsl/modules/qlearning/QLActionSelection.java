package nsl.modules.qlearning;

import java.util.Random;

import javax.vecmath.Quat4f;

import robot.IRobot;
import support.Utiles;
import experiment.ExperimentUniverse;
import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;

public class QLActionSelection extends NslModule {

	public NslDoutInt0 actionVote;
	public NslDinFloat1 states;
	private IRobot robot;
	private ExperimentUniverse universe;
	private Random random;
	private QLSupport value;

	public QLActionSelection(String nslName, NslModule nslParent,
			QLSupport value, int stateSize, IRobot robot,
			ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.universe = universe;
		this.robot = robot;
		this.value = value;

		actionVote = new NslDoutInt0(this, "vote");
		states = new NslDinFloat1(this, "states", stateSize);

		random = new Random();
	}

	public void simRun() {
		int s = getActiveState();

		saveStateAction(s);

		int nextAction = getNextStep(s);
		actionVote.set(nextAction);
	}

	private int getNextStep(int s) {
		// Store values in array
		float[] vals = new float[Utiles.discreteAngles.length];
		float maxVal = 0;
		for (int angle = 0; angle < Utiles.discreteAngles.length; angle++) {
			vals[angle] = value.getValue(new StateAction(s, angle));
			if (vals[angle] > maxVal) {
				maxVal = vals[angle];
			}
		}

		int action;
		// Explore with probability 1 - maxExpectedVal / maxPossibleVal
		// if (random.nextFloat() > maxVal / FOOD_REWARD ){
		if (maxVal == 0) {
			action = -1;
		} else {
			// Randomly assign an angle proportional to its expected reward
			// float r = random.nextFloat() * totalVal;
			// float acc = 0;
			// int nextAngle = -1;
			// do {
			// nextAngle++;
			// acc += vals[nextAngle];
			// } while (acc < r);
			// Exploit best angle
			int nextAngle = value.getMaxAngle(s);

			// Get angle to that maximal direction
			Quat4f nextRot = Utiles
					.angleToRot(Utiles.discreteAngles[nextAngle]);

			// Get the action that better approximates that angle
			boolean[] affordances = robot.affordances();
			action = Utiles.bestActionToRot(nextRot,
					universe.getRobotOrientation(), affordances);
		}

		return action;
	}

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

	private void saveStateAction(int s) {
		// Save the current state and the past action
		value.recordStateAction(new StateAction(s, Utiles
				.discretizeAngle(universe.getRobotOrientationAngle())));
	}
}
