package experiment.multiscalemorris;

import java.util.LinkedList;
import java.util.List;

import nsl.modules.ActionPerformerVote;
import nsl.modules.ArtificialPlaceCellLayer;
import nsl.modules.HeadingAngle;
import nsl.modules.qlearning.QLActionSelection;
import nsl.modules.qlearning.QLSupport;
import nsl.modules.qlearning.QLUpdateValue;
import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import robot.IRobot;
import support.Configuration;
import support.Utiles;

import com.sun.tools.javac.util.Pair;

import experiment.ExperimentUniverse;

public class MSMModel extends NslModel {
	private ArtificialPlaceCellLayer[] pcls;
	private HeadingAngle headingAngle;
	private QLUpdateValue[] qLUpdVal;
	private ActionPerformerVote actionPerformerVote;
	private List<Pair<QLSupport, ArtificialPlaceCellLayer>> PCLQLPairs;
	private QLActionSelection[] qLActionSel;
	private QLSupport[] qlData;

	public MSMModel(String nslName, NslModule nslParent, IRobot robot,
			ExperimentUniverse univ) {
		super(nslName, nslParent);

		// Explorer actionSel = new Explorer("ActionSelector", this, robot,
		// univ);
		// TaxicFoodFinderSchema actionPerf = new TaxicFoodFinderSchema(
		// "ActionPerformer", this, robot, univ);
		headingAngle = new HeadingAngle("HeadingPublisher", this, univ);

		// Get some configuration values for place cells + qlearning
		int numLayers = Configuration.getInt("ArtificialPlaceCells.numLayers");
		float minRadius = Configuration
				.getFloat("ArtificialPlaceCells.minRadius");
		float maxRadius = Configuration
				.getFloat("ArtificialPlaceCells.maxRadius");
		float minX = Configuration.getFloat("ArtificialPlaceCells.minX");
		float minY = Configuration.getFloat("ArtificialPlaceCells.minY");

		pcls = new ArtificialPlaceCellLayer[numLayers];
		qLUpdVal = new QLUpdateValue[numLayers];
		qLActionSel = new QLActionSelection[numLayers];
		qlData = new QLSupport[numLayers];
		PCLQLPairs = new LinkedList<Pair<QLSupport, ArtificialPlaceCellLayer>>();
		
		// Create the layers
		float radius = minRadius;
		// For each layer
		for (int i = 0; i < numLayers; i++) {
			pcls[i] = new ArtificialPlaceCellLayer("PlaceCellLayer", this,
					univ, radius, minX, minY);
			qlData[i] = new QLSupport(Utiles.discreteAngles.length
					* pcls[i].getSize());
			qLActionSel[i] = new QLActionSelection("QLActionSel", this,
					qlData[i], pcls[i].getSize(), robot, univ);
			PCLQLPairs.add(new Pair<QLSupport, ArtificialPlaceCellLayer>(
					qlData[i], pcls[i]));
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
		// Created first to let Qlearning execute once when there is food
		actionPerformerVote = new ActionPerformerVote("ActionPerformer", this,
				numLayers, robot);

		for (int i = 0; i < numLayers; i++) {
			qLUpdVal[i] = new QLUpdateValue("QLUpdVal", this,
					pcls[i].getSize(),qlData[i], robot, univ);
		}
	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		for (int i = 0; i < pcls.length; i++) {
			nslConnect(pcls[i], "activation", qLActionSel[i], "states");
			nslConnect(qLActionSel[i].actionVote, actionPerformerVote.votes[i]);
		}
	}

	public List<Pair<QLSupport, ArtificialPlaceCellLayer>> getPCLQLPairs() {
		return PCLQLPairs;
	}

	public ActionPerformerVote getActionPerformer() {
		return actionPerformerVote;
	}

}
