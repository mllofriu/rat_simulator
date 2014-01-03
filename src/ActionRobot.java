import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import robot.IRobot;
import robot.RobotFactory;

import nslj.src.lang.NslDinDouble1;
import nslj.src.lang.NslDoutDouble1;
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
	private static final String DEFAULT_FILE_NAME = "activationLog.data";
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
		robot.doAction(NON_SENSE_ACTION); // avanza al siguiente punto
	}
	
}