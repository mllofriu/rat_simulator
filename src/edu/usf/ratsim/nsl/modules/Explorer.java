package edu.usf.ratsim.nsl.modules;

import java.util.Random;

import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

public class Explorer extends NslModule {
	private IRobot robot;
	private Random r;

	public Explorer(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);
		this.robot = robot;
		r = new Random();
	}

	public void simRun() {
		
		boolean[] affordances;
		int action;
		do {
			action = (int) Math.round(r.nextGaussian() * .5 + Utiles.discretizeAction(0));
			// Trim
			action = action < 0 ? 0 : action;
			action = action > Utiles.actions.length ? Utiles.actions.length - 1 : action;
			// Rotate the robot to the desired action
			robot.rotate(Utiles.actions[action]);
			// Re-calculate affordances
			affordances = robot.affordances();
		} while (!affordances[Utiles.discretizeAction(0)]);
	
		robot.forward();
	}
}
