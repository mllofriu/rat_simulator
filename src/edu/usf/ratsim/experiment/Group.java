package edu.usf.ratsim.experiment;

import java.util.LinkedList;
import java.util.List;

import edu.usf.ratsim.experiment.subject.ExpSubject;

public class Group {

	List<ExpSubject> subjects;
	String name;
	public Group(String name) {
		super();
		this.subjects = new LinkedList<ExpSubject>();
		this.name = name;
	}
	
	public List<ExpSubject> getSubjects() {
		return subjects;
	}
	
	public void setSubjects(List<ExpSubject> subjects) {
		this.subjects = subjects;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addSubject(ExpSubject subject){
		subjects.add(subject);
	}

	public ExpSubject getSubject(int individualNum) {
		return subjects.get(individualNum);
	}
	
	
}
