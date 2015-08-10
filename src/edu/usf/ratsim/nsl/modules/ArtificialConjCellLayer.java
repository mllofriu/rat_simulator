package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;

import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.universe.Feeder;
import edu.usf.experiment.utils.RandomSingleton;
import edu.usf.ratsim.micronsl.Float1dPort;
import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.Module;

public class ArtificialConjCellLayer extends Module {

	public float[] activation;

	private List<ExponentialConjCell> cells;

	private boolean active;

	private LocalizableRobot robot;

	public ArtificialConjCellLayer(String name, LocalizableRobot robot,
			float placeRadius, float minDirectionRadius,
			float maxDirectionRadius, int numIntentions, int numCells,
			String placeCellType, float xmin, float ymin, float xmax,
			float ymax, List<Feeder> goals, float nearGoalProb) {
		super(name);

		active = true;

		cells = new LinkedList<ExponentialConjCell>();
		Random r = RandomSingleton.getInstance();
		int i = 0;
		float x, y;
		do {
			if (placeCellType.equals("goalExponential")) {
				// || placeCellType.equals("wallGoalExponential")) {
				if (r.nextFloat() < nearGoalProb) {
					int fIndex = r.nextInt(goals.size());
					Point3f p = goals.get(fIndex).getPosition();
					x = (float) (p.x + r.nextFloat() * .2 - .1);
					y = (float) (p.y + r.nextFloat() * .2 - .1);
				} else {
					// TODO change them to have different centers among layers
					x = r.nextFloat() * (xmax - xmin) + xmin;
					y = r.nextFloat() * (ymax - ymin) + ymin;
				}
				float preferredDirection = (float) (r.nextFloat() * Math.PI * 2);
				// float directionRadius = r.nextFloat()
				// * (maxDirectionRadius - minDirectionRadius)
				// + minDirectionRadius;
				// Using Inverse transform sampling to sample from k/x between
				// min and max
				// https://en.wikipedia.org/wiki/Inverse_transform_sampling. k =
				// 1/(ln (max) - ln(min)) due to normalization
				float k = (float) (1 / (Math.log(maxDirectionRadius) - Math
						.log(minDirectionRadius)));
				float s = r.nextFloat();
				float directionRadius = (float) Math.exp(s / k
						+ Math.log(minDirectionRadius));

				int preferredIntention = r.nextInt(numIntentions);
				if (placeCellType.equals("goalExponential")) {
					cells.add(new ExponentialConjCell(new Point3f(x, y, 0),
							preferredDirection, placeRadius, directionRadius,
							preferredIntention));
				}
			} else {
				System.err.println("Place cell type not implemented");
				System.exit(1);
			}
			// else {
			// cells.add(new WallExponentialArtificialPlaceCell(
			// new Point3f(x, y, 0), placeRadius, r));
			// }
			// } else {
			// x = r.nextFloat() * (xmax - xmin) + xmin;
			// y = r.nextFloat() * (ymax - ymin) + ymin;
			// // Find if it intersects any wall
			// if (placeCellType.equals("proportional"))
			// cells.add(new ProportionalArtificialPlaceCell(new Point3f(
			// x, y, 0)));
			// else if (placeCellType.equals("exponential"))
			// cells.add(new ExponentialArtificialPlaceCell(new Point3f(x,
			// y, 0), placeRadius));
			// else
			// throw new RuntimeException(
			// "Place cell type not implemented");
			// }
			i++;
		} while (i < numCells);

		activation = new float[cells.size()];
		addOutPort("activation", new Float1dPortArray(this, activation));

		this.robot = robot;
	}

	public void run() {
		// Find the intention
		Float1dPort intention = (Float1dPort) getInPort("intention");
		int intentionNum = -1;
		int i = 0;
		while (intentionNum == -1 && i < intention.getSize()) {
			if (intention.get(i) == 1)
				intentionNum = i;
			i++;
		}

		simRun(robot.getPosition(), robot.getOrientationAngle(), intentionNum,
				robot.isFeederClose());
	}

	public float[] getActivationValues(Point3f pos, float angle, int intention) {
		float distanceToClosestWall = robot.getDistanceToClosestWall();
		float[] res = new float[cells.size()];

		for (int i = 0; i < cells.size(); i++) {
			res[i] = cells.get(i).getActivation(pos, angle, intention,
					distanceToClosestWall);
		}

		return res;
	}

	public int getSize() {
		return cells.size();
	}

	public List<ExponentialConjCell> getCells() {
		return cells;
	}

	public void deactivate() {
		active = false;
	}

	public void simRun(Point3f pos, float direction, int intention,
			boolean isFeederClose) {
		simRun(pos, direction, intention, isFeederClose,
				robot.getDistanceToClosestWall());
	}

	public void simRun(Point3f pos, float direction, int intention,
			boolean isFeederClose, float distanceToClosestWall) {
		if (active) {
			int i = 0;
			float total = 0;
			for (ExponentialConjCell pCell : cells) {
				float val = pCell.getActivation(pos, direction, intention,
						distanceToClosestWall);
				// if (val < 0 || val > 1)
				// System.err
				// .println("Activation less than 0 or greater than 1: "
				// + val);
				activation[i] = val;
				total += val;
				i++;
			}

			if (Float.isNaN(total))
				System.out.println("Numeric error");

			// if (total != 0)
			// for (i = 0; i < activation.length; i++)
			// activation[i] = activation[i] / total * layerEnergy ;
		} else {
			for (int i = 0; i < activation.length; i++)
				activation[i] = 0;
		}
	}

	@Override
	public boolean usesRandom() {
		return false;
	}

	public void clear() {
		((Float1dPortArray) getOutPort("activation")).set(0);
	}

}