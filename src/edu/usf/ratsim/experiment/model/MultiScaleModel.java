package edu.usf.ratsim.experiment.model;

import java.util.LinkedList;
import java.util.List;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.GoalDecider;
import edu.usf.ratsim.nsl.modules.TaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.SingleLayerAS;
import edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper;
import edu.usf.ratsim.nsl.modules.qlearning.update.ReverseUpdate;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;

public class MultiScaleModel extends NslModel implements RLRatModel {
	private List<ArtificialPlaceCellLayer> pcls;
	private List<ReverseUpdate> qLUpdVal;
	private ProportionalExplorer actionPerformerVote;
	private List<SingleLayerAS> qLActionSel;
	private List<QLSupport> qlData;
	private GoalDecider goalD;
	private TaxicFoodFinderSchema taxicDrive;

	public MultiScaleModel(ElementWrapper params, IRobot robot,
			ExperimentUniverse universe) {
		super("MSModel", (NslModule) null);
		
		goalD = new GoalDecider("GoalDecider", this, universe);

		// Get some configuration values for place cells + qlearning
		float minRadius = params.getChildFloat("minRadius");
		float maxRadius = params.getChildFloat("maxRadius");
		int numLayers = params.getChildInt("numLayers");

		pcls = new LinkedList<ArtificialPlaceCellLayer>();
		qLUpdVal = new LinkedList<ReverseUpdate>();
		qLActionSel = new LinkedList<SingleLayerAS>();
		qlData = new LinkedList<QLSupport>();

		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					"PlaceCellLayer", this, universe, radius);
			QLSupport qlSupport = new QLSupport(pcl.getSize());
			pcls.add(pcl);
			qlData.add(qlSupport);
			qLActionSel.add(new SingleLayerAS("QLActionSel", this,
					qlSupport, pcl.getSize()));
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
		// Created first to let Qlearning execute once when there is food
		actionPerformerVote = new ProportionalExplorer("ActionPerformer", this,
				numLayers, robot, universe);
		
		// Create taxic driver to override in case of flashing
		taxicDrive = new TaxicFoodFinderSchema("Taxic Driver", this, robot, universe);

		for (int i = 0; i < numLayers; i++) {
			qLUpdVal.add(new ReverseUpdate("QLUpdVal", this, pcls.get(i)
					.getSize(), qlData.get(i), robot, universe));
		}
	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		for (int i = 0; i < pcls.size(); i++) {
			nslConnect(pcls.get(i), "activation", qLActionSel.get(i), "states");
			nslConnect(qLActionSel.get(i).actionVote,
					actionPerformerVote.votes[i]);
		}
		nslConnect(goalD.goalFeeder, taxicDrive.goalFeeder);
	}

	public ProportionalExplorer getActionPerformer() {
		return actionPerformerVote;
	}

	public List<ReverseUpdate> getQLValUpdaters() {
		return qLUpdVal;
	}

	public List<ArtificialPlaceCellLayer> getPCLLayers() {
		return pcls;
	}

	public List<QLSupport> getQLDatas() {
		return qlData;
	}

	@Override
	public List<ArtificialPlaceCellLayerWithIntention> getPCLLayersIntention() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PolicyDumper> getPolicyDumpers() {
		// TODO Auto-generated method stub
		return null;
	}

}
