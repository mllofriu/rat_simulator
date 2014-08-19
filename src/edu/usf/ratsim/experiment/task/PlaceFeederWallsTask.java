package edu.usf.ratsim.experiment.task;

import javax.vecmath.Point2f;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.support.ElementWrapper;

public class PlaceFeederWallsTask implements ExperimentTask {

	public PlaceFeederWallsTask(ElementWrapper taskParams) {
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		float radius = .5f;
		float len = .15f;
		for (int i = 0; i < 8; i++) {
			float angle = (float) (Math.PI * 2 / 8 * i + Math.PI * 2 / 16);
			Point2f circlePoint = new Point2f((float) Math.cos(angle) * radius,
					(float) Math.sin(angle) * radius);
			Point2f innerPoint = new Point2f((float) Math.cos(angle) * (radius - len),
					(float) Math.sin(angle) * (radius-len));
			univ.addWall(circlePoint.x, circlePoint.y, innerPoint.x, innerPoint.y);
		}

	}

}
