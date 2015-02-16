package edu.usf.ratsim.nsl.modules;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.util.GeometricShapeFactory;

import edu.usf.ratsim.experiment.ExperimentUniverse;

public class ArtificialPlaceCellLayer extends NslModule {

	public NslDoutFloat1 activation;

	private LinkedList<ArtificialPlaceCell> cells;

	private ExperimentUniverse universe;

	private boolean active;

	public ArtificialPlaceCellLayer(String nslName, NslModule nslParent,
			ExperimentUniverse universe, float radius, int numCells, long seed, String placeCellType) {
		super(nslName, nslParent);

		active = true;

		// Get some parameters from configuration
		Rectangle2D.Float rect = universe.getBoundingRectangle();
		float maxX = (float) rect.getMaxX();
		float maxY = (float) rect.getMaxY();
		float minX = (float) rect.getMinX();
		float minY = (float) rect.getMinY();

		// Compute number of cells
		cells = new LinkedList<ArtificialPlaceCell>();
//		for (float x = minX; x < maxX; x += 2 * radius) {
//			for (float y = minY; y < maxY; y += 2 * radius) {
//				// Add a cell with center x,y
//				cells.add(new ArtificialPlaceCell(new Point3f(x, 0, y), radius));
//				// phased out layer
//				cells.add(new ArtificialPlaceCell(new Point3f(x + radius, 0, y
//						+ radius), radius));
//			}
//		}
		GeometryFactory gf = new GeometryFactory();
		
		Random r = new Random(seed);
		int i = 0;
		do{
			float x = r.nextFloat() * (maxX - minX) + minX;
			float y = r.nextFloat() * (maxY - minY) + minY;
			// Find if it intersects any wall
			GeometricShapeFactory gsf = new GeometricShapeFactory();
			gsf.setCentre(new Coordinate(x,y));
			gsf.setSize(2*radius);
//			System.out.println("PC " + x + " " + y + " " + universe.placeIntersectsWalls(gsf.createCircle()));
			if (!universe.placeIntersectsWalls(gsf.createCircle())){
				if (placeCellType.equals("proportional"))
					cells.add(new ProportionalArtificialPlaceCell(new Point3f(x, y, 0)));
				else if (placeCellType.equals("exponential"))
					cells.add(new ExponentialArtificialPlaceCell(new Point3f(x, y, 0), radius));
				else 
					throw new RuntimeException("Place cell type not implemented");
				i++;
			}
			
		} while (i < numCells);

		activation = new NslDoutFloat1(this, "activation", cells.size());

		// Save the universe for later
		this.universe = universe;
	}

	public void simRun() {
		if (active) {
			int i = 0;
			for (ArtificialPlaceCell pCell : cells) {
				activation.set(i,
						pCell.getActivation(universe.getRobotPosition()));
				i++;
			}
		} else {
			activation.set(0);
		}
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

}
