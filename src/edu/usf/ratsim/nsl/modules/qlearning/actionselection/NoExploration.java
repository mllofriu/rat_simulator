package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Debug;
import edu.usf.ratsim.support.Utiles;

public class NoExploration extends NslModule {

	private static final float FORWARD_EPS = 0.00001f;
	private static final float ANGLE_EPS = 1e-6f;
	public NslDinFloat1 votes;
	public NslDoutInt0 takenAction;

	private IRobot robot;

	private ExperimentUniverse universe;
	private Random random;
	private boolean lastRot;

	public NoExploration(String nslName, NslModule nslParent, int numVotes,
			IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.robot = robot;
		this.universe = universe;

		votes = new NslDinFloat1(this, "votes", Utiles.numActions);

		takenAction = new NslDoutInt0(this, "takenAction");

		random = new Random();

		lastRot = false;
	}

	public void simRun() {
		int selectedAction;

		if (lastRot) {
			selectedAction = Utiles.discretizeAction(0);
			if (Debug.moveRobot)
				robot.forward();
		} else {

			float maxVal = Float.MIN_VALUE;
			for (int angle = 0; angle < votes.getSize(); angle++)
				if (maxVal < votes.get(angle))
					maxVal = votes.get(angle);

			LinkedList<ActionValue> actions = new LinkedList<ActionValue>();
			// Assign values to actions as a function of angles instead of
			// viceversa
			for (int action = 0; action < Utiles.numActions; action++) {
				// Add a small eps to forward action to prefer it in case of a
				// tie
				if (action == Utiles.forwardAction)
					actions.add(new ActionValue(action, votes.get(action)
							+ FORWARD_EPS));
				else
					actions.add(new ActionValue(action, votes.get(action)));
			}

			int action;
			boolean[] aff = robot.getAffordances();

			int count = 0;
			for (int i = 0; i < aff.length; i++)
				if (aff[i])
					count++;

			// Select best action

			Collections.sort(actions);
			action = actions.size() - 1;

			// if (actions.get(action).getAction() == Utiles.eatAction
			// && actions.get(action).getValue() < 0)
			// action = action - 1;
			if (actions.get(action).getAction() == Utiles.eatAction
					&& !universe.isRobotCloseToAFeeder())
				action = action - 1;

			// Rotate the robot the desired angle
			if (actions.get(action).getAction() == Utiles.eatAction) {
				if (robot.hasFoundFood()) {
					if (Debug.printTryingToEat)
						System.out.println("Trying to eat");
					robot.eat();
				}

				selectedAction = actions.get(action).getAction();
				// } else if (actions.get(action).getAction() ==
				// Utiles.waitAction)
				// {
				// // do nothing
				// if (Debug.printTryingToEat) System.out.println("");
			} else {
				if (Debug.printTryingToEat)
					System.out.println("");

				while (action >= 0 && !aff[actions.get(action).getAction()])
					action--;

				if (action < 0)
					if (random.nextFloat() > 0.5)
						selectedAction = Utiles.discretizeAction(90);
					else
						selectedAction = Utiles.discretizeAction(-90);
				else
					selectedAction = actions.get(action).getAction();
				float angle = Utiles.getActionAngle(selectedAction);
				// If going forward and no affordance - rotate
				// if (Math.abs(angle) < ANGLE_EPS
				// && !aff[Utiles.discretizeAction(0)]) {
				// // Depends on the fact that there are only two rotatin
				// // actions
				// // if (random.nextFloat() > 0.5)
				// // angle = Utiles.getActionAngle(0);
				// // else
				// // angle = Utiles.getActionAngle(2);
				// // System.out.println("Angle 0 and no front affordance");
				// if (aff[Utiles.discretizeAction(90)]
				// && !aff[Utiles.discretizeAction(-90)])
				// angle = Utiles.getActionAngle(Utiles
				// .discretizeAction(90));
				// else if (aff[Utiles.discretizeAction(-90)]
				// && !aff[Utiles.discretizeAction(90)])
				// angle = Utiles.getActionAngle(Utiles
				// .discretizeAction(-90));
				// else {
				// if (random.nextFloat() > 0.5)
				// angle = Utiles.getActionAngle(Utiles
				// .discretizeAction(90));
				// else
				// angle = Utiles.getActionAngle(Utiles
				// .discretizeAction(-90));
				// }
				// // System.out.println(angle);
				// // Skip to the next action
				// // action = action - 1;
				// // angle = Utiles
				// // .getActionAngle(actions.get(action).getAction());
				// }

				if (angle != 0)
					do {
						if (Debug.moveRobot)
							robot.rotate(angle);
						aff = robot.getAffordances();
					} while (!aff[Utiles.discretizeAction(0)]);

				else {
					if (Debug.moveRobot)
						robot.forward();
				 }
			}

		}
		// Publish the taken action
		takenAction.set(selectedAction);
		// System.out.println(takenAction.get());
		lastRot = selectedAction == Utiles.discretizeAction(90) || selectedAction == Utiles.discretizeAction(-90);
	}
}
