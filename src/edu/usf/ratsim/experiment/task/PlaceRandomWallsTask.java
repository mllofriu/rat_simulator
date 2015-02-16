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

	private final float OBSTACLE_ZONE_RADIUS = .5f;

	private static final String STR_NUMWALLS = "number";
	private static final String STR_WALLLENGTH = "length";

	private static final float MAX_LENGHT_BEFORE_BREAK = .3f;

	private static final float MIN_DIST_BETWEEN_WALLS = .05f;

	private static final float MIN_DIST_TO_FEEDERS = 0.1f;

	private int numWalls;
	private float length;

	public PlaceRandomWallsTask(ElementWrapper taskParams) {
		numWalls = taskParams.getChildInt(STR_NUMWALLS);
		length = taskParams.getChildFloat(STR_WALLLENGTH);
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		Random random = new Random();

		for (int i = 0; i < numWalls; i++) {
			Point2f x1, x2, x3;
			if (length > MAX_LENGHT_BEFORE_BREAK) {
				LineSegment wall, wall2;

				do {

					// If the obstacle is too big, break it in two

					x1 = new Point2f();
					x1.x = random.nextFloat() * 2 * (OBSTACLE_ZONE_RADIUS)
							- (OBSTACLE_ZONE_RADIUS);
					x1.y = random.nextFloat() * 2 * (OBSTACLE_ZONE_RADIUS)
							- (OBSTACLE_ZONE_RADIUS);
					float orientation = (float) (random.nextFloat() * Math.PI);

					Point2f translation = new Point2f();
					translation.x = (float) (length / 2 * Math.cos(orientation));
					translation.y = (float) (length / 2 * Math.sin(orientation));

					x2 = new Point2f(x1);
					x2.add(translation);
					wall = new LineSegment(new Coordinate(x1.x, x1.y),
							new Coordinate(x2.x, x2.y));

					float breakAngle;
					if (random.nextFloat() > .5)
						breakAngle = (float) (Math.PI / 3);
					else
						breakAngle = (float) (Math.PI / 4);
					if (random.nextFloat() > .5)
						breakAngle = -breakAngle;
					
					x3 = new Point2f(x2);
					translation.x = (float) (length / 2 * Math.cos(orientation
							+ breakAngle));
					translation.y = (float) (length / 2 * Math.sin(orientation
							+ breakAngle));
					x3.add(translation);
					wall2 = new LineSegment(new Coordinate(x2.x, x2.y),
							new Coordinate(x3.x, x3.y));

				} while (!univ.wallInsidePool(wall)
						|| !univ.wallInsidePool(wall2)
						|| univ.shortestDistanceToWalls(wall) < MIN_DIST_BETWEEN_WALLS
						|| univ.shortestDistanceToWalls(wall2) < MIN_DIST_BETWEEN_WALLS
						|| univ.wallDistanceToFeeders(wall) < MIN_DIST_TO_FEEDERS
						|| univ.wallDistanceToFeeders(wall2) < MIN_DIST_TO_FEEDERS);

				univ.addWall(x1.x, x1.y, x2.x, x2.y);

				univ.addWall(x2.x, x2.y, x3.x, x3.y);
			} else {

				LineSegment wall = null;
				do {
					// Create the first point random
					x1 = new Point2f();
					x1.x = random.nextFloat() * 2 * (OBSTACLE_ZONE_RADIUS)
							- (OBSTACLE_ZONE_RADIUS);
					x1.y = random.nextFloat() * 2 * (OBSTACLE_ZONE_RADIUS)
							- (OBSTACLE_ZONE_RADIUS);
					// Deside on orientation
					float orientation = (float) (random.nextFloat() * Math.PI);
					// Translation of x1 acording to orientation
					Point2f translation = new Point2f();
					// If the obstacle is too big, break it in two

					translation.x = (float) (length * Math.cos(orientation));
					translation.y = (float) (length * Math.sin(orientation));
					translation.add(x1);
					x2 = translation;
					wall = new LineSegment(new Coordinate(x1.x, x1.y),
							new Coordinate(x2.x, x2.y));

					// } while (univ.wallIntersectsOtherWalls(wall));
				} while (!univ.wallInsidePool(wall)
						|| univ.shortestDistanceToWalls(wall) < MIN_DIST_BETWEEN_WALLS
						|| univ.wallDistanceToFeeders(wall) < MIN_DIST_TO_FEEDERS);

				univ.addWall(x1.x, x1.y, x2.x, x2.y);

			}
		}

	}
}
