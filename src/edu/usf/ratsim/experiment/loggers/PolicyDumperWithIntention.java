package edu.usf.ratsim.experiment.loggers;

import java.util.List;

import edu.usf.ratsim.experiment.ExperimentLogger;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.model.RLRatModel;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.nsl.modules.qlearning.update.QLAlgorithm;

public class PolicyDumperWithIntention implements ExperimentLogger {
	private List<QLAlgorithm> policyDumpers;
	private String rep;
	private String subName;
	private String trial;
	private String groupName;
	private int numIntentions;
	private ExpSubject subject;

	public PolicyDumperWithIntention(ExpSubject expSubject, String trial,
			String groupName, String subName, String rep, int numIntentions) {
		this.subject = expSubject;
		policyDumpers = ((RLRatModel) subject.getModel()).getPolicyDumpers();
		
		this.trial = trial;
		this.subName = subName;
		this.groupName = groupName;
		this.rep = rep;
		this.numIntentions = numIntentions;
	}

	public void log(ExperimentUniverse universe) {
		for (int i = 0; i < policyDumpers.size(); i++)
			policyDumpers.get(i).dumpPolicy(trial, groupName, subName, rep, numIntentions, universe, subject);
	}

	public void finalizeLog() {

	}

}
