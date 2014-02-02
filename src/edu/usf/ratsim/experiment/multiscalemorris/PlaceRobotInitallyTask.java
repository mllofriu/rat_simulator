package edu.usf.ratsim.experiment.multiscalemorris;

import java.awt.geom.Point2D;

import javax.vecmath.Point4f;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;

public class PlaceRobotInitallyTask implements ExperimentTask {

	private Point4f initPos;

	public PlaceRobotInitallyTask(Point4f initPos) {
		this.initPos = initPos;
	}

	@Override
	public void perform(ExperimentUniverse univ) {
		univ.setRobotPosition(new Point2D.Float(initPos.x, initPos.z));
		// TODO: implement orientation
	}

}
