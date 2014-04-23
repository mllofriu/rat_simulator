package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

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

public class ProportionalExplorer extends NslModule {

	public float aprioriValueVariance;

	public NslDinFloat1[] votes;

	private IRobot robot;

	private Random r;

	private boolean explore;

	private ExperimentUniverse universe;

//	private float maxPossibleReward;

	public ProportionalExplorer(String nslName, NslModule nslParent,
			int numLayers,
			IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.robot = robot;
		this.universe = universe;

		votes = new NslDinFloat1[numLayers];
		for (int i = 0; i < numLayers; i++)
			votes[i] = new NslDinFloat1(this, "votes" + i);

		r = new Random();
	}

	public void simRun() {
		float[] overallValues = new float[Utiles.numAngles];
		for (int i = 0; i < overallValues.length; i++)
			overallValues[i] = 0;
		// Add each contribution
		for (NslDinFloat1 layerVal : votes)
			for (int angle = 0; angle < layerVal.getSize(); angle++)
				overallValues[angle] += layerVal.get(angle);
		// find total value with laplacian
		float maxVal = Float.MIN_VALUE;
		for (int angle = 0; angle < overallValues.length; angle++)
			if (maxVal < overallValues[angle])
				maxVal = overallValues[angle];

		// if (maxVal > 1)
		// System.out.println(maxVal);

//		explore = r.nextFloat() > (maxVal / maxPossibleReward);
		// if (explore)
		// System.out.println("Exploring");
//		 explore = maxVal == 0;
		// System.out.println(maxVal);
		// Make a list of actions and values
		List<ActionValue> actions = new LinkedList<ActionValue>();
		for (int angle = 0; angle < overallValues.length; angle++) {
			// Get angle to that maximal direction
			Quat4f nextRot = Utiles.angleToRot(Utiles.getAngle(angle));

			// Get the action that better approximates that angle
			int action = Utiles.bestActionToRot(nextRot,
					universe.getRobotOrientation());
			// Add a small bias towards going forward and small rotations
			// Radial function centered on the going forward angle
			float val = overallValues[angle];
			if (explore) {
				float increment = (float) (Math.max(maxVal, 1) * Math.exp(-Math
						.pow(Utiles.actionDistance(action,
								Utiles.discretizeAction(0)), 2)
						/ aprioriValueVariance));
				val += increment;
			}

			// float val = (float) (overallValues[angle] +
			// EXPLORATORY_COMPONENT);
			actions.add(new ActionValue(action, val));
		}
		// Collections.sort(actions);

		// Recompute max val
		maxVal = Float.MIN_VALUE;
		for (int a = 0; a < overallValues.length; a++)
			if (maxVal < overallValues[a])
				maxVal = overallValues[a];

			int action;
			// Roulette algorithm
			// Get total value
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
				if (actions.isEmpty())
					System.out.println("no actions");
				do {
					action++;
					nextRVal -= (actions.get(action).getValue() - minVal);
				} while (nextRVal >= 0 && action < actions.size() - 1);

			// Try the selected action
			robot.rotate(Utiles.getAction(actions.get(action).getAction()));
			boolean[] aff = robot.getAffordances();
			// Random if there was no affordable positive value action
			// lastActionRandom = actions.get(action).getValue() <=
			// EXPLORATORY_VARIANCE;
			actions.remove(action);
			// } while (!aff[Utiles.discretizeAction(0)]);

		// Now it is safe to forward
//		if (!aff[Utiles.discretizeAction(0)]) {
//			if (Math.random() > .5)
//				robot.rotate((float) (Math.PI / 2));
//			else {
//				robot.rotate((float) (-Math.PI / 2));
//			}
//			aff = robot.getAffordances();
//		}
		if (aff[Utiles.discretizeAction(0)])
			robot.forward();
	}

	public boolean wasLastActionRandom() {
		return explore;
	}
}


