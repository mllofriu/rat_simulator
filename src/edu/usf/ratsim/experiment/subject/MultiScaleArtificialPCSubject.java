package edu.usf.ratsim.experiment.subject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;

import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModule;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslSystem;
import edu.usf.experiment.robot.Robot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.EatAffordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;
import edu.usf.experiment.subject.affordance.TurnAffordance;
import edu.usf.experiment.utils.ElementWrapper;
import edu.usf.ratsim.support.Configuration;

public class MultiScaleArtificialPCSubject extends Subject {

	private float step;
	private float leftAngle;
	private float rightAngle;
	
	private NslSystem system;
	private NslSequentialScheduler scheduler;
	private NslInterpreter interpreter;
	private MultiScaleArtificialPCModel model;

	public MultiScaleArtificialPCSubject(String name, String group,
			ElementWrapper params, Robot robot) {
		super(name, group, params, robot);
		
		initNSL();
		
		step = params.getChildFloat("step");
		leftAngle = params.getChildFloat("leftAngle");
		rightAngle = params.getChildFloat("rightAngle");

		model = new MultiScaleArtificialPCModel(
				name, (NslModule) null, params, this);
		
		system.addModel(model);
		
		scheduler.initRun();
	}

	@Override
	public void stepCycle() {
		setHasEaten(false);
		
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
	public void setPassiveMode(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Affordance> getPossibleAffordances() {
		List<Affordance> res = new LinkedList<Affordance>();
		
		res.add(new TurnAffordance(leftAngle, step));
		res.add(new ForwardAffordance(step));
		res.add(new TurnAffordance(rightAngle, step));
		res.add(new EatAffordance());
		
		return res;
	}

	@Override
	public float getMinAngle() {
		return leftAngle;
	}

	@Override
	public void newEpisode() {
		model.newEpisode();
	}

	@Override
	public void newTrial() {
		model.newTrial();
	}

	@Override
	public Affordance getHypotheticAction(Point3f pos, float theta,
			int intention) {
		return model.getHypotheticAction(pos, theta, getPossibleAffordances(), intention);
	}

	@Override
	public int getFeedingFeeder() {
		if (getRobot().getClosestFeeder(-1) != null)
			return getRobot().getClosestFeeder(-1).id;
		else
			return -1;
	}

	@Override
	public void deactivateHPCLayers(LinkedList<Integer> indexList) {
		model.deactivatePCL(indexList);
	}
	
	

}
