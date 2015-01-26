package edu.usf.ratsim.nsl.modules;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point3f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;

public class ArtificialPlaceCellLayerWithIntention extends NslModule {

	public NslDoutFloat1 activation;
	public NslDinInt0 goalFeeder;

	private List<List<ArtificialPlaceCell>> intentionCells;

	private ExperimentUniverse universe;

	public ArtificialPlaceCellLayerWithIntention(String nslName,
			NslModule nslParent, ExperimentUniverse universe,
			int numIntentions, float radius) {
		super(nslName, nslParent);
		// Get some parameters from configuration
		Rectangle2D.Float rect = universe.getBoundingRectangle();
		float maxX = (float) rect.getMaxX();
		float maxY = (float) rect.getMaxY();
		float minX = (float) rect.getMinX();
		float minY = (float) rect.getMinY();
		
		goalFeeder = new NslDinInt0(this, "goalFeeder");

		// Compute number of cells
		intentionCells = new LinkedList<List<ArtificialPlaceCell>>();
		LinkedList<ArtificialPlaceCell> cells;
		for (int i = 0; i < numIntentions; i++) {
			cells = new LinkedList<ArtificialPlaceCell>();
			for (float x = minX; x < maxX; x += 2 * radius) {
				for (float y = minY; y < maxY; y += 2 * radius) {
					// Add a cell with center x,y
					cells.add(new ArtificialPlaceCell(new Point3f(x, y,0),
							radius));
					// phased out layer
					cells.add(new ArtificialPlaceCell(new Point3f(x + radius,
							y + radius, 0), radius));
				}
			}
			intentionCells.add(cells);
		}

		activation = new NslDoutFloat1(this, "activation", intentionCells.get(0).size() * numIntentions);

		// Save the universe for later
		this.universe = universe;
	}

	public void simRun() {
		int intention = goalFeeder.get();
		int i = 0;
		
		activation.set(0);
		
		// Intention == -1 is no intention
		if (intention != -1){
			int offset = intentionCells.get(0).size() * intention;
			for (ArtificialPlaceCell pCell : intentionCells.get(intention)) {
				activation.set(offset + i, pCell.getActivation(universe.getRobotPosition()));
				i++;
			}
		}
	}

	public float[] getActivationValues(Point3f pos, int intention) {
		float[] res = new float[intentionCells.get(intention).size()];

		for (int i = 0; i < intentionCells.get(intention).size(); i++) {
			res[i] = intentionCells.get(intention).get(i).getActivation(pos);
		}

		return res;
	}

	public int getSize() {
		return intentionCells.size() * intentionCells.get(0).size();
	}

	public List<ArtificialPlaceCell> getCells(int intention) {
		return intentionCells.get(intention);
	}

}
