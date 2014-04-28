package edu.usf.ratsim.experiment.loggers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.Trial;
import edu.usf.ratsim.experiment.model.MultiScaleModel;
import edu.usf.ratsim.experiment.model.RLRatModel;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.support.ElementWrapper;

public class LoggerFactory {

	private static final String STR_LOGGER = "logger";
	private static final String STR_LOGGER_NAME = "name";
	private static final String STR_LOGGER_PARAMS = "params";
	private static final String STR_NUM_INTENTIONS = "numIntentions";

	public static Collection<ExperimentLogger> createLoggers(
			ElementWrapper loggersNode, Trial t) {
		Collection<ExperimentLogger> res = new LinkedList<ExperimentLogger>();

		List<ElementWrapper> loggerList = loggersNode
				.getChildren(STR_LOGGER);
		for (ElementWrapper loggerNode : loggerList) {
			String loggerName = loggerNode.getChildText(STR_LOGGER_NAME);

			ElementWrapper loggerParams = loggerNode
					.getChild(STR_LOGGER_PARAMS);
			if (loggerName.equals("PositionLogger")) {
				ProportionalExplorer p = ((RLRatModel) t.getSubject()
						.getModel()).getActionPerformer();
				res.add(new PositionLogger(t.getName(), t.getGroup(), t
						.getSubjectName(), t.getRep(), p));
			} else if (loggerName.equals("PolicyDumper")) {
				res.add(new PolicyDumper(((MultiScaleModel) t.getSubject()
						.getModel()), t.getName(), t.getGroup(), t
						.getSubjectName(), t.getRep()));
			} else if (loggerName.equals("PolicyDumperWithIntention")) {
				res.add(new PolicyDumperWithIntention(
						((RLRatModel) t.getSubject()
								.getModel()), t.getName(), t.getGroup(), t
								.getSubjectName(), t.getRep(), Integer
								.parseInt(loggerParams
										.getChildText(STR_NUM_INTENTIONS))));
			} else {
				throw new RuntimeException("Logger " + loggerName
						+ " not implemented.");
			}
		}

		return res;

	}
}
