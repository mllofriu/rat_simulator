package edu.usf.ratsim.experiment.task;

import java.awt.geom.Point2D;
import java.util.Map;

import javax.vecmath.Point4f;

import org.w3c.dom.Element;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;

public class PlaceRobotInitallyTask implements ExperimentTask {

	private static final String STR_INIT_POS = "initialPosition";
	private Point4f initPos;

	public PlaceRobotInitallyTask(Point4f initPos) {
		this.initPos = initPos;
	}

	public PlaceRobotInitallyTask(Element taskParams,
			Map<String, Point4f> points) {
		String initPosName = taskParams.getElementsByTagName(STR_INIT_POS)
				.item(0).getTextContent();
		initPos = points.get(initPosName);
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		univ.setRobotPosition(new Point2D.Float(initPos.x, initPos.z), 0);
		// TODO: implement orientation in xml
	}

}
