package edu.usf.ratsim.nsl.modules.qlearning.actionselection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Quat4f;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Utiles;

public class NoExploration extends NslModule {

	public NslDinFloat1[] votes;

	private IRobot robot;

	private ExperimentUniverse universe;

	public NoExploration(String nslName, NslModule nslParent, int numLayers,
			IRobot robot, ExperimentUniverse universe) {
		super(nslName, nslParent);

		this.robot = robot;
		this.universe = universe;

		votes = new NslDinFloat1[numLayers];
		for (int i = 0; i < numLayers; i++)
			votes[i] = new NslDinFloat1(this, "votes" + i);

	}

	public void simRun() {
		float[] overallValues = new float[Utiles.discreteAngles.length];
		for (int i = 0; i < overallValues.length; i++)
			overallValues[i] = 0;
		// Add each contribution
//		System.out.println("Values");
		for (NslDinFloat1 layerVal : votes){
			for (int angle = 0; angle < layerVal.getSize(); angle++){
//				System.out.print(layerVal.get(angle) + " ");
				overallValues[angle] += layerVal.get(angle);
			}
//			System.out.println();
		}
		
//		for (int angle = 0; angle < Utiles.discreteAngles.length; angle++)
//			System.out.print(overallValues[angle] + "\t");
//		System.out.println();
//		
		// find total value with laplacian
		float maxVal = Float.MIN_VALUE;
		for (int angle = 0; angle < overallValues.length; angle++)
			if (maxVal < overallValues[angle])
				maxVal = overallValues[angle];

		List<ActionValue> actions = new LinkedList<ActionValue>();
		for (int angle = 0; angle < overallValues.length; angle++) {
			// Get angle to that maximal direction
			Quat4f nextRot = Utiles.angleToRot(Utiles.discreteAngles[angle]);

			// Get the action that better approximates that angle
			int action = Utiles.bestActionToRot(nextRot,
					universe.getRobotOrientation());
			// Add a small bias towards going forward and small rotations
			// Radial function centered on the going forward angle
			float val = overallValues[angle];

			actions.add(new ActionValue(action, val));
		}

		int action;
		boolean[] aff;

		// Select best action
		Collections.sort(actions);
		do {
			action = actions.size() - 1;
	
			// Try the selected action
			robot.rotate(Utiles.actions[actions.get(action).getAction()]);
			aff = robot.getAffordances();
			actions.remove(action);
		} while (!aff[Utiles.discretizeAction(0)] && !actions.isEmpty());

		// Now it is safe to forward
//		if (!aff[Utiles.discretizeAction(0)]) {
//			if (Math.random() > .5)
//				robot.rotate((float) (Math.PI / 2));
//			else {
//				robot.rotate((float) (-Math.PI / 2));
//			}
//			aff = robot.getAffordances();
//		}
		aff = robot.getAffordances();
		if (aff[Utiles.discretizeAction(0)])
			robot.forward();
	}
}
