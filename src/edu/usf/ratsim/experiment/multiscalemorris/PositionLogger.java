package edu.usf.ratsim.experiment.multiscalemorris;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point3f;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ActionPerformerVote;

public class PositionLogger extends ExperimentLogger {

	private ActionPerformerVote actionPerformer;
	private List<Pose> poses;

	public PositionLogger(String logDir, ActionPerformerVote actionPerformer) {
		super(logDir);

		this.actionPerformer = actionPerformer;

		poses = new LinkedList<Pose>();
	}

	public void log(ExperimentUniverse universe) {
		Point3f pos = universe.getRobotPosition();
		// -Z coordinate corresponds to y
		poses.add(new Pose(pos.x, -pos.z, actionPerformer.wasLastActionRandom()));
	}

	public void finalizeLog() {
		PrintWriter writer = getWriter();
		writer.write("x\ty\trandom\n");
		for (Pose pose : poses)
			writer.write(pose.x + "\t" + pose.y + "\t" + pose.randomAction
					+ "\n");
		poses.clear();
	}
}

class Pose {
	public float x, y;
	public boolean randomAction;

	public Pose(float x, float y, boolean randomAction) {
		super();
		this.x = x;
		this.y = y;
		this.randomAction = randomAction;
	}
}