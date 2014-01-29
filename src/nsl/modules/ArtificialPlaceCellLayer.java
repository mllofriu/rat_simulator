package nsl.modules;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point3f;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import support.Configuration;
import experiment.ExperimentUniverse;

public class ArtificialPlaceCellLayer extends NslModule {

	public NslDoutFloat1 activation;

	private LinkedList<ArtificialPlaceCell> cells;

	private ExperimentUniverse universe;

	private float minY;
	private float minX;
	private float maxY;
	private float maxX;

	public ArtificialPlaceCellLayer(String nslName, NslModule nslParent,
			ExperimentUniverse universe, float radius, float minX, float minY) {
		super(nslName, nslParent);
		// Get some parameters from configuration
		
		this.minX = minX;
		this.minY = minY;
		maxX = Configuration.getFloat("ArtificialPlaceCells.maxX");
		maxY = Configuration.getFloat("ArtificialPlaceCells.maxY");

		// Compute number of cells
		cells = new LinkedList<ArtificialPlaceCell>();
		for (float x = minX; x < maxX; x += 2 * radius) {
			for (float y = minY; y < maxY; y += 2 * radius) {
				// Add a cell with center x,y
				cells.add(new ArtificialPlaceCell(new Point3f(x, 0, y),
						radius));
				// phased out layer
				cells.add(new ArtificialPlaceCell(new Point3f(x + radius, 0, y + radius),
						radius));
			}
		}

		activation = new NslDoutFloat1(this, "activation", cells.size());

		// Save the universe for later
		this.universe = universe;
	}

	@Override
	public void simRun() {
		int i = 0;
		for (ArtificialPlaceCell pCell : cells) {
			activation.set(i, pCell.getActivation(universe.getRobotPosition()));
			i++;
		}
	}
	
	public float[] getActivationValues(Point3f pos){
		float[] res = new float[cells.size()];
		
		for (int i = 0; i < cells.size(); i++) {
			res[i] = cells.get(i).getActivation(pos);
		}
		
		return res;
	}

	public int getSize() {
		return cells.size();
	}

	public List<Point3f> getDumpPoints() {
		float step = Configuration.getFloat("ArtificialPlaceCells.dumpPointsStep");
		
		List<Point3f> points = new LinkedList<Point3f>();
		for (float x = minX; x < maxX; x += step)
			for(float y = minY; y < maxY; y += step)
				points.add(new Point3f(x,0,y));
				
		return points;
	}

}
