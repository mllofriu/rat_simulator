package edu.usf.ratsim.experiment.loggers;

public class Pose {
	public float x, y;
	public boolean randomAction;

	public Pose(float x, float y, boolean randomAction) {
		super();
		this.x = x;
		this.y = y;
		this.randomAction = randomAction;
	}
}
