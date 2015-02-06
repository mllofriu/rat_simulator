package edu.usf.ratsim.experiment.loggers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.Trial;
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
				res.add(new PositionLogger(t.getName(), t.getGroup(), t
						.getSubjectName(), t.getRep()));
//			} else if (loggerName.equals("PolicyDumper")) {
//				res.add(new PolicyDumper(((MultiScaleModel) t.getSubject()
//						.getModel()), t.getName(), t.getGroup(), t
//						.getSubjectName(), t.getRep()));
			} else if (loggerName.equals("SSLPositionLogger")) {
				res.add(new SSLPositionLogger(t.getName(), t.getGroup(), t
						.getSubjectName(), t.getRep()));
//			} else if (loggerName.equals("PolicyDumper")) {
//				res.add(new PolicyDumper(((MultiScaleModel) t.getSubject()
//						.getModel()), t.getName(), t.getGroup(), t
//						.getSubjectName(), t.getRep()));
			} else if (loggerName.equals("WantedFeederLogger")) {
				res.add(new WantedFeederLogger(t.getName(), t.getGroup(), t
						.getSubjectName(), t.getRep()));
			} else if (loggerName.equals("WallLogger")) {
				res.add(new WallLogger(t.getName(), t.getGroup(), t
						.getSubjectName(), t.getRep()));
			} else {
				throw new RuntimeException("Logger " + loggerName
						+ " not implemented.");
			}
		}

		return res;

	}
}
