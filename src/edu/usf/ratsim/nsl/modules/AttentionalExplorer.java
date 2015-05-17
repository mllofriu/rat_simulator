package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.Robot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.EatAffordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;
import edu.usf.experiment.subject.affordance.TurnAffordance;
import edu.usf.experiment.universe.Feeder;
import edu.usf.ratsim.support.GeomUtils;

/**
 * Module to generate random actions when the agent hasnt moved (just rotated)
 * for a while
 * 
 * @author biorob
 * 
 */
public class AttentionalExplorer extends NslModule {

	private static final float TRACKIN_THRS = .05f;
	private NslDoutFloat1 votes;
	private Subject sub;
	private float exploringVal;
	private Robot robot;
	private Random r;
	private Point3f currentInterest;
	private NslDinInt0 takenAction;
	private int attentionRemaining;
	private int maxAttentionSpan;

	public AttentionalExplorer(String nslName, NslModule nslParent,
			Subject sub, float exploringVal, int maxAttentionSpan) {
		super(nslName, nslParent);

		votes = new NslDoutFloat1(this, "votes", sub.getPossibleAffordances()
				.size() + 1);
		takenAction = new NslDinInt0(this, "takenAction");

		this.maxAttentionSpan = maxAttentionSpan;
		this.attentionRemaining = 0;
		this.sub = sub;
		this.exploringVal = exploringVal;
		this.robot = sub.getRobot();

		r = new Random();
		currentInterest = null;
	}

	public void simRun() {
		votes.set(0);

		// Apply last movement to track interest point
		if (currentInterest != null && takenAction.get() != -1)
			currentInterest = applyLastMove(currentInterest, takenAction.get());

		// Find all visible interest points
		List<Point3f> interestingPoints = robot.getInterestingPoints();
		// If no current interest or not found, create new interest
		if (attentionRemaining <= 0
				&& currentInterest == null
				|| findClosePoint(currentInterest, interestingPoints,
						TRACKIN_THRS) == null) {
			currentInterest = interestingPoints.get(r.nextInt(interestingPoints
					.size()));
			attentionRemaining = maxAttentionSpan;
		} else {
			attentionRemaining--;
		}

		// Get the current position of the tracking
		currentInterest = findClosePoint(currentInterest, interestingPoints,
				TRACKIN_THRS);

		// For each affordance set a value based on current interest point
		List<Affordance> affs = robot.checkAffordances(sub
				.getPossibleAffordances());
		int voteIndex = 0;
		for (Affordance af : affs) {
			float value = 0;
			if (af.isRealizable()) {
				if (af instanceof TurnAffordance) {
					value += getFeederValue(
							GeomUtils.simulate(currentInterest, af),
							exploringVal);
				} else if (af instanceof ForwardAffordance) {
					value += getFeederValue(
							GeomUtils.simulate(currentInterest, af),
							exploringVal);
				} else if (af instanceof EatAffordance) {
				} else
					throw new RuntimeException("Affordance "
							+ af.getClass().getName()
							+ " not supported by robot");
			}

			votes.set(voteIndex, value);
			voteIndex++;
		}
	}

	private Point3f findClosePoint(Point3f p, List<Point3f> points,
			float trackinThrs) {
		for (Point3f p2 : points)
			if (p2.distance(p) < trackinThrs)
				return p2;
		return null;
	}

	private Point3f applyLastMove(Point3f p, int i) {
		return GeomUtils.simulate(p, sub.getPossibleAffordances().get(i));
	}

	private float getFeederValue(Point3f feederPos, float reward) {
		float steps = GeomUtils.getStepsToFeeder(feederPos, sub);
		return (float) (reward * Math.pow(.99, steps));
	}
}
