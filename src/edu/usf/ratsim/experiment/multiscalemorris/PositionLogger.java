package edu.usf.ratsim.experiment.multiscalemorris;

import java.io.File;
import java.io.FileNotFoundException;
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
	private static boolean logInitialized = false;

	public PositionLogger(String trialName, String subName, String repNum, ActionPerformerVote actionPerformer) {
		super();

		this.trialName = trialName;
		this.subName = subName;
		this.repNum = repNum;
		this.actionPerformer = actionPerformer;

		poses = new LinkedList<Pose>();
		
		initWriter();
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
				writer.write(trialName + '\t' + subName + '\t' + repNum +
						'\t' + pose.x + "\t" + pose.y + "\t" + pose.randomAction + "\n");
			poses.clear();
			writer.close();
		}
	}
	
	private static PrintWriter getWriter(){
		try {
			return new PrintWriter(Configuration.getString("Log.DIRECTORY") +
					new File(DUMP_FILENAME));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized void initWriter() {
		if (!logInitialized){
			PrintWriter writer = getWriter();
			writer.write("trial\tsubject\trepetition\tx\ty\trandom\n");
			writer.close();
			logInitialized = true;
		}
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