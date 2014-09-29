package edu.usf.ratsim.experiment.universe;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.naorobot.GlobalCameraUniv;
import edu.usf.ratsim.robot.naorobot.protobuf.NAORobot;
import edu.usf.ratsim.robot.virtual.VirtualExpUniverse;
import edu.usf.ratsim.robot.virtual.VirtualRobot;
import edu.usf.ratsim.support.Configuration;

public class UniverseFactory {

	public static ExperimentUniverse getUniverse(String mazeFile) {
		String univType = Configuration.getString("Reflexion.Universe");
		if (univType.equals("edu.usf.ratsim.robot.virtual.VirtualExpUniverse")) {
			return new VirtualExpUniverse(mazeFile);
		} else if (univType.equals("edu.usf.ratsim.robot.virtual.GlobalCameraUniv")) {
			return new GlobalCameraUniv(mazeFile);
		} else {
			throw new RuntimeException("Universe " + univType
					+ " not implemented");
		}
	}

}
