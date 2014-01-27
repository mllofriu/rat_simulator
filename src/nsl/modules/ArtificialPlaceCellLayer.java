package nsl.modules;

import java.util.LinkedList;

import javax.vecmath.Point3f;

import nslj.src.lang.NslDoutBoolean1;
import nslj.src.lang.NslModule;
import support.Configuration;
import experiment.ExpUniverseFactory;
import experiment.ExperimentUniverse;

public class ArtificialPlaceCellLayer extends NslModule {

	public NslDoutBoolean1 activation;

	private LinkedList<ArtificialPlaceCell> cells;

	private ExperimentUniverse universe;

	public ArtificialPlaceCellLayer(String nslName, NslModule nslParent,
			ExperimentUniverse universe) {
		super(nslName, nslParent);
		// Get some parameters from configuration
		int numLayers = Configuration.getInt("ArtificialPlaceCells.numLayers");
		float minRadius = Configuration
				.getFloat("ArtificialPlaceCells.minRadius");
		float maxRadius = Configuration
				.getFloat("ArtificialPlaceCells.maxRadius");
		float minX = Configuration.getFloat("ArtificialPlaceCells.minX");
		float maxX = Configuration.getFloat("ArtificialPlaceCells.maxX");
		float minY = Configuration.getFloat("ArtificialPlaceCells.minY");
		float maxY = Configuration.getFloat("ArtificialPlaceCells.maxY");

		// Compute number of cells
		cells = new LinkedList<ArtificialPlaceCell>();
		float radius = minRadius;
		for (int i = 1; i <= numLayers; i++) {
			for (float x = minX; x < maxX; x += 2 * radius) {
				for (float y = minY; y < maxY; y += 2 * radius) {
					// Add a cell with center x,y
					cells.add(new ArtificialPlaceCell(new Point3f(x, 0, y),
							radius));
					// Add a cell of the phase shifted layer
					cells.add(new ArtificialPlaceCell(new Point3f(x + radius,
							0, y + radius), radius));
				}
			}
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
		activation = new NslDoutBoolean1(this, cells.size());

		// Save the universe for later
		this.universe = universe;
	}

	@Override
	public void simRun() {
		int i = 0;
		for (ArtificialPlaceCell pCell : cells) {
			activation.set(i, pCell.isActive(universe.getRobotPosition()));
//			System.out.print(pCell.isActive(universe.getRobotPosition()) + " ");
			i++;
		}
//		System.out.println();
	}

}
