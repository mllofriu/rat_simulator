package edu.usf.ratsim.experiment.multiscalemorris;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point4f;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.NodeList;

import com.sun.corba.se.impl.orbutil.graph.Node;

import edu.usf.ratsim.experiment.ExpSubject;
import edu.usf.ratsim.experiment.Experiment;
import edu.usf.ratsim.experiment.Trial;
import edu.usf.ratsim.support.Configuration;

public class MSMExperiment extends Experiment {

	private final String STR_HABITUATION = "habituation";
	private final String STR_TRAINING = "training";
	private final String STR_TESTING = "testing";
	private static final String PLOTTING_SCRIPT = "/edu/usf/ratsim/experiment/plot/plotting.r";
	private static final String EXPERIMENT_XML = "/edu/usf/ratsim/experiment/xml/morrisMultiscale.xml";
	private static final String PLOT_EXECUTER = "/edu/usf/ratsim/experiment/plot/plot.sh";
	private static final String OBJ2PNG_SCRIPT = "/edu/usf/ratsim/experiment/plot/obj2png.r";;

	public MSMExperiment(String filename) {
		super(filename);
	}

	public static void main(String[] args) {
		new MSMExperiment(EXPERIMENT_XML).run();
		System.exit(0);
	}

	public Trial createTrainingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, String group, ExpSubject subject, int rep) {
		return new MSMTrial(params, points, group, subject, rep);
	}

	public Trial createTestingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, String group, ExpSubject subject, int rep) {
		return new MSMTrial(params, points, group, subject, rep);
	}

	public Trial createHabituationTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, String group, ExpSubject subject, int rep) {
		throw new RuntimeException("Habituation trial not implemented");
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

	public ExpSubject createSubject(String name, NodeList params) {
		return new MSMSubject(name, params);
	}

	public Trial createTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, String group,
			ExpSubject subject, int rep) {
		{
			switch (stringType2TrialType(params.get(STR_TRIAL_TYPE))) {
			case HABITUATION:
				return createHabituationTrial(params, points,group, subject, rep);
			case TESTING:
				return createTestingTrial(params, points, group, subject, rep);
			case TRAINING:
				return createTrainingTrial(params, points, group, subject, rep);
			}

			return null;
		}
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
}
