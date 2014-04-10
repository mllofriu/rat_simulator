package edu.usf.ratsim.experiment.task;

import java.util.Map;

import javax.vecmath.Point3f;
import javax.vecmath.Point4f;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.support.ElementWrapper;

public class PlaceWallTask implements ExperimentTask {

	private static final String STR_X1 = "x1";
	private static final String STR_X2 = "x2";
	private Point4f x2;
	private Point4f x1;

	public PlaceWallTask(Point3f x1, Point3f x2) {
		this.x1 = new Point4f(x1.x, x1.y, x1.z, 0);
		this.x2 = new Point4f(x2.x, x2.y, x2.z, 0);
	}

	public PlaceWallTask(ElementWrapper taskParams,
			Map<String, Point4f> points) {
		x1 = points.get(taskParams.getChildText(STR_X1));
		x2 = points.get(taskParams.getChildText(STR_X2));
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		univ.addWall(x1.x, x1.z, x2.x, x2.z);
	}

}
