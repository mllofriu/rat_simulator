package experiment.multiscalemorris;

import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point4d;

import experiment.Experiment;
import experiment.Trial;

public class MSMExperiment extends Experiment {
	
	public MSMExperiment(String filename) {
		super(filename);
	}

	@Override
	public Trial createTrainingTrial(Map<String, String> params,
			Hashtable<String, Point4d> points2) {
		return null;
	}

	@Override
	public Trial createTestingTrial(Map<String, String> params,
			Hashtable<String, Point4d> points2) {
		return null;
	}

	@Override
	public Trial createHabituationTrial(Map<String, String> params,
			Hashtable<String, Point4d> points2) {
		return new MSMorrisHabituation(params);
	}

	public static void main(String[] args){
		new MSMExperiment("experimentos/newFormatTest.xml").run();
	}
}
