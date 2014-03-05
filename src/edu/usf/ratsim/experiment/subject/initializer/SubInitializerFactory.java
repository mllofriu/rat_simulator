package edu.usf.ratsim.experiment.subject.initializer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point4f;

import nslj.src.lang.NslModel;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.task.PlaceRobotInitallyTask;
import edu.usf.ratsim.experiment.task.PolicyValueUpdater;
import edu.usf.ratsim.support.ElementWrapper;

public class SubInitializerFactory {

	private static final String STR_INITIALIZERS = "initializer";
	private static final String STR_INIT_NAME = "name";
	private static final String STR_INIT_PARAMS = "params";

	public static Collection<SubjectInitializer> createInitializer(List<ElementWrapper> initializersList){
		Collection<SubjectInitializer> res = new LinkedList<SubjectInitializer>();
		
		for (ElementWrapper initNode : initializersList) {
			String initName = initNode.getChildText(STR_INIT_NAME);
			
			ElementWrapper initParams = initNode.getChild(STR_INIT_PARAMS);
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
