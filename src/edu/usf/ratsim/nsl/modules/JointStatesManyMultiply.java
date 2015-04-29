package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.utils.Debug;

public class JointStatesManyMultiply extends NslModule {

	private static final float EPS = 0.2f;
	public List<NslDinFloat1> states;
	public NslDoutFloat1 jointState;

	public JointStatesManyMultiply(String nslName, NslModule nslParent,
			List<Integer> stateSizes) {
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

		if (Debug.printJointMultiply) {
			if (jointState.getSize() == 64) {
				System.out.println("Multiply States");
				for (NslDinFloat1 state : states) {
					for (int i = 0; i < state.getSize(); i++)
						System.out.print(state.get(i) + " ");
					System.out.println();
				}
				System.out.println("End Multiply States");
			}
		}
		for (int i = 0; i < jointStatesSize;) {
			float jointActivation = 1;
			int remainingSize = jointState.getSize();
			boolean broke = false;
			for (NslDinFloat1 state : states) {
				remainingSize /= state.getSize();
				// Get the contribution of the particular state
				int index = (i / remainingSize) % state.getSize();
				float activation = state.get(index);

				jointActivation *= activation;

				// If I see a zero activation, I can skip all states involving
				// this one
				// Optimiziation to speed up execution
				if (activation < EPS) {
					// System.out.println("adding " + remainingSize);
					i += remainingSize;
					broke = true;
					break;
				}
			}

			// Only update if not zero above at any point and didnt brake
			if (!broke) {
				jointState.set(i, jointActivation);
				i++;
			}

		}

		if (Debug.printJointMultiply)
			if (jointState.getSize() == 64) {
				System.out.println("Multiply States Joint");
				for (int i = 0; i < jointState.getSize(); i++)
					System.out.print(jointState.get(i) + " ");
				System.out.println();
				System.out.println("End Multiply States Joint");
			}
	}

	@Override
	protected void finalize() {
		// TODO Auto-generated method stub
		super.finalize();

		// System.out.println("Finalized JointStates");
	}

	public int getSize() {
		return jointState.getSize();
	}
}
