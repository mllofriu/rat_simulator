package support;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogerPlain {
	private static final String DEFAULT_DIR = Configuration
			.getString("Log.DIRECTORY");
	PrintWriter out;
	public String SEPARATOR = "\t";

	public LogerPlain(String name) {
		try {
			String fileName = System.getProperty("user.dir")
					+ File.separatorChar + DEFAULT_DIR + File.separatorChar
					+ name + "::" + System.currentTimeMillis() + ".cvs";
			FileWriter outFile = new FileWriter(fileName);
			out = new PrintWriter(outFile);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeln(String line) {
		out.write(line + "\n");
		out.flush();
	}

	public void close() {
		out.close();
	}

}
