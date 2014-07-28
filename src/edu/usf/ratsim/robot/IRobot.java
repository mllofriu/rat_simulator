package edu.usf.ratsim.robot;

import java.awt.image.BufferedImage;

/**
 * 
 */

/**
 * @author gtejera
 * 
 */
public interface IRobot {

	/**
	 * Number of possible actions the robot can perform
	 */
	public static int NUM_POSSIBLE_ACTIONS = 8;

	/**
	 * Makes the robot perform an action.
	 * 
	 * @param degrees
	 *            If degrees == 0, the robot goes forward. Else, it turns the
	 *            amount number of degrees. Negative degrees represent left
	 *            turns.
	 */
	public abstract void rotate(float degrees);
	
	/**
	 * Makes the robot eat food
	 */
	public abstract void eat();

	/**
	 * Returns possible actions to perform
	 * 
	 * @return An array with true in the directions the robot can navigate
	 */
	public abstract boolean[] getAffordances();

	/**
	 * Return whether the robot has found food in the environment
	 * 
	 * @return
	 */
	public abstract boolean hasFoundFood();

	/**
	 * Method invocked at the beginning of each session
	 */
	public abstract void startRobot();

	/**
	 * Visualization purposes only
	 * 
	 * @return
	 */
	public abstract BufferedImage[] getPanoramica();

	public abstract void forward();

	public abstract boolean[] getAffordances(int wallLookahead);

}
