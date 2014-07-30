package edu.usf.ratsim.experiment.stopcondition;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.support.ElementWrapper;

public class ConditionFactory {

	private static final String STR_CONDITION = "stopCondition";
	private static final String STR_COND_NAME = "name";
	private static final String STR_COND_PARAMS = "params";

	public static Collection<StopCondition> createConditions(
			ElementWrapper elementWrapper, Hashtable<String, Point4f> points,
			NslModel nslModel, ExperimentUniverse universe) {
		Collection<StopCondition> res = new LinkedList<StopCondition>();

		List<ElementWrapper> condList = elementWrapper.getChildren(STR_CONDITION);
		for (ElementWrapper condNode : condList) {
			String condName = condNode.getChildText(STR_COND_NAME);

			ElementWrapper condParams = condNode.getChild(STR_COND_PARAMS);
			if (condName.equals("FoundFood")) {
				res.add(new FoundFoodStopCond(universe));
			} else if (condName.equals("FoundNFood")) {
				res.add(new FoundNFoodStopCond(universe, condParams));
			} else if (condName.equals("FoundNFoodNoMistakes")) {
				res.add(new FoundNFoodNoMistakesStopCond(universe, condParams));
			} else if (condName.equals("Time")) {
				res.add(new TimeStop(condParams));
			} else {
				throw new RuntimeException("Condition name " + condName
						+ " not implemented.");
			}

		}

		return res;
	}
}
