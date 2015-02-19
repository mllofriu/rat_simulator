package edu.usf.ratsim.experiment.subject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModule;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslSystem;
import edu.usf.experiment.robot.Robot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.utils.ElementWrapper;
import edu.usf.ratsim.support.Configuration;

public class MultiScaleArtificialPCSubject extends Subject {

	private NslSystem system;
	private NslSequentialScheduler scheduler;
	private NslInterpreter interpreter;

	public MultiScaleArtificialPCSubject(String name, String group,
			ElementWrapper params, Robot robot) {
		super(name, group, params, robot);
		
		initNSL();

		MultiScaleArtificialPCModel model = new MultiScaleArtificialPCModel(
				name, (NslModule) null, params, this);

		system.addModel(model);
		
		scheduler.initRun();
	}

	@Override
	public boolean hasEaten() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stepCycle() {
		scheduler.stepCycle();
	}

	private long checkPCLSeed() {
		File f = new File("pclseed.obj");
		if (f.exists()
				&& Configuration.getBoolean("Experiment.loadSavedPolicy")) {

			try {

				System.out.println("Using existing seed...");
				FileInputStream fin;
				fin = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fin);
				return ((Long) ois.readObject()).longValue();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		Random r = new Random();
		long seed = r.nextLong();

		try {
			FileOutputStream fout = new FileOutputStream("pclseed.obj");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(new Long(seed));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public void initNSL() {
		system = new NslSystem(); // Create System

		interpreter = new NslInterpreter(system); // Create
													// Interpreter
		// scheduler = new NslMultiClockScheduler(system); // Create Scheduler
		scheduler = new NslSequentialScheduler(system); // Create Scheduler

		system.setNoDisplay(false);
		system.setDebug(0);
		system.setStdOut(true);
		system.setStdErr(true);
		system.setInterpreter(interpreter);
		system.nslSetScheduler(scheduler);
		system.nslSetSchedulerMethod("pre");

		system.setRunEndTime(10000000);
		system.nslSetRunDelta(.1);
		system.setNumRunEpochs(1);

		NslHierarchy.nslSetSystem(system);
	}

	@Override
	public boolean hasTriedToEat() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getEatActionNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumActions() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getActionAngle(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getActionForward() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getActionLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getActionRight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPassiveMode(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
