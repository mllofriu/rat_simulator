package edu.usf.ratsim.nsl.modules;

import java.io.PrintWriter;

import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.robot.RobotFactory;
import nslj.src.lang.NslDinDouble1;
import nslj.src.lang.NslModule;

/**
 * 
 */

/**
 * @author gtejera
 * 
 */
public class ActionRobot extends NslModule {
	private static final String DEFAULT_MODULE_NAME = "Cells viewer (nombre por defecto)";
	public static double DEFAULT_MIN_ACTIVATION = 1.8;
	public static int NON_SENSE_ACTION = 0;

	public NslDinDouble1 cellsActivationNSL;
	IRobot robot = RobotFactory.getRobot();
	PrintWriter pw = null;

	public ActionRobot(NslModule nslParent) {
		this(DEFAULT_MODULE_NAME, nslParent);
	}

	public ActionRobot(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
	}

	public void simRun() {
		robot.rotate(NON_SENSE_ACTION); // avanza al siguiente punto
	}

}