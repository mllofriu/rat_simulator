package edu.usf.ratsim.experiment;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class ExperimentLogger {

	private PrintWriter writer;

	public ExperimentLogger(String logDir) {
		try {
			String filePath = logDir + "position.txt";
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			writer = new PrintWriter(file);
		} catch (SecurityException e) {
			System.out
					.println("Could open filehandler for logging, security reasons.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out
					.println("Could open filehandler for logging, IO reasons.");
			e.printStackTrace();
		}
	}

	public abstract void log(ExperimentUniverse universe);

	public abstract void finalizeLog();

	public PrintWriter getWriter() {
		return writer;
	}

	public void closeLog() {
		finalizeLog();
		writer.close();
	}
}
