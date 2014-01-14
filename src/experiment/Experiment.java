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

import javax.vecmath.Point4d;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.tools.corba.se.idl.InvalidArgument;

import robot.IRobot;
import robot.RobotFactory;
import support.Configuration;
import support.XMLDocReader;
import experiment.Trial.Type;

public abstract class Experiment implements Runnable {

	public static final String STR_NAME = "name";
	private final String STR_POINT = "point";

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
		Document doc = XMLDocReader.readDocument(filename);

		// Load points from xml
		Hashtable<String, Point4d> points = loadPoints(doc
				.getElementsByTagName(STR_POINT));

		loadTrials(doc.getElementsByTagName(STR_TRIAL), points);
	}

	private void loadTrials(NodeList list, Hashtable<String, Point4d> points) {
		for (int i = 0; i < list.getLength(); i++) {
			Map<String, String> params = new HashMap<String, String>();
			NodeList paramNodes = list.item(i).getChildNodes();

			for (int j = 0; j < paramNodes.getLength(); j++) {
				// System.out.println(paramNodes.item(j).getNodeName() + " " +
				// paramNodes.item(j).getTextContent());
				params.put(paramNodes.item(j).getNodeName(), paramNodes.item(j)
						.getTextContent());
			}

			Trial t = createTrial(params, points);
			trials.add(t);
		}
	}

	private Trial createTrial(Map<String, String> params,
			Hashtable<String, Point4d> points) {
		switch (stringType2TrialType(params.get(STR_TRIAL_TYPE))) {
		case HABITUATION:
			return createHabituationTrial(params, points);
		case TESTING:
			return createTestingTrial(params, points);
		case TRAINING:
			return createTrainingTrial(params, points);
		}

		return null;
	}

	private Hashtable<String, Point4d> loadPoints(NodeList list) {
		Hashtable<String, Point4d> points = new Hashtable<String, Point4d>();

		for (int i = 0; i < list.getLength(); i++) {
			NamedNodeMap attributes = list.item(i).getAttributes();
			double x = Double.parseDouble(attributes.getNamedItem(
					STR_X_POSITION).getNodeValue());
			double y = Double.parseDouble(attributes.getNamedItem(
					STR_Y_POSITION).getNodeValue());
			double z = Double.parseDouble(attributes.getNamedItem(
					STR_Z_POSITION).getNodeValue());
			double r = Double.parseDouble(attributes.getNamedItem(STR_ANGLE)
					.getNodeValue());
			String pointName = attributes.getNamedItem(STR_NAME).getNodeValue();
			points.put(pointName, new Point4d(x, y, z, r));
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
			Hashtable<String, Point4d> points2);

	public abstract Trial createTestingTrial(Map<String, String> params,
			Hashtable<String, Point4d> points2);

	public abstract Trial createHabituationTrial(Map<String, String> params,
			Hashtable<String, Point4d> points2);

	@Override
	public void run() {
		System.out.println("Running experiment");
		for (Trial t : trials)
			t.run();
		
		System.exit(0);
	}

}
