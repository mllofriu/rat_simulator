package edu.usf.ratsim.nsl.modules;

import java.util.Random;

import javax.vecmath.Quat4f;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

/**
 * Sets the dopaminergic votes for both a flashing feeder and a non flashing
 * one.
 * 
 * @author ludo
 * 
 */
public class WallFollower extends NslModule {

	private static final float EPS_VALUE = 1f;
	private static final float DIMINISH_FACTOR = .8f;
	private ExperimentUniverse univ;
	public NslDoutFloat1 votes;
	private float wallFollowingValue;
	private IRobot robot;
	private float currentValue;
	private boolean active;
	private int direction;
	private Random r;

	public WallFollower(String nslName, NslModule nslParent, IRobot robot,
			ExperimentUniverse univ, int numActions, float wallFollowingValue) {
		super(nslName, nslParent);
		this.univ = univ;
		this.robot = robot;
		this.wallFollowingValue = wallFollowingValue;
		this.currentValue = 0f;
		active = false;

		votes = new NslDoutFloat1(this, "votes", numActions);
		r = new Random();

	}

	public void simRun() {
		votes.set(0);
		// if (univ.isRobotParallelToWall()){
		// votes.set(Utiles.discretizeAction(0), wallFollowingValue);
		// }
//		boolean[] aff = robot.getAffordances();
//		// Get the blocked aff
//		int i;
//		for (i = 0; i < aff.length && aff[i]; i++)
//			;
//
//		int angles = aff.length;
		// Find the next to the left
//		int j = (i - 1 + angles) % angles;
//		while (!aff[j])
//			j = (j - 1 + angles) % angles;
		
//		if (currentValue > EPS_VALUE)
		
		// Stop wall following if there is no near wall
		boolean[] aff = robot.getAffordances();
		if (!aff[Utiles.discretizeAction(0)]){
//			active = true;
			currentValue = wallFollowingValue;
			if (r.nextFloat() > .5)
				direction = 1;
			else {
				direction = -1;
			}
		} /*else {
			boolean anyBlocked = false;
			for (int i = 0; i < aff.length; i++)
				anyBlocked = anyBlocked || !aff[i];
			if (anyBlocked)
				active = false;
		}
		*/
		
		if (currentValue > EPS_VALUE){
			Quat4f ori = univ.getRobotOrientation();
			if (!aff[Utiles.discretizeAction(0)]){
				Quat4f turn = Utiles.angleToRot( direction * 45);
				ori.mul(turn);
				votes.set(Utiles.discretizeAngle(ori), currentValue);
//				j = Utiles.discretizeAction(45);
//				ori = univ.getRobotOrientation();
//				turn = Utiles.angleToRot(Utiles.actions[j]);
//				ori.mul(turn);
//				votes.set(Utiles.discretizeAngle(ori), currentValue);
//				Quat4f ori = univ.getRobotOrientation();
//				votes.set(currentValue);
//				votes.set(Utiles.discretizeAngle(ori), 0);
			} else {
				votes.set(Utiles.discretizeAngle(ori), currentValue);
			}
		} 
		
		currentValue *= DIMINISH_FACTOR;
		// Find the next to the right
//		j = (i + 1) % angles;
//		while (!aff[j])
//			j = (j + 1) % angles;
//		ori = univ.getRobotOrientation();
//		turn = Utiles.angleToRot(Utiles.actions[j]);
//		ori.mul(turn);
//		votes.set(Utiles.discretizeAngle(ori), wallFollowingValue);
//		votes.set(j, wallFollowingValue);

	}
}
