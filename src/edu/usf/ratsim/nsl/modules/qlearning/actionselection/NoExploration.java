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
	public NslDinFloat1 votes;
	public NslDoutInt0 takenAction;

	private IRobot robot;

	private ExperimentUniverse universe;
	private Random random;

	public NoExploration(String nslName, NslModule nslParent, int numVotes,
			IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.robot = robot;
		this.universe = universe;

		votes = new NslDinFloat1(this, "votes", Utiles.numActions);

		takenAction = new NslDoutInt0(this, "takenAction");

		random = new Random();
	}

	public void simRun() {
		float maxVal = Float.MIN_VALUE;
		for (int angle = 0; angle < votes.getSize(); angle++)
			if (maxVal < votes.get(angle))
				maxVal = votes.get(angle);

		LinkedList<ActionValue> actions = new LinkedList<ActionValue>();
		// Assign values to actions as a function of angles instead of viceversa
		for (int action = 0; action < Utiles.numActions; action++) {
			// Add a small eps to forward action to prefer it in case of a tie
			if (action == Utiles.forwardAction)
				actions.add(new ActionValue(action, votes.get(action)
						+ FORWARD_EPS));
			else
				actions.add(new ActionValue(action, votes.get(action)));
		}

		int action;
		boolean[] aff;

		// Select best action
		Collections.sort(actions);
		action = actions.size() - 1;

		// if (actions.get(action).getAction() == Utiles.eatAction
		// && actions.get(action).getValue() < 0)
		// action = action - 1;
		// if (actions.get(action).getAction() == Utiles.eatAction
		// && !universe.isRobotCloseToAFeeder())
		// action = action - 1;

		// Rotate the robot the desired angle
		if (actions.get(action).getAction() == Utiles.eatAction) {
			if (robot.hasFoundFood()) {
				if (Debug.printTryingToEat)
					System.out.println("Trying to eat");
				robot.eat();
			}
			// } else if (actions.get(action).getAction() == Utiles.waitAction)
			// {
			// // do nothing
			// if (Debug.printTryingToEat) System.out.println("");
		} else {
			if (Debug.printTryingToEat)
				System.out.println("");
			float angle = Utiles
					.getActionAngle(actions.get(action).getAction());
			aff = robot.getAffordances();
			// If going forward and no affordance - rotate
			if (angle == 0 && !aff[Utiles.discretizeAction(0)]) {
				// Depends on the fact that there are only two rotatin actions
				if (random.nextFloat() > 0.5)
					angle = Utiles.getActionAngle(0);
				else
					angle = Utiles.getActionAngle(2);
				// Skip to the next action
				// action = action - 1;
				// angle = Utiles
				// .getActionAngle(actions.get(action).getAction());
			}

			do {
				if (Debug.moveRobot)
					robot.rotate(angle);
				aff = robot.getAffordances();
			} while (!aff[Utiles.discretizeAction(0)]);

			// else {
			if (Debug.moveRobot)
				robot.forward();
			// }
		}

		// Publish the taken action
		takenAction.set(actions.get(action).getAction());
		// System.out.println(takenAction.get());

	}
}
