package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.usf.experiment.robot.Robot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.utils.Debug;
import edu.usf.ratsim.micronsl.FloatPort;
import edu.usf.ratsim.micronsl.IntArrayPort;
import edu.usf.ratsim.micronsl.Module;

public class NoExploration extends Module {

	private static final float FORWARD_EPS = 0.00001f;
	private static final float ANGLE_EPS = 1e-6f;

	private Robot robot;

	private Random random;
	private boolean lastRot;
	private Subject sub;
	private FloatPort votes;
	private IntArrayPort takenAction;

	public NoExploration(IntArrayPort takenAction, FloatPort votes, Subject sub) {

		this.votes = votes;
		this.takenAction = takenAction;

		random = new Random();

		lastRot = false;

		robot = sub.getRobot();

		this.sub = sub;
	}

	public void simRun() {
		Affordance selectedAction;
		List<Affordance> aff = robot.checkAffordances(sub
				.getPossibleAffordances());

		for (int action = 0; action < aff.size(); action++)
			aff.get(action).setValue(votes.get(action));

		// Select best action
		List<Affordance> sortedAff = new LinkedList<Affordance>(aff);
		Collections.sort(sortedAff);
		selectedAction = sortedAff.get(aff.size() - 1);

		// Publish the taken action
		if (selectedAction.getValue() > 0) {
			takenAction.set(0,aff.indexOf(selectedAction));
			if (Debug.printSelectedValues)
				System.out.println(selectedAction.toString());

			robot.executeAffordance(selectedAction, sub);
		} else {
			takenAction.set(0,-1);
		}

		// TODO: get the rotation -> forward back
		// // System.out.println(takenAction.get());
		// lastRot = selectedAction == sub.getActionLeft()
		// || selectedAction == sub.getActionRight();
	}
}
