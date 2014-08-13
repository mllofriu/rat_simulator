package edu.usf.ratsim.experiment.postproc;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.sun.tools.internal.jxc.gen.config.Config;

import edu.usf.ratsim.support.Configuration;

public class MoveLogsPP implements ExperimentPostProc {

	@Override
	public void perform() {
		String finalLogPath = Configuration.getString("Log.FINALDIRECTORY")
				+ Configuration.getString("Log.REL_DIRECTORY");
		File finalLogPathDir = new File(finalLogPath);
//		finalLogPathDir.mkdirs();
		
		File currentLogPathDir = new File(Configuration.getString("Log.DIRECTORY"));
		
		try {
			FileUtils.moveDirectory(currentLogPathDir, finalLogPathDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
