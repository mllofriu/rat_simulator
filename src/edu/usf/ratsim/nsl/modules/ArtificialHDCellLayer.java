package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;

import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.utils.Debug;
import edu.usf.ratsim.micronsl.Float1dPortArray;
import edu.usf.ratsim.micronsl.Module;

public class ArtificialHDCellLayer extends Module {

	public float activation[];

	private LinkedList<ArtificialHDCell> cells;

	private LocalizableRobot robot;

	public ArtificialHDCellLayer(String name, int numCells,
			LocalizableRobot robot) {
		super(name);
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

		activation = new float[cells.size()];
		addOutPort("activation", new Float1dPortArray(this, activation));

		this.robot = robot;
	}

	public void simRun() {
		float angle = robot.getOrientationAngle();
		simRun(angle);
	}

	public int getSize() {
		return activation.length;
	}

	public void simRun(float theta) {
		int i = 0;

		for (ArtificialHDCell pCell : cells) {
			activation[i] = pCell.getActivation(theta);
			// System.out.print(pCell.getActivation(angle) + " ");
			i++;
		}
		// System.out.println();
	}

}
