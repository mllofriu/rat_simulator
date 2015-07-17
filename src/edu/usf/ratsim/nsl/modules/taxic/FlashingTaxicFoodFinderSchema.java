package edu.usf.ratsim.nsl.modules.taxic;

import java.util.List;

import javax.vecmath.Point3f;

import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.EatAffordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;
import edu.usf.experiment.subject.affordance.TurnAffordance;
import edu.usf.experiment.universe.Feeder;
import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.Float1dPort;
import edu.usf.ratsim.micronsl.IntPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.support.GeomUtils;

public class FlashingTaxicFoodFinderSchema extends Module {

	public float[] votes;
	private float reward;

	private Subject subject;
	private LocalizableRobot robot;
	private double lambda;
	private boolean estimateValue;

	public FlashingTaxicFoodFinderSchema(String name, Subject subject,
			LocalizableRobot robot, float reward, float lambda,
			boolean estimateValue) {
		super(name);
		this.reward = reward;

		// Votes for action and value
		votes = new float[subject.getPossibleAffordances().size() + 1];
		addOutPort("votes", new Float1dPortArray(this, votes));

		this.subject = subject;
		this.robot = robot;
		this.lambda = lambda;
		this.estimateValue = estimateValue;
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
		IntPort goalFeeder = (IntPort) getInPort("goalFeeder");

		for (int i = 0; i < votes.length; i++)
			votes[i] = 0;

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
						// value += getFeederValue(robot.getClosestFeeder()
						// .getPosition());
						value += reward;
					}
				} else
					throw new RuntimeException("Affordance "
							+ af.getClass().getName()
							+ " not supported by robot");
			}

			votes[voteIndex] = value;
			voteIndex++;
		}

		// Get the value of the current position
		if (estimateValue) {
			float value = 0;
			if (robot.seesFlashingFeeder())
				// value +=
				// getFeederValue(robot.getFlashingFeeder().getPosition());

				// Last position represents the current value
				votes[subject.getPossibleAffordances().size()] = value;
		}
	}

	private float getFeederValue(Point3f feederPos) {
		float steps = GeomUtils.getStepsToFeeder(feederPos, subject);
		return (float) (reward * Math.pow(lambda, steps));
	}
}
