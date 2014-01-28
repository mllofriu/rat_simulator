package experiment;

/*
 * Simulation.java
 * Este modulo levanta la configuracion completa del experimento a realizar.
 * Casos de habitucación, entrenamiento, prueba, parametros de configuracion del modelo, etc 
 * Autor: Gonzalo Tejera
 * Fecha: 11 de agosto de 2010
 */

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.vecmath.Point4f;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import support.Configuration;
import support.XMLDocReader;

public abstract class Experiment implements Runnable {

	public static final String STR_NAME = "name";
	private final String STR_POINT = "point";
	public static final String STR_REPETITIONS = "reps";
	private static final String STR_SUBJECT = "subject";
	private static final String STR_GROUP = "group";
	private static final Object STR_TRIALGROUP = "subjectGroup";
	private static final Object MEMBER_STR = "member";

	private final String STR_TRIAL = "trial";

	private final String STR_TRIAL_TYPE = "type";
	private final String STR_HABITUATION = "habituation";
	private final String STR_TRAINING = "training";
	private final String STR_TESTING = "testing";
	private final String STR_X_POSITION = "xp";
	private final String STR_Y_POSITION = "yp";
	private final String STR_Z_POSITION = "zp";
	private final String STR_ANGLE = "rot";

	private Vector<Trial> trials = new Vector<Trial>();
	private String logPath;

	public Experiment(String filename) {
		// Read experiments from xml file
		Document doc = XMLDocReader.readDocument(filename);

		logPath = computeLogPath();
		File file = new File(logPath);
		file.mkdirs();

		// Copy the configuration file to the experiment's folder
		try {
			FileUtils.copyFile(new File(Configuration.PROP_FILE), new File(
					getLogPath() + File.separator + Configuration.PROP_FILE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Load points from xml
		Hashtable<String, Point4f> points = loadPoints(doc
				.getElementsByTagName(STR_POINT));

		Hashtable<String, ExpSubject> subjects = loadSubjects(doc);
		Hashtable<String, Hashtable<String, ExpSubject>> groups = loadGroups(
				doc, subjects);

		loadTrials(doc.getElementsByTagName(STR_TRIAL), points, groups, logPath);
	}

	private Hashtable<String, Hashtable<String, ExpSubject>> loadGroups(
			Document doc, Hashtable<String, ExpSubject> subjects) {
		Hashtable<String, Hashtable<String, ExpSubject>> groups = new Hashtable<String, Hashtable<String, ExpSubject>>();
		NodeList elems = doc.getElementsByTagName(STR_GROUP);
		for (int i = 0; i < elems.getLength(); i++) {
			NodeList subsNL = elems.item(i).getChildNodes();
			Hashtable<String, ExpSubject> sGroup = new Hashtable<String, ExpSubject>();
			for (int j = 0; j < subsNL.getLength(); j++) {
				if (subsNL.item(j).getNodeType() == Node.ELEMENT_NODE){
					String name = subsNL.item(j).getAttributes().getNamedItem(STR_NAME).getNodeValue();
					sGroup.put(name, subjects.get(name));
				}
			}
			String gName = elems.item(i).getAttributes().getNamedItem(STR_NAME)
					.getNodeValue();
			groups.put(gName, sGroup);
		}
		return groups;
	}

	private Hashtable<String, ExpSubject> loadSubjects(Document doc) {
		Hashtable<String, ExpSubject> subs = new Hashtable<String, ExpSubject>();
		NodeList elems = doc.getElementsByTagName(STR_SUBJECT);
		for (int i = 0; i < elems.getLength(); i++) {
			String name = elems.item(i).getAttributes().getNamedItem("name")
					.getNodeValue();
			subs.put(name, createSubject(name));
		}

		return subs;
	}

	public abstract ExpSubject createSubject(String name);

	public String getLogPath() {
		return logPath;
	}

	public abstract void execPlottingScripts();
	
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
		return logPath;
	}

	private void loadTrials(NodeList list, Hashtable<String, Point4f> points,
			Hashtable<String, Hashtable<String, ExpSubject>> groups,
			String experimetnLogPath) {

		// For each trial
		for (int i = 0; i < list.getLength(); i++) {
			Map<String, String> params = new HashMap<String, String>();
			NodeList paramNodes = list.item(i).getChildNodes();

			// Load parameters into hash
			for (int j = 0; j < paramNodes.getLength(); j++) {
				params.put(paramNodes.item(j).getNodeName(), paramNodes.item(j)
						.getTextContent());
			}
			String trialLogPath = experimetnLogPath + File.separator
					+ params.get(STR_NAME);
			// For each subject in the group
			String gName = params.get(STR_TRIALGROUP);
			for (String subName : groups.get(gName).keySet()){
				ExpSubject subject = groups.get(gName).get(subName);
				String subLogPath = trialLogPath + File.separator + subject.getName();
				// Create <reps> copies of the trial
				for (int j = 0; j < Integer.parseInt(params.get(STR_REPETITIONS)); j++) {
					// Compose trial logPath with expLogPath + trialName + trial rep
					String repLogPath = subLogPath + File.separator + j
							+ File.separator;
					Trial t = createTrial(params, points, subject, repLogPath);
					trials.add(t);
				}
			}
			
		}
	}

	private Trial createTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, ExpSubject subject, String trialLogPath) {
		switch (stringType2TrialType(params.get(STR_TRIAL_TYPE))) {
		case HABITUATION:
			return createHabituationTrial(params, points, subject, trialLogPath);
		case TESTING:
			return createTestingTrial(params, points, subject, trialLogPath);
		case TRAINING:
			return createTrainingTrial(params, points, subject, trialLogPath);
		}

		return null;
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

	private Trial.Type stringType2TrialType(String strType) {
		if (strType.equals(STR_HABITUATION))
			return Trial.Type.HABITUATION;
		else if (strType.equals(STR_TESTING))
			return Trial.Type.TESTING;
		else if (strType.equals(STR_TRAINING))
			return Trial.Type.TRAINING;

		throw new RuntimeException("Invalid argurment");
	}

	public abstract Trial createTrainingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points2, ExpSubject subject, String trialLogPath);

	public abstract Trial createTestingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points2, ExpSubject subject, String trialLogPath);

	public abstract Trial createHabituationTrial(Map<String, String> params,
			Hashtable<String, Point4f> points2, ExpSubject subject, String trialLogPath);

	@Override
	public void run() {

		for (Trial t : trials) {
			t.run();
			System.out.println("Trial " + t + " finished.");
		}
		
		execPlottingScripts();

	}

}
