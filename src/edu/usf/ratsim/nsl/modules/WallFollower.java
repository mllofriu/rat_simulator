package edu.usf.ratsim.nsl.modules;

import java.awt.Robot;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

/**
 * Sets the dopaminergic votes for both a flashing feeder and a non flashing one.
 * @author ludo
 *
 */
public class WallFollower extends NslModule {

	private ExperimentUniverse univ;
	public NslDoutFloat1 votes;
	private float wallFollowingValue;
	private IRobot robot;

	public WallFollower(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse univ, int numActions,
			float wallFollowingValue) {
		super(nslName, nslParent);
		this.univ = univ;
		this.robot = robot;
		this.wallFollowingValue = wallFollowingValue;

		votes = new NslDoutFloat1(this, "votes", numActions);

	}

	public void simRun() {
		votes.set(0);
//		if (univ.isRobotParallelToWall()){
//			votes.set(Utiles.discretizeAction(0), wallFollowingValue);
//		}
		boolean[] aff = robot.getAffordances();
		boolean anyBlock = false;
		for (int i = 0; i < aff.length; i++)
			anyBlock = anyBlock || !aff[i];
		if (anyBlock)
			for (int i = 0; i < aff.length; i++)
				// If there is affordance and it is a forward move (avoid oscilations)
				if (aff[i] && Utiles.actions[i] < Math.PI/2 && Utiles.actions[i] > -Math.PI/2)
					votes.set(i, wallFollowingValue);
		
	}
}
