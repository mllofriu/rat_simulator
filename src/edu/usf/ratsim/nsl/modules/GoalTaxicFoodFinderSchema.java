package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.Landmark;
import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.utils.Debug;
import edu.usf.ratsim.support.GeomUtils;

public class GoalTaxicFoodFinderSchema extends NslModule {

	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 votes;
	private float maxReward;
	private Random r;

	private int repCount;

	private double alpha;

	private Subject subject;
	private float closeToFoodThrs;
	private LocalizableRobot robot;

	public GoalTaxicFoodFinderSchema(String nslName, NslModule nslParent,
			Subject subject, LocalizableRobot robot, float maxReward,
			float explorationHalfLifeVal, float closeToFoodThrs) {
		super(nslName, nslParent);
		this.maxReward = maxReward;
		this.alpha = -Math.log(.5) / explorationHalfLifeVal;
		this.closeToFoodThrs = closeToFoodThrs;

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		votes = new NslDoutFloat1(this, "votes", subject.getNumActions());

		r = new Random();

		repCount = 0;

		this.subject = subject;
		this.robot = robot;
	}

	public void simRun() {
		votes.set(0);

		Landmark lm = getLM(goalFeeder.get(), robot.getLandmarks());
		boolean[] aff = robot.getAffordances();
		if (lm != null) {
			if (Debug.printTaxicBh)
				System.out.println("Found Food - executing taxic");
			if (lm.location.distance(new Point3f(0, 0, 0)) < closeToFoodThrs) {
				votes.set(subject.getEatActionNumber(), maxReward);
			} else {
				Quat4f rotToFood = GeomUtils.angleToPoint(lm.location);

				for (int i = 0; i < aff.length; i++) {
					Quat4f robOrient = robot.getOrientation();
					Quat4f actionAngle = GeomUtils.angleToRot(subject
							.getActionAngle(i));
					robOrient.mul(actionAngle);

					// Set the votes proportional to the error in heading
					// Max heading error should be PI
					votes.set(
							i,
							maxReward
									* (1 - Math.abs(GeomUtils.angleDiff(
											robOrient, rotToFood)) / Math.PI));
				}
			}
		} else {
			// Give a forward impulse
			double explorationValue = maxReward * Math.exp(-repCount * alpha);
			// System.out.println(explorationValue);
			if (r.nextFloat() > .8)
				votes.set(subject.getActionForward(), explorationValue);
			else if (r.nextFloat() > .5)
				votes.set(subject.getActionLeft(), explorationValue);
			else
				votes.set(subject.getActionRight(), explorationValue);
		}
	}

	private Landmark getLM(int id, List<Landmark> list) {
		for (Landmark lm : list)
			if (lm.id == id)
				return lm;

		return null;
	}

	public void newRep() {
		repCount++;
		// System.out.println("Increasing rep" + repCount);
	}

	public void newTrial() {
		repCount = 0;
	}

}
