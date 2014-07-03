package edu.usf.ratsim.experiment.plot;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import edu.usf.ratsim.experiment.plot.multifeeders.MultiFeedersTrialPlotter;
import edu.usf.ratsim.support.ElementWrapper;

public class PlottingFactory {

	private static final String STR_PLOTTER = "plotter";
	private static final String STR_PLOTTER_NAME = "name";
	private static final String STR_PLOTTER_PARAMS = "params";

	public static Collection<ExperimentPlotter> createPlottingTasks(
			ElementWrapper plottersNode) {
		Collection<ExperimentPlotter> res = new LinkedList<ExperimentPlotter>();

		List<ElementWrapper> plotterList = plottersNode
				.getChildren(STR_PLOTTER);
		for (ElementWrapper loggerNode : plotterList) {
			String loggerName = loggerNode.getChildText(STR_PLOTTER_NAME);

			ElementWrapper plotterParams = loggerNode
					.getChild(STR_PLOTTER_PARAMS);
			if (loggerName.equals("MutliFeedersPlotter")) {
				res.add(new MultiFeedersTrialPlotter());
			} else {
				throw new RuntimeException("Logger " + loggerName
						+ " not implemented.");
			}
		}

		return res;

	}

}
