package edu.usf.ratsim.experiment.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ArtificialHDCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.FlashingActiveGoalDecider;
import edu.usf.ratsim.nsl.modules.FlashingOrAnyGoalDecider;
import edu.usf.ratsim.nsl.modules.GeneralTaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.Intention;
import edu.usf.ratsim.nsl.modules.JointStatesManyConcatenate;
import edu.usf.ratsim.nsl.modules.JointStatesManyMultiply;
import edu.usf.ratsim.nsl.modules.PlaceIntention;
import edu.usf.ratsim.nsl.modules.WallAvoider;
import edu.usf.ratsim.nsl.modules.qlearning.Reward;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.NoExploration;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalMaxVotes;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.WTAVotes;
import edu.usf.ratsim.nsl.modules.qlearning.update.MultiStateProportionalQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper;
import edu.usf.ratsim.nsl.modules.qlearning.update.SingleStateQL;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;
import edu.usf.ratsim.support.Utiles;

// TODO: works but does not learn in this version
public class DummyModel extends NslModel {
	

	public DummyModel() {
		super("MSMIModel", (NslModule) null);

		

	}

	public void initSys() {

	}

	public void makeConn() {
		
	}
	

}
