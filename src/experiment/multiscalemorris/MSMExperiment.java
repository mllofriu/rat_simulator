package experiment.multiscalemorris;

import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point4d;
import javax.vecmath.Point4f;

import experiment.Experiment;
import experiment.Trial;

public class MSMExperiment extends Experiment {
	
	public MSMExperiment(String filename) {
		super(filename);
	}

	@Override
	public Trial createTrainingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points) {
		return new MSMTrial(params, points);
	}

	@Override
	public Trial createTestingTrial(Map<String, String> params,
			Hashtable<String, Point4f> points) {
		return new MSMTrial(params, points);
	}

	@Override
	public Trial createHabituationTrial(Map<String, String> params,
			Hashtable<String, Point4f> points) {
		throw new RuntimeException("There are no habituation trials in multiscale morris experiment.");
	}

	public static void main(String[] args){
		new MSMExperiment("experimentos/newFormatTest.xml").run();
	}
}
