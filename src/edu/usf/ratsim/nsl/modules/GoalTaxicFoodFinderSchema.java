package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import javax.vecmath.Quat4f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.Landmark;
import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.EatAffordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;
import edu.usf.experiment.subject.affordance.TurnAffordance;
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
		votes = new NslDoutFloat1(this, "votes", subject
				.getPossibleAffordances().size());

		r = new Random();

		repCount = 0;

		this.subject = subject;
		this.robot = robot;
	}

	public void simRun() {
		votes.set(0);

		List<Affordance> affs = robot.checkAffordances(subject
				.getPossibleAffordances());
		int voteIndex = 0;
		for (Affordance af : affs) {
			float value = 0;
			if(af.isRealizable()){
				if (af instanceof TurnAffordance) {
					if (robot.seesFeeder()){
						TurnAffordance ta = (TurnAffordance) af;
						float angleDiff = diffAfterRot(ta.getAngle());
	
						// Set the votes proportional to the error in heading
						// Max heading error should be PI
						value = (float) (maxReward * (1 - angleDiff) / Math.PI);
					}
				} else if (af instanceof ForwardAffordance){
					if (robot.seesFeeder()){
						float angleDiff = diffAfterRot(0);
	
						// Set the votes proportional to the error in heading
						// Max heading error should be PI
						value = (float) (maxReward * (1 - angleDiff) / Math.PI);
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
		Quat4f rotToFood = GeomUtils.angleToPoint(robot.getClosestFeeder(-1).location);
		
		Quat4f robOrient = robot.getOrientation();
		Quat4f actionAngle = GeomUtils.angleToRot(angle);
		robOrient.mul(actionAngle);
		
		return Math.abs(GeomUtils
				.angleDiff(robOrient, rotToFood));
	}

	public void newRep() {
		repCount++;
		// System.out.println("Increasing rep" + repCount);
	}

	public void newTrial() {
		repCount = 0;
	}

}
