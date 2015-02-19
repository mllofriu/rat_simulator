package edu.usf.ratsim.experiment.subject;

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

	public void stepCycle() {
		NslModel m = (NslModel) system.nslGetModelRef();
		@SuppressWarnings("unchecked")
		Vector<NslModule> children = (Vector<NslModule>) m
				.nslGetModuleChildrenVector();
		for (NslModule c : children) {
			c.simRun();
		}

		updateBuffers(m);
	}

	public void updateBuffers(NslModule module) {
		@SuppressWarnings("unchecked")
		Vector<NslModule> moduleChildren = module.nslGetModuleChildrenVector();
		NslModule child;

		Enumeration<NslModule> e = moduleChildren.elements();

		module.nslUpdateBuffers();
		while (e.hasMoreElements()) {
			child = e.nextElement();
			updateBuffers(child);
		}
	}
	
	public void setSystem(NslSystem system){
		this.system = system;
	}

	public void stepCycle(int numSteps) {
		// TODO Auto-generated method stub

	}

	public void stepEpoch() {
		// TODO Auto-generated method stub

	}

	public void stepEpoch(int numSteps) {
		// TODO Auto-generated method stub

	}

	public void stepModule() {
		// TODO Auto-generated method stub

	}

	public void stepModule(int numSteps) {
		// TODO Auto-generated method stub

	}

	public void initSys() {
		// TODO Auto-generated method stub

	}

	public void initModule() {
		// TODO Auto-generated method stub

	}

	public void initRunEpoch() {
		// TODO Auto-generated method stub

	}

	public void initRun() {
		// TODO Auto-generated method stub

	}

	public void runAll() {
		// TODO Auto-generated method stub

	}

	public void runAll(int endEpoch) {
		// TODO Auto-generated method stub

	}

	public void simRun() {
		// TODO Auto-generated method stub

	}

	public void simRun(double endTime) {
		// TODO Auto-generated method stub

	}

	public void run(char simulationType) {
		// TODO Auto-generated method stub

	}

	public void run(double endTime) {
		// TODO Auto-generated method stub

	}

	public void endRun() {
		// TODO Auto-generated method stub

	}

	public void endRunEpoch() {
		// TODO Auto-generated method stub

	}

	public void initTrainEpoch() {
		// TODO Auto-generated method stub

	}

	public void initTrain() {
		// TODO Auto-generated method stub

	}

	public void trainAll() {
		// TODO Auto-generated method stub

	}

	public void trainAll(int endEpoch) {
		// TODO Auto-generated method stub

	}

	public void simTrain() {
		// TODO Auto-generated method stub

	}

	public void simTrain(double endTime) {
		// TODO Auto-generated method stub

	}

	public void train(char simulationType) {
		// TODO Auto-generated method stub

	}

	public void train(double endTime) {
		// TODO Auto-generated method stub

	}

	public void endTrain() {
		// TODO Auto-generated method stub

	}

	public void endTrainEpoch() {
		// TODO Auto-generated method stub

	}

	public void trainAndRunAll() {
		// TODO Auto-generated method stub

	}

	public void endModule() {
		// TODO Auto-generated method stub

	}

	public void endSys() {
		// TODO Auto-generated method stub

	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public void breakAll() {
		// TODO Auto-generated method stub

	}

	public void breakModules() {
		// TODO Auto-generated method stub

	}

	public void breakEpochs() {
		// TODO Auto-generated method stub

	}

	public void breakCycles() {
		// TODO Auto-generated method stub

	}

	public void continueAll() {
		// TODO Auto-generated method stub

	}

	public void continueAll(double endTime) {
		// TODO Auto-generated method stub

	}

	public void continueModule() {
		// TODO Auto-generated method stub

	}

	public void continueEpoch() {
		// TODO Auto-generated method stub

	}

	public void continueCycle() {
		// TODO Auto-generated method stub

	}

	public void continueModule(int endModule) {
		// TODO Auto-generated method stub

	}

	public void continueEpoch(int endEpoch) {
		// TODO Auto-generated method stub

	}

	public void continueCycle(int endCycle) {
		// TODO Auto-generated method stub

	}

}
