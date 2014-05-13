package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;

public class ArtificialHDCellLayer extends NslModule {

	public NslDoutFloat1 activation;

	private LinkedList<ArtificialHDCell> cells;

	private ExperimentUniverse universe;

	public ArtificialHDCellLayer(String nslName, NslModule nslParent,
			ExperimentUniverse universe, int numCells) {
		super(nslName, nslParent);

		// Compute number of cells
		cells = new LinkedList<ArtificialHDCell>();
		float angleInterval = (float) (Math.PI * 2 / numCells);
		for (int i = 0; i < numCells - 1; i++) {
			// Add a cell with center x,y
			cells.add(new ArtificialHDCell(i * angleInterval,angleInterval*2));
		}

		activation = new NslDoutFloat1(this, "activation", cells.size());

		// Save the universe for later
		this.universe = universe;
	}

	public void simRun() {
		int i = 0;
		float angle = universe.getRobotOrientationAngle();
		for (ArtificialHDCell pCell : cells) {
			activation.set(i,
					pCell.getActivation(angle));
//			System.out.print(pCell.getActivation(angle) + " ");
			i++;
		}
//		System.out.println();
	}

	public int getSize() {
		return activation.getSize();
	}

}
