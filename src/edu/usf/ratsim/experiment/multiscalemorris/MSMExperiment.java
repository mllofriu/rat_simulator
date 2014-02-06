package edu.usf.ratsim.experiment.multiscalemorris;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point4f;

import org.apache.commons.io.FileUtils;

import edu.usf.ratsim.experiment.ExpSubject;
import edu.usf.ratsim.experiment.Experiment;
import edu.usf.ratsim.experiment.Trial;
import edu.usf.ratsim.support.Configuration;

public class MSMExperiment extends Experiment {

	private static final String PLOTTING_SCRIPT = "/edu/usf/ratsim/experiment/plot/plotting.r";
	private static final String EXPERIMENT_XML = "/edu/usf/ratsim/experiment/xml/morrisMultiscaleOneSubject.xml";
	private static final String PLOT_EXECUTER = "/edu/usf/ratsim/experiment/plot/plot.sh";

	public MSMExperiment(String filename) {
		super(filename);
	}

	public static void main(String[] args) {
		new MSMExperiment(EXPERIMENT_XML).run();
		System.exit(0);
	}

	public Trial createTrainingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, ExpSubject subject, int rep) {
		return new MSMTrial(params, points, subject, rep);
	}

	public Trial createTestingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, ExpSubject subject, int rep) {
		return new MSMTrial(params, points, subject, rep);
	}

	public Trial createHabituationTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, ExpSubject subject, int rep) {
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
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					plot.getInputStream()));
//			String line = null;
//			while ((line = in.readLine()) != null) {
//				System.out.println(line);
//			}
//
//			BufferedReader err = new BufferedReader(new InputStreamReader(
//					plot.getErrorStream()));
//			line = null;
//			while ((line = err.readLine()) != null) {
//				System.out.println(line);
//			}
			plot.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ExpSubject createSubject(String name) {
		return new MSMSubject(name);
	}
}
