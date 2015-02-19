package edu.usf.ratsim;

import java.io.File;

import edu.usf.experiment.Experiment;
import edu.usf.ratsim.support.Configuration;

public class ExperimentRunner {

	public static void main(String[] args) {
		for (int i = 0; i < Integer.parseInt(args[2]); i++)
			for (int j = 1; j <= Integer.parseInt(args[3]); j++) {
				Configuration.setProperty("Log.REL_DIRECTORY", File.separator
						+ args[1] + File.separator);
				String tmpLogPath = Configuration.getString("Log.TMP")
						+ File.separator
						+ Configuration.getString("Log.REL_DIRECTORY");
				Experiment e = new Experiment(args[0], tmpLogPath,
						i+"", j+"");

				e.run();
			}
	}
}
