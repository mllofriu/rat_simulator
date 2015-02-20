package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.Robot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.ratsim.support.Debug;
import edu.usf.ratsim.support.GeomUtils;

public class NoExploration extends NslModule {

	private static final float FORWARD_EPS = 0.00001f;
	private static final float ANGLE_EPS = 1e-6f;
	public NslDinFloat1 votes;
	public NslDoutInt0 takenAction;

	private Robot robot;

	private Random random;
	private boolean lastRot;
	private Subject sub;

	public NoExploration(String nslName, NslModule nslParent, Subject sub,
			int numVotes) {
		super(nslName, nslParent);

		votes = new NslDinFloat1(this, "votes", numVotes);

		takenAction = new NslDoutInt0(this, "takenAction");

		random = new Random();

		lastRot = false;

		robot = sub.getRobot();

		this.sub = sub;
	}

	public void simRun() {
		Affordance selectedAction;
		List<Affordance> aff = robot.checkAffordances(sub
				.getPossibleAffordances());
		
		for (int action = 0; action < votes.getSize(); action++)
			aff.get(action).setValue(votes.get(action));

		
		// Select best action
		List<Affordance> sortedAff = new LinkedList<Affordance>(aff);
		Collections.sort(sortedAff);
		selectedAction = sortedAff.get(aff.size()-1);

		// Publish the taken action
		takenAction.set(aff.indexOf(selectedAction));
		robot.executeAffordance(selectedAction);
		// TODO: get the rotation -> forward back
//		// System.out.println(takenAction.get());
//		lastRot = selectedAction == sub.getActionLeft()
//				|| selectedAction == sub.getActionRight();
	}
}
