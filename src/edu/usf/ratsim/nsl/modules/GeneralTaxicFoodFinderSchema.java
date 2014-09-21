//package edu.usf.ratsim.nsl.modules;
//
//import javax.vecmath.Point3f;
//import javax.vecmath.Quat4f;
//import javax.vecmath.Vector3f;
//
//import nslj.src.lang.NslDinInt0;
//import nslj.src.lang.NslDoutFloat1;
//import nslj.src.lang.NslModule;
//import edu.usf.ratsim.experiment.ExperimentUniverse;
//import edu.usf.ratsim.robot.IRobot;
//import edu.usf.ratsim.robot.Landmark;
//import edu.usf.ratsim.support.Configuration;
//import edu.usf.ratsim.support.Debug;
//import edu.usf.ratsim.support.Utiles;
//
///**
// * Sets the dopaminergic votes for both a flashing feeder and a non flashing
// * one.
// * 
// * @author ludo
// * 
// */
//public class GeneralTaxicFoodFinderSchema extends NslModule {
//
//	private ExperimentUniverse univ;
//	public NslDinInt0 goalFeeder;
//	public NslDoutFloat1 votes;
//	private float rewardFlashing, rewardNonFlashing;
//	private IRobot robot;
//	private static int timeWaiting;
//	private static int feedersDelay;
//	private static int waitingForFeeder;
//
//	public GeneralTaxicFoodFinderSchema(String nslName, NslModule nslParent,
//			IRobot robot, ExperimentUniverse univ, int numActions,
//			float rewardFlashing, float rewardNonFlashing) {
//		super(nslName, nslParent);
//		this.univ = univ;
//		this.robot = robot;
//		this.rewardFlashing = rewardFlashing;
//		this.rewardNonFlashing = rewardNonFlashing;
//
//		waitingForFeeder = -1;
//		timeWaiting = 0;
//		feedersDelay = Configuration.getInt("VirtualUniverse.feedersDelay");
//
//		goalFeeder = new NslDinInt0(this, "goalFeeder");
//		votes = new NslDoutFloat1(this, "votes", numActions);
//
//	}
//
//	public void simRun() {
//		votes.set(0);
//
//		boolean foundFeeder = false;
//
//		// Go over all feeders
//		boolean aff[] = robot.getAffordances();
//		for (Landmark lm : robot.getLandmarks()) {
//			// Avoid last feeder
//
//			float angleToFeeder = Utiles.angleToPoint(lm.location);
//			if (aff[Utiles.discretizeAction((int) Math
//							.toDegrees(angleToFeeder))]) {
//				if (wantedFeeder(lm.id)) {
//					foundFeeder = true;
//					// Get the best action for that feeder
//					int action = getActionToFeeder(fn);
//					// Set the reward to be the corresponding reward
//					// float distanceMod = Math.max(0,
//					// (1 - univ.getDistanceToFeeder(fn)));
//					float distanceMod = 1;
//
//					if (univ.getFlashingFeeders().contains(fn))
//						votes.set(
//								action,
//								Math.max(rewardFlashing * distanceMod,
//										votes.get(action)));
//					else
//						votes.set(
//								action,
//								Math.max(rewardNonFlashing * distanceMod,
//										votes.get(action)));
//
//					// if (action == Utiles.eatAction)
//					// lastEatenFeeder = fn;
//				}
//			}
//
//		}
//
//		// If I cannot go in one direction, follow the wall
//		// boolean aff[] = robot.getAffordances();
//		// boolean forbiddenAff = false;
//		// for (int i = 0; i < Utiles.numRotations; i++)
//		// if (Utiles.getActionAngle(i) != 0)
//		// forbiddenAff = forbiddenAff || !aff[i];
//		//
//		// int forwardAction = Utiles.discretizeAction(0);
//		// if (forbiddenAff)
//		// if (!univ.getFlashingFeeders().isEmpty())
//		// votes.set(forwardAction,
//		// Math.max(rewardFlashing, votes.get(forwardAction)));
//		// else
//		// votes.set(
//		// forwardAction,
//		// Math.max(rewardNonFlashing,
//		// votes.get(forwardAction)));
//
//		if (!foundFeeder) {
//			// Give a forward impulse
//			votes.set(Utiles.discretizeAction(0), rewardNonFlashing);
//		}
//
//	}
//
//	private boolean wantedFeeder(int fn) {
//		// The feeder is wanted if it is not the last one and
//		// the time waiting has not expired
//		// 2 * feedersDelay - 1 makes the first taxic component want to eat
//		// but the second, after the robot action wont want to eat
//		return !(waitingForFeeder == fn && timeWaiting > 2 * feedersDelay - 1);
//	}
//
//	private int getActionToFeeder(int feeder) {
//		if (univ.isRobotCloseToFeeder(feeder)) {
//			if (feeder != waitingForFeeder) {
//				waitingForFeeder = feeder;
//				timeWaiting = 0;
//			}
//
//			timeWaiting++;
//			// // If there is food or enough time has passed - eat
//			// // if (univ.hasFoodFeeder(feeder) || timeWaiting > feedersDelay){
//			// // return Utiles.eatAction;
//			// // // If not - wait
//			// // }
//			// // else
//			// // return Utiles.waitAction;
//			// if (timeWaiting < feedersDelay)
//			if (Debug.printTryingToEat)
//				System.out.println("Trying to eat");
//			return Utiles.eatAction;
//		} else {
//			Point3f rPos = univ.getRobotPosition();
//			Point3f fPos = univ.getFoodPosition(feeder);
//
//			// Get the vector food - robot
//			Vector3f vToFood = Utiles.pointsToVector(rPos, fPos);
//
//			// Build quat4d for angle to food
//			// Use (1,0,0) to get absolute orientation
//			Quat4f rotToFood = Utiles.rotBetweenVectors(new Vector3f(1, 0, 0),
//					vToFood);
//
//			Quat4f currRot = univ.getRobotOrientation();
//			return Utiles.bestActionToRot(rotToFood, currRot);
//		}
//	}
//}
