package nsl.modules;
/* M���dulo de selecci���n de acci���n.
   Alejandra Barrera
   Versi���n: 1 (Febrero, 2005)
 */

import java.util.Random;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import robot.IRobot;

public class ActionSelectionSchema extends NslModule {
	public NslDoutInt0 actionTaken;
	private IRobot robot;

	public ActionSelectionSchema(String nslName, NslModule nslParent, IRobot robot) {
		super(nslName, nslParent);
		actionTaken = new NslDoutInt0("ActionTaken", this);
		this.robot = robot;
	}

	public void simRun() {
		// Driver - Always go forward 
//		for (boolean i : robot.affordances())
//			System.out.print(i + " ");
//		System.out.println("");
		
		int action;
		Random r = new Random();
		do{
			action = r.nextInt(IRobot.NUM_POSSIBLE_ACTIONS);
		} while (!robot.affordances()[action]);
		actionTaken.set(action);
	} 
}
