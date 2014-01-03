package simulation;

/*
 * Simulation.java
 * Este modulo levanta la configuracion completa del experimento a realizar.
 * Casos de habitucación, entrenamiento, prueba, parametros de configuracion del modelo, etc 
 * Autor: Gonzalo Tejera
 * Fecha: 11 de agosto de 2010
 */

import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import javax.vecmath.Point4d;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import support.Configuration;


public class Simulation {
	public static final String STR_NAME = "name";
	private final String STR_POINT = "point";
	private final String STR_POINT_NAME = "pointName";

	private final String STR_OPERATION = "operation";
	private final String STR_TRIAL = "trial";
	private final String STR_PRIMITIVE = "primitive";
	
	private final String STR_TRIAL_TYPE = "type";
	private final String STR_HABITUATION = "habituation";
	private final String STR_TRAINING = "training";
	private final String STR_TESTING = "testing";
	private final String STR_X_POSITION = "xp";
	private final String STR_Y_POSITION = "yp";
	private final String STR_Z_POSITION = "zp";
	private final String STR_ANGLE = "rot";
	private final String STR_REPETITIONS = "reps";
	private final String STR_TIME = "time";
	private final String STR_EXPLORATION = "exploration";

	private final String DEFAULT_DIR=Configuration.getString("Simulation.DIRECTORY");
	private final String DEFAULT_FILE_NAME=Configuration.getString("Simulation.FILE");
	private final String DEFAULT_FILE= System.getProperty("user.dir")+File.separatorChar+DEFAULT_DIR+File.separatorChar+DEFAULT_FILE_NAME;
	
	private Vector<SimulationItem> simulationItems=new Vector<SimulationItem>();
	
	private int numberHabituationTrials=0;
	private int numberTrainingTrials=0;
	private int numberTestingTrials=0;
	private int numberTrials=0;
	static private int currenTrial=0;
	private int currentItemNumber=0;
	private SimulationItem currentSimItem;
	static Hashtable<String, Point4d> points=new Hashtable<String, Point4d>();
	private long oldTime;
	private static Vector<SimulationOperation> operations=new Vector<SimulationOperation>();
	
	public Simulation() {
		Document doc=null;

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			doc = docBuilder.parse(new File(DEFAULT_FILE));

			doc.getDocumentElement().normalize();

		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println("   " + err.getMessage());
		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		}

		// levanto todos los puntos del xml
		loadPoints(doc.getElementsByTagName(STR_POINT));
		// levanto las operaciones a realizar en el entorno
		loadOperations(doc.getElementsByTagName(STR_OPERATION));
		
		numberHabituationTrials=load(SimulationItem.HABITUATION, doc.getElementsByTagName(STR_HABITUATION));
		numberTrainingTrials=load(SimulationItem.TRAINING, doc.getElementsByTagName(STR_TRAINING));
		numberTestingTrials=load(SimulationItem.TESTING, doc.getElementsByTagName(STR_TESTING));
		numberTrials=loadTrials(doc.getElementsByTagName(STR_TRIAL));

		numberTrials=numberHabituationTrials+numberTrainingTrials+numberTestingTrials+numberTrials;
		currentSimItem = simulationItems.get(0);
		oldTime=currentSimItem.getTime();
	}
	
	private int loadTrials(NodeList list) {
		NamedNodeMap attributes;
		int type;
		String name;
		int reps;
		String pointName;
		long time;
		double exploration;
		for (int i = 0; i < list.getLength(); i++) {
		attributes = list.item(i).getAttributes();
		name=attributes.getNamedItem(STR_NAME).getNodeValue();
		type=stringType2Int(attributes.getNamedItem(STR_TRIAL_TYPE).getNodeValue());
		pointName=attributes.getNamedItem(STR_POINT_NAME).getNodeValue();
		reps=Integer.parseInt(attributes.getNamedItem(STR_REPETITIONS).getNodeValue());
		time=Long.parseLong(attributes.getNamedItem(STR_TIME).getNodeValue());
		try {
			exploration=Double.parseDouble(attributes.getNamedItem(STR_EXPLORATION).getNodeValue());
		} catch (Exception e) {
			exploration =0;
		}		//System.err.println("Simulation::load ensayo (trial): "+ name);
		simulationItems.add(new SimulationItem(name, type, exploration,points.get(pointName), reps, time));
		}
		return  list.getLength();
	}

	private int stringType2Int(String strType) {
		int result;
		
		if (strType.equals(STR_HABITUATION))
			result= SimulationItem.HABITUATION;
		else if (strType.equals(STR_TESTING))
			result= SimulationItem.TESTING;
		else if (strType.equals(STR_TRAINING))
			result= SimulationItem.TRAINING;
		else 	
			result = -1;
		
		return result;
	}

	private void loadOperations(NodeList list) {
		NamedNodeMap attributes;
		String operation,primitive,trialApply,pointName;

		for (int i = 0; i < list.getLength(); i++) {
			attributes = list.item(i).getAttributes();
			trialApply=attributes.getNamedItem(STR_TRIAL).getNodeValue();
			operation=attributes.getNamedItem(STR_NAME).getNodeValue();
			primitive=attributes.getNamedItem(STR_PRIMITIVE).getNodeValue();
			if (attributes.getNamedItem(STR_POINT_NAME)==null)
				operations.add(new SimulationOperation(operation, primitive, trialApply));
			else
				operations.add(new SimulationOperation(operation, primitive, trialApply,attributes.getNamedItem(STR_POINT_NAME).getNodeValue()));

			//System.out.println("Simulation::"+pointName+" x:"+ x +". y: "+y+". r: "+r+".");
		}
		//System.out.println("operaciones: "+operations.size());
	}

	private void loadPoints(NodeList list) {
		NamedNodeMap attributes;
		String pointName;
		double x, y, z, r;

		for (int i = 0; i < list.getLength(); i++) {
			attributes = list.item(i).getAttributes();
			x=Double.parseDouble(attributes.getNamedItem(STR_X_POSITION).getNodeValue());
			y=Double.parseDouble(attributes.getNamedItem(STR_Y_POSITION).getNodeValue());
			z=Double.parseDouble(attributes.getNamedItem(STR_Z_POSITION).getNodeValue());
			r=Double.parseDouble(attributes.getNamedItem(STR_ANGLE).getNodeValue());
			pointName=attributes.getNamedItem(STR_NAME).getNodeValue();
			points.put(pointName, new Point4d(x, y, z, r));
			//System.out.println("Simulation::"+pointName+" x:"+ x +". y: "+y+". r: "+r+".");
		}
	}
	
	public static Vector<SimulationOperation> getOperations() {
		return operations;
	}

	private int load(int type, NodeList list) {
		int suma=0;
		for (int i = 0; i < list.getLength(); i++)
			suma=suma+add(type, list.item(i));		
		return suma;
	}
	
	private int add(int type, org.w3c.dom.Node node) {
		NamedNodeMap attributes;
		String name;
		int reps;
		Point4d iniPos;
		String pointName;
		long time;
		double exploration;
		numberTrials=0;
		attributes = node.getAttributes();
		name=attributes.getNamedItem(STR_NAME).getNodeValue();		
		pointName=attributes.getNamedItem(STR_POINT_NAME).getNodeValue();
		reps=Integer.parseInt(attributes.getNamedItem(STR_REPETITIONS).getNodeValue());
		time=Long.parseLong(attributes.getNamedItem(STR_TIME).getNodeValue());
		//System.err.println("Simulation::load ensayo: "+ name);
		try {
			exploration=Double.parseDouble(attributes.getNamedItem(STR_EXPLORATION).getNodeValue());
		} catch (Exception e) {
			exploration =0;
		}
		simulationItems.add(new SimulationItem(name, type, exploration,points.get(pointName), reps, time));
		return reps;
	}
	
	// devuelve las coordenadas de un punto a partir de su nombre
	public static Point4d getPoint(String pointName) {
		return points.get(pointName);
	}

	public SimulationItem next() {
		currenTrial++;
		currentSimItem.setTime(oldTime);
		if (currentSimItem.getRepetitions()==0) {
			currentItemNumber++;
			
			if (currentItemNumber==simulationItems.size())
				return null;
			else {
				currentSimItem = simulationItems.get(currentItemNumber);
				oldTime=currentSimItem.getTime();
			}
		}
		
		currentSimItem.decRepetitions();
		//System.out.println("Simulation::#trials: "+ this.numberTrials);
		return currentSimItem;
	}
	
	public static int getCurrenTrial() {
		return currenTrial;
	}

	public int getNumberHabituationTrials() {
		return numberHabituationTrials;
	}

	public int getNumberTrainingTrials() {
		return numberTrainingTrials;
	}

	public int getNumberTestingTrials() {
		return numberTestingTrials;
	}

	public int getNumberTrials() {
		return numberTrials;
	}
	
}
