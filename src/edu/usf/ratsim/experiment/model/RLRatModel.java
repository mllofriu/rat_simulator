package edu.usf.ratsim.experiment.model;

import java.util.List;

import edu.usf.ratsim.nsl.modules.ArtificialPlaceCellLayer;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.NoExploration;
import edu.usf.ratsim.nsl.modules.qlearning.update.QLAlgorithm;

public interface RLRatModel {

	public NoExploration getActionPerformer();

	public List<ArtificialPlaceCellLayer> getPCLLayers();
	
	public List<QLAlgorithm> getPolicyDumpers();
	
	public void setPassiveMode(boolean enabled);
	
}
