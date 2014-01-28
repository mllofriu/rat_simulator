package nsl.modules;

import java.util.Random;

import javax.vecmath.Quat4f;

import experiment.ExpUniverseFactory;
import experiment.ExperimentUniverse;
import nslj.src.lang.NslModule;
import robot.IRobot;
import support.Utiles;

public class Explorer extends NslModule {
	private IRobot robot;
	private Random r;
	private ExperimentUniverse universe;

	public Explorer(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);
		this.robot = robot;
		this.universe = universe;
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
