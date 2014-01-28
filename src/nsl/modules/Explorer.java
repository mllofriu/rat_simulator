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
			
			// Best idiothetic action to reach the desired rotation
//			System.out.println("rotating " + Utiles.actions[action]);
			robot.rotate(Utiles.actions[action]);
			affordances = robot.affordances();
//			for (int i = 0; i < affordances.length; i++)
//				System.out.print(affordances[i] + " ");
//			System.out.println();
		} while (!affordances[action]);
	
		robot.forward();
	}
}
