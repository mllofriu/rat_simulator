package edu.usf.ratsim.experiment.stopcondition;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.usf.ratsim.experiment.ExperimentUniverse;

public class ConditionFactory {

	private static final String STR_CONDITION = "stopCondition";
	private static final String STR_COND_NAME = "name";
	private static final String STR_COND_PARAMS = "params";

	public static Collection<StopCondition> createConditions(
			Element condintions, Hashtable<String, Point4f> points,
			NslModel nslModel, ExperimentUniverse universe) {
		Collection<StopCondition> res = new LinkedList<StopCondition>();

		NodeList condList = condintions.getElementsByTagName(STR_CONDITION);
		for (int i = 0; i < condList.getLength(); i++) {
			Element condNode = (Element) condList.item(i);
			String condName = condNode.getElementsByTagName(STR_COND_NAME)
					.item(0).getTextContent();

			Element condParams = (Element) condNode.getElementsByTagName(
					STR_COND_PARAMS).item(0);
			if (condName.equals("FoundFood")) {
				res.add(new FoundFoodStopCond(universe));
			} else if (condName.equals("Time")) {
				res.add(new TimeStop(condParams));
			} else {
				throw new RuntimeException("Condition name " + condName + " not implemented.");
			}

		}

		return res;
	}
}
