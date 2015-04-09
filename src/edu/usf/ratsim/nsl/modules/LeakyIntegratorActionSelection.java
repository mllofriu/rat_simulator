package edu.usf.ratsim.nsl.modules;

import java.util.List;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import edu.usf.experiment.robot.Robot;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.ratsim.support.Debug;

public class LeakyIntegratorActionSelection extends NslModule {

	public float[] accumulated;
	public NslDinFloat1 votes;
	public NslDoutInt0 takenAction;

	private Robot robot;
	private Subject sub;
	private float thrs;
	private float leakRate;
	private float maxReward;

	public LeakyIntegratorActionSelection(String nslName, NslModule nslParent, Subject sub,
			int numVotes, float thrs, float leakRate, float maxReward) {
		super(nslName, nslParent);

		votes = new NslDinFloat1(this, "votes", numVotes);
		accumulated = new float[numVotes];
		for (int i = 0; i < numVotes; i++)
			accumulated[i] = 0;
		takenAction = new NslDoutInt0(this, "takenAction");
		robot = sub.getRobot();
		this.sub = sub;
		
		this.thrs = thrs;
		this.leakRate = leakRate;
		this.maxReward = maxReward;
	}

	public void simRun() {
		Affordance s																																																																																																																																																																																																																																																																				electedAction;
		List<Affordance> aff = robot.checkAffordances(sub
				.getPossibleAffordances());
		
		// Add the votes to the leaked integrator
		for (int action = 0; action < votes.getSize(); action++){
			aff.get(action).setValue(votes.get(action));
			if (aff.get(action).isRealizable()){
				accumulated[action] += votes.get(action);
			}
		}
		
		// Leaky computations
		for (int i = 0; i < accumulated.length; i++)
			accumulated[i] += accumulated[i] * (-leakRate);

		// Publish the taken action
		takenAction.set(aff.indexOf(selectedAction));
		if (Debug.printSelectedValues)
			System.out.println(selectedAction.toString());
		robot.executeAffordance(selectedAction, sub);

	}
}
