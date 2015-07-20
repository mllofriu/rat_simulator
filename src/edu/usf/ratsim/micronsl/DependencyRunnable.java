package edu.usf.ratsim.micronsl;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class DependencyRunnable implements Runnable {

	private List<DependencyRunnable> preReqs;
	
	public DependencyRunnable(){
		preReqs = new LinkedList<DependencyRunnable>();
	}
	
	public List<DependencyRunnable> getPreReqs() {
		return preReqs;
	}

	public void addPreReq(DependencyRunnable dr1) {
		preReqs.add(dr1);
	}
	
	/**
	 * Set up the run order for the modules by performing a dfs search over the
	 * deps graph
	 * 
	 * @return an order could be reached
	 */
	public static boolean checkCycles(Collection<DependencyRunnable> modules) {
		Set<DependencyRunnable> visited = new HashSet<DependencyRunnable>();
		Set<DependencyRunnable> processed = new HashSet<DependencyRunnable>();

		boolean res = true;
		for (DependencyRunnable m : modules)
			res = res && checkCycles(m, visited, processed);

		if (!res)
			System.err.println("Could not find a suitable run order");
	

		return res;
	}

	private static boolean checkCycles(DependencyRunnable dr, Set<DependencyRunnable> visited,
			Set<DependencyRunnable> processed) {
		if (processed.contains(dr))
			return false;
		if (visited.contains(dr))
			return true;

		visited.add(dr);

		boolean cycles = false;
		for (DependencyRunnable pr : dr.getPreReqs())
			cycles = cycles || checkCycles(pr, visited, processed);

		processed.add(dr);

		return cycles;
	}
	
}
