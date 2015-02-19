package edu.usf.ratsim.robot;

import edu.usf.ratsim.experiment.universe.virtual.VirtUniverse;
import edu.usf.ratsim.robot.naorobot.protobuf.NAORobot;
import edu.usf.ratsim.robot.romina.Romina;
import edu.usf.ratsim.robot.virtual.VirtualRobot;

public class RobotFactory {

	public static IRobot getRobot(String robotType, ExperimentUniverse universe) {
		if (robotType.equals("edu.usf.ratsim.robot.virtual.VirtualRobot")) {
			if (!(universe instanceof VirtUniverse))
				throw new RuntimeException(
						"Virtual robot must be created with a virtual universe");
			return new VirtualRobot((VirtUniverse) universe);
		} else if (robotType.equals("edu.usf.ratsim.robot.naorobot.NAORobot")) {
			return new NAORobot("localhost", 12345,(VirtUniverse) universe);
		} else if (robotType.equals("edu.usf.ratsim.robot.romina.Romina")) {
			return new Romina("localhost", 12345,(VirtUniverse) universe);
		} else {
			throw new RuntimeException("Robot " + robotType
					+ " not implemented");
		}
	}

}
