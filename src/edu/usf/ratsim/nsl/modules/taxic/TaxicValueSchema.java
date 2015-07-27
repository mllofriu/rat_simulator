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
import edu.usf.ratsim.micronsl.Int1dPort;
import edu.usf.ratsim.micronsl.Module;
import edu.usf.ratsim.support.GeomUtils;

public class TaxicValueSchema extends Module {

	public float[] value;
	private float reward;

	private Subject subject;
	private LocalizableRobot robot;
	private double lambda;
	private boolean estimateValue;

	public TaxicValueSchema(String name, Subject subject,
			LocalizableRobot robot, float reward, float lambda,
			boolean estimateValue) {
		super(name);
		this.reward = reward;

		// Value estimation
		value = new float[1];
		addOutPort("value", new Float1dPortArray(this, value));

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
		Int1dPort goalFeeder = (Int1dPort) getInPort("goalFeeder");


		// Get the value of the current position
		if (estimateValue) {
			value[0] = 0;
			for (Feeder f : robot.getVisibleFeeders(goalFeeder.getData())) {
				if (robot.isFeederClose()
						&& robot.getClosestFeeder().getId() == f.getId())
					value[0] += getFeederValue(f.getPosition());
			}
		}
	}

	private float getFeederValue(Point3f feederPos) {
		float steps = GeomUtils.getStepsToFeeder(feederPos, subject);
		return (float) (reward * Math.pow(lambda, steps));
	}

	@Override
	public boolean usesRandom() {
		return false;
	}

}
