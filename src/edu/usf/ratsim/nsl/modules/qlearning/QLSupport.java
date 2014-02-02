package edu.usf.ratsim.nsl.modules.qlearning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;

import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.support.Utiles;

public class QLSupport {

	private static final Float INITIAL_VALUE = 0f;
	private static final String DUMP_FILENAME = "policy.txt";
	
	private HashMap<StateAction, Float> value;

	private LinkedList<StateAction> visitedStateActions;


	public QLSupport(int valueSize){
		value = new HashMap<StateAction, Float>(valueSize);
		for (int s = 0; s < valueSize; s++)
			for (int a = 0; a < Utiles.discreteAngles.length; a++)
				value.put(new StateAction(s, a), INITIAL_VALUE);
		
		visitedStateActions = new LinkedList<StateAction>();
	}
	
	public float getValue(StateAction sa){
		return value.get(sa);
	}
	
	public void setValue(StateAction sa, float newVal){
		value.put(sa, newVal);
	}
	
	public void recordStateAction(StateAction sa){
		// Add to the front to reverse update later
		visitedStateActions.add(0, sa);
	}
	
	public StateAction getVisitedSA(int index){
		return visitedStateActions.get(index);
	}
	
	public void dumpPolicy(String logDir, ArtificialPlaceCellLayer pcl){
		File f = new File(logDir + DUMP_FILENAME);
		PrintWriter writer;
		try {
			writer = new PrintWriter(f);
			
			writer.println("x\ty\tangle");
			List<Point3f> points = pcl.getDumpPoints();
			for(Point3f p : points){
				float[] states = pcl.getActivationValues(p);
				
				// Get the active state
				float maxVal = 0;
				int activeState = -1;
				for (int i = 0; i < states.length; i++)
					if (states[i] > maxVal) {
						activeState = i;
						maxVal = states[i];
					}
				
				// Get the policy angle for this state
				int angle = getMaxAngle(activeState);
				
				// Write to file
				String policyAngle;
				if (angle == -1)
					policyAngle = "NA";
				else 
					policyAngle = new Float(Utiles.discreteAngles[angle]).toString();
				
				writer.println(p.x + "\t" + (-p.z) + "\t" + policyAngle);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getMaxAngle(int s) {
		float[] vals = new float[Utiles.discreteAngles.length];
		int maxAngle = -1;
		float maxVal = 0;
		for (int angle = 0; angle < Utiles.discreteAngles.length; angle++) {
			vals[angle] = getValue(new StateAction(s, angle));
			if (vals[angle] > maxVal){
				maxVal = vals[angle];
				maxAngle = angle;
			}
		}
		return maxAngle;
	}

	public int numVisitedSA() {
		return visitedStateActions.size();
	}

	public void clearRecord() {
		visitedStateActions.clear();
	}
}
