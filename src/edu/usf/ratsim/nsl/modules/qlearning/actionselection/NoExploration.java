package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import java.util.Collections;
import java.util.LinkedList;

import javax.vecmath.Quat4f;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslModule;
import sun.misc.Queue;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

public class NoExploration extends NslModule {

	public NslDinFloat1[] votes;

	private IRobot robot;

	private ExperimentUniverse universe;

	public NoExploration(String nslName, NslModule nslParent, int numVotes,
			IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.robot = robot;
		this.universe = universe;

		votes = new NslDinFloat1[numVotes];
		for (int i = 0; i < numVotes; i++)
			votes[i] = new NslDinFloat1(this, "votes" + i);

	}

	public void simRun() {
		float[] overallValues = new float[Utiles.numAngles];
		for (int i = 0; i < overallValues.length; i++)
			overallValues[i] = 0;
		// Add each contribution
		// System.out.println("Values");
		for (NslDinFloat1 layerVal : votes) {
			for (int angle = 0; angle < layerVal.getSize(); angle++) {
				// System.out.print(layerVal.get(angle) + " ");
				overallValues[angle] += layerVal.get(angle);
			}
			// System.out.println();
		}

//		for (int angle = 0; angle < Utiles.numAngles; angle++)
//			System.out.print(overallValues[angle] + "\t");
//		System.out.println();
		//
		float maxVal = Float.MIN_VALUE;
		for (int angle = 0; angle < overallValues.length; angle++)
			if (maxVal < overallValues[angle])
				maxVal = overallValues[angle];

		LinkedList<ActionValue> actions = new LinkedList<ActionValue>();
		// for (int angle = 0; angle < overallValues.length; angle++) {
		// // Get angle to that maximal direction
		// Quat4f nextRot = Utiles.angleToRot(Utiles.getAngle(angle));
		//
		// // Get the action that better approximates that angle
		// int action = Utiles.bestActionToRot(nextRot,
		// universe.getRobotOrientation());
		// // Add a small bias towards going forward and small rotations
		// // Radial function centered on the going forward angle
		// float val = overallValues[angle];
		//
		// actions.add(new ActionValue(action, val));
		// }
		// Assign values to actions as a function of angles instead of viceversa
		for (int action = 0; action < Utiles.numActions; action++) {
			Quat4f robOri = universe.getRobotOrientation();
			Quat4f turn = Utiles.angleToRot(Utiles.getAction(action));
			robOri.mul(turn);
			float val = overallValues[Utiles.discretizeAngle(robOri)];
			actions.add(new ActionValue(action, val));
		}

		int action;
		boolean[] aff;

		// Select best action
		Collections.sort(actions);
		action = actions.size() - 1;

		robot.rotate(Utiles.getAction(actions.get(action).getAction()));
		
		aff = robot.getAffordances();
		if (aff[Utiles.discretizeAction(0)])
			robot.forward();
	}
}
