package edu.usf.ratsim.nsl.modules;

import nslj.src.lang.NslDoutFloat0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.support.Utiles;

public class HeadingAngle extends NslModule {

	public NslDoutFloat0 headingAngle;

	private ExperimentUniverse universe;

	public HeadingAngle(String nslName, NslModule nslParent,
			ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.universe = universe;

		this.headingAngle = new NslDoutFloat0(this, "headingAngle");
	}

	public void simRun() {
		headingAngle.set((float)Utiles.discretizeAngle(universe
				.getRobotOrientationAngle()));
	}

}
