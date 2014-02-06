package edu.usf.ratsim.experiment;

public interface ExperimentLogger {

	public void log(ExperimentUniverse universe);

	public void finalizeLog();

}
