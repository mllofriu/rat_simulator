package edu.usf.ratsim.micronsl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Model {

	private Map<String, Module> modules;
	private ThreadDependencyExecutor pool;

	public Model() {
		modules = new HashMap<String, Module>();
		pool = new ThreadDependencyExecutor(10, 10, 0, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(20));
	}

	public void addModule(Module m) {
		if (modules.containsKey(m.getName()))
			throw new RuntimeException("Module " + m.getName()
					+ " already exists");
		modules.put(m.getName(), m);
	}

	public Module getModule(String name) {
		return modules.get(name);
	}

	public void simRun() {
//		System.out.println("Sending modules to execute");
		pool.execute((Collection<DependencyRunnable>) (Collection<?>) modules
				.values());
//		System.out.println("Sent modules to execute");
		try {
			pool.awaitTermination(1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Finished executing");
	}
}
