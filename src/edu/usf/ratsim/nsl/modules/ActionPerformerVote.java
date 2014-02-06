package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Quat4f;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

public class ActionPerformerVote extends NslModule {
	
	public final float EXPLORATORY_COMPONENT = .01f; 

	public NslDinFloat1[] votes;

	private IRobot robot;

	private Random r;

	private boolean lastActionRandom;

	private ExperimentUniverse universe;

	public ActionPerformerVote(String nslName, NslModule nslParent,
			int numLayers, IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.robot = robot;
		this.universe = universe;

		votes = new NslDinFloat1[numLayers];
		for (int i = 0; i < numLayers; i++)
			votes[i] = new NslDinFloat1(this, "votes" + i);

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
		// Make a sorted list of actions and values
		List<ActionValue> actions = new LinkedList<ActionValue>();
		for (int angle = 0; angle < overallValues.length; angle++) {
			// Get angle to that maximal direction
			Quat4f nextRot = Utiles.angleToRot(Utiles.discreteAngles[angle]);

			// Get the action that better approximates that angle
			int action = Utiles.bestActionToRot(nextRot,
					universe.getRobotOrientation());
			// Add a small bias towards going forward and small rotations
			// Radial function centered on the going forward angle
			float val = (float) (overallValues[angle]
					+ EXPLORATORY_COMPONENT * Math.exp(-Math.pow(action - Utiles.discretizeAction(0), 2) / 1));
			actions.add(new ActionValue(action, val));
		}
//		Collections.sort(actions);
		
		

		boolean[] aff;
		do {
			// Roulette algorithm
			// Get total value
			float totalVal = 0;
			for (ActionValue aValue : actions)
				totalVal += aValue.getValue();
			// Calc a new random in [0, totalVal]
			float nextRVal = r.nextFloat() * totalVal;
			// Find the next action
			int action = -1;
			do {
				action++;
				nextRVal -= actions.get(action).getValue();
			} while (nextRVal >= 0 && action < actions.size() - 1 );

			// Try the selected action
			robot.rotate(Utiles.actions[actions.get(action).getAction()]);
			aff = robot.getAffordances();
			// Random if there was no affordable positive value action
			lastActionRandom = actions.get(action).getValue() <= EXPLORATORY_COMPONENT;
			actions.remove(action);
		} while (!aff[Utiles.discretizeAction(0)]);

		// Now it is safe to forward
		robot.forward();
	}

	public boolean wasLastActionRandom() {
		return lastActionRandom;
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
