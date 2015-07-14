package edu.usf.ratsim.nsl.modules;

import java.util.LinkedList;
import java.util.List;

import edu.usf.experiment.robot.LocalizableRobot;
import edu.usf.experiment.utils.Debug;
import edu.usf.ratsim.micronsl.Module;

public class ArtificialFeederCellLayer extends Module {

	public float[] activation;

	private LinkedList<ArtificialFeederCell> cells;

	private boolean active;

	private LocalizableRobot robot;

	public ArtificialFeederCellLayer(LocalizableRobot robot, int numFeeders,
			long seed) {
		active = true;

		// Compute number of cells
		cells = new LinkedList<ArtificialFeederCell>();

		for (int i = 0; i < numFeeders; i++)
			for (int j = 0; j < 1; j++)
				cells.add(new ArtificialFeederCell(i));

		activation = new float[cells.size()];

		this.robot = robot;
	}

	public void simRun() {
		boolean feederClose = robot.isFeederClose();
		if (feederClose) {
			simRun(feederClose, robot.getClosestFeeder().getId());
		} else
			simRun(feederClose, -1);
	}

	public float[] getActivationValues(boolean feederClose, int id) {
		float[] res = new float[cells.size()];

		for (int i = 0; i < cells.size(); i++) {
			res[i] = cells.get(i).getActivation(id);
		}

		return res;
	}

	public int getSize() {
		return cells.size();
	}

	public List<ArtificialFeederCell> getCells() {
		return cells;
	}

	public void deactivate() {
		active = false;
	}

	public void simRun(boolean feederClose, int id) {
		if (active && feederClose) {
			int i = 0;
			for (ArtificialFeederCell fCell : cells) {
				activation[i] = fCell.getActivation(id);
				if (Debug.printFeederCells)
					System.out.print(activation[i] + " ");
				i++;
			}
			if (Debug.printFeederCells)
				System.out.println();
		} else {
			for (int i = 0; i < activation.length; i++)
				activation[i] = 0;
		}
	}

}
