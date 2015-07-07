package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.util.GeometricShapeFactory;

import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.universe.Feeder;

public class ArtificialPlaceCellLayer extends NslModule {

	public NslDoutFloat1 activation;

	private LinkedList<ArtificialPlaceCell> cells;

	private boolean active;

	private LocalizableRobot robot;

	public ArtificialPlaceCellLayer(String nslName, NslModule nslParent,
			LocalizableRobot robot, float radius, int numCells, long seed,
			String placeCellType, float xmin, float ymin, float xmax,
			float ymax, List<Feeder> goals, float nearGoalProb) {
		super(nslName, nslParent);

		active = true;

		cells = new LinkedList<ArtificialPlaceCell>();
		Random r = new Random(seed);
		int i = 0;
		float x, y;
		do {
			if (placeCellType.equals("goalExponential") || placeCellType.equals("wallGoalExponential")) {
				if (r.nextFloat() < nearGoalProb){
					int fIndex = r.nextInt(goals.size());
					Point3f p = goals.get(fIndex).getPosition();
					x = (float) (p.x + r.nextFloat() * .2 - .1);
					y = (float) (p.y + r.nextFloat() * .2 - .1);
				} else {
					// TODO change them to have different centers among layers
					x = r.nextFloat() * (xmax - xmin) + xmin;
					y = r.nextFloat() * (ymax - ymin) + ymin;
				}
				
				if (placeCellType.equals("goalExponential")){
					cells.add(new ExponentialArtificialPlaceCell(new Point3f(x,
							y, 0), radius));
				} else {
					cells.add(new WallExponentialArtificialPlaceCell(new Point3f(x,
							y, 0), radius, r));
				}
			} else {
				x = r.nextFloat() * (xmax - xmin) + xmin;
				y = r.nextFloat() * (ymax - ymin) + ymin;
				// Find if it intersects any wall
				if (placeCellType.equals("proportional"))
					cells.add(new ProportionalArtificialPlaceCell(new Point3f(
							x, y, 0)));
				else if (placeCellType.equals("exponential"))
					cells.add(new ExponentialArtificialPlaceCell(new Point3f(x,
							y, 0), radius));
				else
					throw new RuntimeException(
							"Place cell type not implemented");
			}
			i++;
		} while (i < numCells);

		activation = new NslDoutFloat1(this, "activation", cells.size());

		this.robot = robot;
	}

	public void simRun() {
		simRun(robot.getPosition(), robot.isFeederClose());
	}

	public float[] getActivationValues(Point3f pos) {
		float distanceToClosestWall = robot.getDistanceToClosestWall();
		float[] res = new float[cells.size()];

		for (int i = 0; i < cells.size(); i++) {
			res[i] = cells.get(i).getActivation(pos, distanceToClosestWall);
		}

		return res;
	}

	public int getSize() {
		return cells.size();
	}

	public List<ArtificialPlaceCell> getCells() {
		return cells;
	}

	public void deactivate() {
		active = false;
	}
	
	public void simRun(Point3f pos, boolean isFeederClose) {
		simRun(pos, isFeederClose, robot.getDistanceToClosestWall());
	}

	public void simRun(Point3f pos, boolean isFeederClose, float distanceToClosestWall) {
		if (active) {
			int i = 0;
			for (ArtificialPlaceCell pCell : cells) {
				float val = pCell.getActivation(pos, distanceToClosestWall);
				if (val < 0 || val > 1)
					System.err.println("Activation less than 0 or greater than 1: " + val);
				activation.set(i, val);
				i++;
			}
		} else {
			activation.set(0);
		}
	}

}
