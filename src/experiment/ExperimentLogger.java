package experiment;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ExperimentLogger {

	private Logger logger;
	private FileHandler filetxt;

	public ExperimentLogger(String logDir){
		logger = Logger.getLogger(this.getClass().getName() + logDir);
		logger.setLevel(Level.INFO);
		// Remove all previous handlers
		for (Handler handler : logger.getHandlers())
			logger.removeHandler(handler);
		// Avoid using the console too
		logger.setUseParentHandlers(false);
		
		try {
			String filePath = logDir + "position.txt";
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			file.createNewFile();
			filetxt = new FileHandler(filePath);
			filetxt.setFormatter(getFormatter());
			logger.addHandler(filetxt);
		} catch (SecurityException e) {
			System.out.println("Could open filehandler for logging, security reasons.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could open filehandler for logging, IO reasons.");
			e.printStackTrace();
		}
	}
	
	public abstract Formatter getFormatter();

	public abstract void log(ExperimentUniverse universe);
	
	public void closeLog(){
		filetxt.close();
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
