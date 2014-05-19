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
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.support.Configuration;

public class WantedFeederLogger implements ExperimentLogger {

	private static final String DUMP_FILENAME = "wantedFeeder.txt";
	private List<Integer> wantedFeeders;
	private String repNum;
	private String subName;
	private String trialName;
	private String groupName;
	private static PrintWriter writer = null;

	public WantedFeederLogger(String trialName, String groupName, String subName,
			String repNum) {
		super();

		this.trialName = trialName;
		this.subName = subName;
		this.groupName = groupName;
		this.repNum = repNum;

		wantedFeeders = new LinkedList<Integer>();
	}

	public void log(ExperimentUniverse universe) {
		Point3f pos = universe.getRobotPosition();
		// -Z coordinate corresponds to y
		wantedFeeders.add(universe.getWantedFeeder());//actionPerformer.wasLastActionRandom()));
	}

	public void finalizeLog() {
		synchronized (WantedFeederLogger.class) {
			PrintWriter writer = getWriter();
			for (Integer wantedFeeder : wantedFeeders)
				writer.println(trialName + '\t' + groupName + '\t' + subName
						+ '\t' + repNum + '\t' + wantedFeeder);
			wantedFeeders.clear();
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
				writer.println("trial\tgroup\tsubject\trepetition\twantedFeeder");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return writer;
	}

}
