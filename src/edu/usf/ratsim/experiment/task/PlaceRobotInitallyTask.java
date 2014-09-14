package edu.usf.ratsim.experiment.task;

import java.awt.geom.Point2D;
import java.util.Map;

import javax.vecmath.Point4f;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.support.ElementWrapper;

public class PlaceRobotInitallyTask implements ExperimentTask {

	private static final String STR_INIT_POS = "initialPosition";
	private Point4f initPos;

	public PlaceRobotInitallyTask(Point4f initPos) {
		this.initPos = initPos;
	}

	public PlaceRobotInitallyTask(ElementWrapper taskParams,
			Map<String, Point4f> points) {
		String initPosName = taskParams.getChildText(STR_INIT_POS);
		initPos = points.get(initPosName);
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		univ.setRobotPosition(new Point2D.Float(initPos.x, initPos.z), (float) (-Math.PI/2));
		// TODO: implement orientation in xml
	}

}
