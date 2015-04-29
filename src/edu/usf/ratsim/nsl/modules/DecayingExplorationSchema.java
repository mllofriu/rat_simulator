package edu.usf.ratsim.nsl.modules;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
import edu.usf.ratsim.support.GeomUtils;

public class DecayingExplorationSchema extends NslModule {

	public NslDoutFloat1 votes;
	private float maxReward;
	private Random r;
	private double alpha;

	private Subject subject;
	private LocalizableRobot robot;
	private int episodeCount;
	private Affordance lastPicked;

	public DecayingExplorationSchema(String nslName, NslModule nslParent,
			Subject subject, LocalizableRobot robot, float maxReward,
			float explorationHalfLifeVal) {
		super(nslName, nslParent);
		this.maxReward = maxReward;
		this.alpha = -Math.log(.5) / explorationHalfLifeVal;

		votes = new NslDoutFloat1(this, "votes", subject
				.getPossibleAffordances().size());

		r = new Random();

		episodeCount = 0;

		this.subject = subject;
		this.robot = robot;

		this.lastPicked = null;
	}

	public void simRun() {
		votes.set(0);

		// Flashing feeder prevents exploration
		// if (!robot.seesFlashingFeeder()){
		double explorationValue = maxReward * Math.exp(-episodeCount * alpha);
		List<Affordance> affs = robot.checkAffordances(subject
				.getPossibleAffordances());

		// Avoid alternating rotations as exploration
//		if (lastPicked != null && lastPicked instanceof TurnAffordance && turnRealizable(affs, (TurnAffordance)lastPicked))
//			affs = removeOtherTurns(affs, (TurnAffordance) lastPicked);

		Affordance pickedAffordance;
		do {
			pickedAffordance = affs.get(r.nextInt(affs.size()));
		} while (!pickedAffordance.isRealizable() || (pickedAffordance instanceof EatAffordance));

		votes.set(pickedAffordance.getIndex(), explorationValue);

		lastPicked = pickedAffordance;
		// }
	}

	private List<Affordance> removeOtherTurns(List<Affordance> affs,
			TurnAffordance turn) {
		for (Iterator<Affordance> iter = affs.iterator(); iter.hasNext();) {
			Affordance aff = iter.next();
			if (aff instanceof TurnAffordance
					&& ((TurnAffordance) aff).getAngle() != turn.getAngle())
				iter.remove();
		}

		return affs;
	}

	public void newEpisode() {
		episodeCount++;
	}

	public void newTrial() {
		episodeCount = 0;
	}

	public void setExplorationVal(float val) {
		maxReward = val;
	}

}
