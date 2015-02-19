package edu.usf.ratsim.experiment.universe;

import edu.usf.ratsim.experiment.universe.virtual.VirtUniverse;
import edu.usf.ratsim.robot.naorobot.GlobalCameraUniv;
import edu.usf.ratsim.robot.romina.SLAMUniverse;
import edu.usf.ratsim.support.Configuration;

public class UniverseFactory {

	public static ExperimentUniverse getUniverse(String mazeFile) {
		String univType = Configuration.getString("Reflexion.Universe");
		if (univType.equals("edu.usf.ratsim.robot.virtual.VirtualExpUniverse")) {
			return new VirtUniverse(mazeFile);
		} else if (univType.equals("edu.usf.ratsim.robot.virtual.GlobalCameraUniv")) {
			return new GlobalCameraUniv(mazeFile);
		} else if (univType.equals("edu.usf.ratsim.robot.romina.SLAMUniverse")) {
			return new SLAMUniverse(mazeFile);
		} else {
			throw new RuntimeException("Universe " + univType
					+ " not implemented");
		}
	}

}
