package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;

public class JointStatesMany extends NslModule {

	private static final float EPS = 0.01f;
	public List<NslDinFloat1> states;
	public NslDoutFloat1 jointState;

	public JointStatesMany(String nslName, NslModule nslParent,
			ExperimentUniverse universe, List<Integer> stateSizes) {
		super(nslName, nslParent);

		states = new LinkedList<NslDinFloat1>();
		int i = 1;
		int jointSize = 1;
		for (Integer stateSize : stateSizes) {
			states.add(new NslDinFloat1(this, "state" + i, stateSize));
			jointSize *= stateSize;
			i++;
		}
		jointState = new NslDoutFloat1(this, "jointState", jointSize);
	}

	public void simRun() {
		jointState.set(0);
		// Iterate over all states

		int jointStatesSize = jointState.getSize();
		
		for (int i = 0; i < jointStatesSize; i++) {
			float jointActivation = 1;
			int remainingSize = jointState.getSize();
			boolean broke = false;
			for (NslDinFloat1 state : states) {
				remainingSize /= state.getSize();
				// Get the contribution of the particular state
				int index = (i / remainingSize) % state.getSize();
				float activation = state.get(index);

				jointActivation *= activation;
				
				// If I see a cero activation, I can skip all states involving
				// this one
				// Optimiziation to speed up execution
				if (activation < EPS) {
					// System.out.println("adding " + remainingSize);
					i += remainingSize;
					broke = true;
					break;
				}
			}

			// Only update if not zero above at any point and breaked
			if (!broke) {
				jointState.set(i, jointActivation);
//				if (jointActivation > .5) {
//					remainingSize = jointState.getSize();
//					int j = 1;
//					for (NslDinFloat1 state : states) {
//						remainingSize /= state.getSize();
//						int index = (i / remainingSize) % state.getSize();
//						System.out.print("State " + j++ + ": " + index
//								+ " Activ: " + state.get(index) + "\t");
//					}
//					System.out.print("Activation: " + jointActivation);
//					System.out.println();
//				}
			}

		}
	}

	public int getSize() {
		return jointState.getSize();
	}
}
