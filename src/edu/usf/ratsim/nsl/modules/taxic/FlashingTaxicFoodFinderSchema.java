package edu.usf.ratsim.nsl.modules.taxic;

import java.util.List;

import javax.vecmath.Point3f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.EatAffordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;
import edu.usf.experiment.subject.affordance.TurnAffordance;
import edu.usf.experiment.universe.Feeder;
import edu.usf.ratsim.support.GeomUtils;

public class FlashingTaxicFoodFinderSchema extends NslModule {

	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 votes;
	private float reward;

	private Subject subject;
	private LocalizableRobot robot;
	private double lambda;

	public FlashingTaxicFoodFinderSchema(String nslName, NslModule nslParent,
			Subject subject, LocalizableRobot robot, float reward, float lambda) {
		super(nslName, nslParent);
		this.reward = reward;

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		// Votes for action and value
		votes = new NslDoutFloat1(this, "votes", subject
				.getPossibleAffordances().size() + 1);

		this.subject = subject;
		this.robot = robot;
		this.lambda = lambda;
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
	public void simRun() {
		votes.set(0);

		// Get the votes for each affordable action
		List<Affordance> affs = robot.checkAffordances(subject
				.getPossibleAffordances());
		int voteIndex = 0;
		for (Affordance af : affs) {
			float value = 0;
			if (af.isRealizable()) {
				if (af instanceof TurnAffordance) {
					if (robot.seesFlashingFeeder()) {
						Feeder f = robot.getFlashingFeeder();
						value += getFeederValue(GeomUtils.simulate(
								f.getPosition(), af));
					}
				} else if (af instanceof ForwardAffordance) {
					if (robot.seesFlashingFeeder()) {
						Feeder f = robot.getFlashingFeeder();
						value += getFeederValue(GeomUtils.simulate(
								f.getPosition(), af));
					}
				} else if (af instanceof EatAffordance) {
					if (robot.isFeederClose()
							&& robot.seesFlashingFeeder()
							&& robot.getFlashingFeeder().getId() == robot
									.getClosestFeeder().getId()
							&& robot.getFlashingFeeder().getId() != goalFeeder
									.get()) {
//						value += getFeederValue(robot.getClosestFeeder()
//								.getPosition());
						value += reward;
					}
				} else
					throw new RuntimeException("Affordance "
							+ af.getClass().getName()
							+ " not supported by robot");
			}

			votes.set(voteIndex, value);
			voteIndex++;
		}

		// Get the value of the current position
		float value = 0;
		if (robot.seesFlashingFeeder())
			value += getFeederValue(robot.getFlashingFeeder().getPosition());

		// Last position represents the current value
		votes.set(subject.getPossibleAffordances().size(), value);
	}

	private float getFeederValue(Point3f feederPos) {
		float steps = GeomUtils.getStepsToFeeder(feederPos, subject);
		return (float) (reward * Math.pow(lambda, steps));
	}
}
