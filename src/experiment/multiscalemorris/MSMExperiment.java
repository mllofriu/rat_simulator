package experiment.multiscalemorris;

import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point4f;

import experiment.Experiment;
import experiment.Trial;

public class MSMExperiment extends Experiment {

	public MSMExperiment(String filename) {
		super(filename);
	}

	public static void main(String[] args) {
		new MSMExperiment("experimentos/newFormatTest.xml").run();
	}

	@Override
	public Trial createTrainingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, String trialLogPath) {
		return new MSMTrial(params, points, trialLogPath);
	}

	@Override
	public Trial createTestingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, String trialLogPath) {
		return new MSMTrial(params, points, trialLogPath);
	}

	@Override
	public Trial createHabituationTrial(Map<String, String> params,
			Hashtable<String, Point4f> points, String trialLogPath) {
		throw new RuntimeException(
				"There are no habituation trials in multiscale morris experiment.");
	}
}
