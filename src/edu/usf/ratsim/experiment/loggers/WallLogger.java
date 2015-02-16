package edu.usf.ratsim.experiment.loggers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.virtual.WallNode;
import edu.usf.ratsim.support.Configuration;

public class WallLogger implements ExperimentLogger {

	private static final String DUMP_FILENAME = "walls.txt";
	private String repNum;
	private String subName;
	private String trialName;
	private String groupName;
	private List<WallNode> walls;
	private static PrintWriter writer = null;

	public WallLogger(String trialName, String groupName, String subName,
			String repNum) {
		super();

		this.trialName = trialName;
		this.subName = subName;
		this.groupName = groupName;
		this.repNum = repNum;

		walls = new LinkedList<WallNode>();
	}

	public void log(ExperimentUniverse universe) {
		for (WallNode wNode : universe.getWalls())
			walls.add(wNode);
	}

	public void finalizeLog() {

		synchronized (WallLogger.class) {
			PrintWriter writer = getWriter();
			for (WallNode wNode : walls)
				writer.println(trialName + '\t' + groupName + '\t' + subName
						+ '\t' + repNum + '\t' + wNode.x1 + "\t" + wNode.y1
						+ '\t' + wNode.x2 + "\t" + wNode.y2);

			walls.clear();

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
				writer.println("trial\tgroup\tsubject\trepetition\tx\ty\txend\tyend");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return writer;
	}

}
