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
	private static final int WALL_LOOKAHEAD = 15;
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

		boolean[] aff = robot.getAffordances(WALL_LOOKAHEAD);
		for (int i = 0; i < aff.length; i++)
			if (!aff[i])
				votes.set(i, wallFollowingValue);
	}
}
