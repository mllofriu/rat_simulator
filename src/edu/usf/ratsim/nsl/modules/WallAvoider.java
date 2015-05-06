package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.Robot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;

/**
 * Sets the dopaminergic votes for both a flashing feeder and a non flashing
 * one.
 * 
 * @author ludo
 * 
 */
public class WallAvoider extends NslModule {

	private static final float EPS_VALUE = 1f;
	private static final float DIMINISH_FACTOR = .8f;
	private static final int WALL_LOOKAHEAD = 10;
	public NslDoutFloat1 votes;
	private float wallFollowingValue;
	private Robot robot;
	private float currentValue;
	private boolean active;
	private int direction;
	private Random r;
	// private int number;
	private Subject subject;

	public WallAvoider(String nslName, NslModule nslParent, Subject subject,
			float wallFollowingValue, int numActions) {
		super(nslName, nslParent);
		this.subject = subject;
		this.wallFollowingValue = wallFollowingValue;
		this.currentValue = 0f;
		active = false;

		// number = (new Random()).nextInt();

		votes = new NslDoutFloat1(this, "votes", numActions + 1);
		r = new Random();

		robot = subject.getRobot();
		this.subject = subject;
	}

	public void simRun() {
		// System.out.println("Performing wall avoider " + number);

		votes.set(0);

		List<Affordance> affs = robot.checkAffordances(subject.getPossibleAffordances());
		int index = 0;
		for (Affordance af : affs){
			if (!af.isRealizable())
				votes.set(index, wallFollowingValue);
			index++;
		}
	}
}
