package edu.usf.ratsim.experiment.model;

import nslj.src.lang.NslModel;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;

public class ModelFactory {

	public static NslModel createModel(ElementWrapper modelNode, IRobot robot,
			ExperimentUniverse universe) {
		String name = modelNode.getChildText("name");

		if (name.equals("MSMModel")) {
			ElementWrapper params = modelNode.getChild("params");
			return new MultiScaleModel(params, robot, universe);
		} else if (name.equals("MultiScaleMultiIntentionModel")) {
			ElementWrapper params = modelNode.getChild("params");
			return new MultiScaleMultiIntentionModel(params, robot, universe);
		} else if (name.equals("ConfigurableModel")) {
			return new ConfigurableModel(modelNode, robot, universe);
		} else {
			throw new RuntimeException("Model " + name + " not implemented.");
		}
	}

}
