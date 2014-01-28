package experiment.multiscalemorris;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point4f;

import org.apache.commons.io.FileUtils;

import support.Configuration;
import experiment.ExpSubject;
import experiment.Experiment;
import experiment.Trial;

public class MSMExperiment extends Experiment {

	private static final String PLOTTING_SCRIPT = "plot/plotMaze.R";
	private static final String EXPERIMENT_XML = "experimentos/morrisMultiscale.xml";
	

	public MSMExperiment(String filename) {
		super(filename);
	}

	public static void main(String[] args) {
		new MSMExperiment(EXPERIMENT_XML).run();
		System.exit(0);
	}

	@Override
	public Trial createTrainingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, ExpSubject subject, String trialLogPath) {
		return new MSMTrial(params, points,subject, trialLogPath);
	}

	@Override
	public Trial createTestingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points,ExpSubject subject,  String trialLogPath) {
		return new MSMTrial(params, points,subject, trialLogPath);
	}

	@Override
	public Trial createHabituationTrial(Map<String, String> params,
			Hashtable<String, Point4f> points,ExpSubject subject,  String trialLogPath) {
		throw new RuntimeException(
				"There are no habituation trials in multiscale morris experiment.");
	}

	@Override
	public void execPlottingScripts() {
		try {
			// Copy the maze to the experiment's folder
			FileUtils.copyFile(new File(Configuration.getString("Experiment.MAZE_FILE")),
					new File(getLogPath() + "/maze.xml"));
			// Copy the plotting script to the experiment's folder
			FileUtils.copyFile(new File(PLOTTING_SCRIPT),
					new File(getLogPath() + "/plot.r"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Execute the plotting script
		try {
			Runtime.getRuntime().exec("r --no-save < plot.r", null, new File(getLogPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ExpSubject createSubject(String name) {
		return new MSMSubject(name);
	}
}
