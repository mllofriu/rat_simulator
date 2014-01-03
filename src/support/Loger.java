package support;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class Loger {
	private FileHandler handler;
	private Logger log = Logger.getLogger("log_file");
	private static final String DEFAULT_DIR = Configuration
			.getString("Log.DIRECTORY");
	public Loger(String name) {
		try {
			handler = new FileHandler(System.getProperty("user.dir")
					+ File.separatorChar + DEFAULT_DIR + File.separatorChar
					+ name+ "::" +System.currentTimeMillis()+".xml");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.addHandler(handler);
	}

	public Logger getLog() {
		return log;
	}

}
