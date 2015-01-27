package edu.usf.ratsim.experiment.loggers;

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
import edu.usf.ratsim.robot.naorobot.protobuf.VisionListener;
import edu.usf.ratsim.support.Configuration;
 
public class SSLPositionLogger implements ExperimentLogger {

	private static final String DUMP_FILENAME = "sslposition.txt";
	private List<Pose> poses;
	private String repNum;
	private String subName;
	private String trialName;
	private String groupName;
	private VisionListener vision;
	private static PrintWriter writer = null;

	public SSLPositionLogger(String trialName, String groupName, String subName,
			String repNum) {
		super();

		this.trialName = trialName;
		this.subName = subName;
		this.groupName = groupName;
		this.repNum = repNum;

		poses = new LinkedList<Pose>();
		
		vision = new VisionListener();
	}

	public void log(ExperimentUniverse universe) {
		Point3f pos = vision.getRobotPoint();
		poses.add(new Pose(pos.x, pos.y, false));
	}

	public void finalizeLog() {
		synchronized (SSLPositionLogger.class) {
			PrintWriter writer = getWriter();
			for (Pose pose : poses)
				writer.println(trialName + '\t' + groupName + '\t' + subName
						+ '\t' + repNum + '\t' + pose.x + "\t" + pose.y + "\t"
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
								.getString("Log.DIRECTORY") + DUMP_FILENAME))),
						true);
				writer.println("trial\tgroup\tsubject\trepetition\tx\ty\trandom");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return writer;
	}

}
