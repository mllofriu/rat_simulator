package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;

public class JointStatesMany extends NslModule {

	public List<NslDinFloat1> states;
	public NslDoutFloat1 jointState;

	public JointStatesMany(String nslName, NslModule nslParent,
			ExperimentUniverse universe, List<Integer> stateSizes) {
		super(nslName, nslParent);

		states = new LinkedList<NslDinFloat1>();
		int i = 1;
		int jointSize = 1;
		for (Integer stateSize : stateSizes){
				states.add(new NslDinFloat1(this, "state" + i, stateSize));
				jointSize *= stateSize;
				i++;
		}
		jointState = new NslDoutFloat1(this, "jointState",jointSize);
	}

	public void simRun() {
		jointState.set(0);
		// Iterate over all states
		
		for (int i =0 ; i < jointState.getSize(); i++){
			float jointActivation = 1;
			int remainingSize = jointState.getSize();
			for (NslDinFloat1 state : states){
				remainingSize /= state.getSize();
				// Get the contribution of the particular state
				int index = (i / remainingSize) % state.getSize();
				jointActivation *= state.get(index);
			}
			jointState.set(i,jointActivation);
		}
	}

	public int getSize() {
		return jointState.getSize();
	}
}
