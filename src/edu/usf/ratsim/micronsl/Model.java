package edu.usf.ratsim.micronsl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.usf.experiment.utils.Debug;

public class Model {

	private Map<String, Module> modules;
	private List<Module> runOrder;

	private boolean modulesChanged;

	public Model() {
		modules = new HashMap<String, Module>();
		modulesChanged = false;
	}

	public void addModule(Module m) {
		if (modules.containsKey(m.getName()))
			throw new RuntimeException("Module " + m.getName()
					+ " already exists");
		modules.put(m.getName(), m);
		modulesChanged = true;
	}

	public Module getModule(String name) {
		return modules.get(name);
	}

	/**
	 * Set up the run order for the modules by performing a dfs search over the
	 * deps graph
	 * 
	 * @return an order could be reached
	 */
	private boolean setupRunOrder() {
		runOrder = new LinkedList<Module>();

		Set<Module> visited = new HashSet<Module>();
		Set<Module> processed = new HashSet<Module>();

		boolean res = true;
		for (Module m : modules.values())
			res = res && process(m, visited, processed, runOrder);

		if (!res)
			System.err.println("Could not find a suitable run order");
		else if (Debug.printExecutionOrder)
			for (Module m : runOrder)
				System.out.println(m.getName());

		return res;
	}

	private boolean process(Module m, Set<Module> visited,
			Set<Module> processed, List<Module> runOrder) {
		if (processed.contains(m))
			return true;
		if (visited.contains(m))
			return false;

		visited.add(m);

		for (Module pr : m.getPreReqs())
			process(pr, visited, processed, runOrder);

		runOrder.add(m);

		processed.add(m);

		return true;
	}

	public void simRun() {
		// If there are new modules, we need to compute run order again
		if (modulesChanged) {
			setupRunOrder();
			modulesChanged = false;
		}
		// run taking prereqs into account
		for (Module m : runOrder)
			m.run();
	}
}
