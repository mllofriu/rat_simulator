package edu.usf.ratsim.experiment;

/*
 * Simulation.java
 * Este modulo levanta la configuracion completa del experimento a realizar.
 * Casos de habitucación, entrenamiento, prueba, parametros de configuracion del modelo, etc 
 * Autor: Gonzalo Tejera
 * Fecha: 11 de agosto de 2010
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Point4f;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.robot.RobotFactory;
import edu.usf.ratsim.robot.virtual.VirtualExpUniverse;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.ElementWrapper;
import edu.usf.ratsim.support.XMLDocReader;

public class Experiment implements Runnable {

	public static final String STR_NAME = "name";
	private final String STR_POINT = "point";
	public static final String STR_REPETITIONS = "reps";
	private static final String STR_SUBJECT = "subject";
	private static final String STR_GROUP = "group";
	private static final String STR_TRIALGROUPS = "groups";
	private static final String STR_MAZE = "maze";

	private final String STR_TRIAL = "trial";

	public final static String STR_TRIAL_TYPE = "type";
	private static final Object XML_ROOT_STR = "experiment";

	private final String STR_X_POSITION = "xp";
	private final String STR_Y_POSITION = "yp";
	private final String STR_Z_POSITION = "zp";
	private final String STR_ANGLE = "rot";

	private static final String PLOTTING_SCRIPT = "/edu/usf/ratsim/experiment/plot/plotting.r";
	private static final String PLOT_EXECUTER = "/edu/usf/ratsim/experiment/plot/plot.sh";
	private static final String OBJ2PNG_SCRIPT = "/edu/usf/ratsim/experiment/plot/obj2png.r";
//	private static final String EXPERIMENT_XML = "/edu/usf/ratsim/experiment/xml/multiFeedersOneSubSingleVsMultiConfModel.xml";
	private static final String STR_NUM_MEMBERS = "numMembers";

	private Map<ExpSubject, List<Trial>> trials;
	private String logPath;
	private String mazeFile;
	private Hashtable<String, Hashtable<String, ExpSubject>> groups;

	public Experiment(String filename) {
		logPath = computeLogPath();
		File file = new File(logPath);
		file.mkdirs();

		System.out.println("Starting " + logPath);

		// Set the log path in the global configuration class for the rest to
		// know
		Configuration.setProperty("Log.DIRECTORY", logPath);

		// Read experiments from xml file
		try {
			FileUtils.copyURLToFile(getClass().getResource(filename), new File(
					logPath + "experiment.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = XMLDocReader.readDocument(logPath + "experiment.xml");

		ElementWrapper root = new ElementWrapper(doc.getDocumentElement());

		// Copy the configuration file to the experiment's folder
		try {
			FileUtils.copyURLToFile(
					getClass().getResource(Configuration.PROP_FILE), new File(
							getLogPath() + File.separator
									+ Configuration.PROP_FILE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Load points from xml
		Hashtable<String, Point4f> points = loadPoints(root
				.getChildren(STR_POINT));

		// Set the maze to execute
		mazeFile = root.getChildText(STR_MAZE);

		// subjects = loadSubjects(doc);
		groups = loadGroups(root);

		trials = new HashMap<ExpSubject, List<Trial>>();
		loadTrials(root, points, groups, logPath);
	}

	private Hashtable<String, Hashtable<String, ExpSubject>> loadGroups(
			ElementWrapper root) {
		Hashtable<String, Hashtable<String, ExpSubject>> groups = new Hashtable<String, Hashtable<String, ExpSubject>>();
		List<ElementWrapper> groupNodes = root.getChildren(STR_GROUP);
		for (ElementWrapper gNode : groupNodes) {
			String gName = gNode.getChildText(STR_NAME);
			int groupNumSubs = gNode.getChildInt(STR_NUM_MEMBERS);

			Hashtable<String, ExpSubject> sGroup = new Hashtable<String, ExpSubject>();
			for (int k = 1; k <= groupNumSubs; k++) {
				String subName = gName + " - rat " + k;
				VirtualExpUniverse universe = new VirtualExpUniverse(mazeFile);
				IRobot robot = RobotFactory.getRobot(
						Configuration.getString("Reflexion.Robot"), universe);
				sGroup.put(subName, new ExpSubject(subName, robot, universe,
						gNode));
			}

			groups.put(gName, sGroup);
		}

		return groups;
	}

	public String getLogPath() {
		return logPath;
	}

	public void execPlottingScripts() {
		try {
			// Copy the maze to the experiment's folder
			FileUtils.copyURLToFile(getClass().getResource(mazeFile), new File(
					getLogPath() + "/maze.xml"));
			// Copy the plotting script to the experiment's folder
			FileUtils.copyURLToFile(getClass().getResource(PLOTTING_SCRIPT),
					new File(getLogPath() + "/plotting.r"));
			FileUtils.copyURLToFile(getClass().getResource(OBJ2PNG_SCRIPT),
					new File(getLogPath() + "/obj2png.r"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Copy the plotting script to the experiment's folder
		try {
			FileUtils.copyURLToFile(getClass().getResource(PLOT_EXECUTER),
					new File(getLogPath() + "/plot.sh"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Execute the plotting script
		try {
			System.out.println("Executing plotting scripts");
			Process plot = Runtime.getRuntime().exec("sh plot.sh", null,
					new File(getLogPath()));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					plot.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}

			BufferedReader err = new BufferedReader(new InputStreamReader(
					plot.getErrorStream()));
			line = null;
			while ((line = err.readLine()) != null) {
				System.out.println(line);
			}
			plot.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String computeLogPath() {
		// Setup the logPath to be the log directory + name of the experiment
		String logDir = Configuration.getString("Log.DIRECTORY");
		String name = this.getClass().getSimpleName();
		String logPath = logDir + File.separator + name;
		if (new File(logPath).exists()) {
			int i = 1;
			while (new File(logPath + i).exists())
				i++;
			logPath = logPath + i;
		}
		return logPath + File.separator;
	}

	private void loadTrials(ElementWrapper root,
			Hashtable<String, Point4f> points,
			Hashtable<String, Hashtable<String, ExpSubject>> groups,
			String experimentLogPath) {

		List<ElementWrapper> trialNodes = root.getChildren(STR_TRIAL);
		// For each trial
		for (ElementWrapper trialNode : trialNodes) {
			// For each group
			List<ElementWrapper> trialGroups = trialNode.getChild(STR_TRIALGROUPS)
					.getChildren(STR_GROUP);
			for (ElementWrapper groupNode : trialGroups) {
				String groupName = groupNode.getText();
				// For each subject in the group
				for (String subName : groups.get(groupName).keySet()) {
					ExpSubject subject = groups.get(groupName).get(subName);
					// For each repetition
					int reps = trialNode.getChildInt(STR_REPETITIONS);
					for (int k = 0; k < reps; k++) {
						Trial t = new Trial(trialNode, points, groupName,
								subject, k);
						if (!trials.containsKey(subject))
							trials.put(subject, new LinkedList<Trial>());
						trials.get(subject).add(t);
					}
				}
			}
		}
	}

	private Hashtable<String, Point4f> loadPoints(List<ElementWrapper> pointList) {
		Hashtable<String, Point4f> points = new Hashtable<String, Point4f>();

		for (ElementWrapper e : pointList) {
			float x = e.getChildFloat(STR_X_POSITION);
			float y = e.getChildFloat(STR_Y_POSITION);
			float z = e.getChildFloat(STR_Z_POSITION);
			float r = e.getChildFloat(STR_ANGLE);
			String pointName = e.getChildText(STR_NAME);
			points.put(pointName, new Point4f(x, y, z, r));
		}

		return points;
	}

	public void run() {
		Thread[] ts = new Thread[trials.size()];

		int i = 0;
		for (Entry<String, Hashtable<String, ExpSubject>> subjects : groups
				.entrySet())
			for (final Entry<String, ExpSubject> entry : subjects.getValue()
					.entrySet()) {
				// Create a thread for each subject, executing all its
				// experiments
				// in order
				ts[i] = new Thread(new Runnable() {

					public void run() {
						for (Trial trial : trials.get(entry.getValue()))
							trial.run();
					}
				});
				ts[i].start();
				// ts[i].run();
				i++;
			}

		for (Thread thread : ts) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		execPlottingScripts();
	}

	public static void main(String[] args) {
		System.out.println(args[0]);
		new Experiment(args[0]).run();
		System.exit(0);
	}

}
