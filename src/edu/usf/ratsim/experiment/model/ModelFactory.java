package edu.usf.ratsim.experiment.model;

import nslj.src.lang.NslModel;

import org.w3c.dom.Element;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;

public class ModelFactory {

	public static NslModel createModel(ElementWrapper modelNode, IRobot robot,
			ExperimentUniverse universe) {
		String name = modelNode.getChildText("name");

		ElementWrapper params = modelNode.getChild("params");
		if (name.equals("MSMModel")) {
			return new MultiScaleModel(params, robot, universe);
		} else 	if (name.equals("MultiScaleMultiIntentionModel")) {
				return new MultiScaleMultiIntentionModel(params, robot, universe);
		} else {
			throw new RuntimeException("Model " + name + " not implemented.");
		}
	}

}
