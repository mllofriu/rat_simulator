package edu.usf.ratsim.nsl.modules.taxic;

import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.EatAffordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;
import edu.usf.experiment.subject.affordance.TurnAffordance;
import edu.usf.experiment.universe.Feeder;
import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.Int1dPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.support.GeomUtils;

public class TaxicFoodFinderSchema extends Module {

	public float[] votes;
	private float reward;

	private Subject subject;
	private LocalizableRobot robot;
	private float negReward;
	private boolean rememberLastTwo;

	public TaxicFoodFinderSchema(String name, Subject subject,
			LocalizableRobot robot, float reward, float negReward,
			float lambda, boolean estimateValue, boolean rememberLastTwo) {
		super(name);
		this.reward = reward;
		this.negReward = negReward;
		this.rememberLastTwo = rememberLastTwo;

		// Votes for action and value
		votes = new float[subject.getPossibleAffordances().size()];
		addOutPort("votes", new Float1dPortArray(this, votes));

		this.subject = subject;
		this.robot = robot;
	}

	/**
	 * Assigns the value of executing each action as the value of the next step.
	 * The value is estimated using an exponential discount of the remaining
	 * steps, emulated an already learned value function with a lambda parameter
	 * < 1. The getFeederValue does this. When feeders are lost they cease to
	 * provide value, thus dopamine (delta in rl) falls. Sames happens when
	 * trying to eat and no food is found (due to goalFeeder turning to that
	 * goal).
	 */
	public void run() {
		Int1dPort goalFeeder = (Int1dPort) getInPort("goalFeeder");

		for (int i = 0; i < votes.length; i++)
			votes[i] = 0;

		// Get the votes for each affordable action
		List<Affordance> affs = robot.checkAffordances(subject
				.getPossibleAffordances());
		int voteIndex = 0;
		int closestFeeder;
		if (robot.getClosestFeeder() != null)
			closestFeeder = robot.getClosestFeeder().getId();
		else
			closestFeeder = -1;
		boolean feederToEat;
		if (rememberLastTwo)
			feederToEat = robot.isFeederClose()
					&& closestFeeder != goalFeeder.get(0)
					&& closestFeeder != goalFeeder.get(1);
		else 
			feederToEat = robot.isFeederClose();
		
		for (Affordance af : affs) {
			float value = 0;
			if (af.isRealizable()) {
				if (af instanceof TurnAffordance
						|| af instanceof ForwardAffordance) {
					if (!feederToEat)
						for (Feeder f : robot.getVisibleFeeders(goalFeeder
								.getData())) {
							if (!rememberLastTwo || (f.getId() != goalFeeder.get(0)
									&& f.getId() != goalFeeder.get(1))) {
								Point3f newPos = GeomUtils.simulate(
										f.getPosition(), af);
								Quat4f rotToNewPos = GeomUtils
										.angleToPoint(newPos);

								float angleDiff = Math.abs(GeomUtils
										.rotToAngle(rotToNewPos));
								if (angleDiff < robot.getHalfFieldView())
									value += getFeederValue(newPos);
								else
									value += -getFeederValue(f.getPosition());
							}
						}
				} else if (af instanceof EatAffordance) {
					if (feederToEat) {
						value += getFeederValue(robot.getClosestFeeder()
								.getPosition());
						// value += reward;
					}
				} else
					throw new RuntimeException("Affordance "
							+ af.getClass().getName()
							+ " not supported by robot");
			}

			votes[voteIndex] = value;
			voteIndex++;
		}

	}

	private float getFeederValue(Point3f feederPos) {
		float steps = GeomUtils.getStepsToFeeder(feederPos, subject);
		return (float) Math.max(0, (reward + negReward * steps)); // *
																	// Math.pow(lambda,
																	// ));
	}

	@Override
	public boolean usesRandom() {
		return false;
	}

}
