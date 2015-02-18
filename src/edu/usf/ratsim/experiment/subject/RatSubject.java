package edu.usf.ratsim.experiment.subject;

import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.utils.ElementWrapper;

public class RatSubject extends Subject {

	public RatSubject(String name, String group, ElementWrapper modelParams) {
		super(name, group, modelParams);
		System.out.println("Building rat subject");
	}
}
