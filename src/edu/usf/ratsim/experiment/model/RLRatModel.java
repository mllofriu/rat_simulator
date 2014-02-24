package edu.usf.ratsim.experiment.model;

import java.util.List;

import edu.usf.ratsim.nsl.modules.qlearning.QLSupport;
import edu.usf.ratsim.nsl.modules.qlearning.actionselection.ProportionalExplorer;

public interface RLRatModel {

	public ProportionalExplorer getActionPerformer();

	public List<QLSupport> getQLDatas();
}
