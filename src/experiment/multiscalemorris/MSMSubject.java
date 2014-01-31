package experiment.multiscalemorris;

import java.util.List;

import com.sun.tools.javac.util.Pair;

import robot.RobotFactory;
import nsl.modules.ActionPerformerVote;
import nsl.modules.ArtificialPlaceCellLayer;
import nsl.modules.qlearning.QLSupport;
import nsl.modules.qlearning.QLUpdateValue;
import nslj.src.lang.NslHierarchy;
import nslj.src.lang.NslModel;
import nslj.src.system.NslInterpreter;
import nslj.src.system.NslSystem;
import experiment.ExpSubject;
import experiment.ExpUniverseFactory;
import experiment.NslSequentialScheduler;

public class MSMSubject implements ExpSubject{

	private MSMModel model;
	private NslSequentialScheduler scheduler;
	private String name;

	public MSMSubject(String name){
		this.name = name;
		
		NslSystem system = new NslSystem(); // Create System
		NslInterpreter interpreter = new NslInterpreter(system); // Create Interpreter
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
		
		System.out.println("Init model");
		model = new MSMModel("MSMHabituationModel", (NslModel) null,
				RobotFactory.getRobot(), ExpUniverseFactory.getUniverse());
		
		// Load it into nsl
		system.addModel(model);
		
		// init Run epochs
		scheduler.initRun();
	}
	
	@Override
	public void stepCycle() {
		scheduler.stepCycle();
	}

	@Override
	public String getName() {
		return name;
	}

	public List<Pair<QLSupport, ArtificialPlaceCellLayer>> getPCLQlearningPairs() {
		return model.getPCLQLPairs();
	}

	public ActionPerformerVote getActionPerformer() {
		return model.getActionPerformer();
	}

}
