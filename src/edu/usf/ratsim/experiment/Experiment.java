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
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.XMLDocReader;

public class Experiment implements Runnable {

	public static final String STR_NAME = "name";
	private final String STR_POINT = "point";
	public static final String STR_REPETITIONS = "reps";
	private static final String STR_SUBJECT = "subject";
	private static final String STR_GROUP = "group";
	private static final String STR_TRIALGROUPS = "groups";

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
	private static final String EXPERIMENT_XML = "/edu/usf/ratsim/experiment/xml/morrisMultiscaleOneSubject.xml";
	
	private Map<ExpSubject, List<Trial>> trials;
	private String logPath;
	private Hashtable<String, ExpSubject> subjects;

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
		Hashtable<String, Point4f> points = loadPoints(doc
				.getElementsByTagName(STR_POINT));

		subjects = loadSubjects(doc);
		Hashtable<String, Hashtable<String, ExpSubject>> groups = loadGroups(
				doc, subjects);

		trials = new HashMap<ExpSubject, List<Trial>>();
		loadTrials(doc.getDocumentElement(), points, groups, logPath);
	}

	private Hashtable<String, Hashtable<String, ExpSubject>> loadGroups(
			Document doc, Hashtable<String, ExpSubject> subjects) {
		Hashtable<String, Hashtable<String, ExpSubject>> groups = new Hashtable<String, Hashtable<String, ExpSubject>>();
		NodeList elems = doc.getElementsByTagName(STR_GROUP);
		for (int i = 0; i < elems.getLength(); i++) {
			// Only group node that are direct children of <simulation>
			if (elems.item(i).getParentNode().getNodeName()
					.equals(XML_ROOT_STR)) {
				NodeList subsNL = elems.item(i).getChildNodes();
				Hashtable<String, ExpSubject> sGroup = new Hashtable<String, ExpSubject>();
				for (int j = 0; j < subsNL.getLength(); j++) {
					if (subsNL.item(j).getNodeType() == Node.ELEMENT_NODE) {
						String name = subsNL.item(j).getAttributes()
								.getNamedItem(STR_NAME).getNodeValue();
						sGroup.put(name, subjects.get(name));
					}
				}
				String gName = elems.item(i).getAttributes()
						.getNamedItem(STR_NAME).getNodeValue();
				groups.put(gName, sGroup);
			}
		}
		return groups;
	}

	private Hashtable<String, ExpSubject> loadSubjects(Document doc) {
		Hashtable<String, ExpSubject> subs = new Hashtable<String, ExpSubject>();
		NodeList elems = doc.getElementsByTagName(STR_SUBJECT);
		for (int i = 0; i < elems.getLength(); i++) {
			String name = elems.item(i).getAttributes().getNamedItem("name")
					.getNodeValue();
			if (elems.item(i).getParentNode().getNodeName()
					.equals(XML_ROOT_STR))
				subs.put(name, new ExpSubject(name, (Element) elems.item(i)));
		}

		return subs;
	}

	public String getLogPath() {
		return logPath;
	}

	public void execPlottingScripts() {
		try {
			// Copy the maze to the experiment's folder
			FileUtils.copyURLToFile(
					getClass().getResource(
							Configuration.getString("Experiment.MAZE_FILE")),
					new File(getLogPath() + "/maze.xml"));
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

	private void loadTrials(Element docRoot, Hashtable<String, Point4f> points,
			Hashtable<String, Hashtable<String, ExpSubject>> groups,
			String experimentLogPath) {

		NodeList trialNodes = docRoot.getElementsByTagName(STR_TRIAL);
		// For each trial
		for (int i = 0; i < trialNodes.getLength(); i++) {
			Element trialNode = (Element) trialNodes.item(i);
			Element trialGroupsNode = (Element) trialNode.getElementsByTagName(
					STR_TRIALGROUPS).item(0);

			// For each group
			NodeList trialGroups = trialGroupsNode
					.getElementsByTagName(STR_GROUP);
			for (int j = 0; j < trialGroups.getLength(); j++) {
				String groupName = trialGroups.item(j).getTextContent();
				// For each subject in the group
				for (String subName : groups.get(groupName).keySet()) {
					ExpSubject subject = groups.get(groupName).get(subName);
					// For each repetition
					int reps = Integer.parseInt(trialNode
							.getElementsByTagName(STR_REPETITIONS).item(0)
							.getTextContent());
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

	private Hashtable<String, Point4f> loadPoints(NodeList list) {
		Hashtable<String, Point4f> points = new Hashtable<String, Point4f>();

		for (int i = 0; i < list.getLength(); i++) {
			NamedNodeMap attributes = list.item(i).getAttributes();
			float x = Float.parseFloat(attributes.getNamedItem(STR_X_POSITION)
					.getNodeValue());
			float y = Float.parseFloat(attributes.getNamedItem(STR_Y_POSITION)
					.getNodeValue());
			float z = Float.parseFloat(attributes.getNamedItem(STR_Z_POSITION)
					.getNodeValue());
			float r = Float.parseFloat(attributes.getNamedItem(STR_ANGLE)
					.getNodeValue());
			String pointName = attributes.getNamedItem(STR_NAME).getNodeValue();
			points.put(pointName, new Point4f(x, y, z, r));
		}

		return points;
	}

	public void run() {
		Thread[] ts = new Thread[trials.size()];

		int i = 0;
		for (final Entry<String, ExpSubject> entry : subjects.entrySet()) {
			// Create a thread for each subject, executing all its experiments
			// in order
			ts[i] = new Thread(new Runnable() {

				public void run() {
					for (Trial trial : trials.get(entry.getValue()))
						trial.run();
				}
			});
			ts[i].start();
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
		new Experiment(EXPERIMENT_XML).run();
		System.exit(0);
	}

}
