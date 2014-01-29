package experiment.multiscalemorris;

import java.util.LinkedList;
import java.util.List;

import com.sun.tools.javac.util.Pair;

import nsl.modules.ActionPerformerVote;
import nsl.modules.ArtificialPlaceCellLayer;
import nsl.modules.Explorer;
import nsl.modules.HeadingAngle;
import nsl.modules.QLearning;
import nsl.modules.TaxicFoodFinderSchema;
import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import robot.IRobot;
import support.Configuration;
import experiment.ExperimentUniverse;

public class MSMModel extends NslModel {
	private ArtificialPlaceCellLayer[] pcls;
	private HeadingAngle headingAngle;
	private QLearning[] qLearnings;
	private ActionPerformerVote actionPerformerVote;
	private List<Pair<QLearning, ArtificialPlaceCellLayer>> PCLQLPairs;

	public MSMModel(String nslName, NslModule nslParent, IRobot robot,
			ExperimentUniverse univ) {
		super(nslName, nslParent);

		// Explorer actionSel = new Explorer("ActionSelector", this, robot,
		// univ);
//		TaxicFoodFinderSchema actionPerf = new TaxicFoodFinderSchema(
//		 "ActionPerformer", this, robot, univ);
		headingAngle = new HeadingAngle("HeadingPublisher", this, univ);

		// Get some configuration values for place cells + qlearning
		int numLayers = Configuration.getInt("ArtificialPlaceCells.numLayers");
		float minRadius = Configuration
				.getFloat("ArtificialPlaceCells.minRadius");
		float maxRadius = Configuration
				.getFloat("ArtificialPlaceCells.maxRadius");
		float minX = Configuration.getFloat("ArtificialPlaceCells.minX");
		float minY = Configuration.getFloat("ArtificialPlaceCells.minY");
		
		// Created first to let Qlearning execute once when there is food
		actionPerformerVote = new ActionPerformerVote("ActionPerformer", this,
				numLayers, robot);
		
		// Create the layers
		float radius = minRadius;
		pcls = new ArtificialPlaceCellLayer[numLayers];
		qLearnings = new QLearning[numLayers];
		PCLQLPairs = new LinkedList<Pair<QLearning, ArtificialPlaceCellLayer>>();
		// For each layer
		for (int i = 0; i < numLayers; i++) {

			pcls[i] = new ArtificialPlaceCellLayer("PlaceCellLayer", this,
					univ, radius, minX, minY);
			qLearnings[i] = new QLearning("QLearning", this, pcls[i].getSize(),
					robot, univ);

			PCLQLPairs.add(new Pair<QLearning, ArtificialPlaceCellLayer>(qLearnings[i], pcls[i]));
			// Update radius
			radius += (maxRadius - minRadius) / (numLayers - 1);
		}
	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		for (int i = 0; i < pcls.length; i++) {
			nslConnect(pcls[i], "activation", qLearnings[i], "states");
			nslConnect(qLearnings[i].actionVote, actionPerformerVote.votes[i]);
		}
	}

	public List<Pair<QLearning, ArtificialPlaceCellLayer>> getPCLQLPairs() {
		return PCLQLPairs;
	}

}
