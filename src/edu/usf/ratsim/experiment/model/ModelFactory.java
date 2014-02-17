package edu.usf.ratsim.experiment.model;

import nslj.src.lang.NslModel;

import org.w3c.dom.Element;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;

public class ModelFactory {

	public static NslModel createModel(Element model, IRobot robot,
			ExperimentUniverse universe) {
		String name = model.getElementsByTagName("name").item(0)
				.getTextContent();

		if (name.equals("MSMModel")) {
			Element params = (Element) model.getElementsByTagName("params")
					.item(0);
			return new MultiScaleMorrisModel(params, robot, universe);
		}

		throw new RuntimeException("Especified model is not implemented");
	}

}
