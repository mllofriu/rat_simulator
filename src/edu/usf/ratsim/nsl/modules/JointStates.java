package edu.usf.ratsim.nsl.modules;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;

public class JointStates extends NslModule {

	public NslDinFloat1 state1;
	public NslDinFloat1 state2;
	public NslDoutFloat1 jointState;

	public JointStates(String nslName, NslModule nslParent,
			ExperimentUniverse universe, int sizeState1, int sizeState2) {
		super(nslName, nslParent);

		state1 = new NslDinFloat1(this, "state1", sizeState1);
		state2 = new NslDinFloat1(this, "state2", sizeState2);
		jointState = new NslDoutFloat1(this, "jointState", sizeState1
				* sizeState2);

	}

	public void simRun() {
		// Iterate over all states
		for (int i = 0; i < jointState.getSize(); i++) {
			// The joint state is just the multiplication of the two
			jointState.set(
					i,
					state1.get(i % state1.getSize())
							* state2.get(i % state2.getSize()));
		}
	}

	public int getSize() {
		return jointState.getSize();
	}
}
