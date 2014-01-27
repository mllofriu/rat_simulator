package nsl.modules;

import java.util.Random;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import robot.IRobot;

public class RandomActionSelSchema extends NslModule {
	public NslDoutInt0 actionTaken;
	private IRobot robot;
	private Random r;

	public RandomActionSelSchema(String nslName, NslModule nslParent,
			IRobot robot) {
		super(nslName, nslParent);
		actionTaken = new NslDoutInt0("ActionTaken", this);
		this.robot = robot;
		r = new Random();
	}

	public void simRun() {
		// System.out.println("calling affordances");
		boolean[] affordances = robot.affordances();
		// for (boolean i : affordances)
		// System.out.print(i + " ");
		// System.out.println("");

		int action;
		
		do {
			action = r.nextInt(IRobot.NUM_POSSIBLE_ACTIONS);
		} while (!affordances[action]);
		actionTaken.set(action);
	}
}
