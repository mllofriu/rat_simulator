package edu.usf.ratsim.micronsl;

import java.util.HashMap;
import java.util.Map;

public class Model {

	private Map<String, Module> modules;

	public Model() {
		modules = new HashMap<String, Module>();
	}

	public void addModule(String name, Module m) {
		modules.put(name, m);
	}
	
	public Module getModule(String name){
		return modules.get(name);
	}

	public void simRun() {
		// run taking prereqs into account
	}
}
