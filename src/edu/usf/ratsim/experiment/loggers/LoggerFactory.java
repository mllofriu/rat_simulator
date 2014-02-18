package edu.usf.ratsim.experiment.loggers;

import java.util.Collection;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.Trial;
import edu.usf.ratsim.experiment.model.MultiScaleMorrisModel;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;

public class LoggerFactory {

	private static final String STR_LOGGER = "logger";
	private static final String STR_LOGGER_NAME = "name";
	private static final String STR_LOGGER_PARAMS = "params";

	public static Collection<ExperimentLogger> createLoggers(Element loggers,
			Trial t) {
		Collection<ExperimentLogger> res = new LinkedList<ExperimentLogger>();

		NodeList loggerList = loggers.getElementsByTagName(STR_LOGGER);
		for (int i = 0; i < loggerList.getLength(); i++) {
			Element loggerNode = (Element) loggerList.item(i);
			String loggerName = loggerNode
					.getElementsByTagName(STR_LOGGER_NAME).item(0)
					.getTextContent();

			// Element loggerParams =
			// (Element)loggerNode.getElementsByTagName(STR_LOGGER_PARAMS).item(0);
			if (loggerName.equals("PositionLogger")) {
				ProportionalExplorer p = ((MultiScaleMorrisModel) t.getSubject()
						.getModel()).getActionPerformer();
				res.add(new PositionLogger(t.getName(), t.getGroup(), t
						.getSubjectName(), t.getRep(), p));
			} else if (loggerName.equals("PolicyDumper")) {
				res.add(new PolicyDumper(((MultiScaleMorrisModel) t
						.getSubject().getModel()), t.getName(), t.getGroup(), t
						.getSubjectName(), t.getRep()));
			} else {
				throw new RuntimeException("Logger " + loggerName
						+ " not implemented.");
			}
		}

		return res;

	}

}
