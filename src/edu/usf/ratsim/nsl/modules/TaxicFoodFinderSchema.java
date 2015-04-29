package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

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

public class TaxicFoodFinderSchema extends NslModule {

	private double forwardBias;
	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 votes;
	private float maxReward;

	private Subject subject;
	private LocalizableRobot robot;

	// If previous execution tried to eat, no reward is expected - the
	// individual is fixated in the feeder

	public TaxicFoodFinderSchema(String nslName, NslModule nslParent,
			Subject subject, LocalizableRobot robot, float maxReward,
			float minAngle) {
		super(nslName, nslParent);
		this.maxReward = maxReward;
		this.forwardBias = minAngle / 2;

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		votes = new NslDoutFloat1(this, "votes", subject
				.getPossibleAffordances().size());

		this.subject = subject;
		this.robot = robot;
	}

	public void simRun() {
		votes.set(0);

		// TODO: include goal!
		List<Affordance> affs = robot.checkAffordances(subject
				.getPossibleAffordances());
		int voteIndex = 0;
		for (Affordance af : affs) {
			float value = 0;
			if (af.isRealizable()) {
				if (af instanceof TurnAffordance) {
					if (!subject.hasTriedToEat()
							&& !subject.getRobot().isFeederClose()
							&& robot.seesFeeder()
							&& !robot.seesFlashingFeeder()) {
						TurnAffordance ta = (TurnAffordance) af;
						value = valAfterRot(ta.getAngle(), goalFeeder.get());

					} else if (subject.hasTriedToEat())
						if (!subject.hasEaten())
							value = -maxReward;
				} else if (af instanceof ForwardAffordance) {
					if (!subject.hasTriedToEat()
							&& !subject.getRobot().isFeederClose()
							&& robot.seesFeeder()
							&& !robot.seesFlashingFeeder()) {
						value = valAfterRot(0, goalFeeder.get());
					} else if (subject.hasTriedToEat())
						if (!subject.hasEaten())
							value = -maxReward;
				} else if (af instanceof EatAffordance) {
					// If not feeding from the last tried feeder
					// Once tried to eat from it, goal feeder will change and
					// reward will no longer be predicted
					// like dopamine decay after expected reward not appearing
					// goalFeeder will regulate this
					if (!subject.hasTriedToEat()
							&& subject.getRobot().getClosestFeeder().getId() != goalFeeder
									.get()
							&& subject.getRobot().isFeederClose()
							&& !robot.seesFlashingFeeder()) {
						value = maxReward;
					} else if (subject.hasTriedToEat())
						if (!subject.hasEaten())
							value = -maxReward;
					// else if (subject.getRobot().getClosestFeeder().getId() ==
					// goalFeeder
					// .get())
					// value = -maxReward;
				} else
					throw new RuntimeException("Affordance "
							+ af.getClass().getName()
							+ " not supported by robot");
			}

			votes.set(voteIndex, value);
			voteIndex++;
		}

	}

	private float valAfterRot(float angle, int except) {
		float val = 0;
		for (Feeder f : robot.getFeeders(except)) {
			Quat4f rotToFood = GeomUtils.angleToPoint(f.getPosition());

			Quat4f actionAngle = GeomUtils.angleToRot(angle);

			float angleDiff = Math.abs(GeomUtils.angleDiff(actionAngle,
					rotToFood));

			val += maxReward
					* (1 - (Math.max(angleDiff - forwardBias, 0)) / Math.PI);
		}

		return val / robot.getFeeders(-1).size();
	}

}
