package edu.usf.ratsim.experiment.postproc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

import edu.usf.ratsim.support.Configuration;

public class ToRData implements ExperimentPostProc {

	private static final String CONVERT_SCRIPT = "/edu/usf/ratsim/experiment/postproc/convert.R";

	@Override
	public void perform() {
		try {
			// Copy the plotting script to the experiment's folder
			FileUtils.copyURLToFile(getClass().getResource(CONVERT_SCRIPT),
					new File(Configuration.getString("Log.DIRECTORY")
							+ "/convert.R"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Execute the plotting script
		try {
			System.out.println("Executing plotting scripts");
			Process plot = Runtime.getRuntime().exec("Rscript convert.R", null,
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

}
