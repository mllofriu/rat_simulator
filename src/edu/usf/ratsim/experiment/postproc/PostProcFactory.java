package edu.usf.ratsim.experiment.postproc;

import java.util.LinkedList;
import java.util.List;

import edu.usf.ratsim.experiment.plot.multifeeders.MultiFeedersTrialPlotter;
import edu.usf.ratsim.support.ElementWrapper;

public class PostProcFactory {

	private static final String STR_PP = "postproc";
	private static final String STR_PP_NAME = "name";
	private static final String STR_PP_PARAMS = "params";

	public static List<ExperimentPostProc> createPPs(ElementWrapper elementWrapper) {
		List<ExperimentPostProc> res = new LinkedList<ExperimentPostProc>();

		List<ElementWrapper> postProcList = elementWrapper.getChildren(STR_PP);
		for (ElementWrapper ppNode : postProcList) {
			String ppName = ppNode.getChildText(STR_PP_NAME);

			ElementWrapper ppParams = ppNode.getChild(STR_PP_PARAMS);
			if (ppName.equals("moveLogs")) {
				res.add(new MoveLogsPP());
			} else if (ppName.equals("plots")) {
				res.add(new MultiFeedersTrialPlotter());
			} else if (ppName.equals("toRData")) {
				res.add(new ToRData());
			} else {
				throw new RuntimeException("Task " + ppName
						+ " not implemented.");
			}
		}

		return res;
	}
}
