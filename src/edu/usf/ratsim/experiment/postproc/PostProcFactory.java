package edu.usf.ratsim.experiment.postproc;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point4f;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.Trial;
import edu.usf.ratsim.support.ElementWrapper;

public class PostProcFactory {

	private static final String STR_PP = "postproc";
	private static final String STR_PP_NAME = "name";
	private static final String STR_PP_PARAMS = "params";

	public static Collection<ExperimentPostProc> createPPs(ElementWrapper elementWrapper) {
		Collection<ExperimentPostProc> res = new LinkedList<ExperimentPostProc>();

		List<ElementWrapper> postProcList = elementWrapper.getChildren(STR_PP);
		for (ElementWrapper ppNode : postProcList) {
			String ppName = ppNode.getChildText(STR_PP_NAME);

			ElementWrapper ppParams = ppNode.getChild(STR_PP_PARAMS);
			if (ppName.equals("moveLogs")) {
				res.add(new MoveLogsPP());
			} else {
				throw new RuntimeException("Task " + ppName
						+ " not implemented.");
			}
		}

		return res;
	}
}
