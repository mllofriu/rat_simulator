package experiment.multiscalemorris;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.vecmath.Vector3f;

import experiment.ExperimentLogger;
import experiment.ExperimentUniverse;

public class PositionLogger extends ExperimentLogger {

	public PositionLogger(String logDir) {
		super(logDir);
		getLogger().log(Level.INFO, "x\ty");
	}

	@Override
	public void log(ExperimentUniverse universe) {
		Vector3f pos = universe.getRobotPosition();
		// -Z coordinate corresponds to y
		getLogger().log(Level.INFO, pos.x + "\t" + -pos.z);
	}

	@Override
	public Formatter getFormatter() {
		return new PositionFormatter();
	}

}

class PositionFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		return record.getMessage() + System.getProperty("line.separator");
	}
	
}