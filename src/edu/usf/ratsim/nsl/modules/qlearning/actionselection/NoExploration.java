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

	public NslDinFloat1 votes;
	public NslDoutInt0 takenAction;

	private IRobot robot;

	private ExperimentUniverse universe;

	public NoExploration(String nslName, NslModule nslParent, int numVotes,
			IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.robot = robot;
		this.universe = universe;

		votes = new NslDinFloat1(this, "votes", Utiles.numActions);

		takenAction = new NslDoutInt0(this, "takenAction");

	}

	public void simRun() {
//		float[] overallValues = new float[Utiles.numActions];
//		for (int i = 0; i < overallValues.length; i++)
//			overallValues[i] = 0;
		// Add each contribution
//		System.out.println("Values");
//		for (NslDinFloat1 layerVal : votes) {
//			for (int angle = 0; angle < layerVal.getSize(); angle++) {
//				overallValues[angle] += layerVal.get(angle);
//				System.out.print(layerVal.get(angle) + "\t\t");
//			}
//			System.out.println();
//		}
//		System.out.println();
		//
		// System.out.print("Values\t");
		// for (int angle = 0; angle < Utiles.numActions; angle++)
		// System.out.print(overallValues[angle] + "\t\t");
		// System.out.println();
		//
		float maxVal = Float.MIN_VALUE;
		for (int angle = 0; angle < votes.getSize(); angle++)
			if (maxVal < votes.get(angle))
				maxVal = votes.get(angle);

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
			actions.add(new ActionValue(action, votes.get(action)));
		}

		int action;
		boolean[] aff;

		// Select best action
		Collections.sort(actions);
		action = actions.size() - 1;

		if (actions.get(action).getAction() == Utiles.eatAction
				&& actions.get(action).getValue() < 0)
			action = action - 1;

		// Rotate the robot the desired angle
		if (actions.get(action).getAction() == Utiles.eatAction) {
			System.out.println("Trying to eat");
			robot.eat();
		} else {
			float angle = Utiles
					.getActionAngle(actions.get(action).getAction());
			aff = robot.getAffordances();
			// If going forward and no affordance - rotate
			if (angle == 0 && !aff[Utiles.discretizeAction(0)])
				angle = Utiles.getActionAngle(0);

			do {
				robot.rotate(angle);
				aff = robot.getAffordances();
			} while (!aff[Utiles.discretizeAction(0)]);

			// else {
			robot.forward();
			// }
		}

		// Publish the taken action
		takenAction.set(actions.get(action).getAction());
		// System.out.println(takenAction.get());

	}
}
