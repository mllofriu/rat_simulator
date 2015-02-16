package edu.usf.ratsim.experiment;

/*
 * Simulation.java
 * Este modulo levanta la configuracion completa del experimento a realizar.
 * Casos de habitucacin, entrenamiento, prueba, parametros de configuracion del modelo, etc 
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.vecmath.Point4f;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import edu.usf.ratsim.experiment.postproc.ExperimentPostProc;
import edu.usf.ratsim.experiment.postproc.PostProcFactory;
import edu.usf.ratsim.experiment.subject.ExpSubject;
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

	// private static final String EXPERIMENT_XML =
	// "/edu/usf/ratsim/experiment/xml/multiFeedersOneSubSingleVsMultiConfModel.xml";
	private static final String STR_NUM_MEMBERS = "numMembers";

	private Map<ExpSubject, List<Trial>> trials;
	private String logPath;
	private String mazeFile;
	private List<Group> groups;
	private List<ExperimentPostProc> postProcs;

	public Experiment(String filename, String logPath, String group,
			String individual) {
		System.out.println("Starting group " + group + " individual "
				+ " in log " + logPath);

		// If there is no log path, compute it
		if (logPath == null) {
			logPath = computeLogPath();

			// No individual specific execution
			Configuration.setProperty("Log.INDIVIDUAL", "");
		} else {
			logPath = logPath + File.separator + group + File.separator
					+ individual + File.separator;
			// No individual specific execution
			Configuration.setProperty("Log.GROUP", group);
			Configuration.setProperty("Log.INDIVIDUAL", individual);
			Configuration.setProperty("Log.REL_DIRECTORY",
					Configuration.getString("Log.REL_DIRECTORY")
							+ File.separator + group + File.separator
							+ individual + File.separator);
		}

		// Set the log path in the global configuration class for the rest to
		// know
		Configuration.setProperty("Log.DIRECTORY", logPath);

		// Delete tmp logpath if it is still there
		File file = new File(logPath);
//		if (file.exists())
//			file.delete();
		file.mkdirs();

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
			FileUtils
					.copyURLToFile(
							getClass().getResource(Configuration.PROP_FILE),
							new File(logPath + File.separator
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
		// Copy the maze to the experiment's folder
		try {
			FileUtils.copyURLToFile(getClass().getResource(mazeFile), new File(
					logPath + "/maze.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// subjects = loadSubjects(doc);
		groups = loadGroups(root);

		trials = new HashMap<ExpSubject, List<Trial>>();
		loadTrials(root, points, groups, logPath);

		postProcs = PostProcFactory.createPPs(root);
	}

	private List<Group> loadGroups(ElementWrapper root) {
		List<Group> groups = new LinkedList<Group>();
		List<ElementWrapper> groupNodes = root.getChildren(STR_GROUP);
		for (ElementWrapper gNode : groupNodes) {
			String gName = gNode.getChildText(STR_NAME);
			Group g = new Group(gName);
			int groupNumSubs = gNode.getChildInt(STR_NUM_MEMBERS);

			for (int k = 1; k <= groupNumSubs; k++) {
				String subName = gName + " - rat " + k;

				g.addSubject(new ExpSubject(subName, gName, gNode, mazeFile));
			}

			groups.add(g);
		}

		return groups;
	}

	public String getLogPath() {
		return logPath;
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
			Hashtable<String, Point4f> points, List<Group> groups,
			String experimentLogPath) {

		
		
		List<ElementWrapper> trialNodes = root.getChildren(STR_TRIAL);
		// For each trial
		for (ElementWrapper trialNode : trialNodes) {
			int fromRep = 0;
			if (Configuration.getBoolean("Experiment.loadSavedPolicy")){
				System.out.println("Ingrese la repeticion a comenzar");
				try {
					fromRep = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// For each group
			List<ElementWrapper> trialGroups = trialNode.getChild(
					STR_TRIALGROUPS).getChildren(STR_GROUP);
			for (ElementWrapper groupNode : trialGroups) {
				String groupName = groupNode.getText();
				// For each subject in the group
				for (Group group : groups) {
					if (group.getName().equals(groupName)) {
						for (ExpSubject subject : group.getSubjects()) {
							
							
							// For each repetition
							int reps = trialNode.getChildInt(STR_REPETITIONS);
							for (int k = fromRep; k < reps; k++) {
								Trial t = new Trial(trialNode, points,
										groupName, subject, k);
								if (!trials.containsKey(subject))
									trials.put(subject, new LinkedList<Trial>());
								trials.get(subject).add(t);
							}
						}
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
		// If no specified individual - execute all
		if (Configuration.getString("Log.INDIVIDUAL").equals("")) {
			// ExecutorService executor = Executors.newSingleThreadExecutor();
			ExecutorService executor = Executors
					.newFixedThreadPool(Configuration
							.getInt("Experiment.numThreads"));

			// Create threads
			for (Group group : groups) {
				for (ExpSubject subject : group.getSubjects()) {
					executor.execute(new SubjectThread(subject, trials
							.get(subject)));
				}
			}

			// Delete old reference to subjects to free memory when threads are
			// done
			groups.clear();
			trials.clear();

			try {
				System.out.println("Waiting for threads");
				executor.shutdown();
				executor.awaitTermination(1, TimeUnit.DAYS);
				System.out.println("Threads finished");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// execPlottingScripts();
		} else {
			int individualNum = Integer.parseInt(Configuration
					.getString("Log.INDIVIDUAL"));
			int groupNum = Integer.parseInt(Configuration
					.getString("Log.GROUP"));

			ExpSubject subject = groups.get(groupNum).getSubject(individualNum);
			System.out.println("Running " + subject.getName());
			new SubjectThread(subject, trials.get(subject)).run();
		}

		for (ExperimentPostProc pp : postProcs)
			pp.perform();

	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.class.path"));
		
		if (args.length < 1)
			System.out.println("Missing experiment xml argument");

		Experiment e;
		// More than one parameter means that we have to run only one (the
		// specified) inidividual

		if (args.length == 1) {
			e = new Experiment(args[0], null, null, null);
		} else {
			// Set a variable with the relative folder to this run
			Configuration.setProperty("Log.REL_DIRECTORY", File.separator
					+ args[1] + File.separator);
			String tmpLogPath = Configuration.getString("Log.TMP")
					+ File.separator
					+ Configuration.getString("Log.REL_DIRECTORY");
			e = new Experiment(args[0], tmpLogPath, args[2], args[3]);
		}

		e.run();

		System.exit(0);
	}
}

class SubjectThread extends Thread {

	private List<Trial> trials;
	private ExpSubject subject;

	public SubjectThread(ExpSubject subject, List<Trial> trials) {
		this.subject = subject;
		this.trials = trials;
	}

	public void setSubject(ExpSubject subject) {
		this.subject = subject;

	}

	public void run() {
		subject.initModel();
		for (Trial trial : trials)
			trial.run();

		trials.clear();
		subject.disposeInterp();
		subject.destroyUniv();
		subject = null;

		// System.gc();
	}

	public ExpSubject getSubject() {
		return subject;
	}
}
