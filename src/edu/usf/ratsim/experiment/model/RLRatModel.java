package edu.usf.ratsim.experiment.model;

import java.util.List;

import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayerWithIntention;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;
import edu.usf.ratsim.nsl.modules.qlearning.update.PolicyDumper;

public interface RLRatModel {

	public ProportionalExplorer getActionPerformer();

	public List<ArtificialPlaceCellLayerWithIntention> getPCLLayersIntention();

	public List<ArtificialPlaceCellLayer> getPCLLayers();
	
	public List<PolicyDumper> getPolicyDumpers();
	
}
