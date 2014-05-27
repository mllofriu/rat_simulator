package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import java.util.Collections;
import java.util.LinkedList;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

public class NoExploration extends NslModule {

	public NslDinFloat1[] votes;
	public NslDoutInt0 takenAction;

	private IRobot robot;

	private ExperimentUniverse universe;

	public NoExploration(String nslName, NslModule nslParent, int numVotes,
			IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.robot = robot;
		this.universe = universe;

		votes = new NslDinFloat1[numVotes];
		for (int i = 0; i < numVotes; i++)
			votes[i] = new NslDinFloat1(this, "votes" + i, Utiles.numActions);

		takenAction = new NslDoutInt0(this, "takenAction");

	}

	public void simRun() {
		float[] overallValues = new float[Utiles.numActions];
		for (int i = 0; i < overallValues.length; i++)
			overallValues[i] = 0;
		// Add each contribution
		// System.out.println("Values");
		for (NslDinFloat1 layerVal : votes) {
			for (int angle = 0; angle < layerVal.getSize(); angle++) {
				overallValues[angle] += layerVal.get(angle);
//				System.out.print(layerVal.get(angle) + "\t\t");
			}
//			System.out.println();
		}
//		System.out.println();
		//
//		System.out.print("Values\t");
//		for (int angle = 0; angle < Utiles.numActions; angle++)
//			System.out.print(overallValues[angle] + "\t\t");
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
			actions.add(new ActionValue(action, overallValues[action]));
		}

		int action;
		boolean[] aff;

		// Select best action
		Collections.sort(actions);
		action = actions.size() - 1;

		// Rotate the robot the desired angle
		float angle = Utiles.getAction(actions.get(action).getAction());
//		if (angle != 0)
			robot.rotate(angle);
//		else {
			aff = robot.getAffordances();
			if (aff[Utiles.discretizeAction(0)])
				robot.forward();
//		}

		// Publish the taken action
		takenAction.set(actions.get(action).getAction());
		// System.out.println(takenAction.get());

		
	}
}
