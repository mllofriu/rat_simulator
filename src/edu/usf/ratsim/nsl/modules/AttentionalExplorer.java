package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.Robot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.universe.Feeder;

/**
 * Module to generate random actions when the agent hasnt moved (just rotated)
 * for a while
 * 
 * @author biorob
 * 
 */
public class AttentionalExplorer extends NslModule {

	private static final int TIME_EXPLORING = 40;
	private NslDoutFloat1 votes;
	private Subject sub;
	private float exploringVal;
	private Robot robot;
	private Random r;
	private Object currentInterest;

	public AttentionalExplorer(String nslName, NslModule nslParent,
			int maxActionsSinceForward, Subject sub, float exploringVal) {
		super(nslName, nslParent);

		votes = new NslDoutFloat1(this, "votes", sub.getPossibleAffordances()
				.size() + 1);

		this.sub = sub;
		this.exploringVal = exploringVal;
		this.robot = sub.getRobot();

		r = new Random();
		currentInterest = null;
	}

	public void simRun() {
		votes.set(0);

		List<Feeder> feeders = robot.getVisibleFeeders(-1);
		List<Point3f> wEnds = robot.getVisibleWallEnds();
		if (currentInterest == null
				|| !(feeders.contains(currentInterest) || wEnds
						.contains(currentInterest))) {
			int size = feeders.size() + wEnds.size();
			int pick = r.nextInt(size);
			if (pick < feeders.size())
				currentInterest = feeders.get(pick);
			else {
				pick -= feeders.size();
				currentInterest = wEnds.get(pick);
			}
		}
		
		Point3f dest = getPoint(currentInterest);	
		
	}

	private Point3f getPoint(Object obj) {
		if(obj instanceof Feeder)
			return ((Feeder)obj).getPosition();
		else
			return (Point3f) obj;
	}

}
