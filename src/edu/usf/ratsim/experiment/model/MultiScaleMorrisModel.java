package edu.usf.ratsim.experiment.model;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ActionPerformerVote;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.qlearning.QLActionSelection;
import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.nsl.modules.qlearning.QLUpdateValue;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.Configuration;

public class MultiScaleMorrisModel extends NslModel {
	private List<ArtificialPlaceCellLayer> pcls;
	private List<QLUpdateValue> qLUpdVal;
	private ActionPerformerVote actionPerformerVote;
	private List<QLActionSelection> qLActionSel;
	private List<QLSupport> qlData;

	public MultiScaleMorrisModel(Element params, IRobot robot, ExperimentUniverse universe) {
		super("MSMModel", (NslModule) null);

		// Get some configuration values for place cells + qlearning
		float minRadius = Float.parseFloat(params
				.getElementsByTagName("minRadius").item(0).getTextContent());
		float maxRadius = Float.parseFloat(params
				.getElementsByTagName("maxRadius").item(0).getTextContent());
		int numLayers = Integer.parseInt(params
				.getElementsByTagName("numLayers").item(0).getTextContent());
		float minX = Configuration.getFloat("ArtificialPlaceCells.minX");
		float minY = Configuration.getFloat("ArtificialPlaceCells.minY");

		pcls = new LinkedList<ArtificialPlaceCellLayer>();
		qLUpdVal = new LinkedList<QLUpdateValue>();
		qLActionSel = new LinkedList<QLActionSelection>();
		qlData = new LinkedList<QLSupport>();

		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numLayers; i++) {
			ArtificialPlaceCellLayer pcl = new ArtificialPlaceCellLayer(
					"PlaceCellLayer", this, universe, radius, minX, minY);
			QLSupport qlSupport = new QLSupport(pcl.getSize());
			pcls.add(pcl);
			qlData.add(qlSupport);
			qLActionSel.add(new QLActionSelection("QLActionSel", this,
					qlSupport, pcl.getSize(), robot, universe));
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
		// Created first to let Qlearning execute once when there is food
		actionPerformerVote = new ActionPerformerVote("ActionPerformer", this,
				numLayers, robot, universe);

		for (int i = 0; i < numLayers; i++) {
			qLUpdVal.add(new QLUpdateValue("QLUpdVal", this, pcls.get(i)
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
	}

	public ActionPerformerVote getActionPerformer() {
		return actionPerformerVote;
	}

	public List<QLUpdateValue> getQLValUpdaters() {
		return qLUpdVal;
	}

	public List<ArtificialPlaceCellLayer> getPCLLayers() {
		return pcls;
	}

	public List<QLSupport> getQLDatas() {
		return qlData;
	}

}
