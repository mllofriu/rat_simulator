package edu.usf.ratsim.experiment.plot.multifeeders;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

import edu.usf.ratsim.experiment.plot.ExperimentPlotter;
import edu.usf.ratsim.experiment.postproc.ExperimentPostProc;
import edu.usf.ratsim.support.Configuration;

public class MultiFeedersTrialPlotter implements ExperimentPlotter, ExperimentPostProc {
	
	private static final String PLOTTING_SCRIPT = "/edu/usf/ratsim/experiment/plot/multifeeders/plotting.r";
	private static final String PLOT_EXECUTER = "/edu/usf/ratsim/experiment/plot/multifeeders/plot.sh";

	public MultiFeedersTrialPlotter(){
	}
	
	@Override
	public void plot() {
		try {
			// Copy the plotting script to the experiment's folder
			FileUtils.copyURLToFile(getClass().getResource(PLOTTING_SCRIPT),
					new File(Configuration.getString("Log.DIRECTORY") + "/plotting.r"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Copy the plotting script to the experiment's folder
		try {
			FileUtils.copyURLToFile(getClass().getResource(PLOT_EXECUTER),
					new File(Configuration.getString("Log.DIRECTORY") + "/plot.sh"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Execute the plotting script
		try {
			System.out.println("Executing bash plotting scripts");
			Process plot = Runtime.getRuntime().exec("sh plot.sh", null,
					new File(Configuration.getString("Log.DIRECTORY")));
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

	@Override
	public void perform() {
		plot();
	}

}
