package edu.usf.ratsim.experiment.subject.initializer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.task.PlaceRobotInitallyTask;
import edu.usf.ratsim.experiment.task.PolicyValueUpdater;

public class SubInitializerFactory {

	private static final String STR_INITIALIZERS = "initializer";
	private static final String STR_INIT_NAME = "name";
	private static final String STR_INIT_PARAMS = "params";

	public static Collection<SubjectInitializer> createInitializer(NodeList initList){
		Collection<SubjectInitializer> res = new LinkedList<SubjectInitializer>();
		
		for (int i = 0; i < initList.getLength(); i++) {
			Element initNode = (Element) initList.item(i);
			String initName = initNode.getElementsByTagName(STR_INIT_NAME)
					.item(0).getTextContent();
			
			Element initParams = (Element) initNode.getElementsByTagName(
					STR_INIT_PARAMS).item(0);
			if (initName.equals("feederSelector")) {
				res.add(new FeederSelector(initParams));
			} else {
				throw new RuntimeException("Initiliazer " + initName
						+ " not implemented.");
			}
		}
		
		return res;
	}
}
