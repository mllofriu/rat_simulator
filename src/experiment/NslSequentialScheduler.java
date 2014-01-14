package experiment;

import java.util.Enumeration;
import java.util.Vector;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import nslj.src.system.NslScheduler;
import nslj.src.system.NslSystem;

public class NslSequentialScheduler extends NslScheduler {

	private NslSystem system;

	public NslSequentialScheduler(NslSystem system) {
		this.system = system;
	}

	@Override
	public void stepCycle() {
		NslModel m = (NslModel) system.nslGetModelRef();
		Vector<NslModule> children = (Vector<NslModule>) m
				.nslGetModuleChildrenVector();
		for (NslModule c : children) {
			c.simRun();
		}

		updateBuffers(m);
	}

	public void updateBuffers(NslModule module) {
		Vector moduleChildren = module.nslGetModuleChildrenVector();
		NslModule child;

		Enumeration e = moduleChildren.elements();

		module.nslUpdateBuffers();
		while (e.hasMoreElements()) {
			child = (NslModule) e.nextElement();
			updateBuffers(child);
		}
	}

	@Override
	public void stepCycle(int numSteps) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepEpoch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepEpoch(int numSteps) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepModule() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepModule(int numSteps) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initSys() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initModule() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initRunEpoch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initRun() {
		// TODO Auto-generated method stub

	}

	@Override
	public void runAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void runAll(int endEpoch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void simRun() {
		// TODO Auto-generated method stub

	}

	@Override
	public void simRun(double endTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(char simulationType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(double endTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endRun() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endRunEpoch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initTrainEpoch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initTrain() {
		// TODO Auto-generated method stub

	}

	@Override
	public void trainAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void trainAll(int endEpoch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void simTrain() {
		// TODO Auto-generated method stub

	}

	@Override
	public void simTrain(double endTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void train(char simulationType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void train(double endTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTrain() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTrainEpoch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void trainAndRunAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endModule() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endSys() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakModules() {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakEpochs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakCycles() {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueAll(double endTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueModule() {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueEpoch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueCycle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueModule(int endModule) {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueEpoch(int endEpoch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueCycle(int endCycle) {
		// TODO Auto-generated method stub

	}

}
