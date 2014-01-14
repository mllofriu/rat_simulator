package experiment.multiscalemorris;

import robot.IRobot;
import experiment.StopCondition;

public class FoundFoodStopCond implements StopCondition {

	private IRobot robot;

	public FoundFoodStopCond(IRobot robot){
		this.robot = robot;
	}
	
	@Override
	public boolean experimentFinished() {
		return robot.hasFoundFood();
	}

}
