package edu.usf.ratsim.nsl.modules;

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

public class FlashingTaxicFoodFinderSchema extends NslModule {

	private double forwardBias;
	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 votes;
	private float maxReward;
	private Random r;

	private int repCount;

	private double alpha;

	private Subject subject;
	private LocalizableRobot robot;

	public FlashingTaxicFoodFinderSchema(String nslName, NslModule nslParent,
			Subject subject, LocalizableRobot robot, float maxReward,
			float closeToFoodThrs, float minAngle) {
		super(nslName, nslParent);
		this.maxReward = maxReward;
		this.forwardBias = minAngle/2;

		goalFeeder = new NslDinInt0(this, "goalFeeder");
		votes = new NslDoutFloat1(this, "votes", subject
				.getPossibleAffordances().size());

		r = new Random();

		repCount = 0;

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
			if(af.isRealizable()){
				if (af instanceof TurnAffordance) {
					if (robot.seesFlashingFeeder()){
						TurnAffordance ta = (TurnAffordance) af;
						float angleDiff = diffAfterRot(ta.getAngle());

						value = (float) (maxReward * (1 - angleDiff / Math.PI));
					}
				} else if (af instanceof ForwardAffordance){
					if (robot.seesFlashingFeeder()){
						float angleDiff = diffAfterRot(0);

						// Set the votes proportional to the error in heading
						// Max heading error should be PI
						value = (float) (maxReward * (1 - (Math.max(angleDiff - forwardBias, 0)) / Math.PI));
					}
				} else if (af instanceof EatAffordance) {
					value = maxReward;
				} else
					throw new RuntimeException("Affordance "
							+ af.getClass().getName() + " not supported by robot");
			}
			
			votes.set(voteIndex, value);
			voteIndex++;
		}

	}

	private float diffAfterRot(float angle) {
		Quat4f rotToFood = GeomUtils.angleToPoint(robot.getFlashingFeeder().location);
		
		Quat4f actionAngle = GeomUtils.angleToRot(angle);
		
		return Math.abs(GeomUtils
				.angleDiff(actionAngle, rotToFood));
	}

}
