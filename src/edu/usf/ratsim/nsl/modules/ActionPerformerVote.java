package edu.usf.ratsim.nsl.modules;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Quat4f;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Utiles;

public class ActionPerformerVote extends NslModule {

	private static final float LAPLACIAN = 0.00001f;

	private static final float INITIAL_MAXVAL = 0;

	public final float EXPLORATORY_VARIANCE = Configuration
			.getFloat("ActionPerformer.ExploratoryVariance");

	public NslDinFloat1[] votes;

	private IRobot robot;

	private Random r;

	private boolean explore;

	private ExperimentUniverse universe;

	private float maxPossibleVal;

	private float explorationMaxValMultiplier = Configuration
			.getFloat("ActionPerformer.maxValMultiplier");

	public ActionPerformerVote(String nslName, NslModule nslParent,
			int numLayers, IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.robot = robot;
		this.universe = universe;

		votes = new NslDinFloat1[numLayers];
		for (int i = 0; i < numLayers; i++)
			votes[i] = new NslDinFloat1(this, "votes" + i);

		maxPossibleVal = Configuration.getFloat("QLearning.foodReward") / 2
				* numLayers;

		r = new Random();
	}

	public void simRun() {
		float[] overallValues = new float[Utiles.discreteAngles.length];
		for (int i = 0; i < overallValues.length; i++)
			overallValues[i] = 0;
		// Add each contribution
		for (NslDinFloat1 layerVal : votes)
			for (int angle = 0; angle < layerVal.getSize(); angle++)
				overallValues[angle] += layerVal.get(angle);
		// find total value with laplacian
		float maxVal = INITIAL_MAXVAL;
		for (int angle = 0; angle < overallValues.length; angle++)
			if (maxVal < overallValues[angle])
				maxVal = overallValues[angle];

		explore = r.nextFloat() > (maxVal / maxPossibleVal);

		// System.out.println(maxVal);
		// Make a list of actions and values
		List<ActionValue> actions = new LinkedList<ActionValue>();
		for (int angle = 0; angle < overallValues.length; angle++) {
			// Get angle to that maximal direction
			Quat4f nextRot = Utiles.angleToRot(Utiles.discreteAngles[angle]);

			// Get the action that better approximates that angle
			int action = Utiles.bestActionToRot(nextRot,
					universe.getRobotOrientation());
			// Add a small bias towards going forward and small rotations
			// Radial function centered on the going forward angle
			float val = overallValues[angle];
			if (explore)
				val += Math.max(explorationMaxValMultiplier * maxVal, 1)
						* Math.exp(-Math.pow(
								Utiles.actionDistance(action,
										Utiles.discretizeAction(0)), 2)
								/ EXPLORATORY_VARIANCE);

			// float val = (float) (overallValues[angle] +
			// EXPLORATORY_COMPONENT);
			actions.add(new ActionValue(action, val));
		}
		// Collections.sort(actions);

		boolean[] aff;
		do {
			int action;
			// Roulette algorithm
			// Get total value
			if (explore) {
				// Find min val
				float minVal = 0;
				for (ActionValue aValue : actions)
					if (aValue.getValue() < minVal)
						minVal = aValue.getValue();
				// Get max value
				float totalVal = 0;
				for (ActionValue aValue : actions)
					// Substract min val to raise everything above 0
					totalVal += aValue.getValue() - minVal;
				// Calc a new random in [0, totalVal]
				float nextRVal = r.nextFloat() * totalVal;
				// Find the next action
				action = -1;
				do {
					action++;
					nextRVal -= (actions.get(action).getValue() - minVal);
				} while (nextRVal >= 0 && action < actions.size() - 1);
			} else {
				// Select best action
				Collections.sort(actions);
				action = actions.size() - 1;
			}

			// Try the selected action
			robot.rotate(Utiles.actions[actions.get(action).getAction()]);
			aff = robot.getAffordances();
			// Random if there was no affordable positive value action
			// lastActionRandom = actions.get(action).getValue() <=
			// EXPLORATORY_VARIANCE;
			actions.remove(action);
		} while (!aff[Utiles.discretizeAction(0)]);

		// Now it is safe to forward
		robot.forward();
	}

	public boolean wasLastActionRandom() {
		return explore;
	}
}

class Votes implements Comparable<Votes> {
	private int action;
	private int votes;

	public Votes(int action, int votes) {
		this.action = action;
		this.votes = votes;
	}

	public int getAction() {
		return action;
	}

	public int getVotes() {
		return votes;
	}

	public void incrementVotes() {
		votes++;
	}

	public int compareTo(Votes o) {
		if (votes < o.votes)
			return -1;
		else if (votes == o.votes)
			return 0;
		else
			return 1;
	}

	public String toString() {
		return action + " voted " + votes + " times";
	}

}

final class ActionValue implements Comparable<ActionValue> {

	private int action;
	private float value;

	public ActionValue(int action, float value) {
		super();
		this.action = action;
		this.value = value;
	}

	public int getAction() {
		return action;
	}

	public float getValue() {
		return value;
	}

	@Override
	public int compareTo(ActionValue o) {
		if (value < o.value)
			return -1;
		else if (value == o.value)
			return 0;
		else
			return 1;
	}

}
