package experiment.multiscalemorris;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.vecmath.Point3f;

import nsl.modules.ActionPerformerVote;
import experiment.ExperimentLogger;
import experiment.ExperimentUniverse;

public class PositionLogger extends ExperimentLogger {

	private ActionPerformerVote actionPerformer;

	public PositionLogger(String logDir, ActionPerformerVote actionPerformer) {
		super(logDir);
		getLogger().log(Level.INFO, "x\ty\trandom");
		
		this.actionPerformer = actionPerformer;
	}

	@Override
	public void log(ExperimentUniverse universe) {
		Point3f pos = universe.getRobotPosition();
		// -Z coordinate corresponds to y
		getLogger().log(Level.INFO, pos.x + "\t" + -pos.z + "\t" + actionPerformer.wasLastActionRandom());
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