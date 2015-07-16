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
import edu.usf.ratsim.micronsl.FloatArrayPort;
import edu.usf.ratsim.micronsl.IntArrayPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.support.GeomUtils;

public class TaxicFoodFinderSchema extends Module {

	public float[] votes;
	private float reward;

	private Subject subject;
	private LocalizableRobot robot;
	private double lambda;
	private boolean estimateValue;

	public TaxicFoodFinderSchema(String name, Subject subject,
			LocalizableRobot robot, float reward, float lambda,
			boolean estimateValue) {
		super(name);
		this.reward = reward;

		// Votes for action and value
		votes = new float[subject.getPossibleAffordances().size() + 1];
		addOutPort("votes", new FloatArrayPort(this, votes));

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
	public void simRun() {
		IntArrayPort goalFeeder = (IntArrayPort) getInPort("goalFeeder");

		for (int i = 0; i < votes.length; i++)
			votes[i] = 0;

		// Get the votes for each affordable action
		List<Affordance> affs = robot.checkAffordances(subject
				.getPossibleAffordances());
		int voteIndex = 0;
		boolean feederToEat = robot.isFeederClose()
				&& robot.getClosestFeeder().getId() != goalFeeder.get(0)
				&& robot.getClosestFeeder().getId() != goalFeeder.get(1);
		for (Affordance af : affs) {
			float value = 0;
			if (af.isRealizable()) {
				if (af instanceof TurnAffordance) {
					if (!feederToEat)
						for (Feeder f : robot.getVisibleFeeders(goalFeeder
								.getData())) {
							value += getFeederValue(GeomUtils.simulate(
									f.getPosition(), af));
						}
				} else if (af instanceof ForwardAffordance) {
					if (!feederToEat)
						for (Feeder f : robot.getVisibleFeeders(goalFeeder
								.getData())) {
							value += getFeederValue(GeomUtils.simulate(
									f.getPosition(), af));
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

		// Get the value of the current position
		if (estimateValue) {
			float value = 0;
			for (Feeder f : robot.getVisibleFeeders(goalFeeder.getData())) {
				// if (robot.isFeederClose()
				// && robot.getClosestFeeder().getId() == f.getId())
				// value += getFeederValue(f.getPosition());
			}
			// float value = 0;
			// if (feederToEat)
			// value = reward;
			// Last position represents the current value
			votes[subject.getPossibleAffordances().size()] = value;
		}
	}

	private float getFeederValue(Point3f feederPos) {
		float steps = GeomUtils.getStepsToFeeder(feederPos, subject);
		return (float) (reward * Math.pow(lambda, steps));
	}

}
