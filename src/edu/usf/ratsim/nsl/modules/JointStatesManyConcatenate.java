package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;

public class JointStatesManyConcatenate extends NslModule {

	private static final float EPS = 0.01f;
	public List<NslDinFloat1> states;
	public NslDoutFloat1 jointState;

	public JointStatesManyConcatenate(String nslName, NslModule nslParent, List<Integer> stateSizes) {
		super(nslName, nslParent);

		states = new LinkedList<NslDinFloat1>();
		int i = 0;
		int jointSize = 0;
		for (Integer stateSize : stateSizes) {
			states.add(new NslDinFloat1(this, "state" + i, stateSize));
			jointSize += stateSize;
			i++;
		}
		jointState = new NslDoutFloat1(this, "jointState", jointSize);
	}

	public void simRun() {
//		jointState.set(0);

//		System.out.println("sum " + sum);
		int i = 0;
		for (NslDinFloat1 state : states){
			for (int j = 0; j < state.getSize(); j++){
				jointState.set(i, state.get(j));
//				System.out.println(jointState.get(i));
				i++;
			}
		}
//		System.exit(1);
	}

	public int getSize() {
		return jointState.getSize();
	}
}
