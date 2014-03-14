package edu.usf.ratsim.experiment.subject.initializer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
