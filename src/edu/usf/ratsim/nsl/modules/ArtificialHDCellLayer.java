package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;

import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.utils.Debug;

public class ArtificialHDCellLayer extends NslModule {

	public NslDoutFloat1 activation;

	private LinkedList<ArtificialHDCell> cells;

	private LocalizableRobot robot;

	public ArtificialHDCellLayer(String nslName, NslModule nslParent, int numCells, LocalizableRobot robot) {
		super(nslName, nslParent);

		// Compute number of cells
		cells = new LinkedList<ArtificialHDCell>();
		float angleInterval = (float) (Math.PI * 2 / numCells);
		if (Debug.printHDCells)
			System.out.println("Adding " + numCells + " hd cells");
		for (int i = 0; i < numCells - 1; i++) {
			// Add a cell with center x,y
			cells.add(new ArtificialHDCell(i * angleInterval, angleInterval * 2));
			// Create one phased out half the angle interval
			cells.add(new ArtificialHDCell(i * angleInterval + angleInterval
					/ 2, angleInterval * 2));
		}

		activation = new NslDoutFloat1(this, "activation", cells.size());

		this.robot = robot;
	}

	public void simRun() {
		float angle = robot.getOrientationAngle();
		simRun(angle);
	}

	public int getSize() {
		return activation.getSize();
	}

	public void simRun(float theta) {
		int i = 0;
		
		for (ArtificialHDCell pCell : cells) {
			activation.set(i, pCell.getActivation(theta));
			// System.out.print(pCell.getActivation(angle) + " ");
			i++;
		}
		// System.out.println();
	}

}
