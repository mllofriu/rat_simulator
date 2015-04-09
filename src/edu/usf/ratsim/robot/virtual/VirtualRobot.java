package edu.usf.ratsim.robot.virtual;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import edu.usf.experiment.robot.Landmark;
import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.EatAffordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;
import edu.usf.experiment.subject.affordance.TurnAffordance;
import edu.usf.experiment.universe.Feeder;
import edu.usf.experiment.utils.ElementWrapper;
import edu.usf.ratsim.experiment.universe.virtual.VirtUniverse;
import edu.usf.ratsim.support.GeomUtils;

public class VirtualRobot extends LocalizableRobot {

	public VirtUniverse universe;

	private Random r;

	private float noise;

	private float lookaheadSteps;

	private float visionDist;

	private float halfFieldOfView;

	private float closeThrs;

	public VirtualRobot(ElementWrapper params) {
		super(params);

		noise = params.getChildFloat("noise");
		lookaheadSteps = params.getChildFloat("lookaheadSteps");
		halfFieldOfView = params.getChildFloat("halfFieldOfView");
		visionDist = params.getChildFloat("visionDist");
		closeThrs = params.getChildFloat("closeThrs");
		
		universe = VirtUniverse.getInstance();
		if (universe == null)
			throw new RuntimeException("A virtual universe must be created"
					+ " before Virtual Robot is created");

		r = new Random();
	}

	public void rotate(float grados) {
		universe.rotateRobot(grados + noise * r.nextFloat() * grados);
	}

	public void startRobot() {
	}

	public boolean hasFoundFood() {
		return universe.hasRobotFoundFood();
	}

	public void forward(float dist) {
		universe.moveRobot(new Vector3f(dist + dist * r.nextFloat() * noise, 0f,
				0f));
	}

	@Override
	public void eat() {
		universe.robotEat();
	}
	
	public List<Landmark> getLandmarks() {
		return getLandmarks(-1);
	}

	public List<Landmark> getLandmarks(int except) {
		List<Landmark> res = new LinkedList<Landmark>();
		for (Integer i : universe.getFeeders())
			if (i != except)
				if (universe.canRobotSeeFeeder(i, halfFieldOfView, visionDist)) {
					// Get relative position
					Point3f fPos = universe.getFoodPosition(i);
					Point3f rPos = universe.getRobotPosition();
					Point3f relFPos = new Point3f(GeomUtils.pointsToVector(rPos,
							fPos));
					// Rotate to robots framework
					Quat4f rRot = universe.getRobotOrientation();
					rRot.inverse();
					Transform3D t = new Transform3D();
					t.setRotation(rRot);
					t.transform(relFPos);
					// Return the landmark
					res.add(new Landmark(i, relFPos));
				}

		return res;
	}
	
	public List<Feeder> getFeeders(int except) {
		List<Feeder> res = new LinkedList<Feeder>();
		for (Integer i : universe.getFeeders())
			if (i != except)
				if (universe.canRobotSeeFeeder(i, halfFieldOfView, visionDist)) {
					// Get relative position
					Point3f fPos = universe.getFoodPosition(i);
					Point3f rPos = universe.getRobotPosition();
					Point3f relFPos = new Point3f(GeomUtils.pointsToVector(rPos,
							fPos));
					// Rotate to robots framework
					Quat4f rRot = universe.getRobotOrientation();
					rRot.inverse();
					Transform3D t = new Transform3D();
					t.setRotation(rRot);
					t.transform(relFPos);
					// Return the landmark
					res.add(new Feeder(relFPos));
				}

		return res;
	}

	@Override
	public edu.usf.experiment.robot.Landmark getFlashingFeeder() {
		for (Integer i : universe.getFeeders())
			if (universe.canRobotSeeFeeder(i, halfFieldOfView, visionDist) && universe.isFeederFlashing(i)) {
				// Get relative position
				Point3f fPos = universe.getFoodPosition(i);
				Point3f rPos = universe.getRobotPosition();
				Point3f relFPos = new Point3f(GeomUtils.pointsToVector(rPos,
						fPos));
				// Rotate to robots framework
				Quat4f rRot = universe.getRobotOrientation();
				rRot.inverse();
				Transform3D t = new Transform3D();
				t.setRotation(rRot);
				t.transform(relFPos);
				// Return the landmark
				return new Landmark(i, relFPos);
			}
		return null;
	}

	@Override
	public boolean seesFlashingFeeder() {
		return getFlashingFeeder() != null;
	}

	@Override
	public Landmark getClosestFeeder(int lastFeeder) {
		List<Landmark> lms = getLandmarks(lastFeeder);

		if (lms.isEmpty())
			return null;
		
		Landmark closest = lms.get(0);
		Point3f zero = new Point3f(0,0,0);
		for (Landmark lm : lms)
			if (lm.location.distance(zero) < closest.location.distance(zero))
				closest = lm;
		
		return closest;
	}

	@Override
	public boolean isFeederClose() {
		Landmark lm = getClosestFeeder(-1);
		return lm != null && lm.location.distance(new Point3f()) < closeThrs;
	}

	@Override
	public Point3f getPosition() {
		return universe.getRobotPosition();
	}

	@Override
	public float getOrientationAngle() {
		return universe.getRobotOrientationAngle();
	}

	@Override
	public Quat4f getOrientation() {
		return universe.getRobotOrientation();
	}

	@Override
	public List<Affordance> checkAffordances(List<Affordance> affs){
		return universe.getRobotAffordances(affs, lookaheadSteps, closeThrs);
	}

	@Override
	public void executeAffordance(Affordance af, Subject sub) {
		if (af instanceof TurnAffordance){
			TurnAffordance ta = (TurnAffordance) af;
			List<Affordance> forward = new LinkedList<Affordance>();
			forward.add(new ForwardAffordance(ta.getDistance()));
			// Turn until can move forward (there is no wall on front)
			do {
				rotate(ta.getAngle());
				forward = checkAffordances(forward);
			} while (!forward.get(0).isRealizable());
			
		} else if (af instanceof ForwardAffordance)
			forward(((ForwardAffordance)af).getDistance());
		else if (af instanceof EatAffordance){
			sub.setHasEaten(true);
			// Updates food in universe
			eat();
		} else
			throw new RuntimeException("Affordance "
					+ af.getClass().getName() + " not supported by robot");
	}

	@Override
	public boolean seesFeeder() {
		return getClosestFeeder(-1) != null;
	}


}
