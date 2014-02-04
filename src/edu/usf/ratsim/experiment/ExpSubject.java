package edu.usf.ratsim.experiment;

import edu.usf.ratsim.robot.IRobot;

public interface ExpSubject {

	/**
	 * Advances one cycle in the internal model of the brain usually resulting in a decision being taken
	 */
	public void stepCycle();
	
	/**
	 * Returns the name of the subject
	 * @return
	 */
	public String getName();
	
	public ExperimentUniverse getUniverse();
	
	public IRobot getRobot();
}
