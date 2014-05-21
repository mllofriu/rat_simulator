package edu.usf.ratsim.experiment.task;

import java.util.Random;

import javax.vecmath.Point2f;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineSegment;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.support.ElementWrapper;

public class PlaceRandomWallsTask implements ExperimentTask {

	private final float OBSTACLE_ZONE_RADIUS = .35f;

	private static final String STR_NUMWALLS = "number";
	private static final String STR_WALLLENGTH = "length";
	private int numWalls;
	private float length;

	public PlaceRandomWallsTask(ElementWrapper taskParams) {
		numWalls = taskParams.getChildInt(STR_NUMWALLS);
		length = taskParams.getChildFloat(STR_WALLLENGTH);
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		Random random = new Random();

		Point2f x1, x2;
		for (int i = 0; i < numWalls; i++) {
			
			LineSegment wall;
			do {
				// Create the first point random
				x1 = new Point2f();
				x1.x = random.nextFloat() * 2 * (OBSTACLE_ZONE_RADIUS - length)
						- (OBSTACLE_ZONE_RADIUS - length);
				x1.y = random.nextFloat() * 2 * (OBSTACLE_ZONE_RADIUS - length)
						- (OBSTACLE_ZONE_RADIUS - length);
				// Deside on orientation
				float orientation = (float) (random.nextFloat() * Math.PI);
				// Translation of x1 acording to orientation
				Point2f translation = new Point2f();
				translation.x = (float) (length * Math.cos(orientation));
				translation.y = (float) (length * Math.sin(orientation));
				// Transport x1
				translation.add(x1);
				x2 = translation;

				wall = new LineSegment(new Coordinate(x1.x, x1.y),
						new Coordinate(x2.x, x2.y));

//			} while (univ.wallIntersectsOtherWalls(wall));
			} while (univ.shortestDistanceToWalls(wall) < length);
			
			univ.addWall(x1.x, x1.y, x2.x, x2.y);
		}

	}
}
