package edu.usf.ratsim.experiment.multiscalemorris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point3f;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ActionPerformerVote;
import edu.usf.ratsim.support.Configuration;

public class PositionLogger implements ExperimentLogger {

	private static final String DUMP_FILENAME = "position.txt";
	private ActionPerformerVote actionPerformer;
	private List<Pose> poses;
	private String repNum;
	private String subName;
	private String trialName;
	private String groupName;
	private static PrintWriter writer = null;

	public PositionLogger(String trialName, String groupName, String subName, String repNum,
			ActionPerformerVote actionPerformer) {
		super();

		this.trialName = trialName;
		this.subName = subName;
		this.groupName = groupName;
		this.repNum = repNum;
		this.actionPerformer = actionPerformer;

		poses = new LinkedList<Pose>();
	}

	public void log(ExperimentUniverse universe) {
		Point3f pos = universe.getRobotPosition();
		// -Z coordinate corresponds to y
		poses.add(new Pose(pos.x, -pos.z, actionPerformer.wasLastActionRandom()));
	}

	public void finalizeLog() {
		synchronized (PositionLogger.class) {
			PrintWriter writer = getWriter();
			for (Pose pose : poses)
				writer.println(trialName + '\t' + groupName + '\t' + subName + '\t' + repNum
						+ '\t' + pose.x + "\t" + pose.y + "\t"
						+ pose.randomAction);
			poses.clear();
		}
	}

	private static PrintWriter getWriter() {
		if (writer == null) {
			try {
				// Writer with auto flush
					writer = new PrintWriter(new OutputStreamWriter(
							new FileOutputStream(new File(Configuration
									.getString("Log.DIRECTORY") + DUMP_FILENAME))), true);
				writer.println("trial\tgroup\tsubject\trepetition\tx\ty\trandom");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return writer;
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