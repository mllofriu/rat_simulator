package experiment;

/*
 * Simulation.java
 * Este modulo levanta la configuracion completa del experimento a realizar.
 * Casos de habitucación, entrenamiento, prueba, parametros de configuracion del modelo, etc 
 * Autor: Gonzalo Tejera
 * Fecha: 11 de agosto de 2010
 */

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.vecmath.Point4f;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import support.Configuration;
import support.XMLDocReader;

public abstract class Experiment implements Runnable {

	public static final String STR_NAME = "name";
	private final String STR_POINT = "point";
	public static final String STR_REPETITIONS = "reps";

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

	public Experiment(String filename) {
		// Read experiments from xml file
		Document doc = XMLDocReader.readDocument(filename);
		
		String logPath = getLogPath();
		setupLogDir(logPath);

		// Load points from xml
		Hashtable<String, Point4f> points = loadPoints(doc
				.getElementsByTagName(STR_POINT));

		loadTrials(doc.getElementsByTagName(STR_TRIAL), points, logPath);
	}

	private void setupLogDir(String logPath) {
		File file = new File(logPath);
		file.getParentFile().mkdirs();		
		// TODO: copy configuration file and other important info for traceability
		
	}

	private String getLogPath() {
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
			String experimetnLogPath) {
		for (int i = 0; i < list.getLength(); i++) {
			Map<String, String> params = new HashMap<String, String>();
			NodeList paramNodes = list.item(i).getChildNodes();

			for (int j = 0; j < paramNodes.getLength(); j++) {
				// System.out.println(paramNodes.item(j).getNodeName() + " " +
				// paramNodes.item(j).getTextContent());
				params.put(paramNodes.item(j).getNodeName(), paramNodes.item(j)
						.getTextContent());
			}

			// Create <reps> copies of the trial
			for (int j = 0; j < Integer.parseInt(params.get(STR_REPETITIONS)); j++) {
				// Compose trial logPath with expLogPath + trialName + trial rep
				String trialLogPath = experimetnLogPath
						+ File.separator
						+ params.get(STR_NAME)
						+ File.separator + j
						+ File.separator;
				Trial t = createTrial(params, points, trialLogPath);
				trials.add(t);
			}
		}
	}

	private Trial createTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, String trialLogPath) {
		switch (stringType2TrialType(params.get(STR_TRIAL_TYPE))) {
		case HABITUATION:
			return createHabituationTrial(params, points, trialLogPath);
		case TESTING:
			return createTestingTrial(params, points, trialLogPath);
		case TRAINING:
			return createTrainingTrial(params, points, trialLogPath);
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
			Hashtable<String, Point4f> points2, String trialLogPath);

	public abstract Trial createTestingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points2, String trialLogPath);

	public abstract Trial createHabituationTrial(Map<String, String> params,
			Hashtable<String, Point4f> points2, String trialLogPath);

	@Override
	public void run() {

		for (Trial t : trials){
			t.run();
		}

	}

}
