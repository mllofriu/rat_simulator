package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.utils.Debug;

public class JointStatesManySum extends NslModule {

	public List<NslDinFloat1> states;
	public NslDoutFloat1 jointState;

	public JointStatesManySum(String nslName, NslModule nslParent, int numStates, int statesSize) {
		super(nslName, nslParent);

		states = new LinkedList<NslDinFloat1>();
		for (int i = 0; i < numStates; i++) {
			states.add(new NslDinFloat1(this, "state" + i, statesSize));
		}

		int jointSize = statesSize;
		jointState = new NslDoutFloat1(this, "jointState", jointSize);
	}

	public void simRun() {

		jointState.set(0);
		// Iterate over all states

		int jointStatesSize = jointState.getSize();

		if (Debug.printValues)
			System.out.println("Values");
		for (int i = 0; i < states.size(); i++) {
			// Add up all states values
			for (int j = 0; j < jointStatesSize; j++) {
				if (Debug.printValues)
					System.out.format("%.2f\t\t", states.get(i).get(j));
				jointState.set(j, jointState.get(j) + states.get(i).get(j));
			}
			if (Debug.printValues) System.out.println();
		}
		// Go over all states in joint state

		if (Debug.printValues) System.out.println();
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
