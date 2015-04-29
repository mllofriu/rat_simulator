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

public class ArtificialPlaceCellLayer extends NslModule {

	public NslDoutFloat1 activation;

	private LinkedList<ArtificialPlaceCell> cells;

	private boolean active;

	private LocalizableRobot robot;

	public ArtificialPlaceCellLayer(String nslName, NslModule nslParent,
			LocalizableRobot robot, float radius, int numCells, long seed,
			String placeCellType, float xmin, float ymin, float xmax, float ymax) {
		super(nslName, nslParent);

		active = true;

		// Compute number of cells
		cells = new LinkedList<ArtificialPlaceCell>();
		// for (float x = minX; x < maxX; x += 2 * radius) {
		// for (float y = minY; y < maxY; y += 2 * radius) {
		// // Add a cell with center x,y
		// cells.add(new ArtificialPlaceCell(new Point3f(x, 0, y), radius));
		// // phased out layer
		// cells.add(new ArtificialPlaceCell(new Point3f(x + radius, 0, y
		// + radius), radius));
		// }
		// }
		GeometryFactory gf = new GeometryFactory();

		Random r = new Random(seed);
		int i = 0;
		do {
			float x = r.nextFloat() * (xmax - xmin) + xmin;
			float y = r.nextFloat() * (ymax - ymin) + ymin;
			// Find if it intersects any wall
			GeometricShapeFactory gsf = new GeometricShapeFactory();
			gsf.setCentre(new Coordinate(x, y));
			gsf.setSize(2 * radius);
			// System.out.println("PC " + x + " " + y + " " +
			// universe.placeIntersectsWalls(gsf.createCircle()));
			// TODO: restore wall intersect problem
			// if (!universe.placeIntersectsWalls(gsf.createCircle())){
			if (placeCellType.equals("proportional"))
				cells.add(new ProportionalArtificialPlaceCell(new Point3f(x, y,
						0)));
			else if (placeCellType.equals("exponential"))
				cells.add(new ExponentialArtificialPlaceCell(new Point3f(x, y,
						0), radius));
			else
				throw new RuntimeException("Place cell type not implemented");
			i++;
			// }

		} while (i < numCells);

		activation = new NslDoutFloat1(this, "activation", cells.size());

		this.robot = robot;
	}

	public void simRun() {
		simRun(robot.getPosition(), robot.isFeederClose());
	}

	public float[] getActivationValues(Point3f pos) {
		float[] res = new float[cells.size()];

		for (int i = 0; i < cells.size(); i++) {
			res[i] = cells.get(i).getActivation(pos);
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
//		if (active && !isFeederClose) {
		if (active){
			int i = 0;
			for (ArtificialPlaceCell pCell : cells) {
				activation.set(i, pCell.getActivation(pos));
				i++;
			}
		} else {
			activation.set(0);
		}
	}

}
