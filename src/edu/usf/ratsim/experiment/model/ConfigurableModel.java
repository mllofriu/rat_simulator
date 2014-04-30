package edu.usf.ratsim.experiment.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.FlashingActiveGoalDecider;
import edu.usf.ratsim.nsl.modules.FlashingTaxicFoodFinderSchema;
import edu.usf.ratsim.nsl.modules.HeadingAngle;
import edu.usf.ratsim.nsl.modules.qlearning.Reward;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.WTAVotes;
import edu.usf.ratsim.nsl.modules.qlearning.update.SingleStateQL;
import edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.support.ElementWrapper;
import edu.usf.ratsim.support.Utiles;

public class ConfigurableModel extends NslModel implements RLRatModel {
	private static final String STR_MODULE_TYPE = "type";
	private static final String STR_MODULE_PARAMS = "params";
	private static final String STR_MODULE_NAME = "name";
	private List<ArtificialPlaceCellLayerWithIntention> pclsI;
	private List<ArtificialPlaceCellLayer> pcls;
	private ProportionalExplorer actionPerformerVote;

	private List<ElementWrapper> connections;
	private List<PolicyDumper> polDumpers;

	public ConfigurableModel(ElementWrapper modelNode, IRobot robot,
			ExperimentUniverse universe) {
		super("ConfigurableModel", (NslModule) null);

		pclsI = new LinkedList<ArtificialPlaceCellLayerWithIntention>();
		pcls = new LinkedList<ArtificialPlaceCellLayer>();
		polDumpers = new LinkedList<PolicyDumper>();
		for (ElementWrapper module : modelNode.getChild("modules").getChildren(
				"module")) {
			String type = module.getChildText(STR_MODULE_TYPE);
			String name = module.getChildText(STR_MODULE_NAME);
			ElementWrapper params = module.getChild(STR_MODULE_PARAMS);

			if (type.equals("SingleLayerAS")) {
				int numStates = params.getChildInt("numStates");
				new WTAVotes(name, this, numStates);
			} else if (type.equals("PCLayer")) {
				float radius = params.getChildFloat("radius");
				pcls.add(new ArtificialPlaceCellLayer(name, this, universe, radius));
			} else if (type.equals("IPCLayer")) {
				float radius = params.getChildFloat("radius");
				int numIntentions = params.getChildInt("numIntentions");
				pclsI.add(new ArtificialPlaceCellLayerWithIntention(name, this,
						universe, numIntentions, radius));
			} else if (type.equals("ProportionalExplorer")) {
				int numLayers = params.getChildInt("numLayers");
				int maxPossibleReward = params.getChildInt("maxPossibleReward");
				float aprioriExploreVar = params.getChildFloat("aprioriExploreVar");
				actionPerformerVote = new ProportionalExplorer(name, this,
						numLayers, maxPossibleReward, aprioriExploreVar, robot, universe);
			} else if (type.equals("TaxicFoodFinderSchema")) {
				int numActions = Utiles.numAngles;
				int maxPossibleReward = params.getChildInt("maxPossibleReward");
				new FlashingTaxicFoodFinderSchema(name, this, robot, universe, numActions, maxPossibleReward);
			} else if (type.equals("GoalDecider")) {
				new FlashingActiveGoalDecider(name, this, universe);
			} else if (type.equals("NormalQL")) {
				int numStates = params.getChildInt("numStates");
				int numActions = Utiles.numAngles;
				float discountFactor = params.getChildFloat("discountFactor");
				float alpha = params.getChildFloat("alpha");
				float initialValue = params.getChildFloat("initialValue");
				polDumpers.add(new SingleStateQL(name, this, numStates, numActions,
						discountFactor, alpha, initialValue));
			} else if (type.equals("Reward")) {
				float foodReward = params.getChildFloat("foodReward");
				float nonFoodReward = params.getChildFloat("nonFoodReward");

				new Reward(name, this, universe, foodReward, nonFoodReward);
			} else if (type.equals("HeadingAngle")) {
				new HeadingAngle(name, this, universe);
			} else {
				throw new RuntimeException("Module " + type
						+ " not implemented.");
			}
		}

		this.connections = modelNode.getChild("connections").getChildren(
				"connection");
	}

	public void initSys() {
		system.setRunEndTime(100);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(100);
	}

	public void makeConn() {
		for (ElementWrapper conn : connections) {
			ElementWrapper from = conn.getChild("from");
			ElementWrapper to = conn.getChild("to");
			System.out.println(from.getChildText("name") + " "
					+ from.getChildText("variable") + " "
					+ to.getChildText("name") + " "
					+ to.getChildText("variable"));

			nslConnect(getChild(from.getChildText("name")),
					from.getChildText("variable"),
					getChild(to.getChildText("name")),
					to.getChildText("variable"));
		}
	}

	private NslModule getChild(String name) {
		for (NslModule module : (Vector<NslModule>) nslGetModuleChildrenVector()) {
			if (module.nslGetName().equals(name))
				return module;
		}

		return null;
	}

	public ProportionalExplorer getActionPerformer() {
		return actionPerformerVote;
	}

	public List<SingleStateQL> getQLValUpdaters() {
		return null;
	}

	public List<ArtificialPlaceCellLayerWithIntention> getPCLLayersIntention() {
		return pclsI;
	}

	@Override
	public List<PolicyDumper> getPolicyDumpers() {
		return polDumpers;
	}

	@Override
	public List<ArtificialPlaceCellLayer> getPCLLayers() {
		return pcls;
	}

}